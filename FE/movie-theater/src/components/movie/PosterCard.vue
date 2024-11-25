<template lang="ko">
	<div class="container">
		<div class="poster-container">
			<img :src="movie.posterUrl" class="poster-img">
      <div class="overlay">
        <p>{{movie.description}}</p>
      </div>
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
.poster-container {
  overflow: hidden;
  position: relative;
  display: inline-block;
}
.poster-img {
  aspect-ratio: 290/410;
  object-fit: fill;
  width: 100%;
  border-radius: 5px;
}
.overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 83%;
  height: 98%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  padding: 0 20px;
  opacity: 0;
  transition: opacity 0.5s ease;
  color: white;
  border-radius: 5px;
}
.poster-container:hover .poster-img {
  filter: blur(5px);
}
.poster-container:hover .overlay {
  opacity: 1; /* 오버레이 표시 */
}
.movie-title-container {
  margin: 10px 0;
}
.movie-title {
  display: inline;
  font-size: 17px;
}
.age-rating {
  display: inline;
  font-family: "S-CoreDream-6Bold";
  color: white;
  border-radius: 4px;
  margin-right: 8px;
  padding: 2px 1px 2px 3px;
  font-size: 12px;
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
  font-size: 13px;
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
