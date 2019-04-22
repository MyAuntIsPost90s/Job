<template>
  <div class="home-main">
    <div class="home-block">
      <div class="block-hd">
        <div class="heading">
          <div class="heading-main">最新兼职</div>
          <div class="heading-desc">一句话文案编辑</div>
        </div>
        <div class="list">
          <ul class="list__ul">
            <li class="list__item" v-for="(item,index) in listItem" :key="index">
              <router-link :to="{name:'List',query:{jobClassifyId:item.id,type:1}}">{{item.name}}</router-link>
            </li>
          </ul>
          <router-link class="more" :to="{name:'List'}">
            查看更多
            <i class="el-icon-arrow-right"></i>
          </router-link>
        </div>
      </div>
      <ul class="block-bd">
        <router-link :to="{name:'Detail',query:{id:item.id}}" v-for="(item,index) in indexList.new" :key="index">
          <li class="info__item">
            <div class="info--sort">{{item.jobClassify.name}}</div>
            <div class="info--name">{{item.name}}</div>
            <ul class="info--msg">
              <li class="info--msg-item">{{item.jobClassify.type===1?'兼职':'全职'}}</li>
              <li class="info--msg-item">招聘{{item.peopleCountLimit}}人</li>
            </ul>
            <div class="info--pay">
              <div class="info--pay-price">
                <span class="info--pay-val">{{item.salary||'薪资面议'}}</span>
                {{item.salaryUnit}}
              </div>
              <div class="info--pay-type">{{getSettleType(item.settleType)}}</div>
            </div>
          </li>
        </router-link>
      </ul>
    </div>
    <div class="home-block">
      <div class="block-hd">
        <div class="heading">
          <div class="heading-main">人气兼职</div>
          <div class="heading-desc">一句话文案编辑</div>
        </div>
        <div class="list">
          <ul class="list__ul">
            <li class="list__item" v-for="(item,index) in listItem" :key="index">
              <router-link :to="{name:'List',query:{jobClassifyId:item.id,type:1}}">{{item.name}}</router-link>
            </li>
          </ul>
          <router-link class="more" :to="{name:'List'}">
            查看更多
            <i class="el-icon-arrow-right"></i>
          </router-link>
        </div>
      </div>
      <ul class="block-bd">
        <li class="info__item" v-for="(item,index) in indexList.hot" :key="index">
          <div class="info--sort">{{item.jobClassify.name}}</div>
          <div class="info--name">{{item.name}}</div>
          <ul class="info--msg">
            <li class="info--msg-item">{{item.jobClassify.type===1?'兼职':'全职'}}</li>
            <li class="info--msg-item">招聘{{item.peopleCountLimit}}人</li>
          </ul>
          <div class="info--pay">
            <div class="info--pay-price">
              <span class="info--pay-val">{{item.salary||'薪资面议'}}</span>
              {{item.salaryUnit}}
            </div>
            <div class="info--pay-type">{{getSettleType(item.settleType)}}</div>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        listItem: []
      }
    },
    props: ['indexList'],
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
    mounted() {
      /* 兼职列表项 */
      this.axios.get('/app/jobClassify/list?page=1&rows=8&type=1').then(res => {
        this.listItem = res.data.data.rows
      }, err => {
        console.log(err)
      })
    }
  }
</script>

<style lang="css">
  .home-block {
    width: 1160px;
    background: #fff;
    margin: 30px auto 0;
    padding: 0 20px 20px;
    color: #333;
  }

  .block-hd {
    padding: 30px 0 15px;
    display: flex;
    justify-content: space-between;
    align-items: flex-end;
    border-bottom: 1px solid #eee;
  }

  .heading {
    width: 200px;
  }

  .heading-main {
    font-size: 36px;
    font-weight: bold;
    margin-bottom: 8px;
  }

  .heading-desc {
    font-size: 14px;
    color: #999;
  }

  .list {
    display: flex;
  }

  .list__ul {
    color: #666;
    display: flex;
  }

  .list__item {
    margin-right: 24px;
  }

  .more {
    font-weight: bold;
  }

  .list__item:hover,
  .more:hover,
  .info__item:hover .info--name {
    color: #409eff;
  }

  .block-bd {
    display: flex;
    flex-wrap: wrap;
  }

  .info__item {
    width: 248px;
    height: 140px;
    border: 1px solid #eee;
    padding: 40px 20px;
    margin: -1px -1px 0 0;
  }

  .info__item:hover {
    box-shadow: 0 0 12px 0 rgba(0, 0, 0, 0.12);
  }

  .info--sort {
    line-height: 1;
    border-left: 3px solid #ccc;
    padding-left: 9px;
    font-size: 14px;
    color: #999;
  }

  .info--name {
    margin: 10px 0;
    font-size: 18px;
    font-weight: bold;
    color: #4b4b4b;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }

  .info--msg {
    display: flex;
  }

  .info--msg-item {
    line-height: 1;
    margin: 4px 0;
    font-size: 14px;
    color: #999;
  }

  .info--msg-item:nth-child(1) {
    padding-right: 10px;
    border-right: 1px solid #999;
    margin-right: 10px;
  }

  .info--pay {
    display: flex;
    justify-content: space-between;
    align-items: flex-end;
    font-size: 12px;
    margin-top: 30px;
  }

  .info--pay-price {
    color: #f60;
  }

  .info--pay-val {
    font-size: 16px;
    font-weight: bold;
  }

  .info--pay-type {
    border: 1px solid #ccc;
    color: #999;
    padding: 0 2px;
  }
</style>
