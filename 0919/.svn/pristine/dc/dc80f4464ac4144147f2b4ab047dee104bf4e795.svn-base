/*
 * @Author: chenyang
 * @Date: 2017-07-12 10:49:00
 * @Last Modified by: chenyang
 * @Last Modified time: 2017-11-16 10:04:17
 * @Description: noVNC管理远程预览模块
 */
<template>
    <section>
      <el-col :span="6" class="leftMargin marginTop">
        <div>
          <div id="controlArea">
            <el-radio-group v-model="radio" size="" fill="#13CE66" @change="radioGroupClick">
              <el-radio-button label="航站楼"></el-radio-button>
              <el-radio-button label="类型"></el-radio-button>
            </el-radio-group>
            <city-name
              class="selectAirportStyle"
              ref="city"
              v-on:getAirportName = "getAirportCode">
            </city-name>
            <div v-show="showFlag">
              <el-radio-group v-model="TerminalRadio" @change="filterTerminal" class="marginTop">
              <el-radio :label="1">所有</el-radio>
              <el-radio :label="2">异常</el-radio>
              <el-radio :label="3">在线</el-radio>
              <el-radio :label="4">组合屏</el-radio>
              </el-radio-group>
            </div>
            <div v-show="!showFlag">
              <el-radio-group v-model="typeRadio" @change="filterType" class="marginTop">
              <el-radio :label="1">所有</el-radio>
              <el-radio :label="2">异常</el-radio>
              <el-radio :label="3">在线</el-radio>
              <el-radio :label="4">组合屏</el-radio>
              </el-radio-group>
            </div>
            <el-input class="input" placeholder="输入IP地址或设备名称进行过滤" v-model="filterIp">
            </el-input>
          </div>
          <div id='terminal' v-show="showFlag">
            <tree
              class="filter-tree height width"
              :data="terminalRoot"
              :props="defaultProps"
              node-key="id"
              :filter-node-method="filterNode"
              highlight-current
              @node-click="terminalNodeClick"
              :render-content='renderContent'
              v-loading='getDataLoading'
              :default-expand-all = 'true'
              :callback="getRightClickLabel"
              :menuClick="menuClickItem"
              ref="terminal">
            </tree>
          </div>
        <div id='accordingType' v-show="!showFlag">
          <tree
            class="filter-tree height2 width"
            :data="typeRoot"
            :props="defaultProps"
            node-key="id"
            :filter-node-method="filterNode"
            highlight-current
            v-loading='getDataLoading'
            @node-click='devTypeNodeClick'
            :render-content='renderContent'
            :default-expand-all = 'true'
            :callback="getRightClickLabel"
            :menuClick="menuClickItem"
            ref="actype">
          </tree>
        </div>
      </div>
      </el-col>
      <el-col :span="16" class="vncMargin marginTop">
        <div>
            <el-button type="primary" v-on:click="clearAll" icon="delete">清空所有监控设备</el-button>
              <div v-for="iframe in iframeList" :key="iframe.ip">
                  <!-- before-close 加deleteing只有点击删除的时候才会触发效果 -->
                  <el-col :span="5" :class="['divborder', { 'before-close': position === iframe.ip && deleteing }]">
                      <iframe :src="iframe.src" :name="iframe.ip" class='iframestyle'
                          :id="iframe.ip" scrolling="no" frameborder=no @load="sendMessage(iframe.ip, port)">
                      </iframe>
                      <div class='textarea'>{{iframe.ip}}</div>
                      <el-tag class='deviceName el-tag--danger'>{{iframe.deviceName}}</el-tag>
                      <el-tooltip effect="dark" content="删除" placement="top">
                        <div class='shut' v-on:click="deleteDialog(`${ iframe.ip }`)"></div>
                      </el-tooltip>
                      <el-tooltip effect="dark" content="全屏" placement="top">
                        <div class='full' v-on:click="fullScreen(`${ iframe.ip }`)"></div>
                      </el-tooltip>
                  </el-col>
              </div>
                  <!-- base.css源码已修改,scrolling属性没用，滚动条禁用-->
        </div>
      </el-col>
    </section>
</template>

