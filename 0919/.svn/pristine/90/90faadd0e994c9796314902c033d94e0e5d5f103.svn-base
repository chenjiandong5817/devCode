/*
 * @Author: chenyang
 * @Date: 2017-11-06 15:47:46
 * @Last Modified by: chenyang
 * @Last Modified time: 2017-11-21 17:21:53
 * 播放列表多选添加界面
 */
<template>
  <el-dialog v-model="visible" :before-close="handleClose" size="small" :close-on-click-modal="true">
  <el-form ref="displayMuilt" :rules="rules1" :model="displayData">
      <el-card class="box-card maxWidth">
        <el-row>
          <el-col>
            <el-tag type="danger">批量添加播放列表信息</el-tag>
          </el-col>
        </el-row>
        </br>
        <el-form-item label="模板" prop="templateReq">
            <el-select v-model="displayData.temId" placeholder="请选择模板" :filterable="true" class="maxWidth">
              <el-option v-for="mode in templatesLabel" :key="mode.value" :label="mode.text" :value="mode.value">
              </el-option>
            </el-select>
          </el-form-item>
        <el-form-item label="时间间隔">
          <el-input-number v-model="displayData.timeSpan" :min="0" label="时间间隔" class="maxWidth"></el-input-number>
        </el-form-item>
        <el-form-item label="开始时间" prop="startTime">
            <el-input v-model="displayData.begin" placeholder="添加开始时间" type="textarea"></el-input>
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
            <el-input v-model="displayData.end" placeholder="添加结束时间" type="textarea"></el-input>
        </el-form-item>
        <el-form-item prop="deviceChooseRule">
          <span>选择在 {{airportCode}} 下的设备</span>
          <el-select v-model="displayData.deviceId" placeholder="选择设备添加该模板,可多次选择" class="maxWidth" :filterable="true">
            <el-option v-for="sub in deviceLabel" :key="sub.value" :label="sub.text" :value="sub.value" @click.native="insertTag">
              <span style="float: left">{{ sub.left }}</span>
              <span style="float: right;">{{ sub.ip }}</span>
            </el-option>
          </el-select>
        </el-form-item>
        </br>
        <i class="el-icon-star-on" style="margin-right: 5px; color:#CC0000"></i>
        <i>需要添加该模板的设备如下</i>
        <el-form-item>
          </br>
          <el-tag
            class="margin"
            :disable-transitions = "true"
            v-for="tag in tags"
            :key="tag.name"
            closable
            :color="tag.type"
            :hit=true
            @close="tagClose(tag)">
            {{tag.name}}
          </el-tag>
        </el-form-item>
    </el-card>
    </br>
    </div>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click.native="visible = false">取消</el-button>
      <el-button @click.native="reset()">重置</el-button>
      <el-button type="primary" @click.native="submit('displayMuilt')">提交</el-button>
    </div>
    </el-dialog>
</template>

