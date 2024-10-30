package mt.movie_theater.api.controller.hall.response;

import lombok.Builder;
import lombok.Getter;
import mt.movie_theater.api.controller.theater.response.TheaterResponse;
import mt.movie_theater.domain.hall.Hall;

@Getter
public class HallResponse {
    private Long id;
    private TheaterResponse theater;
    private String name;
    private Integer totalSeats;

    @Builder
    public HallResponse(Long id, TheaterResponse theater, String name, Integer totalSeats) {
        this.id = id;
        this.theater = theater;
        this.name = name;
        this.totalSeats = totalSeats;
    }

    public static HallResponse create(Hall hall) {
        return HallResponse.builder()
                .id(hall.getId())
                .theater(TheaterResponse.create(hall.getTheater()))
                .name(hall.getName())
                .totalSeats(hall.getTotalSeats())
                .build();
    }
}
