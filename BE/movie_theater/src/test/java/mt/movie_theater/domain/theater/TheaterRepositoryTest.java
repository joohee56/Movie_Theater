package mt.movie_theater.domain.theater;

import static mt.movie_theater.domain.theater.Region.GYEONGGI;
import static mt.movie_theater.domain.theater.Region.SEOUL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.List;
import mt.movie_theater.IntegrationTestSupport;
import mt.movie_theater.domain.theater.dto.TheaterCountDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class TheaterRepositoryTest extends IntegrationTestSupport {
    @Autowired
    private TheaterRepository theaterRepository;

    @DisplayName("")
    @Test
    void countTheatersByRegion() {
        //given
        createTheater("강남", SEOUL);
        createTheater("강동", SEOUL);
        createTheater("군자", SEOUL);
        createTheater("고양스타필드", GYEONGGI);
        createTheater("광명AK플라자", GYEONGGI);

        //when
        List<TheaterCountDto> results = theaterRepository.countTheatersByRegion();

        //then
        assertThat(results).hasSize(2);
        assertThat(results)
                .extracting("region", "count")
                .containsExactlyInAnyOrder(
                        tuple(SEOUL, Long.valueOf(3)),
                        tuple(GYEONGGI, Long.valueOf(2))
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