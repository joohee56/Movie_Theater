package mt.movie_theater.api.seat.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mt.movie_theater.api.seat.response.SeatResponse;
import mt.movie_theater.api.seat.response.SeatSummaryResponse;
import mt.movie_theater.domain.hall.Hall;
import mt.movie_theater.domain.hall.HallRepository;
import mt.movie_theater.domain.seat.Seat;
import mt.movie_theater.domain.seat.SeatLocation;
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
    public List<SeatResponse> createSeatList(Long hallId, int rows, int columns) {
        Hall hall = validateHall(hallId);
        //TODO: 이미 좌석이 생성되어 있다면, 기존 좌석 제거 후 다시 생성..?

        List<Seat> seats = new ArrayList<>();
        for (char row = 'A'; row < 'A' + rows; row++) {
            for (int col = 1; col <= columns; col++) {
                Seat seat = Seat.builder()
                        .hall(hall)
                        .seatLocation(new SeatLocation(String.valueOf(row), String.valueOf(col)))
                        .build();
                seats.add(seat);
            }
        }

        List<Seat> savedSeats = seatRepository.saveAll(seats);
        return savedSeats.stream()
                .map(seat -> SeatResponse.create(seat))
                .collect(Collectors.toList());
    }

    public Map<String, List<SeatSummaryResponse>> getSeatList(Long hallId) {
        List<Seat> seats = seatRepository.findAllByHall(hallId);

        Map<String, List<Seat>> sectionSeatMap = seats.stream()
                .collect(Collectors.groupingBy(seat -> seat.getSeatLocation().getSection()));

        return sectionSeatMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(seat -> SeatSummaryResponse.create(seat))
                                .collect(Collectors.toList())
                ));
    }

    private Hall validateHall(Long hallId) {
        Optional<Hall> optionalHall = hallRepository.findById(hallId);
        if (optionalHall.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 상영관입니다. 상영관 정보를 다시 확인해 주세요.");
        }
        return optionalHall.get();
    }
}
