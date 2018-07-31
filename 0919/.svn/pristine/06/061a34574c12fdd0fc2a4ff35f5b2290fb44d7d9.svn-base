/*
 * @Author: mikey.zhaopeng
 * @Date: 2017-09-14 11:02:04
 * @Last Modified by: ylj
 * @Last Modified time: 2017-11-24 10:43:08
 */
<template>
<div class="resClass">
  <div :class="widthCls">
  <el-dialog :title="title" v-model="visible" :close-on-click-modal="false" @close="handleClose" :modal="false" @open="initBaseData">
    <el-form label-position="right" label-width="70px" :model="form.segment" ref="resConfig">
      <div class="signClass">
      <el-row :gutter="2">
        <el-col :span="6">
          <el-form-item label="执行日期">
            <!--<date-time v-model="form.segment.opDate" :visitTime="false" style="width: 30%;" datePlaceholder="执行日期" formatter="yyyy-MM-dd" dateStyle="width:100%" :allDisable="true"></date-time>-->
            <el-date-picker v-model="form.segment.opDate" placeholder="执行日期" style="width: 100%;" format="yyyy-MM-dd" :disabled="editAble"></el-date-picker>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="公 司">
            <el-input name="carrier" placeholder="公司" :value="form.segment.carrier" style="width: 100%;" :disabled="editAble"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="航班号">
            <el-input v-model="form.segment.flightNo" name="flightNo" style="width:100%;" placeholder="航班号" :disabled="editAble"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="区 域">
            <el-input name="flightNature" placeholder="区域" :value="$cache.findByName('flightnatures', 'flightNatureCode', form.segment.flightNature, 'description')" style="width: 100%;" :disabled="editAble"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="2">
        <el-col :span="6">
          <el-form-item label="机 号">
            <el-input name="registration" placeholder="机号" :value="form.segment.registration" style="width: 100%;" :disabled="editAble"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="机 型">
            <el-input name="aircraftType" placeholder="机型" :value="form.segment.aircraftType" style="width: 100%;" :disabled="editAble"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="航站楼">
            <el-input name="terminal" placeholder="航站楼" :value="form.segment.terminal" style="width: 100%;" :disabled="editAble"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label=" 任 务">
            <el-input name="flightTask" placeholder="任务" :value="$cache.findByName('flighttasks', 'flightTaskCode', form.segment.flightTask, 'abbr2w') + '(' + form.segment.flightTask + ')'" style="width: 100%;" :disabled="editAble"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="2">
        <el-col :span="6">
          <el-form-item label="进 出">
            <el-input name="direction" placeholder="进出" :value="$cache.findByName('flightdirections', 'directionCode', form.segment.direction, 'description')" style="width: 100%;" :disabled="editAble"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="始发站" prop="origin">
            <el-select v-model="form.segment.origin" style="width:100%;" filterable placeholder="始发站" clearable :disabled="true">
              <el-option
                v-for="item in this.$cache.fetch('airports')"
                :key="item.icaocode"
                :label="item.cnabbr2w + '(' + item.icaocode + ')'"
                :value="item.icaocode">
                <span style="float: left">{{ item.cnabbr2w }}</span>
                <span style="float: right">{{ item.icaocode }}</span>
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="目的站" prop="destination">
            <el-select v-model="form.segment.destination" style="width:100%;" filterable placeholder="始发站" clearable  :disabled="true">
              <el-option
                v-for="item in this.$cache.fetch('airports')"
                :key="item.icaocode"
                :label="item.cnabbr2w + '(' + item.icaocode + ')'"
                :value="item.icaocode">
                <span style="float: left">{{ item.cnabbr2w }}</span>
                <span style="float: right">{{ item.icaocode }}</span>
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      </div>
      <hr class="hrClass"/>
      <el-row :gutter="20">
        <el-col :span="24" class="content-wrapper">
          <el-tabs ref="resTab" v-model="activeName" type="border-card" @tab-click="handleClick" style="min-height: 450px">
            <el-tab-pane label="柜台" name="counterConf" v-if="curSelectRow.direction === 'D'">
              <template>
                <res-config-detail ref="counterConfForm" :initData="API.getCkCounterList().go" :to="API.editCkCounterRes().go" :openOpt="API.openOrCloseCkCounterRes().go" :delOpt="API.removeCkCounterRes().go" ></res-config-detail>
              </template>
            </el-tab-pane>
            <el-tab-pane label="登机口" name="gateConf" v-if="curSelectRow.direction === 'D'">
              <template>
                <res-config-detail ref="gateConfForm" :initData="API.getGateList().go" :to="API.editGateRes().go" :openOpt="API.openOrCloseGateRes().go" :delOpt="API.removeGateRes().go"></res-config-detail>
              </template>
            </el-tab-pane>
            <el-tab-pane label="转盘" name="carConf">
              <template>
                <res-config-detail ref="carConfForm" :initData="API.getCarouselList().go" :to="API.editCarouselRes().go" :openOpt="API.openOrCloseCarsRes().go" :delOpt="API.removeCarouselRes().go"></res-config-detail>
              </template>
            </el-tab-pane>
            <el-tab-pane label="机位" name="standConf">
              <template>
                <res-config-detail ref="standConfForm" :initData="API.getStandList().go" :to="API.editStandRes().go" :openOpt="API.openOrCloseStandRes().go" :delOpt="API.removeStandRes().go"></res-config-detail>
              </template>
            </el-tab-pane>
            <el-tab-pane label="值机/登机时间" name="checkinConf" v-if="curSelectRow.direction === 'D'">
              <template>
                <ckeck-and-board-time ref="checkinConfForm"></ckeck-and-board-time>
              </template>
            </el-tab-pane>
          </el-tabs>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click.native="handleClose">取消</el-button>
      <el-button type="primary" @click.native="handleSubmit" :loading="loading">提交</el-button>
    </div>
  </el-dialog>
  </div>
