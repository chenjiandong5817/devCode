/*
 * @Author: cdroid
 * @Date: 2017-04-17 15:04:00
 * @Last Modified by: ylj
 * @Last Modified time: 2017-11-17 15:22:10
 * @Description: 通用新增或更新界面
 */

<template>
  <el-dialog :title="title" :visible.sync="visible" :close-on-click-modal="false" :before-close="handleBeforeClose" @close="handleClose" :size="size" >
    <el-form :model.sync="target" :label-width="labelWidth + 'px'" :rules="rules" ref="commonForm">
      <el-form-item v-for="item in fields" :label="item.label" :prop="item.name" :key="item.id" v-if="!item.hidden">
        <!-- 一般文本框 -->
        <el-input @change="getInputItem" :type="item.type" v-model="target[item.name]" auto-complete="off" :disabled="item.disabled"
        v-if="item.type === 'text' || item.type === 'textarea' || item.type === 'password'" autosize></el-input>
        <!-- number判断 -->
        <el-input :type="item.type" value="number" v-model.number="target[item.name]" auto-complete="off" v-else-if="item.type === 'number'" :v-show="item.show" :min="item.min" :max="item.max"></el-input>
        <!-- 时间判断 -->
        <el-date-picker :type="item.type" v-model="target[item.name]" v-else-if="item.type === 'datetime'" :picker-options="pickerOptions" >
        </el-date-picker>
        <date-time v-model="target[item.name]" :visitDate="item.visitDate" :visitTime="item.visitTime" style="width: 100%;" :datePlaceholder="item.datePlaceholder" :formatter="item.formatter" :dateStyle="item.dateStyle" v-else-if="item.type === 'dateTimeGroup'"></date-time>
        <!--radio判断 -->
        <el-radio-group v-model="target[item.name]" v-else-if="item.type === 'radio'">
          <el-radio v-for="radioItem in item.choose" class="radio" :label="radioItem.value" :key="radioItem.value">{{radioItem.text}}</el-radio>
        </el-radio-group>
        <el-select class="selectA" v-model="target[item.name]" :disabled="item.disabled" placeholder="请选择" :filterable="item.filterable" :multiple="item.multiple" v-else-if="item.type === 'simpleSelect'">
          <el-option
            v-for="selectItem in item.choose"
            :key="selectItem.value"
            :label="selectItem.text"
            :value="selectItem.value">
            <span style="float: left">{{ selectItem.text }}</span>
            <span style="float: right">{{ selectItem.value }}</span>
          </el-option>
        </el-select>
        <!-- select判断-无解释的 -->
        <el-select @change="getSelectItem" class="selectA" v-model="target[item.name]" :disabled="item.disabled" placeholder="请选择" :filterable="item.filterable" :multiple="item.multiple" v-else-if="item.type === 'select'">
          <el-option
            v-for="selectItem in item.choose"
            :key="selectItem.id"
            :label="selectItem.text"
            :value="selectItem.value">
          </el-option>
        </el-select>
        <!-- select判断-带有解释的 -->
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
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
        <el-button @click.native="visible = false">取消</el-button>
        <el-button @click="resetForm('commonForm')">重置</el-button>
        <el-button type="primary" @click.native="handleSubmit" :loading="loading">提交</el-button>
    </div>
  </el-dialog>
</template>
<script>
import util from './../common/js/util.js'
import dateTime from '../components/DateTime'
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
    // type: {
    //   type: String,
    //   default: 'add'
    // },
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
    }
  },
  data () {
    return {
      fields: [],
      addOrUpdateTag: null,
      visible: false,
      loading: false,
      target: {},
      rules: {},
      oldValue: {},
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
      }
    }
  },
  components: {
    dateTime: dateTime
  },
  methods: {
    show: function (fields, addOrUpdateTag) {
      this.loading = false
      this.visible = true
      this.addOrUpdateTag = addOrUpdateTag
      this.fields = fields
      this.initTarget()
    },
    initTarget: function () {
      this.target = {}
      let result = {}
      for (let i = 0; i < this.fields.length; i++) {
        let field = this.fields[i]
        result[field.name] = field.type === 'number' ? Number(field.value) : field.value
      }
      this.$nextTick(() => {
        this.target = result
        this.oldValue = util.deepCopy(this.target)
        this.initRules()
      })
    },
    // 下拉框数据改变，目的是联动
    getSelectItem () {
      this.$emit('getSelectItem', this.target)
    },
    changeSelectChoose (index, data, key, value) {
      this.fields[index].choose = data
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
        if (this.fields[i].rules) {
          result[this.fields[i].name] = this.fields[i].rules
        }
      }
      this.rules = result
    },
    handleSubmit: function () {
      if (this.addOrUpdateTag === 'add') {
        this.$refs['commonForm'].validate((valid) => {
          if (valid) {
            this.$confirm('确认提交吗？', '提示', {}).then(() => {
              this.loading = true
              // alert(JSON.stringify(this.target))
              let para = this.target
              this.to(para).then((res) => {
                this.loading = false
                this.$notify(util.notifyBody(res.success, res.message))
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
      }
      if (this.addOrUpdateTag === 'edit') {
        this.$refs['commonForm'].validate((valid) => {
          if (valid) {
            this.$confirm('确认提交吗？', '提示', {}).then(() => {
              this.loading = true
              // alert(JSON.stringify(this.target))
              let para = this.target
              this.to(para).then((res) => {
                this.loading = false
                this.$notify(util.notifyBody(res.success, res.message))
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
      }
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
    },
    handleBeforeClose (done) {
      done()
    },
    handleClose () {
      this.$refs['commonForm'].resetFields()
      // 暂时先这样子处理，牺牲下性能
      // this.target = {}
    }
  }
}
</script>
<style>
.selectA {
  width: 100%;
}
/*空白会被浏览器识别*/
.el-select-dropdown__item span {
  white-space: pre;
}
</style>
