/*
 * @Author: ylj
 * @Date: 2017-10-22 17:49:48
 * @Last Modified by: ylj
 * @Last Modified time: 2017-12-01 15:23:57
 */
<template>
  <div class="planSeaonCss">
    <!--搜索工具栏（整行、置顶）-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item style="width: 13%">
          <city-name
            ref="city"
            v-on:getAirportName = "airportCodeChange">
          </city-name>
        </el-form-item>
        <el-form-item style="width: 13%">
          <el-select v-model="filters.seasonId" placeholder="航 季" @change="getSeasonSchedulels" :filterable="true" clearable>
            <el-option
                v-for="item in seasonList"
                :key="item.id"
                :label="item.seasonName"
                :value="item.id">
              </el-option>
          </el-select>
        </el-form-item>
        <el-form-item style="width: 13%">
          <el-select v-model="filters.planSource" placeholder="计划类型" @change="getSeasonSchedulels" :filterable="true" clearable>
            <el-option
              v-for="item in $cache.fetch('planSource')"
              :key="item.sourceCode"
              :label="item.description"
              :value="item.sourceCode">
              <span style="float: left">{{ item.description }}</span>
              <span style="float: right">{{ item.sourceCode }}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item style="width: 13%">
          <el-input v-model="filters.flightNo" placeholder="航班号" :icon="iconClass" @mouseenter.native="inputHovering = true"
            :onIconClick="cleardata" @mouseleave.native="inputHovering = false"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getSeasonSchedulels">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="handleAdd">新增</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="genMorrowFlight"><i class="el-icon-edit el-icon--left"></i>次日航班生成</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="importExcelPlanSea"><i class="el-icon-upload el-icon--left"></i>导入</el-button>
        </el-form-item>

        <el-form-item>
          <el-radio-group v-model="filters.direction" @change="getSeasonSchedulels">
            <el-radio class="radio" label="A">进港</el-radio>
            <el-radio class="radio" label="D">出港</el-radio>
            <el-radio class="radio" label="">所有</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table v-bind:data="seaschedules" highlight-current-row v-loading="loading" @selection-change="selectChange" :height="tableHeight" style="width: 100%;" :row-class-name="setrowStyle">
      <el-table-column type="index" label="序号" align="center" width="42">
        <template scope="scope">
          {{pageSize * (pageNumber - 1) + scope.$index + 1}}
        </template>
      </el-table-column>
      <el-table-column prop="flightTask" label="任务" width="55" sortable>
        <template scope="scope">
          {{$cache.findByName('flighttasks', 'flightTaskCode', scope.row.flightTask, 'abbr2w' )}}
        </template>
      </el-table-column>
      <el-table-column prop="carrier" label="公司" width="55" sortable  show-overflow-tooltip></el-table-column>
      <el-table-column prop="flightNo" label="航班号" width="70" sortable></el-table-column>

      <el-table-column prop="strategyType" label="单位" width="60" sortable>
        <template scope="scope">
          {{scope.row.strategyType === 1 ? '周' : (scope.row.strategyType === 2 ? '月' : '日')}}
        </template>
      </el-table-column>
      <el-table-column prop="strategy" label="周期" width="80" sortable  show-overflow-tooltip></el-table-column>

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
      <el-table-column prop="destination" label="目的站" width="70" sortable show-overflow-tooltip>
        <template scope="scope" >
          {{$cache.findByName('airports', 'icaocode', scope.row.destination, 'cnabbr2w' )}}
        </template>
      </el-table-column>
      <el-table-column prop="aircraftType" label="机型" width="55" sortable></el-table-column>
      <el-table-column prop="flightNature" label="区域" width="55" sortable show-overflow-tooltip >
        <template scope="scope">
          {{$cache.findByName('flightnatures', 'flightNatureCode', scope.row.flightNature, 'description' )}}
        </template>
      </el-table-column>
      <el-table-column prop="scheduleDepartTime" label="计划起飞" :formatter="dataFormat" width="83" sortable></el-table-column>
      <el-table-column prop="depOffset" label="起飞偏移量" align="center" width="100" sortable></el-table-column>
      <el-table-column prop="scheduleArriveTime" label="计划到达" :formatter="dataFormat" width="83" sortable show-overflow-tooltip ></el-table-column>
      <el-table-column prop="arrOffset" label="到达偏移量" align="center" width="100" sortable></el-table-column>
      <el-table-column prop="beginDate" label="开始日期" :formatter="dataFormat" width="83" sortable></el-table-column>
      <el-table-column prop="endDate" label="结束日期" :formatter="dataFormat" width="83" sortable show-overflow-tooltip ></el-table-column>
      <el-table-column prop="generalAgent" label="代理" width="55" sortable>
        <template scope="scope" >
          {{$cache.findByName('generalagents', 'agentCode', scope.row.generalAgent, 'description' )}}
        </template>
      </el-table-column>

      <el-table-column prop="planSource" label="计划类型" width="83" sortable>
        <template scope="scope" >
          {{$cache.findByName('planSource', 'sourceCode', scope.row.planSource, 'description' )}}
        </template>
      </el-table-column>
      <el-table-column prop="flightFlag" label="取消" min-width="55" sortable>
        <template scope="scope" >
          {{ scope.row.generalAgent === '3' ? '取消' : null }}
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
    <pagination :to="getSeasonSchedulels" ref="page"></pagination>

    <!--删除窗口-->
    <commonDelete
      :to="API.removeSeasonSchedule().go"
      :callback="getSeasonSchedulels"
      :labelWidth="100"
      ref="seascheduleDelForm"></commonDelete>

    <!--新增界面-->
    <add-or-update
      title="新增"
      :to="API.addSeasonSchedule().go"
      :callback="getSeasonSchedulels"
      ref="seascheduleAddForm"></add-or-update>

    <!--编辑界面-->
    <add-or-update
     title="编辑"
     :to="API.editSeasonSchedule().go"
     :callback="getSeasonSchedulels"
     ref="seascheduleEditForm"></add-or-update>

    <imp-excel-plan-sea
     title="航季导入"
     :to="API.importSeaSchedule().go"
     :callback="getSeasonSchedulels"
     ref="impExcelSeaForm"></imp-excel-plan-sea>

    <gen-morrow-flight
     title="次日航班生成"
     :to="API.genMorrowFlight().go"
     ref="genMorrowFlightForm"></gen-morrow-flight>

  </div>
