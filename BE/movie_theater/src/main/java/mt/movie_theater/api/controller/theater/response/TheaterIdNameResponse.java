package mt.movie_theater.api.controller.theater.response;

import lombok.Builder;
import lombok.Getter;
import mt.movie_theater.domain.theater.dto.TheaterIdNameDto;

@Getter
public class TheaterIdNameResponse {
    private Long id;
    private String name;

    @Builder
    public TheaterIdNameResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static TheaterIdNameResponse create(TheaterIdNameDto dto) {
        return TheaterIdNameResponse.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }
}
