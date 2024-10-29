<template lang="ko">
	<div class="page-title">
		{{pageTitle}}
	</div>
</template>

<script>
export default {
  props: ["pageTitle"],
};
</script>

<style scoped>
.page-title {
  font-family: SUIT-Medium;
  font-size: 30px;
  margin: 25px 0;
}
</style>
