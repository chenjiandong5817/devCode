<template>
    <transition enter-active-class="animated slideInRight" leave-active-class="animated slideOutRight">
        <div class="wrap">
            <!-- 主要内容 -->
            <div class="content">
                <div class="form-item">
                    <label class="form-label">开票金额</label>
                    <div class="input-item">
                        <input type="number" placeholder="开票金额" v-model="countMoney">
                    </div>
                    <span>元</span>
                </div>
            </div>
            <!-- 描述 -->
            <p class="title">可开票额度¥{{available}}，满￥300包邮</p>
            <!-- 操作 -->
            <p class="title absbottom"><cube-button :disabled="!countMoney" @click="sure">下一步</cube-button></p>
        </div>
    </transition>
</template>
<script>
import { mapState } from 'vuex'
export default{
  data () {
    return {
      countMoney: this.$store.state.countMoney || ''
    }
  },
  computed: mapState({
    available (state) {
      return state.available
    }
  }),
  watch: {
    // 对输入金额进行格式控制
    countMoney (newval, oldval) {
      if (!newval) {
        return
      }
      let reg = /^[1-9]*[1-9][0-9]*$/
      if (!reg.test(newval) || newval.length > 7) {
        this.countMoney = oldval
      }
    }
  },
  methods: {
    sure () {
      if (this.countMoney >= 10000) {
        this.$store.commit('SET_INVOICETYPE', 2)
        let toast = this.$createToast({
          txt: '已超出10000元，默认为您选择纸质发票',
          type: 'warn',
          mask: true
        })
        toast.show()
      }
      if (this.countMoney > this.available) {
        let toast = this.$createToast({
          txt: '开票金额超过可开票额度',
          type: 'warn',
          mask: true
        })
        toast.show()
        return
      }
      this.$store.commit('SET_TICKETTYPE', 2) // 按金额开票
      this.$store.commit('SET_COUNTMONEY', parseInt(this.countMoney))
      this.$router.push('invoiceForm')
    }
  }
}
</script>
<style lang="less" scoped>
@import "../../assets/css/less/variable.less";
.wrap {
  position: fixed;
  width: 100%;
  height: 100%;
  bottom: 0;
  left: 0;
  background: #f3f3f3;
  animation-duration: .3s;
  p.title {
    padding: 5px 15px;
    font-size: 12px;
    line-height: 1.5;
    color: #a9abb0;
  }
  .content {
    padding-left: 15px;
    color: #4f5a67;
    font-size: 14px;
    background: #ffffff;
    .form-item {
      display: flex;
      position: relative;
      height: 50px;
      line-height: 50px;
      &:not(:last-child) {
        &:after {
          content: '';
          position: absolute;
          bottom: 0;
          width: 100%;
          height: .5px;
          background: #f3f3f3;
        }
      }
      label.form-label {
        width: 100px;
      }
      .input-item {
        flex-grow: 1;
        input {
          width: 100%;
          outline: none;
          &::-webkit-input-placeholder {
            color: #c5c7ca;
          }
        }
      }
      span{
        padding: 0 15px;
      }
    }
  }
  .absbottom{
    position: absolute;
    bottom: 15px;
    width: 100%;
    box-sizing: border-box;
  }
}

</style>>