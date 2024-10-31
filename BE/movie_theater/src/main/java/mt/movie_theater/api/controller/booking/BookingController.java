package mt.movie_theater.api.controller.booking;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mt.movie_theater.api.apiResponse.ApiResponse;
import mt.movie_theater.api.controller.booking.request.BookingCreateRequest;
import mt.movie_theater.api.controller.booking.response.BookingResponse;
import mt.movie_theater.api.service.booking.BookingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping("/new")
    public ApiResponse<BookingResponse> createBooking(@Valid @RequestBody BookingCreateRequest request) {
        BookingResponse response = bookingService.createBooking(request);
        return ApiResponse.ok(response);
    }
}
