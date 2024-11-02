package mt.movie_theater.api.screening.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mt.movie_theater.api.apiResponse.ApiResponse;
import mt.movie_theater.api.screening.request.RegionTheaterCountRequest;
import mt.movie_theater.api.screening.request.ScreeningCreateRequest;
import mt.movie_theater.api.screening.response.ScreeningResponse;
import mt.movie_theater.api.screening.service.ScreeningService;
import mt.movie_theater.api.theater.response.RegionTheaterCountResponse;
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

    @GetMapping("/region/theaterCount")
    public ApiResponse<List<RegionTheaterCountResponse>> getRegionsWithTheaterCount(@Valid @ModelAttribute RegionTheaterCountRequest request) {
        List<RegionTheaterCountResponse> regionResponses = screeningService.getRegionsWithTheaterCount(request.getDate(), request.getMovieId());
        return ApiResponse.ok(regionResponses);
    }


}
