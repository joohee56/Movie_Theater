package mt.movie_theater.api.booking.service;

import static mt.movie_theater.domain.booking.BookingStatus.CANCELED;
import static mt.movie_theater.domain.booking.BookingStatus.CONFIRMED;
import static mt.movie_theater.domain.payment.Currency.KRW;
import static mt.movie_theater.domain.payment.PayMethod.CARD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import mt.movie_theater.IntegrationSpringTestSupport;
import mt.movie_theater.api.booking.response.BookingResponse;
import mt.movie_theater.api.booking.response.BookingWithDateResponse;
import mt.movie_theater.api.exception.DuplicateSeatBookingException;
import mt.movie_theater.api.exception.PaymentValidationException;
import mt.movie_theater.api.payment.request.PostPaymentRequest;
import mt.movie_theater.domain.booking.Booking;
import mt.movie_theater.domain.booking.BookingRepository;
import mt.movie_theater.domain.booking.BookingStatus;
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
class BookingServiceSpringTest extends IntegrationSpringTestSupport {
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

    @DisplayName("신규 예매를 생성한다.")
    @Test
    void createBookingTest() {
        //given
        User user = createUser();
        Screening screening = createScreening();
        Seat seat = createSeat();
        PaymentHistory paymentHistory = createPaymentHistory();
        String bookingNumber = "";
        LocalDateTime bookingDate = LocalDateTime.of(2024, 10, 28, 15, 0);
        assertThat(seat.isBooked()).isFalse();

        //when
        BookingResponse response = bookingService.createBooking(user.getId(), screening.getId(), seat.getId(), paymentHistory.getId(), bookingNumber,  bookingDate);

        //then
        assertThat(seat.isBooked()).isTrue();
        assertThat(response.getId()).isNotNull();
    }

