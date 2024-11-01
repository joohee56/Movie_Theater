package mt.movie_theater.api.controller.theater;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mt.movie_theater.api.apiResponse.ApiResponse;
import mt.movie_theater.api.controller.theater.request.TheaterCreateRequest;
import mt.movie_theater.api.controller.theater.response.TheaterCountResponse;
import mt.movie_theater.api.controller.theater.response.TheaterResponse;
import mt.movie_theater.api.service.theater.TheaterService;
import mt.movie_theater.domain.theater.Region;
import mt.movie_theater.domain.theater.dto.TheaterIdNameDto;
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
    public ApiResponse<List<TheaterCountResponse>> getRegionListAndTheaterCount() {
        List<TheaterCountResponse> responses = theaterService.getRegionListAndTheaterCount();
        return ApiResponse.ok(responses);
    }

    @GetMapping("/{region}")
    public ApiResponse<List<TheaterIdNameDto>> getTheaterListByRegion(@PathVariable("region") Region region) {
        List<TheaterIdNameDto> theaterDtos = theaterService.getTheaterListByRegion(region);
        return ApiResponse.ok(theaterDtos);
    }
}
