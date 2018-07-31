<template>
    <div id="app">
        <div class="page">
            <!-- 子路由 -->
            <router-view></router-view>
        </div>
        <div class="footer">
            <span><a href="tel:059296363">联系客服</a></span>|
            <span><a href="https://www.yxzc01.com/api/base/appDownload?from=groupmessage&isappinstalled=0">下载APP</a></span>|
            <span><router-link to="/about">关于我们</router-link></span>
        </div>
    </div>
</template>
<script>
export default{
  name: 'page',
  mounted () {},
  computed: {
  },
  methods: {
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
.page {
  .content {
    position: relative;
    width: 100%;
    box-sizing: border-box;
  }
}

</style>
