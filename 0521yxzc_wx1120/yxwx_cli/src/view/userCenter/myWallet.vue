<template>
    <div class="wrap">
        <!-- 账户余额 -->
        <div class="myWallet">
            <div class="left">
                <p>账户余额</p>
                <p>￥<span class="money">{{balance}}</span></p>
            </div>
            <!-- <div class="right">
                <a href="JavaScript:void(0)" class="btn">充值</a>
                <a href="JavaScript:void(0)" class="btn">明细</a>
            </div> -->
        </div>
        <ul class="menuList">
            <!-- <router-link to="/myCoupon"> -->
              <!-- 电子券 -->
              <a href="/wechat/view/myCoupon">
                <li class="item coupon">
                    <span>电子券</span>
                    <!-- <span class="count">12张</span> -->
                </li>
              </a>
            <!-- </router-link> -->
            <!-- 发票 -->
            <router-link to="/invoice">
                <li class="item invoice">
                    <p>发票</p>
                </li>
            </router-link>
        </ul>
    </div>
</template>
<script>
import { mapState } from 'vuex'
export default {
  mounted () {
    this.$store.dispatch('set_Balance')
  },
  computed: mapState({
    balance (state) {
      return state.balance
    }
  })
}
</script>
<style lang="less" scoped>
.wrap {
  position: fixed;
  width: 100%;
  height: 100%;
  background: #f3f3f3;
  .myWallet {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 35px 0;
    background: #00b5e6;
    color: #ffffff;
    .left {
      padding: 0 40px;
      line-height: 1.23;
      font-size: 14px;
      p {
        span.money {
          font-size: 32px;
        }
      }
    }
    .right {
      display: flex;
      flex-direction: column;
      padding: 0 20px;
      .btn {
        position: relative;
        margin-bottom: 10px;
        padding: 4px 25px;
        line-height: 1.5;
        font-size: 14px;
        color: #00b5e6;
        background: #ffffff;
        border-radius: 4px;
      }
    }
  }
  .menuList {
    position: relative;
    display: block;
    background: #ffffff;
    padding-left: 15px;
    font-size: 14px;
    border-bottom: -1px ;
    li {
      position: relative;
      display: flex;
      justify-content: space-between;
      align-items: center;
      height: 50px;
      padding-left: 35px;
      padding-right: 35px;
      color: #4f5a67;
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
      span.count {
        color: #9ea7b1;
      }
      &.coupon{
        background: url('./images/my_icon_coupon.png') no-repeat left center;
        background-size: 18px 18px;
      }
      &.invoice{
        background: url('./images/my_icon_invoice.png') no-repeat left center;
        background-size: 18px 18px;
      }
    }
    a {
      display: block;
      &:not(:last-child) {
        border-bottom: .5px solid #f3f3f3;
      }
    }
  }
}


</style>
