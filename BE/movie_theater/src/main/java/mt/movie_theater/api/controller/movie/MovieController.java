package mt.movie_theater.api.controller.movie;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mt.movie_theater.api.apiResponse.ApiResponse;
import mt.movie_theater.api.controller.movie.request.MovieCreateRequest;
import mt.movie_theater.api.controller.movie.response.MovieResponse;
import mt.movie_theater.api.service.movie.MovieService;
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
}
