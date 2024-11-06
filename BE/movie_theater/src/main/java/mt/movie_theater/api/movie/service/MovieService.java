package mt.movie_theater.api.movie.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mt.movie_theater.api.movie.request.MovieCreateRequest;
import mt.movie_theater.api.movie.response.MovieResponse;
import mt.movie_theater.api.movie.response.MovieWatchableResponse;
import mt.movie_theater.domain.genre.Genre;
import mt.movie_theater.domain.genre.GenreRepository;
import mt.movie_theater.domain.genre.GenreType;
import mt.movie_theater.domain.movie.Movie;
import mt.movie_theater.domain.movie.MovieRepository;
import mt.movie_theater.domain.screening.ScreeningRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MovieService {
    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final ScreeningRepository screeningRepository;

    @Transactional
    public MovieResponse createMovie(MovieCreateRequest request) {
        List<GenreType> genreTypes = request.getGenreTypes();
        List<Genre> genres = genreRepository.findAllByTypeIn(genreTypes);
        Movie movie = Movie.create(request, genres);
        return MovieResponse.create(movieRepository.save(movie));
    }

    public List<MovieResponse> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        return movies.stream()
                .map(movie -> MovieResponse.create(movie))
                .collect(Collectors.toList());
    }

    /**
     * 전체 영화 리스트 조회, 영화별 상영시간 포함 유무
     */
    public List<MovieWatchableResponse> getMoviesWithIsWatchable(LocalDate date, Long theaterId) {
        LocalDateTime startDateTime = getStartDateTime(date);
        LocalDateTime endDateTime = getEndDateTime(date);
        List<Movie> watchableMovies = screeningRepository.findMoviesByDateAndOptionalTheaterId(startDateTime, endDateTime, theaterId);

        List<Movie> allMovies = movieRepository.findAll();
        return allMovies.stream()
                .map(movie -> MovieWatchableResponse.create(movie, watchableMovies.contains(movie)))
                .collect(Collectors.toList());
    }

    private LocalDateTime getStartDateTime(LocalDate date) {
        return date.atStartOfDay(); // 00:00:00
    }

    private LocalDateTime getEndDateTime(LocalDate date) {
        return date.atTime(LocalTime.MAX); // 23:59:59

    }
}
