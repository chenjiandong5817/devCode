/*
 * @Author: chenyang
 * @Date: 2017-09-25 16:53:22
 * @Last Modified by: chenyang
 * @Last Modified time: 2017-11-09 16:20:59
 */
<template>
  <section>
    <!--搜索工具栏（整行、置顶）-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <city-name
            class="dropDown"
            ref="city"
            v-on:getAirportName = "getAirportCode">
          </city-name>
        </el-form-item>
        <el-form-item>
          <el-select v-model="filters.displayType" placeholder="业务类型" @change="autoShow" :filterable="true">
              <el-option
                v-for="item in deviceTypeLabels"
                :key="item.value"
                :label="item.text"
                :value="item.value">
              </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-input v-model="filters.deviceNo" placeholder="设备编码"></el-input>
        </el-form-item>
        <el-form-item>
          <el-input v-model="filters.deviceIp" placeholder="设备IP"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getDeviceInfo" icon="search">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="handleAdd" icon="edit">新增</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="batchDealWith" icon="setting">批量操作</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table v-bind:data="deviceinfos" highlight-current-row v-loading="deviceInfosLoading" @selection-change="selsChange" :height="tableHeight" style="width: 100%;">
      <el-table-column type="index" width="100">
        <template scope="scope">
          {{pageSize * (pageNumber - 1) + scope.$index + 1}}
        </template>
      </el-table-column>
      <el-table-column prop="deviceStatus" label="状态" width="100" sortable>
        <template scope="scope" >
          <el-tag v-for="item in deviceStatusList" :key="item.id" v-if="scope.row.deviceStatus === parseInt(item.enumNo)" :type="item.remark">
            {{item.displayValue}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="airportCode" label="机场代码" width="120" sortable></el-table-column>
      <el-table-column prop="terminal" label="航站楼" width="100" sortable></el-table-column>
      <el-table-column prop="displayType" label="类型" width="100" :formatter="formatter" sortable></el-table-column>
      <el-table-column prop="groupSort" label="组合" width="100" sortable></el-table-column>
      <el-table-column prop="deviceName" label="设备名" width="200" sortable></el-table-column>
      <el-table-column prop="deviceNo" label="设备编码" width="150" sortable></el-table-column>
      <el-table-column prop="deviceIp" label="设备IP" width="150" sortable></el-table-column>
      <el-table-column prop="scrollText" label="滚动文本" min-width="200" sortable show-overflow-tooltip ></el-table-column>
      <el-table-column label="操作" width="300" fixed="right" >
        <template scope="scope">
          <el-button @click="handleEdit(scope.$index, scope.row)" type="success" size="small">编辑</el-button>
          <el-button @click="handleDel(scope.$index, scope.row)" type="danger" size="small">删除</el-button>
          <el-button @click="clearText(scope.$index, scope.row)" type="text" size="small">清空</el-button>
          <el-button @click="setText(scope.$index, scope.row)" type="text" size="small">设置</el-button>
          <el-button @click="backupText(scope.$index, scope.row)" type="text" size="small">备份</el-button>
          <el-button @click="restoreText(scope.$index, scope.row)" type="text" size="small">还原</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--分页-->
    <pagination :to="getDeviceInfo" ref="page"></pagination>

    <!--删除窗口-->
    <common-delete
      :to="API.removeDeviceInfo().go"
      :callback="getDeviceInfo"
      :labelWidth="100"
      ref="delConfirm"></common-delete>

    <!--新增界面-->
    <add-or-update
      :to="API.addDeviceInfo().go"
      :callback="getDeviceInfo"
      ref="deviceAddForm"></add-or-update>

    <!--编辑界面-->
    <add-or-update
      :to="API.editDeviceInfo().go"
      :callback="getDeviceInfo"
      ref="deviceEditForm"></add-or-update>

    <!--滚动文本-->
    <setting-scroll-text
      :to="API.editDeviceInfo().go"
      :callback="getDeviceInfo"
      ref="inputForm">
    </setting-scroll-text>

    <!--批量处理界面-->
    <batch-deal
      :to="API.editDeviceInfo().go"
      :callback="getDeviceInfo"
      ref="batch">
    </batch-deal>
  </section>
</template>

<script>
  import Util from '../../common/js/util'
  import chooseDialog from './../../components/CommChooseGrid'
  import commonDelete from './../../components/CommDelete'
  import API from '../../api'
  import Pagination from '../../components/Pagination'
  import addOrUpdate from '../device/deviceForm/AddOrUpdate'
  import settingScrollText from '../device/deviceForm/SettingScrollText'
  import batchDeal from '../device/deviceForm/BatchDeal'
  import LoginInfo from '../../vuex/store'
  import CityName from '../../components/CityName'
  export default {
    data () {
      return {
        filters: {
          deviceno: '',
          deviceip: '',
          airportCode: JSON.parse(sessionStorage.getItem('userStorage')).user.aiisAirports[0].airportCode,
          displayType: ''
        },
        airport: [],
        airportMap: {},
        deviceBusiness: [],
        typeAndCodeMap: {},
        codeAndTypeMap: {},
        subscribeAirportsLables: [],
        subscribeAirports: [],
        deviceinfos: [],
        deviceAllInfos: [], // 获取全部数据
        deviceInfo: { id: '', parentId: '', deviceNo: '', deviceName: '', deviceModel: '', deviceOrigin: null, deviceFactory: null, deviceIp: '', displayType: '', deviceStatus: 0, deviceDes: null, deviceCom: '', groupId: 0, linkResId: 0, hostName: null, macAddr: null, osName: null, cpuDes: null, memoryDes: null, hdDes: null, adminName: null, adminPwd: null, vncPwd: '', terminal: '', scrollText: '', remark: '', updateTime: null, groupSort: 0, airportCode: null, dynamicScrollTextSourceId: 0, isAdd: 0, isShowPage: 0, pageSizeC: 0, vncPort: 0, resolutionX: 0, resolutionY: 0, backupScrollText: null, notChangePage: '', screenOpenTime: null, screenCloseTime: null, area: null, openComNo: null, closeComNo: null, comCommandType: '', terminalLevel: null, linkResType: '' },
        deviceStatusList: [],
        tableHeight: 450,
        deviceTypeLabels: [],
        deviceTypeList: [],
        deviceInfosLoading: false,
        sels: [],
        API: API
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
    components: {
      chooseDialog: chooseDialog,
      commonDelete: commonDelete,
      pagination: Pagination,
      addOrUpdate: addOrUpdate,
      settingScrollText: settingScrollText,
      batchDeal: batchDeal,
      cityName: CityName
    },
    methods: {
      getAirportCode (airportData) {
        // console.log(airportData)
        this.filters.airportCode = airportData
        this.autoShow()
      },
      // 获取设备信息列表
      SubscribeAirports () {
        this.subscribeAirportsLables = []
        this.subscribeAirports = this.getSubscribeAirports()   // 用户订阅的机场列表
        for (let i = 0; i < this.subscribeAirports.length; i++) {
          let label = {text: this.subscribeAirports[i], value: this.subscribeAirports[i]}
          this.subscribeAirportsLables.push(label)
        }
        // this.filters.airportCode = this.subscribeAirports[0]
      },
      autoShow () {
        this.getDeviceInfo()
      },
      formatter (row, column) {
        return this.typeAndCodeMap.get(row.displayType)
      },
      changeToChineseName (row, column) {
        return this.airportMap.get(row.airportCode)
      },
      getDeviceInfo () {   // 分页
        let para = Object.assign({}, this.filters, this.$refs['page'].queryParam())
        this.listLoading = true
        API.getDeviceInfo().go(para).then((data) => {
          if (data.ok) {
            this.$refs['page'].set('total', data.attr.data.pager.recordCount)
            this.deviceinfos = data.attr.data.list
            // console.log(this.deviceinfos)
            // alert(JSON.stringify(this.deviceinfos))
          } else {
            this.$notify(Util.notifyBody(false, data.msg))
          }
          this.listLoading = false
        })
      },
      typeCodeToChinese () {
        this.typeAndCodeMap = new Map()
        this.codeAndTypeMap = new Map()
        let para = {enumType: 'BUSINESSTYPE'}
        API.getEnumInfo().go(para).then((data) => {
          if (data.ok) {
            this.deviceBusiness = data.attr.data.list
            for (var i = 0; i < this.deviceBusiness.length; i++) {
              let label = {text: this.deviceBusiness[i].displayValue, value: this.deviceBusiness[i].enumNo}
              this.deviceTypeLabels.push(label)
              this.typeAndCodeMap.set(this.deviceBusiness[i].enumNo, this.deviceBusiness[i].displayValue)
              this.codeAndTypeMap.set(this.deviceBusiness[i].displayValue, this.deviceBusiness[i].enumNo)
            }
          } else {
            this.$notify(Util.notifyBody(false, data.msg))
          }
          this.loading = false
        })
      },
      getSubscribeAirports () {
        let result = []
        let data = LoginInfo.state.userStorage.user.aiisAirports
        for (let i = 0; i < data.length; i++) {
          result.push(data[i].airportCode)
        }
        return result
      },
      getDeviceAllInfo () {  // 获取设备全部信息
        let para = Object.assign({}, this.filters)
        this.listLoading = true
        API.getDeviceInfoAll().go(para).then((data) => {   // 请求所有的数据
        if (data.ok) {
          this.deviceAllInfos = data.attr.data.list
        } else {
          this.$notify(Util.notifyBody(false, data.msg))
        }
        this.listLoading = false
        })
      },
      batchDealWith () {   // 批量操作
        // console.log('清空')
        this.filters.deviceNo = ''
        this.filters.deviceIp = ''
        this.getDeviceAllInfo()
        this.$refs['batch'].batchDevice(this.deviceAllInfos)
      },
      clearText (index, row) {   // 清空滚动文本
          this.$confirm('是否清空当前滚动文本?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          if (row !== null) {
            this.deviceInfo = row  // 这一行的数据
            this.deviceInfo.scrollText = ''
          }
          let para = { newValue: this.deviceInfo, oldValue: this.oldValue }
          this.listLoading = true
          API.editDeviceInfo().go(para).then((data) => {
            if (data.ok) {
                this.$message({
                message: '清空成功!',
                type: 'success'
              })
            }
            this.listLoading = false
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '取消清空'
          })
        })
      },
      setText (index, row) {   // 设置滚动文本
        this.$refs['inputForm'].setting(index, row)
      },
      backupText (index, row) {   // 备份文本
        this.$confirm('是否备份当前滚动文本?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          if (row) {
            this.deviceInfo = row
            this.deviceInfo.backupScrollText = this.deviceInfo.scrollText  // 滚动文本到备份文本
            let para = { newValue: this.deviceInfo, oldValue: this.oldValue }
            this.listLoading = true
            API.editDeviceInfo().go(para).then((data) => {
              if (data.ok) {
                  this.$message({
                  message: '备份成功!',
                  type: 'success'
                })
              }
              this.listLoading = false
            })
          }
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '取消备份'
          })
        })
      },
      restoreText (index, row) {  // 还原文本
        this.$confirm('是否还原当前滚动文本?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          if (row) {
            this.deviceInfo = row
            this.deviceInfo.scrollText = this.deviceInfo.backupScrollText  // 滚动文本到备份文本
            let para = { newValue: this.deviceInfo, oldValue: this.oldValue }
            this.listLoading = true
            API.editDeviceInfo().go(para).then((data) => {
              if (data.ok) {
                  this.$message({
                  message: '还原成功!',
                  type: 'success'
                })
              }
              this.listLoading = false
            })
          }
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '取消还原'
          })
        })
      },
      getAir () {
        let para = Object.assign({}, {pageSize: 0})  // 分页问题
        this.airportMap = new Map()
        this.listLoading = true
        API.getAirportListPage().go(para).then((data) => {
          if (data.ok) {
            // console.log('进来了')
            this.airport = data.attr.data.list
            for (let i = 0; i < this.airport.length; i++) {
              this.airportMap.set(this.airport[i].iatacode, this.airport[i].displaycnname)
            }
          } else {
            this.$notify(Util.notifyBody(false, data.msg))
          }
          this.listLoading = false
        })
      },
      bindData () {
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
      typeApi () {
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
      selsChange: function (sels) {
        this.sels = sels
      },
      handleCommand: function (command, self) {
        var node = self.$vnode.data.attrs
        /* eslint-disable */
        eval('this.' + command).call(this, node.index, node.row)
        /* eslint-enable */
      },
      // 删除
      handleDel: function (index, row) {
        // console.log(row)
        this.$refs['delConfirm'].del(row)
      },
      // 显示新增界面
      handleAdd: function () {
        this.$refs['deviceAddForm'].show()
      },
      // 显示编辑界面
      handleEdit: function (index, row) {
        this.$refs['deviceEditForm'].show(row)
      },
      filterMethod: function (value, row) {
        return row.deviceStatus === value
      },
      setAirportInfo () {
        this.$refs['city'].setCitys(this.subscribeAirports)
      }
    },
    mounted () {
      this.getAir()
      this.bindData()
      this.getDeviceInfo()
      this.getDeviceAllInfo()
      this.typeCodeToChinese()
      this.SubscribeAirports()
      this.setAirportInfo()
    }
  }

</script>

<style scoped>
  .dropDown {
    width: 250px
  }
  .margin{
    margin-left: 30px
  }
  .textColor{
    color: #20a0ff
  }
</style>
