<template>
  <div class="flex-image">
    <div class="flex-image__container" :style="{
      height: height
    }">
      <raiis-img v-for="img in imgs" :key="img" :src="img" :height="height"></raiis-img>
    </div>
  </div>
</template>

<script>
import RaiisImg from './FlexImageItem'
export default {
  props: {
    height: {
      type: String,
      default: `300px`
    },
    start: {
      type: Number,
      default: 0
    },
    delay: {
      type: Number,
      default: 0
    },
    imgs: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {
      items: [],
      activeIndex: -1,
      padding: 30,
      timer: null,
      interval: 8000
    }
  },
  watch: {
    items (val, oldVal) {
      if (val.length > 0) {
        this.setActiveItem(this.startIndex)
      }
    },
    activeIndex (val, oldVal) {
      this.resetItemPosition()
    }
  },
  computed: {
    startIndex () {
      return this.start >= this.items.length ? this.items.length - 1 : this.start
    }
  },
  components: {
    RaiisImg: RaiisImg
  },
  methods: {
    updateItems() {
      this.items = this.$children.filter(child => child.$options.name === 'FlexImageItem')
    },
    resetItemPosition () {
      this.items.forEach((item, index) => {
        //
        item.translateItem(index, this.activeIndex)
      })
    },
    setActiveItem (index) {
      let length = this.items.length
      if (index < 0) {
        this.activeIndex = length - 1
      } else if (index >= length) {
        this.activeIndex = 0
      } else {
        this.activeIndex = index
      }
    },
    playSlides() {
      if (this.activeIndex < this.items.length - 1) {
        this.activeIndex++
      } else {
        this.activeIndex = 0
      }
    },
    pauseTimer() {
      clearInterval(this.timer)
    },
    startTimer() {
      this.timer = setInterval(this.playSlides, this.interval)
    }
  },
  mounted() {
    let self = this
    setTimeout(() => {
      self.updateItems()
    }, 200)

    // this.updateItems()
    this.$nextTick(() => {
      if (this.delay > 0) {
        setTimeout(this.startTimer, this.delay)
      } else {
        this.startTimer()
      }
    })
  }

}
</script>
<style>
.flex-image {
  position: relative;
  text-align: center;
  overflow: hidden;
}

.flex-image::before {
  display: none;
}

.flex-image::after {
  clear: both;
}

.flex-image__container {
  position: relative;
}
</style>
