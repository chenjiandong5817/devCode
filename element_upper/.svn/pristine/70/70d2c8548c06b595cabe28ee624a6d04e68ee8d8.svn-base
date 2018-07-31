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
          <el-input v-model="filters.rankCode" placeholder="级别编码" @change="rankCodeUpper"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getViprankList">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleAdd">新增</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table v-bind:data="vipranks" highlight-current-row v-loading="listLoading" @selection-change="selsChange" :height="tableHeight" style="width: 100%;">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column type="index" width="60">
        <template scope="scope">
          {{pageSize * (pageNumber - 1) + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="rankCode" label="级别编码" width="120" sortable>
      </el-table-column>
      <el-table-column prop="rankOrder" label="优先级" width="100">
      </el-table-column>
      <el-table-column prop="airportCode" label="运营机场" width="200" :formatter="formatterAirport">
      </el-table-column>
      <el-table-column prop="description" label="描述" min-width="100" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="remark" label="备注" min-width="100" show-overflow-tooltip>
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
    <pagination :to="getViprankList" ref="page"></pagination>

    <!--新增界面-->
    <common-add-or-update
        @getInputItem="rankCodeChange"
        type="add"
        :to="API.addViprank().go"
        :callback="getViprankList"
        :labelWidth="100"
        ref="addForm"></common-add-or-update>
    <!--编辑界面-->
    <common-add-or-update
        @getInputItem="rankCodeChange"
        title="编辑"
        type="update"
        :to="API.editViprank().go"
        :callback="getViprankList"
        :labelWidth="100"
        ref="editForm"></common-add-or-update>
    <!--删除窗口-->
    <common-delete
        :to="API.removeViprank().go"
        :callback="getViprankList"
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
          rankCode: '',
          airportCode: JSON.parse(sessionStorage.getItem('userStorage')).user.aiisAirports[0].airportCode,
          description: ''
        },
        vipranks: [],
        tableHeight: 495,
        listLoading: false,
        sels: [],
        airportsChoose: [],
        // 用于分组的用户订阅的机场
        airportsTypesChoose: [],
        rankCodeValidate: null,
        rankOrderValidate: null,
        rankCodeTag: null,
        addUpdateTag: null,
        rankCodeCharacter: [],
        // 新增编辑需要的字段
        fields: [
          { name: 'id', value: '', hidden: true },
          { name: 'airportCode', value: '', label: '运营机场', filterable: true, type: 'selectExplain', choose: [], rules: null, placeholder: '' },
          { name: 'rankCode', value: '', label: '级别编码', type: 'text', rules: null, placeholder: '请输入级别编码' },
          { name: 'rankOrder', value: '', label: '优先级', type: 'number', rules: null, placeholder: '请输入优先级', min: 0, max: 99 },
          { name: 'description', value: '', label: '描述', type: 'textarea', rules: [ { max: 10, min: 1, required: true, message: '请输入一位到十位字符串的描述' } ], placeholder: '请输入说明' },
          { name: 'remark', value: '', label: '备注', type: 'textarea', rules: [ { max: 100, min: 1, message: '请输入一位到一百位字符串的描述' } ], placeholder: '请输入备注' }
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
      getViprankList () {
        // 用户已经订阅的机场
        let para = Object.assign({}, this.filters, this.$refs['page'].queryParam())
        this.listLoading = true
        API.getViprankListPage().go(para).then((data) => {
          if (data.ok) {
            this.$refs['page'].set('total', data.attr.data.pager.recordCount)
            this.vipranks = data.attr.data.list
            this.filters.rankCode = ''
            this.getUsedRankCodes()
            this.getrankCodeValidate()
            this.getRankOrderValidate()
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
        this.fields[1].choose = this.airportsTypesChoose
        if (this.airportsChoose.length === 1) {
          this.fields[1].value = this.airportsChoose[0].value
        }
        this.fields[2].rules = [ { validator: this.rankCodeValidate } ]
        this.fields[3].rules = [ { validator: this.rankOrderValidate } ]
        let addFields = Util.deepCopy(this.fields)
        this.$refs['addForm'].show(addFields)
      },
      // 显示编辑界面
      handleEdit: function (index, row) {
        // 要排除掉编辑时的那个区域代码
        var result = []
        for (var i = 0; i < this.rankCodeCharacter.length; i++) {
          if (this.rankCodeCharacter[i].rankCode !== row.rankCode) {
            result.push(this.rankCodeCharacter[i])
          }
        }
        this.rankCodeCharacter = result
      	this.addUpdateTag = 'edit'
        this.rankCodeTag = row.rankCode
      	this.fields[1].choose = this.airportsTypesChoose
      	this.fields[2].rules = [ { validator: this.rankCodeValidate } ]
        this.fields[3].rules = [ { validator: this.rankOrderValidate } ]
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
      rankCodeChange: function (data) {
        // console.log('data', data)
        if (data.rankCode !== this.rankCodeTag) {
          var singleUpperCode = null
          singleUpperCode = data.rankCode.toUpperCase()
          if (this.addUpdateTag === 'add') {
            var rankCodeTagStrAdd = 'rankCode'
            this.$refs['addForm'].changeInput(rankCodeTagStrAdd, singleUpperCode)
          }
          if (this.addUpdateTag === 'edit') {
            var rankCodeStrEdit = 'rankCode'
            this.$refs['editForm'].changeInput(rankCodeStrEdit, singleUpperCode)
          }
        }
        this.rankCodeTag = data.rankCode
      },
      getrankCodeValidate: function () {
        this.rankCodeValidate = (rule, value, callback) => {
          if (!value) {
            return callback(new Error('级别编码不能为空！'))
          }
          if (value.length !== 1) {
            return callback(new Error('请输入一位字母！'))
          }
          if ((value.charCodeAt(0) < 65) || (value.charCodeAt(0) > 122)) {
            return callback(new Error('只允许输入字母！'))
          }
          for (var i = 0; i < this.rankCodeCharacter.length; i++) {
          	// console.log('value.toUpperCase', value.toUpperCase())
          	// console.log('this.rankCodeCharacter[i].rankCode', this.rankCodeCharacter[i].rankCode)
          	if (value.toUpperCase() === this.rankCodeCharacter[i].rankCode) {
          		return callback(new Error('级别编码已存在！'))
          	}
          }
          callback()
       }
      },
      getRankOrderValidate: function () {
        this.rankOrderValidate = (rule, value, callback) => {
          if (value < 0) {
            return callback(new Error('优先级不能为负数'))
          }
          if (!Number.isInteger(value)) {
            callback(new Error('请输入数字值'))
          }
          if (value > 99) {
            callback(new Error('优先级的值只能在0~99之间'))
          }
          callback()
        }
      },
      // 获取已经使用过的大写字母
      getUsedRankCodes: function () {
      	this.rankCodeCharacter = []
      	for (var i = 0; i < this.vipranks.length; i++) {
      		var item = {}
      		item['rankCode'] = this.vipranks[i].rankCode
      		item['description'] = this.vipranks[i].description
      		this.rankCodeCharacter.push(item)
      	}
      	// console.log('this.rankCodeCharacter', this.rankCodeCharacter)
      },
      rankCodeUpper: function (data) {
        this.filters.rankCode = data.toUpperCase()
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
      this.getViprankList()
    }
  }

</script>

<style scoped>

</style>
