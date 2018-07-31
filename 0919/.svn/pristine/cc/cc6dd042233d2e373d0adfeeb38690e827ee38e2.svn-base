<template>
<div class="confirmClass">
  <el-dialog :title="title" v-model="visible" :close-on-click-modal="false" @close="handleClose" :modal="false">
    <el-form :model="form.segment" ref="flightConfirmForm">
      <el-row :gutter="18">
        <el-col :span="6">
          <el-form-item label="执行日期">
            <el-date-picker v-model="form.segment.opDate" placeholder="执行日期" style="width:100%;" format="yyyy-MM-dd" :picker-options="pickerOptions" :disabled="editAble"></el-date-picker>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="公司">
            <el-select v-model="form.segment.carrier" name="carrier" placeholder="公司" style="width:100%;" filterable :disabled="editAble">
              <el-option
                v-for="item in $cache.fetch('airlines')"
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
          <el-form-item label="航班号">
            <el-input v-model="form.segment.flightNo" name="flightNo" style="width:100%;" placeholder="航班号" :disabled="editAble"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="进出">
            <el-select v-model="form.segment.direction" placeholder="进出" style="width:100%;" :disabled="editAble" filterable>
              <el-option
                v-for="item in $cache.fetch('flightdirections')"
                :key="item.directionCode"
                :label="item.description"
                :value="item.directionCode">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="18">
        <el-col :span="6">
          <el-form-item label="机号">
            <el-select v-model="form.segment.registration" filterable remote :remote-method="registrationRemote" @change="setAircraftType" placeholder="机号" style="width:100%;" :disabled="editAble">
              <el-option
                v-for="item in registrations"
                :key="item.registration"
                :label="item.registration"
                :value="item.registration">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="机型">
            <el-select v-model="form.segment.aircraftType" placeholder="机型" style="width:100%;" filterable :disabled="editAble">
              <el-option
                v-for="item in $cache.fetch('aircraftTypes')"
                :key="item.sysCode"
                :label="item.sysCode"
                :value="item.sysCode">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="任务">
            <el-select v-model="form.segment.flightTask" placeholder="任务" filterable style="width:100%;" filterable :disabled="editAble">
              <el-option
                v-for="item in $cache.fetch('flighttasks')"
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
          <el-form-item label="状态" prop="flightStatus">
            <div v-for="item in $cache.fetch('flightstatus')">
              <input v-if="form.segment.flightStatus === item.statusCode" name="flightStatus" placeholder="状态" :value="item.description + '(' + item.statusCode + ')'" style="width: 100%;background-color: #FFA500" class="el-input__inner" :disabled="editAble"></el-input>
            </div>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="6">
          <el-form-item label="始发站" prop="origin">
            <el-select v-model="form.segment.origin" style="width:100%;" filterable placeholder="始发站" clearable :disabled="true">
              <el-option
                v-for="item in this.$cache.fetch('airports')"
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
          <el-form-item label="目的站" prop="destination">
            <el-select v-model="form.segment.destination" style="width:100%;" filterable placeholder="始发站" clearable  :disabled="true">
              <el-option
                v-for="item in this.$cache.fetch('airports')"
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
          <el-form-item label="航站楼">
            <el-select v-model="form.segment.terminal" placeholder="航站楼" style="width:100%;" filterable :disabled="editAble">
              <el-option
                v-for="item in $cache.fetch('terminals')"
                :key="item.terminalCode"
                :label="item.terminalCode"
                :value="item.terminalCode">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="总代">
            <el-select v-model="form.segment.generalAgent" placeholder="总代" style="width:100%;" filterable :disabled="editAble">
              <el-option
                v-for="item in $cache.fetch('generalagents')"
                :key="item.agentCode"
                :label="item.description"
                :value="item.agentCode">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click.native="handleClose">取消</el-button>
      <el-button type="primary" @click.native="handleSubmit" :loading="loading">提交</el-button>
    </div>

  </el-dialog>
</div>
</template>

<script>
import Util from '../../../common/js/util'
import API from '../../../api'
import AddStopOver from '../dynamicFlightForm/AddStopOver'
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
      editAble: true,
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
          direction: null,
          opDate: '',
          terminal: null,
          generalAgent: null,
          carrier: null,
          flightNo: null,
          registration: null,
          aircraftType: null,
          flightStatus: null,
          flightTask: null
        },
        stopOverList: [],
        irrReason: null
      },
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
      currentSopOver: null,
      registrations: [],
      allRegistrationList: [],
      curSelectRow: [],
      params: {operate: '', optName: '', para: ''}
    }
  },
  components: {
    addStopOver: AddStopOver,
    dateTime: dateTime
  },
  methods: {
    bindData () {
    },
    show: function (row, params) {
      if (row !== undefined) {
        this.curSelectRow = Util.deepCopy(row)
        this.form.segment = Object.assign({}, this.form.segment, params.para.newValue[0])
      }
      this.params = Object.assign({}, params)
      this.visible = true
    },
    handleClose: function () {
      this.visible = false
      this.editAble = true
    },
    handleSubmit: function () {
      this.$refs['flightConfirmForm'].validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.loading = true
            this.to(this.params).then((res) => {
              this.loading = false
              let msg = this.params.optName + res.msg
              this.$notify(Util.notifyBody(res.ok, msg))
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
    registrationRemote: function (query) {
      if (query !== '' && query !== null && query !== undefined) {
        let para = {pageSize: 99999}
        API.searchReg(query.toLowerCase()).go(para).then((data) => {
          this.registrations = data.list
        })
      } else {
        this.registrations = []
      }
    },
    setAircraftType: function (val) {
      let list = []
      list = this.allRegistrationList.filter(item => {
        return item.registration === val
      })
      if (list.length > 0) {
        this.form.segment.aircraftType = list[0].aircraftType
      }
    }
  },
  mounted () {
    // this.bindData()
    var storage = this.$store.getters.getUserStorage
    this.sysUserName = storage.user.profile.nickname || storage.user.name
    this.allRegistrationList = this.$cache.fetch('registrations')
  }
}
</script>

<style lang="scss">
.confirmClass {
  .el-dialog__body {
    padding-top: 5px;
  }
  .el-form-item {
    margin-bottom: 2px!important;
  }
  .el-dialog--small {
    width: 42%!important;
  }
  input {
    color: black!important;
  }
}

.confirmClass [name=flightNo],.confirmClass [name=carrier] {
  background: #FFA500!important;
  color: black!important;
}
</style>
