<template>
  <div class="settings">
    <div class="settings-contain">
      <el-menu :default-active="sidebarIndex" class="settings-sidebar" @select="changeIndex">
        <router-link :to="{name:'StuSettings',query:{index:1}}">
          <el-menu-item index="1">
            <i class="el-icon-tickets"></i>
            <span slot="title">我的简历</span>
          </el-menu-item>
        </router-link>
        <router-link :to="{name:'StuSettings',query:{index:2}}">
          <el-menu-item index="2">
            <i class="el-icon-time"></i>
            <span slot="title">投递历史</span>
          </el-menu-item>
        </router-link>
        <router-link :to="{name:'StuSettings',query:{index:3}}">
          <el-menu-item index="3">
            <i class="el-icon-mobile-phone"></i>
            <span slot="title">更换绑定</span>
          </el-menu-item>
        </router-link>
        <router-link :to="{name:'StuSettings',query:{index:4}}">
          <el-menu-item index="4">
            <i class="el-icon-edit"></i>
            <span slot="title">修改密码</span>
          </el-menu-item>
        </router-link>
      </el-menu>
      <div class="settings-main" v-if="sidebarIndex==='1'">
        <el-form
          :model="resumeForm"
          status-icon
          :rules="resumeRules"
          ref="resumeForm"
          label-width="80px"
          class="settings-form"
          key="1"
        >
          <el-form-item label="证件照">
            <el-upload
              class="avatar-uploader"
              :action="actionUrl"
              :show-file-list="false"
              :data="{ basePath:'/user' }"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload"
            >
              <img v-if="imageUrl" :src="imageUrl" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-form-item>
          <el-form-item label="姓名" prop="username">
            <el-input placeholder="请输入真实姓名" v-model="resumeForm.username"></el-input>
          </el-form-item>
          <el-form-item label="性别">
            <el-radio v-model="resumeForm.sex" label="1">男</el-radio>
            <el-radio v-model="resumeForm.sex" label="0">女</el-radio>
          </el-form-item>
          <el-form-item label="学校名称" prop="schoolname">
            <el-input placeholder="请输入学校名称" v-model="resumeForm.schoolname"></el-input>
          </el-form-item>
          <el-form-item label="个人简介" prop="desc">
            <el-input
              type="textarea"
              :rows="5"
              resize="none"
              placeholder="请输入内容"
              v-model="resumeForm.desc"
            ></el-input>
          </el-form-item>
          <el-form-item label="工作经历" prop="experience">
            <el-input type="textarea" :rows="5" placeholder="请输入内容" v-model="resumeForm.experience"></el-input>
          </el-form-item>
          <el-form-item label="手机号" prop="tel">
            <el-input v-model="resumeForm.tel" disabled></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="saveResumeForm('resumeForm')">保存</el-button>
          </el-form-item>
        </el-form>
      </div>
      <div class="settings-main" v-if="sidebarIndex==='2'">
        <job-list class="set-list" :requestObj="requestObj"/>
      </div>
      <div class="settings-main" v-if="sidebarIndex==='3'">
        <el-form
          :model="telForm"
          status-icon
          :rules="telRules"
          ref="telForm"
          label-width="80px"
          class="settings-form"
          key="3"
        >
          <el-form-item label="原手机号" prop="oldTel">
            <el-input placeholder="请输入手机号" v-model="telForm.oldTel"></el-input>
          </el-form-item>
          <el-form-item label="新手机号" prop="newTel">
            <el-input placeholder="请输入手机号" v-model="telForm.newTel"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="saveTelForm('telForm')">确认更换</el-button>
          </el-form-item>
        </el-form>
      </div>
      <div class="settings-main" v-if="sidebarIndex==='4'">
        <el-form
          :model="pwdForm"
          status-icon
          :rules="pwdRules"
          ref="pwdForm"
          label-width="80px"
          class="settings-form"
          key="4"
        >
          <el-form-item label="原密码" prop="oldPwd">
            <el-input placeholder="请输入原密码" type="password" v-model="pwdForm.oldPwd"></el-input>
          </el-form-item>
          <el-form-item label="新密码" prop="newPwd">
            <el-input placeholder="请输入新密码" type="password" v-model="pwdForm.newPwd"></el-input>
          </el-form-item>
          <el-form-item label="确认密码" prop="newPwd2">
            <el-input placeholder="请再次输入新密码" type="password" v-model="pwdForm.newPwd2"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="savePwdForm('pwdForm')">确认更换</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import md5 from 'js-md5'
