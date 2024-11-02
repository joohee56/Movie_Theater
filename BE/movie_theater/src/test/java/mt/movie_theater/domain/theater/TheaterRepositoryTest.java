package mt.movie_theater.domain.theater;

import static mt.movie_theater.domain.theater.Region.GYEONGGI;
import static mt.movie_theater.domain.theater.Region.SEOUL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.List;
import mt.movie_theater.IntegrationTestSupport;
import mt.movie_theater.domain.theater.dto.RegionTheaterCountDto;
import mt.movie_theater.domain.theater.dto.TheaterIdNameDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class TheaterRepositoryTest extends IntegrationTestSupport {
    @Autowired
    private TheaterRepository theaterRepository;

    @DisplayName("지역에 속하는 영화관 갯수를 조회한다.")
    @Test
    void countTheatersByRegion() {
        //given
        createTheater("강남", SEOUL);
        createTheater("강동", SEOUL);
        createTheater("군자", SEOUL);
        createTheater("고양스타필드", GYEONGGI);
        createTheater("광명AK플라자", GYEONGGI);

        //when
        List<RegionTheaterCountDto> results = theaterRepository.countTheatersByRegion();

        //then
        assertThat(results).hasSize(2);
        assertThat(results)
                .extracting("region", "count")
                .containsExactlyInAnyOrder(
                        tuple(SEOUL, Long.valueOf(3)),
                        tuple(GYEONGGI, Long.valueOf(2))
                );
    }

    @DisplayName("지역에 해당하는 영화관 리스트를 조회한다.")
    @Test
    void findAllByRegion() {
        //given
        createTheater("강남", SEOUL);
        createTheater("강동", SEOUL);
        createTheater("군자", SEOUL);
        createTheater("고양스타필드", GYEONGGI);
        createTheater("광명AK플라자", GYEONGGI);

        //when
        List<TheaterIdNameDto> theaterDtos = theaterRepository.findTheaterIdNameDtoByRegion(SEOUL);

        //then
        assertThat(theaterDtos).hasSize(3)
                .extracting("name")
                .containsExactlyInAnyOrder("강남", "강동", "군자");
    }

    private void createTheater(String name, Region region) {
        Theater theater = Theater.builder()
                .name(name)
                .region(region)
                .build();
        theaterRepository.save(theater);
    }
}