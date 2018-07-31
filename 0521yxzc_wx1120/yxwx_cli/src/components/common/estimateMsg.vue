<!-- 预估价详细视图 -->
<template>
    <transition enter-active-class="animated slideInRight" leave-active-class="animated slideOutRight">
        <div class="estimateMsg">
            <div class="top">
                <label>价格预估</label>
                <h1>{{order.totalFee}}<span>元</span></h1>
            </div>
            <ul>
                <li v-for="(el,index) in order.costItems">
                    <span class="left">{{el.item}}</span>
                    <span class="right">{{el.cost}}元</span>
                </li>
            </ul>
            <label class="labelMsg">高速费、路桥费等不计入预估费用，由乘客支付实际产生的费用。预估费用仅供参考，实际费用可能会因交通情况、天气因素而不同。</label>
            <div class="fixbottom">
                <cube-button @click="goBack()" :outline="true">返回</cube-button>
            </div>
        </div>
    </transition>
</template>
<script>
import {mapState} from 'vuex'
export default {
  name: 'changeCarType',
  computed: mapState({
    order (state) {
      return state.estimateMsg
    }
  }
  ),
  methods: {
    goBack () {
      this.$router.go(-1)
    }
  }
}
</script>
<style lang='less'>
.estimateMsg {
  position: fixed;
  box-sizing: border-box;
  display: block;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  bottom: 0;
  padding: 15px;
  background: #ffffff;
  animation-duration: .3s;
  .top {
    position: relative;
    box-sizing: border-box;
    padding: 30px 0 15px 0;
    label {
      display: block;
      width: 100%;
      font-size: 14px;
      color: #9ea7b1;
      text-align: center;
    }
    h1 {
      font-size: 50px;
      text-align: center;
      line-height: 1.5;
      font-weight: bold;
      color: #4f5a67;
      span {
        font-size: 14px;
        color: #4f5a67;
      }
    }
  }
  ul {
    position: relative;
    display: block;
    width: 100%;
    li {
      position: relative;
      display: block;
      width: 100%;
      height: 45px;
      line-height: 45px;
      font-size: 14px;
      border-bottom: 0.5px solid #f3f3f3;
      color: #6b7886;
      .left {
        position: relative;
        float: left;
      }
      .right {
        position: relative;
        float: right;
      }
    }
  }
  .labelMsg {
    display: block;
    padding: 15px 0;
    font-size: 12px;
    line-height: 1.6;
    color: #9ea7b1;
  }
}

.fixbottom {
  box-sizing: border-box;
  position: absolute;
  width: 100%;
  bottom: 10px;
  left: 0;
  padding: 10px;
}

</style>
