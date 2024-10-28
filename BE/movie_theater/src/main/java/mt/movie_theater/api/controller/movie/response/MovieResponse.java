package mt.movie_theater.api.controller.movie.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mt.movie_theater.domain.movie.AgeRating;
import mt.movie_theater.domain.movie.Movie;
import mt.movie_theater.domain.movie.ScreeningType;
import mt.movie_theater.domain.movieactor.MovieActor;

@Getter
@NoArgsConstructor
public class MovieResponse {
    private Long id;
    private String title;
    private String subTitle;
    private String description;
    @JsonFormat(pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
    private LocalDate releaseDate;
    private Integer durationMinutes;
    private String posterUrl;
    private String ageRating;
    private String director;
    private String screeningType;
    private List<String> movieGenres;
    private List<String> movieActors;

    @Builder
    public MovieResponse(Long id, String title, String subTitle, String description, LocalDate releaseDate,
                         Integer durationMinutes, String posterUrl, AgeRating ageRating, String director,
                         ScreeningType screeningType, List<String> movieGenres, List<String> movieActors) {
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.description = description;
        this.releaseDate = releaseDate;
        this.durationMinutes = durationMinutes;
        this.posterUrl = posterUrl;
        this.ageRating = ageRating.getText();
        this.director = director;
        this.screeningType = screeningType.getText();
        this.movieGenres = movieGenres;
        this.movieActors = movieActors;
    }

    public static MovieResponse create(Movie movie) {
        return MovieResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .subTitle(movie.getSubTitle())
                .description(movie.getDescription())
                .releaseDate(movie.getReleaseDate())
                .durationMinutes(movie.getDurationMinutes())
                .posterUrl(movie.getPosterUrl())
                .ageRating(movie.getAgeRating())
                .director(movie.getDirector())
                .screeningType(movie.getScreeningType())
                .movieGenres(movie.getMovieGenres().stream()
                        .map(movieGenre -> movieGenre.getGenre().getType().getText())
                        .collect(Collectors.toList()))
                .movieActors(movie.getActors().stream()
                        .map(MovieActor::getName)
                        .collect(Collectors.toList()))
                .build();
    }

}
