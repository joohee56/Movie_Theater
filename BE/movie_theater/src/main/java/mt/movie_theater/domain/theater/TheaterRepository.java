package mt.movie_theater.domain.theater;

import java.util.List;
import mt.movie_theater.domain.theater.dto.TheaterIdNameDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Long> {
    @Query("select new mt.movie_theater.domain.theater.dto.TheaterIdNameDto(t.id, t.name) from Theater t where t.region=:region")
    List<TheaterIdNameDto> findTheaterIdNameDtoByRegion(@Param("region") Region region);
}
