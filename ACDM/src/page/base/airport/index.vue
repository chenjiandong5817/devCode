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
          <el-button type="primary" v-auth="{code: 'get_baseAirport_list', click: getTableData}" size="small">查询</el-button>
        </el-form-item>
        <el-form-item v-auth="'post_baseAirport_add'">
          <el-button type="primary" @click="handleAdd" size="small">新增</el-button>
        </el-form-item>
        <el-form-item v-auth="'put_baseAirport_update'">
          <el-button type="primary" @click="handleEdit" :disabled="!rowSelected" size="small">编辑</el-button>
        </el-form-item>
        <el-form-item v-auth="'delete_baseAirport_remove'">
          <el-button type="danger" :disabled="!rowSelected" @click="handleRemove" size="small">删除</el-button>
        </el-form-item>
      </template>
      <template slot="iataCodeForm" slot-scope="scope">
        <el-input v-model="scope.form[scope.column.prop]" @input="val => toUpperCase(val, scope.column)" :maxlength='3' placeholder="请输入IATA编码" size="small"></el-input>
      </template>
      <template slot="icaoCodeForm" slot-scope="scope">
        <el-input v-model="scope.form[scope.column.prop]" @input="val => toUpperCase(val, scope.column)" :maxlength='4' placeholder="请输入ICAO编码" size="small"></el-input>
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
          prop: 'iataCode', label: 'IATA编码', width: 120, labelWidth: 90, query: 1, // 唯一
          rules: [{ required: true, message: '请输入大写IATA编码', trigger: 'blur' }, { max: 3, message: '超出三位限制' }],
          meta: { convert: 'toUpperCase' }
        },
        {
          prop: 'icaoCode', label: 'ICAO编码', width: 120, labelWidth: 90, query: 1,
          rules: [{ required: true, message: '请输入大写ICAO编码', trigger: 'blur' }, { max: 4, message: '超出四位限制' }],
          meta: { convert: 'toUpperCase' }
        },
        { prop: 'airportCNName', label: '中文名称', minWidth: 120, labelWidth: 90 },
        { prop: 'airportENName', label: '英文名称', minWidth: 120, labelWidth: 90 },
        {
          prop: 'airportNature', label: '地区', width: 100, labelWidth: 90, query: 2, type: 'select',
          dicData: RemoteData.instance('get_flightNature_list', { pageSize: 10000, pageNumber: 0 },
          (res) => res.data.content.map(item => { return { label: item.description, value: item.flightNatureCode } }))
        },
        { prop: 'airportTimeZone', label: '时区', width: 100, labelWidth: 90 },
        { prop: 'cnAbbr1w', label: '一字简称', width: 120, labelWidth: 90 },
        { prop: 'cnAbbr2w', label: '二字简称', width: 120, labelWidth: 90 },
        { prop: 'displayCNName', label: '显示中文', minWidth: 120, labelWidth: 90 },
        { prop: 'displayENName', label: '显示英文', minWidth: 120, labelWidth: 90 },
        {
          prop: 'countryCode', label: '国家代码', minWidth: 120, labelWidth: 90, type: 'select',
          dicData: RemoteData.instance('get_base_country_list', { pageSize: 10000, pageNumber: 0 },
          (res) => res.data.content.map(item => { return { label: item.countrycode3l, value: item.countrycode3l, ot: item.cnname } })),
          meta: { template: (target) => [ target.ot, target.label ] }
        },
        { prop: 'firstPinYin', label: '拼音缩写', minWidth: 120, labelWidth: 90 }
      ]
    }
  },
  methods: {
    getTableData () {
      this.rowSelected = null
      let getDataAjax = this.$auth('get_baseAirport_list')
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
      let add = this.$auth('post_baseAirport_add')
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
      let update = this.$auth('put_baseAirport_update')
      update(row).then((res) => {
        res.status && this.$ok(res.message)
        this.getTableData()
        done()
      })
    },
    handleRemove () {
      this.$refs.crud.rowDel(this.rowSelected)
    },
    toUpperCase (val, column) {
      this.$refs.crud.tableForm[column.prop] = val.toUpperCase()
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
        let remove = this.$auth('delete_baseAirport_remove')
        remove({id: row.id}).then(res => {
          res.status && this.$ok(res.message)
          this.getTableData()
        })
      }).catch(() => {})
    }
  }
}
</script>
