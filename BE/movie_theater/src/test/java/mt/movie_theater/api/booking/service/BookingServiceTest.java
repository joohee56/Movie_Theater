package mt.movie_theater.api.booking.service;

import static mt.movie_theater.domain.booking.BookingStatus.CANCELED;
import static mt.movie_theater.domain.booking.BookingStatus.CONFIRMED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import mt.movie_theater.IntegrationTestSupport;
import mt.movie_theater.api.booking.response.BookingResponse;
import mt.movie_theater.api.booking.response.BookingWithDateResponse;
import mt.movie_theater.api.exception.DuplicateSeatBookingException;
import mt.movie_theater.api.exception.PaymentValidationException;
import mt.movie_theater.domain.booking.Booking;
import mt.movie_theater.domain.booking.BookingRepository;
import mt.movie_theater.domain.booking.BookingStatus;
import mt.movie_theater.domain.bookingseat.BookingSeat;
import mt.movie_theater.domain.bookingseat.BookingSeatRepository;
import mt.movie_theater.domain.hall.Hall;
import mt.movie_theater.domain.hall.HallRepository;
import mt.movie_theater.domain.movie.Movie;
import mt.movie_theater.domain.movie.MovieRepository;
import mt.movie_theater.domain.movie.ScreeningType;
import mt.movie_theater.domain.payment.PayStatus;
import mt.movie_theater.domain.payment.PaymentHistory;
import mt.movie_theater.domain.payment.PaymentHistoryRepository;
import mt.movie_theater.domain.screening.Screening;
import mt.movie_theater.domain.screening.ScreeningRepository;
import mt.movie_theater.domain.seat.Seat;
import mt.movie_theater.domain.seat.SeatLocation;
import mt.movie_theater.domain.seat.SeatRepository;
import mt.movie_theater.domain.theater.Theater;
import mt.movie_theater.domain.theater.TheaterRepository;
import mt.movie_theater.domain.user.User;
import mt.movie_theater.domain.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class BookingServiceTest extends IntegrationTestSupport {
    @Autowired
    private BookingService bookingService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private TheaterRepository theaterRepository;
    @Autowired
    private ScreeningRepository screeningRepository;
    @Autowired
    private HallRepository hallRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private PaymentHistoryRepository paymentHistoryRepository;
    @Autowired
    private BookingSeatRepository bookingSeatRepository;

    @DisplayName("예매정보를 조회한다.")
    @Test
    void getBooking() {
        //given
        LocalDateTime startDateTime = LocalDateTime.of(2024, 11, 01, 15, 00);
        Screening screening = createScreening(startDateTime);
        Booking booking = createBooking(screening, LocalDateTime.of(2024, 11, 01, 10, 00));
        booking.addBookingSeat(createBookingSeat("A", "1"));

        //when
        BookingResponse bookingResponse = bookingService.getBooking(booking.getId());

        //then
        assertThat(bookingResponse)
                .extracting("startDate", "startTime", "bookingTime")
                .containsExactly("2024.11.01(금)", "15:00", "2024.11.01(금) 10:00");
    }

    @DisplayName("예매정보를 조회할 때, 유효하지 않은 예매일 경우 예외가 발생한다. ")
    @Test
    void getBookingWithNoBookingId() {
        //when, then
        assertThatThrownBy(() -> bookingService.getBooking(Long.valueOf(1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효하지 않은 예매입니다. 예매 정보를 다시 확인해 주세요.");
    }

    @DisplayName("회원의 전체 예매 내역을 조회한다.")
    @Test
    void getBookingHistory() {
        //given
        User user = createUser();
        Screening screening = createScreening(LocalDateTime.of(2024, 11, 02, 15, 00));
        LocalDateTime bookingTime = LocalDateTime.of(2024, 11, 01, 00, 00);

        Booking booking1 = createBooking(user, screening, CONFIRMED, bookingTime);
        booking1.addBookingSeat(createBookingSeat("A", "1"));

        Booking booking2 = createBooking(user, screening, CONFIRMED, bookingTime);
        booking2.addBookingSeat(createBookingSeat("A", "1"));

        Booking booking3 = createBooking(user, screening, CANCELED, bookingTime);
        booking3.addBookingSeat(createBookingSeat("A", "1"));
        booking3.addBookingSeat(createBookingSeat("A", "2"));

        //when
        Map<BookingStatus, List<BookingWithDateResponse>> bookingStatusMap = bookingService.getBookingHistory(user.getId());

        //then
        assertThat(bookingStatusMap).hasSize(2);
        assertThat(bookingStatusMap.get(CONFIRMED)).hasSize(2);

        assertThat(bookingStatusMap.get(CANCELED)).hasSize(1);
        assertThat(bookingStatusMap.get(CANCELED).getFirst())
                .extracting("startDate", "startTime", "bookingTime")
                .containsExactly("2024.11.02(토)", "15:00", "2024.11.01(금) 00:00");
        assertThat(bookingStatusMap.get(CANCELED).getFirst().getSeats()).hasSize(2)
                .extracting("section", "seatRow")
                .containsExactlyInAnyOrder(
                        tuple("A", "1"),
                        tuple("A", "2")
                );
    }

//    @DisplayName("예매와 결제를 취소하고 예매 내역을 조회한다.")
//    @Test
//    void cancelBookingAndPaymentGetBookingHistory() {
//        //given
//        User user = createUser();
//        LocalDateTime bookingTime = LocalDateTime.of(2024, 11, 01, 00, 00);
//        Booking booking = createBooking(user, CONFIRMED, bookingTime);
//        BookingSeat bookingSeat = createBookingSeat("A", "1");
//        booking.addBookingSeat(bookingSeat);
//
//        //when
//        bookingService.cancelBookingAndPaymentGetBookingHistory(user.getId(), booking.getId());
//
//        //then
//        assertThat(booking.getBookingStatus()).isEqualTo(CANCELED);
//        assertThat(bookingSeat.getSeat().isBooked()).isFalse();
//    }

    private User createUser() {
        User user = User.builder()
                .build();
        return userRepository.save(user);
    }
    private Movie createMovie() {
        Movie movie = Movie.builder()
                .build();
        return movieRepository.save(movie);
    }
    private Theater createTheater() {
        Theater theater = Theater.builder()
                .build();
        return theaterRepository.save(theater);
    }
    private Hall createHall() {
        Hall hall = Hall.builder()
                .screeningType(ScreeningType.TWO_D)
                .theater(createTheater())
                .build();
        return hallRepository.save(hall);
    }
    private PaymentHistory createPaymentHistory() {
        PaymentHistory paymentHistory = PaymentHistory.builder()
                .payStatus(PayStatus.COMPLETED)
                .build();
        return paymentHistoryRepository.save(paymentHistory);
    }
    private Seat createSeat(String section, String seatRow) {
        SeatLocation seatLocation = new SeatLocation(section, seatRow);
        Seat seat = Seat.builder()
                .seatLocation(seatLocation)
                .build();
        return seatRepository.save(seat);
    }
    private Screening createScreening() {
        Screening screening = Screening.builder()
                .movie(createMovie())
                .hall(createHall())
                .startTime(LocalDateTime.of(2024, 11, 01, 00, 00))
                .build();
        return screeningRepository.save(screening);
    }
    private Screening createScreening(LocalDateTime startDateTime) {
        Screening screening = Screening.builder()
                .movie(createMovie())
                .hall(createHall())
                .startTime(startDateTime)
                .build();
        return screeningRepository.save(screening);
    }
    private Booking createBooking(Screening screening, LocalDateTime bookingTime) {
        Booking booking = Booking.builder()
                .user(createUser())
                .screening(screening)
                .paymentHistory(createPaymentHistory())
                .bookingTime(bookingTime)
                .build();
        return bookingRepository.save(booking);
    }
    private Booking createBooking(User user, Screening screening, BookingStatus bookingStatus, LocalDateTime bookingTime) {
        Booking booking = Booking.builder()
                .user(user)
                .screening(screening)
                .paymentHistory(createPaymentHistory())
                .bookingStatus(bookingStatus)
                .bookingTime(bookingTime)
                .build();
        return bookingRepository.save(booking);
    }
    private BookingSeat createBookingSeat(String section, String seatRow) {
        BookingSeat bookingSeat = BookingSeat.builder()
                .seat(createSeat(section, seatRow))
                .build();
        return bookingSeatRepository.save(bookingSeat);
    }

}