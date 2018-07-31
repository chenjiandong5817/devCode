<template>
  <el-dialog :visible.sync="dialogvisible" width="300px" :close-on-click-modal="false" title="消息订阅"
  custom-class="role-message-type-container" @close = "close" >
      <el-row>
          <el-col :span="24">
              <div class="message-type-auth-div">
                  <el-tree  :data="data1"
                            :props="defaultProps"
                            node-key="id"
                            show-checkbox
                            default-expand-all
                            :expand-on-click-node="false"
                            ref="MessageTypeTree"
                            v-loading="loading"
                            ></el-tree>
              </div>
           </el-col>
      </el-row>
    <span slot="footer" class="dialog-footer">
       <el-button  @click="close" size="small" >取消</el-button>
       <el-button type="primary"  @click="handleSave" size="small" v-auth = "'put_message_type_list_update'">确定</el-button>
    </span>
    </el-dialog>
</template>

<script>
export default {
  data () {
    return {
      data1: [],
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      currentKeys: [],
      loading: false,
      dialogvisible: false
    }
  },
  methods: {
    show (id) {
      this.dialogvisible = true
      this.loading = true
      this.getMessageTypeList()
      this.getMessageTypeAuthList(id)
    },
    getMessageTypeList () {
      let getlist = this.$auth('get_message_type_list')
      if (getlist) {
        getlist().then(res => {
          if (res.status === 1) {
            this.data1 = res.data
          }
        })
      }
    },
    close () {
      this.dialogvisible = false
    },
    handleSave () {
      let keys = this.$refs['MessageTypeTree'].getCheckedKeys(true)
      this.$emit('saveMessageTypeAuth', keys)
    },
    getMessageTypeAuthList (id) {
      let listAjax = this.$auth('get_role_message_type_list_by_id')
      listAjax({roleId: id}).then((res) => {
        if (res.status === 1) {
          let item = res.data
          this.currentKeys = []
          if (item) {
            this.getArray(item.meta.messageTypes)
          }
          this.$refs['MessageTypeTree'].setCheckedKeys(this.currentKeys)
          this.loading = false
        }
      })
    },
    getArray (data) {
      for (var i in data) {
        this.currentKeys.push(data[i].id)
        if (data[i].children && data[i].children.length > 0) {
          this.getArray(data[i].children)
        } else {
          continue
        }
      }
    }
  }
}
</script>
<style>
.message-type-auth-div {
  padding: 10px;
}
.org-detail .el-input__inner {
    color: #1f1f1f !important;
  }
.buttonDiv{
    text-align: center;
    margin-top: 20px;
  }
.role-message-type-container .el-dialog__body {
    padding-top: 0;
}
</style>
