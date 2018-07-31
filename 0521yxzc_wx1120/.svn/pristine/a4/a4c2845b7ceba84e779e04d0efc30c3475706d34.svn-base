<!-- 发票视图 -->
<template>
    <transition enter-active-class="animated slideInRight" leave-active-class="animated slideOutRight">
        <div class="invoiceMsg">
            <div class="top">
                <label>可开发票额度</label>
                <h1><span>￥</span>500</h1>
            </div>
            <ul class="menuList">
                <router-link to="/invoiceTrip">
                    <li class="item">
                        按行程开票
                    </li>
                </router-link>
                <router-link to="/invoice">
                    <li class="item">
                        按金额开票
                    </li>
                </router-link>
                <router-link to="/invoice">
                    <li class="item">
                        开票历史
                    </li>
                </router-link>
            </ul>
            <p class="labelMsg">
                开票说明
                <br> 1.应用内仅支持申请开具普通发票。
                <br> 2.可开票金额仅限于用户实际充值或支付的人民币订单费用；任何非人民币支付的订单和营销活动，包括但不限于优惠券抵扣用，均不可开票。
                <br> 3.按行程开票时，选择的行程总额不能超过可开票额度；按金额开票时，填写的金额不能超过可开票额度。
                <br> 4.收到您的开票申请后，我们将尽快对信息进行核实和开票寄到您的邮箱。
                <br> 5.应用内默认使用电子发票，如需使用电子发票，请致电客服，客服电话：0592-96363转0
                <br> 6. 如发生开票有误,需重新开具发票，请致电客服，客服电话：0592-96363转0</p>
        </div>
    </transition>
</template>
<script>
import { mapState } from 'vuex'
export default{
  name: 'changeCarType',
  computed: mapState({
    order (state) {
      return state.estimateMsg
    }
  })
}
</script>
<style lang='less'>
.invoiceMsg {
  position: fixed;
  box-sizing: border-box;
  display: block;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  bottom: 0;
  background: #ffffff;
  animation-duration: .3s;
  .top {
    position: relative;
    box-sizing: border-box;
    padding: 30px 0;
    label {
      display: block;
      width: 100%;
      font-size: 14px;
      color: #9ea7b1;
      text-align: center;
    }
    h1 {
      font-size: 32px;
      text-align: center;
      line-height: 1.5;
      font-weight: bold;
      color: #00b5e6;
      span{
        font-size: 14px;
      }
    }
  }
  .menuList{
    position: relative;
    display: block;
    padding-left: 15px;
    border-top: .5px solid #f3f3f3;
    border-bottom: .5px solid #f3f3f3;
    a {
      display: block;
      &:not(:last-child) {
        border-bottom: .5px solid #f3f3f3;
      }
    }
    li {
      position: relative;
      display: block;
      height: 50px;
      line-height: 50px;
      font-size: 14px;
      color: #4b4d51; 
      &:after{
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
  }
  .labelMsg{
    display: block;
    margin-top: 30px;
    padding: 0 15px;
    font-size: 12px;
    line-height: 1.6;
    color: #9ea7b1;
  }
}

</style>