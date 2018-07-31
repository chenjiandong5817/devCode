/*
 * @Author: ylj
 * @Date: 2017-10-17 15:24:29
 * @Last Modified by: ylj
 * @Last Modified time: 2017-11-24 14:28:37
 */
<template>
<div class="planFlightFormcls">
  <div :class="addOrUpClss">
  <el-dialog :title="title" v-model="visible" :close-on-click-modal="false" @close="handleClose" :modal="false" @open="setClassName">
    <el-form :model="form.segment" ref="planflightForm" >
      <el-row :gutter="10">
        <el-col :span="6">
          <el-form-item label="计划类型" prop="planSource" :rules="[ { required: true, message: '请选择计划类型', trigger: 'blur' } ]">
            <el-select v-model="form.segment.planSource" placeholder="计划类别" :filterable="true" style="width:100%;" clearable >
              <el-option
                v-for="item in baseList.planSources"
                :key="item.sourceCode"
                :lable="item.description"
                :value="item.sourceCode">
                <span style="float: left">{{ item.sourceCode }}</span>
                <span style="float: right">{{ item.description }}</span>
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="运营机场" prop="airportCode" :rules="[ { required: true, message: '请选择运营机场', trigger: 'blur' } ]">
            <el-select v-model="form.segment.airportCode" placeholder="运营机场" :filterable="true" style="width:100%;" clearable>
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
        </el-col>

        <el-col :span="6">
          <el-form-item label="执行日期" prop="opDate" :rules="[ { required: true, message: '请选择执行日期', trigger: 'blur' } ]">
            <date-time v-model="form.segment.opDate" :visitTime="false" style="width: 100%;" datePlaceholder="执行日期" formatter="yyyy-MM-dd" dateStyle="width:100%"></date-time>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="公司" prop="carrier" :rules="[ { required: true, message: '请选择公司', trigger: 'blur' } ]">
            <el-select v-model="form.segment.carrier" name="carrier" placeholder="公司" style="width:100%;" filterable clearable>
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
      </el-row>

      <el-row :gutter="10">
        <el-col :span="6">
          <el-form-item label="航班号" prop="flightNo" :rules="[ { required: true, message: '请填写航班号', trigger: 'blur' } ]">
            <el-input v-model="form.segment.flightNo" name="flightNo" style="width:100%;" placeholder="航班号"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="进出" prop="direction" :rules="[ { required: true, message: '请选择进出', trigger: 'blur' } ]">
            <el-select v-model="form.segment.direction" name="direction" placeholder="进出" style="width:100%;" clearable  filterable>
              <el-option
                v-for="item in baseList.flightdirections"
                :key="item.directionCode"
                :label="item.description"
                :value="item.directionCode">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="任务" prop="flightTask" :rules="[ { required: true, message: '请选择任务', trigger: 'blur' } ]">
            <el-select v-model="form.segment.flightTask" placeholder="任务" filterable style="width:100%;"  filterable clearable>
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
          <el-form-item label="航站楼" prop="terminal" :rules="[ { required: true, message: '请选择航站楼', trigger: 'blur' } ]">
            <el-select v-model="form.segment.terminal" placeholder="航站楼" @change="setLink" style="width:100%;" filterable clearable>
              <el-option
                v-for="item in baseList.terminals"
                :key="item.terminalCode"
                :label="item.terminalCode"
                :value="item.terminalCode">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="10">
        <el-col :span="6">
          <el-form-item label="机号" prop="registration" :rules="[ { required: true, message: '请选择机号', trigger: 'blur' } ]">
            <el-select v-model="form.segment.registration" filterable remote :remote-method="regRemote" @change="setAircraftType" placeholder="机号" style="width:100%;" clearable>
              <el-option
                v-for="item in baseList.registrations"
                :key="item.registration"
                :label="item.registration"
                :value="item.registration">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="机型" prop="aircraftType" :rules="[ { required: true, message: '请选择机型', trigger: 'blur' } ]">
            <el-select v-model="form.segment.aircraftType" placeholder="机型" style="width:100%;" filterable remote :remote-method="airTypeRemote" clearable>
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
          <el-form-item label="总代" prop="generalAgent" :rules="[ { required: true, message: '请选择总代', trigger: 'blur' } ]">
            <el-select v-model="form.segment.generalAgent" placeholder="总代" style="width:100%;" filterable clearable>
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
      </el-row>

      <el-row :gutter="10">
        <el-col :span="6">
          <el-form-item label="登机机位" prop="boardingStand">
            <el-select v-model="form.segment.boardingStand" placeholder="登机机位" style="width:100%;" filterable clearable>
              <el-option
                v-for="item in baseList.standList"
                :key="item.standCode"
                :label="item.standCode"
                :value="item.standCode">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="实际机位" prop="stand">
            <el-select v-model="form.segment.stand" placeholder="实际机位" style="width:100%;" filterable clearable>
              <el-option
                v-for="item in baseList.standList"
                :key="item.standCode"
                :label="item.standCode"
                :value="item.standCode">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="登机口" prop="gate">
            <el-select v-model="form.segment.gate" placeholder="登机口" style="width:100%;" filterable clearable>
              <el-option
                v-for="item in baseList.gateList"
                :key="item.gateCode"
                :label="item.displayCode"
                :value="item.gateCode">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="10">
        <el-col :span="6">
          <el-form-item label="始发站" prop="origin" :rules="[ { required: true, message: '请选择始发站', trigger: 'blur' } ]">
            <el-select v-model="form.segment.origin" style="width:100%;" filterable placeholder="始发站" clearable>
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
          <el-form-item label="计划起飞时间" prop="scheduleDepartTime">
            <br/>
            <date-time v-model="form.segment.scheduleDepartTime" :visitTime="true" dateStyle="width: 65%;" timeStyle="width: 32%;" datePlaceholder="计划日期" timePlaceholder="时间" formatter="yyyy-MM-dd" ></date-time>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="目的站" prop="destination" :rules="[ { required: true, message: '请选择目的站', trigger: 'blur' } ]">
            <el-select v-model="form.segment.destination" filterable placeholder="目的站" clearable style="width:100%;">
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
          <el-form-item label="计划到达时间" prop="scheduleArriveTime">
            <br/>
            <date-time v-model="form.segment.scheduleArriveTime" :visitTime="true" dateStyle="width: 65%;" timeStyle="width: 32%;" datePlaceholder="计划日期" timePlaceholder="时间" formatter="yyyy-MM-dd" ></date-time>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="10">
        <el-col :span="18" >
          <el-form-item label="共享航班" style="padding-top:10px;">
            <el-input v-model="shareFlights" name="shareFlights" placeholder="共享航班"  disabled></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="4">
          <el-form-item style="padding-top:10px;">
            <br/>
            <el-button @click="showShareFlight" type="info" class="shareBtnCls" size="small">设置共享航班</el-button>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="10" style="margin-top: 10px;margin-bottom: 10px;">
        <el-col :span="3" >
          <el-form-item label="经停站列表"></el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item>
            <el-button-group>
              <el-button @click="showPlanStopOverView" type="success" size="small">新增</el-button>
              <el-button @click="showEditStopOverView" type="success" size="small">编辑</el-button>
              <el-button @click="delStopOver" type="danger" size="small">删除</el-button>
            </el-button-group>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="10">
        <el-table v-bind:data="form.stopOverList" highlight-current-row v-loading="tableLoading" border style="width: 100%" height="200" @current-change="handleCurrentChange" ref="stopOverTable" append >
          <el-table-column prop="index" label="顺序"></el-table-column>
          <el-table-column prop="destination" label="经停站" >
            <template scope="scope" >
              {{$cache.findByName('airports', 'icaocode', scope.row.destination, 'cnabbr2w')}}
            </template>
          </el-table-column>
          <el-table-column prop="scheduleArriveTime" label="计划到达" :formatter="dateformat" ></el-table-column>
          <el-table-column prop="scheduleDepartTime" label="计划起飞" :formatter="dateformat" ></el-table-column>
         </el-table>
      </el-row>

    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click.native="handleClose">取消</el-button>
      <el-button @click.native="resetForm('planflightForm')" >重置</el-button>
      <el-button type="primary" @click.native="handAddOrUptSubmit" :loading="loading">提交</el-button>
    </div>

    <!--新增/编辑经停-->
    <plan-stop-over
      :to="params => { return this.addStopOverList(params) }"
      ref="planStopOverForm"></plan-stop-over>

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
import dateTime from '../../../components/DateTime'
import planStopOver from '../planFlightForm/planStopOverForm'
import shareFlight from '../planFlightForm/ShareFlightForm'

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
      operate: '',
      addOrUpClss: 'normalClass',
      dialogClss: 'smallDigCls',
      visible: false,
      loading: false,
      tableLoading: false,
      API: API,
      form: {
        segment: {
          id: null,
          flightId: null,
          linkedFlightId: null,
          combineFlightId: null,
          planSource: null,
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
          flightTask: null,
          origin: null,
          scheduleDepartTime: null,
          destination: null,
          scheduleArriveTime: null,
          segmentIndex: null,
          segmentNature: null,
          flightNature: null
        },
        stopOverList: []
      },
      currentSopOver: null,
      newPlanFlightls: [],
      oldPlanFlightls: [],
      firstSeg: null,
      lastSeg: null,
      delStopOverIdLs: [],
      stopOver: {id: null, flightId: null, index: 0, destination: '', flightNature: '', segmentNature: '', scheduleDepartTime: null, scheduleArriveTime: null, boardingStand: null, stand: null, gate: null},
      curSelectRow: [],
      sysUserId: null,
      subAirportLs: [],
      baseList: {
        planSources: this.$cache.fetch('planSource'),
        airlines: this.$cache.fetch('airlines'),
        flighttasks: this.$cache.fetch('flighttasks'),
        registrations: [],
        aircraftTypes: this.$cache.fetch('aircraftTypes'),
        flightdirections: this.$cache.fetch('flightdirections'),
        terminals: this.$cache.fetch('terminals'),
        generalagents: this.$cache.fetch('generalagents'),
        airports: this.$cache.fetch('airports'),
        standList: this.$cache.fetch('stand'),
        gateList: this.$cache.fetch('gate'),
        vipranks: this.$cache.fetch('vipranks')
      },
      isShareFlight: false,
      shareFlights: '',
      oldShareFlightLs: [],
      shareFlightLs: [],
      deleteShareIdList: []
    }
  },
  components: {
    dateTime: dateTime,
    planStopOver: planStopOver,
    shareFlight: shareFlight
  },
  methods: {
    setClassName () {
      // 设置dialog宽度、字段突出显示
      this.addOrUpClss = (this.operate !== 'Add' ? 'signClass' : 'normalClass') + (document.body.clientWidth <= 1366 ? ' largeDigCls' : ' smallDigCls')
    },
    bindData () {
      // 获取用户id、订阅机场信息
      var storage = this.$store.getters.getUserStorage
      this.sysUserId = storage.user.id
      this.subAirportLs = Butil.setSubAirportls()
    },
    show (row, airportCode) {
      // this.bindData()
      if (row !== undefined && row !== null) {
        this.operate = 'Edeit'
        this.curSelectRow = Util.deepCopy(row)
        // 添加航班其他属性信息
        this.setDefauleDate(null)
        this.loading = true
        let para = { pageSize: 0, flightId: row.flightId }
        API.getPlanFlightls().go(para).then((data) => {
          if (data.ok) {
            this.oldPlanFlightls = data.attr.data.list
            // 1. 获取实航段信息
            let actSegmentLs = this.getActSegmentLs(this.oldPlanFlightls)
            // 2. 绑定显示字段数据
            this.firstSeg = Util.deepCopy(actSegmentLs[0])
            this.lastSeg = Util.deepCopy(actSegmentLs[actSegmentLs.length - 1])
            this.bindSegment()
            // 3. 绑定经停站
            this.bindStopOverLs(actSegmentLs)
            // 4. 设置显示本站机位等信息
            this.setCurStation(actSegmentLs)
            // 5. 设置共享航班信息 1021 待测试 待修改为从API获取【共享航班】
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
          this.visible = true
        })
      } else {
        // 新增设置默认值为空、航班设置日期默认当前日期
        this.operate = 'Add'
        this.initData(airportCode)
        this.setDefauleDate(new Date(), 'yyyy-MM-dd')
        this.visible = true
      }
      // this.visible = true
      this.loading = false
      this.setClassName()
    },
    getActSegmentLs (segmentLs) {
      // 获取实航段信息
      let list = segmentLs.filter(item => {
        return item.segmentNature !== 0
      })
      // 按segmentIndex排序
      list.sort(function (a, b) {
        return a.segmentIndex - b.segmentIndex
      })
      return list
    },
    bindSegment () {
      this.form.segment = Object.assign({}, this.form.segment, this.firstSeg)
      this.form.segment.destination = this.lastSeg.destination
      this.form.segment.scheduleArriveTime = this.lastSeg.scheduleArriveTime
      if (this.form.segment.direction === 'A') {
        this.form.segment.boardingStand = this.lastSeg.boardingStand
        this.form.segment.stand = this.lastSeg.stand
        this.form.segment.gate = this.lastSeg.gate
      }
    },
    bindStopOverLs (actSegmentLs) {
      // 绑定经停站
      var stoplist = []
      for (var i = 0; i < actSegmentLs.length - 1; i++) {
        let stop = Util.deepCopy(this.stopOver)
        stop.index = i + 1
        // 设置经停站其他时间信息
        stop.id = actSegmentLs[i].id
        stop.flightId = actSegmentLs[i].flightId
        stop.destination = actSegmentLs[i].destination
        stop.flightNature = actSegmentLs[i].flightNature
        stop.scheduleArriveTime = actSegmentLs[i].scheduleArriveTime
        stop.scheduleDepartTime = actSegmentLs[i + 1].scheduleDepartTime
        stop.segmentNature = actSegmentLs[i].segmentNature
        // 1029 保存航段登机机位、实际机位、登机口信息 boardingStand stand gate
        stop.boardingStand = actSegmentLs[i].boardingStand
        stop.stand = actSegmentLs[i].stand
        stop.gate = actSegmentLs[i].gate
        stoplist[i] = stop
      }
      this.form.stopOverList = Util.deepCopy(stoplist)
    },
    setCurStation (actSegmentLs) {
      var index = this.form.segment.direction === 'A' ? actSegmentLs.length - 1 : 0
      this.form.segment.boardingStand = actSegmentLs[index].boardingStand
      this.form.segment.stand = actSegmentLs[index].stand
      this.form.segment.gate = actSegmentLs[index].gate
    },
    initData (airportCode) {
      // 新增时初始化数据
      for (var key in this.form.segment) {
        this.form.segment[key] = null
      }
      if (airportCode !== undefined) {
        // 设置航班运营机场
        this.form.segment.airportCode = airportCode
      }
      this.shareFlights = ''
      this.oldShareFlightLs = []
      this.shareFlightLs = []
      this.form.stopOverList = []
      this.curSelectRow = []
      this.delStopOverIdLs = []
    },
    setDefauleDate (date, fmt) {
      let defDate = {opDate: '', scheduleDepartTime: '', scheduleArriveTime: ''}
      if (date !== null) {
        let newDate = Util.formatDate.format(date, fmt)
        defDate.opDate = newDate
        defDate.scheduleDepartTime = newDate
        defDate.scheduleArriveTime = newDate
      }
      this.form.segment = Object.assign({}, this.form.segment, defDate)
    },
    handleClose: function () {
      this.visible = false
      this.editAble = false
      this.shareFlightLs = []
      this.delStopOverIdLs = []
      this.$refs['planflightForm'].resetFields()
    },
    handAddOrUptSubmit: function () {
      // 1. 新增-将始发、经停站、目的站拼成segList 21:00
      let segList = this.setSegList()
      // 2. 循环 拼接实航段和虚航段 newPlanFlightls
      var segIndex = 0
      var index = 0
      this.newPlanFlightls = []
      for (var x = 0; x < segList.length; x++) {
        for (var y = x + 1; y < segList.length; y++) {
          let s = Util.deepCopy(this.form.segment)
          if (this.operate === 'Edeit') {
            s.flightId = this.firstSeg.flightId
            s.linkedFlightId = this.firstSeg.linkedFlightId
            s.combineFlightId = this.firstSeg.combineFlightId
          }
          s.opDate = typeof this.form.segment.opDate === 'object' ? Util.formatDate.format(this.form.segment.opDate, 'yyyy-MM-dd') : this.form.segment.opDate
          s.origin = segList[x].destination
          s.destination = segList[y].destination
          s.flightNature = this.setFlightNature(s.destination)
          s.scheduleDepartTime = segList[x].scheduleDepartTime
          s.scheduleArriveTime = segList[y].scheduleArriveTime
          // 1029 保存航段实际机位、机位、登机口信息
          if ((s.direction === 'A' && !(x === segList.length - 2 && y === x + 1)) || (s.direction === 'D' && !(x === 0 && y === 1))) {
            s.boardingStand = segList[y].boardingStand
            s.stand = segList[y].stand
            s.gate = segList[y].gate
          }
          // ----END 1029
          // 设置航班航段index以及nature
          if (x + 1 === y) {
            // 实航段
            if (segList[y]['segmentNature'] === 3 || segList[y]['segmentNature'] === 2) {
              // 取消、经停航段
              s.segmentNature = segList[y]['segmentNature']
            } else {
              s.segmentNature = 1
            }

            s.id = segList[y].id
            s.segmentIndex = segIndex
            segIndex = segIndex + 1
          } else {
            // 虚航段
            s.id = null
            s.segmentNature = 0
            s.segmentIndex = 0
          }
          this.newPlanFlightls[index] = s
          index = index + 1
        }
      }
      var para = { newValue: this.newPlanFlightls, oldValue: this.oldPlanFlightls, deleteIdList: this.delStopOverIdLs, shareFlightLs: this.shareFlightLs, deleteShareIdList: this.deleteShareIdList }
      this.$refs['planflightForm'].validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.loading = true
            this.to(para).then((res) => {
              this.loading = false
              this.$notify(Util.notifyBody(res.ok, res.msg))
              // this.resetForm('planflightForm')
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
    setSegList () {
      // 新增-将始发、经停站、目的站拼成segList
      let segList = []
      let segFirst = Util.deepCopy(this.stopOver)
      segFirst.destination = this.form.segment.origin
      segFirst.id = this.operate === 'Edeit' ? this.firstSeg.id : null
      segFirst.scheduleDepartTime = this.form.segment.scheduleDepartTime
      segList[0] = segFirst

      segList.push.apply(segList, Util.deepCopy(this.form.stopOverList))

      let setLast = Util.deepCopy(this.stopOver)
      setLast.id = this.operate === 'Edeit' ? this.lastSeg.id : null
      setLast.destination = this.form.segment.destination
      setLast.scheduleArriveTime = this.form.segment.scheduleArriveTime
      // 设置航班属性
      this.form.segment.flightNature = this.setFlightNature(setLast.destination)

      segList[segList.length] = setLast
      return segList
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
        let delIndex = this.currentSopOver.index - 1
        if (this.form.stopOverList[delIndex].id !== null) {
          // 保存删除经停站id信息（与删除的经停站相邻的站点航段也删除）
          this.delStopOverIdLs[this.delStopOverIdLs.length] = this.form.stopOverList[delIndex].id
          if (delIndex + 1 < this.form.stopOverList.length && this.form.stopOverList[delIndex + 1].id !== null) {
            this.delStopOverIdLs[this.delStopOverIdLs.length] = this.form.stopOverList[delIndex + 1].id
          }
        }
        this.form.stopOverList.splice(delIndex, 1)
        let step = 1
        for (var i = delIndex; i < this.form.stopOverList.length; i++, step++) {
          this.form.stopOverList[i].index = delIndex + step
          if (i === delIndex) {
            this.form.stopOverList[delIndex].id = null
          }
        }
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
    showPlanStopOverView () {
      this.$refs['planStopOverForm'].show('新增经停', this.form.stopOverList.length + 1, this.form.segment.flightId)
    },
    showEditStopOverView: function () {
      // 经停站编辑界面展示
      if (this.currentSopOver !== undefined && this.currentSopOver !== null) {
        this.$refs['planStopOverForm'].show('编辑经停', this.currentSopOver.index, this.currentSopOver.flightId, this.currentSopOver)
      } else {
        this.$message({
          type: 'info',
          message: '请选中要编辑的经停站'
        })
      }
    },
    resetForm (formName) {
      this.$refs[formName].resetFields()
      this.$refs['planflightForm'].resetFields()
      this.stopOverList = []
    },
    regRemote: function (query) {
      if (query !== '' && query !== null && query !== undefined) {
        let para = {pageSize: 99999}
        API.searchReg(query.toLowerCase()).go(para).then((data) => {
          this.baseList.registrations = data.list
        })
      } else {
        this.baseList.registrations = []
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
    setAircraftType: function (val) {
      let list = []
      list = this.baseList.registrations.filter(item => {
        return item.registration === val
      })
      if (list.length > 0) {
        this.form.segment.aircraftType = list[0].aircraftType
      }
    },
    dateformat: function (row, column, cellValue) {
      // 经停站列表日期格式化
      let date = row[column.property]
      if (date === undefined || date === null || date === '') {
        return ''
      } else {
        date = (date.length > 10 ? date : date + ' 00:00:00')
        return Util.formatDate.format(new Date(date), 'yyyy-MM-dd hh:mm')
      }
    },
    setFlightNature: function (destination) {
      // 设置航班属性
      let obj = this.baseList.airports.find((item) => {
          return item.icaocode === destination
      })
      return obj !== undefined ? obj.airportnature : null
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
      this.baseList.gateList = this.$cache.fetch('gate').filter(item => {
        return item.terminal === val
      })
    }
  },
  mounted () {
    this.bindData()
    // 自适应浏览器窗口大小
    window.addEventListener('resize', this.setClassName)
  }
}

</script>
<style lang="scss">
.planFlightFormcls {
  .el-form-item {
    margin-bottom: 8px;
  }
  .el-dialog__body {
    padding-top: 5px;
  }
  .el-table {
    margin-left: 0px!important;
    margin-right: 0px!important;
  }
  .el-table .segmetCnl-row {
    background: #e2f0e4;
  }
  .el-table .segmetNormal-row {
    background: #ffffff;
  }
  .smallDigCls {
    .el-dialog--small {
      width: 45%!important;
    }
  }
  .largeDigCls {
    .el-dialog--small {
      width: 65%!important;
    }
  }
  .el-dialog {
    margin-top: -4.5%!important;
  }
  .signClass [name=carrier],.signClass [name=flightNo],.signClass [name=direction] {
    background: #FFA500!important;
    color: black!important;
  }
  [name=shareFlights] {
    color: black!important;
  }
  .shareBtnCls {
    // margin-left: 12px!important;
    padding-left: 5px!important;
    width: 100px!important;
  }
}
</style>

