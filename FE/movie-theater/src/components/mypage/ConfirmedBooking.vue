<template lang="ko">
	<div class="confirmed-booking-container">
		<div>
			<img :src="confirmedBooking.posterUrl" class="poster-img" />
		</div>
		<div class="confirmed-booking">
			<table>
				<tr>
					<td>예매번호</td>
					<td class="booking-number">{{ confirmedBooking.bookingNumber }}</td>
				</tr>
				<tr>
					<td>영화명</td>
					<td>
						{{ confirmedBooking.movieTitle }}
						{{ confirmedBooking.screeningTypeDisplay }}
					</td>
				</tr>
				<tr>
					<td>극장/상영관</td>
					<td>
						{{ confirmedBooking.theaterName }}/{{ confirmedBooking.hallName }}
					</td>
					<td>관람인원</td>
					<td>성인 {{confirmedBooking.seats.length}}명</td>
				</tr>
				<tr>
					<td>관람일시</td>
					<td>
						{{ confirmedBooking.startDate }} {{ confirmedBooking.startTime }}
					</td>
					<td>관람좌석</td>
					<td class="seats">
						<span v-for="seat in confirmedBooking.seats">{{ seat.section }}열 {{ seat.seatRow }}</span>
					</td>
				</tr>
				<tr>
					<td>결제일시</td>
					<td>{{ confirmedBooking.bookingTime }}</td>
				</tr>
			</table>
			<div class="navigation-buttons">
				<button class="cancle-booking-button" @click="cancelBooking(confirmedBooking.id)">
					예매취소
				</button>
			</div>
		</div>
	</div>
</template>

<script>
import { cancelBookingAndGetBookingHistory } from "@/api/booking";

export default {
  props: ["confirmedBooking"],
  methods: {
    async cancelBooking(bookingId) {
      alert("예매를 취소하시겠습니까?");
      const response = await cancelBookingAndGetBookingHistory(bookingId);
      console.log(response.data);
      this.$emit("updateBookingHistory", response.data.data);
    },
  },
};
</script>

<style scoped>
.confirmed-booking-container {
  display: flex;
  border: 1px solid var(--border-line-color);
  border-radius: 10px;
  padding: 20px;
  font-family: "SUIT-Regular";
  font-size: 15px;
  width: 650px;
  margin: 0 auto;
  margin-bottom: 15px;
}
.poster-img {
  width: 130px;
  aspect-ratio: 290/410;
  margin-right: 30px;
}
.confirmed-booking table {
  border-collapse: separate;
  border-spacing: 9px;
  margin-bottom: 5px;
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
.confirmed-booking .seats span:not(:last-child)::after {
  content: ", ";
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
