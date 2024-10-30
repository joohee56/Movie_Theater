package mt.movie_theater.domain.hall;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HallTest {

    @DisplayName("홀 생성 시, totalSeats 초기값은 0이다.")
    @Test
    void initHallWithZeroTotalSeats() {
        //when
        Hall hall = Hall.builder()
                .name("1관")
                .build();

        //then
        assertThat(hall.getTotalSeats()).isZero();
    }
}