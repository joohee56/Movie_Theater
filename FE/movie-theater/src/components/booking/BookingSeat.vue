<template lang="ko">
	<div class="container">

		<div class="left-side">
			<div class="sub-title">관람인원선택</div>
			<div class="select-section-container">
				<div class="audience-selector">
					성인 1
				</div>
				<div class="seat-container">
					<div class="screen-title">SCREEN</div>
					<div v-for="section in sections" :key="section">
						{{section}}
						<button v-for="(seat, index) in seats[section]" :key="`${section}${index}`" @click="toggleSeatSelection(seat)" :class="['seat', { selected: seat.isSelected, booked: seat.isBooked }]" :disabled="seat.isBooked">
							{{seat.seatNumber}}
						</button>
					</div>
					<!-- <div class="selected-seats">
						선택된 좌석: {{ selectedSeats.map(seat => seat.seatNumber).join(', ') }}
					</div> -->
				</div>
			</div>
		</div>

		<div class="right-side">
			<div class="description-container">
				<div class="movie-title-container">
					<div class="age-rating age-ALL">{{screening.ageRatingDisplay}}</div>
					<div>
						<div class="movie-title">{{screening.movieTitle}}</div>
						<div>{{screening.screeningTypeDisplay}}}</div>
					</div>
				</div>
				<div class="booking-description">
					<div>
						<div>{{screening.theaterName}}</div>
						<div>{{screening.hallName}}</div>
						<div>{{screening.startDate}}</div>
						<div class="movie-startTime">{{screening.startTime}}~{{screening.endTime}}</div>
					</div>
					<img src="@/assets/img/no-poster-img.png" class="poster-img"></img>
				</div>
				<div class="seat-description">
					<div class="seat-guide">
						<div>선택</div>
						<div>예매완료</div>
						<div>일반</div>
					</div>
					<div class="selected-seat">
						<div>선택좌석</div>
						<div class="seat-numbers-container">
							<div class="seat-number">A7</div>
							<div class="seat-number">A7</div>
							<div class="seat-number">A7</div>
							<div class="seat-number">A7</div>
							<div class="seat-number">A7</div>
							<div class="seat-number">A7</div>
							<div class="seat-number">A7</div>
							<div class="seat-number">A7</div>
						</div>
					</div>
				</div>
				<div class="total-price-container">
					<span class="title">최종결제금액</span>
					<span class="total-price"><span class="number">{{screening.totalPrice}}</span> 원</span>
				</div>
			</div>
			<div class="navigation-buttons">
				<button class="prev-button">이전</button>
				<button class="next-button">다음</button>
			</div>
		</div>

	</div>
</template>

<script>
import { getSeats } from "@/api/seat";
import { getScreeningAndTotalPrice } from "@/api/screening";

export default {
  data() {
    return {
      sections: [],
      seats: {},
      screening: {},
    };
  },
  computed: {
    selectedSeats() {
      // return this.seats.filter((seat) => seat.selected);
      return Object.values(this.seats)
        .flat()
        .filter((seat) => seat.selected);
    },
  },
  created() {
    this.fetchSeats();
    this.fetchScreeningWithTotalPrice();
  },
  methods: {
    async fetchSeats() {
      const response = await getSeats(this.$route.params.hallId);
      const data = response.data.data;

      // 좌석 정보에 selected 필드를 추가
      this.seats = Object.fromEntries(
        Object.entries(data).map(([section, seatArray]) => [
          section,
          seatArray.map((seat) => ({
            ...seat,
            isSelected: false, // selected 필드 추가
          })),
        ])
      );
      this.sections = Object.keys(data);
    },
    async fetchScreeningWithTotalPrice() {
      const response = await getScreeningAndTotalPrice(
        this.$route.params.screeningId
      );
      this.screening = response.data.data;
      console.log(this.screening);
    },
    toggleSeatSelection(seat) {
      seat.isSelected = !seat.isSelected; // 좌석 선택 상태 토글
    },
  },
};
</script>

<style scoped>
.container {
  --border-color: #434343;
  --secondary-color: #329eb1;
}

