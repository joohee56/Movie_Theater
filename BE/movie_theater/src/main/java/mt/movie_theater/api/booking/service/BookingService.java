package mt.movie_theater.api.booking.service;

import static mt.movie_theater.domain.booking.BookingStatus.CONFIRMED;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mt.movie_theater.api.booking.request.BookingCreateRequest;
import mt.movie_theater.api.booking.response.BookingResponse;
import mt.movie_theater.api.booking.response.BookingWithDateResponse;
import mt.movie_theater.api.exception.DuplicateSeatBookingException;
import mt.movie_theater.domain.booking.Booking;
import mt.movie_theater.domain.booking.BookingRepository;
import mt.movie_theater.domain.booking.BookingStatus;
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

    @Transactional
    public BookingResponse createBooking(Long userId, BookingCreateRequest request, LocalDateTime bookingTime) {
        User user = validateUser(userId);
        Screening screening = validateScreening(request.getScreeningId(), bookingTime);
        Seat seat = validateSeat(request.getSeatId(), screening);

        String bookingNumber = BookingNumberGenerator.generateBookingNumber();
        Booking booking = Booking.create(user,screening,seat,bookingNumber,bookingTime, request.getTotalPrice());
        Booking savedBooking = bookingRepository.save(booking);
        seat.book();
        return BookingResponse.create(savedBooking);
    }

    public BookingResponse getBooking(Long bookingId) {
        Optional<Booking> optionalBooking = bookingRepository.findById(bookingId);
        if (optionalBooking.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 예매입니다. 예매 정보를 다시 확인해 주세요.");
        }
        return BookingResponse.create(optionalBooking.get());
    }

    /**
     * 회원의 전체 예매 내역 조회 (confirmed, canceled)
     */
    public Map<BookingStatus, List<BookingWithDateResponse>> getBookingHistory(Long userId) {
        List<Booking> bookings = bookingRepository.findAllByUserId(userId);
        return bookings.stream()
                .collect(Collectors.groupingBy(
                        Booking::getBookingStatus,
                        Collectors.mapping(booking -> BookingWithDateResponse.create(booking), Collectors.toList())));
    }

    /**
     * 예매 취소 후 예매 내역 조회
     */
    @Transactional
    public Map<BookingStatus, List<BookingWithDateResponse>> cancelBookingAndGetBookingHistory(Long userId, Long bookingId) {
        User user = validateUser(userId);
        Booking booking = validateBooking(bookingId, user.getId());
        booking.cancel();
        return getBookingHistory(userId);
    }

    public User validateUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 사용자입니다. 사용자 정보를 다시 확인해 주세요.");
        }
        return user.get();
    }

    public Screening validateScreening(Long screeningId, LocalDateTime bookingTime) {
        Optional<Screening> screening = screeningRepository.findById(screeningId);
        if (screening.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 상영시간입니다. 상영시간 정보를 다시 확인해 주세요.");
        }
        if (screening.get().getStartTime().isBefore(bookingTime)) {
            throw new IllegalArgumentException("상영 시작 시간이 지났습니다. 다른 상영 시간을 선택해 주세요.");
        }
        return screening.get();
    }

    public Seat validateSeat(Long seatId, Screening screening) {
        Optional<Seat> seat = seatRepository.findById(seatId);
        if (seat.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 좌석입니다. 좌석 정보를 다시 확인해 주세요.");
        }
        Optional<Booking> existingBooking = bookingRepository.findByScreeningAndSeat(screening, seat.get());
        if (existingBooking.isPresent() && existingBooking.get().getBookingStatus().equals(CONFIRMED)) {
            throw new DuplicateSeatBookingException("이미 선택된 좌석입니다.");
        }
        return seat.get();
    }

    public Booking validateBooking(Long bookingId, Long userId) {
        Optional<Booking> optionalBooking = bookingRepository.findById(bookingId);
        if (optionalBooking.isEmpty()) {
            throw new IllegalArgumentException("유효하지 예매입니다. 예매 정보를 다시 확인해 주세요.");
        }
        Booking booking = optionalBooking.get();
        if (booking.getBookingStatus().equals(BookingStatus.CANCELED)) {
            throw new IllegalArgumentException("이미 취소된 예매입니다.");
        }
        if (booking.getUser().getId() != userId) {
            throw new IllegalArgumentException("예매 취소 권한이 없습니다.");
        }
        return optionalBooking.get();
    }
}
