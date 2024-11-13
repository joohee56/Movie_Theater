<template lang="ko">
	<div>
		<div>
			<img src="@/assets/img/no-poster-img.png" class="poster-img">
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
.poster-img {
  aspect-ratio: 290/410;
  object-fit: fill;
  width: 100%;
  margin-bottom: 10px;
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
