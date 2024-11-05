package mt.movie_theater.api.seat.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import mt.movie_theater.domain.seat.Seat;

@Getter
public class SeatSummaryResponse {
    private Long seatId;
    private String section;
    private String seatNumber;
    @JsonProperty("isBooked")
    private boolean isBooked;

    @Builder
    private SeatSummaryResponse(Long seatId, String section, String seatNumber, boolean isBooked) {
        this.seatId = seatId;
        this.section = section;
        this.seatNumber = seatNumber;
        this.isBooked = isBooked;
    }

    public static SeatSummaryResponse create(Seat seat) {
        return SeatSummaryResponse.builder()
                .seatId(seat.getId())
                .section(seat.getSection())
                .seatNumber(seat.getSeatNumber())
                .isBooked(seat.isBooked())
                .build();
    }
}
