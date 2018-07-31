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
          <el-input v-model="filters.agentCode" placeholder="代理人代码" @change="agentCodeUpper"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getGeneralagentsList">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleAdd">新增</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table v-bind:data="generalagents" highlight-current-row v-loading="listLoading" @selection-change="selsChange" :height="tableHeight" style="width: 100%;">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column type="index" width="60">
        <template scope="scope">
          {{pageSize * (pageNumber - 1) + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="agentCode" label="代理人编码" width="150" sortable>
      </el-table-column>
      <el-table-column prop="airportCode" label="运营机场" width="200" :formatter="formatterAirport">
      </el-table-column>
      <el-table-column prop="clientAirline" label="代理航空公司" width="400" :formatter="formatterAirline" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="description" label="描述" width="200" show-overflow-tooltip>
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
    <pagination :to="getGeneralagentsList" ref="page"></pagination>

    <!--新增界面-->
    <div class="generalagentTag">
       <common-add-or-update
        @getInputItem="agentCodeChange"
        type="add"
        :to="API.addGeneralagents().go"
        :callback="getGeneralagentsList"
        :labelWidth="100"
        ref="addForm"></common-add-or-update>
    </div>
    <!--编辑界面-->
    <div class="generalagentTag">
      <common-add-or-update
          @getInputItem="agentCodeChange"
          title="编辑"
          type="update"
          :to="API.editGeneralagents().go"
          :callback="getGeneralagentsList"
          :labelWidth="100"
          ref="editForm"></common-add-or-update>
    </div>
    <!--删除窗口-->
    <common-delete
        :to="API.removeGeneralagents().go"
        :callback="getGeneralagentsList"
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
  import airportSelect from './baseForm/SelectAirport'

  export default {
    data () {
      return {
        filters: {
          agentCode: '',
          airportCode: JSON.parse(sessionStorage.getItem('userStorage')).user.aiisAirports[0].airportCode,
          description: ''
        },
        generalagents: [],
        tableHeight: 495,
        listLoading: false,
        sels: [],
        airportLinesChoose: [],
        airportsChoose: [],
        // 用于分组的用户订阅的机场
        airportsTypesChoose: [],
        agentCodeValidate: null,
        usedCharecter: [],
        addUpdateTag: null,
        row: null,
        agentCodeTag: null,
        // 代理航空公司下拉框的验证
        clientAirlineValidate: null,
        // 代理航空公司改变的标记
        clientAirlineTag: null,
        // 新增编辑需要的字段
        fields: [
          { name: 'id', value: '', hidden: true },
          { name: 'airportCode', value: '', label: '运营机场', type: 'selectExplain', choose: [], rules: null, placeholder: '' },
          { name: 'agentCode', value: '', label: '代理人编码', type: 'text', rules: null, placeholder: '请输入代理人编码' },
          { name: 'clientAirline', value: [], filterable: true, multiple: true, label: '代理航空公司', type: 'select', choose: [], rules: null, placeholder: '' },
          { name: 'description', value: '', label: '描述', type: 'textarea', rules: [ { max: 10, min: 1, required: true, message: '请输入一位到十位字符串的描述' } ], placeholder: '请输入说明' }
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
      getGeneralagentsList () {
        // 用户已经订阅的机场
        let para = Object.assign({}, this.filters, this.$refs['page'].queryParam())
        this.listLoading = true
        API.getGeneralagentsListPage().go(para).then((data) => {
          if (data.ok) {
            this.$refs['page'].set('total', data.attr.data.pager.recordCount)
            this.generalagents = data.attr.data.list
            this.filters.agentCode = ''
            this.getUsedAgentCode()
            this.getAirportLines()
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
        this.clientAirlineTag = null
        this.fields[1].choose = this.airportsTypesChoose
        if (this.airportsChoose.length === 1) {
          this.fields[1].value = this.airportsChoose[0].value
        }
        this.fields[2].rules = [ { validator: this.agentCodeValidate } ]
        // this.fields[3].rules = [ { validator: this.clientAirlineValidate } ]
        this.fields[3].choose = this.airportLinesChoose
        let addFields = Util.deepCopy(this.fields)
        this.$refs['addForm'].show(addFields)
      },
      // 显示编辑界面
      handleEdit: function (index, row) {
        // 这边之所以不使用row在row上修改是因为row修改了，那么它本行的值也会被修改，会影响到cellValue
        var rowCache = null
        rowCache = Util.deepCopy(row)
        this.addUpdateTag = 'edit'
        this.row = rowCache
        rowCache.clientAirline = rowCache.clientAirline.split('/')
        this.clientAirlineTag = rowCache.clientAirline
        this.agentCodeTag = row.agentCode
        this.fields[1].choose = this.airportsTypesChoose
        this.fields[2].rules = [ { validator: this.agentCodeValidate } ]
        // this.fields[3].rules = [ { validator: this.clientAirlineValidate } ]
        this.fields[3].choose = this.airportLinesChoose
        let editFields = Util.deepCopy(this.fields)
        for (let i = 0; i < editFields.length; i++) {
          editFields[i].value = rowCache[editFields[i].name]
        }
        this.$refs['editForm'].show(editFields)
      },
      // 获取航空公司的所有详细信息,为了获得下拉框的value与text
      getAirportLines: function () {
          this.airportLinesChoose = []
          for (var i = 0; i < this.$cache.array.airlines.length; i++) {
            var item = {}
            item['text'] = this.$cache.array.airlines[i].cnname
            item['value'] = this.$cache.array.airlines[i].iatacode
            this.airportLinesChoose.push(item)
          }
      },
      // 格式化代理航空公司
      formatterAirline: function (row, column, cellValue) {
        // console.log('cellValue', cellValue)
        if (cellValue === null) {
          return
        }
        var result = []
        cellValue = cellValue.split('/')
        for (var i = 0; i < cellValue.length; i++) {
          for (var j = 0; j < this.airportLinesChoose.length; j++) {
            if (cellValue[i] === this.airportLinesChoose[j].value) {
              result.push(this.airportLinesChoose[j].text)
            }
          }
        }
        result = result.join(',')
        return result
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
      agentCodeChange: function (data) {
        console.log('data', data)
        if (data.agentCode !== this.agentCodeTag) {
          var singleUpperCode = null
          singleUpperCode = data.agentCode.toUpperCase()
          if (this.addUpdateTag === 'add') {
            var agentCodeStrAdd = 'agentCode'
            this.$refs['addForm'].changeInput(agentCodeStrAdd, singleUpperCode)
          }
          if (this.addUpdateTag === 'edit') {
            var agentCodeStrEdit = 'agentCode'
            this.$refs['editForm'].changeInput(agentCodeStrEdit, singleUpperCode)
          }
        }
        this.agentCodeTag = data.agentCode
      },
      // 限制代理人编码与代理航空公司在添加与删除的时候，代理人不会代理重复的
      // 为代理人代码进行pattern校正
      getUsedAgentCode: function () {
        this.agentCodeValidate = (rule, value, callback) => {
          // 是否输入了值
          if (!value) {
            return callback(new Error('代理人编码不能为空！'))
          }
          // 是否输入的值已经被使用了
          if (value.length !== 1) {
            return callback(new Error('请输入一位字母！'))
          }
          if ((value.charCodeAt(0) < 65) || (value.charCodeAt(0) > 122)) {
            return callback(new Error('只允许输入字母！'))
          }
          callback()
       }
      },
      agentCodeUpper: function (data) {
        this.filters.agentCode = data.toUpperCase()
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
      this.getGeneralagentsList()
    }
  }

</script>

<style>
   .generalagentTag .el-dialog--small {
    width: 40%!important
   }
</style>
