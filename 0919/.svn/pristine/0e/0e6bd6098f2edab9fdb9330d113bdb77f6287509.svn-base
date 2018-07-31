/*
 * @Author: cdroid
 * @Date: 2017-05-25 10:54:55
 * @Last Modified by: cdroid
 * @Last Modified time: 2017-09-14 15:57:11
 * @Description:  用户列表界面
 */
<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-input v-model="filters.name" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item>
          <el-select v-model="filters.locked" placeholder="请选择锁定状态">
            <el-option label="锁定" :value="true"></el-option>
            <el-option label="激活" :value="false"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getUsers">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleAdd">新增</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="danger" @click="batchLocked(true)" :disabled="this.sels.length===0">锁定</el-button>
          <el-button type="primary" @click="batchLocked(false)" :disabled="this.sels.length===0">激活</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <div class="table-content">
      <el-table v-bind:data="users" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;" :height="tableHeight">
        <el-table-column type="selection" width="55">
        </el-table-column>
        <el-table-column type="index" width="60">
        </el-table-column>
        <el-table-column prop="name" label="用户名" min-width="150">
        </el-table-column>
        <el-table-column prop="rs" label="角色" min-width="180">
        </el-table-column>
        <el-table-column prop="ps" label="特许权限" min-width="180">
        </el-table-column>
        <el-table-column prop="locked" label="状态" :formatter="formatLocked" min-width="100">
        </el-table-column>
        <!-- <el-table-column prop="updateTime" label="最近更新时间" width="180" sortable>
        </el-table-column> -->
        <el-table-column label="操作" width="180">
          <template scope="scope">
            <el-dropdown @command="handleCommand">
              <el-button size="small" >更多操作<i class="el-icon-caret-bottom el-icon--right"></i></el-button>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command="handleSetRole" :index="scope.$index" :row="scope.row"><span><i class="el-icon-setting"></i> 设置角色 </span></el-dropdown-item>
                <el-dropdown-item command="handleAirportSubscribe" :index="scope.$index" :row="scope.row"><span><i class="el-icon-setting"></i> 机场订阅 </span></el-dropdown-item>
                <el-dropdown-item command="handleSetPer" :index="scope.$index" :row="scope.row"><span><i class="el-icon-setting"></i> 特许权限 </span></el-dropdown-item>
                <el-dropdown-item command="handleEdit" :index="scope.$index" :row="scope.row"><span><i class="el-icon-edit"></i> 修改密码 </span></el-dropdown-item>
                <el-dropdown-item command="handleDel" :index="scope.$index" :row="scope.row"><span><i class="el-icon-circle-close"></i> 删除 </span></el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </template>
        </el-table-column>
      </el-table>
    </div>


    <!--工具条-->
    <el-col :span="24" class="toolbar">
      <el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="pageSize" :total="total" style="float:right;">
      </el-pagination>
    </el-col>

    <!--设置角色界面-->
    <choose-dialog
        title="设置角色"
        :getListApi="API.fetchUserRoleList().go"
        :updateApi="API.updateUserRole().go"
        :callback="getUsers"
        ref="roleDialog"></choose-dialog>

    <!--设置特许权限界面-->
    <choose-dialog
        title="设置权限"
        :getListApi="API.fetchUserPerList().go"
        :updateApi="API.updateUserPer().go"
        :gridHeight="500"
        :callback="getUsers"
        ref="permissionDialog"></choose-dialog>

    <!--新增界面-->
    <common-add-or-update
        @getInputItem="nameChange"
        type="add"
        :to="API.addUser().go"
        :callback="getUsers"
        ref="addForm"></common-add-or-update>
    <!--编辑界面-->
    <common-add-or-update
        @getInputItem="nameChange"
        title="编辑"
        type="update"
        :to="API.editUser().go"
        :callback="getUsers"
        ref="editForm"></common-add-or-update>

    <!--删除窗口-->
    <common-delete
        :to="API.removeUser().go"
        :callback="getUsers"
        ref="delConfirm"></common-delete>
    <!-- 用户与机场绑定的弹出框 -->
    <user-airport
        title="机场订阅"
        :getAllBaseAirports="API.getAirportListPage().go"
        :getAllDefaultAirport="API.getAiisAirportListPage().go"
        :getAllUsersSubscribes="API.getUserDefaultAirport().go"
        :postUserSubscribe="API.setUserAirportSubscribe().go"
        :getLastedUserData="this.getUsers"
        ref="userAirportDialog">
    </user-airport>
  </section>
</template>

