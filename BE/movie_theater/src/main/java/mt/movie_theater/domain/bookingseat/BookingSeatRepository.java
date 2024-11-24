package mt.movie_theater.domain.bookingseat;

import java.util.List;
import mt.movie_theater.domain.screening.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingSeatRepository extends JpaRepository<BookingSeat, Integer> {
    @Query("select bs from BookingSeat bs"
            + " where bs.booking.screening= :screening"
            + " and bs.seat.id in :seatIds")
    List<BookingSeat> findAllBySeatIdInAndScreening(@Param("screening") Screening screening, @Param("seatIds") List<Long> seatIds);
}
