/*
 * @Author: chenyang
 * @Date: 2017-09-26 15:29:41
 * @Last Modified by: chenyang
 * @Last Modified time: 2017-10-20 09:58:38
 */


 <template>
  <el-dialog :title="title" v-model="visible" :close-on-click-modal="true" :before-close="handleClose" size="small" :modal-append-to-body="false">
    <!--<el-form :model="deviceInfo" ref="deviceAddOrUpdateForm" class="demo-ruleForm" >-->
    <el-form :model="deviceInfo" ref="deviceAddOrUpdateForm" >
      <el-row :gutter="20">
        <el-col :span="6">
          <el-form-item label="机场代码">
            <el-select v-model="deviceInfo.airportCode" placeholder="机场代码">
              <el-option
                v-for="item in airportLabel"
                :key="item.value"
                :label="item.text"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="航站楼">
            <el-select v-model="deviceInfo.terminal" placeholder="航站楼">
              <el-option
                v-for="item in terminalCodeResult"
                :key="item.value"
                :label="item.text"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6" >
          <el-form-item label="楼层" >
            <el-input v-model="deviceInfo.terminalLevel" placeholder="楼层"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="区域">
            <el-input v-model="deviceInfo.area" placeholder="区域"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="6">
          <el-form-item label="父设备">
            <el-input v-model="deviceInfo.parentId" placeholder="父设备"></el-input>
          </el-form-item>
        </el-col>
         <el-col :span="6" >
          <el-form-item label="设备编号" >
            <el-input v-model="deviceInfo.deviceNo" placeholder="设备编号"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="设备名称" >
            <el-input v-model="deviceInfo.deviceName" placeholder="设备名称"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="设备类型">
            <el-select v-model="deviceInfo.deviceModel" placeholder="设备类型">
              <el-option
                v-for="item in deviceModelList"
                :key="item.value"
                :label="item.text"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="设备产地">
            <el-input v-model="deviceInfo.deviceOrigin" placeholder="设备产地"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="设备厂商">
            <el-input v-model="deviceInfo.deviceFactory" placeholder="设备厂商"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="设备IP">
            <el-input v-model="deviceInfo.deviceIp" placeholder="设备IP"></el-input>
          </el-form-item>
        </el-col>
        <!-- <ul class="ipAdress">
          <li v-for="(item,index) in ipAdress">
          <input type="text" @input="checkIpVal(item,index)" @keyup="turnIpPOS(item,index,$event)" v-model:value="item.value" ref="ipInput" @blur="setDefaultVal(item)" />
          <div></div>
          </li>
        </ul> -->
        <el-col :span="6">
          <el-form-item label="设备状态" >
            <el-select v-model="deviceInfo.deviceStatus" placeholder="设备状态">
              <el-option
                v-for="item in deviceStatusList"
                :key="item.value"
                :label="item.text"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="设备描述">
            <el-input v-model="deviceInfo.deviceDes" placeholder="设备描述"  type="textarea"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      </br>
      <el-tabs type="border-card">
        <el-tab-pane label="显示属性">
          <el-row :gutter="20">
            <el-col :span="6">
              <el-form-item label="业务类型" >
                <el-select v-model="deviceInfo.displayType" placeholder="业务类型" :filterable="true">
                  <el-option
                    v-for="item in deviceTypeList"
                    :key="item.enumNo"
                    :label="item.displayValue"
                    :value="item.enumNo">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="显示页码">
                <el-select v-model="deviceInfo.isShowPage" placeholder="显示页码">
                  <el-option
                    v-for="item in trueOrFalseList"
                    :key="item.value"
                    :label="item.text"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="每页行数">
                <el-input v-model="deviceInfo.pageSizeC" placeholder="每页行数"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6" >
              <el-form-item label="文本数据源ID" >
                <el-input v-model="deviceInfo.dynamicScrollTextSourceId" placeholder="文本数据源ID"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6" >
              <el-form-item label="是否叠加">
                  <el-select v-model="deviceInfo.isAdd" placeholder="是否叠加">
                  <el-option
                    v-for="item in trueOrFalseList"
                    :key="item.value"
                    :label="item.text"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="不切换模版">
                <el-input v-model="deviceInfo.notChangePage" placeholder="不切换模版"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="关联类型">
                <el-select v-model="deviceInfo.linkResType" placeholder="关联类型" clearable>
                  <el-option
                    v-for="item in linkTypeList"
                    :key="item.value"
                    :label="item.text"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="类型编号">
                <el-select v-model="typeCode" placeholder="类型编号" @change="storeLinkId" :filterable="true" clearable>
                  <el-option
                    v-for="item in linkCodeResult"
                    :key="item.value"
                    :label="item.text"
                    :value="item.value"
                    >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="组合">
                <el-select v-model="deviceInfo.groupId" placeholder="组合" :filterable="true">
                  <el-option
                    v-for="item in deviceGroupsId"
                    :key="item.value"
                    :label="item.text"
                    :value="item.value"
                    >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="组合顺序">
                <el-input-number v-model="deviceInfo.groupSort" :min="1" :max="groupMax"></el-input-number>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item label="新增群组">
                <el-button type="primary" v-on:click="addGroup">新增设备群组</el-button>
              </el-form-item>
            </el-col>
          </el-row>
        </el-tab-pane>
        <el-tab-pane label="硬件操作系统信息">
          <el-row :gutter="20">
            <el-col :span="6">
              <el-form-item label="主机名">
                <el-input v-model="deviceInfo.hostName" placeholder="主机名"></el-input>
              </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="MAC地址">
              <el-input v-model="deviceInfo.macAddr" placeholder="MAC地址"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="系统">
              <el-input v-model="deviceInfo.osName" placeholder="系统"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="CPU">
              <el-input v-model="deviceInfo.cpuDes" placeholder="CPU"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="内存">
              <el-input v-model="deviceInfo.memoryDes" placeholder="内存"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="硬盘">
              <el-input v-model="deviceInfo.hddes" placeholder="硬盘"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="管理员">
              <el-input v-model="deviceInfo.adminName" placeholder="管理员"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="密码">
              <el-input v-model="deviceInfo.adminPwd" placeholder="密码"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="分辨率X">
              <el-input v-model="deviceInfo.resolutionX" placeholder="分辨率X"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="分辨率Y">
              <el-input v-model="deviceInfo.resolutionY" placeholder="分辨率Y"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        </el-tab-pane>
        <el-tab-pane label="控制属性">
          <el-row :gutter="20">
            <el-col :span="6">
              <el-form-item label="开屏指令">
                <el-input v-model="deviceInfo.screenOpenTime" placeholder="开屏指令"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="关屏指令">
                <el-input v-model="deviceInfo.screenCloseTime" placeholder="关屏指令"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="串口号">
                <el-input v-model="deviceInfo.deviceCom" placeholder="串口号"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="VNC端口">
                <el-input v-model="deviceInfo.vncPort" placeholder="VNC端口"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="VNC密码">
                <el-input v-model="deviceInfo.vncPwd" placeholder="VNC密码"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6" >
              <el-form-item label="串口命令(开屏)" >
                <el-input v-model="deviceInfo.openComNo" placeholder="串口命令(开屏)"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6" >
              <el-form-item label="串口命令(关屏)" >
                <el-input v-model="deviceInfo.closeComNo" placeholder="串口命令(关屏)"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6" >
              <el-form-item label="串口命令类型" >
                <el-input v-model="deviceInfo.comCommandType" placeholder="串口命令类型"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-tab-pane>
      </el-tabs>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click.native="visible = false">取消</el-button>
      <el-button @click="resetForm('deviceAddOrUpdateForm')">重置</el-button>
      <el-button type="primary" @click.native="handleSubmit" :loading="loading">提交</el-button>
    </div>
    <add-group
      title="设备群组"
      ref='devGroup'
      :subscrpitAirport='deviceInfo.airportCode'
      >
    </add-group>
  </el-dialog>
 </template>

 <script>
 import Util from '../../../common/js/util'
 import API from '../../../api'
 import LoginInfo from '../../../vuex/store'
 import AddGroup from '../deviceForm/AddGroup'

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
       linkIdOptions: [{
         value: 'first',
         label: '第一级',
         children: [{
           value: 'second',
           label: '第二级',
           children: [{
             value: 'third',
             label: '第三级'
           }]
         }]
       }],
       baggageList: [],
       checkInCounterList: [],
       gateList: [],
       finalLinkId: '',
       finalLinkType: '',
       subscribeAirports: [],  // 用户订阅机场列表
       visible: false,
       loading: false,
       oldValue: {},
       deviceInfo: { id: '', parentId: '', deviceNo: '', deviceName: '', deviceModel: '', deviceOrigin: null, deviceFactory: null, deviceIp: '', displayType: '', deviceStatus: 0, deviceDes: null, deviceCom: '', groupId: 0, linkResId: 0, hostName: null, macAddr: null, osName: null, cpuDes: null, memoryDes: null, hdDes: null, adminName: null, adminPwd: null, vncPwd: '', terminal: '', scrollText: '', remark: '', lastUpdate: null, groupSort: 0, airportCode: null, dynamicScrollTextSourceId: 0, isAdd: 0, isShowPage: 0, pageSizeC: 0, vncPort: 0, resolutionX: 0, resolutionY: 0, backupScrollText: null, notChangePage: '', screenOpenTime: null, screenCloseTime: null, area: null, openComNo: null, closeComNo: null, comCommandType: '', terminalLevel: null, linkResType: '' },
       deviceTypeList: {},
       deviceStatusList: [ { text: '删除', value: -1 }, { text: '在线', value: 0 }, { text: '离线', value: 1 }, { text: '异常', value: 2 } ],
       deviceModelList: [ { text: '工控机', value: '工控机' }, { text: '安卓盒子', value: '安卓盒子' } ],
       trueOrFalseList: [ {text: '否', value: 0}, { text: '是', value: 1 } ],
       terminalList: [ { text: 'T1', value: 'T1' }, { text: 'T2', value: 'T2' }, { text: 'T3', value: 'T3' }, { text: 'T4', value: 'T4' } ],
       airportLabel: [ {text: 'XMN', value: 'XMN'} ],
       linkTypeList: [ { text: '行李转盘', value: '1' }, { text: '值机柜台', value: '2' }, { text: '登机口', value: '3' } ],
       codeList: [{ text: '行李转盘编号 0', value: 0 }],
       typeCode: '',
       terminalRes: [],
       devicesGroupsList: [],
       groupLabels: [],
       groupMaxNum: 0,
       ipAdress: [{
          value: ''
        }, {
          value: ''
        }, {
          value: ''
        }, {
          value: ''
        }]
        }
   },
   computed: {  // 计算属性用来计算类型和机场代码所产生的编号，如果可以顺便计算id
      deviceGroupsId () {
        this.groupLabels = []
        for (let i = 0; i < this.devicesGroupsList.length; i++) {
          if (this.devicesGroupsList[i].airportCode === this.deviceInfo.airportCode) {
            let label = {text: this.devicesGroupsList[i].groupName, value: this.devicesGroupsList[i].id}
            this.groupLabels.push(label)
          }
        }
        return this.groupLabels
      },
      groupMax () {
        // this.deviceInfo.groupSort = 0
        for (let i = 0; i < this.devicesGroupsList.length; i++) {
          if (this.devicesGroupsList[i].id === this.deviceInfo.groupId) {
            this.groupMaxNum = this.devicesGroupsList[i].groupSize
            break
          }
        }
        return this.groupMaxNum
        // console.log(this.groupMaxNum)
      },
      terminalCodeResult () {
        // this.deviceInfo.terminal = ''
        this.terminalList = []
        let airportCodeChoose = this.deviceInfo.airportCode
        // console.log(airportCodeChoose)
        for (let i = 0; i < this.terminalRes.length; i++) {
          if (this.terminalRes[i].airportCode === airportCodeChoose) {
            let label = {text: this.terminalRes[i].terminalCode, value: this.terminalRes[i].terminalCode}
            this.terminalList.push(label)
          }
        }
        return this.terminalList
      },
      linkCodeResult () {
        this.codeList = []
        // this.typeCode = ''
        if (this.deviceInfo.linkResType === '1') {  // 行李转盘
          let resultList = this.findLinkId(this.baggageList, this.deviceInfo.airportCode)
          for (let i = 0; i < resultList.length; i++) {
            let item = {text: resultList[i].carouselCode, value: resultList[i].carouselCode}
            this.codeList.push(item)
          }
          // console.log(this.codeList)
          return this.codeList
        } else if (this.deviceInfo.linkResType === '2') { // 值机柜台
          let resultList = this.findLinkId(this.checkInCounterList, this.deviceInfo.airportCode)
          for (let i = 0; i < resultList.length; i++) {
            let item = {text: resultList[i].counterCode, value: resultList[i].counterCode}
            this.codeList.push(item)
          }
          // console.log(this.codeList)
          return this.codeList
        } else if (this.deviceInfo.linkResType === '3') {  // 登机口
          let resultList = this.findLinkId(this.gateList, this.deviceInfo.airportCode)
          for (let i = 0; i < resultList.length; i++) {
            let item = {text: resultList[i].gateCode, value: resultList[i].gateCode}
            this.codeList.push(item)
          }
          // console.log(this.codeList)
          return this.codeList
        }
      }
    },
    components: {
      addGroup: AddGroup,
      API: API
    },
   methods: {
    addGroup () {   // 新增群组
      this.$refs['devGroup'].show()
    },
    getDeviceGroup () {
      this.loading = true
      let para = Object.assign({}, {pageSize: 0})
      API.getDeviceGroups().go(para).then((data) => {
        if (data.ok) {
          this.devicesGroupsList = data.attr.data.list
        } else {
          this.$notify(Util.notifyBody(false, data.msg))
        }
        this.loading = false
      })
    },
    checkIpVal (item, index) {
      // 确保每个值都处于0-25
      let val = item.value
      // 当输入的是空格时，值赋为空
      val = val.trim()
      val = parseInt(val, 10)
      if (isNaN(val)) {
          val = ''
      } else {
          val = val < 0 ? 0 : val
          val = val > 255 ? 255 : val
      }
      item.value = val
    },
    turnIpPOS (index, event) {
      let self = this
      let e = event || window.event
      // 左箭头向左跳转，左一不做任何措施
      if (e.keyCode === 37) {
          if (index === 0) {

          } else {
              self.$refs.ipInput[index - 1].focus()
          }
      }
      // 删除键把当前数据删除完毕后会跳转到前一个input，左一不做任何处理
      if (e.keyCode === 8) {
          if (index === 0) {

          } else {
              self.$refs.ipInput[index - 1].focus()
          }
      }
      // 右箭头、回车键、空格键、冒号均向右跳转，右一不做任何措施
      if (e.keyCode === 39 || e.keyCode === 13 || e.keyCode === 32 || e.keyCode === 190) {
        self.$refs.ipInput[index + 1].focus()
      }
    },
    setDefaultVal (item) {
        // 当input失去焦点，而ip没有赋值时，会自动赋值为0
        let val = item.value
        if (val === '') {
            item.value = '0'
        }
    },
     getSubscribeAirports () {
      let result = []
      let data = LoginInfo.state.userStorage.user.aiisAirports
      for (let i = 0; i < data.length; i++) {
        result.push(data[i].airportCode)
      }
      return result
     },
     setAirportCode (airportList) {
       this.airportLabel = []
       for (let i = 0; i < airportList.length; i++) {
         let item = {text: airportList[i], value: airportList[i]}
         this.airportLabel.push(item)
       }
     },
     findLinkId (typeList, subscribeAirport) {
      // 可能返回多条，选择后再找出id
      let ll = []
      for (let i = 0; i < typeList.length; i++) {
        if (typeList[i].airportCode === subscribeAirport) {
          ll.push(typeList[i])
        }
      }
      return ll
     },
     initCascader () {
      this.subscribeAirports = this.getSubscribeAirports()   // 用户订阅的机场列表
      this.setAirportCode(this.subscribeAirports)
      this.linkIdOptions = []
      this.baggageList = this.$cache.fetch('baggageCarousels')
      this.checkInCounterList = this.$cache.fetch('checkinCounters')
      this.gateList = this.$cache.fetch('gate')
     },
     getTerminalFunc () {
        this.loading = true
        let para = Object.assign({}, {pageSize: 0})
        API.getTerminal().go(para).then((data) => {
          if (data.ok) {
            this.terminalRes = data.attr.data.list
          } else {
            this.$notify(Util.notifyBody(false, data.msg))
          }
          this.loading = false
        })
     },
     storeLinkId () {
       this.deviceInfo.linkResId = ''
       if (this.deviceInfo.linkResType === '1') { // 行李转盘
          for (let i = 0; i < this.baggageList.length; i++) {
            if (this.baggageList[i].carouselCode === this.typeCode) {
              this.deviceInfo.linkResId = this.baggageList[i].id
              break
            }
          }
       } else if (this.deviceInfo.linkResType === '2') { // 值机柜台
          for (let i = 0; i < this.checkInCounterList.length; i++) {
            if (this.checkInCounterList[i].counterCode === this.typeCode) {
              this.deviceInfo.linkResId = this.checkInCounterList[i].counterId
              break
            }
          }
       } else if (this.deviceInfo.linkResType === '3') {  // 登机口
          for (let i = 0; i < this.gateList.length; i++) {
            if (this.gateList[i].gateCode === this.typeCode) {
              this.deviceInfo.linkResId = this.gateList[i].id
              break
            }
          }
       }
     },
     bindData: function () {
      this.loading = true
      let para = {enumType: 'BUSINESSTYPE'}
      API.getEnumInfo().go(para).then((data) => {
        if (data.ok) {
          this.deviceTypeList = data.attr.data.list
        } else {
          this.$notify(Util.notifyBody(false, data.msg))
        }
        this.loading = false
      })
     },
     whenAddBindTypeCode (row) {
       if (row.airportCode !== '' && row.linkResType !== null && row.linkResId !== null) {
         if (row.linkResType === '1') {   // 行李转盘
          for (let i = 0; i < this.checkInCounterList.length; i++) {
            if (row.linkResId === this.checkInCounterList[i].id) {
              this.typeCode = this.checkInCounterList[i].carouselCode
              break
            }
          }
         } else if (row.linkResType === '2') {  // 值机柜台
          for (let i = 0; i < this.baggageList.length; i++) {
            if (row.linkResId === this.baggageList[i].counterId) {
              this.typeCode = this.baggageList[i].counterCode
              break
            }
          }
         } else if (row.linkResType === '3') {  // 登机口
          for (let i = 0; i < this.gateList.length; i++) {
            if (row.linkResId === this.gateList[i].id) {
              this.typeCode = this.gateList[i].gateCode
              break
            }
          }
         }
       }
     },
     show: function (row) {
      // 由于类型编号在另一张表上所以要通过类型(1,2,3)和id去寻找是哪个值，再绑定
      this.visible = true
      if (row != null) {
        this.whenAddBindTypeCode(row)
        this.deviceInfo = Util.deepCopy(row)
        // this.deviceInfo = row
        this.oldValue = Object.assign({}, row)
      }
      this.bindData()
     },
     handleClose: function (params) {
      this.visible = false
      this.$refs['deviceAddOrUpdateForm'].resetFields()
      this.deviceInfo = { id: '', parentId: '', deviceNo: '', deviceName: '', deviceModel: '', deviceOrigin: null, deviceFactory: null, deviceIp: '', displayType: '', deviceStatus: 0, deviceDes: null, deviceCom: '', groupId: 0, linkResId: 0, hostName: null, macAddr: null, osName: null, cpuDes: null, memoryDes: null, hdDes: null, adminName: null, adminPwd: null, vncPwd: '', terminal: '', scrollText: '', remark: '', lastUpdate: null, groupSort: 0, airportCode: null, dynamicScrollTextSourceId: 0, isAdd: 0, isShowPage: 0, pageSizeC: 0, vncPort: 0, resolutionX: 0, resolutionY: 0, backupScrollText: null, notChangePage: '', screenOpenTime: null, screenCloseTime: null, area: null, openComNo: null, closeComNo: null, comCommandType: '', terminalLevel: null, linkResType: '' }
      this.typeCode = ''
      console.log('关闭了')
     },
     handleSubmit: function (params) {
       this.$refs['deviceAddOrUpdateForm'].validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.loading = true
            let para = { newValue: this.deviceInfo, oldValue: this.oldValue }
            this.to(para).then((res) => {
              this.loading = false
              this.$notify(Util.notifyBody(res.ok, res.msg))
              this.$refs['deviceAddOrUpdateForm'].resetFields()
              this.visible = false
              this.deviceInfo = { id: '', parentId: '', deviceNo: '', deviceName: '', deviceModel: '', deviceOrigin: null, deviceFactory: null, deviceIp: '', displayType: '', deviceStatus: 0, deviceDes: null, deviceCom: '', groupId: 0, linkResId: 0, hostName: null, macAddr: null, osName: null, cpuDes: null, memoryDes: null, hdDes: null, adminName: null, adminPwd: null, vncPwd: '', terminal: '', scrollText: '', remark: '', lastUpdate: null, groupSort: 0, airportCode: null, dynamicScrollTextSourceId: 0, isAdd: 0, isShowPage: 0, pageSizeC: 0, vncPort: 0, resolutionX: 0, resolutionY: 0, backupScrollText: null, notChangePage: '', screenOpenTime: null, screenCloseTime: null, area: null, openComNo: null, closeComNo: null, comCommandType: '', terminalLevel: null, linkResType: '' }
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
     resetForm (formName) {
       // console.log(formName)
       this.$refs[formName].resetFields()
       this.deviceInfo = { id: '', parentId: '', deviceNo: '', deviceName: '', deviceModel: '', deviceOrigin: null, deviceFactory: null, deviceIp: '', displayType: '', deviceStatus: 0, deviceDes: null, deviceCom: '', groupId: 0, linkResId: 0, hostName: null, macAddr: null, osName: null, cpuDes: null, memoryDes: null, hdDes: null, adminName: null, adminPwd: null, vncPwd: '', terminal: '', scrollText: '', remark: '', lastUpdate: null, groupSort: 0, airportCode: null, dynamicScrollTextSourceId: 0, isAdd: 0, isShowPage: 0, pageSizeC: 0, vncPort: 0, resolutionX: 0, resolutionY: 0, backupScrollText: null, notChangePage: '', screenOpenTime: null, screenCloseTime: null, area: null, openComNo: null, closeComNo: null, comCommandType: '', terminalLevel: null, linkResType: '' }
       this.typeCode = ''
     }
   },
   mounted () {
     this.initCascader()  // 初始化级联选择器，利用缓存
     this.getTerminalFunc()
     this.getDeviceGroup()
   }
 }
 </script>

 <style>
 .ipAdress{
    display: flex;
    align-items: center;
    border: 1px solid #0190FE;
    width: 148px;
    margin-right: 10px;
}
.ipAdress li{
    position: relative;
    height: 23px;
    margin: 0;
}
ul[class="ipAdress"] input[type="text"]{
    border: none;
    width: 100%;
    height: 23px;
    text-align: center;
    background: transparent;
    color: #efefef;
}
.ipAdress li div{
    position: absolute;
    bottom: 2px;
    right: 0;
    border-radius: 50%;
    background: #0190FE;
    width: 2px;
    height: 2px;
}
/*只需要3个div*/
.ipAdress li:last-child div{
    display: none;
}
/*取消掉默认的input focus状态*/
.ipAdress input:focus{
    outline: none;
}
</style>

