package mt.movie_theater.domain.genre;

import static mt.movie_theater.domain.genre.GenreType.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class GenreRepositoryTest {
    @Autowired
    private GenreRepository genreRepository;

    @AfterEach
    void tearDown() {
        genreRepository.deleteAllInBatch();
    }

    @DisplayName("장르타입 리스트에 해당하는 장르 리스트를 반환한다.")
    @Test
    void findAllByTypeIn() {
        //given
        List<GenreType> types = List.of(DRAMA, COMEDY);
        Genre genre1 = Genre.create(DRAMA);
        Genre genre2 = Genre.create(COMEDY);
        Genre genre3 = Genre.create(ACTION);
        genreRepository.saveAll(List.of(genre1, genre2, genre3));

        //when
        List<Genre> genres = genreRepository.findAllByTypeIn(types);

        //then
        assertThat(genres).hasSize(2);
        assertThat(genres)
                .extracting("type")
                .containsExactlyInAnyOrder(DRAMA, COMEDY);
    }

}