<template>
  <div>
    <div class="menu-container ">
      <div class="toolbar menu-toolbar">
        <el-form :inline="true">
          <el-form-item class="divided">
            <el-form-item>
              <el-button type="primary" v-auth="'post_menu_add'" @click="handleAddMenu" size="small">新增</el-button>
              <el-button type="primary" v-auth="'put_menu_update'" :disabled="!selectedNode" @click="handleUpdateMenu" size="small">编辑</el-button>
              <el-button type="danger" v-auth="'post_menu_batch_remove'" :disabled="!nodeChecked" @click="handleDeleteMenu" size="small">删除</el-button>
            </el-form-item>
          </el-form-item>
          <el-form-item>
            <el-form-item>
              <el-button type="danger" v-auth="'btn_permission_set'" @click="handleSetRoleMenu" size="small">权限下发</el-button>
            </el-form-item>
          </el-form-item>
        </el-form>
      </div>
      <div class="menu-content">
        <div class="content">
          <el-row>
            <el-col :span="12">
              <div class="menu-tree">
                <el-tree
                  ref="menuTree"
                  v-loading="loading"
                  :data="menuAll"
                  show-checkbox
                  node-key="id"
                  default-expand-all
                  :default-expanded-keys="defaultExpandedKeys"
                  :default-checked-keys="defaultCheckedKeys"
                  :expand-on-click-node="false"
                  highlight-current
                  @node-click="handleNodeClick"
                  @check-change="handleNodeCheckChange"
                  :props="defaultProps">
                </el-tree>
              </div>
            </el-col>
            <el-col :span="12">
              <div class="menu-info">
                <div class="menu-detail">
                  <el-form label-width="50px" :inline-message="true">
                    <el-form-item label-width="0px">
                      <el-row>
                        <el-col :span="12">
                          <el-form-item label="标题">
                            <el-input :value="selectedNode ? selectedNode.label : ''" size="small" disabled></el-input>
                          </el-form-item>
                        </el-col>
                        <el-col :span="12">
                          <el-form-item label="编码">
                            <el-input :value="selectedNode ? selectedNode.code : ''" size="small" disabled></el-input>
                          </el-form-item>
                        </el-col>
                      </el-row>
                    </el-form-item>
                    <el-form-item label="路径">
                      <el-input :value="selectedNode ? selectedNode.href : ''" size="small" disabled></el-input>
                    </el-form-item>
                    <el-form-item label-width="0px">
                      <el-row>
                        <el-col :span="12">
                          <el-form-item label="图标">
                          <el-input :value="selectedNode ? selectedNode.icon : ''" size="small" disabled></el-input>
                        </el-form-item>
                        </el-col>
                        <el-col :span="12">
                          <el-form-item label="序号">
                            <el-input-number :value="selectedNode ? selectedNode.orderNum : 0" size="small" disabled></el-input-number>
                          </el-form-item>
                        </el-col>
                      </el-row>
                    </el-form-item>
                    <el-form-item label="备注">
                      <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 4}" :value="selectedNode ? selectedNode.remark : ''" size="small" disabled></el-input>
                    </el-form-item>
                    <el-form-item label-width="100px" label="菜单控件数量">
                      <el-button @click="showMenuControl" size="mini" type="primary" :disabled="!selectedNode">{{ menuControlCount }} 个</el-button>
                    </el-form-item>
                  </el-form>
                </div>
                <!-- <div class="menu-control">
                  <menu-control :menu="selectedNode"></menu-control>
                </div> -->
              </div>
            </el-col>
          </el-row>
        </div>
      </div>
      <menu-control-dialog ref="menuControls" :menu="selectedNode"></menu-control-dialog>
      <menu-dialog ref="menuDialog" :callback="getMenus"></menu-dialog>
      <role-menu-dialog ref="roleMenuDialog"></role-menu-dialog>
      <el-dialog
        title="确认"
        custom-class="dialog-to-message"
        :visible.sync="deleteMenuVisible">
        <span>  确定删除选择的节点？</span><br />
        <span>
          <el-checkbox v-model="deleteRootWhenNotChild">当无子节点时删除根节点</el-checkbox>
        </span>
        <span slot="footer" class="dialog-footer">
          <el-button @click="deleteMenuVisible = false" size="small">取 消</el-button>
          <el-button type="primary" @click="submitDeleteMenu" size="small">确 定</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>
