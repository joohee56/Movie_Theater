package mt.movie_theater.domain.screening;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mt.movie_theater.domain.hall.Hall;
import mt.movie_theater.domain.movie.Movie;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Screening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;
    @ManyToOne(fetch = FetchType.LAZY)
    private Hall hall;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long price;
}
