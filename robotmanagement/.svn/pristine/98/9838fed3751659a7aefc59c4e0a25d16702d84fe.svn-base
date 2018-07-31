<!-- /*
 * @Author: cdroid
 * @Date: 2017-05-25 10:42:17
 * @Last Modified by: mikey.zhaopeng
 * @Last Modified time: 2017-06-27 15:33:48
 * @Description: 通用机场列表界面
 */ -->
<template>
  <section id="section">
        <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <div class="operateTag">
         <el-form :inline="true" :model="filters">
            <el-form-item>
              <el-button :type="exportExcelType" @click="clickExportExcel" :loading="buttonLoading">{{ exportExcelName }}</el-button>
            </el-form-item>
            <el-form-item label="结束日期">
              <el-date-picker v-model="filters.opDate_end" type="date" placeholder="结束日期" clearable style="width: 120px" ></el-date-picker>
            </el-form-item>
            <el-form-item label="开始日期">
            <el-date-picker v-model="filters.opDate_begin" type="date" placeholder="开始日期" clearable style="width: 120px" ></el-date-picker>
            </el-form-item>
          </el-form>
      </div>
    </el-col>
    <!--列表-->
    <el-table v-bind:data="chatLogs" highlight-current-row v-loading="listLoading" @selection-change="selsChange" :height="tableHeight" style="width: 100%;">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column type="index" width="80">
        <template slot-scope="scope">
          {{pageSize * (pageNumber - 1) + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="req_msg" label="问题" width="250" sortable show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="resp_msg" label="答案" min-width="400" show-overflow-tooltip>
      </el-table-column>
      <!-- <el-table-column prop="chatlogRobotId" label="机器人名称" width="200" :formatter="getRobotName">
      </el-table-column> -->
      <!-- <el-table-column prop="chat_category" label="日志类别" width="120" :formatter="formatterCategory">
      </el-table-column> -->
      <el-table-column label="日志类别" width="120">
        <template slot-scope="scope">
           <el-tag type="success" v-if="scope.row.chat_category==='航班'">航班信息</el-tag>
           <el-tag type="danger" v-if="scope.row.chat_category==='tulingRobot'">图灵机器人</el-tag>
           <el-tag type="primary" v-if="scope.row.chat_category!=='航班'&&scope.row.chat_category!=='tulingRobot'">{{formatterCategory(scope.row.chat_category)}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="create_time" label="创建时间" min-width="180">
      </el-table-column>
    </el-table>

    <!--分页-->
    <pagination :to="getChatLogList" ref="page"></pagination>

  </section>
</template>

<script>
  import Util from '../../common/js/util'
  import chooseDialog from './../../components/CommChooseGrid'
  import API from '../../api'
  import Pagination from '../../components/Pagination'

  export default {
    data () {
      return {
        chatLogs: [],
        robotNameOptions: [],
        // 表格导出的页码
        filters: {
          opDate_begin: this.setDefDate(),
          opDate_end: this.setDefDate()
        },
        // 导出到excel表格的数据
        exportExcelDatas: [],
        // 导出EXCEL按钮名称
        exportExcelName: '导出EXCEL表',
        // 导出EXCEL按钮类型
        exportExcelType: 'primary',
        // 导出进度的显示
        exportPercent: 0,
        // 进度跳的状态
        status: 'success',
        // 按钮加载状态
        buttonLoading: false,
        tableHeight: null,
        listLoading: false,
        sels: [],
        API: API,
        chatLogCounts: null
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
      pagination: Pagination
    },
    methods: {
      // 获取日志的总条数
      getChatLogCount: function () {
        API.getChatLogCount().go().then((data) => {
          if (data.success) {
            this.chatLogCounts = data.chatlogCount
            this.getChatLogList()
          } else {
            this.$notify(Util.notifyBody(false, data.message))
          }
          this.listLoading = false
        })
      },
      // 获取日志信息列表
      getChatLogList: function () {
        this.listLoading = true
        API.getChatLogListPage(this.pageNumber, this.pageSize).go().then((data) => {
          if (data.success) {
            this.$refs['page'].set('total', this.chatLogCounts)
            this.chatLogs = data.chatlogList
          } else {
            this.$notify(Util.notifyBody(false, data.message))
          }
          this.listLoading = false
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
      // 获取全部机器人
      getAllRobotInfoList () {
        API.getRobotInfoListPage(1, 10000).go().then((data) => {
          if (data.success) {
            var robotArray = data.robotInfoList
            this.robotNameOptions = []
            for (var i = 0; i < robotArray.length; i++) {
              var result = {}
              result['text'] = robotArray[i].name
              result['value'] = robotArray[i].robotId
              this.robotNameOptions.push(result)
            }
          } else {
            this.$notify(Util.notifyBody(false, data.message))
          }
        })
      },
      // 转换机器人名称
      getRobotName (row, column, cellValue) {
        for (var i = 0; i < this.robotNameOptions.length; i++) {
          if (cellValue === this.robotNameOptions[i].value) {
            return this.robotNameOptions[i].text
          }
        }
      },
      getKnowledgeCategoryList () {
        API.getKnowledgeCategoryListPage(1, 10000).go().then((data) => {
          if (data.success) {
            var knowledgeCategoryArray = data.knowledgeCategoryList
            this.knowledgeCategoryOptions = []
            this.knowledgeCategoryOptions = [ {
              text: '全部',
              value: ''
            } ]
            for (var i = 0; i < knowledgeCategoryArray.length; i++) {
              var result = {}
              result['text'] = knowledgeCategoryArray[i].knowledgeCategoryCNName
              result['value'] = knowledgeCategoryArray[i].knowledgeCategoryId
              this.knowledgeCategoryOptions.push(result)
            }
          } else {
            this.$notify(Util.notifyBody(false, data.message))
          }
        })
      },
      formatterCategory (cellValue) {
        var result = null
        for (var i = 0; i < this.knowledgeCategoryOptions.length; i++) {
          if (this.knowledgeCategoryOptions[i].value === cellValue) {
            result = this.knowledgeCategoryOptions[i].text
          }
        }
        if (result === null) {
          result = '暂无'
        }
        return result
      },
      getTableHeight () {
        this.$nextTick(() => {
          var sectionHeight = document.getElementById('section').offsetHeight
          this.tableHeight = sectionHeight - 52
        })
      },
      clickExportExcel () {
        // 按钮状态变化
        this.exportExcelName = '正在导出，禁止操作'
        this.exportExcelType = 'danger'
        this.buttonLoading = true
        // 清空
        this.exportExcelDatas = []
        // 判断能否导出
        // 开始时间
        let beginTime = this.setTimeStamp(this.filters.opDate_begin)
        // 加一天减一秒
        let moment = require('moment')
        this.filters.opDate_end = moment(this.filters.opDate_end).add(1, 'day').subtract(1, 'second')
        let endTime = this.setTimeStamp(this.filters.opDate_end)
        if (beginTime > endTime) {
          this.$notify(Util.notifyBody(false, '开始时间不能大于结束时间'))
          return
        }
        // 根据页码来进行请求
        API.getChatLogListPage(1, 500000).go().then((data) => {
          if (data.success) {
            for (var i = 0; i < data.chatlogList.length; i++) {
              if (this.setTimeStamp(data.chatlogList[i].create_time) >= this.setTimeStamp(this.filters.opDate_begin) && this.setTimeStamp(data.chatlogList[i].create_time) <= this.setTimeStamp(this.filters.opDate_end)) {
                this.exportExcelDatas.push(data.chatlogList[i])
              }
            }
            this.exportExcelDatas.reverse()
            this.exportExcel()
          } else {
            this.$notify(Util.notifyBody(false, data.message))
          }
        })
      },
      exportExcel () {
        require.ensure([], () => {
        const { export_json_to_excel } = require('./../../common/vendor/Export2Excel')
        const tHeader = ['问题', '答案', '日志类别', '创建时间']
        const filterVal = ['req_msg', 'resp_msg', 'chat_category', 'create_time']
        const list = this.exportExcelDatas
        const data = this.formatJson(filterVal, list)
        export_json_to_excel(tHeader, data, '日志信息')
        })
        this.exportExcelName = '导出EXCEL表'
        this.exportExcelType = 'primary'
        this.buttonLoading = false
        this.$notify(Util.notifyBody(true, '导出成功'))
      },
      formatJson (filterVal, jsonData) {
        return jsonData.map(v => filterVal.map(j => v[j]))
      },
      // 初始化时间
      setDefDate () {
        let moment = require('moment')
        let nextDate = moment().format('YYYY-MM-DD')
        return nextDate
      },
      // 把时间转换成时间戳的函数
      setTimeStamp (data) {
        let moment = require('moment')
        let timeStamp = moment(data).format('x')
        return timeStamp
      }
    },
    mounted () {
      this.getChatLogCount()
      this.getAllRobotInfoList()
      this.getKnowledgeCategoryList()
      this.getTableHeight()
    }
  }
</script>

<style scoped>
  .operateTag .el-form-item {
    float: right;
    margin-right: 30px;
  }
  .percentageExport .el-progress {
    width: 100px;
    margin-top: 8px;
  }
</style>
