<template lang="ko">
  <div>
		<PageTitle pageTitle="예매내역"></PageTitle>
    <div class="ticket-count">총 {{bookingHistory['CONFIRMED'].length}}건</div>
    <div v-for="confirmedBooking in bookingHistory['CONFIRMED']">
      <ConfirmedBooking :confirmedBooking="confirmedBooking"></ConfirmedBooking>
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
      bookingHistory: {},
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
</style>
