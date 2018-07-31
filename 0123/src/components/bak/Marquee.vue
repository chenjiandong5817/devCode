<template>
    <div class="raiisMarquee"
         :style="{
              transform: `translateX(${ translate }px) scale(1)`,
              transition: `transform linear ${ transition }s`
            }"
         >
      <div class="marqueeMain">
        <div class="marqueeContent"
             :style="{
               lineHeight: height ? `${ height }px` : `auto`,
               width: width ? `${ width }px` : `auto`
             }"
        >
          {{ content }}
        </div>
        <div :style="{
                margin: `${ spacing }px`,
                display: 'inline'
              }"></div>
        <div class="marqueeContent"></div>
      </div>
    </div>
</template>
<script>
import debounce from 'throttle-debounce/debounce'
export default {
  name: 'Marquee',
  // 传入参数
  props: {
    // Marquee 文本内容
    content: {
      type: [String, Number],
      default: ''
    },
    // 滚动速度，1-10
    speed: {
      type: Number,
      validator: function (value) {
        return value >= 1 && value <= 10
      },
      default: 3
    },
    // 滚动文本之间的间隔
    spacing: {
      type: Number,
      default: 20
    },
    // 高度
    height: {
      type: Number,
      default: 55
    },
    // 宽度
    width: {
      type: Number,
      default: 0
    }
  },
  data() {
    return {
      // timeout事件的ID
      zeroTimeoutId: null,
      recoverTimeoutId: null,
      // 开始滚动初始位置
      translate: 0,
      // 过度特效完成时间
      transition: 0,
      // 起始位置
      startPosition: 35,
      speedTime: 10
    }
  },
  computed: {
    // speedTime () {
    //   let width = this.$el.children[0].children[0].offsetWidth
    //   // console.log(width)
    //   return ((width / 200) * 10) / this.speed
    //   // return 16 - this.speed * 3
    // }
  },
  // 监听数据
  watch: {
    // 监听文本替换
    content: function (val, oldVal) {
      // setTimeout(this.init, 100)
      let m = () => {
        this.setSpeedTime()
        // 切换语言，滚动速度不一致
        this.transition = this.speedTime
        this.stopScroll()
        this.startScroll(val)
      }
      setTimeout(m, 50)
    },
    translate: function (val, oldVal) {
      if (val === 0 || val === -1 || val === this.spacing * 2 || val === this.startPosition) { // 初始位置
        this.transition = 0
        this.zeroTimeoutId = setTimeout(() => {
          if (this.checkScroll()) {
            // this.transition = this.speedTime
            this.translate = 0 - (this.$el.children[0].children[0].offsetWidth)
          }
        }, 5)
      } else {
        this.initStartPosition()
        this.transition = this.speedTime
        this.recoverTimeoutId = setTimeout(() => {
          this.translate = this.spacing * 2
        }, this.speedTime * 1000)
      }
    }
  },
  methods: {
    // 初始化，停止滚动，重新开始
    init: function () {
      this.stopScroll()
      if (this.checkScroll()) {
        this.setSpeedTime()
        this.initStartPosition()
        this.startScroll()
      }
    },
    // 初始化起始位置
    initStartPosition: function () {
      this.startPosition = this.$el.offsetWidth / 3
    },
    // 检测是否需要滚动
    checkScroll: function () {
      let isScroll = this.$el.offsetWidth < this.$el.children[0].children[0].offsetWidth
      if (isScroll) {
        this.$el.children[0].children[1].style.display = 'inline'
      } else {
        this.$el.children[0].children[1].style.display = 'none'
      }
      return isScroll
    },
    // 开始滚动
    startScroll: function (content) {
      if (this.checkScroll()) {
        this.$el.children[0].children[2].innerHTML = content || this.content
        this.translate = this.startPosition
      } else {
        this.$el.children[0].children[2].innerHTML = ''
        this.translate = 0 // this.startPosition
      }
    },
    // 停止滚动
    stopScroll: function () {
      this.transition = 0
      this.translate = 0
      this.$el.children[0].children[2].innerHTML = ''
      clearTimeout(this.zeroTimeoutId)
      clearTimeout(this.recoverTimeoutId)
    },
    // 滚动时间
    setSpeedTime () {
      let width = this.$el.children[0].children[0].offsetWidth
      this.speedTime = ((width / 300) * 10) / this.speed
    }
  },
  created() {
  },
  mounted() {
    // 监听resize事件，判断是否需要滚动
    window.addEventListener('resize', debounce(100, this.init))
    this.init()
  }
}
</script>
<style>
.marqueeContent {
  display: inline;
}
</style>
