<template>
<div class="message-container">
  <div class="toolbar">
    <el-form :inline="true">
      <el-form-item>
        <el-input v-model="filters" placeholder="请输入消息类型或编码" size="small"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary"  size="small"   @click="handleSearchMessageType">查询</el-button>
        <el-button type="primary"  size="small"   @click="handleAddMessageType" v-auth ="`post_message_type_add`">新增</el-button>
        <el-button type="primary"  size="small" @click="handleUpdateMessageType" v-auth = "`put_message_type_update`" :disabled="!selectedNode">编辑</el-button>
        <el-button type="danger"  size="small" @click="deleteMessageTypeVisible = true" v-auth="`post_message_type_list_delete`" :disabled="!selectedNode">删除</el-button>
      </el-form-item>
    </el-form>
  </div>
  <el-row class="message-content">
    <el-col :span="12">
      <div class="message-type-tree">
        <div style="z-index: 1;position: absolute;">暂无数据</div>
        <el-tree
         :data="messageTypeAll"
          show-checkbox
          default-expand-all
          @node-click="handleNodeClick"
          node-key="id"
          ref="messageTypeTree"
          :props="defaultProps"
          highlight-current
          :expand-on-click-node="false"
          :filter-node-method = "filterNode"
          style="z-index:2;flex: 1;align-self: flex-start;">
        </el-tree>
      </div>
    </el-col>
      <el-col :span="11">
        <div class="message-type-info">
          <div class="message-type-detail">
            <el-form label-position="right" label-width="80px" :inline="true"  size="small" >
              <el-form-item label="消息类型">
                <el-input  :value="selectedNode ? selectedNode.name : ''" disabled></el-input>
              </el-form-item>
              <el-form-item label="消息编码">
                <el-input :value="selectedNode ? selectedNode.code : ''" disabled></el-input>
              </el-form-item>
            </el-form>
          </div>
        </div>
      </el-col>
  </el-row>
  <message-type-dialog  :parent = "parent" :openFlag="dialogVisible" @close="close" ref="messageTypeDialog"
  @addItem="addItem" @updateItem="updateItem"></message-type-dialog>
  <el-dialog
      title="确认"
      custom-class="dialog-to-message"
      :visible.sync="deleteMessageTypeVisible"
      width="400px">
      <span>确定删除选择的节点？</span><br />
      <span>
        <el-checkbox v-model="deleteRootWhenNotChild">当无子节点时删除根节点</el-checkbox>
      </span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="deleteMessageTypeVisible = false" size="small">取 消</el-button>
        <el-button type="primary" @click="submitDeleteMessageType" size="small">确 定</el-button>
      </span>
  </el-dialog>
</div>
</template>

<script>
import MessageTypeDialog from './message-type-dialog'
import { deepCopy } from '@/util/util'
import { mapGetters } from 'vuex'
import debounce from 'throttle-debounce/debounce'

