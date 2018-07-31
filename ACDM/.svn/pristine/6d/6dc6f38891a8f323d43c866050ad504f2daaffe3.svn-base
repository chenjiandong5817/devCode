<template>
  <div>
    <div class="toolbar">
    <el-form :inline="true" size="small" :model="filters">
        <el-form-item>
        <el-date-picker
        v-model="filters.value"
        placeholder="选择日期"
        size="small"
        type="datetime"
        format="yyyy-MM-dd HH:mm:ss">
        </el-date-picker>
        </el-form-item>
        <el-form-item>
        <el-button type="primary" v-auth="{code: 'get_generalAgent_list', click: getTableData}" size="small">查询</el-button>
    </el-form-item>
    </el-form>
    </div>
    <crud
      ref="crud"
      :tableData="tableData"
      :tableOption="tableOption"
      :tableLoading="loading"
      :page="page"
      @current-change="handleCurrentChange"
      @size-change="handleSizeChange"
      >
    </crud>
  </div>
</template>
<script>
import Crud from '@/components/crud'
import { ignoreNull, adjustPage } from '@/util/util'
export default {
  components: {
    Crud
  },
  data () {
    return {
      tableData: [],
      filters: {
        value: ''
      },
      page: {
        pageSize: 20,
        pageNumber: 1,
        total: 0,
        pageSizes: [10, 20, 50, 100, 200]
      },
      tableOption: {},
      loading: false
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
        { prop: 'airportCode', label: '时间'},
         {
          prop: 'clientAirline', label: '代理航空公司', minWidth: 150, labelWidth: 110, type: 'date',
          meta: { multiple: true, template: (target) => [ target.ot, target.label ] },
          rules: [{ required: true, message: '请选择代理航空公司', trigger: 'change' }],
        },
        { prop: 'agentCode', label: '用户' },
        { prop: 'clientAirline', label: '操作' },
        { prop: 'description', label: '字段' },
        { prop: 'description', label: '值' }
      ]
    }
  },
  methods: {
    getTableData () {
      this.rowSelected = null
      let getDataAjax = this.$auth('get_generalAgent_list')
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
    }
  }
}
</script>
