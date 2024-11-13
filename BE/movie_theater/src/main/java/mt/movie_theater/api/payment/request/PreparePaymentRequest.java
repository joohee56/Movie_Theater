package mt.movie_theater.api.payment.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PreparePaymentRequest {
    private String paymentId;
    private Long amount;

    @Builder
    public PreparePaymentRequest(String paymentId, Long amount) {
        this.paymentId = paymentId;
        this.amount = amount;
    }
}
