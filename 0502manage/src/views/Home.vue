<template>
  <section>
    <el-progress type="circle" :percentage="0"></el-progress>
    <el-progress type="circle" :percentage="25"></el-progress>
    <el-progress type="circle" :percentage="100" status="success"></el-progress>
    <el-progress type="circle" :percentage="50" status="exception"></el-progress>
  </section>
</template>
<script>
  export default {
    data () {
      return {
        value1: true,
        value2: true
      }
    },
    mounted () {
      this.$el.style.paddingLeft = '100px'
    }
  }
</script>
