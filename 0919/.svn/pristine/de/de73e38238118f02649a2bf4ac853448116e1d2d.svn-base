/*
 * @Author: ylj
 * @Date: 2017-11-29 20:55:57
 * @Last Modified by: ylj
 * @Last Modified time: 2017-11-30 13:50:59
 */
<template>
  <div class="MorFlightGenTimeClss">
    <!--搜索工具栏（整行、置顶）-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item label="开始日期">
          <el-date-picker v-model="filters.beginDate" type="date" placeholder="开始日期" clearable ></el-date-picker>
        </el-form-item>
        <el-form-item label="结束日期">
          <el-date-picker v-model="filters.endDate" type="date" placeholder="结束日期" clearable ></el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="dataSearch">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="handleAdd">新增</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table v-bind:data="datas" highlight-current-row v-loading="loading" @selection-change="selectChange" :height="tableHeight" style="width: 100%;">
      <el-table-column type="index" label="序号" align="center" width="42">
        <template scope="scope">
          {{pageSize * (pageNumber - 1) + scope.$index + 1}}
        </template>
      </el-table-column>
      <el-table-column prop="beginTime" label="开始时间" :formatter="dataFormat" width="83" sortable show-overflow-tooltip ></el-table-column>
      <el-table-column prop="endTime" label="开始时间" :formatter="dataFormat" width="83" sortable show-overflow-tooltip ></el-table-column>
      <el-table-column prop="beginDate" label="生效开始日期" :formatter="dataFormat" width="110" sortable show-overflow-tooltip ></el-table-column>
      <el-table-column prop="endDate" label="生效结束日期" :formatter="dataFormat" width="110" sortable show-overflow-tooltip ></el-table-column>
      <el-table-column prop="isValid" label="是否生效" min-width="55" sortable></el-table-column>
      <el-table-column label="操作" width="90" header-align="center" fixed="right">
        <template scope="scope">
          <el-button @click="handleEdit(scope.$index, scope.row)" type="text" size="small">编辑</el-button>
          <el-button @click="handleDel(scope.$index, scope.row)" type="text" size="small">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--分页-->
    <pagination :to="dataSearch" ref="page"></pagination>

    <!--新增界面-->
    <add-or-update
      title="新增"
      size="tiny"
      :isSimpleSubmit="true"
      :to="API.addMorFlightGenTime().go"
      :callback="dataSearch"
      ref="addForm"></add-or-update>

    <!--编辑界面-->
    <add-or-update
     title="编辑"
     size="tiny"
     :isSimpleSubmit="true"
     :to="API.editMorFlightGenTime().go"
     :callback="dataSearch"
     ref="editForm"></add-or-update>

    <!--删除窗口-->
    <commonDelete
      :to="API.removeMorFlightGenTime().go"
      :callback="dataSearch"
      :labelWidth="100"
      ref="deleteForm"></commonDelete>

  </div>
</template>

<script>
import Util from '../../common/js/util'
import Pagination from '../../components/Pagination'
import API from '../../api'
import AddOrUpdate from '../base/baseForm/CommAddOrUpdate'
import commonDelete from './../../components/CommDelete'

