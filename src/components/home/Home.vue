<template>
  <div class="home">
    <search/>
    <home-list :indexList="indexList"/>
  </div>
</template>

<script>
import Search from '@/components/home/Search'
import HomeList from '@/components/home/HomeList'
export default {
  components: { Search, HomeList },
  data() {
    return {
      indexList: {
        new: [],
        hot: []
      }
    }
  },
  mounted() {
    this.axios.get('/app/job/list?page=1&rows=8&orderType=1&publishStatus=1').then(res => {
      console.log(res.data.data.rows)
      this.indexList.new = res.data.data.rows
    }, err => {
      console.log(err)
    })
    this.axios.get('/app/job/list?page=1&rows=8&orderType=2&publishStatus=1').then(res => {
      console.log(res.data.data.rows)
      this.indexList.hot = res.data.data.rows
    }, err => {
      console.log(err)
    })
  }
}
</script>

<style lang="css">
.home {
  background: #f5f5f5;
  padding-bottom: 50px;
}
</style>
