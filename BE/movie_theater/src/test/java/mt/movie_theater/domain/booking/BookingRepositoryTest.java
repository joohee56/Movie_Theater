package mt.movie_theater.domain.booking;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import mt.movie_theater.IntegrationTestSupport;
import mt.movie_theater.domain.screening.Screening;
import mt.movie_theater.domain.screening.ScreeningRepository;
import mt.movie_theater.domain.seat.Seat;
import mt.movie_theater.domain.seat.SeatRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class BookingRepositoryTest extends IntegrationTestSupport {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private ScreeningRepository screeningRepository;
    @Autowired
    private SeatRepository seatRepository;

    @DisplayName("상영시간과 좌석에 해당하는 예매를 조회한다.")
    @Test
    void findByScreeningIdAndSeatId() {
        //given
        Screening screening = Screening.builder()
                .build();
        Screening savedScreening = screeningRepository.save(screening);
        Seat seat = Seat.builder()
                .build();
        Seat savedSeat = seatRepository.save(seat);
        Booking booking = Booking.builder()
                .screening(screening)
                .seat(seat)
                .build();
        bookingRepository.save(booking);

        //when
        Optional<Booking> findBooking = bookingRepository.findByScreeningAndSeat(savedScreening, savedSeat);
        //then
        assertThat(findBooking).isPresent();
    }

    @DisplayName("상영시간과 좌석에 해당하는 예매가 없다면, 빈값을 반환한다.")
    @Test
    void findByScreeningIdAndSeatIdWithEmpty() {
        //given
        Screening screening = Screening.builder()
                                .build();
        Screening savedScreening = screeningRepository.save(screening);

        Seat seat1 = Seat.builder()
                        .seatNumber("A1")
                        .build();
        Seat seat2 = Seat.builder()
                        .seatNumber("B1")
                        .build();
        Seat savedSeat1 = seatRepository.save(seat1);
        Seat savedSeat2 = seatRepository.save(seat2);
        Booking booking = Booking.builder()
                .screening(screening)
                .seat(savedSeat1)
                .build();
        bookingRepository.save(booking);

        //when
        Optional<Booking> findBooking = bookingRepository.findByScreeningAndSeat(savedScreening, savedSeat2);
        //then
        assertThat(findBooking).isEmpty();
    }

}