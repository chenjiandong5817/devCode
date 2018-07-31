<template>
  <div>
    <div class="toolbar">
      <el-form :inline="true" :model="filters">
        <el-form-item>
        <!--
          <el-form-item>
            <el-input v-model="filters.flightId" @clear="getList" @keyup.13.native="getList" placeholder="航班ID" size="small" clearable></el-input>
          </el-form-item>
        -->
          <el-form-item>
            <el-input v-model="filters.carrier"  @clear="getList" @keyup.13.native="getList" placeholder="承运人" size="small" clearable></el-input>
          </el-form-item>
          <el-form-item>
            <el-input v-model="filters.flight"  @clear="getList" @keyup.13.native="getList" placeholder="航班号" size="small" clearable></el-input>
          </el-form-item>
          <el-form-item>
            <el-input v-model="filters.foodDepartment"  @clear="getList" @keyup.13.native="getList" placeholder="配餐单位" size="small" clearable></el-input>
          </el-form-item>
          <el-form-item>
            <el-input v-model="filters.foodType"  @clear="getList" @keyup.13.native="getList" placeholder="配餐类型" size="small" clearable></el-input>
          </el-form-item>
        </el-form-item>
        <el-form-item>
          <el-form-item>
            <el-button type="primary" v-auth="{code: 'get_providefood_filterlist', click: getList}" size="small">查询</el-button>
          </el-form-item>
          <el-form-item v-auth="'post_providefood_add'">
            <el-button type="primary" @click="handleAdd" size="small">新增</el-button>
          </el-form-item>
          <el-form-item v-auth="'put_providefood_update'">
            <el-button type="primary" @click="handleUpdate" :disabled="!rowSelected" size="small">编辑</el-button>
          </el-form-item>
          <el-form-item v-auth="'delete_providefood_remove'">
            <el-button type="danger" :disabled="!rowSelected" @click="handleRemove" size="small">删除</el-button>
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
        <el-table-column label="服务时间" min-width="150">
          <template slot-scope="scope">
            {{ moment(scope.row.planArrive).format('YYYY-MM-DD HH:mm') }}
          </template>
        </el-table-column>
        <!--
        <el-table-column prop="flightId" label="航班ID" min-width="150" sortable></el-table-column>
        -->
        <el-table-column prop="carrier" label="承运人" min-width="100" sortable></el-table-column>
        <el-table-column prop="flight" label="航班号" min-width="120" sortable></el-table-column>
        <el-table-column prop="foodDepartment" label="配餐单位" min-width="180"></el-table-column>
        <el-table-column prop="foodType" label="配餐类型" min-width="150"></el-table-column>
        <el-table-column prop="foodCount" label="数量" min-width="80" sortable></el-table-column>
        <el-table-column label="计划送达时间" min-width="150">
          <template slot-scope="scope">
            {{ moment(scope.row.provideDate).format('YYYY-MM-DD HH:mm') }}
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
import { ignoreNull, adjustPage } from '@/util/util'
import moment from 'moment'
import Dialog from './provide-food-dialog'
import pagination from '@/components/pagination/'
export default {
  components: {
    Dialog, pagination
  },
  data () {
    return {
      // 查询条件
      filters: {
        flightId: '',
        carrier: '',
        flight: '',
        foodDepartment: '',
        foodType: ''
      },
      // 分页参数
      pager: {
        pageSize: 10,
        pageNumber: 1,
        total: 0
      },
      // 用户列表
      data: [],
      moment: moment,
      rowSelected: null
    }
  },
  mounted () {},
  methods: {
    getList (ajax) {
      this.rowSelected = null
      if (!ajax) {
        ajax = this.$auth('get_providefood_filterlist')
      }
      if (typeof ajax === 'object') { // 判断ajax是否是KeyboardEvent
        ajax = this.$auth('get_providefood_filterlist')
      }
      ajax(Object.assign({}, ignoreNull(this.filters), adjustPage(this.pager))).then(res => {
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
    // 新增按钮响应事件
    handleAdd () {
      this.$refs['dialog'].show()
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
        let remove = this.$auth('delete_providefood_remove')
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
