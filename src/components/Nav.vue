<template>
  <el-header>
    <router-link to="/">
      <img class="nav-logo" :src="logoPicUrl">
    </router-link>
    <el-menu :default-active="activeIndex" class="nav-list" :router="true" mode="horizontal" @select="navSelect">
      <el-menu-item index="/">首页</el-menu-item>
      <el-menu-item index="/list?type=1">兼职</el-menu-item>
      <!--<el-menu-item index="/list?type=2">全职</el-menu-item>-->
      <el-menu-item index="/aboutUs">关于我们</el-menu-item>
    </el-menu>
    <div class="nav-user" v-if="!isLogin">
      <span class="nav-user__span" @click="loginVisible=true">登录</span>|
      <span class="nav-user__span" @click="registerVisible=true">注册</span>
    </div>
    <div class="nav-user" v-else>
      <el-dropdown class="nav-user__span" @visible-change="isHover" @command="navCommand">
        <span class="el-dropdown-link">
          <router-link to="/">{{username|tel}}</router-link>
          <i :class="['el-icon--right',dropdown===false?'el-icon-arrow-down':'el-icon-arrow-up']"></i>
        </span>
        <el-dropdown-menu slot="dropdown" v-if="usertype===1">
          <router-link :to="{name:'StuSettings',query:{index:1}}">
            <el-dropdown-item>我的简历</el-dropdown-item>
          </router-link>
          <router-link :to="{name:'StuSettings',query:{index:2}}">
            <el-dropdown-item>投递历史</el-dropdown-item>
          </router-link>
          <router-link :to="{name:'StuSettings',query:{index:3}}">
            <el-dropdown-item>更换绑定</el-dropdown-item>
          </router-link>
          <router-link :to="{name:'StuSettings',query:{index:4}}">
            <el-dropdown-item>修改密码</el-dropdown-item>
          </router-link>
          <el-dropdown-item command="exit">退出账号</el-dropdown-item>
        </el-dropdown-menu>
        <el-dropdown-menu slot="dropdown" v-else>
          <router-link :to="{name:'HrSettings',query:{index:1}}">
            <el-dropdown-item>公司信息</el-dropdown-item>
          </router-link>
          <router-link :to="{name:'HrSettings',query:{index:2}}">
            <el-dropdown-item>发布历史</el-dropdown-item>
          </router-link>
          <router-link :to="{name:'ChatManage'}" target="_blank">
            <el-dropdown-item>咨询处理</el-dropdown-item>
          </router-link>
          <router-link :to="{name:'HrSettings',query:{index:6}}">
            <el-dropdown-item>收到的简历</el-dropdown-item>
          </router-link>
          <router-link :to="{name:'HrSettings',query:{index:3}}">
            <el-dropdown-item>新建招聘</el-dropdown-item>
          </router-link>
          <router-link :to="{name:'HrSettings',query:{index:4}}">
            <el-dropdown-item>更换绑定</el-dropdown-item>
          </router-link>
          <router-link :to="{name:'HrSettings',query:{index:5}}">
            <el-dropdown-item>修改密码</el-dropdown-item>
          </router-link>
          <el-dropdown-item command="exit">退出账号</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
    <el-dialog
      title="登录"
      :visible.sync="loginVisible"
      width="520px"
      :close-on-click-modal="false"
      :before-close="loginClose"
    >
      <el-form
        :model="loginForm"
        :rules="loginRules"
        ref="loginForm"
        label-width="80px"
        class="dialog-form"
      >
        <el-form-item label="手机号" prop="tel">
          <el-input placeholder="请输入手机号" v-model="loginForm.tel"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="pwd">
          <el-input placeholder="请输入密码" type="password" v-model="loginForm.pwd"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button class="button" type="primary" @click="submitLoginForm('loginForm')">登录</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <el-dialog
      title="注册"
      :visible.sync="registerVisible"
      width="520px"
      :close-on-click-modal="false"
      :before-close="registerClose"
    >
      <el-form
        :model="registerForm"
        :rules="registerRules"
        ref="registerForm"
        label-width="80px"
        class="dialog-form"
      >
        <el-form-item label="手机号" prop="tel">
          <el-input placeholder="请输入手机号" v-model="registerForm.tel"></el-input>
        </el-form-item>
        <el-form-item label="注册类型">
          <el-radio v-model="registerForm.type" label="1">求职者</el-radio>
          <el-radio v-model="registerForm.type" label="2">招聘者</el-radio>
        </el-form-item>
        <el-form-item label="密码" prop="pwd">
          <el-input placeholder="请输入密码" type="password" v-model="registerForm.pwd"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="pwdRepeat">
          <el-input placeholder="请再次输入密码" type="password" v-model="registerForm.pwdRepeat"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button class="button" type="primary" @click="submitRegisterForm('registerForm')">注册</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </el-header>
</template>

