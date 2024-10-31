package mt.movie_theater.domain.theater.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mt.movie_theater.domain.theater.Region;

@Getter
@AllArgsConstructor
public class TheaterCountDto {
    private Region region;
    private Long count;
}
