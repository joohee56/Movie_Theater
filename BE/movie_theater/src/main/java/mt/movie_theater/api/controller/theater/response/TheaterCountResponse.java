package mt.movie_theater.api.controller.theater.response;

import lombok.Builder;
import lombok.Getter;
import mt.movie_theater.domain.theater.dto.TheaterCountDto;

@Getter
public class TheaterCountResponse {
    private String region;
    private String regionDisplay;
    private Long count;

    @Builder
    public TheaterCountResponse(String region, String regionDisplay, Long count) {
        this.region = region;
        this.regionDisplay = regionDisplay;
        this.count = count;
    }

    public static TheaterCountResponse create(TheaterCountDto dto) {
        return TheaterCountResponse.builder()
                .region(dto.getRegion().name())
                .regionDisplay(dto.getRegion().getText())
                .count(dto.getCount())
                .build();
    }
}
