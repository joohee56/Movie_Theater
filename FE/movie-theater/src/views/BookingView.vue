<template lang="ko">
	<div>
		<PageTitle pageTitle="빠른예매"></PageTitle>
		<div class="date-picker-container">
			<date-picker v-model="selectedDate" format="YYYY.MM.DD" />
		</div>
		<div class="section-container">
			<div class="section">
				<div class="section-title">영화</div>
				<div class="movie-list">
					<button v-for="(movie, index) in movies" @click="movieSelect(movie.id, index)" :class="{movieSelected:selectedMovie.index==index}">
						<span :class="[ageClass(movie.ageRatingDisplay), `age-rating`]">{{movie.ageRatingDisplay}}</span>{{movie.title}}
					</button>
				</div>
			</div>
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
							{{theater.theaterName}}({{theater.screeningCount}})
						</button>
					</div>
				</div>
			</div>
			<div class="section">
				<div class="section-title">시간</div>
        <div v-if="screenings && screenings.length <= 0" class="screening-list-empty">
          <div class="content">
            <img src="@/assets/img/screening-list-empty.png"></img>
            <div class="description">영화와 극장을 선택하시면</br>상영시간표를 비교하여 볼 수 있습니다.</div>
          </div>
        </div>
				<div v-else class="screening-list">
          <button v-for="(screening, index) in screenings">
            <div class="time-info">
              <div class="start-time">{{screening.startTime}}</div>
              <div class="end-time">~ {{screening.endTime}}</div>
            </div>
            <div class="movie-info">
              <div class="title">{{screening.movieTitle}}</div>
              <div class="screening-type">{{screening.screeningTypeDisplay}}</div>
            </div>
            <div class="hall-info">
              <div>{{screening.theaterName}}</div>
              <div>{{screening.hallName}}</div>
            </div>
          </button>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import "vue2-datepicker/index.css";
import "vue2-datepicker/locale/ko";
import DatePicker from "vue2-datepicker";
import PageTitle from "@/components/header/PageTitle";
import { getMovies } from "@/api/movie";
import {
  getRegionsWithTheaterCount,
  getScreenings,
  getTheaterAndScreeningCounts,
} from "@/api/screening";

export default {
  data() {
    return {
      selectedDate: new Date(),
      formatDate: null,
      movies: [],
      regions: [],
      theaters: [],
      screenings: [],
      selectedMovie: {
        index: -1,
        id: null,
      },
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
  watch: {
    selectedDate() {
      this.setFormatDate();
      this.fetchAll();
    },
  },
  created() {
    this.setFormatDate();
  },
  components: {
    PageTitle,
    DatePicker,
  },
  mounted() {
    this.fetchMovies();
    this.fetchRegionsWithTheaterCount();
  },
  methods: {
    async fetchMovies() {
      const response = await getMovies();
      if (response.status == 200) {
        this.movies = response.data.data;
      }
    },
    async fetchRegionsWithTheaterCount() {
      const response = await getRegionsWithTheaterCount(
        this.selectedMovie.id,
        this.formatDate
      );
      if (response.status == 200) {
        this.regions = response.data.data;
      }
    },
    async fetchTheaters() {
      const response = await getTheaterAndScreeningCounts(
        this.formatDate,
        this.selectedMovie.id,
        this.selectedRegion.name
      );
      if (response.status == 200) {
        this.theaters = response.data.data;
      }
    },
    async fetchScreening() {
      const response = await getScreenings(
        this.selectedMovie.id,
        this.selectedTheater.id,
        this.formatDate
      );
      if (response.status == 200) {
        this.screenings = response.data.data;
      }
      console.log(response);
    },
    async fetchAll() {
      this.fetchRegionsWithTheaterCount();
      this.fetchTheaters();
      this.fetchScreening();
    },
    movieSelect(movieId, index) {
      if (this.selectedMovie.id != movieId) {
        this.selectedMovie.id = movieId;
        this.selectedMovie.index = index;
      } else {
        this.resetMovie();
      }
      this.fetchAll();
    },
    regionSelect(name, index) {
      if (this.selectedRegion.name != name) {
        this.selectedRegion.index = index;
        this.selectedRegion.name = name;
      } else {
        this.resetRegion();
      }

      this.resetTheater();
      this.fetchAll();
    },
    theaterSelect(theaterId, index) {
      if (this.selectedTheater.id != theaterId) {
        this.selectedTheater.id = theaterId;
        this.selectedTheater.index = index;
      } else {
        this.resetTheater();
      }

      this.fetchAll();
    },
    setFormatDate() {
      const tzOffset = this.selectedDate.getTimezoneOffset() * 60 * 1000;
      this.formatDate = new Date(this.selectedDate - tzOffset)
        .toISOString()
        .split("T")[0];
    },
    ageClass(ageRatingDisplay) {
      return "age-" + ageRatingDisplay;
    },
    resetMovie() {
      this.selectedMovie.id = null;
      this.selectedMovie.index = -1;
    },
    resetRegion() {
      this.selectedRegion.index = -1;
      this.selectedRegion.name = "";
    },
    resetTheater() {
      this.selectedTheater.index = -1;
      this.selectedTheater.id = null;
    },
  },
};
</script>

<style scoped>
.date-picker-container {
  margin: 50px 0 30px;
}
.section-container {
  display: table;
  border-collapse: collapse;
}
.section {
  border: 1px solid var(--border-line-color);
  border-top: 1px solid black;
  border-bottom: 1px solid black;
  padding: 20px;
  height: 500px;
  display: table-cell;
}
.section-title {
  font-family: "SUIT-Regular";
  font-size: 20px;
  margin-bottom: 20px;
}

/* 영화 */
.movie-list {
  width: 250px;
}
.movie-list button,
.theater-list button,
.screening-list button {
  background: white;
  border: none;
  display: block;
  padding: 5px;
  width: 100%;
  text-align: left;
}
.age-rating {
  display: inline;
  font-family: "S-CoreDream-6Bold";
  color: white;
  border-radius: 3px;
  margin-right: 4px;
  padding: 1px 4px 2px 3px;
  font-size: 10px;
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
.movie-list .movieSelected,
.theater-list .theaterSelected {
  background-color: #666666;
  color: white;
}

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
  width: 50%;
  border-right: 1px solid var(--border-line-color);
  height: 90%;
}
.theater-detail-list {
  width: 50%;
}

/* 시간 */
.screening-list-empty {
  width: 400px;
  text-align: center;
  height: 100%;
}
.screening-list-empty .content {
  font-family: "Gothic A1", sans-serif;
  font-size: 15px;
  margin-top: 150px;
}
.screening-list-empty img {
  width: 50px;
}
.screening-list {
  width: 400px;
}
.screening-list .time-info {
  margin-left: 10px;
}
.time-info .start-time {
  font-size: 18px;
  font-family: "SUIT-Bold";
}
.time-info .end-time {
  font-size: 13px;
  font-family: "SUIT-Regular";
  margin-top: 3px;
}

.movie-info .title {
  font-family: "SUIT-Medium";
  font-size: 16px;
}
.movie-info .screening-type {
  font-family: "SUIT-Regular";
  margin-top: 5px;
}
.screening-list .hall-info {
  font-family: "SUIT-Regular";
  margin-top: 5px;
  text-align: right;
}
.screening-list button {
  border-bottom: 1px solid var(--border-line-color);
  display: grid;
  grid-template-columns: 17% 65% 20%;
  padding: 15px 10px;
}
.screening-list button:first-child {
  border-top: 1px solid var(--border-line-color);
}
</style>
