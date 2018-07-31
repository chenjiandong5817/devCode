<template>
<div class="stopOverClass">
  <el-dialog v-if="isDiglogType === true" :title="title" v-model="visible" :close-on-click-modal="false" @close="clearData" :modal-append-to-body="false" size="large">
    <el-form :model="stopOver" ref="addStopOverForm" >
      <el-row :gutter="5">
        <el-col :span="8">
          <el-form-item label="目的站" prop="destination" label-position="top" :rules="{required: true, message: '目的站不能为空', trigger: 'blur'}">
            <el-select v-model="stopOver.destination" filterable placeholder="目的站" style="100%">
              <el-option
                v-for="item in aiisAirportList"
                :key="item.icaocode"
                :label="item.cnabbr2w + '(' + item.icaocode + '/' + item.iatacode + ')'"
                :value="item.icaocode">
                <span style="float: left">{{ item.cnabbr2w }}</span>
                <span style="float: right">{{ item.icaocode + '/' + item.iatacode }}</span>
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="5">
        <el-col :span="8">
          <el-form-item label="计划到达" prop="scheduleArrTime">
            <br/>
            <div style="dispaly:inline">
            <el-date-picker
              v-model="scheduleArrDate"
              type="date"
              placeholder="选择日期"
              format="yyyy-MM-dd"
              style="width:65%;"
              :picker-options="pickerOptions">
            </el-date-picker>
            <el-input v-model="scheduleArrTime" placeholder="时间" style="width:30%;"></el-input>
            </div>
          </el-form-item>
        </el-col>

        <el-col :span="8">
          <el-form-item label="预计到达" prop="estimateArrTime">
            <br/>
            <div style="dispaly:inline">
            <el-date-picker
              v-model="estimateArrDate"
              type="date"
              placeholder="选择日期"
              format="yyyy-MM-dd"
              style="width:65%;"
              :picker-options="pickerOptions">
            </el-date-picker>
            <el-input v-model="estimateArrTime" placeholder="时间" style="width:30%;"></el-input>
            </div>
          </el-form-item>
        </el-col>

        <el-col :span="8">
          <el-form-item label="实际到达" prop="actualArrTime">
            <br/>
            <div style="dispaly:inline">
            <el-date-picker
              v-model="actualArrDate"
              type="date"
              placeholder="选择日期"
              format="yyyy-MM-dd"
              style="width:65%;"
              :picker-options="pickerOptions">
            </el-date-picker>
            <el-input v-model="actualArrTime" placeholder="时间" style="width:30%;"></el-input>
            </div>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="5">
        <el-col :span="8">
          <el-form-item label="计划起飞" prop="scheduleDepTime">
            <br/>
            <div style="dispaly:inline">
            <el-date-picker
              v-model="scheduleDepDate"
              type="date"
              placeholder="选择日期"
              format="yyyy-MM-dd"
              style="width:65%;"
              :picker-options="pickerOptions">
            </el-date-picker>
            <el-input v-model="scheduleDepTime" placeholder="时间" style="width:30%;"></el-input>
            </div>
          </el-form-item>
        </el-col>

        <el-col :span="8">
          <el-form-item label="预计起飞" prop="estimateDepTime">
            <br/>
            <div style="dispaly:inline">
            <el-date-picker
              v-model="estimateDepDate"
              type="date"
              placeholder="选择日期"
              format="yyyy-MM-dd"
              style="width:65%;"
              :picker-options="pickerOptions">
            </el-date-picker>
            <el-input v-model="estimateDepTime" placeholder="时间" style="width:30%;"></el-input>
            </div>
          </el-form-item>
        </el-col>

        <el-col :span="8">
          <el-form-item label="实际起飞" prop="actualDepTime">
            <br/>
            <div style="dispaly:inline">
            <el-date-picker
              v-model="actualDepDate"
              type="date"
              placeholder="选择日期"
              format="yyyy-MM-dd"
              style="width:65%;"
              :picker-options="pickerOptions">
            </el-date-picker>
            <el-input v-model="actualDepTime" placeholder="时间" style="width:30%;"></el-input>
            </div>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click.native="clearData">取消</el-button>
      <el-button @click.native="resetForm">重置</el-button>
      <el-button type="primary" @click.native="handleSubmit" :loading="loading">提交</el-button>
    </div>
  </el-dialog>
  <el-form :model="stopOver" ref="stopOverForm" v-else-if="isDiglogType === false">
    <el-row :gutter="10" style="margin-top: 0px;margin-bottom: 2px;">
      <el-col :span="6">
        <el-form-item label="备降站">
          <el-select v-model="stopOver.destination" style="width:100%;" filterable placeholder="备降站" :disabled="destinationDisable">
            <el-option
              v-for="item in aiisAirportList"
              :key="item.icaocode"
              :label="item.cnabbr2w + '(' + item.icaocode + '/' + item.iatacode + ')'"
              :value="item.icaocode">
              <span style="float: left">{{ item.cnabbr2w }}</span>
              <span style="float: right">{{ item.icaocode + '/' + item.iatacode }}</span>
            </el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="6" >
        <el-form-item label="备降计划到达">
          <br/>
          <div style="dispaly:inline">
            <el-date-picker v-model="scheduleArrDate" type="date" placeholder="计划日期" style="width:67%;" format="yyyy-MM-dd" :picker-options="pickerOptions"></el-date-picker>
            <el-input v-model="scheduleArrTime" placeholder="时间" style="width:30%;"></el-input>
          </div>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="备降预计到达">
          <br/>
          <div style="dispaly:inline">
            <el-date-picker v-model="estimateArrDate" type="date" placeholder="预计时间" style="width:67%;" format="yyyy-MM-dd" :picker-options="pickerOptions"></el-date-picker>
            <el-input v-model="estimateArrTime" placeholder="时间" style="width:30%;"></el-input>
          </div>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="备降实际到达">
          <br/>
          <div style="dispaly:inline">
            <el-date-picker v-model="actualArrDate" type="date" placeholder="实际时间" style="width:67%;" format="yyyy-MM-dd" :picker-options="pickerOptions"></el-date-picker>
            <el-input v-model="actualArrTime" placeholder="时间" style="width:30%;"></el-input>
          </div>
        </el-form-item>
      </el-col>
    </el-row>

    <el-row :gutter="10" style="margin-top: 0px;margin-bottom: 2px;">
      <el-col :span="6" :offset="6">
        <el-form-item label="备降计划起飞">
          <br/>
          <div style="dispaly:inline">
            <el-date-picker v-model="scheduleDepDate" type="date" placeholder="计划日期" style="width:67%;" format="yyyy-MM-dd" :picker-options="pickerOptions"></el-date-picker>
            <el-input v-model="scheduleDepTime" placeholder="时间" style="width:30%;"></el-input>
          </div>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="备降预计起飞">
          <br/>
          <div style="dispaly:inline">
            <el-date-picker v-model="estimateDepDate" type="date" placeholder="预计日期" style="width:67%;" format="yyyy-MM-dd" :picker-options="pickerOptions"></el-date-picker>
            <el-input v-model="estimateDepTime" placeholder="时间" style="width:30%;"></el-input>
          </div>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item label="备降实际起飞">
          <br/>
          <div style="dispaly:inline">
            <el-date-picker v-model="actualDepDate" type="date" placeholder="实际日期" style="width:67%;" format="yyyy-MM-dd" :picker-options="pickerOptions"></el-date-picker>
            <el-input v-model="actualDepTime" placeholder="时间" style="width:30%;"></el-input>
          </div>
        </el-form-item>
      </el-col>
    </el-row>
  </el-form>
