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
          <el-input v-model="filters.terminalCode" placeholder="编码" @change="terminalCodeUpper" ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getTerminalsList">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleAdd">新增</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table v-bind:data="terminals" highlight-current-row v-loading="listLoading" @selection-change="selsChange" :height="tableHeight" style="width: 100%;">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column type="index" width="60">
        <template scope="scope">
          {{pageSize * (pageNumber - 1) + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="terminalCode" label="编码" min-width="100" sortable>
      </el-table-column>
      <el-table-column prop="airportCode" label="运营机场" min-width="200" :formatter="formatterAirport">
      </el-table-column>
      <el-table-column prop="description" label="描述" min-width="150" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="remark" label="备注" min-width="150" show-overflow-tooltip>
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
    <pagination :to="getTerminalsList" ref="page"></pagination>

    <!--新增界面-->
    <div class="terminalTag">
      <common-add-or-update
        @getInputItem="terminalCodeChange"
        type="add"
        :to="API.addTerminals().go"
        :callback="getTerminalsList"
        :labelWidth="100"
        ref="addForm"></common-add-or-update>
    </div>
    <!--编辑界面-->
    <div class="terminalTag">
      <common-add-or-update
          @getInputItem="terminalCodeChange"
          title="编辑"
          type="update"
          :to="API.editTerminals().go"
          :callback="getTerminalsList"
          :labelWidth="100"
          ref="editForm"></common-add-or-update>
    </div>
    <!--删除窗口-->
    <common-delete
        :to="API.removeTerminals().go"
        :callback="getTerminalsList"
        :labelWidth="100"
        ref="delConfirm"></common-delete>
  </section>
</template>

<script>
  import Util from '../../common/js/util'
  import baseUtil from '../../common/js/base-util'
  import chooseDialog from './../../components/CommChooseGrid'
  import commonAddOrUpdate from './../../components/CommAddOrUpdate'
  import commonDelete from './../../components/CommDelete'
  import API from '../../api'
  import Pagination from '../../components/Pagination'
  import airportSelect from './../base/baseForm/SelectAirport'

  export default {
    data () {
      return {
        filters: {
          terminalCode: '',
          airportCode: JSON.parse(sessionStorage.getItem('userStorage')).user.aiisAirports[0].airportCode,
          description: ''
        },
        terminals: [],
        tableHeight: 495,
        listLoading: false,
        sels: [],
        airportsChoose: [],
        // 用于分组的用户订阅的机场
        airportsTypesChoose: [],
        addUpdateTag: null,
        terminalCodeTag: null,
        // 新增编辑需要的字段
        fields: [
          { name: 'id', value: '', hidden: true },
          { name: 'airportCode', value: '', label: '运营机场', type: 'selectExplain', choose: [], rules: null, placeholder: '' },
          { name: 'terminalCode', value: '', label: '编码', type: 'text', rules: [ { max: 4, min: 1, message: '请输入一位到四位字符串的编码' } ], placeholder: '请输入编码' },
          { name: 'description', value: '', label: '描述', type: 'textarea', rules: [ { max: 50, min: 1, message: '请输入一位到五十位字符串的描述' } ], placeholder: '请输入描述' },
          { name: 'remark', value: '', label: '备注', type: 'textarea', rules: [ { max: 100, min: 1, message: '请输入一位到一百位字符串的备注' } ], placeholder: '请输入备注' }
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
      getTerminalsList () {
        let para = Object.assign({}, this.filters, this.$refs['page'].queryParam())
        this.listLoading = true
        API.getTerminalsListPage().go(para).then((data) => {
          if (data.ok) {
            this.$refs['page'].set('total', data.attr.data.pager.recordCount)
            this.terminals = data.attr.data.list
            this.filters.terminalCode = ''
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
        this.terminalCodeTag = null
        this.fields[1].choose = this.airportsTypesChoose
        if (this.airportsChoose.length === 1) {
          this.fields[1].value = this.airportsChoose[0].value
        }
        let addFields = Util.deepCopy(this.fields)
        this.$refs['addForm'].show(addFields)
      },
      // 显示编辑界面
      handleEdit: function (index, row) {
        this.addUpdateTag = 'edit'
        this.terminalCodeTag = row.terminalCode
        this.agentCodeTag = row.agentCode
        this.fields[1].choose = this.airportsTypesChoose
        let editFields = Util.deepCopy(this.fields)
        for (let i = 0; i < editFields.length; i++) {
          editFields[i].value = row[editFields[i].name]
        }
        this.$refs['editForm'].show(editFields)
      },
      // 格式化运营机场
      formatterAirport: function (row, column, cellValue) {
        for (var i = 0; i < this.airportsChoose.length; i++) {
          if (cellValue === this.airportsChoose[i].value) {
            return this.airportsChoose[i].text
          }
        }
      },
      // 代理人代码自动转换成大写字母
      terminalCodeChange: function (data) {
        if (data.terminalCode !== this.terminalCodeTag) {
          console.log(1)
          var singleUpperCode = null
          singleUpperCode = data.terminalCode.toUpperCase()
          if (this.addUpdateTag === 'add') {
            var terminalCodeStrAdd = 'terminalCode'
            this.$refs['addForm'].changeInput(terminalCodeStrAdd, singleUpperCode)
          }
          if (this.addUpdateTag === 'edit') {
            var terminalCodeStrEdit = 'terminalCode'
            this.$refs['editForm'].changeInput(terminalCodeStrEdit, singleUpperCode)
          }
        }
        this.terminalCodeTag = data.terminalCode
      },
      terminalCodeUpper: function (data) {
        this.filters.terminalCode = data.toUpperCase()
      },
      // 机场下拉框的通用模块
      getBaseAirports: function () {
        var airportsData = this.$refs['selectAirport'].getAirportList()
        this.airportsChoose = airportsData.adminAirportChoose
        this.airportsTypesChoose = airportsData.airportsTypesChoose
      },
      // 当机场下拉框的值变化时执行的函数
      getAirportCode: function (value) {
        this.filters.airportCode = value
      }
    },
    mounted () {
      this.getBaseAirports()
      this.getTerminalsList()
    }
  }

</script>

<style>
   .terminalTag .el-dialog--small {
    width: 40%!important
   }
</style>
