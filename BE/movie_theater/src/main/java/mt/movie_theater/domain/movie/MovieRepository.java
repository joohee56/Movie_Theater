package mt.movie_theater.domain.movie;

import mt.movie_theater.domain.moviegenre.MovieGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
}
