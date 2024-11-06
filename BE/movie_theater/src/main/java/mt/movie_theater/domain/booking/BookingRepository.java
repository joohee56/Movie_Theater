package mt.movie_theater.domain.booking;

import java.util.List;
import java.util.Optional;
import mt.movie_theater.domain.screening.Screening;
import mt.movie_theater.domain.seat.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findByScreeningAndSeat(Screening screening, Seat seat);

    @Query("select b from Booking b where b.user.id= :userId order by b.updatedAt desc")
    List<Booking> findAllByUserId(@Param("userId") Long userId);
}
