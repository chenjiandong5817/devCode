<template>
  <!--<input name="button" type="button" onClick="Wopen()" value="点击我，打开新窗口!"/>-->
    <router-view name="test" ref="mainView"></router-view>
</template>

<script>
export default{
  name: 'Previewtable',
  props: {

  },
  data () {
    return {
      tableData: [],
      tableKey: []
    }
  },
  methods: {

  },
  mounted () {
    console.log(777)
    console.log(this.$refs['excelTable'])
  }
}
</script>
