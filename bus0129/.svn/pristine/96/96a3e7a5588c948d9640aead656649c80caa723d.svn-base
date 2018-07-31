/*
 * @Author: ylj
 * @Date: 2018-02-23 20:39:27
 * @Last Modified by: ylj
 * @Last Modified time: 2018-03-07 16:24:13
 */
<template>
  <section class="devIrrRepairClss">
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
          <el-select v-model="filters.deviceNo" filterable placeholder="设备编码" clearable>
            <el-option
              v-for="(item, index) in deviceNumList"
              :key="index"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-select v-model="filters.deviceIp" filterable placeholder="设备IP" clearable>
            <el-option
              v-for="item in deviceIpList"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getDeviceIrrInfos" icon="search">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="handleAdd" icon="edit">新增</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table v-bind:data="deviceirrinfos" highlight-current-row v-loading="deviceirrinfosLoading"  @selection-change="selsChange" :height="tableHeight" style="width: 100%;">
      <!--<el-table-column
        type="selection"
        width="45">
      </el-table-column>-->
      <el-table-column type="index" label="序号" width="45">
        <template slot-scope="scope">
          {{pageSize * (pageNumber - 1) + scope.$index + 1}}
        </template>
      </el-table-column>
      <el-table-column prop="deviceIrrStatus" label="状态" width="80" sortable>
        <template slot-scope="scope" >
          <el-tag v-for="item in deviceStatusList" :key="item.id" v-if="scope.row.deviceIrrStatus === parseInt(item.enumNo)" :type="item.remark">
            {{item.displayValue}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="airportCode" label="机场代码" width="90" sortable></el-table-column>
      <el-table-column prop="deviceNo" label="设备编码" width="90" sortable></el-table-column>
      <el-table-column prop="deviceIp" label="设备IP" width="110" sortable></el-table-column>
      <el-table-column prop="displaytype" label="业务类型" width="100" sortable></el-table-column>
      <el-table-column prop="terminal" label="航站楼" width="70" sortable></el-table-column>
      <el-table-column prop="area" label="区域" width="60" sortable></el-table-column>
      <el-table-column prop="reportUser" label="报修人员" width="100" sortable></el-table-column>
      <el-table-column prop="reportingTime" label="报修时间" :formatter="dataFormat" width="150" sortable></el-table-column>
      <el-table-column prop="irrdescription" label="异常描述" min-width="200" sortable show-overflow-tooltip ></el-table-column>
      <el-table-column label="操作" width="120" fixed="right" >
        <template slot-scope="scope">
          <el-button @click="handleEdit(scope.$index, scope.row)" type="success" size="small">编辑</el-button>
          <el-button @click="handleDel(scope.$index, scope.row)" type="danger" size="small">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--分页-->
    <pagination :to="getDeviceIrrInfos" ref="page"></pagination>

    <!--删除窗口-->
    <common-delete
      :to="API.removeDeviceIrregularInfo().go"
      :callback="getDeviceIrrInfos"
      :labelWidth="100"
      ref="delConfirm"></common-delete>

    <!--新增界面-->
    <add-or-update
      :to="API.addDeviceIrregularInfo().go"
      :callback="getDeviceIrrInfos"
      ref="devIrregularAddForm"></add-or-update>

    <!--编辑界面-->
    <add-or-update
      :to="API.editDeviceIrregularInfo().go"
      :callback="getDeviceIrrInfos"
      ref="devIrregularEditForm"></add-or-update>
  </section>
</template>

<script>
  import Util from '../../../common/js/util'
  import Butil from '../../../common/js/base-util'
  import commonDelete from './../../../components/CommDelete'
  import API from '../../../api'
  import Pagination from '../../../components/Pagination'
  import addOrUpdate from './AddOrUpdate'
  import CityName from '../../../components/CityName'
  export default {
    data () {
      return {
        filters: {
          deviceNo: '',
          deviceip: '',
          airportCode: JSON.parse(sessionStorage.getItem('userStorage')).user.aiisAirports[0].airportCode
        },
        airport: [],
        subAirportLs: [],
        deviceirrinfos: [],
        deviceAllInfos: [], // 获取全部数据
        deviceInfo: { id: '', parentId: '', deviceNo: '', deviceName: '', deviceModel: '', deviceOrigin: null, deviceFactory: null, deviceIp: '', displayType: '', deviceStatus: 0, deviceDes: null, deviceCom: '', groupId: 0, linkResId: 0, hostName: null, macAddr: null, osName: null, cpuDes: null, memoryDes: null, hdDes: null, adminName: null, adminPwd: null, vncPwd: '', terminal: '', scrollText: '', remark: '', updateTime: null, groupSort: 0, airportCode: null, dynamicScrollTextSourceId: 0, isAdd: 0, isShowPage: 0, pageSizeC: 0, vncPort: 0, resolutionX: 0, resolutionY: 0, backupScrollText: null, notChangePage: '', screenOpenTime: null, screenCloseTime: null, area: null, openComNo: null, closeComNo: null, comCommandType: '', terminalLevel: null, linkResType: '' },
        deviceStatusList: [],
        tableHeight: 450,
        deviceirrinfosLoading: false,
        sels: [],
        API: API,
        deviceIpList: [],
        deviceNumList: []
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
      commonDelete: commonDelete,
      pagination: Pagination,
      addOrUpdate: addOrUpdate,
      cityName: CityName
    },
    methods: {
      getAirportCode (airportData) {
        this.filters.airportCode = airportData
        this.getDeviceIrrInfos()
      },
      getDeviceIrrInfos () {
        let para = Object.assign({}, this.filters, this.$refs['page'].queryParam())
        this.listLoading = true
        API.getDeviceIrrInfos().go(para).then((data) => {
          if (data.ok) {
            this.$refs['page'].set('total', data.attr.data.pager.recordCount)
            this.deviceirrinfos = data.attr.data.list
          } else {
            this.$notify(Util.notifyBody(false, data.msg))
          }
          this.listLoading = false
        })
      },
      getDeviceAllInfo () {  // 获取设备全部信息
        this.deviceIpList = []
        this.deviceNumList = []
        let para = Object.assign({}, this.filters)
        this.listLoading = true
        API.getDeviceInfoAll().go(para).then((data) => {   // 请求所有的数据
        if (data.ok) {
          this.deviceAllInfos = data.attr.data.list
          for (let i = 0; i < this.deviceAllInfos.length; i++) {
            this.deviceIpList.push({label: this.deviceAllInfos[i].deviceIp + ' / ' + this.deviceAllInfos[i].deviceName, value: this.deviceAllInfos[i].deviceIp})
            this.deviceNumList.push({label: this.deviceAllInfos[i].deviceNo + ' / ' + this.deviceAllInfos[i].deviceName, value: this.deviceAllInfos[i].deviceNo})
          }
        } else {
          this.$notify(Util.notifyBody(false, data.msg))
        }
        this.listLoading = false
        })
      },
      bindData () {
        // 1. 获取设备异常信息列表
        this.getDeviceStatusList()
        // 2. 获取用户订阅机场
        this.getSubAirportList()
        this.filters.airportCode = this.$refs['city'].airportCode
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
      getSubAirportList () {
        // 获取用户订阅机场
        this.subAirportLs = Butil.getSubscribeAirports()
        this.setAirportInfo(this.subAirportLs)
      },
      selsChange (sels) {
        this.sels = sels
      },
      handleCommand (command, self) {
        var node = self.$vnode.data.attrs
        /* eslint-disable */
        eval('this.' + command).call(this, node.index, node.row)
        /* eslint-enable */
      },
      // 删除
      handleDel (index, row) {
        this.$refs['delConfirm'].del(row)
      },
      // 显示新增界面
      handleAdd () {
        this.$refs['devIrregularAddForm'].show(null, this.filters.airportCode)
      },
      // 显示编辑界面
      handleEdit (index, row) {
        this.$refs['devIrregularEditForm'].show(row, this.filters.airportCode)
      },
      addRepairRecord () {
        // 维修单登记
      },
      setAirportInfo (subAirportLs) {
        this.$refs['city'].setCitys(subAirportLs)
      },
      dataFormat (row, column, cellValue) {
        let date = row[column.property]
        if (date === undefined) {
          return ''
        }
        return Util.formatDate.flightDateFmt('yyyy-MM-dd hhmm', date, false)
      }
    },
    mounted () {
      this.bindData()
      this.getDeviceIrrInfos()
      this.getDeviceAllInfo()
    }
  }
</script>

<style lang="scss">
.devIrrRepairClss {
  .dropDown {
    width: 250px
  }
  .el-table {
    margin-left: 10px !important;
  }
  .el-table .cell, .el-table th>div {
      padding-left: 4px!important;
      padding-right: 2px!important;
      box-sizing: border-box;
      text-overflow: ellipsis;
  }
}
</style>
