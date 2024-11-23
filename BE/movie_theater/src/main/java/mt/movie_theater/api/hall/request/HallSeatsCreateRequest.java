package mt.movie_theater.api.hall.request;

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

    public Hall toEntity(Theater theater) {
        return Hall.builder()
                .theater(theater)
                .name(name)
                .screeningType(screeningType)
                .hallTypeModifier(hallTypeModifier)
                .build();
    }
}
