package mt.movie_theater.api.theater.service;

import static mt.movie_theater.domain.theater.Region.GYEONGGI;
import static mt.movie_theater.domain.theater.Region.SEOUL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import jakarta.transaction.Transactional;
import java.util.List;
import mt.movie_theater.IntegrationTestSupport;
import mt.movie_theater.api.theater.response.RegionTheaterCountResponse;
import mt.movie_theater.domain.theater.Region;
import mt.movie_theater.domain.theater.Theater;
import mt.movie_theater.domain.theater.TheaterRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Transactional
class TheaterServiceTest extends IntegrationTestSupport {
    @Autowired
    private TheaterService theaterService;
    @Autowired
    private TheaterRepository theaterRepository;

    @DisplayName("지역리스트와 지역에 속한 영화관 갯수를 조회한다.")
    @Test
    void getRegionsWithTheaterCount() {
        //given
        createTheater(SEOUL, "강남");
        createTheater(SEOUL, "강동");
        createTheater(GYEONGGI, "고양스타필드");

        //when
        List<RegionTheaterCountResponse> response = theaterService.getRegionsWithTheaterCount();

        //then
        assertThat(response).hasSize(2)
                .extracting("region", "regionDisplay", "count")
                .containsExactlyInAnyOrder(
                        tuple("SEOUL", "서울", Long.valueOf(2)),
                        tuple("GYEONGGI", "경기", Long.valueOf(1))
                );
    }

    private Theater createTheater(Region region, String name) {
        Theater theater = Theater.builder()
                .region(region)
                .name(name)
                .build();
        return theaterRepository.save(theater);
    }
}