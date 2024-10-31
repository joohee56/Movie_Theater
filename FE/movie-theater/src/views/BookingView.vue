<template lang="ko">
	<div>
		<PageTitle pageTitle="빠른예매"></PageTitle>
		<date-picker v-model="selectedDate" format="YYYY.MM.DD" :lang="lang" />
	</div>
</template>

<script>
import DatePicker from "vue2-datepicker";
import "vue2-datepicker/index.css";
import "vue2-datepicker/locale/ko";
import PageTitle from "@/components/header/PageTitle";

export default {
  data() {
    return {
      selectedDate: null,
      lang: {
        formatLocale: {
          firstDayOfWeek: 1,
        },
        monthBeforeYear: false,
      },
    };
  },
  components: {
    PageTitle,
    DatePicker,
  },
};
</script>

<style scoped></style>
