package mt.movie_theater.api.controller.movie;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mt.movie_theater.api.controller.movie.request.MovieCreateRequest;
import mt.movie_theater.api.service.movie.MovieService;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @PostMapping("")
    public void createMovie(@Valid @ModelAttribute MovieCreateRequest request) {
        movieService.createMovie(request);
    }
}