<script>
import API from '../../api'
import util from '../../common/js/util'
export default {
  props: {
    callback: {
      type: Function,
      default: function () { }
    }
  },
  data () {
    var validateValue = (rule, value, callback) => {
        if (this.displayData.begin === '') {
          callback(new Error('选择时间不为空'))
        } else {
          if (!(this.displayData.begin.length === 4)) {
            callback(new Error('输入的值必须在4位之间'))
          }
          if (this.displayData.begin < 0) {
            callback(new Error('值必须大于等于0'))
          }
          if (!(this.displayData.begin % 100 >= 0 && this.displayData.begin % 100 <= 59 && Math.floor(this.displayData.begin / 100) >= 0 && Math.floor(this.displayData.begin / 100) <= 24)) {
            callback(new Error('前两个数字组合应在[00,24]范围内,后两个数字组合应在[00,59]范围内'))
          }
        }
        callback()
    }
    var validateValue1 = (rule, value, callback) => {
        if (this.displayData.end === '') {
          callback(new Error('选择时间不为空'))
        } else {
          if (!(this.displayData.end.length === 4)) {
            callback(new Error('输入的值必须在4位之间'))
          }
          if (this.displayData.end < 0) {
            callback(new Error('值必须大于等于0'))
          }
          if (!(this.displayData.end % 100 >= 0 && this.displayData.end % 100 <= 59 && Math.floor(this.displayData.end / 100) >= 0 && Math.floor(this.displayData.end / 100) <= 24)) {
            callback(new Error('前两个数字组合应在[00,24]范围内,后两个数字组合应在[00,59]范围内'))
          }
        }
        callback()
    }
    var templateReqRule = (rule, value, callback) => {
      if (this.displayData.temId !== '') {
        callback()
      } else {
        callback(new Error('选择模板不为空'))
      }
    }
    var deviceChoose = (rule, value, callback) => {
      if (this.displayData.deviceId !== '') {
        callback()
      } else {
        callback(new Error('选择设备不为空'))
      }
    }
    return {
      tags: [
        {name: '', type: ''}
      ],
      rules1: {
        templateReq: [{ required: true, trigger: 'change', validator: templateReqRule }],
        startTime: [ {validator: validateValue, trigger: 'blur', required: true} ],
        endTime: [ {validator: validateValue1, trigger: 'blur', required: true} ],
        deviceChooseRule: [{validator: deviceChoose, trigger: 'change', required: true}]
      },
      ipChangeIdMap: {},
      displayList: [],
      isAddMap: {},
      deviceIdAndNameMap: {},
      deviceIdAndIpMap: {},
      startTime: '',
      endTime: '',
      uploadSuccess: true,
      deviceLabel: [],
      device: [],
      templatesLabel: [],
      templates: [],
      visible: false,
      index: 0,
      airportCode: '',
      // 第一个存放的是根据deviceIp存放的id(device)
      // 第二个模板描述和id(templates)
      displayData: {deviceId: '', temId: '', timeSpan: 0, begin: 0, end: 0},
      displayCount: 1   // 当前添加的个数
    }
  },
  watch: {
    '$store.state.cached' (val, oldVal) {
      this.getInfoList()
    }
  },
  methods: {
    tagClose (tag) {
      this.tags.splice(this.tags.indexOf(tag), 1)
      this.isAddMap.set(this.displayData.deviceId, 'false')
      if (this.tags.length === 0) {
        this.displayData.deviceId = ''
      }
    },
    insertTag () {
      if (this.isAddMap.get(this.displayData.deviceId) === 'false') {
        this.isAddMap.set(this.displayData.deviceId, 'true')  // 已经添加过了
        let label = {}
        let IpName = this.deviceIdAndNameMap.get(this.displayData.deviceId) + ' ' + '(' + this.deviceIdAndIpMap.get(this.displayData.deviceId) + ')'
        if (this.tags.length % 4 === 0) {
          label = {name: IpName, type: '#e67e22'}
        } else if (this.tags.length % 4 === 1) {
          label = {name: IpName, type: '#3498db'}
        } else if (this.tags.length % 4 === 2) {
          label = {name: IpName, type: '#1abc9c'}
        } else if (this.tags.length % 4 === 3) {
          label = {name: IpName, type: '#8e44ad'}
        }
        this.tags.push(label)
        // this.displayData.deviceId = ''
      } else {
        this.$notify({
          title: '该设备已经存在',
          message: '该设备已经存在于列表之中',
          type: 'warning'
        })
      }
    },
    reset () {
      this.tags = []  // 重置标签
      this.displayData = {deviceId: '', temId: '', timeSpan: 0, begin: 0, end: 0}
      for (var [key] of this.isAddMap) {
        // console.log(key)
        this.isAddMap.set(key, 'false')
      }
    },
    dealWith (names) {
      let str = ''
      for (let i = names.length - 2; i > 0; i--) {
        if (names[i] === '(') {
          break
        }
        str += names[i]
      }
      return str.split('').reverse().join('')
    },
    submit (formName) {
      // this.listLoading = true
      console.log('进来了')
      this.$refs[formName].validate((valid) => {
        console.log(valid)
        if (valid === false) {
          console.log('error submit!!')
          return false
        }
        console.log('合法')
          let ipList = []
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
          for (let i = 0; i < this.tags.length; i++) {
            // displayData: {deviceId: '', temId: '', timeSpan: 0, begin: 0, end: 0},
            let label = {deviceId: this.ipChangeIdMap.get(this.dealWith(this.tags[i].name)), temId: this.displayData.temId, timeSpan: this.displayData.timeSpan, begin: this.displayData.begin, end: this.displayData.end}
            ipList.push(this.dealWith(this.tags[i].name))
            let para = { newValue: label }
            API.addDeviceConfig().go(para).then((data) => {   // 请求所有的数据
              if (data.ok) {
              } else {
                this.$notify(util.notifyBody(false, data.msg))
                this.uploadSuccess = false
              }
            })
            if (this.uploadSuccess === false) {
              break
            }
          }
          if (this.uploadSuccess === true) {
            this.$notify({
              title: '成功',
              message: '上传成功',
              type: 'success'
            })
            this.handleClose()
            var et = this
            window.setTimeout(function () {
              et.$emit('sendToDevice', ipList)
            }, 2000)
            this.callback()
          } else {
            this.$notify({
              title: '失败',
              message: '上传失败',
              type: 'success'
            })
            this.handleClose()
            this.callback()
          }
        })
      })
    },
    handleClose () {
      this.displayData = {deviceId: '', temId: '', timeSpan: 0, begin: 0, end: 0}
      this.visible = false
    },
    setData () {
      this.deviceIdAndIpMap = {}
      this.deviceIdAndIpMap = new Map()
      this.deviceLabel = []
      this.templatesLabel = []
      for (let i = 0; i < this.device.length; i++) {
        if (this.device[i].airportCode !== this.airportCode) {
          continue
        }
        let label = {text: this.device[i].deviceName + ' ' + '(' + this.device[i].deviceIp + ')', value: this.device[i].id, ip: this.device[i].deviceIp, left: this.device[i].deviceName}
        this.deviceLabel.push(label)
        this.deviceIdAndIpMap.set(this.device[i].id, this.device[i].deviceIp)
        this.deviceIdAndNameMap.set(this.device[i].id, this.device[i].deviceName)
        this.ipChangeIdMap.set(this.device[i].deviceIp, this.device[i].id)
        this.isAddMap.set(this.device[i].id, 'false')
      }
      for (let i = 0; i < this.templates.length; i++) {
        let label = {text: this.templates[i].description, value: this.templates[i].id}
        this.templatesLabel.push(label)
      }
    },
    getInfoList () {
      this.device = this.$cache.fetch('deviceInfo') || []
      this.templates = this.$cache.fetch('templates') || []
    },
    show (airport) {
      this.airportCode = airport
      this.deviceIdAndNameMap = {}
      this.deviceIdAndNameMap = new Map()
      this.ipChangeIdMap = {}
      this.ipChangeIdMap = new Map()
      this.isAddMap = {}
      this.isAddMap = new Map()
      this.tags = []
      this.uploadSuccess = true
      this.getInfoList()
      this.setData()
      this.visible = true
    }
  }
}
</script>

<style>
.maxWidth {
    width: 100% !important;
}
.margin {
  margin-right: 15px;
}
</style>
