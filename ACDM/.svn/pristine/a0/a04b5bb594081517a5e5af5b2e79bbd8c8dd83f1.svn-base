<template>
  <div class="serviceTypeWrapper">
    <div class="toolbar">
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
        </el-form-item>
      </el-form>
    </div>
      <el-table
            :data="tableData5"
            border
            style="width:100%"
            size="small"
            highlight-current-row
            row-key="flightId"
            :expand-row-keys="entexpands"
            @row-click="rowExpand"
            @expand-change="expandChange"
            >
        <el-table-column type="expand">
          <template>
            <div class="expand-div" v-if="entexpands.length > 0">
              <p class="typeP" style="margin-top:0">配餐服务&emsp;<i title="点击新增" class="fa fa-plus-square-o" style="cursor: pointer;" @click="toAdd('foodService')"></i></p>
              <div v-for="(item, index) in props.foodService" :key="item.id" class="lineDiv">
                <el-row type="flex" align="bottom" justify="space-between">
                  <el-col :span="3"><div><span>{{index+1}}&emsp;</span><span>当前进度：</span><span>{{ item.meta.progressDetail.title }}</span></div></el-col>
                  <el-col :span="3"><div><span>服务状态：</span><span>{{ item.canceled?'已取消':(item.meta.progressDetail.ended?'结束':'进行中') }}</span></div></el-col>
                  <el-col :span="3"><div class="norwrap"><span>配餐类型：</span><span :title="item.foodType">{{ item.foodType }}</span></div></el-col>
                  <el-col :span="3"><div class="norwrap"><span>配餐单位：</span><span :title="departOptions(item.foodDepartment)">{{ departOptions(item.foodDepartment) }}</span></div></el-col>
                  <el-col :span="2"><div><span>预计数量：</span><span>{{ item.meta.estFoodCount>0?item.meta.estFoodCount:'-' }}</span></div></el-col>
                  <el-col :span="2"><div><span>实际数量：</span><span>{{ item.meta.actFoodCount>0?item.meta.actFoodCount:'-' }}</span></div></el-col>
                  <el-col :span="3"><div><span>送达类型：</span><span>{{ item.sendType }}</span></div></el-col>
                  <el-col :span="4"><div><span>预计送达时间：</span><span>{{  item.planArrive?moment(item.planArrive).format('YYYY-MM-DD HH:mm'):'-' }}</span></div></el-col>
                  <el-col :span="1" style="text-align:right"><div><a  class="buttonA" title="点击获取更多" @click="toDetail('foodService',item)">更多&nbsp;>></a></div></el-col>
                </el-row>
              </div>
              <el-row>
                <el-col :span="2">
                  <p class="typeP">住宿服务&emsp;<i title="点击新增" class="fa fa-plus-square-o" style="cursor: pointer;" @click="toAdd('hotelService')"></i></p>
                </el-col>
                <el-col :span="22">
                  <el-button style="margin-top:23px" size="mini" round class="mini-button"  @click="appendFood" :disabled="props.hotelService && (props.hotelService.length <= 0)"><i class="fa fa-bolt button-icon-left" />追加配餐</el-button>
                </el-col>
              </el-row>
              <div v-for="(item, index) in props.hotelService" :key="item.id" class="lineDiv">
                <el-row type="flex" align="bottom" justify="space-between">
                  <el-col :span="1"><div><span>{{index+1}}</span></div></el-col>
                  <el-col :span="2"><div><span>当前进度：</span><span>{{ item.process }}</span></div></el-col>
                  <el-col :span="3"><div><span>VIP乘客人数：</span><span>{{ item.vipPassengers&&item.vipPassengers>0 ? item.vipPassengers+'人':'-'}}</span></div></el-col>
                  <el-col :span="3"><div><span>普通乘客人数：</span><span>{{ item.ordinaryPassengers&&item.ordinaryPassengers>0?item.ordinaryPassengers+'人':'-'}}</span></div></el-col>
                  <el-col :span="13"><div><span>酒店：</span><span v-for="i in item.hotels" :key="i.id">{{ departOptions(i.hotelName) }}</span></div></el-col>
                  <el-col :span="2" style="text-align:right"><div><a class="buttonA" title="点击获取更多" @click="toDetail('hotelService',item)">更多&nbsp;>></a></div></el-col>
                </el-row>
              </div>
              <p class="typeP">派车服务&emsp;<i title="点击新增" class="fa fa-plus-square-o" style="cursor: pointer;" @click="toAdd('vehicleService')"></i></p>
              <div  v-for="(item, index) in props.vehicleService" :key="item.id" class="lineDiv">
                <el-row type="flex" align="bottom" justify="center">
                  <el-col :span="1"><div><span>{{index+1}}</span></div></el-col>
                  <el-col :span="2"><div><span>当前进度：</span><span>{{ item.meta.progressDetail.title}}</span></div></el-col>
                  <el-col :span="4"><div><span>目的地：</span><span>{{ item.destination }}</span></div></el-col>
                  <el-col :span="4"><div><span>乘客人数：</span><span>{{ item.passengers&&item.passengers>0?item.passengers+'人':'-'}}</span></div></el-col>
                  <el-col :span="11"><div><span>车辆类型：</span><span v-for="i in item.busEntities" :key="i.id">{{ i.type }}</span></div></el-col>
                  <el-col :span="2" style="text-align:right"><div><a  class="buttonA" title="点击获取更多" @click="toDetail('vehicleService',item)">更多&nbsp;>></a></div></el-col>
                </el-row>
              </div>
            </div>
          </template>
        </el-table-column>
        <el-table-column type="index" align="center">
        </el-table-column>
        <el-table-column label="执行日期" prop="opdate" min-width="90" align="center">
          <template slot-scope="scope">
            <span>{{scope.row.opdate?moment(scope.row.opdate).format("YYYY-MM-DD"):''}}</span>
          </template>
        </el-table-column>
        <el-table-column label="承运人" prop="carrier" align="center" ></el-table-column>
        <el-table-column label="航班号" prop="flight" min-width="90" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column label="航站楼" prop="terminal" align="center" min-width="60"  show-overflow-tooltip></el-table-column>
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
      </el-table>
    <pagination
      right
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pager.pageNumber"
      :page-sizes="[15, 30, 50, 100]"
      :page-size="pager.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pager.total">
    </pagination>
  </div>
