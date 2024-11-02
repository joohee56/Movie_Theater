package mt.movie_theater.api.theater.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mt.movie_theater.api.apiResponse.ApiResponse;
import mt.movie_theater.api.theater.request.TheaterCreateRequest;
import mt.movie_theater.api.theater.response.RegionTheaterCountResponse;
import mt.movie_theater.api.theater.response.TheaterIdNameResponse;
import mt.movie_theater.api.theater.response.TheaterResponse;
import mt.movie_theater.api.theater.service.TheaterService;
import mt.movie_theater.domain.theater.Region;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/theaters")
public class TheaterController {
    private final TheaterService theaterService;

    @PostMapping("/new")
    public ApiResponse<TheaterResponse> createTheater(@Valid @RequestBody TheaterCreateRequest request) {
        TheaterResponse response = theaterService.createTheater(request);
        return ApiResponse.ok(response);
    }

    @GetMapping("/count")
    public ApiResponse<List<RegionTheaterCountResponse>> getRegionListAndTheaterCount() {
        List<RegionTheaterCountResponse> responses = theaterService.getRegionListAndTheaterCount();
        return ApiResponse.ok(responses);
    }

    @GetMapping("/{region}")
    public ApiResponse<List<TheaterIdNameResponse>> getTheaterListByRegion(@PathVariable("region") Region region) {
        List<TheaterIdNameResponse> responses = theaterService.getTheaterListByRegion(region);
        return ApiResponse.ok(responses);
    }

}
