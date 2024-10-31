package mt.movie_theater.domain.booking;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BookingStatus {
    CONFIRMED("확정"),
    CANCELED("취소");
    private final String text;
}
