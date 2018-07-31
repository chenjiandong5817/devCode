<template>
  <div class="pull-auto top-logo-container">
    <div class="logo">
      <div class="collapse">
        <i class="fa fa-navicon" :class="['collapse-icon', { 'collapse__right': isCollapse }]" @click="showCollapse"></i>
      </div>
      <transition name="logo-slide-fade" mode="out-in">
        <div class="content" v-if="isCollapse">
          <span class="logo-title is-bold">厦门机场 </span>
          <span class="logo-desc">A-CDM</span>
        </div>
      </transition>
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
@import '~@/styles/global';
.top-logo-container {
  .logo {
    display: flex;
    align-items: flex-end;
    .collapse {
      display: inline-block;
      transform: rotate(0);
      padding: 0 15px;
      margin: 0 5px 0 0;
      font-size: 26px !important;
      background-color: $color-primary;
      .collapse-icon {
        transition: transform .3s ease;
        &.collapse__right {
          transform: rotate(90deg);
        }
        &:hover {
          transition: transform .2s;
          // color: $color-primary;
          transform: scale(1.3, 1.3) rotate(90deg);
          &:not(.collapse__right) {
            transform: scale(1.3, 1.3);
          }

        }
        &:active {
          color: $color-active;
        }
      }
    }
    .content {
      .logo-title {
        padding: 0 8px 0 0;
        color: #409eff;
        font-size: 24px;
        font-style: italic;
        &.is-bold {
          font-weight: 700;
        }
      }
      .logo-desc {
        font-size: 16px;
      }
    }
  }
}
.logo-slide-fade-enter-active, .logo-slide-fade-leave-active {
  transition: all .5s linear;
}
.logo-slide-fade-enter, .logo-slide-fade-leave-to{
  transform: translate(-100%);
  opacity: 0;
}
</style>
