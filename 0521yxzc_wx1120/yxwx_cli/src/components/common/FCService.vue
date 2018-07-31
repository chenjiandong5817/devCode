<template>
    <div class="fcservice" v-bind:class="{active:checked}">
        <div class="checkBtn">
            <div class="ui-checkbox-label">
                <label>
                    <input type="checkbox" name="coupon" v-model="checked">
                    <span class="ui-checkbox-span">
                      <span class="ui-checkbox-inner"></span>
                    </span>
                    <span class="text">赠送头等舱服务</span>
                    <span class="href" @click="changeUrl()">?</span>
                </label>
            </div>
        </div>
        <div class="addInput">
            <span class="minus" v-bind:class="{disable:( VIPnum <= 1)}" v-on:click="checked && ( VIPnum > 1) && minusVIP()">&minus;</span>
            <input type="text" readonly="readonly" placeholder="1" v-model="VIPnum" disabled="true">
            <span class="add" v-bind:class="{disable:( VIPnum >= 6)}" v-on:click="checked && ( VIPnum < 6) && addVIP()">+</span>位
        </div>
    </div>
</template>
<script>
import {mapState} from 'vuex'
export default{
  name: 'fcservice',
  methods: {
    minusVIP () {
      this.VIPnum--
    },
    addVIP () {
      this.VIPnum++
    },
    changeUrl () {
      // window.location.href = this.sysAreaConfigDto.flightVipExplain
      window.location.href = '/wechat/view/ruleDeclaration'
    }
  },
  computed: {
    ...mapState([
      'carType',
      'sysAreaConfigDto'
    ]),
    VIPnum: {
      get () {
        return this.$store.state.VIPnum
      },
      set (newVal) {
        if (this.carType.modelNum !== 2) {
          if (newVal > 4) {
            let toast = this.$createToast({
              time: 1000,
              type: 'warn',
              mask: true,
              txt: '赠送服务人数已超过该车型(4人)限制'
            })
            toast.show()
            return
          }
        }
        this.$store.commit('SET_TOPSERVICE', {name: 'num', val: newVal})
      }
    },
    checked: {
      get () {
        return this.$store.state.topService
      },
      set (newVal) {
        this.$store.commit('SET_TOPSERVICE', {name: 'checked', val: newVal})
      }
    }
  }
}
</script>
<style lang="less">
@import "../../assets/css/less/variable.less";
.fcservice {
  box-sizing: border-box;
  position: relative;
  padding: 5px;
  height: 50px;
  font-size: 12px;
  color: @fc_base;
  background: #ffffff;
  border: .5px solid #f3f3f3;
  &.active {
    border: .5px solid @fc_active;
    &:before {
      border-top: 22px solid #00b5e6;
    }
  }
  &:before {
    z-index: 1;
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    width: 0;
    height: 0;
    border-top: 22px solid #dde1e6;
    border-right: 30px solid transparent;
  }
  &:after {
    z-index: 1;
    content: '';
    position: absolute;
    top: 1px;
    left: 0;
    width: 15px;
    height: 15px;
    background: url('../../assets/images/vip.png') no-repeat center;
    background-size: 100% 100%;
    transform: rotate(-35deg);
  }
  .addInput {
    z-index: 1;
    position: absolute;
    top: 50%;
    right: 30px;
    margin-top: -9px;
    input {
      width: 18px;
      height: 18px;
      border: .5px solid #e5e5e5;
      text-align: center;
      &:disabled{
        color: #4f5a67;
        background: #ffffff;
      }
    }
    span {
      display: inline-block;
      height: 19px;
      line-height: 19px;
      text-align: center;
      font-size: 14px;
      padding: 0 10px;
      &.disable {
        color: #dde1e6;
      }
    }
  }
  // radio美化
  .ui-checkbox-label {
    position: relative;
    padding: 0 15px;
    background: #ffffff;
    width: 100%;
    box-sizing: border-box;
    span.text {
      padding-left: 25px;
      a {
        display: inline-block;
      }
    }
    span.href {
      content: '?';
      display: inline-block;
      text-align: center;
      line-height: 12px;
      position: relative;
      z-index: 9;
      margin-left: 15px;
      width: 12px;
      height: 12px;
      font-size: 8px;
      border-radius: 50%;
      background: #b1b6be;
      color: #ffffff;
    }
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
    input[type='checkbox'] {
      z-index: 1;
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      opacity: 0;
    }
    input[type='checkbox']:checked+.ui-checkbox-span {
      .ui-checkbox-inner {
        background: @fc_active;
        border: 1px solid @fc_active;
        &:after {
          transform: rotate(45deg) scale(1);
        }
      }
    }
    .ui-checkbox-span {
      margin-right: 0;
      position: absolute;
      top: calc(50% - 16px);
      left: 0;
      top: 6px;
      cursor: pointer;
      .ui-checkbox-inner {
        display: inline-block;
        width: 10px;
        height: 10px;
        border-radius: 50%;
        position: relative;
        top: 6px;
        left: 0;
        border: 1px solid #dddee1;
        background-color: #dcdfe3;
        transition: border-color .2s ease-in-out, background-color .2s ease-in-out;
        &:after {
          content: '';
          display: table;
          position: absolute;
          width: 4px;
          height: 7px;
          z-index: 9;
          top: 0;
          left: 3px;
          border: 1.5px solid #fff;
          border-top: 0;
          border-left: 0;
          transform: rotate(45deg) scale(1);
          transition: all .2s ease-in-out;
        }
      }
    }
  }
}


</style>