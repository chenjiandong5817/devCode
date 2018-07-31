<template>
  <div class="oauth-client-container">
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
      <!-- 操作按钮组 -->
      <template slot="btn_opt">
        <el-form-item>
          <el-button type="primary" v-auth="{code: 'get_oauth_client_page'}" @click="getTableData" size="small">查询</el-button>
        </el-form-item>
        <el-form-item v-auth="'post_oauth_client_add'">
          <el-button type="primary" @click="handleAdd" size="small">新增</el-button>
        </el-form-item>
        <el-form-item v-auth="'put_oauth_client_update'">
          <el-button type="primary" @click="handleEdit" :disabled="!rowSelected" size="small">编辑</el-button>
        </el-form-item>
        <el-form-item v-auth="'delete_oauth_client_remove'">
          <el-button type="danger" @click="handleRemove" :disabled="!rowSelected" size="small">删除</el-button>
        </el-form-item>
      </template>
      <template slot="accessTokenValidityForm" slot-scope="{ form, column }">
        <el-form-item>
          <el-input-number size="small" v-model="form[column.prop]" class="number-input" style="width: 230px;"></el-input-number>
        </el-form-item>
      </template>
      <template slot="refreshTokenValidityForm" slot-scope="{ form, column }">
        <el-form-item>
          <el-input-number size="small" v-model="form[column.prop]" class="number-input" style="width: 230px;"></el-input-number>
        </el-form-item>
      </template>
    </crud>
  </div>
</template>
<script>
import Crud from '@/components/crud'
import { validatenull } from '@/util/validate'
import { ignoreNull, adjustPage, deepCopy } from '@/util/util'
export default {
  components: {
    Crud
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
        formWidth: 560,
        column: [
          { prop: 'clientId', label: 'ID', width: 120, span: 24, query: 1, rules: [{ required: true, message: '请输入ID', trigger: 'blur' }] },
          { prop: 'clientSecret', label: 'Secret', width: 120, span: 24, rules: [{ required: true, message: '请输入Secret', trigger: 'blur' }] },
          { prop: 'scope', label: 'Scope', width: 150, span: 24, rules: [{ required: true, message: '请输入Scope', trigger: 'blur' }] },
          { prop: 'authorizedGrantTypes', label: '授权方式', minWidth: 180, span: 24, rules: [{ required: true, message: '请输入授权方式', trigger: 'blur' }] },
          { prop: 'webServerRedirectUri', label: '重定向地址', minWidth: 180, span: 24, rules: [{ required: true, message: '请输入授权方式', trigger: 'blur' }] },
          { prop: 'accessTokenValidity', label: '访问时长', width: 120, span: 24, formSlot: true, type: 'number', meta: {value: 3600}, rules: [{ required: true, message: '请输入访问时长', trigger: 'blur' }] },
          { prop: 'refreshTokenValidity', label: '刷新时长', width: 120, span: 24, formSlot: true, type: 'number', meta: {value: 2592000}, rules: [{ required: true, message: '请输入刷新时长', trigger: 'blur' }] },
          { prop: 'autoapprove', label: '登录自动授权', width: 120, span: 24, dicData: 'STR_TRUE_OR_FALSE', type: 'radio', meta: {border: true, value: 'true'} },
          { prop: 'description', label: '说明', minWidth: 120, span: 24, type: 'textarea' }
        ]
      },
      tableData: [],
      filters: {
      },
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
      let getDataAjax = this.$auth('get_oauth_client_page')
      if (!getDataAjax) return
      this.loading = true
      getDataAjax(Object.assign({}, ignoreNull(this.filters), adjustPage(this.page))).then(res => {
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
      // console.log(row)
      let formCopy = Object.assign({}, row)
      let add = this.$auth('post_oauth_client_add')
      add(formCopy).then((res) => {
        res.status && this.$ok(res.message)
        this.getTableData()
        done()
      })
    },
    handleEdit () {
      let rowSelected = deepCopy(this.rowSelected)
      // console.log(this.rowSelected)
      this.$refs.crud.rowEdit(rowSelected)
    },
    handleRowUpdate (row, done) {
      let formCopy = Object.assign({}, row)
      let update = this.$auth('put_oauth_client_update')
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
      this.$confirm(`是否删除?`, {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        if (this.tableData.length === 1 && this.page.pageNumber !== 1) {
          this.page.pageNumber-- // 当在最后一页删除最后一条数据 并且 该页不是第一页时, 自动跳转到前一页
        }
        let remove = this.$auth('delete_oauth_client_remove')
        remove({id: row.clientId}).then(res => {
          res.status && this.$ok(res.message)
          this.getTableData()
        })
      }).catch(() => {})
    }
  }
}
</script>
<style lang="scss">

</style>
