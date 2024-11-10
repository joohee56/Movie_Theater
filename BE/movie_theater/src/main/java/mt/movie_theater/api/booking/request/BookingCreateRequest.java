package mt.movie_theater.api.booking.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class BookingCreateRequest {
    private Long screeningId;
    private Long seatId;
    private int totalPrice;

    @Builder
    public BookingCreateRequest(Long screeningId, Long seatId, int totalPrice) {
        this.screeningId = screeningId;
        this.seatId = seatId;
        this.totalPrice = totalPrice;
    }
}
