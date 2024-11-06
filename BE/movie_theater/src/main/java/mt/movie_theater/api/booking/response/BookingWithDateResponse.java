package mt.movie_theater.api.booking.response;

import lombok.Builder;
import lombok.Getter;
import mt.movie_theater.domain.booking.Booking;
import mt.movie_theater.util.DateUtil;

@Getter
public class BookingWithDateResponse {
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
    private int totalPrice;
    private String cancelDate;
    private String cancelTime;
    private String bookingDate;

    @Builder
    public BookingWithDateResponse(Long id, String bookingNumber, String posterUrl, String movieTitle,
                                   String screeningTypeDisplay, String theaterName, String hallName, String startDate,
                                   String startTime, String seatSection, String seatRow, int totalPrice,
                                   String cancelDate,
                                   String cancelTime, String bookingDate) {
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
        this.totalPrice = totalPrice;
        this.cancelDate = cancelDate;
        this.cancelTime = cancelTime;
        this.bookingDate = bookingDate;
    }

    public static BookingWithDateResponse create(Booking booking) {
        return BookingWithDateResponse.builder()
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
                .totalPrice(booking.getTotalPrice())
                .cancelDate(DateUtil.formatToStartDate(booking.getUpdatedAt()))
                .cancelTime(DateUtil.formatToHourAndMinute(booking.getUpdatedAt()))
                .bookingDate(DateUtil.formatToStartDate(booking.getBookingTime()))
                .build();
    }
}
