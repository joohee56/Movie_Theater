package mt.movie_theater.api.controller.screening.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import mt.movie_theater.api.controller.hall.response.HallResponse;
import mt.movie_theater.api.controller.movie.response.MovieResponse;
import mt.movie_theater.domain.screening.Screening;

@Getter
public class ScreeningResponse {
    private Long id;
    private MovieResponse movie;
    private HallResponse hall;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int totalPrice;

    @Builder
    public ScreeningResponse(Long id, MovieResponse movie, HallResponse hall, LocalDateTime startTime,
                             LocalDateTime endTime, int totalPrice) {
        this.id = id;
        this.movie = movie;
        this.hall = hall;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalPrice = totalPrice;
    }

    public static ScreeningResponse create(Screening screening) {
        return ScreeningResponse.builder()
                .id(screening.getId())
                .movie(MovieResponse.create(screening.getMovie()))
                .hall(HallResponse.create(screening.getHall()))
                .startTime(screening.getStartTime())
                .endTime(screening.getEndTime())
                .totalPrice(screening.getTotalPrice())
                .build();
    }
}
