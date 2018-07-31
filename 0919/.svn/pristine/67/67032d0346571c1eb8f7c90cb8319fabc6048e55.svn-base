/*
 * @Author: cdroid
 * @Date: 2017-05-25 10:54:21
 * @Last Modified by: cdroid
 * @Last Modified time: 2017-09-05 13:05:06
 * @Description:  权限列表界面
 */
<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-input v-model="filters.name" placeholder="权限名" ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getPermissions">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleAdd">新增</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table :data="permissions" highlight-current-row v-loading="listLoading" @selection-change="selsChange" :height="tableHeight" style="width: 100%;">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column type="index" width="60">
        <template scope="scope">
          {{pageSize * (pageNumber - 1) + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="name" label="权限" min-width="300" sortable>
      </el-table-column>
      <el-table-column prop="alias" label="别名" min-width="250">
      </el-table-column>
      <el-table-column prop="description" label="描述" show-overflow-tooltip min-width="100">
      </el-table-column>
      <el-table-column label="操作" width="180" fixed="right">
        <template scope="scope">
          <el-button size="small" @click="handleEdit(scope.$index, scope.row)"><span><i class="el-icon-edit"></i> 编辑 </span></el-button>
          <el-button size="small" @click="handleDel(scope.$index, scope.row)"><span><i class="el-icon-delete"></i> 删除 </span></el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--分页-->
    <pagination :to="getPermissions" ref="page"></pagination>

    <!--删除窗口-->
    <common-delete
        :to="API.removePermission().go"
        :callback="getPermissions"
        ref="delConfirm"></common-delete>

    <!--新增界面-->
    <common-add-or-update
        type="add"
        :to="API.addPermission().go"
        :callback="getPermissions"
        ref="addForm"></common-add-or-update>

    <!--编辑界面-->
    <common-add-or-update
        title="编辑"
        type="update"
        :to="API.editPermission().go"
        :callback="getPermissions"
        ref="editForm"></common-add-or-update>
  </section>
</template>

<script>
  import util from '../../common/js/util'
  import commonAddOrUpdate from './../../components/CommAddOrUpdate'
  import commonDelete from './../../components/CommDelete'
  import API from '../../api'
  import Pagination from '../../components/Pagination'

  export default {
    data () {
      return {
        filters: {
          name: ''
        },
        permissions: [],
        tableHeight: 495,
        listLoading: false,
        fields: [
          { name: 'id', value: '', hidden: true },
          { name: 'name', value: '', label: '权限名', type: 'text', rules: [ { required: true, message: '请输入权限名', trigger: 'blur' } ] },
          { name: 'alias', value: '', label: '别名', type: 'text', rules: null },
          { name: 'description', value: '', label: '描述', type: 'textarea', rules: null }
        ],
        API: API
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
      commonAddOrUpdate: commonAddOrUpdate,
      commonDelete: commonDelete,
      pagination: Pagination
    },
    methods: {
      selsChange: function (sels) {
        this.sels = sels
      },
      handleCommand: function (command, self) {
        var node = self.$vnode.data.attrs
        /* eslint-disable */
        eval('this.' + command).call(this, node.index, node.row)
        /* eslint-enable */
      },
      // 获取用户列表
      getPermissions () {
        let para = {
          name: this.filters.name
        }
        this.listLoading = true
        API.getPermissionListPage().go(para).then((res) => {
          if (res.ok) {
            this.total = res.attr.data.pager.recordCount
            this.permissions = res.attr.data.list
            this.filters.name = ''
          } else {
            this.$notify(util.notifyBody(false, res.msg))
          }
          this.listLoading = false
        })
      },
      // 删除
      handleDel: function (index, row) {
        console.log(row)
        this.$refs['delConfirm'].del(row)
      },
      // 显示编辑界面
      handleEdit: function (index, row) {
        let editFields = util.deepCopy(this.fields)
        for (let i = 0; i < editFields.length; i++) {
          editFields[i].value = row[editFields[i].name]
        }
        this.$refs['editForm'].show(editFields, row)
      },
      // 显示新增界面
      handleAdd: function () {
        let addFields = util.deepCopy(this.fields)
        this.$refs['addForm'].show(addFields)
      }
    },
    mounted () {
      this.getPermissions()
    }
  }

</script>

<style scoped>

</style>
