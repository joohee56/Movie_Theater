package mt.movie_theater.api.service.hall;

import static org.assertj.core.api.Assertions.*;

import mt.movie_theater.IntegrationTestSupport;
import mt.movie_theater.api.controller.hall.request.HallCreateRequest;
import mt.movie_theater.api.controller.hall.response.HallResponse;
import mt.movie_theater.domain.hall.HallRepository;
import mt.movie_theater.domain.theater.Region;
import mt.movie_theater.domain.theater.Theater;
import mt.movie_theater.domain.theater.TheaterRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class HallServiceTest extends IntegrationTestSupport {
    @Autowired
    private TheaterRepository theaterRepository;
    @Autowired
    private HallService hallService;
    @Autowired
    private HallRepository hallRepository;

    @AfterEach
    void tearDown() {
        hallRepository.deleteAllInBatch();
        theaterRepository.deleteAllInBatch();
    }

    @DisplayName("신규 상영관을 등록한다.")
    @Test
    void createHall() {
        //given
        Theater theater = Theater.builder()
                                .name("창동")
                                .region(Region.SEOUL)
                                .build();
        Theater savedTheater = theaterRepository.save(theater);

        HallCreateRequest request = HallCreateRequest.builder()
                                    .theaterId(savedTheater.getId())
                                    .name("1관")
                                    .build();
        //when
        HallResponse response = hallService.createHall(request);

        //then
        assertThat(response.getId()).isNotNull();
        assertThat(response)
                .extracting("name", "totalSeats")
                .contains("1관", 0);
        assertThat(response.getTheater().getId()).isEqualTo(savedTheater.getId());
    }

    @DisplayName("신규 상영관을 등록할 때, 잘못된 극장 번호가 포함될 경우 예외가 발생한다.")
    @Test
    void createHallWithNoTheater() {
        //given
        HallCreateRequest request = HallCreateRequest.builder()
                .theaterId(Long.valueOf(1))
                .name("1관")
                .build();
        //when, then
        assertThatThrownBy(() -> hallService.createHall(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 영화관입니다.");
    }

}