</template>

<script>
import Util from '../../common/js/util'
import Butil from '../../common/js/base-util'
import Pagination from '../../components/Pagination'
import API from '../../api'
import addOrUpdate from '../fids/planSeasonschedule/AddOrUpdate'
import commonDelete from './../../components/CommDelete'
import CityName from '../../components/CityName'
import impExcel from '../../common/js/importExcel'
import impExcelPlanSea from '../fids/planSeasonschedule/impExcelPlanSea'
import genMorrowFlight from '../fids/planSeasonschedule/generateFlight'

export default {
  data () {
    return {
      filters: {
        seasonId: '',
        planSource: '',
        flightNo: '',
        airportCode: '',
        direction: ''
      },
      seaschedules: [],
      subAirportLs: [],
      loading: false,
      tableHeight: 450,
      selects: [],
      API: API,
      inputHovering: false,
      seasonList: [],
      impExcel: impExcel
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
    }
  },
  watch: {
    '$store.state.cached' (val, oldVal) {
      // 重新刷新数据
      this.getSeasonSchedulels()
    }
  },
  components: {
    pagination: Pagination,
    addOrUpdate: addOrUpdate,
    commonDelete: commonDelete,
    CityName: CityName,
    impExcelPlanSea: impExcelPlanSea,
    genMorrowFlight: genMorrowFlight
  },
  methods: {
    bindData () {
      this.subAirportLs = Butil.getSubscribeAirports()
      this.setAirportInfo(this.subAirportLs)
      this.filters.airportCode = this.$refs['city'].airportCode
      this.seasonList = this.setSeasonList(this.filters.airportCode)
      this.getSeasonSchedulels()
    },
    setSeasonList (airportCode) {
      let list = this.$cache.fetch('seasonName').filter(item => {
        return item.airportCode === airportCode
      })
      return list
    },
    getSeasonSchedulels () {
      let para = Object.assign({}, this.filters, this.$refs['page'].queryParam())
      this.loading = true
      API.getSeasonSchedulels().go(para).then((data) => {
        if (data.ok) {
          this.$refs['page'].set('total', data.attr.data.pager.recordCount)
          this.seaschedules = data.attr.data.list
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
      this.$refs['seascheduleAddForm'].show(null, this.filters.airportCode)
    },
    handleEdit: function (index, row) {
      this.$refs['seascheduleEditForm'].show(row, this.filters.airportCode)
    },
    handleDel: function (index, row) {
      this.$refs['seascheduleDelForm'].del(row)
    },
    dataFormat: function (row, column, cellValue) {
      // 格式化数据列表中日期格式
      let date = row[column.property]
      if (date === undefined) {
        return ''
      }
      let field = column.property
      switch (field) {
        case 'beginDate': return Util.formatDate.flightDateFmt('yyyy-MM-dd', date, true, row.opDate)
        case 'endDate': return Util.formatDate.flightDateFmt('yyyy-MM-dd', date, true, row.opDate)
        case 'scheduleDepartTime': return Util.formatDate.flightDateFmt('hhmm', date, true, row.opDate)
        case 'scheduleArriveTime': return Util.formatDate.flightDateFmt('hhmm', date, true, row.opDate)
        default: break
      }
    },
    cleardata: function () {
      // 清除输入框内容
      this.filters.flightNo = ''
    },
    setAirportInfo (subAirportLs) {
      this.$refs['city'].setCitys(subAirportLs)
    },
    airportCodeChange: function (airportCode) {
      this.filters.airportCode = airportCode
      this.seasonList = this.setSeasonList(airportCode)
      this.getSeasonSchedulels()
    },
    importExcelPlanSea: function () {
      let seadonInfo = {airportCode: this.filters.airportCode, airportCode4: null, planSource: '', seasonId: '', strategyType: 1, fileList: []}
      this.$refs['impExcelSeaForm'].show(seadonInfo, this.filters.airportCode)
    },
    setrowStyle (row, rowIndex) {
      if (row.flightFlag === '3') {
        return 'cancel-rowdata'
      } else {
        return 'normal-rowdata'
      }
    },
    genMorrowFlight () {
      let seasonInfo = { airportCode: this.filters.airportCode, seasonId: this.filters.seasonId }
      this.$refs['genMorrowFlightForm'].show(seasonInfo)
    }
  },
  mounted () {
    this.bindData()
  }
}
</script>
<style lang="scss">
.planSeaonCss {
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

  .el-table .normal-rowdata {
    background: #fff;
  }

  .el-table .cancel-rowdata {
    background: #f0f9eb;
  }
}
</style>

