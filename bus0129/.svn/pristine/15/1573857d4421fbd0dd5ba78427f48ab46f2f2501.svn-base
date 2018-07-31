/*
 * @Author: ylj
 * @Date: 2018-02-23 20:39:27
 * @Last Modified by: ylj
 * @Last Modified time: 2018-03-05 20:48:32
 */
<template>
  <section class="devRepairClss">
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
          <el-select v-model="filters.repairStatus" filterable placeholder="维修状态" clearable>
            <el-option
              v-for="(item, index) in devRepairStatusList"
              :key="item.id"
              :label="item.displayValue"
              :value="parseInt(item.enumNo)">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="维修单编号">

          <el-input v-model="filters.repairNo" placeholder="维修单编号" :icon="iconClass" @mouseenter.native="inputHovering = true"
            :onIconClick="cleardata" @mouseleave.native="inputHovering = false" style="width: 70%"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getDevRepairRecords" icon="search">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="handleAdd" icon="edit">新增</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table v-bind:data="devRepairRecords" highlight-current-row v-loading="listLoading" @selection-change="selsChange" :height="tableHeight" style="width: 100%;">
      <!--<el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-form-item label="商品名称">
              <span>{{ props.row.name }}</span>
            </el-form-item>
            <el-form-item label="所属店铺">
              <span>{{ props.row.shop }}</span>
            </el-form-item>
            <el-form-item label="商品 ID">
              <span>{{ props.row.id }}</span>
            </el-form-item>
            <el-form-item label="店铺 ID">
              <span>{{ props.row.shopId }}</span>
            </el-form-item>
            <el-form-item label="商品分类">
              <span>{{ props.row.category }}</span>
            </el-form-item>
            <el-form-item label="店铺地址">
              <span>{{ props.row.address }}</span>
            </el-form-item>
            <el-form-item label="商品描述">
              <span>{{ props.row.desc }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>-->
      <el-table-column type="index" label="序号" width="45">
        <template slot-scope="scope">
          {{pageSize * (pageNumber - 1) + scope.$index + 1}}
        </template>
      </el-table-column>
      <el-table-column prop="repairStatus" label="维修状态" width="90" align="center" sortable>
        <template slot-scope="scope" >
          <el-tag v-for="item in devRepairStatusList" :key="item.id" v-if="scope.row.repairStatus === parseInt(item.enumNo)" :type="item.remark">
            {{item.displayValue}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="repairNo" label="维修单编码" width="120" sortable></el-table-column>
      <el-table-column prop="repairMatter" label="维修问题" width="120" sortable show-overflow-tooltip></el-table-column>
      <el-table-column prop="repairUnit" label="维修单位" width="90" sortable></el-table-column>
      <el-table-column prop="repairUser" label="维修人员" width="90" sortable></el-table-column>
      <el-table-column prop="warrantyUnit" label="保修单位" width="90" sortable></el-table-column>
      <el-table-column prop="warrantyMan" label="保修人员" width="90" sortable></el-table-column>
      <el-table-column prop="beginTime" label="维修开始时间" :formatter="dataFormat" width="150" sortable></el-table-column>
      <el-table-column prop="endTime" label="维修结束时间" :formatter="dataFormat" width="150" sortable></el-table-column>
      <el-table-column prop="repairRecord" label="维修记录" min-width="150" sortable show-overflow-tooltip></el-table-column>
      <el-table-column label="操作" width="120" fixed="right" >
        <template slot-scope="scope">
          <el-button @click="handleEdit(scope.$index, scope.row)" type="success" size="small">编辑</el-button>
          <el-button @click="handleDel(scope.$index, scope.row)" type="danger" size="small">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--分页-->
    <pagination :to="getDevRepairRecords" ref="page"></pagination>

    <!--删除窗口-->
    <common-delete
      :to="API.removeDeviceRepairRecord().go"
      :callback="getDevRepairRecords"
      :labelWidth="100"
      ref="delConfirm"></common-delete>

    <!--新增界面-->
    <add-or-update
      :to="API.addDeviceRepairRecord().go"
      :callback="getDevRepairRecords"
      ref="devRepairRecordAddForm"></add-or-update>

    <!--编辑界面-->
    <add-or-update
      :to="API.editDeviceRepairRecord().go"
      :callback="getDevRepairRecords"
      ref="devRepairRecordEditForm"></add-or-update>
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
          repairNo: '',
          repairStatus: null,
          airportCode: JSON.parse(sessionStorage.getItem('userStorage')).user.aiisAirports[0].airportCode
        },
        inputHovering: false,
        airport: [],
        subAirportLs: [],
        devRepairRecords: [],
        deviceAllInfos: [], // 获取全部数据
        deviceInfo: { id: '', parentId: '', deviceNo: '', deviceName: '', deviceModel: '', deviceOrigin: null, deviceFactory: null, deviceIp: '', displayType: '', deviceStatus: 0, deviceDes: null, deviceCom: '', groupId: 0, linkResId: 0, hostName: null, macAddr: null, osName: null, cpuDes: null, memoryDes: null, hdDes: null, adminName: null, adminPwd: null, vncPwd: '', terminal: '', scrollText: '', remark: '', updateTime: null, groupSort: 0, airportCode: null, dynamicScrollTextSourceId: 0, isAdd: 0, isShowPage: 0, pageSizeC: 0, vncPort: 0, resolutionX: 0, resolutionY: 0, backupScrollText: null, notChangePage: '', screenOpenTime: null, screenCloseTime: null, area: null, openComNo: null, closeComNo: null, comCommandType: '', terminalLevel: null, linkResType: '' },
        devRepairStatusList: [],
        tableHeight: 450,
        listLoading: false,
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
      },
      iconClass () {
        let criteria = this.inputHovering &&
          this.filters.repairNo !== undefined &&
          this.filters.repairNo !== '' &&
          this.filters.repairNo !== null
        return criteria ? 'circle-close is-show-close' : ' '
      }
    },
    components: {
      commonDelete: commonDelete,
      pagination: Pagination,
      addOrUpdate: addOrUpdate,
      cityName: CityName
    },
    methods: {
      cleardata () {
        this.filters.repairNo = ''
      },
      getAirportCode (airportData) {
        this.filters.airportCode = airportData
        this.getDevRepairRecords()
      },
      getDevRepairRecords () {
        let para = Object.assign({}, this.filters, this.$refs['page'].queryParam())
        this.listLoading = true
        API.getDevRepairRecords().go(para).then((data) => {
          if (data.ok) {
            this.$refs['page'].set('total', data.attr.data.pager.recordCount)
            this.devRepairRecords = data.attr.data.list
          } else {
            this.$notify(Util.notifyBody(false, data.msg))
          }
          this.listLoading = false
        })
      },
      bindData () {
        // 1. 获取设备维修状态信息列表
        this.getDevRepairStatusList()
        // 2. 获取用户订阅机场
        this.getSubAirportList()
        this.filters.airportCode = this.$refs['city'].airportCode
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
      getSubAirportList () {
        // 获取用户订阅机场
        this.subAirportLs = Butil.getSubscribeAirports()
        this.setAirportInfo(this.subAirportLs)
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
        this.$refs['delConfirm'].del(row)
      },
      // 显示新增界面
      handleAdd: function () {
        this.$refs['devRepairRecordAddForm'].show(null, this.filters.airportCode, true)
      },
      // 显示编辑界面
      handleEdit: function (index, row) {
        this.$refs['devRepairRecordEditForm'].show(row, this.filters.airportCode, true)
      },
      setAirportInfo: function (subAirportLs) {
        this.$refs['city'].setCitys(subAirportLs)
      },
      dataFormat: function (row, column, cellValue) {
        let date = row[column.property]
        if (date === undefined) {
          return ''
        }
        return Util.formatDate.flightDateFmt('yyyy-MM-dd hhmm', date, false)
      }
    },
    mounted () {
      this.bindData()
      this.getDevRepairRecords()
    }
  }
</script>

<style lang="scss">
.devRepairClss {
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
