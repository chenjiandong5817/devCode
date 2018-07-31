<template>
    <transition enter-active-class="animated slideInRight" leave-active-class="animated slideOutRight">
        <div class="wrap">
            <div class="onUse">
                <!-- radio美化组件 start -->
                <div class="ui-radio-label">
                    <label>
                        不使用电子券
                        <input type="radio" name="coupon" v-model="picked" value='-1' @click="setTitle('不使用电子券')">
                        <span class="ui-radio-span">
                        	<span class="ui-radio-inner"></span>
                        </span>
                    </label>
                </div>
                <!-- radio美化组件 end -->
            </div>
            <div class="content">
                <label class="labelMsg">有{{couponList.length}}个可用电子券</label>
                <ul>
                    <li class="item" v-for="(item,index) in couponList" :class="{yellow:item.type}">
                        <label  @click="setTitle(item.title)">
                            <div class="itemMsg">
                                <p class="title">
                                    {{item.title}}</p>
                                <p class="date">有效期至 {{item.date | Dateformt}}</p>
                                <span class="value" v-if="item.type ==1">{{item.discount}} <i>折</i></span>
                                <span class="value" v-else>{{item.money}} <i>元</i></span>
                            </div>
                            <div class="checkRadio">
                                <!-- radio美化组件 start -->
                                <div class="ui-radio-label">
                                    <label>
                                        {{item.des}}
                                        <input type="radio" name="coupon" v-model="picked" :value="item.id">
                                        <span class="ui-radio-span">
                                        <span class="ui-radio-inner"></span>
                                        </span>
                                    </label>
                                </div>
                                <!-- radio美化组件 end -->
                            </div>
                        </label>
                    </li>
                </ul>
            </div>
            <div class="submitBar">
              已选择: {{title}}
              <div>
              <cube-button :inline="true" :light="true" @click="goBack()">取消</cube-button>
              <cube-button :inline="true" @click="setCoupon()">确认</cube-button>
            </div>
            </div>
        </div>
    </transition>
</template>
<script>
import { mapState } from 'vuex'
export default{
  filters: {
    Dateformt (val) {
      if (val) {
        let time = new Date(val)
        return time.getFullYear() + '-' + (time.getMonth() + 1) + '-' + time.getDate()
      } else {
        return ''
      }
    }
  },
  mounted () {
    this.$store.dispatch('post_findCoupon')
  },
  data () {
    return {
      picked: this.$store.state.coupon.id,
      title: this.$store.state.coupon.title
    }
  },
  computed: mapState({
    couponList (state) {
      return state.couponList
    }
  }),
  /* watch: {
    picked (val) {
      // this.setcoupon()
      console.log(val)
      if (val === '0') {
        this.$store.commit('SET_COUPON', {
          id: '0',
          value: 0,
          money: 0,
          discount: 0,
          type: 0,
          title: '',
          des: '',
          date: 0
        })
        return
      }
      this.$store.dispatch('setcoupon', val)
    }
  }, */
  methods: {
    setTitle (val) {
      this.title = val
    },
    setCoupon () {
      this.$store.dispatch('setcoupon', this.picked)
      this.$router.go(-1)
    },
    goBack () {
      this.$router.go(-1)
    }
  }
}
</script>
<style lang="less" scoped>
@import "../../assets/css/less/variable.less";
.wrap {
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
  .onUse {
    z-index: 99;
    position: fixed;
    width: 100%;
    .boxShadow()
  }
  .content {
    padding: 48px 10px;
    .labelMsg {
      line-height: 2;
      font-size: 12px;
      color: #a9abb0;
    }
    .item {
      position: relative;
      display: block;
      width: 100%;
      line-height: 1.5;
      &:not(:first-child) {
        margin-top: 15px;
      }
      background-color: #ffffff;
      color: #57a1bc;
      background: url('../../assets/images/coupon-blue-bg.png') no-repeat;
      background-size: 100%;
      &.yellow {
        color: #d18500;
        background: url('../../assets/images/coupon-yellow-bg.png') no-repeat;
        background-size: 100%;
      }
      .itemMsg {
        position: relative;
        box-sizing: border-box;
        padding: 10px 15px;
        width: 100%;
        .title {
          font-size: 16px;
          font-weight: bold;
        }
        .date {
          font-size: 12px;
          color: #ffffff;
        }
        span.value{
        	position: absolute;
        	top: calc(50% - 36px);
        	right: 15px;
        	color: #ffffff;
        	font-size: 32px;
          i{
            font-size: 14px;
          }
        }
      }
      .checkRadio {
        font-size: 12px;
        line-height: 12px;
        .ui-radio-label{
        	border-radius: 0 0 6px 6px;
        }
      }
    }
  }
  .submitBar {
    z-index: 11;
    position: fixed;
    display: flex;
    justify-content: space-between;
    align-items: center;
    box-sizing: border-box;
    width: 100%;
    bottom: 0;
    background: #ffffff;
    padding: 10px 15px;
    color: #666;
    font-size: 14px;
  }
}


// radio美化
.ui-radio-label {
  position: relative;
  padding: 0 15px;
  background: #ffffff;
  width: 100%;
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
    margin-right: 0;
    position: absolute;
    top: calc(50% - 16px);
    right: 0;
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

