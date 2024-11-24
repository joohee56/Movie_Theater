package mt.movie_theater.domain.booking;

import static mt.movie_theater.domain.booking.BookingStatus.CANCELED;
import static mt.movie_theater.domain.booking.BookingStatus.CONFIRMED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.List;
import java.util.Optional;
import mt.movie_theater.IntegrationTestSupport;
import mt.movie_theater.domain.bookingseat.BookingSeat;
import mt.movie_theater.domain.bookingseat.BookingSeatRepository;
import mt.movie_theater.domain.screening.Screening;
import mt.movie_theater.domain.screening.ScreeningRepository;
import mt.movie_theater.domain.user.User;
import mt.movie_theater.domain.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class BookingRepositoryTest extends IntegrationTestSupport {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private ScreeningRepository screeningRepository;
    @Autowired
    private BookingSeatRepository bookingSeatRepository;
    @Autowired
    private UserRepository userRepository;

    @DisplayName("회원에 해당하는 전체 예매 내역을 예매좌석과 함께 수정일자 내림차순으로 조회한다.")
    @Test
    void findAllByUserId() {
        //given
        User user1 = createUser();
        User user2 = createUser();

        Booking booking1 = createBooking(user1, CONFIRMED);
        Booking booking2 = createBooking(user1, CONFIRMED);
        Booking booking3 = createBooking(user1, CONFIRMED);
        booking2.cancel();
        Booking booking4 = createBooking(user2, CONFIRMED); //회원 미해당

        BookingSeat bookingSeat1 = createBookingSeat();
        BookingSeat bookingSeat2 = createBookingSeat();
        BookingSeat bookingSeat3 = createBookingSeat();
        BookingSeat bookingSeat4 = createBookingSeat();

        booking1.addBookingSeat(bookingSeat1);
        booking1.addBookingSeat(bookingSeat2);
        booking2.addBookingSeat(bookingSeat3);
        booking3.addBookingSeat(bookingSeat4);

        //when
        List<Booking> bookings = bookingRepository.findAllWithBookingSeatsByUserId(user1.getId());

        //then
        assertThat(bookings).hasSize(3)
                .extracting("user", "id", "bookingStatus")
                .containsExactly(
                  tuple(user1, booking2.getId(), CANCELED),
                  tuple(user1, booking3.getId(), CONFIRMED),
                  tuple(user1, booking1.getId(), CONFIRMED)
                );
        assertThat(bookings)
                .extracting(booking -> booking.getBookingSeats().size())
                .containsExactlyInAnyOrder(2, 1, 1);
    }

    @DisplayName("Id에 해당하는 예매내역을 예매좌석과 함께 조회한다.")
    @Test
    void findByIdWithBookingSeats() {
        //given
        Booking booking1 = createBooking();
        Booking booking2 = createBooking();
        BookingSeat bookingSeat1 = createBookingSeat();
        BookingSeat bookingSeat2 = createBookingSeat();
        BookingSeat bookingSeat3 = createBookingSeat();
        booking1.addBookingSeat(bookingSeat1);
        booking1.addBookingSeat(bookingSeat2);
        booking1.addBookingSeat(bookingSeat3);

        //when
        Optional<Booking> booking = bookingRepository.findByIdWithBookingSeats(booking1.getId());

        //then
        assertThat(booking).isPresent();
        assertThat(booking.get().getBookingSeats()).hasSize(3)
                .containsExactly(bookingSeat1, bookingSeat2, bookingSeat3);
    }

    private User createUser() {
        User user = User.builder().build();
        return userRepository.save(user);
    }
    private Screening createScreening() {
        Screening screening = Screening.builder().build();
        return screeningRepository.save(screening);
    }
    private Booking createBooking() {
        Booking booking = Booking.builder()
                .build();
        return bookingRepository.save(booking);
    }
    private Booking createBooking(User user, BookingStatus bookingStatus) {
        Booking booking = Booking.builder()
                .user(user)
                .screening(createScreening())
                .bookingStatus(bookingStatus)
                .build();
        return bookingRepository.save(booking);
    }
    private BookingSeat createBookingSeat() {
        BookingSeat bookingSeat = BookingSeat.builder().build();
        return bookingSeatRepository.save(bookingSeat);
    }
}