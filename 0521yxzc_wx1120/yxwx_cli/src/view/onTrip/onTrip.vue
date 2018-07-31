<!-- 等待应答 -->
<template>
    <div class="wrap">
        <svg class="svg" xmlns="http://www.w3.org/2000/svg" version="1.1">
            <circle cx="110" cy="110" r="100" stroke="rgb(0,181,230)" stroke-width="1" fill="rgb(255,255,255)" />
            <circle :cx="X" :cy="Y" r="5" fill="rgb(0,181,230)" />
            <text x="110" y="110" fill="rgb(0,181,230)" ref="waitTime">{{text | dataFilter}}</text>
        </svg>
    </div>
</template>
<script>
export default{
  data () {
    return {
      X: 110,
      Y: 110,
      text: 0
    }
  },
  filters: {
    dataFilter (date) {
      var s = parseInt(date % 60) > 9 ? parseInt(date % 60) : ('0' + parseInt(date % 60))
      var m = parseInt(date / 60) > 9 ? parseInt(date / 60) : ('0' + parseInt(date / 60))
      return m + ':' + s
    }
  },
  mounted () {
    this.init()
  },
  methods: {
    init () {
      var self = this
      var rad = -180
      var PI = Math.PI
      var r = 100
      var waitTime = 0
      setInterval(function () {
        if (rad < 180) {
          self.X = Math.cos(PI / 180 * rad) * r + 110
          self.Y = Math.sin(PI / 180 * rad) * r + 110
          rad++
        } else {
          rad = -180
          self.X = Math.cos(PI / 180 * rad) * r + 110
          self.Y = Math.sin(PI / 180 * rad) * r + 110
        }
        waitTime = waitTime + 10
        self.text = waitTime / 1000
      }, 10)
    }
  }
}
</script>
<style lang="less">
.wrap {
	z-index: 9;
    position: fixed;
    display: block;
    width: 100%;
    height: 100%;
    top: 0;
    bottom: 0;
    left: 0;
    background: #f3f3f3;
    text-align: center;
    .svg {
        width: 220px;
        height: 220px;
        text {
            font-size: 48px;
            text-anchor: middle;
            /* 文本水平居中 */
            dominant-baseline: middle;
            /* 文本垂直居中 */
        }
    }
}
</style>