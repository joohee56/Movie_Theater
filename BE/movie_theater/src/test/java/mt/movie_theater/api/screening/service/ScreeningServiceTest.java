package mt.movie_theater.api.screening.service;

import static mt.movie_theater.domain.movie.ScreeningType.IMAX;
import static mt.movie_theater.domain.movie.ScreeningType.TWO_D;
import static mt.movie_theater.domain.theater.Region.GYEONGGI;
import static mt.movie_theater.domain.theater.Region.JEJU;
import static mt.movie_theater.domain.theater.Region.SEOUL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import mt.movie_theater.IntegrationTestSupport;
import mt.movie_theater.api.screening.request.ScreeningCreateRequest;
import mt.movie_theater.api.screening.response.FullScreeningResponse;
import mt.movie_theater.api.screening.response.ScreeningResponse;
import mt.movie_theater.api.screening.response.ScreeningWithPriceResponse;
import mt.movie_theater.api.screening.response.TheaterScreeningCountResponse;
import mt.movie_theater.api.theater.response.RegionScreeningCountResponse;
import mt.movie_theater.domain.hall.Hall;
import mt.movie_theater.domain.hall.HallRepository;
import mt.movie_theater.domain.movie.AgeRating;
import mt.movie_theater.domain.movie.Movie;
import mt.movie_theater.domain.movie.MovieRepository;
import mt.movie_theater.domain.movie.ScreeningType;
import mt.movie_theater.domain.screening.Screening;
import mt.movie_theater.domain.screening.ScreeningRepository;
import mt.movie_theater.domain.theater.Region;
import mt.movie_theater.domain.theater.Theater;
import mt.movie_theater.domain.theater.TheaterRepository;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class ScreeningServiceTest extends IntegrationTestSupport {
    @Autowired
    private ScreeningService screeningService;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private TheaterRepository theaterRepository;
    @Autowired
    private HallRepository hallRepository;
    @Autowired
    private ScreeningRepository screeningRepository;

    @DisplayName("신규 상영 시간을 등록한다.")
    @Test
    void createScreening() {
        //given
        LocalDate releaseDate = LocalDateTime.now().toLocalDate();
        Movie movie = createMovie("청설", Duration.ofMinutes(108), 10000);
        Theater theater = createTheater(Region.SEOUL, "강남");
        Hall hall = createHall(theater, IMAX, 3000);

        LocalDateTime startDate = LocalDateTime.of(2024, 10, 31, 15, 0);
        LocalDateTime endDate = startDate.plus(movie.getDurationMinutes());
        ScreeningCreateRequest request = createRequest(movie.getId(), hall.getId(), startDate);

        //when
        ScreeningResponse response = screeningService.createScreening(request);

        //then
        assertThat(response.getId()).isNotNull();
        assertThat(response)
                .extracting("startTime", "endTime", "totalPrice")
                .contains("15:00", "16:48", 13000);
    }

    @DisplayName("신규 상영시간을 등록할 때, 유효하지 않은 영화일 시 예외가 발생한다.")
    @Test
    void createScreeningNoMovie() {
        //given
        LocalDateTime startDate = LocalDateTime.now();
        ScreeningCreateRequest request = createRequest(Long.valueOf(1), null, startDate);

        //when, then
        assertThatThrownBy(() -> screeningService.createScreening(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효하지 않은 영화입니다. 영화 정보를 다시 확인해 주세요.");
    }

    @DisplayName("신규 상영시간을 등록할 때, 유효하지 않은 상영관일 시 예외가 발생한다.")
    @Test
    void createScreeningNoHall() {
        //given
        LocalDate releaseDate = LocalDateTime.now().toLocalDate();
        Movie movie = createMovie("청설", Duration.ofMinutes(108), 10000);

        LocalDateTime startDate = LocalDateTime.now();
        ScreeningCreateRequest request = createRequest(movie.getId(), Long.valueOf(1), startDate);

        //when, then
        assertThatThrownBy(() -> screeningService.createScreening(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효하지 않은 상영관입니다. 상영관 정보를 다시 확인해 주세요.");
    }

    @DisplayName("전체 지역 리스트와 지역별 상영시간 갯수를 조회한다. 상영시간 조건에는 날짜와 (영화)가 있다.")
    @Test
    void getRegionsWithScreeningCount() {
        //given
        Movie movie = createMovie("청설", Duration.ofMinutes(108), 10000);

        Theater theater1 = createTheater(SEOUL, "강남");
        Theater theater2 = createTheater(GYEONGGI, "고양스타필드");
        Theater theater3 = createTheater(JEJU, "제주삼화");

        Hall hall1 = createHall(theater1, TWO_D, 0);
        Hall hall2 = createHall(theater2, TWO_D, 0);
        Hall hall3 = createHall(theater3, TWO_D, 0);

        createScreening(movie, hall1, LocalDateTime.of(2024, 11, 01, 00, 00));
        createScreening(movie, hall1, LocalDateTime.of(2024, 11, 01, 00, 00));
        createScreening(movie, hall2, LocalDateTime.of(2024, 11, 01, 00, 00));
        createScreening(movie, hall3, LocalDateTime.of(2024, 11, 01, 00, 00));

        LocalDate date = LocalDate.of(2024, 11, 01);

        //when
        List<RegionScreeningCountResponse> regionTheaterCounts = screeningService.getRegionsWithScreeningCount(date, movie.getId());

        //then
        assertThat(regionTheaterCounts).hasSize(8)
                .extracting("region", "regionDisplay", "count")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("SEOUL", "서울", Long.valueOf(2)),
                        Tuple.tuple("GYEONGGI", "경기", Long.valueOf(1)),
                        Tuple.tuple("INCHEON", "인천", Long.valueOf(0)),
                        Tuple.tuple("DAEJEON_CHUNGCHEONG_SEJONG", "대전/충청/세종", Long.valueOf(0)),
                        Tuple.tuple("BUSAN_DAEGU_GYEONGSANG", "부산/대구/경상", Long.valueOf(0)),
                        Tuple.tuple("GWANGJU_JEONLLA", "광주/전라", Long.valueOf(0)),
                        Tuple.tuple("GANGWON", "강원", Long.valueOf(0)),
                        Tuple.tuple("JEJU", "제주", Long.valueOf(1))
                );
    }

    @DisplayName("전체 지역별 영화관 리스트와 영화관별 상영시간 갯수를 조회한다. 상영시간 조건에는 날짜, (영화)가 있다.")
    @Test
    void getTheatersWithScreeningCount() {
        //given
        Movie movie = createMovie("청설", Duration.ofMinutes(108), 10000);

        Theater theater1 = createTheater(SEOUL, "강남");
        Theater theater2 = createTheater(SEOUL, "강동");
        Theater theater3 = createTheater(GYEONGGI, "고양스타필드");
        Theater theater4 = createTheater(JEJU, "제주삼화");

        Hall hall1 = createHall(theater1, TWO_D, 0);
        Hall hall2 = createHall(theater2, TWO_D, 0);
        Hall hall3 = createHall(theater3, TWO_D, 0);
        Hall hall4 = createHall(theater4, TWO_D, 0);

        LocalDate date = LocalDate.of(2024, 11, 01);
        createScreening(movie, hall1, LocalDateTime.of(2024, 11, 01, 00, 00));
        createScreening(movie, hall1, LocalDateTime.of(2024, 11, 01, 00, 00));
        createScreening(movie, hall2, LocalDateTime.of(2024, 11, 01, 00, 00));
        createScreening(movie, hall3, LocalDateTime.of(2024, 11, 01, 00, 00));


        //when
        List<TheaterScreeningCountResponse> theaterScreeningCounts = screeningService.getTheatersWithScreeningCount(date, SEOUL, movie.getId());

        //then
        assertThat(theaterScreeningCounts).hasSize(2)
                .extracting("theaterName", "screeningCount")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("강남", Long.valueOf(2)),
                        Tuple.tuple("강동", Long.valueOf(1))
                );
    }

    @DisplayName("상영시간 리스트를 조회한다. 상영시간 조건에는 날짜, (영화), 영화관이 있다.")
    @Test
    void getScreenings() {
        //given
        LocalDate date = LocalDate.of(2024, 11, 01);
        Movie movie = createMovie("청설", Duration.ofMinutes(108), 10000);
        Theater theater = createTheater(SEOUL, "서울");
        Hall hall = createHall(theater, TWO_D, 0);

        createScreening(movie, hall, LocalDateTime.of(2024, 11, 01, 00, 00));
        createScreening(movie, hall, LocalDateTime.of(2024, 11, 01, 23, 59));
        createScreening(movie, hall, LocalDateTime.of(2024, 11, 02, 00, 00));

        //when
        List<FullScreeningResponse> screenings = screeningService.getScreenings(date, movie.getId(), theater.getId());

        //then
        assertThat(screenings).hasSize(2)
                .extracting("startTime", "endTime")
                .containsExactlyInAnyOrder(
                        tuple("00:00", "01:48"),
                        tuple("23:59", "01:47")
                );
    }

    @DisplayName("최종 결제 금액과 상영시간을 조회한다.")
    @Test
    void getScreeningWithTotalPrice() {
        //given
        Movie movie = createMovie("청설", Duration.ofMinutes(108), 10000);
        Theater theater = createTheater(SEOUL, "서울");
        Hall hall = createHall(theater, TWO_D, 0);
        LocalDateTime startDateTime = LocalDateTime.of(2024, 11, 01, 15, 00);
        Screening screening = createScreening(movie, hall, startDateTime);

        //when
        ScreeningWithPriceResponse response = screeningService.getScreeningWithTotalPrice(screening.getId());

        //then
        assertThat(response)
                .extracting("startDate", "startTime", "endTime")
                .containsExactly("2024.11.01 (금)", "15:00", "16:48");
    }

    @DisplayName("최종 결제 금액과 상영시간을 조회할 때, 유효하지 않은 상영시간일 경우 예외가 발생한다.")
    @Test
    void getScreeningWithTotalPriceWithNoScreening() {
        //when, then
        assertThatThrownBy(() -> screeningService.getScreeningWithTotalPrice(Long.valueOf(1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효하지 않은 상영시간입니다. 상영시간 정보를 다시 확인해 주세요.");
    }

    private Movie createMovie(String title, Duration durationMinutes, int standardPrice) {
        Movie movie = Movie.builder()
                .title(title)
                .releaseDate(LocalDate.of(2024, 11, 03))
                .movieGenres(List.of())
                .movieActors(List.of())
                .ageRating(AgeRating.ALL)
                .screeningType(TWO_D)
                .standardPrice(standardPrice)
                .durationMinutes(durationMinutes)
                .build();
        return movieRepository.save(movie);
    }

    private Theater createTheater(Region region, String name) {
        Theater theater = Theater.builder()
                .region(region)
                .name(name)
                .build();
        return theaterRepository.save(theater);
    }
    private Hall createHall(Theater theater, ScreeningType screeningType, int hallTypeModifier) {
        Hall hall = Hall.builder()
                .name("1관")
                .theater(theater)
                .screeningType(screeningType)
                .hallTypeModifier(hallTypeModifier)
                .build();
        return hallRepository.save(hall);
    }

    private ScreeningCreateRequest createRequest(Long movieId, Long hallId, LocalDateTime startDate) {
        return ScreeningCreateRequest.builder()
                .movieId(movieId)
                .hallId(hallId)
                .startTime(startDate)
                .build();
    }

    private Screening createScreening(Movie movie, Hall hall, LocalDateTime startTime) {
        Screening screening = Screening.builder()
                .movie(movie)
                .hall(hall)
                .startTime(startTime)
                .endTime(startTime.plus(movie.getDurationMinutes()))
                .build();
        return screeningRepository.save(screening);
    }

}