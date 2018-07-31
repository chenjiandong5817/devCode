<!-- 车型选择视图 -->
<template>
    <!-- 进遮罩层出无遮罩层 -->
    <transition enter-active-class="animated bounceIn" leave-active-class="animated slideOutDown">
        <div class="wrap">
            <div class="carTypePick">
                <!-- 操作提示栏 -->
                <div class="picker-choose">
                  <span class="cancel" @click="goback()">取消</span>
                  <span class="confirm" @click="confirm()">确定</span>
                  <h1 class="title">选择支付方式</h1>
                </div>
                <ul class="pick-list">
                    <li class="item" v-for="(item,index) in payTypeList" @click="picksomeOne(item)">
                      <label>
                        <input type="radio" name="carType" v-model="pickItem.type" :value="item.type">
                        <div class="item-content">
                        <!-- 图标 -->
                        <div :class="item.type===1?'enterprisePay':'personPay'"></div>
                        <!-- 名称 -->
                        <p class="mar-t10">{{item.name}}</p>
                        <!-- 解释 -->
                        <p class="sub mar-b10" v-if="item.status" >(余额<span>{{item.money}}</span>元)</p>
                        <p class="sub mar-b10" v-else>(无企业账户)</p>                          
                        </div>
                      </label>
                    </li>
                </ul>
            </div>
            <!-- 进遮罩层有出遮罩层无 -->
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
      payTypeList: this.$store.state.payTypeList,
      // payTypeList: payTypeList,
      pickItem: this.$store.state.payType
    }
  },
  components: {
    comMask
  },
  computed: {
    ...mapState([
      'VIPnum'
    ])
  },
  methods: {
    confirm () {
      if (!this.pickItem.status) {
        let toast = this.$createToast({
          type: 'warn',
          mask: true,
          time: 1000,
          txt: '该支付方式不可用'
        })
        toast.show()
        return
      }
      this.$store.commit('SWITCH_PAYTYPE', true)
      this.$store.commit('SET_PAYTYPE', this.pickItem)
      window.history.go(-1)
    },
    picksomeOne (val) {
      this.pickItem = val
    },
    goback () {
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

.carTypePick {
  z-index: 99;
  position: fixed;
  bottom: 0;
  width: 100%;
  height: 210px;
  text-align: center;
  font-size: 12px;
  color: #6b7886;
  background: #ffffff;
  .picker-choose {
    position: relative;
    height: 50px;
    font-size: 16px;
    color: #6b7886;
    border-bottom: 0.5px solid #f3f3f3;
    h1{
      line-height: 50px;
    }
    .cancel{
      position: absolute;
      top: 3px;
      left: 0;
      padding: 15px;
      font-size: 14px;
      color: #999;
    }
    .confirm{
      position: absolute;
      top: 3px;
      right: 0;
      padding: 16px;
      font-size: 14px;
      color: #00b5e6;
    }
  }
  .pick-list {
    display: flex;
    padding: 30px 15px 20px 15px;
    li.item {
      flex-grow: 1;
      text-align: center;
      label{
        display: block;
        width: 100%;
        height: 100%;
      }
      input[type="radio"]{
        display:none;
      }
      input[type="radio"]:checked + div.item-content{
        border-bottom: 1.5px solid #00b5e6;
        .personPay{
          background: url('../../assets/images/person-pay-active.png') no-repeat center;
          background-size: 100% 100%;
        }
        .enterprisePay{
          background: url('../../assets/images/enterprise-pay-active.png') no-repeat center;
          background-size: 100% 100%;
        }
      }
      .item-content{
        font-size: 12px;
        line-height: 1.5;
        border-bottom: 1.5px solid #dcdfe3;
        color: #4b4e51;
        .personPay{
          margin: 0 auto;
          background: url('../../assets/images/person-pay.png') no-repeat center;
          background-size: 90% 90%;
        }
        .enterprisePay{
          margin: 0 auto;
          background: url('../../assets/images/enterprise-pay.png') no-repeat center;
          background-size: 90% 90%;
        }
        .sub{
          color: #c5c7ca;
          font-size: 12px;
        }
      }
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
