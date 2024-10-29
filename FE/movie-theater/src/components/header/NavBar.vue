<template lang="ko">
  <div class="container">
		<div class="top-nav">
			<a>로그인</a>
			<a>회원가입</a>
		</div>
		<div class="main-nav">
			<a>영화</a>
			<a>예매</a>
			<img src="@/assets/megabox-logo.png" class="logo">
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
.main-nav {
  font-family: SUIT-SemiBold;
  font-size: 19px;
  display: flex;
}
.main-nav a {
  margin-right: 20px;
  text-align: end;
  line-height: 2;
  padding-top: 0.5em;
}
.logo {
  width: 140px;
  margin: 0 auto;
  display: block;
}
</style>
