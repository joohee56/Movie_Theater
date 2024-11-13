package mt.movie_theater.api.payment.service;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.request.PrepareData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import com.siot.IamportRestClient.response.Prepare;
import jakarta.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import mt.movie_theater.api.booking.response.BookingResponse;
import mt.movie_theater.api.booking.service.BookingService;
import mt.movie_theater.api.exception.CancelPaymentException;
import mt.movie_theater.api.exception.PaymentValidationException;
import mt.movie_theater.api.payment.request.PostPaymentRequest;
import mt.movie_theater.domain.payment.PaymentHistory;
import mt.movie_theater.domain.payment.PaymentHistoryRepository;
import mt.movie_theater.domain.user.User;
import mt.movie_theater.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:application-imp.properties")
public class PaymentService {
    @Value("${IMP_API_KEY}")
    private String apiKey;
    @Value("${IMP_API_SECRET_KEY}")
    private String apiSecretKey;
    private IamportClient iamportClient;
    private final UserRepository userRepository;
    private final BookingService bookingService;
    private final PaymentHistoryRepository paymentHistoryRepository;

    @PostConstruct
    public void init() {
        this.iamportClient = new IamportClient(apiKey, apiSecretKey);
    }

    /**
     * 결제 사전 검증
     */
    public boolean preparePayment(String paymentId, int amount) {
        PrepareData prepareData = new PrepareData(paymentId, BigDecimal.valueOf(amount));
        try {
            IamportResponse<Prepare> response = iamportClient.postPrepare(prepareData);
            if (response.getCode() == 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 결제 사후 검증 후 결제내역, 예매 생성
     */
    @Transactional
    public BookingResponse postPayment(PostPaymentRequest request, Long userId, LocalDateTime bookingTime) {
        if (!validatePaymentAmount(request.getImpId(), request.getAmount())) {
            cancelPayment(request.getImpId(), "유효하지 않은 결제이므로 취소됩니다.");
        }
        User user = validateUser(userId);
        PaymentHistory savedPaymentHistory = paymentHistoryRepository.save(PaymentHistory.create(request, user));
        return bookingService.createBooking(userId, request.getScreeningId(), request.getSeatId(),
                savedPaymentHistory.getId(), request.getBookingNumber(), bookingTime);
    }

    public void cancelPayment(String impId, String reason) {
        cancelIamportPayment(impId, reason);
        Optional<PaymentHistory> paymentHistory = paymentHistoryRepository.findByImpId(impId);
        if (paymentHistory.isPresent()) {
            paymentHistory.get().cancel();
        }
    }

    private boolean cancelIamportPayment(String impId, String reason) {
        try {
            CancelData cancelData = new CancelData(impId, true);
            cancelData.setReason(reason);

            IamportResponse<Payment> response = iamportClient.cancelPaymentByImpUid(cancelData);
            if (response.getResponse() != null) {
                return true;
            }
            return false;

        } catch (Exception e) {
            throw new CancelPaymentException("취소 중 예외가 발생했습니다.", e);
        }
    }

    private User validateUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 사용자입니다. 사용자 정보를 다시 확인해 주세요.");
        }
        return user.get();
    }

    private boolean validatePaymentAmount(String imp_uid, Long amount) {
        try {
            IamportResponse<com.siot.IamportRestClient.response.Payment> payment = iamportClient.paymentByImpUid(imp_uid);
            Long paidAmount = payment.getResponse().getAmount().longValue();
            if (paidAmount != amount) {
                return false;
            }
            return true;
        } catch (Exception e) {
            throw new PaymentValidationException("유효하지 않은 결제입니다", e);
        }

    }
}
