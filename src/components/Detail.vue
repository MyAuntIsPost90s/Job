<template>
  <div class="detail">
    <div class="detail-contain">
      <div class="detail-block">
        <div class="detail-name">{{details.name}}</div>
        <div class="detail-info">
          <span class="detail-info--pay">
            <span class="detail-info--val">{{details.salary||'薪资面议'}}</span>
            {{details.salaryUnit}}
          </span>
          <span class="detail-info--type">{{getSettleType(details.settleType)}}</span>
          <span class="detail-info--sort">{{details.jobClassify.name}}</span>
          <span class="detail-info--count">{{details.peopleCountLimit}}人</span>
          <router-link
            :to="{name:'Company',query:{id:details.createUserId}}"
            class="detail-info--more"
          >查看公司信息
          </router-link>
        </div>
        <div class="detail-btn" @click="openChat(details.createUserId)">我要咨询</div>
        <div class="detail-btn" @click="join(details.id)" v-if="details.applyed===false">我要报名</div>
        <div class="detail-btn" v-else>已报名</div>
      </div>
      <div class="detail-block">
        <div class="detail-title">职位详情</div>
        <div class="detail-txt">{{details.content}}</div>
        <div class="detail-title">薪资待遇</div>
        <div class="detail-head--mini">结算方式：{{getSettleType(details.settleType)}}</div>
        <div class="detail-head--mini">薪资：{{details.salary}}{{details.salaryUnit}}</div>
      </div>
      <div class="detail-block">
        <div class="detail-title">工作时间</div>
        <div>{{details.workTime}}</div>
      </div>
      <div class="detail-block">
        <div class="detail-title">工作地点</div>
        <div>{{details.address}}</div>
      </div>
      <div class="detail-block">
        <div class="detail-title">我要举报</div>
        <div>
          <el-input
            type="textarea"
            :rows="5"
            resize="none"
            placeholder="请输入举报内容"
            v-model="note">
          </el-input>
        </div>
        <div style="margin-top: 10px">
          <el-button @click="report">我要举报</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        details: {
          jobClassify: {}
        },
        note: ""
      }
    },
    computed: {
      getSettleType() {
        return function (type) {
          if (type === 1) {
            return '日结'
          } else if (type === 2) {
            return '半月结'
          } else {
            return '月结'
          }
        }
      }
    },
    methods: {
      //投递
      join(id) {
        let accessToken = window.localStorage.getItem('JobsAccessToken')
        this.axios.post(`/app/JobResume/add?jobId=${id}`, {}, {
          headers: {'AppKey': 'JOB201902', 'AccessToken': accessToken}
        }).then(res => {
          this.$alert(res.data.msg, '提示', {
            confirmButtonText: '确定'
          })
        }, err => {
          console.log(err)
        })
      },
      //举报
      report() {
        if (this.node === '') {
          this.$message.error("请输入举报内容")
          return
        }
        let accessToken = window.localStorage.getItem('JobsAccessToken')
        this.axios.post(`/app/jobReport/add?jobId=${this.details.id}&note=${this.note}`, {}, {
          headers: {'AppKey': 'JOB201902', 'AccessToken': accessToken}
        }).then(res => {
          this.note = ''
          this.$message.success("已经帮您提交举报信息，客服MM会尽快处理~")
        }, err => {
          console.log(err)
        })
      },
      openChat(targetUserId) {
        const {href} = this.$router.resolve({
          path: '/chat',
          query: {
            targetUserId: targetUserId
          }
        })
        window.open(href)
      }
    },
    mounted() {
      let accessToken = window.localStorage.getItem('JobsAccessToken')
      this.axios.get(`/app/job/detail?id=${this.$route.query.id}`, {
        headers: {'AppKey': 'JOB201902', 'AccessToken': accessToken}
      }).then(res => {
        console.log(res.data.data)
        this.details = res.data.data
      }, err => {
        console.log(err)
      })
    }
  }
</script>

<style lang="css">
  .detail {
    background: #f5f5f5;
    padding: 20px 0 2px;
  }

  .detail-contain {
    width: 1200px;
    margin: auto;
  }

  .detail-block {
    padding: 30px;
    background: #fff;
    color: #333;
    margin-bottom: 20px;
  }

  .detail-name {
    font-size: 28px;
    margin-bottom: 20px;
  }

  .detail-info {
    font-size: 14px;
    color: #999;
  }

  .detail-info--pay {
    font-weight: bold;
    margin-right: 50px;
    color: #333;
  }

  .detail-info--val {
    color: #f60;
    font-size: 20px;
    margin-right: 4px;
  }

  .detail-info--type,
  .detail-info--sort {
    line-height: 1;
    padding-right: 10px;
    border-right: 1px solid #999;
    margin-right: 4px;
  }

  .detail-info--more {
    color: #409eff;
    font-size: 12px;
    border: 1px solid #409eff;
    padding: 2px 4px;
    margin-left: 10px;
  }

  .detail-btn {
    width: 150px;
    line-height: 40px;
    text-align: center;
    color: #fff;
    background: #409eff;
    margin-top: 20px;
    cursor: pointer;
    display: inline-block;
  }

  .detail-btn:hover {
    background: #66b1ff;
  }

  .detail-title {
    font-size: 20px;
    padding-bottom: 24px;
    position: relative;
    margin-bottom: 20px;
  }

  .detail-title::after {
    content: "";
    position: absolute;
    width: 80px;
    height: 3px;
    bottom: 0;
    left: 0;
    background: #ccc;
  }

  .detail-txt {
    margin-bottom: 50px;
  }

  .detail-head--mini {
    font-size: 14px;
    line-height: 24px;
  }
</style>
