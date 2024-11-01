package mt.movie_theater.domain.hall;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import mt.movie_theater.IntegrationTestSupport;
import mt.movie_theater.domain.hall.dto.HallIdNameDto;
import mt.movie_theater.domain.theater.Region;
import mt.movie_theater.domain.theater.Theater;
import mt.movie_theater.domain.theater.TheaterRepository;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class HallRepositoryTest extends IntegrationTestSupport {
    @Autowired
    private HallRepository hallRepository;
    @Autowired
    private TheaterRepository theaterRepository;

    @DisplayName("지역에 속하는 상영관 리스트를 조회한다.")
    @Test
    void findAllByRegion() {
        //given
        Theater theater1 = createTheater(Region.SEOUL);
        Theater theater2 = createTheater(Region.GANGWON);

        createHall(theater1, "1관");
        createHall(theater1, "2관");
        createHall(theater2, "3관");
        createHall(theater2, "4관");

        //when
        List<HallIdNameDto> halls = hallRepository.findAllByRegion(Region.SEOUL);

        //then
        assertThat(halls).hasSize(2)
                .extracting("id", "name")
                .containsExactlyInAnyOrder(
                        Tuple.tuple(Long.valueOf(1), "1관"),
                        Tuple.tuple(Long.valueOf(2), "2관")
                );
    }

    public Theater createTheater(Region region) {
        Theater theater = Theater.builder()
                .name("강남")
                .region(region)
                .build();
        return theaterRepository.save(theater);
    }

    public Hall createHall(Theater theater, String name) {
        Hall hall = Hall.builder()
                .theater(theater)
                .name((name))
                .build();
        return hallRepository.save(hall);
    }

}