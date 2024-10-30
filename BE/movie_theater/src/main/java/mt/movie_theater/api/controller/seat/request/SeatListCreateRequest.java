package mt.movie_theater.api.controller.seat.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SeatListCreateRequest {
    private Long hallId;
    private int rows;
    private int columns;

    @Builder
    public SeatListCreateRequest(Long hallId, int rows, int columns) {
        this.hallId = hallId;
        this.rows = rows;
        this.columns = columns;
    }
}
