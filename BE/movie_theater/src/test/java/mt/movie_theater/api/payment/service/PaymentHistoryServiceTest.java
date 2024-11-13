package mt.movie_theater.api.payment.service;

import static mt.movie_theater.domain.payment.PayStatus.CANCELED;
import static mt.movie_theater.domain.payment.PayStatus.COMPLETED;
import static mt.movie_theater.domain.payment.PayStatus.FAILED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import com.siot.IamportRestClient.response.Prepare;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;
import mt.movie_theater.IntegrationMockitoTestSupport;
import mt.movie_theater.IntegrationTestSupport;
import mt.movie_theater.api.exception.PreparePaymentException;
import mt.movie_theater.domain.payment.PayStatus;
import mt.movie_theater.domain.payment.PaymentHistory;
import mt.movie_theater.domain.payment.PaymentHistoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@ExtendWith(MockitoExtension.class)
class PaymentHistoryServiceMockitoTest extends IntegrationTestSupport {
    @MockBean
    private IamportResponse<Prepare> prepareResponse;
    @MockBean
    private IamportResponse<Payment> paymentResponse;
    @MockBean
    private IamportClient iamportClient;
    @InjectMocks
    private PaymentHistoryService paymentHistoryService;
    @Spy
    private PaymentHistoryRepository paymentHistoryRepository;

    @DisplayName("결제 사전 등록이 성공한다.")
    @Test
    void preparePayment() throws IamportResponseException, IOException {
        //given
        BDDMockito.given(iamportClient.postPrepare(Mockito.any()))
                        .willReturn(prepareResponse);
        BDDMockito.given(prepareResponse.getCode())
                .willReturn(0);

        //when, then
        assertThat(paymentHistoryService.preparePayment("paymentId", Long.valueOf(10000) )).isTrue();
    }

    @DisplayName("결제 사전 등록이 실패한다.")
    @Test
    void preparePaymentFail() throws IamportResponseException, IOException {
        //given
        BDDMockito.given(prepareResponse.getCode())
                .willReturn(-1);
        BDDMockito.given(iamportClient.postPrepare(Mockito.any()))
                .willReturn(prepareResponse);

        //when, then
        assertThat(paymentHistoryService.preparePayment("paymentId",Long.valueOf(10000) )).isFalse();
    }

    @DisplayName("결제 사전 등록 중 예외 발생 시 예외가 전환된다.")
    @Test
    void preparePaymentException() throws IamportResponseException, IOException {
        //given
        BDDMockito.given(iamportClient.postPrepare(Mockito.any()))
                .willThrow(IOException.class);

        //when, then
        assertThatThrownBy(() -> paymentHistoryService.preparePayment("paymentId", Long.valueOf(10000)))
                .isInstanceOf(PreparePaymentException.class)
                .hasMessage("결제 사전 등록 중 예외가 발생했습니다.");
    }

    @DisplayName("결제를 실패 처리한다.")
    @Test
    void failPayment() throws IamportResponseException, IOException {
        //given
        setupPaymentResponsePayStatus("cancelled");
        BDDMockito.given(iamportClient.cancelPaymentByImpUid(Mockito.any()))
                .willReturn(paymentResponse);

        PaymentHistory paymentHistory = createPaymentHistory(COMPLETED);
        Mockito.doReturn(Optional.of(paymentHistory))
                        .when(paymentHistoryRepository)
                        .findByImpId(paymentHistory.getImpId());

        //when
        paymentHistoryService.failPayment(paymentHistory.getImpId(), "결제 실패 처리 테스트");

        //then
        assertThat(paymentHistory.getPayStatus()).isEqualTo(FAILED);
    }

    @DisplayName("결제를 취소 처리한다.")
    @Test
    void cancelPayment() throws IamportResponseException, IOException {
        //given
        setupPaymentResponsePayStatus("cancelled");
        BDDMockito.given(iamportClient.cancelPaymentByImpUid(Mockito.any()))
                .willReturn(paymentResponse);

        PaymentHistory paymentHistory = createPaymentHistory(COMPLETED);
        Mockito.doReturn(Optional.of(paymentHistory))
                .when(paymentHistoryRepository)
                .findByImpId(paymentHistory.getImpId());

        //when
        paymentHistoryService.cancelPayment(paymentHistory.getImpId(), "결제 취소 처리 테스트");

        //then
        assertThat(paymentHistory.getPayStatus()).isEqualTo(CANCELED);
    }

    @DisplayName("사용자가 결제 요청한 금액과 실제 처리된 금액을 비교할 때, 같은 경우 성공한다.")
    @Test
    void validatePaymentAmountSuccess() throws IamportResponseException, IOException {
        //given
        setupPaymentResponseAmount(Long.valueOf(10000));
        BDDMockito.given(iamportClient.paymentByImpUid(Mockito.any()))
                .willReturn(paymentResponse);

        //when, then
        assertThat(paymentHistoryService.validatePaymentAmount("paymentId", Long.valueOf(10000))).isTrue();
    }

    @DisplayName("사용자가 결제 요청한 금액과 실제 처리된 금액을 비교할 때, 다른 경우 실패한다.")
    @Test
    void validatePaymentAmountFail() throws IamportResponseException, IOException {
        //given
        setupPaymentResponseAmount(Long.valueOf(10000));
        BDDMockito.given(iamportClient.paymentByImpUid(Mockito.any()))
                .willReturn(paymentResponse);

        //when, then
        assertThat(paymentHistoryService.validatePaymentAmount("paymentId", Long.valueOf(30000))).isFalse();
    }

    private void setupPaymentResponsePayStatus(String payStatus) {
        Payment payment = Mockito.mock(Payment.class);
        BDDMockito.given(paymentResponse.getResponse())
                .willReturn(payment);
        BDDMockito.given(payment.getStatus())
                .willReturn(payStatus);
    }

    private void setupPaymentResponseAmount(Long amount) {
        Payment payment = Mockito.mock(Payment.class);
        BDDMockito.given(paymentResponse.getResponse())
                .willReturn(payment);
        BDDMockito.given(payment.getAmount())
                .willReturn(BigDecimal.valueOf(amount));
    }

    private PaymentHistory createPaymentHistory(PayStatus payStatus) {
        return PaymentHistory.builder()
                .payStatus(payStatus)
                .build();
    }
}