<!-- 发票提交对话框 -->
<template>
    <transition enter-active-class="animated fadeIn" leave-active-class="animated fadeOut">
        <div>
            <div class="invice_dialog">
                <div class="dialog_content">
                    <div class="item">
                        <p>抬头类型：{{accountType==1?'公司':'个人'}}</p>
                    </div>
                    <div class="item">
                        <label>发票抬头：</label>
                        <p>{{buyername}}</p>
                    </div>
                    <div class="item">
                        <label>纳税人识别号：</label>
                        <p>{{taxnum?taxnum:'未填写'}}</p>
                    </div>
                    <div class="item" v-if="invoiceType==1">
                        <label>电子邮箱：</label>
                        <p>{{email}}</p>
                    </div>
                    <div class="item">
                        <p class="small" v-if="accountType==2">*您选择的发票抬头为个人抬头，如需报销，请选择公司抬头票。 </p>
                        <p class="small" v-if="invoiceType==1">电子发票将在系统开具后发送至您的邮箱，请确保上述信息无误。</p>
                        <p class="small" v-else>*纸质发票将在开票后将送至您的收件地址，请确保上述信息无误</p>
                    </div>
                </div>
                <div class="dialog_btn">
                    <div class="btn active" @click="comfirmBtn">确认提交</div>
                    <div class="btn" @click="cancelBtn">取消</div>
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
  data () {
    return {
      showMask: true
    }
  },
  computed: {
    ...mapState([
      'invoiceType',
      'accountType',
      'buyername',
      'taxnum',
      'email'
    ])
  },
  methods: {
    comfirmBtn () {
      if (this.invoiceType === 1) {
        this.$store.dispatch('post_invoiceMsg').then((res) => {
          if (res.success) {
        // 提交成功
            this.$emit('comfirmOpt', true)
          } else {
        // 提交失败
            this.$emit('comfirmOpt', false)
          }
        }).catch((res) => {
        // 提交失败
          this.$emit('comfirmOpt', false)
        })
      } else {
        this.$store.dispatch('post_paperinvoiceMsg').then((res) => {
          if (res.success) {
        // 提交成功
            this.$emit('comfirmOpt', true)
          } else {
        // 提交失败
            this.$emit('comfirmOpt', false)
          }
        }).catch((res) => {
        // 提交失败
          this.$emit('comfirmOpt', false)
        })
      }
    },
    cancelBtn () {
      this.$emit('cancelOpt')
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
  width: 90%;
  transform: translate(-50%,-50%);
  animation-duration: .3s;
  border-radius: 10px;
  background: #ffffff;
  color: #4f5a67;
  .dialog_content{
    padding: 15px;
    border-bottom: .5px solid #f3f3f3;
    .item{
      font-size: 14px;
      line-height: 1.5;
      padding: 5px 0;
      label{
        color: #9ea7b1;
        &.small{
          font-size: 12px;
        }
      }
      p{
        color: #4f5a67;
        &.small{
          color: #9ea7b1;
          font-size: 12px;
        }
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