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
          <el-select v-model="filters.enumType" filterable placeholder="类型">
            <el-option
              v-for="item in enumTypeChoose"
              :key="item"
              :label="item"
              :value="item">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-input v-model="filters.enumNo" placeholder="枚举编码"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getEnumInfoList">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleAdd">新增</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table v-bind:data="enuminfos" highlight-current-row v-loading="listLoading" @selection-change="selsChange" :height="tableHeight" style="width: 100%;">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column type="index" width="60">
        <template scope="scope">
          {{pageSize * (pageNumber - 1) + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="enumType" label="类型" width="180" sortable>
      </el-table-column>
      <el-table-column prop="enumNo" label="枚举编码" width="150">
      </el-table-column>
      <el-table-column prop="displayValue" label="显示值" min-width="180">
      </el-table-column>
      <el-table-column prop="remark" label="备注" min-width="100" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="updateTime" label="更新时间" min-width="180" >
      </el-table-column>
      <el-table-column label="操作" width="180" fixed="right" >
        <template scope="scope">
          <el-button size="small" @click="handleEdit(scope.$index, scope.row)"><span><i class="el-icon-edit"></i> 编辑 </span></el-button>
          <el-button size="small" @click="handleDel(scope.$index, scope.row)"><span><i class="el-icon-delete"></i> 删除 </span></el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--分页-->
    <pagination :to="getEnumInfoList" ref="page"></pagination>

    <!--新增界面-->
    <div class="enuminfoTag">
      <common-add-or-update
        @getInputItem="EnumTypeChange"
        type="add"
        :to="API.addEnumInfo().go"
        :callback="getEnumInfoList"
        :labelWidth="100"
        ref="addForm"></common-add-or-update>
    </div>
    <!--编辑界面-->
    <div class="enuminfoTag">
       <common-add-or-update
        @getInputItem="EnumTypeChange"
        title="编辑"
        type="update"
        :to="API.editEnumInfo().go"
        :callback="getEnumInfoList"
        :labelWidth="100"
        ref="editForm"></common-add-or-update>
    </div>
    <!--删除窗口-->
    <common-delete
        :to="API.removeEnumInfo().go"
        :callback="getEnumInfoList"
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
          enumType: '',
          enumNo: '',
          displayValue: ''
        },
        enuminfos: [],
        tableHeight: 495,
        listLoading: false,
        sels: [],
        enumTypeTag: null,
        addUpdateTag: null,
        // 用于给类型的下拉框
        enumTypeChoose: [],
        // 新增编辑需要的字段
        fields: [
          { name: 'id', value: '', hidden: true },
          { name: 'enumType', value: '', label: '类型', type: 'text', rules: [ { max: 20, min: 1, required: true, message: '请输入一位到二十位字符串的类型' } ], placeholder: '请输入类型' },
          { name: 'enumNo', value: '', label: '枚举编码', type: 'text', rules: [ { max: 50, min: 1, required: true, message: '请输入一位到五十位字符串的枚举编码' } ], placeholder: '请输入枚举编码' },
          { name: 'displayValue', value: '', label: '显示值', type: 'text', rules: [ { max: 100, min: 1, required: true, message: '请输入一位到一百位字符串的显示值' } ], placeholder: '请输入显示值' },
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
      getEnumInfoList () {
        let para = Object.assign({}, this.filters, this.$refs['page'].queryParam())
        this.listLoading = true
        API.getEnumInfoListPage().go(para).then((data) => {
          if (data.ok) {
            this.$refs['page'].set('total', data.attr.data.pager.recordCount)
            this.enuminfos = data.attr.data.list
            // this.filters.enumType = ''
            this.filters.enumNo = ''
            this.getEnumTypeChoose()
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
        this.enumTypeTag = null
        let addFields = Util.deepCopy(this.fields)
        this.$refs['addForm'].show(addFields)
      },
      // 显示编辑界面
      handleEdit: function (index, row) {
        // 要排除掉编辑时的那个区域代码
        this.addUpdateTag = 'edit'
        this.enumTypeTag = row.enumType
        let editFields = Util.deepCopy(this.fields)
        for (let i = 0; i < editFields.length; i++) {
          editFields[i].value = row[editFields[i].name]
        }
        this.$refs['editForm'].show(editFields)
      },
      EnumTypeChange: function (data) {
        if (data.enumType !== this.enumTypeTag) {
          var singleUpperCode = null
          singleUpperCode = data.enumType.toUpperCase()
          if (this.addUpdateTag === 'add') {
            var enumTypeTagStrAdd = 'enumType'
            this.$refs['addForm'].changeInput(enumTypeTagStrAdd, singleUpperCode)
          }
          if (this.addUpdateTag === 'edit') {
            var enumTypeStrEdit = 'enumType'
            this.$refs['editForm'].changeInput(enumTypeStrEdit, singleUpperCode)
          }
        }
        this.enumTypeTag = data.enumType
      },
      // 获取类型的下拉框的数据
      getEnumTypeChoose: function () {
        // 下拉框数据的编写
        this.enumTypeChoose = []
        let para = {
          enumType: '',
          enumNo: '',
          displayValue: '',
          pageNumber: 1,
          pageSize: 10000000
        }
        API.getEnumInfoListPage().go(para).then((data) => {
          if (data.ok) {
            for (var i = 0; i < data.attr.data.list.length; i++) {
              if (this.enumTypeChoose.indexOf(data.attr.data.list[i].enumType) === -1) {
                this.enumTypeChoose.push(data.attr.data.list[i].enumType)
              }
            }
          }
        })
      }
    },
    mounted () {
      this.getEnumInfoList()
    }
  }

</script>

<style>
   .enuminfoTag .el-dialog--small {
    width: 40%!important
   }
</style>
