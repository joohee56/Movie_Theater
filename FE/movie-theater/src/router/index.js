import Vue from "vue";
import VueRouter from "vue-router";
import HomeView from "../views/HomeView.vue";
import MovieView from "../views/MovieView.vue";
import BookingView from "../views/BookingView.vue";
import BookingMovieOptions from "@/components/booking/BookingMovieOptions.vue";

Vue.use(VueRouter);

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
    ],
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
