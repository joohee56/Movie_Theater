package mt.movie_theater.domain.booking;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mt.movie_theater.domain.BaseEntity;
import mt.movie_theater.domain.screening.Screening;
import mt.movie_theater.domain.seat.Seat;
import mt.movie_theater.domain.user.User;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "booking", uniqueConstraints = {
        @UniqueConstraint(
                name="screening_seat_unique",
                columnNames = {"screening_id", "seat_id"}
        )
})
public class Booking extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    private Screening screening;
    @ManyToOne(fetch = FetchType.LAZY)
    private Seat seat;
    private String bookingNumber;
    private LocalDateTime bookingTime;
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;
    private int totalPrice;

    @Builder
    public Booking(User user, Screening screening, Seat seat, String bookingNumber, LocalDateTime bookingTime,
                   BookingStatus bookingStatus, int totalPrice) {
        this.user = user;
        this.screening = screening;
        this.seat = seat;
        this.bookingNumber = bookingNumber;
        this.bookingTime = bookingTime;
        this.bookingStatus = bookingStatus;
        this.totalPrice = totalPrice;
    }

    public static Booking create(User user, Screening screening, Seat seat, String bookingNumber, LocalDateTime bookingTime, int totalPrice) {
        return Booking.builder()
                .user(user)
                .screening(screening)
                .seat(seat)
                .bookingNumber(bookingNumber)
                .bookingTime(bookingTime)
                .bookingStatus(BookingStatus.CONFIRMED)
                .totalPrice(totalPrice)
                .build();
    }

    public void cancel() {
        this.bookingStatus = BookingStatus.CANCELED;
    }
}
