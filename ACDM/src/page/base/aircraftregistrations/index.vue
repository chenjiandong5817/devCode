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
          <el-button type="primary" v-auth="{code: 'get_base_aircraftRegistration'}" @click="getTableData('click')" size="small">查询</el-button>
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
      <template slot="registrationForm" slot-scope="scope">
        <el-input v-model="scope.form[scope.column.prop]" @input="val => toUpperCase(val, scope.column)" :maxlength='10' placeholder="请输入机号" size="small"></el-input>
      </template>
      <template slot="aircraftTypeForm" slot-scope="scope">
        <el-input v-model="scope.form[scope.column.prop]" @input="val => toUpperCase(val, scope.column)" :maxlength='10' placeholder="请输入机型IATA代码" size="small"></el-input>
      </template>
      <template slot="airlinesBranchForm" slot-scope="scope">
        <el-input v-model="scope.form[scope.column.prop]" @input="val => toUpperCase(val, scope.column)" :maxlength='5' placeholder="请输入分公司代码" size="small"></el-input>
      </template>
      <template slot="airlinesCrud" slot-scope="{ target }">
        <div>{{ target.label + target.ot }}</div>
      </template>
      <template slot="airlinesBranchCrud" slot-scope="{ target }">
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
        labelWidth: 120,
        stripe: true,
        selection: false,
        index: true,
        page: true,
        column: [
          {
            // eslint-disable-next-line
            prop: 'registration', label: '机号', minWidth: 120, query: 1, sortable: true,
            rules: [{ required: true, min: 1, max: 10, message: '请输入不超过10位的机号', trigger: 'blur' }],
            meta: { convert: 'toUpperCase' }
          },
          {
            // eslint-disable-next-line
            prop: 'aircraftType', label: '机型IATA代码', minWidth: 140, query: 1, sortable: true,
            rules: [{ required: true, min: 1, max: 10, message: '请输入不超过10位的机型IATA代码', trigger: 'blur' | 'change' }],
            meta: { convert: 'toUpperCase' }
          },
          { prop: 'airlines', label: '承运人代码', width: 100, query: 1, type: 'select', dicData: RemoteData.instance('get_base_airline_list', { pageSize: 10000, pageNumber: 0 }, (res) => res.data.content.map(item => { return { label: item.iatacode, value: item.iatacode, ot: item.cnname } })), meta: { template: (target) => [ target.ot, target.label ] }, queryType: 'select' },
          {
            // eslint-disable-next-line
            prop: 'airlinesBranch', label: '分公司代码', width: 120, sortable: true, type: 'select', dicData: RemoteData.instance('get_base_airlinesBranch_list', { pageSize: 10000, pageNumber: 0 }, (res) => res.data.content.map(item => { return { label: item.airlinesCode, value: item.airlinesCode, ot: item.cnabbr } })),
            rules: [{ required: true, min: 1, max: 5, message: '请输入不超过5位的分公司代码', trigger: 'blur' | 'change' }],
            meta: { convert: 'toUpperCase', template: (target) => [ target.ot, target.label ] }
          },
          { prop: 'availableSeats', label: '可供座位', minWidth: 90, type: 'number', meta: { max: 99999999, min: 0 } },
          { prop: 'maximumSeats', label: '最大座位', minWidth: 90, type: 'number', meta: { max: 99999999, min: 0 } },
          { prop: 'availablePayLoad', label: '可供业载', minWidth: 90, type: 'number', meta: { max: 99999999, min: 0 } },
          { prop: 'maximumPayLoad', label: '最大业载', minWidth: 90, type: 'number', meta: { max: 99999999, min: 0 } },
          { prop: 'isLoadedPlate', label: '是否装板', width: 90, type: 'select', dicData: 'YES_OR_NO' },
          { prop: 'isoxygenChamber', label: '是否有有氧舱', minWidth: 120, type: 'select', dicData: 'YES_OR_NO' },
          { prop: 'maintenanceSeat', label: '机务座位', minWidth: 90, rules: [{ trigger: 'blur' }, { max: 50, message: '请输入少于50位的机务座位' }] },
          { prop: 'crewSeat', label: '机组座位', minWidth: 90, rules: [{ trigger: 'blur' }, { max: 50, message: '请输入少于50位的机组座位' }] },
          { prop: 'safetyOfficerSeat', label: '安全员座位', width: 100, rules: [{ trigger: 'blur' }, { max: 50, message: '请输入少于50位的安全员座位' }] },
          { prop: 'seatLayOut', label: '座位布局', width: 90, rules: [{ trigger: 'blur' }, { max: 50, message: '请输入少于50位的座位布局' }] },
          { prop: 'doorSize', label: '舱门尺寸', width: 90, type: 'number', meta: { max: 99999999, min: 0 } },
          { prop: 'emergencyExit', label: '紧急出口', width: 90, rules: [{ trigger: 'blur' }, { max: 50, message: '请输入少于50位的紧急出口' }] },
          { prop: 'mtow', label: '最大起飞重量', width: 120, type: 'number', meta: { max: 9999999999, min: 0 } },
          { prop: 'parkTime', label: '标准停场时间', type: 'number', width: 120, meta: { max: 999, min: 0 } },
          { prop: 'ctn', label: '离港CTN', width: 90, rules: [{ trigger: 'blur' }, { max: 10, message: '请输入少于10位的离港CTN' }] },
          { prop: 'aircraftTypeDetail', label: '细分机型', width: 90, rules: [{ trigger: 'blur' }, { max: 20, message: '请输入少于20位的细分机型' }] },
          { prop: 'remark', label: '备注', width: 90, span: 24, type: 'textarea', rules: [{ trigger: 'blur' }, { max: 100, message: '请输入少于100位的备注' }] }
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
    })
  },
  methods: {
    getTableData (val) {
      this.rowSelected = null
      let getDataAjax = this.$auth('get_base_aircraftRegistration')
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
      let add = this.$auth('post_base_aircraftRegistration')
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
      let update = this.$auth('put_base_aircraftRegistration')
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
        let remove = this.$auth('delete_base_aircraftRegistration')
        remove({id: row.id}).then(res => {
          res.status && this.$ok(res.message)
          this.getTableData()
        })
      }).catch(() => {})
    }
  }
}
</script>
