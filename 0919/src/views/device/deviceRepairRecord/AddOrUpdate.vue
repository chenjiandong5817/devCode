/*
 * @Author: ylj
 * @Date: 2018-02-23 22:09:03
 * @Last Modified by: ylj
 * @Last Modified time: 2018-03-07 14:59:12
 */
<template>
<div :class="addOrUpdateClss">
  <el-dialog :title="title" v-model="visible" :close-on-click-modal="false" :before-close="handleClose" :size="digSize" :modal-append-to-body="false" @open="bindData">
    <div class="borderClss">
    <el-form class="formClss" :model="devRepairRecord" ref="devRepairRecordForm" :rules="rule">
      <div id="devIrrSelDiv" v-if="isRepairForm">
      <el-row :gutter="20">
        <el-col :span="6">
          <el-form-item label="" prop="airportCode">
            <label>机场代码</label>
            <city-name
              style="width: 100%;"
              ref="city"
              v-on:getAirportName = "setAirportCode"
              :disabled="editDisable">
            </city-name>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="航站楼">
            <el-select v-model="devRepairRecord.terminal" @change="setLinkField" placeholder="航站楼" clearable>
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
          <el-form-item label="异常状态" >
            <el-select v-model="devRepairRecord.deviceIrrStatus" @change="setLinkField" placeholder="异常状态" clearable width="100%">
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
            <date-time v-model="devRepairRecord.reportingTime" :visitTime="false" dateStyle="width:100%" datePlaceholder="异常时间" timePlaceholder="时间" formatter="yyyy-MM-dd" :allDisable="false" v-on:setOnChangeVal = "setLinkFieldByTime"></date-time>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="24">
          <el-transfer
            v-model="selDevIrrList"
            filterable
            filter-placeholder="请输入设备信息(编号或IP)"
            :render-content="renderFunc"
            :titles="['设备异常记录批量选择', '设备异常记录批量处理']"
            :button-texts="['移除', '添加']"
            :footer-format="{
              noChecked: '${total}' + '个',
              hasChecked: '${checked}/${total}' + '个'
            }"
            :data="devIrrTranList">
          </el-transfer>
        </el-col>
      </el-row>
      </div>

      <el-row :gutter="20">
        <el-col :span="6">
          <el-form-item label="维修单编号" prop="repairNo">
            <el-input v-model="devRepairRecord.repairNo" placeholder="维修单编号"  type="text"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="维修单位" prop="repairUnit">
            <el-input v-model="devRepairRecord.repairUnit" placeholder="维修单位"  type="text"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="维修人员" prop="repairUser">
            <el-input v-model="devRepairRecord.repairUser" placeholder="维修人员"  type="text"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="维修状态">
            <el-select v-model="devRepairRecord.repairStatus" placeholder="维修状态">
              <el-option
                v-for="item in devRepairStatusList"
                :key="item.id"
                :label="item.displayValue"
                :value="parseInt(item.enumNo)">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="6">
          <el-form-item label="保修单位" prop="warrantyUnit">
            <el-input v-model="devRepairRecord.warrantyUnit" placeholder="保修单位"  type="text"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="保修人员" prop="warrantyMan">
            <el-input v-model="devRepairRecord.warrantyMan" placeholder="保修人员"  type="text"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="开始时间" prop="beginTime">
            <br/>
            <date-time v-model="devRepairRecord.beginTime" :visitTime="true" dateStyle="width:67%" timeStyle="width:30%" datePlaceholder="异常时间" timePlaceholder="时间" formatter="yyyy-MM-dd" :allDisable="false"></date-time>
          </el-form-item>
        </el-col>

        <el-col :span="6">
          <el-form-item label="结束时间" prop="endTime">
            <br/>
            <date-time v-model="devRepairRecord.endTime" :visitTime="true" dateStyle="width:67%" timeStyle="width:30%" datePlaceholder="异常时间" timePlaceholder="时间" formatter="yyyy-MM-dd" :allDisable="false"></date-time>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="24">
          <el-form-item label="维修问题" prop="repairMatter">
            <el-input v-model="devRepairRecord.repairMatter" placeholder="维修问题"  type="textarea"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="24">
          <el-form-item label="维修记录" prop="repairRecord">
            <el-input v-model="devRepairRecord.repairRecord" placeholder="维修记录"  type="textarea"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button @click.native="handleClose">取消</el-button>
      <el-button @click="resetForm('devRepairRecordForm')">重置</el-button>
      <el-button type="primary" @click.native="handleSubmit" :loading="loading">提交</el-button>
    </div>
  </el-dialog>