<script>
import { mapGetters } from 'vuex'
import { deepCopy, findParent } from '@/util/util'
import MenuDialog from './menu-dialog'
import MenuControlDialog from './control/index-control-dialog'
import RoleMenuDialog from './menu-role-dialog'
import debounce from 'throttle-debounce/debounce'
export default {
  data () {
    return {
      defaultExpandedKeys: [],
      defaultCheckedKeys: [],
      // menus: [],
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      loading: false,
      deleteMenuVisible: false,
      deleteMenuLoading: false,
      deleteRootWhenNotChild: true,
      nodeChecked: false,
      selectedNode: null
    }
  },
  components: {
    MenuDialog, MenuControlDialog, RoleMenuDialog
  },
  computed: {
    ...mapGetters(['menuAll']),
    menuControlCount () {
      if (this.selectedNode && this.selectedNode.meta.controls) {
        return this.selectedNode.meta.controls.length
      }
      return 0
    }
  },
  beforeDestroy () {
    this.removeScrollListener()
  },
  created () {
    // this.$store.dispatch('GetMenuAll').then(data => {})
  },
  mounted () {
    // this.getMenus()
    this.addScrollListener()
  },
  methods: {
    getMenus () {
      if (!this.$auth('get_menu_tree')) {
        return
      }
      this.loading = true
      this.$store.dispatch('GetMenuAll').then(data => {
        this.loading = false
        // this.menus = data
        this.selectedNode = null
      })
      // getMenuList().then(res => {
      //   this.loading = false
      //   this.menus = res.data
      //   this.selectedNode = null
      // })
    },
    handleNodeClick (data) {
      this.selectedNode = data
    },
    handleAddMenu () {
      // let parent = searchNodeParent(this.selectedNode, this.menus)
      let parent = this.selectedNode ? findParent(this.menuAll, this.selectedNode.id) : null
      this.$refs['menuDialog'].toAdd(parent || this.selectedNode)
    },
    handleUpdateMenu () {
      if (!this.selectedNode) {
        this.$fail('请先选择一个节点')
        return
      }
      let menu = deepCopy(this.selectedNode)
      // let parent = searchNodeParent(this.selectedNode, this.menuAll)
      let parent = this.selectedNode ? findParent(this.menuAll, this.selectedNode.id) : null
      menu.parentId = parent ? parent.id : ''
      this.$refs['menuDialog'].toUpdate(menu)
    },
    handleDeleteMenu () {
      this.deleteMenuVisible = true
    },
    submitDeleteMenu () {
      let keys = this.$refs['menuTree'].getCheckedKeys(!this.deleteRootWhenNotChild)
      if (!keys || keys.length === 0) {
        this.$fail('未选中节点')
        this.deleteMenuVisible = false
        return
      }
      this.deleteMenuLoading = true
      let batchRemoveMenu = this.$auth('post_menu_batch_remove')
      batchRemoveMenu({ids: keys}).then(res => {
        res.status && this.$ok(res.message)
        this.getMenus()
        this.deleteMenuLoading = false
        this.deleteMenuVisible = false
      })
    },
    handleSetRoleMenu () {
      this.$refs['roleMenuDialog'].show()
    },
    addScrollListener () {
      let el = this.$el.querySelector('.menu-content')
      el.addEventListener('scroll', this.handleScroll)
    },
    removeScrollListener () {
      let el = this.$el.querySelector('.menu-content')
      el.removeEventListener('scroll', this.handleScroll)
    },
    handleScroll: debounce(100, function (event) {
      let el = this.$el.querySelector('.menu-info')
      el.style.top = event.target.scrollTop + 'px'
      let menuContent = this.$el.querySelector('.menu-content')
      el.style.height = menuContent.clientHeight - 1 + 'px'
    }),
    showMenuControl () {
      this.$refs['menuControls'].show()
    },
    handleNodeCheckChange () {
      this.nodeChecked = this.$refs['menuTree'] && (this.$refs['menuTree'].getCheckedKeys(!this.deleteRootWhenNotChild).length > 0)
    }
  }
}
</script>
<style lang="scss">
@import '~@/styles/global';
.menu-container {
  position: relative;
  height: 100%;
  overflow: hidden;
  // .menu-toolbar {
  //   margin-bottom: 5px;
  // }
  .menu-content {
    height: calc(100% - 47px);
    overflow-y: auto;
    .content {
      .menu-detail, .menu-control {
        border: 1px solid $content-border-color;
        border-radius: 3px;
        transition: .2s;
        box-shadow: 0 0 8px 0 rgba(15, 18, 27, .6), 0 2px 4px 0 rgba(15, 18, 27, .5);
      }
      .menu-tree {
        border-right: 1px solid $content-border-color;
      }
      .menu-info {
        position: absolute;
        top: 0;
        transition: top .35s ease;
        overflow-y: auto;
        .menu-detail, .menu-control {
          margin: 0 10px;
          padding: 10px;
        }
        .menu-detail {
          .el-form-item {
            margin-bottom: 0px;
          }
          .el-input__inner, .el-textarea__inner {
            color: #eee !important;
            cursor: text !important;
          }
        }
        .menu-control {
          margin-top: 10px;
          // height: calc(100% - 205px);
          overflow: hidden;
          .menu-control-container {
            overflow-y: auto;
          }
        }
      }
    }
  }
}
</style>
