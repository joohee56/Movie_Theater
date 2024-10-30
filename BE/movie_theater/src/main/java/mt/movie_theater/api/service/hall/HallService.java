package mt.movie_theater.api.service.hall;

import java.util.Optional;
import lombok.AllArgsConstructor;
import mt.movie_theater.api.controller.hall.request.HallCreateRequest;
import mt.movie_theater.api.controller.hall.response.HallResponse;
import mt.movie_theater.domain.hall.Hall;
import mt.movie_theater.domain.hall.HallRepository;
import mt.movie_theater.domain.theater.Theater;
import mt.movie_theater.domain.theater.TheaterRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HallService {
    private final HallRepository hallRepository;
    private final TheaterRepository theaterRepository;

    public HallResponse createHall(HallCreateRequest request) {
        Optional<Theater> theater = theaterRepository.findById(request.getTheaterId());
        if(theater.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 영화관입니다.");
        }

        Hall hall = request.toEntity(theater.get());
        Hall savedHall = hallRepository.save(hall);
        return HallResponse.create(savedHall);
    }
}
