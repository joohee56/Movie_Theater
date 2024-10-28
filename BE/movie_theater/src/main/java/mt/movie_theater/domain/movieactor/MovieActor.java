package mt.movie_theater.domain.movieactor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import mt.movie_theater.domain.BaseEntity;
import mt.movie_theater.domain.actor.Actor;
import mt.movie_theater.domain.movie.Movie;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MovieActor extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Actor actor;
    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;
    @Column(length = 50)
    private String roleName;
}
