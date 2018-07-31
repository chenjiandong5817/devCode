<template>
<div class="flightFormclass">
  <div :class="addOrUpClss">
  <el-dialog :title="title" v-model="visible" :close-on-click-modal="false" @close="handleClose" :modal="false" @open="setReplaceList">
    <el-form :model="form.segment" ref="flightAddOrUpdateForm" >
      <el-row :gutter="10">
        <el-col :span="6">
          <el-form-item label="运营机场" prop="airportCode" :rules="[ { required: true, message: '请选择运营机场', trigger: 'blur' } ]">
            <city-name
              style="width: 100%;"
              ref="city"
              v-model="form.segment.airportCode"
              v-on:getAirportName = "setAirportCode">
            </city-name>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="执行日期" prop="opDate" :rules="[ { required: true, message: '请选择执行日期', trigger: 'blur' } ]">
            <el-date-picker v-model="form.segment.opDate" placeholder="执行日期" style="width:100%;" format="yyyy-MM-dd" :picker-options="pickerOptions" :disabled="editAble"></el-date-picker>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="公司" prop="carrier" :rules="[ { required: true, message: '请选择公司', trigger: 'blur' } ]">
            <el-select v-model="form.segment.carrier" name="carrier" placeholder="公司" style="width:100%;" filterable :disabled="editAble">
              <el-option
                v-for="item in baseList.airlines"
                :key="item.icaocode"
                :label="item.cnname+'(' + item.iatacode + '/' + item.icaocode + ')'"
                :value="item.icaocode">
                <span style="float: left">{{ item.cnname }}</span>
                <span style="float: right">{{ item.iatacode + '/' + item.icaocode }}</span>
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="航班号" prop="flightNo" :rules="[ { required: true, message: '请填写航班号', trigger: 'blur' } ]">
            <el-input v-model="form.segment.flightNo" name="flightNo" style="width:100%;" placeholder="航班号" :disabled="editAble"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="10">
        <el-col :span="6">
          <el-form-item label="任务" prop="flightTask" :rules="[ { required: true, message: '请选择任务', trigger: 'blur' } ]">
            <el-select v-model="form.segment.flightTask" placeholder="任务" filterable style="width:100%;"  filterable clearable  :disabled="editAble || operate === 'addAlt'">
              <el-option
                v-for="item in baseList.flighttasks"
                :key="item.id"
                :label="item.abbr2w + '(' + item.flightTaskCode + ')'"
                :value="item.flightTaskCode">
                <span style="float: left">{{ item.abbr2w }}</span>
                <span style="float: right">{{ item.flightTaskCode }}</span>
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="机号" prop="registration" :rules="[ { required: true, message: '请选择机号', trigger: 'blur' } ]">
            <el-select v-model="form.segment.registration" filterable remote :remote-method="regRemote" @change="setAircraftType" placeholder="机号" style="width:100%;" :disabled="editAble && operate !== 'flightAircraftChange'">
              <el-option
                v-for="item in registrations"
                :key="item.registration"
                :label="item.registration"
                :value="item.registration">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="机型" prop="aircraftType" :rules="[ { required: true, message: '请选择机型', trigger: 'blur' } ]">
            <el-select v-model="form.segment.aircraftType" placeholder="机型" style="width:100%;" filterable remote :remote-method="airTypeRemote" :disabled="editAble && operate !== 'flightAircraftChange'">
              <el-option
                v-for="item in baseList.aircraftTypes"
                :key="item.sysCode"
                :label="item.sysCode"
                :value="item.sysCode">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="进出" prop="direction" :rules="[ { required: true, message: '请选择进出', trigger: 'blur' } ]">
            <el-select v-model="form.segment.direction" name="direction" placeholder="进出" style="width:100%;" clearable  :disabled="directionDisable" filterable>
              <el-option
                v-for="item in baseList.flightdirections"
                :key="item.directionCode"
                :label="item.description"
                :value="item.directionCode">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="10">
        <el-col :span="6">
          <el-form-item label="航站楼" prop="terminal" :rules="[ { required: true, message: '请选择航站楼', trigger: 'blur' } ]">
            <el-select v-model="form.segment.terminal" placeholder="航站楼" @change="setLink" style="width:100%;" filterable clearable  :disabled="editAble">
              <el-option
                v-for="item in baseList.terminals"
                :key="item.terminalCode"
                :label="item.terminalCode"
                :value="item.terminalCode">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="总代" prop="generalAgent" :rules="[ { required: true, message: '请选择总代', trigger: 'blur' } ]">
            <el-select v-model="form.segment.generalAgent" placeholder="总代" style="width:100%;" filterable clearable  :disabled="editAble">
              <el-option
                v-for="item in baseList.generalagents"
                :key="item.agentCode"
                :label="item.description"
                :value="item.agentCode">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="VIP" prop="vipTag">
            <el-select v-model="form.segment.vipTag" placeholder="VIP" style="width:100%;" filterable clearable>
              <el-option
                v-for="item in baseList.vipranks"
                :key="item.rankCode"
                :label="item.description"
                :value="item.rankCode">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="状态" prop="flightStatus">
            <el-select v-model="form.segment.flightStatus" placeholder="状态" style="width:100%;" filterable clearable  :disabled="editAble || operate === 'addAlt'">
              <el-option
                v-for="item in baseList.flightstatus"
                :key="item.statusCode"
                :label="item.description + '(' + item.statusCode + ')'"
                :value="item.statusCode">
                <span style="float: left">{{ item.description }}</span>
                <span style="float: right">{{ item.statusCode }}</span>
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <!--只保存本站信息-->
      <el-row :gutter="10">
        <el-col :span="6">
          <el-form-item label="异常" prop="irregularStatus">
            <el-select v-model="form.segment.irregularStatus" placeholder="异常" style="width:100%;" filterable clearable  :disabled="editAble">
              <el-option
                v-for="item in baseList.flightstatus"
                :key="item.statusCode"
                :label="item.description + '(' + item.statusCode + ')'"
                :value="item.statusCode">
                <span style="float: left">{{ item.description }}</span>
                <span style="float: right">{{ item.statusCode }}</span>
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="10">
        <el-col :span="6">
          <el-form-item label="始发站" prop="origin" :rules="[ { required: true, message: '请选择始发站', trigger: 'blur' } ]">
            <el-select v-model="form.segment.origin" style="width:100%;" filterable placeholder="始发站" clearable  :disabled="editAble">
              <el-option
                v-for="item in baseList.airports"
                :key="item.icaocode"
                :label="item.cnabbr2w + '(' + item.icaocode + '/' + item.iatacode + ')'"
                :value="item.icaocode">
                <span style="float: left">{{ item.cnabbr2w }}</span>
                <span style="float: right">{{ item.icaocode + '/' + item.iatacode }}</span>
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="计划时间" prop="scheduleDepartTime">
            <br/>
            <div style="dispaly:inline">
              <el-date-picker v-model="form.segment.scheduleDepartDate" type="date" placeholder="计划日期" style="width:67%;" format="yyyy-MM-dd" :picker-options="pickerOptions" :disabled="fieldAble.scheduleDepartDate"></el-date-picker>
              <el-input v-model="form.segment.scheduleDepTime" placeholder="时间" style="width:30%;" :disabled="fieldAble.scheduleDepTime"></el-input>
            </div>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="预计时间" prop="estimateDepartTime">
            <br/>
            <div style="dispaly:inline">
              <el-date-picker v-model="form.segment.estimateDepartDate" type="date" placeholder="预计日期" style="width:67%;" format="yyyy-MM-dd" :picker-options="pickerOptions" :disabled="fieldAble.estimateDepartDate"></el-date-picker>
              <el-input v-model="form.segment.estimateDepTime" placeholder="时间" style="width:30%;" :disabled="fieldAble.estimateDepTime"></el-input>
            </div>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="实际时间" prop="actualDepartTime">
            <br/>
            <div style="dispaly:inline">
              <el-date-picker v-model="form.segment.actualDepartDate" type="date" placeholder="实际日期" style="width:67%;" format="yyyy-MM-dd" :picker-options="pickerOptions" :disabled="fieldAble.actualDepartDate"></el-date-picker>
              <el-input v-model="form.segment.actualDepTime" placeholder="时间" style="width:30%;" :disabled="fieldAble.actualDepTime"></el-input>
            </div>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="10">
        <el-col :span="6">
          <el-form-item label="目的站" prop="destination" :rules="[ { required: true, message: '请选择目的站', trigger: 'blur' } ]">
            <el-select v-model="form.segment.destination" filterable placeholder="目的站" clearable style="width:100%;" :disabled="editAble">
              <el-option
                v-for="item in baseList.airports"
                :key="item.icaocode"
                :label="item.cnabbr2w + '(' + item.icaocode + '/' + item.iatacode + ')'"
                :value="item.icaocode">
                <span style="float: left">{{ item.cnabbr2w }}</span>
                <span style="float: right">{{ item.icaocode + '/' + item.iatacode }}</span>
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="计划时间" prop="scheduleArriveTime">
            <br/>
            <div style="dispaly:inline">
              <el-date-picker v-model="form.segment.scheduleArriveDate" type="date" placeholder="计划日期" style="width:67%;" format="yyyy-MM-dd" :picker-options="pickerOptions" :disabled="fieldAble.scheduleArriveDate"></el-date-picker>
              <el-input v-model="form.segment.scheduleArrTime" placeholder="时间" style="width:30%;" :disabled="fieldAble.scheduleArrTime"></el-input>
            </div>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="预计时间" prop="estimateArriveTime">
            <br/>
            <div style="dispaly:inline">
              <el-date-picker v-model="form.segment.estimateArriveDate" type="date" placeholder="预计时间" style="width:67%;" format="yyyy-MM-dd" :picker-options="pickerOptions" :disabled="fieldAble.estimateArriveDate"></el-date-picker>
              <el-input v-model="form.segment.estimateArrTime" placeholder="时间" style="width:30%;" :disabled="fieldAble.estimateArrTime"></el-input>
            </div>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="实际时间" prop="actualArriveTime">
            <br/>
            <div style="dispaly:inline">
              <el-date-picker v-model="form.segment.actualArriveDate" type="date" placeholder="实际时间" style="width:67%;" format="yyyy-MM-dd" :picker-options="pickerOptions" :disabled="fieldAble.actualArriveDate"></el-date-picker>
              <el-input v-model="form.segment.actualArrTime" placeholder="时间" style="width:30%;" :disabled="fieldAble.actualArrTime"></el-input>
            </div>
          </el-form-item>
        </el-col>
      </el-row>

      <add-stop-over :isDiglogType="isDiglog"
        :to="params => { return this.addStopOverList(params) }"
        ref="stopOverForm"></add-stop-over>

      <el-row :gutter="10">
        <el-col :span="18" >
          <el-form-item label="共享航班" style="padding-top:10px;">
            <el-input v-model="shareFlights" name="shareFlights" placeholder="共享航班" disabled></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="4">
          <el-form-item style="padding-top:10px;">
            <br/>
            <el-button @click="showShareFlight" type="info" class="shareBtnCls" :disabled="shareAble" size="small">设置共享航班</el-button>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="10" style="margin-top: 10px;margin-bottom: 10px;" v-if="isShowIrrInfo">
        <el-col :span="48" >
          <el-form-item label="异常原因" prop="irrReason">
            <el-select v-model="form.irrReason" placeholder="异常原因" style="width:100%;" filterable>
              <el-option
                v-for="item in baseList.irregularcodes"
                :key="item.irregularCode"
                :label="item.description"
                :value="item.irregularCode">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <div v-if="isDiglog === true">
      <el-row :gutter="10" style="margin-top: 10px;margin-bottom: 10px;">
        <el-col :span="3" >
          <el-form-item label="经停站列表"></el-form-item>
        </el-col>
        <el-col :span="12" v-if="operate === 'Add' || operate === 'Edeit' || operate === 'flightArrAlt' || operate === 'addAlt'">
          <el-form-item>
            <el-button-group>
              <el-button @click="showAddStopOverView" type="success" v-if="operate !== 'addAlt'" size="small">新增</el-button>
              <el-button @click="showEditStopOverView" type="success" size="small">编辑</el-button>
              <el-button @click="cancelStopOver" type="success" size="small">取消</el-button>
              <el-button @click="delStopOver" type="danger" v-if="operate !== 'addAlt'" size="small">删除</el-button>
            </el-button-group>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="10">
        <el-table v-bind:data="form.stopOverList" highlight-current-row v-loading="tableLoading" border style="width: 100%" :row-class-name="setRowClass" height="200" @current-change="handleCurrentChange" ref="stopOverTable" append >
          <el-table-column prop="index" label="顺序" width="45"></el-table-column>
          <el-table-column prop="destination" label="经停站" width="80">
            <template scope="scope" >
              {{$cache.findByName('airports', 'icaocode', scope.row.destination, 'cnabbr2w')}}
            </template>
          </el-table-column>
          <el-table-column prop="scheduleArriveTime" label="计划到达" :formatter="dateformat" width="130"></el-table-column>
          <el-table-column prop="estimateArriveTime" label="预计到达" :formatter="dateformat" width="130"></el-table-column>
          <el-table-column prop="actualArriveTime" label="实际到达" :formatter="dateformat" width="130"></el-table-column>
          <el-table-column prop="scheduleDepartTime" label="计划起飞" :formatter="dateformat" width="130"></el-table-column>
          <el-table-column prop="estimateDepartTime" label="预计起飞" :formatter="dateformat" width="130"></el-table-column>
          <el-table-column prop="actualDepartTime" label="实际起飞" :formatter="dateformat" min-width="130"></el-table-column>
        </el-table>
      </el-row>
      </div>

    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click.native="handleClose">取消</el-button>
      <el-button @click.native="resetForm('flightAddOrUpdateForm')" v-if="operate === 'Add' || operate === 'Edeit' || operate === 'flightArrAlt'">重置</el-button>
      <el-button type="primary" @click.native="handleSubmit" :loading="loading">提交</el-button>
    </div>

    <!--新增经停-->
    <add-stop-over :isDiglogType="isDiglog" v-if="isDiglog"
      :to="params => { return this.addStopOverList(params) }"
      ref="stopOverAddForm"></add-stop-over>

    <!--编辑经停-->
    <add-stop-over :isDiglogType="isDiglog" v-if="isDiglog"
      :to="params => { return this.addStopOverList(params) }"
      ref="stopOverEditForm"></add-stop-over>

    <!--设置共享航班-->
    <share-flight
      :to="params => { return this.setShareFlightLs(params) }"
      ref="shareFlightForm"></share-flight>

  </el-dialog>
  </div>
