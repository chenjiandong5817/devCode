/*
 * @Author: cdroid
 * @Date: 2017-04-17 15:04:00
 * @Last Modified by: ylj
 * @Last Modified time: 2017-12-01 15:34:22
 * @Description: 通用新增或更新界面
 */

<template>
  <el-dialog :title="title" :visible.sync="visible" :close-on-click-modal="false" :before-close="handleBeforeClose" @close="handleClose" :size="size" >
    <el-form :model="target" :label-position="labelPosition" :rules="rules" ref="commonForm">
       <el-row v-for="formItem in fields" :key="formItem.id" v-if="!formItem.hidden" :gutter="gutter">
          <el-col v-for="item in formItem.item" :key="item.name" :span="item.span">
            <el-form-item :label="item.label" :label-width="labelWidth + 'px'" :prop="item.name">
              <el-input @change="getInputItem" :type="item.type" v-model="target[item.name]" auto-complete="off" :disabled="item.disabled"
                v-if="item.type === 'text' || item.type === 'textarea' || item.type === 'password'"></el-input>
              <el-input :type="item.type" value="number" v-model.number="target[item.name]" auto-complete="off" v-else-if="item.type === 'number'" :v-show="item.show" :min="item.min" :max="item.max"></el-input>
              <el-date-picker :type="item.type" v-model="target[item.name]" v-else-if="item.type === 'datetime'" >
              </el-date-picker>
              <el-date-picker :type="item.type" v-model="target[item.name]" v-else-if="item.type === 'date'" :width="item.width" :picker-options="pickerOptions">
              </el-date-picker>
              <date-time v-model="target[item.name]" :visitDate="item.visitDate" :visitTime="item.visitTime" :dateStyle="item.dateStyle" :timeStyle="item.timeStyle" :datePlaceholder="item.datePlaceholder" :timePlaceholder="item.timePlaceholder" :formatter="item.formatter" v-else-if="item.type === 'dateTimeGroup'" :allDisable="item.allDisable"></date-time>
              <el-radio-group v-model="target[item.name]" v-else-if="item.type === 'radio'">
                <el-radio v-for="radioItem in item.choose" class="radio" :label="radioItem.value" :key="radioItem.value">{{radioItem.text}}</el-radio>
              </el-radio-group>
              <el-select @change="getSelectItem" class="selectA" v-model="target[item.name]" :disabled="item.disabled" placeholder="请选择" :filterable="item.filterable" :multiple="item.multiple" v-else-if="item.type === 'select'">
                <el-option
                  v-for="selectItem in item.choose"
                  :key="selectItem.value"
                  :label="selectItem.text"
                  :value="selectItem.value">
                </el-option>
              </el-select>
              <el-select @change="getSelectItem" class="selectA" v-model="target[item.name]" :disabled="item.disabled" placeholder="请选择" :filterable="item.filterable" :multiple="item.multiple" v-else-if="item.type === 'selectExplain'">
                <el-option-group
                  v-for="group in item.choose"
                  :key="group.label"
                  :label="group.label">
                <el-option
                  v-for="selectItem in group.options"
                  :key="selectItem.value"
                  :label="selectItem.labelShow"
                  :value="selectItem.value">
                  <span style="float: left">{{ selectItem.text }}</span>
                  <span style="float: right">{{ selectItem.code }}</span>
                </el-option>
                </el-option-group>
              </el-select>
              <el-select @change="getSelectItem" class="selectA" v-model="target[item.name]" :disabled="item.disabled" placeholder="请选择" :filterable="item.filterable" :multiple="item.multiple" v-else-if="item.type === 'selectExplainNoType'">
                <el-option
                  v-for="selectItem in item.choose"
                  :key="selectItem.value"
                  :label="selectItem.text"
                  :value="selectItem.value">
                  <span style="float: left">{{ selectItem.text }}</span>
                  <span style="float: right">{{ selectItem.code }}</span>
                </el-option>
              </el-select>

              <el-select @change="getSelectItem" class="selectA" v-model="target[item.name]" :disabled="item.disabled" placeholder="请选择" :filterable="item.filterable" :clearable="item.clearable" :multiple="item.multiple" v-else-if="item.type === 'selectSimple'">
               <el-option
                  v-for="selectItem in item.choose"
                  :key="selectItem[item.selValue]"
                  :label="selectItem[item.selLabel]"
                  :value="selectItem[item.selValue]">
                  <span style="float: left">{{ selectItem[item.selLabel] }}</span>
                  <span style="float: right" v-if="!item.is2Label">{{ selectItem[item.selValue] }}</span>
                  <span style="float: right" v-else-if="item.is2Label">{{ selectItem[item.selValue2] + '/' + selectItem[item.selValue] }}</span>
                </el-option>
              </el-select>

              <city-name
                ref="city"
                style="width: 100%"
                v-model="target[item.name]"
                v-on:getAirportName = "setAirportCode"
                v-else-if="item.type === 'cityName'">
              </city-name>

              <!--EXCEL导入模板-->
              <excel-upload
                ref="excelUpload"
                :action="item.action"
                :autoUpload="item.autoUpload"
                :multiple="item.multiple"
                :fileType="item.fileType"
                :previewSize="item.previewSize"
                v-on:getFileList = "setFileList"
                v-if="item.type === 'excelUpload'"></excel-upload>
		        </el-form-item>
          </el-col>
       </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
        <el-button @click.native="handleClose">取消</el-button>
        <el-button @click="resetForm('commonForm')">重置</el-button>
        <el-button type="primary" @click.native="handleSubmit" v-if="!isDefSubmit" :loading="loading">提交</el-button>
        <el-button type="primary" @click.native="handleDefSubmit" v-if="isDefSubmit" :loading="loading">提交</el-button>
    </div>
  </el-dialog>
