<!-- 发票提交对话框 -->
<template>
    <transition enter-active-class="animated fadeIn" leave-active-class="animated fadeOut">
        <div v-show="show">
            <div class="invice_dialog">
                <div class="dialog_content" :class="{fail:!type}">
                    <div class="item center" v-if="invoiceType===1">
                        <p v-if="type">提交成功，您的电子发票将在</br>30分钟内发送至您的邮箱！</p>
                        <p v-if="!type">提交失败</br>请您稍后再试</p>
                    </div>
                    <div class="item center" v-else>
                        <p v-if="type">提交成功！</p>
                        <p v-if="!type">提交失败</br>请您稍后再试</p>
                    </div>
                </div>
                <div class="dialog_btn">
                    <div class="btn active" v-if="type" @click="comfirmBtn">我知道了</div>
                    <div class="btn" v-if="!type" @click="cancelBtn">我知道了</div>
                </div>
            </div>
            <com-mask :show="showMask" :event="default_event"></com-mask>
        </div>
    </transition>
</template>
<script>
import { mapState } from 'vuex'
const comMask = () => import('@/components/Mask.vue')
export default{
  props: ['show', 'type', 'invoiceType'],
  data () {
    return {
      showMask: true
    }
  },
  computed: {
    ...mapState([
      'accountType',
      'buyername',
      'taxnum',
      'email'
    ])
  },
  methods: {
    comfirmBtn () {
      // 页面跳转
      window.location.href = '/wechat/view/userCenter#/invoice'
    },
    cancelBtn () {
      this.$emit('listenChild')
    },
    default_event () {
      return false
    }
  },
  components: {
    comMask
  }
}
</script>
<style lang="less" scoped>
div{
  animation-duration: .3s;
}
.invice_dialog{
  z-index: 11;
  position: fixed;
  top: 50%;
  left: 50%;
  width: 70%;
  transform: translate(-50%,-50%);
  animation-duration: .3s;
  border-radius: 10px;
  background: #ffffff;
  color: #4f5a67;
  .dialog_content{
    padding: 50px 0 15px;
    border-bottom: .5px solid #f3f3f3;
    &:before{
      content:'';
      position: absolute;
      top: -30px;
      left: 50%;
      transform: translateX(-50%);
      height: 60px;
      width: 60px;
      background: url('../images/icon_finish.png') no-repeat center;
      background-size: 100% 100%;
    }
    &.fail:before{
      content:'';
      position: absolute;
      top: -30px;
      left: 50%;
      transform: translateX(-50%);
      height: 60px;
      width: 60px;
      background: url('../images/icon_Failure.png') no-repeat center;
      background-size: 100% 100%;
    }
    .item{
      font-size: 14px;
      line-height: 1.5;
      padding: 5px 0;
      p{
        color: #9ea7b1;
        text-align: center;
      }
    }
  }
  .dialog_btn{
    display: flex;
    align-content:center;
    .btn{
      position: relative;
      box-sizing: border-box;
      flex-grow: 1;
      text-align: center;
      line-height: 45px;
      width: 50%;
      height: 45px;
      &.active{
        color: #00b5e6;
      }
      &:not(:first-child){
        border-left: .5px solid #f3f3f3;
      }
    }
  }
}
</style>