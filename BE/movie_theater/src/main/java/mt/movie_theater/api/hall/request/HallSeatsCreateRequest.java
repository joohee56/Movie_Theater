package mt.movie_theater.api.hall.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mt.movie_theater.domain.hall.Hall;
import mt.movie_theater.domain.movie.ScreeningType;
import mt.movie_theater.domain.theater.Theater;

@Getter
@NoArgsConstructor
public class HallSeatsCreateRequest {
    private Long theaterId;
    private String name;
    private ScreeningType screeningType;
    private int hallTypeModifier;
    private int rows;
    private int columns;

    @Builder
    public HallSeatsCreateRequest(Long theaterId, String name, ScreeningType screeningType, int hallTypeModifier,
                                  int rows,
                                  int columns) {
        this.theaterId = theaterId;
        this.name = name;
        this.screeningType = screeningType;
        this.hallTypeModifier = hallTypeModifier;
        this.rows = rows;
        this.columns = columns;
    }

    public Hall toEntity(Theater theater) {
        return Hall.builder()
                .theater(theater)
                .name(name)
                .screeningType(screeningType)
                .hallTypeModifier(hallTypeModifier)
                .build();
    }
}
