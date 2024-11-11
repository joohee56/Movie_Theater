<template lang="ko">
	<div>
		결제창
    <button @click="requestPayment">결제</button>
	</div>
</template>

<script>
const { IMP } = window;
import { getScreeningAndTotalPrice } from "@/api/screening";
import { preparePayment } from "@/api/payment";
// import { createBooking } from "@/api/booking";

export default {
  data() {
    return {
      screening: {},
      screeningId: sessionStorage.getItem("screeningId"),
      seatId: sessionStorage.getItem("seatId"),
    };
  },
  created() {
    this.fetchScreeningWithTotalPrice();
  },
  mounted() {
    IMP.init("imp26684322");
  },
  methods: {
    async fetchScreeningWithTotalPrice() {
      const response = await getScreeningAndTotalPrice(this.screeningId);
      this.screening = response.data.data;
      console.log(this.screening);
    },
    generateUniquePaymentId() {
      const now = new Date();
      const formattedDate = now.toISOString().slice(0, 10).replace(/-/g, ""); // yyyyMMdd 형식

      // 랜덤한 문자열 생성 (영문자 + 숫자, 길이 8)
      const randomString = Math.random()
        .toString(36)
        .substring(2, 10)
        .toUpperCase();

      return `${formattedDate}-${randomString}`;
    },
    async requestPayment() {
      const paymentId = this.generateUniquePaymentId();
      const response = await preparePayment(
        paymentId,
        this.screening.totalPrice
      );
      console.log(response);

      IMP.request_pay(
        {
          channelKey: "channel-key-9b25fa8e-097c-4a0b-a3e0-56522892ccc9",
          pay_method: "card",
          merchant_uid: paymentId, // 주문번호
          name: "노르웨이 회전 의자",
          amount: 64900,
          buyer_email: "gildong@gmail.com",
          buyer_name: "홍길동",
          buyer_tel: "010-4242-4242",
          buyer_addr: "서울특별시 강남구 신사동",
          buyer_postcode: "01181",
        },
        async (response) => {
          if (response.error_code != null) {
            return alert(
              `결제에 실패하였습니다. 에러 내용: ${response.error_msg}`
            );
          }
        }
      );
    },

    // async submitReservation() {
    //   const bookingRequest = {
    //     screeningId: this.$route.params.screeningId,
    //     seatId: this.selectedSeats.at(0).seatId,
    //     totalPrice: this.screening.totalPrice,
    //   };
    //   const response = await createBooking(bookingRequest);
    //   if (response.status == 200) {
    //     this.$router.push({
    //       name: "bookingSuccess",
    //       params: { bookingId: response.data.data.id },
    //     });
    //   }
    // },
  },
};
</script>

<style scoped></style>
