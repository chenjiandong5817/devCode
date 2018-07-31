<template>
  <div>
    <crud
      ref="crud"
      :tableData="tableData"
      :tableOption="tableOption"
      :tableLoading="loading"
      :page="page"
      :filters.sync="filters"
      @row-click="handleRowClick"
      @current-change="handleCurrentChange"
      @size-change="handleSizeChange"
      @row-save="handleRowSave"
      @row-update="handleRowUpdate"
      @row-del="handleRowDelete"
      >
      <template slot-scope="scope" slot="birthday">
          {{ formatDate(scope.row.birthday) }}
      </template>
      <!-- 操作按钮组 -->
      <template slot="btn_opt">
        <el-form-item>
          <el-button type="primary" v-auth="{code: 'get_user_list'}" @click="getTableData('click')" size="small">查询</el-button>
        </el-form-item>
        <el-form-item v-auth="'post_user_add'">
          <el-button type="primary" @click="handleAdd" size="small">新增</el-button>
        </el-form-item>
        <el-form-item v-auth="'put_user_update'">
          <el-button type="primary" @click="handleEdit" :disabled="!rowSelected" size="small">编辑</el-button>
        </el-form-item>
        <el-form-item v-auth="'put_user_save_roles'" class="divided">
          <el-button type="warning" @click="handleSetUserRole" :disabled="!rowSelected" size="small">授权</el-button>
        </el-form-item>
        <el-form-item v-auth="'put_user_reset_pwd'">
          <el-button type="danger" @click="handleResetPwd" :disabled="!rowSelected" size="small">重置密码</el-button>
        </el-form-item>
        <el-form-item v-auth="'put_user_change_pwd'">
          <el-button type="danger" @click="handleChangetPwd" :disabled="!rowSelected" size="small">修改密码</el-button>
        </el-form-item>
        <el-form-item v-auth="'put_user_logic_delete'">
          <el-button type="danger" :disabled="!rowSelected" @click="handleRemove" size="small">删除</el-button>
        </el-form-item>
        <el-form-item class="divided">
          <span style="color: #eee;">显示已删除：</span>
          <el-switch
            v-model="isDeleted"
            @change="handleToggleUserDeleted"
            active-value="1"
            inactive-value="0">
          </el-switch>
        </el-form-item>
      </template>
    </crud>
    <user-password-dialog ref="userPasswordDialog"></user-password-dialog>
    <user-role-dialog ref="userRoleDialog"></user-role-dialog>
  </div>
