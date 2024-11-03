package mt.movie_theater.api.screening.request;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mt.movie_theater.domain.theater.Region;
import org.springframework.format.annotation.DateTimeFormat;

@NoArgsConstructor
@Getter
@Setter
public class TheaterScreeningCountRequest {
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private LocalDate date;
    private Long movieId;
    private Region region;
}
