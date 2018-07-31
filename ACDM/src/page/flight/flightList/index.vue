<template>
<div class="flightListDiv">
  <div class="toolbar">
    <el-row type="flex" align="middle">
      <el-col :span="18">
        <el-form :inline="true" size="small" :model="filters">
          <el-form-item label="当前机场" >
            <el-select v-model="filters.airportICAOCode" placeholder="选择机场" >
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-input  placeholder="请输入航班号，查询航班" v-model="filters.flight"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" size="small" @click="getList">查询</el-button>
            <el-button type="primary" size="small" @click="flightChoose">增加</el-button>
          </el-form-item>
        </el-form>
      </el-col>
      <el-col :span="6" style="text-align: right;">
        <el-form :inline="true">
          <el-form-item label="进港航班">
            <span>{{arrTotal}}</span>
          </el-form-item>
          <el-form-item label="出港航班">
            <span>{{depTotal}}</span>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </div>
  <el-tabs v-model="activeName">
    <el-tab-pane label="进港航班" name="flightSagmentArr">
      <div class="table-content">
        <el-table
            :data = "flightSagmentData"
            border
            style="width:100%"
            size="small"
            highlight-current-row
            >
            <el-table-column type="index" align="center" fixed>
            </el-table-column>
            <el-table-column label="执行日期" prop="opdate" min-width="90" align="center" fixed>
              <template slot-scope="scope">
                <span>{{scope.row.opdate?moment(scope.row.opdate).format("YYYY-MM-DD"):''}}</span>
              </template>
            </el-table-column>
            <el-table-column label="机号" prop="registration" align="center" fixed></el-table-column>
            <el-table-column label="机型" prop="aircraftType" align="center" fixed></el-table-column>
            <el-table-column label="飞行任务" prop="flightTask" align="center"  min-width="90" show-overflow-tooltip></el-table-column>
            <el-table-column label="承运人" prop="carrier" align="center" ></el-table-column>
            <el-table-column label="航班号" prop="flight" min-width="90" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column label="合并航班号" prop="combineFlightId" min-width="90" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column label="航班状态" prop="flightStatus" align="center" ></el-table-column>
            <el-table-column label="航班异常状态" prop="irregularStatus" min-width="95" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column label="机位" prop="stand" align="center" min-width="60" show-overflow-tooltip></el-table-column>
            <el-table-column label="登机口" prop="gate" align="center" min-width="60" show-overflow-tooltip></el-table-column>
            <el-table-column label="航站楼" prop="terminal" align="center" min-width="60"  show-overflow-tooltip></el-table-column>
            <el-table-column label="代理" prop="generalagent" align="center" :formatter="formatterAgent" min-width="60"  show-overflow-tooltip></el-table-column>
            <el-table-column label="航段（中文）" prop="segmentCN" min-width="155" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column label="航段（英文）" prop="segmentEN" min-width="240" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column label="计划起飞时间" min-width="100" align="center" >
              <template slot-scope="scope">
                <span>{{scope.row.stot?moment(scope.row.stot).format("HH:mm"):''}}</span>
              </template>
            </el-table-column>
            <el-table-column label="预计起飞时间" min-width="100" align="center" show-overflow-tooltip>
              <template slot-scope="scope">
                <span>{{scope.row.etot? moment(scope.row.etot).format("HH:mm"):''}}</span>
              </template>
            </el-table-column>
            <el-table-column label="实际起飞时间" min-width="100" align="center" show-overflow-tooltip>
              <template slot-scope="scope">
                <span>{{scope.row.atot?moment(scope.row.atot).format("HH:mm"):''}}</span>
              </template>
            </el-table-column>
            <el-table-column label="计划抵达时间" min-width="100" align="center" show-overflow-tooltip>
              <template slot-scope="scope">
                <span>{{scope.row.sldt?moment(scope.row.sldt).format("HH:mm"):''}}</span>
              </template>
            </el-table-column>
            <el-table-column label="预计抵达时间" min-width="100" align="center" show-overflow-tooltip>
              <template slot-scope="scope">
                <span>{{scope.row.eldt?moment(scope.row.eldt).format("HH:mm"):''}}</span>
              </template>
            </el-table-column>
            <el-table-column label="实际抵达时间" min-width="100" align="center" show-overflow-tooltip>
              <template slot-scope="scope">
                <span>{{scope.row.aldt?moment(scope.row.aldt).format("HH:mm"):''}}</span>
              </template>
            </el-table-column>
            <!-- <el-table-column label="运营机场" prop="airportICAOCode" align="center" min-width="70" show-overflow-tooltip></el-table-column> -->
        </el-table>
      </div>
      <pagination
      right
      @size-change="handleSizeChangeSagment"
      @current-change="handleCurrentChangeSagment"
      :current-page="pagerSagment.pageNumber"
      :page-sizes="[15, 30, 50, 100]"
      :page-size="pagerSagment.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pagerSagment.total">
      </pagination>
    </el-tab-pane>
    <el-tab-pane label="出港航班" name="flightSagmentDep">
      <div class="table-content">
        <el-table
            :data="flightSagmentDepData"
            border
            style="width:100%"
            size="small"
            highlight-current-row
            ref="flightSagmentDepTable"
            >
            <el-table-column type="index" fixed align="center">
            </el-table-column>
            <el-table-column label="执行日期" min-width="90" align="center" fixed>
              <template slot-scope="scope">
                <span>{{scope.row.opdate?moment(scope.row.opdate).format("YYYY-MM-DD"):''}}</span>
              </template>
            </el-table-column>
            <el-table-column label="机号" prop="registration"  align="center" fixed></el-table-column>
            <el-table-column label="机型" prop="aircraftType"  align="center" fixed></el-table-column>
            <el-table-column label="飞行任务" prop="flightTask" align="center" min-width="90" show-overflow-tooltip></el-table-column>
            <el-table-column label="承运人" prop="carrier" align="center" ></el-table-column>
            <el-table-column label="航班号" prop="flight" align="center" min-width="90" show-overflow-tooltip></el-table-column>
            <el-table-column label="合并航班号" prop="combineFlightId" align="center" min-width="90" show-overflow-tooltip></el-table-column>
            <el-table-column label="航班状态" prop="flightStatus" align="center" ></el-table-column>
            <el-table-column label="航班异常状态" prop="irregularStatus" align="center" min-width="95" show-overflow-tooltip></el-table-column>
            <el-table-column label="机位" prop="stand" align="center" min-width="60" show-overflow-tooltip></el-table-column>
            <el-table-column label="登机口" prop="gate" align="center" min-width="60" show-overflow-tooltip></el-table-column>
            <el-table-column label="航站楼" prop="terminal" align="center" min-width="60" show-overflow-tooltip></el-table-column>
            <el-table-column label="代理" prop="generalagent" align="center" :formatter="formatterAgent" min-width="60"  show-overflow-tooltip></el-table-column>
            <el-table-column label="航段（中文）" prop="segmentCN" align="center"  min-width="155" show-overflow-tooltip></el-table-column>
            <el-table-column label="航段（英文）" prop="segmentEN" align="center"  min-width="240" show-overflow-tooltip></el-table-column>
            <el-table-column label="计划起飞时间" align="center"  min-width="100" show-overflow-tooltip>
              <template slot-scope="scope">
                <span>{{scope.row.stot?moment(scope.row.stot).format("HH:mm"):''}}</span>
              </template>
            </el-table-column>
            <el-table-column label="预计起飞时间" align="center"  min-width="100" show-overflow-tooltip>
              <template slot-scope="scope">
                <span>{{scope.row.etot?moment(scope.row.etot).format("HH:mm"):''}}</span>
              </template>
            </el-table-column>
            <el-table-column label="实际起飞时间" align="center"  min-width="100" show-overflow-tooltip>
              <template slot-scope="scope">
                <span>{{scope.row.atot?moment(scope.row.atot).format("HH:mm"):''}}</span>
              </template>
            </el-table-column>
            <el-table-column label="计划抵达时间" align="center"  min-width="100" >
              <template slot-scope="scope">
                <span>{{scope.row.sldt?moment(scope.row.sldt).format("HH:mm"):''}}</span>
              </template>
            </el-table-column>
            <el-table-column label="预计抵达时间" align="center"  min-width="100" show-overflow-tooltip>
              <template slot-scope="scope">
                <span>{{scope.row.eldt?moment(scope.row.eldt).format("HH:mm"):''}}</span>
              </template>
            </el-table-column>
            <el-table-column label="实际抵达时间" align="center"  min-width="100" show-overflow-tooltip>
              <template slot-scope="scope">
                <span>{{scope.row.aldt?moment(scope.row.aldt).format("HH:mm"):''}}</span>
              </template>
            </el-table-column>
            <!-- <el-table-column label="运营机场" prop="airportICAOCode" align="center"  min-width="70" show-overflow-tooltip></el-table-column> -->
        </el-table>
      </div>
      <pagination
      right
      @size-change="handleSizeChangeSagmentDep"
      @current-change="handleCurrentChangeSagmentDep"
      :current-page="pagerSagmentDep.pageNumber"
      :page-sizes="[15, 30, 50, 100]"
      :page-size="pagerSagmentDep.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pagerSagmentDep.total">
      </pagination>
    </el-tab-pane>
    <el-tab-pane label="关联航班" name="flightSagmentLinked">
      <div class="table-content">
        <el-table :data="data" style="width:100%"  size="small" border fit highlight-current-row>
          <el-table-column
            v-for="col in cols"
            :label="col.label" :key="col.label" :align ="col.align" :formatter="col.formatter"  show-overflow-tooltip :min-width="col.minWidth">
            <el-table-column
              v-for="item in col.children"
              :prop="item.prop" :label="item.label" :key="item.label" :align ="item.align"  :formatter="item.formatter" :min-width="item.minWidth" show-overflow-tooltip>
            </el-table-column>
          </el-table-column>
        </el-table>
      </div>
      <pagination
      right
      @size-change="handleSizeChangeSagmentLinked"
      @current-change="handleCurrentChangeSagmentLinked"
      :current-page="pagerSagmentLinked.pageNumber"
      :page-sizes="[15, 30, 50, 100]"
      :page-size="pagerSagmentLinked.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pagerSagmentLinked.total">
      </pagination>
    </el-tab-pane>
  </el-tabs>
  <!-- <accommodation-vehicle-dialog ref="accommodationVehicleDialog" :callback="getList"></accommodation-vehicle-dialog> -->
  <provide-food-add ref="provideFoodAdd"></provide-food-add>
  <flight-choose-dialog :show.sync="show" @dbClick="dbClick"></flight-choose-dialog>
