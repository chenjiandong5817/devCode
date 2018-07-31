<template>
<section class="resDetailClass">
  <section v-for="(item, index) in form.resLists" :key="index">
  <el-form :ref="setFormId(index)" :model="form.resLists[index]" :label-position="labelPosition" :rules="rules">
    <el-row :gutter="18" v-for="(rowItem, rowIndex) in resFields" :key="rowIndex" class="content">
      <el-col :span="6" v-for="(ritem, cIndex) in rowItem" :key="ritem.name" v-if="!ritem.hidden">
        <el-form-item :label="ritem.label" :prop="ritem.name" :key="item.id" v-if="!ritem.hidden">
          <el-input :type="ritem.type" v-model="form.resLists[index][ritem.name]" auto-complete="off" :disabled="isDisabled(index)"
          v-if="ritem.type === 'text' || ritem.type === 'textarea' || ritem.type === 'password'"></el-input>
          <!-- number判断 -->
          <el-input :type="ritem.type" value="number" v-model.number="form.resLists[index][ritem.name]" auto-complete="off" v-else-if="ritem.type === 'number'" :v-show="ritem.show" :disabled="isDisabled(index)"></el-input>
          <!-- 时间判断 -->
          <el-date-picker :type="item.type" v-model="form.resLists[index][ritem.name]" v-else-if="item.type === 'datetime'" :disabled="isDisabled(index)">
          </el-date-picker>
          <date-time v-model="form.resLists[index][ritem.name]" :visitTime="ritem.visitTime" :datePlaceholder="ritem.datePlaceholder" :timePlaceholder="ritem.timePlaceholder" :formatter="ritem.formatter" v-else-if="ritem.type === 'dateTimeGroup'" :allDisable="isDisabled(index)"></date-time>
          <!--radio判断 -->
          <el-radio-group v-model="form.resLists[index][ritem.name]" v-else-if="ritem.type === 'radio'">
            <el-radio v-for="radioItem in ritem.choose" class="radio" :label="radioItem.value" :key="radioItem.value">{{radioItem.text}}</el-radio>
          </el-radio-group>
          <!-- select判断 -->
          <el-select class="selectA" v-model="form.resLists[index][ritem.name]" :filterable="ritem.filterable" :clearable="ritem.clearable" :disabled="isDisabled(index)" placeholder="请选择" v-else-if="ritem.type === 'select' && !ritem.isLink && !ritem.isSetChange" >
            <el-option
              v-for="selectItem in ritem.choose"
              :key="selectItem[ritem.selVal]"
              :label="selectItem[ritem.selText]"
              :value="selectItem[ritem.selVal]">
            </el-option>
          </el-select>

          <!-- select判断 设置联动 -->
          <el-select class="selectA" v-model="form.resLists[index][ritem.name]" :filterable="ritem.filterable" :clearable="ritem.clearable" :disabled="isDisabled(index)" placeholder="请选择" v-else-if="ritem.type === 'select' && !ritem.isLink && ritem.isSetChange" @change="setLinkVal(ritem.changeFun, form.resLists[index][ritem.name], index, rowIndex, cIndex)">
            <el-option
              v-for="selectItem in resFieldList[index][rowIndex][cIndex].choose"
              :key="selectItem[resFieldList[index][rowIndex][cIndex].selVal]"
              :label="selectItem[resFieldList[index][rowIndex][cIndex].selText]"
              :value="selectItem[resFieldList[index][rowIndex][cIndex].selVal]">
            </el-option>
          </el-select>

          <!-- select判断 设置联动 资源信息绑定 -->
          <el-select class="selectA" v-model="form.resLists[index].res[ritem.name]" :filterable="ritem.filterable" :clearable="ritem.clearable" :disabled="isDisabled(index)" placeholder="请选择" v-else-if="ritem.type === 'select' && ritem.isLink && ritem.isSetChange" @change="setLinkVal(ritem.changeFun, form.resLists[index].res[ritem.name], index, rowIndex, cIndex)">
            <el-option
              v-for="selectItem in resFieldList[index][rowIndex][cIndex].choose"
              :key="selectItem[resFieldList[index][rowIndex][cIndex].selVal]"
              :label="selectItem[resFieldList[index][rowIndex][cIndex].selText]"
              :value="selectItem[resFieldList[index][rowIndex][cIndex].selVal]">
            </el-option>
          </el-select>

        </el-form-item>
      </el-col>
    </el-row>

    <el-row>
      <div class="buttonCls">
        <span class="wrapper">
          <el-button size="small" type="success" @click="openOrCloseRes(index, true)">开启</el-button>
          <el-button size="small" type="danger" @click="openOrCloseRes(index, false)">关闭</el-button>
          <el-button size="small" type="info" @click="addResInfo(index)" v-if="index === form.resLists.length - 1">新增</el-button>
          <el-button size="small" type="warning" @click="delResInfo(index)">删除</el-button>
        </span>
      </div>
    </el-row>
    <hr class="hrClass" v-if="index !== form.resLists.length - 1"/>
  </el-form>
  </section>
  <section v-if="form.resLists.length === 0">
    <div class="buttonCls">
      <span class="wrapper">
        <el-button size="small" type="info" @click="addResInfo(0)">新增</el-button>
      </span>
    </div>
  </section>
