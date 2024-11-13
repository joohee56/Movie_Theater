import Vue from "vue";
import VueRouter from "vue-router";
import HomeView from "../views/HomeView.vue";
import MovieView from "../views/MovieView.vue";
import BookingView from "../views/BookingView.vue";
import BookingMovieOptions from "@/components/booking/BookingMovieOptions.vue";
import BookingSeat from "@/components/booking/BookingSeat.vue";
import BookingSuccess from "@/views/BookingSuccess.vue";
import MyPage from "@/views/MyPage.vue";
import BookingHistory from "@/components/mypage/BookingHistory.vue";
import store from "@/store/index.js";
import PaymentView from "@/views/PaymentView";

Vue.use(VueRouter);

const checkAuthenticate = (to, from, next) => {
  const isAuthenticated = localStorage.getItem("isAuthenticated") === "true";

  if (!isAuthenticated) {
    store.commit("SHOW_LOGIN_MODAL");
    next(false);
  } else {
    next();
  }
};

const routes = [
  {
    path: "/",
    name: "home",
    component: HomeView,
  },
  {
    path: "/movie",
    name: "movie",
    component: MovieView,
  },
  {
    path: "/booking",
    name: "booking",
    component: BookingView,
    redirect: "/booking/movieOptions",
    children: [
      {
        path: "/booking/movieOptions",
        name: "bookingMovieOptions",
        component: BookingMovieOptions,
      },
      {
        path: "/booking/seat",
        name: "bookingSeat",
        beforeEnter: checkAuthenticate,
        component: BookingSeat,
      },
    ],
  },
  {
    path: "/booking/payment",
    name: "paymentView",
    component: PaymentView,
  },
  {
    path: "/booking/success/:bookingId",
    name: "bookingSuccess",
    component: BookingSuccess,
  },
  {
    path: "/mypage",
    name: "myPage",
    component: MyPage,
    redirect: "/mypage/bookingHistory",
    children: [
      {
        path: "/mypage/bookingHistory",
        name: "bookingHistory",
        component: BookingHistory,
      },
    ],
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
