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
import mt.movie_theater.domain.screening.ScreeningRepository;
import mt.movie_theater.domain.theater.Region;
import mt.movie_theater.domain.theater.Theater;
import mt.movie_theater.domain.theater.TheaterRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
        Movie savedMovie = movieRepository.save(movie);

        Theater theater = Theater.builder()
                .region(Region.SEOUL)
                .build();
        Theater savedTheater = theaterRepository.save(theater);

        Hall hall = Hall.builder()
                .name("1관")
                .theater(savedTheater)
                .screeningType(ScreeningType.IMAX)
                .hallTypeModifier(3000)
                .build();
        Hall savedHall = hallRepository.save(hall);

        LocalDateTime startDate = LocalDateTime.of(2024, 10, 31, 15, 0);
        ScreeningCreateRequest request = ScreeningCreateRequest.builder()
                .movieId(savedMovie.getId())
                .hallId(savedHall.getId())
                .startTime(startDate)
                .build();

        //when
        ScreeningResponse response = screeningService.createScreening(request);

        //then
        assertThat(response.getId()).isNotNull();
        assertThat(response.getEndTime()).isEqualTo(startDate.plus(movie.getDurationMinutes()));
        assertThat(response.getTotalPrice()).isEqualTo(13000);
    }

    @DisplayName("신규 상영시간을 등록할 때, 유효하지 않은 영화일 시 예외가 발생한다.")
    @Test
    void createScreeningNoMovie() {
        //given
        LocalDateTime startDate = LocalDateTime.now();
        ScreeningCreateRequest request = ScreeningCreateRequest.builder()
                .movieId(Long.valueOf(1))
                .startTime(startDate)
                .build();

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
        Movie savedMovie = movieRepository.save(movie);

        LocalDateTime startDate = LocalDateTime.now();
        ScreeningCreateRequest request = ScreeningCreateRequest.builder()
                .movieId(savedMovie.getId())
                .hallId(Long.valueOf(1))
                .startTime(startDate)
                .build();

        //when, then
        assertThatThrownBy(() -> screeningService.createScreening(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효하지 않은 상영관입니다. 상영관 정보를 다시 확인해 주세요.");
    }

    private Movie createMovie(String title, LocalDate releaseDate, Duration durationMinutes, int standardPrice) {
        return Movie.builder()
                .title(title)
                .releaseDate(releaseDate)
                .movieGenres(List.of())
                .movieActors(List.of())
                .ageRating(AgeRating.ALL)
                .screeningType(ScreeningType.TWO_D)
                .standardPrice(standardPrice)
                .durationMinutes(durationMinutes)
                .build();
    }

}