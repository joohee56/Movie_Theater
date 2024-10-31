package mt.movie_theater.api.service.booking;

import java.util.Random;

public class BookingNumberGenerator {
    public static String generateBookingNumber() {
        Random random = new Random();

        int part1 = 1000 + random.nextInt(9000); //1000 ~ 9999
        int part2 = 100 + random.nextInt(900); //100 ~ 999
        int part3 = 10000 + random.nextInt(90000); //10000 ~ 99999

        return String.format("%04d-%03d-%05d", part1, part2, part3);
    }
}