</template>
<script>
import util from './../../../common/js/util.js'
import dateTime from '../../../components/DateTime'
import CityName from '../../../components/CityName'
import ExcelUpload from '../../../components/ExcelUpload'

export default {
  props: {
    size: {
      type: String,
      default: 'small'
    },
    title: {
      type: String,
      default: '新增'
    },
    labelPosition: {
      type: String,
      default: 'right'
    },
    gutter: {
      type: Number,
      default: 10
    },
    labelWidth: {
      type: Number,
      default: 80
    },
    to: {
      type: Function,
      default: function () {}
    },
    callback: {
      type: Function,
      default: function () {}
    },
    isDefSubmit: {
      type: Boolean,
      default: false
    },
    isImpExcUpload: {
      type: Boolean,
      default: false
    },
    isSimpleSubmit: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      fields: [],
      visible: false,
      loading: false,
      target: {},
      rules: {},
      oldValue: {},
      pickerOptions: {
        shortcuts: [{
          text: '今天',
          onClick (picker) {
            picker.$emit('pick', new Date())
          }
        }, {
          text: '昨天',
          onClick (picker) {
            const date = new Date()
            date.setTime(date.getTime() - 3600 * 1000 * 24)
            picker.$emit('pick', date)
          }
        }, {
          text: '一周前',
          onClick (picker) {
            const date = new Date()
            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7)
            picker.$emit('pick', date)
          }
        }]
      },
      airportCodeLs: [],
      titleMap: {},
      titleNum: 0
    }
  },
  components: {
    dateTime: dateTime,
    CityName: CityName,
    ExcelUpload: ExcelUpload
  },
  methods: {
    show: function (fields, isImpCity, airportCode) {
      this.loading = false
      this.visible = true
      this.fields = fields
      this.initTarget()
      this.initRules()
      this.$nextTick(() => {
        this.$refs['commonForm'].resetFields()
      })
      this.oldValue = util.deepCopy(this.target)
      // isImpCity判断是否引入city-name控件
      if (isImpCity !== undefined && isImpCity !== null && isImpCity) {
        this.$nextTick(() => {
          this.$refs['city'][0].setCitys(this.airportCodeLs)
          if (airportCode !== undefined && airportCode !== null && airportCode !== '') {
            this.$refs['city'][0].setValue(airportCode)
          }
        })
      }
      // 上传导入EXCEL文件
      if (this.isImpExcUpload) {
        this.setTitleInfo(this.titleMap, this.titleNum)
      }
    },
    setAirportCodeLs (airportCodeLs) {
      this.airportCodeLs = airportCodeLs
    },
    initTarget: function () {
      this.target = {}
      let result = {}
      for (let i = 0; i < this.fields.length; i++) {
      	for (let j = 0; j < this.fields[i].item.length; j++) {
      		let field = this.fields[i].item[j]
      		result[field.name] = field.type === 'number' ? Number(field.value) : field.value
      	}
      }
      this.target = result
    },
    getSelectItem () {
      this.$emit('getSelectItem', this.target)
    },
    changeSelectChoose (index1, index2, data, key, value) {
      this.fields[index1].item[index2].choose = data
      this.target[key] = value
    },
    // 输入框数据改变，目的是改变input框的值
    getInputItem () {
      this.$emit('getInputItem', this.target)
    },
    changeInput (key, value) {
      // this.fields[index].choose = data
      this.target[key] = value
    },
    changeRules (key, value) {
      this.rules[key] = []
      this.rules[key][0] = { validate: value }
      // this.rules[key] = value
    },
    initRules: function () {
      this.rules = {}
      let result = {}
      for (let i = 0; i < this.fields.length; i++) {
      	for (let j = 0; j < this.fields[i].item.length; j++) {
      		if (this.fields[i].item[j].rules) {
	          result[this.fields[i].item[j].name] = this.fields[i].item[j].rules
	        }
      	}
      }
      this.rules = result
    },
    handleSubmit: function () {
      if (this.isSimpleSubmit) {
        this.handleSimpleSubmit()
        return
      }
      this.$refs['commonForm'].validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.loading = true
            // alert(JSON.stringify(this.target))
            let para = { newValue: this.target, oldValue: this.oldValue }
            if (para.newValue.updateStart) {
              para.newValue.updateStart = this.dateFormated(para.newValue.updateStart)
            }
            if (para.newValue.publishDate) {
              para.newValue.publishDate = this.dateFormated(para.newValue.publishDate)
            }
            // 这边的if语句不会影响到其他地方，因为根据agentCode来判断执行条件的
            if (para.newValue.agentCode) {
              para.newValue.clientAirline = para.newValue.clientAirline.join('/')
              para.oldValue.clientAirline = para.oldValue.clientAirline.join('/')
            }
            if (para.newValue.isvirtual) {
              para = { newValue: this.target }
            }
            this.to(para).then((res) => {
              this.loading = false
              this.$notify(util.notifyBody(res.ok, res.msg))
              this.$refs['commonForm'].resetFields()
              this.visible = false
              this.fields = []
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
    dateFormated: function (date) {
      var dataFormated = null
      var moment = null
      moment = require('moment')
      dataFormated = moment(date).format('YYYY-MM-DD HH:mm:ss')
      return dataFormated
    },
    resetForm (formName) {
      this.$refs['commonForm'].resetFields()
      this.target = {}
      this.target = Object.assign({}, this.oldValue)
      this.initExcUpload()
    },
    handleBeforeClose (done) {
      done()
    },
    handleClose () {
      this.$refs['commonForm'].resetFields()
      this.target = {}
      // 清楚导入EXCEL模板数据
      this.initExcUpload()
      this.visible = false
    },
    setAirportCode (airportCode) {
      this.target.airportCode = airportCode
    },
    handleDefSubmit () {
      this.$refs['commonForm'].validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.loading = true
            let para = util.deepCopy(this.target)
            this.to(para)
            this.visible = false
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消提交'
            })
          })
        }
      })
    },
    handleSimpleSubmit () {
      this.$refs['commonForm'].validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.loading = true
            let para = {}
            para = this.setParams()
            this.to(para).then((res) => {
              this.loading = false
              this.$notify(util.notifyBody(res.ok, res.msg))
              this.$refs['commonForm'].resetFields()
              this.visible = false
              this.fields = []
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
    setParams (val) {
      let params = { newValue: this.target }
      if (this.isImpExcUpload) {
        let excelData = this.getImpParam()
        if (excelData !== {}) {
          params['importPara'] = util.deepCopy(excelData.excelData)
        }
      }
      return params
    },
    getTarget () {
      return this.target
    },
    getImpParam () {
      if (this.isImpExcUpload && this.visible) {
        return this.$refs['excelUpload'][0].getImportPara()
      }
    },
    setTitleInfo (titleMap, titleNum) {
      if (this.isImpExcUpload && this.visible) {
        this.$nextTick(() => {
          this.$refs['excelUpload'][0].setTitleInfo(this.titleMap, this.titleNum)
        })
      } else {
        this.titleMap = util.deepCopy(titleMap)
        this.titleNum = titleNum
      }
    },
    initExcUpload () {
      if (this.isImpExcUpload && this.visible) {
        this.$refs['excelUpload'][0].initData()
      }
    },
    setFileList (fileList) {
      this.target.fileList = fileList
    }
  }
}
</script>
<style scoped>
.selectA {
  width: 100%;
}

/*空白会被浏览器识别*/
.el-select-dropdown__item span {
  white-space: pre;
}
</style>
