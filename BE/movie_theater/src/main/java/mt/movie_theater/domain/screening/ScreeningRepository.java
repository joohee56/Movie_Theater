package mt.movie_theater.domain.screening;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {

    @Query("SELECT s FROM Screening s WHERE s.hall.id = :hallId " +
            "AND s.startTime >= :startOfDay AND s.startTime <= :endOfDay " +
            "AND (:movieId IS NULL OR s.movie.id = :movieId)")
    List<Screening> findByHallIdAndDateAndOptionalMovieId(
            @Param("hallId") Long hallId,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay,
            @Param("movieId") Long movieId
    );
}
