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
        <el-select v-model="filters.isBridge" placeholder="请选择是否桥位">
          <el-option
            v-for="item in isBridgeOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
        </el-form-item>
        <el-form-item>
          <el-input v-model="filters.standCode" placeholder="编码" ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getStandList">查询</el-button>
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
      <el-table-column prop="standCode" label="编码" width="90" sortable>
      </el-table-column>
      <el-table-column prop="airportCode" label="所属机场" width="150" :formatter="formatterAirport" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="standLength" label="机位长" width="85">
      </el-table-column>
      <el-table-column prop="standWidth" label="机位宽" width="85">
      </el-table-column>
      <el-table-column prop="leftStand" label="左机位" width="85">
      </el-table-column>
      <el-table-column prop="rightStand" label="右机位" width="85">
      </el-table-column>
      <el-table-column prop="latitude" label="纬度" width="75">
      </el-table-column>
      <el-table-column prop="longitude" label="精度" width="75">
      </el-table-column>
      <el-table-column prop="leftSpace" label="左间距" width="85">
      </el-table-column>
      <el-table-column prop="rightStand" label="右间距" width="85">
      </el-table-column>
      <el-table-column prop="arpon" label="机坪区域" width="100">
      </el-table-column>
      <el-table-column prop="natureState" label="可停靠航班区域" width="145" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="nature" label="区域属性" width="100" :formatter="baseUtil.formatterNature">
      </el-table-column>
      <el-table-column prop="classification" label="机位分类" width="100">
      </el-table-column>
      <el-table-column prop="standCategory" label="机位等级" width="100">
      </el-table-column>
      <el-table-column prop="fuelPipe" label="是否加油管线设施" width="150" :formatter="baseUtil.formatterIsNo">
      </el-table-column>
      <el-table-column prop="isBridge" label="是否桥位" width="100" :formatter="baseUtil.formatterIsNo">
      </el-table-column>
      <el-table-column prop="status" label="状态" width="75" :formatter="baseUtil.formatterStatus">
      </el-table-column>
      <el-table-column prop="description" label="描述" width="130" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="remark" label="备注" width="130" show-overflow-tooltip>
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
    <pagination :to="getStandList" ref="page"></pagination>

    <!--新增界面-->
    <common-add-or-update
        @getSelectItem="getStandLeftRightChoose"
        type="add"
        :to="API.addStand().go"
        :callback="getStandList"
        :labelWidth="140"
        ref="addForm"></common-add-or-update>
    <!--编辑界面-->
    <common-add-or-update
        @getSelectItem="getStandLeftRightChoose"
        title="编辑"
        type="update"
        :to="API.editStand().go"
        :callback="getStandList"
        :labelWidth="140"
        ref="editForm"></common-add-or-update>
    <!--删除窗口-->
    <common-delete
        :to="API.removeStand().go"
        :callback="getStandList"
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
          standCode: '',
          isBridge: null,
          airportCode: JSON.parse(sessionStorage.getItem('userStorage')).user.aiisAirports[0].airportCode
        },
        isBridgeOptions: [
          { value: null, label: '全部' },
          { value: '1', label: '是' },
          { value: '0', label: '否' }
        ],
        stands: [],
        airports: [],
        tableHeight: 495,
        listLoading: false,
        sels: [],
        // 用于给机位等级这个字段下拉框数据
        standCategoryChoose: [],
        // 用于给左机位与右机位做选择
        standLeftRightChoose: [],
        // 用于标记是添加还是编辑
        addUpdateTag: null,
        // 登录用户订阅的机场
        adminAirportChoose: [],
        // 用于分组的用户订阅的机场
        airportsTypesChoose: [],
        // 用于标记左机位的下拉框是否被选中
        standLeftTag: null,
        // 用于标记机场下拉框的值是否变化
        airportCodeTag: null,
        standLeftResult: [],
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
              { name: 'airportCode', value: '', label: '所属机场', type: 'selectExplain', choose: [], filterable: true, rules: [ { max: 4, min: 1 } ], placeholder: '', span: 12 },
              { name: 'standCode', value: '', label: '编码', type: 'text', rules: [ { max: 6, min: 1, required: true, message: '请输入一位到六位字符串的编码' } ], placeholder: '编码', span: 12 }
            ]
           },
           {
            id: '2',
            item: [
              { name: 'standLength', value: '', label: '机位长', type: 'text', rules: [ {max: 20, min: 1, message: '机位长只能为少于二十位的字符串'} ], placeholder: '', span: 12 },
              { name: 'standWidth', value: '', label: '机位宽', type: 'text', rules: [ {max: 20, min: 1, message: '机位宽只能为少于二十位的字符串'} ], placeholder: '', span: 12 }
            ]
           },
           {
            id: '3',
            item: [
              { name: 'leftStand', value: '', label: '左机位', filterable: true, type: 'select', choose: [], rules: [ {max: 3, min: 1, message: '左机位只能为少于三位的字符串'} ], placeholder: '', span: 12 },
              { name: 'rightStand', value: '', label: '右机位', filterable: true, type: 'select', choose: [], rules: [ {max: 3, min: 1, message: '右机位只能为少于三位的字符串'} ], placeholder: '', span: 12 }
            ]
           },
           {
            id: '4',
            item: [
              { name: 'latitude', value: '', label: '纬度', type: 'text', rules: [ {max: 20, min: 1, message: '纬度只能为少于二十位的字符串'} ], placeholder: '', span: 12 },
              { name: 'longitude', value: '', label: '精度', type: 'text', rules: [ {max: 20, min: 1, message: '精度只能为少于二十位的字符串'} ], placeholder: '', span: 12 }
            ]
           },
           {
            id: '5',
            item: [
               { name: 'leftSpace', value: '', label: '左间距', type: 'text', rules: [ {max: 20, min: 1, message: '左间距只能为少于二十位的字符串'} ], placeholder: '', span: 12 },
               { name: 'rightSpace', value: '', label: '右间距', type: 'text', rules: [ {max: 20, min: 1, message: '右间距只能为少于二十位的字符串'} ], placeholder: '', span: 12 }
            ]
           },
           {
            id: '6',
            item: [
              { name: 'natureState', value: '', label: '可停靠航班区域', type: 'text', rules: [ {max: 500, min: 1, message: '可停靠航班区域只能为少于五百位的字符串'} ], placeholder: '', span: 12 },
              { name: 'arpon', value: '', label: '机坪区域', type: 'text', rules: [ {max: 4, min: 1, message: '机坪区域只能为少于四位的字符串'} ], placeholder: '', span: 12 }
            ]
           },
           {
            id: '7',
            item: [
               { name: 'nature', value: '', label: '区域属性', type: 'select', choose: [], rules: [], placeholder: '', span: 12 },
               { name: 'classification', value: '', label: '机位分类', type: 'text', rules: [ { max: 20, min: 1, message: '机位分类只能为少于二十位的字符串' } ], placeholder: '', span: 12 }
            ]
           },
           {
            id: '8',
            item: [
              { name: 'fuelPipe', value: '', label: '是否加油管线设施', type: 'select', choose: [ { text: '是', value: '1' }, { text: '否', value: '0' } ], rules: null, placeholder: '', span: 12 },
              { name: 'standCategory', value: '', label: '机位等级', type: 'select', choose: [], rules: [ { max: 1, min: 1 } ], placeholder: '', span: 12 }
            ]
           },
           {
            id: '9',
            item: [
              { name: 'isBridge', value: '', label: '是否桥位', type: 'select', choose: [ { text: '是', value: '1' }, { text: '否', value: '0' } ], rules: [ { required: true, message: '请选择是否桥位' } ], placeholder: '区域', span: 12 },
              { name: 'status', value: '', label: '状态', type: 'select', choose: [], rules: [], placeholder: '状态', span: 12 }
            ]
           },
           {
            id: '10',
            item: [
              { name: 'description', value: '', label: '描述', type: 'textarea', rules: [ {max: 50, min: 1, message: '描述只能为少于五十位的字符串'} ], placeholder: '', span: 12 },
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
      getStandList () {
        let para = Object.assign({}, this.filters, this.$refs['page'].queryParam())
        this.listLoading = true
        API.getStandListPage().go(para).then((data) => {
          if (data.ok) {
            this.$refs['page'].set('total', data.attr.data.pager.recordCount)
            this.stands = data.attr.data.list
            this.filters.standCode = ''
            this.filters.isBridge = ''
            this.getStandCategoriesList()
            this.getStandsChoose()
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
        this.standLeftTag = null
        this.airportCodeTag = null
        this.fields[1].item[0].choose = this.airportsTypesChoose
        if (this.adminAirportChoose.length === 1) {
          this.fields[1].item[0].value = this.adminAirportChoose[0].value
        }
        this.fields[8].item[1].choose = this.standCategoryChoose
        this.fields[9].item[1].choose = this.flightStatusChoose
        this.fields[7].item[0].choose = this.flightNatureChoose
        let addFields = Util.deepCopy(this.fields)
        this.$refs['addForm'].show(addFields)
      },
      // 显示编辑界面
      handleEdit: function (index, row) {
        this.addUpdateTag = 'update'
        this.airportCodeTag = row.airportCode
        this.standLeftTag = row.leftStand
        // 机场下拉框的值
        this.fields[1].item[0].choose = this.airportsTypesChoose
        if (this.adminAirportChoose.length === 1) {
          this.fields[1].item[0].value = this.adminAirportChoose[0].value
        }
        // 状态下拉框的值
        this.fields[9].item[1].choose = this.flightStatusChoose
        // 区域下拉框的值
        this.fields[7].item[0].choose = this.flightNatureChoose
        // 机位等级下拉框的值
        this.fields[8].item[1].choose = this.standCategoryChoose
        // 通过机场下拉框获取左机位下拉框的值
        var standLeftEdit = []
        for (var b = 0; b < this.standLeftRightChoose.length; b++) {
          if (this.standLeftRightChoose[b].airportCode === row.airportCode) {
            standLeftEdit.push(this.standLeftRightChoose[b])
          }
        }
        this.fields[3].item[0].choose = standLeftEdit
        console.log('this.fields[3].item[0].choose', this.fields[3].item[0].choose)
        console.log('row', row)
        // console.log('standLeftEdit', standLeftEdit)
        // 当选择完这边的机位的时候，右边要清空，然后不能出现重复的值
        // 如果左机位没有值，那么右机位也没有下拉框的值
        if (row.leftStand === null) {
          this.fields[3].item[1].choose = []
        }
        if (row.leftStand) {
          var standRightEdit = []
          for (var a = 0; a < standLeftEdit.length; a++) {
            if (standLeftEdit[a].value !== row.LeftStand) {
              standRightEdit.push(standLeftEdit[a])
            }
          }
          this.fields[3].item[1].choose = standRightEdit
        }
        // console.log('standRightEdit', standRightEdit)
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
      getStandCategoriesList: function () {
        this.standCategoryChoose = []
        for (var i = 0; i < this.$cache.array.aircraftcategorys.length; i++) {
          var item = {}
          item['text'] = this.$cache.array.aircraftcategorys[i].categoryCode
          item['value'] = this.$cache.array.aircraftcategorys[i].categoryCode
          this.standCategoryChoose.push(item)
        }
      },
      // 重构左机位右机位下拉框的数据
      getStandsChoose: function () {
        this.standLeftRightChoose = []
        for (var i = 0; i < this.$cache.array.stand.length; i++) {
          var item = {}
          item['text'] = this.$cache.array.stand[i].standCode
          item['value'] = this.$cache.array.stand[i].standCode
          item['airportCode'] = this.$cache.array.stand[i].airportCode
          this.standLeftRightChoose.push(item)
        }
        // console.log('this.standLeftRightChoose', this.standLeftRightChoose)
      },
      // 左机位与右机位的下拉框相互关联
      getStandLeftRightChoose: function (data) {
        // 这边用于根据机场来进行操作，啥机场对应啥机位
        var leftStand = null
        if (this.airportCodeTag !== data.airportCode) {
          this.standLeftResult = []
          for (var j = 0; j < this.standLeftRightChoose.length; j++) {
            if (this.standLeftRightChoose[j].airportCode === data.airportCode) {
              this.standLeftResult.push(this.standLeftRightChoose[j])
            }
          }
          if (this.addUpdateTag === 'add') {
            var leftStandStrAdd = 'leftStand'
            this.$refs['addForm'].changeSelectChoose(3, 0, this.standLeftResult, leftStandStrAdd, leftStand)
          }
          if (this.addUpdateTag === 'update') {
            var leftStandStrUpdate = 'leftStand'
            this.$refs['editForm'].changeSelectChoose(3, 0, this.standLeftResult, leftStandStrUpdate, leftStand)
          }
        }
        this.airportCodeTag = data.airportCode
        // 这边用于根据左机位来进行操作，左机位与右机位不能相同
        var rightStand = null
        if (this.standLeftTag !== data.leftStand) {
          var result = []
          for (var i = 0; i < this.standLeftResult.length; i++) {
            if (this.standLeftResult[i].value !== data.leftStand) {
              result.push(this.standLeftResult[i])
            }
          }
          if (this.addUpdateTag === 'add') {
            var rightStandStrAdd = 'rightStand'
            this.$refs['addForm'].changeSelectChoose(3, 1, result, rightStandStrAdd, rightStand)
          }
          if (this.addUpdateTag === 'update') {
            var rightStandStrUpdate = 'rightStand'
            this.$refs['editForm'].changeSelectChoose(3, 1, result, rightStandStrUpdate, rightStand)
          }
        }
        this.standLeftTag = data.leftStand
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
      this.getStandList()
      this.getFlightStatusChoose()
      this.getFlightNatureChoose()
    }
  }

</script>

<style scoped>

</style>
