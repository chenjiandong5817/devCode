<!-- 预估价视图 -->
<template>
    <div class="estimate">
        <!-- 上部分 -->
        <router-link to="estimateMsg" append>
            <h2>约<span class="price">{{price | dataFloor}}</span>元</h2>
        </router-link>
        <!-- 下部分 -->
        <template v-if="payType">
            <router-link to="coupon" append>券已抵扣 <span class="coupon">{{coupon | dataFloor}}</span>元 &rsaquo;&rsaquo;</router-link>
        </template>
        <!-- 满足条件后出现 -->
        <p v-if="ifPrepaid &&(prepaidmoney >= 1)">匹配司机后，需预支付 <b class="prepaidmoney">{{prepaidmoney | dataFloor}}</b>元，请及时确认</p>
    </div>
</template>
<script>
import { mapState } from 'vuex'
export default{
  name: 'estimate',
  filters: {
    dataFloor (val) {
      return Math.floor(val) > 0 ? Math.floor(val) : 0
    }
  },
  computed: mapState({
    payType (state) {
      return state.payType.type === 2
    },
    // 劵
    coupon (state) {
      // return (state.estimateMsg.couponMoney && state.estimateMsg.couponMoney >= 0) ? state.estimateMsg.couponMoney : 0
      return state.estimateMsg.couponMoney ? state.estimateMsg.couponMoney : 0
    },
    // 钱
    price (state) {
      return this.payType ? (state.order.totalFee - this.coupon) : state.order.totalFee
    },
    ifPrepaid (state) {
      let nowDate = Date.parse(new Date())
      let departTime = state.order.departTime ? state.order.departTime : nowDate
      if (state.sysAreaConfigDto) {
        return ((departTime >= (nowDate + state.sysAreaConfigDto.prepaidTime * 60000) || state.sysAreaConfigDto.prepaidTime === 0) && state.sysAreaConfigDto.prepaidStatus && state.payType.type === 2)
      } else return false
      // return state.sysAreaConfigDto.prepaidStatus
    },
    prepaidmoney (state) {
      return state.sysAreaConfigDto.prepaidPercent / 100 * this.price
    }
  })
}
</script>
<style lang='less'>
.estimate {
  padding: 15px;
  line-height: 1.2;
  text-align: center;
  font-size: 14px;
  color: #4f5a67;
  background: #ffffff;
  .price{
  	font-size: 28px;
    padding: 0 5px;
    color: #4f5a67;
  }
  p,a{
    font-size: 12px;
    line-height: 1.67;
    color: #9ea7b1;
  }
  .coupon{
    color: #f99359;
  }
  .prepaidmoney{
    color: #00b5e6;
    font-weight: bold;
  }
}
</style>
