package mt.movie_theater.api.booking.request;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookingHoldRequest {
    private Long screeningId;
    private List<Long> seatIds;
}