.container {
  display: grid;
  grid-template-columns: 70% 28%;
  column-gap: 20px;
  height: 580px;
}

/* left-side */
.left-side {
  height: 100%;
  border-top: 1px solid black;
}
.sub-title {
  font-family: "SUIT-Regular";
  font-size: 20px;
  padding: 15px 0;
}
.audience-selector {
  font-family: "SUIT-Regular";
  background-color: #f2f4f5;
  padding: 15px 10px;
  font-size: 16px;
}
.select-section-container {
  border: 1px solid var(--border-line-color);
  height: 90%;
}

.seat-container {
  text-align: center;
  padding: 20px;
  overflow-y: auto;
  max-height: 430px;
}
.screen-title {
  font-family: "SUIT-Bold";
  font-size: 18px;
  margin: 10px;
}
.seat {
  width: 22px;
  height: 22px;
  background-color: #747474;
  border: 2px solid var(--secondary-color);
  cursor: pointer;
  text-align: center;
  font-size: 11px;
  color: white;
}
.selected {
  background: var(--primary-color);
}
.booked {
  background: #cccccc;
}

/* right side */
.right-side {
  background-color: #333333;
  border-radius: 10px;
  color: #c4c4c4;
  font-family: "SUIT-Regular";
  font-size: 14px;
  position: relative;
}
.description-container {
  padding: 20px;
}
.movie-title-container {
  display: flex;
  border-bottom: 1px solid var(--border-color);
  padding-bottom: 10px;
}
.movie-title {
  color: white;
  font-family: "SUIT-SemiBold";
  font-size: 18px;
}
.age-rating {
  display: inline;
  font-family: "S-CoreDream-6Bold";
  color: white;
  border-radius: 3px;
  margin-right: 8px;
  padding: 3px;
  font-size: 10px;
  width: 20px;
  height: 15px;
  text-align: center;
}
.age-ALL {
  background-color: var(--age-all-color);
}
.age-12 {
  background-color: var(--age-12-color);
}
.age-15 {
  background-color: var(--age-15-color);
}
.age-19 {
  background-color: var(--age-19-color);
}

/* 예매 상세 */
.booking-description {
  display: grid;
  grid-template-columns: 70% 30%;
  padding: 10px 0;
  border-bottom: 1px solid var(--border-color);
  margin-bottom: 10px;
}
.movie-startTime {
  margin-top: 20px;
  color: white;
}
.poster-img {
  width: 70px;
}

/* 좌석 상세 */
.seat-description {
  border: 1px solid var(--border-color);
  display: grid;
  grid-template-columns: 55% 45%;
}
.seat-guide {
  border-right: 1px solid var(--border-color);
  padding: 15px;
}
.selected-seat {
  text-align: center;
  padding: 15px;
}
.seat-numbers-container {
  display: grid;
  grid-template-columns: 50% 50%; /* 2개의 열 */
  grid-template-rows: repeat(4, 1fr); /* 4개의 행 */
  column-gap: 5px; /* 요소 간 간격 */
  row-gap: 10px;
  margin-top: 20px;
}
.seat-number {
  border: 1px solid var(--border-color);
  width: 40px;
  height: 35px;
}

.total-price-container {
  font-family: "SUIT-SemiBold";
  font-size: 15px;
  margin-top: 50px;
  color: white;
}
.total-price {
  float: right;
  line-height: 13px;
}
.total-price .number {
  color: var(--secondary-color);
  font-size: 23px;
  font-family: "SUIT-Bold";
}

/* 이전, 다음 버튼 */
.navigation-buttons {
  position: absolute;
  bottom: 0;
  width: 100%;
}
.navigation-buttons button {
  font-family: "SUIT-Medium";
  font-size: 18px;
  color: white;
  border: none;
  padding: 8px 0;
}
.navigation-buttons .prev-button {
  width: 50%;
  background-color: #53565b;
  border-bottom-left-radius: 10px;
}
.navigation-buttons .next-button {
  width: 50%;
  background-color: var(--secondary-color);
  border-bottom-right-radius: 10px;
}
</style>