</template>
<script>
import Crud from '@/components/crud'
import { validatenull } from '@/util/validate'
import { ignoreNull, adjustPage, deepCopy } from '@/util/util'
import moment from 'moment'
import UserPasswordDialog from './user-password-dialog'
import UserRoleDialog from './user-role-dialog'
export default {
  components: {
    Crud, UserPasswordDialog, UserRoleDialog
  },
  data () {
    return {
      tableOption: {
        height: 'auto',
        border: true,
        labelWidth: 120,
        stripe: true,
        // selection: true,
        index: true,
        page: true,
        column: [
          {
            // eslint-disable-next-line
            prop: 'username', label: '用户名', width: 120, query: 1, sortable: true, span: 24, editDisabled: true,
            rules: [{ required: true, min: 4, max: 32, message: '请输入4-32位字符', trigger: 'blur' }]
          },
          {
            // eslint-disable-next-line
            prop: 'password', label: '密码', hide: true, type: 'password', span: 24, editVisible: false,
            rules: [{ required: true, min: 6, max: 32, message: '请输入6-32位字符', trigger: 'blur' }]
          },
          {
            // eslint-disable-next-line
            prop: 'name', label: '姓名', width: 120, query: 1,
            rules: [{ required: true, message: '请输入姓名', trigger: 'blur' }]
          },
          { prop: 'mobilePhone', label: '手机', width: 120 },
          { prop: 'telPhone', label: '电话', width: 120 },
          { prop: 'email', label: '邮箱', width: 85 },
          { prop: 'address', label: '地址', minWidth: 85, span: 24 },
          { prop: 'gender', label: '性别', width: 50, dicData: 'SEX', type: 'select' },
          { prop: 'birthday', label: '生日', width: 110, type: 'date', slot: true },
          { prop: 'isSuperAdmin', label: '超级管理员', width: 90, dicData: 'YES_OR_NO', type: 'radio', meta: {border: true, value: '0'} },
          { prop: 'isDisabled', label: '已禁用', width: 90, dicData: 'YES_OR_NO', type: 'radio', meta: {border: true, value: '0'} },
          { prop: 'isDeleted', label: '已删除', width: 90, dicData: 'YES_OR_NO', query: 1, queryType: 'select', hide: true, editVisible: false, addVisible: false },
          { prop: 'remark', label: '备注', width: 100, span: 24 }
        ]
      },
      tableData: [],
      filters: {
      },
      isDeleted: '0',
      page: {
        pageSize: 50,
        pageNumber: 1,
        total: 0,
        pageSizes: [50, 100]
      },
      loading: false,
      rowSelected: null
    }
  },
  mounted () {
    let vm = this
    setTimeout(() => {
      vm.$set(vm.tableOption, 'calcHeight', 10)
    }, 5000)
  },
  methods: {
    getTableData (val) {
      this.rowSelected = null
      let getDataAjax = this.$auth('get_user_list')
      if (!getDataAjax) return
      this.loading = true
      getDataAjax(Object.assign(ignoreNull({isDeleted: this.isDeleted === '1' ? null : this.isDeleted}), ignoreNull(this.filters), adjustPage(this.page))).then(res => {
        this.loading = false
        if (res.status && res.data) {
          this.tableData = res.data.content
          this.page.total = res.data.totalElements
          if (this.page.pageNumber > 1 && validatenull(this.tableData)) {
            this.page.pageNumber = 1
            console.log('重新查询')
            this.getTableData()
          }
        }
      })
    },
    handleCurrentChange (val) {
      this.page.pageNumber = val
      this.getTableData()
    },
    handleSizeChange (val) {
      this.page.pageSize = val
      this.getTableData()
    },
    handleRowClick (row, event, column) {
      this.rowSelected = deepCopy(row)
    },
    handleAdd () {
      this.$refs.crud.rowAdd()
    },
    handleRowSave (row, done) {
      console.log(row)
      let formCopy = Object.assign({}, row)
      let add = this.$auth('post_user_add')
      add(formCopy).then((res) => {
        res.status && this.$ok(res.message)
        this.getTableData()
        done()
      })
    },
    handleEdit () {
      let rowSelected = deepCopy(this.rowSelected)
      console.log(this.rowSelected)
      this.$refs.crud.rowEdit(rowSelected)
    },
    handleRowUpdate (row, done) {
      let formCopy = Object.assign({}, row)
      let update = this.$auth('put_user_update')
      update(formCopy).then((res) => {
        res.status && this.$ok(res.message)
        this.getTableData()
        done()
      })
    },
    handleRemove () {
      this.$refs.crud.rowDel(this.rowSelected)
    },
    handleRowDelete (row) {
      this.$confirm(`是否删除【${row ? row.username : 'undefined'}】?`, {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        if (this.tableData.length === 1 && this.page.pageNumber !== 1) {
          this.page.pageNumber-- // 当在最后一页删除最后一条数据 并且 该页不是第一页时, 自动跳转到前一页
        }
        let remove = this.$auth('put_user_logic_delete')
        remove({id: row.id}).then(res => {
          res.status && this.$ok(res.message)
          this.getTableData()
        })
      }).catch(() => {})
    },
    handleResetPwd () {
      this.$confirm(`是否重置【${this.rowSelected ? this.rowSelected.username : 'undefined'}】密码?`, {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let resetPwdAjax = this.$auth('put_user_reset_pwd')
        resetPwdAjax({id: this.rowSelected.id}).then(res => {
          res.status && this.$ok(res.message)
        })
      }).catch(() => {})
    },
    handleChangetPwd () {
      this.$refs['userPasswordDialog'].show(this.rowSelected)
    },
    formatDate (timestamp) {
      if (!timestamp) {
        return ''
      }
      return moment(timestamp).format('YYYY-MM-DD')
    },
    handleToggleUserDeleted (newVal) {
      let isDeletedColumn = this.tableOption.column.find(column => column.prop === 'isDeleted')
      isDeletedColumn.hide = newVal === '0'
      if (isDeletedColumn.hide && this.filters['isDeleted'] !== undefined) {
        delete this.filters['isDeleted']
      }
      this.$nextTick(() => {
        this.getTableData()
      })
    },
    handleSetUserRole () {
      if (validatenull(this.rowSelected)) {
        this.$fail('请先选择一行记录')
      } else {
        this.$refs['userRoleDialog'].show(this.rowSelected)
      }
    }
  }
}
</script>
