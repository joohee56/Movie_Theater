package mt.movie_theater.api.hall.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mt.movie_theater.api.apiResponse.ApiResponse;
import mt.movie_theater.api.hall.request.HallCreateRequest;
import mt.movie_theater.api.hall.response.HallResponse;
import mt.movie_theater.api.hall.service.HallService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/halls")
@RequiredArgsConstructor
public class HallController {
    private final HallService hallService;

    @PostMapping("/new")
    public ApiResponse<HallResponse> createHall(@Valid @RequestBody HallCreateRequest request) {
        HallResponse response = hallService.createHall(request);
        return ApiResponse.ok(response);
    }
}