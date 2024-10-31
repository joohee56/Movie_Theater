package mt.movie_theater.api.controller.booking.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookingCreateRequest {
    private Long userId;
    private Long screeningId;
    private Long seatId;
    private int totalPrice;
}
