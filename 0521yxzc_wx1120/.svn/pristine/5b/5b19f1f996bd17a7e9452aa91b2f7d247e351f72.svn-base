<template>
    <transition enter-active-class="animated slideInRight" leave-active-class="animated slideOutRight">
        <div class="wrap">
            <div class="content">
                <label class="labelMsg">有{{list.length}}个可用电子券</label>
                <ul>
                    <li v-for="(item,index) in list" :class="{yellow:item.type}">
                        <label class="item" >
                                <!-- radio美化组件 start -->
                                <div class="ui-checkbox-label">
                                    <label>
                                        {{item.des}}
                                        <input type="checkbox" name="coupon" v-model="picked" :value="item.uuid">
                                        <span class="ui-checkbox-span">
                                        <span class="ui-checkbox-inner"></span>
                                        </span>
                                    </label>
                                </div>
                                <!-- radio美化组件 end -->
                            <div class="itemMsg">
                              <div>
                                <p class="date">{{item.date | Dateformt}} <span class="carType">{{item.type}}</span></p>
                                <p class="from">{{item.from}}</p>
                                <p class="to">{{item.to}}</p></div>
                                <div>{{item.money}}</div>
                            </div>
                        </label>
                    </li>
                </ul>
            </div>
            <div class="submitBar">
              已选择
              <cube-button :inline="true" @click="setcoupon">确认</cube-button>
            </div>
        </div>
    </transition>
</template>
<script>
import { createNamespacedHelpers } from 'vuex'
const list = [
  {
    uuid: 'sdfshdfj2345h4k3j',
    date: 1516167463000,
    type: '专车',
    from: '厦门市湖里大道南山路12号',
    to: '厦门市会展中心',
    money: 23.4
  },
  {
    uuid: 'g3gh324h5j3h5',
    date: 12673462836482,
    type: '专车',
    from: '厦门市软件园',
    to: '观音山商务中心',
    money: 12.4
  },
  {
    uuid: 'g23h4523h4g5jv',
    date: 523646345746535,
    type: '专车',
    from: '厦门市软件园',
    to: '厦门市会展中心',
    money: 34.4
  },
  {
    uuid: 'jh5h2k4h2h34b',
    date: 343536564564565,
    type: '专车',
    from: '思明区古地石社公交站',
    to: '泉州市南安县',
    money: 323.4
  }
]
const { mapState } = createNamespacedHelpers('@store/invoice/index')
export default{
  filters: {
    Dateformt (val) {
      if (val) {
        let time = new Date(val)
        return (time.getMonth() + 1) + '月' + time.getDate() + '日'
      } else {
        return ''
      }
    }
  },
  mounted () {
    this.$store.dispatch('get_tripList')
  },
  data () {
    return {
      picked: [],
      list: list
    }
  },
  computed: {
    ...mapState([
      'tripList'
    ])
  },
  methods: {
    setcoupon () {
      console.log('开关')
    }
  }
}
</script>
<style lang="less" scoped>
@import "../../../assets/css/less/variable.less";
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
  .content {
    padding: 10px;
    .labelMsg {
      line-height: 2;
      font-size: 12px;
      color: #a9abb0;
    }
    li {
      .boxShadow();
      &:not(:first-child) {
        margin-top: 15px;
      }
    }
    label.item {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 15px;
      background-color: #ffffff;
    }
    .itemMsg {
      position: relative;
      display: flex;
      justify-content: space-between;
      align-items: center;
      box-sizing: border-box;
      width: 100%;
      font-size: 13px;
      color: #4f5a67;
      line-height: 1.8;
      p {
        position: relative;
        padding-left: 15px;
      }
      .from {
        &:before {
          content: '';
          position: absolute;
          width: 5px;
          height: 5px;
          top: 50%;
          left: 0;
          margin-top: -2.5px;
          border-radius: 50%;
          background: #22cedb;
        }
      }
      .to {
        &:before {
          content: '';
          position: absolute;
          width: 5px;
          height: 5px;
          top: 50%;
          left: 0;
          margin-top: -2.5px;
          border-radius: 50%;
          background: #f99358;
        }
      }
      .date {
        color: #9ea7b1;
        &:before {
          content: '';
          position: absolute;
          width: 8px;
          height: 8px;
          top: 50%;
          left: 0;
          margin-top: -4px;
          background: url('../../../assets/images/clock.png') no-repeat center;
          background-size: 100% 100%;
        }
        span.carType{
          padding: 2px 10px;
          border-radius: 2px;
          border:.5px solid #f3f3f3;
          font-size: 10px;
        }
      }
    }
  }
}

// radio美化
.ui-checkbox-label {
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
      background: #00b5e6;
      border: 1px solid #00b5e6;
      &:after {
        transform: rotate(45deg) scale(1);
      }
    }
  }
  .ui-checkbox-span {
    position: absolute;
    right: 0;
    cursor: pointer;
    .ui-checkbox-inner {
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
        z-index: 99;
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


