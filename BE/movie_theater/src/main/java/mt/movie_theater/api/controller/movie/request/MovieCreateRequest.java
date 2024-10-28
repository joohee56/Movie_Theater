package mt.movie_theater.api.controller.movie.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import mt.movie_theater.domain.genre.GenreType;
import mt.movie_theater.domain.movie.AgeRating;
import mt.movie_theater.domain.movie.Movie;
import mt.movie_theater.domain.movie.ScreeningType;

@Getter
public class MovieCreateRequest {

    @NotBlank(message = "영화제목은 필수 입력값입니다.")
    private String title;
    private String subTitle;
    @NotBlank(message = "줄거리는 필수 입력값입니다.")
    private String description;
    @NotNull(message = "개봉일은 필수 입력값입니다.")
    private LocalDate releaseDate;
    @Positive(message = "상영시간은 양수여야합니다.")
    private Integer durationMinutes;
    @NotBlank(message = "포스터 이미지 url은 필수 입력값입니다.")
    private String posterUrl;
    @NotNull(message = "영화 관람 등급은 필수 입력값입니다.")
    private AgeRating ageRating;
    @NotBlank(message = "감독은 필수 입력값입니다.")
    private String director;
    @NotNull(message = "상영타입은 필수 입력값입니다.")
    private ScreeningType screeningType;
    @NotEmpty(message = "장르 리스트는 필수 입력값입니다.")
    private List<GenreType> genreTypes;
    @NotEmpty(message = "배우 리스트는 필수 입력값입니다.")
    private List<String> actors;

    public Movie toEntity() {
        return Movie.builder()
                .title(this.title)
                .subTitle(this.subTitle)
                .releaseDate(this.releaseDate)
                .durationMinutes(this.durationMinutes)
                .posterUrl(this.posterUrl)
                .ageRating(this.ageRating)
                .director(this.director)
                .screeningType(this.screeningType)
                .genreTypes(this.genreTypes)
                .actors(this.actors)
                .build();
    }

    @Builder
    public MovieCreateRequest(String title, String subTitle, String description, LocalDate releaseDate,
                              Integer durationMinutes, String posterUrl, AgeRating ageRating,
                              String director, ScreeningType screeningType, List<GenreType> genreTypes,
                              List<String> actors) {
        this.title = title;
        this.subTitle = subTitle;
        this.description = description;
        this.releaseDate = releaseDate;
        this.durationMinutes = durationMinutes;
        this.posterUrl = posterUrl;
        this.ageRating = ageRating;
        this.director = director;
        this.screeningType = screeningType;
        this.genreTypes = genreTypes;
        this.actors = actors;
    }
}
