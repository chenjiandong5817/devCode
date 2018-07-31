<template>
  <el-dialog :title="`权限下发 > ${selectedRole ? selectedRole.name : '请选择角色...'}`" :visible.sync="visible" :close-on-click-modal="false"
      @close="handleClose" width="80%" custom-class="role-menu-container">
      <el-row class="role-menu-body">
        <el-col :span="7">
          <div class="role-menu-item">
            <div class="title">
              角色列表
            </div>
            <el-table :data="roleAll" style="width: 100%" @row-click="handleSelectRoleRow"
              highlight-current-row ref="roleListTable" v-if="!roleLoading">
              <el-table-column label="编码" prop="code" width="120px"></el-table-column>
              <el-table-column label="名称" prop="name"></el-table-column>
            </el-table>
            <div class="role-table-layout" v-show="roleLoading" v-loading="tableLoading"></div>
          </div>
        </el-col>
        <el-col :span="7">
          <div class="role-menu-item">
            <div class="title">
              菜单列表
              <i class="fa fa-save icon icon-1" v-auth="{code: 'put_role_save_menus', click: handleSaveMenu, native: true}" v-if="selectedRole"/>
              <i class="fa fa-save icon icon-1 disabled" v-auth="'put_role_save_menus'" v-else/>
              <i class="fa fa-refresh icon icon-2" @click="handleResetMenu" v-if="selectedRole"/>
              <i class="fa fa-refresh icon icon-2 disabled" v-else/>
            </div>
            <div class="tree-container">
              <el-tree
                v-show="selectedRole"
                ref="menuTree"
                :data="menuAll"
                show-checkbox
                node-key="id"
                :expand-on-click-node="false"
                highlight-current
                @node-click="handleTreeNodeClick"
                :props="{
                  children: 'children',
                  label: 'label'
                }">
              </el-tree>
              <div v-show="!selectedRole" class="no-check-role-text">
                未选择角色...
              </div>
            </div>
          </div>
        </el-col>
        <el-col :span="9">
          <div class="role-menu-item">
            <div class="title">
              菜单控件
              <i class="fa fa-save icon icon-1" v-auth="{code: 'put_role_save_controls', click: handleSaveMenuControll, native: true}" v-if="selectedRole"/>
              <i class="fa fa-save icon icon-1 disabled" v-auth="'put_role_save_controls'" v-else/>
              <i class="fa fa-refresh icon icon-2" @click="handleResetControl" v-if="selectedRole"/>
              <i class="fa fa-refresh icon icon-2 disabled" v-else/>
            </div>
            <div class="tree-container">
              <el-tree
                v-show="selectedRole"
                ref="menuControlTree"
                :data="controlAll"
                show-checkbox
                node-key="id"
                :expand-on-click-node="false"
                highlight-current
                :props="{
                  children: 'children',
                  label: 'label'
                }">
              </el-tree>
              <div v-show="!selectedRole" class="no-check-role-text">
                未选择角色...
              </div>
            </div>
          </div>
        </el-col>
      </el-row>
      <div class="dialog-to-top">
          <i class="fa fa-arrow-up fa-lg" @click="click2Top"/>
      </div>
  </el-dialog>
