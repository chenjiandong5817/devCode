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
        <el-select v-model="filters.airportnature" placeholder="请选择">
          <el-option
            v-for="item in flightNatureChoose"
            :key="item.value"
            :label="item.text"
            :value="item.value">
          </el-option>
        </el-select>
        </el-form-item>
        <el-form-item>
          <el-input v-model="filters.iatacode" placeholder="IATA编码" @change="iataCodeUpper"></el-input>
        </el-form-item>
        <el-form-item>
          <el-input v-model="filters.icaocode" placeholder="ICAO编码" @change="icaoCodeUpper"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getAirports">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleAdd">新增</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table v-bind:data="airports" highlight-current-row v-loading="listLoading" @selection-change="selsChange" :height="tableHeight" style="width: 100%;">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column type="index" width="60">
        <template scope="scope">
          {{pageSize * (pageNumber - 1) + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="iatacode" label="IATA编码" width="120" sortable>
      </el-table-column>
      <el-table-column prop="icaocode" label="ICAO编码" width="120" sortable>
      </el-table-column>
      <el-table-column prop="airportcnname" label="中文名称" width="180" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="airportenname" label="英文名称" width="180" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="airportnature" label="地区" width="120" :formatter="baseUtil.formatterNature">
      </el-table-column>
      <el-table-column prop="cnabbr1w" label="一字简称" width="120">
      </el-table-column>
      <el-table-column prop="cnabbr2w" label="二字简称" width="120">
      </el-table-column>
      <!-- <el-table-column prop="cityid" label="城市编号" width="120"> -->
      <!-- </el-table-column> -->
      <el-table-column prop="airporttimezone" label="时区" width="120">
      </el-table-column>
      <el-table-column prop="displaycnname" label="显示中文" width="180" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="displayenname" label="显示英文" width="180" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="countrycode" label="国家代码" width="120">
      </el-table-column>
      <el-table-column prop="firstpinyin" label="拼音缩写" min-width="120">
      </el-table-column>
      <el-table-column label="操作" width="180" fixed="right" >
        <template scope="scope">
          <el-button size="small" @click="handleEdit(scope.$index, scope.row)"><span><i class="el-icon-edit"></i> 编辑 </span></el-button>
          <el-button size="small" @click="handleDel(scope.$index, scope.row)"><span><i class="el-icon-delete"></i> 删除 </span></el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--分页-->
    <pagination :to="getAirports" ref="page"></pagination>

    <!--新增界面-->
    <common-add-or-update
        @getInputItem="IATAICAOChange"
        type="add"
        :to="API.addAirport().go"
        :callback="getAirports"
        :labelWidth="100"
        ref="addForm"></common-add-or-update>
    <!--编辑界面-->
    <common-add-or-update
        @getInputItem="IATAICAOChange"
        title="编辑"
        type="update"
        :to="API.editAirport().go"
        :callback="getAirports"
        :labelWidth="100"
        ref="editForm"></common-add-or-update>

    <!--删除窗口-->
    <common-delete
        :to="API.removeAirport().go"
        :callback="getAirports"
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
          airportnature: null,
          iatacode: '',
          icaocode: ''
        },
        flightNatureChoose: [],
        airports: [],
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
              { name: 'iatacode', value: '', label: 'IATA编码', type: 'text', rules: null, span: 12 },
              { name: 'icaocode', value: '', label: 'ICAO编码', type: 'text', rules: null, span: 12 }
            ]
           },
           {
            id: '2',
            item: [
              { name: 'airportcnname', value: '', label: '中文名称', type: 'text', rules: [ { max: 100, min: 1, message: '中文名称只能为少于一百位的字符串' } ], span: 12 },
              { name: 'airportenname', value: '', label: '英文名称', type: 'text', rules: [ { max: 50, min: 1, message: '英文名称只能为少于五十位的字符串' } ], span: 12 }
            ]
           },
           {
            id: '3',
            item: [
              { name: 'airportnature', value: '', label: '地区代码', type: 'select', choose: [], rules: [ { required: true, message: '请选择地区代码' } ], span: 12 },
              { name: 'cnabbr1w', value: '', label: '一字代码', type: 'text', rules: [ { max: 20, min: 1, message: '一字代码只能为少于二十位的字符串' } ], span: 12 }
            ]
           },
           {
            id: '4',
            item: [
              { name: 'cnabbr2w', value: '', label: '二字代码', type: 'text', rules: [ { max: 20, min: 1, message: '二字代码只能为少于二十位的字符串' } ], span: 12 },
              // { name: 'cityid', value: '', label: '城市编号', type: 'number', rules: null },
              { name: 'airporttimezone', value: '', label: '时区', type: 'text', rules: [ { max: 10, min: 1, message: '时区只能为少于十位的字符串' } ], span: 12 }
            ]
           },
           {
            id: '5',
            item: [
              { name: 'displaycnname', value: '', label: '显示中文名称', type: 'text', rules: [ { max: 40, min: 1, message: '显示中文名称只能为少于四十位的字符串' } ], span: 12 },
              { name: 'displayenname', value: '', label: '显示英文名称', type: 'text', rules: [ { max: 50, min: 1, message: '显示英文名称只能为少于五十位的字符串' } ], span: 12 }
            ]
           },
           {
            id: '6',
            item: [
              { name: 'countrycode', value: '', label: '国家代码', type: 'text', rules: [ { max: 3, min: 3, message: '请输入三位国家代码' } ], span: 12 },
             { name: 'firstpinyin', value: '', label: '拼音缩写', type: 'text', rules: [ { max: 20, min: 1, message: '拼音缩写只能为少于二十位的字符串' } ], span: 12 }
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
      pagination: Pagination
    },
    methods: {
      // 获取用户列表
      getAirports () {
        let para = Object.assign({}, this.filters, this.$refs['page'].queryParam())
        this.listLoading = true
        API.getAirportListPage().go(para).then((data) => {
          if (data.ok) {
            this.$refs['page'].set('total', data.attr.data.pager.recordCount)
            this.airports = data.attr.data.list
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
        this.$refs['delConfirm'].del(row.id)
      },
      // 显示新增界面
      handleAdd: function () {
        this.addUpdateTag = 'add'
        this.IATAValueTag = null
        this.ICAOValueTag = null
        this.fields[1].item[0].rules = this.IATAValidate
        this.fields[1].item[1].rules = this.ICAOValidate
        this.fields[3].item[0].choose = this.flightNatureChoose
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
        this.fields[3].item[0].choose = this.flightNatureChoose
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
            if ((value.charCodeAt(0) < 65) || (value.charCodeAt(0) > 122)) {
            return callback(new Error('只允许输入字母！'))
          }
          }
          if (valueToArrayIATA.length !== 3) {
            return callback(new Error('请输入三位字符串'))
          }
          callback()
       }
       this.ICAOValidate = (rule, value, callback) => {
          var valueToArrayICAO = value.split('')
          for (var i = 0; i < valueToArrayICAO.length; i++) {
            if ((value.charCodeAt(0) < 65) || (value.charCodeAt(0) > 122)) {
            return callback(new Error('只允许输入字母！'))
            }
            }
            if (valueToArrayICAO.length !== 4) {
              return callback(new Error('请输四位字符串'))
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
      // 转换为大小写
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
      this.getAirports()
      this.getIATAICAOValidate()
      this.getFlightNatureChoose()
    }
  }

</script>

<style scoped>

</style>
