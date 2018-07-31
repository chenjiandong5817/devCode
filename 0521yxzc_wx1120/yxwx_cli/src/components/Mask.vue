<!-- 公共遮罩层 -->
<template>
    <transition enter-active-class="animated fadeIn" leave-active-class="animated fadeOut">
        <div class="comMask" v-if="show" @click="event"></div>
    </transition>
</template>
<script>
export default{
  // 变量show控制遮罩层显示/关闭
  props: ['show', 'event']
}
</script>
<style lang="less">
.comMask{
  position: fixed;
  z-index: 10;
  left: 0;
  top: 0;
  bottom: 0;
  right: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(37, 38, 45, .4);
  animation-duration: .3s;
  transition: all .2s;
  -webkit-transition: all .2s;
  backdrop-filter: blur(10px);
  -webkit-backdrop-filter: blur(10px);
}
</style>