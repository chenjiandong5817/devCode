/*
 * @Author: ylj
 * @Date: 2017-11-13 19:16:05
 * @Last Modified by: cdroid
 * @Last Modified time: 2017-12-29 18:44:06
 */
<template>
  <div class="flightLoad">
    <!--搜索工具栏（整行、置顶）-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item label="运营机场">
          <city-name
            ref="city"
            v-on:getAirportName = "airportCodeChange">
          </city-name>
        </el-form-item>
        <el-form-item label="执行日期">
          <el-date-picker v-model="filters.opDate_begin" type="date" placeholder="开始日期" clearable style="width: 120px"></el-date-picker>
          <!--<label>~</label>
          <el-date-picker v-model="filters.opDate_end" type="date" placeholder="结束日期" clearable ></el-date-picker>-->
        </el-form-item>
        <el-form-item label="~">
          <el-date-picker v-model="filters.opDate_end" type="date" placeholder="结束日期" clearable style="width: 120px"></el-date-picker>
        </el-form-item>
        <el-form-item label="航班号">
          <el-input v-model="filters.flightNo" placeholder="航班号" :icon="iconClass" @mouseenter.native="inputHovering = true"
            :onIconClick="cleardata" @mouseleave.native="inputHovering = false" style="width: 100px"></el-input>
        </el-form-item>
         <!--自动添加搜索框 BEGIN-->
        <el-form-item v-for="(item, index) in seaFields" :key="index">
          <search-form-item :item="item" :index="index" v-on:setFiltersVal="setFiltersVal"></search-form-item>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getFloadlsByFlight">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="handleAdd">新增</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="exportFlightLoad">客货行邮导出</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="success" v-on:click="addSearchField"><i class="el-icon-plus el-icon--left"></i>新增条件</el-button>
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
    <el-table v-bind:data="flightLoads" highlight-current-row v-loading="loading" @selection-change="selectChange" :height="tableHeight" style="width: 100%;" row-class-name="setrowStyle">
      <el-table-column type="index" label="序号" align="center" width="42">
        <template slot-scope="scope">
          {{pageSize * (pageNumber - 1) + scope.$index + 1}}
        </template>
      </el-table-column>
      <el-table-column prop="flightSchedule.opDate" label="执行日期" :formatter="dataFormat" width="83" sortable show-overflow-tooltip ></el-table-column>
      <el-table-column prop="flightSchedule.flightTask" label="任务" width="55" sortable>
        <template slot-scope="scope">
          {{$cache.findByName('flighttasks', 'flightTaskCode', scope.row.flightSchedule.flightTask, 'abbr2w' )}}
        </template>
      </el-table-column>
      <el-table-column prop="flightSchedule.carrier" label="公司" width="55" sortable></el-table-column>
      <el-table-column prop="flightSchedule.flightNo" label="航班号" width="70" sortable></el-table-column>
      <el-table-column prop="flightSchedule.direction" label="进出" width="55" sortable>
        <template slot-scope="scope" >
          {{$cache.findByName('flightdirections', 'directionCode', scope.row.flightSchedule.direction, 'description' )}}
        </template>
      </el-table-column>
      <el-table-column prop="flightSchedule.origin" label="起飞站" width="70" sortable show-overflow-tooltip >
        <template slot-scope="scope" >
          {{ scope.row.origin !== '' && scope.row.origin !== null && scope.row.origin.length === 3 ? $cache.findByName('airports', 'iatacode', scope.row.origin, 'cnabbr2w' ) : $cache.findByName('airports', 'icaocode', scope.row.origin, 'cnabbr2w' ) }}
        </template>
      </el-table-column>
      <el-table-column prop="flightSchedule.destination" label="目的站" width="70" sortable>
        <template slot-scope="scope" >
          {{ scope.row.destination !== '' && scope.row.destination !== null && scope.row.destination.length === 3 ? $cache.findByName('airports', 'iatacode', scope.row.destination, 'cnabbr2w' ) : $cache.findByName('airports', 'icaocode', scope.row.destination, 'cnabbr2w' ) }}
        </template>
      </el-table-column>
      <el-table-column prop="flightSchedule.registration" label="机号" width="75" sortable show-overflow-tooltip ></el-table-column>
      <el-table-column prop="flightSchedule.aircraftType" label="机型" width="55" sortable></el-table-column>
      <el-table-column prop="flightSchedule.flightNature" label="区域" width="55" sortable show-overflow-tooltip >
        <template slot-scope="scope">
          {{$cache.findByName('flightnatures', 'flightNatureCode', scope.row.flightSchedule.flightNature, 'description' )}}
        </template>
      </el-table-column>
      <el-table-column prop="flightSchedule.flightStatus" label="状态" width="55" sortable show-overflow-tooltip >
        <template slot-scope="scope">
          {{$cache.findByName('flightstatus', 'statusCode', scope.row.flightSchedule.flightStatus, 'description' )}}
        </template>
      </el-table-column>
      <el-table-column prop="adult" label="成人数" width="70" align="center" sortable show-overflow-tooltip ></el-table-column>
      <el-table-column prop="child" label="儿童数" width="70" align="center" sortable show-overflow-tooltip ></el-table-column>
      <el-table-column prop="infant" label="婴儿数" width="70" align="center" sortable show-overflow-tooltip ></el-table-column>
      <el-table-column prop="firstClass" label="头等舱人数" width="100" align="center" sortable show-overflow-tooltip ></el-table-column>
      <el-table-column prop="businessClass" label="商务舱人数" width="100" align="center" sortable show-overflow-tooltip ></el-table-column>
      <el-table-column prop="economyClass" label="经济舱人数" width="100" align="center" sortable show-overflow-tooltip ></el-table-column>
      <el-table-column prop="transitAdult" label="过站成人数" width="100" align="center" sortable show-overflow-tooltip ></el-table-column>
      <el-table-column prop="transitChild" label="过站儿童数" width="100" align="center" sortable show-overflow-tooltip ></el-table-column>
      <el-table-column prop="transitInfant" label="过站婴儿数" width="100" align="center" sortable show-overflow-tooltip ></el-table-column>
      <el-table-column prop="baggage" label="行李重量" width="85" align="center" sortable show-overflow-tooltip ></el-table-column>
      <el-table-column prop="baggageCount" label="行李件数" width="85" align="center" sortable show-overflow-tooltip ></el-table-column>
      <el-table-column prop="post" label="邮件重量" width="85" align="center" sortable show-overflow-tooltip ></el-table-column>
      <el-table-column prop="cargo" label="货物重量" width="85" align="center" sortable show-overflow-tooltip ></el-table-column>
      <el-table-column prop="transitBaggage" label="过站行李重量" width="115" align="center" sortable show-overflow-tooltip ></el-table-column>
      <el-table-column prop="transitBaggageCount" label="过站行李件数" min-width="115" align="center" sortable show-overflow-tooltip ></el-table-column>
      <el-table-column prop="transitPost" label="过站邮件重量" width="115" align="center" sortable show-overflow-tooltip ></el-table-column>
      <el-table-column prop="transitCargo" label="过站货物重量" width="115" align="center" sortable show-overflow-tooltip ></el-table-column>
      <el-table-column label="操作" width="90" header-align="center" fixed="right">
        <template slot-scope="scope">
          <el-button @click="handleEdit(scope.$index, scope.row)" type="text" size="small">编辑</el-button>
          <el-button @click="handleDel(scope.$index, scope.row)" type="text" size="small">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--分页-->
    <pagination :to="getFloadlsByFlight" ref="page"></pagination>

    <!--删除窗口-->
    <commonDelete
      :to="API.removeFlightLoad().go"
      :callback="getFloadlsByFlight"
      :labelWidth="100"
      ref="flightLoadDelForm"></commonDelete>

    <!--新增界面-->
    <add-or-update
      type="add"
      title="货邮编辑"
      :to="API.addFlightLoad().go"
      :callback="getFloadlsByFlight"
      :labelWidth="100"
      ref="addOrUpdateForm"></add-or-update>

    <!--航班计划导出-->
    <common-add-form
      type="add"
      size="tiny"
      title="航班货邮EXCEL导出"
      :gutter="5"
      :labelWidth="70"
      :to="params => { return this.expFlightLoad(params) }"
      :isDefSubmit="true"
      ref="expFlightLoadForm"></common-add-form>

    <!--动态添加查询条件-->
    <sea-field-config
     title="新增查询条件"
     size="tiny"
     :callback="param => { return this.setSeaCondition(param) }"
     ref="searchFieldConf"></sea-field-config>

  </div>
