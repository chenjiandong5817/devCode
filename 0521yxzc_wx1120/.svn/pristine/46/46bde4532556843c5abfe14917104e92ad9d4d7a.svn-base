<template>
    <transition enter-active-class="animated slideInRight" leave-active-class="animated slideOutRight">
        <div class="wrap">
            <div class="content">
                <ul>
                  <!-- 纸质发票已开票提示 -->
                    <li v-if="invoiceData.status===2 && invoiceData.type===1">
                        <div class="itemMsg">
                            <p>
                                <span>纸质发票预计在开票后<span class="status1">5个工作日</span>内寄到</span>
                            </p>
                        </div>
                    </li>
                    <!-- 已开票 -->
                    <li v-if="invoiceData.status===2">
                        <div class="itemMsg" @click="openPDF(invoiceData.type)">
                            <p :class="{'desc':invoiceData.type===2}">
                                <label>{{invoiceData.type===2?'电子发票':'纸质发票'}}
                                  <p v-if="invoiceData.type===1" class="kpdate">{{invoiceData.invoicedate | Dateformt}}</p></label>
                                <span :class="['status'+invoiceData.status , invoiceData.type===1?'no_pad_r':'']">{{invoiceData.status | statusFormt}}</span>
                            </p>
                        </div>
                    </li>
                    <!-- 待开票、退票中、已退票 -->
                    <li v-else>
                        <div class="itemMsg">
                            <p>
                                <label>{{invoiceData.type===2?'电子发票':'纸质发票'}}</label>
                                <span :class="'status'+invoiceData.status">{{invoiceData.status | statusFormt}}</span>
                            </p>
                        </div>
                    </li>
                    <li>
                        <div class="itemMsg">
                            <p>
                                <label>抬头类型：</label><span>{{(invoiceData.accountType===1)?'公司':'个人'}}</span></p>
                            <p>
                                <label>发票抬头：</label><span>{{invoiceData.buyername}}</span></p>
                            <p>
                                <label>纳税人识别号：</label><span>{{invoiceData.taxnum}}</span></p>
                            <p>
                                <label>发票金额：</label><span>￥{{invoiceData.ordertotal}}</span></p>
                            <p>
                                <label>发票内容：</label><span>客运服务费</span></p>
                            <p>
                                <label>申请时间：</label><span>{{invoiceData.createTime | Dateformt}}</span></p>
                            <p v-if="invoiceData.type===2">
                                <label>电子邮件：</label><span>{{invoiceData.email}}</span></p>
                            <p>
                                <label>备注说明：</label><span>{{invoiceData.message}}</span></p>
                            <p>
                                <label>地址：</label><span>{{invoiceData.address}}</span></p>
                            <p>
                                <label>电话：</label><span>{{invoiceData.telephone}}</span></p>
                            <p>
                                <label>开户行及账号：</label><span>{{invoiceData.account}}</span></p>
                        </div>
                    </li>
                    <!-- 纸质发票收件信息 -->
                    <li v-if="(invoiceData.type===1)">
                        <div class="itemMsg">
                            <p>
                                <label>收件人：</label><span>{{invoiceData.recipient}}</span></p>
                            <p>
                                <label>联系电话：</label><span>{{invoiceData.mobile}}</span></p>
                            <p>
                                <label>收件地址：</label><span>{{invoiceData.area}}{{invoiceData.detailAddress}}</span></p>
                            <p>
                                <label>运费支付：</label><span>{{invoiceData.freightType===1?'货到付款':'免运费'}}</span></p>
                        </div>
                    </li>
                    <!-- 行程开票且状态正常则显示行程说明 -->
                    <li v-if="invoiceData.ticketType===1 && invoiceData.status!==3 && invoiceData.status!==4" @click="toTripDesc">
                        <div class="itemMsg">
                            <p class="desc">
                                <label>行程说明</label>
                                <span class="status2">查看</span>
                            </p>
                        </div>
                    </li>
                </ul>
            </div>
            <a class="absBottom" href="tel:059296363">
                <i class="tel"></i>
                <span>0592-96363转0</span>
            </a>
        </div>
    </transition>
