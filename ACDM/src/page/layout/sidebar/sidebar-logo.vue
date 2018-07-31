<template>
  <div class="sidebar-logo-container pull-auto">
    <!-- <div class="complete-border"></div> -->
    <div class="logo">
      <div class="logo-title is-bold">厦门机场 </div>
      <div class="logo-desc">
        <div>
          A-CDM
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  name: 'top-logo',
  data () {
    return {}
  },
  created () {},
  computed: mapGetters(['isCollapse']),
  methods: {
    showCollapse () {
      this.$store.commit('SET_COLLAPSE')
    }
  }
}
</script>

<style scoped="scoped" lang="scss">
@import "~@/styles/global";
.sidebar-logo-container {
  position: relative;
  white-space: nowrap;
  overflow: inherit;
  background: $sidebar-logo-background-color;
  // .complete-border {
  //   height: 2px;
  //   width: 210px;
  //   position: absolute;
  //   z-index: 1001;
  //   top: calc(#{$main-header-height} - 2px);
  //   left: 0px;
  //   background: $color-primary;
  //   border: 0;
  // }
  .logo {
    display: flex;
    align-items: flex-end;
    z-index: 1000;
  }
  .logo-title {
    padding: 0 8px;
    color: #409eff;
    font-size: 20px;
    font-style: italic;
    &.is-bold {
      font-weight: 700;
    }
  }
  .logo-desc {
    position: relative;
    font-size: 16px;
    color: $header-text-color;
    // > div {
    //   height: 56px;
    // }
  }
}
</style>
