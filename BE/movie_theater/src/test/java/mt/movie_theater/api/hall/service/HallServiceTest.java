package mt.movie_theater.api.hall.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import mt.movie_theater.IntegrationTestSupport;
import mt.movie_theater.api.hall.request.HallCreateRequest;
import mt.movie_theater.api.hall.response.HallResponse;
import mt.movie_theater.domain.movie.ScreeningType;
import mt.movie_theater.domain.theater.Region;
import mt.movie_theater.domain.theater.Theater;
import mt.movie_theater.domain.theater.TheaterRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class HallServiceTest extends IntegrationTestSupport {
    @Autowired
    private TheaterRepository theaterRepository;
    @Autowired
    private HallService hallService;

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
                                    .screeningType(ScreeningType.IMAX)
                                    .hallTypeModifier(3000)
                                    .build();
        //when
        HallResponse response = hallService.createHall(request);

        //then
        assertThat(response.getId()).isNotNull();
        assertThat(response)
                .extracting("name", "totalSeats", "screeningType", "hallTypeModifier")
                .contains("1관", 0, "IMAX", 3000);
        assertThat(response.getTheater().getId()).isEqualTo(savedTheater.getId());
    }

    @DisplayName("신규 상영관을 등록할 때, 잘못된 영화관이 포함될 경우 예외가 발생한다.")
    @Test
    void createHallWithNoTheater() {
        //given
        HallCreateRequest request = HallCreateRequest.builder()
                .theaterId(Long.valueOf(1))
                .name("1관")
                .screeningType(ScreeningType.TWO_D)
                .build();

        //when, then
        assertThatThrownBy(() -> hallService.createHall(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효하지 않은 영화관입니다. 영화관 정보를 다시 확인해 주세요.");
    }

}