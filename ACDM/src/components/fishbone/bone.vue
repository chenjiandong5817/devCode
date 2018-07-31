<template>
  <div
    class="fishbone"
    :class="[`is-${$parent.direction}`]"
    :style="style"
  >
    <div class="fishbone-branch"
      :class="[`is-${status}`, {'is-bottom': isBottom}]"
      :style="{
        width: `${width}px`
      }"
    >
      <template>
        <div class="fishbone-branch--line" v-if="isBottom">
        </div>
        <div class="fishbone-branch--label" @mouseenter="handleMouseEnter" @mouseleave="handleMouseLeave">
          <div class="fishbone-branch--text" :style="{
            maxWidth: `${width}px`,
            backgroundColor: labelColor,
            color: labelTextColor
          }">
            <span>{{ label }}</span>
          </div>
        </div>
        <div class="fishbone-branch--line" v-if="!isBottom">
        </div>
      </template>
      <div class="fishbone-branch--description">
        <slot ></slot>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    label: String,
    status: {
      type: String,
      default: 'normal'
    },
    position: {
      type: String,
      validator (item) {
        return !item || ['top', 'bottom'].includes(item)
      },
      default: ''
    },
    labelColor: String,
    labelTextColor: String
  },
  data () {
    return {
      index: -1
    }
  },
  beforeCreate () {
    this.$parent.bones.push(this)
  },
  beforeDestroy () {
    const bones = this.$parent.bones
    const index = bones.indexOf(this)
    if (index >= 0) {
      bones.splice(index, 1)
    }
  },
  mounted () {
  },
  computed: {
    width () {
      return this.$parent.width || 100
    },
    height () {
      return this.$parent.height || 100
    },
    isOddIndex () {
      return this.index < 0 || this.index % 2 !== 0
    },
    isVertical () {
      return this.$parent.direction === 'vertical'
    },
    isLast () {
      const parent = this.$parent
      return parent.bones[parent.bones.length - 1] === this
    },
    isFinish () {
      return this.status === 'finish'
    },
    boneCounts () {
      return this.$parent.bones.length
    },
    isBottom () {
      return (this.position !== 'top' && this.isOddIndex) || this.position === 'bottom'
    },
    space () {
      return ''
    },
    style () {
      const style = {}
      let topFx = this.isBottom ? this.height : 0
      style.top = `${topFx}px`
      style.left = `${(this.index + 1) * this.width}px`
      style.height = `${this.height}px`
      return style
    }
  },
  methods: {
    handleMouseEnter (event) {
      const labelText = event.target.querySelector('.fishbone-branch--text')
      if (labelText.scrollWidth > labelText.offsetWidth) {
        this.$parent.setTooltipContent && this.$parent.setTooltipContent(labelText)
      }
    },
    handleMouseLeave (event) {
      const tooltip = this.$parent.$refs.tooltip
      if (tooltip) {
        tooltip.setExpectedState(false)
        tooltip.handleClosePopper()
      }
    }
  }
}
</script>
<style lang="scss">
@import "~@/styles/global";
.fishbone {
  position: absolute;
  &.is-horizontal {
    display: inline-block;
  }
  .fishbone-branch {
    position: relative;
    height: 100%;
    box-sizing: border-box;
    .fishbone-branch--label {
      height: 36px;
      line-height: 36px;
      text-align: center;
      .fishbone-branch--text {
        display: inline-block;
        box-sizing: border-box;
        border: 1px solid #c0c4cc;
        border-radius: 4px;
        padding: 0px 5px;
        height: 36px;
        line-height: 36px;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
      }
    }
    .fishbone-branch--line {
      position: relative;
      height: calc(100% - 36px);
      width: 2px;
      margin-left: 50%;
      background-color: #c0c4cc;
    }
    .fishbone-branch--description {
      position: absolute;
      bottom: 10px;
      left: 55%;
    }
    &:not(.is-bottom) {
      .fishbone-branch--line {
        &::after {
          content: " ";
          position: absolute;
          z-index: 9;
          bottom: -6px;
          height: 10px;
          width: 10px;
          border-radius: 50%;
          margin-left: -4px;
          background: #c0c4cc;
        }
      }
      .fishbone-branch--description {
        bottom: 5px;
      }
    }
    &.is-bottom {
      .fishbone-branch--line {
        &::before {
          content: " ";
          position: absolute;
          z-index: 9;
          top: -4px;
          height: 10px;
          width: 10px;
          border-radius: 50%;
          margin-left: -4px;
          background: #c0c4cc;
        }
      }
      .fishbone-branch--description {
        top: 5px;
      }
    }
    &.is-warning {
      .fishbone-branch--text {
        border-color: #f78c1b;
        background-color: #f78c1b;
        color: #fff;
      }
      .fishbone-branch--line {
        background-color: #f78c1b;
        &::before,
        &::after {
          background-color: #f78c1b;
        }
      }
      .fishbone-branch--description {
        color: #f78c1b;
      }
    }
    &.is-danger {
      .fishbone-branch--text {
        border-color: #d33f3f;
        background-color: #d33f3f;
        color: #fff;
      }
      .fishbone-branch--line {
        background-color: #d33f3f;
        &::before,
        &::after {
          background-color: #d33f3f;
        }
      }
      .fishbone-branch--description {
        color: #d33f3f;
      }
    }
    &.is-finish {
      .fishbone-branch--text {
        border-color: $color-primary;
        background-color: $color-primary;
        color: $color-primary-text;
      }
      .fishbone-branch--line {
        background-color: $color-primary;
        &::before,
        &::after {
          background-color: $color-primary;
        }
      }
      .fishbone-branch--description {
        color: $color-primary;
      }
    }
  }
}
</style>
