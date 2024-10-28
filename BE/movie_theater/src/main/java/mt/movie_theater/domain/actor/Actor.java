package mt.movie_theater.domain.movieactor;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mt.movie_theater.domain.BaseEntity;
import mt.movie_theater.domain.actor.Actor;
import mt.movie_theater.domain.movie.Movie;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MovieActor extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;
    private String name;

    @Builder
    private MovieActor(Actor actor, Movie movie) {
        this.actor = actor;
        this.movie = movie;
    }

    public static MovieActor create(Movie movie, Actor actor) {
        return MovieActor.builder()
                .movie(movie)
                .actor(actor)
                .build();
    }
}
