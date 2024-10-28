package mt.movie_theater.api.service.movie;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mt.movie_theater.api.controller.movie.request.MovieCreateRequest;
import mt.movie_theater.api.controller.movie.response.MovieResponse;
import mt.movie_theater.domain.genre.Genre;
import mt.movie_theater.domain.genre.GenreRepository;
import mt.movie_theater.domain.genre.GenreType;
import mt.movie_theater.domain.movie.Movie;
import mt.movie_theater.domain.movie.MovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MovieService {
    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;

    @Transactional
    public MovieResponse createMovie(MovieCreateRequest request) {
        List<GenreType> genreTypes = request.getGenreTypes();
        List<Genre> genres = genreRepository.findAllByTypeIn(genreTypes);

        Movie movie = request.toEntity(genres);
        return MovieResponse.create(movieRepository.save(movie));
    }

    public List<MovieResponse> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        return movies.stream()
                .map(movie -> MovieResponse.create(movie))
                .collect(Collectors.toList());
    }
}
