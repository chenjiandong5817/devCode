<template>
  <el-dialog :visible.sync="visible" title="菜单控件" :close-on-click-modal="true" width="60%" custom-class="menu-control-dialog">
    <menu-control :menu="menu" v-if="tableVisible"></menu-control>
  </el-dialog>
</template>
<script>
import MenuControl from './index'
export default {
  components: {
    MenuControl
  },
  props: {
    menu: Object
  },
  data () {
    return {
      visible: false,
      tableVisible: false
    }
  },
  watch: {
    menu (val) {
      this.tableVisible = false
      setTimeout(() => {
        this.tableVisible = true
      }, 100)
    }
  },
  methods: {
    show () {
      this.visible = true
      this.tableVisible = true
    }
  }
}
</script>
<style lang="scss">
.menu-control-dialog {
  .el-dialog__body {
    padding-top: 0px !important;
  }
}
</style>
