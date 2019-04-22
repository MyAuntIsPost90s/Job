<template>
  <div class="type">
    <div class="type-item">
      <div class="type-label">职位：</div>
      <ul class="type-list">
        <li
          :class="['type-list__item',{'type-list__item--active':typeIndex===-1}]"
          @click="changeTypeIndex(-1,-1)"
        >全部
        </li>
        <li
          :class="['type-list__item',{'type-list__item--active':typeIndex===item.id}]"
          v-for="(item,index) in listItem"
          :key="index"
          @click="changeTypeIndex(item.id,item.id)"
        >{{item.name}}
        </li>
      </ul>
    </div>
    <div class="type-item">
      <div class="type-label">结算方式：</div>
      <ul class="type-list">
        <li
          :class="['type-list__item',{'type-list__item--active':payIndex===0}]"
          @click="changePayIndex(0,-1)"
        >全部
        </li>
        <li
          :class="['type-list__item',{'type-list__item--active':payIndex===1}]"
          @click="changePayIndex(1,1)"
        >日结
        </li>
        <li
          :class="['type-list__item',{'type-list__item--active':payIndex===2}]"
          @click="changePayIndex(2,2)"
        >半月结
        </li>
        <li
          :class="['type-list__item',{'type-list__item--active':payIndex===3}]"
          @click="changePayIndex(3,3)"
        >月结
        </li>
      </ul>
    </div>
    <div class="type-item">
      <div class="type-label">性别要求：</div>
      <ul class="type-list">
        <li
          :class="['type-list__item',{'type-list__item--active':sexIndex===0}]"
          @click="changeSexIndex(0,-1)"
        >不限
        </li>
        <li
          :class="['type-list__item',{'type-list__item--active':sexIndex===1}]"
          @click="changeSexIndex(1,1)"
        >男
        </li>
        <li
          :class="['type-list__item',{'type-list__item--active':sexIndex===2}]"
          @click="changeSexIndex(2,0)"
        >女
        </li>
      </ul>
    </div>
    <div class="type-item">
      <div class="type-label">排序方式：</div>
      <ul class="type-list">
        <li
          :class="['type-list__item',{'type-list__item--active':sortIndex===0}]"
          @click="changeSortIndex(0,1)"
        >最新发布
        </li>
        <li
          :class="['type-list__item',{'type-list__item--active':sortIndex===1}]"
          @click="changeSortIndex(1,2)"
        >最热职位
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        typeIndex: -1,
        payIndex: 0,
        sexIndex: 0,
        sortIndex: 0,
        listItem: [],
        jobClassifyId: -1,
        settleType: -1,
        sexLimit: -1,
        orderType: 1,
        type: -1
      }
    },
    watch: {
      '$route.query'() {
        this.setQuery()
      }
    },
    methods: {
      changeTypeIndex(index, value) {
        this.typeIndex = index
        this.jobClassifyId = value
        this.jump()
      },
      changePayIndex(index, value) {
        this.payIndex = index
        this.settleType = value
        this.jump()
      },
      changeSexIndex(index, value) {
        this.sexIndex = index
        this.sexLimit = value
        this.jump()
      },
      changeSortIndex(index, value) {
        this.sortIndex = index
        this.orderType = value
        this.jump()
      },
      getList() {
        /* 兼职列表项 */
        this.axios.get(`/app/jobClassify/list?page=1&rows=100&type=${this.$route.query.type}`).then(res => {
          this.listItem = res.data.data.rows
        }, err => {
          console.log(err)
        })
      },
      jump() {
        let str = '?'
        if (this.jobClassifyId !== -1) {
          str += `jobClassifyId=${this.jobClassifyId}&`
        }
        if (this.settleType !== -1) {
          str += `settleType=${this.settleType}&`
        }
        if (this.sexLimit !== -1) {
          str += `sexLimit=${this.sexLimit}&`
        }
        if (this.orderType !== -1) {
          str += `orderType=${this.orderType}&`
        }
        if (this.type !== -1) {
          str += `type=${this.type}&`
        }
        str = str.slice(0, -1)
        console.log(str)
        this.$router.push({path: `/list${str}`})
      },
      setQuery() {
        this.jobClassifyId = this.$route.query.jobClassifyId || this.jobClassifyId
        this.settleType = this.$route.query.settleType || this.settleType
        this.sexLimit = this.$route.query.sexLimit || this.sexLimit
        this.orderType = this.$route.query.orderType || this.orderType
        this.type = this.$route.query.type || this.type
      }
    },
    mounted() {
      this.setQuery()
      this.getList()
      this.changeTypeIndex(this.jobClassifyId,this.jobClassifyId)
    }
  }
</script>

<style lang="css">
  .type {
    width: 1160px;
    background: #fff;
    padding: 20px;
    color: #333;
    margin: auto;
  }

  .type-item {
    display: flex;
  }

  .type-item:last-child .type-list__item {
    margin-bottom: 0;
  }

  .type-label {
    width: 80px;
    margin-right: 10px;
    text-align: right;
    font-size: 14px;
  }

  .type-list {
    display: flex;
    flex-wrap: wrap;
    width: 1070px;
    font-size: 12px;
  }

  .type-list__item {
    padding: 2px 4px;
    margin-right: 6px;
    margin-bottom: 10px;
    cursor: pointer;
  }

  .type-list__item:hover {
    color: #409eff;
  }

  .type-list__item--active {
    background: #409eff;
    color: #fff !important;
  }
</style>
