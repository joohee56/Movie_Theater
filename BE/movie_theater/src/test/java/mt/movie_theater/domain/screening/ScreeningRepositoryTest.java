package mt.movie_theater.domain.screening;

import mt.movie_theater.IntegrationTestSupport;
import mt.movie_theater.domain.hall.HallRepository;
import mt.movie_theater.domain.movie.MovieRepository;
import mt.movie_theater.domain.theater.TheaterRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;

class ScreeningRepositoryTest extends IntegrationTestSupport {
    @Autowired
    private ScreeningRepository screeningRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private TheaterRepository theaterRepository;
    @Autowired
    private HallRepository hallRepository;

    @AfterEach
    void tearDown() {
        screeningRepository.deleteAllInBatch();
        hallRepository.deleteAllInBatch();
        theaterRepository.deleteAllInBatch();
        movieRepository.deleteAllInBatch();
    }

}