package mt.movie_theater.api.seat.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import mt.movie_theater.domain.seat.Seat;

@Getter
public class SeatResponse {
    private Long id;
    private Long hallId;
    private String section;
    private String seatRow;
    @JsonProperty("isBooked")
    private boolean isBooked;

    @Builder
    public SeatResponse(Long id, Long hallId, String section, String seatRow, boolean isBooked) {
        this.id = id;
        this.hallId = hallId;
        this.section = section;
        this.seatRow = seatRow;
        this.isBooked = isBooked;
    }

    public static SeatResponse create(Seat seat) {
        return SeatResponse.builder()
                .id(seat.getId())
                .hallId(seat.getHall().getId())
                .section(seat.getSeatLocation().getSection())
                .seatRow(seat.getSeatLocation().getSeatRow())
                .isBooked(seat.isBooked())
                .build();
    }
}
