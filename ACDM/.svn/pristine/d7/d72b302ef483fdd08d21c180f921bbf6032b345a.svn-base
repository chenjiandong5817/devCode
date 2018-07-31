<template>
  <el-dialog title="" :visible.sync="visible" :close-on-click-modal="false"
    @close="handleClose" width="50%" >
    <el-form :model.sync="user" label-width="80px" :rules="rules" ref="userForm" v-if="initComplete">
      <el-form-item label-width="0px">
       <el-row>
         <el-col :span="12">
           <el-form-item label="用户名" prop="username">
            <el-input v-model="user.username" :disabled="updateFlag" placeholder="请输入用户名" size="small"></el-input>
          </el-form-item>
         </el-col>
         <el-col :span="12">
           <el-form-item label="姓名" prop="name">
            <el-input v-model="user.name" placeholder="请输入姓名" size="small"></el-input>
          </el-form-item>
         </el-col>
       </el-row>
      </el-form-item>
      <el-form-item label="密码" prop="password" v-if="!updateFlag">
        <el-input type="password" v-model="user.password" placeholder="请输入密码" size="small"></el-input>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 4}" v-model="user.remark" placeholder="请输入备注" size="small"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false" size="small">取 消</el-button>
      <el-button type="primary" @click="handleSubmit" :loading="loading" size="small">确 定</el-button>
    </span>
  </el-dialog>
</template>
<script>
import { deepCopy } from '@/util/util'
const defaultUser = {
  username: '',
  password: '',
  name: '',
  remark: ''
}
export default {
  props: {
    callback: Function
  },
  data () {
    return {
      user: {},
      visible: false,
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '请输入姓名', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 32, message: '长度在 6 到 32 个字符', trigger: 'blur' }
        ]
      },
      // 初始化标志，直接销毁form组件，避免resetFields的显示错误的数据
      initComplete: false,
      updateFlag: false,
      loading: false
    }
  },
  methods: {
    show (user) {
      this.init(user)
      this.visible = true
    },
    init (user) {
      this.updateFlag = Boolean(user)
      this.user = deepCopy(user || defaultUser)
      this.initComplete = true
    },
    handleClose () {
      // this.$refs['userForm'].resetFields()
      this.initComplete = false
    },
    handleSubmit () {
      this.$refs['userForm'].validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.loading = true
            let addAjax = this.$auth('post_user_add')
            let updateAjax = this.$auth('put_user_update')
            let doOperate = this.updateFlag ? updateAjax : addAjax
            doOperate(deepCopy(this.user)).then((res) => {
              this.loading = false
              res.status && this.$ok(res.message)
              this.callback && this.callback()
              this.visible = false
            })
          }).catch(() => {})
        }
      })
    }
  }
}
</script>
