<template>
  <div>
    <transition enter-active-class="animated bounceIn" leave-active-class="animated bounceOut">
      <div class="login-form" v-if="loginMask">
        <form>
          <!-- 标题 -->
          <p class="title">验证手机号，方便司机联系您</p>
          <!-- 手机号 -->
          <div class="form-item phone">
            <input type="tel" placeholder="请输入手机号" v-model.trim="phone" maxlength="11">
            <span class="clearBtn" v-show="phone" @click="clear"></span>
            <span class="btn" v-if="(time < 60)">{{time}} 秒</span>
            <span class="btn" v-else @click="sendMsg()">验证</span>
          </div>
          <!-- 验证码 -->
          <div class="form-item validate">
            <input type="number" placeholder="请输入验证码" v-model.trim="validate" maxlength="4">
          </div>
          <!-- 确定 -->
          <div class="form-item">
            <!-- <div class="submitBtn" @click="login()">确定</div> -->
            <cube-button class="submitBtn" :disabled="!disabled" @click="login()">确定</cube-button>
          </div>
          <!-- 是否接受 -->
          <label class="style-label rule">
            <input type="checkbox" v-model="checked" hidden="hidden">
            <span class="style-checkbox"><span class="style-checkbox-inner"></span></span> <a href="/api/base/userAgreement">我接受元翔专车用户协议</a> </label>
        </form>
        <!-- 取消底部关闭按钮 -->
        <!-- <span class="closeBtn" @click="switch_loginMask">X</span>-->
      </div>
    </transition>
    <transition enter-active-class="animated fadeIn" leave-active-class="animated fadeOut">
      <com-mask :show="loginMask" :event="default_event"></com-mask>
    </transition>
  </div>
</template>
<script>
import { mapState, mapActions } from 'vuex'
const comMask = () =>
  import('@/components/Mask.vue')
export default {
  data () {
    return {
      phone: '',
      validate: '',
      time: 60,
      checked: true
    }
  },
  components: {
    comMask
  },
  computed: {
    ...mapState(['loginMask']),
    disabled () {
      return this.phone && this.validate
    }
  },
  watch: {
    validate (newValue, oldValue) {
      console.log('newValue', newValue)
      console.log('oldValue', oldValue)
      if (newValue.length > 4) {
        this.validate = oldValue
      }
    },
    phone (newValue, oldValue) {
      console.log('newValue', newValue)
      console.log('oldValue', oldValue)
      let re = /^[0-9]*$/
      if (!re.test(newValue)) {
        this.phone = oldValue || ''
      } else {
        this.phone = newValue
      }
    }
  },
  methods: {
    ...mapActions({
      switch_loginMask: 'switch_loginMask'
    }),
    Countdown () {
      var self = this
      if (self.time === 0) {
        self.time = 60
      } else {
        self.time--
        setTimeout(function () {
          self.Countdown()
        }, 1000)
      }
    },
    // 清空输入框
    clear () {
      this.phone = ''
    },
    sendMsg () {
      if (!this.phone) {
        this.showToastTime('手机不能为空')
        return
      }
      this.$store.dispatch('sendMsgRequest', this.phone).then((data) => {
        if (data.success) {
          this.Countdown()
        } else {
          this.showToastTime(data.msg)
        }
      }).catch(() => {
        this.showToastTime('请求出错')
        return false
      })
    },
    login () {
      if (!this.checked) {
        this.showToastTime('请勾选用户协议')
        return
      }
      if (!this.phone || !this.validate) {
        this.showToastTime('请填写手机和验证码')
        return
      }
      let RE = /^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\d{8}$/
      if (!RE.test(this.phone) || this.phone.length !== 11) {
        this.showToastTime('手机格式不正确')
        return
      }
      this.$store.dispatch('loginRequest', { phone: this.phone, validate: this.validate }).then((data) => {
        if (data.success) {
          // 绑定成功触发页面刷新
          window.location.reload()
        } else {
          this.showToastTime(data.msg)
        }
        console.log(data)
      }).catch(() => {
        this.showToastTime('请求出错')
        return false
      })
    },
    showToastTime (text) {
      const toast = this.$createToast({
        type: 'warn',
        time: 1000,
        txt: text
      })
      toast.show()
    },
    default_event () {
      return false
    }
  }
}
</script>
<style lang="less">
@import "../../assets/css/less/variable.less";
.login-form {
  display: none;
  position: absolute;
  z-index: 99;
  top: 50%;
  left: 50%;
  margin-left: -155px;
  margin-top: -140px;
  padding: 0 15px;
  width: 280px;
  height: 280px;
  display: block;
  background: #fff;
  border-radius: 8px;
  text-align: center;
  /* 取消底部关闭按钮 */
  /* &:after{
    position: absolute;
    content: '';
    bottom: -33px;
    left: 50%;
    margin-left: -1px;
    width: 2px;
    height: 33px;
    background: #fff;
  } */
  .title {
    padding: 30px 0 15px;
    text-align: center;
    font-size: 14px;
    color: #4b4e51;
    line-height: 2;
  }
  .form-item {
    position: relative;
    display: table;
    width: 100%;
    padding: 15px 0;
    font-size: 14px;
    border-bottom: .5px solid #e5e5e5;
    &:nth-last-child(2) {
      border: none;
    }
    &.phone {
      &:before {
        position: absolute;
        content: '';
        top: 50%;
        margin-top: -8px;
        left: 2px;
        height: 16px;
        width: 12px;
        background: url('../../assets/images/phone.png') no-repeat center;
        background-size: 100% 100%;
      }
    }
    &.validate {
      &:before {
        position: absolute;
        content: '';
        top: 50%;
        margin-top: -8px;
        left: 2px;
        height: 17px;
        width: 15px;
        background: url('../../assets/images/validate.png') no-repeat center;
        background-size: 100% 100%;
      }
    }

    &:after {
      .clear()
    }
    input {
      display: inline-block;
      float: left;
      height: 20px;
      line-height: 20px;
      padding-left: 30px;
      &::-webkit-input-placeholder {
        color: #d0d5d9;
      }
    }
    span.btn {
      display: inline-block;
      padding: 0 10px;
      line-height: 20px;
      color: @fc_active;
    }
    .clearBtn {
      position: relative; //right: 0;
      //top: 50%;
      //margin-top: -8px;
      //display: block;
      display: inline-block;
      margin-left: -16px;
      width: 16px;
      height: 16px;
      background: url('../../assets/images/wrong.png');
    }
    .submitBtn {
      display: table-cell;
      text-align: center;
      height: 40px;
      line-height: 40px;
      font-size: 16px;
      color: #fff;
      border-radius: 2px;
      padding: 0;
      /* &:active,
      &:focus,
      &:visited {
        background: @fc_active - 11
      } */
    }
  }
  .rule {
    margin: 0 auto;
    font-size: 12px;
    line-height: 16px;
    color: @fc_active;
    a {
      color: @fc_active;
    }
  }
  .closeBtn {
    position: absolute;
    width: 36px;
    height: 36px;
    line-height: 36px;
    text-align: center;
    bottom: -66px;
    left: 50%;
    margin-left: -18px;
    font-size: 16px;
    color: @fc_active;
    background: #fff;
    border-radius: 50%;
  }
}

</style>
