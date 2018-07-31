/*
 * @Author: llf
 * @Date: 2017-06-08 09:22:17
 * @Last Modified by: ylj
 * @Last Modified time: 2017-11-29 14:20:34
 * @Description: 航班动态
 */
<template>
  <div class="dynamicFlightCss">
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
          <el-button type="primary" v-on:click="getDynamicFlight">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="handleAdd">新增</el-button>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" v-on:click="handleAddAlt">新增备降</el-button>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" v-on:click="exportFlightExcel">导出Excel</el-button>
        </el-form-item>

        <el-form-item>
          <el-radio-group v-model="filters.direction" @change="setDirFileter">
            <el-radio class="radio" label="A">进港</el-radio>
            <el-radio class="radio" label="D">出港</el-radio>
            <el-radio class="radio" label="">所有</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table v-bind:data="flights" highlight-current-row v-loading="flightsLoading" :element-loading-text="loadingText" @selection-change="selectChange" @filter-change="filterChange" :height="tableHeight" style="width: 100%;" row-class-name="setrowStyle">
      <el-table-column type="index" label="序号" width="42" align="center">
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
      <el-table-column prop="flightStatus" label="状态" width="55" sortable show-overflow-tooltip >
        <template scope="scope">
          {{$cache.findByName('flightstatus', 'statusCode', scope.row.flightStatus, 'description' )}}
        </template>
      </el-table-column>
      <el-table-column prop="irregularStatus" label="异常" width="55" sortable>
        <template scope="scope">
          {{$cache.findByName('flightstatus', 'statusCode', scope.row.irregularStatus, 'description' )}}
        </template>
      </el-table-column>
      <el-table-column prop="flightNature" label="区域" width="55" sortable show-overflow-tooltip >
        <template scope="scope">
          {{$cache.findByName('flightnatures', 'flightNatureCode', scope.row.flightNature, 'description' )}}
        </template>
      </el-table-column>
      <el-table-column prop="stand" label="机位" width="55" sortable></el-table-column>
      <el-table-column prop="gate" label="登机口" width="70" sortable></el-table-column>
      <el-table-column prop="scheduleDepartTime" label="计划起飞" :formatter="dataFormat" width="83" sortable></el-table-column>
      <el-table-column prop="estimateDepartTime" label="预计起飞" :formatter="dataFormat" width="83" sortable show-overflow-tooltip ></el-table-column>
      <el-table-column prop="actualDepartTime" label="实际起飞" :formatter="dataFormat" width="83" sortable></el-table-column>
      <el-table-column prop="scheduleArriveTime" label="计划到达" :formatter="dataFormat" width="83" sortable show-overflow-tooltip ></el-table-column>
      <el-table-column prop="estimateArriveTime" label="预计到达" :formatter="dataFormat" width="83" sortable></el-table-column>
      <el-table-column prop="actualArriveTime" label="实际到达" :formatter="dataFormat" width="83" sortable show-overflow-tooltip ></el-table-column>
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
      <el-table-column label="操作" width="275" header-align="center" fixed="right">
        <template scope="scope">
          <el-button @click="resConfig(scope.$index, scope.row)" type="text" size="small">资源配置</el-button>
          <!--<el-button @click="checkin(scope.$index, scope.row)" type="text" size="small">值机安排</el-button>-->
          <el-button @click="flightAheadDep(scope.$index, scope.row)" v-if="scope.row.direction === 'A'" type="text" size="small">前方起飞</el-button>
          <el-button @click="flightArrCur(scope.$index, scope.row)" v-if="scope.row.direction === 'A'" type="text" size="small">本场到达</el-button>
          <el-button @click="flightDep(scope.$index, scope.row)" v-if="scope.row.direction === 'D'" type="text" size="small">航班起飞</el-button>
          <el-dropdown @command="handleCommand" v-if="scope.row.direction === 'D'">
            <el-button size="small" > 登机 <i class="el-icon-caret-bottom el-icon--right"></i></el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="startBoarding" :index="scope.$index" :row="scope.row"><span><i class="el-icon-edit"></i> 本场 </span></el-dropdown-item>
              <el-dropdown-item command="passBoarding" :index="scope.$index" :row="scope.row"><span><i class="el-icon-edit"></i> 过站 </span></el-dropdown-item>
              <el-dropdown-item command="urgingBoarding" :index="scope.$index" :row="scope.row"><span><i class="el-icon-edit"></i> 催促 </span></el-dropdown-item>
              <el-dropdown-item command="endBoarding" :index="scope.$index" :row="scope.row"><span><i class="el-icon-edit"></i> 结束 </span></el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
          <el-dropdown @command="handleCommand">
            <el-button size="small" >更多操作<i class="el-icon-caret-bottom el-icon--right"></i></el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="handleEdit" :index="scope.$index" :row="scope.row"><span><i class="el-icon-edit"></i> 修改航班 </span></el-dropdown-item>
              <el-dropdown-item command="flightArrNext" v-if="scope.row.direction === 'D'" :index="scope.$index" :row="scope.row"><span><i class="el-icon-edit"></i> 到达下站 </span></el-dropdown-item>

              <el-dropdown-item command="flightCnl" :index="scope.$index" :row="scope.row"><span><i class="el-icon-edit"></i> 航班取消 </span></el-dropdown-item>
              <el-dropdown-item command="flightDly" :index="scope.$index" :row="scope.row"><span><i class="el-icon-edit"></i> 航班延误 </span></el-dropdown-item>
              <el-dropdown-item command="flightAbnormalRecy" :index="scope.$index" :row="scope.row"><span><i class="el-icon-edit"></i> 异常恢复 </span></el-dropdown-item>
              <el-dropdown-item command="flightAircraftChange" :index="scope.$index" :row="scope.row"><span><i class="el-icon-edit"></i> 更换飞机 </span></el-dropdown-item>

              <el-dropdown-item command="flightArrAlt" v-if="scope.row.direction === 'A'" :index="scope.$index" :row="scope.row"><span><i class="el-icon-edit"></i> 进港备降 </span></el-dropdown-item>
              <el-dropdown-item command="flightArrRtn" v-if="scope.row.direction === 'A'" :index="scope.$index" :row="scope.row"><span><i class="el-icon-edit"></i> 进港返航 </span></el-dropdown-item>
              <el-dropdown-item command="flightDepRtn" v-if="scope.row.direction === 'D'" :index="scope.$index" :row="scope.row"><span><i class="el-icon-edit"></i> 出港返航 </span></el-dropdown-item>
              <el-dropdown-item command="checkInFlight" v-if="scope.row.direction === 'D'" :index="scope.$index" :row="scope.row"><span><i class="el-icon-edit"></i> 值机开始 </span></el-dropdown-item>
              <el-dropdown-item command="checkOffFlight" v-if="scope.row.direction === 'D'" :index="scope.$index" :row="scope.row"><span><i class="el-icon-edit"></i> 值机关闭 </span></el-dropdown-item>
              <el-dropdown-item command="irrUnderPassenger" v-if="scope.row.direction === 'D'" :index="scope.$index" :row="scope.row"><span><i class="el-icon-edit"></i> 异常下客 </span></el-dropdown-item>
              <el-dropdown-item command="handleDel" :index="scope.$index" :row="scope.row"><span><i class="el-icon-edit"></i> 航班删除 </span></el-dropdown-item>
              <el-dropdown-item command="VIPManagement" :index="scope.$index" :row="scope.row"><span><i class="el-icon-edit"></i> VIP管理 </span></el-dropdown-item>
              <el-dropdown-item command="flightLoadOpt" :index="scope.$index" :row="scope.row"><span><i class="el-icon-edit"></i> 货邮编辑 </span></el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </template>
      </el-table-column>
    </el-table>

    <!--分页-->
    <pagination :to="getDynamicFlight" ref="page"></pagination>

    <!--删除窗口-->
    <commonDelete
      :to="API.removeDynamicFlight().go"
      :callback="getDynamicFlight"
      :labelWidth="100"
      ref="dynamicDelForm"></commonDelete>

    <!--新增界面-->
    <add-or-update
      :title="dialogaTitle"
      :to="API.addDynamicFlight().go"
      :callback="getDynamicFlight"
      ref="dynamicAddForm"></add-or-update>

    <!--编辑界面-->
    <add-or-update
     :title="dialogaTitle"
     :to="API.editDynamicFlight().go"
     :callback="getDynamicFlight"
     ref="dynamicEditForm"></add-or-update>

    <!--确认界面-->
    <flight-confirm
     :title="confirmTitle"
     :to="params => { return API.operateFlightData(params.operate, params.optName).go(params.para) }"
     :callback="getDynamicFlight"
     ref="dynamicConfirmForm"></flight-confirm>

    <res-config
     title="资源配置"
     :to="API.editCarouselRes().go"
     :callback="getDynamicFlight"
     ref="resConfigForm"></res-config>

    <vip-config
      ref="vipConfigDialog">
    </vip-config>

    <!--货邮编辑界面-->
    <flight-load-form
      type="add"
      title="货邮编辑"
      :to="API.addFlightLoad().go"
      :labelWidth="100"
      ref="flightLoadForm"></flight-load-form>

    <common-add-form
      type="add"
      size="tiny"
      title="航班动态EXCEL导出"
      :gutter="5"
      :labelWidth="70"
      :to="params => { return this.flightScheduleDownload(params) }"
      :isDefSubmit="true"
      ref="expFlightForm"></common-add-form>
  </div>
