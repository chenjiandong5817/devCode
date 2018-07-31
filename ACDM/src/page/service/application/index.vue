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
      <!-- 操作按钮组 -->
      <template slot="btn_opt">
        <el-form-item>
          <el-button type="primary" v-auth="{code: 'get_service_page'}" @click="getTableData" size="small">查询</el-button>
        </el-form-item>
        <el-form-item v-auth="'post_service_add'">
          <el-button type="primary" @click="handleAdd" size="small">新增</el-button>
        </el-form-item>
        <el-form-item v-auth="'put_service_update'">
          <el-button type="primary" @click="handleEdit" :disabled="!rowSelected" size="small">编辑</el-button>
        </el-form-item>
        <el-form-item v-auth="'delete_service_remove'">
          <el-button type="danger" @click="handleRemove" :disabled="!rowSelected" size="small">删除</el-button>
        </el-form-item>
        <el-form-item class="divided"></el-form-item>
        <el-form-item v-auth="'put_service_clients'">
          <el-button type="warning" @click="handleSetClients" :disabled="!rowSelected" size="small">授权</el-button>
        </el-form-item>
      </template>
    </crud>
    <service-client-dialog ref="serviceClientDialog"></service-client-dialog>
  </div>
</template>
<script>
import Crud from '@/components/crud'
import { validatenull } from '@/util/validate'
import { ignoreNull, adjustPage, deepCopy } from '@/util/util'
import ServiceClientDialog from './service-client-dialog'
export default {
  components: {
    Crud, ServiceClientDialog
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
          { prop: 'code', label: '编码', width: 220, span: 24, query: 1, rules: [{ required: true, message: '请输入编码', trigger: 'blur' }] },
          { prop: 'secret', label: 'Secret', width: 220, span: 24, rules: [{ required: true, message: '请输入Secret', trigger: 'blur' }] },
          { prop: 'name', label: '名称', width: 180, span: 24, rules: [{ required: true, message: '请输入名称', trigger: 'blur' }] },
          { prop: 'locked', label: '锁定', width: 120, span: 24, dicData: 'YES_OR_NO', type: 'radio', meta: {border: true, value: '0'} },
          { prop: 'description', label: '描述', minWidth: 120, span: 24, type: 'textarea' }
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
      let getDataAjax = this.$auth('get_service_page')
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
      let add = this.$auth('post_service_add')
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
      let update = this.$auth('put_service_update')
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
        let remove = this.$auth('delete_service_remove')
        remove({id: row.id}).then(res => {
          res.status && this.$ok(res.message)
          this.getTableData()
        })
      }).catch(() => {})
    },
    handleSetClients () {
      this.$refs['serviceClientDialog'].show(this.rowSelected)
    }
  }
}
</script>
<style lang="scss">

</style>
