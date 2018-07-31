<template>
    <transition enter-active-class="animated slideInRight" leave-active-class="animated slideOutRight">
        <div class="wrap">
            <!-- 主要内容 -->
            <div class="content">
                <!-- <label class="labelMsg">有{{mytripList.length}}个可用电子券</label> -->
                <scroll class="wrapper" v-bind:pullup="true" v-on:scrollToEnd="loadMoreList">
                    <ul>
                        <li v-for="(item,index) in mytripList" :class="{yellow:item.type}">
                            <label class="item">
                                <!-- radio美化组件 start -->
                                <!-- 前面的radio -->
                                <div class="ui-checkbox-label">
                                    <label>
                                        {{item.des}}
                                        <input type="checkbox" name="coupon" v-model="picked" :value="item.uuid">
                                        <span class="ui-checkbox-span">
                                    <span class="ui-checkbox-inner"></span>
                                        </span>
                                    </label>
                                </div>
                                <!-- radio美化组件 end -->
                                <div class="itemMsg">
                                    <div>
                                        <!-- 日期 -->
                                        <p class="date">{{item.deparTime | Dateformt}} <span class="carType">{{item.orderType | orderTypeFormt}}</span></p>
                                        <!-- 开始地点 -->
                                        <p class="from">{{item.originAddress}}</p>
                                        <!-- 结束地点 -->
                                        <p class="to">{{item.destAddress}}</p>
                                    </div>
                                    <!-- 多少钱 -->
                                    <div class="totalFare"><span>{{item.payFare}}</span>元</div>
                                </div>
                            </label>
                        </li>
                        <!-- 提示 -->
                        <div class="loading">
                            <cube-loading v-show="show"></cube-loading>
                            <p v-show="onmore">已经到底啦</p>
                        </div>
                    </ul>
                </scroll>
            </div>
            <!-- 底部操作栏 -->
            <div class="submitBar">
                <!-- 左 -->
                <label class="allCheckCom">
                    <!-- radio美化组件 start -->
                    <div class="ui-checkbox-label" @click="pickAllitem()">
                        <label>
                            <input type="checkbox" name="coupon" v-model="allPick">
                            <span class="ui-checkbox-span">
                          <span class="ui-checkbox-inner"></span>
                            </span>
                        </label>
                    </div>
                    <!-- radio美化组件 end -->
                    <span>全选</span>
                </label>
                <!-- <label>{{picked.length}}个行程</label> -->
                <!-- 中间 -->
                <label>
                  <p class="countMoney">开票总额<span>￥{{countMoney}}</span></p>
                  <p class="msg">满￥300包邮</p>
                </label>
                <!-- 右边 -->
                <cube-button :inline="true" @click="nextStep">下一步</cube-button>
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
      let Minutes = time.getMinutes() < 10 ? '0' + time.getMinutes() : time.getMinutes()
      return time.getMonth() + 1 + '月' + time.getDate() + '日' + ' ' + time.getHours() + ':' + Minutes
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
    // 获取mytripList这个变量
    this.$store.dispatch('set_MytripList', 1)
  },
  data () {
    return {
      allPick: false,
      available: this.$store.state.available,
      picked: this.$store.state.PicktripList,
      countMoney: this.$store.state.countMoney,
      page: 1,
      show: false,
      onmore: false
    }
  },
  watch: {
    // 行程选择监听
    picked (val) {
      let self = this
      // 利用长度检测全选与否
      if (this.mytripListUuid.length !== val.length) {
        this.allPick = false
      }
      // 遍历计价
      let sub = 0
      val.forEach(function (el, index) {
        self.mytripList.forEach(function (subEl, subIndex) {
          if (el === subEl.uuid) {
            sub += subEl.payFare
          }
        })
      })
      self.countMoney = sub
    },
    // 开票金额监听
    countMoney (val) {
      if (val > this.available) {
        this.picked.pop()
        this.allPick = false
        let toast = this.$createToast({
          time: 1000,
          type: 'warn',
          mask: true,
          txt: '已超出可开票额度，请重新选择'
        })
        toast.show()
      }
      if (val >= 10000) {
        this.$store.commit('SET_TICKETTYPE', 1) // 按行程开票
        this.$store.commit('SET_INVOICETYPE', 2)
        let toast = this.$createToast({
          time: 1000,
          type: 'warn',
          txt: '已超出10000元，默认为您选择纸质发票'
        })
        toast.show()
      }
    }
  },
  computed: mapState({
    mytripListUuid (state) {
      let uuidList = []
      state.MytripList.forEach(function (el, index) {
        uuidList.push(el.uuid)
      })
      return uuidList
    },
    mytripList (state) {
      if (this.picked.length !== this.mytripListUuid.length) {
        this.allPick = false
      }
      return state.MytripList
    }
  }),
  methods: {
    // 分页加载
    loadMoreList () {
      this.show = true
      this.onmore = false
      this.page++
      this.$store.dispatch('set_MytripList', this.page).then((res) => {
        if (res) {
          this.show = false
        } else {
          this.show = false
          this.onmore = true
        }
      })
    },
    // 全选
    pickAllitem () {
      this.allPick = !this.allPick
      if (this.allPick) {
        this.picked = []
      } else {
        this.picked = this.mytripListUuid
      }
    },
    // 路由跳转
    nextStep () {
      this.$store.commit('SET_TICKETTYPE', 1) // 按行程开票
      this.$store.commit('SET_PICKTRIPLIST', this.picked)
      this.$store.commit('SET_COUNTMONEY', this.countMoney)
      this.$router.push('invoiceForm')
    }
  },
  components: {
    scroll
  }
}
</script>
<style lang="less" scoped>
@import "../../assets/css/less/variable.less";
@50px: 50px;
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
    margin-bottom: 50px;
    .wrapper {
      position: absolute;
      height: calc(~'100% -' @50px);
      width: calc(~'100% -' @20px);
      overflow: hidden;
      ul {
        position: relative;
        display: block;
        overflow: hidden;
        .loading {
          display: flex;
          justify-content: center;
          padding: 15px 0;
          p {
            font-size: 12px;
            color: #9ea7b1;
          }
        }
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
  .submitBar {
    z-index: 11;
    position: fixed;
    display: flex;
    justify-content: space-between;
    align-items: center;
    box-sizing: border-box;
    width: 100%;
    bottom: 0;
    background: #ffffff;
    padding: 10px 15px;
    color: #666;
    font-size: 14px;
    .countMoney{
      margin-top: -10px;
      color: #7A7C81;
      font-size: 16px;
      line-height: 1.5;
      span{
        color: @fc_active;
      }
    }
    .msg {
      margin-bottom: -15px;
      text-align: right;
      font-size: 12px;
      color: #9ea7b1; // line-height: 1.8;
    }
    .allCheckCom {
      display: flex;
      align-items: center;
    }
    .none{
      position: absolute;
      visibility: hidden;
      display: block;
      z-index: -999;
    }
  }
}

// radio美化
.ui-checkbox-label {
  position: relative;
  padding: 0 15px;
  background: #ffffff;
  box-sizing: border-box;
  label {
    position: relative;
    display: flex;
    -webkit-box-align: center;
    align-items: center;
    box-sizing: border-box;
    width: 100%;
    height: 100%;
    padding: 11px 0;
    color: #4f5a67;
    line-height: 1.5;
    word-break: break-word;
    word-wrap: break-word;
  }
  input[type='checkbox'] {
    z-index: 1;
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    opacity: 0;
  }
  input[type='checkbox']:checked+.ui-checkbox-span {
    .ui-checkbox-inner {
      background: #00b5e6;
      border: 1px solid #00b5e6;
      &:after {
        transform: rotate(45deg) scale(1);
      }
    }
  }
  .ui-checkbox-span {
    position: absolute;
    right: 0;
    cursor: pointer;
    .ui-checkbox-inner {
      display: inline-block;
      width: 16px;
      height: 16px;
      border-radius: 50%;
      position: relative;
      top: 0;
      left: 0;
      border: 1px solid #dddee1;
      background-color: #fff;
      transition: border-color .2s ease-in-out, background-color .2s ease-in-out;
      &:after {
        content: '';
        display: table;
        position: absolute;
        width: 4px;
        height: 8px;
        z-index: 99;
        top: 3px;
        left: 6px;
        border: 1.5px solid #fff;
        border-top: 0;
        border-left: 0;
        transform: rotate(45deg) scale(0);
        transition: all .2s ease-in-out;
      }
    }
  }
}

</style>


