/*
 * @Author: chenyang
 * @Date: 2017-08-07 17:13:04
 * @Last Modified by: chenyang
 * @Last Modified time: 2017-09-19 17:06:07
 * @Description: 滚动文本设置
 */
<template>
  <div>
    <el-dialog :title="title" v-model="visible" :close-on-click-modal="true" :before-close="handleClose" size="small" :modal-append-to-body="false">
      <el-input type="textarea" :rows="2" placeholder="请输入滚动文本" v-model="textarea" ref="inputText" :maxlength="370"></el-input>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="handleClose">取消</el-button>
        <el-button @click="reset('inputText')">重置</el-button>
        <el-button type="primary" @click.native="handleSubmit" :loading="loading">提交</el-button>
      </div>
    </el-dialog>
    <el-dialog :title="title" v-model="batchVisible" :close-on-click-modal="true" :before-close="batchClose" :modal="false" size="small" :modal-append-to-body="false">
      <el-input type="textarea" :rows="2" placeholder="请输入批量设置的滚动文本" v-model="batchTextarea" ref="batchSetting" :maxlength="370"></el-input>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="batchClose">取消</el-button>
        <el-button @click="batchReset('batchSetting')">重置</el-button>
        <el-button type="primary" @click.native="batchSettingSubmit" :loading="batchLoading">提交</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import Util from '../../../common/js/util'
