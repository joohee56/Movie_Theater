package mt.movie_theater.api.payment.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mt.movie_theater.domain.payment.Currency;

@Getter
@NoArgsConstructor
public class PostPaymentRequest {
    @NotNull
    private String impId;
    private Long amount;
    private String bookingNumber;
    private Long payTime;
    private String payMethod;
    private Currency currency;
    private Long screeningId;
    private Long seatId;

    @Builder
    public PostPaymentRequest(String impId, Long amount, String bookingNumber, Long payTime, String payMethod, Currency currency, Long screeningId, Long seatId) {
        this.impId = impId;
        this.amount = amount;
        this.bookingNumber = bookingNumber;
        this.payTime = payTime;
        this.payMethod = payMethod;
        this.currency = currency;
        this.screeningId = screeningId;
        this.seatId = seatId;
    }
}
