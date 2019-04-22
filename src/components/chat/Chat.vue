<template>
  <div class="settings">
    <div class="settings-contain">
      <div class="chat-head">
        <img class="chat-img"
             :src="targetUser.headPicUrl">
        <div class="chat-user">
          {{targetUser.name}}
        </div>
      </div>
      <div class="chat-contain">
        <div :class="item.type" v-for="(item,index) in chats" :key="index">
          <div class="content">
            <div class="date">{{ item.date }}</div>
            <div class="msg">{{ item.msg }}</div>
          </div>
        </div>
      </div>
      <div class="chat-text">
        <el-input placeholder="请输入内容" v-model="content" class="input-with-select">
          <el-button slot="append" @click="sendMsg">发送</el-button>
        </el-input>
      </div>
    </div>
  </div>
</template>

<script>
  let currUser;
  let websocket;
  let targetUserId;

  export default {
    name: "ChatManage",
    data() {
      return {
        content: null,
        chats: [],
        targetUser: {},
      }
    },
    mounted() {
      targetUserId = this.$route.query.targetUserId
      if (!targetUserId) {
        this.$message.error('初始化失败')
        return
      }
      this.getTargetUser()
      this.getCurrUser()
    },
    methods: {
      getCurrUser() {
        let accessToken = window.localStorage.getItem('JobsAccessToken')
        if (accessToken !== null && accessToken !== undefined) {
          this.axios.post('/app/user/currUser', {}, {
            headers: {'AppKey': 'JOB201902', 'AccessToken': accessToken}
          }).then(resp => {
            currUser = resp.data.data
            this.initChat(currUser.id)
          })
        }
      },
      getTargetUser() {
        let accessToken = window.localStorage.getItem('JobsAccessToken')
        if (accessToken !== null && accessToken !== undefined) {
          let data = new URLSearchParams()
          data.append('id', targetUserId)
          this.axios.post('/app/user/detail', data, {
            headers: {'AppKey': 'JOB201902', 'AccessToken': accessToken}
          }).then(resp => {
            this.targetUser = resp.data.data
          })
        }
      },
      initChat(userId) {
        if ('WebSocket' in window) {
          websocket = new WebSocket("ws://localhost:8084/chatSession?key=" + userId)
          setInterval(function () {
            websocket.send("pong")
          }, 3000);

          //接收到消息的回调方法
          websocket.onmessage = event => {
            let data = JSON.parse(event.data)
            if (data.code == 0) {
              this.$message.error(data.errMsg)
              return;
            }
            //写入
            if (data.currKey == currUser.id && data.targetKey == targetUserId) {
              this.chats.push({date: new Date().toLocaleString(), msg: data.content, type: 'me'})
            }
            if (data.targetKey == currUser.id && data.currKey == targetUserId) {
              this.chats.push({date: new Date().toLocaleString(), msg: data.content, type: 'other'})
            }
          }
        } else {
          this.$message.error('当前浏览器 不支持 websocket ！！！')
        }
      },
      sendMsg() {
        if (currUser == null || currUser.id == null || targetUserId == null) {
          this.$message.error('无法发送，初始化失败')
          return
        }
        if (currUser.id == targetUserId) {
          this.$message.error('无法对自己发送')
          return
        }
        if (this.content == '' || this.content == null) {
          this.$message.error('输入的内容不能为空')
          return
        }
        websocket.send(JSON.stringify({
          currKey: currUser.id
          , targetKey: targetUserId, content: this.content
          , currName: currUser.name
          , targetName: this.targetUser.name
        }))
        this.content = ''
      }
    }
  }
</script>

<style scoped>
  .chat-img {
    float: left;
    width: 50px;
    height: 50px;
  }

  .chat-user {
    float: left;
    color: #606266;
    font-size: 14px;
    padding: 12px 10px;
  }

  .chat-contain {
    padding: 10px;
    border-bottom: 1px solid #e8e8e8;
    overflow-y: auto;
    height: 500px;
  }

  .chat-text {
    padding: 10px;
  }

  .chat-head {
    padding: 10px;
    border-bottom: 1px solid #e8e8e8;
    height: 50px;
  }

  .settings {
    background: #f5f5f5;
    padding: 20px;
  }

  .settings-contain {
    width: 1200px;
    height: 820px;
    background: #fff;
    margin: auto;
  }

  .chat-contain .content {
    max-width: 60%;
    width: auto;
    display: inline-block;
    padding: 10px;
  }

  .chat-contain .content .date {
    font-size: 12px;
    color: #909399;
  }

  .chat-contain .content .msg {
    padding: 10px;
    color: #fff;
    border-radius: 10px;
    text-align: left;
  }

  .chat-contain .me .content .msg {
    background-color: #409EFF;
  }

  .chat-contain .other .content .msg {
    background-color: #67C23A;
  }

  .chat-contain .me {
    text-align: left;
  }

  .chat-contain .other {
    text-align: right;
  }
</style>
