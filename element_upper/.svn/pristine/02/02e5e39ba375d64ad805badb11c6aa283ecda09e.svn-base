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
          <el-input v-model="filters.gateCode" placeholder="编码" ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getGateList">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleAdd">新增</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table v-bind:data="gates" highlight-current-row v-loading="listLoading" @selection-change="selsChange" :height="tableHeight" style="width: 100%;">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column type="index" width="60">
        <template scope="scope">
          {{pageSize * (pageNumber - 1) + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="gateCode" label="编码" width="90" sortable>
      </el-table-column>
      <el-table-column prop="displayCode" label="实际显示编码" width="130">
      </el-table-column>
      <el-table-column prop="airportCode" label="所属机场" min-width="150" :formatter="formatterAirport" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="associatedGate" label="关联登机口" width="115">
      </el-table-column>
      <el-table-column prop="waitingHall" label="候机区" width="85">
      </el-table-column>
      <el-table-column prop="terminal" label="所属航站楼" width="115" :formatter="formatterTerminal">
      </el-table-column>
      <el-table-column prop="nature" label="区域" width="75" :formatter="baseUtil.formatterNature">
      </el-table-column>
      <el-table-column prop="direction" label="进港/出港" width="115" :formatter="baseUtil.formatterDirection">
      </el-table-column>
      <el-table-column prop="status" label="状态" width="75" :formatter="baseUtil.formatterStatus">
      </el-table-column>
      <el-table-column prop="description" label="描述" min-width="130" show-overflow-tooltip>
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
    <pagination :to="getGateList" ref="page"></pagination>

    <!--新增界面-->
    <common-add-or-update
        @getSelectItem="getWaitingHallTerminalChoose"
        type="add"
        :to="API.addGate().go"
        :callback="getGateList"
        :labelWidth="120"
        ref="addForm"></common-add-or-update>
    <!--编辑界面-->
    <common-add-or-update
        @getSelectItem="getWaitingHallTerminalChoose"
        title="编辑"
        type="update"
        :to="API.editGate().go"
        :callback="getGateList"
        :labelWidth="120"
        ref="editForm"></common-add-or-update>
    <!--删除窗口-->
    <common-delete
        :to="API.removeGate().go"
        :callback="getGateList"
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
          gateCode: '',
          airportCode: JSON.parse(sessionStorage.getItem('userStorage')).user.aiisAirports[0].airportCode
        },
        gates: [],
        tableHeight: 495,
        listLoading: false,
        sels: [],
        // 登录用户订阅的机场
        adminAirportChoose: [],
        // 用于分组的用户订阅的机场
        airportsTypesChoose: [],
        // 航站楼数据，用于formatter
        terminals: [],
        // 关联登机口下拉框数据
        associatedGateChoose: [],
        // 登机口下拉框的数据
        waitingHallsChoose: [],
        // 添加还是编辑的标记
        addUpdateTag: null,
        // 用于识别机场的值是否改变
        airportCodeTag: null,
        // 所属机场的格式化以及编辑新增的时候用于选择
        airports: [],
        // 区域下拉框的数据
        flightNatureChoose: [],
        // 进出港下拉框的数据
        flightDirectionChoose: [],
        // 状态下拉框的数据
        flightStatusChoose: [],
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
              { name: 'gateCode', value: '', label: '编码', type: 'text', rules: [ { max: 6, min: 1, required: true, message: '请输入一位到六位字符串的编码' } ], placeholder: '编码', span: 12 }
            ]
          },
          {
            id: '2',
            item: [
              { name: 'displayCode', value: '', label: '实际显示编码', type: 'text', rules: [ { max: 4, min: 1, required: true, message: '请输入一位到四位字符串的实际显示编码' } ], placeholder: '实际编码', span: 12 },
              { name: 'associatedGate', value: 0, label: '关联登机口', type: 'select', choose: [], rules: null, placeholder: '', span: 12 }
            ]
          },
          {
            id: '3',
            item: [
              { name: 'waitingHall', value: '', label: '候机区', type: 'select', choose: [], rules: [ { max: 4, min: 1, message: '候机区只能为一位到四位的字符串' } ], placeholder: '', span: 12 },
              { name: 'terminal', value: '', label: '所属航站楼', type: 'select', choose: [], rules: [ { max: 4, min: 1, required: true, message: '请输入所属航站楼' } ], placeholder: '请输入所属航站楼', span: 12 }
            ]
          },
          {
            id: '4',
            item: [
              { name: 'nature', value: '', label: '区域', type: 'select', choose: [], rules: [ { max: 1, min: 1, required: true, message: '请输入区域' } ], placeholder: '区域', span: 12 },
              { name: 'isRemote', value: '', label: '是否远机位', type: 'select', choose: [ { text: '是', value: '1' }, { text: '否', value: '0' } ], rules: [ { required: true, message: '请选择是否远机位' } ], placeholder: '是否远机位', span: 12 }
            ]
          },
          {
            id: '5',
            item: [
              { name: 'direction', value: '', label: '进港/出港', type: 'select', choose: [], rules: [ { required: true, message: '请选择出港OR进港' } ], placeholder: '请选择', span: 12 },
              { name: 'status', value: '', label: '状态', type: 'select', choose: [], rules: null, placeholder: '请选择', span: 12 }
            ]
          },
          {
            id: '6',
            item: [
              { name: 'description', value: '', label: '描述', type: 'textarea', rules: [ { max: 50, min: 1, message: '描述只能为少于五十位的字符串' } ], placeholder: '', span: 12 },
              { name: 'remark', value: '', label: '备注', type: 'textarea', rules: [ { max: 100, min: 1, message: '备注只能为少于一百位的字符串' } ], placeholder: '', span: 12 }
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
      getGateList () {
        let para = Object.assign({}, this.filters, this.$refs['page'].queryParam())
        this.listLoading = true
        API.getGateListPage().go(para).then((data) => {
          if (data.ok) {
            this.$refs['page'].set('total', data.attr.data.pager.recordCount)
            this.gates = data.attr.data.list
            this.filters.gateCode = ''
            // 获取所有的登机口
            let requestPara = {
              gateCode: '',
              airportCode: '',
              pageSize: 100000,
              pageNumber: 1
            }
            API.getGateListPage().go(requestPara).then((data) => {
              this.associatedGateChoose = []
              for (var i = 0; i < data.attr.data.list.length; i++) {
                var item = {}
                item['text'] = data.attr.data.list[i].displayCode
                item['value'] = data.attr.data.list[i].gateCode
                item['airportCode'] = data.attr.data.list[i].airportCode
                this.associatedGateChoose.push(item)
              }
            })
            this.getTerminalList()
            this.getWaitingHallsChoose()
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
        this.fields[4].item[0].choose = this.flightNatureChoose
        this.fields[5].item[0].choose = this.flightDirectionChoose
        this.fields[5].item[1].choose = this.flightStatusChoose
        this.fields[1].item[0].choose = this.airportsTypesChoose
        let addFields = Util.deepCopy(this.fields)
        this.$refs['addForm'].show(addFields)
      },
      // 显示编辑界面
      handleEdit: function (index, row) {
        this.addUpdateTag = 'update'
        this.airportCodeTag = row.airportCode
        this.fields[4].item[0].choose = this.flightNatureChoose
        this.fields[5].item[0].choose = this.flightDirectionChoose
        this.fields[5].item[1].choose = this.flightStatusChoose
        // 每次点击编辑的时候，要过滤可选的航站楼
        var terminalEdit = []
        for (var j = 0; j < this.terminals.length; j++) {
          if (this.terminals[j].airportCode === row.airportCode) {
            terminalEdit.push(this.terminals[j])
          }
        }
        this.fields[3].item[1].choose = terminalEdit
        // 每次点击编辑的时候，要过滤可选的候机区
        var waitingHallEdit = []
        for (var i = 0; i < this.waitingHallsChoose.length; i++) {
          if (this.waitingHallsChoose[i].airportCode === row.airportCode) {
            waitingHallEdit.push(this.waitingHallsChoose[i])
          }
        }
        this.fields[3].item[0].choose = waitingHallEdit
        // 机场的下拉框的数据
        this.fields[1].item[0].choose = this.airportsTypesChoose
        if (this.adminAirportChoose.length === 1) {
          this.fields[1].item[0].value = this.adminAirportChoose[0].value
        }
        // 每次点击编辑的时候，要过滤的关联登机口
        var associatedGateEdit = []
        for (var a = 0; a < this.associatedGateChoose.length; a++) {
          if (this.associatedGateChoose[a].airportCode === row.airportCode) {
            associatedGateEdit.push(this.associatedGateChoose[a])
          }
        }
        this.fields[2].item[1].choose = associatedGateEdit
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
      // 获取登机区的下拉框的数据
      getWaitingHallsChoose: function () {
        this.waitingHallsChoose = []
        for (var i = 1; i < this.$cache.array.waitinghalls.length; i++) {
          var item = {}
          item['text'] = this.$cache.array.waitinghalls[i].waittingHallCode
          item['value'] = this.$cache.array.waitinghalls[i].waittingHallCode
          item['airportCode'] = this.$cache.array.waitinghalls[i].airportCode
          this.waitingHallsChoose.push(item)
        }
      },
      // 候机区和所属航站楼的下拉框的数据被机场过滤
      getWaitingHallTerminalChoose: function (data) {
        if (data.airportCode !== this.airportCodeTag) {
          var waitingHall = null
          var terminal = null
          var associatedGate = null
          var waitingResult = []
          var terminalResult = []
          var associatedGateResult = []
          for (var i = 0; i < this.waitingHallsChoose.length; i++) {
            if (this.waitingHallsChoose[i].airportCode === data.airportCode) {
              waitingResult.push(this.waitingHallsChoose[i])
            }
          }
          for (var j = 0; j < this.terminals.length; j++) {
            if (this.terminals[j].airportCode === data.airportCode) {
              terminalResult.push(this.terminals[j])
            }
          }
          for (var k = 0; k < this.associatedGateChoose.length; k++) {
            if (this.associatedGateChoose[k].airportCode === data.airportCode) {
              associatedGateResult.push(this.associatedGateChoose[k])
            }
          }
          if (this.addUpdateTag === 'add') {
            var waitingHallStrAdd = 'waitingHall'
            var terminalStrAdd = 'terminal'
            var associatedGateStrAdd = 'associatedGate'
            // 传入的四个值分别代表要改变值的下标，值，要清空的key值，空值
            this.$refs['addForm'].changeSelectChoose(3, 0, waitingResult, waitingHallStrAdd, waitingHall)
            this.$refs['addForm'].changeSelectChoose(3, 1, terminalResult, terminalStrAdd, terminal)
            this.$refs['addForm'].changeSelectChoose(2, 1, associatedGateResult, associatedGateStrAdd, associatedGate)
          }
          if (this.addUpdateTag === 'update') {
            var waitingHallStrUpdate = 'waitingHall'
            var terminalStrUpdate = 'terminal'
            var associatedGateStrUpdate = 'associatedGate'
            this.$refs['editForm'].changeSelectChoose(3, 0, waitingResult, waitingHallStrUpdate, waitingHall)
            this.$refs['editForm'].changeSelectChoose(3, 1, terminalResult, terminalStrUpdate, terminal)
            this.$refs['editForm'].changeSelectChoose(2, 1, associatedGateResult, associatedGateStrUpdate, associatedGate)
          }
        }
        this.airportCodeTag = data.airportCode
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
      getFlightDirectionChoose: function () {
        this.flightDirectionChoose = baseUtil.getFlightDirectionChoose()
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
      this.getGateList()
      this.getFlightDirectionChoose()
      this.getFlightStatusChoose()
      this.getFlightNatureChoose()
    }
  }

</script>

<style scoped>

</style>
