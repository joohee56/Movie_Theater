package mt.movie_theater.domain.booking;

import java.util.Optional;
import mt.movie_theater.domain.screening.Screening;
import mt.movie_theater.domain.seat.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findByScreeningAndSeat(Screening screening, Seat seat);
}
