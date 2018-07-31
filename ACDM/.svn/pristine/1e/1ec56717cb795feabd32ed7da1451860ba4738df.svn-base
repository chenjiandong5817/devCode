<template>
  <div class="fishbone-container">
    <div class="fishbone-content" :class="['fishbone--' + direction]" :style="containerStyle">
      <slot></slot>
      <div class="fishbone-backbone">
        <div :class="['fishbone-backbone--inner', {'is-finish': isFinish}]" :style="style">
          <div class="fishbone-backbone--progress" :style="{ width: progressWidth }"></div>
        </div>
      </div>
    </div>
    <el-tooltip placement="top" ref="tooltip" :content="tooltipContent"></el-tooltip>
  </div>
</template>

<script>
import debounce from 'throttle-debounce/debounce'
export default {
  name: 'FishBone',
  props: {
    space: [Number, String],
    direction: {
      type: String,
      default: 'horizontal'
    },
    alignCenter: Boolean,
    simple: Boolean,
    finishStatus: {
      type: String,
      default: 'finish'
    },
    processStatus: {
      type: String,
      default: 'process'
    },
    height: {
      type: Number,
      default: 150
    },
    width: {
      type: Number,
      default: 100
    }
  },
  data () {
    return {
      bones: [],
      boneOffset: 0,
      tooltipContent: ''
    }
  },
  computed: {
    containerWidth () {
      return this.bones.length * this.width + 200
    },
    containerStyle () {
      let style = {}
      style.height = `${this.height * 2}px`
      style.width = `${this.containerWidth}px`
      return style
    },
    style () {
      let style = {}
      style.margin = `${this.height}px 10px`
      style.width = `${this.containerWidth > 300 ? this.containerWidth - 20 : 300}px`
      return style
    },
    active () {
      let index = this.bones.findIndex(item => !item.isFinish)
      if (index < 0) {
        index = this.bones.length
      }
      return index
    },
    isFinish () {
      return this.bones.length > 0 && this.active === this.bones.length
    },
    progressWidth () {
      return this.isFinish ? '100%' : (`${this.width + this.width * this.active - 10}px`)
    }
  },
  created () {
    this.activateTooltip = debounce(50, tooltip => tooltip.handleShowPopper())
  },
  methods: {
    getBoneByIndex (index) {
      if (index < 0 || index > this.bones.length - 1) {
        return null
      }
      return this.bones[index]
    },
    setTooltipContent (el) {
      if (this.$refs.tooltip) {
        const tooltip = this.$refs.tooltip
        this.tooltipContent = el.textContent || el.innerText
        tooltip.referenceElm = el
        tooltip.$refs.popper && (tooltip.$refs.popper.style.display = 'none')
        tooltip.doDestroy()
        tooltip.setExpectedState(true)
        this.activateTooltip(tooltip)
      }
    }
  },
  watch: {
    bones (bones) {
      bones.forEach((child, index) => {
        child.index = index
      })
    }
  }
}
</script>
<style lang="scss">
@import "~@/styles/global";
.fishbone-container {
  overflow: auto;
  padding: 20px 0px;
  .fishbone-content {
    position: relative;
    min-width: 300px;
    &.fishbone--horizontal {
      white-space: nowrap;
      .fishbone-backbone--inner {
        height: 2px;
        top: 0px;
        left: 0;
        right: 10px;
        .fishbone-backbone--progress {
          height: 2px;
        }
      }
    }
    .fishbone-backbone {
      position: relative;
      .fishbone-backbone--inner {
        position: absolute;
        border-color: inherit;
        background-color: #c0c4cc;
        &:before {
          content: " ";
          position: absolute;
          height: 20px;
          width: 20px;
          top: -9px;
          border-radius: 50%;
          background: $color-primary;
        }
        &:after {
          content: " ";
          position: absolute;
          right: -1px;
          top: -19px;
          border-bottom: 20px solid transparent;
          border-top: 20px solid transparent;
          border-left: 20px solid #c0c4cc;
        }
        &.is-finish {
          &:after {
            border-left-color: $color-primary;
          }
        }
        .fishbone-backbone--progress {
          background-color: $color-primary;
        }
      }
    }
  }
}
</style>