import API from '../../../api'
export default {
  props: {
    title: {
      type: String,
      default: '滚动文本设置'
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
      batchVisible: false,
      deviceInfos: [],
      textarea: '',
      batchTextarea: '',
      loading: false,
      batchLoading: false,
      batchList: [],
      deviceInfo: { id: '', parentId: '', deviceNo: '', deviceName: '', deviceModel: '', deviceOrigin: null, deviceFactory: null, deviceIp: '', displayType: '', deviceStatus: 0, deviceDes: null, deviceCom: '', groupId: 0, linkResId: 0, hostName: null, macAddr: null, osName: null, cpuDes: null, memoryDes: null, hdDes: null, adminName: null, adminPwd: null, vncPwd: '', terminal: '', scrollText: '', remark: '', lastUpdate: null, groupSort: 0, airportCode: null, dynamicScrollTextSourceId: 0, isAdd: 0, isShowPage: 0, pageSizeC: 0, vncPort: 0, resolutionX: 0, resolutionY: 0, backupScrollText: null, notChangePage: '', screenOpenTime: null, screenCloseTime: null, area: null, openComNo: null, closeComNo: null, comCommandType: '', terminalLevel: null, linkResType: '' },
      deviceInfoSec: { id: '', parentId: '', deviceNo: '', deviceName: '', deviceModel: '', deviceOrigin: null, deviceFactory: null, deviceIp: '', displayType: '', deviceStatus: 0, deviceDes: null, deviceCom: '', groupId: 0, linkResId: 0, hostName: null, macAddr: null, osName: null, cpuDes: null, memoryDes: null, hdDes: null, adminName: null, adminPwd: null, vncPwd: '', terminal: '', scrollText: '', remark: '', lastUpdate: null, groupSort: 0, airportCode: null, dynamicScrollTextSourceId: 0, isAdd: 0, isShowPage: 0, pageSizeC: 0, vncPort: 0, resolutionX: 0, resolutionY: 0, backupScrollText: null, notChangePage: '', screenOpenTime: null, screenCloseTime: null, area: null, openComNo: null, closeComNo: null, comCommandType: '', terminalLevel: null, linkResType: '' },
      oldValue: {}
    }
  },
  methods: {
    handleClose () {
       this.visible = false
    },
    batchClose () {
      this.batchVisible = false
    },
    // show () {
    //   this.visible = true
    // },
    batchSetting (listInfo, devices) {
      // this.getDeviceAllInfo()
      this.deviceInfos = []
      this.visible = false
      this.batchVisible = true
      this.batchList = listInfo
      for (let i = 0; i < this.batchList.length; i++) {
        // console.log(devices[this.batchList[i]])
        this.deviceInfos.push(devices[this.batchList[i]])
      }
    },
    setting (index, row) {
      this.textarea = row.scrollText   // 按设置的时候显示当前的滚动文本方便修改
      this.visible = true
      this.batchVisible = false
      if (row != null) {
        this.deviceInfo = row
        this.oldValue = Object.assign({}, row)
      }
      // alert('你好阿')
    },
    reset (Name) {
      // this.$refs[Name].resetFields()
      this.textarea = ''
    },
    batchReset (Name) {
      this.batchTextarea = ''
    },
    handleSubmit () {
      this.$confirm('确认提交吗？', '提示', {}).then(() => {
        this.loading = true
        this.deviceInfo.scrollText = this.textarea
        let para = { newValue: this.deviceInfo, oldValue: this.oldValue }
        this.to(para).then((res) => {
          this.$notify(Util.notifyBody(res.ok, res.msg))
          this.visible = false
          this.textarea = ''
          this.deviceInfo = { id: '', parentId: '', deviceNo: '', deviceName: '', deviceModel: '', deviceOrigin: null, deviceFactory: null, deviceIp: '', displayType: '', deviceStatus: 0, deviceDes: null, deviceCom: '', groupId: 0, linkResId: 0, hostName: null, macAddr: null, osName: null, cpuDes: null, memoryDes: null, hdDes: null, adminName: null, adminPwd: null, vncPwd: '', terminal: '', scrollText: '', remark: '', lastUpdate: null, groupSort: 0, airportCode: null, dynamicScrollTextSourceId: 0, isAdd: 0, isShowPage: 0, pageSizeC: 0, vncPort: 0, resolutionX: 0, resolutionY: 0, backupScrollText: null, notChangePage: '', screenOpenTime: null, screenCloseTime: null, area: null, openComNo: null, closeComNo: null, comCommandType: '', terminalLevel: null, linkResType: '' }
          this.getDeviceAllInfo()
          this.loading = false
        })
      }).catch(() => {
        this.textarea = ''
        this.$message({
          type: 'info',
          message: '已取消提交'
        })
      })
    },
    getDeviceAllInfo () {  // 获取设备全部信息
        let para = Object.assign({}, this.filters)
        API.getDeviceInfoAll().go(para).then((data) => {   // 请求所有的数据
          if (data.ok) {
            this.deviceInfos = data.attr.data.list
          } else {
            this.$notify(Util.notifyBody(false, data.msg))
          }
        })
    },
    batchSettingSubmit () {
      this.$confirm('确认提交吗？', '提示', {}).then(() => {
        this.batchLoading = true
        for (let i = 0; i < this.deviceInfos.length; i++) {
          this.deviceInfoSec = { id: '', parentId: '', deviceNo: '', deviceName: '', deviceModel: '', deviceOrigin: null, deviceFactory: null, deviceIp: '', displayType: '', deviceStatus: 0, deviceDes: null, deviceCom: '', groupId: 0, linkResId: 0, hostName: null, macAddr: null, osName: null, cpuDes: null, memoryDes: null, hdDes: null, adminName: null, adminPwd: null, vncPwd: '', terminal: '', scrollText: '', remark: '', lastUpdate: null, groupSort: 0, airportCode: null, dynamicScrollTextSourceId: 0, isAdd: 0, isShowPage: 0, pageSizeC: 0, vncPort: 0, resolutionX: 0, resolutionY: 0, backupScrollText: null, notChangePage: '', screenOpenTime: null, screenCloseTime: null, area: null, openComNo: null, closeComNo: null, comCommandType: '', terminalLevel: null, linkResType: '' }
          this.deviceInfoSec = this.deviceInfos[i]
          this.oldValue = this.deviceInfos[i]
          this.deviceInfoSec.scrollText = this.batchTextarea
          // console.log(this.deviceInfoSec.backupScrollText)
          let para = { newValue: this.deviceInfoSec, oldValue: this.oldValue }
          API.editDeviceInfo().go(para).then((res) => {
          })
        }
        this.$message({
          type: 'success',
          message: '批量设置成功'
        })
        this.batchVisible = false
        this.batchTextarea = ''
        this.getDeviceAllInfo()
        this.batchLoading = false
      }).catch(() => {
        this.batchTextarea = ''
        this.$message({
          type: 'info',
          message: '已取消提交'
        })
      })
    }
  }
}
</script>

<style>

</style>
