package mt.movie_theater.domain.screening;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {

//    @Query("select s from Screening s where s.startTime >= :startDateTime and s.startTime < :endDateTime " +
//            "and (:movieId is null or s.movie.id = :movieId) " +
//            "and (:theaterId is null or s.hall.theater.id = :theaterId) order by s.startTime asc ")
//    List<Screening> findByDateAndMovieIdAndTheaterId(
//            @Param("startOfDay") LocalDateTime startDateTime,
//            @Param("endOfDay") LocalDateTime endDateTime,
//            @Param("movieId") Long movieId,
//            @Param("theaterId") Long theaterId
//    );
}
