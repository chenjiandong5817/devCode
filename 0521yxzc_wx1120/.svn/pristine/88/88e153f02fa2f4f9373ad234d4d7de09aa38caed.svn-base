<template>
    <transition enter-active-class="animated slideInRight" leave-active-class="animated slideOutRight">
        <div class="wrap">
            <p class="title">发票详情</p>
            <div class="content">
                <div class="form-item" @click="showtypePicker">
                    <label class="form-label">发票类型</label>
                    <div class="input-item">
                        <p class="more darkcolor" v-html="(invoiceType==1?'电子发票':'纸质发票')"></p>
                    </div>
                </div>
                <div class="form-item">
                    <label class="form-label">抬头类型</label>
                    <div class="input-item form-item">
                        <label class="checkRadio">
                            <!-- radio美化组件 start -->
                            <div class="ui-radio-label">
                                <label>
                                    <input type="radio" name="accountType" value="1" v-model="accountType">
                                    <span class="ui-radio-span">
                                        <span class="ui-radio-inner"></span>
                                    </span>
                                </label>
                            </div>
                            <span>公司</span>
                        </label>
                        <!-- radio美化组件 end -->
                        <label class="checkRadio">
                            <!-- radio美化组件 start -->
                            <div class="ui-radio-label">
                                <label>
                                    <input type="radio" name="accountType" value="2" v-model="accountType">
                                    <span class="ui-radio-span">
                                        <span class="ui-radio-inner"></span>
                                    </span>
                                </label>
                            </div>
                            <span>个人</span>
                            <!-- radio美化组件 end -->
                        </label>
                    </div>
                </div>
                <div class="form-item">
                    <label class="form-label">发票抬头</label>
                    <div class="input-item">
                        <input type="text" maxlength="25" placeholder="填写个人或单位名称" v-model="buyername">
                    </div>
                </div>
                <div class="form-item">
                    <label class="form-label">纳税人识别号</label>
                    <div class="input-item">
                        <input type="text" placeholder="填写纳税人识别号" v-model="taxnum">
                    </div>
                </div>
                <div class="form-item">
                    <label class="form-label">发票金额</label>
                    <div class="input-item">
                        <p>￥{{countMoney}}</p>
                    </div>
                </div>
                <div class="form-item">
                    <label class="form-label">发票内容</label>
                    <div class="input-item">
                        <p>客运服务费</p>
                    </div>
                </div>
                <div class="form-item">
                    <label class="form-label">更多信息</label>
                    <div class="input-item">
                        <router-link to="invoiceFormMore" append>
                            <p class="more" @click="show=true" v-if="!moreMsg">填写购买方、备注等（选填）</p>
                            <p class="more darkcolor" @click="show=true" v-if="moreMsg">{{moreMsg|moreMsgcount}}</p>
                        </router-link>
                    </div>
                </div>
            </div>
            <p class="title">收件信息</p>
            <!-- 单子发票 -->
            <template v-if="(invoiceType===1)">
                <div class="content">
                    <div class="form-item">
                        <label class="form-label">电子邮箱</label>
                        <div class="input-item">
                            <input type="email" placeholder="填写收件人邮箱" v-model="email">
                        </div>
                    </div>
                </div>
                <p class="title">*您所填写的邮箱地址将作为电子发票发送的邮箱地址，请认真填写并确保无误。</p>
                <p class="title">
                    <cube-button @click="sure" :disabled="!(buyername&&email)">提交申请</cube-button>
                </p>
            </template>
            <!-- 电子发票 -->
            <!-- 纸质发票 -->
            <template v-else>
                <div class="content">
                    <div class="form-item">
                        <label class="form-label">收件人</label>
                        <div class="input-item">
                            <input type="text" placeholder="填写收件人" v-model="post_name" maxlength="10">
                        </div>
                    </div>
                    <div class="form-item">
                        <label class="form-label">联系电话</label>
                        <div class="input-item">
                            <input type="tel" placeholder="收件人电话" v-model="post_mobile">
                        </div>
                    </div>
                    <div class="form-item" @click="showareaPicker">
                        <label class="form-label">所在地区</label>
                        <div class="input-item">
                          <p class="more" :class="{darkcolor:(post_area!='请选择')}">{{post_area}}</p>
                        </div>
                    </div>
                    <div class="form-item">
                      <textarea type="text" placeholder="请填写详细地址" rows="2" v-model="post_address" maxlength="50"></textarea>
                    </div>
                    <!-- <div class="form-item">
                        <label class="form-label">详细地址</label>
                        <div class="input-item">
                            <textarea type="text" placeholder="请填写详细地址" rows="1" v-model="post_address"></textarea>
                        </div>
                    </div> -->
                </div>
                <p class="title">运费支付</p>
                <div class="content">
                    <div class="form-item" v-if="(countMoney>=300)">
                        <label class="form-label">满300，免运费</label>
                    </div>
                    <div class="form-item" v-else>
                        <label class="form-label">货到付款</label>
                        <div class="input-item">
                            <p>(开票金额未满300)</p>
                        </div>
                    </div>
                </div>
                <p class="title">*您所填写的收件信息，将作为纸质发票的邮寄地址，请认真填写并确保无误。</p>
                <p class="title">
                    <cube-button @click="sure" :disabled="!(buyername&&post_name&&post_mobile&&post_address)">提交申请</cube-button>
                </p>
            </template>
            <!-- 纸质发票 -->
            <invoiceDialog v-show="showcomfirmDialog" v-on:comfirmOpt="comfirmOpt" v-on:cancelOpt="cancelOpt"></invoiceDialog>
            <postToast :show="showpostToast" :type="ToastType" :invoiceType="invoiceType" v-on:listenChild="switchToast"></postToast>
        </div>
    </transition>
