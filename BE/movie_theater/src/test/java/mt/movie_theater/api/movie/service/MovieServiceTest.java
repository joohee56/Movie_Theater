package mt.movie_theater.api.movie.service;

import static mt.movie_theater.domain.genre.GenreType.ACTION;
import static mt.movie_theater.domain.genre.GenreType.COMEDY;
import static mt.movie_theater.domain.genre.GenreType.DRAMA;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import mt.movie_theater.IntegrationTestSupport;
import mt.movie_theater.api.movie.request.MovieCreateRequest;
import mt.movie_theater.api.movie.response.MovieResponse;
import mt.movie_theater.api.movie.response.MovieWatchableResponse;
import mt.movie_theater.domain.genre.Genre;
import mt.movie_theater.domain.genre.GenreRepository;
import mt.movie_theater.domain.genre.GenreType;
import mt.movie_theater.domain.hall.Hall;
import mt.movie_theater.domain.hall.HallRepository;
import mt.movie_theater.domain.movie.AgeRating;
import mt.movie_theater.domain.movie.Movie;
import mt.movie_theater.domain.movie.MovieRepository;
import mt.movie_theater.domain.movie.ScreeningType;
import mt.movie_theater.domain.screening.Screening;
import mt.movie_theater.domain.screening.ScreeningRepository;
import mt.movie_theater.domain.theater.Theater;
import mt.movie_theater.domain.theater.TheaterRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class MovieServiceTest extends IntegrationTestSupport {
    @Autowired
    private MovieService movieService;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private TheaterRepository theaterRepository;
    @Autowired
    private HallRepository hallRepository;
    @Autowired
    private ScreeningRepository screeningRepository;

    @DisplayName("신규 영화를 등록한다.")
    @Test
    void createMovie() {
        //given
        List<GenreType> types = List.of(DRAMA, COMEDY);
        Genre genre1 = Genre.create(DRAMA);
        Genre genre2 = Genre.create(COMEDY);
        Genre genre3 = Genre.create(ACTION);
        genreRepository.saveAll(List.of(genre1, genre2, genre3));

        MovieCreateRequest request = MovieCreateRequest.builder()
                .title("청설")
                .subTitle("Hear Me: Our Summer")
                .description("사랑이야기")
                .releaseDate(LocalDate.of(2024, 10, 27))
                .durationMinutes(108)
                .posterUrl("test@test.com")
                .ageRating(AgeRating.ALL)
                .director("조선호")
                .screeningType(ScreeningType.TWO_D)
                .standardPrice(10000)
                .genreTypes(List.of(GenreType.DRAMA, GenreType.COMEDY))
                .actors(List.of("홍경", "노윤서", "김민주"))
                .build();

        //when
        MovieResponse response = movieService.createMovie(request);

        //then
        assertThat(response.getId()).isNotNull();
        assertThat(response)
                .extracting("ageRating", "screeningType")
                .contains(request.getAgeRating().getText(), request.getScreeningType().getText());
        assertThat(response.getMovieGenres()).hasSize(2);
        assertThat(response.getMovieGenres()).containsExactlyInAnyOrder("드라마", "코미디");
        assertThat(response.getMovieActors()).hasSize(3);
        assertThat(response.getMovieActors()).containsExactlyInAnyOrder("홍경", "노윤서", "김민주");
    }

    //...refactoring
    @DisplayName("전체 영화를 조회한다.")
    @Test
    void getAllMovies() {
        //given
        LocalDate releaseDate = LocalDate.now();
        createMovie("청설", releaseDate);
        createMovie("아마존 활명수", releaseDate);
        createMovie("고래와 나", releaseDate);

        //when
        List<MovieResponse> response = movieService.getAllMovies();

        //then
        assertThat(response).hasSize(3);
        assertThat(response)
                .extracting("title")
                .containsExactlyInAnyOrder("청설", "아마존 활명수", "고래와 나");
    }

    @DisplayName("날짜, 영화관이 주어졌을 때, 조건에 맞는 상영시간 유무가 포함된 전체 영화 리스트를 조회한다.")
    @Test
    void getMoviesWithIsWatchable() {
        //given
        LocalDate releaseDate = LocalDate.now();
        Movie movie1 = createMovie("청설", releaseDate);
        Movie movie2 = createMovie("아마존 활명수", releaseDate);
        Movie movie3 = createMovie("보통의 가족", releaseDate);

        Theater theater = createTheater();
        Hall hall = createHall(theater);
        createScreening(movie1, hall, LocalDateTime.of(2024, 11, 01, 00, 00));
        createScreening(movie1, hall, LocalDateTime.of(2024, 11, 01, 00, 00));
        createScreening(movie2, hall, LocalDateTime.of(2024, 11, 01, 00, 00));

        LocalDate date = LocalDate.of(2024, 11, 01);
        //when
        List<MovieWatchableResponse> movies = movieService.getMoviesWithIsWatchable(date, theater.getId());

        //then
        assertThat(movies).hasSize(3)
                .extracting("title", "isWatchable")
                .containsExactlyInAnyOrder(
                        tuple("청설", true),
                        tuple("아마존 활명수", true),
                        tuple("보통의 가족", false)
                );
    }

    private Movie createMovie(String title, LocalDate releaseDate) {
        Movie movie = Movie.builder()
                .title(title)
                .releaseDate(releaseDate)
                .movieGenres(List.of())
                .movieActors(List.of())
                .ageRating(AgeRating.ALL)
                .screeningType(ScreeningType.TWO_D)
                .durationMinutes(Duration.ofMinutes(108))
                .build();
        return movieRepository.save(movie);
    }

    private Theater createTheater() {
        Theater theater = Theater.builder()
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