import JobList from '@/components/list/JobList'
export default {
  components: { JobList },
  watch: {
    '$route.query.index'() {
      this.sidebarIndex = this.$route.query.index.toString()
      console.log(this.$route.query.index)
    }
  },
  data() {
    let checkUsername = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('姓名不能为空'))
      } else {
        callback()
      }
    }
    let checkSchoolname = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('学校名称不能为空'))
      } else {
        callback()
      }
    }
    let checkDesc = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('个人简介不能为空'))
      } else {
        callback()
      }
    }
    let checkExperience = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('工作经历不能为空'))
      } else {
        callback()
      }
    }
    let checkTel = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('手机号不能为空'))
      } else {
        callback()
      }
    }
    let checkOldTel = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('原手机号不能为空'))
      } else {
        callback()
      }
    }
    let checkNewTel = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('新手机号不能为空'))
      } else {
        callback()
      }
    }
    let checkOldPwd = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('原密码不能为空'))
      } else {
        callback()
      }
    }
    let checkNewPwd = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('新密码不能为空'))
      } else {
        callback()
      }
    }
    let checkNewPwd2 = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('确认密码不能为空'))
      } else {
        callback()
      }
    }
    return {
      actionUrl: `${process.env.SERVICE_URL}/base/file/upload`,
      sidebarIndex: '1',
      imageUrl: '',
      resumeForm: {
        imageUrl: '',
        username: '',
        sex: '',
        schoolname: '',
        desc: '',
        experience: '',
        tel: ''
      },
      telForm: {
        sysTel: '',
        oldTel: '',
        newTel: ''
      },
      pwdForm: {
        sysPwd: '',
        oldPwd: '',
        newPwd: '',
        newPwd2: ''
      },
      resumeRules: {
        username: [
          { validator: checkUsername, trigger: 'blur' }
        ],
        schoolname: [
          { validator: checkSchoolname, trigger: 'blur' }
        ],
        desc: [
          { validator: checkDesc, trigger: 'blur' }
        ],
        experience: [
          { validator: checkExperience, trigger: 'blur' }
        ],
        tel: [
          { validator: checkTel, trigger: 'blur' }
        ]
      },
      telRules: {
        oldTel: [
          { validator: checkOldTel, trigger: 'blur' }
        ],
        newTel: [
          { validator: checkNewTel, trigger: 'blur' }
        ]
      },
      pwdRules: {
        oldPwd: [
          { validator: checkOldPwd, trigger: 'blur' }
        ],
        newPwd: [
          { validator: checkNewPwd, trigger: 'blur' }
        ],
        newPwd2: [
          { validator: checkNewPwd2, trigger: 'blur' }
        ]
      },
      requestObj:{
        url: '/app/job/applyHistory',
        rows: 4,
        type: 'stu'
      }
    }
  },
  methods: {
    changeIndex(key) {
      this.sidebarIndex = key
    },
    handleAvatarSuccess(res, file) {
      if (res.code !== 1) {
        this.$alert(res.msg, '提示', {
          confirmButtonText: '确定'
        })
        return
      }
      /* 相对路径，用于后端传输 */
      this.resumeForm.imageUrl = res.data[0].path
      /* 绝对路径用于前端展示 */
      this.imageUrl = res.data[0].url
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg'
      const isLt2M = file.size / 1024 / 1024 < 2
      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return isJPG && isLt2M
    },
    saveResumeForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let accessToken = window.localStorage.getItem('JobsAccessToken')
          let data = new URLSearchParams()
          data.append('name', this.resumeForm.username)
          data.append('sex', this.resumeForm.sex)
          data.append('headPicUrl', this.resumeForm.imageUrl)
          data.append('school', this.resumeForm.schoolname)
          data.append('introduce', this.resumeForm.desc)
          data.append('workLive', this.resumeForm.experience)
          this.axios.post('/app/user/update', data, {
            headers: { 'AppKey': 'JOB201902', 'AccessToken': accessToken }
          }).then(res => {
            console.log(res)
            this.$message.success(res.data.msg)
          }, err => {
            console.log(err)
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    saveTelForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.telForm.oldTel !== this.telForm.sysTel) {
            this.$alert('输入的原手机号错误', '提示', {
              confirmButtonText: '确定'
            })
            return false
          }
          let accessToken = window.localStorage.getItem('JobsAccessToken')
          let data = new URLSearchParams()
          data.append('username', this.telForm.newTel)
          this.axios.post('/app/user/update', data, {
            headers: { 'AppKey': 'JOB201902', 'AccessToken': accessToken }
          }).then(res => {
            this.$alert('已更换为新手机号', '提示', {
              confirmButtonText: '确定'
            })
          }, err => {
            console.log(err)
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    savePwdForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.pwdForm.sysPwd !== md5(this.pwdForm.oldPwd).toUpperCase()) {
            this.$message.error('原密码错误')
            return false
          }
          let accessToken = window.localStorage.getItem('JobsAccessToken')
          let data = new URLSearchParams()
          data.append('password', md5(this.pwdForm.newPwd).toUpperCase())
          this.axios.post('/app/user/update', data, {
            headers: { 'AppKey': 'JOB201902', 'AccessToken': accessToken }
          }).then(res => {
            this.$message.success('密码修改成功')
          }, err => {
            console.log(err)
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    }
  },
  mounted() {
    /* 将url的参数作为改变侧边栏index的方式之一 */
    this.sidebarIndex = this.$route.query.index.toString()

    /* 请求用户原表单的数据并写入表单 */
    let accessToken = window.localStorage.getItem('JobsAccessToken')
    if (accessToken !== null && accessToken !== undefined) {
      this.axios.post('/app/user/currUser', {}, {
        headers: { 'AppKey': 'JOB201902', 'AccessToken': accessToken }
      }).then(res => {
        console.log('获取用户数据')
        console.log(res.data.data)
        let data = res.data.data
        this.imageUrl = data.headPicUrl
        this.resumeForm.imageUrl = data.headPicUrlNoPrefix
        this.resumeForm.username = data.name
        this.resumeForm.sex = data.sex === null ? '' : data.sex + ''
        this.resumeForm.schoolname = data.school
        this.resumeForm.desc = data.introduce
        this.resumeForm.experience = data.workLive
        this.resumeForm.tel = data.username
        this.telForm.sysTel = data.username
        this.pwdForm.sysPwd = data.password
      }, err => {
        console.log(err)
      })
    }
  }
}
</script>

<style lang="css">
.settings {
  background: #f5f5f5;
  padding: 20px;
}
.settings-contain {
  width: 1200px;
  height: 820px;
  /* min-height: 820px; */
  background: #fff;
  margin: auto;
  display: flex;
}
.settings-sidebar {
  width: 240px;
  height: 100%;
}
.settings-main {
  width: 850px;
  height: auto;
  padding: 30px 50px;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 160px;
  height: 160px;
  line-height: 160px;
  text-align: center;
}
.avatar {
  width: 160px;
  height: 160px;
  display: block;
}
.settings-main .el-input,
.settings-main .el-button {
  width: 300px;
}
.set-list .job-list {
  width: auto;
}
</style>
