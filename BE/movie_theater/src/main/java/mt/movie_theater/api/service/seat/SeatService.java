package mt.movie_theater.api.service.seat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mt.movie_theater.api.controller.seat.request.SeatListCreateRequest;
import mt.movie_theater.api.controller.seat.response.SeatResponse;
import mt.movie_theater.domain.hall.Hall;
import mt.movie_theater.domain.hall.HallRepository;
import mt.movie_theater.domain.seat.Seat;
import mt.movie_theater.domain.seat.SeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SeatService {
    private final SeatRepository seatRepository;
    private final HallRepository hallRepository;

    @Transactional
    public List<SeatResponse> createSeatList(SeatListCreateRequest request) {
        Optional<Hall> hall = hallRepository.findById(request.getHallId());
        if (hall.isEmpty()) {
            throw new IllegalArgumentException("잘못된 상영관입니다.");
        }

        List<Seat> seats = new ArrayList<>();
        int rows = request.getRows();
        int columns = request.getColumns();
        for (char row = 'A'; row < 'A' + rows; row++) {
            for (int col = 1; col <= columns; col++) {
                String seatNumber = row + String.valueOf(col);
                Seat seat = Seat.builder()
                        .seatNumber(seatNumber)
                        .build();
                hall.get().addSeat(seat);
                seats.add(seat);
            }
        }

        List<Seat> savedSeats = seatRepository.saveAll(seats);
        return savedSeats.stream()
                .map(seat -> SeatResponse.create(seat))
                .collect(Collectors.toList());
    }
}
