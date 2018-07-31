<template>
  <div class="menu-control-container">
    <div class="toolbar">
      <el-form :inline="true">
        <el-form-item>
          <el-button type="primary" v-auth="'post_menu_control_add'" :disabled="hasNotSelectedMenu" @click="handleAddMenuControl" size="small">新增</el-button>
          <el-button type="danger" v-auth="'delete_menu_control_remove'" :disabled="!selectedRow" @click="handleRemoveMenuControl" size="small">删除</el-button>
        </el-form-item>
        <el-form-item class="divided">
          <el-button type="warning" :disabled="!selectedRow" @click="handleSetMessage" size="small">绑定消息</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="table-content">
      <el-table :data="menuControls" style="width: 100%" v-loading="loading" @row-click="handleSelectRow"
        highlight-current-row class="menu-control-table" ref="table">
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="left" inline class="demo-table-expand">
              <el-form-item label="类型">
                <span>{{ props.row.type }}</span>
              </el-form-item>
              <el-form-item label="请求方式">
                <span>{{ props.row.method }}</span>
              </el-form-item>
              <el-form-item label="备注" class="full">
                <span>{{ props.row.remark }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column label="编码" prop="code" min-width="150px" show-overflow-tooltip></el-table-column>
        <el-table-column label="名称" prop="name" width="180px" show-overflow-tooltip></el-table-column>
        <el-table-column label="路径" prop="uri" min-width="150px" show-overflow-tooltip> </el-table-column>
      </el-table>
    </div>
    <menu-control-dialog :callback="getMenuControls" ref="menuControlDialog"></menu-control-dialog>
    <menu-control-message-dialog ref="messageDialog"></menu-control-message-dialog>
  </div>
</template>
<script>
import MenuControlDialog from './control-dialog'
import MenuControlMessageDialog from './control-message-type-dialog'
export default {
  props: {
    menu: Object
  },
  components: {
    MenuControlDialog, MenuControlMessageDialog
  },
  data () {
    return {
      loading: false,
      selectedRow: null,
      menuControls: [
        // { code: 'test', name: '测试', type: 'BUTTON', method: 'GET', uri: '/test/test2', remark: '这是一条测试的数据，很长很长很长很长很长的数据' },
        // { code: 'test', name: '测试', type: 'BUTTON', method: 'GET', uri: '/test/test2', remark: '这是一条测试的数据，很长很长很长很长很长的数据' },
        // { code: 'test', name: '测试', type: 'BUTTON', method: 'GET', uri: '/test/test2', remark: '这是一条测试的数据，很长很长很长很长很长的数据' }
      ]
    }
  },
  computed: {
    hasNotSelectedMenu () {
      return !this.menu || !this.menu.id
    }
  },
  watch: {
    menu: {
      immediate: true,
      handler (val) {
        if (this.hasNotSelectedMenu) {
          this.menuControls = []
        } else {
          this.getControlsFromMenu()
        }
      }
    }
  },
  methods: {
    getControlsFromMenu () {
      let getMenuControlList = this.$auth('get_menu_control_list')
      if (getMenuControlList) {
        this.menuControls = this.menu.meta.controls
      }
    },
    // 获取菜单控件列表，必须选择某个菜单
    getMenuControls () {
      if (this.hasNotSelectedMenu) {
        return
      }
      this.loading = true
      let getMenuControlList = this.$auth('get_menu_control_list')
      getMenuControlList({menuId: this.menu.id}).then(res => {
        this.menuControls = res.data
        // 改变上级元素的值
        this.menu.meta.controls = res.data
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },
    handleAddMenuControl () {
      this.$refs['menuControlDialog'].show()
    },
    handleRemoveMenuControl () {
      this.$confirm(`是否删除 [${this.selectedRow.code}]控件 ?`, {
        type: 'warning'
      }).then(() => {
        let removeMenuControl = this.$auth('delete_menu_control_remove')
        removeMenuControl({id: this.selectedRow.id}).then(res => {
          res.status && this.$ok(res.message)
          this.getMenuControls()
        })
      }).catch(() => {})
    },
    handleSelectRow (row) {
      this.selectedRow = row
    },
    handleSetMessage () {
      this.$refs['messageDialog'].show(this.selectedRow)
    }
  }
}
</script>
<style lang="scss">
.menu-control-container {
  .table-content {
    overflow: auto;
    max-height: 600px;
    th, td {
      padding: 5px 0px;
    }
    .demo-table-expand {
      font-size: 0;
      padding: 0 20px;
      label {
        width: 90px;
        color: #99a9bf;
      }
      .el-form-item {
        margin-right: 0;
        margin-bottom: 0;
        width: 50%;
      }
      .full {
        width: 100%;
      }
    }
    .menu-control-table {
      .el-table__expanded-cell{
        padding: 5px 0px;
      }
    }
  }
}

</style>
