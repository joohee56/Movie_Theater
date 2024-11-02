package mt.movie_theater.api.screening.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mt.movie_theater.api.screening.request.ScreeningCreateRequest;
import mt.movie_theater.api.screening.response.FullScreeningResponse;
import mt.movie_theater.api.screening.response.ScreeningResponse;
import mt.movie_theater.api.theater.response.RegionTheaterCountResponse;
import mt.movie_theater.domain.hall.Hall;
import mt.movie_theater.domain.hall.HallRepository;
import mt.movie_theater.domain.movie.Movie;
import mt.movie_theater.domain.movie.MovieRepository;
import mt.movie_theater.domain.screening.Screening;
import mt.movie_theater.domain.screening.ScreeningRepository;
import mt.movie_theater.domain.screening.dto.RegionTheaterCountDto;
import mt.movie_theater.domain.theater.Region;
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

    //날짜와 (영화)가 주어졌을 때, 조건에 맞는 상영시간을 가진 지역과 지역별 영화관 갯수 조회
    public List<RegionTheaterCountResponse> getRegionsWithTheaterCount(LocalDate date, Long movieId) {
        LocalDateTime startDateTime = getStartDateTime(date);
        LocalDateTime endDateTime = getEndDateTime(date);
        List<RegionTheaterCountDto> regionCountDtos = screeningRepository.countTheaterByRegion(startDateTime, endDateTime, movieId);

        Map<Region, Long> regionCountMap = regionCountDtos.stream()
                .collect(Collectors.toMap(RegionTheaterCountDto::getRegion, RegionTheaterCountDto::getCount));

        return Arrays.stream(Region.values())
                .map(region -> RegionTheaterCountResponse.create(region, regionCountMap.getOrDefault(region, Long.valueOf(0))))
                .collect(Collectors.toList());
    }

    //날짜, 영화, 영화관이 주어졌을 때, 조건에 맞는 상영시간 리스트 조회
    public List<FullScreeningResponse> getScreenings(LocalDate date, Long movieId, Long theaterId) {
        LocalDateTime startDateTime = getStartDateTime(date);
        LocalDateTime endDateTime = getEndDateTime(date);
        List<Screening> screenings = screeningRepository.findAllByDateAndMovieIdAndTheaterId(startDateTime, endDateTime, movieId, theaterId);
        return screenings.stream()
                .map(screening -> FullScreeningResponse.create(screening))
                .collect(Collectors.toList());
    }

    //날짜, 영화관이 주어졌을 때, 조건에 맞는 상영시간 리스트와 영화 리스트 조회

    private LocalDateTime getStartDateTime(LocalDate date) {
        return date.atStartOfDay(); // 00:00:00
    }

    private LocalDateTime getEndDateTime(LocalDate date) {
        return date.atTime(LocalTime.MAX); // 23:59:59

    }

}
