<template>
  <div class="resume-contain">
    <div class="resume-list">
      <el-card v-for="(item,index) in resumes" :key="index" class="box-card">
        <div slot="header">
          <span>姓名：{{ item.userDto.name }}</span>
          <el-button style="float: right; padding: 3px 0" type="text">
            <el-button v-if="item.status == 0" size="mini" @click="setStatus(item.id,1)" type="primary">标记处理</el-button>
            <el-button v-if="item.status == 1" size="mini" @click="setStatus(item.id,0)" type="primary">撤回标记</el-button>
          </el-button>
        </div>
        <div class="text item">
          <el-row>
            状态：{{ item.status==0?'待处理':'已处理' }}
          </el-row>
          <el-row>
            <el-col :span="2">头像：</el-col>
            <el-col :span="6">
              <img v-if="item.userDto.headPicUrl===undefined || item.userDto.headPicUrl===''"
                   src="@/../static/images/dft-head.jpg" width="80" style="border: 1px solid #EBEEF5"/>
              <img v-else :src="item.userDto.headPicUrl" width="80"
                   style="border: 1px solid #EBEEF5;background-color:#EBEEF5"/>
            </el-col>
          </el-row>
          <el-row>
            性别：{{ item.userDto.sex == 0?'女':'男' }}
          </el-row>
          <el-row>
            毕业学校：{{ item.userDto.school }}
          </el-row>
          <el-row>
            求职岗位：{{ item.jobDto.name }}
          </el-row>
          <el-row>
            工作经历：{{ item.userDto.introduce }}
          </el-row>
          <el-row>
            个人简介：{{ item.userDto.workLive }}
          </el-row>
        </div>
      </el-card>
    </div>
    <div style="text-align: center;padding: 10px">
      <el-pagination
        background
        layout="prev, pager, next"
        :page-size="resumes.length"
        :total="resumesSize"
        @current-change="loadData"
      ></el-pagination>
    </div>
  </div>
</template>

<script>
  import ElRow from "element-ui/packages/row/src/row";
  import ElCard from "element-ui/packages/card/src/main";

  export default {
    components: {
      ElCard,
      ElRow
    },
    name: "ResumeList",
    data() {
      return {
        resumes: [],
        resumesSize: 0,
        page: 1,
        rows: 1
      }
    },
    mounted() {
      this.loadData()
    },
    methods: {
      loadData(currPage = 1) {
        this.page = currPage
        let accessToken = window.localStorage.getItem('JobsAccessToken')
        this.axios.post(`/app/JobResume/applicants?page=${this.page}&rows=${this.rows}`, {}, {
          headers: {'AppKey': 'JOB201902', 'AccessToken': accessToken}
        }).then(res => {
          if (res.data.code !== 1) {
            this.$message.error(res.data.msg)
            return
          }
          this.resumes = res.data.data.rows
          this.resumesSize = res.data.data.total
        }, err => {
          console.log(err)
        })
      },
      setStatus(id, status) {
        let accessToken = window.localStorage.getItem('JobsAccessToken')
        this.axios.post(`/app/JobResume/update?id=${id}&status=${status}`, {}, {
          headers: {'AppKey': 'JOB201902', 'AccessToken': accessToken}
        }).then(res => {
          if (res.data.code !== 1) {
            this.$message.error(res.data.msg)
            return
          }
          this.$message.success(res.data.msg)
          this.loadData(this.page)
        }, err => {
          console.log(err)
        })
      }
    }
  }
</script>

<style scoped>
  .resume-list {
    font-size: 14px;
    height: 720px;
    overflow-y: auto;
  }

  .resume-list .el-row {
    padding: 5px 0;
    line-height: 28px;
  }

  .box-card {
    margin: 10px 0px;
  }
</style>