    @DisplayName("신규 예매를 생성할 때, 유효하지 않은 사용자일 경우 예외가 발생한다.")
    @Test
    void createBookingNoUser() {
        //given
        Screening screening = createScreening();
        Seat seat = createSeat();
        PaymentHistory paymentHistory = createPaymentHistory();
        String bookingNumber = "";
        LocalDateTime bookingDate = LocalDateTime.of(2024, 10, 28, 15, 0);

        //when
        assertThatThrownBy(() -> bookingService.createBooking(Long.valueOf(1), screening.getId(), seat.getId(), paymentHistory.getId(), bookingNumber,  bookingDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효하지 않은 사용자입니다. 사용자 정보를 다시 확인해 주세요.");
    }

    @DisplayName("신규 예매를 생성할 때, 유효하지 않은 상영시간일 경우 예외가 발생한다.")
    @Test
    void createBookingNoScreening() {
        //given
        User user = createUser();
        Seat seat = createSeat();
        PaymentHistory paymentHistory = createPaymentHistory();
        String bookingNumber = "";
        LocalDateTime bookingDate = LocalDateTime.of(2024, 10, 28, 15, 0);

        //when
        assertThatThrownBy(() -> bookingService.createBooking(user.getId(), Long.valueOf(1), seat.getId(), paymentHistory.getId(), bookingNumber,  bookingDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효하지 않은 상영시간입니다. 상영시간 정보를 다시 확인해 주세요.");
    }

    @DisplayName("신규 예매를 생성할 때, 유효하지 않은 좌석일 경우 예외가 발생한다.")
    @Test
    void createBookingNoSeat() {
        //given
        User user = createUser();
        Screening screening = createScreening();
        PaymentHistory paymentHistory = createPaymentHistory();
        String bookingNumber = "";
        LocalDateTime bookingDate = LocalDateTime.of(2024, 10, 28, 15, 0);

        //when
        assertThatThrownBy(() -> bookingService.createBooking(user.getId(), screening.getId(), Long.valueOf(1), paymentHistory.getId(), bookingNumber,  bookingDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효하지 않은 좌석입니다. 좌석 정보를 다시 확인해 주세요.");
    }

    @DisplayName("신규 예매를 생성할 때, 이미 선택된 좌석일 경우 예외가 발생한다.")
    @Test
    void createBookingExistingBooking() {
        //given
        User user = createUser();
        Screening screening = createScreening();
        Seat seat = createSeat();
        PaymentHistory paymentHistory = createPaymentHistory();
        String bookingNumber = "";
        LocalDateTime bookingDate = LocalDateTime.of(2024, 10, 28, 15, 0);
        bookingService.createBooking(user.getId(), screening.getId(), seat.getId(), paymentHistory.getId(), bookingNumber,  bookingDate);

        //when
        assertThatThrownBy(() -> bookingService.createBooking(user.getId(), screening.getId(), seat.getId(), paymentHistory.getId(), bookingNumber,  bookingDate))
                .isInstanceOf(DuplicateSeatBookingException.class)
                .hasMessage("이미 선택된 좌석입니다.");
    }

    @DisplayName("신규 예매를 생성할 때, 상영 시작 시간보다 예매 시간이 더 이후일 경우 예외가 발생한다.")
    @Test
    void createBookingAfterStartDate() {
        //given
        User user = createUser();
        LocalDateTime startDate = LocalDateTime.of(2024, 10, 30, 15, 0);
        Screening screening = createScreening(startDate);
        Seat seat = createSeat();
        PaymentHistory paymentHistory = createPaymentHistory();
        String bookingNumber = "";
        LocalDateTime bookingDate = LocalDateTime.of(2024, 10, 31, 15, 0);

        //when
        assertThatThrownBy(() -> bookingService.createBooking(user.getId(), screening.getId(), seat.getId(), paymentHistory.getId(), bookingNumber,  bookingDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상영 시작 시간이 지났습니다. 다른 상영 시간을 선택해 주세요.");
    }

    @DisplayName("신규 예매를 생성할 때, 유효하지 않은 결제일 경우 예외가 발생한다.")
    @Test
    void createBookingNoPayment() {
        //given
        User user = createUser();
        Screening screening = createScreening();
        Seat seat = createSeat();
        String bookingNumber = "";
        LocalDateTime bookingDate = LocalDateTime.of(2024, 10, 31, 15, 0);

        //when
        assertThatThrownBy(() -> bookingService.createBooking(user.getId(), screening.getId(), seat.getId(), Long.valueOf(1), bookingNumber,  bookingDate))
                .isInstanceOf(PaymentValidationException.class)
                .hasMessage("유효하지 않은 결제입니다. 결제 정보를 다시 확인해 주세요.");
    }

    @DisplayName("예매정보를 조회한다.")
    @Test
    void getBooking() {
        //given
        LocalDateTime startDateTime = LocalDateTime.of(2024, 11, 01, 15, 00);
        Screening screening = createScreening(startDateTime);
        Booking booking = createBooking(screening);

        //when
        BookingResponse bookingResponse = bookingService.getBooking(booking.getId());

        //then
        assertThat(bookingResponse)
                .extracting("startDate", "startTime")
                .containsExactly("2024.11.01(금)", "15:00");
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
        LocalDateTime bookingDate = LocalDateTime.of(2024, 11, 01, 00, 00);
        createBooking(user, screening, CONFIRMED, bookingDate);
        createBooking(user, screening, CONFIRMED, bookingDate);
        createBooking(user, screening, CONFIRMED, bookingDate);
        createBooking(user, screening, CANCELED, bookingDate);

        //when
        Map<BookingStatus, List<BookingWithDateResponse>> bookingStatusMap = bookingService.getBookingHistory(user.getId());

        //then
        assertThat(bookingStatusMap).hasSize(2);
        assertThat(bookingStatusMap.get(CONFIRMED)).hasSize(3);
        assertThat(bookingStatusMap.get(CONFIRMED).get(0))
                .extracting("startDate", "startTime", "bookingDate")
                .containsExactly("2024.11.02(토)", "15:00", "2024.11.01(금)");
        assertThat(bookingStatusMap.get(CANCELED)).hasSize(1);
    }

    @DisplayName("예매를 취소한 후 예매 내역을 조회한다.")
    @Test
    void cancelBookingAndGetBookingHistory() {
        //given
        User user = createUser();
        Seat seat1 = createSeat(true);
        Seat seat2 = createSeat(true);
        Booking booking1 = createBooking(user, seat1, CONFIRMED);
        Booking booking2 = createBooking(user, seat2, CONFIRMED);

        //when
        Map<BookingStatus, List<BookingWithDateResponse>> bookingStatusMap =
                bookingService.cancelBookingAndPaymentGetBookingHistory(user.getId(), booking1.getId());

        //then
        assertThat(bookingStatusMap.get(CONFIRMED)).hasSize(1);
        assertThat(bookingStatusMap.get(CANCELED)).hasSize(1);
        assertThat(booking1.getSeat().isBooked()).isFalse();
        assertThat(booking2.getSeat().isBooked()).isTrue();
    }



    @DisplayName("결제 사후 검증 후 결제내역과 예매를 생성 시, 유효하지 않은 사용자일 경우 예외가 발생한다.")
    @Test
    void createBookingAndPaymentHistoryNoUser() {
        //given
        PostPaymentRequest request = PostPaymentRequest.builder()
                .amount(Long.valueOf(10000))
                .payMethod(CARD)
                .currency(KRW)
                .screeningId(Long.valueOf(1))
                .seatId(Long.valueOf(1))
                .build();
        LocalDateTime bookingTime = LocalDateTime.of(2024, 11, 12, 10, 00);

        //when
        assertThatThrownBy(() -> bookingService.createBookingAndPaymentHistory(Long.valueOf(1), request, bookingTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효하지 않은 사용자입니다. 사용자 정보를 다시 확인해 주세요.");
    }

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

    private Seat createSeat() {
        Seat seat = Seat.builder()
                .seatLocation(new SeatLocation("A", "1"))
                .build();
        return seatRepository.save(seat);
    }
    private Seat createSeat(boolean isBooked) {
        Seat seat = Seat.builder()
                .seatLocation(new SeatLocation("A", "1"))
                .isBooked(isBooked)
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
    private Booking createBooking(Screening screening) {
        Booking booking = Booking.builder()
                .user(createUser())
                .screening(screening)
                .seat(createSeat())
                .paymentHistory(createPaymentHistory())
                .build();
        return bookingRepository.save(booking);
    }
    private Booking createBooking(User user, Seat seat, BookingStatus bookingStatus) {
        Booking booking = Booking.builder()
                .user(user)
                .screening(createScreening())
                .seat(seat)
                .paymentHistory(createPaymentHistory())
                .bookingStatus(bookingStatus)
                .bookingTime(LocalDateTime.of(2024, 11, 06, 00, 00))
                .build();
        return bookingRepository.save(booking);
    }
    private Booking createBooking(User user, Screening screening, BookingStatus bookingStatus, LocalDateTime bookingTime) {
        Booking booking = Booking.builder()
                .user(user)
                .screening(screening)
                .seat(createSeat())
                .paymentHistory(createPaymentHistory())
                .bookingStatus(bookingStatus)
                .bookingTime(bookingTime)
                .build();
        return bookingRepository.save(booking);
    }

}