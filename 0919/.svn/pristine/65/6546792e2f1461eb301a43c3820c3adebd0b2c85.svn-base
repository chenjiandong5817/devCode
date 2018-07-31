/*
 * @Author: ylj
 * @Date: 2017-11-15 11:00:17
 * @Last Modified by: ylj
 * @Last Modified time: 2017-12-01 15:33:00
 */
<template>
<div class="importExcelCss">
  <el-dialog :title="title" v-model="visible" :close-on-click-modal="false" @close="handleClose" :modal="false" size="tiny">
    <el-form :model="form.seasonInfo" ref="impExcelPlanSea" :rules="rules">
      <el-row :gutter="6">
        <el-col :span="14">
          <el-form-item label="运营机场" prop="airportCode">
            <city-name
              style="width: 100%"
              v-model="form.seasonInfo.airportCode"
              ref="city"
              v-on:getAirportName = "setAirportCode">
            </city-name>
          </el-form-item>
        </el-col>

        <el-col :span="10">
          <el-form-item label="计划类型" prop="planSource">
            <el-select v-model="form.seasonInfo.planSource" placeholder="计划类型" style="width: 100%" :filterable="true" clearable >
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
      </el-row>

      <el-row :gutter="6">
        <el-col :span="14">
          <el-form-item label="航季名称" prop="seasonId">
            <el-select v-model="form.seasonInfo.seasonId" style="width: 100%" placeholder="航季名称" filterable clearable>
              <el-option
                v-for="item in baseList.seasonList"
                :key="item.id"
                :label="item.seasonName"
                :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="10">
          <el-form-item label="执行周期" prop="strategyType">
            <br/>
            <el-radio-group v-model="form.seasonInfo.strategyType" @change.native="initDayList">
              <el-radio :label="2">月</el-radio>
              <el-radio :label="1">周</el-radio>
              <el-radio :label="3">日</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="1">
        <el-col :span="7">
          <el-form-item label="文件上传" prop="fileList">
            <el-upload
              class="upload-demo"
              ref="excleImport"
              action="/raiis/src/views/fids/excelfile"
              :data="importPara"
              :file-list="form.seasonInfo.fileList"
              :auto-upload="false"
              :multiple="false"
              :accept="fileType"
              :on-change="importChange"
              :before-upload="beforeImport"
              :on-preview="handlePreview"
              :on-remove="handleRemove"
              style="width: 100%">
            <el-row :gutter="10">
              <el-col :span="10">
                <el-button size="small" type="primary">选择文件</el-button>
              </el-col>
            </el-row>
            </el-upload>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="" prop="files">
            <span style="font-size: 10px">请选择上传文件</span>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <!--<vue-xlsx-table @on-click-ok="handleOk"></vue-xlsx-table>-->
      <el-button @click.native="handleClose">取消</el-button>
      <el-button @click.native="resetForm('impExcelPlanSea')" >重置</el-button>
      <el-button type="primary" @click.native="handAddOrUptSubmit" :loading="loading">提交</el-button>
    </div>

    <el-dialog :title="excelTitle" v-model="tableVisible" :close-on-click-modal="false" @close="excelTableClose" :modal="false" size="large" class="excelTableCls">
      <common-table ref="excelTable"></common-table>
      <div slot="footer" class="dialog-footer">
      </div>
    </el-dialog>
  </el-dialog>
</div>
</template>

