package mt.movie_theater.domain.seat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mt.movie_theater.domain.BaseEntity;
import mt.movie_theater.domain.hall.Hall;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Seat extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Hall hall;
    private String seatNumber;
    @Column(columnDefinition = "TINYINT(1) DEFAULT true")
    private boolean isAvailable;

    @Builder
    public Seat(String seatNumber) {
        this.seatNumber = seatNumber;
        this.isAvailable = true;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }
}
