package mt.movie_theater.domain.screening;

import java.time.LocalDateTime;
import java.util.List;
import mt.movie_theater.domain.screening.dto.RegionTheaterCountDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query("select new mt.movie_theater.domain.screening.dto.RegionTheaterCountDto(t.region, count(s)) from Screening s join s.hall.theater t " +
            "where s.startTime >= :startDateTime and s.startTime < :endDateTime "
            + "and (:movieId is null or s.movie.id = :movieId) "
            + "group by t.region")
    List<RegionTheaterCountDto> countTheaterByRegion(@Param("startDateTime") LocalDateTime startDateTime, @Param("endDateTime") LocalDateTime endDateTime, @Param("movieId") Long movieId);

    @Query("select s from Screening s "
            + "where s.startTime >= :startDateTime and s.startTime < :endDateTime "
            + "and s.movie.id= :movieId "
            + "and s.hall.theater.id= :theaterId")
    List<Screening> findAllByDateAndMovieIdAndTheaterId(@Param("startDateTime") LocalDateTime startDateTime, @Param("endDateTime") LocalDateTime endDateTime, @Param("movieId") Long movieId,
                                                        @Param("theaterId") Long theaterId);
}
