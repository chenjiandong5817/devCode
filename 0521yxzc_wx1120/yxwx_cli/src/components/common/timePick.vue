<template>
  <div class="taxi-time" @click="showTimePicker">
    <i class="clock"></i>
    <span>{{selectedText | Dateformt}}</span>
</div>
</template>
<script>
import {mapState} from 'vuex'
export default {
  filters: {
    Dateformt (val) {
      if (val === '现在') {
        return '现在出发'
      } else return val.replace(/[:]/g, '')
    }
  },
  computed: mapState({
    selectedText (state) {
      return state.order.departTimeTXT
    }
  }),
  methods: {
    showTimePicker () {
      this.$createTimePicker({
        showNow: true,
        minuteStep: 10,
        delay: 15,
        day: {
          len: 3,
          filter: ['今天', '明天', '后天'],
          format: 'M月d日'
        },
        onSelect: (selectedTime, selectedText) => {
          // console.log('selectedTime', selectedTime)
          // console.log('selectedText', selectedText)
          // // 判断是哪个时区的，然后自动转换成北京时间
          // // 从系统获取时间
          // var systemTime = new Date()
          // // 将从系统获取的时间转换为字符串
          // systemTime = systemTime.toString()
          // // 获取所在时区的字符串
          // var timaArea = systemTime.slice(systemTime.length - 12, systemTime.length - 11)
          // // 将所在时区的字符串转换为数字
          // timaArea = parseInt(timaArea)
          // if (timaArea === 8) {
          //   selectedTime = selectedTime * 1
          // }
          // if (timaArea === 0) {
          //   selectedTime = selectedTime + 8 * 60 * 60 * 1000
          //   // 改变现实时间
          //   // 获取哪个钟时并且转化为数字
          //   // var hourNumber = parseInt(selectedText.slice(selectedText.length - 6, selectedText.length - 5))
          //   // console.log('hourNumber', hourNumber)
          //   // var hourChina = (hourNumber + 8) % 24
          //   // console.log('hourChina', hourChina)
          //   // if (selectedText.length === 9) {
          //   //   selectedText = selectedText.slice(0, 3) + hourChina + selectedText.slice(4, 9)
          //   // }
          //   // if (selectedText.length === 10) {
          //   //   selectedText = selectedText.slice(0, 3) + hourChina + selectedText.slice(5, 9)
          //   // }
          // }
          // console.log('selectedTime', selectedTime)
          // console.log('selectedText', selectedText)
          this.$store.dispatch('switch_order', {name: 'departTime', val: selectedTime})
          this.$store.dispatch('switch_order', {name: 'departTimeTXT', val: selectedText})
        },
        onCancel: () => {
          this.$createToast({
            type: 'correct',
            txt: '操作已取消',
            time: 1000
          }).show()
        }
      }).show()
    }
  }
}
</script>
<style lang="less">
.taxi-time{
  position: relative;
  padding: 0 15px;
  height: 50px;
  line-height: 50px;
  color: #6b7886;
  background: #fff;
  span{
    line-height: 100%;
    font-size: 12px;
  }
}
i.clock{
  position: relative;
  display: inline-block;
  width: 12px;
  height: 12px;
  margin:0 6px; 
  background: url('../../assets/images/clock.png') no-repeat center;
  background-size:100% 100%; 
}
</style>