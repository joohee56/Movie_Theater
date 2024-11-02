package mt.movie_theater.api.service.screening;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import mt.movie_theater.IntegrationTestSupport;
import mt.movie_theater.api.controller.screening.request.ScreeningCreateRequest;
import mt.movie_theater.api.controller.screening.response.ScreeningResponse;
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
import org.junit.jupiter.api.AfterEach;
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

    @AfterEach
    void tearDown() {
        screeningRepository.deleteAllInBatch();
        hallRepository.deleteAllInBatch();
        theaterRepository.deleteAllInBatch();
        movieRepository.deleteAllInBatch();
    }

    @DisplayName("신규 상영 시간을 등록한다.")
    @Test
    void createScreening() {
        //given
        LocalDate releaseDate = LocalDateTime.now().toLocalDate();
        Movie movie = createMovie("청설", releaseDate, Duration.ofMinutes(108), 10000);
        Theater theater = createTheater(Region.SEOUL);
        Hall hall = createHall(theater, ScreeningType.IMAX, 3000);

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
        Movie movie = createMovie("청설", releaseDate, Duration.ofMinutes(108), 10000);

        LocalDateTime startDate = LocalDateTime.now();
        ScreeningCreateRequest request = createRequest(movie.getId(), Long.valueOf(1), startDate);

        //when, then
        assertThatThrownBy(() -> screeningService.createScreening(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효하지 않은 상영관입니다. 상영관 정보를 다시 확인해 주세요.");
    }

    private Movie createMovie(String title, LocalDate releaseDate, Duration durationMinutes, int standardPrice) {
        Movie movie = Movie.builder()
                .title(title)
                .releaseDate(releaseDate)
                .movieGenres(List.of())
                .movieActors(List.of())
                .ageRating(AgeRating.ALL)
                .screeningType(ScreeningType.TWO_D)
                .standardPrice(standardPrice)
                .durationMinutes(durationMinutes)
                .build();
        return movieRepository.save(movie);
    }

    private Theater createTheater(Region region) {
        Theater theater = Theater.builder()
                .region(region)
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