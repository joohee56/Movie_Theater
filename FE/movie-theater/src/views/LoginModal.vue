<template lang="ko">
  <div v-if="isLoginModalVisible" class="modal-overlay">
    <div class="modal-content">
      <div class="top">
        <div>로그인</div>
        <button class="close-btn" @click="HIDE_LOGIN_MODAL">X</button>
      </div>
      <div class="content">
        <div class="left-side">
          <div class="input-container">
            <input type="text" id="userId" v-model="userId" placeholder="아이디" required />
            <input type="password" id="password" v-model="password" placeholder="비밀번호" required />
          </div>
          <button class="login-btn" :disabled="isLoginDisabled" @click="login">로그인</button>
        </div>
        <div class="ad">
          <img src="@/assets/img/ad-img.png"></img>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
import { login } from "@/api/user";

export default {
  data() {
    return {
      userId: "",
      password: "",
    };
  },
  computed: {
    ...mapState(["isLoginModalVisible"]),
    isLoginDisabled() {
      return this.userId.trim() === "" || this.password.trim() === "";
    },
  },
  methods: {
    ...mapMutations(["HIDE_LOGIN_MODAL"]),
    close() {
      this.HIDE_LOGIN_MODAL();
    },
    async login() {
      const response = await login(this.userId, this.password);
      console.log(response);
      if (response.status == 200) {
        localStorage.setItem("isAuthenticated", true);
        this.$emit("checkAuthStatus");
        this.close();
      }
    },
  },
};
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  background: white;
  width: 800px;
  position: relative;
  font-family: "SUIT-Regular";
}
.modal-content .top {
  background-color: var(--primary-color);
  display: flex;
  padding: 12px 20px;
  font-size: 17px;
  font-family: "SUIT-Regular";
}
.modal-content .content {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  column-gap: 50px;
  padding: 20px;
}
.content .left-side {
  width: 100%;
}
.left-side .input-container {
  margin-bottom: 20px;
}
.content input {
  border: 1px solid var(--border-line-color);
  display: block;
  margin-bottom: 12px;
  height: 45px;
  padding: 0 10px;
  width: 100%;
  box-sizing: border-box;
  outline: none;
  font-family: "SUIT-Regular";
}
.content input:focus {
  border: 2px solid black;
}
.content .login-btn {
  width: 100%;
  border: none;
  padding: 15px 0;
  font-size: 15px;
  border-radius: 5px;
  font-family: "SUIT-Medium";
  background-color: var(--primary-color);
}
.content .login-btn:disabled {
  background-color: #e0e0e0;
  color: gray;
}
.content .ad {
  width: 370px;
}
.content .ad img {
  width: 100%;
}

.close-btn {
  /* position: absolute;
  top: 10px;
  right: 10px; */
  background: none;
  border: none;
  font-size: 18px;
  cursor: pointer;
  margin-left: auto;
  font-family: "SUIT-SemiBold";
}
</style>
