package mt.movie_theater.api.seat.response;

import lombok.Builder;
import lombok.Getter;
import mt.movie_theater.domain.seat.Seat;

@Getter
public class SeatResponse {
    private Long id;
    private Long hallId;
    private String section;
    private String seatNumber;
    private boolean isBooked;

    @Builder
    public SeatResponse(Long id, Long hallId, String section, String seatNumber, boolean isBooked) {
        this.id = id;
        this.hallId = hallId;
        this.section = section;
        this.seatNumber = seatNumber;
        this.isBooked = isBooked;
    }

    public static SeatResponse create(Seat seat) {
        return SeatResponse.builder()
                .id(seat.getId())
                .hallId(seat.getHall().getId())
                .section(seat.getSection())
                .seatNumber(seat.getSeatNumber())
                .isBooked(seat.isBooked())
                .build();
    }
}
