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
          <el-button type="primary" v-auth="{code: 'get_checkCounterServiceType_list', click: getTableData}" size="small">查询</el-button>
        </el-form-item>
        <el-form-item v-auth="'post_checkCounterServiceType_add'">
          <el-button type="primary" @click="handleAdd" size="small">新增</el-button>
        </el-form-item>
        <el-form-item v-auth="'put_checkCounterServiceType_update'">
          <el-button type="primary" @click="handleEdit" :disabled="!rowSelected" size="small">编辑</el-button>
        </el-form-item>
        <el-form-item v-auth="'delete_checkCounterServiceType_remove'">
          <el-button type="danger" :disabled="!rowSelected" @click="handleRemove" size="small">删除</el-button>
        </el-form-item>
      </template>
    </crud>
  </div>
</template>
<script>
import Crud from '@/components/crud'
import RemoteData from '@/util/remote-data'
import { ignoreNull, adjustPage, deepCopy } from '@/util/util'
export default {
  components: {
    Crud
  },
  data () {
    return {
      tableData: [],
      filters: {},
      page: {
        pageSize: 20,
        pageNumber: 1,
        total: 0,
        pageSizes: [10, 20, 50, 100, 200]
      },
      tableOption: {},
      loading: false,
      rowSelected: null
    }
  },
  mounted () {
    // this.getTableData()
    this.tableOption = {
      height: 'auto',
      border: true,
      stripe: true,
      selection: false,
      index: true,
      page: true,
      column: [ /* eslint-disable */
        {
          prop: 'serviceTypeCode', label: '代码', width: 150, labelWidth: 100, query: 1,
          rules: [{ required: true, message: '请输入代码', trigger: 'blur' }, { max: 4, message: '超出四位限制' }]
        },
        {
          prop: 'description', label: '描述', minWidth: 120, labelWidth: 100,
          rules: [{ required: true, message: '请输入描述', trigger: 'blur' }]
        },
        {
          prop: 'ckCounterTemplate', label: '值机柜台模版', minWidth: 120, labelWidth: 100, query: 1, type: 'select',
          dicData: RemoteData.instance('get_base_ckCounterOpMode_list', { pageSize: 10000, pageNumber: 0 },
          (res) => res.data.content.map(item => { return { label: item.description, value: item.opmodeCode } }))
        },
        { prop: 'remark', label: '备注', minWidth: 120, labelWidth: 100, span: 24, type: 'textarea' }
      ]
    }
  },
  methods: {
    getTableData () {
      this.rowSelected = null
      let getDataAjax = this.$auth('get_checkCounterServiceType_list')
      if (!getDataAjax) return
      this.loading = true
      getDataAjax(Object.assign({}, ignoreNull(this.filters), adjustPage(this.page))).then(res => {
        this.loading = false
        if (res.status && res.data) {
          this.tableData = res.data.content
          this.page.total = res.data.totalElements
          if (this.page.pageNumber > 1 && this.tableData.length === 0) {
            this.page.pageNumber = 1
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
      let add = this.$auth('post_checkCounterServiceType_add')
      add(row).then((res) => {
        res.status && this.$ok(res.message)
        this.getTableData()
        done()
      })
    },
    handleEdit () {
      this.$refs.crud.rowEdit(this.rowSelected)
    },
    handleRowUpdate (row, done) {
      let update = this.$auth('put_checkCounterServiceType_update')
      update(row).then((res) => {
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
        let remove = this.$auth('delete_checkCounterServiceType_remove')
        remove({id: row.id}).then(res => {
          res.status && this.$ok(res.message)
          this.getTableData()
        })
      }).catch(() => {})
    }
  }
}
</script>
