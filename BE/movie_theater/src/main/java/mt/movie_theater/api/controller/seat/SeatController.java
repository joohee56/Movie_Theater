package mt.movie_theater.api.controller.seat;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mt.movie_theater.api.apiResponse.ApiResponse;
import mt.movie_theater.api.controller.seat.request.SeatListCreateRequest;
import mt.movie_theater.api.controller.seat.response.SeatResponse;
import mt.movie_theater.api.service.seat.SeatService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/seats")
public class SeatController {
    private final SeatService seatService;

    @PostMapping("/new")
    public ApiResponse<List<SeatResponse>> createSeatList(@Valid @RequestBody SeatListCreateRequest request) {
        List<SeatResponse> seats = seatService.createSeatList(request);
        return ApiResponse.ok(seats);
    }
}
