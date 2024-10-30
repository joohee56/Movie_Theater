package mt.movie_theater.domain.hall;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mt.movie_theater.domain.BaseEntity;
import mt.movie_theater.domain.theater.Theater;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hall extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Theater theater;
    @Column(length = 50)
    private String name;
    private Integer totalSeats;
}