</template>
<script>
import { mapState } from 'vuex'
import { areaData } from '@/store/areaData.js'
const invoiceDialog = () => import('./components/dialog')
const postToast = () => import('./components/postToast')
const typeData = [
  {
    'text': '电子发票',
    'value': 1
  }, {
    'text': '纸质发票',
    'value': 2
  }]
export default{
  mounted () {
    this.typePicker = this.$createCascadePicker({
      title: '发票类型',
      data: typeData,
      onSelect: (selectedVal, selectedIndex, selectedText) => {
        this.$store.commit('SET_INVOICETYPE', selectedVal[0])
      },
      onCancel: () => {
      }
    })
    this.addressPicker = this.$createCascadePicker({
      title: '选择地区',
      data: areaData,
      onSelect: (selectedVal, selectedIndex, selectedText) => {
        let str = ''
        selectedText.forEach(el => {
          str += el
        })
        this.$store.commit('SET_POSTAREA', str)
      },
      onCancel: () => {
      }
    })
  },
  data () {
    return {
      showcomfirmDialog: false,
      showpostToast: false,
      ToastType: false
    }
  },
  filters: {
    moreMsgcount (val) {
      switch (val) {
        case 1:
          return '已填一项'
        case 2:
          return '已填两项'
        case 3:
          return '已填三项'
        case 4:
          return '已填四项'
        default:
          break
      }
    }
  },
  computed: {
    ...mapState([
      'invoiceType',
      'countMoney',
      'PicktripList',
      'message',
      'telephone',
      'address',
      'account'
      // 'post_name',
      // 'post_mobile',
      // 'post_area',
      // 'post_address'
    ]),
    moreMsg () {
      var length = 0
      var arr = [this.message, this.address, this.telephone, this.account]
      arr.forEach(function (el, index) {
        if (el) {
          length++
        }
      })
      return length
    },
    accountType: {
      get () {
        return this.$store.state.accountType
      },
      set (val) {
        this.$store.commit('SET_ACCOUNTTYPE', +val)
      }
    },
    buyername: {
      get () {
        return this.$store.state.buyername
      },
      set (val) {
        this.$store.commit('SET_BUYERNAME', val)
      }
    },
    email: {
      get () {
        return this.$store.state.email
      },
      set (val) {
        this.$store.commit('SET_EMAIL', val)
      }
    },
    taxnum: {
      get () {
        return this.$store.state.taxnum
      },
      set (val) {
        this.$store.commit('SET_TAXNUM', val)
      }
    },
    post_name: {
      get () {
        return this.$store.state.post_name
      },
      set (val) {
        this.$store.commit('SET_POSTNAME', val)
      }
    },
    post_mobile: {
      get () {
        return this.$store.state.post_mobile
      },
      set (val) {
        this.$store.commit('SET_POSTMOBILE', val)
      }
    },
    post_area: {
      get () {
        return this.$store.state.post_area || '请选择'
      },
      set (val) {
        this.$store.commit('SET_POSTAREA', val)
      }
    },
    post_address: {
      get () {
        return this.$store.state.post_address
      },
      set (val) {
        this.$store.commit('SET_POSTADDRESS', val)
      }
    }
  },
  methods: {
    showtypePicker () {
      if (this.countMoney < 10000) {
        this.typePicker.show()
      } else {
        this.showToastTime('开票金额超过10000元，仅提供纸质发票')
      }
    },
    showareaPicker () {
      this.addressPicker.show()
    },
    showToastTime (msg) {
      let toast = this.$createToast({
        type: 'warn',
        time: 1000,
        txt: msg
      })
      toast.show()
    },
    sure () {
      if (!this.buyername) {
        this.showToastTime('发票抬头不能为空')
        return
      }
      if (this.accountType === 1) {
        if (!this.taxnum) {
          this.showToastTime('纳税人识别号不能为空')
          return
        }
      }
      if (this.taxnum.length !== 0 && this.taxnum.length !== 15 && this.taxnum.length !== 18 && this.taxnum.length !== 20) {
        this.showToastTime('纳税人识别号格式错误')
        return
      }
      if (!this.email && this.invoiceType === 1) {
        this.showToastTime('电子邮箱不能为空')
        return
      }
      let E_RE = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
      if (!E_RE.test(this.email) && this.email && this.invoiceType === 1) {
        this.showToastTime('邮箱格式不正确')
        return
      }
      let RE = /^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\d{8}$/
      if (!RE.test(this.post_mobile) && this.post_mobile && this.invoiceType === 2) {
        this.showToastTime('手机格式不正确')
        return
      }
      // if (!this.post_name && this.invoiceType === 2) {
      //   this.showToastTime('收件人不能为空')
      //   return
      // }
      // if (!this.post_mobile && this.invoiceType === 2) {
      //   this.showToastTime('联系电话不能为空')
      //   return
      // }
      // if (!this.post_area && this.invoiceType === 2) {
      //   this.showToastTime('收件地址不能为空')
      //   return
      // }
      // 弹出提交对话框
      this.showcomfirmDialog = true
    },
    // 二级操作-确认提交
    comfirmOpt (res) {
      this.showcomfirmDialog = false
      if (res) {
        this.ToastType = true
      } else {
        this.ToastType = false
      }
      this.showpostToast = true
    },
    // 二级操作-取消提交
    cancelOpt () {
      this.showcomfirmDialog = false
    },
    // 结果提示对话框
    switchToast () {
      this.showpostToast = false
    }
  },
  components: {
    invoiceDialog,
    postToast
  }
}
</script>
<style lang="less" scoped>
@import "../../assets/css/less/variable.less";
.wrap {
  position: fixed;
  overflow-y:scroll; 
  width: 100%;
  height: 100%;
  bottom: 0;
  left: 0;
  overflow-y: scroll;
  overflow-x: hidden;
  background: #f3f3f3;
  animation-duration: .3s;
  &::-webkit-scrollbar {
    display: none;
  }
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
      position: relative; //height: 50px;
      padding-right: 15px;
      line-height: 50px;
      &:not(:last-child) {
        border-bottom: .5px solid #f3f3f3;
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
        .checkRadio {
          display: flex;
          align-items: center;
          width: 100px;
        }
        .more {
          color: #c5c7ca;
          &:after {
            content: '';
            position: absolute;
            border-right: 1px solid #c5c7ca;
            border-bottom: 1px solid #c5c7ca;
            width: 8px;
            height: 8px;
            -webkit-transform: rotate(-45deg);
            transform: rotate(-45deg);
            top: 50%;
            margin-top: -2px;
            right: 15px;
          }
        }
        .darkcolor{
          color: #4f5a67;
        }
      }
      textarea {
          width: 100%;
          border: 0;
          color: #4f5a67;
          font-size: 14px;
          line-height: 1.8;
          outline: none;
          resize: none;
          &::-webkit-input-placeholder {
            color: #c5c7ca;
          }
        }
    }
  }
  .absbottom {
    position: absolute;
    bottom: 15px;
    width: 100%;
    box-sizing: border-box;
  }
}

