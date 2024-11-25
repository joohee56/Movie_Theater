<template lang="ko">
	<div>
		<div class="poster-container">
			<img :src="movie.posterUrl" class="poster-img">
      <p>{{movie.title}}</p>
      <div class="overlay">
        <p>{{movie.description}}</p>
      </div>
		</div>
		<div>
			<button class="booking-btn">예매</button>
		</div>
	</div>
</template>

<script>
export default {
  props: ["movie"],
};
</script>

<style scoped>
p {
  margin: 0;
}
.poster-container {
  overflow: hidden;
  position: relative;
  display: inline-block;
  width: 100%;
  height: 100%;
}
.poster-img {
  aspect-ratio: 290/410;
  object-fit: fill;
  width: 100%;
}
.overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 95%;
  height: 86%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  padding: 10px;
  opacity: 0;
  transition: opacity 0.5s ease;
}

.poster-container:hover .poster-img {
  filter: blur(5px);
}
.poster-container:hover .overlay {
  opacity: 1; /* 오버레이 표시 */
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
