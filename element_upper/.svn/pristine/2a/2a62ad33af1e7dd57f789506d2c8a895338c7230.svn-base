<!-- /*
 * @Author: cdroid
 * @Date: 2017-05-25 10:42:17
 * @Last Modified by: mikey.zhaopeng
 * @Last Modified time: 2017-06-27 15:33:48
 * @Description: 通用机场列表界面
 */ -->
<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-input v-model="filters.flightTaskCode" placeholder="飞行任务代码" @change="flightTaskCodeUpper"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getFlightTaskList">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleAdd">新增</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table v-bind:data="flightTasks" highlight-current-row v-loading="listLoading" @selection-change="selsChange" :height="tableHeight" style="width: 100%;">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column type="index" width="60">
        <template scope="scope">
          {{pageSize * (pageNumber - 1) + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="flightTaskCode" label="飞行任务代码" width="145" sortable>
      </el-table-column>
      <el-table-column prop="abbr1w" label="一字简称" width="150">
      </el-table-column>
      <el-table-column prop="abbr2w" label="二字简称" width="150" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="priority" label="优先级" width="150">
      </el-table-column>
      <el-table-column prop="disabled" label="状态" width="150" :formatter="baseUtil.formatterDisableString">
      </el-table-column>
      <el-table-column prop="description" label="描述" width="180" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="remark" label="备注" width="180" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="updateTime" label="最后更新时间" min-width="180">
      </el-table-column>
      <el-table-column label="操作" width="180" fixed="right" >
        <template scope="scope">
          <el-button size="small" @click="handleEdit(scope.$index, scope.row)"><span><i class="el-icon-edit"></i> 编辑 </span></el-button>
          <el-button size="small" @click="handleDel(scope.$index, scope.row)"><span><i class="el-icon-delete"></i> 删除 </span></el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--分页-->
    <pagination :to="getFlightTaskList" ref="page"></pagination>

    <!--新增界面-->
    <common-add-or-update
        @getInputItem="getFlightTaskCodeChange"
        type="add"
        :to="API.addFlightTask().go"
        :callback="getFlightTaskList"
        :labelWidth="120"
        ref="addForm"></common-add-or-update>
    <!--编辑界面-->
    <common-add-or-update
        @getInputItem="getFlightTaskCodeChange"
        title="编辑"
        type="update"
        :to="API.editFlightTask().go"
        :callback="getFlightTaskList"
        :labelWidth="120"
        ref="editForm"></common-add-or-update>
    <!--删除窗口-->
    <common-delete
        :to="API.removeFlightTask().go"
        :callback="getFlightTaskList"
        :labelWidth="100"
        ref="delConfirm"></common-delete>
  </section>
</template>

<script>
  import Util from '../../common/js/util'
  import baseUtil from '../../common/js/base-util'
  import chooseDialog from './../../components/CommChooseGrid'
  import commonAddOrUpdate from './baseForm/CommAddOrUpdate'
  import commonDelete from './../../components/CommDelete'
  import API from '../../api'
  import Pagination from '../../components/Pagination'

  export default {
    data () {
      return {
        filters: {
          flightTaskCode: '',
          description: ''
        },
        flightTasks: [],
        tableHeight: 495,
        listLoading: false,
        sels: [],
        // 用于标记是添加还是编辑是否改变
        addUpdateTag: null,
        // 用于编辑飞行任务代码是否改变
        flightTaskCodeTag: null,
        priorityValidate: null,
        // 新增编辑需要的字段
        fields: [
          {
            id: '0',
            hidden: true,
            item: [
              { name: 'id', value: '' }
            ]
          },
          {
            id: '1',
            item: [
              { name: 'abbr1w', value: '', label: '一字简称', type: 'text', rules: [ { max: 10, min: 1, message: '一字简称只能为少于十位的字符串' } ], placeholder: '', span: 12 },
              { name: 'abbr2w', value: '', label: '二字简称', type: 'text', rules: [ { max: 20, min: 1, message: '二字简称只能为少于二十位的字符串' } ], placeholder: '', span: 12 }
            ]
          },
          {
            id: '2',
            item: [
              { name: 'priority', value: 0, label: '优先级', type: 'number', rules: null, placeholder: '', span: 12, min: 0, max: 99 },
              { name: 'disabled', value: '0', label: '状态', type: 'select', choose: [ { text: '禁用', value: '1' }, { text: '启用', value: '0' } ], rules: null, placeholder: '', span: 12 }
            ]
          },
          {
            id: '3',
            item: [
              { name: 'flightTaskCode', value: '', label: '飞行任务代码', type: 'text', rules: [ { max: 3, min: 1, required: true, message: '请输入一位到三位字符串的飞行任务代码' } ], placeholder: '请输入飞行任务代码', span: 12 },
              { name: 'description', value: '', label: '描述', type: 'textarea', rules: [ { max: 50, min: 1, required: true, message: '请输入一位到五十位字符串的描述' } ], placeholder: '请输入描述', span: 12 }
            ]
          },
          {
            id: '4',
            item: [
              { name: 'remark', value: '', label: '备注', type: 'textarea', rules: [ { max: 100, min: 1, message: '备注只能为少于一百位的字符串' } ], placeholder: '', span: 12 }
            ]
          }
        ],
        API: API,
        baseUtil: baseUtil
      }
    },
    computed: {
      pageNumber () {
        return this.$refs['page'].get('pageNumber')
      },
      pageSize () {
        return this.$refs['page'].get('pageSize')
      }
    },
    components: {
      chooseDialog: chooseDialog,
      commonAddOrUpdate: commonAddOrUpdate,
      commonDelete: commonDelete,
      pagination: Pagination
    },
    methods: {
      // 获取用户列表
      getFlightTaskList () {
        let para = Object.assign({}, this.filters, this.$refs['page'].queryParam())
        this.listLoading = true
        API.getFlightTaskListPage().go(para).then((data) => {
          if (data.ok) {
            this.$refs['page'].set('total', data.attr.data.pager.recordCount)
            this.flightTasks = data.attr.data.list
            this.filters.flightTaskCode = ''
            this.getPriorityValidate()
          } else {
            this.$notify(Util.notifyBody(false, data.msg))
          }
          this.listLoading = false
        })
      },
      selsChange: function (sels) {
        this.sels = sels
      },
      handleCommand: function (command, self) {
        var node = self.$vnode.data.attrs
        /* eslint-disable */
        eval('this.' + command).call(this, node.index, node.row)
        /* eslint-enable */
      },
      // 删除
      handleDel: function (index, row) {
        this.$refs['delConfirm'].del(row)
      },
      // 显示新增界面
      handleAdd: function () {
        this.addUpdateTag = 'add'
        this.fields[2].item[0].rules = this.priorityValidate
        this.flightTaskCodeTag = null
        let addFields = Util.deepCopy(this.fields)
        this.$refs['addForm'].show(addFields)
      },
      // 显示编辑界面
      handleEdit: function (index, row) {
        this.addUpdateTag = 'update'
        this.fields[2].item[0].rules = this.priorityValidate
        this.flightTaskCodeTag = row.flightTaskCode
        let editFields = Util.deepCopy(this.fields)
        for (let i = 0; i < editFields.length; i++) {
          for (let j = 0; j < editFields[i].item.length; j++) {
            editFields[i].item[j].value = row[editFields[i].item[j].name]
          }
        }
        this.$refs['editForm'].show(editFields)
      },
      getFlightTaskCodeChange: function (data) {
        if (data.flightTaskCode !== this.flightTaskCodeTag) {
          var upperFlightTaskCode = null
          upperFlightTaskCode = data.flightTaskCode.toUpperCase()
          if (this.addUpdateTag === 'add') {
            var flightTaskCodeStrAdd = 'flightTaskCode'
            this.$refs['addForm'].changeInput(flightTaskCodeStrAdd, upperFlightTaskCode)
          }
          if (this.addUpdateTag === 'update') {
            var flightTaskCodeStrUpdate = 'flightTaskCode'
            this.$refs['editForm'].changeInput(flightTaskCodeStrUpdate, upperFlightTaskCode)
          }
        }
        this.flightTaskCodeTag = data.flightTaskCode
      },
      flightTaskCodeUpper: function (data) {
        this.filters.flightTaskCode = data.toUpperCase()
      },
      getPriorityValidate: function () {
        this.priorityValidate = (rule, value, callback) => {
          if (value < 0) {
            return callback(new Error('值不能为负数'))
          }
          if (!Number.isInteger(value)) {
            callback(new Error('请输入数字值'))
          }
          if (value > 99) {
            callback(new Error('值只能在0~99之间'))
          }
          callback()
        }
      }
    },
    mounted () {
      this.getFlightTaskList()
    }
  }

</script>

<style scoped>

</style>
