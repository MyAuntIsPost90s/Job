<template>
  <div class="list-page">
    <job-type/>
    <job-list class="job-list" :requestObj="requestObj"/>
  </div>
</template>

<script>
import JobType from '@/components/list/JobType'
import JobList from '@/components/list/JobList'
export default {
  components: { JobType, JobList },
  data() {
    return {
      requestObj: {
        url: '/app/job/list',
        rows: 8,
        type: 'index',
        publishStatus: 1
      }
    }
  }
}
</script>

<style lang="css">
.list-page {
  background: #f5f5f5;
  padding: 20px 0;
}
.job-list {
  width: 1200px;
}
</style>