</template>
<script>
import { mapState } from 'vuex'
const scroll = () => import('@/components/scroll')
export default{
  filters: {
    Dateformt (val) {
      if (typeof val === 'number') {
        let time = new Date(val)
        let Minutes = time.getMinutes() < 10 ? '0' + time.getMinutes() : time.getMinutes()
        return time.getMonth() + 1 + '月' + time.getDate() + '日' + ' ' + time.getHours() + ':' + Minutes
      } else return val
    },
    statusFormt (val) {
      switch (val) {
        case 1:
          return '开票中'
        case 2:
          return '已开票'
        case 3:
          return '已取消'
        default:
          return '已退票'
      }
    }
  },
  mounted () {
    this.uuid = this.$route.params.uuid
    // this.uuid = this.$route.query.uuid
  },
  data () {
    return {
      uuid: this.$route.params.uuid
      // uuid: this.$route.query.uuid
    }
  },
  computed: mapState({
    // 根据orderno查询对应数据
    invoiceData (state) {
      let self = this
      let data
      state.invoiceHistory.forEach(function (el, index) {
        if (self.uuid === el.invoiceUuid) {
          data = el
        }
      })
      return data
    }
  }),
  methods: {
    // 打开pdf链接
    openPDF (type) {
      // window.location.href = this.invoiceData.pdfUrl
      if (type === 2) {
        window.open(this.invoiceData.pdfUrl, '_top')
      }
    },
    // 路由跳转
    toTripDesc () {
      this.$router.push('../invoiceHistoryTrip/' + this.uuid)
      /* this.$router.push({
        path: '../invoiceHistoryTrip',
        query: {uuid: item.orderno}
      }) */
    }
  },
  components: {
    scroll
  }
}
</script>
<style lang="less" scoped>
@import "../../assets/css/less/variable.less";
.wrap {
  display: flex;
  overflow-y:scroll; 
  flex-direction: column;
  justify-content: space-between;
  z-index: 9;
  top: 0;
  bottom: 0;
  left: 0;
  position: fixed;
  overflow-y: scroll;
  overflow-x: hidden;
  width: 100%;
  height: 100%;
  background: #f3f3f3;
  animation-duration: .3s;
  &::-webkit-scrollbar {
    display: none;
  }
  .content {
    flex-grow: 1;
    ul {
      position: relative;
      display: block;
      overflow: hidden;
    }
    li {
      padding: 15px;
      background: #ffffff;
      margin-bottom: 10px;
      &:not(:last-child) {
        border-bottom: .5px solid #f3f3f3;
      }
    }
    .itemMsg {
      position: relative;
      box-sizing: border-box;
      width: 100%;
      font-size: 13px;
      color: #4f5a67;
      line-height: 1.8;
      p {
        color: #4f5a67;
        position: relative;
        display: flex;
        justify-content: space-between;
        label {
          width: 100px;
        }
        span {
          text-align: right;
        }
        .status1 {
          color: #00b5e6;
        }
        .status2 {
          padding-right: 15px;
          color: #9ea7b1;
        }
        .status3 {
          color: #f16565;
        }
        .status4 {
          color: #f16565;
        }
        .no_pad_r{
          padding-right: 0;
        }
        &.desc:after {
          content: '';
          position: absolute;
          border-right: 1px solid #c5c7ca;
          border-bottom: 1px solid #c5c7ca;
          width: 8px;
          height: 8px;
          transform: rotate(-45deg);
          top: 50%;
          margin-top: -4px;
          right: 0;
        }
        &.kpdate{
          font-size: 12px;
          line-height: 1;
          color: #A8ABAF;
        }
      }
    }
  }
  .absBottom {
    position: relative;
    width: 100%;
    margin-bottom: 15px;
    font-size: 18px;
    text-align: center;
    color: @fc_active;
    .tel {
      display: inline-block;
      width: 16px;
      height: 16px;
      background: url('./images/order_driver_phone.png') no-repeat center;
      background-size: 100% 100%;
    }
  }
}


</style>


