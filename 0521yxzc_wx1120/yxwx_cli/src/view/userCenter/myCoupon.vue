<template>
    <transition enter-active-class="animated slideInRight" leave-active-class="animated slideOutRight">
        <div class="wrap">
            <div class="content">
                <!-- <label class="labelMsg">有{{couponList.length}}个可用电子券</label> -->
                <scroll class="wrapper" v-bind:pullup="true" v-on:scrollToEnd="loadMoreList" v-if="couponList.length">
                <ul>
                    <li class="item" v-for="(item,index) in couponList" :class="{yellow:item.type}">
                        <label>
                            <div class="itemMsg">
                                <p class="title">
                                    {{item.title}}</p>
                                <p class="date">有效期至 {{item.date | Dateformt}}</p>
                                <span class="value" v-if="item.type ==1">{{item.discount}} <i>折</i></span>
                                <span class="value" v-else>{{item.money}} <i>元</i></span>
                            </div>
                            <div class="checkRadio">
                                <!-- radio美化组件 start -->
                                <div class="ui-radio-label">
                                    <label>
                                        {{item.des}}
                                    </label>
                                </div>
                                <!-- radio美化组件 end -->
                            </div>
                        </label>
                    </li>
                        <div class="loading">
                            <cube-loading v-show="show"></cube-loading>
                            <p v-show="onmore">已经到底啦</p>
                        </div>
                </ul>
                </scroll>
                <div class="noHistory" v-if="!couponList.length">
                  <i class="img"></i>
                  <p>暂无优惠券</p>
                </div>
            </div>
            <!-- <div class="submitBar">
              已选择: {{title}}
              <div>
              <cube-button :inline="true" :light="true" @click="goBack()">取消</cube-button>
              <cube-button :inline="true" @click="setCoupon()">确认</cube-button>
            </div>
            </div> -->
        </div>
    </transition>
</template>
<script>
import { mapState } from 'vuex'
const scroll = () => import('@/components/scroll')
export default{
  filters: {
    Dateformt (val) {
      if (val) {
        let time = new Date(val)
        return time.getFullYear() + '-' + (time.getMonth() + 1) + '-' + time.getDate()
      } else {
        return ''
      }
    }
  },
  mounted () {
    this.$store.dispatch('set_MyCouponList', 1)
  },
  data () {
    return {
      page: 1,
      show: false,
      onmore: false
    }
  },
  computed: mapState({
    couponList (state) {
      return state.MycouponList
    }
  }),
  methods: {
    loadMoreList () {
      this.show = true
      this.onmore = false
      this.page++
      this.$store.dispatch('set_MyCouponList', this.page).then((res) => {
        if (res) {
          this.show = false
        } else {
          this.show = false
          this.onmore = true
        }
      })
    },
    goBack () {
      this.$router.go(-1)
    }
  },
  components: {
    scroll
  }
}
</script>
<style lang="less" scoped>
@import "../../assets/css/less/variable.less";
@20px: 20px;
.wrap {
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
    width: 100%;
    height: 100%;
    flex-grow: 1;
    padding: 10px;
    .labelMsg {
      line-height: 2;
      font-size: 12px;
      color: #a9abb0;
    }
    .wrapper {
      position: absolute;
      height: 100%;
      width: calc(~'100% -' @20px);
      overflow: hidden;
      ul {
        position: relative;
        display: block;
        width: 100%;
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
    .item {
      position: relative;
      display: block; //width: 100%;
      line-height: 1.5;
      &:not(:first-child) {
        margin-top: 15px;
      }
      background-color: #ffffff;
      color: #57a1bc;
      background: url('../../assets/images/coupon-blue-bg.png') no-repeat;
      background-size: 100%;
      &.yellow {
        color: #d18500;
        background: url('../../assets/images/coupon-yellow-bg.png') no-repeat;
        background-size: 100%;
      }
      .itemMsg {
        position: relative;
        box-sizing: border-box;
        padding: 10px 15px;
        width: 100%;
        .title {
          font-size: 16px;
          font-weight: bold;
        }
        .date {
          font-size: 12px;
          color: #ffffff;
        }
        span.value {
          position: absolute;
          top: calc(50% - 36px);
          right: 15px;
          color: #ffffff;
          font-size: 32px;
          i {
            font-size: 14px;
          }
        }
      }
      .checkRadio {
        font-size: 12px;
        line-height: 12px;
        .ui-radio-label {
          border-radius: 0 0 6px 6px;
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



// radio美化
.ui-radio-label {
  position: relative;
  padding: 0 15px;
  background: #ffffff;
  width: 100%;
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
}

</style>