</template>

<script>
import pagination from '@/components/pagination/'
import { adjustPage } from '@/util/util'
import moment from 'moment'
import { mapGetters } from 'vuex'
const AgentType = { A: '厦航', B: '机场' }
export default {
  components: {
    pagination
  },
  data () {
    return {
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
      moment: moment,
      filters: {
        airportICAOCode: 'ZSAM',
        flight: null
      },
      props: {},
      pager: {
        pageNumber: 1,
        pageSize: 15,
        total: 0
      },
      entexpands: [],
      tableData5: [],
      selectItem: null,
      timer: null,
      departMent: null
    }
  },
  computed: {
    ...mapGetters(['tab', 'tabList', 'messageTypeAll'])
  },
  beforeDestroy () {
    this.clearEntexpands()
  },
  methods: {
    departOptions (val) {
      let name = ''
      if (!val) return
      let arr = val.split('.')
      if (arr.length > 0) {
        let Obj = this.messageTypeAll.find(item => item.code === arr[0])
        if (Obj && Obj.children) {
          let unitObj = Obj.children.find(item => item.code === arr[1])
          if (unitObj && unitObj.children) {
            unitObj.children.forEach(ele => {
              if (ele.code === arr[2]) {
                name = ele.name
              }
            })
          }
        }
      }
      return name
    },
    appendFood () {
      if (this.props.hotelService.length === 0) {
        this.$fail('请先添加住宿信息再执行追加配餐')
        return
      }
      let address = this.departOptions(this.props.hotelService[0].hotels[0].hotelName)
      let obj = {
        sendAddr: this.props.hotelService[0].hotels[0].hotelName,
        zhSendAddr: address,
        foodDepartment: this.props.hotelService[0].hotels[0].hotelName,
        serviceType: 'ADD_MEALS_SERVICE',
        foodType: '',
        flightId: this.selectItem.flightId,
        carrier: this.selectItem.carrier,
        flight: this.selectItem.flight,
        progress: 0
      }
      this.router2FlightService({food: obj, activeName: 'food', carrier: this.selectItem.carrier, flight: this.selectItem.flight, flightId: this.selectItem.flightId})
    },
    getList () {
      let ajax = this.$auth('post_flight_segment_dep_list')
      if (ajax) {
        this.clearEntexpands()
        ajax(Object.assign(this.filters, adjustPage(this.pager))).then(res => {
          if (res.status && res.data) {
            this.tableData5 = res.data.content
            this.pager.total = res.data.totalElements
          }
        })
      }
    },
    expandChange (row, expandedRows) {
      this.rowExpand(row)
    },
    isExist () {
      let result = null
      this.tabList.forEach(element => {
        if (element.name.indexOf('flightSagmentDep') > -1) {
          result = element
        }
      })
      return result
    },
    rowExpand (row, event, column) {
      this.props = {}
      if (this.entexpands.indexOf(row.flightId) < 0) {
        this.clearEntexpands()
        let ajax = this.$auth('get_service_all_list')
        if (ajax) {
          ajax({ flightId: row.flightId }).then(res => {
            if (res.status && res.data) {
              this.selectItem = row
              this.entexpands.push(row.flightId)
              this.props = res.data
              this.timer = setInterval(() => {
                this.getLastedData(row.flightId)
              }, 10 * 1000)
            }
          })
        }
      } else {
        this.clearEntexpands()
      }
    },
    getLastedData (id) {
      let ajax = this.$auth('get_service_all_list')
      if (ajax && this.entexpands.length > 0 && this.isExist()) {
        ajax({ flightId: id }).then(res => {
          if (res.status && res.data) {
            this.props = res.data
          }
        })
      } else {
        this.clearEntexpands()
      }
    },
    handleSizeChange (val) {
      this.pager.pageSize = val
      this.clearEntexpands()
      this.getList()
    },
    handleCurrentChange (val) {
      this.pager.pageNumber = val
      this.clearEntexpands()
      this.getList()
    },
    clearEntexpands () {
      if (this.timer) {
        clearInterval(this.timer)
        this.timer = null
      }
      this.entexpands.splice(0)
    },
    formatterAgent (row, column) {
      return row.generalagent ? AgentType[row.generalagent] : ' '
    },
    router2FlightService (params) {
      this.$router.push({path: '/flight_delay/newDemo'})
      this.$store.commit('ADD_TAB', {
        label: '航延服务流程',
        name: 'newDemo',
        value: '/flight_delay/newDemo'
      })
      params.carrier = this.selectItem.carrier
      params.flight = this.selectItem.flight
      params.flightId = this.selectItem.flightId
      params.terminal = this.selectItem.terminal
      this.$store.dispatch('ChangeService', params)
    },
    toDetail (type, item) {
      switch (type) {
        case 'foodService':
          this.router2FlightService({food: item, activeName: 'food'})
          break
        case 'vehicleService':
          this.router2FlightService({vehicle: item, activeName: 'vehicle'})
          break
        case 'hotelService':
          this.router2FlightService({accom: item, activeName: 'accom'})
          break
        default:
          return false
      }
    },
    toAdd (type) {
      let obj = null
      switch (type) {
        case 'foodService':
          obj = {
            name: 'food',
            flightId: this.selectItem.flightId,
            carrier: this.selectItem.carrier,
            flight: this.selectItem.flight,
            terminal: this.selectItem.terminal,
            gate: this.selectItem.gate,
            serviceType: 'MEALS_SERVICE',
            progress: 0
          }
          this.router2FlightService({food: obj, activeName: 'food'})
          break
        case 'vehicleService':
          obj = {
            name: 'vehicle',
            flightId: this.selectItem.flightId,
            carrier: this.selectItem.carrier,
            flight: this.selectItem.flight,
            terminal: this.selectItem.terminal,
            gate: this.selectItem.gate
          }
          this.router2FlightService({vehicle: obj, activeName: 'vehicle'})
          break
        case 'hotelService':
          obj = {
            name: 'accom',
            flightId: this.selectItem.flightId,
            carrier: this.selectItem.carrier,
            flight: this.selectItem.flight,
            terminal: this.selectItem.terminal,
            gate: this.selectItem.gate
          }
          this.router2FlightService({accom: obj, activeName: 'accom'})
          break
        default:
          return false
      }
    }
  }
}
</script>

<style>
.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}
.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
}
.expand-div {
  max-height: 240px;
  overflow: auto;
  background-color: #e9eae7;
}
.expand-div .mini-button {
  padding: 3px 15px !important;
}
.expand-div .button-icon-left {
  margin-right: 5px;
}
.typeP {
  font-weight: bold;
  font-size: 14px;
  margin-top: 20px;
}
.buttonA {
  cursor: pointer;
}
.lineDiv {
  position: relative;
}
.lineDiv:after {
  position: absolute;
  content: ' ';
  background: #292f36;
  width: 100%;
  height: 1px;
  left: 0;
  right: 0;
  transform: scaleY(0.5);
}
.serviceTypeWrapper .el-table__expanded-cell {
  padding: 0;
}
.serviceTypeWrapper .expand-div {
  padding: 10px;
  background: #181F10;
}
.norwrap{
  max-width: 10rem;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
</style>
