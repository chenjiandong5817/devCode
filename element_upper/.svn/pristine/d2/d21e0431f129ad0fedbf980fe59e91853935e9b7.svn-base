/*
 * @Author: ylj
 * @Date: 2017-11-15 11:00:17
 * @Last Modified by: ylj
 * @Last Modified time: 2017-11-30 10:54:49
 */
<template>
<div class="genMorFlightCss">
  <el-dialog :title="title" v-model="visible" :close-on-click-modal="false" @close="handleClose" :modal="false" size="tiny">
    <el-form :model="seasonInfo" ref="genMorFlightForm" :rules="rules">
      <el-row :gutter="6">
        <el-col :span="12">
          <el-form-item label="运营机场" prop="airportCode">
            <city-name
              style="width: 100%"
              v-model="seasonInfo.airportCode"
              ref="city"
              v-on:getAirportName = "setAirportCode">
            </city-name>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="航季名称" prop="seasonId">
            <el-select v-model="seasonInfo.seasonId" placeholder="航季名称" @change="setDefSeaDate" filterable clearable style="width: 100%;">
              <el-option
                v-for="item in seasonList"
                :key="item.id"
                :label="item.seasonName"
                :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="6">
        <el-col :span="12">
          <el-form-item label="开始日期" prop="beginDate">
            <el-date-picker v-model="seasonInfo.beginDate" align="right" type="date" placeholder="选择日期" :picker-options="pickerOptions" style="width: 100%;"></el-date-picker>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="结束日期" prop="endDate">
            <el-date-picker v-model="seasonInfo.endDate" align="right" type="date" placeholder="选择日期" :picker-options="pickerOptions" style="width: 100%;"></el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="6">
        <el-col :span="12">
           <el-form-item label="航班开始日期" prop="beginTimeFlight">
            <date-time v-model="seasonInfo.beginTimeFlight" :visitTime="true" style="width: 100%;" dateStyle="width: 68%;" datePlaceholder="请选择时间" timePlaceholder="时间" formatter="yyyy-MM-dd" ></date-time>
          </el-form-item>
        </el-col>

        <el-col :span="12">
           <el-form-item label="航班结束日期" prop="endTimeFlight">
            <date-time v-model="seasonInfo.endTimeFlight" :visitTime="true" style="width: 100%;" dateStyle="width: 68%;" datePlaceholder="请选择时间" timePlaceholder="时间" formatter="yyyy-MM-dd" ></date-time>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click.native="handleClose">取消</el-button>
      <el-button @click.native="resetForm('genMorFlightForm')" >重置</el-button>
      <el-button type="primary" @click.native="handAddOrUptSubmit" :loading="loading">提交</el-button>
    </div>
  </el-dialog>
</div>
</template>

<script>
import Util from '../../../common/js/util'
import Butil from '../../../common/js/base-util'
import API from '../../../api'
import dateTime from '../../../components/DateTime'
import CityName from '../../../components/CityName'

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
      API: API,
      subAirportLs: [],
      seasonList: this.$cache.fetch('seasonName'),
      seasonInfo: { airportCode: '', seasonId: '', beginDate: '', endDate: '', beginTimeFlight: '', endTimeFlight: '' },
      rules: {
        airportCode: [ { required: true, message: '请选择运营机场', trigger: 'change' } ],
        seasonId: [ { required: true, message: '请选择航季名称', trigger: 'change' } ],
        beginDate: [ { type: 'date', required: true, message: '请选择航季开始日期', trigger: 'change' } ],
        endDate: [ { type: 'date', required: true, message: '请选择航季结束日期', trigger: 'change' } ],
        beginTimeFlight: [ { required: true, message: '请选择航班开始时间', trigger: 'change' } ],
        endTimeFlight: [ { required: true, message: '请选择航班结束时间', trigger: 'change' } ]
      }
    }
  },
  components: {
    dateTime: dateTime,
    CityName: CityName
  },
  watch: {
    '$store.state.cached' (curval, oldval) {
      this.seasonList = this.$cache.fetch('seasonName')
    }
  },
  methods: {
    setSeasonList (airportCode) {
      let list = this.$cache.fetch('seasonName').filter(item => {
        return item.airportCode === airportCode
      })
      this.seasonList = list
    },
    show (seasonInfo) {
      this.visible = true
      this.showAirportInfo(this.subAirportLs, seasonInfo.airportCode)
      this.setSeasonList(seasonInfo.airportCode)
      this.loading = false
      this.seasonInfo.airportCode = seasonInfo.airportCode
      this.seasonInfo.seasonId = seasonInfo.seasonId
    },
    initData (airportCode) {
      this.seasonInfo = { airportCode: '', seasonId: '', beginDate: '', endDate: '', beginTimeFlight: '', endTimeFlight: '' }
    },
    handleClose: function () {
      this.visible = false
      this.$refs['genMorFlightForm'].resetFields()
      // this.initData()
    },
    handAddOrUptSubmit: function () {
      let seasonInfo = Util.deepCopy(this.seasonInfo)
      let moment = require('moment')
      seasonInfo.beginDate = moment(seasonInfo.beginDate).format('YYYY-MM-DD')
      seasonInfo.endDate = moment(seasonInfo.endDate).format('YYYY-MM-DD')
      let para = { seasonInfo: seasonInfo }
      this.$refs['genMorFlightForm'].validate((valid) => {
        if (valid) {
          this.$confirm('确认根据所选航季生成次日航班吗？', '提示', {}).then(() => {
            this.loading = true
            this.to(para).then((res) => {
              this.loading = false
              this.$notify(Util.notifyBody(res.ok, res.mes))
              this.$refs['genMorFlightForm'].resetFields()
              this.visible = false
              this.callback()
            })
          }).catch(() => {
            this.message({
              type: 'info',
              message: '已取消提交'
            })
          })
        }
      })
    },
    resetForm (formName) {
      this.$refs[formName].resetFields()
    },
    setAirportCode (airportCode) {
      this.seasonInfo.airportCode = airportCode
      this.setSeasonList(airportCode)
      this.seasonInfo.seasonId = this.seasonList.length > 0 ? this.seasonList[0].id : ''
    },
    showAirportInfo (subAirportLs, airportCode) {
      this.$nextTick(() => {
        if (airportCode !== '' || airportCode !== undefined) {
          this.$refs['city'].airportCode = airportCode
        }
        this.$refs['city'].setCitys(subAirportLs)
      })
    },
    setDefSeaDate (val) {
      // 设置默认航季开始结束时间
      var list = this.seasonList.filter(item => {
        return item.id === val
      })
      if (list.length > 0) {
        // 1129 带测试修改
        this.seasonInfo.beginDate = new Date(list[0].beginDate)
        this.seasonInfo.endDate = new Date(list[0].endDate)
      }
    }
  },
  mounted () {
    this.subAirportLs = Butil.getSubscribeAirports()
  }
}

</script>
<style lang="scss">
.genMorFlightCss {
  .el-dialog__body {
    padding-top: 10px;
  }

  .el-form-item {
    margin-bottom: 10px;
  }
}
</style>

