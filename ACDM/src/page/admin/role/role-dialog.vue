<template>
  <el-dialog title="" :visible.sync="visible" :close-on-click-modal="false"
    @close="handleClose" width="50%" >
    <el-form :model.sync="target" label-width="80px" :rules="rules" ref="targetForm" v-if="initComplete">
      <el-form-item label-width="0px">
       <el-row>
         <el-col :span="12">
           <el-form-item label="角色编码" prop="code">
            <el-input v-model="target.code" placeholder="请输入用户名" size="small" :disabled="updateFlag"></el-input>
          </el-form-item>
         </el-col>
         <el-col :span="12">
           <el-form-item label="名称" prop="name">
            <el-input v-model="target.name" placeholder="请输入名称" size="small"></el-input>
          </el-form-item>
         </el-col>
       </el-row>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input type="textarea" v-model="target.remark" :autosize="{ minRows: 2, maxRows: 4}" placeholder="请输入备注" size="small"></el-input>
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
import { validLegalName } from '@/util/rules'
const defaultTarget = {
  code: '',
  name: '',
  remark: ''
}
export default {
  props: {
    callback: Function
  },
  data () {
    return {
      target: {},
      visible: false,
      rules: {
        code: [
          { required: true, message: '请输入角色编码', trigger: 'blur' },
          { validator: validLegalName, trigger: 'blur' }
        ],
        name: [
          { required: true, message: '请输入名称', trigger: 'blur' }
        ]
      },
      // 初始化标志，直接销毁form组件，避免resetFields的显示错误的数据
      initComplete: false,
      updateFlag: false,
      loading: false
    }
  },
  methods: {
    show (target) {
      this.init(target)
      this.visible = true
    },
    init (target) {
      this.updateFlag = Boolean(target)
      this.target = deepCopy(target || defaultTarget)
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
            let updateRole = this.$auth('put_role_update')
            let addRole = this.$auth('post_role_add')
            let doOperate = this.updateFlag ? updateRole : addRole
            doOperate(deepCopy(this.target)).then((res) => {
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
