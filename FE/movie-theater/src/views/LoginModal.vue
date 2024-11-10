<template lang="ko">
  <div v-if="isVisible" class="modal-overlay">
    <div class="modal-content">
      <div class="top">
        <div>로그인</div>
        <button class="close-btn" @click="close">X</button>
      </div>
      <div class="content">
        <div class="left-side">
          <div class="input-container">
            <input type="text" id="userId" v-model="userId" placeholder="아이디" required />
            <input type="password" id="password" v-model="password" placeholder="비밀번호" required />
          </div>
          <button class="login-btn" :disabled="isLoginDisabled">로그인</button>
        </div>
        <div class="ad">
          <img src="@/assets/img/ad-img.png"></img>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      userId: "",
      password: "",
    };
  },
  props: ["isVisible"],
  computed: {
    isLoginDisabled() {
      return this.userId.trim() === "" || this.password.trim() === "";
    },
  },
  methods: {
    close() {
      this.$emit("close");
    },
    handleSubmit() {
      // 로그인 처리 로직
      console.log("Username:", this.username);
      console.log("Password:", this.password);
      this.close(); // 로그인 완료 후 모달 닫기
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
  color: white;
  display: flex;
  padding: 10px 20px;
  font-size: 17px;
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
  color: white;
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
  color: white;
  margin-left: auto;
  font-family: "SUIT-Regular";
}
</style>
