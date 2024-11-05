package mt.movie_theater.api.seat.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import mt.movie_theater.domain.seat.Seat;

@Getter
public class SeatSummaryResponse {
    private String section;
    private String seatNumber;
    @JsonProperty("isBooked")
    private boolean isBooked;

    @Builder
    private SeatSummaryResponse(String section, String seatNumber, boolean isBooked) {
        this.section = section;
        this.seatNumber = seatNumber;
        this.isBooked = isBooked;
    }

    public static SeatSummaryResponse create(Seat seat) {
        return SeatSummaryResponse.builder()
                .section(seat.getSection())
                .seatNumber(seat.getSeatNumber())
                .isBooked(seat.isBooked())
                .build();
    }
}