<script>
import Util from '../../../common/js/util'
import Butil from '../../../common/js/base-util'
import impExcel from '../../../common/js/importExcel'
import API from '../../../api'
import dateTime from '../../../components/DateTime'
import CityName from '../../../components/CityName'
import commonTable from '../../../components/Table'
import Rules from './../../../common/js/rules'
// import 'vue-xlsx-table/dist/style.css'
// import vueXlsxTable from 'vue-xlsx-table'

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
      visible: false,
      tableVisible: false,
      excelTitle: '',
      loading: false,
      API: API,
      form: {
        seasonInfo: { airportCode: null, airportCode4: null, planSource: null, seasonId: null, strategyType: 1, fileList: [] }
      },
      subAirportLs: [],
      baseList: { planSources: this.$cache.fetch('planSource'), seasonList: this.$cache.fetch('seasonName') },
      rules: {
        airportCode: [ { required: true, message: '请选择运营机场', trigger: 'blur' } ],
        planSource: [ { required: true, message: '请选择计划类型', trigger: 'blur' } ],
        seasonId: [ { required: true, message: '请选择航季名称', trigger: 'blur' } ],
        strategyType: [ { type: 'number', required: true, message: '请选择执行周期', trigger: 'blur' } ],
        fileList: [ { validator: Rules.arrayRequire, message: '请选择导入文件', trigger: 'blur' } ]
      },
      importPara: {},
      fileType: '.xls,.xlsx',
      // titleMap: {
      //   '公司': 'carrier',
      //   '任务': 'flightTask',
      //   '航班号': 'flightNo',
      //   '单位': 'strategyType',
      //   '周期': 'strategy',
      //   '进出': 'direction',
      //   '起飞站': 'origin',
      //   '目的站': 'destination',
      //   '机型': 'aircraftType',
      //   '区域': 'flightNature',
      //   '计划起飞': 'scheduleDepartTime',
      //   '计划到达': 'scheduleArriveTime',
      //   '开始日期': 'beginDate',
      //   '结束日期': 'endDate',
      //   '代理': 'generalAgent',
      //   '计划类型': 'planSource'
      // },
      titleMap: {
        '承运人': 'carrier',
        '航班号': 'flightNo',
        '机型': 'aircraftType',
        '班期': 'strategy',
        '类型': 'flightNature',
        '站点1': 'origin',
        '出发1': 'scheduleDepartTime1',
        '到达1': 'scheduleArriveTime1',
        '站点2': 'destination1',
        '出发2': 'scheduleDepartTime2',
        '到达2': 'scheduleArriveTime2',
        '站点3': 'destination2',
        '出发3': 'scheduleDepartTime3',
        '到达3': 'scheduleArriveTime3',
        '站点4': 'destination3',
        '出发4': 'scheduleDepartTime4',
        '到达4': 'scheduleArriveTime4',
        '站点5': 'destination4',
        '执行起始日': 'beginDate',
        '截至日期': 'endDate'
      }
      // titleMap: { '书名': 'bookName', '出借人': 'user', '数量': 'num', '时间': 'time' }
    }
  },
  components: {
    dateTime: dateTime,
    CityName: CityName,
    commonTable: commonTable
  },
  watch: {
    '$store.state.cached' (curval, oldval) {
      this.baseList = { planSources: this.$cache.fetch('planSource'), seasonList: this.$cache.fetch('seasonName') }
    }
  },
  methods: {
    bindData () {
    },
    setSeasonList (airportCode) {
      let list = this.$cache.fetch('seasonName').filter(item => {
        return item.airportCode === airportCode
      })
      this.baseList.seasonList = list
    },
    show (seasonInfo, airportCode) {
      this.visible = true
      this.showAirportInfo(this.subAirportLs, airportCode)
      this.setSeasonList(airportCode)
      this.loading = false
      this.form.seasonInfo = Util.deepCopy(seasonInfo)
    },
    initData (airportCode) {

    },
    handleClose: function () {
      this.visible = false
      this.$refs['impExcelPlanSea'].resetFields()
      this.form.seasonInfo = {}
    },
    handAddOrUptSubmit: function () {
      console.log('--handAddOrUptSubmit--')
      this.form.seasonInfo['airportCode4'] = this.$cache.findByName('airports', 'iatacode', this.form.seasonInfo.airportCode, 'icaocode')
      let para = { seasonInfo: this.form.seasonInfo, importPara: this.importPara.excelData }
      this.$refs['impExcelPlanSea'].validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.loading = true
            this.to(para).then((res) => {
              this.loading = false
              this.$notify(Util.notifyBody(res.ok, res.mes))
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
      this.form.seasonInfo.airportCode = airportCode
      this.setSeasonList(airportCode)
      this.form.seasonInfo.seasonId = this.baseList.seasonList.length > 0 ? this.baseList.seasonList[0].id : ''
    },
    showAirportInfo (subAirportLs, airportCode) {
      this.$nextTick(() => {
        if (airportCode !== '' || airportCode !== undefined) {
          this.$refs['city'].airportCode = airportCode
        }
        this.$refs['city'].setCitys(subAirportLs)
      })
    },
    importChange (file, fileList) {
      // let titleMap = { '书名': 'bookName', '出借人': 'user', '数量': 'num', '时间': 'time' }
      impExcel.importFiles(file, this.titleMap, 20).then(res => {
        console.log('--importChange--')
        if (res !== undefined && res.ok) {
          this.form.seasonInfo.fileList.push(file)
          this.importPara['excelData'] = res.resultJson
          // 导入API测试联调 begin 待续 1118
          // API.importSeaSchedule(this.importPara).then((res) => {
          //   this.loading = false
          //   this.$notify(Util.notifyBody(res.ok, res.msg))
          //   // this.resetForm('seaScheduleForm')
          //   this.visible = false
          //   this.callback()
          // })
          // 导入API测试联调 end 待续 1118
        } else {
          // 文件上传类型出错，从上传列表中删除
          this.$message({
            type: 'info',
            message: res.msg
          })
        }
      })
    },
    beforeImport () {
      console.log('--beforeImport--')
    },
    handleRemove (file, fileList) {
      console.log('--handleRemove--')
      let list = this.form.seasonInfo.fileList.filter(item => {
        return JSON.stringify(item.raw) !== JSON.stringify(file.raw)
      })
      this.form.seasonInfo.fileList = list
    },
    handlePreview (file) {
      console.log('--handlePreview--')
      this.tableVisible = true
      let tableKey = []
      for (var key in this.titleMap) {
        let s = {}
        s['name'] = key
        s['value'] = this.titleMap[key]
        tableKey.push(s)
      }
      this.excelTitle = file.raw.name
      this.$nextTick(() => {
        this.$refs['excelTable'].tableData = Util.deepCopy(this.importPara['excelData'])
        this.$refs['excelTable'].tableKey = tableKey
      })
      console.log('test')
      console.log(window.location.origin)
      // window.open(window.location.origin + '/a/b/c')
    },
    excelTableClose (convertedData) {
      this.$refs['excelTable'].clearAllData()
      this.tableVisible = false
    }
  },
  mounted () {
    this.subAirportLs = Butil.getSubscribeAirports()
    this.bindData()
  }
}

</script>
<style lang="scss">
.importExcelCss {
  .el-dialog__body {
    padding-top: 10px;
  }

  .el-form-item {
    margin-bottom: 10px;
  }

  .excelTableCls {
    // width: 1200px!important;
  }
}
</style>

