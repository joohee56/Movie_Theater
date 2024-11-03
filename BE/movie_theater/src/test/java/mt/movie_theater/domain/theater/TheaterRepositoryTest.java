package mt.movie_theater.domain.theater;

import static mt.movie_theater.domain.theater.Region.GYEONGGI;
import static mt.movie_theater.domain.theater.Region.SEOUL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.List;
import mt.movie_theater.IntegrationTestSupport;
import mt.movie_theater.domain.theater.dto.RegionTheaterCountDto;
import mt.movie_theater.domain.theater.dto.TheaterIdNameDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class TheaterRepositoryTest extends IntegrationTestSupport {
}