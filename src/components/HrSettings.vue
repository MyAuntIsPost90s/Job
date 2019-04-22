<template>
  <div class="settings">
    <div class="settings-contain">
      <el-menu :default-active="sidebarIndex" class="settings-sidebar" @select="changeIndex">
        <router-link :to="{name:'HrSettings',query:{index:1}}">
          <el-menu-item index="1">
            <i class="el-icon-tickets"></i>
            <span slot="title">公司信息</span>
          </el-menu-item>
        </router-link>
        <router-link :to="{name:'HrSettings',query:{index:2}}">
          <el-menu-item index="2">
            <i class="el-icon-time"></i>
            <span slot="title">发布历史</span>
          </el-menu-item>
        </router-link>
        <router-link :to="{name:'HrSettings',query:{index:6}}">
          <el-menu-item index="6">
            <i class="el-icon-tickets"></i>
            <span slot="title">收到的简历</span>
          </el-menu-item>
        </router-link>
        <router-link :to="{name:'HrSettings',query:{index:3}}">
          <el-menu-item index="3">
            <i class="el-icon-plus"></i>
            <span slot="title">新建招聘</span>
          </el-menu-item>
        </router-link>
        <router-link :to="{name:'HrSettings',query:{index:4}}">
          <el-menu-item index="4">
            <i class="el-icon-mobile-phone"></i>
            <span slot="title">更换绑定</span>
          </el-menu-item>
        </router-link>
        <router-link :to="{name:'HrSettings',query:{index:5}}">
          <el-menu-item index="5">
            <i class="el-icon-edit"></i>
            <span slot="title">修改密码</span>
          </el-menu-item>
        </router-link>
      </el-menu>
      <div class="settings-main" v-if="sidebarIndex==='1'">
        <el-form
          :model="msgForm"
          status-icon
          :rules="msgRules"
          ref="msgForm"
          label-width="80px"
          class="settings-form"
          key="1"
        >
          <el-form-item label="预览图">
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
          <el-form-item label="公司名称" prop="companyName">
            <el-input placeholder="请输入公司名称" v-model="msgForm.companyName"></el-input>
          </el-form-item>
          <el-form-item label="公司简介" prop="companyDesc">
            <el-input
              type="textarea"
              :rows="5"
              resize="none"
              placeholder="请输入内容"
              v-model="msgForm.companyDesc"
            ></el-input>
          </el-form-item>
          <el-form-item label="联系方式" prop="contact">
            <el-input
              type="textarea"
              :rows="5"
              resize="none"
              placeholder="请输入内容"
              v-model="msgForm.contact"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="saveMsgForm('msgForm')">保存</el-button>
          </el-form-item>
        </el-form>
      </div>
      <div class="settings-main" v-if="sidebarIndex==='2'">
        <job-list :requestObj="requestObj"/>
      </div>
      <div class="settings-main" v-if="sidebarIndex==='3'">
        <el-form
          :model="addForm"
          status-icon
          :rules="addRules"
          ref="addForm"
          label-width="80px"
          class="settings-form"
          key="3"
        >
          <el-form-item label="招聘名称" prop="offerName">
            <el-input placeholder="请输入招聘名称" v-model="addForm.offerName"></el-input>
          </el-form-item>
          <el-form-item label="所属分类">
            <el-select v-model="addForm.offerType.value" placeholder="请选择">
              <el-option
                v-for="item in addForm.offerType.options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="性别限制">
            <el-radio v-model="addForm.sexType" label="2">不限</el-radio>
            <el-radio v-model="addForm.sexType" label="1">男</el-radio>
            <el-radio v-model="addForm.sexType" label="0">女</el-radio>
          </el-form-item>
          <el-form-item label="结算方式">
            <el-radio v-model="addForm.payType" label="1">日结</el-radio>
            <el-radio v-model="addForm.payType" label="2">半月结</el-radio>
            <el-radio v-model="addForm.payType" label="3">月结</el-radio>
          </el-form-item>
          <el-form-item label="薪资待遇">
            <el-input placeholder="请输入内容" v-model="addForm.payVal.val" class="input-with-select">
              <el-select
                v-model="addForm.payVal.type"
                slot="append"
                placeholder="请选择"
                style="width:120px"
              >
                <el-option label="元/小时" value="元/小时"></el-option>
                <el-option label="元/天" value="元/天"></el-option>
                <el-option label="元/月" value="元/月"></el-option>
              </el-select>
            </el-input>
          </el-form-item>
          <el-form-item label="招聘人数">
            <el-input-number v-model="addForm.number" :min="1"></el-input-number>
          </el-form-item>
          <el-form-item label="职位详情" prop="offerInfo">
            <el-input
              type="textarea"
              :rows="4"
              resize="none"
              placeholder="请输入内容"
              v-model="addForm.offerInfo"
            ></el-input>
          </el-form-item>
          <el-form-item label="工作时间" prop="offerTime">
            <el-input
              type="textarea"
              :rows="4"
              resize="none"
              placeholder="请输入内容"
              v-model="addForm.offerTime"
            ></el-input>
          </el-form-item>
          <el-form-item label="工作地点" prop="location">
            <el-input
              type="textarea"
              :rows="4"
              resize="none"
              placeholder="请输入内容"
              v-model="addForm.location"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="saveAddForm('addForm')">提交审核</el-button>
          </el-form-item>
        </el-form>
      </div>
      <div class="settings-main" v-if="sidebarIndex==='4'">
        <el-form
          :model="telForm"
          status-icon
          :rules="telRules"
          ref="telForm"
          label-width="80px"
          class="settings-form"
          key="4"
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
      <div class="settings-main" v-if="sidebarIndex==='5'">
        <el-form
          :model="pwdForm"
          status-icon
          :rules="pwdRules"
          ref="pwdForm"
          label-width="80px"
          class="settings-form"
          key="5"
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
      <div class="settings-main" v-if="sidebarIndex === '6'">
        <ResumeList/>
      </div>
    </div>
  </div>
