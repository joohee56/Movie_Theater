package mt.movie_theater.api.payment.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mt.movie_theater.domain.payment.Currency;

@Getter
@NoArgsConstructor
public class ConfirmBookingRequest {
    @NotNull
    private Long bookingId;
    @NotNull
    private String impId;
    private Long amount;
    private String bookingNumber;
    private Long payTime;
    private String payMethod;
    private Currency currency;

    @Builder
    public ConfirmBookingRequest(Long bookingId, String impId, Long amount, String bookingNumber, Long payTime,
                                 String payMethod, Currency currency) {
        this.bookingId = bookingId;
        this.impId = impId;
        this.amount = amount;
        this.bookingNumber = bookingNumber;
        this.payTime = payTime;
        this.payMethod = payMethod;
        this.currency = currency;
    }
}
