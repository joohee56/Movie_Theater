package mt.movie_theater.api.controller.hall.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mt.movie_theater.domain.hall.Hall;
import mt.movie_theater.domain.theater.Theater;

@Getter
@NoArgsConstructor
public class HallCreateRequest {
    private Long theaterId;
    private String name;

    @Builder
    public HallCreateRequest(Long theaterId, String name) {
        this.theaterId = theaterId;
        this.name = name;
    }

    public Hall toEntity(Theater theater) {
        return Hall.builder()
                .theater(theater)
                .name(name)
                .build();
    }
}