</section>
</template>

<script>
import Util from '../../../common/js/util'
import API from '../../../api'
import dateTime from '../../../components/DateTime'

export default {
  props: {
    title: {
      type: String,
      default: '新增'
    },
    labelPosition: {
      type: String,
      default: 'top'
    },
    to: {
      type: Function,
      default: function () {}
    },
    initData: {
      type: Function,
      default: function () {}
    },
    callback: {
      type: Function,
      default: function () {}
    },
    openOpt: {
      type: Function,
      default: function () {}
    },
    closeOpt: {
      type: Function,
      default: function () {}
    },
    delOpt: {
      type: Function,
      default: function () {}
    }
  },
  data () {
    return {
      editAble: false,
      loading: false,
      curTabName: '',
      API: API,
      form: {
        res: {},
        resLists: [],
        oldResLists: []
      },
      resFields: [],
      rules: {},
      flight: {},
      delResIdList: [],
      baseList: {},
      resFieldList: [],
      date: Util.formatDate.format(new Date(), 'yyyy-MM-dd') + ' 00:00:00'
    }
  },
  components: {
    dateTime: dateTime
  },
  computed: {
    counterConf: function () {
      let array = []
      if (this.curTabName === 'counterConfForm') {
        array = [
          [{ name: 'id', value: '', hidden: true, rules: null },
          { name: 'flightId', value: this.flight.id, hidden: true, rules: null },
          { name: 'flight', value: this.flight.flightNo, hidden: true, rules: null },
          { name: 'carrier', value: this.flight.carrier, hidden: true, rules: null },
          { name: 'flightTask', value: this.flight.flightTask, hidden: true, rules: null },
          { name: 'airportCode', value: this.flight.airportCode, hidden: true, rules: null },
          { name: 'checkinCounterId', value: null, hidden: true, rules: null },
          { name: 'terminal', value: this.flight.terminal, label: '航站楼', selVal: 'terminalCode', selText: 'terminalCode', type: 'select', filterable: true, clearable: true, choose: this.$cache.fetch('terminals'), rules: [ { required: true, message: '请选择航站楼' } ], isLink: false, isSetChange: true, changeFun: 'setLinkLs', linklsName: 'counterGroupls', linkRIndex: 0, linkCIndex: 8, linkField: 'terminal' },
          { name: 'checkinGroup', value: null, label: '值机岛', selVal: 'checkinGroup', selText: 'checkinGroup', type: 'select', filterable: true, clearable: true, choose: this.baseList.ckinGroup, rules: [ { required: true, message: '请选择值机岛编码' } ], isLink: false, isSetChange: true, changeFun: 'setCkcounterLinkLs', linkRIndex: 0, linkCIndex: 9 },
          { name: 'counterCode', value: null, label: '值机柜台', selVal: 'counterCode', selText: 'counterCode', type: 'select', filterable: true, clearable: true, choose: this.baseList.counterList, rules: [ { required: true, message: '请选择值机柜台编码' } ], isLink: false, isSetChange: true, changeFun: 'setCkCounterInfo' },
          { name: 'counterMode', value: null, label: '操作模式', selVal: 'opModeCode', selText: 'description', type: 'select', filterable: true, clearable: true, choose: this.$cache.fetch('ckcounteropmodes'), rules: [ { required: true, message: '请选择操作模式' } ], isLink: false, isSetChange: true, changeFun: 'setLinkLs', linklsName: 'ckCounterSerTypes', linkRIndex: 1, linkCIndex: 0, linkField: 'ckCounterTemplate' }],
          [{ name: 'serviceType', value: null, label: '服务类型', selVal: 'serviceTypeCode', selText: 'description', type: 'select', filterable: true, clearable: true, choose: this.$cache.fetch('ckcounterservicetypes'), rules: [ { required: true, message: '请选择服务类型' } ], isLink: false, isSetChange: false },
          { name: 'scheduleOpen', value: this.date, label: '开办时间', visitTime: true, type: 'dateTimeGroup', datePlaceholder: '开办日期', timePlaceholder: '时间', formatter: 'yyyy-MM-dd', rules: null },
          { name: 'scheduleClose', value: this.date, label: '停办时间', visitTime: true, type: 'dateTimeGroup', datePlaceholder: '停办日期', timePlaceholder: '时间', formatter: 'yyyy-MM-dd', rules: null }]
        ]
      } else {
        return this.counterConf
      }
      return array
    },
    gateConf: function () {
      let array = []
      if (this.curTabName === 'gateConfForm') {
        array = [
          [{ name: 'id', value: '', hidden: true, rules: null },
          { name: 'flightSegmentId', value: this.flight.id, hidden: true, rules: null },
          { name: 'airportCode', value: this.flight.airportCode, hidden: true, rules: null },
          { name: 'gateId', value: null, hidden: true, rules: null },
          { name: 'gateCode', value: null, label: '登机口', selVal: 'gateCode', selText: 'displayCode', type: 'select', filterable: true, clearable: true, choose: this.baseList.gateList, rules: [ { required: true, message: '请选择登机口编码' } ], isLink: false, isSetChange: true, changeFun: 'setGateInfo' },
          { name: 'boardingIndex', value: 1, hidden: false, label: '使用次序', type: 'number', rules: null },
          { name: 'scheduleOpen', value: this.date, label: '开始时间', visitTime: true, type: 'dateTimeGroup', datePlaceholder: '开始日期', timePlaceholder: '时间', formatter: 'yyyy-MM-dd', rules: null },
          { name: 'scheduleClose', value: this.date, label: '结束时间', visitTime: true, type: 'dateTimeGroup', datePlaceholder: '结束日期', timePlaceholder: '时间', formatter: 'yyyy-MM-dd', rules: null }]
        ]
      } else {
        return this.gateConf
      }
      return array
    },
    carConf: function () {
      let array = []
      if (this.curTabName === 'carConfForm') {
        array = [
          [{ name: 'id', value: '', hidden: true, rules: null },
          { name: 'airportCode', value: this.flight.airportCode, hidden: true, rules: null },
          { name: 'flightSegmentId', value: this.flight.id, hidden: true, rules: null },
          { name: 'carouselId', value: null, hidden: true, rules: null },
          { name: 'ruleId', value: null, hidden: true, rules: null },
          { name: 'terminal', value: this.flight.terminal, label: '航站楼', selVal: 'terminalCode', selText: 'terminalCode', type: 'select', filterable: true, clearable: true, choose: this.$cache.fetch('terminals'), rules: null, isLink: true, isSetChange: true, changeFun: 'setLinkLs', linklsName: 'carList', linkRIndex: 0, linkCIndex: 6, linkField: 'terminal' },
          { name: 'carouselCode', value: null, label: '转盘编码', selVal: 'carouselCode', selText: 'carouselCode', type: 'select', filterable: true, clearable: true, choose: this.baseList.carList, rules: [ { required: true, message: '请选择转盘编码' } ], isLink: false, isSetChange: true, changeFun: 'setCarlInfo' },
          { name: 'scheduleOpen', value: this.date, label: '开启时间', visitTime: true, type: 'dateTimeGroup', datePlaceholder: '开启日期', timePlaceholder: '时间', formatter: 'yyyy-MM-dd', rules: null },
          { name: 'scheduleClose', value: this.date, label: '关闭时间', visitTime: true, type: 'dateTimeGroup', datePlaceholder: '关闭日期', timePlaceholder: '时间', formatter: 'yyyy-MM-dd', rules: null }]
        ]
      } else {
        return this.carConf
      }
      return array
    },
    standConf: function () {
      let array = []
      if (this.curTabName === 'standConfForm') {
        array = [
          [{ name: 'id', value: '', hidden: true, rules: null },
          { name: 'currentAircraft', value: this.flight.registration, hidden: true, rules: null },
          { name: 'aFlightId', value: this.flight.direction === 'A' ? this.flight.id : this.flight.linkedFlightId, hidden: true, rules: null },
          { name: 'dFlightId', value: this.flight.direction === 'D' ? this.flight.linkedFlightId : this.flight.id, hidden: true, rules: null },
          { name: 'linkedId', value: null, hidden: true, rules: null },
          { name: 'airportCode', value: this.flight.airportCode, hidden: true, rules: null },
          { name: 'standId', value: null, hidden: true, rules: null },
          { name: 'standCode', value: null, label: '机位编码', selVal: 'standCode', selText: 'standCode', type: 'select', filterable: true, clearable: true, choose: this.baseList.standList, rules: [ { required: true, message: '请选择机位编码' } ], isLink: false, isSetChange: true, changeFun: 'setStandInfo' },
          { name: 'action', value: null, label: '动作', selVal: 'value', selText: 'text', type: 'select', filterable: true, clearable: true, choose: [ { text: '初始', value: '0' }, { text: '移动', value: '1' }, { text: '顶推', value: '2' }, { text: '作废', value: '-1' } ], rules: [ { required: true, message: '请选择占用类型' } ], isLink: false, isSetChange: false },
          { name: 'estOccupiedBegin', value: this.date, label: '开始时间', visitTime: true, type: 'dateTimeGroup', datePlaceholder: '开始日期', timePlaceholder: '时间', formatter: 'yyyy-MM-dd', rules: null },
          { name: 'estOccupiedEnd', value: this.date, label: '结束时间', visitTime: true, type: 'dateTimeGroup', datePlaceholder: '结束日期', timePlaceholder: '时间', formatter: 'yyyy-MM-dd', rules: null }]
        ]
      } else {
        return this.standConf
      }
      return array
    }
  },
  methods: {
    bindData () {
      let para = {}
      if (!this.editAble) {
        if (this.curTabName === 'standConfForm') {
          // 机位分配，关联进出港id
          let currentAircraft = this.form.res.currentAircraft
          let aFlightId = this.form.res.aFlightId
          let dFlightId = this.form.res.dFlightId
          para = { currentAircraft: currentAircraft, aFlightId: aFlightId, dFlightId: dFlightId }
        } else if (this.curTabName === 'counterConfForm') {
          // 值机柜台分配
          para = {flightId: this.flight.id}
        } else {
          para = {flightSegmentId: this.flight.id}
        }
        this.form.resLists.push(Util.deepCopy(this.form.res))
        this.resFieldList.push(Util.deepCopy(this.resFields))
        this.initData(para).then((data) => {
          this.loading = false
          if (data.ok) {
            if (data.attr.data.list.length > 0) {
              this.form.resLists = Util.deepCopy(data.attr.data.list)
              this.form.oldResLists = Util.deepCopy(this.form.resLists)
              this.resFieldList = []
              for (var i = 0; i < data.attr.data.list.length; i++) {
                this.resFieldList.push(Util.deepCopy(this.resFields))
              }
            }
          }
          this.editAble = 'true'
        })
      }
    },
    initResData: function () {
      this.editAble = false
      this.loading = false
      this.curTabName = ''
      this.form = {
        res: {},
        resLists: [],
        oldResLists: []
      }
      this.resFields = []
      this.rules = {}
      this.flight = {}
      this.delResIdList = []
      this.baseList = {}
      this.resFieldList = []
    },
    setFormId: function (index) {
      return 'resDetailForm' + index
    },
    isDisabled: function (index) {
      return this.form.resLists[index]['id'] !== null && this.form.resLists[index]['id'] !== undefined && this.form.resLists[index]['id'] !== ''
    },
    handleSubmit: function () {
      let newlist = Util.deepCopy(this.form.resLists)
      let oldlist = Util.deepCopy(this.form.oldResLists)
      // 1. 有修改才提交
      if (JSON.stringify(newlist) !== JSON.stringify(oldlist)) {
        let param = { newValue: newlist, oldValue: oldlist }
        // 2. 表单提交验证
        console.log('2. 表单提交验证')
        if (this.validForm()) {
          console.log('3. 表单提交验证')
          if (this.curTabName === 'counterConfForm') {
            console.log('--值机柜台时间校验ckinCounterVerify--')
            // 3.值机柜台资源分配时间检验
            let result = this.ckinCounterVerify()
            console.log(result)
            if (result === -1) {
              return false
            } else if (result.length !== 0) {
              // 提示值机柜台资源时分配错误 1107
            } else {
              this.to(param).then((res) => {
                this.loading = false
                this.$notify(Util.notifyBody(res.ok, res.msg))
              })
              return true
            }
          } else {
            this.to(param).then((res) => {
              this.loading = false
              this.$notify(Util.notifyBody(res.ok, res.msg))
            })
            return true
          }
        }
      }
    },
    validForm () {
      // 表单提交验证
      let validFlag = true
      for (var i = 0; i < this.form.resLists.length; i++) {
        let formid = 'resDetailForm' + i
        this.$refs[formid][0].validate((valid) => {
          validFlag = valid
          if (!valid) {
            return false
          }
        })
        if (!validFlag) {
          return false
        }
      }
      return true
    },
    initField () {
      let field = {}
      for (let i = 0; i < this.resFields.length; i++) {
        for (let j = 0; j < this.resFields[i].length; j++) {
          if (this.resFields[i][j]['isLink']) { // 从关联的资源表中取信息
            let res = {}
            res[this.resFields[i][j].name] = this.resFields[i][j].value
            field['res'] = Util.deepCopy(res)
          } else {
            field[this.resFields[i][j].name] = this.resFields[i][j].value
          }
        }
      }
      this.form.res = field
    },
    initRules: function () {
      let result = {}
      for (let i = 0; i < this.resFields.length; i++) {
        for (let j = 0; j < this.resFields[i].length; j++) {
          let t = this.resFields[i][j].name
          result[t] = this.resFields[i][j].rules
        }
      }
      this.rules = result
    },
    init: function () {
      let field = {}
      let result = {}
      for (let i = 0; i < this.resFields.length; i++) {
        for (let j = 0; j < this.resFields[i].length; j++) {
          // 1. 初始化field
          if (this.resFields[i][j]['isLink']) { // 从关联的资源表中取信息
            let res = {}
            res[this.resFields[i][j].name] = this.resFields[i][j].value
            field['res'] = Util.deepCopy(res)
          } else {
            field[this.resFields[i][j].name] = this.resFields[i][j].value
          }
          // 2. 初始话校验规则
          let t = this.resFields[i][j].name
          result[t] = this.resFields[i][j].rules
        }
      }
      this.form.res = field
      this.rules = result
    },
    show: function (tabKey, flightObj, fields) {
      this.loading = false
      this.curTabName = tabKey
      if (!this.editAble) {
        this.flight = Util.deepCopy(flightObj)
        this.resFields = Util.deepCopy(this[fields])
      }
      // this.resFields = fields
      this.init()
      this.bindData()
    },
    addResInfo: function (index) {
      let obj = Util.deepCopy(this.form.res)
      let field = Util.deepCopy(this.resFields)
      if (this.curTabName === 'standConfForm') {
        // 机位分配，机位使用列表排序处理
        this.form.resLists.unshift(obj)
        this.resFieldList.unshift(field)
      } else {
        this.form.resLists.push(obj)
        this.resFieldList.push(field)
      }
    },
    delResInfo: function (index) {
      let curObj = this.form.resLists[index]
      if (curObj.id !== null && curObj.id !== '') {
        this.delResIdList.push(curObj.id)
      }
      let para = Util.deepCopy(curObj)
      this.$confirm('确认删除吗？', '提示', {}).then(() => {
        if (curObj.id !== null && curObj.id !== '') {
          this.loading = true
          this.delOpt(para).then((res) => {
            this.loading = false
            if (res.ok) {
              this.delInfo(index)
            }
            this.$notify(Util.notifyBody(res.ok, res.msg))
          })
        } else {
          this.delInfo(index)
          this.$notify(Util.notifyBody(true, '删除成功'))
        }
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消提交'
        })
      })
    },
    delInfo: function (index) {
      // 删除资源列表信息、清除校验规则
      this.form.resLists.splice(index, 1)
      this.resFieldList.splice(index, 1)
    },
    openOrCloseRes: function (index, isOpen) {
      this.setActTime(index, isOpen)
      let para = {allocationinfo: this.form.resLists[index]}
      let msg = '确认' + (isOpen ? '开启' : '关闭') + '吗？'
      this.$confirm(msg, '提示', {}).then(() => {
        this.loading = true
        this.openOpt(para).then((res) => {
          this.loading = false
          this.$notify(Util.notifyBody(res.ok, res.msg))
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消提交'
        })
      })
    },
    closeResInfo: function (index) {

    },
    setActTime: function (index, isOpen) {
      // 设置资源开启关闭时间
      var moment = require('moment')
      let now = moment(new Date()).format('YYYY-MM-DD HH:mm:ss')
      switch (this.curTabName) {
        case 'standConfForm': {
          if (isOpen) {
            this.form.resLists[index]['actOccupiedBegin'] = now
          } else {
            this.form.resLists[index]['actOccupiedEnd'] = now
          }
          break
        }
        default: {
          if (isOpen) {
            this.form.resLists[index]['actualOpen'] = now
          } else {
            this.form.resLists[index]['actualClose'] = now
          }
          break
        }
      }
    },
    setLinkVal: function (cmd, val, index, rowIndex, cIndex) {
      // 设置下拉选择控件联动操作
      /* eslint-disable */
      eval('this.' + cmd).call(this, index, val, rowIndex, cIndex)
      /* eslint-disable */
    },
    setCkCounterInfo: function (index, val, rowIndex, cIndex) {
      // 值机柜台设置、联动设置值机柜台id、值机岛
      let list = this.resFields[rowIndex][cIndex].choose.filter(item => {
        return item.counterCode === val
      })
      this.form.resLists[index].checkinCounterId = list[0].counterId
      this.form.resLists[index].checkinGroup = list[0].checkinGroup
    },
    setGateInfo: function (index, val, rowIndex, cIndex) {
      let list = this.resFields[rowIndex][cIndex].choose.filter(item => {
        return item.gateCode === val
      })
      if (list.length > 0) {
        this.form.resLists[index].gateId = list[0].id
      }
    },
    setCarlInfo: function (index, val, rowIndex, cIndex) {
      let list = this.resFields[rowIndex][cIndex].choose.filter(item => {
        return item.carouselCode === val
      })
      if (list.length > 0) {
        this.form.resLists[index].carouselId = list[0].id
      }
    },
    setStandInfo: function (index, val, rowIndex, cIndex) {
      let list = this.resFields[rowIndex][cIndex].choose.filter(item => {
        return item.standCode === val
      })
      if (list.length > 0) {
        this.form.resLists[index].standId = list[0].id
      }
    },
    setLinkLs: function (index, val, rowIndex, cIndex) {
      // 设置联动
      let linklsName = this.resFields[rowIndex][cIndex].linklsName
      let linkCIndex = this.resFields[rowIndex][cIndex].linkCIndex
      let linkRIndex = this.resFields[rowIndex][cIndex].linkRIndex
      let linkField = this.resFields[rowIndex][cIndex].linkField
      let list = this.baseList[linklsName].filter(item => {
        return item[linkField] === val
      })
      // this.resFields[rowIndex][linkCIndex].choose = list
      this.resFieldList[index][linkRIndex][linkCIndex].choose = Util.deepCopy(list)
    },
    setCkcounterLinkLs: function (index, val, rowIndex, cIndex) {
      let linkCIndex = this.resFields[rowIndex][cIndex].linkCIndex
      let linkRIndex = this.resFields[rowIndex][cIndex].linkRIndex
      let list = this.baseList.counterList.filter(item => {
        return item.checkinGroup === val && item.terminal === this.form.resLists[index].terminal
      })
      this.resFieldList[index][linkRIndex][linkCIndex].choose = Util.deepCopy(list)
    },
    ckinCounterVerify () {
      // 值机柜台资源配置时间检验
      let verifyList = []
      let errorArray = []
      console.log(this.form.resLists)
      for (var i = 0; i < this.form.resLists.length; i++) {
        let t = {}
        t.counterId = this.form.resLists[i].checkinCounterId
        t.scheduleOpen = this.form.resLists[i].scheduleOpen
        t.scheduleClose = this.form.resLists[i].scheduleClose
        verifyList.push(t)
      }
      let para = {verifyList: verifyList}
      API.checkincounterverify().go(para).then((data) => {
        if (data.ok) {
          let resultList = data.attr.data.list
          for (var i = 0; i < resultList.length; i++) {
            if (resultList[i].status === -1) {// 错误提示方式待定 1107
              errorArray.push(resultList[i].counterId)
            }
          }
          console.log('--时间校验--')
          console.log(errorArray)
          return errorArray
        } else {
          this.$notify(Util.notifyBody(false, data.msg))
          return -1
        }
      })
    }
  },
  mounted () {
    // this.bindData()
  }
}
</script>

<style lang="scss">
.resDetailClass {
  .el-form-item {
    margin-bottom: 2px!important;
  }
  .content {
    margin-top: 18px!important;
  }
  .hrClass {
    padding: 0px;
    margin-top: 15px!important;
    margin-bottom: 15px!important;
    color: #987cb9;
    size: 1;
  }
  .buttonCls {
    display: block;
    float: right;
    padding-top: 10px;
  }
  .selectA {
    width: 100%;
  }
}

.confirmClass [name=flightNo],.confirmClass [name=carrier] {
  background: #FFA500!important;
  color: black!important;
}
</style>
