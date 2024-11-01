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
					<button v-for="(region, index) in regions">
						{{region.region}}({{region.count}})
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
import { getAllMovies } from "@/api/movie";
import { getAllRegionsAndTheaterCount } from "@/api/theater";

export default {
  data() {
    return {
      selectedDate: null,
      movies: [],
      regions: [],
      selectedMovie: {
        index: -1,
        id: -1,
      },
    };
  },
  components: {
    PageTitle,
    DatePicker,
  },
  mounted() {
    this.fetchMovies();
    this.fetchRegions();
  },
  methods: {
    async fetchMovies() {
      const response = await getAllMovies();
      if (response.status == 200) {
        this.movies = response.data.data;
      }
    },
    async fetchRegions() {
      const response = await getAllRegionsAndTheaterCount();
      if (response.status == 200) {
        this.regions = response.data.data;
      }
      console.log(response.data);
    },
    movieSelect(movieId, index) {
      this.selectedMovie.id = movieId;
      this.selectedMovie.index = index;
    },
    ageClass(ageRatingDisplay) {
      return "age-" + ageRatingDisplay;
    },
  },
};
</script>

<style scoped>
.date-picker-container {
  margin: 20px 0;
}
.section-container {
  display: table;
  border-collapse: collapse;
}
.section {
  border: 1px solid var(--border-line-color);
  padding: 20px;
  height: 500px;
  display: table-cell;
}
.section-title {
  font-family: "SUIT-Regular";
  font-size: 20px;
  margin-bottom: 20px;
}
.movie-list {
  width: 250px;
}
.movie-list button,
.theater-list button {
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
.movie-list .movieSelected {
  background-color: #666666;
  color: white;
}
.theater-list {
  width: 300px;
}
</style>
