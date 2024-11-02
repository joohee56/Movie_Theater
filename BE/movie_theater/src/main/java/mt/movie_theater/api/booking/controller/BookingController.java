package mt.movie_theater.api.booking.controller;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import mt.movie_theater.api.apiResponse.ApiResponse;
import mt.movie_theater.api.booking.request.BookingCreateRequest;
import mt.movie_theater.api.booking.response.BookingResponse;
import mt.movie_theater.api.booking.service.BookingService;
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
        LocalDateTime bookingDate = LocalDateTime.now();
        BookingResponse response = bookingService.createBooking(request, bookingDate);
        return ApiResponse.ok(response);
    }
}
