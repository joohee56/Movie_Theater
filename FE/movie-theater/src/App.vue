<template>
  <div id="app">
    <NavBar></NavBar>
    <router-view />
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
  text-align: center;
  color: #2c3e50;
}

/* :root {
  --primary-color: #503396;
  --secondary-color: #2ecc71;
  --text-color: #333333;
  --background-color: #f0f0f0;
} */

nav a.router-link-exact-active {
  color: #42b983;
}
</style>
