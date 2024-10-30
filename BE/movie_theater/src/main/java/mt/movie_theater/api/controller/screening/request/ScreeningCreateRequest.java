package mt.movie_theater.api.controller.screening.request;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ScreeningCreateRequest {
    private Long movieId;
    private Long hallId;
    private LocalDateTime startTime;

    @Builder
    public ScreeningCreateRequest(Long movieId, Long hallId, LocalDateTime startTime) {
        this.movieId = movieId;
        this.hallId = hallId;
        this.startTime = startTime;
    }
}
