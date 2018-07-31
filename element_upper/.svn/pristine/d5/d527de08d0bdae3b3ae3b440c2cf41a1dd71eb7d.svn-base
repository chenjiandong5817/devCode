<template>
<div class="confirmClass">
<div :class="dialogClss">
  <el-dialog :title="title" v-model="visible" :close-on-click-modal="false" @close="handleClose" :modal="false" @open="setDgWidth">
    <el-form :model="form.checkIn" ref="checkTimeForm">
      <el-row :gutter="18">
        <el-col :span="6">
          <el-form-item label="执行日期">
            <date-time v-model="form.segment.opDate" :visitTime="false" datePlaceholder="执行日期" formatter="yyyy-MM-dd" dateStyle="width:100%" :allDisable="true"></date-time>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="公司">
            <el-input name="carrier" placeholder="公司" :value="form.segment.carrier" style="width: 100%;" :disabled="true"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="航班号">
            <el-input v-model="form.segment.flightNo" name="flightNo" style="width:100%;" placeholder="航班号" :disabled="true"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="进出">
            <el-input name="direction" placeholder="进出" :value="$cache.findByName('flightdirections', 'directionCode', form.segment.direction, 'description')" style="width: 100%;" :disabled="true"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="18">
        <el-col :span="6">
          <el-form-item label="机号">
            <el-input v-model="form.segment.registration" style="width:100%;" placeholder="机号" :disabled="true"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="机型">
            <el-input name="aircraftType" placeholder="机型" :value="form.segment.aircraftType" style="width: 100%;" :disabled="true"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="任务">
            <el-input name="flightTask" placeholder="任务" :value="$cache.findByName('flighttasks', 'flightTaskCode', form.segment.flightTask, 'abbr2w') + '(' + form.segment.flightTask + ')'" style="width: 100%;" :disabled="true"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="状态" prop="flightStatus">
            <el-input name="flightStatus" placeholder="状态" :value="$cache.findByName('flightstatus', 'statusCode', form.segment.flightStatus, 'description') + '(' + form.segment.flightStatus + ')'" style="width: 100%;" :disabled="true"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="6">
          <el-form-item label="航站楼">
            <el-input name="terminal" placeholder="航站楼" :value="form.segment.terminal" style="width: 100%;" :disabled="true"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="总代">
            <el-input :value="$cache.findByName('generalagents', 'agentCode', form.segment.generalAgent, 'description')" placeholder="总代" style="width: 100%;" :disabled="true"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <!--<el-row :gutter="20">
        <el-col :span="3" >
          <el-form-item label="值机时间"></el-form-item>
        </el-col>
      </el-row>-->
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

      <el-row :gutter="20">
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
      </el-row>

    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click.native="handleClose">取消</el-button>
      <el-button @click.native="resetForm('checkTimeForm')">重置</el-button>
      <el-button type="primary" @click.native="handleSubmit" :loading="loading">提交</el-button>
    </div>

  </el-dialog>
</div>
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
      dialogClss: 'ckinTInmeClass1',
      editAble: true,
      visible: false,
      loading: false,
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
        }
      },
      oldCheckIn: {}
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
      this.setDefaultTime(now)
      if (row !== undefined) {
        this.form.segment = Object.assign({}, this.form.segment, row)
      }
      // 0923 航班值机时间查询
      API.getFlightCkinTime().go(para).then((res) => {
        if (res.ok) {
          let obj = res.attr.data.list.length > 0 ? res.attr.data.list[0] : {}
          this.form.checkIn = Object.assign({}, this.form.checkIn, obj)
          this.oldCheckIn = Object.assign({}, this.form.checkIn, obj)
        }
        this.visible = true
      })
    },
    handleClose: function () {
      this.visible = false
      this.editAble = true
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
        }
      }
      this.oldCheckIn = {}
    },
    resetForm: function (formid) {
      this.form.checkIn.scheduleCheckinOpen = null
      this.$refs[formid].resetFields()
    },
    handleSubmit: function () {
      let para = { newValue: this.form.checkIn, oldValue: this.oldCheckIn }
      this.$refs['checkTimeForm'].validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.loading = true
            // 值机时间保存
            this.to(para).then((res) => {
              this.loading = false
              this.$notify(Util.notifyBody(res.ok, res.msg))
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
    setDefaultTime: function (date) {
      let obj = {scheduleCheckinOpen: date, scheduleCheckinClose: date, estimateCheckinOpen: date, estimateCheckinClose: date, actualCheckinOpen: date, actualCheckinClose: date}
      this.form.checkIn = Object.assign({}, this.form.checkIn, obj)
    },
    setDgWidth (event) {
      this.dialogClss = document.body.clientWidth < 1920 ? 'ckinTInmeClass2' : 'ckinTInmeClass1'
    }
  },
  mounted () {
    // this.bindData()
    // 自适应浏览器窗口大小
    window.addEventListener('resize', this.setDgWidth)
  }
}
</script>

<style lang="scss">
.confirmClass {
  .el-form-item {
    margin-bottom: 2px!important;
  }

  .ckinTInmeClass1 {
    .el-dialog--small {
      width: 50%!important;
    }
  }

  .ckinTInmeClass2 {
    .el-dialog--small {
      width: 65%!important;
    }
  }
}

.confirmClass [name=flightNo],.confirmClass [name=carrier],.confirmClass [name=flightStatus] {
  background: #FFA500!important;
  color: black!important;
}
</style>
