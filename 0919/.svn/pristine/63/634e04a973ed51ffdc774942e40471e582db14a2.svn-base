<!-- /*
 * @Author: cdroid
 * @Date: 2017-05-25 10:42:17
 * @Last Modified by: mikey.zhaopeng
 * @Last Modified time: 2017-06-27 15:33:48
 * @Description: 通用机场列表界面
 */ -->
<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <airport-select
            ref="selectAirport"
            v-on:airportCodeChange="getAirportCode">
          </airport-select>
        </el-form-item>
        <el-form-item>
          <el-input v-model="filters.counterCode" placeholder="值机柜台编码" @change="counterCodeUpper" ></el-input>
        </el-form-item>
        <el-form-item>
          <el-input v-model="filters.checkinGroup" placeholder="值机岛编码" @change="checkinGroupUpper" ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getCheckincountersList">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleAdd">新增</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table v-bind:data="stands" highlight-current-row v-loading="listLoading" @selection-change="selsChange" :height="tableHeight" style="width: 100%;">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column type="index" width="60">
        <template scope="scope">
          {{pageSize * (pageNumber - 1) + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="counterCode" label="值机柜台编码" width="145" sortable>
      </el-table-column>
      <el-table-column prop="checkinGroup" label="值机岛编码" width="115">
      </el-table-column>
      <el-table-column prop="airportCode" label="所属机场" width="150" :formatter="formatterAirport" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="allocationMode" label="分配模式" width="145" :formatter="formatterOpMode">
      </el-table-column>
      <el-table-column prop="serviceType" label="服务模式" width="155" :formatter="formatterServiceType">
      </el-table-column>
      <el-table-column prop="terminal" label="所属航站楼" width="115" :formatter="formatterTerminal">
      </el-table-column>
      <el-table-column prop="nature" label="区域属性" width="100" :formatter="baseUtil.formatterNature">
      </el-table-column>
      <el-table-column prop="status" label="状态" width="75" :formatter="baseUtil.formatterStatus">
      </el-table-column>
      <el-table-column prop="description" label="描述" width="130" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="remark" label="备注" min-width="130" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="updateTime" label="最后更新时间" min-width="180">
      </el-table-column>
      <el-table-column label="操作" width="180" fixed="right" >
        <template scope="scope">
          <el-button size="small" @click="handleEdit(scope.$index, scope.row)"><span><i class="el-icon-edit"></i> 编辑 </span></el-button>
          <el-button size="small" @click="handleDel(scope.$index, scope.row)"><span><i class="el-icon-delete"></i> 删除 </span></el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--分页-->
    <pagination :to="getCheckincountersList" ref="page"></pagination>

    <!--新增界面-->
    <common-add-or-update
        @getSelectItem="getAllocationedServiceType"
        @getInputItem="getCounterCodeCheckinGroupChange"
        type="add"
        :to="API.addCheckincounters().go"
        :callback="getCheckincountersList"
        :labelWidth="110"
        ref="addForm"></common-add-or-update>
    <!--编辑界面-->
    <common-add-or-update
        @getSelectItem="getAllocationedServiceType"
        @getInputItem="getCounterCodeCheckinGroupChange"
        title="编辑"
        type="update"
        :to="API.editCheckincounters().go"
        :callback="getCheckincountersList"
        :labelWidth="110"
        ref="editForm"></common-add-or-update>
    <!--删除窗口-->
    <common-delete
        :to="API.removeCheckincounters().go"
        :callback="getCheckincountersList"
        :labelWidth="100"
        ref="delConfirm"></common-delete>
  </section>
</template>

<script>
  import Util from '../../common/js/util'
  import baseUtil from '../../common/js/base-util'
  import chooseDialog from './../../components/CommChooseGrid'
  import commonAddOrUpdate from './../base/baseForm/CommAddOrUpdate'
  import commonDelete from './../../components/CommDelete'
  import API from '../../api'
  import Pagination from '../../components/Pagination'
  import airportSelect from './../base/baseForm/SelectAirport'

  export default {
    data () {
      return {
        filters: {
          counterCode: '',
          checkinGroup: '',
          airportCode: JSON.parse(sessionStorage.getItem('userStorage')).user.aiisAirports[0].airportCode,
          nature: '',
          status: '',
          terminal: ''
        },
        stands: [],
        tableHeight: 495,
        listLoading: false,
        sels: [],
        // 登录用户订阅的机场
        adminAirportChoose: [],
        // 用于分组的用户订阅的机场
        airportsTypesChoose: [],
        // 状态下拉框的数据
        flightStatusChoose: [],
        // 区域下拉框的数据
        flightNatureChoose: [],
        // 分配模式
        ckcounteropmodes: [],
        // 服务类型
        ckcounterservicetypes: [],
        // 标识是添加还是编辑
        addUpdateTag: null,
        // 编辑页面的值
        editData: null,
        // 用于识别值是否改变
        allocationModeTag: '',
        // 用于识别机场代码是否改变
        airportCodeTag: '',
        // 用于判断值机柜台编码是否改变
        counterCodeTag: null,
        // 用于判断值机岛编码编码是否改变
        checkinGroupTag: null,
        terminals: [],
        airports: [],
        // 新增编辑需要的字段
        fields: [
          {
            id: '0',
            hidden: true,
            item: [
              { name: 'counterId', value: '' }
            ]
          },
          {
            id: '1',
            item: [
              { name: 'airportCode', value: '', label: '所属机场', type: 'selectExplain', choose: [], filterable: true, rules: null, placeholder: '', span: 12 },
              { name: 'terminal', value: '', label: '所属航站楼', type: 'select', choose: [], rules: [ { required: true, message: '请选择所属航站楼' } ], placeholder: '请选择所属航站楼', span: 12 }
            ]
          },
          {
            id: '2',
            item: [
              { name: 'counterCode', value: '', label: '值机柜台编码', type: 'text', rules: [ { max: 6, min: 1, required: true, message: '请输入一位到六位字符串的值机柜台编码' } ], placeholder: '请输入值机柜台编码', span: 12 },
              { name: 'checkinGroup', value: '', label: '值机岛编码', type: 'text', rules: [ { max: 1, min: 1, required: true, message: '请输入一位字符串的值机岛编码' } ], placeholder: '请输入值机岛编码', span: 12 }
            ]
          },
          {
            id: '3',
            item: [
              { name: 'allocationMode', value: '', label: '分配模式', type: 'select', choose: [], rules: [ { max: 4, min: 1 } ], placeholder: '', span: 12 },
              { name: 'serviceType', value: '', label: '服务模式', type: 'select', choose: [], rules: [ { max: 4, min: 1 } ], placeholder: '', span: 12 }
            ]
          },
          {
            id: '4',
            item: [
              { name: 'nature', value: '', label: '区域属性', type: 'select', choose: [], rules: [ { max: 1, min: 1, required: true, message: '请输入区域属性' } ], placeholder: '请输入区域属性', span: 12 },
              { name: 'status', value: '', label: '状态', type: 'select', choose: [], rules: [ { required: true, message: '请选择状态' } ], placeholder: '请选择状态', span: 12 }
            ]
          },
          {
            id: '5',
            item: [
              { name: 'description', value: '', label: '描述', type: 'textarea', rules: [ { max: 50, min: 1, message: '请输入少于五十位字符串的描述' } ], placeholder: '', span: 12 },
              { name: 'remark', value: '', label: '备注', type: 'textarea', rules: [ { max: 100, min: 1, message: '请输入少于一百位字符串的备注' } ], placeholder: '', span: 12 }
            ]
          }
        ],
        API: API,
        baseUtil: baseUtil
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
      commonAddOrUpdate: commonAddOrUpdate,
      commonDelete: commonDelete,
      pagination: Pagination,
      airportSelect: airportSelect
    },
    methods: {
      // 获取用户列表
      getCheckincountersList () {
        let para = Object.assign({}, this.filters, this.$refs['page'].queryParam())
        this.listLoading = true
        API.getCheckincountersListPage().go(para).then((data) => {
          if (data.ok) {
            this.$refs['page'].set('total', data.attr.data.pager.recordCount)
            this.stands = data.attr.data.list
            this.filters.counterCode = ''
            this.filters.checkinGroup = ''
            this.getCkcounteropmodesList()
            this.getCkcounterservicetypesList()
            this.getTerminalList()
          } else {
            this.$notify(Util.notifyBody(false, data.msg))
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
      // 删除
      handleDel: function (index, row) {
        this.$refs['delConfirm'].del(row.counterId)
      },
      // 显示新增界面
      handleAdd: function () {
        this.addUpdateTag = 'add'
        this.allocationModeTag = null
        this.airportCodeTag = null
        this.counterCodeTag = null
        this.checkinGroup = null
        this.fields[1].item[0].choose = this.airportsTypesChoose
        if (this.adminAirportChoose.length === 1) {
          this.fields[1].item[0].value = this.adminAirportChoose[0].value
        }
        this.fields[3].item[0].choose = this.ckcounteropmodes
        this.fields[4].item[0].choose = this.flightNatureChoose
        this.fields[4].item[1].choose = this.flightStatusChoose
        let addFields = Util.deepCopy(this.fields)
        this.$refs['addForm'].show(addFields)
      },
      // 显示编辑界面
      handleEdit: function (index, row) {
        this.editData = row
        this.addUpdateTag = 'update'
        // 用于标记上一个的分配模式和机场
        this.allocationModeTag = row.allocationMode
        this.airportCodeTag = row.airportCode
        this.counterCodeTag = row.counterCode
        this.checkinGroupTag = row.checkinGroup
        // 如果机场就一个，就给他默认值
        this.fields[1].item[0].choose = this.airportsTypesChoose
        if (this.adminAirportChoose.length === 1) {
          this.fields[1].item[0].value = this.adminAirportChoose[0].value
        }
        this.fields[3].item[0].choose = this.ckcounteropmodes
        this.fields[4].item[0].choose = this.flightNatureChoose
        this.fields[4].item[1].choose = this.flightStatusChoose
        // 在编辑框里也要根据条件选出来
        // 筛选航站楼
        var terminalsEdit = []
        for (var a = 0; a < this.terminals.length; a++) {
          if (this.terminals[a].airportCode === row.airportCode) {
            terminalsEdit.push(this.terminals[a])
          }
        }
        this.fields[1].item[1].choose = terminalsEdit
        // 筛选服务模式
        var serviceTypeEdit = []
        for (var b = 0; b < this.ckcounterservicetypes.length; b++) {
          if (this.ckcounterservicetypes[b].ckCounterTemplate === row.allocationMode) {
            serviceTypeEdit.push(this.ckcounterservicetypes[b])
          }
        }
        this.fields[3].item[1].choose = serviceTypeEdit
        // 赋值
        let editFields = Util.deepCopy(this.fields)
        for (let i = 0; i < editFields.length; i++) {
          for (let j = 0; j < editFields[i].item.length; j++) {
            editFields[i].item[j].value = row[editFields[i].item[j].name]
          }
        }
        this.$refs['editForm'].show(editFields)
      },
      // 获取值机柜台分配模式
      getCkcounteropmodesList: function () {
    		this.ckcounteropmodes = []
      	for (var i = 0; i < this.$cache.array.ckcounteropmodes.length; i++) {
      		var objItem = {}
      		objItem['text'] = this.$cache.array.ckcounteropmodes[i].description
      		objItem['value'] = this.$cache.array.ckcounteropmodes[i].opModeCode
      		this.ckcounteropmodes.push(objItem)
      	}
      },
      // 获取值机柜台服务类型
      getCkcounterservicetypesList: function () {
      		this.ckcounterservicetypes = []
	      	for (var i = 0; i < this.$cache.array.ckcounterservicetypes.length; i++) {
	      		var objItem = {}
	      		objItem['text'] = this.$cache.array.ckcounterservicetypes[i].description
	      		objItem['value'] = this.$cache.array.ckcounterservicetypes[i].serviceTypeCode
            objItem['ckCounterTemplate'] = this.$cache.array.ckcounterservicetypes[i].ckCounterTemplate
            objItem['serviceTypeCode'] = this.$cache.array.ckcounterservicetypes[i].serviceTypeCode
	      		this.ckcounterservicetypes.push(objItem)
	      	}
      },
      // 对表格的column进行格式化
      // 对分配模式的编码进行格式化
      formatterOpMode: function (row, column, cellValue) {
      	var result = null
      	for (var i = 0; i < this.ckcounteropmodes.length; i++) {
      		if (cellValue === this.ckcounteropmodes[i].value) {
      			result = this.ckcounteropmodes[i].text
      		}
      	}
      	return result
      },
      // 对服务模式的编码进行格式化
      formatterServiceType: function (row, column, cellValue) {
      	var result = null
      	for (var i = 0; i < this.ckcounterservicetypes.length; i++) {
      		if (cellValue === this.ckcounterservicetypes[i].value) {
      			result = this.ckcounterservicetypes[i].text
      		}
      	}
      	return result
      },
      // 获取航站楼信息列表
      getTerminalList: function (index, row) {
          this.terminals = []
          for (var i = 0; i < this.$cache.array.terminals.length; i++) {
            var item = {}
            item['text'] = this.$cache.array.terminals[i].terminalCode
            item['value'] = this.$cache.array.terminals[i].terminalCode
            item['airportCode'] = this.$cache.array.terminals[i].airportCode
            this.terminals.push(item)
          }
      },
      // 格式化航站楼的代码
      formatterTerminal: function (row, column, cellValue) {
        for (var i = 0; i < this.terminals.length; i++) {
          if (this.terminals[i].value === cellValue) {
            return this.terminals[i].text
          }
        }
      },
      // 格式化机场的的代码
      formatterAirport: function (row, column, cellValue) {
        for (var i = 0; i < this.adminAirportChoose.length; i++) {
          if (this.adminAirportChoose[i].value === cellValue) {
            return this.adminAirportChoose[i].text1
          }
        }
      },
      getAllocationedServiceType: function (data) {
        // 只要分配模式的选项改变就会触发
        if (this.allocationModeTag !== data.allocationMode) {
          var serviceTypeData = null
          var result = []
          for (var i = 0; i < this.ckcounterservicetypes.length; i++) {
            if (this.ckcounterservicetypes[i].ckCounterTemplate === data.allocationMode) {
              result.push(this.ckcounterservicetypes[i])
            }
          }
          if (this.addUpdateTag === 'add') {
            var serviceTypeStrAdd = 'serviceType'
            // 传入的四个值分别代表要改变值的下标，值，要清空的key值，空值
            this.$refs['addForm'].changeSelectChoose(3, 1, result, serviceTypeStrAdd, serviceTypeData)
          }
          if (this.addUpdateTag === 'update') {
            var serviceTypeStrUpdate = 'serviceType'
            this.$refs['editForm'].changeSelectChoose(3, 1, result, serviceTypeStrUpdate, serviceTypeData)
          }
        }
        if (this.airportCodeTag !== data.airportCode) {
          var terminalValue = null
          var terminalResult = []
          for (var j = 0; j < this.terminals.length; j++) {
            if (this.terminals[j].airportCode === data.airportCode) {
              terminalResult.push(this.terminals[j])
            }
          }
          if (this.addUpdateTag === 'add') {
            var terminalStrAdd = 'terminal'
            // 传入的四个值分别代表要改变值的下标，值，要清空的key值，空值
            this.$refs['addForm'].changeSelectChoose(1, 1, terminalResult, terminalStrAdd, terminalValue)
          }
          if (this.addUpdateTag === 'update') {
            var terminalStrUpdate = 'terminal'
            this.$refs['editForm'].changeSelectChoose(1, 1, terminalResult, terminalStrUpdate, terminalValue)
          }
        }
        this.allocationModeTag = data.allocationMode
        this.airportCodeTag = data.airportCode
      },
      getCounterCodeCheckinGroupChange: function (data) {
        if (data.counterCode !== this.counterCodeTag) {
          var upperCounterCode = null
          upperCounterCode = data.counterCode.toUpperCase()
          if (this.addUpdateTag === 'add') {
            var counterCodeStrAdd = 'counterCode'
            this.$refs['addForm'].changeInput(counterCodeStrAdd, upperCounterCode)
          }
          if (this.addUpdateTag === 'update') {
            var counterCodeStrUpdate = 'counterCode'
            this.$refs['editForm'].changeInput(counterCodeStrUpdate, upperCounterCode)
          }
        }
        if (data.checkinGroup !== this.checkinGroupTag) {
          var upperCheckinGroup = null
          upperCheckinGroup = data.checkinGroup.toUpperCase()
          if (this.addUpdateTag === 'add') {
            var checkinGroupStrAdd = 'checkinGroup'
            this.$refs['addForm'].changeInput(checkinGroupStrAdd, upperCheckinGroup)
          }
          if (this.addUpdateTag === 'update') {
            var checkinGroupStrUpdate = 'checkinGroup'
            this.$refs['editForm'].changeInput(checkinGroupStrUpdate, upperCheckinGroup)
          }
        }
        this.counterCodeTag = data.counterCode
        this.checkinGroupTag = data.checkinGroup
      },
      counterCodeUpper: function (data) {
        this.filters.counterCode = data.toUpperCase()
      },
      checkinGroupUpper: function (data) {
        this.filters.checkinGroup = data.toUpperCase()
      },
      // 机场下拉框的通用模块
      getAirportList: function () {
        var airportsData = this.$refs['selectAirport'].getAirportList()
        this.adminAirportChoose = airportsData.adminAirportChoose
        this.airportsTypesChoose = airportsData.airportsTypesChoose
      },
      // 当机场下拉框的值变化时执行的函数
      getAirportCode: function (value) {
        this.filters.airportCode = value
      },
      getFlightStatusChoose: function () {
        this.flightStatusChoose = baseUtil.getResourceStatusChoose()
      },
      getFlightNatureChoose: function () {
        this.flightNatureChoose = baseUtil.getFlightNatureChoose()
      }
    },
    mounted () {
      this.getAirportList()
      this.getCheckincountersList()
      this.getFlightStatusChoose()
      this.getFlightNatureChoose()
    }
  }

</script>

<style scoped>

</style>
