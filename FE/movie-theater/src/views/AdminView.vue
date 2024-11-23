<template lang="ko">
	<div>
		<ul>
			<li><router-link :to="{name: 'createMovie'}">영화 등록</router-link></li>
			<li><router-link :to="{name: 'createTheater'}">영화관 등록</router-link></li>
			<li><router-link :to="{name: 'createHall'}">상영관 등록</router-link></li>
			<li><router-link :to="{name: 'createScreening'}">상영 시간 등록</router-link></li>
		</ul>
	</div>
</template>

<script>
import {} from "@/api/movie";

export default {};
</script>

<style scoped></style>
