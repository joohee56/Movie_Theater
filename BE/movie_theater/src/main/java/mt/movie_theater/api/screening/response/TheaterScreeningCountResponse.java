package mt.movie_theater.api.screening.response;

import lombok.Builder;
import lombok.Getter;
import mt.movie_theater.domain.screening.dto.TheaterScreeningCountDto;

@Getter
public class TheaterScreeningCountResponse {
    private Long theaterId;
    private String theaterName;
    private Long screeningCount;

    @Builder
    public TheaterScreeningCountResponse(Long theaterId, String theaterName, Long screeningCount) {
        this.theaterId = theaterId;
        this.theaterName = theaterName;
        this.screeningCount = screeningCount;
    }

    public static TheaterScreeningCountResponse create(TheaterScreeningCountDto dto) {
        return TheaterScreeningCountResponse.builder()
                .theaterId(dto.getTheater().getId())
                .theaterName(dto.getTheater().getName())
                .screeningCount(dto.getCount())
                .build();
    }
}
