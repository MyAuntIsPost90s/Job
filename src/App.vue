<template>
  <div id="app">
    <Nav :logoPicUrl="logoPicUrl"/>
    <router-view/>
    <Footer :copyright="copyright"/>
  </div>
</template>

<script>
import Nav from '@/components/Nav'
import Footer from '@/components/Footer'
export default {
  components: { Nav, Footer },
  name: 'App',
  data() {
    return {
      logoPicUrl: '',
      copyright: ''
    }
  },
  mounted() {
    this.axios.get('/app/sysConfig/detail').then(res => {
      let data = res.data.data
      this.logoPicUrl = data.logoPicUrl
      this.copyright = data.copyright
    }, err => {
      console.log(err)
    })
  }
}
</script>

<style>
</style>
