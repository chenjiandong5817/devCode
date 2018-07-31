<template>
    <transition enter-active-class="animated slideInRight" leave-active-class="animated slideOutRight">
        <div class="wrap">
            <div class="content">
                <!-- 有内容 -->
                <scroll class="wrapper" v-bind:pullup="true" v-on:scrollToEnd="loadMoreList" v-if="invoiceHistory.length">
                    <ul>
                        <li v-for="(item,index) in invoiceHistory" @click="toInvoiceDes(item)">
                          <div class="itemMsg">
                            <p class="date">
                              <label>{{(item.ticketType==1)?'按行程开票':'按金额开票'}} {{item.createTime | Dateformt}}</label>
                              <span :class="'status'+item.status">{{item.status | statusFormt}}</span>
                            </p>
                            <p>金额：￥{{item.ordertotal}}</p>
                            <p>类型：{{(item.type==2)?'电子发票':'纸质发票'}}</p>
                            <p>抬头：{{item.buyername}}</p>
                            <!-- <p v-if="item.kptype===1">收件人邮箱：{{item.email}}</p> -->
                          </div>
                        </li>
                    <div class="loading">
                      <cube-loading v-show="show"></cube-loading>
                      <p v-show="onmore">已经到底啦</p>
                    </div>
                    </ul>
                </scroll>
                <!-- 无内容 -->
                <div class="noHistory" v-if="!invoiceHistory.length">
                  <i class="img"></i>
                  <p>暂无开票记录</p>
                </div>
            </div>
        </div>
    </transition>
</template>
<script>
import { mapState } from 'vuex'
const scroll = () => import('@/components/scroll')
export default{
  filters: {
    Dateformt (val) {
      if (typeof val === 'number') {
        let time = new Date(val)
        let Minutes = time.getMinutes() < 10 ? '0' + time.getMinutes() : time.getMinutes()
        return time.getMonth() + 1 + '月' + time.getDate() + '日' + ' ' + time.getHours() + ':' + Minutes
      } else return val
    },
    statusFormt (val) {
      switch (val) {
        case 1:
          return '开票中'
        case 2:
          return '已开票'
        case 3:
          return '退票中'
        default:
          return '已退票'
      }
    }
  },
  mounted () {
    this.$store.dispatch('set_invoiceHistory', 1)
  },
  data () {
    return {
      page: 1,
      show: false,
      onmore: false
    }
  },
  computed: mapState({
    invoiceHistory (state) {
      return state.invoiceHistory
    }
  }),
  methods: {
    // 分页加载
    loadMoreList () {
      this.show = true
      this.onmore = false
      this.page++
      this.$store.dispatch('set_invoiceHistory', this.page).then((res) => {
        if (res) {
          this.show = false
        } else {
          this.show = false
          this.onmore = true
        }
      })
    },
    // 路由跳转
    toInvoiceDes (item) {
      this.$router.push('invoiceHistoryDesc/' + item.invoiceUuid)
      /* this.$router.push({
        path: 'invoiceHistoryDesc',
        query: {uuid: item.orderno}
      }) */
    }
  },
  components: {
    scroll
  }
}
</script>
<style lang="less" scoped>
@import "../../assets/css/less/variable.less";
.wrap {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  z-index: 9;
  top: 0;
  bottom: 0;
  left: 0;
  position: fixed;
  overflow-y: scroll;
  overflow-x: hidden;
  width: 100%;
  height: 100%;
  background: #f3f3f3;
  animation-duration: .3s;
  &::-webkit-scrollbar {
    display: none;
  }
  .content {
    flex-grow: 1;
    .wrapper {
      position: absolute;
      height: 100%;
      width: 100%;
      overflow: hidden;
      ul {
        position: relative;
        display: block;
        overflow: hidden;
        padding-left: 15px;
        background: #ffffff;
      }
      .loading {
        display: flex;
        justify-content: center;
        p {
          line-height: 2;
          font-size: 12px;
          color: #9ea7b1;
        }
      }
    }
    li {
      padding: 15px 0;
      &:not(:last-child) {
        border-bottom: .5px solid #f3f3f3;
      }
    }
    .itemMsg {
      position: relative;
      box-sizing: border-box;
      width: 100%;
      font-size: 13px;
      color: #4f5a67;
      line-height: 1.8;
      p {
        color: #4f5a67;
        &.date {
          position: relative;
          display: flex;
          justify-content: space-between;
          padding-right: 30px;
          .status1{
            color: #00b5e6;
          }
          .status2{
            color: #9ea7b1;
          }
          .status3{
            color: #f16565;
          }
          .status4{
            color: #f16565;
          }
          &:after {
            content: '';
            position: absolute;
            border-right: 1px solid #c5c7ca;
            border-bottom: 1px solid #c5c7ca;
            width: 8px;
            height: 8px;
            transform: rotate(-45deg);
            top: 50%;
            margin-top: -4px;
            right: 15px;
          }
        }
        label {
          color: #9ea7b1;
        }
      }
    }
    .noHistory {
      position: absolute;
      width: 100%;
      top: 50%;
      transform: translateY(-50%);
      .img {
        display: block;
        margin: 0 auto;
        width: 100px;
        height: 100px;
        top: 50%;
        background: url('./images/icon_ticket.png') no-repeat center;
        background-size: 100% 100%;
      }
      p {
        padding: 15px 0;
        font-size: 16px;
        color: #a9abb0;
        text-align: center;
      }
    }

  }
}

</style>


