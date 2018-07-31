/*
 * @Author: ylj
 * @Date: 2017-11-19 23:01:07
 * @Last Modified by: ylj
 * @Last Modified time: 2017-11-24 14:41:08
 */
<template>
<div class="flightLoadFormcls">
  <div :class="addOrUpClss" height="100%">
  <!--<el-dialog :title="title" v-model="visible" :close-on-click-modal="false" @close="handleClose" :modal="false" size="large" @open="setClassName">-->
    <el-form :model="form.flightLoad" :rules="rules" ref="flightLoadForm" >
      <el-row :gutter="10">
        <el-col :span="6">
          <el-form-item label="运营机场" prop="airportCode">
            <el-select v-model="form.flight.airportCode" placeholder="运营机场" :filterable="true" style="width:100%: null" clearable :disabled="true">
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
          <el-form-item label="执行日期" prop="opDate">
            <date-time v-model="form.flight.opDate" :visitTime="false" style="width: 100%: null" datePlaceholder="执行日期" formatter="yyyy-MM-dd" dateStyle="width:100%" :allDisable="true"></date-time>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="公司" prop="carrier">
            <el-select v-model="form.flight.carrier" name="carrier" placeholder="公司" style="width:100%: null" filterable clearable :disabled="true">
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
          <el-form-item label="任务" prop="flightTask">
            <el-select v-model="form.flight.flightTask" placeholder="任务" filterable style="width:100%: null"  filterable clearable :disabled="true">
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
          <el-form-item label="航班号" prop="flightNo" >
            <el-input v-model="form.flight.flightNo" name="flightNo" style="width:100%;" placeholder="航班号" :disabled="true"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="进出" prop="direction" >
            <el-select v-model="form.flight.direction" name="direction" placeholder="进出" style="width:100%" clearable  filterable :disabled="true">
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
          <el-form-item label="机号" prop="registration" >
            <el-select v-model="form.flight.registration" filterable placeholder="机号" style="width:100%" clearable :disabled="true">
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
          <el-form-item label="机型" prop="aircraftType" >
            <el-select v-model="form.flight.aircraftType" placeholder="机型" style="width:100%" filterable clearable :disabled="true">
              <el-option
                v-for="item in baseList.aircraftTypes"
                :key="item.sysCode"
                :label="item.sysCode"
                :value="item.sysCode">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="10">
        <el-col :span="6">
          <el-form-item label="始发站" prop="origin" >
            <el-select v-model="form.flight.origin" style="width:100%" filterable placeholder="始发站" clearable :disabled="true">
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
          <el-form-item label="目的站" prop="destination" >
            <el-select v-model="form.flight.destination" filterable placeholder="目的站" clearable style="width:100%" :disabled="true">
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
          <el-form-item label="区 域" prop="flightNature" >
            <el-select v-model="form.flight.flightNature" placeholder="区域" style="width:100%" filterable clearable :disabled="true">
              <el-option
                v-for="item in $cache.fetch('flightnatures')"
                :key="item.flightNatureCode"
                :label="item.description"
                :value="item.flightNatureCode">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="状态" prop="flightStatus">
            <el-select v-model="form.flight.flightStatus" placeholder="状态" style="width:100%" filterable clearable  :disabled="true">
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
      <hr/>

      <el-row :gutter="10">
        <el-col :span="6">
          <el-form-item label="">
            <br/>
            <el-tag class="tagInfoClss" style="float: right;">旅客数</el-tag>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="成人数" prop="adult">
            <el-input type="number" value="number" v-model.number="form.flightLoad.adult" auto-complete="off" min="0"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="儿童数" prop="child">
            <el-input type="number" value="number" v-model.number="form.flightLoad.child" auto-complete="off" min="0"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="婴儿数" prop="infant">
            <el-input type="number" value="number" v-model.number="form.flightLoad.infant" auto-complete="off" min="0"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="10">
        <el-col :span="6">
          <el-form-item label="">
            <br/>
            <el-tag class="tagInfoClss" style="float: right;">过站旅客数</el-tag>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="过站成人数" prop="transitAdult">
            <el-input type="number" value="number" v-model.number="form.flightLoad.transitAdult" auto-complete="off" min="0"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="过站儿童数" prop="transitChild">
            <el-input type="number" value="number" v-model.number="form.flightLoad.transitChild" auto-complete="off" min="0"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="过站婴儿数" prop="transitInfant">
            <el-input type="number" value="number" v-model.number="form.flightLoad.transitInfant" auto-complete="off" min="0"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="10">
        <el-col :span="6">
          <el-form-item label="">
            <br/>
            <el-tag class="tagInfoClss" style="float: right;">舱室旅客数</el-tag>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="头等舱人数" prop="firstClass">
            <el-input type="number" value="number" v-model.number="form.flightLoad.firstClass" auto-complete="off" min="0"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="商务舱人数" prop="businessClass">
            <el-input type="number" value="number" v-model.number="form.flightLoad.businessClass" auto-complete="off" min="0"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="经济舱人数" prop="economyClass">
            <el-input type="number" value="number" v-model.number="form.flightLoad.economyClass" auto-complete="off" min="0"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <hr/>
      <el-form-item>
        <el-tag class="tagInfoClss">货邮信息</el-tag>
      </el-form-item>

      <el-row :gutter="10">
        <el-col :span="6">
          <el-form-item label="行李重量" prop="baggage">
            <el-input type="number" value="number" v-model.number="form.flightLoad.baggage" auto-complete="off" min="0"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="行李件数" prop="baggageCount">
            <el-input type="number" value="number" v-model.number="form.flightLoad.baggageCount" auto-complete="off" min="0"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="邮件重量" prop="post">
            <el-input type="number" value="number" v-model.number="form.flightLoad.post" auto-complete="off" min="0"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="货物重量" prop="cargo">
            <el-input type="number" value="number" v-model.number="form.flightLoad.cargo" auto-complete="off" min="0"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="10">
        <el-col :span="6">
          <el-form-item label="过站行李重量" prop="transitBaggage">
            <el-input type="number" value="number" v-model.number="form.flightLoad.transitBaggage" auto-complete="off" min="0"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="过站行李件数" prop="transitBaggageCount">
            <el-input type="number" value="number" v-model.number="form.flightLoad.transitBaggageCount" auto-complete="off" min="0"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="过站邮件重量" prop="transitPost">
            <el-input type="number" value="number" v-model.number="form.flightLoad.transitPost" auto-complete="off" min="0"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="过站货物重量" prop="transitCargo">
            <el-input type="number" value="number" v-model.number="form.flightLoad.transitCargo" auto-complete="off" min="0"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

    </el-form>
    <div slot="footer" class="dialog-footer footerBtnClss">
      <el-button @click.native="handleClose">取消</el-button>
      <el-button @click.native="resetForm('flightLoadForm')" >重置</el-button>
      <el-button type="primary" @click.native="handAddOrUptSubmit" :loading="loading">提交</el-button>
    </div>
  <!--</el-dialog>-->
  </div>
