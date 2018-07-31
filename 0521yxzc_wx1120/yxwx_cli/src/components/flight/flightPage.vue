<template>
    <transition enter-active-class="animated slideInRight" leave-active-class="animated slideOutRight">
        <div class="flightPage">
          <div class="absCon">
            <h1>请您输入航班号和起飞时间</h1>
            <div class="flight border-bottom-1px">
                <i class="plane"></i>
                <!-- <span>航班号</span> -->
                <input type="text" placeholder="请输入航班号" v-model="tempNum">
            </div>
            <div class="flight border-bottom-1px" @click="showDatePicker">
                <i class="clock"></i>
                <!-- <span>起飞时间</span> -->
                <input type="text" placeholder="请选择出发时间" readonly="true" :value="tempTime | Dateformt">
            </div>
          </div>
                <div class="absbottom cube-btn-group">
                    <cube-button @click="sure">确认</cube-button>
                    <cube-button :light="true" @click="cancel">返回</cube-button>
                </div>
        </div>
    </transition>
</template>
<script>
import {mapState} from 'vuex'
const Data = initDate()
export default{
  name: 'flightPage',
  mounted () {
    if (this.airport.num) {
      this.tempNum = this.airport.num
    }
    if (this.airport.time) {
      this.tempTimeStr = this.airport.time
      this.tempTime = this.airport.time
    }
  },
  data () {
    return {
      tempNum: '',
      tempTimeStr: '',
      tempTime: 0
    }
  },
  filters: {
    Dateformt (val) {
      if (val) {
        let time = new Date(val)
        return time.getMonth() + 1 + '月' + time.getDate() + '日'
      } else {
        return ''
      }
    }
  },
  computed: mapState({
    airport (state) {
      return state.airport
    }
  }),
  methods: {
    showDatePicker () {
      this.$createPicker({
        title: '航班日期',
        data: [Data],
        onSelect: (selectedVal, selectedIndex, selectedText) => {
          this.tempTime = selectedVal[0]
          this.tempTimeStr = selectedText[0]
        },
        onCancel: () => {
          this.$createToast({
            type: 'correct',
            txt: '操作已取消',
            time: 1000
          }).show()
        }
      }).show()
    },
    sure () {
      this.$store.commit('SET_AIRPORT', {name: 'num', val: this.tempNum})
      this.$store.commit('SET_AIRPORT', {name: 'time', val: this.tempTime})
      this.$router.go(-1)
    },
    cancel () {
      this.$router.go(-1)
    }
  }
}
function initDate () {
  var Datelist = []
  for (let i = 0; i < 30; i++) {
    let now = new Date()
    let item = {}
    let future = new Date(now.setDate(now.getDate() + i - 1))
    item['text'] = (future.getMonth() + 1) + '月' + future.getDate() + '日'
    item['value'] = Date.parse(future)
    Datelist.push(item)
  }
  return Datelist
}
</script>
<style lang="less">
@import "../../assets/css/less/variable.less";
.flightPage {
  //display: block;
  // display: flex;
  // flex-direction:column;
  // justify-content:space-between;
  box-sizing: border-box;
  position: fixed;
  overflow: hidden;
  z-index: 9;
  top: 0;
  left: 0;
  bottom: 0;
  width: 100%;
  padding: 10px;
  height: 100%;
  background: #f3f3f3;
  animation-duration: .3s;
  h1 {
    text-align: center;
    font-size: 16px;
    line-height: 2;
    padding: 15px 0;
    color: #6b7886;
  }
  .flight {
    display: flex;
    flex-grow: 1;
    align-items: center;
    position: relative;
    padding: 0 15px;
    height: 50px;
    line-height: 50px;
    font-size: 16px;
    color: #6b7886;
    background: #fff;
    span {
      line-height: 100%;
      font-size: 14px;
    }
    input {
      width: 100%;
      height: 100%;
      font-size: 12px;
      color: #6b7886;
    }
  }
  .absbottom {
    // z-index: -1;
    box-sizing: border-box;
    //position: relative;
    margin-top: -140px;
    width: 100%;
    bottom: 10px;
    left: 0;
    padding: 10px;
  }
}
.absCon{
  display: block;
  width: 100%;
  height: 100%;
  min-height: 450px;
  padding-bottom: 150px;
  box-sizing: border-box;
  background: #f3f3f3;
}
i.plane {
  //position: relative;
  //display: inline-block;
  width: 12px;
  height: 12px;
  margin:0 6px; 
  background: url('../../assets/images/plane.png') no-repeat center;
  background-size:100% 100%; 
}

i.clock {
  //position: relative;
  //display: inline-block;
  width: 12px;
  height: 12px;
  margin:0 6px; 
  background: url('../../assets/images/clock.png') no-repeat center;
  background-size:100% 100%; 
}

</style>