/*
 * @Author: ylj
 * @Date: 2017-10-17 14:32:14
 * @Last Modified by: ylj
 * @Last Modified time: 2017-12-01 12:03:22
 */
<template>
  <div class="planFlightCss">
    <!--搜索工具栏（整行、置顶）-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <city-name
            ref="city"
            v-on:getAirportName = "airportCodeChange">
          </city-name>
        </el-form-item>
        <el-form-item>
          <el-input v-model="filters.flightNo" placeholder="航班号" :icon="iconClass" @mouseenter.native="inputHovering = true"
            :onIconClick="cleardata" @mouseleave.native="inputHovering = false"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getPlanFlightls">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="handleAdd">新增</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="expPlanFlight">导出Excel</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="impPlanMorFlight">次日航班计划导入</el-button>
        </el-form-item>

        <el-form-item>
          <el-radio-group v-model="directionRadio" @change="setDirFileter">
            <el-radio class="radio" label="A">进港</el-radio>
            <el-radio class="radio" label="D">出港</el-radio>
            <el-radio class="radio" label="">所有</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table v-bind:data="planFlights" highlight-current-row v-loading="loading" @selection-change="selectChange" :height="tableHeight" style="width: 100%;" row-class-name="setrowStyle">
      <el-table-column type="index" label="序号" align="center" width="42">
        <template scope="scope">
          {{pageSize * (pageNumber - 1) + scope.$index + 1}}
        </template>
      </el-table-column>
      <el-table-column prop="opDate" label="执行日期" :formatter="dataFormat" width="83" sortable show-overflow-tooltip ></el-table-column>
      <el-table-column prop="flightTask" label="任务" width="55" sortable>
        <template scope="scope">
          {{$cache.findByName('flighttasks', 'flightTaskCode', scope.row.flightTask, 'abbr2w' )}}
        </template>
      </el-table-column>
      <el-table-column prop="carrier" label="公司" width="55" sortable></el-table-column>
      <el-table-column prop="flightNo" label="航班号" width="70" sortable></el-table-column>
      <el-table-column prop="direction" label="进出" width="55" sortable>
        <template scope="scope" >
          {{$cache.findByName('flightdirections', 'directionCode', scope.row.direction, 'description' )}}
        </template>
      </el-table-column>
      <el-table-column prop="origin" label="起飞站" width="70" sortable show-overflow-tooltip >
        <template scope="scope" >
          {{$cache.findByName('airports', 'icaocode', scope.row.origin, 'cnabbr2w' )}}
        </template>
      </el-table-column>
      <el-table-column prop="destination" label="目的站" width="70" sortable>
        <template scope="scope" >
          {{$cache.findByName('airports', 'icaocode', scope.row.destination, 'cnabbr2w' )}}
        </template>
      </el-table-column>
      <el-table-column prop="registration" label="机号" width="73" sortable></el-table-column>
      <el-table-column prop="aircraftType" label="机型" width="55" sortable></el-table-column>
      <el-table-column prop="flightNature" label="区域" width="55" sortable show-overflow-tooltip >
        <template scope="scope">
          {{$cache.findByName('flightnatures', 'flightNatureCode', scope.row.flightNature, 'description' )}}
        </template>
      </el-table-column>
      <el-table-column prop="stand" label="机位" width="55" sortable></el-table-column>
      <el-table-column prop="boardingStand" label="登机机位" width="83" sortable></el-table-column>
      <el-table-column prop="gate" label="登机口" width="70" sortable></el-table-column>
      <el-table-column prop="scheduleDepartTime" label="计划起飞" :formatter="dataFormat" width="83" sortable></el-table-column>
      <el-table-column prop="scheduleArriveTime" label="计划到达" :formatter="dataFormat" width="83" sortable show-overflow-tooltip ></el-table-column>
      <el-table-column prop="terminal" label="航站楼" width="70" sortable show-overflow-tooltip ></el-table-column>
      <el-table-column prop="generalAgent" label="代理" width="55" sortable>
        <template scope="scope" >
          {{$cache.findByName('generalagents', 'agentCode', scope.row.generalAgent, 'description' )}}
        </template>
      </el-table-column>
      <el-table-column prop="vipTag" label="VIP" width="55" sortable show-overflow-tooltip >
        <template scope="scope" >
          {{$cache.findByName('vipranks', 'rankCode', scope.row.vipTag, 'description' )}}
        </template>
      </el-table-column>
      <el-table-column prop="planSource" label="计划类型" min-width="83" sortable>
        <template scope="scope" >
          {{$cache.findByName('planSource', 'sourceCode', scope.row.planSource, 'description' )}}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="90" header-align="center" fixed="right">
        <template scope="scope">
          <el-button @click="handleEdit(scope.$index, scope.row)" type="text" size="small">编辑</el-button>
          <el-button @click="handleDel(scope.$index, scope.row)" type="text" size="small">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--分页-->
    <pagination :to="getPlanFlightls" ref="page"></pagination>

    <!--删除窗口-->
    <commonDelete
      :to="API.removePlanFlight().go"
      :callback="getPlanFlightls"
      :labelWidth="100"
      ref="planFlightDelForm"></commonDelete>

    <!--新增界面-->
    <add-or-update
      title="新增"
      :to="API.addPlanFlight().go"
      :callback="getPlanFlightls"
      ref="planFlightAddForm"></add-or-update>

    <!--编辑界面-->
    <add-or-update
     title="编辑"
     :to="API.editPlanFlight().go"
     :callback="getPlanFlightls"
     ref="planFlightEditForm"></add-or-update>

    <!--航班计划导出-->
    <common-add-form
      type="add"
      size="tiny"
      title="航班计划EXCEL导出"
      :gutter="5"
      :labelWidth="70"
      :to="params => { return this.PlanFlightDownload(params) }"
      :isDefSubmit="true"
      ref="expPlanFlightForm"></common-add-form>

    <common-add-form
      type="add"
      size="tiny"
      title="次日航班计划导入"
      :gutter="5"
      :labelWidth="70"
      :to="API.importPlanFlight().go"
      :isImpExcUpload="true"
      :isSimpleSubmit="true"
      ref="impPlanMorFlight"></common-add-form>

  </div>