// radio美化
.ui-radio-label {
  position: relative;
  padding: 0 15px;
  background: #ffffff;
  box-sizing: border-box;
  label {
    position: relative;
    display: flex;
    -webkit-box-align: center;
    align-items: center;
    box-sizing: border-box;
    width: 100%;
    height: 100%;
    padding: 11px 0;
    color: #4f5a67;
    line-height: 1.5;
    word-break: break-word;
    word-wrap: break-word;
  }
  input[type='radio'] {
    z-index: 1;
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    opacity: 0;
  }
  input[type='radio']:checked+.ui-radio-span {
    .ui-radio-inner {
      background: @fc_active;
      border: 1px solid @fc_active;
      &:after {
        transform: rotate(45deg) scale(1);
      }
    }
  }
  .ui-radio-span {
    position: absolute;
    top: 2px;
    right: -4px;
    cursor: pointer;
    .ui-radio-inner {
      display: inline-block;
      width: 16px;
      height: 16px;
      border-radius: 50%;
      position: relative;
      top: 0;
      left: 0;
      border: 1px solid #dddee1;
      background-color: #fff;
      transition: border-color .2s ease-in-out, background-color .2s ease-in-out;
      &:after {
        content: '';
        display: table;
        position: absolute;
        width: 4px;
        height: 8px;
        z-index: 10;
        top: 3px;
        left: 6px;
        border: 1.5px solid #fff;
        border-top: 0;
        border-left: 0;
        transform: rotate(45deg) scale(0);
        transition: all .2s ease-in-out;
      }
    }
  }
}



</style>