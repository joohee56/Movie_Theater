package mt.movie_theater.domain.theater;

import static mt.movie_theater.domain.theater.Region.GYEONGGI;
import static mt.movie_theater.domain.theater.Region.SEOUL;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import mt.movie_theater.IntegrationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class TheaterRepositoryTest extends IntegrationTestSupport {
    @Autowired
    private TheaterRepository theaterRepository;

    @DisplayName("지역에 속하는 영화관 리스트를 조회한다.")
    @Test
    void findALlByRegion() {
        //given
        createTheater(SEOUL, "강남");
        createTheater(SEOUL, "강동");
        createTheater(SEOUL, "군자");
        createTheater(GYEONGGI, "고양스타필드");
        createTheater(GYEONGGI, "광명ak플라자");

        Region region = SEOUL;

        //when
        List<Theater> theaters = theaterRepository.findALlByRegion(region);

        //then
        assertThat(theaters).hasSize(3)
                .extracting("name")
                .containsExactlyInAnyOrder("강남", "강동", "군자");
    }

    private Theater createTheater(Region region, String name) {
        Theater theater = Theater.builder()
                .region(region)
                .name(name)
                .build();
        return theaterRepository.save(theater);
    }
}