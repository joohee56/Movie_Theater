<template lang="ko">
  <div class="container">
		<div class="top-nav">
			<a>로그인</a>
			<a>회원가입</a>
		</div>
		<div class="main-nav">
			<router-link :to="{name: 'movie'}">영화</router-link>
			<a>예매</a>
			<router-link :to="{name: 'home'}" id="logo-link">
				<img src="@/assets/img/megabox-logo.png" class="logo">
			</router-link>
		</div>
	</div>
</template>

<script>
export default {};
</script>

<style scoped>
.container {
  padding-bottom: 10px;
}

/* top nav  */
.top-nav {
  font-family: IBM Plex Sans KR;
  font-size: 13px;
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
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
.main-nav a {
  margin-right: 20px;
  line-height: 2;
  padding-top: 0.5em;
}
#logo-link {
  display: block;
  margin: 0 auto;
  padding: 0;
}
.logo {
  display: block;
  width: 140px;
}
</style>
