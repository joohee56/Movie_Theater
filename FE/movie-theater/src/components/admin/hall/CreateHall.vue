<template lang="ko">
  <div>
		<div>상영관 등록</div>
		<div class="section">
			<div class="section-title">극장</div>
			<div class="theater-list">
				<div class="region-list">
					<button v-for="(region, index) in regions" @click="regionSelect(region.region, index)" :class="{regionSelected:selectedRegion.index==index}">
						{{region.regionDisplay}}({{region.count}})
					</button>
				</div>
				<div class="theater-detail-list">
					<button v-for="(theater, index) in theaters" @click="theaterSelect(theater.theaterId, index)" :class="{theaterSelected:selectedTheater.index==index}">
						{{theater.theaterName}}
					</button>
				</div>
				<div class="hall-list">
					<div v-for="(hall, index) in halls">
						{{hall.name}}
					</div>
				</div>
			</div>
		</div>
		<div>
			<div>상영관 이름</div>
			<div>
				<input type="text" v-model="hall.name">
			</div>
			<div>상영타입</div>
      <select v-model="hall.screeningType">
        <option value="TWO_D">2D</option>
        <option value="THREE_D">3D</option>
        <option value="IMAX">IMAX</option>
        <option value="FOUR_DX">4DX</option>
      </select>
			<div>추가 요금</div>
			<div>
				<input type="text" v-model="hall.hallTypeModifier">
			</div>
			<div>좌석 행 갯수</div>
			<div>
				<input type="text" v-model="hall.rows">
			</div>
			<div>좌석 열 갯수</div>
			<div>
				<input type="text" v-model="hall.columns">
			</div>
		</div>
		<button @click="submit">등록</button>
	</div>
</template>

<script>
import { getRegionsWithTheaterCount, getTheatersByRegion } from "@/api/theater";
import { createHallWithSeats, getHalls } from "@/api/hall";

export default {
  data() {
    return {
      hall: {
        theaterId: 0,
        name: "",
        screeningType: "",
        hallTypeModifier: 0,
        rows: 0,
        columns: 0,
      },
      regions: [],
      theaters: [],
      halls: [],
      selectedRegion: {
        index: -1,
        name: "",
      },
      selectedTheater: {
        index: -1,
        id: null,
      },
    };
  },
  mounted() {
    this.fetchRegions();
  },
  methods: {
    async fetchRegions() {
      const response = await getRegionsWithTheaterCount();
      if (response.code == 200) {
        this.regions = response.data;
      }
    },
    regionSelect(name, index) {
      this.selectedRegion.index = index;
      this.selectedRegion.name = name;

      this.resetTheater();
      this.fetchTheaters();
    },
    theaterSelect(theaterId, index) {
      this.selectedTheater.id = theaterId;
      this.selectedTheater.index = index;
      this.hall.theaterId = this.selectedTheater.id;

      this.fetchHalls();
    },
    async fetchTheaters() {
      const response = await getTheatersByRegion(this.selectedRegion.name);
      if (response.code == 200) {
        this.theaters = response.data;
      }
    },
    async fetchHalls() {
      const response = await getHalls(this.selectedTheater.id);
      if (response.code == 200) {
        this.halls = response.data;
      }
    },
    async submit() {
      console.log(this.hall);
      const response = await createHallWithSeats(this.hall);
      if (response.code == 200) {
        alert("상영관 생성 완료 " + response.data.hallId);
      }
      this.fetchHalls();
    },
    resetTheater() {
      this.selectedTheater.id = null;
      this.selectedTheater.index = -1;
    },
  },
};
</script>

<style scoped>
/* 극장 */
.theater-list {
  width: 300px;
  height: 100%;
  display: flex;
}
.theater-list .regionSelected {
  background-color: #ebebeb;
}
.region-list {
  width: 55%;
  border-right: 1px solid var(--border-line-color);
  height: 90%;
}
.theater-detail-list {
  width: 50%;
  margin-left: 10px;
}
.theater-list .theaterSelected {
  background-color: #666666;
  color: white;
}
</style>
