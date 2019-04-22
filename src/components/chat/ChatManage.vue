<template>
  <div class="settings">
    <div class="settings-contain">
      <el-table
        :data="tableData"
        style="width: 100%">
        <el-table-column
          prop="name"
          label="姓名"
          width="180">
        </el-table-column>
        <el-table-column
          prop="msg"
          label="信息">
        </el-table-column>
        <el-table-column
          prop="date"
          label="时间">
        </el-table-column>
        <el-table-column
          prop="userId"
          label="操作">
          <template slot-scope="scope">
            <el-button @click="openChat(scope.row.userId)">回复</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
  let currUser;
  let websocket;

  export default {
    name: "ChatManage",
    data() {
      return {
        tableData: []
      }
    },
    mounted() {
      this.getCurrUser();
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
      openChat(targetUserId) {
        const {href} = this.$router.resolve({
          path: '/chat',
          query: {
            targetUserId: targetUserId
          }
        })
        window.open(href)
      },
      initChat(userId) {
        if ('WebSocket' in window) {
          websocket = new WebSocket("ws://localhost:8084/chatSession?key=" + userId)
          console.log(websocket)
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
            if (data.currKey != userId) {
              //写入
              for (let i = 0; i < this.tableData.length; i++) {
                if (this.tableData[i].userId == data.currKey) {
                  this.tableData.splice(i, 1)
                }
              }
              this.tableData.push({
                date: new Date().toLocaleString(),
                msg: data.content,
                userId: data.currKey,
                name: data.currName
              })
            }
          }
        } else {
          this.$message.error('当前浏览器 不支持 websocket ！！！')
        }
      }
    }
  }
</script>

<style scoped>
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
    overflow-y: auto;
  }

  .div-chat-user {
    margin: 10px;
    width: 100%;
  }
</style>
