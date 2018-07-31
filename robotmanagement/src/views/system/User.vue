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
          <el-input v-model="filters.name" placeholder="名称"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="searchUser">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleAdd">新增</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table v-bind:data="users" highlight-current-row v-loading="listLoading" @selection-change="selsChange" :height="tableHeight" style="width: 100%;">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column type="index" width="60">
        <template slot-scope="scope">
          {{pageSize * (pageNumber - 1) + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="username" label="名字" min-width="120" sortable>
      </el-table-column>
      <el-table-column label="操作" width="180" fixed="right" >
        <template slot-scope="scope">
          <el-button size="small" @click="handleEdit(scope.$index, scope.row)"><span><i class="el-icon-edit"></i> 编辑 </span></el-button>
          <el-button size="small" @click="handleDel(scope.$index, scope.row)"><span><i class="el-icon-delete"></i> 删除 </span></el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--分页-->
    <pagination :to="getAllUsersList" ref="page"></pagination>

    <!--新增界面-->
    <common-add-or-update
        type="add"
        :to="API.busAddUser().go"
        :callback="getAllUsersList"
        :labelWidth="100"
        ref="addForm"></common-add-or-update>
    <!--编辑界面-->
    <common-add-or-update
        title="编辑"
        type="update"
        :to="API.busEditUser().go"
        :callback="getAllUsersList"
        :labelWidth="100"
        ref="editForm"></common-add-or-update>
    <!--删除窗口-->
    <common-delete
        :to="API.busRemoveUser().go"
        :callback="getAllUsersList"
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
          name: ''
        },
        flightstatus: [],
        tableHeight: 495,
        listLoading: false,
        sels: [],
        users: [],
        // 分页赋值
        page: {
          pageNum: 1,
          pageSize: 50
        },
        // 用于标记是新增还是编辑
        addUpdateTag: null,
        // 新增编辑需要的字段
        fields: [
          { name: 'id', value: '', hidden: true },
          { name: 'username', value: '', disabled: false, label: '用户名', type: 'text', rules: [ { required: true, message: '请输入用户名' } ] },
          { name: 'password', value: '', disabled: false, label: '密码', type: 'text', rules: [ { required: true, message: '请输入密码' } ] }
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
      getUsersList () {
        let para = Object.assign({}, this.filters)
        this.listLoading = true
        API.busGetUserListPage().go(para).then((data) => {
          if (data.status) {
            var result = []
            result.push(data.attr.data)
            this.users = result
          } else {
            this.$notify(Util.notifyBody(false, data.message))
          }
          this.listLoading = false
        })
      },
      // 获取所有用户列表
      getAllUsersList () {
        let para = Object.assign({}, this.page)
        this.listLoading = true
        API.busGetAllUserListPage().go(para).then((data) => {
          if (data.status) {
            this.$refs['page'].set('total', data.attr.data.total)
            this.users = data.attr.data.list
          } else {
            this.$notify(Util.notifyBody(false, data.message))
          }
          this.listLoading = false
        })
      },
      // 查询操作
      searchUser () {
        if (this.filters.name) {
          this.getUsersList()
        } else {
          this.getAllUsersList()
        }
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
        this.addUpdateTag = 'update'
        let editFields = Util.deepCopy(this.fields)
        for (let i = 0; i < editFields.length; i++) {
          editFields[i].value = row[editFields[i].name]
          if (editFields[i].name === 'username') {
            editFields[i].disabled = true
          }
          if (editFields[i].name === 'password') {
            editFields[i].value = ''
          }
        }
        this.$refs['editForm'].show(editFields)
      }
    },
    mounted () {
      this.getAllUsersList()
    }
  }
</script>

<style scoped>

</style>
