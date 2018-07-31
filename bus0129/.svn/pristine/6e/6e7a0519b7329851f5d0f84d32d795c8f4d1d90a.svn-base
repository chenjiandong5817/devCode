/*
 * @Author: ylj
 * @Date: 2018-02-23 22:09:03
 * @Last Modified by: ylj
 * @Last Modified time: 2018-03-07 11:18:37
 */
<template>
<div class="devIrrClss">
<div :class="devIrrClssName">
  <el-dialog :title="title" v-model="visible" :close-on-click-modal="true" :modal="false" :before-close="handleClose" size="small" :modal-append-to-body="false" @open="bindData">
    <div class="borderClss">
    <el-form class="formClss" :model="deviceIrrInfo" ref="devIrrAddOrUpdateForm" :rules="rule">
      <el-row :gutter="20" v-if="opt === 'add'">
        <el-col :span="6">
          <!--<el-form-item label="" prop="registerModel">-->
            <label>批量登记</label>
            <el-switch
              v-model="registerModel"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-text="单个登记"
              inactive-text="批量登记">
            </el-switch>
          <!--</el-form-item>-->
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="6">
          <el-form-item label="机场代码" prop="airportCode">
            <city-name
              style="width: 100%;"
              ref="airCity"
              v-on:getAirportName = "setAirportCode"
              :disabled="editDisable">
            </city-name>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="航站楼" prop="terminal">
            <el-select v-model="deviceIrrInfo.terminal" @change="setLinkDevList" filterable placeholder="航站楼" clearable>
              <el-option
                v-for="item in terminalList"
                :key="item.id"
                :label="item.terminalCode"
                :value="item.terminalCode">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="区域" prop="deviceStatus">
            <!--<el-select v-model="deviceIrrInfo.area" placeholder="区域" @change="setLinkDevList" clearable>
              <el-option
                v-for="item in deviceStatusList"
                :key="item.id"
                :label="item.displayValue"
                :value="parseInt(item.enumNo)">
              </el-option>
            </el-select>-->
            <el-input v-model="deviceIrrInfo.area" placeholder="区域" @change="setLinkDevList"  type="text"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="6" v-if="!registerModel">
          <el-form-item label="设备IP" prop="deviceId">
            <!--<el-input v-model="deviceIrrInfo.deviceIp" placeholder="设备IP"></el-input>-->
            <el-select v-model="deviceIrrInfo.deviceId" placeholder="设备IP" filterable clearable>
              <el-option
                v-for="item in deviceList"
                :key="item.id"
                :label="item.deviceIp + '(' + item.deviceName + ')'"
                :value="item.id">
                <span style="float: left">{{ item.deviceIp }}</span>
                <span style="float: right">{{ item.deviceName }}</span>
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20" v-if="registerModel">
        <el-col :span="24">
          <el-transfer
            v-model="deviceDatas"
            filterable
            filter-placeholder="请输入设备信息(编号或IP)"
            :render-content="renderFunc"
            :titles="['设备批量选择', '设备批量处理']"
            :button-texts="['移除', '添加']"
            :footer-format="{
              noChecked: '${total}' + '个',
              hasChecked: '${checked}/${total}' + '个'
            }"
            :data="deviceTranList">
          </el-transfer>
        </el-col>
      </el-row>

      <el-row :gutter="10">
        <el-col :span="6">
          <el-form-item label="报修人员" prop="reportUser">
            <el-input v-model="deviceIrrInfo.reportUser" placeholder="报修人员"  type="text"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="异常状态" prop="deviceIrrStatus">
            <el-select v-model="deviceIrrInfo.deviceIrrStatus" placeholder="异常状态" width="100%" clearable>
              <el-option
                v-for="item in deviceStatusList"
                :key="item.id"
                :label="item.displayValue"
                :value="parseInt(item.enumNo)">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="报修时间" prop="reportingTime">
            <br/>
            <date-time v-model="deviceIrrInfo.reportingTime" :visitTime="true" dateStyle="width:65%" timeStyle="width:32%" datePlaceholder="异常时间" timePlaceholder="时间" formatter="yyyy-MM-dd" :allDisable="false"></date-time>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="24">
          <el-form-item label="设备异常描述" prop="irrDescription">
            <br>
            <el-input v-model="deviceIrrInfo.irrDescription" placeholder="设备异常描述"  type="textarea"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <hr/>
      <br/>
      <dev-repair-record-div ref="devRepairForm"></dev-repair-record-div>

    </el-form>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button @click.native="handleClose()">取消</el-button>
      <el-button @click="resetForm('devIrrAddOrUpdateForm')">重置</el-button>
      <el-button type="primary" @click.native="handleSubmit" :loading="loading">提交</el-button>
    </div>
  </el-dialog>
