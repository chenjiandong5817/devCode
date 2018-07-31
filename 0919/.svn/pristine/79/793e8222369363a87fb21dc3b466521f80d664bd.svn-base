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
          <el-select v-model="filters.superior" filterable placeholder="请选择异常代码父级">
            <el-option
              v-for="item in superiorChoose"
              :key="item.value"
              :label="item.text"
              :value="item.value">
              <span style="float: left">{{ item.text }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{ item.code }}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-input v-model="filters.irregularCode" placeholder="异常编码" @change="irregularCodeUpper"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getIrregularcodesList">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleAdd">新增</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table v-bind:data="irregularcodes" highlight-current-row v-loading="listLoading" @selection-change="selsChange" :height="tableHeight" style="width: 100%;">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column type="index" width="60">
        <template scope="scope">
          {{pageSize * (pageNumber - 1) + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="irregularCode" label="异常编码" width="115" sortable>
      </el-table-column>
      <el-table-column prop="airportCode" label="机场" width="180" :formatter="formatterAirport" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="useTime" label="使用次数" width="100">
      </el-table-column>
      <el-table-column prop="priority" label="优先级" width="85">
      </el-table-column>
      <el-table-column prop="flightEventCode" label="关联事件代码" width="180" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="superior" label="父级" width="85">
      </el-table-column>
      <el-table-column prop="disabled" label="状态" width="75" :formatter="baseUtil.formatterDisable">
      </el-table-column>
      <el-table-column prop="description" label="描述" width="180" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="remark" label="备注" width="180" show-overflow-tooltip>
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
    <pagination :to="getIrregularcodesList" ref="page"></pagination>

    <!--新增界面-->
    <common-add-or-update
        type="add"
        :to="API.addIrregularcodes().go"
        :callback="getIrregularcodesList"
        :labelWidth="100"
        ref="addForm"></common-add-or-update>
    <!--编辑界面-->
    <common-add-or-update
        title="编辑"
        type="update"
        :to="API.editIrregularcodes().go"
        :callback="getIrregularcodesList"
        :labelWidth="100"
        ref="editForm"></common-add-or-update>
    <!--删除窗口-->
    <common-delete
        :to="API.removeIrregularcodes().go"
        :callback="getIrregularcodesList"
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
  import airportSelect from './baseForm/SelectAirport'

  export default {
    data () {
      return {
        filters: {
          superior: '',
          irregularCode: '',
          airportCode: JSON.parse(sessionStorage.getItem('userStorage')).user.aiisAirports[0].airportCode,
          description: ''
        },
        irregularcodes: [],
        tableHeight: 495,
        listLoading: false,
        sels: [],
        // 登录用户订阅的机场
        adminAirportChoose: [],
        // 用于分组的用户订阅的机场
        airportsTypesChoose: [],
        // 父级的选项
        superiorChoose: [],
        airports: [],
        priorityValidate: null,
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
              { name: 'airportCode', value: '', label: '机场', type: 'selectExplain', filterable: true, choose: [], rules: null, placeholder: '', span: 12 },
              { name: 'superior', value: '000000', label: '父级', type: 'selectExplain', choose: [], rules: [ { max: 6, min: 1, message: '父级只能为少于六位的字符串' } ], placeholder: '', span: 12 }
            ]
          },
          {
            id: '2',
            item: [
              { name: 'irregularCode', value: '', label: '异常编码', type: 'text', rules: [ { max: 6, min: 1, required: true, message: '请输入一位到六位字符串的异常编码' } ], placeholder: '请输入异常编码', span: 12 },
              { name: 'priority', value: 0, label: '优先级', type: 'number', rules: null, placeholder: '', span: 12, min: 0, max: 99 }
            ]
          },
          {
            id: '3',
            item: [
              { name: 'flightEventCode', value: '', label: '关联事件代码', type: 'text', rules: [ { max: 30, min: 1, message: '关联事件代码只能为少于三十位的字符串' } ], placeholder: '', span: 12 },
              { name: 'disabled', value: 1, label: '状态', type: 'select', choose: [ { text: '禁用', value: 1 }, { text: '启用', value: 0 } ], rules: null, placeholder: '', span: 12 }
            ]
          },
          {
            id: '4',
            item: [
              { name: 'description', value: '', label: '描述', type: 'textarea', rules: [ { max: 100, min: 1, required: true, message: '请输入一位到一百位字符串的描述' } ], placeholder: '请输入描述', span: 12 },
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
      getIrregularcodesList () {
        // 用户已经订阅的机场
        let para = Object.assign({}, this.filters, this.$refs['page'].queryParam())
        this.listLoading = true
        API.getIrregularcodesListPage().go(para).then((data) => {
          if (data.ok) {
            this.$refs['page'].set('total', data.attr.data.pager.recordCount)
            this.irregularcodes = data.attr.data.list
            this.filters.superior = ''
            this.filters.irregularCode = ''
            this.getSuperiorChooseData()
            this.getPriorityValidate()
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
        this.fields[1].item[0].choose = this.airportsTypesChoose
        this.fields[1].item[1].choose = this.superiorChoose
        this.fields[2].item[1].rules = this.priorityValidate
        if (this.adminAirportChoose.length === 1) {
          this.fields[1].item[0].value = this.adminAirportChoose[0].value
        }
        let addFields = Util.deepCopy(this.fields)
        this.$refs['addForm'].show(addFields)
      },
      // 显示编辑界面
      handleEdit: function (index, row) {
        this.fields[1].item[0].choose = this.airportsTypesChoose
        this.fields[1].item[1].choose = this.superiorChoose
        this.fields[2].item[1].rules = this.priorityValidate
        if (this.adminAirportChoose.length === 1) {
          this.fields[1].item[0].value = this.adminAirportChoose[0].value
        }
        let editFields = Util.deepCopy(this.fields)
        for (let i = 0; i < editFields.length; i++) {
          for (let j = 0; j < editFields[i].item.length; j++) {
            editFields[i].item[j].value = row[editFields[i].item[j].name]
          }
        }
        this.$refs['editForm'].show(editFields)
      },
      // 格式化机场的的代码
      formatterAirport: function (row, column, cellValue) {
        for (var i = 0; i < this.adminAirportChoose.length; i++) {
          if (this.adminAirportChoose[i].value === cellValue) {
            return this.adminAirportChoose[i].text1
          }
        }
      },
      // 构造出父级选项下拉框的值
      getSuperiorChooseData: function () {
        this.superiorChoose = []
        for (var i = 0; i < this.$cache.array.irregularcodes.length; i++) {
          if (this.$cache.array.irregularcodes[i].superior === null) {
            var item = {}
            item['text'] = this.$cache.array.irregularcodes[i].irregularCode
            item['value'] = this.$cache.array.irregularcodes[i].irregularCode
            item['code'] = this.$cache.array.irregularcodes[i].description
            this.superiorChoose.push(item)
          }
        }
      },
      irregularCodeUpper: function (data) {
        this.filters.irregularCode = data.toUpperCase()
      },
      getPriorityValidate: function () {
        this.priorityValidate = (rule, value, callback) => {
          console.log('value', value)
          if (value < 0) {
            return callback(new Error('值不能为负数'))
          }
          if (!Number.isInteger(value)) {
            callback(new Error('请输入数字值'))
          }
          if (value > 99) {
            callback(new Error('值只能在0~99之间'))
          }
          callback()
        }
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
      this.getAirportList()
      this.getIrregularcodesList()
    }
  }

</script>

<style scoped>

</style>
