<template>
  <el-dialog :title="`授权 > ${user ? user.name || user.username : '请先选择用户...'}`" :visible.sync="visible" :close-on-click-modal="false"
    @close="handleClose" width="40%" custom-class="user-role-container">
    <el-table :data="roleAll" style="width: 100%" v-loading="!user || tableLoading"
      @selection-change="handleSelectionChange" ref="roleListTable" v-if="!roleLoading">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column label="编码" prop="code" width="120"></el-table-column>
      <el-table-column label="名称" prop="name"></el-table-column>
    </el-table>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false" size="small">取 消</el-button>
      <el-button type="primary" @click="handleSaveUserRole" :loading="loading" size="small">确 定</el-button>
    </span>
  </el-dialog>
</template>
<script>
import { mapGetters } from 'vuex'
import { deepCopy } from '@/util/util'
export default {
  data () {
    return {
      visible: false,
      loading: false,
      tableLoading: false,
      selectKeys: [],
      user: null
    }
  },
  computed: {
    ...mapGetters(['roleAll', 'roleLoading'])
  },
  methods: {
    show (user) {
      this.user = deepCopy(user)
      let getRolesAjax = this.$auth('get_user_roles')
      if (!getRolesAjax) {
        this.$fail('您没有查询角色列表的权限')
      } else {
        this.loading = true
        getRolesAjax({userId: user.id}).then(res => {
          this.loading = false
          this.visible = true
          this.$nextTick(() => {
            this.user && this.initRoleChecked(res.data)
          })
        }).catch(() => {
          this.loading = false
          this.visible = true
        })
      }
    },
    // 退出dialog
    handleClose () {
      this.$refs['roleListTable'] && this.$refs['roleListTable'].clearSelection()
    },
    // 初始化表格选中状态
    initRoleChecked (roles) {
      let keys = roles.map(role => role.id)
      this.roleAll.forEach(role => {
        if (keys.includes(role.id)) {
          this.$refs['roleListTable'].toggleRowSelection(role, true)
        }
      })
    },
    // 表格选中事件
    handleSelectionChange (selection) {
      this.selectKeys = selection.map(item => item.id)
    },
    // 保存角色控件
    handleSaveUserRole () {
      let params = {
        userId: this.user.id,
        roleIds: this.selectKeys
      }
      let ajax = this.$auth('put_user_save_roles')
      this.loading = true
      ajax(params).then((res) => {
        this.loading = false
        if (res.status) {
          this.$ok(res.message)
          // this.$emit('afterSave')
          this.$store.dispatch('GetRoleAll')
          this.$nextTick(() => {
            this.visible = false
          })
        }
      })
    }
  }
}
</script>
<style lang="scss">
@import '~@/styles/global';
.user-role-container {
  .el-dialog__body {
    padding-top: 0;
  }

}
</style>
