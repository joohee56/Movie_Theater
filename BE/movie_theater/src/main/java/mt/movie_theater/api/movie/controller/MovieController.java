package mt.movie_theater.api.movie.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mt.movie_theater.api.apiResponse.ApiResponse;
import mt.movie_theater.api.movie.request.MovieCreateRequest;
import mt.movie_theater.api.movie.response.MovieResponse;
import mt.movie_theater.api.movie.service.MovieService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/movies")
public class MovieController {
    private final MovieService movieService;

    @PostMapping("/new")
    public ApiResponse<MovieResponse> createMovie(@Valid @RequestBody MovieCreateRequest request) {
        MovieResponse response = movieService.createMovie(request);
        return ApiResponse.ok(response);
    }

    @GetMapping
    public ApiResponse<List<MovieResponse>> getAllMovies() {
        return ApiResponse.ok(movieService.getAllMovies());
    }
}
