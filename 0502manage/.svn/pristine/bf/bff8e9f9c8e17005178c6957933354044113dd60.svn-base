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
    <div class="operateTag">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-button type="primary" @click="handleAdd">新增</el-button>
        </el-form-item>
      </el-form>
    </div>
    </el-col>

    <!--列表-->
    <el-table v-bind:data="desClassifys" highlight-current-row v-loading="listLoading" @selection-change="selsChange" :height="tableHeight" style="width: 100%;">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column type="index" width="60">
        <template slot-scope="scope">
          {{pageSize * (pageNumber - 1) + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="classify" label="目的地分类名称" min-width="250" sortable>
      </el-table-column>
      <el-table-column label="操作" width="180" fixed="right" >
        <template slot-scope="scope">
          <el-button size="small" @click="handleEdit(scope.$index, scope.row)"><span><i class="el-icon-edit"></i> 编辑 </span></el-button>
          <el-button size="small" @click="handleDel(scope.$index, scope.row)"><span><i class="el-icon-delete"></i> 删除 </span></el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--分页-->
    <pagination :to="getDesClassifysList" ref="page"></pagination>

    <!--新增界面-->
    <div class="resourceStatuTag">
      <common-add-or-update
        type="add"
        :to="API.addDesClassify().go"
        :callback="getDesClassifysList"
        :labelWidth="120"
        ref="addForm"></common-add-or-update>
    </div>
    <!--编辑界面-->
    <div class="resourceStatuTag">
       <common-add-or-update
        title="编辑"
        type="update"
        :to="API.editDesClassify().go"
        :callback="getDesClassifysList"
        :labelWidth="120"
        ref="editForm"></common-add-or-update>
    </div>
    <!--删除窗口-->
    <common-delete
        :to="API.removeDesClassify().go"
        :callback="getDesClassifysList"
        :labelWidth="100"
        ref="delConfirm"></common-delete>
  </section>
</template>

<script>
  import Util from './../../common/js/util'
  import baseUtil from './../../common/js/base-util'
  import chooseDialog from './../../components/CommChooseGrid'
  import commonAddOrUpdate from './../../components/CommAddOrUpdate'
  import commonDelete from './../../components/CommDelete'
  import API from './../../api'
  import Pagination from './../../components/Pagination'

  export default {
    data () {
      return {
        filters: {
          name: null
        },
        desClassifys: [],
        tableHeight: 495,
        listLoading: false,
        sels: [],
        addUpdateTag: null,
        // 新增编辑需要的字段
        fields: [
          { name: 'id', value: '', hidden: true },
          { name: 'classify', value: '', label: '目的地分类名称', type: 'text', rules: [ { required: true, message: '请输入目的地分类名称' } ], placeholder: '请输入目的地分类名称' }
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
      getDesClassifysList () {
        let para = Object.assign({}, this.$refs['page'].queryParam())
        this.listLoading = true
        API.getDesClassifyListPage().go(para).then((data) => {
          if (data.status === 1) {
            this.$refs['page'].set('total', data.data.total)
            this.desClassifys = data.data.list
          } else {
            this.$notify(Util.notifyBody(false, data.message))
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
        let addFields = Util.deepCopy(this.fields)
        this.$refs['addForm'].show(addFields)
      },
      // 显示编辑界面
      handleEdit: function (index, row) {
      	this.addUpdateTag = 'edit'
        let editFields = Util.deepCopy(this.fields)
        for (let i = 0; i < editFields.length; i++) {
          editFields[i].value = row[editFields[i].name]
        }
        this.$refs['editForm'].show(editFields)
      }
    },
    mounted () {
      this.getDesClassifysList()
    }
  }
</script>

<style>
   .resourceStatuTag .el-dialog--small {
    width: 40%!important
   }
   .operateTag .el-form-item {
    float: right;
    margin-right: 30px;
  }
</style>
