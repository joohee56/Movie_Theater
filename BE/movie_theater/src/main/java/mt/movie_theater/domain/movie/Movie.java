package mt.movie_theater.domain.movie;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import mt.movie_theater.api.controller.request.MovieCreateRequest;
import mt.movie_theater.domain.BaseEntity;
import mt.movie_theater.domain.actor.Actor;
import mt.movie_theater.domain.genre.Genre;
import mt.movie_theater.domain.genre.GenreType;
import mt.movie_theater.domain.movieactor.MovieActor;
import mt.movie_theater.domain.moviegenre.MovieGenre;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Movie extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String subTitle;
    @Column(columnDefinition = "TEXT")
    private String description;
    private LocalDate releaseDate;
    private Integer durationMinutes;
    private String posterUrl;
    @Enumerated(EnumType.STRING)
    private AgeRating ageRating;
    @Column(length = 50)
    private String director;
    @Enumerated(EnumType.STRING)
    private ScreeningType screeningType;
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<MovieGenre> movieGenres = new ArrayList<>();
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<MovieActor> movieActors = new ArrayList<>();

    @Builder
    public Movie(String title, String subTitle, String description, LocalDate releaseDate, Integer durationMinutes,
                  String posterUrl, AgeRating ageRating, String director, ScreeningType screeningType,
                  List<GenreType> genreTypes, List<String> actors) {

        if (releaseDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("영화 개봉일은 현재보다 이후일 수 없습니다.");
        }

        this.title = title;
        this.subTitle = subTitle;
        this.description = description;
        this.releaseDate = releaseDate;
        this.durationMinutes = durationMinutes;
        this.posterUrl = posterUrl;
        this.ageRating = ageRating;
        this.director = director;
        this.screeningType = screeningType;
        this.movieGenres = genreTypes.stream()
                            .map(genreType -> MovieGenre.create(this, Genre.create(genreType)))
                            .collect(Collectors.toList());
        this.movieActors = actors.stream()
                            .map(actor -> MovieActor.create(this, Actor.create(actor)))
                            .collect(Collectors.toList());
    }
}
