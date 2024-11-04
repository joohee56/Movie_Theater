<template lang="ko">
	<div>
		<PageTitle pageTitle="빠른예매"></PageTitle>
    <router-view />
	</div>
</template>

<script>
import PageTitle from "@/components/header/PageTitle.vue";

export default {
  components: { PageTitle },
};
</script>

<style scoped></style>