export default {
  data () {
    return {
      filters: {
        beginDate: '',
        endDate: ''
      },
      datas: [],
      loading: false,
      tableHeight: 450,
      selects: [],
      API: API
    }
  },
  computed: {
    pageNumber () {
      return this.$refs['page'].get('pageNumber')
    },
    pageSize () {
      return this.$refs['page'].get('pageSize')
    },
    fields () {
      let field = [
        {
          id: '0',
          hidden: true,
          item: [
            { name: 'id', value: '', label: 'id', type: 'text', disabled: true, span: 12 },
            { name: 'name', value: '', label: '名称', type: 'text', disabled: true, span: 12 }
          ]
        },
        {
          id: '1',
          item: [
            { name: 'beginDate', value: '', label: '启用日期', visitDate: true, visitTime: false, dateStyle: 'width: 100%', timeStyle: 'width: 30%', type: 'dateTimeGroup', allDisable: false, datePlaceholder: '生效开始日期', timePlaceholder: '时间', formatter: 'yyyy-MM-dd', rules: [ { required: true, message: '请选择结束时间', trigger: 'change' } ], span: 12 },
            { name: 'endDate', value: '', label: '禁用日期', visitDate: true, visitTime: false, dateStyle: 'width: 100%', timeStyle: 'width: 30%', type: 'dateTimeGroup', allDisable: false, datePlaceholder: '生效结束日期', timePlaceholder: '时间', formatter: 'yyyy-MM-dd', rules: [ { required: true, message: '请选择结束时间', trigger: 'change' } ], span: 12 }
          ]
        },
        {
          id: '2',
          item: [
            { name: 'beginTime', value: '', label: '开始时间', visitDate: true, visitTime: true, dateStyle: 'width: 67%', timeStyle: 'width: 30.5%', type: 'dateTimeGroup', allDisable: false, datePlaceholder: '开始日期', timePlaceholder: '时间', formatter: 'yyyy-MM-dd', rules: [ { required: true, message: '请选择结束时间', trigger: 'change' } ], span: 12 },
            { name: 'endTime', value: '', label: '结束时间', visitDate: true, visitTime: true, dateStyle: 'width: 67%', timeStyle: 'width: 30.5%', type: 'dateTimeGroup', allDisable: false, datePlaceholder: '结束日期', timePlaceholder: '时间', formatter: 'yyyy-MM-dd', rules: [ { required: true, message: '请选择结束时间', trigger: 'change' } ], span: 12 }
          ]
        }
      ]
      return field
    }
  },
  watch: {
    '$store.state.cached' (val, oldVal) {
      // 重新刷新数据
      this.dataSearch()
    }
  },
  components: {
    pagination: Pagination,
    addOrUpdate: AddOrUpdate,
    commonDelete: commonDelete
  },
  methods: {
    bindData () {
      this.dataSearch()
    },
    dataSearch () {
      this.filters.beginDate = Util.formatDate.flightDateFmt('yyyy-MM-dd', this.filters.beginDate, false)
      this.filters.endDate = Util.formatDate.flightDateFmt('yyyy-MM-dd', this.filters.endDate, false)
      let para = Object.assign({}, this.filters, this.$refs['page'].queryParam())
      this.loading = true
      API.getMorFlightGenTimels().go(para).then((data) => {
        if (data.ok) {
          this.$refs['page'].set('total', data.attr.data.pager.recordCount)
          this.datas = data.attr.data.list
        } else {
          this.$notify(Util.notifyBody(false, data.msg))
        }
        this.loading = false
      })
    },
    selectChange: function (select) {
      this.selects = select
    },
    handleAdd: function () {
      let fields = Util.deepCopy(this.fields)
      this.$refs['addForm'].show(fields)
    },
    handleEdit: function (index, row) {
      let fields = Util.deepCopy(this.fields)
      for (let i = 0; i < fields.length; i++) {
        for (let j = 0; j < fields[i].item.length; j++) {
          fields[i].item[j].value = row[fields[i].item[j].name]
        }
      }
      this.$refs['editForm'].show(fields)
    },
    handleDel: function (index, row) {
      this.$refs['deleteForm'].del(row)
    },
    dataFormat: function (row, column, cellValue) {
      // 格式化数据列表中日期格式
      let date = row[column.property]
      if (date === undefined || date === null) {
        return ''
      }
      let field = column.property
      switch (field) {
        case 'beginTime': return Util.formatDate.flightDateFmt('yyyy-MM-dd hhmm', date, false)
        case 'endTime': return Util.formatDate.flightDateFmt('yyyy-MM-dd hhmm', date, false)
        case 'beginDate': return Util.formatDate.flightDateFmt('yyyy-MM-dd', date, false)
        case 'endDate': return Util.formatDate.flightDateFmt('yyyy-MM-dd', date, false)
        default: break
      }
    }
  },
  mounted () {
    this.bindData()
  }
}
</script>
<style lang="scss">
.MorFlightGenTimeClss {
  .el-table .cell, .el-table th>div {
      padding-left: 4px!important;
      padding-right: 2px!important;
      box-sizing: border-box;
      text-overflow: ellipsis;
  }

  .el-table {
      overflow: hidden;
      width: 100%;
      max-width: 100%;
      background-color: #fff;
      border: 1px solid #dfe6ec;
      font-size: 14px;
      color: #1f2d3d;
      margin-left: 10px !important;
  }
}
</style>

