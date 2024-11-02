package mt.movie_theater.api.service.theater;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mt.movie_theater.api.controller.theater.request.TheaterCreateRequest;
import mt.movie_theater.api.controller.theater.response.RegionTheaterCountResponse;
import mt.movie_theater.api.controller.theater.response.TheaterIdNameResponse;
import mt.movie_theater.api.controller.theater.response.TheaterResponse;
import mt.movie_theater.domain.theater.Region;
import mt.movie_theater.domain.theater.Theater;
import mt.movie_theater.domain.theater.TheaterRepository;
import mt.movie_theater.domain.theater.dto.RegionTheaterCountDto;
import mt.movie_theater.domain.theater.dto.TheaterIdNameDto;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TheaterService {
    private final TheaterRepository theaterRepository;

    public TheaterResponse createTheater(TheaterCreateRequest request) {
        Theater theater = request.toEntity();
        Theater savedTheater = theaterRepository.save(theater);
        return TheaterResponse.create(savedTheater);
    }

    public List<RegionTheaterCountResponse> getRegionListAndTheaterCount() {
        List<RegionTheaterCountDto> theaterCountDtos = theaterRepository.countTheatersByRegion();
        return theaterCountDtos.stream()
                .map(dto -> RegionTheaterCountResponse.create(dto))
                .collect(Collectors.toList());
    }

    public List<TheaterIdNameResponse> getTheaterListByRegion(Region region) {
        List<TheaterIdNameDto> dtos = theaterRepository.findTheaterIdNameDtoByRegion(region);
        return dtos.stream()
                .map(dto -> TheaterIdNameResponse.create(dto))
                .collect(Collectors.toList());
    }
}
