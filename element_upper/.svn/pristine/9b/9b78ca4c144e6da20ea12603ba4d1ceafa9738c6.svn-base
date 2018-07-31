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
          <el-input v-model="filters.sourceCode" placeholder="区域代码" @change="sourceCodeUpper"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getSchedulesourcesList">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleAdd">新增</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table v-bind:data="schedulesources" highlight-current-row v-loading="listLoading" @selection-change="selsChange" :height="tableHeight" style="width: 100%;">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column type="index" width="60">
        <template scope="scope">
          {{pageSize * (pageNumber - 1) + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="sourceCode" label="区域代码" width="150" sortable>
      </el-table-column>
      <el-table-column prop="description" label="描述" min-width="200" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="remark" label="备注" min-width="200" show-overflow-tooltip>
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
    <pagination :to="getSchedulesourcesList" ref="page"></pagination>

    <!--新增界面-->
    <div class="schedulesourceTag">
        <common-add-or-update
          @getInputItem="sourceCodeChange"
          type="add"
          :to="API.addSchedulesources().go"
          :callback="getSchedulesourcesList"
          :labelWidth="100"
          ref="addForm"></common-add-or-update>
    </div>
    <!--编辑界面-->
    <div class="schedulesourceTag">
        <common-add-or-update
            @getInputItem="sourceCodeChange"
            title="编辑"
            type="update"
            :to="API.editSchedulesources().go"
            :callback="getSchedulesourcesList"
            :labelWidth="100"
            ref="editForm"></common-add-or-update>
    </div>
    <!--删除窗口-->
    <common-delete
        :to="API.removeSchedulesources().go"
        :callback="getSchedulesourcesList"
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
          sourceCode: ''
        },
        schedulesources: [],
        tableHeight: 495,
        listLoading: false,
        sels: [],
        // airportsChoose: [],
        sourceCodeValidate: null,
        sourceCodeTag: null,
        addUpdateTag: null,
        sourceCodeCharacter: [],
        // 新增编辑需要的字段
        fields: [
          { name: 'id', value: '', hidden: true },
          { name: 'sourceCode', value: '', label: '区域代码', type: 'text', rules: [ { max: 10, min: 1, required: true, message: '请输入一位到十位字符串的区域代码' } ], placeholder: '请输入区域代码' },
          { name: 'description', value: '', label: '描述', type: 'textarea', rules: [ { max: 50, min: 1, message: '请输入一位到五十位字符串的描述' } ], placeholder: '请输入描述' },
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
      getSchedulesourcesList () {
        let para = Object.assign({}, this.filters, this.$refs['page'].queryParam())
        this.listLoading = true
        API.getSchedulesourcesListPage().go(para).then((data) => {
          if (data.ok) {
            this.$refs['page'].set('total', data.attr.data.pager.recordCount)
            this.schedulesources = data.attr.data.list
            this.filters.sourceCode = ''
            this.getUsedSourceCodes()
            this.getSourceCodeValidate()
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
        this.fields[1].rules = [ { validator: this.sourceCodeValidate } ]
        let addFields = Util.deepCopy(this.fields)
        this.$refs['addForm'].show(addFields)
      },
      // 显示编辑界面
      handleEdit: function (index, row) {
        // 要排除掉编辑时的那个区域代码
        var result = []
        for (var i = 0; i < this.sourceCodeCharacter.length; i++) {
          if (this.sourceCodeCharacter[i].sourceCode !== row.sourceCode) {
            result.push(this.sourceCodeCharacter[i])
          }
        }
        this.sourceCodeCharacter = result
      	this.addUpdateTag = 'edit'
        this.sourceCodeTag = row.sourceCode
      	this.fields[1].rules = [ { validator: this.sourceCodeValidate } ]
        let editFields = Util.deepCopy(this.fields)
        for (let i = 0; i < editFields.length; i++) {
          editFields[i].value = row[editFields[i].name]
        }
        this.$refs['editForm'].show(editFields)
      },
      sourceCodeChange: function (data) {
        if (data.sourceCode !== this.sourceCodeTag) {
          var singleUpperCode = null
          singleUpperCode = data.sourceCode.toUpperCase()
          if (this.addUpdateTag === 'add') {
            var sourceCodeTagStrAdd = 'sourceCode'
            this.$refs['addForm'].changeInput(sourceCodeTagStrAdd, singleUpperCode)
          }
          if (this.addUpdateTag === 'edit') {
            var sourceCodeStrEdit = 'sourceCode'
            this.$refs['editForm'].changeInput(sourceCodeStrEdit, singleUpperCode)
          }
        }
        this.rankCodeTag = data.rankCode
      },
      getSourceCodeValidate: function () {
        this.sourceCodeValidate = (rule, value, callback) => {
          if (!value) {
            return callback(new Error('区域代码不能为空！'))
          }
          if (value.length !== 1) {
            return callback(new Error('请输入一位字母！'))
          }
          if ((value.charCodeAt(0) < 65) || (value.charCodeAt(0) > 122)) {
            return callback(new Error('只允许输入字母！'))
          }
          for (var i = 0; i < this.sourceCodeCharacter.length; i++) {
          	if (value.toUpperCase() === this.sourceCodeCharacter[i].sourceCode) {
          		return callback(new Error('级别编码已存在！'))
          	}
          }
          callback()
       }
      },
      // 获取已经使用过的大写字母
      getUsedSourceCodes: function () {
      	this.sourceCodeCharacter = []
      	for (var i = 0; i < this.schedulesources.length; i++) {
      		var item = {}
      		item['sourceCode'] = this.schedulesources[i].sourceCode
      		item['description'] = this.schedulesources[i].description
      		this.sourceCodeCharacter.push(item)
      	}
      },
      sourceCodeUpper: function (data) {
        this.filters.sourceCode = data.toUpperCase()
      }
    },
    mounted () {
      this.getSchedulesourcesList()
    }
  }

</script>

<style>
  .schedulesourceTag .el-dialog--small {
    width: 40%!important
  }
</style>
