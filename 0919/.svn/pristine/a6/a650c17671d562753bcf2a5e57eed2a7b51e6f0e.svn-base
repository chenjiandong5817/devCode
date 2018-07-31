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
          <el-input v-model="filters.arponCode" placeholder="编码" @change="arponCodeUpper" ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getApronsList">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleAdd">新增</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table v-bind:data="aprons" highlight-current-row v-loading="listLoading" @selection-change="selsChange" :height="tableHeight" style="width: 100%;">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column type="index" width="60">
        <template scope="scope">
          {{pageSize * (pageNumber - 1) + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="arponCode" label="编码" width="90" sortable>
      </el-table-column>
      <el-table-column prop="airportCode" label="所属机场" width="180" :formatter="formatterAirport" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="terminal" label="所属航站楼" min-width="150" :formatter="formatterTerminal">
      </el-table-column>
      <el-table-column prop="description" label="描述" min-width="80" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="remark" label="备注" min-width="120" show-overflow-tooltip>
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
    <pagination :to="getApronsList" ref="page"></pagination>

    <!--新增界面-->
    <common-add-or-update
        @getSelectItem="getTerminalChoose"
        @getInputItem="arponCodeChange"
        type="add"
        :to="API.addAprons().go"
        :callback="getApronsList"
        :labelWidth="100"
        ref="addForm"></common-add-or-update>
    <!--编辑界面-->
    <common-add-or-update
        @getSelectItem="getTerminalChoose"
        @getInputItem="arponCodeChange"
        title="编辑"
        type="update"
        :to="API.editAprons().go"
        :callback="getApronsList"
        :labelWidth="100"
        ref="editForm"></common-add-or-update>
    <!--删除窗口-->
    <common-delete
        :to="API.removeAprons().go"
        :callback="getApronsList"
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
          arponCode: '',
          airportCode: JSON.parse(sessionStorage.getItem('userStorage')).user.aiisAirports[0].airportCode
        },
        aprons: [],
        tableHeight: 495,
        listLoading: false,
        terminals: [],
        airports: [],
        // 添加还是编辑的标记
        addUpdateTag: null,
        // 用于识别机场的值是否改变
        airportCodeTag: '',
        arponCodeTag: '',
        sels: [],
        // 登录用户订阅的机场
        adminAirportChoose: [],
        // 用于分组的用户订阅的机场
        airportsTypesChoose: [],
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
               { name: 'terminal', value: '', label: '所属航站楼', type: 'select', choose: [], rules: null, placeholder: '请选择所属航站楼', span: 12 }
            ]
          },
          {
            id: '2',
            item: [
               { name: 'arponCode', value: '', label: '编码', type: 'text', rules: [ { max: 4, min: 1, required: true, message: '请输入一位到四位字符串的编码' } ], placeholder: '编码', span: 12 },
               { name: 'description', value: '', label: '描述', type: 'textarea', rules: [ { max: 50, min: 1, message: '请输入少于五十位字符串的描述' } ], placeholder: '', span: 12 }
            ]
          },
          {
            id: '3',
            item: [
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
      getApronsList () {
        let para = Object.assign({}, this.filters, this.$refs['page'].queryParam())
        this.listLoading = true
        API.getApronsListPage().go(para).then((data) => {
          if (data.ok) {
            this.$refs['page'].set('total', data.attr.data.pager.recordCount)
            this.aprons = data.attr.data.list
            this.filters.arponCode = ''
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
        this.arponCodeTag = null
        this.fields[1].item[0].choose = this.airportsTypesChoose
        if (this.adminAirportChoose.length === 1) {
          this.fields[1].item[0].value = this.adminAirportChoose[0].value
        }
        let addFields = Util.deepCopy(this.fields)
        this.$refs['addForm'].show(addFields)
      },
      // 显示编辑界面
      handleEdit: function (index, row) {
        this.addUpdateTag = 'update'
        this.airportCodeTag = row.airportCode
        this.arponCodeTag = row.arponCode
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
      // 通过机场代码的筛选，选出所属航站楼
      getTerminalChoose: function (data) {
        if (data.airportCode !== this.airportCodeTag) {
          var terminalValue = null
          var terminalResult = []
          for (var i = 0; i < this.terminals.length; i++) {
            if (this.terminals[i].airportCode === data.airportCode) {
              terminalResult.push(this.terminals[i])
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
        this.airportCodeTag = data.airportCode
      },
      arponCodeChange: function (data) {
        if (data.arponCode !== this.arponCodeTag) {
          console.log(1)
          var singleUpperCode = null
          singleUpperCode = data.arponCode.toUpperCase()
          if (this.addUpdateTag === 'add') {
            var bridgeCodeTagStrAdd = 'arponCode'
            this.$refs['addForm'].changeInput(bridgeCodeTagStrAdd, singleUpperCode)
          }
          if (this.addUpdateTag === 'update') {
            var arponCodeStrEdit = 'arponCode'
            this.$refs['editForm'].changeInput(arponCodeStrEdit, singleUpperCode)
          }
        }
        this.arponCodeTag = data.arponCode
      },
      arponCodeUpper: function (data) {
        this.filters.arponCode = data.toUpperCase()
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
      }
    },
    mounted () {
  	  this.getTerminalList()
  	  this.getAirportList()
      this.getApronsList()
    }
  }

</script>

<style scoped>

</style>