</div>
</template>

<script>
import Util from '../../../common/js/util'
import Butil from '../../../common/js/base-util'
import API from '../../../api'
import Rules from '../../../common/js/rules'
import dateTime from '../../../components/DateTime'

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
      loading: false,
      API: API,
      form: {
        flight: {
          id: null,
          flightId: null,
          linkedFlightId: null,
          combineFlightId: null,
          airportCode: null,
          direction: null,
          opDate: '',
          terminal: null,
          carrier: null,
          flightNo: null,
          registration: null,
          aircraftType: null,
          flightTask: null,
          origin: null,
          destination: null,
          flightNature: null
        },
        flightLoad: {
	        id: null,
          adult: 0,
          child: 0,
          infant: 0,
          firstClass: 0,
          businessClass: 0,
          economyClass: 0,
          transitAdult: 0,
          transitChild: 0,
          transitInfant: 0,
          baggage: 0,
          baggageCount: 0,
          post: 0,
          cargo: 0,
          transitBaggage: 0,
          transitPost: 0,
          transitCargo: 0,
          transitBaggageCount: 0
        }
      },
      newFlightLoadls: [],
      oldFlightLoadls: [],
      curSelectRow: {},
      subAirportLs: [],
      baseList: {
        airlines: this.$cache.fetch('airlines'),
        flighttasks: this.$cache.fetch('flighttasks'),
        registrations: [],
        aircraftTypes: this.$cache.fetch('aircraftTypes'),
        flightdirections: this.$cache.fetch('flightdirections'),
        terminals: this.$cache.fetch('terminals'),
        airports: this.$cache.fetch('airports'),
        flightStatus: this.$cache.fetch('flightstatus')
      },
      rules: {
        adult: [ { validator: Rules.checkNum, minVal: 0, maxVal: 999, trigger: 'blur,change' },
                 { type: 'integer', message: '请输入整数', trigger: 'blur,change' } ],
        child: [ { validator: Rules.checkNum, minVal: 0, maxVal: 999, trigger: 'blur,change' },
                 { type: 'integer', message: '请输入整数', trigger: 'blur,change' } ],
        infant: [ { validator: Rules.checkNum, minVal: 0, maxVal: 999, trigger: 'blur,change' },
                  { type: 'integer', message: '请输入整数', trigger: 'blur,change' } ],
        firstClass: [ { validator: Rules.checkNum, minVal: 0, maxVal: 999, trigger: 'blur,change' },
                      { type: 'integer', message: '请输入整数', trigger: 'blur,change' } ],
        businessClass: [ { validator: Rules.checkNum, minVal: 0, maxVal: 999, trigger: 'blur,change' },
                         { type: 'integer', message: '请输入整数', trigger: 'blur,change' } ],
        economyClass: [ { validator: Rules.checkNum, minVal: 0, maxVal: 999, trigger: 'blur,change' },
                        { type: 'integer', message: '请输入整数', trigger: 'blur,change' } ],
        transitAdult: [ { validator: Rules.checkNum, minVal: 0, maxVal: 999, trigger: 'blur,change' },
                        { type: 'integer', message: '请输入整数', trigger: 'blur,change' } ],
        transitChild: [ { validator: Rules.checkNum, minVal: 0, maxVal: 999, trigger: 'blur,change' },
                        { type: 'integer', message: '请输入整数', trigger: 'blur,change' } ],
        transitInfant: [ { validator: Rules.checkNum, minVal: 0, maxVal: 999, trigger: 'blur,change' },
                          { type: 'integer', message: '请输入整数', trigger: 'blur,change' } ],
        baggage: [ { validator: Rules.checkNum, minVal: 0, maxVal: 999999.99, trigger: 'blur,change' },
                   { type: 'number', message: '请输入数字', trigger: 'blur,change' } ],
        baggageCount: [ { validator: Rules.checkNum, minVal: 0, maxVal: 99999999, trigger: 'blur,change' },
                        { type: 'integer', message: '请输入数字', trigger: 'blur,change' } ],
        post: [ { validator: Rules.checkNum, minVal: 0, maxVal: 999999.99, trigger: 'blur,change' },
                { type: 'number', message: '请输入数字', trigger: 'blur,change' } ],
        cargo: [ { validator: Rules.checkNum, minVal: 0, maxVal: 999999.99, trigger: 'blur,change' },
                 { type: 'number', message: '请输入数字', trigger: 'blur,change' } ],
        transitBaggage: [ { validator: Rules.checkNum, minVal: 0, maxVal: 999999.99, trigger: 'blur,change' },
                          { type: 'number', message: '请输入数字', trigger: 'blur,change' } ],
        transitPost: [ { validator: Rules.checkNum, minVal: 0, maxVal: 999999.99, trigger: 'blur,change' },
                       { type: 'number', message: '请输入数字', trigger: 'blur,change' } ],
        transitCargo: [ { validator: Rules.checkNum, minVal: 0, maxVal: 999999.99, trigger: 'blur,change' },
                        { type: 'number', message: '请输入数字', trigger: 'blur,change' } ],
        transitBaggageCount: [ { validator: Rules.checkNum, minVal: 0, maxVal: 99999999, trigger: 'blur,change' },
                               { type: 'integer', message: '请输入数字', trigger: 'blur,change' } ]
      }
    }
  },
  components: {
    dateTime: dateTime
  },
  watch: {
    '$store.state.cached' (val, oldVal) {
      // 重新刷新数据
      this.baseList = {
        airlines: this.$cache.fetch('airlines'),
        flighttasks: this.$cache.fetch('flighttasks'),
        registrations: [],
        aircraftTypes: this.$cache.fetch('aircraftTypes'),
        flightdirections: this.$cache.fetch('flightdirections'),
        terminals: this.$cache.fetch('terminals'),
        airports: this.$cache.fetch('airports'),
        flightStatus: this.$cache.fetch('flightstatus')
      }
    }
  },
  methods: {
    setClassName () {
      // 设置dialog宽度、字段突出显示
      this.addOrUpClss = (this.operate !== 'Add' ? 'signClass' : 'normalClass') + (document.body.clientWidth <= 1366 ? ' largeDigCls' : ' smallDigCls')
    },
    bindData () {
      // 获取订阅机场信息
      this.subAirportLs = Butil.setSubAirportls()
    },
    show (operate, row, flight) {
      // this.bindData()
      this.operate = operate
      if (flight !== null && flight !== undefined) {
        this.form.flight = Util.deepCopy(flight)
      }
      if (operate === 'Edit') {
        // 航班货邮编辑
        this.curSelectRow = Util.deepCopy(row)
        delete this.curSelectRow['flightSchedule']
        this.oldFlightLoadls = []
        this.oldFlightLoadls.push(this.curSelectRow)
        this.form.flightLoad = Util.deepCopy(this.curSelectRow)
      } else if (operate === 'DynamicAdd') {
        // 航班动态入口，航班货邮编辑
        this.getFlightLoadls(flight)
      } else {
        // 航班货邮录入
        if (flight !== null && flight !== undefined) {
          this.form.flightLoad.id = flight.id
          this.getFlightLoadls(flight)
        } else {
          this.initData()
        }
      }
      this.loading = false
      this.setClassName()
    },
    getFlightLoadls (flight) {
      this.loading = true
      let para = { id: flight.id }
      API.getFlightLoadls().go(para).then((data) => {
        if (data.ok) {
          if (data.attr.data.list.length > 0) {
            let Obj = data.attr.data.list[0]
            delete Obj['flightSchedule']
            this.curSelectRow = Util.deepCopy(Obj)
            this.form.flightLoad = Util.deepCopy(Obj)
            this.oldFlightLoadls.push(Util.deepCopy(Obj))
          } else {
            this.initData()
          }
        } else {
          this.$notify(Util.notifyBody(false, data.msg))
        }
        this.form.flightLoad.id = flight.id
        this.loading = false
      })
    },
    initData () {
      // 新增时初始化数据
      this.loading = false
      // this.form.flight = {
      //   id: null,
      //   flightId: null,
      //   linkedFlightId: null,
      //   combineFlightId: null,
      //   airportCode: null,
      //   direction: null,
      //   opDate: '',
      //   terminal: null,
      //   carrier: null,
      //   flightNo: null,
      //   registration: null,
      //   aircraftType: null,
      //   flightTask: null,
      //   origin: null,
      //   destination: null,
      //   flightNature: null
      // }
      this.form.flightLoad = {
	      id: null,
        adult: 0,
        child: 0,
        infant: 0,
        firstClass: 0,
        businessClass: 0,
        economyClass: 0,
        transitAdult: 0,
        transitChild: 0,
        transitInfant: 0,
        baggage: 0,
        baggageCount: 0,
        post: 0,
        cargo: 0,
        transitBaggage: 0,
        transitPost: 0,
        transitCargo: 0,
        transitBaggageCount: 0
      }
      this.newFlightLoadls = []
      this.oldFlightLoadls = []
      this.curSelectRow = {}
    },
    handleClose: function () {
      this.$refs['flightLoadForm'].resetFields()
      if (this.operate !== 'flightLoadAdd') {
        this.callback()
      }
    },
    handAddOrUptSubmit: function () {
      this.newFlightLoadls = []
      this.newFlightLoadls.push(this.form.flightLoad)
      var para = { newValue: this.newFlightLoadls, oldValue: this.oldFlightLoadls }
      this.$refs['flightLoadForm'].validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.loading = true
            this.to(para).then((res) => {
              this.loading = false
              this.$notify(Util.notifyBody(res.ok, res.msg))
              // 非航班客货行邮录入时，点击提交关闭弹窗
              if (this.operate !== 'flightLoadAdd') {
                this.callback()
              }
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
      this.form.flightLoad = (this.curSelectRow !== null && this.curSelectRow !== {}) ? Util.deepCopy(this.curSelectRow) : this.form.flightLoad
      this.form.flightLoad.id = this.form.flight.id
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
.flightLoadFormcls {
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
      width: 42%!important;
    }
  }
  .largeDigCls {
    .el-dialog--small {
      width: 62%!important;
    }
  }
  .el-dialog {
    margin-top: -4.5%!important;
  }
  // .signClass [name=carrier],.signClass [name=flightNo],.signClass [name=direction] {
  //   background: #FFA500!important;
  //   color: black!important;
  // }

  .tagInfoClss {
    width: 72px;
    text-align: center;
    // height: 24px!important;
    // line-height: 24px!important;
  }

  .footerBtnClss {
    float: right;
    margin-bottom: 15px;
  }

  input {
    color: black!important;
  }
}
</style>