export default {
  components: {
    MessageTypeDialog
  },
  data () {
    return {
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      selectedNode: null,
      id: null,
      addFlag: false,
      dialogVisible: false,
      parent: {
        name: null,
        id: null
      },
      deleteMessageTypeVisible: false,
      deleteRootWhenNotChild: true,
      filters: null
    }
  },
  computed: {
    ...mapGetters(['messageTypeAll'])
  },
  mounted () {
    this.addScrollListener()
  },
  beforeDestroy () {
    this.removeScrollListener()
  },
  methods: {
    addScrollListener () {
      let el = this.$el.querySelector('.message-content')
      el.addEventListener('scroll', this.handleScroll)
    },
    removeScrollListener () {
      let el = this.$el.querySelector('.message-content')
      el.removeEventListener('scroll', this.handleScroll)
    },
    handleScroll: debounce(100, function (event) {
      let el = this.$el.querySelector('.message-type-info')
      el.style.top = event.target.scrollTop + 'px'
      let menuContent = this.$el.querySelector('.message-content')
      el.style.height = menuContent.clientHeight - 1 + 'px'
    }),
    handleSearchMessageType () {
      this.$refs['messageTypeTree'].filter(this.filters)
    },
    filterNode (value, data) {
      if (!value) return true
      return data.name.indexOf(value) !== -1 || data.code.indexOf(value) !== -1
    },
    getMessageTypeById () {
      let getMessageType = this.$auth('get_message_type')
      getMessageType({id: this.id}).then((res) => {
        this.dialogVisible = true
        this.$refs['messageTypeDialog'].toUpdate(res.data)
      })
    },
    getMessageTypeList () {
      let getMessageTypeList = this.$auth('get_message_type_list')
      if (getMessageTypeList) {
        getMessageTypeList().then((res) => {
          if (res.status === 1) {
            // this.data = res.data
            // 更新缓存, 20180528 cdroid 修改从vuex获取消息类型
            this.$store.commit('SET_MESSAGR_TYPE_ALL', res.data)
          }
        })
      } else {
        this.$fail('无权限访问！')
      }
    },
    handleNodeClick (data) {
      this.selectedNode = data
    },
    close (data) {
      this.parent.name = null
      this.parent.id = null
      this.dialogVisible = data
    },
    handleAddMessageType () {
      this.dialogVisible = true
      if (this.selectedNode) {
        this.parent.name = this.selectedNode.name
        this.parent.id = this.selectedNode.id
      }
      this.$refs['messageTypeDialog'].toAdd()
    },
    handleUpdateMessageType () {
      if (!this.selectedNode) {
        this.$fail('请先选择一个节点')
        return
      }
      let ele = this.$refs['messageTypeTree'].getNode(this.selectedNode.id).parent
        ? this.$refs['messageTypeTree'].getNode(this.selectedNode.id).parent
        : ''
      this.parent.name = ele ? ele.data.name : ''
      this.parent.id = ele ? ele.data.id : ''
      this.id = this.selectedNode.id
      this.getMessageTypeById()
    },
    submitDeleteMessageType () {
      let keys = this.$refs['messageTypeTree'].getCheckedKeys(!this.deleteRootWhenNotChild)
      if (!keys || keys.length === 0) {
        this.$fail('未选中节点')
        this.deleteMessageTypeVisible = false
        return
      }
      let deleteItem = this.$auth('post_message_type_list_delete')
      deleteItem({ids: keys}).then((res) => {
        if (res.status === 1) {
          this.$ok(res.message)
          this.deleteMessageTypeVisible = false
          this.getMessageTypeList()
          this.removeData()
        }
      })
    },
    addItem (data) {
      let messageTypeAdd = this.$auth('post_message_type_add')
      messageTypeAdd(data).then((res) => {
        if (res.status === 1) {
          this.$ok(res.message)
          this.getMessageTypeList()
          this.removeData()
        } else {
          this.$fail(res.message)
        }
      })
    },
    updateItem (data) {
      let updateItem = this.$auth('put_message_type_update')
      updateItem(deepCopy(data)).then((res) => {
        if (res.status === 1) {
          this.$ok(res.message)
          this.getMessageTypeList()
          this.removeData()
        } else {
          this.$fail(res.message)
        }
      })
    },
    removeData () {
      this.selectedNode = null
      this.dialogVisible = false
      this.id = null
    }
  }
}
</script>

<style lang="scss">
@import '~@/styles/global';
.message-container {
  position: relative;
  height: 100%;
  overflow: hidden;
    .message-content{
      height: calc(100% - 47px);
      overflow-y: auto;
        .message-type-tree{
          display: flex;
          justify-content: center;
          align-items: center;
          min-height: 50px;
          border: 1px solid $content-border-color;
          border-radius: 3px;
          transition: 0.2s;
          box-shadow: 0 0 8px 0 rgba(15, 18, 27, .6), 0 2px 4px 0 rgba(15, 18, 27, .5);
        }
        .message-type-info{
          position: absolute;
          top: 0;
          transition: top .35s ease;
          overflow-y: auto;
          .message-type-detail  {
            margin: 0 10px;
            padding: 10px;
            border: 1px solid $content-border-color;
            border-radius: 3px;
            transition: 0.2s;
            box-shadow: 0 0 8px 0 rgba(15, 18, 27, .6), 0 2px 4px 0 rgba(15, 18, 27, .5);
              .el-input__inner {
                color: #eee !important;
              }
          }
        }

    }
}

</style>
