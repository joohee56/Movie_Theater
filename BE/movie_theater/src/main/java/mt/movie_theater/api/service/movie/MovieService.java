package mt.movie_theater.api.service.movie;

import lombok.RequiredArgsConstructor;
import mt.movie_theater.api.controller.movie.request.MovieCreateRequest;
import mt.movie_theater.domain.movie.Movie;
import mt.movie_theater.domain.movie.MovieRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

    public void createMovie(MovieCreateRequest request) {
        Movie movie = request.toEntity();
    }
}
