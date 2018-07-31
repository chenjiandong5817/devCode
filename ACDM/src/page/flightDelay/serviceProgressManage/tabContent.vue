<template>
  <div>
    <div class="toolbar">
        <el-form :inline="true" size="small">
          <el-form-item label="操作">
            <el-button type="primary" size="small" @click="handleAdd">新增</el-button>
            <el-button type="primary" size="small" @click="handleUpdate">修改</el-button>
            <el-button type="danger" size="small" @click="handleDelete">删除</el-button>
          </el-form-item>
        </el-form>
    </div>
    <el-table
    :data="tableData"
    :span-method="arraySpanMethod"
    border
    @row-click="handleRowClick"
    highlight-current-row
    size="small"
    style="width: 100%">
        <el-table-column
            prop="progress"
            label="进度">
        </el-table-column>
        <el-table-column
            prop="title"
            label="名称">
        </el-table-column>
        <el-table-column
            prop="description"
            label="描述">
        </el-table-column>
        <el-table-column
            prop="roleCode"
            label="角色"
            :formatter="formatter">
        </el-table-column>
            <el-table-column
            prop="readable"
            label="可读"
            :formatter="formatterR">
        </el-table-column>
        <el-table-column
            prop="writeable"
            label="可写"
            :formatter="formatterW">
        </el-table-column>
    </el-table>
    <el-dialog
    title="确认"
    custom-class="dialog-to-message"
    :visible.sync="deleteVisible"
    :close-on-click-modal="false">
    <span>确定删除此条服务进度管理信息？</span><br />
    <span slot="footer" class="dialog-footer">
        <el-button @click="deleteVisible = false" size="small">取 消</el-button>
        <el-button type="primary" @click="submitDelete" size="small">确 定</el-button>
    </span>
    </el-dialog>
    <service-progress-dialog :list="tableData" :show.sync="show" ref="dialog" @commitData="commitData"></service-progress-dialog>
  </div>
</template>
<script>
import _ from 'lodash'
import ServiceProgressDialog from './serviceProgressDialog'
import { ignoreNull } from '@/util/util'
import { mapGetters } from 'vuex'
export default {
  components: {
    ServiceProgressDialog
  },
  props: {
    list: {
      type: Array,
      default: () => []
    },
    activeName: {
      type: String,
      default: null
    }
  },
  data () {
    return {
      deleteVisible: false,
      selectedRow: null,
      show: false,
      flag: true
    }
  },
  methods: {
    formatter (row, column) {
      let result = null
      this.roleAll.forEach(ele => {
        if (ele.code === row.roleCode) {
          result = ele.name
        }
      })
      return result
    },
    handleUpdate () {
      if (!this.selectedRow) {
        this.$fail('请先选中数据')
        return
      }
      this.show = true
      this.$refs['dialog'].toUpdate(this.selectedRow)
      this.flag = false
    },
    handleAdd () {
      this.show = true
      this.$refs['dialog'].toAdd()
      this.flag = true
    },
    commitData (data) {
      let ajax
      if (this.flag) {
        ajax = this.$auth('post_progress_role_add')
      } else {
        ajax = this.$auth('put_progress_role_update')
      }
      if (ajax) {
        data.serviceType = this.activeName
        ajax(ignoreNull(data)).then((res) => {
          if (res.status === 1) {
            this.$ok(res.message)
            this.show = false
            this.$emit('update', null)
          }
        })
      }
    },
    handleRowClick (row) {
      this.selectedRow = row
    },
    handleDelete () {
      this.deleteVisible = true
    },
    submitDelete () {
      let ajax = this.$auth('delete_progress_role')
      if (ajax) {
        ajax({id: this.selectedRow.id}).then((res) => {
          res.status && this.$ok(res.message)
          this.$emit('update', null)
          this.removeData()
        })
      }
    },
    removeData () {
      this.selectedRow = null
      this.deleteVisible = false
    },
    formatterR (row, column) {
      return row.readable != null ? (row.readable ? '是' : '否') : ''
    },
    formatterW (row, column) {
      return row.writeable != null ? (row.writeable ? '是' : '否') : ''
    },
    arraySpanMethod ({ row, column, rowIndex, columnIndex }) {
      if (columnIndex === 0 || columnIndex === 1 || columnIndex === 2) {
        const _row = this.spanArr[rowIndex]
        const _col = _row > 0 ? 1 : 0
        return {
          rowspan: _row,
          colspan: _col
        }
      }
    }
  },
  computed: {
    ...mapGetters(['roleAll']),
    tableData () {
      let arr = _.sortBy(this.list, 'progress')
      return arr
    },
    spanArr () {
      let result = []
      let contactNum = 0
      let arr = _.sortBy(this.list, 'progress')
      arr.forEach((item, index) => {
        if (index === 0) {
          result.push(1)
        } else {
          if (item.progress === arr[index - 1].progress) {
            result[contactNum] = result[contactNum] + 1
            result.push(0)
          } else {
            result.push(1)
            contactNum = index
          }
        }
      })
      return result
    }
  }
}
</script>
