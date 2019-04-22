<template>
  <div class="company">
    <div class="company-contain">
      <div class="company-block">
        <img class="company-img" :src="company.headPicUrl">
        <div class="company-info">
          <div class="company-name">{{company.name}}</div>
        </div>
      </div>
      <div class="company-block">
        <div class="company-title">公司基本信息</div>
        <div class="company-txt">{{company.introduce}}</div>
        <div class="company-title">联系方式</div>
        <div class="company-txt" v-for="(item,index) in company.workLive" v-html="item" :key="index"></div>
        <div class="company-title">招聘职位</div>
        <job-list class="cmp-list" :requestObj="requestObj"/>
      </div>
    </div>
  </div>
</template>

<script>
import JobList from '@/components/list/JobList'
export default {
  components: { JobList },
  data() {
    return {
      company: {},
      requestObj: {
        url: '/app/job/list',
        rows: 8,
        type: 'index'
      }
    }
  },
  mounted() {
    this.axios.get(`/app/user/detail?id=${this.$route.query.id}`).then(res => {
      console.log(res.data.data)
      this.company = res.data.data
      if(this.company.workLive){
        this.company.workLive = this.company.workLive.split('\n');
      }
    }, err => {
      console.log(err)
    })
  }
}
</script>

<style lang="css">
.company {
  background: #f5f5f5;
  padding: 20px 0 2px;
}
.company-contain {
  width: 1200px;
  margin: auto;
}
.company-block {
  padding: 30px;
  background: #fff;
  color: #333;
  margin-bottom: 20px;
}
.company-img {
  width: 100px;
  height: 100px;
  margin-right: 30px;
}
.company-block:first-child {
  display: flex;
}
.company-name {
  font-size: 28px;
  font-weight: bold;
  margin-bottom: 20px;
}
.company-title {
  font-size: 20px;
  padding-bottom: 24px;
  position: relative;
  margin-bottom: 20px;
}
.company-title::after {
  content: "";
  position: absolute;
  width: 36px;
  height: 3px;
  bottom: 0;
  left: 0;
  background: #ccc;
}
.company-txt {
  margin-bottom: 50px;
}
.cmp-list .job-list__item {
  padding: 0;
}
.cmp-list .job-list {
  width: auto;
}
</style>
