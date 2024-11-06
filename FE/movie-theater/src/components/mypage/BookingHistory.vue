<template lang="ko">
  <div>
		<PageTitle pageTitle="예매내역"></PageTitle>
    <div class="ticket-count">총 {{bookingHistory['CONFIRMED'].length}}건</div>
    <div v-for="confirmedBooking in bookingHistory['CONFIRMED']">
      <ConfirmedBooking :confirmedBooking="confirmedBooking"></ConfirmedBooking>
    </div>
    <div>
      <div>예매취소내역</div>
      <table>
        <th>
          <td>취소일시</td><td>영화명</td><td>극장</td><td>상영일시</td><td>취소금액</td>
        </th>
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
      const response = await getBookingHistory("1");
      console.log(response);
      this.bookingHistory = response.data.data;
      console.log(this.bookingHistory);
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
</style>