</div>
</template>
<script>
import pagination from '@/components/pagination/'
import { adjustPage } from '@/util/util'
import moment from 'moment'
/* import accommodationVehicleDialog from '../../flightDelay/accommodation/accommodation-vehicle-dialog' */
import provideFoodAdd from '@/page/flightDelay/provideFood/provide-food-dialog'
import flightChooseDialog from '../flightChooseDialog'

const AgentType = { 'A': '厦航', 'B': '机场' }
export default {
  components: {
    pagination, provideFoodAdd, /* accommodationVehicleDialog */ flightChooseDialog
  },
  data () {
    return {
      show: false,
      moment: moment,
      activeName: 'flightSagmentArr',
      flightSagmentData: [],
      flightSagmentDepData: [],
      flightSagmentLinkedData: [],
      filtersflightSagment: {
        carrier: '',
        flight: '',
        opdate: ''
      },
      filtersflightSagmentDep: {
        carrier: '',
        flight: '',
        opdate: ''
      },
      filtersflightSagmentLinked: {
        carrier: '',
        flight: '',
        opdate: ''
      },
      pagerSagment: {
        pageSize: 15,
        pageNumber: 1,
        total: 0
      },
      pagerSagmentDep: {
        pageSize: 15,
        pageNumber: 1,
        total: 0
      },
      pagerSagmentLinked: {
        pageSize: 15,
        pageNumber: 1,
        total: 0
      },
      depTotal: 0,
      arrTotal: 0,
      cols: [
        {label: '进港航班',
          align: 'center',
          children: [
            { label: '执行日期',
              prop: 'arrOpdate',
              align: 'center',
              minWidth: '90',
              formatter: (row, column) => { return row.arrOpdate ? moment(row.arrOpdate).format('YYYY-MM-DD') : '' }
            },
            { label: '承运人', prop: 'arrCarrier', align: 'center' },
            { label: '航班号', prop: 'arrFlight', align: 'center' },
            { label: '航线（中文）', prop: 'arrSegmentCN', minWidth: '130', align: 'center' },
            { label: '航线（英文）', prop: 'arrSegmentEN', minWidth: '175', align: 'center' },
            { label: '任务', prop: 'arrFlightTask', minWidth: '100', align: 'center' },
            { label: '代理', prop: 'arrGeneralagent', align: 'center', minWidth: '60', formatter: (row, column) => { return row.arrGeneralagent ? AgentType[row.arrGeneralagent] : ' ' } },
            { label: '状态', prop: 'arrFlightStatus', align: 'center' },
            { label: '计落',
              prop: 'sldt',
              align: 'center',
              minWidth: '130',
              formatter: function (row, column) {
                return row.sldt ? moment(row.sldt).format('YYYY-MM-DD HH:mm') : ''
              }
            },
            { label: '预落', prop: 'eldt', align: 'center', minWidth: '130', formatter: (row, column) => { return row.eldt ? moment(row.eldt).format('YYYY-MM-DD HH:mm') : '' } },
            { label: '实落', prop: 'aldt', align: 'center', minWidth: '130', formatter: (row, column) => { return row.aldt ? moment(row.aldt).format('YYYY-MM-DD HH:mm') : '' } },
            { label: '机位', prop: 'arrStand', align: 'center', minWidth: '60' },
            { label: '航站楼', prop: 'arrTerminal', align: 'center', minWidth: '60' }
          ]},
        { label: '中转信息',
          align: 'center',
          children: [
            { label: '机号', prop: 'registration', align: 'center' },
            { label: '机型', prop: 'aircraftType', align: 'center' },
            { label: '地服', prop: 'suit', align: 'center', formatter: (row, column) => { return row.suit ? AgentType[row.suit] : ' ' } },
            { label: '保障状态', prop: 'flightevent', align: 'center' }
          ]},
        { label: '出港航班信息',
          align: 'center',
          children: [
            { label: '执行日期',
              prop: 'depOpdate',
              align: 'center',
              minWidth: '90',
              formatter: (row, column) => { return row.depOpdate ? moment(row.depOpdate).format('YYYY-MM-DD') : '' }
            },
            { label: '承运人', prop: 'depCarrier', align: 'center' },
            { label: '航班号', prop: 'depFlight', align: 'center' },
            { label: '航线（中文）', prop: 'depSegmentCN', minWidth: '155', align: 'center' },
            { label: '航线（英文）', prop: 'depSegmentEN', minWidth: '240', align: 'center' },
            { label: '任务', prop: 'depFlightTask', minWidth: '100', align: 'center' },
            { label: '代理', prop: 'depGeneralagent', align: 'center', minWidth: '60', formatter: (row, column) => { return row.depGeneralagent ? AgentType[row.depGeneralagent] : ' ' } },
            { label: '状态', prop: 'depFlightStatus', align: 'center' },
            { label: '计起', prop: 'stot', align: 'center', minWidth: '130', formatter: (row, column) => { return row.stot ? moment(row.stot).format('YYYY-MM-DD HH:mm') : '' } },
            { label: '预起', prop: 'etot', align: 'center', minWidth: '130', formatter: (row, column) => { return row.etot ? moment(row.etot).format('YYYY-MM-DD HH:mm') : '' } },
            { label: '实起', prop: 'atot', align: 'center', minWidth: '130', formatter: (row, column) => { return row.atot ? moment(row.atot).format('YYYY-MM-DD HH:mm') : '' } },
            { label: '机位', prop: 'depStand', align: 'center', minWidth: '60' },
            { label: '航站楼', prop: 'depTerminal', align: 'center', minWidth: '60' }
          ]}
      ],
      filters: {
        airportICAOCode: 'ZSAM',
        flight: ''
      },
      options: [
        {
          value: 'ZSAM',
          label: '厦门(ZSAM)'
        },
        {
          value: 'ZSFZ',
          label: '福州(ZSFZ)'
        },
        {
          value: 'ZSLO',
          label: '连城(ZSLO)'
        },
        {
          value: 'ZSWY',
          label: '武夷山(ZSWY)'
        }
      ],
      value: '',
      data: [],
      contextRow: null
    }
  },
  methods: {
    getflightSagmentData () {
      let ajax = this.$auth('post_flight_Segment_list')
      if (ajax) {
        let time = moment(this.filtersflightSagment.opdate)
        this.filtersflightSagment.opdate = time.valueOf()
        ajax(Object.assign(this.filters, adjustPage(this.pagerSagment))).then((res) => {
          if (res.status === 1) {
            this.flightSagmentData = res.data.content
            this.pagerSagment.total = res.data.totalElements
          }
        })
      }
    },
    getflightSagmentDepData () {
      let ajax = this.$auth('post_flight_segment_dep_list')
      if (ajax) {
        ajax(Object.assign(this.filters, adjustPage(this.pagerSagmentDep))).then((res) => {
          if (res.status === 1) {
            this.flightSagmentDepData = res.data.content
            this.pagerSagmentDep.total = res.data.totalElements
          }
        })
      }
    },
    handleCurrentChangeSagment (val) {
      this.pagerSagment.pageNumber = val
      this.getflightSagmentData()
    },
    handleSizeChangeSagment (val) {
      this.pagerSagment.pageSize = val
      this.getflightSagmentData()
    },
    handleCurrentChangeSagmentDep (val) {
      this.pagerSagmentDep.pageNumber = val
      this.getflightSagmentDepData()
    },
    handleSizeChangeSagmentDep (val) {
      this.pagerSagmentDep.pageSize = val
      this.getflightSagmentDepData()
    },
    handleCurrentChangeSagmentLinked (val) {
      this.pagerSagmentLinked.pageNumber = val
      this.getSegmentList()
    },
    handleSizeChangeSagmentLinked (val) {
      this.pagerSagmentLinked.pageSize = val
      this.getSegmentList()
    },
    getList () {
      this.getTotalNums()
      switch (this.activeName) {
        case 'flightSagmentArr':
          this.pagerSagment.pageNumber = 0
          this.getflightSagmentData()
          break
        case 'flightSagmentDep':
          this.pagerSagmentDep.pageNumber = 0
          this.getflightSagmentDepData()
          break
        case 'flightSagmentLinked':
          this.pagerSagmentLinked.pageNumber = 0
          this.getSegmentList()
          break
        default: return true
      }
    },
    getSegmentList () {
      let ajax = this.$auth('post_flight_segment_linked_list')
      if (ajax) {
        ajax(Object.assign(this.filters, adjustPage(this.pagerSagmentLinked))).then((res) => {
          this.data = res.data.content
          this.pagerSagmentLinked.total = res.data.totalElements
        })
      }
    },
    getTotalNums () {
      let ajax = this.$auth('get_total_nums')
      if (ajax) {
        ajax().then((res) => {
          this.depTotal = res.data.depTotal
          this.arrTotal = res.data.arrTotal
        })
      }
    },
    accommodationAdd () {
      this.$refs['accommodationVehicleDialog'].show(this.contextRow)
    },
    providefoodAdd () {
      let obj = {
        flightId: this.contextRow.flightId,
        carrier: this.contextRow.carrier,
        flight: this.contextRow.flight
      }
      this.$refs['provideFoodAdd'].show(obj, true)
    },
    formatterAgent (row, column) {
      return row.generalagent ? AgentType[row.generalagent] : ' '
    },
    flightChoose () {
      this.show = true
    },
    dbClick (data) {
      console.log(data)
    }
  }
}
</script>
<style>
.flightListDiv #tab-flightSagmentArr {
    padding-left: 0;
}
.flightListDiv #tab-flightSagmentLinked {
    padding-right: 0;
}
</style>
