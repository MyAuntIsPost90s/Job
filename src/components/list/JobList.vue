<template>
  <div class="job-list-contain">
    <ul class="job-list" v-if="jobList.length!==0">
      <li class="job-list__item" v-for="(item,index) in jobList" :key="index">
        <div class="job-info">
          <router-link :to="{name:'Detail',query:{id:item.id}}">
            <div class="job-name">{{item.name}}</div>
          </router-link>
          <ul class="job-info__list">
            <li class="job-info__item">工作时间：{{item.workTime}}</li>
            <li class="job-info__item">工作类型：{{item.jobClassify.name}}</li>
            <li class="job-info__item">招聘人数：{{item.peopleCountLimit}}人</li>
            <li class="job-info__item">工作地点：{{item.address}}</li>
<!--            <li class="job-info__item">报名时间：2019-01-28</li>-->
            <li
              class="job-info__item"
              v-if="item.publishStatus"
            >状态：{{getStatus(item.publishStatus)}}
            </li>
          </ul>
        </div>
        <div class="job-right">
          <div class="job-price">
            <span class="job-price-val">{{item.salary||'薪资面议'}}</span>
            {{item.salaryUnit}}
            <span class="job-pay-type">{{getType(item.settleType)}}</span>
          </div>
          <div
            class="job-btn"
            v-if="requestObj.type==='index'&&item.applyed===false"
            @click="join(item.id)"
          >我要报名
          </div>
          <div class="job-btn" v-if="requestObj.type==='index'&&item.applyed===true">已报名</div>
          <!--<div class="job-btn" v-if="requestObj.type==='stu'">查看详情</div>-->
          <div class="job-btn" v-if="requestObj.type==='hr'&&item.publishStatus===0">正在审核</div>
          <div class="job-btn" v-if="requestObj.type==='hr'&&item.publishStatus===1" @click="setStatus(item.id,3)">下架</div>
          <div class="job-btn" v-if="requestObj.type==='hr'&&item.publishStatus===2">未通过</div>
          <div class="job-btn" v-if="requestObj.type==='hr'&&item.publishStatus===3" @click="setStatus(item.id,1)">已失效(点击撤销)</div>
        </div>
      </li>
    </ul>
    <el-pagination
      background
      layout="prev, pager, next"
      :page-size="requestObj.rows"
      :total="total"
      @current-change="getCurrentPage"
    ></el-pagination>
  </div>
</template>

<script>
  export default {
    props: ['requestObj'],
    data() {
      return {
        total: 0,
        jobList: [{
          jobClassify: {}
        }],
        jobClassifyId: -1,
        settleType: -1,
        sexLimit: -1,
        orderType: 1,
        type: -1,
        name: ''
      }
    },
    watch: {
      '$route.query'() {
        this.setQuery()
        this.getCurrentPage()
      },
      requestObj:{
        handler() {
          this.getCurrentPage()
        },
        deep:true
      }
    },
    computed: {
      getType() {
        return function (type) {
          if (type === 1) {
            return '日结'
          } else if (type === 2) {
            return '半月结'
          } else {
            return '月结'
          }
        }
      },
      getStatus() {
        return function (status) {
          if (status === 0) {
            return '正在审核'
          } else if (status === 1) {
            return '审核通过'
          } else {
            return '审核未通过'
          }
        }
      }
    },
    methods: {
      getCurrentPage(val = 1) {
        /* 调用渲染 */
        let accessToken = window.localStorage.getItem('JobsAccessToken')
        let str = `?page=${val}&rows=${this.requestObj.rows}&`
        if (this.jobClassifyId !== -1) {
          str += `jobClassifyId=${this.jobClassifyId}&`
        }
        if (this.settleType !== -1) {
          str += `settleType=${this.settleType}&`
        }
        if (this.sexLimit !== -1) {
          str += `sexLimit=${this.sexLimit}&`
        }
        if (this.orderType !== '') {
          str += `orderType=${this.orderType}&`
        }
        if (this.type !== -1) {
          str += `type=${this.type}&`
        }
        if (this.name !== '') {
          str += `name=${this.name}&`
        }
        if (this.requestObj.publishStatus) {
          str += `publishStatus=${this.requestObj.publishStatus}&`
        }
        if (this.requestObj.createUserId) {
          str += `createUserId=${this.requestObj.createUserId}&`
        }
        str = str.slice(0, -1)

        this.axios.get(`${this.requestObj.url}${str}`, {
          headers: {'AppKey': 'JOB201902', 'AccessToken': accessToken}
        }).then(res => {
          this.jobList = res.data.data.rows
          this.total = res.data.data.total
        }, err => {
          console.log(err)
        })
      },
      join(id) {
        let accessToken = window.localStorage.getItem('JobsAccessToken')
        this.axios.post(`/app/jobResume/add?jobId=${id}`, {}, {
          headers: {'AppKey': 'JOB201902', 'AccessToken': accessToken}
        }).then(res => {
          this.$alert(res.data.msg, '提示', {
            confirmButtonText: '确定'
          })
        }, err => {
          console.log(err)
        })
      },
      setStatus(id, status) {
        let accessToken = window.localStorage.getItem('JobsAccessToken')
        this.axios.post(`/app/job/update?id=${id}&publishStatus=${status}`, {}, {
          headers: {'AppKey': 'JOB201902', 'AccessToken': accessToken}
        }).then(res => {
          this.$message.success(res.data.msg)
          this.getCurrentPage();
        }, err => {
          console.log(err)
        })
      },
      setQuery() {
        this.jobClassifyId = this.$route.query.jobClassifyId || -1
        this.settleType = this.$route.query.settleType || -1
        this.sexLimit = this.$route.query.sexLimit || -1
        this.orderType = this.$route.query.orderType || 1
        this.type = this.$route.query.type || -1
        this.name = this.$route.query.jobName || ''
      }
    },
    mounted() {
      this.setQuery()
      this.getCurrentPage()
    }
  }
</script>

<style lang="css">
  .job-list-contain {
    margin: 20px auto;
  }

  .job-list__item {
    margin-top: 10px;
    background: #fff;
    padding: 20px;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .job-info {
    width: 500px;
  }

  .job-name {
    line-height: 32px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }

  .job-name:hover {
    color: #409eff;
  }

  .job-info__list {
    font-size: 12px;
    line-height: 24px;
    display: flex;
    flex-wrap: wrap;
    color: #999;
    margin-top: 10px;
  }

  .job-info__item {
    width: 240px;
    margin-right: 10px;
    overflow: hidden;
    height: 24px;
    white-space: nowrap;
    text-overflow: ellipsis;
  }

  .job-right {
    display: flex;
    align-items: center;
  }

  .job-price {
    font-size: 12px;
    color: #999;
  }

  .job-price-val {
    color: #f60;
    font-size: 16px;
    font-weight: bold;
    margin-right: 6px;
  }

  .job-btn {
    width: 150px;
    line-height: 40px;
    text-align: center;
    color: #fff;
    background: #409eff;
    margin-left: 42px;
    cursor: pointer;
  }

  .job-btn--join {
    background-color: #e7e7e7;
    color: #999;
  }

  .el-pagination {
    text-align: center;
    margin-top: 20px;
  }
</style>
