<template lang="ko">
  <div :class="homeClass">
		<div class="top-nav">
      <button v-if="isAuthenticated" @click="logout">로그아웃</button>
      <button v-else @click="SHOW_LOGIN_MODAL">로그인</button>
      <LoginModal @checkAuthStatus="checkAuthStatus" />
			<button>회원가입</button>
		</div>
		<div class="main-nav">
      <div class="left-side">
        <router-link :to="{name: 'movie'}">영화</router-link>
        <router-link :to="{name: 'booking'}">예매</router-link>
      </div>
			<router-link :to="{name: 'home'}">
        <img v-if="this.$route.path=='/'" src="@/assets/img/logo-sample2.png" class="logo-img">
        <img v-else src="@/assets/img/logo-sample.png" class="logo-img">
			</router-link>
      <router-link :to="{name: 'myPage'}" class="user-link">
        <i class="fa-solid fa-user"></i>
			</router-link>
		</div>
	</div>
</template>

<script>
import { mapMutations } from "vuex";
import { logout } from "@/api/user";
import LoginModal from "@/views/LoginModal.vue";

export default {
  components: { LoginModal },
  data() {
    return {
      showLoginModal: false,
      isAuthenticated: false,
    };
  },
  computed: {
    homeClass() {
      return this.$route.path == "/" ? "home" : "default";
    },
  },
  mounted() {
    this.checkAuthStatus();
    window.addEventListener("storage", this.checkAuthStatus);
  },
  beforeUnmount() {
    window.removeEventListener("storage", this.checkAuthStatus);
  },
  methods: {
    ...mapMutations(["SHOW_LOGIN_MODAL"]),
    checkAuthStatus() {
      const authStatus = localStorage.getItem("isAuthenticated");
      this.isAuthenticated = authStatus === "true";
    },
    async logout() {
      const response = await logout();
      if (response.status == 200) {
        localStorage.removeItem("isAuthenticated");
        this.checkAuthStatus();
      }
    },
  },
};
</script>

<style scoped>
.home a,
.home button {
  color: white;
}
/* top nav  */
.top-nav {
  font-family: IBM Plex Sans KR;
  font-size: 13px;
  display: flex;
  justify-content: flex-end;
  margin-bottom: 10px;
  padding-top: 15px;
}
.top-nav button {
  margin-left: 20px;
  border: none;
  cursor: pointer;
  background: none;
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
  position: fixed;
  left: 50%;
  transform: translate(-50%, -50%);
  margin-top: 10px;
  font-family: "Cafe24Dangdanghae";
  font-size: 20px;
}
.logo-img {
  position: fixed;
  left: 50%;
  transform: translate(-50%, -50%);
  margin-top: 10px;
  width: 170px;
}
.user-link {
  margin-left: auto;
  margin-top: 10px;
  margin-right: 5px;
}
.user-img {
  width: 20px;
  padding-top: 0.7rem;
}
</style>
