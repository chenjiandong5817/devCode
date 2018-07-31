<template>
  <el-dialog :title="title" :visible.sync="visible" :close-on-click-modal="false"
    @close="handleClose" width="30%">
    <el-form :model.sync="target" label-width="80px" label-position="left" :rules="rules" ref="targetForm" v-if="initComplete">

      <el-form-item label="接收时间" prop="receiveTime">
        <el-date-picker
          v-model="target.receiveTime"
          type="datetime"
          size="small"
          format="yyyy-MM-dd HH:mm"
          placeholder="选择接收时间">
        </el-date-picker>
      </el-form-item>

      <el-form-item label="消息内容">
        <el-input v-model="target.messageInfo.content" placeholder="消息内容"
        type="textarea" autosize readonly size="small"></el-input>
      </el-form-item>

      <el-form-item label="消息确认">
        <el-radio-group v-model="target.messageStatus" size="small">
          <el-radio-button label="确认"></el-radio-button>
          <el-radio-button label="取消"></el-radio-button>
        </el-radio-group>
      </el-form-item>

    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false" size="small">取 消</el-button>
      <el-button type="primary" @click="handleSubmit" :loading="loading" size="small">确 定</el-button>
    </span>
  </el-dialog>
</template>
<script>
export default {
  props: {
    callback: Function
  },
  data () {
    return {
      target: {},
      visible: false,
      title: '',
      rules: {
        planArrive: [{ required: true, message: '请输入日期', trigger: 'blur' }]
      },
      // 初始化标志，直接销毁form组件，避免resetFields的显示错误的数据
      initComplete: false,
      loading: false
    }
  },
  methods: {
    show (target) {
      this.init(target)
      this.visible = true
    },
    init (target) {
      this.target = Object.assign({}, target, {messageStatus: '确认'})
      this.title = '编辑'
      this.initComplete = true
    },
    handleClose () {
      // this.$refs['userForm'].resetFields()
      this.initComplete = false
    },
    handleSubmit () {
      this.$refs['targetForm'].validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.loading = true
            let formCopy = Object.assign({}, {
              id: this.target.id,
              messageStatus: this.target.messageStatus === '确认' ? 'confirmed' : 'canceled'
            })
            let update = this.$auth('put_message_log_update')
            update(formCopy).then((res) => {
              this.loading = false
              res.status && this.$ok(res.message)
              this.callback && this.callback()
              this.visible = false
            }).catch(() => { this.loading = false })
          }).catch(() => {})
        }
      })
    }
  }
}
</script>