</template>

<script>
import Util from '../../common/js/util'
import Butil from '../../common/js/base-util'
import Rules from '../../common/js/rules'
import Pagination from '../../components/Pagination'
import API from '../../api'
import addOrUpdate from '../fids/planFlightForm/AddOrUpdate'
import commonDelete from './../../components/CommDelete'
import CityName from '../../components/CityName'
import commonAddForm from '../base/baseForm/CommAddOrUpdate'

export default {
  data () {
    return {
      filters: {
        flightNo: '',
        airportCode: '',
        direction: this.directionRadio
      },
      planFlights: [],
      subAirportLs: [],
      loading: false,
      tableHeight: 450,
      selects: [],
      API: API,
      inputHovering: false,
      directionRadio: '',
      titleMap: {
        '承运人': 'carrier',
        '航班号': 'flightNo',
        '机号': 'registration',
        '执行日期': 'opDate',
        '任务': 'flightTask',
        '进出': 'direction',
        '始发地': 'origin',
        '目的地': 'destination',
        '机型': 'aircraftType',
        '区域': 'flightNature',
        '机位': 'stand',
        '航段序号': 'segmentIndex',
        '登机机位': 'boardingStand',
        '登机口': 'gate',
        '计划起飞': 'scheduleDepartTime',
        '计划到达': 'scheduleArriveTime',
        '航站楼': 'terminal',
        '代理': 'generalAgent',
        'VIP': 'vipTag',
        '计划类型': 'planSource'
      }
    }
  },
  computed: {
    pageNumber () {
      return this.$refs['page'].get('pageNumber')
    },
    pageSize () {
      return this.$refs['page'].get('pageSize')
    },
    iconClass () {
      // 计算过滤行输入框样式
      let criteria = this.inputHovering &&
        this.filters.flightNo !== undefined &&
        this.filters.flightNo !== '' &&
        this.filters.flightNo !== null
      return criteria ? 'circle-close is-show-close' : ' '
    },
    expInfofields () {
      let field = [
        {
          id: '0',
          item: [
            { name: 'airportCode', value: '', label: '运营机场', type: 'cityName', filterable: true, multiple: false, clearable: true, rules: null, placeholder: '运营机场', span: 12 },
            { name: 'carrier', value: '', label: '航空公司', type: 'selectSimple', filterable: true, multiple: false, clearable: true, choose: this.$cache.fetch('airlines'), selValue: 'icaocode', selValue2: 'iatacode', selLabel: 'cnname', is2Label: true, rules: null, placeholder: '公司', span: 12 }
          ]
        },
        {
          id: '1',
          item: [
            { name: 'opDate_begin', value: '', label: '开始日期', visitDate: true, visitTime: false, dateStyle: 'width: 100%', timeStyle: 'width: 30%', type: 'dateTimeGroup', allDisable: false, datePlaceholder: '开始日期', timePlaceholder: '时间', formatter: 'yyyy-MM-dd', rules: null, span: 12 },
            { name: 'opDate_end', value: '', label: '结束日期', visitDate: true, visitTime: false, dateStyle: 'width: 100%', timeStyle: 'width: 30%', type: 'dateTimeGroup', allDisable: false, datePlaceholder: '结束日期', timePlaceholder: '时间', formatter: 'yyyy-MM-dd', rules: null, span: 12 }
          ]
        },
        {
          id: '2',
          item: [
            { name: 'planSource', value: '', label: '计划类型', type: 'selectSimple', filterable: true, multiple: false, clearable: true, selValue: 'sourceCode', selLabel: 'description', is2Label: false, choose: this.$cache.fetch('planSource'), rules: null, placeholder: '计划类型', span: 12 },
            { name: 'flightNature', value: '', label: '区域', type: 'selectSimple', filterable: true, multiple: false, clearable: true, choose: this.$cache.fetch('flightnatures'), selValue: 'flightNatureCode', selLabel: 'description', is2Label: false, rules: null, placeholder: '区域', span: 12 }
          ]
        },
        {
          id: '3',
          item: [
            { name: 'direction', value: '', label: '进出', type: 'selectSimple', filterable: true, multiple: false, clearable: true, selValue: 'directionCode', selLabel: 'description', is2Label: false, choose: this.$cache.fetch('flightdirections'), rules: null, placeholder: '进出', span: 12 },
            { name: 'flightTask', value: '', label: '任务', type: 'selectSimple', filterable: true, multiple: false, clearable: true, selValue: 'flightTaskCode', selLabel: 'abbr2w', is2Label: false, choose: this.$cache.fetch('flighttasks'), rules: null, placeholder: '任务', span: 12 }
          ]
        }
      ]
      return field
    },
    impFields () {
      let field = [
        {
          id: '0',
          item: [
            { name: 'airportCode', value: '', label: '运营机场', type: 'cityName', filterable: true, multiple: false, clearable: true, rules: null, placeholder: '运营机场', span: 12 },
            { name: 'carrier', value: '', label: '航空公司', type: 'selectSimple', filterable: true, multiple: false, clearable: true, choose: this.$cache.fetch('airlines'), selValue: 'icaocode', selValue2: 'iatacode', selLabel: 'cnname', is2Label: true, rules: null, placeholder: '公司', span: 12 }
          ]
        },
        {
          id: '1',
          item: [
            { name: 'planSource', value: '', label: '计划类型', type: 'selectSimple', filterable: true, multiple: false, clearable: true, selValue: 'sourceCode', selLabel: 'description', is2Label: false, choose: this.$cache.fetch('planSource'), rules: null, placeholder: '计划类型', span: 12 },
            { name: 'opDate', value: '', label: '执行日期', visitDate: true, visitTime: false, dateStyle: 'width: 100%', timeStyle: 'width: 30%', type: 'dateTimeGroup', allDisable: false, datePlaceholder: '开始日期', timePlaceholder: '时间', formatter: 'yyyy-MM-dd', rules: null, span: 12 }
          ]
        },
        {
          id: '2',
          item: [
            { name: 'fileList', value: '', label: '文件上传', type: 'excelUpload', action: '', autoUpload: false, multiple: false, fileType: '.xls,.xlsx', previewSize: 'large', rules: [ { validator: Rules.arrayRequire, message: '请选择上传文件', trigger: 'blur' } ], span: 24 }
          ]
        }
      ]
      return field
    }
  },
  watch: {
    '$store.state.cached' (val, oldVal) {
      // 重新刷新数据
      this.getPlanFlightls()
    }
  },
  components: {
    pagination: Pagination,
    addOrUpdate: addOrUpdate,
    commonDelete: commonDelete,
    CityName: CityName,
    commonAddForm: commonAddForm
  },
  methods: {
    bindData () {
      // this.$refs['page'].set('pageSize', 40)
      this.subAirportLs = Butil.getSubscribeAirports()
      this.setAirportInfo(this.subAirportLs)
      this.filters.airportCode = this.$refs['city'].airportCode
      this.getPlanFlightls()
    },
    getPlanFlightls () {
      let para = Object.assign({}, this.filters, this.$refs['page'].queryParam())
      this.loading = true
      API.getPlanFlightls().go(para).then((data) => {
        if (data.ok) {
          this.$refs['page'].set('total', data.attr.data.pager.recordCount)
          this.planFlights = data.attr.data.list
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
      this.$refs['planFlightAddForm'].show(null, this.filters.airportCode)
    },
    handleEdit: function (index, row) {
      this.$refs['planFlightEditForm'].show(row, this.filters.airportCode)
    },
    handleDel: function (index, row) {
      this.$refs['planFlightDelForm'].del(row)
    },
    dataFormat: function (row, column, cellValue) {
      // 格式化数据列表中日期格式
      let date = row[column.property]
      if (date === undefined || date === null) {
        return ''
      }
      let field = column.property
      switch (field) {
        case 'opDate': return Util.formatDate.flightDateFmt('yyyy-MM-dd', date, false)
        case 'scheduleDepartTime': return Util.formatDate.flightDateFmt('hhmm', date, true, row.opDate)
        case 'scheduleArriveTime': return Util.formatDate.flightDateFmt('hhmm', date, true, row.opDate)
        default: break
      }
    },
    cleardata: function () {
      // 清除输入框内容
      this.filters.flightNo = ''
    },
    setDirFileter: function (val) {
      // 根据进出港Radio筛选数据
      this.filters['direction'] = val
      if (val === 'Z') {
        this.filters.direction = ''
      }
      this.getPlanFlightls()
    },
    setAirportInfo (subAirportLs) {
      this.$refs['city'].setCitys(subAirportLs)
    },
    airportCodeChange: function (airportCode) {
      this.filters.airportCode = airportCode
      this.getPlanFlightls()
    },
    expPlanFlight () {
      // EXCEL导出航班计划
      let field = Util.deepCopy(this.expInfofields)
      this.$refs['expPlanFlightForm'].setAirportCodeLs(this.subAirportLs)
      this.$refs['expPlanFlightForm'].show(field, true, this.filters.airportCode)
    },
    PlanFlightDownload (param) {
      let url = 'http://localhost:9090/raiis/plan/download?'
      for (let key in param) {
        if (param[key] !== '' && param[key] !== null) {
          let val = (key === 'opDate_begin' || key === 'opDate_end') ? param[key].substring(0, 10) : param[key]
          url += key + '=' + val + '&'
        }
      }
      url = url.substring(0, url.length - 1)
      window.location.href = url
      return true
    },
    impPlanMorFlight () {
      // 次日航班计划EXCEL导入
      let field = Util.deepCopy(this.impFields)
      this.$refs['impPlanMorFlight'].setTitleInfo(this.titleMap, 19)
      this.$refs['impPlanMorFlight'].setAirportCodeLs(this.subAirportLs)
      this.$refs['impPlanMorFlight'].show(field, true, this.filters.airportCode)
    }
  },
  mounted () {
    this.bindData()
  }
}
</script>
<style lang="scss">
.planFlightCss {
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

