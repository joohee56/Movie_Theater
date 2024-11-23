<template lang="ko">
	<div>
		영화관 등록
		<div>
			<div>영화관 이름</div>
			<div>
				<input type="text" v-model="theater.name">
			</div>
			<div>주소</div>
			<div>
				<input type="text" v-model="theater.address">
			</div>
			<div>지역</div>
			<div>
				<select v-model="theater.region">
					<option value="SEOUL">서울</option>
					<option value="GYEONGGI">경기</option>
					<option value="INCHEON">인천</option>
					<option value="DAEJEON_CHUNGCHEONG_SEJONG">대전/충청/세종</option>
					<option value="BUSAN_DAEGU_GYEONGSANG">부산/대구/경상</option>
					<option value="GWANGJU_JEONLLA">광주/전라</option>
					<option value="GANGWON">강원</option>
					<option value="JEJU">제주</option>
				</select>
			</div>
			<div>위도</div>
			<div>
				<input type="text" v-model="theater.latitude">
			</div>
			<div>경도</div>
			<div>
				<input type="text" v-model="theater.longitude">
			</div>
			<div>전화번호</div>
			<div>
				<input type="text" v-model="theater.contactNumber">
			</div>
		</div>
		<button @click="submit">등록</button>
	</div>
</template>

<script>
import { createTheater } from "@/api/theater";

export default {
  data() {
    return {
      theater: {
        name: "",
        address: "",
        region: "",
        latitude: 0,
        longitude: 0,
        contactNumber: "",
      },
    };
  },
  methods: {
    async submit() {
      console.log(this.theater);
      const response = await createTheater(this.theater);
      console.log(response);
    },
  },
};
</script>

<style scoped></style>
