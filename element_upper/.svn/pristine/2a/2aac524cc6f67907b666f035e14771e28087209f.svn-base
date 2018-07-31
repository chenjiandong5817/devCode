<template>
<div class="ckAndBoardTimeCls">
    <el-form :model="form.checkIn" ref="checkTimeForm">
      <el-row :gutter="20">
        <el-col :span="24">
          <el-tag type="primary" :hit="true">值机时间</el-tag>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="6">
          <el-form-item label="计划开始时间" prop="scheduleCheckinOpen">
            <date-time v-model="form.checkIn.scheduleCheckinOpen" :visitTime="true" dateStyle="width:68%" datePlaceholder="计划开始日期" timePlaceholder="时间" formatter="yyyy-MM-dd" :allDisable="false"></date-time>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="计划关闭时间" prop="scheduleCheckinClose">
            <date-time v-model="form.checkIn.scheduleCheckinClose" :visitTime="true" dateStyle="width:68%" datePlaceholder="计划关闭日期" timePlaceholder="时间" formatter="yyyy-MM-dd" :allDisable="false"></date-time>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="预计开始时间" prop="estimateCheckinOpen">
            <date-time v-model="form.checkIn.estimateCheckinOpen" :visitTime="true" dateStyle="width:68%" datePlaceholder="预计开始日期" timePlaceholder="时间" formatter="yyyy-MM-dd" :allDisable="false"></date-time>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="预计关闭时间" prop="estimateCheckinClose">
            <date-time v-model="form.checkIn.estimateCheckinClose" :visitTime="true" dateStyle="width:68%" datePlaceholder="预计关闭日期" timePlaceholder="时间" formatter="yyyy-MM-dd" :allDisable="false"></date-time>
          </el-form-item>
        </el-col>
      </el-row>

      <!--<el-row :gutter="20">
        <el-col :span="6">
          <el-form-item label="值机开始时间" prop="actualCheckinOpen">
            <date-time v-model="form.checkIn.actualCheckinOpen" :visitTime="true" dateStyle="width:68%" datePlaceholder="值机开始日期" timePlaceholder="时间" formatter="yyyy-MM-dd" :allDisable="false"></date-time>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="值机关闭时间" prop="actualCheckinClose">
            <date-time v-model="form.checkIn.actualCheckinClose" :visitTime="true" dateStyle="width:68%" datePlaceholder="值机关闭日期" timePlaceholder="时间" formatter="yyyy-MM-dd" :allDisable="false"></date-time>
          </el-form-item>
        </el-col>
      </el-row>-->

    </el-form>

    <el-form :model="form.boardTime" ref="boardTimeForm" style="padding-top: 15px;">
      <el-row :gutter="20">
        <el-col :span="24">
          <el-tag type="primary" :hit="true">登机时间</el-tag>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="6">
          <el-form-item label="计划开始时间" prop="scheduleBoardingOpen">
            <date-time v-model="form.checkIn.scheduleBoardingOpen" :visitTime="true" dateStyle="width:68%" datePlaceholder="计划开始日期" timePlaceholder="时间" formatter="yyyy-MM-dd" :allDisable="false"></date-time>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="计划关闭时间" prop="scheduleBoardingClose">
            <date-time v-model="form.checkIn.scheduleBoardingClose" :visitTime="true" dateStyle="width:68%" datePlaceholder="计划关闭日期" timePlaceholder="时间" formatter="yyyy-MM-dd" :allDisable="false"></date-time>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="预计开始时间" prop="estimateBoardingOpen">
            <date-time v-model="form.checkIn.estimateBoardingOpen" :visitTime="true" dateStyle="width:68%" datePlaceholder="预计开始日期" timePlaceholder="时间" formatter="yyyy-MM-dd" :allDisable="false"></date-time>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="预计关闭时间" prop="estimateBoardingClose">
            <date-time v-model="form.checkIn.estimateBoardingClose" :visitTime="true" dateStyle="width:68%" datePlaceholder="预计关闭日期" timePlaceholder="时间" formatter="yyyy-MM-dd" :allDisable="false"></date-time>
          </el-form-item>
        </el-col>
      </el-row>

      <!--<el-row :gutter="20">
        <el-col :span="6">
          <el-form-item label="值机开始时间" prop="actualBoardingOpen">
            <date-time v-model="form.checkIn.actualBoardingOpen" :visitTime="true" dateStyle="width:68%" datePlaceholder="值机开始日期" timePlaceholder="时间" formatter="yyyy-MM-dd" :allDisable="false"></date-time>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="值机关闭时间" prop="actualBoardingClose">
            <date-time v-model="form.checkIn.actualBoardingClose" :visitTime="true" dateStyle="width:68%" datePlaceholder="值机关闭日期" timePlaceholder="时间" formatter="yyyy-MM-dd" :allDisable="false"></date-time>
          </el-form-item>
        </el-col>
      </el-row>-->

    </el-form>
