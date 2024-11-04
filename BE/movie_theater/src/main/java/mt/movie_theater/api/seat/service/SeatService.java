package mt.movie_theater.api.seat.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mt.movie_theater.api.seat.request.SeatListCreateRequest;
import mt.movie_theater.api.seat.response.SeatResponse;
import mt.movie_theater.api.seat.response.SeatSummaryResponse;
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
        Optional<Hall> optionalHall = hallRepository.findById(request.getHallId());
        if (optionalHall.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 상영관입니다. 상영관 정보를 다시 확인해 주세요.");
        }

        //TODO: 이미 좌석이 생성되어 있다면, 기존 좌석 제거 후 다시 생성..?

        Hall hall = optionalHall.get();
        List<Seat> seats = new ArrayList<>();
        int rows = request.getRows();
        int columns = request.getColumns();
        for (char row = 'A'; row < 'A' + rows; row++) {
            for (int col = 1; col <= columns; col++) {
                Seat seat = Seat.builder()
                        .hall(hall)
                        .section(String.valueOf(row))
                        .seatNumber(String.valueOf(col))
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
                .collect(Collectors.groupingBy(Seat::getSection));
        return sectionSeatMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(seat -> SeatSummaryResponse.create(seat))
                                .collect(Collectors.toList())
                ));
    }
}
