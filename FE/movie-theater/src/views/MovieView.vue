<template lang="ko">
  <div>
    <div class="page-title">
      전체영화
    </div>
    <div class="postercard-container">
      <PosterCard v-for="movie in movies" :key="movie.id" :movie=movie></PosterCard>
    </div>
  </div>
</template>

<script>
import { getAllMovies } from "@/api/movie";
import PosterCard from "@/components/movie/PosterCard";

export default {
  data() {
    return {
      movies: [],
    };
  },
  components: {
    PosterCard,
  },
  mounted() {
    this.fetchMovies();
  },
  methods: {
    async fetchMovies() {
      const response = await getAllMovies();
      if (response.status == 200) {
        this.movies = response.data.data;
      }
      console.log(response);
    },
  },
};
</script>

<style scoped>
.page-title {
  font-family: SUIT-Medium;
  font-size: 30px;
  margin: 25px 0;
}
.postercard-container {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 60px;
}
</style>
