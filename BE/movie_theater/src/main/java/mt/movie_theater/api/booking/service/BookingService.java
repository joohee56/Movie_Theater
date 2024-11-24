package mt.movie_theater.api.booking.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mt.movie_theater.api.booking.response.BookingResponse;
import mt.movie_theater.api.booking.response.BookingWithDateResponse;
import mt.movie_theater.api.exception.DuplicateSeatBookingException;
import mt.movie_theater.api.exception.PaymentValidationException;
import mt.movie_theater.api.payment.request.PostPaymentRequest;
import mt.movie_theater.api.payment.service.PaymentHistoryService;
import mt.movie_theater.domain.booking.Booking;
import mt.movie_theater.domain.booking.BookingRepository;
import mt.movie_theater.domain.booking.BookingStatus;
import mt.movie_theater.domain.bookingseat.BookingSeat;
import mt.movie_theater.domain.bookingseat.BookingSeatRepository;
import mt.movie_theater.domain.payment.PayStatus;
import mt.movie_theater.domain.payment.PaymentHistory;
import mt.movie_theater.domain.payment.PaymentHistoryRepository;
import mt.movie_theater.domain.screening.Screening;
import mt.movie_theater.domain.screening.ScreeningRepository;
import mt.movie_theater.domain.seat.Seat;
import mt.movie_theater.domain.seat.SeatRepository;
import mt.movie_theater.domain.user.User;
import mt.movie_theater.domain.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookingService {
    private final BookingRepository bookingRepository;
    private final ScreeningRepository screeningRepository;
    private final SeatRepository seatRepository;
    private final UserRepository userRepository;
    private final PaymentHistoryRepository paymentHistoryRepository;
    private final PaymentHistoryService paymentHistoryService;
    private final BookingSeatRepository bookingSeatRepository;

    @Transactional
    public BookingResponse createBooking(Long userId, Long screeningId, List<Long> seatIds, Long paymentId, String bookingNumber, LocalDateTime bookingTime) {
        User user = validateUser(userId);
        Screening screening = validateScreening(screeningId, bookingTime);
        List<Seat> seats = validateSeats(seatIds, screening);
        PaymentHistory paymentHistory = validatePayment(paymentId);

        Booking booking = Booking.create(user, screening, paymentHistory, bookingNumber, bookingTime, seats);
        return BookingResponse.create(bookingRepository.save(booking));
    }

    public BookingResponse getBooking(Long bookingId) {
        Optional<Booking> booking = bookingRepository.findByIdWithBookingSeats(bookingId);
        if (booking.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 예매입니다. 예매 정보를 다시 확인해 주세요.");
        }
        return BookingResponse.create(booking.get());
    }

    /**
     * 회원의 전체 예매 내역 조회 (confirmed, canceled 기준)
     */
    public Map<BookingStatus, List<BookingWithDateResponse>> getBookingHistory(Long userId) {
        List<Booking> bookings = bookingRepository.findAllWithBookingSeatsByUserId(userId);
        return bookings.stream()
                .collect(Collectors.groupingBy(
                        Booking::getBookingStatus,
                        Collectors.mapping(BookingWithDateResponse::create, Collectors.toList())));
    }

    /**
     * 예매와 결제 취소 후 예매 내역 조회
     */
    @Transactional
    public Map<BookingStatus, List<BookingWithDateResponse>> cancelBookingAndPaymentGetBookingHistory(Long userId, Long bookingId) {
        User user = validateUser(userId);
        Booking booking = validateBookingForCancel(bookingId, user.getId());
        paymentHistoryService.cancelPayment(booking.getPaymentHistory().getImpId(), "예매를 취소합니다.");
        booking.cancel();
        for (BookingSeat bookingSeat : booking.getBookingSeats()) {
            bookingSeat.getSeat().cancel();
        }
        return getBookingHistory(userId);
    }

    /**
     * 결제 사후 검증 후 결제내역, 예매 생성
     */
    @Transactional
    public BookingResponse createBookingAndPaymentHistory(Long userId, PostPaymentRequest request, LocalDateTime bookingTime) {
        User user = validateUser(userId);
        PaymentHistory paymentHistory = paymentHistoryRepository.save(PaymentHistory.create(request, user));
        if (!paymentHistoryService.validatePaymentAmount(request.getImpId(), request.getAmount())) {
            paymentHistoryService.failPayment(request.getImpId(), "비정상적인 접근입니다. 결제 요청이 유효하지 않습니다.");
        }

        return createBooking(user.getId(), request.getScreeningId(), request.getSeatIds(), paymentHistory.getId(), request.getBookingNumber(), bookingTime);
    }

    private User validateUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 사용자입니다. 사용자 정보를 다시 확인해 주세요.");
        }
        return user.get();
    }

    private Screening validateScreening(Long screeningId, LocalDateTime bookingTime) {
        Optional<Screening> screening = screeningRepository.findById(screeningId);
        if (screening.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 상영시간입니다. 상영시간 정보를 다시 확인해 주세요.");
        }
        if (screening.get().getStartTime().isBefore(bookingTime)) {
            throw new IllegalArgumentException("상영 시작 시간이 지났습니다. 다른 상영 시간을 선택해 주세요.");
        }
        return screening.get();
    }

    private PaymentHistory validatePayment(Long paymentId) {
        Optional<PaymentHistory> payment = paymentHistoryRepository.findById(paymentId);
        if (payment.isEmpty() || !payment.get().getPayStatus().equals(PayStatus.COMPLETED)) {
            throw new PaymentValidationException("유효하지 않은 결제입니다. 결제 정보를 다시 확인해 주세요.");
        }
        return payment.get();
    }

    private List<Seat> validateSeats(List<Long> seatIds, Screening screening) {
        List<Seat> seats = seatRepository.findAllByIdIn(seatIds);
        if (seatIds.size() != seats.size()) {
            throw new IllegalArgumentException("유효하지 않은 좌석입니다. 좌석 정보를 다시 확인해 주세요.");
        }

        List<BookingSeat> bookingSeats = bookingSeatRepository.findAllBySeatIdInAndScreening(screening, seatIds);
        for (BookingSeat bookingSeat : bookingSeats) {
            if (bookingSeat.getSeat().isBooked() || bookingSeat.getBooking().getBookingStatus().equals(BookingStatus.CONFIRMED)) {
                throw new DuplicateSeatBookingException("이미 선택된 좌석입니다.");
            }
        }
        return seats;
    }

    private Booking validateBooking(Long bookingId) {
        Optional<Booking> optionalBooking = bookingRepository.findById(bookingId);
        if (optionalBooking.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 예매입니다. 예매 정보를 다시 확인해 주세요.");
        }
        return optionalBooking.get();
    }

    private Booking validateBookingForCancel(Long bookingId, Long userId) {
        Booking booking = validateBooking(bookingId);
        if (booking.getBookingStatus().equals(BookingStatus.CANCELED)) {
            throw new IllegalArgumentException("취소된 예매입니다.");
        }
        if (booking.getUser().getId() != userId) {
            throw new IllegalArgumentException("예매 취소 권한이 없습니다.");
        }
        return booking;
    }
}
