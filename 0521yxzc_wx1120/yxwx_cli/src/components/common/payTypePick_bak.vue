<!-- 车型选择视图 -->
<template>
    <transition enter-active-class="animated bounceIn" leave-active-class="animated slideOutDown">
        <div class="wrap">
            <div class="payTypePick">
                <h1 class="title">选择支付方式</h1>
                <ul class="pick-list">
                    <li class="item" v-for="(item,index) in payTypeList" @click="pick(item)">
                        <div :class="(item.type === 2)?'personPay':'enterprisePay'"></div>
                        <p class="mar-t10">{{item.name}}</p>
                        <p class="sub" v-if="item.status" >(余额<span>{{item.money}}</span>元)</p>
                        <p class="sub" v-else>(无企业账户)</p>
                    </li>
                </ul>
                <div class="fix_bottom"><a href="javascript:void(0);" @click="goback()">取消</a></div>
            </div>
            <transition enter-active-class="animated fadeIn" leave-active-class="animated fadeOut">
                <com-mask :show="true" :event="default_event"></com-mask>
            </transition>
        </div>
    </transition>
</template>
<script>
import { mapState } from 'vuex'
const comMask = () => import('@/components/Mask.vue')
export default{
  name: 'payType',
  data () {
    return {
      payTypeList: this.$store.state.payTypeList
    }
  },
  components: {
    comMask
  },
  computed: mapState({
  }),
  methods: {
    pick (val) {
      if (val.status) {
        this.$store.commit('SET_PAYTYPE', val)
      // this.$router.go(-1)
        window.history.go(-1)
      } else {
        let toast = this.$createToast({
          type: 'warn',
          mask: true,
          time: 1000,
          txt: '该支付方式不可用'
        })
        toast.show()
      }
    },
    goback () {
      // this.$router.go(-1)
      window.history.go(-1)
    },
    default_event () {
      return false
    }
  }
}
</script>
<style lang='less' scoped>
@import "../../assets/css/less/variable.less";
.wrap {
  z-index: 9;
  position: fixed;
  top: 0;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: #f3f3f3;
  animation-duration: .3s;
}

.payTypePick {
  z-index: 99;
  position: fixed;
  bottom: 0;
  width: 100%;
  height: 250px;
  text-align: center;
  font-size: 12px;
  color: #6b7886;
  background: #ffffff;
  .title {
    padding: 10px 0;
    line-height: 50px;
    font-size: 16px;
    color: #333333;
  }
  .pick-list {
    display: flex;
    padding: 0 30px;
    li.item {
      flex-grow: 1;
      text-align: center;
      padding: 15px 0;
      &:active {
        box-shadow: inset 0 3px 5px rgba(0, 0, 0, .125);
      }
      &:focus {
        outline-offset: 2px;
      }
      p {
        line-height: 1.5;
        font-size: 12px;
        color: #4b4d51;
        &.sub {
          color: #a9abb0;
          font-size: 10px;
          span{
            color:@fc_active;
          }
        }
      }
    }
  }
  .fix_bottom {
    position: fixed;
    bottom: 0;
    width: 100%;
    box-sizing: border-box;
    height: 50px;
    line-height: 50px;
    font-size: 16px;
    color: #333333;
    text-align: center;
    border-top: 0.5px solid #e5e5e5;
    cursor: pointer;
    a {
      width: 100%;
      height: 100%;
      display: block;
      color: #333333;
      &:active {}
    }
  }
}


.personPay {
  display: block;
  margin: 0 auto;
  width: 55px;
  height: 55px;
  background: url('../../assets/images/person-pay.png') no-repeat center;
  background-size: 100% 100%;
}

.enterprisePay {
  display: block;
  margin: 0 auto;
  width: 55px;
  height: 55px;
  background: url('../../assets/images/enterprise-pay.png') no-repeat center;
  background-size: 100% 100%;
}

</style>
