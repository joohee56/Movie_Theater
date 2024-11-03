package mt.movie_theater.api.theater.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mt.movie_theater.api.theater.request.TheaterCreateRequest;
import mt.movie_theater.api.theater.response.TheaterIdNameResponse;
import mt.movie_theater.api.theater.response.TheaterResponse;
import mt.movie_theater.domain.theater.Region;
import mt.movie_theater.domain.theater.Theater;
import mt.movie_theater.domain.theater.TheaterRepository;
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

    //...refactoring
    public List<TheaterIdNameResponse> getTheaterListByRegion(Region region) {
        List<TheaterIdNameDto> dtos = theaterRepository.findTheaterIdNameDtoByRegion(region);
        return dtos.stream()
                .map(dto -> TheaterIdNameResponse.create(dto))
                .collect(Collectors.toList());
    }


}
