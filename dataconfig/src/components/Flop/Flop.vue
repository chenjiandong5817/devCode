<template>
  <div :style="{
    overflow: 'hidden',
    position: 'relative',
  }">
    <div :style="{
      position: 'relative',
      textAlign: 'center',
      height: `${ height }px`,

    }">
      <slot></slot>
    </div>
  </div>
</template>
<script>
export default {
  // 旋转翻牌
  props: {
    height: {
      type: Number,
      default: 0
    },
    interval: {
      type: Number,
      default: 3000
    }
  },
  data () {
    return {
      activeIndex: -1,
      items: [],
      timer: null,
      // 初始化标志
      initSign: true
    }
  },
  watch: {
    items (val, oldVal) {
      if (val.length === 0) {
        // activeIndex初始化之后为0，造空行 会使得item销毁，当item重新初始化active默认为false
        // 当item再次有值的时候，因为length = 1的时候无法触发changeItemSurface， item的active始终为false
        // 故强行设置activeIndex为未初始化状态, 在下次切换时候触发activeIndex的watch函数
        this.setActiveItem(-1)
      } else if (val.length !== oldVal.length) {
        this.setActiveItem(0)
      }
    },
    activeIndex (val, oldVal) {
      this.changeItemSurface(val)
    }
  },
  methods: {
    updateItems () {
      this.items = this.$children.filter(child => child.$options.name === 'FlopItem')
      if (this.initSign) {
        this.initSign = false
        this.startTimer()
      }
    },
    setActiveItem(index) {
      index = Number(index)
      if (isNaN(index) || index !== Math.floor(index)) {
        process.env.NODE_ENV !== 'production' &&
        console.warn('index must be an integer.')
        return
      }
      let length = this.items.length
      if (index < 0) {
        this.activeIndex = length - 1
      } else if (index >= length) {
        this.activeIndex = 0
      } else {
        this.activeIndex = index
      }
    },
    startTimer() {
      if (this.interval <= 0) return
      this.timer = setInterval(this.playSlides, this.interval)
    },
    pauseTimer() {
      clearInterval(this.timer)
    },
    playSlides() {
      if (this.items.length === 0) {
        return
      }
      if (this.activeIndex < this.items.length - 1) {
        this.activeIndex++
      } else {
        this.activeIndex = 0
      }
    },
    changeItemSurface(activeIndex) {
      this.items.forEach((item, index) => {
        if (index === activeIndex) {
          item.rotateItem()
        } else {
          item.resetItemSurface()
        }
      })
    }
  },
  mounted () {
    // this.updateItems()
    // this.$nextTick(() => {
    //   this.startTimer()
    // })
  },
  updated () {
    // console.log('update')
    this.updateItems()
  }
}
</script>
<style>
.radius-class {
  background: #fff;
  -moz-border-radius: 60px;
  -webkit-border-radius: 60px;
  border-radius: 50px;
}
</style>
