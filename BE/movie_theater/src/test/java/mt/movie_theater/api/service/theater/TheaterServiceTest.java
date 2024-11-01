package mt.movie_theater.api.service.theater;

import static mt.movie_theater.domain.theater.Region.GYEONGGI;
import static mt.movie_theater.domain.theater.Region.SEOUL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.List;
import mt.movie_theater.IntegrationTestSupport;
import mt.movie_theater.api.controller.theater.response.TheaterCountResponse;
import mt.movie_theater.domain.theater.Region;
import mt.movie_theater.domain.theater.Theater;
import mt.movie_theater.domain.theater.TheaterRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class TheaterServiceTest extends IntegrationTestSupport {
    @Autowired
    private TheaterService theaterService;
    @Autowired
    private TheaterRepository theaterRepository;

    @AfterEach
    void tearDown() {
        theaterRepository.deleteAllInBatch();
    }

    @DisplayName("지역 리스트와 지역에 속한 영화관 갯수를 조회한다.")
    @Test
    void getRegionListAndTheaterCount() {
        //given
        createTheater("강남", SEOUL);
        createTheater("강동", SEOUL);
        createTheater("군자", SEOUL);
        createTheater("고양스타필드", GYEONGGI);
        createTheater("광명AK플라자", GYEONGGI);

        //when
        List<TheaterCountResponse> responses = theaterService.getRegionListAndTheaterCount();

        //then
        assertThat(responses).hasSize(2);
        assertThat(responses)
                .extracting("region", "count")
                .containsExactlyInAnyOrder(
                        tuple("서울", Long.valueOf(3)),
                        tuple("경기", Long.valueOf(2))
                );
    }

    private void createTheater(String name, Region region) {
        Theater theater = Theater.builder()
                .name(name)
                .region(region)
                .build();
        theaterRepository.save(theater);
    }
}