</template>

<script>
import Util from '../../common/js/util'
import Butil from '../../common/js/base-util'
import Pagination from '../../components/Pagination'
import API from '../../api'
import addOrUpdate from '../fids/dynamicFlightForm/AddOrUpdate'
import commonDelete from './../../components/CommDelete'
import FlightConfirm from '../fids/dynamicFlightForm/FlightConfirm'
import resConfig from '../fids/dynamicFlightForm/resConfig'
import vipConfig from './VipForm/vip'
import CityName from '../../components/CityName'
import flightLoadForm from './flightLoadForm/FlightLoadAddMain'
import commonAddForm from '../base/baseForm/CommAddOrUpdate'
// import Vue from 'vue'

export default {
  data () {
    return {
      filters: {
        flight: '',
        flightNo: '',
        airportCode: '',
        direction: ''
      },
      flights: [],
      subAirportLs: [],
      flightsLoading: false,
      loadingText: '数据加载中...',
      tableHeight: 450,
      selects: [],
      API: API,
      inputHovering: false,
      dialogaTitle: '',
      confirmTitle: '',
      isFirstLoading: true,
      isWatch: false
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
            { name: 'flightNature', value: '', label: '区域', type: 'selectSimple', filterable: true, multiple: false, clearable: true, choose: this.$cache.fetch('flightnatures'), selValue: 'flightNatureCode', selLabel: 'description', is2Label: false, rules: null, placeholder: '区域', span: 12 },
            { name: 'direction', value: '', label: '进出', type: 'selectSimple', filterable: true, multiple: false, clearable: true, selValue: 'directionCode', selLabel: 'description', is2Label: false, choose: this.$cache.fetch('flightdirections'), rules: null, placeholder: '进出', span: 12 }
          ]
        },
        {
          id: '3',
          item: [
            { name: 'flightTask', value: '', label: '任务', type: 'selectSimple', filterable: true, multiple: false, clearable: true, selValue: 'flightTaskCode', selLabel: 'abbr2w', is2Label: false, choose: this.$cache.fetch('flighttasks'), rules: null, placeholder: '任务', span: 12 }
          ]
        }
      ]
      return field
    }
  },
  watch: {
    '$store.state.cached' (val, oldVal) {
      // 重新刷新数据
      // this.getDynamicFlight()
      this.isWatch = true
      this.bindData()
    }
  },
  components: {
    pagination: Pagination,
    addOrUpdate: addOrUpdate,
    commonDelete: commonDelete,
    flightConfirm: FlightConfirm,
    resConfig: resConfig,
    vipConfig: vipConfig,
    CityName: CityName,
    flightLoadForm: flightLoadForm,
    commonAddForm: commonAddForm
  },
  methods: {
    bindData () {
      this.$refs['page'].set('pageSize', 40)
      this.subAirportLs = Butil.getSubscribeAirports()
      this.setAirportInfo(this.subAirportLs)
      this.filters.airportCode = this.$refs['city'].airportCode
      this.getDynamicFlight()
    },
    isUserOldCache () {
      if (!this.$cache.isUserOldCache()) {
        return
      } else {
        this.loadingData()
      }
    },
    getDynamicFlight () {
      // 1. 判断是否为初始加载数据，是等待缓存加载完毕再加载数据
      if (this.isFirstLoading && !this.isWatch) {
        setTimeout(this.isUserOldCache, 5)
      } else {
        // 2. 加载数据
        this.loadingData()
      }
    },
    loadingData () {
      let para = Object.assign({}, this.filters, this.$refs['page'].queryParam())
      this.flightsLoading = true
      // let loading = Vue.prototype.$loading({text: '玩命加载中...'})
      API.getDynamicFlight().go(para).then((data) => {
        if (data.ok) {
          this.$refs['page'].set('total', data.attr.data.pager.recordCount)
          // this.$refs['page'].set('pageSize', data.attr.data.pager.recordCount)
          this.flights = data.attr.data.list
        } else {
          this.$notify(Util.notifyBody(false, data.msg))
        }
        this.flightsLoading = false
        // loading.close()
        this.isFirstLoading = false
        this.isWatch = false
      })
    },
    selectChange: function (select) {
      this.selects = select
    },
    filterChange: function (filters) {
    },
    handleAdd: function () {
      this.dialogaTitle = '新增'
      this.$refs['dynamicAddForm'].operate = 'Add'
      this.$refs['dynamicAddForm'].editAble = false
      this.$refs['dynamicAddForm'].isShowIrrInfo = false
      this.$refs['dynamicAddForm'].setIsDisableTime(false)
      this.$refs['dynamicAddForm'].isDiglog = true
      this.$refs['dynamicAddForm'].show(null, this.filters.airportCode)
    },
    handleEdit: function (index, row) {
      this.dialogaTitle = '修改航班'
      this.$refs['dynamicEditForm'].operate = 'Edeit'
      this.$refs['dynamicEditForm'].editAble = false
      this.$refs['dynamicEditForm'].isShowIrrInfo = false
      this.$refs['dynamicEditForm'].setIsDisableTime(false)
      this.$refs['dynamicEditForm'].isDiglog = true
      this.$refs['dynamicEditForm'].show(row)
    },
    handleDel: function (index, row) {
      this.$refs['dynamicDelForm'].del(row)
    },
    dataFormat: function (row, column, cellValue) {
      // let date = cellValue
      let date = row[column.property]
      if (date === undefined) {
        return ''
      }
      let field = column.property
      switch (field) {
        case 'opDate': return Util.formatDate.flightDateFmt('yyyy-MM-dd', date, false)
        case 'scheduleDepartTime': return Util.formatDate.flightDateFmt('hhmm', date, true, row.opDate)
        case 'estimateDepartTime': return Util.formatDate.flightDateFmt('hhmm', date, true, row.opDate)
        case 'actualDepartTime': return Util.formatDate.flightDateFmt('hhmm', date, true, row.opDate)
        case 'scheduleArriveTime': return Util.formatDate.flightDateFmt('hhmm', date, true, row.opDate)
        case 'estimateArriveTime': return Util.formatDate.flightDateFmt('hhmm', date, true, row.opDate)
        case 'actualArriveTime': return Util.formatDate.flightDateFmt('hhmm', date, true, row.opDate)
        default: break
      }
      // return API.dateFmt('YYYY-MM-dd', date)
    },
    handleCommand: function (command, self) {
      var node = self.$vnode.data.attrs
      /* eslint-disable */
      eval('this.' + command).call(this, node.index, node.row)
      /* eslint-disable */
    },
    showConfirmForm: function (row, opt, optName, para) {
      let params = {operate: opt, optName: optName, para: para}
      // 0829
      this.$refs['dynamicConfirmForm'].editAble = true
      this.$refs['dynamicConfirmForm'].show(row, params)
      this.confirmTitle = optName
    },
    flightDep: function (index, row) {
      // 航班起飞
      let flightList = []
      let oldFlightList = []
      oldFlightList[0] = Util.deepCopy(row)
      flightList[0] = Util.deepCopy(row)
      flightList[0].flightStatus = 'DEP'
      flightList[0].actualDepartTime = Util.formatDate.format(new Date(), 'yyyy-MM-dd hh:mm:ss')
      let opt = {operate: 'flightDep', optName: '航班起飞'}
      let para = { newValue: flightList, oldValue: oldFlightList }
      para = Object.assign({}, para, opt)
      // 0827 确认框
      this.showConfirmForm(row, 'flightDep', '航班起飞', para)
      // this.dealFlight('flightDep', '航班起飞', para)
    },
    flightAheadDep: function (index, row) {
      // 前方起飞
      let flightObj = []
      let flightLists = []
      let oldFlights = []
      oldFlights[0] = Util.deepCopy(row)
      // flightObj = Util.deepCopy(row)
      flightObj = Util.deepCopy(row)
      // 更新航班状态、实际起飞、预计到达
      flightObj.flightStatus = 'ENR'
      flightObj.actualDepartTime = Util.formatDate.format(new Date(), 'yyyy-MM-dd hh:mm:ss')
      let flightTime = parseInt(new Date(row.scheduleArriveTime) - new Date(row.scheduleDepartTime))
      flightObj.estimateArriveTime = Util.formatDate.format(new Date((new Date(flightObj.actualDepartTime)).getTime() + flightTime), 'yyyy-MM-dd hh:mm:ss')
      flightLists[0] = Util.deepCopy(flightObj)
      // 更新关联航班预计起飞时间
      if (row.linkedFlightId !== null) {
        let linkFlightObj = []
        linkFlightObj = this.flights.filter(item => {
          return item.id === row.linkedFlightId
        })
        oldFlights[1] = Util.deepCopy(linkFlightObj)
        // flightLists[1] = Util.deepCopy(oldFlights[1])
        flightLists[1] = linkFlightObj
        flightLists[1].estimateDepartTime = flightObj.estimateArriveTime
      }
      let para = { newValue: flightLists, oldValue: oldFlights }
      let opt = {operate: 'flightAheadDep', optName: '前方起飞'}
      para = Object.assign({}, para, opt)
      this.showConfirmForm(row, 'flightAheadDep', '前方起飞', para)
      // this.dealFlight('flightAheadDep', '前方起飞', para)
    },
    flightArrCur: function (index, row) {
      // 本场到达
      let flightList = []
      let oldFlightList = []
      oldFlightList[0] = Util.deepCopy(row)
      flightList[0] = Util.deepCopy(row)
      flightList[0].flightStatus = 'ARR'
      flightList[0].actualArriveTime = Util.formatDate.format(new Date(), 'yyyy-MM-dd hh:mm:ss')
      let para = { newValue: flightList, oldValue: oldFlightList }
      let opt = {operate: 'flightArrCur', optName: '本场到达'}
      para = Object.assign({}, para, opt)
      this.showConfirmForm(row, 'flightArrCur', '本场到达', para)
      // this.dealFlight('flightArrCur', '本场到达', para)
    },
    flightArrNext: function (index, row) {
      // 到达下站
      let flightList = []
      let oldFlightList = []
      oldFlightList[0] = Util.deepCopy(row)
      flightList[0] = Util.deepCopy(row)
      flightList[0].actualArriveTime = Util.formatDate.format(new Date(), 'yyyy-MM-dd hh:mm:ss')
      let para = { newValue: flightList, oldValue: oldFlightList }
      let opt = {operate: 'flightArrNext', optName: '到达下站'}
      para = Object.assign({}, para, opt)
      this.showConfirmForm(row, 'flightArrNext', '到达下站', para)
      // this.dealFlight('flightArrNext', '到达下站', para)
    },
    flightCnl: function (index, row) {
      // 航班取消操作窗口显示
      this.dialogaTitle = '航班取消'
      this.$refs['dynamicEditForm'].operate = 'flightCnl'
      this.$refs['dynamicEditForm'].optName = '航班取消'
      this.$refs['dynamicEditForm'].editAble = true
      this.$refs['dynamicEditForm'].isShowIrrInfo = true
      this.$refs['dynamicEditForm'].setIsDisableTime(true)
      this.$refs['dynamicEditForm'].isDiglog = true
      this.$refs['dynamicEditForm'].show(row)
    },
    flightDly: function (index, row) {
      // 航班延误操作窗口显示
      this.dialogaTitle = '航班延误'
      this.$refs['dynamicEditForm'].operate = 'flightDly'
      this.$refs['dynamicEditForm'].optName = '航班延误'
      this.$refs['dynamicEditForm'].editAble = true
      this.$refs['dynamicEditForm'].isShowIrrInfo = true
      // 设置航班延误界面显示预计时间控件，其他时间控件不显示
      this.$refs['dynamicEditForm'].setIsDisableTime(true)
      this.$refs['dynamicEditForm'].fieldAble.estimateDepartDate = false
      this.$refs['dynamicEditForm'].fieldAble.estimateDepTime = false
      this.$refs['dynamicEditForm'].isDiglog = true
      this.$refs['dynamicEditForm'].show(row)
    },
    flightAbnormalRecy: function (index, row) {
      // 异常恢复 具体操作未实现
      let flightList = []
      let oldFlightList = []
      oldFlightList[0] = Util.deepCopy(row)
      flightList[0] = Util.deepCopy(row)
      flightList[0].irregularstatus = null
      let para = { newValue: flightList, oldValue: oldFlightList }
      let opt = {operate: 'flightAbnormalRecy', optName: '异常恢复'}
      para = Object.assign({}, para, opt)
      this.showConfirmForm(row, 'flightAbnormalRecy', '异常恢复', para)
      // this.dealFlight('flightAbnormalRecy', '异常恢复', para)
    },
    flightAircraftChange: function (index, row) {
      // 变更机号机型登记窗口
      this.dialogaTitle = '变更机号'
      this.$refs['dynamicEditForm'].operate = 'flightAircraftChange'
      this.$refs['dynamicEditForm'].optName = '变更机号'
      this.$refs['dynamicEditForm'].editAble = true
      this.$refs['dynamicEditForm'].isShowIrrInfo = false
      this.$refs['dynamicEditForm'].setIsDisableTime(true)
      this.$refs['dynamicEditForm'].isDiglog = true
      this.$refs['dynamicEditForm'].show(row)
    },
    startBoarding: function (index, row) {
      // 开始登机
      let flightList = []
      let oldFlightList = []
      oldFlightList[0] = Util.deepCopy(row)
      flightList[0] = Util.deepCopy(row)
      flightList[0].flightStatus = 'BDO'
      // 修改其关联的登记口状态以及实际登记时间
      let para = { newValue: flightList, oldValue: oldFlightList }
      let opt = {operate: 'startBoarding', optName: '开始登机'}
      para = Object.assign({}, para, opt)
      this.showConfirmForm(row, 'startBoarding', '开始登机', para)
      // this.dealFlight('startBoarding', '开始登机', para)
    },
    passBoarding: function (index, row) {
      // 过站登机
      let flightList = []
      let oldFlightList = []
      oldFlightList[0] = Util.deepCopy(row)
      flightList[0] = Util.deepCopy(row)
      flightList[0].flightStatus = 'BDT'
      // 修改其关联的登记口状态以及实际登记时间
      let para = { newValue: flightList, oldValue: oldFlightList }
      let opt = {operate: 'passBoarding', optName: '过站登机'}
      para = Object.assign({}, para, opt)
      this.showConfirmForm(row, 'passBoarding', '过站登机', para)
      // this.dealFlight('passBoarding', '过站登机', para)
    },
    urgingBoarding: function (index, row) {
      // 催促登机
      let flightList = []
      let oldFlightList = []
      oldFlightList[0] = Util.deepCopy(row)
      flightList[0] = Util.deepCopy(row)
      flightList[0].flightStatus = 'BDF'
      // 修改其关联的登记口状态以及实际登记时间
      let para = { newValue: flightList, oldValue: oldFlightList }
      let opt = {operate: 'urgingBoarding', optName: '催促登机'}
      para = Object.assign({}, para, opt)
      this.showConfirmForm(row, 'urgingBoarding', '催促登机', para)
      // this.dealFlight('urgingBoarding', '催促登机', para)
    },
    endBoarding: function (index, row) {
      // 结束登机
      let flightList = []
      let oldFlightList = []
      oldFlightList[0] = Util.deepCopy(row)
      flightList[0] = Util.deepCopy(row)
      flightList[0].flightStatus = 'BDC'
      // 修改其关联的登记口状态以及实际登记时间
      let para = { newValue: flightList, oldValue: oldFlightList }
      let opt = {operate: 'endBoarding', optName: '结束登机'}
      para = Object.assign({}, para, opt)
      this.showConfirmForm(row, 'endBoarding', '结束登机', para)
      // this.dealFlight('endBoarding', '结束登机', para)
    },
    checkInFlight: function (index, row) {
      // 值机开始
      let flightList = []
      let oldFlightList = []
      oldFlightList[0] = Util.deepCopy(row)
      flightList[0] = Util.deepCopy(row)
      flightList[0].flightStatus = 'CKI'
      // 修改值机时间表实际开始时间
      let para = { newValue: flightList, oldValue: oldFlightList }
      let opt = {operate: 'checkInFlight', optName: '值机开始'}
      para = Object.assign({}, para, opt)
      this.showConfirmForm(row, 'checkInFlight', '值机开始', para)
      // this.dealFlight('checkInFlight', '值机开始', para)
    },
    checkOffFlight: function (index, row) {
      // 值机关闭
      let flightList = []
      let oldFlightList = []
      oldFlightList[0] = Util.deepCopy(row)
      flightList[0] = Util.deepCopy(row)
      flightList[0].flightStatus = 'CKC'
      // 修改值机时间表实际结束时间
      let para = { newValue: flightList, oldValue: oldFlightList }
      let opt = {operate: 'checkOffFlight', optName: '值机关闭'}
      para = Object.assign({}, para, opt)
      this.showConfirmForm(row, 'checkOffFlight', '值机关闭', para)
      // this.dealFlight('checkOffFlight', '值机关闭', para)
    },
    irrUnderPassenger: function (index, row) {
      // 异常下客，不知如何处理 未处理
      let flightList = []
      let oldFlightList = []
      oldFlightList[0] = Util.deepCopy(row)
      flightList[0] = Util.deepCopy(row)
      // flightList[0].flightStatus = 'CKI'
      let para = { newValue: flightList, oldValue: oldFlightList }
      let opt = {operate: 'irrUnderPassenger', optName: '异常下客'}
      para = Object.assign({}, para, opt)
      this.showConfirmForm(row, 'irrUnderPassenger', '异常下客', para)
      // this.dealFlight('irrUnderPassenger', '异常下客', para)
    },
    flightArrAlt: function (index, row) {
      // 进港备降处理(目的地为XMN、进港A)
      this.dialogaTitle = '进港备降'
      this.$refs['dynamicEditForm'].operate = 'flightArrAlt'
      this.$refs['dynamicEditForm'].optName = '进港备降'
      this.$refs['dynamicEditForm'].editAble = true
      this.$refs['dynamicEditForm'].isShowIrrInfo = true
      this.$refs['dynamicEditForm'].setIsDisableTime(true)
      this.$refs['dynamicEditForm'].fieldAble.estimateArriveDate = false
      this.$refs['dynamicEditForm'].fieldAble.estimateArrTime = false
      // 0828 进港备降
      this.$refs['dynamicEditForm'].isDiglog = false
      this.$refs['dynamicEditForm'].showCurFlight(row)
    },
    flightArrRtn: function (index, row) {
      // 进港返航
      this.dialogaTitle = '进港返航'
      this.$refs['dynamicEditForm'].operate = 'flightArrRtn'
      this.$refs['dynamicEditForm'].optName = '进港返航'
      this.$refs['dynamicEditForm'].editAble = true
      this.$refs['dynamicEditForm'].isShowIrrInfo = true
      this.$refs['dynamicEditForm'].setIsDisableTime(true)
      this.$refs['dynamicEditForm'].isDiglog = true
      this.$refs['dynamicEditForm'].show(row)
    },
    flightDepRtn: function (index, row) {
      // 出港返航
      this.dialogaTitle = '出港返航'
      this.$refs['dynamicEditForm'].operate = 'flightDepRtn'
      this.$refs['dynamicEditForm'].optName = '出港返航'
      this.$refs['dynamicEditForm'].editAble = true
      this.$refs['dynamicEditForm'].isShowIrrInfo = true
      this.$refs['dynamicEditForm'].setIsDisableTime(false)
      this.$refs['dynamicEditForm'].isDiglog = true
      this.$refs['dynamicEditForm'].showFlightDepRtn(row, this.filters.airportCode)
    },
    flightLoadOpt (index, row) {
      this.$refs['flightLoadForm'].show('DynamicAdd', null, row)
    },
    dealFlight: function (action, actionName, param) {
      API.operateFlightData(action, actionName).go(param).then((data) => {
        if (data.ok) {
          let msg = actionName + data.msg
          this.$notify(Util.notifyBody(true, msg))
          this.getDynamicFlight()
        } else {
          this.$notify(Util.notifyBody(false, data.msg))
        }
      })
    },
    cleardata: function () {
      this.filters.flightNo = ''
    },
    handleAddAlt: function () {
      // 新增备降操作显示窗口展示
      this.dialogaTitle = '新增备降'
      this.$refs['dynamicAddForm'].operate = 'addAlt'
      this.$refs['dynamicAddForm'].editAble = false
      this.$refs['dynamicAddForm'].isShowIrrInfo = true
      this.$refs['dynamicAddForm'].setIsDisableTime(false)
      // 0828
      this.$refs['dynamicAddForm'].isDiglog = false
      this.$refs['dynamicAddForm'].show(null, this.filters.airportCode)
    },
    setDirFileter: function (val) {
      this.filters['direction'] = val
      if (val === 'Z') {
        this.filters.direction = ''
      }
      this.getDynamicFlight()
    },
    resConfig: function (index, row) {
      this.$refs['resConfigForm'].show(row)
    },
    // 在航班动态中的更多操作添加VIP管理
    VIPManagement: function (index, row) {
      this.$refs['vipConfigDialog'].showVipConfig(row)
    },
    airportCodeChange: function (airportCode) {
      this.filters.airportCode = airportCode
      this.getDynamicFlight()
    },
    setAirportInfo (subAirportLs) {
      this.$refs['city'].setCitys(subAirportLs)
    },
    exportFlightExcel () {
      // EXCEL导出航班动态
      let field = Util.deepCopy(this.expInfofields)
      this.$refs['expFlightForm'].setAirportCodeLs(this.subAirportLs)
      this.$refs['expFlightForm'].show(field, true, this.filters.airportCode)
    },
    flightScheduleDownload (param) {
      let url = 'http://localhost:9090/raiis/flights/download?'
      for (let key in param) {
        if (param[key] !== '' && param[key] !== null) {
          let val = (key === 'opDate_begin' || key === 'opDate_end') ? param[key].substring(0, 10) : param[key]
          url += key + '=' + val + '&'
        }
      }
      url = url.substring(0, url.length - 1)
      window.location.href = url
      return true
    }
  },
  mounted () {
    // 设置绑定用户管辖运营机场
    this.flightsLoading = true
    this.bindData()
  }
}
</script>
<style lang="scss">
.dynamicFlightCss {
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

