<!-- /*
 * @Author: chenyang
 * @Date: 2017-06-26 15:42:17
 * @Last Modified by: chenyang
 * @Last Modified time: 2017-06-27 16:16:09
 * @Description: 机型座位等级分类
 */ -->

<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-input v-model="filters.categoryCode" placeholder="等级编码" @change="categoryCodeUpper"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getCategory" icon="search">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleAdd" icon="plus">新增</el-button>
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
      <el-table-column prop="categoryCode" label="等级编码" width="200" sortable>
      </el-table-column>
      <el-table-column prop="description" label="描述" min-width="200" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="remark" label="备注"  min-width="200" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="updateTime" label="更新时间" min-width="180">
      </el-table-column>
      <el-table-column label="操作" width="180">
        <template scope="scope">
          <el-button size="small" @click="handleEdit(scope.$index, scope.row)"><span><i class="el-icon-edit"></i> 编辑 </span></el-button>
          <el-button size="small" @click="handleDel(scope.$index, scope.row)"><span><i class="el-icon-delete"></i> 删除 </span></el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--工具条-->
    <el-col :span="24" class="toolbar-bottom">
      <el-pagination layout="total, sizes, prev, pager, next, jumper" @size-change="handleSizeChange" @current-change="handleCurrentChange" :page-size="pageSize" :total="total" style="float:right;">
      </el-pagination>
    </el-col>

    <!--新增界面-->
    <div class="aircraftegoryTag">
       <common-add-or-update
        @getInputItem="getCategoryChange"
        type="add"
        :to="API.addAircraftcategory().go"
        :callback="getCategory"
        :labelWidth="100"
        ref="addForm"></common-add-or-update>
    </div>
    <!--编辑界面-->
    <div class="aircraftegoryTag">
        <common-add-or-update
            @getInputItem="getCategoryChange"
            title="编辑"
            type="update"
            :to="API.editAircraftcategory().go"
            :callback="getCategory"
            :labelWidth="100"
            ref="editForm"></common-add-or-update>
    </div>
    <!--删除窗口-->
    <common-delete
        :to="API.removeAircraftcategory().go"
        :callback="getCategory"
        :labelWidth="100"
        ref="delConfirm"></common-delete>
  </section>
</template>

<script>
  import util from '../../common/js/util'
  import chooseDialog from './../../components/CommChooseGrid'
  import commonAddOrUpdate from './../../components/CommAddOrUpdate'
  import commonDelete from './../../components/CommDelete'
  import API from '../../api'
  import Pagination from '../../components/Pagination'

  export default {
    data () {
      return {
        filters: {
          categoryCode: '',
          description: '',
          remark: ''
        },
        airports: [],
        tableHeight: 495,
        total: 0,
        pageNumber: 1,
        pageSize: 10,
        listLoading: false,
        sels: [],
        categoryCodeValidate: null,
        // 编辑新增或者编辑
        addUpdateTag: null,
        // 判断等级编码是否发生改变
        categoryCodeTag: null,
        // 新增编辑需要的字段
        fields: [
          {name: 'id', value: '', hidden: true},
          {name: 'categoryCode', value: '', label: '等级编码', type: 'text', rules: [{max: 1, min: 1, required: true, message: '请输入一位等级编码'}]},
          {name: 'description', value: '', label: '描述', type: 'textarea', rules: [{max: 50, min: 1, required: true, message: '请输入一位到五十位字符串的描述'}]},
          {name: 'remark', value: '', label: '备注', type: 'textarea', rules: [{max: 100, min: 1, message: '备注只能为少于一百位的字符串'}]}
        ],
        API: API
      }
    },
    components: {
      chooseDialog: chooseDialog,
      commonAddOrUpdate: commonAddOrUpdate,
      commonDelete: commonDelete,
      pagination: Pagination
    },
    methods: {
      // 切换每页显示的条数
      handleSizeChange (val) {
        this.pageSize = val
        this.getCategory()
      },
      // 切换当前页,val第几页
      handleCurrentChange (val) {
        this.pageNumber = val
        this.getCategory()
      },
      // 获取机型分类列表
      getCategory () {
        let para = {
          pageNumber: this.pageNumber,
          pageSize: this.pageSize,
          categoryCode: this.filters.categoryCode,
          description: this.filters.description,
          remark: this.filters.remark
        }
        this.listLoading = true
        API.getAircraftcategory().go(para).then((data) => {
          if (data.ok) {
            this.total = data.attr.data.pager.recordCount
            this.airports = data.attr.data.list
            this.filters.categoryCode = ''
          } else {
            this.$notify(util.notifyBody(false, data.msg))
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
        this.categoryCodeTag = null
        this.fields[1].rules = this.categoryCodeValidate
        let addFields = util.deepCopy(this.fields)
        this.$refs['addForm'].show(addFields)
      },
      // 显示编辑界面
      handleEdit: function (index, row) {
        this.addUpdateTag = 'update'
        this.categoryCodeTag = row.categoryCode
        this.fields[1].rules = this.categoryCodeValidate
        let editFields = util.deepCopy(this.fields)
        for (let i = 0; i < editFields.length; i++) {
          editFields[i].value = row[editFields[i].name]
        }
        this.$refs['editForm'].show(editFields, row)
      },
      // 新增或者编辑的时候，在等级编码中无论输入大小写代码都能转换成大写字母
      getCategoryChange: function (data) {
        if (data.categoryCode !== this.categoryCodeTag) {
          var upperCategoryCode = null
          upperCategoryCode = data.categoryCode.toUpperCase()
          if (this.addUpdateTag === 'add') {
            var categoryCodeStrAdd = 'categoryCode'
            this.$refs['addForm'].changeInput(categoryCodeStrAdd, upperCategoryCode)
          }
          if (this.addUpdateTag === 'update') {
            var categoryCodeStrUpdate = 'categoryCode'
            this.$refs['editForm'].changeInput(categoryCodeStrUpdate, upperCategoryCode)
          }
        }
        this.categoryCodeTag = data.categoryCode
      },
      getCategoryCodeValidate: function () {
        this.categoryCodeValidate = (rule, value, callback) => {
          var categoryCode = value.split('')
          for (var i = 0; i < categoryCode.length; i++) {
            if ((value.charCodeAt(0) < 65) || (value.charCodeAt(0) > 122)) {
            return callback(new Error('只允许输入字母！'))
            }
            }
            if (categoryCode.length > 1) {
              return callback(new Error('等级编码只能是一位字母'))
            }
            callback()
       }
      },
      categoryCodeUpper: function (data) {
        this.filters.categoryCode = data.toUpperCase()
      }
    },
    mounted () {
      this.getCategory()
      this.getCategoryCodeValidate()
    }
  }

</script>

<style>
   .aircraftegoryTag .el-dialog--small {
    width: 40%!important
   }
</style>
