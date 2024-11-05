package mt.movie_theater.api.booking.response;

import lombok.Builder;
import lombok.Getter;
import mt.movie_theater.domain.booking.Booking;
import mt.movie_theater.util.DateUtil;

@Getter
public class BookingResponse {
    private Long id;
    private String bookingNumber;
    private String posterUrl;
    private String movieTitle;
    private String screeningTypeDisplay;
    private String theaterName;
    private String hallName;
    private String startDate;
    private String startTime;
    private String seatSection;
    private String seatRow;
    private String userEmail;
    private int totalPrice;

    @Builder
    public BookingResponse(Long id, String bookingNumber, String posterUrl, String movieTitle,
                           String screeningTypeDisplay,
                           String theaterName, String hallName, String startDate, String startTime, String seatSection, String seatRow,
                           String userEmail, int totalPrice) {
        this.id = id;
        this.bookingNumber = bookingNumber;
        this.posterUrl = posterUrl;
        this.movieTitle = movieTitle;
        this.screeningTypeDisplay = screeningTypeDisplay;
        this.theaterName = theaterName;
        this.hallName = hallName;
        this.startDate = startDate;
        this.startTime = startTime;
        this.seatSection = seatSection;
        this.seatRow = seatRow;
        this.userEmail = userEmail;
        this.totalPrice = totalPrice;
    }

    public static BookingResponse create(Booking booking) {
        return BookingResponse.builder()
                .id(booking.getId())
                .bookingNumber(booking.getBookingNumber())
                .posterUrl(booking.getScreening().getMovie().getPosterUrl())
                .movieTitle(booking.getScreening().getMovie().getTitle())
                .screeningTypeDisplay(booking.getScreening().getHall().getScreeningType().getText())
                .theaterName(booking.getScreening().getHall().getTheater().getName())
                .hallName(booking.getScreening().getHall().getName())
                .startDate(DateUtil.formatToStartDate(booking.getScreening().getStartTime()))
                .startTime(DateUtil.formatToHourAndMinute(booking.getScreening().getStartTime()))
                .seatSection(booking.getSeat().getSeatLocation().getSection())
                .seatRow(booking.getSeat().getSeatLocation().getSeatRow())
                .userEmail(booking.getUser().getEmail())
                .totalPrice(booking.getTotalPrice())
                .build();
    }
}
