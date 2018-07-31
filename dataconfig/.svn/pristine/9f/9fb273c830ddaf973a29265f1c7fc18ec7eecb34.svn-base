<template>
  <div class="double-layer-div" :style="{ textAlign: align, transform: `translate(${ translateX }px, ${ translateY }px)` }">
    <el-row class="double-layer-item" :style="topStyle">
      <slot name="topSlot"></slot>
    </el-row>
    <el-row class="double-layer-item" :style="bottomStyle">
      <slot name="bottomSlot"></slot>
    </el-row>
  </div>
</template>

<script>
export default {
  props: {
    align: {
      type: String,
      default: 'left'
    },
    translateX: {
      type: Number,
      default: 0
    },
    translateY: {
      type: Number,
      default: 0
    },
    topStyle: {
      type: Object,
      default: () => {
        return {
          fontSize: `50px`,
          color: `#fff`,
          borderBottom: `1px solid`
        }
      }
    },
    bottomStyle: {
      type: Object,
      default: () => {
        return {
          fontSize: `25px`,
          color: `#fff`
        }
      }
    }
  }
}
</script>

<style>
.top-text {
  font-size: 80px;
  color: #fff;
}
.bottom-text {
  font-size: 30px;
  color: #fff;
}
.double-layer-div {
  /*padding: 10px 20px 10px 10px;*/
  position: relative;
  padding-top: 20px;
  padding-bottom: 20px;
}

.double-layer-item {
}
</style>
