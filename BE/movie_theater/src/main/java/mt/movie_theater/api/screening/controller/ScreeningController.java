package mt.movie_theater.api.screening.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mt.movie_theater.api.apiResponse.ApiResponse;
import mt.movie_theater.api.screening.request.RegionTheaterCountRequest;
import mt.movie_theater.api.screening.request.ScreeningCreateRequest;
import mt.movie_theater.api.screening.request.ScreeningsRequest;
import mt.movie_theater.api.screening.request.TheaterScreeningCountRequest;
import mt.movie_theater.api.screening.response.FullScreeningResponse;
import mt.movie_theater.api.screening.response.ScreeningResponse;
import mt.movie_theater.api.screening.response.TheaterScreeningCountResponse;
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

    @GetMapping("")
    public ApiResponse<List<FullScreeningResponse>> getScreenings(@Valid @ModelAttribute ScreeningsRequest request) {
        List<FullScreeningResponse> screenings = screeningService.getScreenings(request.getDate(), request.getMovieId(), request.getTheaterId());
        return ApiResponse.ok(screenings);
    }

    @GetMapping("/theater/screeningCount")
    public ApiResponse<List<TheaterScreeningCountResponse>> getTheaterListAndScreeningCountByRegion(@Valid @ModelAttribute TheaterScreeningCountRequest request) {
        List<TheaterScreeningCountResponse> responses = screeningService.getTheaterAndScreeningCountsByRegion(request.getDate(), request.getRegion(), request.getMovieId());
        return ApiResponse.ok(responses);
    }

}
