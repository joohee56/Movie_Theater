package mt.movie_theater.domain.movie;

import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MovieTest {

    @DisplayName("영화 생성 시, 개봉일이 현재보다 이후일 경우 예외가 발생한다.")
    @Test
    void createMovieWithReleaseDateAfterThanCurrent() {
        //given
        LocalDate now = LocalDate.now();

        //when, then
        Assertions.assertThatThrownBy(() -> Movie.builder()
                .releaseDate(now.plusDays(1))
                .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("영화 개봉일은 현재보다 이후일 수 없습니다.");
    }

}