</div>
</div>
</template>

<script>
 import Util from '../../../common/js/util'
 import Butil from '../../../common/js/base-util'
 import API from '../../../api'
 import Rules from '../../../common/js/rules'
 import DateTime from '../../../components/DateTime'
 import CityName from '../../../components/CityName'
 import DevRepairRecordDiv from '../deviceRepairRecord/devRepairRecordForm'

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
       opt: 'add',
       API: API,
       Rules: Rules,
       devIrrClssName: '',
       registerModel: false,
       sysUserId: null,
       sysUserName: null,
       editDisable: false,
       deviceIrrInfo: {
         id: '',
         deviceId: '',
         deviceNo: '',
         deviceIp: '',
         deviceIrrStatus: null,
         airportCode: '',
         displayType: '',
         terminal: '',
         area: '',
         reportUserid: '',
         reportUser: '',
         reportingTime: Util.formatDate.format(new Date(), 'yyyy-MM-dd hh:mm:ss'),
         irrDescription: ''
       },
       oldValue: {},
       form: {
         devRepairRecords: [],
         oldDevRepairRecords: []
       },
       allDeviceList: [],
       allDeviceObjList: {},
       deviceList: [],
       deviceTranList: [],
       deviceDatas: [],
       userList: [],
       deviceStatusList: [],
       terminalList: this.$cache.fetch('terminals'),
       terminalRes: this.$cache.fetch('terminals'),
       rule: {
         reportingTime: [{ validator: Rules.validSegTimeType, message: '非法时间格式', trigger: 'blur' }],
         deviceIrrStatus: [{ type: 'number', required: true, message: '请选择设备异常状态', trigger: 'blur' }]
       },
       visible: false,
       loading: false
      }
    },
    components: {
      API: API,
      dateTime: DateTime,
      cityName: CityName,
      devRepairRecordDiv: DevRepairRecordDiv
    },
    methods: {
     setFormClassName () {
      let isMinWidth = (document.body.clientWidth <= 1366)
      this.devIrrClssName = isMinWidth ? 'largeDigCls' : 'smallDigCls'
     },
     bindData () {
      this.setFormClassName()
      this.getDeviceStatusList()
     },
     getDeviceStatusList () {
        // 获取设备异常类型列表
        let para = {enumType: 'DEVICESTATUS'}
        API.getEnumInfo().go(para).then((data) => {
          if (data.ok) {
            this.deviceStatusList = data.attr.data.list
          } else {
            this.$notify(Util.notifyBody(false, data.msg))
          }
          this.loading = false
        })
     },
     show (row, airportCode) {
      this.visible = true
      this.showAirportInfo(this.subAirportLs)
      if (Util.isNotNull(airportCode)) {
        this.setLinkByAirport(airportCode)
      } else {
        this.setLinkByAirport(row.airportCode)
      }
      if (row !== null && row !== undefined) {
        this.opt = 'edit'
        this.deviceIrrInfo = Util.deepCopy(row)
        // 获取设备信息列表
        this.getDeviceAllInfo()
        // this.deviceInfo = row
        this.oldValue = Object.assign({}, row)
        let para = { irrId: row.id }
        this.loading = true
        // 获取设备维修记录 后端对接
        API.getDeviceIrrInfos().go(para).then((data) => {
          if (data.ok) {
            this.form.devRepairRecords = data.attr.data.deviceRepairRecords
            this.form.oldDevRepairRecords = data.attr.data.deviceRepairRecords
            this.$nextTick(() => {
              this.$refs['devRepairForm'].show(this.form.devRepairRecords)
            })
          } else {
            this.$notify(Util.notifyBody(false, data.msg))
          }
          this.visible = true
          this.loading = false
        })
        // this.form.devRepairRecords = this.deviceIrrInfo.deviceRepairRecords
        // this.form.oldDevRepairRecords = this.deviceIrrInfo.deviceRepairRecords
        // this.$nextTick(() => {
        //   this.$refs['devRepairForm'].show(this.form.devRepairRecords)
        // })
      } else {
        this.opt = 'add'
        this.deviceIrrInfo.airportCode = airportCode
        this.deviceIrrInfo.reportUserid = this.sysUserId
        this.deviceIrrInfo.reportUser = this.sysUserName
        this.getDeviceAllInfo()
        this.setAirportInfo(airportCode)
        this.$nextTick(() => {
          this.$refs['devRepairForm'].show([])
        })
      }
     },
     setLinkByAirport (airportCode) {
        let list = this.terminalRes.filter(item => {
          return item.airportCode === airportCode
        })
        this.terminalList = list
     },
     handleClose (params) {
      this.visible = false
      // this.$refs['devIrrAddOrUpdateForm'].resetFields()
      this.initData()
     },
     initData () {
      this.deviceIrrInfo = {
         id: '',
         deviceId: '',
         deviceNo: '',
         deviceIp: '',
         deviceStatus: null,
         deviceIrrStatus: null,
         airportCode: '',
         displayType: '',
         terminal: '',
         area: '',
         reportUserid: '',
         reportUser: '',
         reportingTime: '',
         irrDescription: ''
      }
      this.registerModel = false
      this.oldValue = []
      this.form.devRepairRecords = []
      this.form.oldDevRepairRecords = []
     },
     handleSubmit (params) {
       // 单条新增或者批量新增
       this.$refs['devIrrAddOrUpdateForm'].validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.loading = true
            let devIrrlist = []
            let selDeviceList = []
            let devRepairParam = {}
            selDeviceList[0] = this.deviceIrrInfo.deviceId
            selDeviceList = (this.registerModel ? this.deviceDatas : selDeviceList)
            for (var i = 0; i < selDeviceList.length; i++) {
              let devid = selDeviceList[i]
              let dev = this.allDeviceObjList[devid]
              let obj = { deviceId: dev.id, deviceNo: dev.deviceNo, deviceIp: dev.deviceIp, area: dev.area, displayType: dev.displayType }
              let devIrrObj = Object.assign({}, this.deviceIrrInfo, obj)
              devIrrlist.push(devIrrObj)
            }
            this.$nextTick(() => {
                devRepairParam = this.$refs['devRepairForm'].getDevRepairPara()
                let para = {}
                if (this.opt === 'add') {
                  para = Object.assign({}, { devIrregularNew: devIrrlist, devIrregularOld: this.oldValue, isBatchRegister: this.registerModel }, devRepairParam)
                } else {
                  para = Object.assign({}, { devIrregularNew: devIrrlist[0], devIrregularOld: this.oldValue[0], isBatchRegister: this.registerModel }, devRepairParam)
                }
                if (devIrrlist.length > 0) {
                  this.to(para).then((res) => {
                    this.loading = false
                    this.$notify(Util.notifyBody(res.ok, res.msg))
                    this.$refs['devIrrAddOrUpdateForm'].resetFields()
                    this.visible = false
                    this.initData()
                    this.callback()
                  })
                } else {
                  this.$message({
                    type: 'info',
                    message: '请选择设备信息再提交'
                  })
                  this.loading = false
                }
            })
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消提交'
            })
            this.loading = false
          })
        }
      })
     },
     resetForm (formName) {
       this.$refs[formName].resetFields()
       this.initData()
     },
     getDeviceAllInfo () {  // 获取设备全部信息
        this.deviceIpList = []
        this.deviceNumList = []
        let para = Object.assign({}, this.filters)
        this.listLoading = true
        API.getDeviceInfoAll().go(para).then((data) => {   // 请求所有的数据
          if (data.ok) {
            this.allDeviceList = data.attr.data.list
            // 设置设备列表信息联动
            for (var i = 0; i < this.allDeviceList.length; i++) {
              this.allDeviceObjList[this.allDeviceList[i].id] = this.allDeviceList[i]
            }
            this.setLinkDevList()
          } else {
            this.$notify(Util.notifyBody(false, data.msg))
          }
          this.listLoading = false
        })
     },
     setAirportCode (airportCode) {
        this.deviceIrrInfo.airportCode = airportCode
        this.setLinkByAirport(airportCode)
        this.setLinkDevList()
     },
     showAirportInfo (subAirportLs) {
        this.$nextTick(() => {
          this.$refs['airCity'].setCitys(subAirportLs)
        })
     },
     setAirportInfo (airportCode) {
        this.$nextTick(() => {
          this.$refs['airCity'].airportCode = airportCode
        })
     },
     setLinkDevList () {
       // 联动设置设备列表
       let noNullAirport = Util.isNotNull(this.deviceIrrInfo.airportCode)
       let noNullTerminal = Util.isNotNull(this.deviceIrrInfo.terminal)
       let nonullDevArea = Util.isNotNull(this.deviceIrrInfo.area)
       let tranDatas = []
       let devList = this.allDeviceList.filter(item => {
         let result = (noNullAirport ? item.airportCode === this.deviceIrrInfo.airportCode : true) && (noNullTerminal ? item.terminal === this.deviceIrrInfo.terminal : true) && (nonullDevArea ? item.area === this.deviceIrrInfo.area : true)
         if (result) {
           tranDatas.push({
             key: item.id,
             label: item.deviceNo + '-' + item.deviceIp
           })
         }
         return result
       })
       this.deviceTranList = tranDatas
       this.deviceList = devList
     },
     renderFunc (h, option) {
        return h(
          'span',
          {},
          [option.label]
        )
     }
   },
   mounted () {
    //  this.bindData()
      var storage = this.$store.getters.getUserStorage
      this.sysUserId = storage.user.id
      this.sysUserName = storage.user.name
      this.subAirportLs = Butil.getSubscribeAirports()
      window.addEventListener('resize', this.setFormClassName)
   }
 }
</script>

<style lang="scss">
.devIrrClss {
  .el-dialog {
    margin-top: -6%!important;
  }
  .el-form-item {
    margin-bottom: 8px;
  }
  .el-dialog__body {
    padding: 10px 20px;
  }
  .el-transfer-panel {
    border: 1px solid #d1dbe5;
    box-shadow: 0 2px 4px rgba(0,0,0,.12), 0 0 6px rgba(0,0,0,.04);
    display: inline-block;
    width: 35% !important;
    position: relative;
  }
  .formClss {
    margin: 5px 5px 5px 5px;
  }
  .borderClss {
    position: relative;
    border: 1px solid gray;
    width: 100%;
    height: 100%;
    margin: 0px 0px 0px -10px;
    overflow: auto;
    overflow-x: hidden;
    min-height: 650px;
    max-height: 650px;
    max-width: 100%;
  }
  .smallDigCls {
    .el-dialog--small {
      width: 50%!important;
    }
  }

  .largeDigCls {
    .el-dialog--small {
      width: 70%!important;
    }
  }
}
</style>


