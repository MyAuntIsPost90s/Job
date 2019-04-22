<template>
  <div class="search-wrap" :style="getUrl">
    <el-input placeholder="请输入内容" v-model="name" class="search__input">
      <el-button slot="append" @click="search()" icon="el-icon-search">搜索</el-button>
    </el-input>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        name: '',
        introducePicUrl: ''
      }
    },
    mounted() {
      this.axios.get('/app/sysConfig/detail').then(res => {
        let data = res.data.data
        this.introducePicUrl = data.introducePicUrl
      }, err => {
        console.log(err)
      })
    },
    computed: {
      getUrl() {
        return {
          'background': `url("${this.introducePicUrl}") no-repeat center`
        }
      }
    },
    methods: {
      search() {
        this.$router.push({path: `/searchList`, query: {jobName: this.name}})
      }
    }
  }
</script>

<style lang="css">
  .search-wrap {
    min-width: 1200px;
    height: 450px;
    overflow: hidden;
    text-align: center;
    margin-top: 10px;
  }

  .search__input {
    width: 600px;
    margin-top: 260px;
  }

  .search__input .el-input-group__append button.el-button,
  .search__input .el-input-group__append div.el-select .el-input__inner {
    border-color: #409eff;
    background-color: #409eff;
    border-radius: 0;
    color: #fff;
  }
</style>