<script>
import util from '../../common/js/util'
import API from '../../api'
import Tree from './../../components/tree/tree'
import StompUtil from './../../common/stomp/stomp-util'
import JsonList from './../../common/menu/jsonList'
import LoginInfo from '../../vuex/store'
import CityName from '../../components/CityName'
import vncConfig from '../../api/device/vncManageConfig'
// let baseId = 100
export default {
  components: {
    Tree,
    cityName: CityName
  },
  data () {
    return {
        showFlag: true,
        port: '', // 端口
        airportCode: '',
        subscribeAirportsLables: [],
        subscribeAirports: [],
        getDataLoading: true,
        radio: 3,   // 选航站楼,类型,列表
        TerminalRadio: 1,
        typeRadio: 1,
        RepicWidth: 200,
        index: null,
        qwerqwre: 0,
        position: -1,
        businessMap: {},
        deviceBusiness: [],
        isFullScreen: false,  // 是否全屏，全屏用全屏那套src,非全屏用缩略图缩放那套src
        fields: [
          {name: 'id', value: '', hidden: true},
          {name: 'IP地址', value: '', label: 'IP地址', type: 'text', rules: null},
          {name: '设备命名', value: '', label: '设备命名', type: 'text', rules: null},
          {name: '描述', value: '', label: '描述', type: 'text', rules: null}
          // {name: 'disabled', value: '0', label: 'disabled', type: 'text', rules: null},
          // {name: 'groupid', value: '', label: 'groupid', type: 'text', rules: null}
        ],
        deleteing: false,
        flag: false,
        iframeList: [{
          src: 'http://192.168.1.118:9092/vnc.html?path=websockify/?token=192.168.1.114',
          ip: '192.168.1.1',
          VncPassWord: '预留',
          isAdd: true,
          deviceName: '肯德基'
        }],
        vncList: [{
          src: 'http://element.eleme.io/#/zh-CN/component/layout',   // url
          ip: '192.168.1.1',   // ip地址
          VncPassWord: '预留',
          isAdd: true,
          deviceName: '肯德基'
        }],
        deviceinfos: [], // 所有的信息
        abnormalinfos: [], // 异常的信息列表，过滤用
        onlineinfos: [], // 在线的信息列表，过滤用
        deletedevinfos: [], // 被删除的列表，过滤用
        combineinfos: [], // 组合设备的信息列表，过滤用
        filters: {
          deviceNo: null,  // 设备编号
          deviceIp: null,  // 设备IP
          terminal: null,   // 候机楼
          displayType: null, // 设备类型
          deviceModel: null, // 设备模型,比如工控机等
          onlined: null // 是否在线
        },
        filterIp: '',
        filterName: '',
        terminalRoot: [{    // 根据航站楼作为父节点
          id: 1,
          label: '一级 1',
          children: [{
            id: 2,
            label: '二级 1-1',
            children: [{
              id: 3,
              label: '三级 1-1-1'
            }]
          }]
        }],
        typeRoot: [{   // 根据类型作为父节点
          id: 1,
          label: '类型为父节点',
          children: [{
            id: 2,
            label: '类型',
            children: [{
              id: 3,
              label: '类型'
            }]
          }]
        }],
        defaultProps: {
          children: 'children',
          label: 'label'
        },
        terminal: [],  // 候机楼汇总,预留,后期重构用
        statusMap: {},
        terminalstatusMap: {},
        storeMap: {},
        typeMap: {},
        deviceNameMap: {},
        ipMap: {}, // IP和设备组成的字符串和Ip键值对
        IppasswordMap: {},  // ip和密码键值对
        monitorIp: 'x.x.x.x',  // 宿主机ip
        monitorOriginPort: vncConfig.monitorOriginPort,  // 宿主机全屏端口
        monitorSmallPort: vncConfig.monitorSmallPort, // 宿主机非全屏端口
        typeAndCodeMap: {}, // 位置名称对应编号Map,供拼接routing key用
        curNode: null // 当前点击位置的node
    }
  },
  watch: {
      filterIp (val) {   // 根据IP地址进行过滤
        if (this.radio === '航站楼') {
          this.$refs.terminal.filter(val)
        } else if (this.radio === '类型') {
          this.$refs.actype.filter(val)
        }
      },
      filterName (val) {
        if (this.radio === '航站楼') {
          this.$refs.terminal.filter(val)
        } else if (this.radio === '类型') {
          this.$refs.actype.filter(val)
        }
      }
  },
  methods: {
    getAirportCode (airportData) {
        // console.log(airportData)
        this.airportCode = airportData
        this.getDeviceInfo()
    },
    stompConnect () {
      StompUtil.connect()
    },
    changeIp (ip) {
      var ipArray = []
      ipArray = ip.split('.')
      let changeResult = ''
      for (let i = 0; i < ipArray.length; i++) {
        if (ipArray[i].length < 3) {  // ip 位数小于3,补前导0
          for (let j = 0; j < 3 - ipArray[i].length; j++) { // 补几个
            changeResult += '0'
          }
          changeResult += ipArray[i]
        } else {
          changeResult += ipArray[i]
        }
      }
      return changeResult
    },
    getRightClickLabel (obj) {
      this.curNode = obj
      // console.log(this.curNode.label)
    },
    SubscribeAirports () {
      this.subscribeAirportsLables = []
      this.subscribeAirports = this.getSubscribeAirports()   // 用户订阅的机场列表
      for (let i = 0; i < this.subscribeAirports.length; i++) {
        let label = {text: this.subscribeAirports[i], value: this.subscribeAirports[i]}
        this.subscribeAirportsLables.push(label)
      }
      this.airportCode = this.subscribeAirports[0]
    },
    getSubscribeAirports () {
      let result = []
      let data = LoginInfo.state.userStorage.user.aiisAirports
      console.log(data)
      for (let i = 0; i < data.length; i++) {
        result.push(data[i].airportCode)
      }
      return result
    },
    menuClickItem (item) {
      // let menuArray = ['清空缓存', '同步时间', '刷新', '应用升级', '更新播放列表', '关机', '重启', '检查设备状态', '下载多媒体信息', '亮屏', '灭屏', '上传应用错误日志']
      let msg = null
      if (item === '清空缓存') {
        msg = JsonList.clearCache
      } else if (item === '同步时间') {
        msg = JsonList.syncTime
      } else if (item === '刷新界面') {
        msg = JsonList.refresh
      } else if (item === '应用升级') {
        msg = JsonList.upgrade
      } else if (item === '更新播放列表') {
        msg = JsonList.updatePlayList
      } else if (item === '关机') {
        msg = JsonList.shutdown
      } else if (item === '重启') {
        msg = JsonList.reboot
      } else if (item === '检查设备状态') {
        msg = JsonList.checkDevice
      } else if (item === '下载多媒体信息') {
        msg = JsonList.downloadMedia
      } else if (item === '亮屏') {
        msg = JsonList.screenOn
      } else if (item === '灭屏') {
        msg = JsonList.screenOff
      } else if (item === '上传应用错误日志') {
        msg = JsonList.uploadErrorLogs
      }
      let prefix = 'ROOT' + '.' + this.airportCode + '.'
      if (this.radio === '航站楼') {
        if (this.curNode.level === 3) {
          let bottomNode = this.ipMap.get(this.curNode.label)   // ip有点要重新划分组合(IP)
          bottomNode = this.changeIp(bottomNode)  // 划分
          let typeCodeNode = this.typeAndCodeMap.get(this.curNode.parent.label)  // 位置
          let terminalNode = this.curNode.parent.parent.label  // 航站楼
          let sendMessage = prefix + terminalNode + '.' + typeCodeNode + '.' + bottomNode
          StompUtil.handleSend(sendMessage, msg)
        } else if (this.curNode.level === 2) {
          let typeCodeNode = this.typeAndCodeMap.get(this.curNode.label) // 位置
          let terminalNode = this.curNode.parent.label // 航站楼
          let sendMessage = prefix + terminalNode + '.' + typeCodeNode
          StompUtil.handleSend(sendMessage, msg)
        } else if (this.curNode.level === 1) {
          // console.log(this.nodeData.childNodes)
          let terminalNode = this.curNode.label // 航站楼
          let sendMessage = prefix + terminalNode
          // this.handleSend(sendMessage, msg)
          StompUtil.handleSend(sendMessage, msg)
        }
      } else {
        if (this.curNode.level === 3) {
          let bottomNode = this.ipMap.get(this.curNode.label)   // ip有点要重新划分组合(IP)
          bottomNode = this.changeIp(bottomNode)  // 划分
          let terminalNode = this.curNode.parent.label  // 航站楼
          let typeCodeNode = this.typeAndCodeMap.get(this.curNode.parent.parent.label)  // 类型
          let sendMessage = prefix + typeCodeNode + '.' + terminalNode + '.' + bottomNode
          StompUtil.handleSend(sendMessage, msg)
        } else if (this.curNode.level === 2) {
          let terminalNode = this.curNode.label // 航站楼
          let typeCodeNode = this.typeAndCodeMap.get(this.curNode.parent.label) // 位置
          let sendMessage = prefix + typeCodeNode + '.' + terminalNode
          StompUtil.handleSend(sendMessage, msg)
        } else if (this.curNode.level === 1) {
          // console.log(this.nodeData.childNodes)
          let typeCodeNode = this.typeAndCodeMap.get(this.curNode.label) // 位置
          let sendMessage = prefix + typeCodeNode
          // this.handleSend(sendMessage, msg)
          StompUtil.handleSend(sendMessage, msg)
        }
      }
    },
    radioGroupClick () {  // 根节点的切换
      if (this.radio === '航站楼') {
        this.showFlag = true
        // this.filterRadio = 1
        // this.accordingTerminal(this.deviceinfos)
        // // 要跳到单选框第一个数据，所有
      } else if (this.radio === '类型') {
        this.showFlag = false
        // // 要跳到单选框第一个数据，所有
        // this.filterRadio = 1
        // this.accordingDeviceType(this.deviceinfos)
      }
    },
    filterTerminal () {  // 过滤条件的切换
      let filterRadioValue = this.TerminalRadio
      if (filterRadioValue === 1) {   // 所有列表的信息
        this.accordingTerminal(this.deviceinfos)
      } else if (filterRadioValue === 2) {   // 异常
        this.accordingTerminal(this.abnormalinfos)
      } else if (filterRadioValue === 3) {   // 在线
        this.accordingTerminal(this.onlineinfos)
      } else if (filterRadioValue === 4) {   // 组合屏
        this.accordingTerminal(this.combineinfos)
      } else {   // 默认显示所有列表
        this.accordingTerminal(this.deviceinfos)
      }
    },
    filterType () {  // 过滤条件的切换
      let filterRadioValue = this.typeRadio
      if (filterRadioValue === 1) {   // 所有列表的信息
        this.accordingDeviceType(this.deviceinfos)
      } else if (filterRadioValue === 2) {   // 异常
        this.accordingDeviceType(this.abnormalinfos)
      } else if (filterRadioValue === 3) {   // 在线
        this.accordingDeviceType(this.onlineinfos)
      } else if (filterRadioValue === 4) {   // 组合屏
        this.accordingDeviceType(this.combineinfos)
      } else {   // 默认显示所有列表
        this.accordingDeviceType(this.deviceinfos)
      }
    },
    terminalNodeClick (data) {  // 以航站楼为根节点的树形控件点击事件
      let terminalIp = this.ipMap.get(data.label)
      if (terminalIp !== undefined) {
        let url = this.joint(this.monitorIp, this.monitorSmallPort, terminalIp)   // 宿主机的IP和端口,要监控的host,url为宿主机ip
        this.setData(url, terminalIp, this.IppasswordMap.get(terminalIp), this.deviceNameMap.get(terminalIp))
        // let iframe = document.getElementById(terminalIp).contentWindow
        // iframe.postMessage(this.IppasswordMap.get(terminalIp), 'http://' + this.monitorIp + ':' + this.monitorOriginPort)   // 发给宿主机,密码和宿主机地址
      }
    },
    devTypeNodeClick (data) {
      let devTypeIp = this.ipMap.get(data.label)
      if (devTypeIp !== undefined) {
        let url = this.joint(this.monitorIp, this.monitorSmallPort, devTypeIp)
        this.setData(url, devTypeIp, this.IppasswordMap.get(devTypeIp), this.deviceNameMap.get(devTypeIp))
        // console.log('url:' + url)
        // let iframe = document.getElementById(devTypeIp).contentWindow
        // iframe.postMessage(this.IppasswordMap.get(devTypeIp), 'http://' + this.monitorIp + ':' + this.monitorOriginPort)
      }
    },
    filterNode (value, data, node) {   // value为输入的值
      if (!value) return true
      if (data.label === null) {
        return ''
      }
      return data.label.indexOf(value) !== -1
    },
    setData (url, ip, VncPassWord, deviceName) {
      let dataList = this.vncList
      this.vncList = []
      dataList[0].src = url
      dataList[0].ip = ip
      dataList[0].VncPassWord = VncPassWord
      dataList[0].isAdd = false // 还没添加
      dataList[0].deviceName = deviceName
      this.vncList.push(Object.assign({}, dataList[0]))
      // console.log(url + ' ' + ip + ' ' + VncPassWord)
      // console.log('vncListLength:' + this.vncList.length)
      this.addPage(ip)
    },
    typeVisit (type, num, devinfos) {  // type的相关遍历
      this.typeMap = {}
      this.typeMap = new Map()
      let cnt = 0
      for (var tt1 = 0; tt1 < type.length; tt1++) {
        // 建第二层节点
        let typeNode = {}
        if (type[tt1].terminal === null) {
          typeNode = {label: 'others', children: []}
        } else {
          typeNode = {label: type[tt1].terminal, children: []}
        }
        if (!this.typeMap.has(type[tt1].terminal)) {
          // console.log('添加成功')
          this.typeRoot[num].children.push(typeNode)
          this.typeMap.set(type[tt1].terminal, cnt++)  // 置为已经添加过了,并记录数组下标
        }  // 如果没有添加过，那么添加

        // 建第三层节点
        let devIp = {label: type[tt1].deviceName + ' ' + type[tt1].deviceIp, children: []}
        this.typeRoot[num].children[this.typeMap.get(type[tt1].terminal)].children.push(devIp)
        this.deviceNameMap.set(type[tt1].deviceIp, type[tt1].deviceName)
      }
      this.statusMap = new Map()
      for (var cz = 0; cz < devinfos.length; cz++) {  // 获取IP和状态的键值对
        let devStatus = ''
        if (devinfos[cz].deviceStatus === -1) {
          devStatus = '删除'
        } else if (devinfos[cz].deviceStatus === 0) {
          devStatus = '在线'
        } else {
          devStatus = '异常'
        }
        this.statusMap.set(devinfos[cz].deviceName + ' ' + devinfos[cz].deviceIp, devStatus)
        this.ipMap.set(devinfos[cz].deviceName + ' ' + devinfos[cz].deviceIp, devinfos[cz].deviceIp)
      }
    },
    terminalVisit (lTerminal, num, devinfos) {  // type的相关遍历
      this.typeMap = {}
      this.typeMap = new Map()
      let cnt = 0
      for (var tt1 = 0; tt1 < lTerminal.length; tt1++) {
        // 建第二层节点
        let typeNode = {}
        if (lTerminal[tt1].displayType === null) {
          typeNode = {label: 'others', children: []}
        } else {
          if (typeof this.businessMap.get(lTerminal[tt1].displayType) === 'undefined') {
            typeNode = {label: lTerminal[tt1].displayType, children: []}
          } else {
            typeNode = {label: this.businessMap.get(lTerminal[tt1].displayType), children: []}
          }
        }
        if (!this.typeMap.has(lTerminal[tt1].displayType)) {
          // console.log('添加成功')
          this.terminalRoot[num].children.push(typeNode)
          this.typeMap.set(lTerminal[tt1].displayType, cnt++)  // 置为已经添加过了,并记录数组下标
        }  // 如果没有添加过，那么添加

        // 建第三层节点
        let devIp = {label: lTerminal[tt1].deviceName + ' ' + lTerminal[tt1].deviceIp, children: []}
        this.terminalRoot[num].children[this.typeMap.get(lTerminal[tt1].displayType)].children.push(devIp)
        this.deviceNameMap.set(lTerminal[tt1].deviceIp, lTerminal[tt1].deviceName)
      }
      this.terminalstatusMap = new Map()
      for (var cz = 0; cz < devinfos.length; cz++) {  // 获取IP和状态的键值对
        let devStatus = ''
        if (devinfos[cz].deviceStatus === -1) {
          devStatus = '删除'
        } else if (devinfos[cz].deviceStatus === 0) {
          devStatus = '在线'
        } else {
          devStatus = '异常'
        }
        this.terminalstatusMap.set(devinfos[cz].deviceName + ' ' + devinfos[cz].deviceIp, devStatus)
        this.ipMap.set(devinfos[cz].deviceName + ' ' + devinfos[cz].deviceIp, devinfos[cz].deviceIp)
      }
    },
    filterInfo () {  // 根据条件进行过滤
      this.onlineinfos = []
      this.abnormalinfos = []
      this.deletedevinfos = []
      this.combineinfos = []
      for (var index = 0; index < this.deviceinfos.length; index++) {
        if (this.deviceinfos[index].deviceStatus === 0) {  // 在线
          this.onlineinfos.push(this.deviceinfos[index])
        } else if (this.deviceinfos[index].deviceStatus === 2) {  // 离线
          this.abnormalinfos.push(this.deviceinfos[index])
        } else if (this.deviceinfos[index].deviceStatus === -1) {  // 删除
          this.deletedevinfos.push(this.deviceinfos[index])
        }
        if (this.deviceinfos[index].groupSort !== 0) {  // 证明是组合屏
          this.combineinfos.push(this.deviceinfos[index])
        }
        this.IppasswordMap.set(this.deviceinfos[index].deviceIp, this.deviceinfos[index].vncPwd)
      }
      // console.log('在线:' + this.onlineinfos.length + '离线:' + this.abnormalinfos.length + '删除' + this.deletedevinfos.length + '组合屏' + this.combineinfos.length)
    },
    accordingDeviceType (devinfos) {  // 根据状态一览
      this.typeRoot = []  // 初始化,typeRoot代表以类型为根节点
      this.deviceNameMap = {}
      this.deviceNameMap = new Map()
      this.storeMap = {}
      this.storeMap = new Map()
      this.ipMap = {}
      this.ipMap = new Map()

      // 获取根节点 类型
      let cnt = 0
      let typeArray = null
      // 类型 第一层
      let typeList = []  // 存放根节点个数元素
      let TypeObjList = []  // List[Obj,Obj,...] Obj=>同类型里面的数据

      for (var root = 0; root < devinfos.length; root++) {
        if (!this.storeMap.has(devinfos[root].displayType)) {  // 类型根节点为空过滤掉
          if (devinfos[root].displayType === null) {
            typeArray = {label: 'others', children: []}
          } else {
            if (typeof this.businessMap.get(devinfos[root].displayType) === 'undefined') {
              typeArray = {label: devinfos[root].displayType, children: []}
            } else {
              typeArray = {label: this.businessMap.get(devinfos[root].displayType), children: []}
            }
          }
          this.typeRoot.push(typeArray)
          this.storeMap.set(devinfos[root].displayType, cnt++)
          if (devinfos[root] != null) {
            let dev = devinfos[root].displayType
            typeList.push(dev)
          }
        }
      }
      for (var c = 0; c < typeList.length; c++) {  // 类型
        let lType = []
        for (var j = 0; j < devinfos.length; j++) {
          if (devinfos[j].displayType === typeList[c]) {
            lType.push(devinfos[j])  // 插入
          }
        }
        TypeObjList.push(lType)
      }

      for (var ii = 0; ii < TypeObjList.length; ii++) {
        let temp = TypeObjList[ii]
        this.typeVisit(TypeObjList[ii], this.storeMap.get(temp[0].displayType), devinfos)
      }
    },
    accordingTerminal (devinfos) {   // 根据航站楼一览 (航站楼(T1~T4,other)->类型(等于0不显示,大于0显示个数)->ip等详细信息)
      this.terminalRoot = []  // 初始化,terminalRoot代表以候机楼为根节点
      this.deviceNameMap = {}
      this.deviceNameMap = new Map()
      this.storeMap = {}
      this.storeMap = new Map()
      this.ipMap = {}
      this.ipMap = new Map()

      // 获取根节点 候机楼
      let cnt = 0
      let terminalArray = null
      // 类型 第一层
      let terminalList = []  // 存放根节点个数元素
      let terminalObjList = []  // List[Obj,Obj,...] Obj=>同类型里面的数据

      for (var root = 0; root < devinfos.length; root++) {
        if (!this.storeMap.has(devinfos[root].terminal)) {   // 候机楼根节点为空过滤掉
          if (devinfos[root].terminal === null) {
            terminalArray = {label: 'others', children: []}
          } else {
            terminalArray = {label: devinfos[root].terminal, children: []}
          }
          this.terminalRoot.push(terminalArray)
          this.storeMap.set(devinfos[root].terminal, cnt++)
          if (devinfos[root] != null) {
            terminalList.push(devinfos[root].terminal)
            // console.log(devinfos[root].deviceType)
          }
        }
      }
      for (var c = 0; c < terminalList.length; c++) {  // 候机楼
        let lTerminal = []
        for (var j = 0; j < devinfos.length; j++) {
          if (devinfos[j].terminal === terminalList[c]) {
            lTerminal.push(devinfos[j])  // 插入
          }
        }
        terminalObjList.push(lTerminal)
      }

      for (var ii = 0; ii < terminalObjList.length; ii++) {
        this.terminalVisit(terminalObjList[ii], this.storeMap.get(terminalObjList[ii][0].terminal), devinfos)
      }
    },
    // 获取设备信息列表
    getDeviceInfo () {
      // let para = Object.assign({}, this.filters, this.$refs['page'].queryParam())
      // this.filters.deviceNo = 'DJYD25-3'
      // this.menuFunction()
      // this.IppasswordMap = {}
      // this.IppasswordMap = new Map()
      this.getDataLoading = true
      this.filters.airportCode = this.airportCode
      // console.log(this.airportCode)
      let para = Object.assign({}, this.filters)
      // document.getElementsByClassName('el-tree-node__content').attr('id', 'hello')
      // console.log('122134')
      API.getDeviceInfoAll().go(para).then((data) => {   // 请求所有的数据
        if (data.ok) {
          // this.$refs['page'].set('total', data.attr.data.pager.recordCount)
          this.deviceinfos = data.attr.data.list
          // alert(this.deviceinfos.length)
          // console.log(this.deviceinfos.length)
          // this.setData()
        } else {
          this.$notify(util.notifyBody(false, data.msg))
        }
        this.accordingTerminal(this.deviceinfos)
        this.accordingDeviceType(this.deviceinfos)
        this.TerminalRadio = 1   // 复位
        this.typeRadio = 1  // 复位
        this.getDataLoading = false
        this.filterInfo()
        this.stompConnect()
      })
    },
    requestFullScreen: function (element) {
        let requestMethod = element.requestFullScreen || element.webkitRequestFullScreen || element.mozRequestFullScreen || element.msRequestFullScreen
        // console.log(requestMethod)
        if (requestMethod) {
            requestMethod.call(element)
        } else if (typeof window.ActiveXObject !== 'undefined') {
            let wscript = new window.ActiveXObject('WScript.Shell')
            if (wscript !== null) {
                wscript.SendKeys('{F11}')
            }
        }
    },
    // 本机ip,被监控的ip,端口号,token
    joint: function (hostip, hostPort, token) {
        var url = ''
        url += 'http://' + hostip + ':' + hostPort + '/vnc.html?path=websockify/?token=' + token
        return url
    },
    // 全屏切换成全屏源码那套
    fullScreen: function (id) {
        this.position = id
        // console.log('id' + id)
        let elem = document.getElementById(this.position)
        // elem.src = 'http://192.168.1.125:6081/vnc.html?path=websockify/?token=host4'
        // elem.nodeValue = '<iframe src="http://192.168.1.107:6081/vnc.html?path=websockify/?token=host4" name="iframe_index" style=“padding-right:30px” id="iframe_index" scrolling="no" frameborder=no>'
        this.isFullScreen = true
        this.requestFullScreen(elem)
        window.addEventListener('fullscreenchange', this.iframeChangetoOrigin)
        window.addEventListener('mozfullscreenchange', this.iframeChangetoOrigin)
        window.addEventListener('webkitfullscreenchange', this.iframeChangetoOrigin)
        window.addEventListener('msfullscreenchange', this.iframeChangetoOrigin)
    },
    // 清除所有被监控的vnc列表
    clearAll: function () {
        // let elem = document.getElementById('row_id')
        // elem.remove(elem)
      this.$confirm('是否清空目前所有视图?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.iframeList = []
        this.$message({
          type: 'success',
          message: '清空成功'
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消清空'
        })
      })
    },
    // iframe切换成缩略图源码那套
    iframeChangetoOrigin: function () {
        let elem = document.getElementById(this.position)
        let host = ''
        for (var i = 0; i < this.iframeList.length; i++) {
            let iframe = this.iframeList[i]
            if (iframe.ip === this.position) {  // 如果找到这个请求全屏的元素
                host = iframe.ip
                break
            }
        }
        // console.log('host:' + host)
        if (typeof host === 'undefined') {  // src保留原来的
            // console.log('进来了!')
            this.isFullScreen = true
            elem.focus()
        } else {
          if (!this.isFullScreen) {
            this.isFullScreen = true
            // document.getElementById('noVNC_screen').zoom = 1.0
            // elem.src = 'http://192.168.1.110:6080/vnc.html?path=websockify/?token=' + host
            let monitor = 'http://' + this.monitorIp + ':' + this.monitorSmallPort   // 宿主机ip,全屏完整的端口.没修改过源码
            this.port = this.monitorSmallPort
            // console.log('改变前:' + elem.src)
            elem.src = monitor + '/vnc.html?path=websockify/?token=' + host
            // console.log('改变后:' + elem.src)
            // this.sendMessage(host, this.monitorOriginPort)
            elem.focus()
        } else {
            this.isFullScreen = false
            let monitor = 'http://' + this.monitorIp + ':' + this.monitorOriginPort  // 宿主机ip,缩略图的端口,修改后源码那份
            // elem.src = 'http://192.168.1.110:6081/vnc.html?path=websockify/?token=' + host
            this.port = this.monitorOriginPort
            // console.log('改变前:' + elem.src)
            elem.src = monitor + '/vnc.html?path=websockify/?token=' + host
            // console.log('改变后:' + elem.src)
            // this.sendMessage(host, this.monitorSmallPort)
            elem.focus()
        }
      }
    },
    translate () {   // 把代号翻译成中文
        this.businessMap = new Map()
        this.typeAndCodeMap = new Map()
        let para = {enumType: 'BUSINESSTYPE'}
        API.getEnumInfo().go(para).then((data) => {
          if (data.ok) {
            this.deviceBusiness = data.attr.data.list
            for (var i = 0; i < this.deviceBusiness.length; i++) {
              this.businessMap.set(this.deviceBusiness[i].enumNo, this.deviceBusiness[i].displayValue)
              this.typeAndCodeMap.set(this.deviceBusiness[i].displayValue, this.deviceBusiness[i].enumNo)
            }
          } else {
            this.$notify(util.notifyBody(false, data.msg))
          }
        })
    },
    closePage: function (id) {  // 关闭页面
        this.deleteing = true  // 判断条件，删除的时候才触发
        this.position = id  // 找到要被删除的id
        let _this = this
        setTimeout(() => {
            let list = []
            for (var i = 0; i < this.iframeList.length; i++) {
                let iframe = this.iframeList[i]
                if (iframe.ip !== id) {
                    list.push(iframe)
                } else {
                    iframe.isAdd = false  // 被删除了还没被添加
                }
            }
            _this.iframeList = list
            _this.position = null
            _this.deleteing = false
        }, 1000)
    },
    addPage: function (ip) {  // 根据IP判断，不重复添加
      if (this.iframeList.length > 15) {   // 超过16个禁止添加,提示请先删除
        this.$notify({
          title: '警告',
          message: '添加已达上限，请关闭一些窗口后继续添加',
          type: 'warning'
        })
        return
      }
      var isAddflag = false
      for (var i = 0; i < this.iframeList.length; i++) {
        if (ip === this.iframeList[i].ip) {
          // 不添加
          isAddflag = true
          this.$message({
            message: '已在监控列表中,不可重复添加哦!',
            type: 'warning'
          })
          break
        }
      }
      // let list = []
      if (!isAddflag) {
        this.iframeList.push(Object.assign({}, this.vncList[0]))
        this.$message({
          message: '添加成功!',
          type: 'success'
        })
      }
      // console.log(this.iframeList)
    },
    deleteDialog: function (id) {
      this.$confirm('是否删除当前VNC窗口?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.closePage(id)  // 如果确定删除,提示删除成功
        this.$message({
          type: 'success',
          message: '删除成功'
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    setAirportInfo () {
      this.$refs['city'].setCitys(this.subscribeAirports)
    },
    sendMessage (ip, port) {   // iframe加载完成后调用
      let iframe = document.getElementById(ip).contentWindow
      // this.contextMenu(iframe)
      let vncPwd = this.IppasswordMap.get(ip)
      // console.log('端口:' + port)
      let monitor = 'http://' + this.monitorIp + ':' + port
      // console.log('vncPwd:' + vncPwd)
      iframe.postMessage(vncPwd, monitor)     // vncPwd为密码,后面为宿主机ip
    },
    renderContent (h, { node, data, store }) {
      if (this.radio === '类型' && this.getDataLoading === false) {   // 等待加载完成
        return h(
          'span',
          [
            h(
              'span',
              [
                h('span', {}, [node.label])
              ]
            ),
            node.isLeaf ? h(    // 如果是叶子节点
              'span',
              { style: { paddingLeft: '15px' } },
              [
                this.statusMap.get(node.label) === '在线' ? (h(
                  'el-tag',
                  {
                    class: 'el-tag--success'
                  },
                  this.statusMap.get(node.label)
                  // this.data2[4].children.push(ip) 其他
                )) : (this.statusMap.get(node.label) === '异常' ? (h(
                  'el-tag',
                  {
                    class: 'el-tag--danger'
                  },
                  this.statusMap.get(node.label)
                  // this.data2[4].children.push(ip) 其他
                )) : this.statusMap.get(node.label) === '删除' ? (h(
                  'el-tag',
                  {
                    class: 'el-tag--warning'
                  },
                  this.statusMap.get(node.label)
                  // this.data2[4].children.push(ip) 其他
                )) : '')
              ]
            ) : h(   // 如果不是叶子节点
              'span',
              { style: { paddingLeft: '15px' } },
              [
                h(
                  'el-tag',
                  {
                    class: 'el-tag--primary'
                  },
                  data.children.length + '个'
                )
              ]
            )
          ]
        )
      } else if (this.radio === '航站楼' && this.getDataLoading === false) {
        return h(
          'span',
          [
           h(
              'span',
              [
                h(
                  'span',
                  [ node.label ]
                )
              ]
            ),
            node.isLeaf ? h(    // 如果是叶子节点
              'span',
              { style: { paddingLeft: '15px' } },
              [
                this.terminalstatusMap.get(node.label) === '在线' ? (h(
                  'el-tag',
                  {
                    class: 'el-tag--success'
                  },
                  this.terminalstatusMap.get(node.label)
                  // this.data2[4].children.push(ip) 其他
                )) : (this.terminalstatusMap.get(node.label) === '异常' ? (h(
                  'el-tag',
                  {
                    class: 'el-tag--danger'
                  },
                  this.terminalstatusMap.get(node.label)
                  // this.data2[4].children.push(ip) 其他
                )) : this.terminalstatusMap.get(node.label) === '删除' ? (h(
                  'el-tag',
                  {
                    class: 'el-tag--warning'
                  },
                  this.terminalstatusMap.get(node.label)
                  // this.data2[4].children.push(ip) 其他
                )) : '')
              ]
            ) : h(   // 如果不是叶子节点
              'span',
              { style: { paddingLeft: '15px' } },
              [
                h(
                  'el-tag',
                  {
                    class: 'el-tag--primary'
                  },
                  data.children.length + '个'
                )
              ]
            )
          ]
        )
      }
    }
  },
  mounted () {
      this.showFlag = true
      this.IppasswordMap = {}
      this.IppasswordMap = new Map()
      this.iframeList = []
      this.translate()
      this.SubscribeAirports()
      this.radio = '航站楼'
      this.monitorIp = vncConfig.monitorIp   // 宿主机IP
      this.monitorSmallPort = vncConfig.monitorSmallPort   // 宿主机所在端口
      this.port = vncConfig.monitorSmallPort
      this.setAirportInfo()
      this.getDeviceInfo()
      document.getElementById('accordingType').style = 'display:none'  // 隐藏根据类型过滤
  }
}
</script>
<style>
.shut {
    position: absolute;
    width: 35px;
    height: 30px;
    top: 0px;
    right: 0px;
    background: url(./../../../static/close.png) no-repeat 0px 0px;
}
.shut:hover{
    background:url(./../../../static/close_red.png) no-repeat 0px 0px;
}
.textarea{
    position: absolute;
    width: 100%;
    height: 100%;
    top: 0px;
    left: 0px;
}
.deviceName{
  top: 15px;
  left: 0px;
}
.full{
    position: absolute;
    width: 35px;
    height: 30px;
    top: 0px;
    right: 30px;
    background: url(./../../../static/max.png) no-repeat 0px 0px;
}
.full:hover{
    background:url(./../../../static/max_pink.png) no-repeat 0px 0px;
}
.divborder{
    position: relative;
    border: 2px solid gray;
    width: 100%;
    height: 100%;
    margin-top: 20px;
    margin-left: 10px;
    margin-bottom: 10px;
    margin-right: 10px;
}
.iframestyle{
    position: relative;
    width: 100%;
    height: 100%;
    margin-top:30px;
    margin-bottom: -3px;
}

.before-close {
  transform: scale(0);
  transition: all 1s ease-out;
}
.navMenuPadding{
  margin-left: 20px;
  margin-top: 20px;
}
.height{
  overflow: auto;
  height: auto;
  max-height: 850px;
}
.height2{
  overflow: auto;
  height: auto;
  max-height: 850px;
}
.width{
  position: relative;
  margin-top: 15px;
  overflow: auto;
}
.marginTop{
  margin: 10px;
}
.leftMargin{
  margin-left: 10px;
}
.el-tree-node > .el-tree-node__children {
  overflow: inherit !important;
  background-color: transparent;
}
.filter{
  position: relative;
}
.input{
  margin-top: 10px;
}
.el-tag--success {
    background-color: rgba(18,206,102,.1);
    border-color: rgba(18,206,102,.2);
    color: #13ce66;
}
.el-tag--danger {
    background-color: rgba(255,73,73,.1);
    border-color: rgba(255,73,73,.2);
    color: #ff4949;
}
.el-tag--primary {
    background-color: rgba(32,160,255,.1);
    border-color: rgba(32,160,255,.2);
    color: #20a0ff;
}
.el-tag--warning {
    background-color: rgba(247,186,41,.1);
    border-color: rgba(247,186,41,.2);
    color: #f7ba2a;
}
.el-tag {
    border-color: #e4e8f1;
}
.vncMargin {
  margin-left: 30px;
}
.content-container{   /* 背景全白 */
  background: #fff !important;
}
.selectAirportStyle {
  width: 67%;
}
</style>
