<template lang="ko">
  <div class="container">
		<div class="top-nav">
			<a>로그인</a>
			<a>회원가입</a>
		</div>
		<div class="main-nav">
      <div class="left-side">
        <router-link :to="{name: 'movie'}">영화</router-link>
        <router-link :to="{name: 'booking'}">예매</router-link>
      </div>
			<router-link :to="{name: 'home'}">
				<img src="@/assets/img/megabox-logo.png" class="logo">
			</router-link>
      <router-link :to="{name: 'myPage'}" class="user-link">
				<img src="@/assets/img/user.png" class="user-img">
			</router-link>
		</div>
	</div>
</template>

<script>
export default {};
</script>

<style scoped>
/* top nav  */
.top-nav {
  font-family: IBM Plex Sans KR;
  font-size: 13px;
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
  margin-bottom: 10px;
}
.top-nav a {
  margin-left: 20px;
}

/* main nav */
.main-nav {
  font-family: SUIT-SemiBold;
  font-size: 19px;
  display: flex;
}
.main-nav .left-side a {
  margin-right: 20px;
  line-height: 2;
}
.logo {
  position: fixed; /* 화면을 기준으로 고정 */
  left: 50%;
  transform: translate(-50%, -50%); /* 요소를 정확히 중앙에 배치 */
  z-index: 1000; /* 다른 요소 위에 오도록 설정 */
  width: 140px;
  margin-top: 10px;
}
.user-link {
  margin-left: auto;
}
.user-img {
  width: 20px;
  padding-top: 0.7rem;
}
</style>
