package mt.movie_theater.api.movie.request;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@NoArgsConstructor
@Setter
public class MovieWatchableRequest {
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private LocalDate date;
    private Long theaterId;
}
