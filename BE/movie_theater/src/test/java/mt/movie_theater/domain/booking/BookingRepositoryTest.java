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

    @DisplayName("")
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

}