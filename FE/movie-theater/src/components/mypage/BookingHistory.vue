<template lang="ko">
  <div>
		<PageTitle pageTitle="예매내역"></PageTitle>
    <div class="ticket-count">총 {{bookingHistory['CONFIRMED'].length}}건</div>
    <div v-for="confirmedBooking in bookingHistory['CONFIRMED']" class="confirmed-booking-container">
      <div>
        <img src="@/assets/img/no-poster-img.png" class="poster-img">
      </div>
      <div class="confirmed-booking">
        <table>
          <tr>
            <td>예매번호</td><td class="booking-number">{{confirmedBooking.bookingNumber}}</td>
          </tr>
          <tr>
            <td>영화명</td><td>{{confirmedBooking.movieTitle}} {{confirmedBooking.screeningTypeDisplay}}</td>
          </tr>
          <tr>
            <td>극장/상영관</td><td>{{confirmedBooking.theaterName}}/{{confirmedBooking.hallName}}</td><td>관람인원</td><td>성인 1명</td>
          </tr>
          <tr>
            <td>관람일시</td><td>{{confirmedBooking.startDate}} {{confirmedBooking.startTime}}</td><td>관람좌석</td><td>{{confirmedBooking.seatSection}}열 {{confirmedBooking.seatRow}}</td>
          </tr>
          <tr>
            <td>결제일시</td><td>{{confirmedBooking.bookingDate}}</td>
          </tr>
        </table>
        <div class="navigation-buttons">
          <button class="cancle-booking-button" @click="submitCancleBooking">예매취소</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import PageTitle from "@/components/header/PageTitle.vue";
import { getBookingHistory } from "@/api/booking";

export default {
  data() {
    return {
      bookingHistory: {},
    };
  },
  components: { PageTitle },
  mounted() {
    this.fetchBookingList();
  },
  methods: {
    async fetchBookingList() {
      const response = await getBookingHistory("1");
      console.log(response);
      this.bookingHistory = response.data.data;
      console.log(this.bookingHistory);
    },
    submitCancleBooking() {
      alert("예매를 취소하시겠습니까?");
    },
  },
};
</script>

<style scoped>
.ticket-count {
  font-family: "SUIT-Medium";
  font-size: 19px;
  padding: 10px 0;
}
.confirmed-booking-container {
  display: flex;
  border: 1px solid var(--border-line-color);
  border-radius: 10px;
  padding: 30px;
  font-family: "SUIT-Regular";
  font-size: 15px;
  width: 620px;
  margin: 0 auto;
}
.poster-img {
  width: 130px;
  aspect-ratio: 290/410;
  margin-right: 50px;
}
.confirmed-booking table {
  border-collapse: separate;
  border-spacing: 10px;
  margin-bottom: 15px;
}
.booking-number {
  color: var(--point-color);
  font-size: 17px;
  font-family: "SUIT-SemiBold";
}
.confirmed-booking table td:first-child {
  text-align: right;
  font-family: "SUIT-SemiBold";
}
.confirmed-booking table td:nth-child(2) {
  padding-right: 70px;
}

/* 예매 취소 버튼 */
.navigation-buttons {
  float: right;
}
.navigation-buttons button {
  font-size: 15px;
  border: none;
  padding: 10px 15px;
  color: white;
  font-family: "SUIT-SemiBold";
  border-radius: 4px;
}
.navigation-buttons .cancle-booking-button {
  background-color: #666666;
}
</style>
