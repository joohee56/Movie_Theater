package mt.movie_theater.api.controller.theater.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mt.movie_theater.domain.theater.Region;
import mt.movie_theater.domain.theater.Theater;

@Getter
@NoArgsConstructor
public class TheaterCreateRequest {
    private String name;
    private String address;
    private Region region;
    private Float latitude;
    private Float longitude;
    private String contactNumber;

    @Builder
    public TheaterCreateRequest(String name, String address, Region region, Float latitude, Float longitude,
                                String contactNumber) {
        this.name = name;
        this.address = address;
        this.region = region;
        this.latitude = latitude;
        this.longitude = longitude;
        this.contactNumber = contactNumber;
    }

    public Theater toEntity() {
        return Theater.builder()
                .name(this.name)
                .address(this.address)
                .region(this.region)
                .latitude(this.latitude)
                .longitude(this.longitude)
                .contactNumber(this.contactNumber)
                .build();
    }
}
