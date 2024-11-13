package mt.movie_theater.domain.booking;

import static mt.movie_theater.domain.booking.BookingStatus.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

import java.util.List;
import java.util.Optional;
import mt.movie_theater.IntegrationSpringTestSupport;
import mt.movie_theater.domain.screening.Screening;
import mt.movie_theater.domain.screening.ScreeningRepository;
import mt.movie_theater.domain.seat.Seat;
import mt.movie_theater.domain.seat.SeatRepository;
import mt.movie_theater.domain.user.User;
import mt.movie_theater.domain.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class BookingRepositorySpringTest extends IntegrationSpringTestSupport {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private ScreeningRepository screeningRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private UserRepository userRepository;

    @DisplayName("상영시간과 좌석에 해당하는 예매를 조회한다.")
    @Test
    void findByScreeningIdAndSeatId() {
        //given
        Screening screening = createScreening();
        Seat seat = createSeat();
        createBooking(screening, seat);

        //when
        Optional<Booking> findBooking = bookingRepository.findByScreeningAndSeat(screening, seat);
        //then
        assertThat(findBooking).isPresent();
    }

    @DisplayName("상영시간과 좌석에 해당하는 예매가 없다면, 빈값을 반환한다.")
    @Test
    void findByScreeningIdAndSeatIdWithEmpty() {
        //given
        Screening screening = createScreening();
        Seat seat1 = createSeat();
        Seat seat2 = createSeat();
        Booking booking = createBooking(screening, seat1);
        bookingRepository.save(booking);

        //when
        Optional<Booking> findBooking = bookingRepository.findByScreeningAndSeat(screening, seat2);
        //then
        assertThat(findBooking).isEmpty();
    }

    @DisplayName("회원에 해당하는 전체 예매 내역을 수정일자 내림차순으로 조회한다.")
    @Test
    void findAllByUserId() {
        //given
        User user1 = createUser();
        User user2 = createUser();

        Booking booking1 = createBooking(user1, CONFIRMED);
        Booking booking2 = createBooking(user1, CONFIRMED);
        Booking booking3 = createBooking(user1, CONFIRMED);
        booking2.cancel();

        Booking booking4 = createBooking(user2, CONFIRMED);

        //when
        List<Booking> bookings = bookingRepository.findAllByUserId(user1.getId());

        //then
        assertThat(bookings).hasSize(3)
                .extracting("user", "id", "bookingStatus")
                .containsExactly(
                  tuple(user1, booking2.getId(), CANCELED),
                  tuple(user1, booking3.getId(), CONFIRMED),
                  tuple(user1, booking1.getId(), CONFIRMED)
                );
    }

    private User createUser() {
        User user = User.builder().build();
        return userRepository.save(user);
    }
    private Screening createScreening() {
        Screening screening = Screening.builder().build();
        return screeningRepository.save(screening);
    }
    private Seat createSeat() {
        Seat seat = Seat.builder().build();
        return seatRepository.save(seat);
    }
    private Booking createBooking(Screening screening, Seat seat) {
        Booking booking = Booking.builder()
                .screening(screening)
                .seat(seat)
                .build();
        return bookingRepository.save(booking);
    }
    private Booking createBooking(User user, BookingStatus bookingStatus) {
        Booking booking = Booking.builder()
                .user(user)
                .screening(createScreening())
                .seat(createSeat())
                .bookingStatus(bookingStatus)
                .build();
        return bookingRepository.save(booking);
    }

}