</div>
</template>

<script>
import Util from '../../../common/js/util'
import API from '../../../api'
import Tabs from '../../../components/Tabs.vue'
import resConfigDetail from './resConfigDetail'
import dateTime from '../../../components/DateTime'
import ckeckAndBoardTime from '../dynamicFlightForm/checkAndBoardTime'

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
      widthCls: 'normalClss',
      activeName: 'counterConf',
      editAble: true,
      visible: false,
      loading: false,
      API: API,
      form: {
        segment: {
          id: null,
          flightId: null,
          linkedFlightId: null,
          combineFlightId: null,
          direction: null,
          opDate: '',
          terminal: null,
          generalAgent: null,
          carrier: null,
          airportCode: null,
          flightNo: null,
          registration: null,
          aircraftType: null,
          flightStatus: null,
          flightTask: null
        },
        segment1: []
      },
      curSelectRow: [],
      counterGroupls: [],
      counterList: [],
      standList: [],
      carList: [],
      gateList: []
    }
  },
  components: {
    resConfigDetail: resConfigDetail,
    Tabs: Tabs,
    dateTime: dateTime,
    ckeckAndBoardTime: ckeckAndBoardTime
  },
  methods: {
    setClassName: function () {
      this.widthCls = (document.body.clientWidth < 1920 ? 'specialClss' : 'normalClss')
    },
    initFormData: function () {
      this.setActiveName()
      this.editAble = true
      this.visible = false
      this.loading = false
      this.form = { segment: {}, segment1: [] }
      this.counterList = []
    },
    setActiveName: function () {
      if (this.curSelectRow.direction === 'D') {
        this.activeName = 'counterConf'
      } else {
        this.activeName = 'carConf'
      }
    },
    bindData () {
    },
    show: function (row) {
      if (row !== undefined) {
        this.curSelectRow = Util.deepCopy(row)
        this.form.segment = Object.assign({}, this.form.segment, row)
        this.form.segment1.push(this.form.segment)
      }
      this.visible = true
    },
    handleClose: function () {
      this.initFormData()
      // 还原资源配置信息
      if (this.curSelectRow.direction === 'D') {
        this.$refs['counterConfForm'].initResData()
        this.$refs['gateConfForm'].initResData()
        this.$refs['checkinConfForm'].handleClose()
      }
      this.$refs['carConfForm'].initResData()
      this.$refs['standConfForm'].initResData()
    },
    opdateFmt: function (row, column, cellValue) {
      let date = row[column.property]
      if (date === undefined) {
        return ''
      }
      return Util.formatDate.format(new Date(date), 'yyyy-MM-dd')
    },
    handleSubmit: function () {
      let closeFlag = ''
      this.$confirm('确认提交吗？', '提示', {}).then(() => {
        if (this.curSelectRow.direction === 'D') {
          closeFlag += this.$refs['counterConfForm'].handleSubmit()
          closeFlag += this.$refs['gateConfForm'].handleSubmit()
          closeFlag += this.$refs['checkinConfForm'].handleSubmit()
        }
        closeFlag += this.$refs['carConfForm'].handleSubmit()
        closeFlag += this.$refs['standConfForm'].handleSubmit()
        if (closeFlag.indexOf('false') === -1) {
          this.loading = false
          this.visible = false
          this.callback()
        }
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消提交'
        })
      })
    },
    handleClick: function (tab, event) {
      let terminal = this.form.segment.terminal
      let ckinGroup = this.counterGroupls.filter(item => {
        return item.terminal === terminal
      })
      let formid = this.activeName + 'Form'
      let baseList = { counterGroupls: this.counterGroupls, ckinGroup: ckinGroup, counterList: this.counterList, gateList: this.gateList, carList: this.carList, standList: this.standList, terminalList: this.$cache.fetch('terminals'), ckcouOpmodeLs: this.$cache.fetch('ckcounteropmodes'), ckCounterSerTypes: this.$cache.fetch('ckcounterservicetypes') }
      this.$refs[formid].baseList = Util.deepCopy(baseList)
      this.$refs[formid].flight = Util.deepCopy(this.curSelectRow)
      if (this.activeName === 'checkinConf') {
        this.$refs[formid].show(this.curSelectRow)
      } else {
        this.$refs[formid].show(formid, this.curSelectRow, this.activeName)
      }
    },
    initBaseData: function () {
      // 调整弹框宽度
      this.setClassName()
      this.setActiveName()
      this.getBaseList()
      setTimeout(() => {
        this.$nextTick(() => {
          this.handleClick()
        })
      }, 90)
    },
    getBaseList: function () {
      let airportCode = this.form.segment.airportCode
      let direction = this.form.segment.direction
      // 值机柜台基础信息过滤
      let counterList = this.$cache.fetch('checkinCounters').filter(item => {
        return item.airportCode === airportCode
      })
      this.counterList = counterList
      // 值机柜台分配模式基础信息过滤
      let para = {airportCode: airportCode, pageSize: 99999}
      API.checkingroups().go(para).then((data) => {
        if (data.ok) {
          this.counterGroupls = data.attr.data
        } else {
          this.counterGroupls = []
        }
      })
      // let counterGroupls = this.$cache.fetch('counterGroups').filter(item => {
      //   return item.airportCode === airportCode
      // })
      // this.counterGroupls = counterGroupls
      // 机位基础信息过滤
      let standList = this.$cache.fetch('stand').filter(item => {
        return item.airportCode === airportCode
      })
      this.standList = standList
      // 转盘基础信息过滤
      let carList = this.$cache.fetch('baggageCarousels').filter(item => {
        return item.airportCode === airportCode && item.carouselType === direction
      })
      this.carList = carList
      // 登机口基础信息过滤
      let gateList = this.$cache.fetch('gate').filter(item => {
        return item.airportCode === airportCode && item.direction === direction
      })
      this.gateList = gateList
    }
  },
  mounted () {
    // 加载时，运行第一个资源分配TAB
    // 自适应浏览器窗口大小
    window.addEventListener('resize', this.setClassName)
  }
}
</script>

<style lang="scss">
.resClass {
  .el-dialog__body {
    padding: 8px 20px!important;
  }
  .el-form-item {
    margin-bottom: 8px!important;
  }
  .el-tabs__content {
    padding-top: 5px!important;
  }
  .hrClass {
    padding: 0px!important;
    margin-top: 10px!important;
    color: #987cb9;
    size: 1;
  }

  input {
    color: black!important;
  }

  .normalClss {
    .el-dialog--small {
      width: 50%!important;
      top: 15%!important;
    }
  }

  .specialClss {
    .el-dialog--small {
      width: 73%!important;
      top: 10%!important;
    }
  }

  .signClass [name=carrier] {
    background: #FFA500!important;
    color: black!important;
  }

  .signClass [name=flightNo] {
    background: #FFA500!important;
    color: black!important;
  }

  .signClass [name=direction] {
    background: #FFA500!important;
    color: black!important;
  }
}
</style>
