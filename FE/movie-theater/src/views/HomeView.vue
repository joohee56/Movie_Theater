<template lang="ko">
	<div class="container">
    <div class="moving-mouse">
			<i class="fa-solid fa-computer-mouse"></i>
		</div>
		<div class="movies-container">
			<div class="box-office-text">박스오피스</div>
			<div>
				<router-link :to="{name: 'movie'}" class="more-movie-link">더 많은 영화보기 + </router-link>
			</div>
			<div class="movie-card-container">
				<div v-for="movie in movies" :key="movie.movieId">
					<MovieCard :movie="movie"></MovieCard>
				</div>
			</div>
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
      if (response.code == 200) {
        this.movies = response.data;
      }
    },
  },
};
</script>

<style scoped>
.container {
  height: 100vh;
}
.moving-mouse {
  color: white;
  animation: moveUpDown 2s infinite ease-in-out;
  font-size: 18px;
  text-align: center;
  margin-top: 100px;
  margin-bottom: 20px;
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
.movies-container {
  color: white;
  font-family: "IBM Plex Sans KR", sans-serif;
}
.box-office-text {
  font-size: 17px;
  font-family: "SUIT-Medium";
  text-align: center;
}
.more-movie-link {
  color: white;
  float: right;
  font-size: 15px;
  margin-bottom: 15px;
}
.movie-card-container {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  column-gap: 40px;
  row-gap: 70px;
  width: 100%;
  margin-bottom: 70px;
}
</style>
