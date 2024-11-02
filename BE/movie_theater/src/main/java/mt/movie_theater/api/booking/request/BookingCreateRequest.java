package mt.movie_theater.api.booking.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookingCreateRequest {
    private Long userId;
    private Long screeningId;
    private Long seatId;
    private int totalPrice;

    @Builder
    public BookingCreateRequest(Long userId, Long screeningId, Long seatId, int totalPrice) {
        this.userId = userId;
        this.screeningId = screeningId;
        this.seatId = seatId;
        this.totalPrice = totalPrice;
    }
}
