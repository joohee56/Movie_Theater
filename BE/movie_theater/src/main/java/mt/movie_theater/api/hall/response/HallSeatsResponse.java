package mt.movie_theater.api.hall.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class HallSeatsResponse {
    private Long hallId;

    @Builder
    private HallSeatsResponse(Long hallId) {
        this.hallId = hallId;
    }

    public static HallSeatsResponse create(Long hallId) {
        return HallSeatsResponse.builder()
                .hallId(hallId).build();
    }

}
