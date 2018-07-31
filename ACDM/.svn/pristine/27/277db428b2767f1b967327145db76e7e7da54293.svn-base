<template>
  <div>
    <div class="toolbar">
      <el-form :inline="true" :model="filters">
        <el-form-item class="divided">
          <el-form-item>
            <el-input v-model="filters.code" placeholder="角色编码" size="small"></el-input>
          </el-form-item>
          <el-form-item>
            <el-input v-model="filters.name" placeholder="名称" size="small"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" v-auth="{code: 'get_role_list', click: getList}" size="small">查询</el-button>
          </el-form-item>
        </el-form-item>
        <el-form-item class="divided">
          <el-form-item v-auth="'post_role_add'">
            <el-button type="primary" @click="handleAdd" size="small">新增</el-button>
          </el-form-item>
          <el-form-item v-auth="'put_role_update'">
            <el-button type="primary" @click="handleUpdate" :disabled="!selectedRole" size="small">编辑</el-button>
          </el-form-item>
          <el-form-item v-auth="'delete_role_remove'">
            <el-button type="danger" :disabled="!selectedRole" @click="handleRemove" size="small">删除</el-button>
          </el-form-item>
        </el-form-item>
      </el-form>
    </div>
    <div class="table-content">
      <el-table v-bind:data="roles" highlight-current-row @row-click="handleSelect" style="width: 100%;" size="small">
        <el-table-column type="index" width="60">
        </el-table-column>
        <el-table-column prop="code" label="角色编码" width="150">
        </el-table-column>
        <el-table-column prop="name" label="名称" width="180">
        </el-table-column>
        <el-table-column prop="remark" label="备注" min-width="150">
        </el-table-column>
        <el-table-column
          fixed="right"
          label="操作"
          width="200">
          <template slot-scope="scope">
            <el-button
              @click="handleSetMessageTypeAuth(scope.$index, scope.row)"
              v-auth="'put_message_type_list_update'"
              type="primary"
              size="mini">
              消息订阅
            </el-button>
            <el-button
              @click="handleShowUsers(scope.$index, scope.row)"
              v-auth = "'put_message_type_list_update'"
              type="warning"
              size="mini">
              设置人员
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <pagination
      right
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pager.pageNumber"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pager.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pager.total">
    </pagination>
    <role-dialog ref="roleDialog" :callback="getList"></role-dialog>
    <role-message-type-dialog ref="roleMessageTypeDialog"  :id = "currentRow?currentRow.id:''"  @saveMessageTypeAuth = "saveMessageTypeAuth"></role-message-type-dialog>
    <role-user-dialog ref="roleUserDialog" @afterSave="getList"></role-user-dialog>
  </div>
</template>
<script>
import { ignoreNull, adjustPage } from '@/util/util'
import RoleDialog from './role-dialog'
import pagination from '@/components/pagination/'
import RoleMessageTypeDialog from './role-message-type-dialog'
import RoleUserDialog from './role-user-dialog'
export default {
  components: {
    RoleDialog, pagination, RoleMessageTypeDialog, RoleUserDialog
  },
  data () {
    return {
      // 查询条件
      filters: {
        code: '',
        name: ''
      },
      // 分页参数
      pager: {
        pageSize: 10,
        pageNumber: 1,
        total: 0
      },
      // 用户列表
      roles: [],
      selectedRole: null,
      currentRow: null
    }
  },
  mounted () {
  },
  methods: {
    getList (ajax) {
      if (!ajax) {
        ajax = this.$auth('get_role_list')
      }
      ajax(Object.assign({}, ignoreNull(this.filters), adjustPage(this.pager))).then(res => {
        // console.log(res)
        if (res.status && res.data) {
          this.roles = res.data.content
          this.pager.total = res.data.totalElements
        }
      })
      // role 被改动，重新获取roleAll
      this.$store.dispatch('GetRoleAll')
    },
    // 新增按钮响应事件
    handleAdd () {
      this.$refs['roleDialog'].show()
    },
    // 编辑按钮响应事件
    handleUpdate () {
      this.$refs['roleDialog'].show(this.selectedRole)
    },
    // 删除按钮响应事件
    handleRemove () {
      this.$confirm(`是否删除 [${this.selectedRole.name}] ?`, {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let removeRole = this.$auth('delete_role_remove')
        removeRole({id: this.selectedRole.id}).then(res => {
          res.status && this.$ok(res.message)
          this.getList()
        })
      }).catch(() => {})
    },
    // 表格选中一条记录的响应事件
    handleSelect (row) {
      this.selectedRole = row
    },
    // 分页点击页码事件
    handleCurrentChange (val) {
      this.pager.pageNumber = val
      this.getList()
    },
    // 分页修改分页大小事件
    handleSizeChange (val) {
      this.pager.pageSize = val
      this.getList()
    },
    handleSetMessageTypeAuth (index, row) {
      this.currentRow = row
      this.$refs['roleMessageTypeDialog'].show(this.currentRow.id)
    },
    saveMessageTypeAuth (data) {
      let type = this.$auth('put_message_type_list_update')
      type({roleId: this.currentRow.id, messageTypeIds: data}).then((res) => {
        if (res.status === 1) {
          this.$ok(res.message)
          this.currentRow = null
          this.$refs['roleMessageTypeDialog'].close()
          this.$store.dispatch('GetRoleAll')
        }
      })
    },
    handleShowUsers (index, row) {
      this.$refs['roleUserDialog'].show(row)
    }
  }
}
</script>
