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
          <el-input v-model="filters.bridgeCode" placeholder="编码" @change="bridgeCodeUpper" ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getBridgesList">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleAdd">新增</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table v-bind:data="bridges" highlight-current-row v-loading="listLoading" @selection-change="selsChange" :height="tableHeight" style="width: 100%;">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column type="index" width="60">
        <template scope="scope">
          {{pageSize * (pageNumber - 1) + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="bridgeCode" label="编码" width="90" sortable>
      </el-table-column>
      <el-table-column prop="airportCode" label="所属机场" width="180" :formatter="formatterAirport" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="standCode" label="对应机位" width="100">
      </el-table-column>
      <el-table-column prop="terminal" label="所属航站楼" width="115" :formatter="formatterTerminal">
      </el-table-column>
      <el-table-column prop="nature" label="区域" width="100" :formatter="baseUtil.formatterNature">
      </el-table-column>
      <el-table-column prop="status" label="状态" width="100" :formatter="baseUtil.formatterStatus">
      </el-table-column>
      <el-table-column prop="description" label="描述" min-width="150" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="remark" label="备注" min-width="150" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="updateTime" label="最后更新时间" width="180">
      </el-table-column>
      <el-table-column label="操作" width="180" fixed="right" >
        <template scope="scope">
          <el-button size="small" @click="handleEdit(scope.$index, scope.row)"><span><i class="el-icon-edit"></i> 编辑 </span></el-button>
          <el-button size="small" @click="handleDel(scope.$index, scope.row)"><span><i class="el-icon-delete"></i> 删除 </span></el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--分页-->
    <pagination :to="getBridgesList" ref="page"></pagination>

    <!--新增界面-->
    <common-add-or-update
        @getSelectItem="getTerminalChoose"
        @getInputItem="bridgeCodeChange"
        type="add"
        :to="API.addBridges().go"
        :callback="getBridgesList"
        :labelWidth="100"
        ref="addForm"></common-add-or-update>
    <!--编辑界面-->
    <common-add-or-update
        @getSelectItem="getTerminalChoose"
        @getInputItem="bridgeCodeChange"
        title="编辑"
        type="update"
        :to="API.editBridges().go"
        :callback="getBridgesList"
        :labelWidth="100"
        ref="editForm"></common-add-or-update>
    <!--删除窗口-->
    <common-delete
        :to="API.removeBridges().go"
        :callback="getBridgesList"
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
          airportCode: JSON.parse(sessionStorage.getItem('userStorage')).user.aiisAirports[0].airportCode,
          bridgeCode: '',
          standCode: '',
          status: '',
          nature: '',
          terminal: ''
        },
        bridges: [],
        tableHeight: 495,
        listLoading: false,
        terminals: [],
        airports: [],
        stands: [],
        // 添加还是编辑的标记
        addUpdateTag: null,
        // 用于识别机场的值是否改变
        airportCodeTag: '',
        bridgeCodeTag: '',
        sels: [],
        // 登录用户订阅的机场
        adminAirportChoose: [],
        // 用于分组的用户订阅的机场
        airportsTypesChoose: [],
        // 状态下拉框的数据
        flightStatusChoose: [],
        // 区域下拉框的数据
        flightNatureChoose: [],
        // 新增编辑需要的字段
        fields: [
          {
            id: '0',
            hidden: true,
            item: [
              { name: 'id', value: '' }
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
               { name: 'bridgeCode', value: '', label: '编码', type: 'text', rules: [ { max: 6, min: 1, required: true, message: '请输入一位到六位字符串的编码' } ], placeholder: '编码', span: 12 },
               { name: 'standCode', value: '', label: '对应机位', type: 'select', choose: [], filterable: true, rules: [ { required: true, message: '请选择对应机位' } ], placeholder: '', span: 12 }
            ]
          },
          {
            id: '3',
            item: [
               { name: 'status', value: '', label: '状态', type: 'select', choose: [], rules: [ { required: true, message: '请选择状态' } ], placeholder: '请选择', span: 12 },
               { name: 'nature', value: '', label: '区域', type: 'select', choose: [], rules: [ { max: 1, min: 1, required: true, message: '请输入区域' } ], placeholder: '区域', span: 12 }
            ]
          },
          {
            id: '4',
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
      getBridgesList () {
        let para = Object.assign({}, this.filters, this.$refs['page'].queryParam())
        this.listLoading = true
        API.getBridgesListPage().go(para).then((data) => {
          if (data.ok) {
            this.$refs['page'].set('total', data.attr.data.pager.recordCount)
            this.bridges = data.attr.data.list
            this.filters.bridgeCode = ''
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
        this.$refs['delConfirm'].del(row.id)
      },
      // 显示新增界面
      handleAdd: function () {
        this.addUpdateTag = 'add'
        this.airportCodeTag = null
        this.bridgeCodeTag = null
        this.fields[1].item[0].choose = this.airportsTypesChoose
        if (this.adminAirportChoose.length === 1) {
          this.fields[1].item[0].value = this.adminAirportChoose[0].value
        }
        this.fields[3].item[0].choose = this.flightStatusChoose
        this.fields[3].item[1].choose = this.flightNatureChoose
        let addFields = Util.deepCopy(this.fields)
        this.$refs['addForm'].show(addFields)
      },
      // 显示编辑界面
      handleEdit: function (index, row) {
        this.addUpdateTag = 'update'
        this.airportCodeTag = row.airportCode
        this.bridgeCodeTag = row.bridgeCode
        this.fields[3].item[0].choose = this.flightStatusChoose
        this.fields[3].item[1].choose = this.flightNatureChoose
        this.fields[1].item[0].choose = this.airportsTypesChoose
        if (this.adminAirportChoose.length === 1) {
          this.fields[1].item[0].value = this.adminAirportChoose[0].value
        }
        // 用于在编辑的时候得到对应的所属航站楼
        var terminalsEdit = []
        for (var a = 0; a < this.terminals.length; a++) {
          if (this.terminals[a].airportCode === row.airportCode) {
            terminalsEdit.push(this.terminals[a])
          }
        }
        this.fields[1].item[1].choose = terminalsEdit
        // 用于在编辑的时候得到对应的机位选项
        var standsEdit = []
        for (var b = 0; b < this.stands.length; b++) {
          if (this.stands[b].airportCode === row.airportCode) {
            standsEdit.push(this.stands[b])
          }
        }
        this.fields[2].item[1].choose = standsEdit
        // 传值
        let editFields = Util.deepCopy(this.fields)
        for (let i = 0; i < editFields.length; i++) {
          for (let j = 0; j < editFields[i].item.length; j++) {
            editFields[i].item[j].value = row[editFields[i].item[j].name]
          }
        }
        this.$refs['editForm'].show(editFields)
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
      // 获取对应机位的列表的选项
      getStandCodeChoose: function () {
      	this.stands = []
      	for (var i = 0; i < this.$cache.array.stand.length; i++) {
      		var item = {}
      		item['text'] = this.$cache.array.stand[i].standCode
      		item['value'] = this.$cache.array.stand[i].standCode
          // 在stands中添加airportCode属性
          item['airportCode'] = this.$cache.array.stand[i].airportCode
      		this.stands.push(item)
      	}
      },
      // 通过机场代码的筛选，选出所属航站楼
      getTerminalChoose: function (data) {
        if (data.airportCode !== this.airportCodeTag) {
          var terminalValue = null
          var standValue = null
          var terminalResult = []
          var standResult = []
          for (var i = 0; i < this.terminals.length; i++) {
            if (this.terminals[i].airportCode === data.airportCode) {
              terminalResult.push(this.terminals[i])
            }
          }
          for (var j = 0; j < this.stands.length; j++) {
            if (this.stands[j].airportCode === data.airportCode) {
              standResult.push(this.stands[j])
            }
          }
          if (this.addUpdateTag === 'add') {
            var terminalStrAdd = 'terminal'
            var standStrAdd = 'standCode'
            // 传入的四个值分别代表要改变值的下标，值，要清空的key值，空值
            this.$refs['addForm'].changeSelectChoose(1, 1, terminalResult, terminalStrAdd, terminalValue)
            this.$refs['addForm'].changeSelectChoose(2, 1, standResult, standStrAdd, standValue)
          }
          if (this.addUpdateTag === 'update') {
            var terminalStrUpdate = 'terminal'
            var standStrUpdate = 'standCode'
            this.$refs['editForm'].changeSelectChoose(1, 1, terminalResult, terminalStrUpdate, terminalValue)
            this.$refs['editForm'].changeSelectChoose(2, 1, standResult, standStrUpdate, standValue)
          }
        }
        this.airportCodeTag = data.airportCode
      },
      bridgeCodeChange: function (data) {
        if (data.bridgeCode !== this.bridgeCodeTag) {
          var singleUpperCode = null
          singleUpperCode = data.bridgeCode.toUpperCase()
          if (this.addUpdateTag === 'add') {
            var bridgeCodeTagStrAdd = 'bridgeCode'
            this.$refs['addForm'].changeInput(bridgeCodeTagStrAdd, singleUpperCode)
          }
          if (this.addUpdateTag === 'update') {
            var bridgeCodeStrEdit = 'bridgeCode'
            this.$refs['editForm'].changeInput(bridgeCodeStrEdit, singleUpperCode)
          }
        }
        this.bridgeCodeTag = data.bridgeCode
      },
      bridgeCodeUpper: function (data) {
        this.filters.bridgeCode = data.toUpperCase()
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
      this.getStandCodeChoose()
	    this.getTerminalList()
	    this.getAirportList()
      this.getBridgesList()
      this.getFlightStatusChoose()
      this.getFlightNatureChoose()
    }
  }

</script>

<style scoped>

</style>
