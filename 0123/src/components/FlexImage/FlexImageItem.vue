<template>
  <div :class="['flex-image-item', {'is-active': active}]" :style="{
    transform: `translateX(${ translate }px)`,
    width: `${ width }px`,
    marginLeft: `${ marginPx }px`,
    marginRight: `${ marginPx }px`
  }">
    <img :src="src" :style="{
      height: height,
      transform: `scale(${ scale })`
    }" />
  </div>
</template>
<script>
export default {
  name: 'FlexImageItem',
  props: {
    src: {
      type: String,
      default: ''
    },
    height: {
      type: String,
      default: `0px`
    }
  },
  data () {
    return {
      translate: 0,
      scale: 1,
      active: false,
      width: 0,
      marginPx: 5
    }
  },
  methods: {
    processIndex (index, activeIndex, length) {
      if (index <= activeIndex) {
        return length - 1 - (activeIndex - index)
      } else {
        return index - activeIndex - 1
      }
    },
    calculateTranslate (index, parentWidth) {
      return index * ((parentWidth / 3))
    },
    translateItem (index, activeIndex) {
      const parentWidth = this.$parent.$el.offsetWidth
      const length = this.$parent.items.length
      this.active = index === activeIndex
      let count = this.processIndex(index, activeIndex, length)
      if (this.active) {
        let self = this
        setTimeout(() => {
          self.scale = 0
          self.translate = this.calculateTranslate(count, parentWidth)
          setTimeout(() => {
            self.scale = 1
          }, 1000)
        }, 1000)
        this.translate = this.calculateTranslate(-1, parentWidth)
      } else {
        this.translate = this.calculateTranslate(count, parentWidth)
      }
    }
  },
  mounted () {
    this.width = (this.$parent.$el.offsetWidth / 3) - this.marginPx * 2
  }

}
</script>
<style>
.flex-image-item {
  position: absolute;
  top: 0;
  left: 0;
  display: inline-block;
  overflow: hidden;
  transition: transform 1s ease-in-out;
  z-index: 1;
}

.is-active {
  z-index: 0;

}

</style>
