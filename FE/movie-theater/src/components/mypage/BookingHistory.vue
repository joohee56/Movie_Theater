<template lang="ko">
  <div>
		<PageTitle pageTitle="예매내역"></PageTitle>
    <!-- <div class="ticket-count">총 {{bookingHistory['CONFIRMED'].length}}건</div> -->
    <div v-for="confirmedBooking in bookingHistory['CONFIRMED']">
      <ConfirmedBooking :confirmedBooking="confirmedBooking" @updateBookingHistory="updateBookingHistory"></ConfirmedBooking>
    </div>
    <div class="canceled-booking-section">
      <div class="sub-title">예매취소내역</div>
      <table class="canceled-booking">
        <tr class="header">
          <td style="width: 20%;">취소일시</td><td style="width: 30%;">영화명</td><td style="width: 15%;">극장</td><td style="width: 20%;">상영일시</td><td style="width: 15%;">취소금액</td>
        </tr>
        <tr v-for="canceledBooking in bookingHistory['CANCELED']"">
          <td>{{canceledBooking.cancelDate}} ({{canceledBooking.cancelTime}})</td><td>{{canceledBooking.movieTitle}}</td><td>{{canceledBooking.theaterName}}</td><td>{{canceledBooking.startDate}} {{canceledBooking.startTime}}</td><td class="cancel-price">{{canceledBooking.totalPrice}}원</td>
        </tr>
      </table>
    </div>
  </div>
</template>

<script>
import PageTitle from "@/components/header/PageTitle.vue";
import ConfirmedBooking from "./ConfirmedBooking.vue";
import { getBookingHistory } from "@/api/booking";

export default {
  data() {
    return {
      bookingHistory: {
        CONFIRMED: [],
        CANCELED: [],
      },
    };
  },
  components: { PageTitle, ConfirmedBooking },
  mounted() {
    this.fetchBookingList();
  },
  methods: {
    async fetchBookingList() {
      const response = await getBookingHistory();
      console.log(response);
      this.bookingHistory = response.data.data;
      console.log(this.bookingHistory);
    },
    updateBookingHistory(data) {
      console.log("parent update history");
      console.log(data);
      this.bookingHistory = data;
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
.canceled-booking-section {
  width: 100%;
}
.canceled-booking {
  table-layout: fixed;
  width: 100%;
  font-family: "SUIT-Regular";
  border-top: solid 1px black;
}
.sub-title {
  font-family: SUIT-Medium;
  font-size: 26px;
  margin: 150px 0 20px;
  color: var(--primary-color);
}
.canceled-booking .header {
  background-color: #f2f4f5;
  font-family: "SUIT-SemiBold";
  text-align: center;
}
.canceled-booking td {
  padding: 15px 0;
  border-bottom: solid 1px #f2f4f5;
  font-size: 15px;
  padding-left: 10px;
}
.cancel-price {
  text-align: right;
  color: #e81002;
  font-family: "SUIT-SemiBold";
}
</style>
