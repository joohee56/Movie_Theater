package mt.movie_theater.api.controller.screening.request;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@NoArgsConstructor
@Setter
public class ScreeningListRequest {
    private Long movieId;
    private Long hallId;
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private LocalDate date;
}
