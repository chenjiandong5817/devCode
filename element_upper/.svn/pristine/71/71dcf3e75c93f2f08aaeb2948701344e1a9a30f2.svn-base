/*
 * @Author: chenyang
 * @Date: 2017-08-08 09:59:01
 * @Last Modified by: chenyang
 * @Last Modified time: 2017-09-19 17:05:52
 * @Description: 批量文本设置、备份、还原、清空
 */
<template>
  <el-dialog :title="title" v-model="visible" :close-on-click-modal="true" :before-close="handleClose" size="large">
    <el-transfer
      v-model="deviceData"
      filterable
      :render-content="renderFunc"
      :titles="['批量选择', '批量处理']"
      :button-texts="['批量移除', '批量处理']"
      :footer-format="{
        noChecked: '${total}' + '个',
        hasChecked: '${checked}/${total}' + '个'
      }"
      @change="handleChange"
      :data="data">
      <!-- <el-button class="transfer-footer" slot="left-footer" size="small">点击事件</el-button> -->
    </el-transfer>
    <div class="buttonPosition">
      <el-button size="small" type="danger" @click="clearText">清空文本</el-button>
      <el-button size="small" type="info" @click="setText">设置文本</el-button>
      <el-button size="small" type="success" @click="backupText">备份文本</el-button>
      <el-button size="small" type="warning" @click="restoreText">还原文本</el-button>
    </div>
    <!--滚动文本-->
    <setting-scroll-text
      ref="inputForm">
    </setting-scroll-text>
  </el-dialog>
</template>

