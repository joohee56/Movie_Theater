package mt.movie_theater.domain.screening;

import java.time.LocalDateTime;
import java.util.List;
import mt.movie_theater.domain.screening.dto.RegionTheaterCountDto;
import mt.movie_theater.domain.screening.dto.TheaterScreeningCountDto;
import mt.movie_theater.domain.theater.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {
    @Query("select new mt.movie_theater.domain.screening.dto.RegionTheaterCountDto(t.region, count(s)) from Screening s join s.hall.theater t " +
            "where s.startTime >= :startDateTime and s.startTime < :endDateTime "
            + "and (:movieId is null or s.movie.id = :movieId) "
            + "group by t.region")
    List<RegionTheaterCountDto> countTheaterByRegion(@Param("startDateTime") LocalDateTime startDateTime, @Param("endDateTime") LocalDateTime endDateTime, @Param("movieId") Long movieId);

    @Query("select s from Screening s "
            + "where s.startTime >= :startDateTime and s.startTime < :endDateTime "
            + "and (:movieId is null or s.movie.id= :movieId) "
            + "and s.hall.theater.id= :theaterId")
    List<Screening> findAllByDateTheaterIdAndOptionalMovieId(@Param("startDateTime") LocalDateTime startDateTime, @Param("endDateTime") LocalDateTime endDateTime, @Param("movieId") Long movieId,
                                                             @Param("theaterId") Long theaterId);

    @Query("select new mt.movie_theater.domain.screening.dto.TheaterScreeningCountDto(s.hall.theater, count(s)) from Screening s "
            + "where s.hall.theater.region= :region "
            + "and s.startTime >= :startTime and s.startTime < :endTime "
            + "and (:movieId is null or s.movie.id= :movieId) "
            + "group by s.hall.theater")
    List<TheaterScreeningCountDto> findTheaterScreeningCounts(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime, @Param("region") Region region, @Param("movieId") Long movieId);
}
