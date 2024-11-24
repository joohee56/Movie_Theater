<template lang="ko">
	<div class="container">
		<div class="movies-container">
			<div>
				<span class="box-office-text">박스오피스</span>
				<router-link to="#" class="more-movie-link">더 많은 영화보기 + </router-link>
			</div>
			<div class="movie-card-container">
				<div v-for="movie in movies" :key="movie.movieId">
					<MovieCard :movie="movie"></MovieCard>
				</div>
			</div>
		</div>
		<div class="moving-mouse">
			<i class="fa-solid fa-computer-mouse"></i>
		</div>
	</div>
</template>

<script>
import { getRecentMovies } from "@/api/movie";
import MovieCard from "@/components/home/MovieCard.vue";

export default {
  data() {
    return {
      movies: [],
    };
  },
  components: { MovieCard },
  mounted() {
    this.fetchMovies();
  },
  methods: {
    async fetchMovies() {
      const response = await getRecentMovies();
      this.movies = response.data.data;
      console.log(this.movies);
    },
  },
};
</script>

<style scoped>
.container {
  height: 100vh;
}
.movies-container {
  color: white;
  font-family: "IBM Plex Sans KR", sans-serif;
  margin-top: 100px;
}
.box-office-text {
  position: fixed;
  transform: translate(-50%, -50%);
  left: 50%;
}
.more-movie-link {
  float: right;
  font-size: 13px;
  margin-bottom: 15px;
}
.movie-card-container {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  column-gap: 40px;
  row-gap: 30px;
  width: 100%;
  margin-bottom: 70px;
}
@keyframes moveUpDown {
  0% {
    transform: translateY(-15px);
  }
  50% {
    transform: translateY(0px);
  }
  100% {
    transform: translateY(-15px);
  }
}
.moving-mouse {
  color: white;
  animation: moveUpDown 2s infinite ease-in-out;
  font-size: 18px;
  text-align: center;
}
</style>
