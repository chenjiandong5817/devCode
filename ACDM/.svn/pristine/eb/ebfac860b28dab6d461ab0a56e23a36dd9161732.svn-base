<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogVisible"
    width="400px"
    @close="close"
    :close-on-click-modal="false">
    <el-form :model.sync="form" label-position="right" ref="foodTypeForm" :rules="rules">
      <el-form-item>
        <el-row>
          <el-col :span="24">
            <el-form-item label="父级"  :label-width="formLabelWidth" >
                <el-input  :value="parent.foodName"  size="small" disabled ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form-item>
       <el-form-item>
        <el-row>
          <el-col :span="24">
            <el-form-item label="检测冲突" prop="needConflictDetect" :label-width="formLabelWidth" >
              <el-radio-group v-model="form.needConflictDetect">
                <el-radio :label="0"  size="small">否</el-radio>
                <el-radio :label="1"  size="small">是</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form-item>
      <el-form-item>
        <el-row>
          <el-col :span="24">
            <el-form-item label="配餐类型" :label-width="formLabelWidth" prop="foodName">
              <el-input  v-model="form.foodName" placeholder="请输入配餐类型" size="small"></el-input>
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
const defaultForm = {
  parentId: null,
  remark: '',
  foodName: '',
  canBeFilled: 1,
  needConflictDetect: 0
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
          foodName: ''
        }
      }
    }
  },
  data () {
    return {
      formLabelWidth: '80px',
      rules: {
        foodName: [{ required: true, message: '请输入配餐类型', trigger: 'blur' }]
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
      this.$refs['foodTypeForm'].resetFields()
    },
    handleSubmit () {
      this.$refs['foodTypeForm'].validate((valid) => {
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
      const newChild = { foodName: this.form.foodName, mainTypeId: this.parent.id, needConflictDetect: this.form.needConflictDetect }
      this.$emit('addItem', newChild)
    },
    updateItem () {
      this.$emit('updateItem', this.form)
    }
  }
}
</script>
