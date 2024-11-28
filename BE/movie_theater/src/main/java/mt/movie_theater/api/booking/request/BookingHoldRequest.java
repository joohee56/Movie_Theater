package mt.movie_theater.api.booking.request;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookingHoldRequest {
    private Long screeningId;
    private List<Long> seatIds;

    @Builder
    public BookingHoldRequest(Long screeningId, List<Long> seatIds) {
        this.screeningId = screeningId;
        this.seatIds = seatIds;
    }
}
