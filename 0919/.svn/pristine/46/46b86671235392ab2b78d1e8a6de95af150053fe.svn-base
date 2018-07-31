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
          <el-input v-model="filters.statusCode" placeholder="编码" @change="statusCodeUpper"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getFlightstatusList">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleAdd">新增</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table v-bind:data="flightstatus" highlight-current-row v-loading="listLoading" @selection-change="selsChange" :height="tableHeight" style="width: 100%;">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column type="index" width="60">
        <template scope="scope">
          {{pageSize * (pageNumber - 1) + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="statusCode" label="编码" width="115" sortable>
      </el-table-column>
      <el-table-column prop="direction" label="进港/出港" width="115" :formatter="baseUtil.formatterDirection">
      </el-table-column>
      <el-table-column prop="statusCatalog" label="状态分类" width="160">
      </el-table-column>
      <el-table-column prop="disabled" label="状态" width="100" :formatter="baseUtil.formatterDisableString">
      </el-table-column>
      <el-table-column prop="description" label="描述" min-width="180" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="remark" label="备注" min-width="180" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="updateTime" label="最后更新时间" width="180">
      </el-table-column>
      <el-table-column label="操作" width="180" fixed="right" >
        <template scope="scope">
          <el-button size="small" @click="handleEdit(scope.$index, scope.row)"><span><i class="el-icon-edit"></i> 编辑 </span></el-button>
          <el-button size="small" @click="handleDel(scope.$index, scope.row)"><span><i class="el-icon-delete"></i> 删除 </span></el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--分页-->
    <pagination :to="getFlightstatusList" ref="page"></pagination>

    <!--新增界面-->
    <common-add-or-update
        @getInputItem="getStatusCodeChange"
        type="add"
        :to="API.addFlightstatus().go"
        :callback="getFlightstatusList"
        :labelWidth="100"
        ref="addForm"></common-add-or-update>
    <!--编辑界面-->
    <common-add-or-update
        @getInputItem="getStatusCodeChange"
        title="编辑"
        type="update"
        :to="API.editFlightstatus().go"
        :callback="getFlightstatusList"
        :labelWidth="100"
        ref="editForm"></common-add-or-update>
    <!--删除窗口-->
    <common-delete
        :to="API.removeFlightstatuscodes().go"
        :callback="getFlightstatusList"
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
          statusCode: ''
        },
        flightstatus: [],
        tableHeight: 495,
        listLoading: false,
        sels: [],
        airports: [],
        // 用于标记是新增还是编辑
        addUpdateTag: null,
        // 用于标记编码是否已经改变
        statusCodeTag: null,
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
              { name: 'statusCode', value: '', label: '编码', type: 'text', rules: [ { max: 6, min: 1, message: '编码只能为少于六位的字符串' } ], placeholder: '', span: 12 },
              { name: 'direction', value: 'A', label: '进港/出港', type: 'select', choose: [ { text: '进港', value: 'A' }, { text: '出港', value: 'D' } ], rules: null, placeholder: '', span: 12 }
            ]
          },
          {
            id: '2',
            item: [
              { name: 'statusCatalog', value: '', label: '状态分类', type: 'text', rules: [ { max: 60, min: 1, message: '状态分类只能为少于六十位的字符串' } ], placeholder: '', span: 12 },
              { name: 'disabled', value: '1', label: '状态', type: 'select', choose: [ { text: '禁用', value: '1' }, { text: '启用', value: '0' } ], rules: null, placeholder: '', span: 12 }
            ]
          },
          {
            id: '3',
            item: [
              { name: 'description', value: '', label: '描述', type: 'textarea', rules: [ { max: 50, min: 1, message: '描述只能为少于五十位的字符串' } ], placeholder: '', span: 12 },
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
      getFlightstatusList () {
        let para = Object.assign({}, this.filters, this.$refs['page'].queryParam())
        this.listLoading = true
        API.getFlightstatusListPage().go(para).then((data) => {
          if (data.ok) {
            this.$refs['page'].set('total', data.attr.data.pager.recordCount)
            this.flightstatus = data.attr.data.list
            this.filters.statusCode = ''
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
        this.$refs['delConfirm'].del(row.id)
      },
      // 显示新增界面
      handleAdd: function () {
        this.addUpdateTag = 'add'
        this.statusCodeTag = null
        let addFields = Util.deepCopy(this.fields)
        this.$refs['addForm'].show(addFields)
      },
      // 显示编辑界面
      handleEdit: function (index, row) {
        this.addUpdateTag = 'update'
        this.statusCodeTag = row.statusCode
        let editFields = Util.deepCopy(this.fields)
        for (let i = 0; i < editFields.length; i++) {
          for (let j = 0; j < editFields[i].item.length; j++) {
            editFields[i].item[j].value = row[editFields[i].item[j].name]
          }
        }
        this.$refs['editForm'].show(editFields)
      },
      getStatusCodeChange: function (data) {
        if (data.statusCode !== this.statusCodeTag) {
          var upperStatusCode = null
          upperStatusCode = data.statusCode.toUpperCase()
          if (this.addUpdateTag === 'add') {
            var statusCodeStrAdd = 'statusCode'
            this.$refs['addForm'].changeInput(statusCodeStrAdd, upperStatusCode)
          }
          if (this.addUpdateTag === 'update') {
            var statusCodeStrUpdate = 'statusCode'
            this.$refs['editForm'].changeInput(statusCodeStrUpdate, upperStatusCode)
          }
        }
        this.statusCodeTag = data.statusCode
      },
      statusCodeUpper: function (data) {
        this.filters.statusCode = data.toUpperCase()
      }
    },
    mounted () {
      this.getFlightstatusList()
    }
  }

</script>

<style scoped>

</style>
