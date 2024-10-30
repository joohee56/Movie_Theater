package mt.movie_theater.domain.theater;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mt.movie_theater.domain.BaseEntity;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Theater extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50)
    private String name;
    private String address;
    private Region region;
    @Column(columnDefinition = "FLOAT(9,6)")
    private Float latitude;
    @Column(columnDefinition = "FLOAT(9,6)")
    private Float longitude;
    @Column(length = 50)
    private String contactNumber;
}
