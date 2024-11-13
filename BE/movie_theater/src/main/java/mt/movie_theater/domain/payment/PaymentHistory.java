package mt.movie_theater.domain.payment;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mt.movie_theater.api.payment.request.PostPaymentRequest;
import mt.movie_theater.domain.user.User;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    private String impId;
    private Long amount;
    private LocalDateTime payTime;
    @Enumerated(EnumType.STRING)
    private PayMethod payMethod;
    @Enumerated(EnumType.STRING)
    private Currency currency;
    @Enumerated(EnumType.STRING)
    private PayStatus payStatus;

    @Builder
    private Payment(User user, String impId, Long amount, LocalDateTime payTime, PayMethod payMethod, Currency currency,
                   PayStatus payStatus) {
        this.user = user;
        this.impId = impId;
        this.amount = amount;
        this.payTime = payTime;
        this.payMethod = payMethod;
        this.currency = currency;
        this.payStatus = payStatus;
    }

    public static Payment create(PostPaymentRequest request, User user) {
        return Payment.builder()
                .user(user)
                .impId(request.getImpId())
                .amount(request.getAmount())
                .payTime(request.getPayTime())
                .payMethod(request.getPayMethod())
                .currency(request.getCurrency())
                .payStatus(PayStatus.COMPLETED)
                .build();
    }
}
