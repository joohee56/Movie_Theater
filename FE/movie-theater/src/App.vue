<template>
  <div id="app" :class="backgroundClass">
    <div :class="['navbar-container', navBackgroundClass]">
      <NavBar></NavBar>
    </div>
    <hr class="divider" />
    <div class="main-routerview-container">
      <router-view />
    </div>
  </div>
</template>

<script>
import NavBar from "./components/header/NavBar.vue";

export default {
  components: {
    NavBar,
  },
  computed: {
    backgroundClass() {
      return this.$route.path == "/" ? "home" : "default";
    },
    navBackgroundClass() {
      return this.$route.path == "/" ? "nav-home" : "default-nav";
    },
  },
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}
body {
  margin: 0;
}
.home {
  background-color: #1f120f;
}
.nav-home {
  background-color: #180f0d;
}

.navbar-container,
.main-routerview-container {
  max-width: var(--max-width);
  margin: 0 auto;
}

.divider {
  border-bottom: 1px solid var(--secondary-color);
  transform: scaleY(0.5); /* 0.5배로 축소 */
  transform-origin: top;
}

a {
  color: black;
  text-decoration: none;
}
</style>
