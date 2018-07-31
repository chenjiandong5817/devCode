<template>
  <div>
    <div class="toolbar">
      <el-form :inline="true" :model="filters">
        <el-form-item>

          <el-form-item>
            <el-input v-model="filters.messageContent" placeholder="消息内容" size="small" clearable></el-input>
          </el-form-item>

          <el-form-item>
            <el-date-picker v-model="filters.receive" type="datetimerange" size="small"
              range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期">
            </el-date-picker>
          </el-form-item>

          <el-form-item>
            <el-date-picker v-model="filters.confirm" type="datetimerange" size="small"
              range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期">
            </el-date-picker>
          </el-form-item>

        </el-form-item>
        <el-form-item>
          <el-form-item>
            <el-button type="primary" v-auth="{code: 'get_message_log_list', click: getList}" size="small">查询</el-button>
          </el-form-item>
          <el-form-item v-auth="'put_message_log_update'">
            <el-button type="primary" @click="handleUpdate" :disabled="!rowSelected" size="small">编辑</el-button>
          </el-form-item>
        </el-form-item>
      </el-form>
    </div>
    <div class="table-content">
      <el-table v-bind:data="data" highlight-current-row @row-click="handleSelect" style="width: 100%;" size="small">
        <el-table-column type="index" min-width="60">
           <template slot-scope="scope">
            {{ (pager.pageNumber - 1) * pager.pageSize + scope.$index + 1 }}
          </template>
        </el-table-column>
        <el-table-column prop="user.name" label="用户" width="100"></el-table-column>
        <el-table-column label="接收时间" min-width="120">
          <template slot-scope="scope">
            {{ moment(scope.row.receiveTime).format('YYYY-MM-DD HH:mm') }}
          </template>
        </el-table-column>
        <el-table-column prop="messageInfo.content" label="消息内容" min-width="250" sortable show-overflow-tooltip></el-table-column>
        <el-table-column label="确认时间" min-width="120">
          <template slot-scope="scope">
            <el-button v-if="scope.row.confirmTime === null" size="mini" type="primary" @click="handleEdit(scope.row.id, 'confirmed')">确认</el-button>
            <span v-else>{{moment(scope.row.confirmTime).format('YYYY-MM-DD HH:mm') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="取消时间" min-width="120">
          <template slot-scope="scope">
            <!-- {{ scope.row.cancelTime ? moment(scope.row.cancelTime).format('YYYY-MM-DD HH:mm') : '' }} -->
            <el-button v-if="scope.row.cancelTime === null" size="mini" @click="handleEdit(scope.row.id, 'canceled')">取消</el-button>
            <span v-else>{{moment(scope.row.cancelTime).format('YYYY-MM-DD HH:mm') }}</span>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <pagination
      right
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pager.pageNumber"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pager.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pager.total">
    </pagination>
    <Dialog ref="dialog" :callback="getList"></Dialog>
  </div>
</template>
<script>
import moment from 'moment'
import { ignoreNull, adjustPage, deepCopy } from '@/util/util'
import { validatenull } from '@/util/validate'
import Dialog from './message-log-dialog'
import pagination from '@/components/pagination/'
export default {
  components: {
    Dialog, pagination
  },
  data () {
    return {
      // 查询条件
      filters: {
        messageContent: '',
        receive: [],
        confirm: []
      },
      // 分页参数
      pager: {
        pageSize: 10,
        pageNumber: 1,
        total: 0
      },
      // 数据列表
      data: [],
      rowSelected: null,
      moment: moment
    }
  },
  mounted () {},
  methods: {
    getList (ajax) {
      this.rowSelected = null
      if (!ajax) {
        ajax = this.$auth('get_message_log_list')
      }
      let filters = deepCopy(this.filters)
      let obj = {
        messageContent: filters.messageContent,
        receiveStartTime: validatenull(filters.receive) ? null : +moment(filters.receive[0]),
        receiveEndTime: validatenull(filters.receive) ? null : +moment(filters.receive[1]),
        confirmStartTime: validatenull(filters.confirm) ? null : +moment(filters.confirm[0]),
        confirmEndTime: validatenull(filters.confirm) ? null : +moment(filters.confirm[1])
      }
      ajax(Object.assign({}, ignoreNull(obj), adjustPage(this.pager))).then(res => {
        if (res.status && res.data) {
          this.data = res.data.content
          this.pager.total = res.data.totalElements
          if (this.pager.pageNumber > 1 && this.data.length === 0) {
            this.pager.pageNumber = 1
            this.getList()
          }
        }
      })
    },
    handleEdit (id, messageStatus) {
      let update = this.$auth('put_message_log_update')
      update({id: id, messageStatus: messageStatus}).then((res) => {
        this.loading = false
        res.status && this.$ok(res.message)
        this.getList()
        this.visible = false
      })
    },
    // 编辑按钮响应事件
    handleUpdate () {
      this.$refs['dialog'].show(this.rowSelected)
    },
    // 删除按钮响应事件
    handleRemove () {
      this.$confirm(`是否删除?`, {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        if (this.data.length === 1 && this.pager.pageNumber !== 1) {
          this.pager.pageNumber-- // 当在最后一页删除最后一条数据 并且 该页不是第一页时, 自动跳转到前一页
        }
        let remove = this.$auth('delete_messageInfo_remove')
        remove({id: this.rowSelected.id}).then(res => {
          res.status && this.$ok(res.message)
          this.getList()
        })
      }).catch(() => {})
    },
    // 表格选中一条记录的响应事件
    handleSelect (row) {
      this.rowSelected = row
    },
    // 分页点击页码事件
    handleCurrentChange (val) {
      this.pager.pageNumber = val
      this.getList()
    },
    // 分页修改分页大小事件
    handleSizeChange (val) {
      this.pager.pageSize = val
      this.getList()
    }
  }
}
</script>
