/*
 * @Author: ylj
 * @Date: 2017-10-17 15:24:29
 * @Last Modified by: ylj
 * @Last Modified time: 2017-11-24 14:28:37 修改航线编辑设置信息
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
                v-for="(item, index) in baseList.planSources"
                :key="index"
                :label="item.description"
                :value="item.sourceCode">
                <span style="float: left">{{ item.sourceCode }}</span>
                <span style="float: right">{{ item.description }}</span>
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="运营机场" prop="airportCode" :rules="[ { required: true, message: '请选择运营机场', trigger: 'blur' } ]">
            <el-select v-model="form.segment.airportCode" placeholder="运营机场" :filterable="true" style="width:100%;" clearable @change="setLinkFields">
              <el-option
                v-for="(item, index) in subAirportLs"
                :key="index"
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
      </el-row>

      <el-row :gutter="10">
        <el-col :span="6">
          <el-form-item label="公司" prop="carrier" :rules="[ { required: true, message: '请选择公司', trigger: 'blur' } ]">
            <el-select v-model="form.segment.carrier" name="carrier" placeholder="公司" style="width:100%;" filterable clearable>
              <el-option
                v-for="(item, index) in baseList.airlines"
                :key="index"
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
            <el-input v-model="form.segment.flightNo" name="flightNo" style="width:100%;" placeholder="航班号"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="进出" prop="direction" :rules="[ { required: true, message: '请选择进出', trigger: 'blur' } ]">
            <el-select v-model="form.segment.direction" name="direction" placeholder="进出" style="width:100%;" clearable  filterable @change="bindAirport">
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
          <el-form-item label="航站楼" prop="terminal" :rules="[ { required: true, message: '请选择航站楼', trigger: 'blur' } ]">
            <el-select v-model="form.segment.terminal" placeholder="航站楼" @change="setLink" style="width:100%;" filterable clearable>
              <el-option
                v-for="(item, index) in baseList.terminals"
                :key="index"
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
                v-for="(item, index) in baseList.registrations"
                :key="index"
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
                v-for="(item, index) in baseList.aircraftTypes"
                :key="index"
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
                v-for="(item, index) in baseList.generalagents"
                :key="index"
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
                v-for="(item, index) in baseList.vipranks"
                :key="index"
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
                v-for="(item, index) in baseList.standList"
                :key="index"
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
                v-for="(item, index) in baseList.standList"
                :key="index"
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
                v-for="(item, index) in baseList.gateList"
                :key="index"
                :label="item.displayCode"
                :value="item.gateCode">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="10">
        <el-col :span="18" >
          <el-form-item label="共享航班">
            <el-input v-model="shareFlights" name="shareFlights" placeholder="共享航班"  disabled></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="4">
          <el-form-item>
            <br/>
            <el-button @click="showShareFlight" type="info" class="shareBtnCls" size="small">设置共享航班</el-button>
          </el-form-item>
        </el-col>
      </el-row>

      <hr/>
      <el-row :gutter="5">
        <el-col :span="6">
          <el-form-item label-width="0px">
            <el-tag type="info">航线设置 (北京时间) </el-tag>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="5">
        <airline-form ref="pairlineForm"></airline-form>
      </el-row>

    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click.native="handleClose">取消</el-button>
      <el-button @click.native="resetForm('planflightForm')" >重置</el-button>
      <el-button type="primary" @click.native="handAddOrUptSubmit" :loading="loading">提交</el-button>
    </div>

    <!--设置共享航班-->
    <share-flight
      :to="params => { return this.setShareFlightLs(params) }"
      ref="shareFlightForm"></share-flight>

  </el-dialog>
  </div>
</div>
</template>

<script>
import Util from '../../../../common/js/util'
import Butil from '../../../../common/js/base-util'
import API from '../../../../api'
import dateTime from '../../../../components/DateTime'
import planStopOver from '../planFlightForm/planStopOverForm'
import shareFlight from '../planFlightForm/ShareFlightForm'
import AirlineForm from './PlanAirlineForm'

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
          planSource: 'M',
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
          flightTask: 'W/Z',
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
      oldStopOverList: [],
      currentSopOver: null,
      newPlanFlightls: [],
      oldPlanFlightls: [],
      firstSeg: null,
      lastSeg: null,
      delStopOverIdLs: [],
      stopOver: {id: null, flightId: null, origin: '', destination: '', flightNature: '', segmentNature: '', scheduleDepartTime: null, scheduleArriveTime: null, boardingStand: null, stand: null, gate: null},
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
    shareFlight: shareFlight,
    airlineForm: AirlineForm
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
    setLinkByAirport (airportCode) {
      this.setLink(this.form.segment.terminal)
      this.baseList.terminals = this.$cache.findListByName('terminals', 'airportCode', airportCode)
      this.baseList.generalagents = this.$cache.findListByName('generalagents', 'airportCode', airportCode)
      this.baseList.standList = this.$cache.findListByName('stand', 'airportCode', airportCode)
    },
    show (row, airportCode) {
      // this.bindData()
      // 设置运营机场关联（总代、登机口、航站楼）
      this.form.segment.airportCode = airportCode
      this.setLinkByAirport(airportCode)
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
        let moment = require('moment')
        let nextDate = moment().add(1, 'days').format('YYYY-MM-DD HH:mm:ss')
        this.operate = 'Add'
        this.initData(airportCode)
        this.setDefauleDate(new Date(nextDate), 'yyyy-MM-dd')
        this.bindStopOverLs()
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
      // 绑定经停站 1203
      this.$nextTick(() => {
        if (actSegmentLs === undefined) {
          this.$refs['pairlineForm'].show(this.form.segment, null)
        } else {
          this.$refs['pairlineForm'].show(this.form.segment, this.firstSeg.flightId, actSegmentLs)
        }
        let obj = this.$refs['pairlineForm'].getAirlineList()
        this.form.stopOverList = obj.newAirline
        this.oldStopOverList = obj.oldAirlineList
      })
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
      this.form.segment.planSource = 'M'
      this.form.segment.flightTask = 'W/Z'
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
      this.deleteShareIdList = []
      this.delStopOverIdLs = []
      // this.$refs['planflightForm'].resetFields()
      // this.$refs['pairlineForm'].initData()
    },
    handAddOrUptSubmit: function () {
      // 设置航段信息（实航段 + 虚航段）
      this.newPlanFlightls = []
      let segList = Util.deepCopy(this.$refs['pairlineForm'].getAirlineList().newAirline)
      let segLength = segList.length
      for (var i = 0; i < segList.length; i++) {
        // 1. 设置实航段信息
        let s = Object.assign({}, Util.deepCopy(this.form.segment), segList[i])
        s.segmentNature = '1'
        s.segmentIndex = i
        // 设置本站航段信息
        s = Object.assign(s, this.bindCurStation(s, i, segLength))
        this.newPlanFlightls.push(s)
        // 2. 设置虚航段信息
        for (var j = i + 1; j < segList.length; j++) {
          let s = Object.assign({}, Util.deepCopy(this.form.segment), segList[j])
          // 设置虚航段信息
          s.id = null
          s.origin = segList[i].origin
          s.scheduleDepartTime = segList[i].scheduleDepartTime
          s.estimateDepartTime = segList[i].estimateDepartTime
          s.actualDepartTime = segList[i].actualDepartTime
          s.segmentNature = '0'
          s.segmentIndex = 0
          s = Object.assign(s, this.bindCurStation(s, j, segLength))
          this.newPlanFlightls.push(s)
        }
      }
      // 获取删除航段信息
      this.delStopOverIdLs = this.$refs['pairlineForm'].delStopOverIdLs
      var para = { newValue: this.newPlanFlightls, oldValue: this.oldPlanFlightls, deleteIdList: this.delStopOverIdLs, shareFlightLs: this.shareFlightLs, deleteShareIdList: this.deleteShareIdList }
      let airlineValid = this.$refs['pairlineForm'].airlineValid()
      this.$refs['planflightForm'].validate((valid) => {
        if (valid && airlineValid) {
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
    bindCurStation (segment, index, segLength) {
      // 若为本站航班，设置本站的机位、实际机位、登机口信息
      let obj = {}
      let segList = Util.deepCopy(this.$refs['pairlineForm'].getAirlineList().newAirline)
      if ((segment.direction === 'A' && index === segLength - 1) || (segment.direction === 'D' && segment.origin === segList[0].origin)) {
        obj['gate'] = this.form.segment.gate
        obj['stand'] = this.form.segment.stand
        obj['boardingStand'] = this.form.segment.boardingStand
      } else {
        obj['gate'] = segment.gate
        obj['stand'] = segment.stand
        obj['boardingStand'] = segment.boardingStand
      }
      return obj
    },
    handleCurrentChange (val) {
      this.currentSopOver = val
    },
    resetForm (formName) {
      this.$refs['planflightForm'].resetFields()
      // 1203
      this.$refs['pairlineForm'][0].resetForm()
      // 重置经停站信息
      if (this.oldStopOverList.length > 0) {
        this.form.stopOverList = Util.deepCopy(this.oldStopOverList)
      } else {
        let array = []
        array.push(Util.deepcopy(this.stopOver))
        this.form.stopOverList = array
      }
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
      // 登机口关联航站楼和运营机场
      if (Util.isNotNull(val)) {
        this.baseList.gateList = this.$cache.fetch('gate').filter(item => {
          return item.terminal === val && item.airportCode === this.form.segment.airportCode
        })
      } else {
        this.baseList.gateList = this.$cache.fetch('gate').filter(item => {
          return item.airportCode === this.form.segment.airportCode
        })
      }
    },
    bindAirport () {
      // 设置航线绑定起飞目的地
      this.$nextTick(() => {
        this.$refs['pairlineForm'].bindAirport(this.form.segment)
      })
    },
    setLinkFields () {
      // 设置字段与运营机场联动
      this.setLinkByAirport(this.form.segment.airportCode)
      this.form.segment.terminal = ''
      this.form.segment.gate = ''
      this.form.segment.generalAgent = ''
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
      width: 50%!important;
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

