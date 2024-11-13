package mt.movie_theater.domain.booking;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mt.movie_theater.domain.BaseEntity;
import mt.movie_theater.domain.payment.PaymentHistory;
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
    @OneToOne(fetch = FetchType.LAZY)
    private PaymentHistory paymentHistory;
    private String bookingNumber;
    private LocalDateTime bookingTime;
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;


    @Builder
    public Booking(User user, Screening screening, Seat seat, PaymentHistory paymentHistory, String bookingNumber, LocalDateTime bookingTime,
                   BookingStatus bookingStatus) {
        this.user = user;
        this.screening = screening;
        this.seat = seat;
        this.paymentHistory = paymentHistory;
        this.bookingNumber = bookingNumber;
        this.bookingTime = bookingTime;
        this.bookingStatus = bookingStatus;
    }

    public static Booking create(User user, Screening screening, Seat seat, PaymentHistory paymentHistory, String bookingNumber, LocalDateTime bookingTime) {
        return Booking.builder()
                .user(user)
                .screening(screening)
                .seat(seat)
                .paymentHistory(paymentHistory)
                .bookingNumber(bookingNumber)
                .bookingTime(bookingTime)
                .bookingStatus(BookingStatus.CONFIRMED)
                .build();
    }

    public void cancel() {
        //TODO: 결제 취소
        this.bookingStatus = BookingStatus.CANCELED;
        this.seat.cancel();
    }
}
