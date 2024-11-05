package mt.movie_theater.api.seat.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;

import java.util.List;
import java.util.Map;
import mt.movie_theater.IntegrationTestSupport;
import mt.movie_theater.api.seat.request.SeatListCreateRequest;
import mt.movie_theater.api.seat.response.SeatResponse;
import mt.movie_theater.api.seat.response.SeatSummaryResponse;
import mt.movie_theater.domain.hall.Hall;
import mt.movie_theater.domain.hall.HallRepository;
import mt.movie_theater.domain.seat.Seat;
import mt.movie_theater.domain.seat.SeatRepository;
import mt.movie_theater.domain.theater.TheaterRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

class SeatServiceTest extends IntegrationTestSupport {
    @Autowired
    private SeatService seatService;
    @Autowired
    private TheaterRepository theaterRepository;
    @Autowired
    private HallRepository hallRepository;
    @Autowired
    private SeatRepository seatRepository;

    @AfterEach
    void tearDown() {
        seatRepository.deleteAllInBatch();
        hallRepository.deleteAllInBatch();
        theaterRepository.deleteAllInBatch();
    }

    @Transactional
    @DisplayName("상영관에 좌석 리스트를 등록한다.")
    @Test
    void createSeatList() {
        //given
        Hall hall = createHall();
        SeatListCreateRequest request = SeatListCreateRequest.builder()
                .hallId(hall.getId())
                .rows(2)
                .columns(3)
                .build();

        //when
        List<SeatResponse> seats = seatService.createSeatList(request);

        //then
        assertThat(seats).hasSize(6)
                .extracting("section", "seatNumber", "isBooked")
                .containsExactlyInAnyOrder(
                        tuple("A", "1", false),
                        tuple("A", "2", false),
                        tuple("A", "3", false),
                        tuple("B", "1", false),
                        tuple("B", "2", false),
                        tuple("B", "3", false)
                );
    }

    @DisplayName("상영관에 좌석 리스트를 등록할 때, 잘못된 상영관이 요청된 경우 예외가 발생한다.")
    @Test
    void createSeatListWithNoHall() {
        //given
        SeatListCreateRequest request = SeatListCreateRequest.builder()
                .hallId(Long.valueOf(1))
                .rows(2)
                .columns(3)
                .build();

        //when, then
        assertThatThrownBy(() -> seatService.createSeatList(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효하지 않은 상영관입니다. 상영관 정보를 다시 확인해 주세요.");
    }

    @DisplayName("상영관의 좌석 리스트를 Map 형태로 조죄한다.")
    @Test
    void getSeatList() {
        //given
        Hall hall1 = createHall();
        Hall hall2 = createHall();
        createSeat(hall1, "A", "1");
        createSeat(hall1, "A", "2");
        createSeat(hall1, "B", "1");
        createSeat(hall1, "B", "2");
        createSeat(hall2, "A", "1");

        //when
        Map<String, List<SeatSummaryResponse>> sectionSeatMap = seatService.getSeatList(hall1.getId());

        //then
        assertThat(sectionSeatMap).hasSize(2);
        assertThat(sectionSeatMap.get("A")).hasSize(2)
                .extracting("section", "seatNumber")
                .containsExactlyInAnyOrder(
                        tuple("A", "1"),
                        tuple("A", "2")
                );
        assertThat(sectionSeatMap.get("B")).hasSize(2)
                .extracting("section", "seatNumber")
                .containsExactlyInAnyOrder(
                        tuple("B", "1"),
                        tuple("B", "2")
                );
    }

    private Hall createHall() {
        Hall hall = Hall.builder()
                .build();
        return hallRepository.save(hall);
    }
    private Seat createSeat(Hall hall, String section, String seatNumber) {
        Seat seat = Seat.builder()
                .hall(hall)
                .section(section)
                .seatNumber(seatNumber)
                .build();
        return seatRepository.save(seat);
    }
}