</div>
</template>

<script>
 import Util from '../../../common/js/util'
 import Butil from '../../../common/js/base-util'
 import API from '../../../api'
 import Rules from '../../../common/js/rules'
 import DateTime from '../../../components/DateTime'
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
       opt: 'add',
       Rules: Rules,
       addOrUpdateClss: '',
       digSize: 'small',
       isRepairForm: true,
       sysUserId: null,
       sysUserName: null,
       editDisable: false,
       devRepairRecord: {
         id: '',
         airportCode: '',
         terminal: '',
         reportingTime: null,
         deviceIrrStatus: null,
         deviceIrrId: '',
         repairUser: '',
         repairStatus: null,
         repairRecord: '',
         beginTime: '',
         endTime: '',
         warrantyUnit: '',
         warrantyMan: '',
         repairNo: '',
         repairUnit: '',
         repairMatter: ''
       },
       oldValue: {},
       deviceStatusList: [],
       allDevIrrList: [],
       allDevIrrObjs: [],
       devIrrTranList: [],
       selDevIrrList: [],
       userList: [],
       devRepairStatusList: [],
       terminalList: this.$cache.fetch('terminals'),
       terminalRes: this.$cache.fetch('terminals'),
       subAirportLs: [],
       rule: {
         beginTime: [{ validator: Rules.validSegTimeType, message: '非法时间格式', trigger: 'blur' }],
         endTime: [{ validator: Rules.validSegTimeType, message: '非法时间格式', trigger: 'blur' }],
         repairNo: [{ required: true, message: '请选择设备异常状态', trigger: 'blur' }],
         repairStatus: [{ type: 'number', required: true, message: '请选择设备异常状态', trigger: 'blur' }]
       },
       visible: false,
       loading: false,
       listLoading: false
      }
   },
   computed: {
    },
    components: {
      API: API,
      dateTime: DateTime,
      cityName: CityName
    },
    methods: {
     setFormClassName () {
      let isMinWidth = (document.body.clientWidth <= 1366)
      this.digSize = (this.isRepairForm ? 'small' : 'large')
      this.addOrUpdateClss = isMinWidth ? 'devRepairClss smallDigCls2' : (this.isRepairForm ? 'devRepairClss smallDigCls' : 'devRepairClss largeDigCls')
     },
     bindData () {
       var storage = this.$store.getters.getUserStorage
       this.sysUserId = storage.user.id
       this.sysUserName = storage.user.name
       this.getAllDeviceIrrInfo()
      //  this.setLinkField(this.allDevIrrList)
       this.subAirportLs = Butil.getSubscribeAirports()
       this.setFormClassName()
       if (this.isRepairForm) {
         this.showAirportInfo(this.subAirportLs)
       }
       this.getDevRepairStatusList()
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
     getDevRepairStatusList () {
        // 获取设备维修类型列表
        let para = {enumType: 'DEVICEREPAIRSTATUS'}
        API.getEnumInfo().go(para).then((data) => {
          if (data.ok) {
            this.devRepairStatusList = data.attr.data.list
          } else {
            this.$notify(Util.notifyBody(false, data.msg))
          }
          this.loading = false
        })
     },
     show (row, airportCode, isRepairForm) {
      this.visible = true
      this.isRepairForm = isRepairForm
      this.devRepairRecord.airportCode = airportCode
      this.setLinkByAirport(this.devRepairRecord.airportCode)
      if (isRepairForm) {
        this.showAirportInfo(this.subAirportLs)
        this.setAirportInfo(airportCode)
      }
      if (row !== null && row !== undefined && this.isRepairForm) {
        // 查找改维修单的所有故障设备
        this.opt = 'edit'
        this.devRepairRecord = Util.deepCopy(row)
        let para = { repairId: row.id }
        this.loading = true
        // 获取维修单对应异常信息列表，与后端对接 待测试
        API.getDeviceIrrInfos().go(para).then((data) => {
          if (data.ok) {
            let devIrrList = data.attr.data.deviceIrregulars
            let array = []
            let selDevIrrList = []
            for (var i = 0; i < devIrrList.length; i++) {
              let devIrrObj = { airportCode: devIrrList[i].airportCode, terminal: devIrrList[i].terminal, reportingTime: devIrrList[i].reportingTime, deviceIrrStatus: parseInt(devIrrList[i].deviceIrrStatus) }
              let obj = Object.assign({}, this.devRepairRecord, devIrrObj)
              this.devRepairRecord = obj
              selDevIrrList[i] = devIrrList[i].id
              array.push(obj)
            }
            this.oldValue = array
            this.selDevIrrList = selDevIrrList
          } else {
            this.$notify(Util.notifyBody(false, data.msg))
          }
          this.visible = true
          this.loading = false
        })
      } else if (this.isRepairForm) {
        this.opt = 'add'
      } else {
        this.opt = 'addByIrr'
        this.devRepairRecord = Util.deepCopy(row)
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
      // this.$refs['devRepairRecordForm'].resetFields()
      this.initData()
     },
     initData () {
       this.digSize = 'small'
       this.isRepairForm = true
       this.editDisable = false
       this.devRepairRecord = {
         id: '',
         airportCode: '',
         terminal: '',
         reportingTime: null,
         deviceIrrStatus: null,
         deviceIrrId: '',
         repairUser: '',
         repairStatus: '',
         repairRecord: '',
         beginTime: '',
         endTime: '',
         warrantyUnit: '',
         warrantyMan: '',
         repairNo: '',
         repairUnit: '',
         repairMatter: ''
       }
       this.oldValue = []
       this.devIrrTranList = []
       this.selDevIrrList = []
     },
     handleSubmit () {
       // 维修记录编辑提交，前后端对接，待测试
       this.$refs['devRepairRecordForm'].validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.loading = true
            let newValue = []
            let para = {}
            newValue.push(this.devRepairRecord)
            if (!this.isRepairForm) {
              para = Object.assign(para, { newRepairRecord: this.devRepairRecord, oldRepairRecord: this.oldValue[0] })
              this.to(para)
              this.handleClose()
            } else {
              let selDevIrrObjList = []
              for (var i = 0; i < this.selDevIrrList.length; i++) {
                let id = this.selDevIrrList[i]
                selDevIrrObjList[i] = this.allDevIrrObjs[id]
              }
              para = Object.assign(para, { devIrregulars: selDevIrrObjList, newValue: this.devRepairRecord, oldValue: this.oldValue[0] })
              this.to(para).then((res) => {
                this.loading = false
                this.$notify(Util.notifyBody(res.ok, res.msg))
                this.visible = false
                this.initData()
                this.callback()
              })
            }
          }).catch(() => {
            this.$message({
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
     getAllDeviceIrrInfo () {  // 获取设备全部信息
        this.listLoading = true
        let filters = {}
        let objs = {}
        let para = Object.assign({}, filters)
        API.getDeviceIrrInfos().go(para).then((data) => {   // 请求所有的数据
          if (data.ok) {
            this.allDevIrrList = data.attr.data.list
            for (var i = 0; i < this.allDevIrrList.length; i++) {
              let id = this.allDevIrrList[i].id
              objs[id] = Util.deepCopy(this.allDevIrrList[i])
            }
            this.allDevIrrObjs = objs
            this.setLinkField(this.allDevIrrList)
          } else {
            this.$notify(Util.notifyBody(false, data.msg))
          }
          this.listLoading = false
        })
     },
     setAirportCode (airportCode) {
        this.devRepairRecord.airportCode = airportCode
        this.setLinkField()
     },
     showAirportInfo (subAirportLs) {
        this.$nextTick(() => {
          this.$refs['city'].setCitys(subAirportLs)
        })
     },
     setAirportInfo (airportCode) {
        this.$nextTick(() => {
          this.$refs['city'].airportCode = airportCode
        })
     },
     setLinkField (alldevIrrList) {
       let devIrrTranList = []
       let noNullAirport = Util.isNotNull(this.devRepairRecord.airportCode)
       let noNullTerminal = Util.isNotNull(this.devRepairRecord.terminal)
       let noNullIrrStatus = Util.isNotNull(this.devRepairRecord.deviceIrrStatus)
       this.allDevIrrList = (this.allDevIrrList === undefined && alldevIrrList !== undefined) ? alldevIrrList : this.allDevIrrList
       if (this.allDevIrrList !== undefined) {
         this.allDevIrrList.filter(item => {
           let result = (noNullAirport ? item.airportCode === this.devRepairRecord.airportCode : true) &&
              (noNullTerminal ? item.terminal === this.devRepairRecord.terminal : true) &&
              (noNullIrrStatus ? item.deviceIrrStatus === this.devRepairRecord.deviceIrrStatus : true)
           if (result) {
             devIrrTranList.push({
               key: item.id,
               label: item.deviceNo + '-' + item.deviceIp
             })
           }
           return result
         })
       }
       this.devIrrTranList = devIrrTranList
       this.selDevIrrList = this.selDevIrrList.length === 0 ? [] : Util.deepCopy(this.selDevIrrList)
     },
     setLinkFieldByTime () {
       let devIrrTranList = []
       let noNullAirport = Util.isNotNull(this.devRepairRecord.airportCode)
       let noNullTerminal = Util.isNotNull(this.devRepairRecord.terminal)
       let noNullIrrStatus = Util.isNotNull(this.devRepairRecord.deviceIrrStatus)
       let noNullTime = Util.isNotNull(this.devRepairRecord.reportingTime)
       if (this.allDevIrrList !== undefined) {
        this.allDevIrrList.filter(item => {
          let result = (noNullTime && item.reportingTime >= this.devRepairRecord.reportingTime) &&
              (noNullAirport ? item.airportCode === this.devRepairRecord.airportCode : true) &&
              (noNullTerminal ? item.terminal === this.devRepairRecord.terminal : true) &&
              (noNullIrrStatus ? item.deviceIrrStatus === this.devRepairRecord.deviceIrrStatus : true)
          if (result) {
            devIrrTranList.push({
              key: item.id,
              label: item.deviceNo + '-' + item.deviceIp
            })
          }
          return result
        })
       }
       this.devIrrTranList = devIrrTranList
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
      // this.getAllDeviceIrrInfo()
      window.addEventListener('resize', this.setFormClassName)
   }
 }
</script>

<style lang="scss">
.devRepairClss {
  .el-dialog {
    margin-top: -7%!important;
  }
  .el-dialog__body {
    padding: 10px 20px;
  }
  .el-form-item {
    margin-bottom: 8px;
  }
  .formClss {
    margin: 5px 5px 5px 5px;
  }
  .el-transfer-panel {
    border: 1px solid #d1dbe5;
    box-shadow: 0 2px 4px rgba(0,0,0,.12), 0 0 6px rgba(0,0,0,.04);
    display: inline-block;
    width: 45% !important;
    position: relative;
  }
  .smallDigCls {
    .el-dialog--small {
      width: 50%!important;
    }
    .borderClss {
      min-height: 770px;
      max-height: 770px;
    }
  }

  .smallDigCls2 {
    .el-dialog--small {
      width: 73%!important;
    }
    .borderClss {
      min-height: 770px;
      max-height: 770px;
    }
  }

  .largeDigCls {
    .el-dialog--large {
      width: 100%!important;
    }
    .borderClss {
      min-height: 440px;
      max-height: 440px;
    }
  }

  .borderClss {
    position: relative;
    border: 1px solid gray;
    width: 100%;
    height: 100%;
    margin: 0px 0px 0px -10px;
    overflow: auto;
    overflow-x: hidden;
    min-height: 440px;
    max-height: 440px;
    max-width: 100%;
  }
}
</style>


