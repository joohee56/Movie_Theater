package mt.movie_theater.api.screening.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import mt.movie_theater.api.screening.request.ScreeningCreateRequest;
import mt.movie_theater.api.screening.response.ScreeningResponse;
import mt.movie_theater.domain.hall.Hall;
import mt.movie_theater.domain.hall.HallRepository;
import mt.movie_theater.domain.movie.Movie;
import mt.movie_theater.domain.movie.MovieRepository;
import mt.movie_theater.domain.screening.Screening;
import mt.movie_theater.domain.screening.ScreeningRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScreeningService {
    private final ScreeningRepository screeningRepository;
    private final MovieRepository movieRepository;
    private final HallRepository hallRepository;

    @Transactional
    public ScreeningResponse createScreening(ScreeningCreateRequest request) {
        Optional<Movie> movie = movieRepository.findById(request.getMovieId());
        if (movie.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 영화입니다. 영화 정보를 다시 확인해 주세요.");
        }
        Optional<Hall> hall = hallRepository.findById(request.getHallId());
        if (hall.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 상영관입니다. 상영관 정보를 다시 확인해 주세요.");
        }

        LocalDateTime endTime = calculateEndTime(request.getStartTime(), movie.get().getDurationMinutes());
        int ticketTotalPrice = TicketPriceCalculator.calculateFinalPrice(movie.get().getStandardPrice(), hall.get().getHallTypeModifier(), request.getStartTime());
        Screening screening = Screening.builder()
                                .movie(movie.get())
                                .hall(hall.get())
                                .startTime(request.getStartTime())
                                .endTime(endTime)
                                .totalPrice(ticketTotalPrice)
                                .build();
        Screening savedScreening = screeningRepository.save(screening);
        return ScreeningResponse.create(savedScreening);
    }

    private LocalDateTime calculateEndTime(LocalDateTime startTime, Duration duration) {
        return startTime.plus(duration);
    }
}
