/*
 * @Author: ylj
 * @Date: 2017-11-02 09:32:14
 * @Last Modified by: ylj
 * @Last Modified time: 2017-11-17 15:16:53
 */
<template>
  <section class="seasonNameCss">
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-select v-model="filters.airportCode" placeholder="机场代码" @change="getPlanSeasonNameLs" filterable clearable>
            <el-option
              v-for="item in subAirportLs"
              :key="item.value"
              :label="item.text + '(' + item.value + ')'"
              :value="item.value">
              <span style="float: left">{{ item.text }}</span>
              <span style="float: right">{{ item.value }}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getPlanSeasonNameLs">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleAdd">新增</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table v-bind:data="seasons" highlight-current-row v-loading="loadingList" @selection-change="selChange" :height="tableHeight" style="width: 100%;">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column type="index" width="60">
        <template scope="scope">
          {{ pageSize * (pageNumber - 1) + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="seasonName" label="航季名称" width="90" sortable>
      </el-table-column>
      <el-table-column prop="beginDate" label="开始日期" width="100" sortable :formatter="dataFormat">
      </el-table-column>
      <el-table-column prop="endDate" label="结束日期" width="100" sortable :formatter="dataFormat">
      </el-table-column>
      <el-table-column prop="isCurrentSeason" label="当前航季" min-width="90" show-overflow-tooltip>
        <template scope="scope" >
          {{ scope.row.isCurrentSeason === '1' ? '是' : '否'}}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="180" fixed="right" >
        <template scope="scope">
          <el-button size="small" @click="handleEdit(scope.$index, scope.row)"><span><i class="el-icon-edit"></i> 编辑 </span></el-button>
          <el-button size="small" @click="handleDel(scope.$index, scope.row)"><span><i class="el-icon-delete"></i> 删除 </span></el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--分页-->
    <pagination :to="getPlanSeasonNameLs" ref="page"></pagination>

    <!--新增界面-->
    <common-add-or-update
        type="add"
        size="tiny"
        :to="API.addPlanSeasonName().go"
        :callback="getPlanSeasonNameLs"
        :labelWidth="100"
        ref="addForm"></common-add-or-update>

    <!--编辑界面-->
    <common-add-or-update
        title="编辑"
        type="update"
        size="tiny"
        :to="API.editPlanSeasonName().go"
        :callback="getPlanSeasonNameLs"
        :labelWidth="100"
        ref="editForm"></common-add-or-update>

    <!--删除窗口-->
    <common-delete
        :to="API.removePlanSeasonName().go"
        :callback="getPlanSeasonNameLs"
        :labelWidth="100"
        ref="delConfirm"></common-delete>
  </section>
</template>

<script>
  import Util from '../../common/js/util'
  import Butil from '../../common/js/base-util'
  import commonAddOrUpdate from './../../components/CommAddOrUpdate'
  import commonDelete from './../../components/CommDelete'
  import Pagination from '../../components/Pagination'
  import API from '../../api'

  export default {
    data () {
      return {
        filters: {
          airportCode: ''
        },
        tableHeight: 495,
        loadingList: false,
        selects: [],
        subAirportLs: [],
        seasons: [],
        // 新增编辑需要的字段
        fields: [
          { name: 'id', value: '', hidden: true },
          { name: 'airportCode', value: '', label: '运营机场', filterable: true, type: 'simpleSelect', choose: this.subAirportLs, rules: [ { required: true, message: '请选择运营机场', trigger: 'blur' } ], placeholder: '运营机场' },
          { name: 'seasonName', value: '', label: '航季名称', type: 'text', placeholder: '航季名称', rules: [ { required: true, message: '请输入航季名称', trigger: 'blur' } ] },
          { name: 'beginDate', value: '', label: '开始时间', formatter: 'yyyy-MM-dd', visitDate: true, visitTime: false, dateStyle: 'width:100%', type: 'dateTimeGroup', rules: [ { required: true, message: '请选择开始时间', trigger: 'blur' } ], placeholder: '开始时间' },
          { name: 'endDate', value: '', label: '结束时间', formatter: 'yyyy-MM-dd', visitDate: true, visitTime: false, dateStyle: 'width:100%', type: 'dateTimeGroup', rules: [ { required: true, message: '请选择结束时间', trigger: 'blur' } ], placeholder: '结束时间' },
          { name: 'isCurrentSeason', value: false, label: '当前航季', type: 'radio', choose: [ {value: true, text: '是'}, {value: false, text: '否'} ], rules: null }
        ],
        API: API
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
      commonAddOrUpdate: commonAddOrUpdate,
      commonDelete: commonDelete,
      pagination: Pagination
    },
    methods: {
      // 获取用户列表
      getPlanSeasonNameLs () {
        let para = Object.assign({}, this.filters, this.$refs['page'].queryParam())
        this.loadingList = true
        API.getPlanSeasonNameLs().go(para).then((data) => {
          if (data.ok) {
            this.$refs['page'].set('total', data.attr.data.pager.recordCount)
            this.seasons = data.attr.data.list
          } else {
            this.$notify(Util.notifyBody(false, data.msg))
          }
          this.loadingList = false
        })
      },
      selChange: function (selects) {
        this.selects = selects
      },
      handleDel: function (index, row) {
        this.$refs['delConfirm'].del(row.id)
      },
      handleAdd: function () {
        this.fields[1].choose = this.subAirportLs
        let addFields = Util.deepCopy(this.fields)
        this.$refs['addForm'].show(addFields)
      },
      handleEdit: function (index, row) {
        this.fields[1].choose = this.subAirportLs
        let editfields = Util.deepCopy(this.fields)
        for (let i = 0; i < editfields.length; i++) {
          editfields[i].value = row[editfields[i].name]
        }
        this.$refs['editForm'].show(editfields, row)
      },
      dataFormat: function (row, column, cellValue) {
      // let date = cellValue
      let date = row[column.property]
      if (date === undefined) {
        return ''
      }
      return Util.formatDate.flightDateFmt('yyyy-MM-dd', date, false)
    }
    },
    mounted () {
      // 设置绑定用户管辖运营机场
      this.subAirportLs = Butil.setSubAirportls()
      this.filters.airportCode = this.subAirportLs.length > 0 ? this.subAirportLs[0].value : ''
      this.getPlanSeasonNameLs()
    }
  }
</script>

<style lang="scss">
.seasonNameCss {
  .el-table .cell, .el-table th>div {
      padding-left: 4px!important;
      padding-right: 2px!important;
      box-sizing: border-box;
      text-overflow: ellipsis;
  }
}
</style>
