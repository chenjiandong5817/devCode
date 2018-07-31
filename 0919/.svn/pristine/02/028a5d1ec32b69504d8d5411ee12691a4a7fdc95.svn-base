/*
 * @Author: cdroid
 * @Date: 2017-05-25 10:54:43
 * @Last Modified by: cdroid
 * @Last Modified time: 2017-09-05 13:04:25
 * @Description:  角色列表界面
 */
<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-input v-model="filters.name" placeholder="角色名"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getRoles">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleAdd">新增</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <div class="table-content">
      <el-table :data="roles" highlight-current-row v-loading="listLoading" @selection-change="selsChange" :height="tableHeight" style="width: 100%;">
        <el-table-column type="selection" width="55">
        </el-table-column>
        <el-table-column type="index" width="60">
          <template scope="scope">
            {{pageSize * (pageNumber - 1) + scope.$index + 1 }}
          </template>
        </el-table-column>
        <el-table-column prop="name" label="角色" width="180" sortable>
        </el-table-column>
        <el-table-column prop="alias" label="别名" width="180" sortable>
        </el-table-column>
        <el-table-column prop="ps" label="权限" min-width="180" sortable>
        </el-table-column>
        <el-table-column prop="description" label="描述" min-width="180" sortable>
        </el-table-column>
        <el-table-column label="操作" width="180">
          <!--<template scope="scope">
            <el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
            <el-button type="danger" size="small" @click="handleDel(scope.$index, scope.row)">删除</el-button>
          </template>-->
          <template scope="scope">
            <el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
            <el-dropdown @command="handleCommand">
              <el-button size="small" >更多操作<i class="el-icon-caret-bottom el-icon--right"></i></el-button>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command="handleSetPer" :index="scope.$index" :row="scope.row"><span><i class="el-icon-edit"></i> 设置权限 </span></el-dropdown-item>
                <el-dropdown-item command="handleSetMenu" :index="scope.$index" :row="scope.row" :disabled="scope.row.name === 'admin'"><span><i class="el-icon-edit"></i> 设置菜单 </span></el-dropdown-item>
                <el-dropdown-item command="handleDel" :index="scope.$index" :row="scope.row" :disabled="scope.row.name === 'admin'"><span><i class="el-icon-circle-close"></i> 删除 </span></el-dropdown-item>
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

    <!--删除窗口-->
    <common-delete
        :to="API.removeRole().go"
        :callback="getRoles"
        ref="delConfirm"></common-delete>

    <!--新增界面-->
    <common-add-or-update
        type="add"
        :to="API.addRole().go"
        :callback="getRoles"
        ref="addForm"></common-add-or-update>

    <!--编辑界面-->
    <common-add-or-update
        title="编辑"
        type="update"
        :to="API.editRole().go"
        :callback="getRoles"
        ref="editForm"></common-add-or-update>

    <!--设置权限-->
    <choose-dialog
        title="设置权限"
        :gridHeight="500"
        :getListApi="API.fetchRolePerList().go"
        :updateApi="API.editRolePermission().go"
        :callback="getRoles"
        ref="permissionDialog"></choose-dialog>

    <!--设置菜单-->
    <choose-dialog
        title="设置菜单"
        :tree="true"
        :updateApi="API.updateRoleMenu().go"
        :callback="getRoles"
        ref="menuDialog"></choose-dialog>
  </section>
</template>

<script>
  import util from '../../common/js/util'
  import chooseDialog from './../../components/CommChooseGrid'
  import commonAddOrUpdate from './../../components/CommAddOrUpdate'
  import commonDelete from './../../components/CommDelete'
  import API from '../../api'
  import RouterConfig from '../../router/router-config'

  export default {
    data () {
      return {
        filters: {
          name: ''
        },
        roles: [],
        tableHeight: 495,
        total: 0,
        pageNumber: 1,
        pageSize: 20,
        listLoading: false,
        fields: [
          { name: 'id', value: '', hidden: true },
          { name: 'name', value: '', disabled: false, label: '角色名', type: 'text', rules: [ { required: true, message: '请输入角色名', trigger: 'blur' } ] },
          { name: 'alias', value: '', label: '别名', type: 'text', rules: null },
          { name: 'description', value: '', label: '描述', type: 'textarea', rules: null }
        ],
        API: API
      }
    },
    components: {
      chooseDialog: chooseDialog,
      commonAddOrUpdate: commonAddOrUpdate,
      commonDelete: commonDelete
    },
    methods: {
      handleCurrentChange (val) {
        this.pageNumber = val
        this.getRoles()
      },
      // 获取用户列表
      getRoles () {
        let para = {
          pageNumber: this.pageNumber,
          pageSize: this.pageSize,
          name: this.filters.name
        }
        this.listLoading = true
        API.getRoleListPage().go(para).then((res) => {
          if (res.ok) {
            this.total = res.attr.data.pager.recordCount
            this.roles = res.attr.data.list
            for (var i = 0; i < this.roles.length; i++) {
              var r = this.roles[i]
              var pstr = ''
              for (var j = 0; j < r.permissions.length; j++) {
                var p = r.permissions[j]
                if (p.alias) {
                  pstr += p.alias
                } else {
                  pstr += p.name
                }
                pstr += ' '
                if (j >= 0 && r.permissions.length > j + 1) {
                  pstr += ' 等' + r.permissions.length + '种权限'
                  break
                }
              }
              r.ps = pstr
            }
          } else {
            this.$notify(util.notifyBody(false, res.msg))
          }
          this.listLoading = false
        })
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
      // 编辑界面
      handleEdit: function (index, row) {
        let editFields = util.deepCopy(this.fields)
        for (let i = 0; i < editFields.length; i++) {
          editFields[i].value = row[editFields[i].name]
          if (editFields[i].name === 'name') {
            editFields[i].disabled = true
          }
        }
        this.$refs['editForm'].show(editFields, row)
      },
      // 新增界面
      handleAdd: function () {
        let addFields = util.deepCopy(this.fields)
        this.$refs['addForm'].show(addFields)
      },
      // 设置权限事件
      handleSetPer: function (index, row) {
        let columns = [
          { name: 'name', label: '权限名', width: 220 },
          { name: 'alias', label: '别名', width: 150 },
          { name: 'description', label: '描述', overflow: true }
        ]
        this.$refs.permissionDialog.show(row, columns)
      },
      // 设置菜单
      handleSetMenu: function (index, row) {
        let treeColumns = {
          label: 'name',
          children: 'children'
        }
        let m = function (data) {
          let children = []
          for (let i = 0; i < data.length; i++) {
            let item = data[i]
            if (!item.hidden) {
              let result = { id: item.id, name: item.name }
              if (item.leaf && item.children) {
                  result.id = item.children[0].id
                  result.name = item.children[0].name
              } else if (item.children) {
                var child = m(item.children)
                result['children'] = child
              }
              children.push(result)
            }
          }
          return children
        }
        API.getRoleMenuList().go({ id: row.id }).then(res => {
          console.log(res)
          let menuList = res.attr.data.list || []
          let treeCheckedKeys = []
          for (var i = 0; i < menuList.length; i++) {
            treeCheckedKeys.push(menuList[i].menuId)
          }
          let initData = {
            data: m(RouterConfig),
            treeCheckedKeys: treeCheckedKeys
          }
          this.$refs['menuDialog'].show(row, treeColumns, initData)
        })
      }
    },
    mounted () {
      this.getRoles()
    }
  }

</script>

<style scoped>

</style>
