<template lang="ko">
  <div class="container">
		<PageTitle pageTitle="예매완료"></PageTitle>
		<div class="ticket-content">
			<div class="left-side">
				<div>티켓 예매번호</div>
				<div class="booking-number">{{booking.bookingNumber}}</div>
				<img src="@/assets/img/no-poster-img.png" class="poster-img">
			</div>
			<div class="right-side">
				<div class="title">예매가 완료되었습니다!</div>
				<div class="booking-detail">
					<table>
						<tr>
							<td>· 예매영화</td><td>{{booking.movieTitle}} / {{booking.screeningTypeDisplay}}</td>
						</tr>
						<tr>
							<td>· 관람극장/상영관</td><td>{{booking.theaterName}} / {{booking.hallName}}</td>
						</tr>
						<tr>
							<td>· 관람일시</td><td>{{booking.startDate}} {{booking.startTime}}</td>
						</tr>
						<tr>
							<td>· 관람인원</td><td>성인 1명</td>
						</tr>
						<tr>
							<td>· 좌석번호</td><td>{{booking.seatSection}}열 {{booking.seatRow}}</td>
						</tr>
						<tr>
							<td>· 이메일</td><td>{{booking.userEmail}}</td>
						</tr>
						<tr>
							<td>· 결제정보</td><td>{{booking.totalPrice}}원</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
		<img src="@/assets/img/ticket-frame.png" class="ticket-frame"></img>
		<div class="navigation-buttons">
			<button class="booking-history-btn">예매내역</button>
		</div>
	</div>
</template>

<script>
import PageTitle from "@/components/header/PageTitle.vue";
import { getBooking } from "@/api/booking";

export default {
  data() {
    return {
      booking: {},
    };
  },
  components: { PageTitle },
  mounted() {
    this.fetchBooking();
  },
  methods: {
    async fetchBooking() {
      const response = await getBooking(this.$route.params.bookingId);
      this.booking = response.data.data;
    },
  },
};
</script>

<style scoped>
.ticket-content {
  z-index: 1;
  position: relative;
  display: flex;
  color: var(--text-sub-color);
  height: 375px;
  font-family: "SUIT-Regular";
  font-size: 16px;
}
.ticket-content .left-side {
  text-align: center;
  width: 270px;
  padding-top: 30px;
}
.left-side .booking-number {
  color: var(--point-color);
  font-family: "SUIT-Bold";
  font-size: 23px;
  margin-bottom: 20px;
}
.poster-img {
  width: 160px;
}

.ticket-content .right-side {
  padding-left: 70px;
  width: 750px;
}
.right-side .title {
  color: var(--point-color);
  font-family: "SUIT-Bold";
  font-size: 23px;
  padding: 30px 0;
  border-bottom: 1px solid #434343;
}
.booking-detail table {
  border-collapse: separate;
  border-spacing: 6px;
  padding: 15px 0;
}
.booking-detail td:nth-child(2) {
  color: white;
  padding-left: 17px;
}
.ticket-frame {
  position: absolute;
  z-index: -1;
  top: 190px;
  width: 1110px;
}
.navigation-buttons {
  margin-top: 20px;
  text-align: center;
}
.navigation-buttons button {
  font-family: "SUIT-Medium";
  padding: 13px 30px;
  font-size: 16px;
  border-radius: 5px;
}
.booking-history-btn {
  background-color: var(--primary-color);
  color: white;
  border: none;
}
</style>
