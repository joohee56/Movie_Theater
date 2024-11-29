package mt.movie_theater.api.screening.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@NoArgsConstructor
public class ScreeningsRequest {

    @NotNull(message = "날짜는 필수 입력값입니다.")
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private LocalDate date;

    private Long movieId;

    @NotNull(message = "영화관 ID는 필수 입력값입니다.")
    private Long theaterId;
}
