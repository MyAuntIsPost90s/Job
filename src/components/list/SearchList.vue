<template>
  <div class="list-page">
    <search />
    <job-list class="job-list" :requestObj="requestObj"/>
  </div>
</template>

<script>
  import Search from '@/components/home/Search'
  import JobList from '@/components/list/JobList'

  export default {
    components: {JobList, Search},
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