</div>
</template>

<script>
import Util from '../../../common/js/util'
import API from '../../../api'
import AddOrUpdate from '../dynamicFlightForm/AddOrUpdate.vue'
export default {
  props: {
    title: {
      type: String,
      default: '新增经停'
    },
    to: {
      type: Function,
      default: function () {}
    },
    callback: {
      type: Function,
      default: function () {}
    },
    isDiglogType: {
      type: Boolean,
      default: true
    }
  },
  data () {
    return {
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
      visible: false,
      loading: false,
      arrDate: new Date(),
      stopOver: {id: null, flightId: null, flightStatus: '', irregularStatus: '', boardingStand: '', stand: '', gate: '', index: 0, flightNature: '', destination: '', segmentNature: '', scheduleDepartTime: '', estimateDepartTime: '', actualDepartTime: '', scheduleArriveTime: '', estimateArriveTime: '', actualArriveTime: ''},
      oldStopOver: {},
      aiisAirportList: this.$cache.fetch('airports'),
      API: API,
      index: 0,
      scheduleArriveTime: null,
      scheduleArrDate: null,
      scheduleArrTime: null,
      scheduleDepartTime: null,
      scheduleDepDate: null,
      scheduleDepTime: null,
      estimateDepartTime: null,
      estimateDepDate: null,
      estimateDepTime: null,
      actualDepartTime: null,
      actualDepDate: null,
      actualDepTime: null,
      estimateArriveTime: null,
      estimateArrDate: null,
      estimateArrTime: null,
      actualArriveTime: null,
      actualArrDate: null,
      actualArrTime: null,
      destinationDisable: true
    }
  },
  components: {
    AddOrUpdate: AddOrUpdate
  },
  methods: {
    bindData () {
    },
    show: function (index, flightId, row) {
      // this.bindData()
      this.visible = true
      if (row !== undefined) {
        this.index = row.index
        this.stopOver = Util.deepCopy(row)
        this.oldStopOver = Util.deepCopy(row)

        // 计划到达时间
        this.scheduleArriveTime = row.scheduleArriveTime
        this.scheduleArrDate = (this.isNotNull(row.scheduleArriveTime) ? row.scheduleArriveTime.substring(0, 10) : null)
        this.scheduleArrTime = (this.isNotNull(row.scheduleArriveTime) ? row.scheduleArriveTime.substring(11, 16).replace(':', '') : null)
        // 预计到达时间
        this.estimateArriveTime = row.estimateArriveTime
        this.estimateArrDate = (this.isNotNull(row.estimateArriveTime) ? row.estimateArriveTime.substring(0, 10) : null)
        this.estimateArrTime = (this.isNotNull(row.estimateArriveTime) ? row.estimateArriveTime.substring(11, 16).replace(':', '') : null)
        // 实际到达时间
        this.actualArriveTime = row.actualArriveTime
        this.actualArrDate = (this.isNotNull(row.actualArriveTime) ? row.actualArriveTime.substring(0, 10) : null)
        this.actualArrTime = (this.isNotNull(row.actualArriveTime) ? row.actualArriveTime.substring(11, 16).replace(':', '') : null)
        // 计划起飞时间
        this.scheduleDepartTime = row.scheduleDepartTime
        this.scheduleDepDate = (this.isNotNull(row.scheduleDepartTime) ? row.scheduleDepartTime.substring(0, 10) : null)
        this.scheduleDepTime = (this.isNotNull(row.scheduleDepartTime) ? row.scheduleDepartTime.substring(11, 16).replace(':', '') : null)
        // 预计起飞时间
        this.estimateDepartTime = row.estimateDepartTime
        this.estimateDepDate = (this.isNotNull(row.estimateDepartTime) ? row.estimateDepartTime.substring(0, 10) : null)
        this.estimateDepTime = (this.isNotNull(row.estimateDepartTime) ? row.estimateDepartTime.substring(11, 16).replace(':', '') : null)
        // 实际起飞时间
        this.actualDepartTime = row.actualDepartTime
        this.actualDepDate = (this.isNotNull(row.actualDepartTime) ? row.actualDepartTime.substring(0, 10) : null)
        this.actualDepTime = (this.isNotNull(row.actualDepartTime) ? row.actualDepartTime.substring(11, 16).replace(':', '') : null)
      } else {
        this.index = index
        // 设置默认值，日期默认为当前日期
        this.initData()
        this.setDefaultDate()
        this.stopOver.index = index
        this.stopOver.flightId = flightId
      }
    },
    setDefaultDate: function () {
      this.scheduleArriveTime = Util.formatDate.format(new Date(), 'yyyy-MM-dd')
      this.scheduleArrDate = Util.formatDate.format(new Date(), 'yyyy-MM-dd')
      this.scheduleDepartTime = Util.formatDate.format(new Date(), 'yyyy-MM-dd')
      this.scheduleDepDate = Util.formatDate.format(new Date(), 'yyyy-MM-dd')
    },
    handleClose: function () {
      this.visible = true
    },
    handleSubmit: function () {
      this.$refs['addStopOverForm'].validate((valid) => {
        if (valid) {
          this.setStopOver()
          let para = Util.deepCopy(this.stopOver)
          this.to(para)
          this.visible = false
        } else {
          return false
        }
      })
    },
    setStopOver: function () {
      this.stopOver.index = this.index

      this.stopOver.scheduleArriveTime = this.toDateTime(this.scheduleArrDate, this.scheduleArrTime)
      this.stopOver.scheduleDepartTime = this.toDateTime(this.scheduleDepDate, this.scheduleDepTime)

      this.stopOver.estimateDepartTime = this.toDateTime(this.estimateDepDate, this.estimateDepTime)
      this.stopOver.actualDepartTime = this.toDateTime(this.actualDepDate, this.actualDepTime)
      this.stopOver.estimateArriveTime = this.toDateTime(this.estimateArrDate, this.estimateArrTime)
      this.stopOver.actualArriveTime = this.toDateTime(this.actualArrDate, this.actualArrTime)
    },
    toDateTime: function (date, time) {
      return this.isNotNull(date) ? ((typeof date === 'object' ? Util.formatDate.format(date, 'yyyy-MM-dd') : date) + (this.isNotNull(time) ? (' ' + time.substring(0, 2) + ':' + time.substring(2, 4) + ':00') : ' 00:00:00')) : null
    },
    initData: function () {
      this.oldStopOver = {}
      this.stopOver = {id: null, flightId: null, flightStatus: '', irregularStatus: '', boardingStand: '', stand: '', gate: '', index: 0, destination: '', scheduleDepartTime: '', estimateDepartTime: '', actualDepartTime: '', scheduleArriveTime: '', estimateArriveTime: '', actualArriveTime: ''}
      this.scheduleArriveTime = null
      this.scheduleArrDate = null
      this.scheduleArrTime = null
      this.scheduleDepartTime = null
      this.scheduleDepDate = null
      this.scheduleDepTime = null
      // 预计到达时间
      this.estimateArriveTime = null
      this.estimateArrDate = null
      this.estimateArrTime = null
      // 实际到达时间
      this.actualArriveTime = null
      this.actualArrDate = null
      this.actualArrTime = null
      // 预计起飞时间
      this.estimateDepartTime = null
      this.estimateDepDate = null
      this.estimateDepTime = null
      // 实际起飞时间
      this.actualDepartTime = null
      this.actualDepDate = null
      this.actualDepTime = null
    },
    isNotNull: function (data) {
      return data !== undefined && data !== null && data !== ''
    },
    clearData: function () {
      this.index = 0
      this.$refs['addStopOverForm'].resetFields()
      this.initData()
      this.visible = false
    },
    resetForm: function () {
      this.stopOver = Util.deepCopy(this.oldStopOver)
    }
  },
  mounted () {

  }
}
</script>
<style lang="scss">
.stopOverClass {
  .el-form-item {
    margin-bottom: 10px!important;
  }
  .el-dialog--large {
    width: 75%!important;
  }
  .el-dialog__body {
    padding: 2px 20px!important;
  }
}
</style>
