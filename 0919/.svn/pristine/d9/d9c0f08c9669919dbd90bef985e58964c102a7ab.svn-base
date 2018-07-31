/*
 * @Author: chenyang
 * @Date: 2017-10-19 09:50:48
 * @Last Modified by: chenyang
 * @Last Modified time: 2017-11-21 09:12:40
 * 播放列表管理模块树形结构
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
            <el-input class="input" placeholder="输入IP地址或设备名称进行过滤" v-model="filterIp">
            </el-input>
          </div>
          <div id='terminal' v-show="isShow">
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
              ref="terminal">
            </tree>
          </div>
        <div id='accordingType' v-show="!isShow">
          <tree
            class="filter-tree height2 width"
            :data="typeRoot"
            :props="defaultProps"
            node-key="id"
            :filter-node-method="filterNode"
            highlight-current
            @node-click='devTypeNodeClick'
            :render-content='renderContent'
            v-loading='getDataLoading'
            :default-expand-all = 'true'
            ref="actype">
          </tree>
        </div>
      </div>
      </el-col>
      <el-col :span="16" class="marginTop">
        <el-button type="primary" @click="handleAdd">新增</el-button>
      </el-col>
      <pagination :to="getDeviceConfigList" ref="page"></pagination>
      <display-add-or-update
        v-on:delAndEdit = "sendToOneDevice"
        ref='display'
        >
      </display-add-or-update>
      <display-muilt-add
        v-on:sendToDevice = "sendToDeviceList"
        :callback="getDeviceConfigList"
        ref="displayMuiltAdd">
      </display-muilt-add>
    </section>
</template>

<script>
import util from '../../common/js/util'
import API from '../../api'
import Tree from './../../components/tree/tree'
import LoginInfo from '../../vuex/store'
import Pagination from '../../components/Pagination'
import DisplayAddOrUpdate from '../device/DisplayAddOrUpdate'
import DisplayMuiltAdd from './../device/DisplayMuiltAdd'
import CityName from '../../components/CityName'
import StompUtil from './../../common/stomp/stomp-util'
import JsonList from './../../common/menu/jsonList'
// let baseId = 100
export default {
  components: {
    Tree,
    pagination: Pagination,
    displayAddOrUpdate: DisplayAddOrUpdate,
    displayMuiltAdd: DisplayMuiltAdd,
    cityName: CityName
  },
  data () {
    return {
        clickIp: '', // 点击的ip
        airportCode: '',
        subscribeAirportsLables: [],
        isShow: false,
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
        allAirportInfo: [],
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
  computed: {
    pageNumber () {
      return this.$refs['page'].get('pageNumber')
    },
    pageSize () {
      return this.$refs['page'].get('pageSize')
    }
  },
  methods: {
    sendToOneDevice (id) {
      let str = ''
      let prefix = 'ROOT' + '.' + this.airportCode + '.'
      for (let j = 0; j < this.allAirportInfo.length; j++) { // 找设备
        if (id === this.allAirportInfo[j].id) {
          let terminal = ''
          let type = ''
          if (this.allAirportInfo[j].terminal === null) {
            terminal = 'others'
          } else {
            terminal = this.allAirportInfo[j].terminal
          }
          if (this.allAirportInfo[j].displayType === null) {
            type = 'others'
          } else {
            type = this.allAirportInfo[j].displayType
          }
          str = terminal + '.' + type + '.' + this.changeIp(this.allAirportInfo[j].deviceIp)
          break
        }
      }
      let sendMessage = prefix + str
      console.log(sendMessage)
      StompUtil.handleSend(sendMessage, JsonList.updatePlayList)  // 发送刷新界面
    },
    sendToDeviceList (deviceIpList) {
      let prefix = 'ROOT' + '.' + this.airportCode + '.'
      /*
          let bottomNode = this.ipMap.get(this.curNode.label)   // ip有点要重新划分组合(IP)
          bottomNode = this.changeIp(bottomNode)  // 划分
          let typeCodeNode = this.typeAndCodeMap.get(this.curNode.parent.label)  // 位置
          let terminalNode = this.curNode.parent.parent.label  // 航站楼
          let sendMessage = prefix + terminalNode + '.' + typeCodeNode + '.' + bottomNode
      */
      console.log(deviceIpList)
      console.log(this.allAirportInfo)
      for (let i = 0; i < deviceIpList.length; i++) {
        let str = ''
        for (let j = 0; j < this.allAirportInfo.length; j++) { // 找设备
          if (deviceIpList[i] === this.allAirportInfo[j].deviceIp) {
            let terminal = ''
            let type = ''
            if (this.allAirportInfo[j].terminal === null) {
              terminal = 'others'
            } else {
              terminal = this.allAirportInfo[j].terminal
            }
            if (this.allAirportInfo[j].displayType === null) {
              type = 'others'
            } else {
              type = this.allAirportInfo[j].displayType
            }
            str = terminal + '.' + type + '.' + this.changeIp(this.allAirportInfo[j].deviceIp)
            break
          }
        }
        let sendMessage = prefix + str
        console.log(sendMessage)
        StompUtil.handleSend(sendMessage, JsonList.updatePlayList)  // 发送刷新界面
      }
    },
    getAirportCode (airportData) {
        // console.log(airportData)
        this.airportCode = airportData
        this.getDeviceInfo()
    },
    getDeviceConfigList () {
      console.log('有数据')
      this.deivceLoading = true
      this.filters.deviceId = this.id
      let params = Object.assign({}, this.filters, this.$refs['page'].queryParam())
      API.getDeviceConfig().go(params).then(res => {
        console.log(res)
        this.deivceLoading = false
        this.deviceConfig = res.attr.data.list
        this.$refs['page'].set('total', res.attr.data.pager.recordCount)
      })
      // this.$refs['display'].getParentSendIp(this.clickIp)
    },
    handleAdd () {
      this.$refs['displayMuiltAdd'].show(this.airportCode)
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
      for (let i = 0; i < data.length; i++) {
        result.push(data[i].airportCode)
      }
      return result
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
    radioGroupClick () {  // 根节点的切换
      if (this.radio === '航站楼') {
        this.isShow = true
      } else if (this.radio === '类型') {
        this.isShow = false
      }
    },
    terminalNodeClick (data) {  // 以航站楼为根节点的树形控件点击事件
      if (data.children.length === 0) {
        let terminalIp = this.ipMap.get(data.label)
        this.clickIp = terminalIp
        this.$refs['display'].getParentSendIp(terminalIp)
      }
      // console.log(terminalIp)
    },
    devTypeNodeClick (data) {   // 在里面做每个Item的点击事件
      if (data.children.length === 0) { // 叶子节点点击才有用
        let devTypeIp = this.ipMap.get(data.label)
        this.clickIp = devTypeIp
        this.$refs['display'].getParentSendIp(devTypeIp)
      }
      // console.log(devTypeIp)
    },
    filterNode (value, data, node) {   // value为输入的值
      if (!value) return true
      if (data.label === null) {
        return ''
      }
      return data.label.indexOf(value) !== -1
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
        let typeNode
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
        // console.log(this.deviceNameMap)
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
    stompConnect () {
      StompUtil.connect()
    },
    // 获取设备信息列表
    getDeviceInfo () {
      // let para = Object.assign({}, this.filters, this.$refs['page'].queryParam())
      // this.filters.deviceNo = 'DJYD25-3'
      // this.menuFunction()
      this.filters.airportCode = this.airportCode
      let para = Object.assign({}, this.filters)
      this.getDataLoading = true
      // document.getElementsByClassName('el-tree-node__content').attr('id', 'hello')
      // console.log('122134')
      API.getDeviceInfoAll().go(para).then((data) => {   // 请求所有的数据
        if (data.ok) {
          // this.$refs['page'].set('total', data.attr.data.pager.recordCount)
          this.deviceinfos = data.attr.data.list
          this.allAirportInfo = data.attr.data.list
          // alert(this.deviceinfos.length)
          // console.log(this.deviceinfos.length)
        } else {
          this.$notify(util.notifyBody(false, data.msg))
        }
        // this.listLoading = false
        this.accordingTerminal(this.deviceinfos)
        this.accordingDeviceType(this.deviceinfos)
        this.getDataLoading = false
      })
    },
    setAirportInfo () {
      this.$refs['city'].setCitys(this.subscribeAirports)
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
          this.loading = false
        })
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
      this.clickIp = ''
      this.translate()
      this.SubscribeAirports()
      this.radio = '航站楼'
      this.setAirportInfo()
      this.getDeviceInfo()
      this.stompConnect()
      document.getElementById('accordingType').style = 'display:none'  // 隐藏根据类型过滤
  }
}
</script>
<style scoped>
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
  margin-top: 10px;
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
