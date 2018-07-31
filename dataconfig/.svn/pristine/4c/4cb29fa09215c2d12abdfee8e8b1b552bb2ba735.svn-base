<template>
  <div :style="{
       position: 'absolute',
       left: 0,
       top: 0,
       width: '100%',
       height: '100%',
       zIndex: this.zIndex,
       transform: `rotateY(${ active ? 0 : -180 }deg)`,
       transition: 'transform 2s'
  }"  class="flop-item">
    <slot></slot>
  </div>
</template>
<script>
export default {
  name: 'FlopItem',
  data () {
    return {
      active: false,
      child: [],
      zIndex: 0
    }
  },
  watch: {
    active (val, oldVal) {
      let _this = this
      setTimeout(() => {
        _this.zIndex = val ? 2 : 1
      }, 0)
    }
  },
  methods: {
    rotateItem () {
      this.active = true
    },
    resetItemSurface () {
      this.active = false
    }
  },
  mounted () {
  }
}
</script>
<style>
.flop-item {
  backface-visibility:hidden;
  -webkit-backface-visibility:hidden;	/* Chrome 和 Safari */
  -moz-backface-visibility:hidden; 	/* Firefox */
  -ms-backface-visibility:hidden; 	/* Internet Explorer */
}
</style>
