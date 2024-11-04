package mt.movie_theater.domain.seat;

import static org.assertj.core.groups.Tuple.tuple;

import java.util.List;
import mt.movie_theater.IntegrationTestSupport;
import mt.movie_theater.domain.hall.Hall;
import mt.movie_theater.domain.hall.HallRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class SeatRepositoryTest extends IntegrationTestSupport {
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private HallRepository hallRepository;

    @DisplayName("")
    @Test
    void findAllByHall() {
        //given
        Hall hall1 = createHall();
        Hall hall2 = createHall();

        createSeat(hall1, "A", "1");
        createSeat(hall1, "A", "2");
        createSeat(hall2, "A", "1");

        //when
        List<Seat> seats = seatRepository.findAllByHall(hall1.getId());

        //then
        Assertions.assertThat(seats).hasSize(2)
                .extracting("hall", "section", "seatNumber")
                .containsExactlyInAnyOrder(
                        tuple(hall1, "A", "1"),
                        tuple(hall1, "A", "2")
                );
    }

    private Hall createHall() {
        Hall hall = Hall.builder()
                    .build();
        return hallRepository.save(hall);
    }

    private Seat createSeat(Hall hall, String section, String seatNumber) {
        Seat seat = Seat.builder()
                .section(section)
                .seatNumber(seatNumber)
                .hall(hall)
                .build();
        return seatRepository.save(seat);
    }

}