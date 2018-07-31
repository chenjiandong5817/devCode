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
          <el-button type="primary" v-auth="{code: 'get_aircraftType_list', click: getTableData}" size="small">查询</el-button>
        </el-form-item>
        <el-form-item v-auth="'post_aircraftType_add'">
          <el-button type="primary" @click="handleAdd" size="small">新增</el-button>
        </el-form-item>
        <el-form-item v-auth="'put_aircraftType_update'">
          <el-button type="primary" @click="handleEdit" :disabled="!rowSelected" size="small">编辑</el-button>
        </el-form-item>
        <el-form-item v-auth="'delete_aircraftType_remove'">
          <el-button type="danger" :disabled="!rowSelected" @click="handleRemove" size="small">删除</el-button>
        </el-form-item>
      </template>
    </crud>
  </div>
</template>
<script>
import Crud from '@/components/crud'
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
      column: [
        { // eslint-disable-next-line
          prop: 'iataCode', label: 'IATA编码', width: 120, labelWidth: 110, query: 1, meta: { convert: 'toUpperCase' },
          rules: [{ max: 3, message: '超出三位限制' }, { required: true, message: '请输入IATA编码', trigger: 'blur' }]
        },
        { // eslint-disable-next-line
          prop: 'icaoCode', label: 'ICAO编码', width: 120, labelWidth: 110, query: 1, meta: { convert: 'toUpperCase' },
          rules: [{ max: 4, message: '超出四位限制' }, { required: true, message: '请输入ICAO编码', trigger: 'blur' }]
        },
        { prop: 'sysCode', label: '系统代码', width: 120, labelWidth: 110, rules: [{ max: 10, message: '超出十位限制' }] },
        { prop: 'aircraftModel', label: '机型', minWidth: 150, labelWidth: 110 },
        { prop: 'subModel', label: '亚机型', minWidth: 120, labelWidth: 110 },
        { // eslint-disable-next-line
          prop: 'wakeFlow', label: '尾流类型', width: 120, labelWidth: 110, type: 'select',
          dicData: [
            {label: 'H', value: 'H'},
            {label: 'M', value: 'M'},
            {label: 'L', value: 'L'},
            {label: 'n/a', value: 'n/a'}
          ],
          rules: [{ max: 4, message: '超出四位限制' }]
        },
        { prop: 'wingType', label: '机翼类型', width: 100, labelWidth: 110 },
        { prop: 'wingSpan', label: '翼展(m)', width: 120, labelWidth: 110 },
        { prop: 'manufacturer', label: '厂商', width: 120, labelWidth: 110 },
        { prop: 'cruisingSpeed', label: '巡航速度(km/h)', minWidth: 120, labelWidth: 110, rules: [{ max: 6, message: '超出六位限制' }] },
        { prop: 'seatCategory', label: '按座位划分等级', minWidth: 150, labelWidth: 110, rules: [{ max: 1, message: '超出一位限制' }] },
        { prop: 'anchor', label: '地锚类型', minWidth: 120, labelWidth: 110, rules: [{ max: 10, message: '超出十位限制' }] },
        { prop: 'sizeType', label: '按大小划分', minWidth: 120, labelWidth: 110, rules: [{ max: 1, message: '超出一位限制' }] },
        { prop: 'engineNumber', label: '引擎数', minWidth: 120, labelWidth: 110, rules: [{ max: 1, message: '超出一位限制' }] },
        { prop: 'engineType', label: '引擎类型', minWidth: 120, labelWidth: 110, rules: [{ max: 10, message: '超出十位限制' }] },
        { prop: 'classified', label: '用途分类', minWidth: 120, labelWidth: 110, rules: [{ max: 10, message: '超出十位限制' }] },
        { prop: 'purpose', label: '用途分类', minWidth: 120, labelWidth: 110, rules: [{ max: 10, message: '超出十位限制' }] },
        { prop: 'standCategory', label: '机型分类', minWidth: 120, labelWidth: 110, rules: [{ max: 2, message: '超出二位限制' }] },
        { prop: 'remark', label: '备注', minWidth: 120, labelWidth: 110 }
      ]
    }
  },
  methods: {
    getTableData () {
      this.rowSelected = null
      let getDataAjax = this.$auth('get_aircraftType_list')
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
      let add = this.$auth('post_aircraftType_add')
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
      let update = this.$auth('put_aircraftType_update')
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
        let remove = this.$auth('delete_aircraftType_remove')
        remove({id: row.id}).then(res => {
          res.status && this.$ok(res.message)
          this.getTableData()
        })
      }).catch(() => {})
    }
  }
}
</script>
