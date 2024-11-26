<template lang="ko">
  <div v-if="isJoinModalVisible" class="modal-overlay">
    <div class="modal-content">
      <div class="top">
        <div>회원가입</div>
        <button class="close-btn" @click="HIDE_JOIN_MODAL">X</button>
      </div>
      <div class="content">
        <div class="input-container">
          <input type="text" v-model="user.loginId" placeholder="아이디" required />
          <input type="password" v-model="user.password" placeholder="비밀번호" required />
          <input type="text" v-model="user.name" placeholder="이름" required />
          <input type="text" v-model="user.email" placeholder="이메일" required />
        </div>
        <button class="join-btn" :disabled="isJoinDisabled" @click="join">회원가입</button>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
import { join } from "@/api/user";

export default {
  data() {
    return {
      user: {
        loginId: "",
        password: "",
        name: "",
        email: "",
      },
    };
  },
  computed: {
    ...mapState(["isJoinModalVisible"]),
    isJoinDisabled() {
      return (
        this.user.loginId.trim() === "" ||
        this.user.password.trim() === "" ||
        this.user.name.trim() === "" ||
        this.user.email.trim() === ""
      );
    },
  },
  methods: {
    ...mapMutations(["HIDE_JOIN_MODAL", "SHOW_LOGIN_MODAL"]),
    close() {
      this.HIDE_JOIN_MODAL();
    },
    async join() {
      const response = await join(this.user);
      if (response.code == 200) {
        alert(response.data.name + "님 반갑습니다.");
        this.close();
        this.SHOW_LOGIN_MODAL();
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
  width: 500px;
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
  column-gap: 50px;
  padding: 30px;
}
.content .input-container {
  margin: 0 auto;
  margin-bottom: 30px;
  width: 70%;
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
.content .join-btn {
  border: none;
  padding: 15px 0;
  font-size: 15px;
  border-radius: 5px;
  font-family: "SUIT-Medium";
  background-color: var(--primary-color);
  width: 50%;
  display: block;
  margin: 0 auto;
}
.content .join-btn:disabled {
  background-color: #e0e0e0;
  color: gray;
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