<script>
  import md5 from 'js-md5'

  export default {
    data() {
      let checkTel = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('手机号不能为空'))
        } else if (value.length !== 11) {
          callback(new Error('手机号格式不正确'))
        } else {
          callback()
        }
      }
      let checkPwd = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('密码不能为空'))
        } else if (value.length < 8 || value.length > 16) {
          callback(new Error('请输入8-16位密码'))
        } else {
          callback()
        }
      }
      let checkPwdRepeat = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'))
        } else if (value !== this.registerForm.pwd) {
          callback(new Error('两次输入密码不一致!'))
        } else {
          callback()
        }
      }
      return {
        loginVisible: false,
        registerVisible: false,
        activeIndex: '1',
        dropdown: false,
        isLogin: false,
        username: '',
        usertype: '',
        loginForm: {
          tel: '',
          pwd: ''
        },
        registerForm: {
          tel: '',
          type: '',
          pwd: '',
          pwdRepeat: ''
        },
        loginRules: {
          tel: [
            {validator: checkTel, trigger: 'blur'}
          ],
          pwd: [
            {validator: checkPwd, trigger: 'blur'}
          ]
        },
        registerRules: {
          tel: [
            {validator: checkTel, trigger: 'blur'}
          ],
          pwd: [
            {validator: checkPwd, trigger: 'blur'}
          ],
          pwdRepeat: [
            {validator: checkPwdRepeat, trigger: 'blur'}
          ]
        }
      }
    },
    props: ['logoPicUrl'],
    filters: {
      tel(val) {
        return val.substr(0, 3) + '****' + val.substr(7)
      }
    },
    watch: {
      '$route'(to, from) {
        this.setActive(to.name)
      }
    },
    methods: {
      navSelect(key, keyPath) {
        console.log(key, keyPath)
      },
      isHover(params) {
        this.dropdown = params
      },
      navCommand(command) {
        if (command === 'exit') {
          window.localStorage.removeItem('JobsAccessToken')
          window.location.href = '/'
          this.isLogin = false
        }
      },
      loginClose(done) {
        this.$refs['loginForm'].resetFields()
        done()
      },
      registerClose(done) {
        this.$refs['registerForm'].resetFields()
        done()
      },
      submitLoginForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            let data = new URLSearchParams()
            data.append('username', this.loginForm.tel)
            data.append('pwd', md5(this.loginForm.pwd).toUpperCase())
            this.axios.post('/app/user/login', data).then(res => {
              window.localStorage.setItem('JobsAccessToken', res.data.data)
              window.location.href = '/'
            }, err => {
              console.log(err)
            })
            this.$refs['loginForm'].resetFields()
            this.loginVisible = false
          } else {
            console.log('error submit!!')
            return false
          }
        })
      },
      submitRegisterForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            let data = new URLSearchParams()
            data.append('username', this.registerForm.tel)
            data.append('password', md5(this.registerForm.pwd).toUpperCase())
            data.append('type', this.registerForm.type)
            this.axios.post('/app/user/register', data).then(res => {
              this.$alert('注册成功!', '提示', {
                confirmButtonText: '确定'
              })
            })
            this.$refs['registerForm'].resetFields()
            this.registerVisible = false
          } else {
            console.log('error submit!!')
            return false
          }
        })
      },
      setActive(name) {
        if (name === '/') {
          this.activeIndex = '/'
        }
        if (name === 'List') {
          this.activeIndex = '/list?type=1'
        }
        if (name === 'AboutUs') {
          this.activeIndex = '/aboutUs'
        }
      }
    },
    mounted() {
      let accessToken = window.localStorage.getItem('JobsAccessToken')
      if (accessToken !== null && accessToken !== undefined) {
        this.axios.post('/app/user/currUser', {}, {
          headers: {'AppKey': 'JOB201902', 'AccessToken': accessToken}
        }).then(res => {
          console.log(res)
          if (res.data.code === 1) {
            this.isLogin = true
            this.usertype = res.data.data.type
            this.username = res.data.data.username
          }
        }, err => {
          console.log(err)
        })
      }
    }
  }
</script>

<style lang="css">
  .el-header {
    width: 1200px;
    margin: auto;
    padding: 0;
  }

  .nav-logo {
    width: 150px;
    height: 50px;
    margin: 5px 50px 5px 0;
    float: left;
  }

  .nav-list {
    width: 500px;
    float: left;
  }

  .el-menu.el-menu--horizontal {
    border: 0;
  }

  .nav-user {
    float: right;
    height: 60px;
    color: #333;
    font-size: 14px;
  }

  .nav-user__span {
    line-height: 60px;
    cursor: pointer;
  }

  .nav-user__span:nth-child(1) {
    margin-right: 8px;
  }

  .nav-user__span:nth-child(2) {
    margin-left: 4px;
  }

  .nav-user__span:hover {
    color: #666;
  }

  .dialog-form .el-form-item {
    height: 60px;
  }

  .dialog-form {
    padding: 20px 50px 20px 10px;
  }

  .button {
    width: 100%;
  }
</style>
