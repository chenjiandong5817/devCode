<template>
  <el-dialog :visible.sync="dialogvisible" :append-to-body="true" width="40%" top="20vh" :close-on-click-modal="true" title="绑定消息"
    custom-class="control-message-type-container" @close="close" >
    <el-row>
      <el-col :span="24">
        <div>
          <el-tree
            :data="messageTypeAll"
            :props="defaultProps"
            node-key="id"
            show-checkbox
            default-expand-all
            :expand-on-click-node="false"
            ref="messageTypeTree"
            v-loading="loading"
          >
            <span class="custom-tree-node" slot-scope="{ node, data }">
              <span>{{ node.label }}</span>
              <span v-if="!data.children || data.children.length === 0">
                <span class="content-tip" v-if="isEmptyContent(data.id)">未配置文本</span>
                <el-button type="text" size="mini" @click="() => handleSetNodeOptions(data.id)">
                  设置参数
                </el-button>
              </span>
            </span>
          </el-tree>
        </div>
      </el-col>
    </el-row>
    <span slot="footer" class="dialog-footer">
       <el-button  @click="close" size="small" >关闭</el-button>
       <el-button type="primary"  @click="handleSave" size="small">确定</el-button>
    </span>
    <message-text ref="messageText"></message-text>
  </el-dialog>
</template>

<script>
import MessageText from './control-message-text-dialog'
import { mapGetters } from 'vuex'
export default {
  components: {
    MessageText
  },
  data () {
    return {
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      loading: false,
      dialogvisible: false,
      menuControl: null,
      // 每个类型设置一个文本和ack
      nodeOptions: {}
    }
  },
  computed: {
    ...mapGetters(['messageTypeAll']),
    checkedMessageTypeIds () {
      let array = []
      if (this.menuControl) {
        array = this.menuControl.meta.messageTypes
      }
      return array.map(item => item.messageType.id)
    }
  },
  watch: {
    menuControl: {
      deep: true,
      handler (val) {
        this.nodeOptions = {}
        if (!val) {
          return
        }
        let array = val.meta.messageTypes
        array.forEach(item => {
          this.nodeOptions[item.messageType.id] = {
            content: item.content,
            ack: item.ack
          }
        })
      }
    }
  },
  mounted () {
  },
  methods: {
    // 显示dialog
    show (control) {
      this.dialogvisible = true
      this.menuControl = control
      this.$nextTick(() => {
        this.setDefaultChecked()
      })
    },
    // 关闭dialog
    close () {
      this.dialogvisible = false
      this.menuControl = null
    },
    // 根据controlId获取MessageType列表
    getMessageTypes () {

    },
    setDefaultChecked () {
      this.$refs['messageTypeTree'].setCheckedKeys(this.checkedMessageTypeIds)
    },
    getOptionsProperty (id, key) {
      let target = this.nodeOptions[id]
      if (target) {
        return target[key]
      }
      return undefined
    },
    setOptionsProperty (id, key, value) {
      if (!this.nodeOptions[id]) {
        this.nodeOptions[id] = {}
      }
      this.nodeOptions[id][key] = value
    },
    // 确认事件
    handleSave () {
      let nodes = this.$refs['messageTypeTree'].getCheckedNodes(true)
      let options = nodes.map(node => {
        let content = this.getOptionsProperty(node.id, 'content')
        let ack = this.getOptionsProperty(node.id, 'ack')
        if (content === undefined) {
          content = ''
          this.setOptionsProperty(node.id, 'content', content)
        }
        if (ack === undefined) {
          ack = 1
          this.setOptionsProperty(node.id, 'ack', ack)
        }
        return {
          msgTypeId: node.id,
          content: content,
          ack: ack
        }
      })
      let ajax = this.$auth('put_mune_control_msgtype')
      if (ajax) {
        let params = {
          menuControlId: this.menuControl.id,
          list: options
        }
        ajax(params).then(res => {
          res.status && this.$ok(res.message)
          // 修改到this.menuControl上，只有刷新浏览器才从后端同步
          this.menuControl.meta.messageTypes = nodes.map(item => {
            return {
              messageType: item,
              content: this.getOptionsProperty(item.id, 'content'),
              ack: this.getOptionsProperty(item.id, 'ack')
            }
          })
          // 修改到角色所拥有的menuControl，只有刷新浏览器模块才从后端同步
          console.log('menuControl', this.menuControl)
          this.$store.dispatch('BindControlMessageType', this.menuControl)
        })
      }
    },
    isEmptyContent (key) {
      return this.getOptionsProperty(key, 'content') === undefined
    },
    // 设置文本
    handleSetNodeOptions (key) {
      if (!this.nodeOptions[key]) {
        this.nodeOptions[key] = {content: '', ack: 1}
      }
      this.$refs['messageText'].show(this.nodeOptions[key])
    }
  }
}
</script>
<style lang="scss">
.control-message-type-container {
  min-width: 400px;
  .el-dialog__body {
    padding-top: 0px !important;
  }
  .custom-tree-node {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: space-between;
    font-size: 14px;
    padding-right: 8px;
    .content-tip {
      color: #cccccc;
      font-size: 12px;
      font-style: italic;
      user-select: none;
    }
  }
}
</style>
