package mt.movie_theater.api.booking.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import mt.movie_theater.api.user.response.UserResponse;
import mt.movie_theater.domain.booking.Booking;
import mt.movie_theater.domain.booking.BookingStatus;

@Getter
public class BookingResponse {
    private Long id;
    private String bookingNumber;
    private String posterUrl;
    private String movieTitle;
    private String screeningType;
    private String theaterName;
    private String hallName;
    private LocalDateTime startTime;
    private LocalDateTime bookingTime;
    private String seatNumber;
    private UserResponse user;
    private int totalPrice;
    private BookingStatus bookingStatus;

    @Builder
    public BookingResponse(Long id, String bookingNumber, String posterUrl, String movieTitle, String screeningType,
                           String theaterName, String hallName, LocalDateTime startTime, LocalDateTime bookingTime,
                           String seatNumber, UserResponse user, int totalPrice, BookingStatus bookingStatus) {
        this.id = id;
        this.bookingNumber = bookingNumber;
        this.posterUrl = posterUrl;
        this.movieTitle = movieTitle;
        this.screeningType = screeningType;
        this.theaterName = theaterName;
        this.hallName = hallName;
        this.startTime = startTime;
        this.bookingTime = bookingTime;
        this.seatNumber = seatNumber;
        this.user = user;
        this.totalPrice = totalPrice;
        this.bookingStatus = bookingStatus;
    }

    public static BookingResponse create(Booking booking) {
        return BookingResponse.builder()
                .id(booking.getId())
                .bookingNumber(booking.getBookingNumber())
                .posterUrl(booking.getScreening().getMovie().getPosterUrl())
                .movieTitle(booking.getScreening().getMovie().getTitle())
                .screeningType(booking.getScreening().getHall().getScreeningType().getText())
                .theaterName(booking.getScreening().getHall().getTheater().getName())
                .hallName(booking.getScreening().getHall().getName())
                .startTime(booking.getScreening().getStartTime())
                .bookingTime(booking.getBookingTime())
                .seatNumber(booking.getSeat().getSeatNumber())
                .user(UserResponse.create(booking.getUser()))
                .totalPrice(booking.getTotalPrice())
                .bookingStatus(booking.getBookingStatus())
                .build();
    }
}
