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
      @sort-change="handleSortChange"
      >
      <template slot-scope="{ row, $index }" slot="whetherConfirm">
        <el-tag size="mini" :type="row.whetherConfirm === 1 ? 'success' : 'danger'">
          {{ row.whetherConfirm === 1 ? '是' : $index }}
        </el-tag>
      </template>
      <!-- 操作按钮组 -->
      <template slot="btn_opt">
        <el-form-item>
          <el-button type="primary" v-auth="{code: 'get_messageInfo_filterlist', click: getTableData}" size="small">查询</el-button>
        </el-form-item>
        <el-form-item class="divided">
          <el-button type="primary" v-auth="{code: 'post_messageInfo_add', click: handleAdd}" size="small">新增</el-button>
        </el-form-item>
        <el-form-item v-auth="'put_messageInfo_update'">
          <el-button type="primary" @click="handleEdit" :disabled="!rowSelected" size="small">编辑</el-button>
        </el-form-item>
        <el-form-item v-auth="'delete_messageInfo_remove'">
          <el-button type="danger" :disabled="!rowSelected" @click="handleRemove" size="small">删除</el-button>
        </el-form-item>
      </template>
      <template slot="flightForm" slot-scope="scope">
        <el-input v-model="scope.form[scope.column.prop]" placeholder="77777" size="small"></el-input>
      </template>
      <template slot="carrierCrud" slot-scope="{ target }">
        <div>{{ target.label + target.value + target.ot }}</div>
      </template>
    </crud>
  </div>
</template>
<script>
import Crud from '@/components/crud'
import { validatenull } from '@/util/validate'
import { ignoreNull, adjustPage, deepCopy } from '@/util/util'
import { validUpperCase } from '@/util/rules'
import RemoteData from '@/util/remote-data'
export default {
  components: {
    Crud
  },
  data () {
    let delBlankChild = (tree) => {
      for (let node of tree) {
        if (node.children.length !== 0) {
          delBlankChild(node.children)
        } else {
          node.children = null
        }
      }
    }
    let getMessageType = (res) => {
      if (res.status && res.data) {
        let tree = deepCopy(res.data)
        delBlankChild(tree)
        return tree
      }
    }
    return {
      tableData: [],
      filters: {},
      page: {
        pageSize: 10,
        pageNumber: 1,
        total: 0,
        pageSizes: [10, 20, 50, 100, 200]
      },
      loading: false,
      rowSelected: null,
      tableOption: {
        border: true,
        stripe: true,
        selection: true,
        index: true,
        page: true,
        height: 'auto',
        column: [
          { prop: 'flightId', label: '航班ID', width: 120, span: 24 },
          {
            prop: 'group-flight',
            label: '航班信息',
            group: [
              { prop: 'carrier', label: '航空公司', width: 120, type: 'select', dicData: [{label: '一号', value: '1', ot: 'xixi'}, {label: '二号', value: '2', ot: '啊哈'}], query: 1, rules: [{ required: true, validator: validUpperCase, trigger: 'blur' }], meta: { clearable: false, convert: 'toUpperCase', template: (target) => [target.value] } },
              {
                prop: 'sub-group-flight',
                label: '详细信息',
                group: [
                  { prop: 'flight', label: '航班号', width: 120, sortable: true, query: 1, formSlot: true },
                  { prop: 'whetherConfirm', label: '需要确认', width: 120, slot: true, dicData: 'NUM_YES_OR_NO', type: 'radio', query: 1, queryType: 'select', meta: { value: 0, border: false } }
                ]
              }
            ]
          },
          {
            prop: 'group-message',
            label: '消息',
            group: [
              { prop: 'typeCode', label: '消息类型', width: 100, query: 1, type: 'cascader', dicData: RemoteData.instance('get_message_type_list', getMessageType), rules: [{ required: true, trigger: 'blur' }], meta: {props: { value: 'code', label: 'name', children: 'children' }} },
              { prop: 'content', label: '消息内容', span: 24, type: 'textarea', dicData: RemoteData.instance('get_messageInfo_filterlist', { pageSize: 10, pageNumber: 0 }, (res) => res.data.content) }
            ]
          },
          { prop: 'testDate', label: '测试日期', hide: true, width: 120, type: 'text', query: 2, queryType: 'daterange', meta: { clearable: false } },
          { prop: 'testDate2', label: '测试日期', hide: true, width: 120, type: 'datetime', query: 2, queryType: 'date', meta: { clearable: false } },
          { prop: 'whetherConfirm', label: '需要确认', width: 120, slot: true, dicData: 'NUM_YES_OR_NO', type: 'radio', query: 1, queryType: 'select', meta: { value: 0, border: false } },
          { prop: 'whetherConfirm', label: '需要确认', width: 120, slot: true, dicData: 'NUM_YES_OR_NO', type: 'radio', query: 1, queryType: 'select', meta: { value: 0, border: false } },
          { prop: 'whetherConfirm', label: '需要确认', width: 120, slot: true, dicData: 'NUM_YES_OR_NO', type: 'radio', query: 1, queryType: 'select', meta: { value: 0, border: false } },
          { prop: 'whetherConfirm', label: '需要确认', width: 120, slot: true, dicData: 'NUM_YES_OR_NO', type: 'radio', query: 1, queryType: 'select', meta: { value: 0, border: false } },
          { prop: 'whetherConfirm', label: '需要确认', width: 120, slot: true, dicData: 'NUM_YES_OR_NO', type: 'radio', query: 1, queryType: 'select', meta: { value: 0, border: false } },
          { prop: 'whetherConfirm', label: '需要确认', width: 520, slot: true, dicData: 'NUM_YES_OR_NO', type: 'radio', query: 1, queryType: 'select', meta: { value: 0, border: false } },
          { prop: 'whetherConfirm', label: '需要确认', width: 520, slot: true, dicData: 'NUM_YES_OR_NO', type: 'radio', query: 1, queryType: 'select', meta: { value: 0, border: false } }
        ]
      }
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
    getTableData () {
      console.log(this.filters)
      let getDataAjax = this.$auth('get_messageInfo_filterlist')
      if (!getDataAjax) return
      this.loading = true
      getDataAjax(Object.assign({}, ignoreNull(this.filters), adjustPage(this.page))).then(res => {
        console.log(res)
        this.loading = false
        this.tableData = res.data.content
        this.page.total = res.data.totalElements
        if (this.page.pageNumber > 1 && validatenull(this.tableData)) {
          this.page.pageNumber = 1
          console.log('重新查询')
          this.getTableData()
        }
      })
    },
    handleCurrentChange (val) {
      this.page.pageNumber = val
      this.getTableData()
    },
    handleSortChange (val) {
      // console.log(val)
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
      formCopy.typeCode = formCopy.typeCode.join('_')
      let add = this.$auth('post_messageInfo_add')
      add(formCopy).then((res) => {
        res.status && this.$ok(res.message)
        this.getTableData()
        done()
      })
    },
    handleEdit () {
      let rowSelected = deepCopy(this.rowSelected)
      console.log(this.rowSelected)
      rowSelected.typeCode = rowSelected.typeCode.split('_')
      this.$refs.crud.rowEdit(rowSelected)
    },
    handleRowUpdate (row, done) {
      let formCopy = Object.assign({}, row)
      formCopy.typeCode = formCopy.typeCode.join('_')
      let update = this.$auth('put_messageInfo_update')
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
        let remove = this.$auth('delete_messageInfo_remove')
        remove({id: row.id}).then(res => {
          res.status && this.$ok(res.message)
          this.getTableData()
        })
      }).catch(() => {})
    }
  }
}
</script>
