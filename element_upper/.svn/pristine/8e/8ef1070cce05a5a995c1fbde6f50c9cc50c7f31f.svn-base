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
          <el-select v-model="filters.ckCounterTemplate" filterable placeholder="柜台分配模式">
            <el-option
              v-for="item in ckCounterTemplateChoose"
              :key="item.value"
              :label="item.text"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-input v-model="filters.serviceTypeCode" placeholder="服务类型代码" @change="serviceTypeCodeUpper"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getCkcounterServiceTypesList">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleAdd">新增</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table v-bind:data="ckcounterservicetypes" highlight-current-row v-loading="listLoading" @selection-change="selsChange" :height="tableHeight" style="width: 100%;">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column type="index" width="60">
        <template scope="scope">
          {{pageSize * (pageNumber - 1) + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="serviceTypeCode" label="服务类型代码" width="150" sortable>
      </el-table-column>
      <el-table-column prop="ckCounterTemplate" label="值机柜台模板" min-width="150" :formatter="formatterCkCounterTemplate">
      </el-table-column>
      <el-table-column prop="description" label="描述" min-width="150" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="remark" label="备注" min-width="150" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="updateTime" label="最后更新时间" min-width="180" show-overflow-tooltip>
      </el-table-column>
      <el-table-column label="操作" width="180" fixed="right" >
        <template scope="scope">
          <el-button size="small" @click="handleEdit(scope.$index, scope.row)"><span><i class="el-icon-edit"></i> 编辑 </span></el-button>
          <el-button size="small" @click="handleDel(scope.$index, scope.row)"><span><i class="el-icon-delete"></i> 删除 </span></el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--分页-->
    <pagination :to="getCkcounterServiceTypesList" ref="page"></pagination>

    <!--新增界面-->
    <div class="ckcounterservicetypeTag">
      <common-add-or-update
        @getInputItem="serviceTypeCodeChange"
        type="add"
        :to="API.addCkcounterServiceTypes().go"
        :callback="getCkcounterServiceTypesList"
        :labelWidth="100"
        ref="addForm"></common-add-or-update>
    </div>
    <!--编辑界面-->
    <div class="ckcounterservicetypeTag">
      <common-add-or-update
          @getInputItem="serviceTypeCodeChange"
          title="编辑"
          type="update"
          :to="API.editCkcounterServiceTypes().go"
          :callback="getCkcounterServiceTypesList"
          :labelWidth="100"
          ref="editForm"></common-add-or-update>
    </div>
    <!--删除窗口-->
    <common-delete
        :to="API.removeCkcounterServiceTypes().go"
        :callback="getCkcounterServiceTypesList"
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

  export default {
    data () {
      return {
        filters: {
          ckCounterTemplate: '',
          serviceTypeCode: '',
          description: ''
        },
        ckcounterservicetypes: [],
        tableHeight: 495,
        listLoading: false,
        sels: [],
        // airportsChoose: [],
        serviceTypeCodeValidate: null,
        serviceTypeCodeTag: null,
        addUpdateTag: null,
        serviceTypeCodeCharacter: [],
        ckCounterTemplateChoose: [],
        // 新增编辑需要的字段
        fields: [
          { name: 'id', value: '', hidden: true },
          { name: 'ckCounterTemplate', value: '', label: '值机柜台模板', type: 'select', choose: [], rules: null, placeholder: '请选择值机柜台模板' },
          { name: 'serviceTypeCode', value: '', label: '服务类型代码', type: 'text', rules: null, placeholder: '请输入服务类型代码' },
          { name: 'description', value: '', label: '描述', type: 'textarea', rules: [ { max: 50, min: 1, required: true, message: '请输入一位到五十位字符串的描述' } ], placeholder: '请输入描述' },
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
      pagination: Pagination
    },
    methods: {
      // 获取用户列表
      getCkcounterServiceTypesList () {
        let para = Object.assign({}, this.filters, this.$refs['page'].queryParam())
        this.listLoading = true
        API.getCkcounterServiceTypesListPage().go(para).then((data) => {
          if (data.ok) {
            this.$refs['page'].set('total', data.attr.data.pager.recordCount)
            this.ckcounterservicetypes = data.attr.data.list
            this.filters.serviceTypeCode = ''
            this.filters.description = ''
            this.getUsedServiceTypeCode()
            this.getServiceTypeCodeValidate()
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
        this.fields[1].choose = this.ckCounterTemplateChoose
        this.fields[2].rules = [ { validator: this.serviceTypeCodeValidate } ]
        let addFields = Util.deepCopy(this.fields)
        this.$refs['addForm'].show(addFields)
      },
      // 显示编辑界面
      handleEdit: function (index, row) {
        this.addUpdateTag = 'edit'
        this.serviceTypeCodeTag = row.serviceTypeCode
        this.fields[1].choose = this.ckCounterTemplateChoose
        this.fields[2].rules = [ { validator: this.serviceTypeCodeValidate } ]
        let editFields = Util.deepCopy(this.fields)
        for (let i = 0; i < editFields.length; i++) {
          editFields[i].value = row[editFields[i].name]
        }
        this.$refs['editForm'].show(editFields)
      },
      serviceTypeCodeChange: function (data) {
        if (data.serviceTypeCode !== this.serviceTypeCodeTag) {
          var singleUpperCode = null
          singleUpperCode = data.serviceTypeCode.toUpperCase()
          if (this.addUpdateTag === 'add') {
            var serviceTypeCodeTagStrAdd = 'serviceTypeCode'
            this.$refs['addForm'].changeInput(serviceTypeCodeTagStrAdd, singleUpperCode)
          }
          if (this.addUpdateTag === 'edit') {
            var serviceTypeCodeStrEdit = 'serviceTypeCode'
            this.$refs['editForm'].changeInput(serviceTypeCodeStrEdit, singleUpperCode)
          }
        }
        this.serviceTypeCodeTag = data.serviceTypeCode
      },
      getServiceTypeCodeValidate: function () {
        this.serviceTypeCodeValidate = (rule, value, callback) => {
          if (!value) {
            return callback(new Error('服务类型代码不能为空！'))
          }
          if (value.length !== 1) {
            return callback(new Error('请输入一位字母！'))
          }
          if ((value.charCodeAt(0) < 65) || (value.charCodeAt(0) > 122)) {
            return callback(new Error('只允许输入字母！'))
          }
          callback()
       }
      },
      // 获取已经使用过的大写字母
      getUsedServiceTypeCode: function () {
        this.serviceTypeCodeCharacter = []
        for (var i = 0; i < this.ckcounterservicetypes.length; i++) {
          var item = {}
          item['serviceTypeCode'] = this.ckcounterservicetypes[i].serviceTypeCode
          item['description'] = this.ckcounterservicetypes[i].description
          this.serviceTypeCodeCharacter.push(item)
        }
      },
      // 获取分配模式的下拉框
      getckCounterTemplateChoose: function () {
      	this.ckCounterTemplateChoose = []
      	for (var i = 0; i < this.$cache.array.ckcounteropmodes.length; i++) {
      		var item = {}
      		item['text'] = this.$cache.array.ckcounteropmodes[i].description
      		item['value'] = this.$cache.array.ckcounteropmodes[i].opModeCode
      		this.ckCounterTemplateChoose.push(item)
      	}
      },
      // 格式化值机柜台模板
      formatterCkCounterTemplate: function (row, column, cellValue) {
      	var result = null
        for (var i = 0; i < this.$cache.array.ckcounteropmodes.length; i++) {
	        if (cellValue === this.$cache.array.ckcounteropmodes[i].opModeCode) {
	          result = this.$cache.array.ckcounteropmodes[i].description
	        }
	    }
	    return result
	   },
     serviceTypeCodeUpper: function (data) {
      this.filters.serviceTypeCode = data.toUpperCase()
     }
    },
    mounted () {
      this.getCkcounterServiceTypesList()
      this.getckCounterTemplateChoose()
    }
  }

</script>

<style>
   .ckcounterservicetypeTag .el-dialog--small {
    width: 40%!important
   }
</style>
