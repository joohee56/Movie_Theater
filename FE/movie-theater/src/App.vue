<template>
  <div id="app">
    <div class="navbar-container">
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
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
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
</style>