<script>
  import util from '../../common/js/util'
  import chooseDialog from './../../components/CommChooseGrid'
  import commonAddOrUpdate from './../../components/CommAddOrUpdate'
  import commonDelete from './../../components/CommDelete'
  import userAirport from './systemUserAirport/UserAirport'
  import API from '../../api'

  export default {
    data () {
      return {
        filters: {
          name: '',
          locked: null
        },
        users: [],
        roles: [],
        total: 0,
        pageNumber: 1,
        pageSize: 50,
        listLoading: false,
        tableHeight: 495,
        nameTag: null,
        addUpdateTag: null,
        sels: [],
        // 新增编辑需要的字段
        fields: [
          { name: 'id', value: '', hidden: true },
          { name: 'name', value: '', disabled: false, label: '用户名', type: 'text', rules: [ { required: true, message: '请输入用户名', trigger: 'blur' } ] },
          { name: 'password', value: '', label: '密码', type: 'password', rules: [ { required: true, message: '请输入密码', trigger: 'blur' } ] },
          { name: 'locked', value: false, label: '锁定', type: 'radio', choose: [ { text: '是', value: true }, { text: '否', value: false } ], rules: [ { required: true, message: '请选择锁定状态' } ] }
        ],
        API: API
      }
    },
    components: {
      chooseDialog: chooseDialog,
      commonAddOrUpdate: commonAddOrUpdate,
      commonDelete: commonDelete,
      userAirport: userAirport
    },
    methods: {
      formatLocked (row, column) {
        return row.locked === true ? '锁定' : '激活'
      },
      handleCurrentChange (val) {
        this.pageNumber = val
        this.getUsers()
      },
      // 获取用户列表
      getUsers () {
        let param = {
          pageNumber: this.pageNumber,
          pageSize: this.pageSize,
          name: this.filters.name,
          locked: this.filters.locked === '' ? null : this.filters.locked
        }
        this.listLoading = true
        API.getUserListPage().go(param).then((data) => {
          this.listLoading = false
          if (!data.ok) {
            this.$notify(util.notifyBody(false, data.msg))
            return
          }
          this.total = data.attr.data.pager.recordCount
          this.users = data.attr.data.list
          // this.total = data.attr.data.length
          // this.users = data.attr.data
          for (var i = 0; i < this.users.length; i++) {
            var u = this.users[i]
            var pstr = ''
            for (var j = 0; j < u.permissions.length; j++) {
              var p = u.permissions[j]
              if (p.alias) {
                pstr += p.alias
              } else {
                pstr += p.name
              }
              pstr += ' '
              if (j >= 0 && u.permissions.length > j + 1) {
                pstr += ' 等' + u.permissions.length + '种权限'
                break
              }
            }
            u.ps = pstr
            var rstr = ''
            for (var k = 0; k < u.roles.length; k++) {
              var r = u.roles[k]
              if (r.alias) {
                rstr += r.alias
              } else {
                rstr += r.name
              }
              if (k >= 0 && u.roles.length > k + 1) {
                rstr += ' 等' + u.roles.length + '种角色'
                break
              }
            }
            u.rs = rstr
          }
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
        let addFields = util.deepCopy(this.fields)
        this.$refs['addForm'].show(addFields)
      },
      // 显示编辑界面
      handleEdit: function (index, row) {
        this.addUpdateTag = 'edit'
        let editFields = util.deepCopy(this.fields)
        for (let i = 0; i < editFields.length; i++) {
          editFields[i].value = row[editFields[i].name]
          if (editFields[i].name === 'locked') {
            editFields.splice(i, 1)
          } else if (editFields[i].name === 'name') {
            editFields[i].disabled = true
          }
        }
        this.$refs['editForm'].show(editFields, row)
      },
      // 批量锁定
      batchLocked: function (locked) {
        let toApi = API.batchLockedUser().go
        let opt = '锁定'
        if (!locked) {
          toApi = API.batchUnlockedUser().go
          opt = '激活'
        }
        var ids = this.sels.map(item => item.id).toString()
        this.$confirm('确认' + opt + '选中记录吗？', '提示', {
          type: 'warning'
        }).then(() => {
          this.listLoading = true
          let para = { ids: ids }
          toApi(para).then((res) => {
            this.listLoading = false
            this.$notify(util.notifyBody(res.ok, res.msg))
            this.getUsers()
          })
        }).catch(() => {

        })
      },
      // 设置角色界面
      handleSetRole: function (index, row) {
        let columns = [
          { name: 'name', label: '角色名', width: 220 },
          { name: 'alias', label: '别名', width: 150 },
          { name: 'description', label: '描述', overflow: true }
        ]
        this.$refs['roleDialog'].show(row, columns)
      },
      // 设置权限界面
      handleSetPer: function (index, row) {
        let columns = [
          { name: 'name', label: '权限名', width: 220 },
          { name: 'alias', label: '别名', width: 150 },
          { name: 'description', label: '描述', overflow: true }
        ]
        this.$refs['permissionDialog'].show(row, columns)
      },
      // 显示机场订阅的界面
      handleAirportSubscribe: function (index, row) {
        this.$refs['userAirportDialog'].handleAirportSubscribe(index, row)
      },
      nameChange: function (data) {
        console.log('data', data)
        if (data.name !== this.nameTag) {
          var singleLowerCode = null
          singleLowerCode = data.name.toLowerCase()
          if (this.addUpdateTag === 'add') {
            var nameStrAdd = 'name'
            this.$refs['addForm'].changeInput(nameStrAdd, singleLowerCode)
          }
          if (this.addUpdateTag === 'edit') {
            var nameStrEdit = 'name'
            this.$refs['editForm'].changeInput(nameStrEdit, singleLowerCode)
          }
        }
        this.nameTag = data.name
      }
    },
    mounted () {
      this.getUsers()
    }
  }

</script>

<style scoped>
  /*.el-select {
    width: 250px!important;
  }*/
</style>
