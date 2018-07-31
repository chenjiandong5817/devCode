<template>
    <div id="app">
            <!-- 子路由 -->
            <router-view></router-view>
        <div class="footer">
            <span><a href="tel:059296363">联系我们：0592-96363转0</a></span>
        </div>
    </div>
</template>
<script>
export default{
  name: 'userCenter',
  mounted () {
    // this.$store.dispatch('init_data')
    // this.$router.push({path: '/myWallet'})
  }
}
</script>
<style lang='less'>
@import '../../assets/css/animate.css';
#app {
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 100%;
  .page {
    flex: 1 0 auto;
    width: 100%;
    padding: 10px;
    box-sizing: border-box;
    &:after {
      content: '';
      position: relative;
      height: 0;
      width: 0;
      clear: both;
    }
  }
}

.footer {
  flex: 0 0 auto;
  line-height: 40px;
  font-size: 12px;
  color: #7e8c8d;
  text-align: center;
  span {
    padding: 0 20px;
  }
}

</style>
