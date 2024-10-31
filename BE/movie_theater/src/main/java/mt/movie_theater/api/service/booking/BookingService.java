package mt.movie_theater.api.service.booking;

import static mt.movie_theater.domain.booking.BookingStatus.CONFIRMED;

import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import mt.movie_theater.api.controller.booking.request.BookingCreateRequest;
import mt.movie_theater.api.controller.booking.response.BookingResponse;
import mt.movie_theater.api.exception.DuplicateSeatBookingException;
import mt.movie_theater.domain.booking.Booking;
import mt.movie_theater.domain.booking.BookingRepository;
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
    public BookingResponse createBooking(BookingCreateRequest request) {
        User user = validateUser(request.getUserId());
        Screening screening = validateScreening(request.getScreeningId());
        Seat seat = validateSeat(request.getSeatId(), screening);

        String bookingNumber = BookingNumberGenerator.generateBookingNumber();
        LocalDateTime bookingTime = LocalDateTime.now();
        Booking booking = Booking.builder()
                .user(user)
                .screening(screening)
                .seat(seat)
                .bookingNumber(bookingNumber)
                .bookingTime(bookingTime)
                .totalPrice(request.getTotalPrice())
                .build();
        Booking savedBooking = bookingRepository.save(booking);
        return BookingResponse.create(savedBooking);
    }

    public User validateUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 사용자입니다. 사용자 정보를 다시 확인해 주세요.");
        }
        return user.get();
    }

    public Screening validateScreening(Long screeningId) {
        Optional<Screening> screening = screeningRepository.findById(screeningId);
        if (screening.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 상영시간입니다. 상영시간 정보를 다시 확인해 주세요.");
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
}