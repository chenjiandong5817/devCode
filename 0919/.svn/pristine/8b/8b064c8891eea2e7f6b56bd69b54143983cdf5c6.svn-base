<template>
<div class="planStopOverCls">
  <el-dialog :title="title" v-model="visible" :close-on-click-modal="false" @close="clearData" :modal-append-to-body="false" size="small">
    <el-form :model="stopOver" label-position="right" label-width="70px" ref="planStopOverForm" >
      <el-row :gutter="5">
        <el-col :span="24">
          <el-form-item label=" 目的站 " prop="destination" label-position="top" :rules="{required: true, message: '目的站不能为空', trigger: 'blur'}">
            <el-select v-model="stopOver.destination" filterable placeholder="目的站" style="width: 100%;">
              <el-option
                v-for="item in $cache.fetch('airports')"
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

      <div id="planFlightDiv" v-if="!isPlanSeason">
        <el-row :gutter="5">
          <el-col :span="24">
            <el-form-item label="计划到达" prop="scheduleArriveTime">
              <date-time v-model="stopOver.scheduleArriveTime" :visitTime="true" dateStyle="width: 60%;" timeStyle="width: 38%;" datePlaceholder="计划日期" timePlaceholder="时间" formatter="yyyy-MM-dd" ></date-time>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="5">
          <el-col :span="24">
            <el-form-item label="计划起飞" prop="scheduleDepartTime">
              <date-time v-model="stopOver.scheduleDepartTime" :visitTime="true" dateStyle="width: 60%;" timeStyle="width: 38%;" datePlaceholder="计划日期" timePlaceholder="时间" formatter="yyyy-MM-dd" ></date-time>
            </el-form-item>
          </el-col>
        </el-row>
      </div>

      <div id="planSeasonDiv" v-else-if="isPlanSeason">
        <el-row :gutter="1">
          <el-col :span="16">
            <el-form-item label="计划到达" prop="scheduleArriveTime">
              <date-time v-model="stopOver.scheduleArriveTime" :visitDate="false" :visitTime="true" timeStyle="width: 66%;" datePlaceholder="计划日期" timePlaceholder="时间" formatter="yyyy-MM-dd" ></date-time>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item prop="arrOffset">
              <el-input type="number" value="number" v-model.number="stopOver.arrOffset" auto-complete="off" placeholder="阈值" style="width:68%;" :rules="[ { min: 0, max: 1, message: '请填写不大于1的整数' } ]"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="1">
          <el-col :span="16">
            <el-form-item label="计划起飞" prop="scheduleDepartTime">
              <date-time v-model="stopOver.scheduleDepartTime" :visitDate="false" :visitTime="true" timeStyle="width: 66%;" datePlaceholder="计划日期" timePlaceholder="时间" formatter="yyyy-MM-dd" ></date-time>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item prop="depOffset">
              <el-input type="number" value="number" v-model.number="stopOver.depOffset" auto-complete="off" placeholder="阈值" style="width:68%;" :rules="[ { min: 0, max: 1, message: '请填写不大于1的整数' } ]"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click.native="clearData">取消</el-button>
      <el-button @click.native="resetForm">重置</el-button>
      <el-button type="primary" @click.native="handleSubmit" :loading="loading">提交</el-button>
    </div>
  </el-dialog>
</div>
</template>

<script>
import Util from '../../../common/js/util'
import API from '../../../api'
import dateTime from '../../../components/DateTime'
export default {
  props: {
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
      title: '',
      visible: false,
      loading: false,
      curFlightId: null,
      API: API,
      index: 0,
      isPlanSeason: false,
      stopOver: {id: null, flightId: null, index: 0, flightNature: '', destination: '', segmentNature: '', scheduleDepartTime: '', scheduleArriveTime: '', boardingStand: null, stand: null, gate: null, depOffset: 0, arrOffset: 0},
      oldStopOver: {}
    }
  },
  components: {
    dateTime: dateTime
  },
  methods: {
    bindData () {
    },
    show: function (title, index, flightId, row, isPlanSeason) {
      this.visible = true
      this.title = title
      this.index = index
      this.isPlanSeason = isPlanSeason
      this.curFlightId = (flightId === '' ? null : flightId)
      if (row !== undefined && row !== null) {
        this.stopOver = Util.deepCopy(row)
        this.oldStopOver = Util.deepCopy(row)
      } else {
        // 设置默认值
        this.initData()
      }
    },
    handleClose: function () {
      this.visible = false
    },
    handleSubmit: function () {
      this.$refs['planStopOverForm'].validate((valid) => {
        if (valid) {
          let para = Util.deepCopy(this.stopOver)
          this.to(para)
          this.visible = false
        } else {
          return false
        }
      })
    },
    initData: function () {
      let date = Util.formatDate.format(new Date(), 'yyyy-MM-dd')
      this.stopOver = {id: null, flightId: this.curFlightId, index: this.index, flightNature: '', segmentNature: '', destination: '', scheduleDepartTime: date, scheduleArriveTime: date, boardingStand: null, stand: null, gate: null, depOffset: 0, arrOffset: 0}
      this.oldStopOver = {}
    },
    clearData: function () {
      this.index = 0
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
.planStopOverCls {
  .el-form-item {
    margin-bottom: 25px!important;
  }
  .el-dialog__body {
    padding: 2px 20px!important;
  }
}
.seaScheduleFormcls {
  .smallDigCls .planStopOverCls .el-dialog--small {
    width: 40%!important;
  }
  .largeDigCls .planStopOverCls .el-dialog--small {
    width: 39.5%!important;
  }
}

.largeDigCls .planStopOverCls .el-dialog--small {
  width: 40%!important;
}
</style>