<script>
import API from '../../../api'
import settingScrollText from '../deviceForm/SettingScrollText'
import Util from '../../../common/js/util'
export default {
  props: {
    title: {
      type: String,
      default: '滚动文本批量设置'
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
      deviceInfos: [],
      deviceInfo: { id: '', parentId: '', deviceNo: '', deviceName: '', deviceModel: '', deviceOrigin: null, deviceFactory: null, deviceIp: '', displayType: '', deviceStatus: 0, deviceDes: null, deviceCom: '', groupId: 0, linkResId: 0, hostName: null, macAddr: null, osName: null, cpuDes: null, memoryDes: null, hdDes: null, adminName: null, adminPwd: null, vncPwd: '', terminal: '', scrollText: '', remark: '', lastUpdate: null, groupSort: 0, airportCode: null, dynamicScrollTextSourceId: 0, isAdd: 0, isShowPage: 0, pageSizeC: 0, vncPort: 0, resolutionX: 0, resolutionY: 0, backupScrollText: null, notChangePage: '', screenOpenTime: null, screenCloseTime: null, area: null, openComNo: null, closeComNo: null, comCommandType: '', terminalLevel: null, linkResType: '' },
      oldValue: {},
      rightIndex: [], // 进行数组下标的记录
      data: [],
      deviceData: [],
      renderFunc (h, option) {
        return h(
          'span',
          {},
          [option.label]
        )
      }
    }
  },
  components: {
    settingScrollText: settingScrollText
  },
  methods: {
    clearText () {
      let para = Object.assign({}, this.filters)
        API.getDeviceInfo().go(para).then((data) => {
          if (data.ok) {
            this.deviceInfos = data.attr.data.list
            this.$confirm('是否清空目前批量处理框中的滚动文本?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
              // this.updateData()
              this.listLoading = true
              for (let i = 0; i < this.rightIndex.length; i++) {
                let index = this.rightIndex[i] // 记录数组下标，在原来的设备信息数组中
                let device = this.deviceInfos[index] // 对应的元素
                this.oldValue = Object.assign({}, device)
                // this.oldValue = device
                this.deviceInfo = device
                this.deviceInfo.scrollText = ''  // 清空滚动文本
                let para = { newValue: this.deviceInfo, oldValue: this.oldValue }
                API.editDeviceInfo().go(para).then((data) => {

                })
              }
              this.listLoading = false
              this.deviceInfos = []
              this.$message({
                type: 'success',
                message: '批量清空成功'
              })
           }).catch(() => {
            this.$message({
              type: 'info',
              message: '取消批量清空'
            })
          })
          } else {
            console.log('失败了')
            this.deviceInfos = data.attr.data.list
            this.$notify(Util.notifyBody(false, data.msg))
          }
        })
    },
    setText () {
      // this.$refs['inputForm'].show()
      let para = Object.assign({}, this.filters)
        API.getDeviceInfo().go(para).then((data) => {
          if (data.ok) {
            this.deviceInfo = {}
            this.deviceInfos = data.attr.data.list
            // alert(JSON.stringify(this.deviceinfos))
            this.$refs['inputForm'].batchSetting(this.rightIndex, this.deviceInfos)
            this.deviceInfos = []
          } else {
            this.deviceInfos = data.attr.data.list
            this.$notify(Util.notifyBody(false, data.msg))
          }
      })
    },
    backupText () {
      let para = Object.assign({}, this.filters)
      API.getDeviceInfoAll().go(para).then((data) => {   // 更新数据
        if (data.ok) {
          this.deviceInfo = {}
          this.deviceInfos = data.attr.data.list
          this.$confirm('是否批量备份目前存在于批量处理框中的滚动文本?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
          }).then(() => {
              // this.updateData()
            for (let i = 0; i < this.rightIndex.length; i++) {
              let index = this.rightIndex[i] // 记录数组下标，在原来的设备信息数组中
              // console.log(index)
              let device = this.deviceInfos[index] // 对应的元素
              // console.log(device)
              this.oldValue = Object.assign({}, device)
              // this.oldValue = device
              this.deviceInfo = device
              let temp = this.deviceInfo.scrollText
              this.deviceInfo.backupScrollText = temp  // 备份
              let para = { newValue: this.deviceInfo, oldValue: this.oldValue }
              this.listLoading = true
              API.editDeviceInfo().go(para).then((data) => {

              })
            }
            this.deviceInfos = []
            this.$message({
              type: 'success',
              message: '批量备份成功'
            })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '取消批量备份'
          })
        })
        } else {
          console.log('失败了')
          this.deviceInfos = data.attr.data.list
          this.$notify(Util.notifyBody(false, data.msg))
        }
      })
    },
    restoreText () {
       let para = Object.assign({}, this.filters)
        API.getDeviceInfoAll().go(para).then((data) => {
          if (data.ok) {
            this.deviceInfo = {}
            this.deviceInfos = data.attr.data.list
            this.$confirm('是否批量还原处理框中的滚动文本?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
              // this.updateData()
              for (let i = 0; i < this.rightIndex.length; i++) {
                let index = this.rightIndex[i] // 记录数组下标，在原来的设备信息数组中
                let device = this.deviceInfos[index] // 对应的元素
                this.oldValue = Object.assign({}, device)
                // this.oldValue = device
                this.deviceInfo = device
                let temp = this.deviceInfo.backupScrollText
                this.deviceInfo.scrollText = temp // 还原滚动文本
                let para = { newValue: this.deviceInfo, oldValue: this.oldValue }
                this.listLoading = true
                API.editDeviceInfo().go(para).then((data) => {

                })
              }
              this.deviceInfos = []
              this.$message({
                type: 'success',
                message: '批量还原成功'
              })
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '取消批量还原'
            })
          })
            // alert(JSON.stringify(this.deviceinfos))
          } else {
            console.log('失败了')
            this.deviceInfos = data.attr.data.list
            this.$notify(Util.notifyBody(false, data.msg))
          }
        })
    },
    batchDevice (deviceInfoList) {   // 每次做完更新这个?
      // console.log(deviceInfoList)
      this.data = []
      this.deviceInfos = deviceInfoList
      for (let i = 0; i < this.deviceInfos.length; i++) {
        this.data.push({
          key: i,
          label: this.deviceInfos[i].deviceNo + '-' + this.deviceInfos[i].deviceIp
        })
      }
      this.visible = true  // 显示
    },
    handleChange (value, direction, movedKeys) {  // 在里面进行处理
      // console.log(value)
      this.rightIndex = []
      for (let i = 0; i < value.length; i++) {  // value[i] 代表数组的下标
        // console.log(value[i])
        this.rightIndex.push(value[i])
      }
    },
    handleClose: function (params) {
      this.visible = false
      this.rightIndex = []
      this.deviceInfo = {}
      this.deviceData = []
      this.callback()
    }
  }
}
</script>

<style>
  .transfer-footer {
    margin-left: 30px;
    padding: 6px 5px;
  }
  .buttonPosition {
    margin-top: 20px;
  }
  .el-transfer-panel {
    border: 1px solid #d1dbe5;
    box-shadow: 0 2px 4px rgba(0,0,0,.12), 0 0 6px rgba(0,0,0,.04);
    display: inline-block;
    width: 40% !important;
    position: absolute;
}
</style>
