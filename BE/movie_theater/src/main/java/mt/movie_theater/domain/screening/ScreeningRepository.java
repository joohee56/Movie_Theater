package mt.movie_theater.domain.screening;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {

    @Query("select s from Screening s where s.hall.theater.id = :theaterId " +
            "and s.startTime >= :startOfDay and s.startTime < :endOfDay " +
            "and (:movieId IS NULL OR s.movie.id = :movieId) order by s.startTime asc ")
    List<Screening> findByTheaterIdAndDateAndOptionalMovieId(
            @Param("theaterId") Long theaterId,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay,
            @Param("movieId") Long movieId
    );
}