</div>
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
      editAble: false,
      visible: false,
      ckinLoading: false,
      boardLoading: false,
      API: API,
      form: {
        segment: {},
        checkIn: {
          id: null,
          airportCode: null,
          flightId: null,
          scheduleCheckinOpen: null,
          scheduleCheckinClose: null,
          estimateCheckinOpen: null,
          estimateCheckinClose: null,
          actualCheckinOpen: null,
          actualCheckinClose: null
        },
        boardTime: {
          id: null,
          airportCode: null,
          flightId: null,
          scheduleBoardingOpen: null,
          scheduleBoardingClose: null,
          estimateBoardingOpen: null,
          estimateBoardingClose: null,
          actualBoardingOpen: null,
          actualBoardingClose: null
        }
      },
      oldCheckIn: {},
      oldBoardTime: {}
    }
  },
  components: {
    dateTime: dateTime
  },
  methods: {
    bindData () {
    },
    show: function (row) {
      let para = {flightId: row.id}
      let now = Util.formatDate.format(new Date(), 'yyyy-MM-dd') + ' 00:00:00'
      let flightObj = {}
      this.editAble = true
      if (row !== undefined) {
        // 保存航段id、airportCode
        this.form.segment = Object.assign({}, this.form.segment, row)
        flightObj = { flightId: this.form.segment.id, airportCode: this.form.segment.carrier }
      }
      // 0923 航班值机时间查询
      this.ckinLoading = true
      this.boardLoading = true
      API.getFlightCkinTime().go(para).then((res) => {
        if (res.ok) {
          let obj = res.attr.data.list.length > 0 ? res.attr.data.list[0] : {}
          if (res.attr.data.list.length === 0) {
            this.setDefaultTime(now, 'checkin')
          }
          this.form.checkIn = Object.assign({}, this.form.checkIn, flightObj, obj)
          this.oldCheckIn = Object.assign({}, this.form.checkIn, flightObj, obj)
          this.ckinLoading = false
        }
        this.visible = this.isVisible()
      })
      // 0927 航班登机时间查询
      API.getFlightBoardingTime().go(para).then((res) => {
        if (res.ok) {
          let obj = res.attr.data.list.length > 0 ? res.attr.data.list[0] : {}
          if (res.attr.data.list.length === 0) {
            this.setDefaultTime(now, 'boarding')
          }
          this.form.boardTime = Object.assign({}, this.form.boardTime, flightObj, obj)
          this.oldBoardTime = Object.assign({}, this.form.boardTime, flightObj, obj)
          this.boardLoading = false
        }
        this.visible = this.isVisible()
      })
    },
    isVisible: function () {
      return !(this.ckinLoading || this.boardLoading)
    },
    handleClose: function () {
      this.visible = false
      this.editAble = false
      this.form = {
        segment: {},
        checkIn: {
          id: null,
          airportCode: null,
          flightId: null,
          scheduleCheckinOpen: null,
          scheduleCheckinClose: null,
          estimateCheckinOpen: null,
          estimateCheckinClose: null,
          actualCheckinOpen: null,
          actualCheckinClose: null
        },
        boardTime: {
          id: null,
          airportCode: null,
          flightId: null,
          scheduleBoardingOpen: null,
          scheduleBoardingClose: null,
          estimateBoardingOpen: null,
          estimateBoardingClose: null,
          actualBoardingOpen: null,
          actualBoardingClose: null
        }
      }
      this.oldCheckIn = {}
      this.oldBoardTime = {}
    },
    resetForm: function () {
      this.$refs['checkTimeForm'].resetFields()
      this.$refs['boardTimeForm'].resetFields()
    },
    handleSubmit: function () {
      this.submitCheckInTime()
      this.submitBoardingTime()
    },
    submitCheckInTime: function () {
      if (!this.editAble) {
        return
      }
      let para = { newValue: this.form.checkIn, oldValue: this.oldCheckIn }
      this.$refs['checkTimeForm'].validate((valid) => {
        if (valid && JSON.stringify(this.form.checkIn) !== JSON.stringify(this.oldCheckIn)) {
          // 值机时间保存
          API.saveFlightCkinTime().go(para).then((res) => {
            this.ckinLoading = false
            this.$notify(Util.notifyBody(res.ok, res.msg))
            this.visible = this.isVisible()
            if (this.visible) {
              this.callback()
            }
          })
        }
      })
    },
    submitBoardingTime: function () {
      if (!this.editAble) {
        return
      }
      let para = { newValue: this.form.boardTime, oldValue: this.oldBoardTime }
      this.$refs['boardTimeForm'].validate((valid) => {
        if (valid && JSON.stringify(this.form.boardTime) !== JSON.stringify(this.oldBoardTime)) {
          // 登机时间保存
          API.saveFlightBoardingTime().go(para).then((res) => {
            this.boardLoading = false
            this.$notify(Util.notifyBody(res.ok, res.msg))
            this.visible = this.isVisible()
            if (this.visible) {
              this.callback()
            }
          })
        }
      })
    },
    setDefaultTime: function (date, type) {
      // 设置默认日期
      if (type === 'checkin') {
        let obj = {scheduleCheckinOpen: date, scheduleCheckinClose: date, estimateCheckinOpen: date, estimateCheckinClose: date, actualCheckinOpen: date, actualCheckinClose: date}
        this.form.checkIn = Object.assign({}, this.form.checkIn, obj)
      } else {
        let boardObj = {scheduleBoardingOpen: date, scheduleBoardingClose: date, estimateBoardingOpen: date, estimateBoardingClose: date, actualBoardingOpen: date, actualBoardingClose: date}
        this.form.boardTime = Object.assign({}, this.form.boardTime, boardObj)
      }
    }
  },
  mounted () {
    // this.bindData()
    // 自适应浏览器窗口大小
    // window.addEventListener('resize', this.setDgWidth)
  }
}
</script>

<style lang="scss">
.ckAndBoardTimeCls {
  .el-form-item {
    margin-bottom: 2px!important;
  }
}
</style>
