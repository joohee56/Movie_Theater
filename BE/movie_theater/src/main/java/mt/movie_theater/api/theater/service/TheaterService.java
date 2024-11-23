package mt.movie_theater.api.theater.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mt.movie_theater.api.theater.request.TheaterCreateRequest;
import mt.movie_theater.api.theater.response.RegionTheaterCountResponse;
import mt.movie_theater.api.theater.response.TheaterIdNameResponse;
import mt.movie_theater.api.theater.response.TheaterResponse;
import mt.movie_theater.domain.theater.Region;
import mt.movie_theater.domain.theater.Theater;
import mt.movie_theater.domain.theater.TheaterRepository;
import mt.movie_theater.domain.theater.dto.RegionTheaterCountDto;
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

    public List<RegionTheaterCountResponse> getRegionsWithTheaterCount() {
        List<RegionTheaterCountDto> regions = theaterRepository.findRegionListWithTheaterCount();
        return regions.stream()
                .map(dto -> RegionTheaterCountResponse.create(dto))
                .collect(Collectors.toList());
    }

    public List<TheaterIdNameResponse> getTheatersByRegion(Region region) {
        List<Theater> theaters = theaterRepository.findALlByRegion(region);
        return theaters.stream()
                .map(theater -> TheaterIdNameResponse.create(theater))
                .collect(Collectors.toList());
    }
}
