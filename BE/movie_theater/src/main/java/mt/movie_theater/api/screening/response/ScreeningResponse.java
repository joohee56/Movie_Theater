package mt.movie_theater.api.screening.response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Builder;
import lombok.Getter;
import mt.movie_theater.api.hall.response.HallResponse;
import mt.movie_theater.api.movie.response.MovieResponse;
import mt.movie_theater.domain.screening.Screening;

@Getter
public class ScreeningResponse {
    private Long id;
    private MovieResponse movie;
    private HallResponse hall;
    private String startTime;
    private String endTime;
    private int totalPrice;

    @Builder
    public ScreeningResponse(Long id, MovieResponse movie, HallResponse hall, String startTime,
                             String endTime, int totalPrice) {
        this.id = id;
        this.movie = movie;
        this.hall = hall;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalPrice = totalPrice;
    }

    public static ScreeningResponse create(Screening screening) {
        return ScreeningResponse.builder()
                .id(screening.getId())
                .movie(MovieResponse.create(screening.getMovie()))
                .hall(HallResponse.create(screening.getHall()))
                .startTime(formatToHourAndMinute(screening.getStartTime()))
                .endTime(formatToHourAndMinute(screening.getEndTime()))
                .totalPrice(screening.getTotalPrice())
                .build();
    }

    private static String formatToHourAndMinute(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return dateTime.format(formatter);
    }
}
