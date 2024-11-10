package mt.movie_theater.api.booking.controller;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import mt.movie_theater.api.apiResponse.ApiResponse;
import mt.movie_theater.api.booking.request.BookingCreateRequest;
import mt.movie_theater.api.booking.response.BookingResponse;
import mt.movie_theater.api.booking.response.BookingWithDateResponse;
import mt.movie_theater.api.booking.service.BookingService;
import mt.movie_theater.api.user.annotation.Login;
import mt.movie_theater.api.user.annotation.LoginCheck;
import mt.movie_theater.domain.booking.BookingStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @LoginCheck
    @PostMapping("/new")
    public ApiResponse<BookingResponse> createBooking(@Valid @RequestBody BookingCreateRequest request, @Login Long userId) {
        LocalDateTime bookingDate = LocalDateTime.now();
        BookingResponse response = bookingService.createBooking(request, userId, bookingDate);
        return ApiResponse.ok(response);
    }

    @GetMapping("/booking/{bookingId}")
    public ApiResponse<BookingResponse> getBooking(@PathVariable("bookingId") Long bookingId) {
        return ApiResponse.ok(bookingService.getBooking(bookingId));
    }

    @GetMapping("/user")
    public ApiResponse<Map<BookingStatus, List<BookingWithDateResponse>>> getBookingHistory(Long userId) {
        return ApiResponse.ok(bookingService.getBookingHistory(userId));
    }

    @GetMapping("/cancel/{bookingId}/user/{userId}")
    public ApiResponse<Map<BookingStatus, List<BookingWithDateResponse>>> cancelBookingAndGetBookingHistory(@PathVariable("userId") Long userId, @PathVariable("bookingId") Long bookingId) {
        return ApiResponse.ok(bookingService.cancelBookingAndGetBookingHistory(userId, bookingId));
    }
}
