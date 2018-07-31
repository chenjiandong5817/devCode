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
          <el-input v-model="filters.iatacode" placeholder="IATA代码" @change="iataCodeUpper"></el-input>
        </el-form-item>
        <el-form-item>
          <el-input v-model="filters.icaocode" placeholder="ICAO代码" @change="icaoCodeUpper"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getAirlinesList">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleAdd">新增</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table v-bind:data="airlines" highlight-current-row v-loading="listLoading" @selection-change="selsChange" :height="tableHeight" style="width: 100%;">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column type="index" width="60">
        <template scope="scope">
          {{pageSize * (pageNumber - 1) + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="iatacode" label="IATA代码" width="115">
      </el-table-column>
      <el-table-column prop="icaocode" label="ICAO代码" width="115">
      </el-table-column>
      <el-table-column prop="isvirtual" label="是否虚拟" width="180" :formatter="baseUtil.formatterIsNo">
      </el-table-column>
      <el-table-column prop="cnname" label="中文名称" width="130" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="enname" label="英文名称" width="130" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="cnnabbr" label="中文简称" width="130">
      </el-table-column>
      <el-table-column prop="ennabbr" label="英文简称" width="130">
      </el-table-column>
      <el-table-column prop="airlinesnature" label="航空公司属性" width="130" :formatter="baseUtil.formatterNature">
      </el-table-column>
      <!-- <el-table-column prop="logo" label="图标" width="115">
      </el-table-column> -->
      <el-table-column prop="countrycode" label="国家3字代码" width="120">
      </el-table-column>
      <el-table-column prop="ticketprefix" label="客票号前缀" width="120">
      </el-table-column>
      <el-table-column prop="billprefix" label="货票号前缀" width="120">
      </el-table-column>
      <el-table-column prop="generalagent" label="总代理" width="100">
      </el-table-column>
      <el-table-column label="操作" width="180" fixed="right" >
        <template scope="scope">
          <el-button size="small" @click="handleEdit(scope.$index, scope.row)"><span><i class="el-icon-edit"></i> 编辑 </span></el-button>
          <el-button size="small" @click="handleDel(scope.$index, scope.row)"><span><i class="el-icon-delete"></i> 删除 </span></el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--分页-->
    <pagination :to="getAirlinesList" ref="page"></pagination>

    <!--新增界面-->
    <common-add-or-update
        @getInputItem="IATAICAOChange"
        type="add"
        :to="API.addAirlines().go"
        :callback="getAirlinesList"
        :labelWidth="125"
        ref="addForm"></common-add-or-update>
    <!--编辑界面-->
    <common-add-or-update
        @getInputItem="IATAICAOChange"
        title="编辑"
        type="update"
        :to="API.editAirlines().go"
        :callback="getAirlinesList"
        :labelWidth="125"
        ref="editForm"></common-add-or-update>
    <!--删除窗口-->
    <common-delete
        :to="API.removeAirlines().go"
        :callback="getAirlinesList"
        :labelWidth="100"
        ref="delConfirm"></common-delete>
  </section>
</template>

<script>
  import Util from '../../common/js/util'
  import baseUtil from '../../common/js/base-util'
  import chooseDialog from './../../components/CommChooseGrid'
  import commonAddOrUpdate from './baseForm/CommAddOrUpdate'
  import commonDelete from './../../components/CommDelete'
  import API from '../../api'
  import Pagination from '../../components/Pagination'

  export default {
    data () {
      return {
        filters: {
          iatacode: '',
          icaocode: ''
        },
        flightNatureChoose: [],
        airlines: [],
        tableHeight: 495,
        listLoading: false,
        // 验证输入IATA以及ICAO
        IATAValidate: null,
        ICAOValidate: null,
        // 将IATA与ICAO转换成大写的时候用的
        addUpdateTag: null,
        IATAValueTag: null,
        ICAOValueTag: null,
        sels: [],
        airports: [],
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
              { name: 'iatacode', value: '', label: 'IATA代码', type: 'text', rules: null, placeholder: '请输入IATA代码', span: 12 },
              { name: 'icaocode', value: '', label: 'ICAO代码', type: 'text', rules: null, placeholder: '请输入ICAO代码', span: 12 }
            ]
           },
           {
            id: '2',
            item: [
              { name: 'enname', value: '', label: '英文名称', type: 'text', rules: [ { max: 50, min: 1, message: '英文名称只能为少于五十位的字符串' } ], placeholder: '', span: 12 },
              { name: 'cnname', value: '', label: '中文名称', type: 'text', rules: [ { max: 50, min: 1, required: true, message: '请输入一位到五十位字符串的中文名称' } ], placeholder: '', span: 12 }
            ]
           },
           {
            id: '3',
            item: [
              { name: 'enabbr', value: '', label: '英文简称', type: 'text', rules: [ { max: 50, min: 1, message: '英文只能为少于五十位的字符串' } ], placeholder: '', span: 12 },
              { name: 'cnabbr', value: '', label: '中文简称', type: 'text', rules: [ { max: 10, min: 1, required: true, message: '请输入一位到十位字符串的中文简称' } ], placeholder: '请输入中文简称', span: 12 }
            ]
           },
           {
            id: '4',
            item: [
              { name: 'isvirtual', value: '0', label: '是否虚拟', type: 'select', choose: [ { text: '否', value: '0' }, { text: '是', value: '1' } ], rules: null, placeholder: '', span: 12 },
              { name: 'airlinesnature', value: '', label: '航空公司属性', type: 'select', choose: [], rules: [ { required: true, message: '航空公司属性不能为空' } ], placeholder: '请输入航空公司属性', span: 12 }
            ]
           },
           {
            id: '5',
            item: [
              { name: 'generalagent', value: '', label: '总代理', type: 'text', rules: [ { max: 10, min: 1, message: '总代理只能为少于十位的字符串' } ], placeholder: '', span: 12 },
              { name: 'countrycode', value: '', label: '国家3字代码', type: 'text', rules: [ { max: 3, min: 3, message: '国家3字代码只能为少于三位的字符串' } ], placeholder: '', span: 12 }
            ]
           },
           {
            id: '6',
            item: [
              { name: 'ticketprefix', value: '', label: '客票号前缀', type: 'text', rules: [ { max: 3, min: 3, message: '客票号前缀只能为三位的字符串' } ], placeholder: '', span: 12 },
              { name: 'billprefix', value: '', label: '货票号前缀', type: 'text', rules: [ { max: 3, min: 3, message: '货票号前缀只能为少于三位的字符串' } ], placeholder: '', span: 12 }
            ]
           }
           // {
           //  id: '7',
           //  item: [
           //    { name: 'generalagent', value: '', label: '总代理', type: 'text', rules: [ { max: 10, min: 1, message: '总代理只能为少于十位的字符串' } ], placeholder: '', span: 12 }
           //  ]
           // }
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
      pagination: Pagination
    },
    methods: {
      // 获取用户列表
      getAirlinesList () {
        let para = Object.assign({}, this.filters, this.$refs['page'].queryParam())
        this.listLoading = true
        API.getAirlines().go(para).then((data) => {
          if (data.ok) {
            this.$refs['page'].set('total', data.attr.data.pager.recordCount)
            this.airlines = data.attr.data.list
            this.filters.iatacode = ''
            this.filters.icaocode = ''
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
        this.$refs['delConfirm'].del(row)
      },
      // 显示新增界面
      handleAdd: function () {
        this.addUpdateTag = 'add'
        this.IATAValueTag = null
        this.ICAOValueTag = null
        this.fields[1].item[0].rules = this.IATAValidate
        this.fields[1].item[1].rules = this.ICAOValidate
        this.fields[4].item[1].choose = this.flightNatureChoose
        let addFields = Util.deepCopy(this.fields)
        this.$refs['addForm'].show(addFields)
      },
      // 显示编辑界面
      handleEdit: function (index, row) {
        this.addUpdateTag = 'update'
        this.IATAValueTag = row.iatacode
        this.ICAOValueTag = row.icaocode
        this.fields[1].item[0].rules = this.IATAValidate
        this.fields[1].item[1].rules = this.ICAOValidate
        this.fields[4].item[1].choose = this.flightNatureChoose
        let editFields = Util.deepCopy(this.fields)
        for (let i = 0; i < editFields.length; i++) {
          for (let j = 0; j < editFields[i].item.length; j++) {
            editFields[i].item[j].value = row[editFields[i].item[j].name]
          }
        }
        this.$refs['editForm'].show(editFields)
      },
      getIATAICAOValidate: function () {
        this.IATAValidate = (rule, value, callback) => {
          var valueToArrayIATA = value.split('')
          for (var i = 0; i < valueToArrayIATA.length; i++) {
            if (((valueToArrayIATA[i].charCodeAt(0) < 65) || (valueToArrayIATA[i].charCodeAt(0) > 122)) && ((valueToArrayIATA[i].charCodeAt(0) < 48) || (valueToArrayIATA[i].charCodeAt(0) > 57))) {
            return callback(new Error('默认是大写字母或者数字'))
          }
          }
          if (valueToArrayIATA.length > 2) {
            return callback(new Error('请输入一位到两位字符串的IATA代码'))
          }
          if (!value) {
            return callback(new Error('IATA代码不能为空'))
          }
          callback()
       }
       this.ICAOValidate = (rule, value, callback) => {
          var valueToArrayICAO = value.split('')
          for (var i = 0; i < valueToArrayICAO.length; i++) {
            if (((valueToArrayICAO[i].charCodeAt(0) < 65) || (valueToArrayICAO[i].charCodeAt(0) > 122)) && ((valueToArrayICAO[i].charCodeAt(0) < 48) || (valueToArrayICAO[i].charCodeAt(0) > 57))) {
            return callback(new Error('默认是大写字母或者数字'))
          }
          }
          if (valueToArrayICAO.length > 3) {
            return callback(new Error('请输入一位到三位字符串的IATA代码'))
          }
          if (!value) {
            return callback(new Error('ICAO代码不能为空'))
          }
          callback()
       }
      },
      IATAICAOChange: function (data) {
        if (data.iatacode !== this.IATAValueTag) {
          var UpperCodeIATA = null
          UpperCodeIATA = data.iatacode.toUpperCase()
          if (this.addUpdateTag === 'add') {
            var iataCodeStrAdd = 'iatacode'
            this.$refs['addForm'].changeInput(iataCodeStrAdd, UpperCodeIATA)
          }
          if (this.addUpdateTag === 'update') {
            var iataCodeStrEdit = 'iatacode'
            this.$refs['editForm'].changeInput(iataCodeStrEdit, UpperCodeIATA)
          }
        }
        if (data.icaocode !== this.ICAOValueTag) {
          var UpperCodeICAO = null
          UpperCodeICAO = data.icaocode.toUpperCase()
          if (this.addUpdateTag === 'add') {
            var icaoCodeStrAdd = 'icaocode'
            this.$refs['addForm'].changeInput(icaoCodeStrAdd, UpperCodeICAO)
          }
          if (this.addUpdateTag === 'update') {
            var icaoCodeStrEdit = 'icaocode'
            this.$refs['editForm'].changeInput(icaoCodeStrEdit, UpperCodeICAO)
          }
        }
        this.IATAValueTag = data.iatacode
        this.ICAOValueTag = data.icaocode
      },
      iataCodeUpper: function (data) {
        this.filters.iatacode = data.toUpperCase()
      },
      icaoCodeUpper: function (data) {
        this.filters.icaocode = data.toUpperCase()
      },
      getFlightNatureChoose: function () {
        this.flightNatureChoose = baseUtil.getFlightNatureChoose()
      }
    },
    mounted () {
      this.getAirlinesList()
      this.getIATAICAOValidate()
      this.getFlightNatureChoose()
    }
  }

</script>

<style scoped>

</style>
