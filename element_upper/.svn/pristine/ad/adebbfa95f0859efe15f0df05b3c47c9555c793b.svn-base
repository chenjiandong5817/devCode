<template>
  <section>
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-select v-model="filters.airlines" filterable placeholder="请选择承运人代码">
            <el-option
              v-for="item in airlinesIATACodeChoose"
              :key="item.value"
              :label="item.text"
              :value="item.value">
              <span style="float: left">{{ item.text }}</span>
              <span style="float: right">{{ item.code }}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-input v-model="filters.registration" placeholder="机号" @change="registrationUpper"></el-input>
        </el-form-item>
        <el-form-item>
          <el-input v-model="filters.aircraftType" placeholder="机型IATA代码" @change="aircraftTypeUpper"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getAircraftregistrationsList">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleAdd">新增</el-button>
        </el-form-item>
      </el-form>
    </el-col>
    <el-table v-bind:data="aircraftregistrations" highlight-current-row v-loading="listLoading" @selection-change="selsChange" :height="tableHeight" style="width: 100%;">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column type="index" width="70">
        <template scope="scope">
          {{pageSize * (pageNumber - 1) + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="registration" label="机号" width="115" sortable>
      </el-table-column>
      <el-table-column prop="aircraftType" label="机型IATA代码" width="125">
      </el-table-column>
      <el-table-column prop="airlines" label="承运人代码" width="160">
      </el-table-column>
      <el-table-column prop="availableSeats" label="可供座位" width="105">
      </el-table-column>
      <el-table-column prop="maximumSeats" label="最大座位" width="105">
      </el-table-column>
      <el-table-column prop="availablePayload" label="可供业载" width="105">
      </el-table-column>
      <el-table-column prop="maximumPayload" label="最大业载" width="105">
      </el-table-column>
      <el-table-column prop="isLoadedPlate" label="是否装板" width="105" :formatter="baseUtil.formatterIsNo">
      </el-table-column>
      <el-table-column prop="isOxygenChamber" label="是否有有氧舱" width="120" :formatter="baseUtil.formatterIsNo">
      </el-table-column>
      <el-table-column prop="maintenanceSeat" label="机务座位" width="105">
      </el-table-column>
      <el-table-column prop="crewSeat" label="机组座位" width="105">
      </el-table-column>
      <el-table-column prop="safetyOfficerSeat" label="安全员座位" width="115">
      </el-table-column>
      <el-table-column prop="seatLayout" label="座位布局" width="105">
      </el-table-column>
      <el-table-column prop="doorSize" label="舱门尺寸" width="105">
      </el-table-column>
      <el-table-column prop="emergencyExit" label="紧急出口" width="105">
      </el-table-column>
      <el-table-column prop="ctn" label="离港CTN" width="102">
      </el-table-column>
      <el-table-column prop="mtow" label="最大起飞重量" width="120">
      </el-table-column>
      <el-table-column prop="airlinesBranch" label="分公司代码" width="115">
      </el-table-column>
      <el-table-column prop="parkTime" label="标准停场时间" width="120">
      </el-table-column>
      <el-table-column prop="aircraftTypeDetail" label="细分机型" width="105">
      </el-table-column>
      <el-table-column prop="remark" label="备注" width="130" show-overflow-tooltip>
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
    <pagination :to="getAircraftregistrationsList" ref="page"></pagination>
    <common-add-or-update
        @getInputItem="getAircraftTypeRegistrationChange"
        type="add"
        :to="API.addAircraftregistrations().go"
        :callback="getAircraftregistrationsList"
        :labelWidth="140"
        ref="addForm"></common-add-or-update>
    <common-add-or-update
        @getInputItem="getAircraftTypeRegistrationChange"
        title="编辑"
        type="update"
        :to="API.editAircraftregistrations().go"
        :callback="getAircraftregistrationsList"
        :labelWidth="140"
        ref="editForm"></common-add-or-update>
    <common-delete
        :to="API.removeAircraftregistrations().go"
        :callback="getAircraftregistrationsList"
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
          registration: '',
          aircraftType: '',
          airlines: ''
        },
        aircraftregistrations: [],
        tableHeight: 495,
        listLoading: false,
        sels: [],
        airports: [],
        amountValidate: null,
        airlinesIATACodeChoose: [],
        // 标记是添加还是编辑
        addUpdateTag: null,
        // 机型
        aircraftTypeTag: null,
        // 机号
        registrationTag: null,
        seatPayloadValidate: null,
        timeValidate: null,
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
             { name: 'airlines', value: '', label: '承运人代码', type: 'selectExplainNoType', choose: [], rules: [ { max: 2, min: 1, message: '承运人代码只能为少于两位的字符串' } ], filterable: true, placeholder: '', span: 12 },
             { name: 'aircraftType', value: '', label: '机型IATA代码', type: 'text', rules: [ { max: 10, min: 1, message: '机型IATA代码只能为少于十位的字符串' } ], placeholder: '', span: 12 }
            ]
          },
          {
            id: '2',
            item: [
             { name: 'availableSeats', value: 0, label: '可供座位', type: 'number', rules: null, placeholder: '', span: 12, min: 0, max: 99999999 },
             { name: 'maximumSeats', value: 0, label: '最大座位', type: 'number', rules: null, placeholder: '', span: 12, min: 0, max: 99999999 }
            ]
          },
          {
            id: '3',
            item: [
              { name: 'availablePayload', value: 0, label: '可供业载', type: 'number', rules: null, placeholder: '', span: 12, min: 0, max: 99999999 },
              { name: 'maximumPayload', value: 0, label: '最大业载', type: 'number', rules: null, placeholder: '', span: 12, min: 0, max: 99999999 }
            ]
          },
          {
            id: '4',
            item: [
             { name: 'isLoadedPlate', value: '', label: '是否装板', type: 'select', choose: [ { text: '是', value: '1' }, { text: '否', value: '0' } ], rules: null, placeholder: '', span: 12 },
             { name: 'isOxygenChamber', value: '', label: '是否有有氧舱', type: 'select', choose: [ { text: '是', value: '1' }, { text: '否', value: '0' } ], rules: null, placeholder: '', span: 12 }
            ]
          },
          {
            id: '5',
            item: [
              { name: 'maintenanceSeat', value: '', label: '机务座位', type: 'text', rules: [ { max: 50, min: 1, message: '机务座位只能为少于五十位的字符串' } ], placeholder: '', span: 12 },
              { name: 'crewSeat', value: '', label: '机组座位', type: 'text', rules: [ { max: 50, min: 1, message: '机组座位只能为少于五十位的字符串' } ], placeholder: '', span: 12 }
            ]
          },
          {
            id: '6',
            item: [
               { name: 'safetyOfficerSeat', value: '', label: '安全员座位', type: 'text', rules: [ { max: 50, min: 1, message: '安全员座位只能为少于五十位的字符串' } ], placeholder: '', span: 12 },
               { name: 'seatLayout', value: '', label: '座位布局', type: 'text', rules: [ { max: 50, min: 1, message: '作为布局只能为少于五十位的字符串' } ], placeholder: '', span: 12 }
            ]
          },
          {
            id: '7',
            item: [
              { name: 'doorSize', value: 0, label: '舱门尺寸', type: 'number', rules: null, placeholder: '', span: 12, min: 0 },
              { name: 'emergencyExit', value: '', label: '紧急出口', type: 'text', rules: [ { max: 50, min: 1, message: '紧急出口只能为少于五十位的字符串' } ], placeholder: '', span: 12 }
            ]
          },
          {
            id: '8',
              item: [
              { name: 'ctn', value: '', label: '离港CTN', type: 'text', rules: [ { max: 10, min: 1, message: '离港CTN只能为少于十位的字符串' } ], placeholder: '', span: 12 },
              { name: 'mtow', value: 0, label: '最大起飞重量', type: 'number', rules: null, placeholder: '', span: 12, min: 0, max: 99999999 }
            ]
          },
          {
            id: '9',
            item: [
              { name: 'airlinesBranch', value: '', label: '分公司代码', type: 'text', rules: [ { max: 5, min: 1, message: '分公司代码只能为少于五位的字符串' } ], placeholder: '', span: 12 },
              { name: 'parkTime', value: 0, label: '标准停场时间(分钟)', type: 'number', rules: null, placeholder: '', span: 12, min: 0, max: 999 }
            ]
          },
          {
            id: '10',
            item: [
             { name: 'aircraftTypeDetail', value: '', label: '细分机型', type: 'text', rules: [ { max: 20, min: 1, message: '编码只能为少于二十位的字符串' } ], placeholder: '', span: 12 },
             { name: 'registration', value: '', label: '机号', type: 'text', rules: [ { max: 10, min: 1, message: '机号只能为少于十位的字符串' } ], placeholder: '', span: 12 }
            ]
          },
          {
            id: '11',
            item: [
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
      pagination: Pagination
    },
    methods: {
      getAircraftregistrationsList () {
        let para = Object.assign({}, this.filters, this.$refs['page'].queryParam())
        this.listLoading = true
        API.getAircraftregistrationsListPage().go(para).then((data) => {
          if (data.ok) {
            this.$refs['page'].set('total', data.attr.data.pager.recordCount)
            this.aircraftregistrations = data.attr.data.list
            this.filters.registration = ''
            this.filters.aircraftType = ''
            this.filters.airlines = ''
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
      handleDel: function (index, row) {
        this.$refs['delConfirm'].del(row.id)
      },
      handleAdd: function () {
        this.addUpdateTag = 'add'
        this.aircraftTypeTag = null
        this.registrationTag = null
        this.fields[1].item[0].choose = this.airlinesIATACodeChoose
        this.fields[2].item[0].rules = this.seatPayloadValidate
        this.fields[2].item[1].rules = this.seatPayloadValidate
        this.fields[3].item[0].rules = this.seatPayloadValidate
        this.fields[3].item[1].rules = this.seatPayloadValidate
        this.fields[7].item[0].rules = this.seatPayloadValidate
        this.fields[8].item[1].rules = this.seatPayloadValidate
        this.fields[9].item[1].rules = this.timeValidate
        let addFields = Util.deepCopy(this.fields)
        this.$refs['addForm'].show(addFields)
      },
      handleEdit: function (index, row) {
        this.addUpdateTag = 'update'
        this.aircraftTypeTag = row.aircraftType
        this.registrationTag = row.registration
        this.fields[1].item[0].choose = this.airlinesIATACodeChoose
        this.fields[2].item[0].rules = this.seatPayloadValidate
        this.fields[2].item[1].rules = this.seatPayloadValidate
        this.fields[3].item[0].rules = this.seatPayloadValidate
        this.fields[3].item[1].rules = this.seatPayloadValidate
        this.fields[7].item[0].rules = this.seatPayloadValidate
        this.fields[8].item[1].rules = this.seatPayloadValidate
        this.fields[9].item[1].rules = this.timeValidate
        let editFields = Util.deepCopy(this.fields)
        for (let i = 0; i < editFields.length; i++) {
          for (let j = 0; j < editFields[i].item.length; j++) {
            editFields[i].item[j].value = row[editFields[i].item[j].name]
          }
        }
        this.$refs['editForm'].show(editFields)
      },
      getValidate: function () {
        this.seatPayloadValidate = (rule, value, callback) => {
          if (value < 0) {
            return callback(new Error('请保证输入的值大于0'))
          }
          if (!Number.isInteger(value)) {
            callback(new Error('请输入数字值'))
          }
          if (value > 99999999) {
            callback(new Error('值只能在0~99999999之间'))
          }
          callback()
       }
       this.timeValidate = (rule, value, callback) => {
          if (value < 0) {
            return callback(new Error('请保证输入的值大于0'))
          }
          if (!Number.isInteger(value)) {
            callback(new Error('请输入数字值'))
          }
          if (value > 999) {
            callback(new Error('值只能在0~999之间'))
          }
          callback()
       }
      },
      getAirlinesIATACodeChoose: function () {
        for (var i = 0; i < this.$cache.array.airlines.length; i++) {
          var item = {}
          item['text'] = this.$cache.array.airlines[i].iatacode
          item['value'] = this.$cache.array.airlines[i].iatacode
          item['code'] = this.$cache.array.airlines[i].cnname
          this.airlinesIATACodeChoose.push(item)
        }
      },
      getAircraftTypeRegistrationChange: function (data) {
        if (data.aircraftType !== this.aircraftTypeTag) {
          var upperAircraftType = null
          upperAircraftType = data.aircraftType.toUpperCase()
          if (this.addUpdateTag === 'add') {
            var aircraftTypeStrAdd = 'aircraftType'
            this.$refs['addForm'].changeInput(aircraftTypeStrAdd, upperAircraftType)
          }
          if (this.addUpdateTag === 'update') {
            var aircraftTypeStrUpdate = 'aircraftType'
            this.$refs['editForm'].changeInput(aircraftTypeStrUpdate, upperAircraftType)
          }
        }
        if (data.registration !== this.registrationTag) {
          var upperRegistration = null
          upperRegistration = data.registration.toUpperCase()
          if (this.addUpdateTag === 'add') {
            var registrationStrAdd = 'registration'
            this.$refs['addForm'].changeInput(registrationStrAdd, upperRegistration)
          }
          if (this.addUpdateTag === 'update') {
            var registrationStrUpdate = 'registration'
            this.$refs['editForm'].changeInput(registrationStrUpdate, upperRegistration)
          }
        }
        this.aircraftTypeTag = data.aircraftType
        this.registrationTag = data.registration
      },
      registrationUpper: function (data) {
        this.filters.registration = data.toUpperCase()
      },
      aircraftTypeUpper: function (data) {
        this.filters.aircraftType = data.toUpperCase()
      }
    },
    mounted () {
      this.getAircraftregistrationsList()
      this.getValidate()
      this.getAirlinesIATACodeChoose()
    }
  }

</script>

<style scoped>

</style>
