<template lang="ko">
	<div class="container">
		<div>
			<img src="@/assets/img/no-poster-img.png" class="poster">
		</div>
		<div class="movie-title-container">
			<div :class="[ageClass, `age-rating`]">
				{{movie.ageRatingDisplay}}
			</div>
			<div class="movie-title">
				{{movie.title}}
			</div>
		</div>
		<div class="movie-releaseDate">
			개봉일 {{movie.releaseDate}}
		</div>
		<div>
			<button class="booking-btn">예매</button>
		</div>
	</div>
</template>

<script>
export default {
  props: ["movie"],
  computed: {
    ageClass() {
      return "age-" + this.movie.ageRatingDisplay;
    },
  },
};
</script>

<style scoped>
.container {
  font-family: "IBM Plex Sans KR", sans-serif;
}
.poster {
  aspect-ratio: 290/410;
  object-fit: fill;
  width: 100%;
}

.movie-title-container {
  margin: 10px 0;
}
.movie-title {
  display: inline;
  font-size: 20px;
}
.age-rating {
  display: inline;
  font-family: "S-CoreDream-6Bold";
  color: white;
  border-radius: 4px;
  margin-right: 8px;
  padding: 2px 1px 2px 3px;
  font-size: 14px;
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
.movie-releaseDate {
  font-size: 15px;
  margin: 10px 0;
}
.booking-btn {
  background-color: var(--primary-color);
  border-radius: 4px;
  width: 100%;
  font-size: 17px;
  padding: 10px 0;
  font-family: "SUIT-Medium";
  border: none;
}
</style>