</template>
<script>
import { mapGetters } from 'vuex'
import { validatenull } from '@/util/validate'
import throttle from 'throttle-debounce/throttle'
import { elment2Top } from '@/util/util'
export default {
  data () {
    return {
      visible: false,
      tableLoading: false,
      controlLoading: false,
      selectedRole: null,
      menuControlAll: []
    }
  },
  computed: {
    ...mapGetters(['menuAll', 'roleAll', 'roleLoading']),
    controlAll () {
      let fillControl = (menus) => {
        let array = []
        if (menus) {
          for (let menu of menus) {
            if (!validatenull(menu.children)) {
              // array.push(...fillControl(menu.children))
              array.push({
                id: menu.id,
                label: menu.label || menu.code,
                children: fillControl(menu.children)
              })
            } else if (!validatenull(menu.meta.controls)) {
              array.push({
                id: menu.id,
                label: menu.label || menu.code,
                children: menu.meta.controls.map(control => {
                  return {
                    id: control.id,
                    label: control.name + ` (${control.code})`
                  }
                })
              })
            }
          }
        }
        // console.log(array)
        return array
      }
      return fillControl(this.menuAll)
    }
  },
  watch: {
    roleLoading: {
      immediate: true,
      handler (val) {
        let el = this.$el
        let vmTable = this.$refs['roleListTable']
        if (el && vmTable) {
          let table = vmTable.$el
          let layout = el.querySelector('.role-table-layout')
          if (layout) {
            layout.style.height = table.clientHeight + 'px'
            layout.style.width = table.clientWidth + 'px'
          }
        }
      }
    }
  },
  beforeDestroy () {
    this.removeDialogEventListener()
  },
  mounted () {
    this.addDialogEventListener()
  },
  methods: {
    show () {
      this.visible = true
    },
    // 退出dialog，取消选中的角色
    handleClose () {
      this.selectedRole = null
      // 清除所有的选中状态
      this.$refs['menuTree'].setCheckedKeys([])
      this.$refs['menuControlTree'].setCheckedKeys([])
      this.$refs['roleListTable'].setCurrentRow()
    },
    // 点击角色列表的一行
    handleSelectRoleRow (row) {
      this.selectedRole = null
      this.$nextTick(() => {
        this.selectedRole = row
        this.handleResetMenu()
        this.handleResetControl()
      })
    },
    handleResetMenu () {
      // console.log(this.selectedRole.meta.menus)
      this.$refs['menuTree'].setCheckedKeys(this.selectedRole.meta.menus.map(item => item.id))
    },
    handleResetControl () {
      this.$refs['menuControlTree'].setCheckedKeys(this.selectedRole.meta.controls.map(item => item.id))
    },
    // 点击菜单列表，选中控件列表
    handleTreeNodeClick (node) {
      this.$refs['menuControlTree'].setCurrentKey(node.id)
    },
    // 保存角色控件
    handleSaveMenuControll (saveRoleMenuControl) {
      let keys = this.$refs['menuControlTree'].getCheckedKeys(true)
      let params = {
        roleId: this.selectedRole.id,
        controlIds: keys
      }
      // let saveRoleMenuControl = this.$auth('put_role_save_controls')
      saveRoleMenuControl(params).then(res => {
        if (res.status) {
          this.$ok(res.message)
          this.getRoleAll()
        }
      })
    },
    // 保存角色菜单
    handleSaveMenu (saveRoleMenu) {
      let keys = this.$refs['menuTree'].getCheckedKeys(true)
      let params = {
        roleId: this.selectedRole.id,
        menuIds: keys
      }
      saveRoleMenu(params).then(res => {
        if (res.status) {
          this.$ok(res.message)
          this.getRoleAll()
        }
      })
    },
    // 操作结束后，重新获取角色
    getRoleAll () {
      this.tableLoading = true
      this.$store.dispatch('GetRoleAll').then(data => {
        this.tableLoading = false
        if (this.selectedRole) {
          this.$nextTick(() => {
            // 直接使用this.selectedRole当作参数无法选中，估计是element-ui的bug
            for (let role of this.roleAll) {
              if (role.id === this.selectedRole.id) {
                this.$refs['roleListTable'].setCurrentRow(role)
              }
            }
          })
        }
      })
    },
    // 点击返回顶部
    click2Top () {
      elment2Top(this.$el)
    },
    // 滚动事件
    toggleToTopIcon: throttle(150, function (event) {
      let el = this.$el
      if (!el) return
      let toTop = el.querySelector('.dialog-to-top')
      let body = el.querySelector('.role-menu-body')
      if (!toTop || !body) return
      let right = (el.clientWidth - body.clientWidth) / 2
      toTop.style.right = (right > 5 ? right : 5) + 'px'
      if (el.scrollTop > el.clientHeight * 0.5) {
        toTop.style.bottom = 50 + 'px'
      } else {
        toTop.style.bottom = -50 + 'px'
      }
    }),
    // 添加事件监听
    addDialogEventListener () {
      if (this.$el) {
        window.addEventListener('resize', this.toggleToTopIcon)
        this.$el.addEventListener('scroll', this.toggleToTopIcon)
      }
    },
    // 移除事件监听
    removeDialogEventListener () {
      if (this.$el) {
        window.removeEventListener('resize', this.toggleToTopIcon)
        this.$el.removeEventListener('scroll', this.toggleToTopIcon)
      }
    }
  }
}
</script>
<style lang="scss">
@import '~@/styles/global';
.role-menu-container {
  min-width: 1000px;
  transition: scrollTop  1s ease;
  .el-dialog__body {
    padding-top: 0;
  }
  .el-row {
    .el-col {
      &:first-child {
        margin-right: 10px;
      }
      &:last-child {
        margin-left: 10px;
      }
    }
  }
  .role-menu-item {
    // padding: 3px 3px 0px 3px;
    border: 1px solid $content-border-color;
    border-radius: 3px;
    box-shadow: 0 0 4px 0 rgba(15, 18, 27, .3), 0 2px 4px 0 rgba(83, 108, 182, 0.5);
    .tree-container {
      padding: 3px;
      overflow: auto;
      .no-check-role-text {
        padding: 5px 20px;
      }
    }
    .el-tree-node {
      > .el-tree-node__children {
        overflow: inherit;
      }
    }
    .title {
      position: relative;
      font-size: 20px;
      text-align: center;
      font-weight: 700;
      margin: 3px 0;
      .icon {
        position: absolute;
        cursor: pointer;
        transition: transform .2s;
        &:not(.disabled) {
          &:hover {
            color: $color-primary;
            transform: scale(1.3, 1.3);
          }
          &:active {
            color: #cf483e;
          }
        }
        &.disabled {
          cursor: not-allowed;
        }
        &.icon-1 {
          right: 1px;
        }
        &.icon-2 {
          right: 22px;
        }
      }
    }
  }
  .dialog-to-top {
    position: fixed;
    transition: bottom .35s ease, right .35s ease;
    bottom: -50px;
    height: 25px;
    line-height: 25px;
    width: 25px;
    background-color: #f1f1f1;
    border: 1px solid #cccccc;
    border-radius: 4px;
    text-align: center;
    i {
      cursor: pointer;
    }
  }
}
</style>
