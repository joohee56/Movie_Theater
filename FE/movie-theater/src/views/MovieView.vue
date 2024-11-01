<template lang="ko">
  <div>
    <PageTitle pageTitle="전체영화"></PageTitle>
    <div class="postercard-container">
      <PosterCard v-for="movie in movies" :key="movie.id" :movie=movie></PosterCard>
    </div>
  </div>
</template>

<script>
import { getMovies } from "@/api/movie";
import PageTitle from "@/components/header/PageTitle";
import PosterCard from "@/components/movie/PosterCard";

export default {
  data() {
    return {
      movies: [],
    };
  },
  components: {
    PosterCard,
    PageTitle,
  },
  mounted() {
    this.fetchMovies();
  },
  methods: {
    async fetchMovies() {
      const response = await getMovies();
      if (response.status == 200) {
        this.movies = response.data.data;
      }
      console.log(response);
    },
  },
};
</script>

<style scoped>
.postercard-container {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 60px;
}
</style>
