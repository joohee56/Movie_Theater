<template lang="ko">
  <div>새 영화 등록
		<div>
			<div>
				<div>영화 제목</div>
				<div>
					<input type="text" v-model="movie.title">
				</div>
			</div>
			<div>
				<div>부제</div>
				<div>
					<input type="text" v-model="movie.subTitle">
				</div>
			</div>
			<div>
				<div>줄거리</div>
				<div>
					<textarea v-model="movie.description"></textarea>
				</div>
			</div>
			<div>
				<div>개봉일</div>
				<div>
					<input type="text" v-model="movie.releaseDate">
				</div>
			</div>
			<div>
				<div>상영시간</div>
				<div>
					<input type="text" v-model="movie.durationMinutes">
				</div>
			</div>
			<div>
				<div>포스터 이미지</div>
				<input type="file" ref="poster" @change="selectPosterImage">
				<img v-if="posterPreview != ''" :src="posterPreview">
			</div>
			<div>
				<div>영화 관람 등급</div>
				<select v-model="movie.ageRating">
					<option value="ALL">전체 관람가</option>
					<option value="AGE_12">12세 이상 관람가</option>
					<option value="AGE_15">15세 이상 관람가</option>
					<option value="AGE_19">청소년 관람 불가</option>
				</select>
			</div>
			<div>
				<div>감독</div>
				<div>
					<input type="text" v-model="movie.director">
				</div>
			</div>
			<div>
				<div>상영타입</div>
				<select v-model="movie.screeningType">
					<option value="TWO_D">2D</option>
					<option value="THREE_D">3D</option>
					<option value="IMAX">IMAX</option>
					<option value="FOUR_DX">4DX</option>
				</select>
			</div>
			<div>
				<div>기본 가격</div>
				<div>
					<input type="text" v-model="movie.standardPrice">
				</div>
			</div>
			<div>
				<div>장르</div>
				<select v-model="movie.genreTypes">
					<option value="ACTION">액션</option>
					<option value="COMEDY">코미디</option>
					<option value="DRAMA">드라마</option>
					<option value="HORROR">공포</option>
					<option value="THRILLER">스릴러</option>
					<option value="ROMANCE">로맨스</option>
					<option value="SF">SF</option>
					<option value="DOCUMENTARY">다큐멘터리</option>
				</select>
			</div>
			<div>
				<div>배우</div>
				<div>
					<input type="text" v-model="movie.actors">
				</div>
			</div>
		</div>
		<button @click="submit">등록</button>
	</div>
</template>

<script>
import { createMovie } from "@/api/movie";

export default {
  data() {
    return {
      movie: {
        title: "",
        subTitle: "",
        description: "",
        releaseDate: "",
        durationMinutes: 0,
        posterImage: "",
        ageRating: "",
        director: "",
        screeningType: "",
        standardPrice: "",
        genreTypes: "",
        actors: "",
      },
      posterPreview: "",
    };
  },
  methods: {
    selectPosterImage() {
      this.movie.posterImage = this.$refs.poster.files[0];
      this.posterPreview = URL.createObjectURL(this.movie.posterImage);
    },
    async submit() {
      var formData = new FormData();
      this.addObjectFormData(formData, this.movie);

      const response = await createMovie(formData);
      if (response.code == 200) {
        alert("완료되었습니다. 아이디:" + response.data.id);
      }
    },
    addObjectFormData(formData, movie) {
      for (const key in movie) {
        formData.append(key, movie[key]);
      }
    },
  },
};
</script>

<style scoped></style>
