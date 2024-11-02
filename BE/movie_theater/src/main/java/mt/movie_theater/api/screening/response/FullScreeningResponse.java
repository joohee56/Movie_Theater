package mt.movie_theater.api.screening.response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Builder;
import mt.movie_theater.domain.screening.Screening;

public class FullScreeningResponse {
    private Long screeningId;
    private String startTime;
    private String endTime;
    private String movieTitle;
    private String screeningTypeDisplay;
    private String theaterName;
    private String hallName;

    @Builder
    public FullScreeningResponse(Long screeningId, String startTime, String endTime, String movieTitle,
                                 String screeningTypeDisplay, String theaterName, String hallName) {
        this.screeningId = screeningId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.movieTitle = movieTitle;
        this.screeningTypeDisplay = screeningTypeDisplay;
        this.theaterName = theaterName;
        this.hallName = hallName;
    }

    public static FullScreeningResponse create(Screening screening) {
        return FullScreeningResponse.builder()
                .screeningId(screening.getId())
                .startTime(formatToHourAndMinute(screening.getStartTime()))
                .endTime(formatToHourAndMinute(screening.getEndTime()))
                .movieTitle(screening.getMovie().getTitle())
                .screeningTypeDisplay(screening.getHall().getScreeningType().getText())
                .theaterName(screening.getHall().getTheater().getName())
                .hallName(screening.getHall().getName())
                .build();
    }

    private static String formatToHourAndMinute(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return dateTime.format(formatter);
    }
}
