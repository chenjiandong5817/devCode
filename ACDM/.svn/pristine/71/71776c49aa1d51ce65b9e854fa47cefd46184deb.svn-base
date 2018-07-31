<template>
  <div>
    <div class="toolbar">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-form-item>
            <el-input v-model="filters.carrier"  @clear="getList" @keyup.13.native="getList"
              placeholder="航空公司" size="small" style="width: 100px" clearable>
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-input v-model="filters.flight"  @clear="getList" @keyup.13.native="getList"
              placeholder="航班号" size="small" style="width: 100px" clearable>
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-cascader
              expand-trigger="hover"
              :options="options"
              :props="props"
              v-model="filters.typeCode"
              size="small"
              placeholder="消息类型"
              change-on-select
              clearable
              style="width: 250px">
            </el-cascader>
          </el-form-item>
          <el-form-item>
            <el-input v-model="filters.content"  @clear="getList" @keyup.13.native="getList"
              placeholder="消息内容" size="small" clearable>
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-select v-model="filters.whetherConfirm" placeholder="是否确认"
            size="small" clearable style="width: 100px" @clear="getList">
              <el-option
                v-for="item in selectOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-form-item>
        <el-form-item>
          <el-form-item>
            <el-button type="primary" v-auth="{code: 'get_messageInfo_filterlist', click: getList}" size="small">查询</el-button>
          </el-form-item>
          <el-form-item v-auth="'post_messageInfo_add'">
            <el-button type="primary" @click="handleAdd" size="small">新增</el-button>
          </el-form-item>
          <el-form-item v-auth="'put_messageInfo_update'">
            <el-button type="primary" @click="handleUpdate" :disabled="!rowSelected" size="small">编辑</el-button>
          </el-form-item>
          <el-form-item v-auth="'delete_messageInfo_remove'">
            <el-button type="danger" :disabled="selection.length === 0" @click="handleRemove" size="small">删除</el-button>
          </el-form-item>
        </el-form-item>
      </el-form>
    </div>
    <div class="table-content">
      <el-table ref="table" v-bind:data="data" highlight-current-row style="width: 100%;" size="small"
      @row-click="handleSelect" @selection-change="handleSelection">
        <el-table-column type="selection" width="55">
        </el-table-column>
        <el-table-column type="index" min-width="60">
          <template slot-scope="scope">
            {{ (pager.pageNumber - 1) * pager.pageSize + scope.$index + 1 }}
          </template>
        </el-table-column>
        <el-table-column prop="carrier" label="航空公司" min-width="100" sortable></el-table-column>
        <el-table-column prop="flight" label="航班号" min-width="120" sortable></el-table-column>
        <el-table-column prop="typeCode" label="消息类型" min-width="150"></el-table-column>
        <el-table-column prop="content" label="消息内容" min-width="200" sortable show-overflow-tooltip></el-table-column>
        <el-table-column prop="whetherConfirm" label="是否确认" min-width="100" sortable>
          <template slot-scope="scope">
            <el-tag :type="scope.row.whetherConfirm === 1 ? 'success' : 'danger'">
              {{ scope.row.whetherConfirm === 1 ? '是' : '否' }}
            </el-tag>
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
import { ignoreNull, adjustPage, deepCopy } from '@/util/util'
import Dialog from './message-info-dialog'
import pagination from '@/components/pagination/'
export default {
  components: {
    Dialog, pagination
  },
  data () {
    return {
      // 查询条件
      filters: {
        carrier: '',
        flight: '',
        typeCode: [],
        content: '',
        whetherConfirm: ''
      },
      // 分页参数
      pager: {
        pageSize: 10,
        pageNumber: 1,
        total: 0
      },
      options: [],
      selectOptions: [{
        value: 1,
        label: '是'
      }, {
        value: 0,
        label: '否'
      }],
      props: {
        value: 'code',
        label: 'name',
        children: 'children'
      },
      // 数据列表
      data: [],
      selection: [],
      rowSelected: null
    }
  },
  mounted () {
    let getMassageType = this.$auth('get_message_type_list')
    getMassageType().then(res => {
      if (res.status && res.data) {
        let tree = deepCopy(res.data)
        this.delBlankChild(tree)
        this.options = tree
      }
    })
  },
  methods: {
    getList (ajax) {
      this.rowSelected = null
      if (!ajax) {
        ajax = this.$auth('get_messageInfo_filterlist')
      }
      if (typeof ajax === 'object') { // 判断ajax是否是KeyboardEvent
        ajax = this.$auth('get_messageInfo_filterlist')
      }
      let filters = deepCopy(this.filters)
      filters.typeCode = filters.typeCode.join('.')
      ajax(Object.assign({}, ignoreNull(filters), adjustPage(this.pager))).then(res => {
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
      this.$refs['dialog'].show(null, this.options)
    },
    // 编辑按钮响应事件
    handleUpdate () {
      this.$refs['dialog'].show(this.rowSelected, this.options)
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
        let remove = this.$auth('put_messageInfo_delete') // 批量删除
        let ids = this.selection.map(item => item.id).join(',')
        remove({ids: ids}).then(res => {
          res.status && this.$ok(res.message)
          this.getList()
        })
      }).catch(() => {})
    },
    // 表格选中一条记录的响应事件
    handleSelect (row) {
      this.rowSelected = row
      // this.$refs.table.toggleRowSelection(row) 单击选中
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
    },
    handleSelection (val) {
      this.selection = val
    },
    delBlankChild (tree) { // 递归遍历树 删除空的子节点 为了构造级联选择器的options
      for (let node of tree) {
        if (node.children.length !== 0) {
          this.delBlankChild(node.children)
        } else {
          node.children = null
        }
      }
    }
  }
}
</script>
