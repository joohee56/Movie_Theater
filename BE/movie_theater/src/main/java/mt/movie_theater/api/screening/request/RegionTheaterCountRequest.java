package mt.movie_theater.api.screening.request;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@NoArgsConstructor
public class RegionTheaterCountRequest {
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private LocalDate date;
    private Long movieId;
}
