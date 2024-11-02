package mt.movie_theater.domain.theater.dto;

import lombok.Builder;
import lombok.Getter;
import mt.movie_theater.domain.theater.Theater;

@Getter
public class TheaterIdNameDto {
    private Long id;
    private String name;

    @Builder
    public TheaterIdNameDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static TheaterIdNameDto create(Theater theater) {
        return TheaterIdNameDto.builder()
                .id(theater.getId())
                .name(theater.getName())
                .build();
    }
}
