<template>
  <div style="width:100%">
    <top></top>
    <middle></middle>
    <bottom></bottom>
  </div>
</template>
<script>
import Top from './components/top'
import Middle from './components/middle'
import Bottom from './components/bottom'
import echartTitle from 'echarts/lib/component/title'
export default {
  components: {
    Top, Bottom, Middle, echartTitle
  },
  mounted () {
    console.log('home')
  }
}
</script>
<style lang="scss">
@import "~@/styles/global";
.home-charts {
  height: 100%;
}
.el-row {
    margin-bottom: 1.5%;
    &:last-child {
      margin-bottom: 0;
    }
  }
  .bg-purple-dark {
    background: #99a9bf;
  }
  .bg-purple {
    background: $content-background-color;
  }
  .bg-purple-light {
    background: $content-background-color;
  }
  .grid-content {
    min-height: 36px;
  }
  .row-bg {
    padding: 10px 0;
    background-color: $content-background-color;
  }
  .box {
    box-shadow: 0 0 8px 0 rgba(12, 12, 14, 0.6), 0 2px 4px 0 rgba(22, 25, 26, 0.5);
    border: 1px solid $content-border-color;
  }
  .div1{
    height:8.80% ;
  }
  .div2{
    height: 33.33%;
  }
  .div3{
    height: 39.91%;
  }
</style>
