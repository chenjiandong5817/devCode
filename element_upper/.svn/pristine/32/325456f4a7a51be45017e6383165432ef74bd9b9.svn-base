/*
 * @Author: ylj
 * @Date: 2017-10-22 20:52:32
 * @Last Modified by: ylj
 * @Last Modified time: 2017-11-30 16:51:36
 */
<template>
<div class="seaScheduleFormcls">
  <div :class="addOrUpClss">
  <el-dialog :title="title" v-model="visible" :close-on-click-modal="false" @close="handleClose" :modal="false" @open="setClassName">
    <el-form :model="form.seasonSeg" :rules="rules" label-position="right" label-width="80px" ref="seaScheduleForm">
      <el-row :gutter="2">
        <el-col :span="6">
          <el-form-item label="航季名称" prop="seasonId">
            <el-select v-model="form.seasonSeg.seasonId" placeholder="航季名称" @change="setDefSeaDate" filterable clearable>
              <el-option
                v-for="item in baseList.seasonList"
                :key="item.id"
                :label="item.seasonName"
                :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="计划类型" prop="planSource">
            <el-select v-model="form.seasonSeg.planSource" placeholder="计划类型" :filterable="true" clearable>
              <el-option
                v-for="item in baseList.planSources"
                :key="item.sourceCode"
                :label="item.description"
                :value="item.sourceCode">
                <span style="float: left">{{ item.sourceCode }}</span>
                <span style="float: right">{{ item.description }}</span>
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="6">
           <el-form-item label="开始日期" prop="beginDate">
            <date-time v-model="form.seasonSeg.beginDate" :visitTime="false" style="width: 100%;" datePlaceholder="开始日期" formatter="yyyy-MM-dd" dateStyle="width:100%"></date-time>
          </el-form-item>
        </el-col>

        <el-col :span="6">
           <el-form-item label="结束日期" prop="endDate">
            <date-time v-model="form.seasonSeg.endDate" :visitTime="false" style="width: 100%;" datePlaceholder="结束日期" formatter="yyyy-MM-dd" dateStyle="width:100%"></date-time>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="2">
        <el-col :span="6">
          <el-form-item label="运营机场" prop="airportCode">
            <el-select v-model="form.seasonSeg.airportCode" placeholder="运营机场" :filterable="true" clearable @change="setSeasonList">
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
          <el-form-item label="公 司" prop="carrier">
            <el-select v-model="form.seasonSeg.carrier" name="carrier" placeholder="公司" filterable clearable>
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
          <el-form-item label="航班号" prop="flightNo">
            <el-input v-model="form.seasonSeg.flightNo" name="flightNo" placeholder="航班号"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="进出港" prop="direction">
            <el-select v-model="form.seasonSeg.direction" name="direction" placeholder="进出" clearable filterable>
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

      <el-row :gutter="2">
        <el-col :span="6">
          <el-form-item label="任 务" prop="flightTask" >
            <el-select v-model="form.seasonSeg.flightTask" placeholder="任务" filterable filterable clearable>
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
          <el-form-item label="机 型" prop="aircraftType">
            <el-select v-model="form.seasonSeg.aircraftType" placeholder="机型" filterable remote :remote-method="airTypeRemote" clearable>
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
          <el-form-item label="总 代" prop="generalAgent" >
            <el-select v-model="form.seasonSeg.generalAgent" placeholder="总代" filterable clearable>
              <el-option
                v-for="item in baseList.generalagents"
                :key="item.agentCode"
                :label="item.description"
                :value="item.agentCode">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <!--班期计划-->
      <el-row :gutter="2">
        <el-col :span="24">
          <el-form-item label="航季周期（本站）" label-width="80px" class="labelCls">
            <el-card class="box-card boxCls">
              <el-row :gutter="2" style="padding-top: 10px;">
                <el-col :span="8">
                  <el-form-item label="周期类别" prop="isDepRef">
                      <el-radio-group v-model="form.seasonSeg.isDepRef">
                        <el-radio :label="true">起飞周期</el-radio>
                        <el-radio :label="false">落地周期</el-radio>
                      </el-radio-group>
                  </el-form-item>
                </el-col>
              </el-row>

              <el-row :gutter="2">
                <el-col :span="8">
                  <el-form-item label="执行周期" prop="strategyType">
                    <template>
                      <el-radio-group v-model="form.seasonSeg.strategyType" @change.native="initDayList">
                        <el-radio :label="2">月</el-radio>
                        <el-radio :label="1">周</el-radio>
                        <el-radio :label="3">日</el-radio>
                      </el-radio-group>
                    </template>
                  </el-form-item>
                </el-col>

                <el-col :span="7">
                  <el-form-item label="时间间隔" prop="interval" >
                    <el-input type="number" value="number" v-model.number="form.seasonSeg.interval" auto-complete="off" placeholder="时间间隔" style="width:65%;" :rules="[ { min: 0, message: '请输入大于等于零的整数' } ]"></el-input>
                  </el-form-item>
                </el-col>
              </el-row>

              <el-row v-if="form.seasonSeg.strategyType !== 3">
                <el-col :span="24">
                  <el-form-item label="" prop="dayList">
                    <span style="color: red">*</span>
                    <span>周期设定</span>
                    <el-checkbox :indeterminate="isIndeterminate" v-model="isCheckAll" @change="handleCkallChange" style="padding-left: 10px;">全选</el-checkbox>

                    <el-form-item label-width="0px" style="padding-left: 10px;">
                      <el-checkbox-group v-model="form.seasonSeg.dayList" @change="setExceCycle" v-if="form.seasonSeg.strategyType === 2">
                        <el-checkbox v-for="n in 31" :label="n" :key="n" style="width: 43px;">
                          <span class="el-checkbox__label ckMonthLabelCls"> {{ n }} </span>
                        </el-checkbox>
                      </el-checkbox-group>

                      <el-checkbox-group v-model="form.seasonSeg.dayList" @change="setExceCycle" v-else-if="form.seasonSeg.strategyType === 1">
                        <el-checkbox v-for="item in weekList" :label="item.value" :key="item.value">
                          <span class="el-checkbox__label ckWeekLabelCls"> {{ item.text }}  </span>
                        </el-checkbox>
                      </el-checkbox-group>
                    </el-form-item>
                  </el-form-item>
                </el-col>
              </el-row>
            </el-card>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="5">
        <el-col :span="20" >
          <el-form-item label="共享航班" >
            <el-input v-model="shareFlights" name="shareFlights" placeholder="共享航班" style="width: 82%" disabled></el-input>
            <el-button @click="showShareFlight" type="info" class="shareBtnCls" size="small">设置共享航班</el-button>
          </el-form-item>
        </el-col>

        <el-col :span="3">
          <el-form-item prop="flightFlag" label-width="0px">
            <span style="margin-right: 5px">取 消</span>
            <el-checkbox v-model="form.seasonSeg.flightFlag" true-label="3" @change="setFlightFlag"></el-checkbox>
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

      <el-form v-for="(item, index) in form.airSegList" :key="index" :model="form.airSegList[index]" label-position="top" label-width="80px" :ref="setFormid(index)">
      <el-row :gutter="2">
        <el-col :span="6">
          <el-form-item
            label="起飞地"
            prop="origin"
            label-width="70px"
            :rules="[ { required: true, message: '请选择始发站', trigger: 'blur' },
                      { validator: Rules.validSegment, message: '航线配置出错', preDestination: index !== 0 ? form.airSegList[index - 1].destination : index, trigger: 'change' } ]">
            <el-select v-model="form.airSegList[index].origin" filterable placeholder="始发站" clearable>
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

        <el-col :span="2">
          <el-form-item
            label="起飞时间"
            prop="scheduleDepartTime"
            :rules="[ { required: true, message: '请填写起飞时间', trigger: 'blur' },
                      { validator: Rules.validSegTime, message: '时间配置错', preTime: index !== 0 ? form.airSegList[index - 1].scheduleArriveTime : index, timeType: 'depTime', trigger: 'change,blur' } ]">
            <date-time v-model="form.airSegList[index].scheduleDepartTime" :visitTime="true" :visitDate="false" timeStyle="width: 100%;" datePlaceholder="起飞日期" timePlaceholder="时间" formatter="yyyy-MM-dd"></date-time>
          </el-form-item>
        </el-col>

        <el-col :span="2">
          <el-form-item
            label="阈值"
            prop="depOffset"
            :rules="[ { type: 'number', min: -2, max: 2, message: '阈值[-2, 2]', trigger: 'blur' } ]">
            <el-input type="number" value="number" v-model.number="form.airSegList[index].depOffset" auto-complete="off" placeholder="阈值" style="width:100%;" @change="setTime(index, form.airSegList[index].depOffset, 'depOffset')"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item
            label="目的地"
            prop="destination"
            label-width="70px"
            :rules="[ { required: true, message: '请选择目的站', trigger: 'blur' } ]">
            <el-select v-model="form.airSegList[index].destination" filterable placeholder="目的站" clearable @change="setFlightNatureVal(index, form.airSegList[index].destination)">
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

        <el-col :span="2">
          <el-form-item
            label="降落时间"
            prop="scheduleArriveTime"
            :rules="[ { required: true, message: '请填写降落时间', trigger: 'blur' },
                      { validator: Rules.validSegTime, message: '时间配置错', preTime: form.airSegList[index].scheduleDepartTime, timeType: 'arrTime', trigger: 'change,blur' } ]">
            <date-time v-model="form.airSegList[index].scheduleArriveTime" :visitTime="true" :visitDate="false" timeStyle="width: 100%;" datePlaceholder="降落日期" timePlaceholder="时间" formatter="yyyy-MM-dd" :rules="[ { required: true, message: '请填写降落时间', trigger: 'blur' } ]"></date-time>
          </el-form-item>
        </el-col>

        <el-col :span="2">
          <el-form-item
            label="阈值"
            prop="arrOffset"
            :rules="[ { type: 'number', min: -2, max: 2, message: '阈值[-2, 2]', trigger: 'blur' } ]">
            <el-input type="number" value="number" v-model.number="form.airSegList[index].arrOffset" auto-complete="off" placeholder="阈值" style="width:100%;" :rules="[ { min: 0, max: 1, message: '请填写不大于1的整数' } ]" @change="setTime(index, form.airSegList[index].arrOffset, 'arrOffset')"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="2">
          <el-form-item label="区域属性"  prop="flightNature">
            <el-select v-model="form.airSegList[index].flightNature" placeholder="区域" style="width: 109%; color: #fff;" filterable remote clearable :disabled="true">
              <el-option
                v-for="item in $cache.fetch('flightnatures')"
                :key="item.flightNatureCode"
                :label="item.description"
                :value="item.flightNatureCode">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="2">
          <el-form-item label=" ">
            <el-button type="info" @click.native="addAirSegList(index)" class="el-icon-plus buttonClss" size="mini" ></el-button>
            <el-button type="info" @click.native="delAirSegment(index)" class="el-icon-minus buttonClss" size="mini" v-if="index !== 0"></el-button>
          </el-form-item>
        </el-col>
      </el-row>
      </el-form>

    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click.native="handleClose">取消</el-button>
      <el-button @click.native="resetForm('seaScheduleForm')" >重置</el-button>
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
import Util from '../../../common/js/util'
import Butil from '../../../common/js/base-util'
import Rules from './../../../common/js/rules'
import API from '../../../api'
import dateTime from '../../../components/DateTime'
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
      position: 'right',
      tableLoading: false,
      API: API,
      Rules: Rules,
      form: {
        seasonSeg: {
          id: null,
          flightId: null,
          linkedFlightId: null,
          planSource: null,
          airportCode: null,
          direction: null,
          generalAgent: null,
          carrier: null,
          flightNo: null,
          aircraftType: null,
          flightTask: null,
          origin: null,
          destination: null,
          scheduleDepartTime: null,
          scheduleArriveTime: null,
          segmentIndex: null,
          segmentNature: null,
          flightNature: null,
          seasonId: null,
          strategy: null,
          beginDate: null,
          endDate: null,
          flightFlag: null,
          strategyType: 1,
          interval: null,
          depOffset: 0,
          arrOffset: 0,
          dayList: [],
          isDepRef: true
        },
        airSegList: []
      },
      date: Util.formatDate.flightDateFmt('yyyy-MM-dd', new Date(), false),
      airSegment: {id: null, flightId: null, origin: '', destination: '', flightNature: '', segmentNature: '', scheduleDepartTime: '', scheduleArriveTime: '', depOffset: null, arrOffset: null},
      isCheckAll: false,
      isIndeterminate: false,
      currentSopOver: null,
      newSeasonFlightls: [],
      oldSeasonFlightls: [],
      firstSeg: null,
      lastSeg: null,
      delStopOverIdLs: [],
      curSelectRow: [],
      sysUserId: null,
      subAirportLs: [],
      baseList: { planSources: this.$cache.fetch('planSource'),
                  airlines: this.$cache.fetch('airlines'),
                  flighttasks: this.$cache.fetch('flighttasks'),
                  aircraftTypes: this.$cache.fetch('aircraftTypes'),
                  flightdirections: this.$cache.fetch('flightdirections'),
                  generalagents: this.$cache.fetch('generalagents'),
                  airports: this.$cache.fetch('airports'),
                  seasonList: this.$cache.fetch('seasonName')
                 },
      isShareFlight: false,
      shareFlights: '',
      oldShareFlightLs: [],
      shareFlightLs: [],
      deleteShareIdList: [],
      weekList: [{value: 1, text: '周一'}, {value: 2, text: '周二'}, {value: 3, text: '周三'}, {value: 4, text: '周四'}, {value: 5, text: '周五'}, {value: 6, text: '周六'}, {value: 7, text: '周日'}],
      rules: {
        seasonId: [ { required: true, message: '请选择航季名称', trigger: 'blur' } ],
        planSource: [ { required: true, message: '请选择计划类型', trigger: 'blur' } ],
        beginDate: [ { required: true, message: '请选择开始时间', trigger: 'blur' } ],
        endDate: [ { required: true, message: '请选择结束时间', trigger: 'blur' } ],
        airportCode: [ { required: true, message: '请选择运营机场', trigger: 'blur' } ],
        carrier: [ { required: true, message: '请选择公司', trigger: 'blur' } ],
        flightNo: [ { required: true, message: '请选择航班号', trigger: 'blur' } ],
        direction: [ { required: true, message: '请选择进出标志', trigger: 'blur' } ],
        aircraftType: [ { required: true, message: '请选择机型', trigger: 'blur' } ],
        flightNature: [ { required: true, message: '请选择区域', trigger: 'blur' } ],
        // origin: [ { required: true, message: '请选择始发站', trigger: 'blur' } ],
        // scheduleDepartTime: [ { required: true, message: '请填写起飞时间', trigger: 'blur' } ],
        // destination: [ { required: true, message: '请选择目的站', trigger: 'blur' } ],
        // scheduleArriveTime: [ { required: true, message: '请填写降落时间', trigger: 'blur' } ],
        dayList: [ { validator: Rules.arrayRequire, message: '请设置执行周期', trigger: 'blur' } ]
      },
      airSegRules: {
        origin: [ { required: true, message: '请选择始发站', trigger: 'blur' } ],
        scheduleDepartTime: [ { required: true, message: '请填写起飞时间', trigger: 'blur' } ],
        destination: [ { required: true, message: '请选择目的站', trigger: 'blur' } ],
        scheduleArriveTime: [ { required: true, message: '请填写降落时间', trigger: 'blur' } ]
      },
      oldAirSegList: []
    }
  },
  components: {
    dateTime: dateTime,
    shareFlight: shareFlight
  },
  methods: {
    setFlightFlag (val) {
      if (val !== '3') {
        this.form.seasonSeg.flightFlag = this.oldSeasonFlightls.length > 0 ? this.oldSeasonFlightls[0].flightFlag : '0'
      }
    },
    initDayList () {
      this.form.seasonSeg.dayList = []
      this.isIndeterminate = false
    },
    handleCkallChange (val) {
      this.form.seasonSeg.dayList = this.isCheckAll ? this.setDays() : []
      this.isIndeterminate = false
    },
    setDays () {
      let array = []
      let count = this.form.seasonSeg.strategyType === 1 ? 7 : 31
      for (var i = 1; i <= count; i++) {
        array.push(i)
      }
      return array
    },
    setExceCycle (val) {
      let ckCount = val.length
      let count = this.form.seasonSeg.strategyType === 1 ? 7 : 31
      this.isCheckAll = ckCount === count
      this.isIndeterminate = ckCount > 0 && ckCount < count
    },
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
    setSeasonList (airportCode) {
      let date = Util.formatDate.flightDateFmt('yyyy-MM-dd', new Date(), false)
      let list = this.$cache.fetch('seasonName').filter(item => {
        return item.airportCode === airportCode && date <= item.endDate
      })
      this.baseList.seasonList = list
    },
    show (row, airportCode) {
      // 1. 设置航段默认日期值
      this.airSegment.scheduleDepartTime = this.date
      this.airSegment.scheduleArriveTime = this.date
      if (row !== undefined && row !== null) {
        this.operate = 'Edeit'
        this.curSelectRow = Util.deepCopy(row)
        // 添加航班其他属性信息
        this.loading = true
        let para = { pageSize: 0, flightId: row.flightId }
        API.getSeasonSchedulels().go(para).then((data) => {
          if (data.ok) {
            this.oldSeasonFlightls = data.attr.data.list
            // 1. 获取实航段信息
            let actSegmentLs = Util.deepCopy(data.attr.data.list)
            this.airSegment.flightId = actSegmentLs.length > 0 ? actSegmentLs[0].flightId : null
            // 2. 绑定显示字段数据
            this.firstSeg = Util.deepCopy(actSegmentLs[0])
            this.lastSeg = Util.deepCopy(actSegmentLs[actSegmentLs.length - 1])

            this.form.seasonSeg = Object.assign({}, this.form.seasonSeg, this.firstSeg)
            this.form.seasonSeg.destination = this.lastSeg.destination
            this.form.seasonSeg.scheduleArriveTime = this.lastSeg.scheduleArriveTime
            // 3. 绑定航班航段信息
            this.bindAirSegList(actSegmentLs)
            // 4. 绑定航季周期 1030
            this.form.seasonSeg.dayList = this.setDayList()
            this.setExceCycle(this.form.seasonSeg.dayList)
            // 5. 设置共享航班信息
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
        this.visible = true
        // this.setDefauleDate(new Date(), 'yyyy-MM-dd')
      }
      this.setSeasonList(this.form.seasonSeg.airportCode)
      this.loading = false
      this.setClassName()
    },
    setDayList () {
      let s = this.form.seasonSeg.strategy
      let array = []
      if (s !== null && s.length > 0) {
        let t = (s.indexOf('/') > 0 ? s.substring(0, s.indexOf('/')) : s)
        let temp = []
        this.form.seasonSeg.interval = (this.form.seasonSeg.strategyType === 3 ? s : s.substring(s.indexOf('/') + 1, s.length))
        temp = t.split(',')
        array = temp.map(function (data) {
          return +data
        })
      }
      return array
    },
    bindAirSegList (actSegmentLs) {
      // 绑定经停站
      var airSegList = []
      for (var i = 0; i < actSegmentLs.length; i++) {
        let airSeg = Util.deepCopy(this.airSegment)
        // 设置经停站其他时间信息
        airSeg.id = actSegmentLs[i].id
        airSeg.flightId = actSegmentLs[i].flightId
        airSeg.origin = actSegmentLs[i].origin
        airSeg.destination = actSegmentLs[i].destination
        airSeg.scheduleArriveTime = actSegmentLs[i].scheduleArriveTime
        airSeg.scheduleDepartTime = actSegmentLs[i].scheduleDepartTime
        airSeg.flightNature = actSegmentLs[i].flightNature
        airSeg.segmentNature = actSegmentLs[i].segmentNature
        airSeg.depOffset = actSegmentLs[i].depOffset
        airSeg.arrOffset = actSegmentLs[i].arrOffset
        airSegList[i] = airSeg
      }
      this.form.airSegList = Util.deepCopy(airSegList)
      this.oldAirSegList = Util.deepCopy(airSegList)
    },
    initData (airportCode) {
      // 新增时初始化数据
      for (var key in this.form.seasonSeg) {
        this.form.seasonSeg[key] = null
      }
      this.form.seasonSeg.strategyType = 1
      this.form.seasonSeg.depOffset = 0
      this.form.seasonSeg.arrOffset = 0
      this.form.seasonSeg.isDepRef = true
      this.form.seasonSeg.dayList = []
      if (airportCode !== undefined) {
        // 设置航班运营机场
        this.form.seasonSeg.airportCode = airportCode
      }
      let array = []
      array.push(Util.deepCopy(this.airSegment))
      this.form.airSegList = array
      this.oldAirSegList = []
      this.shareFlights = ''
      this.oldShareFlightLs = []
      this.shareFlightLs = []
      this.curSelectRow = []
      this.delStopOverIdLs = []
    },
    handleClose: function () {
      this.visible = false
      this.editAble = false
      this.shareFlightLs = []
      this.oldShareFlightLs = []
      this.deleteShareIdList = []
      this.delStopOverIdLs = []
      this.oldAirSegList = []
    },
    handAddOrUptSubmit: function () {
      let segList = Util.deepCopy(this.form.airSegList)
      this.newSeasonFlightls = []
      // 1. 设置实航段信息
      for (var x = 0; x < segList.length; x++) {
        let s = Object.assign({}, Util.deepCopy(this.form.seasonSeg), segList[x])
        // 设置航段信息
        s.flightNature = this.setFlightNature(s.destination)
        s.segmentNature = '1'
        s.segmentIndex = x
        // 设置执行周期
        s.strategy = this.form.seasonSeg.dayList.join(',')
        if (this.form.seasonSeg.strategyType !== 3) {
          s.strategy += '/' + (this.form.seasonSeg.interval === null ? 0 : this.form.seasonSeg.interval)
        }
        this.newSeasonFlightls.push(s)
      }
      // 2. 设置虚航段信息 航季无需保存虚航段信息 1124
      // for (var i = 0; i < segList.length; i++) {
      //   for (var j = i + 1; j < segList.length; j++) {
      //     let s = Object.assign({}, Util.deepCopy(this.form.seasonSeg), segList[j])
      //     // 设置航段信息
      //     s.origin = segList[i].origin
      //     s.scheduleDepartTime = segList[i].scheduleDepartTime
      //     s.depOffset = segList[i].depOffset
      //     s.flightNature = this.setFlightNature(s.destination)
      //     s.segmentNature = '0'
      //     s.segmentIndex = 0
      //     // 设置执行周期
      //     s.strategy = this.form.seasonSeg.dayList.join(',')
      //     if (this.form.seasonSeg.strategyType !== 3) {
      //       s.strategy += '/' + (this.form.seasonSeg.interval === null ? 0 : this.form.seasonSeg.interval)
      //     }
      //     this.newSeasonFlightls.push(s)
      //   }
      // }
      var para = { newValue: this.newSeasonFlightls, oldValue: this.oldSeasonFlightls, deleteIdList: this.delStopOverIdLs, shareFlightLs: this.shareFlightLs, deleteShareIdList: this.deleteShareIdList }
      this.$refs['seaScheduleForm'].validate((valid) => {
        let segValid = this.segValid()
        if (valid && segValid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.loading = true
            this.to(para).then((res) => {
              this.loading = false
              this.$notify(Util.notifyBody(res.ok, res.msg))
              // this.resetForm('seaScheduleForm')
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
    resetForm (formName) {
      this.$refs[formName].resetFields()
      // 重置航线校验
      for (let i = 0; i < this.form.airSegList.length; i++) {
        this.$refs['airSegForm' + i][0].resetFields()
      }
      // 航班航段信息重新
      // for (var i = 0; i < this.form.airSegList.length; i++) {
      //   let formid = 'airSegForm' + i
      //   this.$refs[formid][0].resetFields()
      // }
      // 航班航段信息、共享航班信息重置
      if (this.oldAirSegList.length > 0) {
        this.form.airSegList = Util.deepCopy(this.oldAirSegList)
      } else {
        let list = []
        list.push(this.airSegment)
        this.form.airSegList = list
      }
      this.shareFlightLs = Util.deepCopy(this.oldShareFlightLs)
      this.delStopOverIdLs = []
    },
    airTypeRemote (query) {
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
    setFlightNature (destination) {
      // 设置航班属性
      let obj = this.baseList.airports.find((item) => {
          return item.icaocode === destination
      })
      return obj !== undefined ? obj.airportnature : null
    },
    setFlightNatureVal (index, val) {
      let flightNature = this.setFlightNature(val)
      this.form.airSegList[index].flightNature = flightNature
    },
    showShareFlight () {
      // 展示共享航班编辑界面
      let curSegList = this.setCurSegmentList()
      this.$refs['shareFlightForm'].show(curSegList, this.shareFlightLs)
    },
    setCurSegmentList () {
      // 拼接本场航段
      let list = []
      let origin = this.$cache.findByName('airports', 'icaocode', this.form.seasonSeg.origin, 'cnabbr2w') + '(' + this.form.seasonSeg.origin + ')'
      let last = this.$cache.findByName('airports', 'icaocode', this.form.seasonSeg.destination, 'cnabbr2w') + '(' + this.form.seasonSeg.destination + ')'
      for (var i = 0; i < this.form.airSegList.length - 1; i++) {
        let seg = {}
        let name = this.$cache.findByName('airports', 'icaocode', this.form.airSegList[i].destination, 'cnabbr2w')
        let destination = name + '(' + this.form.airSegList[i].destination + ')'
        seg = { segmentName: origin + '-' + destination, origin: this.form.seasonSeg.origin, destination: this.form.airSegList[i].destination }
        list.push(seg)
      }
      list[list.length] = { segmentName: origin + '-' + last, origin: this.form.seasonSeg.origin, destination: this.form.seasonSeg.destination }
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
    },
    setDefSeaDate (val) {
      // 设置默认航季开始结束时间
      var list = this.baseList.seasonList.filter(item => {
        return item.id === val && item.airportCode === this.form.seasonSeg.airportCode
      })
      if (list.length > 0) {
        this.form.seasonSeg.beginDate = list[0].beginDate
        this.form.seasonSeg.endDate = list[0].endDate
      }
      this.form.seasonSeg.planSource = 'B'
    },
    addAirSegList (index) {
      // 新增航段
      let airSegment = Util.deepCopy(this.airSegment)
      airSegment.origin = this.form.airSegList[index].destination
      if (index === this.form.airSegList.length - 1) {
        this.form.airSegList.push(airSegment)
      } else {
        this.form.airSegList.splice(index + 1, 0, airSegment)
      }
    },
    delAirSegment (index) {
      let curObj = Util.deepCopy(this.form.airSegList[index])
      // 1. 保存删除ID航段信息
      if (curObj.id !== null && curObj.id !== '') {
        this.delStopOverIdLs.push(curObj.id)
        this.delStopOverIdLs[this.delStopOverIdLs.length] = curObj.id
      }
      this.form.airSegList.splice(index, 1)
    },
    setFormid (index) {
      return 'airSegForm' + index
    },
    segValid () {
      // 航线配置验证
      let validResult = 'true'
      for (let i = 0; i < this.form.airSegList.length; i++) {
        this.$refs['airSegForm' + i][0].validate((valid) => {
          if (valid) {
            validResult += 'true'
          } else {
            validResult += 'false'
          }
        })
      }
      if (validResult.indexOf('false') > 0) {
        return false
      } else {
        return true
      }
    },
    setTime (index, val, field) {
      let moment = require('moment')
      let date = new Date()
      if (val !== null && typeof val === 'number') {
        let time = null
        if (field === 'depOffset') {
          time = moment(date).format('YYYY-MM-DD') + ' ' + moment(this.form.airSegList[index].scheduleDepartTime).format('HH:mm:ss')
          time = moment(time).add(val, 'days')
          this.form.airSegList[index].scheduleDepartTime = time.format('YYYY-MM-DD HH:mm:ss')
        } else {
          time = moment(date).format('YYYY-MM-DD') + ' ' + moment(this.form.airSegList[index].scheduleArriveTime).format('HH:mm:ss')
          time = moment(time).add(val, 'days')
          this.form.airSegList[index].scheduleArriveTime = time.format('YYYY-MM-DD HH:mm:ss')
        }
      }
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
.seaScheduleFormcls {
  .el-form-item {
    margin-bottom: 20px;
  }
  .el-dialog__body {
    padding-top: 5px;
  }
  .el-table {
    margin-left: 0px!important;
    margin-right: 0px!important;
  }
  .smallDigCls {
    .el-dialog--small {
      width: 47%!important;
    }
  }
  .largeDigCls {
    .el-dialog--small {
      width: 67%!important;
    }
  }
  .el-dialog {
    margin-top: -4.5%!important;
  }
  .signClass [name=carrier],.signClass [name=flightNo],.signClass [name=direction] {
    background: #FFA500!important;
    color: black!important;
  }
  input {
    color: black!important;
  }
  .shareBtnCls {
    width: 100px!important;
    margin-left: 5px;
  }
  .boxCls {
    width: 100%;
    min-height: 126px;
    // padding-bottom: 20px;
    border: 1px solid #000;
  }
  .el-card__body {
    padding: 0px 0px 20px 5px;
  }
  .labelCls > .el-form-item__label:first-child {
    padding: 10px 12px 11px 12px;
    text-align: center;
  }
  .el-checkbox+.el-checkbox {
    margin-left: 0px;
  }
  .el-checkbox {
    margin-right: 5px!important;
  }
  .ckMonthLabelCls {
    float: left;
    text-align: right;
    padding-left: 0px;
    padding-right: 5px;
    width: 15px;
  }
  .ckWeekLabelCls {
    float: left;
    text-align: right;
    padding-left: 0px;
    padding-right: 8px;
    width: 30px;
  }

  .buttonClss {
    margin-top: 15px!important;
    margin-left: 7px!important;
  }
}
</style>

