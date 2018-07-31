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
          <el-button type="primary" v-auth="{code: 'get_base_irregularCode_list'}" @click="getTableData('click')" size="small">查询</el-button>
        </el-form-item>
        <el-form-item v-auth="'post_messageInfo_add'">
          <span></span>
        </el-form-item>
        <el-form-item v-auth="'post_messageInfo_add'">
          <el-button type="primary" @click="handleAdd" size="small">新增</el-button>
        </el-form-item>
        <el-form-item v-auth="'put_messageInfo_update'">
          <el-button type="primary" @click="handleEdit" :disabled="!rowSelected" size="small">编辑</el-button>
        </el-form-item>
        <el-form-item v-auth="'delete_messageInfo_remove'">
          <el-button type="danger" :disabled="!rowSelected" @click="handleRemove" size="small">删除</el-button>
        </el-form-item>
      </template>
      <template slot="airportCodeCrud" slot-scope="{ target }">
        <div>{{ target.label + target.ot }}</div>
      </template>
      <template slot="superiorCrud" slot-scope="{ target }">
        <div>{{ target.label + target.ot }}</div>
      </template>
    </crud>
  </div>
</template>
<script>
import Crud from '@/components/crud'
import { validatenull } from '@/util/validate'
import { ignoreNull, adjustPage, deepCopy } from '@/util/util'
import RemoteData from '@/util/remote-data'
export default {
  components: {
    Crud
  },
  data () {
    return {
      tableOption: {
        height: 'auto',
        border: true,
        labelWidth: 80,
        stripe: true,
        selection: false,
        index: true,
        page: true,
        column: [
          { prop: 'irregularCode', label: '异常编码', sortable: true, minWidth: 80, query: 1, rules: [{ required: true, message: '请输入1~6位的异常编码', trigger: 'blur' }, { max: 6, message: '请输入1~6位的异常编码' }] },
          { prop: 'airportCode', label: '机场', labelWidth: 100, query: 1, type: 'select', queryType: 'select', dicData: RemoteData.instance('get_baseAirport_list', { pageSize: 10000, pageNumber: 0 }, (res) => res.data.content.map(item => { return { label: item.iataCode, value: item.iataCode, ot: item.airportCNName } })), meta: { template: (target) => [ target.ot, target.label ] } },
          { prop: 'usetime', label: '使用次数', addVisible: false, editVisible: false },
          { prop: 'priority', label: '优先级', width: 120, type: 'number', meta: { max: 99, min: 0 } },
          { prop: 'flightEventCode', label: '关联事件代码', width: 120, labelWidth: 100, rules: [{ max: 30, message: '请输入1~30位的关联事件代码' }] },
          { prop: 'superior', label: '父级', width: 80, query: 1, type: 'select', dicData: RemoteData.instance('get_base_flightServiceCode_list', { pageSize: 10000, pageNumber: 0 }, (res) => res.data.content.map(item => { return { label: item.parentServiceId, value: item.parentServiceId, ot: item.flightServiceDesc } })), meta: { template: (target) => [ target.ot, target.label ] }, queryType: 'select' },
          { prop: 'disabled', label: '状态', width: 80, labelWidth: 100, type: 'select', dicData: 'OPEN_OR_CLOSE', meta: { value: '1' } },
          { prop: 'description', label: '描述', span: 24, width: 120, type: 'textarea', rules: [{ required: true, message: '请输入少于100位字符串的描述', trigger: 'blur' }, { max: 100, message: '请输入少于100位字符串的描述' }] },
          { prop: 'remark', label: '备注', span: 24, width: 120, type: 'textarea', rules: [{ trigger: 'blur', message: '请输入少于100位字符串的备注' }, { max: 100, message: '请输入少于100位字符串的备注' }] }
        ]
      },
      tableData: [],
      filters: {},
      page: {
        pageSize: 20,
        pageNumber: 1,
        total: 0,
        pageSizes: [10, 20, 50, 100, 200]
      },
      loading: false,
      rowSelected: null
    }
  },
  mounted () {
    let vm = this
    setTimeout(() => {
      console.log('set')
      vm.$set(vm.tableOption, 'calcHeight', 10)
    }, 5000)
  },
  methods: {
    getTableData (val) {
      this.rowSelected = null
      let getDataAjax = this.$auth('get_base_irregularCode_list')
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
      console.log(row)
      let formCopy = Object.assign({}, row)
      let add = this.$auth('post_base_irregularCode_add')
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
      let update = this.$auth('put_base_irregularCode_update')
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
        let remove = this.$auth('delete_base_irregularCode_remove')
        remove({id: row.id}).then(res => {
          res.status && this.$ok(res.message)
          this.getTableData()
        })
      }).catch(() => {})
    }
  }
}
</script>
