package mt.movie_theater.api.service.booking;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import mt.movie_theater.IntegrationTestSupport;
import mt.movie_theater.api.controller.booking.request.BookingCreateRequest;
import mt.movie_theater.api.controller.booking.response.BookingResponse;
import mt.movie_theater.api.exception.DuplicateSeatBookingException;
import mt.movie_theater.domain.booking.BookingRepository;
import mt.movie_theater.domain.hall.Hall;
import mt.movie_theater.domain.hall.HallRepository;
import mt.movie_theater.domain.movie.Movie;
import mt.movie_theater.domain.movie.MovieRepository;
import mt.movie_theater.domain.movie.ScreeningType;
import mt.movie_theater.domain.screening.Screening;
import mt.movie_theater.domain.screening.ScreeningRepository;
import mt.movie_theater.domain.seat.Seat;
import mt.movie_theater.domain.seat.SeatRepository;
import mt.movie_theater.domain.theater.Theater;
import mt.movie_theater.domain.theater.TheaterRepository;
import mt.movie_theater.domain.user.User;
import mt.movie_theater.domain.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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

    @AfterEach
    void tearDown() {
        bookingRepository.deleteAllInBatch();
        screeningRepository.deleteAllInBatch();
        seatRepository.deleteAllInBatch();
        hallRepository.deleteAllInBatch();
        theaterRepository.deleteAllInBatch();
        movieRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }

    @DisplayName("신규 예매를 생성한다.")
    @Test
    void createBooking() {
        //given
        User savedUser = createUser();
        Screening savedScreening = createScreening();
        Seat savedSeat = createSeat();
        BookingCreateRequest request = BookingCreateRequest.builder()
                                        .userId(savedUser.getId())
                                        .screeningId(savedScreening.getId())
                                        .seatId(savedSeat.getId())
                                        .totalPrice(12000).build();
        String bookingNumberPattern = "^\\d{4}-\\d{3}-\\d{5}$";

        //when
        BookingResponse response = bookingService.createBooking(request);

        //then
        assertThat(response.getId()).isNotNull();
        assertThat(response.getBookingNumber().matches(bookingNumberPattern)).isTrue();
    }

    @DisplayName("신규 예매를 생성할 때, 유효하지 않은 사용자일 경우 예외가 발생한다.")
    @Test
    void createBookingNoUser() {
        //given
        Screening savedScreening = createScreening();
        Seat savedSeat = createSeat();
        BookingCreateRequest request = BookingCreateRequest.builder()
                .userId(Long.valueOf(1))
                .screeningId(savedScreening.getId())
                .seatId(savedSeat.getId())
                .totalPrice(12000).build();

        //when
        assertThatThrownBy(() -> bookingService.createBooking(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효하지 않은 사용자입니다. 사용자 정보를 다시 확인해 주세요.");
    }

    @DisplayName("신규 예매를 생성할 때, 유효하지 않은 상영시간일 경우 예외가 발생한다.")
    @Test
    void createBookingNoScreening() {
        //given
        User savedUser = createUser();
        Seat savedSeat = createSeat();
        BookingCreateRequest request = BookingCreateRequest.builder()
                .userId(savedUser.getId())
                .screeningId(Long.valueOf(1))
                .seatId(savedSeat.getId())
                .totalPrice(12000).build();

        //when
        assertThatThrownBy(() -> bookingService.createBooking(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효하지 않은 상영시간입니다. 상영시간 정보를 다시 확인해 주세요.");
    }

    @DisplayName("신규 예매를 생성할 때, 유효하지 않은 좌석일 경우 예외가 발생한다.")
    @Test
    void createBookingNoSeat() {
        //given
        User savedUser = createUser();
        Screening savedScreening = createScreening();
        BookingCreateRequest request = BookingCreateRequest.builder()
                .userId(savedUser.getId())
                .screeningId(savedScreening.getId())
                .seatId(Long.valueOf(1))
                .totalPrice(12000).build();

        //when
        assertThatThrownBy(() -> bookingService.createBooking(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효하지 않은 좌석입니다. 좌석 정보를 다시 확인해 주세요.");
    }

    @DisplayName("신규 예매를 생성할 때, 이미 선택된 좌석일 경우 예외가 발생한다.")
    @Test
    void createBookingExistingBooking() {
        //given
        User savedUser = createUser();
        Screening savedScreening = createScreening();
        Seat savedSeat = createSeat();
        BookingCreateRequest request = BookingCreateRequest.builder()
                .userId(savedUser.getId())
                .screeningId(savedScreening.getId())
                .seatId(savedSeat.getId())
                .totalPrice(12000).build();
        bookingService.createBooking(request);

        //when
        assertThatThrownBy(() -> bookingService.createBooking(request))
                .isInstanceOf(DuplicateSeatBookingException.class)
                .hasMessage("이미 선택된 좌석입니다.");
    }

    public User createUser() {
        User user = User.builder()
                .loginId("joohee56")
                .name("이주희")
                .email("test@test.com")
                .build();
        return userRepository.save(user);
    }
    public Screening createScreening() {
        Movie movie = Movie.builder()
                .posterUrl("test@test.com")
                .title("청설")
                .build();
        Movie savedMovie = movieRepository.save(movie);

        Theater theater = Theater.builder()
                .name("백석벨라시타")
                .build();
        Theater savedTheater = theaterRepository.save(theater);

        Hall hall = Hall.builder()
                .screeningType(ScreeningType.TWO_D)
                .theater(savedTheater)
                .name("102호")
                .build();
        Hall savedHall = hallRepository.save(hall);

        Screening screening = Screening.builder()
                .movie(savedMovie)
                .hall(savedHall)
                .startTime(LocalDateTime.now())
                .build();
        return screeningRepository.save(screening);
    }

    public Seat createSeat() {
        Seat seat = Seat.builder().build();
        return seatRepository.save(seat);
    }

}