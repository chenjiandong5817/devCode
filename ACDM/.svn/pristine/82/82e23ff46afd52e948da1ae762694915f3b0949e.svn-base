<template>
  <el-dialog title="" :visible.sync="visible" :close-on-click-modal="false"
    @close="handleClose" width="26%" >
    <el-form :model.sync="user" label-width="80px" :rules="rules" ref="userForm">
      <el-form-item label="用户名">
        <el-input v-model="userInfo.username" disabled size="small"></el-input>
      </el-form-item>
      <el-form-item label="旧密码" prop="oldPassword">
        <el-input type="password" v-model="user.oldPassword" placeholder="请输入旧密码" size="small"></el-input>
      </el-form-item>
      <el-form-item label="新密码" prop="password">
        <el-input type="password" v-model="user.password" placeholder="请输入新密码" size="small"></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="checkPassword">
        <el-input type="password" v-model="user.checkPassword" placeholder="请再次输入密码" size="small"></el-input>
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
// import { mapState } from 'vuex'
let defaultUser = {
  oldPassword: '',
  password: '',
  checkPassword: ''
}
export default {
  data () {
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.user.password) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    return {
      user: {},
      visible: false,
      rules: {
        oldPassword: [
          { required: true, message: '请输入旧密码', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入新密码', trigger: 'blur' },
          { min: 6, max: 32, message: '长度在 6 到 32 个字符', trigger: 'blur' }
        ],
        checkPassword: [
          { required: true, message: '请输入新密码', trigger: 'blur' },
          { validator: validatePass, trigger: 'blur' }
        ]
      },
      updateFlag: false,
      loading: false,
      userInfo: {}
    }
  },
  computed: {
    // ...mapState({
    //   userInfo: state => state.user.userInfo
    // })
  },
  methods: {
    show (userInfo) {
      this.userInfo = userInfo
      this.init()
      this.visible = true
    },
    init () {
      this.user = deepCopy(defaultUser)
    },
    handleClose () {
      this.$refs['userForm'].resetFields()
    },
    handleSubmit () {
      this.$refs['userForm'].validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.loading = true
            let ajax = this.$auth('put_user_change_pwd')
            let params = {
              id: this.userInfo.id,
              oldPassword: this.user.oldPassword,
              password: this.user.password
            }
            ajax(params).then((res) => {
              this.loading = false
              res.status && this.$ok(res.message)
              this.visible = false
            }).catch(() => {
              this.loading = false
            })
          })
        }
      })
    }
  }
}
</script>
