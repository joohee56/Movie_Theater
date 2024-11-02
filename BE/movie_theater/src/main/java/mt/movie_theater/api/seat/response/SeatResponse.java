package mt.movie_theater.api.seat.response;

import lombok.Builder;
import lombok.Getter;
import mt.movie_theater.api.hall.response.HallResponse;
import mt.movie_theater.domain.seat.Seat;

@Getter
public class SeatResponse {
    private Long id;
    private HallResponse hall;
    private String seatNumber;
    private boolean isAvailable;

    @Builder
    public SeatResponse(Long id, HallResponse hall, String seatNumber, boolean isAvailable) {
        this.id = id;
        this.hall = hall;
        this.seatNumber = seatNumber;
        this.isAvailable = isAvailable;
    }

    public static SeatResponse create(Seat seat) {
        return SeatResponse.builder()
                .id(seat.getId())
                .hall(HallResponse.create(seat.getHall()))
                .seatNumber(seat.getSeatNumber())
                .isAvailable(seat.isAvailable())
                .build();
    }
}
