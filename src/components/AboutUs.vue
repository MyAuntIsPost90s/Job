<template>
  <div class="home">
    <search/>
    <div class="home-main">
      <div class="home-block">
        <h4>标题一</h4>
        这里由你自己发挥
        <h4>标题二</h4>
        这里由你自己发挥
        <h4>标题三</h4>
        这里由你自己发挥
      </div>
    </div>
  </div>
</template>

<script>
  import Search from '@/components/home/Search'

  export default {
    name: "AboutUs",
    components: { Search },
  }
</script>

<style scoped>
  .home-block {
    width: 1160px;
    background: #fff;
    margin: 30px auto 0;
    padding: 20px 20px;
    color: #333;
  }
  .heading-main {
    font-size: 36px;
    font-weight: bold;
    margin-bottom: 8px;
  }
  .home {
    background: #f5f5f5;
    padding-bottom: 50px;
  }
</style>
