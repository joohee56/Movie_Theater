<template lang="ko">
	<div>
		상영시간 등록
		<div>
			<div>영화 선택</div>
      <select v-model="screening.movieId">
        <option :value="movie.id" v-for="movie in movies">{{movie.title}}</option>
      </select>
			<div>상영관 선택</div>
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
						<button v-for="(hall, index) in halls" @click="hallSelect(hall.id, index)" :class="{hallSelected:selectedHall.index==index}">
							{{hall.name}}
						</button>
					</div>
				</div>
			</div>
			<div>
				<div>상영 시간</div>
				<div>
					<input type="text" v-model="screening.startTime">
				</div>
			</div>
		</div>
		<button @click="submit">등록</button>
	</div>
</template>

<script>
import { getRegionsWithTheaterCount, getTheatersByRegion } from "@/api/theater";
import { getHalls } from "@/api/hall";
import { getMovies } from "@/api/movie";
import { createScreening } from "@/api/screening";

export default {
  data() {
    return {
      screening: {
        movieId: null,
        hallId: null,
        startTime: "",
      },
      movies: [],
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
      selectedHall: {
        index: -1,
        id: null,
      },
    };
  },
  mounted() {
    this.fetchMovies();
    this.fetchRegions();
  },
  methods: {
    async fetchMovies() {
      const response = await getMovies();
      if (response.code == 200) {
        this.movies = response.data;
      }
    },
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

      this.fetchHalls();
    },
    hallSelect(hallId, index) {
      this.selectedHall.id = hallId;
      this.selectedHall.index = index;
      this.screening.hallId = hallId;
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
      console.log(this.screening);
      const response = await createScreening(this.screening);
      if (response.code == 200) {
        alert("상영시간 생성 완료 " + response.data.id);
      }
    },
    resetTheater() {
      this.selectedTheater.id = null;
      this.selectedTheater.index = -1;
    },
    resetHall() {
      this.selectedHall.id = null;
      this.selectedHall.index = -1;
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
.theater-list .theaterSelected,
.theater-list .hallSelected {
  background-color: #666666;
  color: white;
}
</style>
