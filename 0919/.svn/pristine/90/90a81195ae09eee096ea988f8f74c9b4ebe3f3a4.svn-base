<template>
  <div class="flightCls">
    <!--搜索工具栏（整行、置顶）-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <city-name
            class="dropDown"
            ref="city"
            v-on:getAirportName = "airportCodeChange">
          </city-name>
        </el-form-item>

        <el-form-item style="width: 15%">
          <el-date-picker v-model="filters.opDate" type="date" placeholder="执行日期" default-value="2017-06-12" clearable style="width: 100%"></el-date-picker>
        </el-form-item>

        <el-form-item style="width: 15%">
          <el-input v-model="filters.flightNo" placeholder="航班号" :icon="iconClass" @mouseenter.native="inputHovering = true"
            :onIconClick="cleardata" @mouseleave.native="inputHovering = false"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" v-on:click="getFlightSchedulels">查询</el-button>
        </el-form-item>

        <el-form-item style="width: 25%">
          <el-radio-group v-model="filters.direction" @change="setDirFileter">
            <el-radio class="radio" label="A">进港</el-radio>
            <el-radio class="radio" label="D">出港</el-radio>
            <el-radio class="radio" label="">所有</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table v-bind:data="flights" highlight-current-row v-loading="flightsLoading" @selection-change="selectChange" @row-click="rowClick" :height="tableHeight" row-class-name="setrowStyle">
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
      <el-table-column prop="vipTag" label="VIP" min-width="55" sortable show-overflow-tooltip >
        <template scope="scope" >
          {{$cache.findByName('vipranks', 'rankCode', scope.row.vipTag, 'description' )}}
        </template>
      </el-table-column>
    </el-table>

    <!--分页-->
    <pagination clas="" :to="getFlightSchedulels" ref="page"></pagination>
  </div>
</template>

<script>
import Util from '../../../common/js/util'
import Butil from '../../../common/js/base-util'
import API from '../../../api'
import Pagination from '../../../components/Pagination'
import CityName from '../../../components/CityName'

export default {
  props: {
    onSelectChange: {
      type: Function,
      default: function () {}
    },
    onRowClick: {
      type: Function,
      default: function () {}
    }
  },
  data () {
    return {
      filters: {
        opDate: Util.formatDate.flightDateFmt('yyyy-MM-dd', new Date(), false),
        flight: '',
        flightNo: '',
        airportCode: '',
        direction: ''
      },
      flights: [],
      subAirportLs: [],
      flightsLoading: false,
      tableHeight: 653,
      selects: [],
      API: API,
      inputHovering: false,
      firstLoading: true
    }
  },
  components: {
    Pagination: Pagination,
    CityName: CityName
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
    }
  },
  methods: {
    bindData () {
      this.subAirportLs = Butil.getSubscribeAirports()
      this.setAirportInfo(this.subAirportLs)
      this.filters.airportCode = this.$refs['city'].airportCode
      this.getFlightSchedulels()
    },
    getFlightSchedulels () {
      this.filters.opDate = Util.formatDate.flightDateFmt('yyyy-MM-dd', this.filters.opDate, false)
      let para = Object.assign({}, this.filters, this.$refs['page'].queryParam())
      this.flightsLoading = true
      API.getFlightSchedulels().go(para).then((data) => {
        if (data.ok) {
          this.$refs['page'].set('total', data.attr.data.pager.recordCount)
          this.flights = data.attr.data.list
        } else {
          this.$notify(Util.notifyBody(false, data.msg))
        }
        this.flightsLoading = false
        this.firstLoading = false
      })
    },
    airportCodeChange (airportCode) {
      this.filters.airportCode = airportCode
      this.getFlightSchedulels()
    },
    setAirportInfo (subAirportLs) {
      this.$refs['city'].setCitys(subAirportLs)
    },
    cleardata () {
      this.filters.flightNo = ''
    },
    selectChange (select) {
      this.selects = select
      this.$emit('onSelectChange', this.selects)
    },
    rowClick (row, event, column) {
      this.$emit('onRowClick', row)
    },
    dataFormat (row, column, cellValue) {
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
    },
    setDirFileter (val) {
      this.filters['direction'] = val
      if (val === 'Z') {
        this.filters.direction = ''
      }
      this.getDynamicFlight()
    },
    clearData () {
      this.filters = {
        opDate: Util.formatDate.flightDateFmt('yyyy-MM-dd', new Date(), false),
        flight: '',
        flightNo: '',
        airportCode: '',
        direction: ''
      }
      this.flights = []
      this.subAirportLs = []
      this.flightsLoading = false
      this.selects = []
      this.inputHovering = false
    }
  },
  mounted () {
    this.flightsLoading = true
    this.bindData()
  }
}
</script>
<style lang="scss">
.flightCls {
  .el-table .cell, .el-table th>div {
    padding-left: 4px!important;
    padding-right: 2px!important;
    box-sizing: border-box;
    text-overflow: ellipsis;
  }

  .el-table {
    overflow: hidden;
    width: auto;
    max-width: auto;
    background-color: #fff;
    border: 1px solid #dfe6ec;
    font-size: 14px;
    color: #1f2d3d;
    margin-left: 10px !important;
  }

  .dropDown {
    width: 230px
  }

  .pageClss {
    margin-right: 0px;
    margin-bottom: 0px;
  }
}
</style>
