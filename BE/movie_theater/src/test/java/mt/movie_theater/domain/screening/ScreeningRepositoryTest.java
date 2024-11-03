package mt.movie_theater.domain.screening;

import static mt.movie_theater.domain.theater.Region.GYEONGGI;
import static mt.movie_theater.domain.theater.Region.JEJU;
import static mt.movie_theater.domain.theater.Region.SEOUL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.time.LocalDateTime;
import java.util.List;
import mt.movie_theater.IntegrationTestSupport;
import mt.movie_theater.domain.hall.Hall;
import mt.movie_theater.domain.hall.HallRepository;
import mt.movie_theater.domain.movie.Movie;
import mt.movie_theater.domain.movie.MovieRepository;
import mt.movie_theater.domain.screening.dto.RegionTheaterCountDto;
import mt.movie_theater.domain.screening.dto.TheaterScreeningCountDto;
import mt.movie_theater.domain.theater.Region;
import mt.movie_theater.domain.theater.Theater;
import mt.movie_theater.domain.theater.TheaterRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class ScreeningRepositoryTest extends IntegrationTestSupport {
    @Autowired
    private ScreeningRepository screeningRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private TheaterRepository theaterRepository;
    @Autowired
    private HallRepository hallRepository;

    @DisplayName("날짜, 영화가 주어질 때, 조건에 맞는 상영시간을 가진 지역과 지역별 영화관 갯수 리스트를 조회한다.")
    @Test
    void countTheaterByRegion() {
        //given
        LocalDateTime startDateTime = LocalDateTime.of(2024, 11, 01, 00, 00);
        LocalDateTime endDateTime = LocalDateTime.of(2024, 11, 02, 00, 00);
        Movie movie1 = createMovie();
        Movie movie2 = createMovie();

        Theater theater1 = createTheater(SEOUL, "강남");
        Theater theater2 = createTheater(GYEONGGI, "고양스타필드");
        Theater theater3 = createTheater(JEJU, "제주삼화");

        Hall hall1 = createHall(theater1);
        Hall hall2 = createHall(theater2);
        Hall hall3 = createHall(theater3);

        createScreening(movie1, hall1, LocalDateTime.of(2024, 11, 01, 00, 00));
        createScreening(movie1, hall1, LocalDateTime.of(2024, 11, 01, 23, 59));
        createScreening(movie1, hall2, LocalDateTime.of(2024, 11, 01, 23, 59));
        createScreening(movie1, hall3, LocalDateTime.of(2024, 11, 01, 23, 59));

        //상영시간 불일치
        createScreening(movie1, hall1, LocalDateTime.of(2024, 11, 02, 00, 00));
        //영화 불일치
        createScreening(movie2, hall1, LocalDateTime.of(2024, 11, 01, 00, 00));

        //when
        List<RegionTheaterCountDto> regionDtos = screeningRepository.countTheaterByRegion(startDateTime, endDateTime, movie1.getId());

        //then
        assertThat(regionDtos).hasSize(3)
                .extracting("region", "count")
                .containsExactlyInAnyOrder(
                        tuple(SEOUL, Long.valueOf(2)),
                        tuple(GYEONGGI, Long.valueOf(1)),
                        tuple(JEJU, Long.valueOf(1))
                );
    }

    @DisplayName("주어진 날짜, 영화, 영화관에 해당하는 상영시간을 조회한다.")
    @Test
    void findAllByDateAndMovieIdAndTheaterId() {
        //given
        LocalDateTime startDateTime = LocalDateTime.of(2024, 11, 01, 00, 00);
        LocalDateTime endDateTime = LocalDateTime.of(2024, 11, 02, 00, 00);
        Movie movie1 = createMovie();
        Movie movie2 = createMovie();

        Theater theater1 = createTheater(SEOUL, "강남");
        Theater theater2 = createTheater(GYEONGGI, "고양스타필드");

        Hall hall1 = createHall(theater1);
        Hall hall2 = createHall(theater2);

        createScreening(movie1, hall1, LocalDateTime.of(2024, 11, 01, 00, 00));
        //날짜 불일치
        createScreening(movie1, hall1, LocalDateTime.of(2024, 11, 02, 00, 00));
        //영화 불일치
        createScreening(movie2, hall1, LocalDateTime.of(2024, 11, 01, 00, 00));
        //영화관 불일치
        createScreening(movie1, hall2, LocalDateTime.of(2024, 11, 01, 00, 00));

        //when
        List<Screening> screenings = screeningRepository.findAllByDateAndTheaterIdAndOptionalMovieId(
                startDateTime, endDateTime, movie1.getId(), theater1.getId());

        //then
        assertThat(screenings).hasSize(1)
                .extracting("movie", "hall")
                .containsExactlyInAnyOrder(
                        tuple(movie1, hall1)
                );
    }

    @DisplayName("각 영화관에 속하는 상영시간 갯수를 조회한다. 상영시간의 조건은 날짜, 영화, 지역이다.")
    @Test
    void findScreeningCountByRegion() {
        //given
        LocalDateTime startDateTime = LocalDateTime.of(2024, 11, 01, 00, 00);
        LocalDateTime endDateTime = LocalDateTime.of(2024, 11, 02, 00, 00);
        Movie movie1 = createMovie();
        Movie movie2 = createMovie();

        Theater theater1 = createTheater(SEOUL, "강남");
        Theater theater2 = createTheater(SEOUL, "강동");
        Theater theater3 = createTheater(GYEONGGI, "고양스타필드");

        Hall hall1 = createHall(theater1);
        Hall hall2 = createHall(theater2);
        Hall hall3 = createHall(theater3);

        createScreening(movie1, hall1, LocalDateTime.of(2024, 11, 01, 00, 00));
        createScreening(movie1, hall1, LocalDateTime.of(2024, 11, 01, 23, 59));
        createScreening(movie1, hall2, LocalDateTime.of(2024, 11, 01, 00, 00));
        createScreening(movie1, hall3, LocalDateTime.of(2024, 11, 01, 00, 00));

        //상영시간 불일치
        createScreening(movie1, hall1, LocalDateTime.of(2024, 11, 02, 00, 00));
        //영화 불일치
        createScreening(movie2, hall1, LocalDateTime.of(2024, 11, 01, 00, 00));

        //when
        List<TheaterScreeningCountDto> dtos = screeningRepository.findTheaterScreeningCounts(startDateTime, endDateTime, SEOUL, movie1.getId());

        //then
        assertThat(dtos).hasSize(2)
                .extracting("theater", "count")
                .containsExactlyInAnyOrder(
                        tuple(theater1, Long.valueOf(2)),
                        tuple(theater2, Long.valueOf(1))
                );
    }

    @DisplayName("주어진 날짜, 영화관에 해당하는 상영시간이 있는 영화 리스트를 조회한다.")
    @Test
    void findMoviesByDateAndOptionalTheaterId() {
        //given
        LocalDateTime startDateTime = LocalDateTime.of(2024, 11, 01, 00, 00);
        LocalDateTime endDateTime = LocalDateTime.of(2024, 11, 02, 00, 00);
        Movie movie1 = createMovie();
        Movie movie2 = createMovie();
        Movie movie3 = createMovie();

        Theater theater1 = createTheater(SEOUL, "강남");
        Theater theater2 = createTheater(SEOUL, "강동");

        Hall hall1 = createHall(theater1);
        Hall hall2 = createHall(theater2);

        createScreening(movie1, hall1, LocalDateTime.of(2024, 11, 01, 00, 00));
        createScreening(movie1, hall1, LocalDateTime.of(2024, 11, 01, 23, 59));
        createScreening(movie2, hall1, LocalDateTime.of(2024, 11, 01, 00, 00));

        //영화관 불일치
        createScreening(movie1, hall2, LocalDateTime.of(2024, 11, 01, 00, 00));
        //날짜 불일치
        createScreening(movie1, hall1, LocalDateTime.of(2024, 11, 02, 00, 00));

        //when
        List<Movie> movies = screeningRepository.findMoviesByDateAndOptionalTheaterId(startDateTime, endDateTime, theater1.getId());

        //then
        assertThat(movies).hasSize(2);
    }

    private Movie createMovie() {
        Movie movie = Movie.builder().build();
        return movieRepository.save(movie);
    }
    private Theater createTheater(Region region, String name) {
        Theater theater = Theater.builder()
                .region(region)
                .name(name)
                .build();
        return theaterRepository.save(theater);
    }
    private Hall createHall(Theater theater) {
        Hall hall = Hall.builder()
                .theater(theater)
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