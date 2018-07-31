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
      <template slot-scope="scope" slot="airlinesnature">
          {{ scope.row.airlinesnature === 'I' ? '国际' : '国内' }}
      </template>
      <template slot-scope="scope" slot="isvirtual">
          {{ scope.row.isvirtual === '0' ? '否' : '是' }}
      </template>>
      <!-- 操作按钮组 -->
      <template slot="btn_opt">
        <el-form-item>
          <el-button type="primary" v-auth="{code: 'get_base_airline_list'}" @click="getTableData('click')" size="small">查询</el-button>
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
      <template slot="iataCodeForm" slot-scope="scope">
        <el-input v-model="scope.form[scope.column.prop]" @input="val => toUpperCase(val, scope.column)" :maxlength='2' placeholder="请输入IATA编码" size="small"></el-input>
      </template>
      <template slot="icaoCodeForm" slot-scope="scope">
        <el-input v-model="scope.form[scope.column.prop]" @input="val => toUpperCase(val, scope.column)" :maxlength='3' placeholder="请输入ICAO编码" size="small"></el-input>
      </template>
      <template slot="countrycodeCrud" slot-scope="{ target }">
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
import { validChinese } from '@/util/rules'
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
        selection: true,
        index: true,
        page: true,
        column: [
          {
            // eslint-disable-next-line
            prop: 'iatacode', label: 'IATA代码', width: 120, query: 1, sortable: true,
            rules: [{ required: true, min: 2, max: 2, message: '请输入二位大写字母', trigger: 'blur' | 'change' }],
            meta: { convert: 'toUpperCase' }
          },
          {
            // eslint-disable-next-line
            prop: 'icaocode', label: 'ICAO代码', width: 120, query: 1,
            rules: [{ required: true, min: 3, max: 3, message: '请输入三位大写字母', trigger: 'blur' }],
            meta: { convert: 'toUpperCase' }
          },
          { prop: 'cnname', label: '中文名称', minWidth: 100, rules: [{ required: true, validator: validChinese, message: '请输入少于50位的中文名称', trigger: 'blur' }, { max: 50, message: '请输入少于50位的中文名称' }] },
          { prop: 'enname', label: '英文名称', minWidth: 100, rules: [{ trigger: 'blur' }, { max: 50, message: '请输入少于50位的英文名称' }] },
          { prop: 'cnabbr', label: '中文简称', width: 85, rules: [{ required: true, validator: validChinese, message: '请输入少于10位的中文简称', trigger: 'blur' }, { max: 10, message: '请输入少于10位的中文简称' }] },
          { prop: 'enabbr', label: '英文简称', width: 85, rules: [{ trigger: 'blur' }, { max: 50, message: '请输入少于50位的英文简称' }] },
          { prop: 'airlinesnature', label: '航空公司属性', width: 120, slot: false, dicData: RemoteData.instance('get_flightNature_list', { pageSize: 10000, pageNumber: 0 }, (res) => res.data.content.map(item => { return { label: item.description, value: item.flightNatureCode } })), type: 'select', rules: [{ required: true, message: '航空公司属性不能为空', trigger: 'change' }] },
          { prop: 'countrycode', label: '国家3字代码', width: 110, rules: [{ trigger: 'blur' }, { max: 3, message: '请输入少于100位的国家3字代码' }], type: 'select', dicData: RemoteData.instance('get_base_country_list', { pageSize: 10000, pageNumber: 0 }, (res) => res.data.content.map(item => { return { label: item.countrycode3l, value: item.countrycode3l, ot: item.cnname } })), meta: { template: (target) => [ target.ot, target.label ] } },
          { prop: 'ticketprefix', label: '客票号前缀', width: 100, rules: [{ trigger: 'blur' }, { max: 3, message: '请输入少于100位的客票号前缀' }] },
          { prop: 'billprefix', label: '货票号前缀', width: 100, rules: [{ trigger: 'blur' }, { max: 3, message: '请输入少于100位的货票号前缀' }] },
          { prop: 'isvirtual', label: '是否虚拟', width: 85, slot: false, dicData: 'YES_OR_NO', type: 'select', meta: { value: '0' } },
          { prop: 'generalagent', label: '总代理', width: 80, type: 'select', dicData: RemoteData.instance('get_generalAgent_list', { pageSize: 10000, pageNumber: 0 }, (res) => res.data.content.map(item => { return { label: item.agentCode, value: item.agentCode } })) }
        ]
      },
      tableData: [],
      filters: {},
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
      console.log('set')
      vm.$set(vm.tableOption, 'calcHeight', 10)
    }, 5000)
  },
  methods: {
    getTableData (val) {
      this.rowSelected = null
      let getDataAjax = this.$auth('get_base_airline_list')
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
      let add = this.$auth('post_base_airline_add')
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
      let update = this.$auth('put_base_airline_update')
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
        let remove = this.$auth('delete_base_airline_remove')
        remove({id: row.id}).then(res => {
          res.status && this.$ok(res.message)
          this.getTableData()
        })
      }).catch(() => {})
    }
  }
}
</script>
