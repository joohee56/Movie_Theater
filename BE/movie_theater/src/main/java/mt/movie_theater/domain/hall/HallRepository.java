package mt.movie_theater.domain.hall;

import java.util.List;
import mt.movie_theater.domain.hall.dto.HallIdNameDto;
import mt.movie_theater.domain.theater.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HallRepository extends JpaRepository<Hall, Long> {

    @Query("select new mt.movie_theater.domain.hall.dto.HallIdNameDto(h.id, h.name) from Hall h where h.theater.region = :region")
    List<HallIdNameDto> findAllByRegion(@Param("region") Region region);
}
