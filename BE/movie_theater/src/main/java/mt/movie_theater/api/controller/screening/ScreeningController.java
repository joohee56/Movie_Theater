package mt.movie_theater.api.controller.screening;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mt.movie_theater.api.apiResponse.ApiResponse;
import mt.movie_theater.api.controller.screening.request.ScreeningCreateRequest;
import mt.movie_theater.api.controller.screening.response.ScreeningResponse;
import mt.movie_theater.api.service.screening.ScreeningService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/screenings")
public class ScreeningController {
    private final ScreeningService screeningService;

    /**
    * 상영시간 생성
     * request: 영화 Id, 상영관 Id, 시작 시간
     */
    @PostMapping("/new")
    public ApiResponse<ScreeningResponse> createScreening(@Valid @RequestBody ScreeningCreateRequest request) {
        ScreeningResponse response = screeningService.createScreening(request);
        return ApiResponse.ok(response);
    }
}
