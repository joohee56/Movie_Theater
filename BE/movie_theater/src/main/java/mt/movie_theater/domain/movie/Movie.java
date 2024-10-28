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
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import mt.movie_theater.domain.BaseEntity;
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
    @Column(length = 50)
    private String country;
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
}