</div>
</template>

<script>
import Util from '../../../common/js/util'
import Butil from '../../../common/js/base-util'
import API from '../../../api'
import AddStopOver from '../dynamicFlightForm/AddStopOver'
import DynamicFlight from '../DynamicFlight'
import shareFlight from '../planFlightForm/ShareFlightForm'
import CityName from '../../../components/CityName'

export default {
  props: {
    title: {
      type: String,
      default: '新增'
    },
    to: {
      type: Function,
      default: function () {}
    },
    callback: {
      type: Function,
      default: function () {}
    }
  },
  data () {
    return {
      addOrUpClss: 'normalClass',
      dialogClss: 'smallDigCls',
      isDiglog: true,
      editAble: false,
      visible: false,
      loading: false,
      operate: 'Add',
      optName: '',
      tableLoading: false,
      directionDisable: false,
      API: API,
      form: {
        segment: {
          id: null,
          flightId: null,
          linkedFlightId: null,
          combineFlightId: null,
          airportCode: null,
          direction: null,
          opDate: '',
          terminal: null,
          stand: null,
          boardingStand: null,
          vipTag: null,
          gate: null,
          generalAgent: null,
          carrier: null,
          flightNo: null,
          registration: null,
          aircraftType: null,
          flightStatus: null,
          flightTask: null,
          irregularStatus: null,
          origin: null,
          scheduleDepartTime: null,
          scheduleDepartDate: null,
          scheduleDepTime: null,
          estimateDepartTime: null,
          estimateDepartDate: null,
          estimateDepTime: null,
          actualDepartTime: null,
          actualDepartDate: null,
          actualDepTime: null,
          destination: null,
          scheduleArriveTime: null,
          scheduleArriveDate: null,
          scheduleArrTime: null,
          estimateArriveTime: null,
          estimateArriveDate: null,
          estimateArrTime: null,
          actualArriveTime: null,
          actualArriveDate: null,
          actualArrTime: null,
          segmentIndex: null,
          segmentNature: null,
          flightNature: null
        },
        stopOverList: [],
        irrReason: null
      },
      pickerOptions: {
        shortcuts: [{
          text: '昨天',
          onClick (picker) {
            const date = new Date()
            date.setTime(date.getTime() - 3600 * 1000 * 24)
            picker.$emit('pick', date)
          }
        }, {
          text: '今天',
          onClick (picker) {
            picker.$emit('pick', new Date())
          }
        }, {
          text: '明天',
          onClick (picker) {
            const date = new Date()
            date.setTime(date.getTime() + 3600 * 1000 * 24)
            picker.$emit('pick', date)
          }
        }]
      },
      currentSopOver: null,
      flightlist: [],
      oldflightlist: [],
      segmentNature1List: [],
      firstSeg: null,
      lastSeg: null,
      baseList: [],
      registrations: [],
      allRegistrationList: [],
      stopOver: {id: null, flightId: null, flightStatus: '', irregularStatus: '', boardingStand: '', stand: '', gate: '', index: 0, destination: '', flightNature: '', segmentNature: '', scheduleDepartTime: null, estimateDepartTime: null, actualDepartTime: null, scheduleArriveTime: null, estimateArriveTime: null, actualArriveTime: null},
      segList: [],
      editStopOverList: [],
      delStopOverList: [],
      isShowIrrInfo: false,
      curSelectRow: [],
      irrObj: {id: null, flightId: null, irregularStatus: null, irregularCode: null, publisher: null, localReason: null, remark: null},
      fieldAble: {
        scheduleDepartDate: false,
        scheduleDepTime: false,
        estimateDepartDate: false,
        estimateDepTime: false,
        actualDepartDate: false,
        actualDepTime: false,
        destination: false,
        scheduleArriveDate: false,
        scheduleArrTime: false,
        estimateArriveDate: false,
        estimateArrTime: false,
        actualArriveDate: false,
        actualArrTime: false
      },
      sysUserId: null,
      delIdList: [],
      subAirportLs: [],
      shareFlights: '',
      oldShareFlightLs: [],
      shareFlightLs: [],
      deleteShareIdList: []
    }
  },
  components: {
    addStopOver: AddStopOver,
    DynamicFlight: DynamicFlight,
    shareFlight: shareFlight,
    CityName: CityName
  },
  computed: {
    shareAble () {
      // 设置是否禁用共享航班设置按钮
      if (this.operate === 'Add' || this.operate === 'Edeit') {
        return false
      } else {
        return true
      }
    }
  },
  methods: {
    setClassName: function () {
      this.addOrUpClss = ((this.operate !== 'Add' && this.operate !== 'addAlt') ? 'signClass' : 'normalClass') + (document.body.clientWidth <= 1366 ? ' largeDigCls' : ' smallDigCls')
    },
    bindData () {
    },
    show: function (row, airportCode) {
      this.showAirportInfo(this.subAirportLs)
      // this.bindData()
      this.directionDisable = false
      if (row !== undefined && row !== null) {
        this.curSelectRow = Util.deepCopy(row)
        // 添加航班其他属性信息
        // this.operate = 'Edeit'
        this.setDefauleDate(null)
        this.form.segment = Object.assign({}, this.form.segment, row)
        this.setAirportInfo(this.form.segment.airportCode)
        this.directionDisable = true
        this.loading = true
        let para = { pageSize: 0, flightId: row.flightId }
        API.getDynamicFlight().go(para).then((data) => {
          if (data.ok) {
            this.oldflightlist = data.attr.data.list
            // 实航段
            let list = this.oldflightlist.filter(item => {
              return item.segmentNature !== 0
            })

            // 按segmentIndex排序
            list.sort(function (a, b) {
              return a.segmentIndex - b.segmentIndex
            })
            this.segmentNature1List = Util.deepCopy(list)
            // 绑定显示字段数据
            this.firstSeg = Util.deepCopy(this.segmentNature1List[0])
            this.lastSeg = Util.deepCopy(this.segmentNature1List[this.segmentNature1List.length - 1])
            this.bindSegment()
            // 绑定经停站
            this.form.stopOverList = this.bindStopOverLs()
            // 设置共享航班信息
            this.shareFlightLs = data.attr.data.shareFlightLs
            if (data.attr.data.isShareFlight) {
              this.shareFlightLs = data.attr.data.shareFlightLs
            } else {
              this.shareFlights = ''
              this.shareFlightLs = []
            }
            this.oldShareFlightLs = Util.deepCopy(this.shareFlightLs)
            this.setShareFlightInfo()
          } else {
            this.$notify(Util.notifyBody(false, data.msg))
          }
          this.loading = false
        })
      } else {
        // this.operate = 'Add'
        // 新增设置默认值为空、航班设置日期默认当前日期
        this.initData(airportCode)
        this.setDefauleDate(new Date())
      }
      this.visible = true
      this.loading = false
    },
    bindSegment: function () {
      this.form.segment.direction = this.firstSeg.direction
      this.form.segment.opDate = this.firstSeg.opDate
      this.form.segment.terminal = this.firstSeg.terminal
      this.form.segment.generalAgent = this.firstSeg.generalAgent
      this.form.segment.carrier = this.firstSeg.carrier
      this.form.segment.flightTask = this.firstSeg.flightTask
      this.form.segment.flightNo = this.firstSeg.flightNo
      this.form.segment.registration = this.firstSeg.registration
      this.form.segment.aircraftType = this.firstSeg.aircraftType
      this.form.segment.origin = this.firstSeg.origin
      this.form.segment.scheduleDepartTime = this.firstSeg.scheduleDepartTime
      this.form.segment.scheduleDepartDate = this.firstSeg.scheduleDepartTime
      this.form.segment.scheduleDepTime = this.$refs['stopOverAddForm'].isNotNull(this.firstSeg.scheduleDepartTime) ? this.firstSeg.scheduleDepartTime.substring(11, 16).replace(':', '') : null
      this.form.segment.estimateDepartTime = this.firstSeg.estimateDepartTime
      this.form.segment.estimateDepartDate = this.firstSeg.estimateDepartTime
      this.form.segment.estimateDepTime = this.$refs['stopOverAddForm'].isNotNull(this.firstSeg.estimateDepartTime) ? this.firstSeg.estimateDepartTime.substring(11, 16).replace(':', '') : null
      this.form.segment.actualDepartTime = this.firstSeg.actualDepartTime
      this.form.segment.actualDepartDate = this.firstSeg.actualDepartTime
      this.form.segment.actualDepTime = this.$refs['stopOverAddForm'].isNotNull(this.firstSeg.actualDepartTime) ? this.firstSeg.actualDepartTime.substring(11, 16).replace(':', '') : null
      this.form.segment.destination = this.lastSeg.destination
      this.form.segment.scheduleArriveTime = this.lastSeg.scheduleArriveTime
      this.form.segment.scheduleArriveDate = this.lastSeg.scheduleArriveTime
      this.form.segment.scheduleArrTime = this.$refs['stopOverAddForm'].isNotNull(this.lastSeg.scheduleArriveTime) ? this.lastSeg.scheduleArriveTime.substring(11, 16).replace(':', '') : null
      this.form.segment.estimateArriveTime = this.lastSeg.estimateArriveTime
      this.form.segment.estimateArriveDate = this.lastSeg.estimateArriveTime
      this.form.segment.estimateArrTime = this.$refs['stopOverAddForm'].isNotNull(this.lastSeg.estimateArriveTime) ? this.lastSeg.estimateArriveTime.substring(11, 16).replace(':', '') : null
      this.form.segment.actualArriveTime = this.lastSeg.actualArriveTime
      this.form.segment.actualArriveDate = this.lastSeg.actualArriveTime
      this.form.segment.actualArrTime = this.$refs['stopOverAddForm'].isNotNull(this.lastSeg.actualArriveTime) ? this.lastSeg.actualArriveTime.substring(11, 16).replace(':', '') : null
      if (this.form.segment.direction === 'A') {
        this.form.segment.flightStatus = this.lastSeg.flightStatus
        this.form.segment.irregularStatus = this.lastSeg.irregularStatus
        this.form.segment.boardingStand = this.lastSeg.boardingStand
        this.form.segment.stand = this.lastSeg.stand
        this.form.segment.gate = this.lastSeg.gate
      }
    },
    bindStopOverLs: function () {
      var stoplist = []
      for (var i = 0; i < this.segmentNature1List.length - 1; i++) {
        let stop = Util.deepCopy(this.stopOver)
        stop.index = i + 1
        // 0825 设置经停站其他时间信息(直接copy整个实体 0827)
        stop.id = this.segmentNature1List[i].id
        stop.flightId = this.segmentNature1List[i].flightId
        stop.destination = this.segmentNature1List[i].destination
        stop.flightNature = this.segmentNature1List[i].flightNature
        stop.scheduleArriveTime = this.segmentNature1List[i].scheduleArriveTime
        stop.estimateArriveTime = this.segmentNature1List[i].estimateArriveTime
        stop.actualArriveTime = this.segmentNature1List[i].actualArriveTime
        stop.scheduleDepartTime = this.segmentNature1List[i + 1].scheduleDepartTime
        stop.estimateDepartTime = this.segmentNature1List[i + 1].estimateDepartTime
        stop.actualDepartTime = this.segmentNature1List[i + 1].actualDepartTime
        // 0830
        stop.segmentNature = this.segmentNature1List[i].segmentNature
        stop.flightStatus = this.segmentNature1List[i].flightStatus
        stop.irregularStatus = this.segmentNature1List[i].irregularStatus
        stop.boardingStand = this.segmentNature1List[i].boardingStand
        stop.stand = this.segmentNature1List[i].stand
        stop.gate = this.segmentNature1List[i].gate
        stoplist[i] = stop
      }
      return stoplist
    },
    initData: function (airportCode) {
      for (var key in this.form.segment) {
        this.form.segment[key] = null
      }
      if (airportCode !== undefined) {
        // 设置航班运营机场
        this.setAirportInfo(airportCode)
        this.form.segment.airportCode = airportCode
      }
      if (this.operate === 'addAlt') {
        // 新增备降，设置经停站(与用户管辖权限进行绑定) 任务备降
        this.directionDisable = true
        this.form.segment.flightTask = 'A/N'
        // 0828 经停站显示
        this.visible = true
        this.$nextTick(() => {
          this.$refs['stopOverForm'].initData()
          this.$refs['stopOverForm'].setDefaultDate()
          this.$refs['stopOverForm'].stopOver.destination = airportCode
          this.$refs['stopOverForm'].destinationDisable = true
          this.$refs['stopOverForm'].index = this.form.stopOverList.length + 1
        })
      } else {
        this.form.stopOverList = []
      }
      this.shareFlights = ''
      this.oldShareFlightLs = []
      this.shareFlightLs = []
      this.curSelectRow = []
      this.form.irrReason = null
      this.irrReason = null
      this.delIdList = []
    },
    setDefauleDate: function (date) {
      if (date === null) {
        let depTimeEditAble = {scheduleDepartDate: '', scheduleDepTime: '', estimateDepartDate: '', estimateDepTime: '', actualDepartDate: '', actualDepTime: ''}
        let arrTimeEditAble = {scheduleArriveDate: '', scheduleArrTime: '', estimateArriveDate: '', estimateArrTime: '', actualArriveDate: '', actualArrTime: ''}
        this.form.segment = Object.assign({}, this.form.segment, depTimeEditAble, arrTimeEditAble)
      } else {
        this.form.segment.opDate = Util.formatDate.format(date, 'yyyy-MM-dd')
        this.form.segment.scheduleDepartTime = Util.formatDate.format(date, 'yyyy-MM-dd')
        this.form.segment.scheduleDepartDate = Util.formatDate.format(date, 'yyyy-MM-dd')
        this.form.segment.scheduleDepTime = ''
        this.form.segment.estimateDepartTime = Util.formatDate.format(date, 'yyyy-MM-dd')
        this.form.segment.estimateDepartDate = Util.formatDate.format(date, 'yyyy-MM-dd')
        this.form.segment.estimateDepTime = ''
        this.form.segment.actualDepartTime = Util.formatDate.format(date, 'yyyy-MM-dd')
        this.form.segment.actualDepartDate = Util.formatDate.format(date, 'yyyy-MM-dd')
        this.form.segment.actualDepTime = ''
        this.form.segment.scheduleArriveTime = Util.formatDate.format(date, 'yyyy-MM-dd')
        this.form.segment.scheduleArriveDate = Util.formatDate.format(date, 'yyyy-MM-dd')
        this.form.segment.scheduleArrTime = ''
        this.form.segment.estimateArriveTime = Util.formatDate.format(date, 'yyyy-MM-dd')
        this.form.segment.estimateArriveDate = Util.formatDate.format(date, 'yyyy-MM-dd')
        this.form.segment.estimateArrTime = ''
        this.form.segment.actualArriveTime = Util.formatDate.format(date, 'yyyy-MM-dd')
        this.form.segment.actualArriveDate = Util.formatDate.format(date, 'yyyy-MM-dd')
        this.form.segment.actualArrTime = ''
      }
    },
    handleClose: function () {
      this.visible = false
      this.editAble = false
      this.delIdList = []
      this.$refs['flightAddOrUpdateForm'].resetFields()
    },
    handleSubmit: function () {
      if (this.operate === 'Add' || this.operate === 'Edeit') {
        // 新增或编辑操作
        this.handAddOrUptSubmit()
      } else if (this.operate === 'flightArrAlt') {
        // 进港备降提交
        this.addFlightArrAltSubmit()
      } else if (this.operate === 'addAlt') {
        // 新增备降提交
        this.addFlightAltSubmit()
      } else if (this.operate === 'flightDepRtn') {
        // 出港返航提交
        this.handFlightDepRtn()
      } else {
        this.handFlightSimpleCommand()
      }
    },
    handFlightSimpleCommand: function () {
      // 异常原因登记操作
      let newFlightls = []
      let oldFlightls = []
      let irrReason = {id: null, flightId: this.curSelectRow.id, irregularStatus: null, irregularCode: this.form.irrReason, publisher: this.sysUserId, localReason: null, remark: null}
      let para = {}
      let isNeedIrregular = false
      oldFlightls[0] = Util.deepCopy(this.curSelectRow)
      newFlightls[0] = Util.deepCopy(this.curSelectRow)
      switch (this.operate) {
        case 'flightCnl': {
          newFlightls[0].flightStatus = 'CNL'
          newFlightls[0].irregularStatus = 'CNL'
          // 获取当前用户信息 待修整 0822
          irrReason.irregularStatus = 'CNL'
          isNeedIrregular = true
          para = Object.assign({}, para, {irregularInfo: irrReason})
          break
        }
        case 'flightDly': {
          newFlightls[0].irregularStatus = 'DLY'
          newFlightls[0].estimateDepartTime = this.toDateStr(this.form.segment.estimateDepartDate)
          newFlightls[0].estimateDepartTime = this.toTimeStr(newFlightls[0].estimateDepartTime, this.form.segment.estimateDepTime)
          // let irrReason = {irrReason: this.form.irrReason}
          irrReason.irregularStatus = 'DLY'
          isNeedIrregular = true
          para = Object.assign({}, para, {irregularInfo: irrReason})
          break
        }
        case 'flightAircraftChange': {
          newFlightls[0].registration = this.form.segment.registration
          newFlightls[0].aircraftType = this.form.segment.aircraftType
          break
        }
        case 'flightArrRtn': {
          newFlightls[0].flightStatus = null
          newFlightls[0].irregularStatus = 'RTN'
          newFlightls[0].actualDepartTime = null
          newFlightls[0].estimateArriveTime = null
          // let irrReason = {irrReason: this.form.irrReason}
          irrReason.irregularStatus = 'RTN'
          isNeedIrregular = true
          para = Object.assign({}, para, {irregularInfo: irrReason})
          break
        }
        default: break
      }

      para = Object.assign({}, para, { newValue: newFlightls, oldValue: oldFlightls, operate: this.operate, isNeedIrregular: isNeedIrregular })
      // 异常原因处理，待测试
      this.$refs['flightAddOrUpdateForm'].validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.loading = true
            API.operateFlightData(this.operate, this.optName).go(para).then((data) => {
              this.loading = false
              this.$notify(Util.notifyBody(data.ok, data.msg))
              this.resetForm('flightAddOrUpdateForm')
              this.visible = false
              this.callback()
            })
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消提交'
            })
          })
        }
      })
    },
    handAddOrUptSubmit: function () {
      // 新增，将始发、经停站、目的站拼成segList
      let seg = Util.deepCopy(this.stopOver)
      seg.destination = this.form.segment.origin
      // 0831
      seg.id = this.operate === 'Edeit' ? this.firstSeg.id : null
      seg.scheduleDepartTime = this.toDateStr(this.form.segment.scheduleDepartDate)
      seg.estimateDepartTime = this.toDateStr(this.form.segment.estimateDepartDate)
      seg.actualDepartTime = this.toDateStr(this.form.segment.actualDepartDate)

      seg.scheduleDepartTime = this.toTimeStr(seg.scheduleDepartTime, this.form.segment.scheduleDepTime)
      seg.estimateDepartTime = this.toTimeStr(seg.estimateDepartTime, this.form.segment.estimateDepTime)
      seg.actualDepartTime = this.toTimeStr(seg.actualDepartTime, this.form.segment.actualDepTime)

      this.form.segment.scheduleDepartTime = seg.scheduleDepartTime
      this.form.segment.estimateDepartTime = seg.estimateDepartTime
      this.form.segment.actualDepartTime = seg.actualDepartTime
      this.segList = []
      this.segList[0] = seg
      for (var i = 0; i < this.form.stopOverList.length; i++) {
        // 0825 保存航段信息
        let se = Util.deepCopy(this.stopOver)
        se = Object.assign({}, this.form.stopOverList[i])
        this.segList[i + 1] = se
      }
      let setLast = Util.deepCopy(this.stopOver)
      setLast.id = this.operate === 'Edeit' ? this.lastSeg.id : null
      setLast.destination = this.form.segment.destination
      // 0828 设置航班属性
      this.form.segment.flightNature = this.setFlightNature(setLast.destination)
      setLast.scheduleArriveTime = this.toDateStr(this.form.segment.scheduleArriveDate)
      setLast.estimateArriveTime = this.toDateStr(this.form.segment.estimateArriveDate)
      setLast.actualArriveTime = this.toDateStr(this.form.segment.actualArriveDate)

      setLast.scheduleArriveTime = this.toTimeStr(setLast.scheduleArriveTime, this.form.segment.scheduleArrTime)
      setLast.estimateArriveTime = this.toTimeStr(setLast.estimateArriveTime, this.form.segment.estimateArrTime)
      setLast.actualArriveTime = this.toTimeStr(setLast.actualArriveTime, this.form.segment.actualArrTime)
      // 保存航段的状态、异常、机位等信息
      setLast.flightStatus = (this.operate === 'Edeit' ? this.lastSeg.flightStatus : null)
      setLast.irregularStatus = (this.operate === 'Edeit' ? this.lastSeg.irregularStatus : null)
      setLast.boardingStand = (this.operate === 'Edeit' ? this.lastSeg.boardingStand : null)
      setLast.stand = (this.operate === 'Edeit' ? this.lastSeg.stand : null)
      setLast.gate = (this.operate === 'Edeit' ? this.lastSeg.gate : null)

      this.form.segment.scheduleArriveTime = setLast.scheduleArriveTime
      this.form.segment.estimateArriveTime = setLast.estimateArriveTime
      this.form.segment.actualArriveTime = setLast.actualArriveTime
      this.segList[this.form.stopOverList.length + 1] = setLast
      // 循环 拼接实航段和虚航段 flightlist
      var segIndex = 0
      var index = 0
      this.flightlist = []
      for (var x = 0; x < this.segList.length; x++) {
        for (var y = x + 1; y < this.segList.length; y++) {
          let s = (x + 1 === y ? Object.assign({}, this.form.segment, this.segList[y]) : Util.deepCopy(this.form.segment))
          if (this.operate === 'Edeit') {
            s.flightId = this.firstSeg.flightId
            s.linkedFlightId = this.firstSeg.linkedFlightId
            s.combineFlightId = this.firstSeg.combineFlightId
          }
          s.opDate = typeof this.form.segment.opDate === 'object' ? Util.formatDate.format(this.form.segment.opDate, 'yyyy-MM-dd') : this.form.segment.opDate
          s.origin = this.segList[x].destination
          s.destination = this.segList[y].destination
          // 保存航班区域属性
          s.flightNature = this.setFlightNature(s.destination)
          s.scheduleDepartTime = this.segList[x].scheduleDepartTime
          s.scheduleArriveTime = this.segList[y].scheduleArriveTime
          // 分别保存航段的起飞/到达时间
          s.estimateDepartTime = this.segList[x].estimateDepartTime
          s.actualDepartTime = this.segList[x].actualDepartTime
          s.estimateArriveTime = this.segList[y].estimateArriveTime
          s.actualArriveTime = this.segList[y].actualArriveTime
          // 设置航班航段index以及nature
          // 0909 修改实航段
          if (x + 1 === y) {
            // 实航段
            if (this.segList[y]['segmentNature'] === 3 || this.segList[y]['segmentNature'] === 2) {
              // 取消、经停航段
              s.segmentNature = this.segList[y]['segmentNature']
            } else {
              s.segmentNature = 1
            }

            s.id = this.segList[y].id
            s.segmentIndex = segIndex
            segIndex = segIndex + 1
          } else {
            // 虚航段
            s.id = null
            s.segmentNature = 0
            s.segmentIndex = 0
          }
          this.flightlist[index] = s
          index = index + 1
        }
      }
      if (this.operate === 'Edeit') {
        // 修改
        // 实航段
        if (this.delIdList.length > 0 && this.flightlist.length === 1) {
          this.flightlist[0].id = null
        }
      }
      var para = { newValue: this.flightlist, oldValue: this.oldflightlist, deleteIdList: this.delIdList, shareFlightLs: this.shareFlightLs, deleteShareIdList: this.deleteShareIdList }
      this.$refs['flightAddOrUpdateForm'].validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.loading = true
            this.to(para).then((res) => {
              this.loading = false
              this.$notify(Util.notifyBody(res.ok, res.msg))
              this.resetForm('flightAddOrUpdateForm')
              this.visible = false
              this.callback()
            })
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消提交'
            })
          })
        }
      })
    },
    regRemote: function (query) {
      if (query !== '' && query !== null && query !== undefined) {
        let para = {pageSize: 99999}
        API.searchReg(query.toLowerCase()).go(para).then((data) => {
          this.registrations = data.list
        })
      } else {
        this.registrations = []
      }
    },
    airTypeRemote: function (query) {
      if (query !== '' && query !== null && query !== undefined) {
        this.loading = true
        setTimeout(() => {
          this.loading = false
          let allAirTypeList = this.$cache.fetch('aircraftTypes')
          this.baseList.aircraftTypes = allAirTypeList.filter(item => {
            return item.sysCode !== '' && item.sysCode !== null && item.sysCode.toLowerCase().indexOf(query.toLowerCase()) > -1
          })
        }, 200)
      } else {
        this.baseList.aircraftTypes = []
      }
    },
    handleCurrentChange (val) {
      this.currentSopOver = val
    },
    delStopOver: function () {
      if (this.currentSopOver === null) {
        this.$message({
          type: 'info',
          message: '请选中要删除的经停站'
        })
      } else {
        // 循环方式删除经停站，并修改后续经停站index值
        let newList = []
        for (var i = 0; i < this.form.stopOverList.length; i++) {
          if (this.form.stopOverList[i].index < this.currentSopOver.index) {
            newList[i] = this.form.stopOverList[i]
          } else if (this.form.stopOverList[i].index > this.currentSopOver.index) {
            this.form.stopOverList[i].index = this.form.stopOverList[i].index - 1
            newList[i - 1] = this.form.stopOverList[i]
          } else if (this.form.stopOverList[i].id !== null) {
            // 与删除的经停站相邻的站点航段也删除
            this.delIdList[this.delIdList.length] = this.form.stopOverList[i].id
            if (i + 1 < this.form.stopOverList.length && this.form.stopOverList[i + 1].id !== null) {
              this.delIdList[this.delIdList.length] = this.form.stopOverList[i + 1].id
            }
            if (this.currentSopOver.index < this.form.stopOverList.length) {
              // 相邻的经停站id重新插入，设置id为null
              this.form.stopOverList[this.currentSopOver.index].id = null
            }
          }
        }
        if (this.form.stopOverList.length === 1 && this.delIdList.length > 0) {
          // 删除所有存在的经停站
          this.delIdList[this.delIdList.length] = this.lastSeg.id
        }
        this.form.stopOverList = newList
      }
    },
    addStopOverList (stopover) {
      // 新增经停
      if (stopover !== undefined) {
        let data = Util.deepCopy(this.form.stopOverList)
        data[stopover.index - 1] = Object.assign({}, data[stopover.index - 1], stopover)
        this.form.stopOverList = data
        return {
          ok: true,
          msg: '成功'
        }
      } else {
        return {
          ok: false,
          msg: '失败'
        }
      }
    },
    showAddStopOverView: function () {
      let flightId = null
      if (this.form.segment.flightId !== null) {
        flightId = this.form.segment.flightId
      }
      this.$refs['stopOverAddForm'].show(this.form.stopOverList.length + 1, flightId)
    },
    showEditStopOverView: function () {
      // 经停站编辑界面展示
      let row = this.currentSopOver
      if (row !== undefined && row !== null) {
        this.$refs['stopOverEditForm'].show(this.currentSopOver.index, row.flightId, row)
      } else {
        this.$message({
          type: 'info',
          message: '请选中要编辑的经停站'
        })
      }
    },
    cancelStopOver: function () {
      // 经停站取消 0827
      if (this.currentSopOver !== undefined && this.currentSopOver !== null) {
        this.$confirm('确认取消该经停站吗？', '提示', {}).then(() => {
          this.form.stopOverList[this.currentSopOver.index - 1]['segmentNature'] = 3
          this.$message({
            type: 'info',
            message: '已取消该经停站'
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消提交'
          })
        })
      } else {
        this.$message({
          type: 'info',
          message: '请选中要取消的经停站'
        })
      }
    },
    resetForm (formName) {
      this.form = {
        segment: {
          airportCode: null,
          direction: null,
          opDate: '',
          terminal: null,
          stand: null,
          boardingStand: null,
          vipTag: null,
          gate: null,
          generalAgent: null,
          carrier: null,
          flightNo: null,
          registration: null,
          aircraftTypes: null,
          flightStatus: null,
          flightTask: null,
          irregularStatus: null,
          origin: null,
          scheduleDepartTime: null,
          scheduleDepartDate: null,
          scheduleDepTime: null,
          estimateDepartTime: null,
          estimateDepartDate: null,
          estimateDepTime: null,
          actualDepartTime: null,
          actualDepartDate: null,
          actualDepTime: null,
          destination: null,
          scheduleArriveTime: null,
          scheduleArriveDate: null,
          scheduleArrTime: null,
          estimateArriveTime: null,
          estimateArriveDate: null,
          estimateArrTime: null,
          actualArriveTime: null,
          actualArriveDate: null,
          actualArrTime: null,
          segmentIndex: null,
          segmentNature: null,
          flightNature: null
        },
        stopOverList: [],
        irrReason: null
      }
      // 以下重置方法为撤回至初始状态
      // this.$refs[formName].resetFields()
      // this.form.stopOverList = []
      // this.form.irrReason = null
    },
    setAircraftType: function (val) {
      let list = []
      list = this.registrations.filter(item => {
        return item.registration === val
      })
      if (list.length > 0) {
        this.form.segment.aircraftType = list[0].aircraftType
      }
    },
    toDateStr: function (data) {
      // 转化为YYYY-mm-dd
      return typeof data === 'object' ? Util.formatDate.format(data, 'yyyy-MM-dd') : (this.$refs['stopOverAddForm'].isNotNull(data) ? data.substring(0, 10) : null)
    },
    toTimeStr: function (date, time) {
      // 转化为HHmm
      return this.$refs['stopOverAddForm'].isNotNull(date) ? (date + (this.$refs['stopOverAddForm'].isNotNull(time) ? (' ' + time.substring(0, 2) + ':' + time.substring(2, 4) + ':00') : '')) : null
    },
    showCurFlight: function (row) {
      // this.bindData()
      this.directionDisable = false
      this.showAirportInfo(this.subAirportLs)
      if (row !== undefined) {
        this.curSelectRow = Util.deepCopy(row)
        this.setAirportInfo(this.curSelectRow.airportCode)
        this.directionDisable = true
        this.loading = true
        this.firstSeg = null
        this.lastSeg = null
        this.form.segment = Object.assign({}, this.form.segment, row)
        this.form.segment.irregularStatus = 'ALT'
        this.form.segment.flightStatus = null
        this.toDateStr(new Date())

        this.form.segment.scheduleDepartDate = this.curSelectRow.scheduleDepartTime
        this.form.segment.scheduleDepTime = this.isNotNull(this.curSelectRow.scheduleDepartTime) ? this.curSelectRow.scheduleDepartTime.substring(11, 16).replace(':', '') : null

        this.form.segment.estimateDepartDate = this.curSelectRow.estimateDepartTime
        this.form.segment.estimateDepTime = this.isNotNull(this.curSelectRow.estimateDepartTime) ? this.curSelectRow.estimateDepartTime.substring(11, 16).replace(':', '') : null

        this.form.segment.actualDepartDate = this.curSelectRow.actualDepartTime
        this.form.segment.actualDepTime = this.isNotNull(this.curSelectRow.actualDepartTime) ? this.curSelectRow.actualDepartTime.substring(11, 16).replace(':', '') : null

        this.form.segment.scheduleArriveDate = this.curSelectRow.scheduleArriveTime
        this.form.segment.scheduleArrTime = this.isNotNull(this.curSelectRow.scheduleArriveTime) ? this.curSelectRow.scheduleArriveTime.substring(11, 16).replace(':', '') : null

        this.form.segment.estimateArriveDate = this.curSelectRow.estimateArriveTime
        this.form.segment.estimateArrTime = this.isNotNull(this.curSelectRow.estimateArriveTime) ? this.curSelectRow.estimateArriveTime.substring(11, 16).replace(':', '') : null

        this.form.segment.actualArriveDate = this.curSelectRow.actualArriveTime
        this.form.segment.actualArrTime = this.isNotNull(this.curSelectRow.actualArriveTime) ? this.curSelectRow.actualArriveTime.substring(11, 16).replace(':', '') : null
        this.form.stopOverList = []
        // 进港备降经停站显示 0828
        this.form.irrReason = null
        this.loading = false
      }
      this.visible = true
      this.$nextTick(() => {
        this.$refs['stopOverForm'].initData()
        this.$refs['stopOverForm'].setDefaultDate()
        this.$refs['stopOverForm'].destinationDisable = false
        this.$refs['stopOverForm'].index = this.form.stopOverList.length + 1
      })
    },
    showFlightDepRtn: function (row, airportCode) {
      // 出港返航编辑界面展示 0821
      this.curSelectRow = Util.deepCopy(row)
      this.showAirportInfo(this.subAirportLs)
      this.setAirportInfo(this.curSelectRow.airportCode)
      this.oldflightlist[0] = row
      this.directionDisable = true
      this.loading = true
      this.firstSeg = null
      this.lastSeg = null
      this.form.segment = Object.assign({}, this.form.segment, this.curSelectRow)
      this.form.segment.origin = airportCode
      this.form.segment.destination = airportCode
      this.form.segment.direction = 'A'
      this.form.segment.flightTask = 'F/H'
      this.form.segment.irregularStatus = 'RTN'

      let now = new Date()
      let datetime = Util.formatDate.format(now, 'yyyy-MM-dd hh:mm:ss')
      let date = Util.formatDate.format(now, 'yyyy-MM-dd')
      let time = Util.formatDate.format(now, 'hhmm')
      var moment = require('moment')
      // 设置计划到达时间-延迟10分钟
      let scheduleArriveTime = moment(new Date(datetime)).add(10, 'minutes')
      let segDepTimeAttr = {scheduleDepartTime: datetime, scheduleDepartDate: date, scheduleDepTime: time, estimateDepartTime: null, estimateDepartDate: null, estimateDepTime: null, actualDepartTime: null, actualDepartDate: null, actualDepTime: null}
      let segArrTimeAttr = {scheduleArriveTime: scheduleArriveTime.format('YYYY-MM-DD HH:mm:ss'), scheduleArriveDate: scheduleArriveTime.format('YYYY-MM-DD'), scheduleArrTime: scheduleArriveTime.format('HHmm'), estimateArriveTime: null, estimateArriveDate: null, estimateArrTime: null, actualArriveTime: null, actualArriveDate: null, actualArrTime: null}

      this.form.segment = Object.assign({}, this.form.segment, segDepTimeAttr, segArrTimeAttr)
      this.form.stopOverList = []
      this.form.irrReason = null
      this.loading = false
      this.visible = true
    },
    addFlightArrAltSubmit: function () {
      // 进港备降
      let newFlightls = []
      let oldFlightls = []
      oldFlightls[0] = Util.deepCopy(this.curSelectRow)
      newFlightls[0] = Util.deepCopy(this.curSelectRow)
      newFlightls[0].flightStatus = null
      newFlightls[0].irregularStatus = 'ALT'
      newFlightls[0].estimateArriveTime = null
      newFlightls[0].segmentNature = '0'
      // 新增航段 1.原起始-备降 2 备降-原目的 航段属性2
      newFlightls[1] = Util.deepCopy(this.curSelectRow)
      newFlightls[2] = Util.deepCopy(this.curSelectRow)

      // 0828 设置经停站信息
      this.setStopOverFormDate()
      newFlightls[1].destination = this.form.stopOverList[0].destination
      newFlightls[1].scheduleArriveTime = this.form.stopOverList[0].scheduleArriveTime
      newFlightls[1].estimateArriveTime = this.form.stopOverList[0].estimateArriveTime
      newFlightls[1].actualArriveTime = this.form.stopOverList[0].actualArriveTime
      newFlightls[1].segmentNature = '2'
      newFlightls[1].segmentIndex = this.curSelectRow.segmentIndex

      newFlightls[2].origin = this.form.stopOverList[0].destination
      newFlightls[2].flightStatus = null
      newFlightls[2].scheduleDepartTime = this.form.stopOverList[0].scheduleDepartTime
      newFlightls[2].estimateDepartTime = this.form.stopOverList[0].estimateDepartTime
      newFlightls[2].actualDepartTime = this.form.stopOverList[0].actualDepartTime
      newFlightls[2].segmentNature = '2'
      newFlightls[2].segmentIndex = this.curSelectRow.segmentIndex + 1

      // let irrReason = {irrReason: this.form.irrReason}
      let irrReason = {id: null, flightId: this.curSelectRow.id, irregularStatus: 'ALT', irregularCode: this.form.irrReason, publisher: this.sysUserId, localReason: null, remark: null}
      // let para = { newValue: newFlightls, oldValue: oldFlightls }
      // para = Object.assign(para, irrReason)
      let para = { irregularInfo: irrReason, firstFlight: newFlightls[1], secondFlight: newFlightls[2] }

      this.$refs['flightAddOrUpdateForm'].validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.loading = true
            API.operateFlightArrAlt().go(para).then((res) => {
              this.loading = false
              this.$notify(Util.notifyBody(res.ok, res.msg))
              this.initData()
              this.visible = false
              this.callback()
            })
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消提交'
            })
          })
        }
      })
    },
    setStopOverFormDate: function () {
      // 0828
      this.$refs['stopOverForm'].setStopOver()
      this.addStopOverList(this.$refs['stopOverForm'].stopOver)
    },
    addFlightAltSubmit: function () {
      // 新增备降0828
      let airportCode = this.form.segment.airportCode
      this.setStopOverFormDate()

      let newFlightls = []
      this.setSegDateTime('stopOverForm')
      let newSegmentlist = [{flightTask: 'A/N', destination: airportCode, direction: 'A', flightStatus: 'ENR', scheduleArriveTime: this.form.stopOverList[0].scheduleArriveTime, estimateArriveTime: this.form.stopOverList[0].estimateArriveTime, actualArriveTime: this.form.stopOverList[0].actualArriveTime, segmentNature: 1},
                            {flightTask: 'A/N', origin: airportCode, direction: 'D', flightStatus: null, scheduleDepartTime: this.form.stopOverList[0].scheduleDepartTime, estimateDepartTime: this.form.stopOverList[0].estimateDepartTime, actualDepartTime: this.form.stopOverList[0].actualDepartTime, segmentNature: 1}]

      newFlightls[0] = Util.deepCopy(this.form.segment)
      newFlightls[0] = Object.assign({}, newFlightls[0], newSegmentlist[0])
      newFlightls[0].flightNature = this.setFlightNature(newFlightls[0].destination)
      newFlightls[1] = Util.deepCopy(this.form.segment)
      newFlightls[1] = Object.assign({}, newFlightls[1], newSegmentlist[1])
      newFlightls[1].flightNature = this.setFlightNature(newFlightls[1].destination)
      this.oldflightlist = []
      // 新增备降需测试第三个参数、无需虚航段 0827
      let irrReason = {id: null, flightId: null, irregularStatus: 'ALT', irregularCode: this.form.irrReason, publisher: this.sysUserId, localReason: null, remark: null}
      let para = { irregularInfo: irrReason, firstFlight: newFlightls[0], secondFlight: newFlightls[1] }
      this.$refs['flightAddOrUpdateForm'].validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.loading = true
            API.operateFlightAddAlt().go(para).then((res) => {
              this.loading = false
              this.$notify(Util.notifyBody(res.ok, res.msg))
              this.initData()
              this.visible = false
              this.callback()
            })
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消提交'
            })
          })
        }
      })
    },
    handFlightDepRtn: function () {
      // 出港返航提交处理
      // 设置航班时间
      let objid = {id: null, flightId: null, linkedFlightId: null, combineFlightId: null}
      let irrReason = {id: null, flightId: this.curSelectRow.id, irregularStatus: 'RTN', irregularCode: this.form.irrReason, publisher: this.sysUserId, localReason: null, remark: null}
      let para = {}
      let estArrTime = null
      let firstFlight = {}
      let secondFlight = {}
      this.setSegTime()
      firstFlight = Object.assign({}, Util.deepCopy(this.curSelectRow), this.form.segment, objid)
      secondFlight = Util.deepCopy(this.curSelectRow)

      if (this.$refs['stopOverForm'].isNotNull(this.form.segment.estimateArriveTime)) {
        estArrTime = new Date(this.form.segment.estimateArriveTime.replace('-', '/'))
        estArrTime = estArrTime.setHours(estArrTime.getHours() + 1)
        estArrTime = Util.formatDate.format(new Date(estArrTime), 'yyyy-MM-dd hh:mm:ss')
      }
      secondFlight.scheduleDepartTime = estArrTime

      para = Object.assign({}, para, { firstFlight: firstFlight, secondFlight: secondFlight, irregularInfo: irrReason })
      // 出港返航提交
      this.$refs['flightAddOrUpdateForm'].validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.loading = true
            API.operateFlightDepRtn().go(para).then((res) => {
              this.loading = false
              this.$notify(Util.notifyBody(res.ok, res.msg))
              this.initData()
              this.visible = false
              this.callback()
            })
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消提交'
            })
          })
        }
      })
    },
    submitSave: function (formid, para) {
      // form提交保存数据
      this.$refs[formid].validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.loading = true
            this.to(para).then((res) => {
              this.loading = false
              this.$notify(Util.notifyBody(res.ok, res.msg))
              this.initData()
              this.visible = false
              this.callback()
            })
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消提交'
            })
          })
        }
      })
    },
    setSegTime: function () {
      // 提交保存前，设置航段时间值
      // 1.设置起飞时间
      let scheduleDepartTime = this.toDateStr(this.form.segment.scheduleDepartDate)
      let estimateDepartTime = this.toDateStr(this.form.segment.estimateDepartDate)
      let actualDepartTime = this.toDateStr(this.form.segment.actualDepartDate)

      this.form.segment.scheduleDepartTime = this.toTimeStr(scheduleDepartTime, this.form.segment.scheduleDepTime)
      this.form.segment.estimateDepartTime = this.toTimeStr(estimateDepartTime, this.form.segment.estimateDepTime)
      this.form.segment.actualDepartTime = this.toTimeStr(actualDepartTime, this.form.segment.actualDepTime)

      // 2.设置到达时间
      let scheduleArriveTime = this.toDateStr(this.form.segment.scheduleArriveDate)
      let estimateArriveTime = this.toDateStr(this.form.segment.estimateArriveDate)
      let actualArriveTime = this.toDateStr(this.form.segment.actualArriveDate)

      this.form.segment.scheduleArriveTime = this.toTimeStr(scheduleArriveTime, this.form.segment.scheduleArrTime)
      this.form.segment.estimateArriveTime = this.toTimeStr(estimateArriveTime, this.form.segment.estimateArrTime)
      this.form.segment.actualArriveTime = this.toTimeStr(actualArriveTime, this.form.segment.actualArrTime)
    },
    isNotNull: function (data) {
      return data !== undefined && data !== null && data !== ''
    },
    setIsDisableTime: function (isDisable) {
      // 设置是否禁用时间控件
      let depTimeEditAble = {scheduleDepartDate: isDisable, scheduleDepTime: isDisable, estimateDepartDate: isDisable, estimateDepTime: isDisable, actualDepartDate: isDisable, actualDepTime: isDisable}
      let arrTimeEditAble = {scheduleArriveDate: isDisable, scheduleArrTime: isDisable, estimateArriveDate: isDisable, estimateArrTime: isDisable, actualArriveDate: isDisable, actualArrTime: isDisable}
      this.fieldAble = Object.assign({}, this.fieldAble, depTimeEditAble, arrTimeEditAble)
    },
    dateformat: function (row, column, cellValue) {
      let date = row[column.property]
      if (date === undefined || date === null || date === '') {
        return ''
      } else {
        return Util.formatDate.format(new Date(date), 'yyyy-MM-dd hh:mm')
      }
    },
    setFlightNature: function (destination) {
      // 0828 设置航班属性
      let obj = this.baseList.airports.find((item) => {
          return item.icaocode === destination
      })
      return obj !== undefined ? obj.airportnature : null
    },
    setReplaceList: function () {
      this.loading = true
      // 0910 从缓存中获取基础表信息
      let tkeys = ['airports', 'flightdirections', 'generalagents', 'airlines', 'terminals', 'flighttasks', 'irregularcodes', 'flightstatus', 'aircraftTypes', 'stand', 'gate', 'vipranks']
      for (var i = 0; i < tkeys.length; i++) {
        this.baseList[tkeys[i]] = this.$cache.fetch(tkeys[i])
        if (i === tkeys.length - 1) {
          this.loading = false
        }
      }
      this.setClassName()
    },
    toUpper: function (val) {
      this.form.segment.flightTask = val.key.toUpperCase()
    },
    setSegDateTime: function (formid) {
      this.form.segment.scheduleArriveTime = this.$refs[formid].toDateTime(this.form.segment.scheduleArriveDate, this.form.segment.scheduleArrTime)
      this.form.segment.scheduleDepartTime = this.$refs[formid].toDateTime(this.form.segment.scheduleDepartDate, this.form.segment.scheduleDepTime)

      this.form.segment.estimateDepartTime = this.$refs[formid].toDateTime(this.form.segment.estimateDepartDate, this.form.segment.estimateDepTime)
      this.form.segment.actualDepartTime = this.$refs[formid].toDateTime(this.form.segment.actualDepartDate, this.form.segment.actualDepTime)
      this.form.segment.estimateArriveTime = this.$refs[formid].toDateTime(this.form.segment.estimateArriveDate, this.form.segment.estimateArrTime)
      this.form.segment.actualArriveTime = this.$refs[formid].toDateTime(this.form.segment.actualArriveDate, this.form.segment.actualArrTime)
    },
    setRowClass: function (row, index) {
      if (row.segmentNature === 3) {
        return 'segmetCnl-row'
      } else {
        return 'segmetNormal-row'
      }
    },
    initVarBeforeShow: function (para) {
      // 设置标志参数，控制控件是否展示
      // para = {operate: '', optName: '', editAble: true, isShowIrrInfo: true, isDisableTime: true, isDiglog: true}
      for (var key in para) {
        if (key !== 'isDisableTime') {
          this[key] = para[key]
        } else {
          this.setIsDisableTime(para[key])
        }
      }
    },
    showShareFlight () {
      // 展示共享航班编辑界面
      let curSegList = this.setCurSegmentList()
      this.$refs['shareFlightForm'].show(curSegList, this.shareFlightLs)
    },
    setCurSegmentList () {
      // 拼接本场航段
      let list = []
      let origin = this.$cache.findByName('airports', 'icaocode', this.form.segment.origin, 'cnabbr2w') + '(' + this.form.segment.origin + ')'
      let last = this.$cache.findByName('airports', 'icaocode', this.form.segment.destination, 'cnabbr2w') + '(' + this.form.segment.destination + ')'
      for (var i = 0; i < this.form.stopOverList.length; i++) {
        let seg = {}
        let name = this.$cache.findByName('airports', 'icaocode', this.form.stopOverList[i].destination, 'cnabbr2w')
        let destination = name + '(' + this.form.stopOverList[i].destination + ')'
        seg = { segmentName: origin + '-' + destination, origin: this.form.segment.origin, destination: this.form.stopOverList[i].destination }
        list.push(seg)
      }
      list[this.form.stopOverList.length] = { segmentName: origin + '-' + last, origin: this.form.segment.origin, destination: this.form.segment.destination }
      return list
    },
    setShareFlightInfo () {
      // 拼接共享航班文本信息
      let t = ''
      for (var i = 0; i < this.shareFlightLs.length; i++) {
        let c = i === 0 ? '' : '/'
        t += c + this.shareFlightLs[i].carrier + this.shareFlightLs[i].flightNo
      }
      this.shareFlights = t
    },
    setShareFlightLs (params) {
      // 保存共享航班信息
      this.shareFlightLs = Util.deepCopy(params.shareFlightLs)
      this.deleteShareIdList = Util.deepCopy(params.deleteShareIdList)
      this.setShareFlightInfo()
    },
    setLink (val) {
      this.baseList.gate = this.$cache.fetch('gate').filter(item => {
        return item.terminal === val
      })
    },
    setAirportCode (airportCode) {
      this.form.segment.airportCode = airportCode
    },
    showAirportInfo (subAirportLs) {
      this.$nextTick(() => {
        this.$refs['city'].setCitys(subAirportLs)
      })
    },
    setAirportInfo (airportCode) {
      this.$nextTick(() => {
        this.$refs['city'].airportCode = airportCode
      })
    }
  },
  mounted () {
    // this.bindData()
    var storage = this.$store.getters.getUserStorage
    this.sysUserId = storage.user.id
    this.subAirportLs = Butil.getSubscribeAirports()
    // 自适应浏览器窗口大小
    window.addEventListener('resize', this.setClassName)
  }
}

</script>
<style lang="scss">
.flightFormclass {
  .el-dialog__body {
    padding: 5px 20px;
  }
  .el-form-item {
    margin-bottom: 8px;
  }

  .el-table .segmetCnl-row {
    background: #e2f0e4;
  }

  .el-table .segmetNormal-row {
    background: #ffffff;
  }

  .smallDigCls {
    .el-dialog--small {
      width: 50%!important;
    }
  }

  .largeDigCls {
    .el-dialog--small {
      width: 70%!important;
    }
  }
  .el-dialog {
    margin-top: -4.5%!important;
  }
  .el-table {
    margin-left: 0px!important;
    margin-right: 0px!important;
  }
  .shareBtnCls {
    // margin-left: 12px!important;
    padding-left: 5px!important;
    width: 100px!important;
  }
  [name=shareFlights] {
    color: black!important;
  }
}

.flightFormclass .signClass [name=carrier],.signClass [name=flightNo],.signClass [name=direction] {
  background: #FFA500!important;
  color: black!important;
}
</style>

