import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    isLoginModalVisible: false,
  },
  getters: {},
  mutations: {
    SHOW_LOGIN_MODAL(state) {
      state.isLoginModalVisible = true;
    },
    HIDE_LOGIN_MODAL(state) {
      state.isLoginModalVisible = false;
    },
  },
  actions: {},
  modules: {},
});
