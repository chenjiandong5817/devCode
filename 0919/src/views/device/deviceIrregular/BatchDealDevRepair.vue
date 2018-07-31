/*
 * @Author: ylj
 * @Date: 2018-03-01 16:26:09
 * @Last Modified by: ylj
 * @Last Modified time: 2018-03-01 16:33:51
 */

<template>
<section class="batchDealRepairClss">
<div :class="formClssName">
  <el-dialog :title="title" v-model="visible" :close-on-click-modal="true" :modal="false" :before-close="handleClose" size="small" :modal-append-to-body="false" @open="bindData">
    <!--列表-->
    <el-table v-bind:data="deviceirrinfos" highlight-current-row v-loading="deviceirrinfosLoading" :height="tableHeight" style="width: 100%;">
      <el-table-column
        type="selection"
        width="45">
      </el-table-column>
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
    </el-table>

    <hr/>
    <br/>
    <dev-repair-record-div ref="devRepairForm"></dev-repair-record-div>
  </el-dialog>
</div>
</section>
</template>

<script>
  import Util from '../../../common/js/util'
  import API from '../../../api'
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
        formClssName: '',
        deviceStatusList: [],
        tableHeight: 450,
        API: API,
        deviceirrinfosLoading: false,
        deviceirrinfos: []
      }
    },
    components: {
      devRepairRecordDiv: DevRepairRecordDiv
    },
    methods: {
      bindData () {
        this.setFormClassName()
      },
      setFormClassName () {
        this.formClssName = document.body.clientWidth <= 1366 ? 'largeDigCls' : 'smallDigCls'
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
      dataFormat (row, column, cellValue) {
        let date = row[column.property]
        if (date === undefined) {
          return ''
        }
        return Util.formatDate.flightDateFmt('yyyy-MM-dd hhmm', date, false)
      },
      handleClose () {
        this.visible = false
        this.initData()
      }
    },
    mounted () {

    }
  }
</script>

<style lang="scss">
.batchDealRepairClss {
  // .el-table {
  //   margin-left: 10px !important;
  // }
  // .el-table .cell, .el-table th>div {
  //     padding-left: 4px!important;
  //     padding-right: 2px!important;
  //     box-sizing: border-box;
  //     text-overflow: ellipsis;
  // }

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
