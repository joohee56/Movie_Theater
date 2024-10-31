package mt.movie_theater.domain.theater;

import java.util.List;
import mt.movie_theater.domain.theater.dto.TheaterCountDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Long> {

    @Query("select new mt.movie_theater.domain.theater.dto.TheaterCountDto(t.region, count(t)) from Theater t group by t.region")
    List<TheaterCountDto> countTheatersByRegion();
}
