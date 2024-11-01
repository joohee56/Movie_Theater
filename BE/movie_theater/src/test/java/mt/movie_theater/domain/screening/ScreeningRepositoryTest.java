package mt.movie_theater.domain.screening;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import mt.movie_theater.IntegrationTestSupport;
import mt.movie_theater.domain.hall.Hall;
import mt.movie_theater.domain.hall.HallRepository;
import mt.movie_theater.domain.movie.Movie;
import mt.movie_theater.domain.movie.MovieRepository;
import mt.movie_theater.domain.theater.Theater;
import mt.movie_theater.domain.theater.TheaterRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ScreeningRepositoryTest extends IntegrationTestSupport {
    @Autowired
    private ScreeningRepository screeningRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private TheaterRepository theaterRepository;
    @Autowired
    private HallRepository hallRepository;

    @AfterEach
    void tearDown() {
        screeningRepository.deleteAllInBatch();
        hallRepository.deleteAllInBatch();
        theaterRepository.deleteAllInBatch();
        movieRepository.deleteAllInBatch();
    }

    @DisplayName("상영관, 날짜, 영화에 해당하는 상영시간을 조회한다.")
    @Test
    void findByHallIdAndDateAndOptionalMovieId() {
        //given
        Movie movie1 = createMovie();
        Movie movie2 = createMovie();
        Theater theater = createTheater();
        Hall hall1 = createHall(theater);
        Hall hall2 = createHall(theater);

        createScreening(movie1, hall1, LocalDateTime.of(2024, 11, 01, 00, 00));
        createScreening(movie1, hall1, LocalDateTime.of(2024, 11, 01, 04, 00));
        createScreening(movie1, hall1, LocalDateTime.of(2024, 11, 01, 23, 59));

        //상영관 불일치
        createScreening(movie1, hall2, LocalDateTime.of(2024, 11, 01, 00, 00));
        //영화 불일치
        createScreening(movie2, hall1, LocalDateTime.of(2024, 11, 01, 00, 00));
        //시작시간 불일치
        createScreening(movie1, hall1, LocalDateTime.of(2024, 11, 02, 00, 00));

        LocalDateTime startOfDay = LocalDateTime.of(2024, 11, 01, 00, 00);
        LocalDateTime endOfDay = LocalDateTime.of(2024, 11, 01, 23, 59);

        //when
        List<Screening> screenings = screeningRepository.findByHallIdAndDateAndOptionalMovieId(
                hall1.getId(), startOfDay, endOfDay, movie1.getId());

        //then
        assertThat(screenings).hasSize(3)
                .extracting(screening -> screening.getMovie().getId(), screening -> screening.getHall().getId())
                .containsExactlyInAnyOrder(
                        tuple(movie1.getId(), hall1.getId()),
                        tuple(movie1.getId(), hall1.getId()),
                        tuple(movie1.getId(), hall1.getId())
                );
    }

    @DisplayName("상영관, 날짜에 해당하는 상영시간을 조회한다.")
    @Test
    void findByHallIdAndDateAndOptionalNoMovieId() {
        //given
        Movie movie = createMovie();
        Theater theater = createTheater();

        Hall hall1 = createHall(theater);
        Hall hall2 = createHall(theater);

        createScreening(movie, hall1, LocalDateTime.of(2024, 11, 01, 00, 00));
        //상영관 불일치
        createScreening(movie, hall2, LocalDateTime.of(2024, 11, 01, 00, 00));
        //상영시간 불일치
        createScreening(movie, hall1, LocalDateTime.of(2024, 11, 02, 00, 00));

        LocalDateTime startOfDay = LocalDateTime.of(2024, 11, 01, 00, 00);
        LocalDateTime endOfDay = LocalDateTime.of(2024, 11, 01, 23, 59);

        //when
        List<Screening> screenings = screeningRepository.findByHallIdAndDateAndOptionalMovieId(
                hall1.getId(), startOfDay, endOfDay, null);

        //then
        assertThat(screenings).hasSize(1);
    }

    private Movie createMovie() {
        Movie movie = Movie.builder()
                .title("청설")
                .durationMinutes(Duration.ofMinutes(108))
                .build();
        return movieRepository.save(movie);
    }
    private Theater createTheater() {
        Theater theater = Theater.builder()
                .name("강남")
                .build();
        return theaterRepository.save(theater);
    }
    private Hall createHall(Theater theater) {
        Hall hall = Hall.builder()
                .theater(theater)
                .name("1관")
                .build();
        return hallRepository.save(hall);
    }
    private Screening createScreening(Movie movie, Hall hall, LocalDateTime startTime) {
        Screening screening = Screening.builder()
                .movie(movie)
                .hall(hall)
                .startTime(startTime)
                .build();
        return screeningRepository.save(screening);
    }

}