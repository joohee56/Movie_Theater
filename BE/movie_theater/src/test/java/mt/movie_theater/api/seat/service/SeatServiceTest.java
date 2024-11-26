package mt.movie_theater.api.seat.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;

import java.util.List;
import java.util.Map;
import mt.movie_theater.IntegrationTestSupport;
import mt.movie_theater.api.seat.response.SeatResponse;
import mt.movie_theater.api.seat.response.SeatSummaryResponse;
import mt.movie_theater.domain.hall.Hall;
import mt.movie_theater.domain.hall.HallRepository;
import mt.movie_theater.domain.screening.Screening;
import mt.movie_theater.domain.screening.ScreeningRepository;
import mt.movie_theater.domain.seat.Seat;
import mt.movie_theater.domain.seat.SeatLocation;
import mt.movie_theater.domain.seat.SeatRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class SeatServiceTest extends IntegrationTestSupport {
    @Autowired
    private SeatService seatService;
    @Autowired
    private HallRepository hallRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private ScreeningRepository screeningRepository;

    @Transactional
    @DisplayName("상영관에 좌석 리스트를 등록한다.")
    @Test
    void createSeatList() {
        //given
        Hall hall = createHall();
        int rows = 2;
        int columns = 3;

        //when
        List<SeatResponse> seats = seatService.createSeatList(hall.getId(), rows, columns);

        //then
        assertThat(seats).hasSize(6)
                .extracting("section", "seatRow")
                .containsExactlyInAnyOrder(
                        tuple("A", "1"),
                        tuple("A", "2"),
                        tuple("A", "3"),
                        tuple("B", "1"),
                        tuple("B", "2"),
                        tuple("B", "3")
                );
    }

    @DisplayName("상영관에 좌석 리스트를 등록할 때, 잘못된 상영관이 요청된 경우 예외가 발생한다.")
    @Test
    void createSeatListWithNoHall() {
        //given
        int rows = 2;
        int columns = 3;

        //when, then
        assertThatThrownBy(() -> seatService.createSeatList(Long.valueOf(1), rows, columns))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효하지 않은 상영관입니다. 상영관 정보를 다시 확인해 주세요.");
    }

    @DisplayName("상영관의 좌석 리스트를 Map 형태로 조회한다.")
    @Test
    void getSeatList() {
        //given
        Hall hall1 = createHall();
        Hall hall2 = createHall();
        Screening screening = createScreening(hall1);
        createSeat(hall1, "A", "1");
        createSeat(hall1, "A", "2");
        createSeat(hall1, "B", "1");
        createSeat(hall1, "B", "2");
        createSeat(hall2, "A", "1");

        //when
        Map<String, List<SeatSummaryResponse>> sectionSeatMap = seatService.getSeatList(screening.getId(), hall1.getId());

        //then
        assertThat(sectionSeatMap).hasSize(2);
        assertThat(sectionSeatMap.get("A")).hasSize(2)
                .extracting("section", "seatRow")
                .containsExactlyInAnyOrder(
                        tuple("A", "1"),
                        tuple("A", "2")
                );
        assertThat(sectionSeatMap.get("B")).hasSize(2)
                .extracting("section", "seatRow")
                .containsExactlyInAnyOrder(
                        tuple("B", "1"),
                        tuple("B", "2")
                );
    }

    private Screening createScreening(Hall hall) {
        Screening screening = Screening.builder()
                .hall(hall)
                .build();
        return screeningRepository.save(screening);
    }

    private Hall createHall() {
        Hall hall = Hall.builder()
                .build();
        return hallRepository.save(hall);
    }
    private Seat createSeat(Hall hall, String section, String seatRow) {
        Seat seat = Seat.builder()
                .hall(hall)
                .seatLocation(new SeatLocation(section, seatRow))
                .build();
        return seatRepository.save(seat);
    }
}