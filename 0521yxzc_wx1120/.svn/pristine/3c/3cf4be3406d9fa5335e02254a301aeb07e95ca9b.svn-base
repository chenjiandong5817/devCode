<template>
    <transition enter-active-class="animated slideInRight" leave-active-class="animated slideOutRight">
        <div class="wrap">
            <div class="content">
                <scroll class="wrapper">
                    <ul>
                        <li v-for="(item,index) in TripList" :class="{yellow:item.type}">
                            <label class="item">
                                <div class="itemMsg">
                                    <div>
                                        <p class="date">{{item.deparTime | Dateformt}} <span class="carType">{{item.orderType | orderTypeFormt}}</span></p>
                                        <p class="from">{{item.originAddress}}</p>
                                        <p class="to">{{item.destAddress}}</p>
                                    </div>
                                    <div class="totalFare"><span>{{item.payFare}}</span>元</div>
                                </div>
                            </label>
                        </li>
                    </ul>
                </scroll>
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
      let time = new Date(val)
      return time.getMonth() + 1 + '月' + time.getDate() + '日' + ' ' + time.getHours() + ':' + time.getMinutes()
    },
    orderTypeFormt (val) {
      switch (val) {
        case 1:
          return '预约'
        case 2:
          return '接机'
        case 5:
          return '立即用车'
        default:
          return '专车'
      }
    }
  },
  mounted () {
    this.uuid = this.$route.params.uuid
    // this.uuid = this.$route.query.uuid
    /* this.$store.dispatch('get_invoiceHistoryTrip', this.$route.params.uuid).then((res) => {
      if (res.success) {
        this.TripList = res.data
      }
    }) */
  },
  data () {
    return {
      uuid: this.$route.params.uuid,
      // uuid: this.$route.query.uuid,
      page: 1,
      show: false,
      onmore: false
      // TripList: []
    }
  },
  computed: mapState({
    // 订单行程说明查询
    TripList (state) {
      let self = this
      let data
      state.invoiceHistory.forEach(function (el, index) {
        if (self.uuid === el.invoiceUuid) {
          data = el
        }
      })
      return data.orderList
    }
  }),
  components: {
    scroll
  }
}
</script>
<style lang="less" scoped>
@import "../../assets/css/less/variable.less";
@20px: 20px;
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
    padding: 0 10px;
    .wrapper {
      position: absolute;
      height: 100%;
      width: calc(~'100% -' @20px);
      overflow: hidden;
      ul {
        position: relative;
        display: block;
        overflow: hidden;
      }
    }
    li {
      margin-top: 10px;
      .boxShadow();
      &:last-child {
        margin-bottom: 10px;
      }
    }
    label.item {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 15px;
      background-color: #ffffff;
    }
    .itemMsg {
      position: relative;
      display: flex;
      justify-content: space-between;
      align-items: center;
      box-sizing: border-box;
      width: 100%;
      font-size: 13px;
      color: #4f5a67;
      line-height: 1.8;
      p {
        position: relative;
        padding-left: 15px;
      }
      .from {
        &:before {
          content: '';
          position: absolute;
          width: 5px;
          height: 5px;
          top: 50%;
          left: 0;
          margin-top: -2.5px;
          border-radius: 50%;
          background: #22cedb;
        }
      }
      .to {
        &:before {
          content: '';
          position: absolute;
          width: 5px;
          height: 5px;
          top: 50%;
          left: 0;
          margin-top: -2.5px;
          border-radius: 50%;
          background: #f99358;
        }
      }
      .date {
        color: #9ea7b1;
        &:before {
          content: '';
          position: absolute;
          width: 8px;
          height: 8px;
          top: 50%;
          left: 0;
          margin-top: -4px;
          background: url('../../assets/images/clock.png') no-repeat center;
          background-size: 100% 100%;
        }
        span.carType {
          padding: 2px 10px;
          border-radius: 2px;
          border: .5px solid #f3f3f3;
          font-size: 10px;
        }
      }
      .totalFare {
        font-size: 14px;
        color: #4f5a67;
        span {
          color: #00b5e6;
        }
      }
    }
  }
}

</style>


