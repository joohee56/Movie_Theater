import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    isLoginModalVisible: false,
    isJoinModalVisible: false,
  },
  getters: {},
  mutations: {
    SHOW_LOGIN_MODAL(state) {
      state.isLoginModalVisible = true;
    },
    HIDE_LOGIN_MODAL(state) {
      state.isLoginModalVisible = false;
    },
    SHOW_JOIN_MODAL(state) {
      state.isJoinModalVisible = true;
    },
    HIDE_JOIN_MODAL(state) {
      state.isJoinModalVisible = false;
    },
  },
  actions: {},
  modules: {},
});
