package mt.movie_theater.api.service.seat;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;

import java.util.List;
import mt.movie_theater.IntegrationTestSupport;
import mt.movie_theater.api.seat.request.SeatListCreateRequest;
import mt.movie_theater.api.seat.response.SeatResponse;
import mt.movie_theater.api.seat.service.SeatService;
import mt.movie_theater.domain.hall.Hall;
import mt.movie_theater.domain.hall.HallRepository;
import mt.movie_theater.domain.movie.ScreeningType;
import mt.movie_theater.domain.seat.SeatRepository;
import mt.movie_theater.domain.theater.Region;
import mt.movie_theater.domain.theater.Theater;
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
        Theater theater = Theater.builder()
                .name("창동")
                .region(Region.SEOUL)
                .build();
        Theater savedTheater = theaterRepository.save(theater);

        Hall hall = Hall.builder()
                .theater(savedTheater)
                .screeningType(ScreeningType.TWO_D)
                .name("1관")
                .build();
        Hall savedHall = hallRepository.save(hall);

        SeatListCreateRequest request = SeatListCreateRequest.builder()
                .hallId(savedHall.getId())
                .rows(2)
                .columns(3)
                .build();

        //when
        List<SeatResponse> seats = seatService.createSeatList(request);

        //then
        assertThat(seats.size()).isEqualTo(6);
        assertThat(savedHall.getSeats().size()).isEqualTo(6);
        assertThat(seats)
                .extracting("seatNumber", "isAvailable")
                .containsExactlyInAnyOrder(
                        tuple("A1", true),
                        tuple("A2", true),
                        tuple("A3", true),
                        tuple("B1", true),
                        tuple("B2", true),
                        tuple("B3", true)
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
}