</template>

<script>
  import md5 from 'js-md5'
  import JobList from '@/components/list/JobList'
  import ResumeList from '@/components/list/ResumeList'

  export default {
    components: {JobList, ResumeList},
    watch: {
      '$route.query.index'() {
        this.sidebarIndex = this.$route.query.index.toString()
        console.log(this.$route.query.index)
      }
    },
    data() {
      let checkCompanyName = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('公司名称不能为空'))
        } else {
          callback()
        }
      }
      let checkCompanyDesc = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('公司简介不能为空'))
        } else {
          callback()
        }
      }
      let checkContact = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('联系方式不能为空'))
        } else {
          callback()
        }
      }
      let checkOfferName = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('招聘名称不能为空'))
        } else {
          callback()
        }
      }
      let checkOfferInfo = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('职位详情不能为空'))
        } else {
          callback()
        }
      }
      let checkOfferTime = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('工作时间不能为空'))
        } else {
          callback()
        }
      }
      let checkLocation = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('工作地点不能为空'))
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
        } else if (value.length < 8 || value.length > 16) {
          callback(new Error('请输入8-16位密码'))
        } else {
          callback()
        }
      }
      let checkNewPwd2 = (rule, value, callback) => {
        if (!value) {
          callback(new Error('确认密码不能为空'))
        } else if (value !== this.pwdForm.newPwd) {
          callback(new Error('两次输入的密码不一致'))
        } else {
          callback()
        }
      }
      return {
        id: '',
        actionUrl: `${process.env.SERVICE_URL}/base/file/upload`,
        sidebarIndex: '1',
        requestObj: {
          url: '/app/job/list',
          rows: 4,
          type: 'hr',
          createUserId: undefined
        },
        imageUrl: '',
        msgForm: {
          imageUrl: '',
          companyName: '',
          companyDesc: '',
          contact: ''
        },
        addForm: {
          offerName: '',
          offerType: {
            options: [],
            value: ''
          },
          sexType: '',
          payType: '',
          payVal: {
            val: '',
            type: ''
          },
          number: '',
          offerInfo: '',
          offerTime: '',
          location: ''
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
        msgRules: {
          companyName: [
            {validator: checkCompanyName, trigger: 'blur'}
          ],
          companyDesc: [
            {validator: checkCompanyDesc, trigger: 'blur'}
          ],
          contact: [
            {validator: checkContact, trigger: 'blur'}
          ]
        },
        addRules: {
          offerName: [
            {validator: checkOfferName, trigger: 'blur'}
          ],
          offerInfo: [
            {validator: checkOfferInfo, trigger: 'blur'}
          ],
          offerTime: [
            {validator: checkOfferTime, trigger: 'blur'}
          ],
          location: [
            {validator: checkLocation, trigger: 'blur'}
          ]
        },
        telRules: {
          oldTel: [
            {validator: checkOldTel, trigger: 'blur'}
          ],
          newTel: [
            {validator: checkNewTel, trigger: 'blur'}
          ]
        },
        pwdRules: {
          oldPwd: [
            {validator: checkOldPwd, trigger: 'blur'}
          ],
          newPwd: [
            {validator: checkNewPwd, trigger: 'blur'}
          ],
          newPwd2: [
            {validator: checkNewPwd2, trigger: 'blur'}
          ]
        },
        select: '',
        num1: ''
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
        this.msgForm.imageUrl = res.data[0].path
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
      saveMsgForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            let accessToken = window.localStorage.getItem('JobsAccessToken')
            let data = new URLSearchParams()
            data.append('headPicUrl', this.msgForm.imageUrl)
            data.append('name', this.msgForm.companyName)
            data.append('introduce', this.msgForm.companyDesc)
            data.append('workLive', this.msgForm.contact)
            this.axios.post('/app/user/update', data, {
              headers: {'AppKey': 'JOB201902', 'AccessToken': accessToken}
            }).then(res => {
              if (res.data.code === 1) {
                this.$message.success(res.data.msg)
              } else {
                this.$message.error(res.data.msg)
              }
              console.log(res)
            }, err => {
              console.log(err)
            })
          } else {
            console.log('error submit!!')
            return false
          }
        })
      },
      saveAddForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            let accessToken = window.localStorage.getItem('JobsAccessToken')
            let data = new URLSearchParams()
            data.append('name', this.addForm.offerName)
            data.append('jobClassifyId', this.addForm.offerType.value)
            data.append('sexLimit', this.addForm.sexType)
            data.append('settleType', this.addForm.payType)
            data.append('salary', this.addForm.payVal.val)
            data.append('salaryUnit', this.addForm.payVal.type)
            data.append('peopleCountLimit', this.addForm.number)
            data.append('content', this.addForm.offerInfo)
            data.append('workTime', this.addForm.offerTime)
            data.append('address', this.addForm.location)
            /* 日志查看表单信息 */
            // console.log(this.addForm.offerName)
            // console.log(this.addForm.offerType.value)
            // console.log(this.addForm.sexType)
            // console.log(this.addForm.payType)
            // console.log(this.addForm.payVal.val + this.addForm.payVal.type)
            // console.log(this.addForm.number)
            // console.log(this.addForm.offerInfo)
            // console.log(this.addForm.offerTime)
            // console.log(this.addForm.location)

            /* 上传表单信息：新建招聘 */
            this.axios.post('/app/job/add', data, {
              headers: {'AppKey': 'JOB201902', 'AccessToken': accessToken}
            }).then(res => {
              this.$alert('已提交审核!', '提示', {
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
              headers: {'AppKey': 'JOB201902', 'AccessToken': accessToken}
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
              this.$alert('输入的原密码错误', '提示', {
                confirmButtonText: '确定'
              })
              return false
            }
            let accessToken = window.localStorage.getItem('JobsAccessToken')
            let data = new URLSearchParams()
            data.append('password', md5(this.pwdForm.newPwd).toUpperCase())
            this.axios.post('/app/user/update', data, {
              headers: {'AppKey': 'JOB201902', 'AccessToken': accessToken}
            }).then(res => {
              this.$alert('密码修改成功', '提示', {
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
      }
    },
    mounted() {
      /* 将url的参数作为改变侧边栏index的方式之一 */
      this.sidebarIndex = this.$route.query.index.toString()

      /* 请求用户原表单的数据并写入表单 */
      let accessToken = window.localStorage.getItem('JobsAccessToken')
      if (accessToken !== null && accessToken !== undefined) {
        this.axios.post('/app/user/currUser', {}, {
          headers: {'AppKey': 'JOB201902', 'AccessToken': accessToken}
        }).then(res => {
          console.log('获取用户数据')
          console.log(res.data.data)
          let data = res.data.data

          /* 预览图，一个用于前台展示，一个用于后台传输 */
          this.imageUrl = data.headPicUrl
          this.msgForm.imageUrl = data.headPicUrlNoPrefix

          /* 公司名称 */
          this.msgForm.companyName = data.name
          /* 公司简介 */
          this.msgForm.companyDesc = data.introduce
          /* 联系方式 */
          this.msgForm.contact = data.workLive

          /* 存储当前的手机号 */
          this.telForm.sysTel = data.username

          /* 存储当前密码的密文，用于更改密码的校验 */
          this.pwdForm.sysPwd = data.password

          /* 存储id */
          this.id = data.id
          this.requestObj.createUserId = data.id
        }, err => {
          console.log(err)
        })
      }

      /* 获取所属分类列表 */
      this.axios.get('/app/jobClassify/list?page=1&rows=100&type=1').then(res => {
        console.log(res.data.data)
        res.data.data.rows.forEach(item => {
          this.addForm.offerType.options.push({
            'value': item.id,
            'label': item.type === 1 ? item.name + '(兼职)' : item.name + '(全职)'
          })
        })
      }, err => {
        console.log(err)
      })
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
    max-width: 300px;
  }

  .el-input-group__append {
    background: #fff;
  }

  .settings-main .job-list-contain {
    margin: -20px auto 0;
  }
</style>
