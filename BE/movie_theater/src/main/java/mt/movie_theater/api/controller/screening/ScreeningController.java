package mt.movie_theater.api.controller.screening;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mt.movie_theater.api.apiResponse.ApiResponse;
import mt.movie_theater.api.controller.screening.request.ScreeningCreateRequest;
import mt.movie_theater.api.controller.screening.request.ScreeningListRequest;
import mt.movie_theater.api.controller.screening.response.ScreeningResponse;
import mt.movie_theater.api.service.screening.ScreeningService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/screenings")
public class ScreeningController {
    private final ScreeningService screeningService;

    @PostMapping("/new")
    public ApiResponse<ScreeningResponse> createScreening(@Valid @RequestBody ScreeningCreateRequest request) {
        ScreeningResponse response = screeningService.createScreening(request);
        return ApiResponse.ok(response);
    }

    @GetMapping("")
    public ApiResponse<List<ScreeningResponse>> getScreeningList(@ModelAttribute ScreeningListRequest request) {
        List<ScreeningResponse> screenings = screeningService.getScreeningList(request.getMovieId(), request.getTheaterId(), request.getDate());
        return ApiResponse.ok(screenings);
    }
}
