package mt.movie_theater.api.service.movie;

import static mt.movie_theater.domain.genre.GenreType.ACTION;
import static mt.movie_theater.domain.genre.GenreType.COMEDY;
import static mt.movie_theater.domain.genre.GenreType.DRAMA;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import mt.movie_theater.IntegrationTestSupport;
import mt.movie_theater.api.controller.movie.request.MovieCreateRequest;
import mt.movie_theater.api.controller.movie.response.MovieResponse;
import mt.movie_theater.domain.genre.Genre;
import mt.movie_theater.domain.genre.GenreRepository;
import mt.movie_theater.domain.genre.GenreType;
import mt.movie_theater.domain.movie.AgeRating;
import mt.movie_theater.domain.movie.ScreeningType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class MovieServiceTest extends IntegrationTestSupport {
    @Autowired
    private MovieService movieService;
    @Autowired
    private GenreRepository genreRepository;

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

}