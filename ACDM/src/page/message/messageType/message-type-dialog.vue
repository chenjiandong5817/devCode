<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogVisible"
    width="400px"
    @close="close"
    :close-on-click-modal="false">
    <el-form :model.sync="form" label-position="right" ref="messageTypeForm" :rules="rules">
      <el-form-item>
        <el-row>
          <el-col :span="24">
            <el-form-item label="父级"  :label-width="formLabelWidth" >
                <el-input  :value="parent.name"  size="small" disabled ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form-item>
      <el-form-item>
        <el-row>
          <el-col :span="24">
            <el-form-item label="消息类型" :label-width="formLabelWidth" prop="name">
              <el-input  v-model="form.name" placeholder="请输入消息类型" size="small"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form-item>
      <el-form-item>
        <el-row>
          <el-col :span="24">
            <el-form-item label="消息编码" :label-width="formLabelWidth" prop="code">
              <el-input  v-model="form.code" placeholder="请输入消息编码" size="small"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="close" size="small">取 消</el-button>
      <el-button type="primary" @click="handleSubmit" size="small">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { deepCopy } from '@/util/util'
import { validLegalName } from '@/util/rules'
const defaultForm = {
  parentId: null,
  remark: '',
  name: '',
  code: ''
}
export default {
  props: {
    openFlag: {
      type: Boolean,
      default: false
    },
    id: {
      type: String,
      default: ''
    },
    parent: {
      type: Object,
      default: () => {
        return {
          id: '',
          name: ''
        }
      }
    }
  },
  data () {
    return {
      formLabelWidth: '80px',
      rules: {
        name: [{ required: true, message: '请输入消息类型', trigger: 'blur' }],
        code: [
          { required: true, message: '请输入消息编码', trigger: 'blur' },
          { validator: validLegalName, trigger: 'blur' }
        ]
      },
      form: {},
      addFlag: true,
      dialogVisible: false
    }
  },
  computed: {
    title () {
      return !this.addFlag ? '编辑消息类型' : '新增消息类型'
    }
  },
  watch: {
    openFlag () {
      this.dialogVisible = this.openFlag
    }
  },
  methods: {
    toAdd () {
      this.addFlag = true
      this.form = deepCopy(defaultForm)
    },
    toUpdate (form) {
      this.addFlag = false
      this.form = deepCopy(form || defaultForm)
    },
    close () {
      this.$emit('close', false)
      this.$refs['messageTypeForm'].resetFields()
    },
    handleSubmit () {
      this.$refs['messageTypeForm'].validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {})
            .then(() => {
              this.addFlag ? this.addItem() : this.updateItem()
            })
            .catch(() => {})
        }
      })
    },
    addItem () {
      const newChild = { name: this.form.name, parentId: this.parent.id, code: this.form.code }
      this.$emit('addItem', newChild)
    },
    updateItem () {
      this.$emit('updateItem', this.form)
    }
  }
}
</script>