</template>

<script>
import Util from '../../../common/js/util'
import Butil from '../../../common/js/base-util'
import Pagination from '../../../components/Pagination'
import API from '../../../api'
import commonDelete from './../../../components/CommDelete'
import CityName from '../../../components/CityName'
import addOrUpdate from './flightLoadForm/FlightLoadAddMain'
import commonAddForm from '../../base/baseForm/CommAddOrUpdate'
import SeaFieldConfig from '../searchFieldConfig/searchFieldConfig'
import searchFormItem from '../searchFieldConfig/searchFormItem'

export default {
  data () {
    return {
      filters: {
        flightNo: '',
        airportCode: '',
        direction: this.directionRadio,
        opDate_begin: Util.formatDate.flightDateFmt('yyyy-MM-dd', new Date(), false),
        opDate_end: Util.formatDate.flightDateFmt('yyyy-MM-dd', new Date(), false)
      },
      flightLoads: [],
      subAirportLs: [],
      loading: false,
      tableHeight: 450,
      selects: [],
      API: API,
      inputHovering: false,
      directionRadio: '',
      seaFields: []
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
            { name: 'airportCode', value: this.filters.airportCode, label: '运营机场', type: 'cityName', filterable: true, multiple: false, clearable: true, rules: null, placeholder: '运营机场', span: 12 },
            { name: 'direction', value: this.directionRadio, label: '进出', type: 'selectSimple', filterable: true, multiple: false, clearable: true, selValue: 'directionCode', selLabel: 'description', is2Label: false, choose: this.$cache.fetch('flightdirections'), rules: null, placeholder: '进出', span: 12 }
          ]
        },
        {
          id: '1',
          item: [
            { name: 'opDate_begin', value: this.filters.opDate_begin, label: '开始日期', visitDate: true, visitTime: false, dateStyle: 'width: 100%', timeStyle: 'width: 30%', type: 'dateTimeGroup', allDisable: false, datePlaceholder: '开始日期', timePlaceholder: '时间', formatter: 'yyyy-MM-dd', rules: null, span: 12 },
            { name: 'opDate_end', value: this.filters.opDate_end, label: '结束日期', visitDate: true, visitTime: false, dateStyle: 'width: 100%', timeStyle: 'width: 30%', type: 'dateTimeGroup', allDisable: false, datePlaceholder: '结束日期', timePlaceholder: '时间', formatter: 'yyyy-MM-dd', rules: null, span: 12 }
          ]
        }
      ]
      for (let i = 1, j = 2; i <= this.seaFields.length; i++) {
        let item = Util.deepCopy(this.seaFields[i - 1])
        item.value = this.filters[this.seaFields[i - 1].name]
        if (i % 2 !== 0) {
          let obj = { id: j, item: [] }
          field.push(obj)
          field[j].item.push(item)
        } else {
          field[j].item.push(item)
          j++
        }
      }
      return field
    },
    fieldConf () {
      let field = {
        carrier: { name: 'carrier', value: '', label: '航空公司', type: 'selectSimple', filterable: true, multiple: false, clearable: true, choose: this.$cache.fetch('airlines'), selValue: 'icaocode', selValue2: 'iatacode', selLabel: 'cnname', is2Label: true, rules: null, placeholder: '公司', span: 12 },
        flightNature: { name: 'flightNature', value: '', label: '区域', type: 'selectSimple', filterable: true, multiple: false, clearable: true, choose: this.$cache.fetch('flightnatures'), selValue: 'flightNatureCode', selLabel: 'description', is2Label: false, rules: null, placeholder: '区域', span: 12 },
        flightTask: { name: 'flightTask', value: '', label: '任务', type: 'selectSimple', filterable: true, multiple: false, clearable: true, selValue: 'flightTaskCode', selLabel: 'abbr2w', is2Label: false, choose: this.$cache.fetch('flighttasks'), rules: null, placeholder: '任务', span: 12 },
        origin: { name: 'origin', value: '', label: '起飞地', type: 'selectSimple', filterable: true, multiple: false, clearable: true, selValue: 'icaocode', selLabel: 'cnabbr2w', selValue2: 'iatacode', is2Label: true, choose: this.$cache.fetch('airports'), rules: null, placeholder: '起飞地', span: 12 },
        destinct: { name: 'destinct', value: '', label: '目的地', type: 'selectSimple', filterable: true, multiple: false, clearable: true, selValue: 'icaocode', selLabel: 'cnabbr2w', selValue2: 'iatacode', is2Label: true, choose: this.$cache.fetch('airports'), rules: null, placeholder: '目的地', span: 12 }
      }
      return field
    }
  },
  watch: {
    '$store.state.cached' (val, oldVal) {
      // 重新刷新数据
      this.getFloadlsByFlight()
    }
  },
  components: {
    pagination: Pagination,
    addOrUpdate: addOrUpdate,
    commonDelete: commonDelete,
    CityName: CityName,
    commonAddForm: commonAddForm,
    searchFormItem: searchFormItem,
    seaFieldConfig: SeaFieldConfig
  },
  methods: {
    bindData () {
      // this.$refs['page'].set('pageSize', 40)
      this.subAirportLs = Butil.getSubscribeAirports()
      this.setAirportInfo(this.subAirportLs)
      this.filters.airportCode = this.$refs['city'].airportCode
      this.getFloadlsByFlight()
    },
    getFloadlsByFlight () {
      this.filters.opDate_begin = Util.formatDate.flightDateFmt('yyyy-MM-dd', this.filters.opDate_begin, false)
      this.filters.opDate_end = Util.formatDate.flightDateFmt('yyyy-MM-dd', this.filters.opDate_end, false)
      let para = Object.assign({}, this.filters, this.$refs['page'].queryParam())
      this.loading = true
      API.getFloadlsByFlight().go(para).then((data) => {
        if (data.ok) {
          if (data.attr.data.pager !== null) {
            this.$refs['page'].set('total', data.attr.data.pager.recordCount)
          }
          this.flightLoads = data.attr.data.list
        } else {
          this.$notify(Util.notifyBody(false, data.msg))
        }
        this.loading = false
      })
    },
    selectChange (select) {
      this.selects = select
    },
    handleAdd () {
      this.$refs['addOrUpdateForm'].show('flightLoadAdd', null, null)
    },
    handleEdit (index, row) {
      this.$refs['addOrUpdateForm'].show('Edit', row, row.flightSchedule)
    },
    handleDel (index, row) {
      this.$refs['flightLoadDelForm'].del(row)
    },
    dataFormat (row, column, cellValue) {
      let property = column.property
      let fields = []
      fields = property.split('.')
      // 格式化数据列表中日期格式
      let date = row[fields[0]][fields[1]]
      if (date === undefined || date === null) {
        return ''
      }
      switch (property) {
        case 'flightSchedule.opDate': return Util.formatDate.flightDateFmt('yyyy-MM-dd', date, false)
        case 'flightSchedule.scheduleDepartTime': return Util.formatDate.flightDateFmt('hhmm', date, true, row.opDate)
        case 'flightSchedule.scheduleArriveTime': return Util.formatDate.flightDateFmt('hhmm', date, true, row.opDate)
        default: break
      }
    },
    cleardata () {
      // 清除输入框内容
      this.filters.flightNo = ''
    },
    setDirFileter (val) {
      // 根据进出港Radio筛选数据
      this.filters['direction'] = val
      if (val === 'Z') {
        this.filters.direction = ''
      }
      this.getFloadlsByFlight()
    },
    setAirportInfo (subAirportLs) {
      this.$refs['city'].setCitys(subAirportLs)
    },
    airportCodeChange (airportCode) {
      this.filters.airportCode = airportCode
      this.getFloadlsByFlight()
    },
    exportFlightLoad () {
      // EXCEL导出航班计划
      let field = Util.deepCopy(this.expInfofields)
      this.$refs['expFlightLoadForm'].setAirportCodeLs(this.subAirportLs)
      this.$refs['expFlightLoadForm'].show(field, true, this.filters.airportCode)
    },
    expFlightLoad (param) {
      let url = 'http://localhost:9090/raiis/flightload/byflight/download?'
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
    addSearchField () {
      // 动态增加搜索条件
      let checkList = []
      let fixSeaFields = '/flightNo/airportCode/direction'
      for (let i = 0; i < this.seaFields.length; i++) {
        if (fixSeaFields.indexOf(this.seaFields[i].name) <= 0) {
          checkList.push(this.seaFields[i].name)
        }
      }
      this.$refs['searchFieldConf'].show(this.fieldConf, checkList)
    },
    setSeaCondition (fields) {
      this.seaFields = Util.deepCopy(fields)
    },
    setFiltersVal (item) {
      this.filters[item.field] = item.value
      this.seaFields[item.index].value = item.value
    }
  },
  mounted () {
    this.bindData()
  }
}
</script>
<style lang="scss">
.flightLoad {
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

