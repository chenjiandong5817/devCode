<template>
  <div>
    <el-dialog :visible.sync="dialogVisible" :title="title" @close="handleClose" width="400px" :close-on-click-modal="false">
      <el-form :model.sync="form" label-width="80px" :rules="rules" ref="serviceProgressForm">
        <el-form-item label="进度" prop="progress">
          <el-select v-model="form.progress" placeholder="请选择" allow-create filterable @change="change">
            <el-option
            v-for="item in progress"
            :key="item"
            :label="item"
            :value="item"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="名称" prop="title">
          <el-input v-model="form.title" placeholder="请输入名称" :readOnly="addClass?true:false"></el-input>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input type="textarea" v-model="form.description" placeholder="请输入描述" :readOnly="addClass?true:false"></el-input>
        </el-form-item>
        <el-form-item label="角色" prop="roleCode">
          <el-select v-model="form.roleCode" placeholder="请选择">
            <el-option
            v-for="item in roleAll"
            :key="item.code"
            :label="item.name"
            :value="item.code">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="可读" prop="readable">
          <el-radio-group v-model="form.readable">
            <el-radio :label="false"  size="small">否</el-radio>
            <el-radio :label="true"  size="small">是</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="可写" prop="writeable">
          <el-radio-group v-model="form.writeable">
            <el-radio :label="false"  size="small">否</el-radio>
            <el-radio :label="true"  size="small">是</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="handleClose" size="small">取 消</el-button>
        <el-button type="primary" @click="handleSubmit" size="small">确 定</el-button>
    </span>
    </el-dialog>
  </div>
</template>
<script>
import _ from 'lodash'
import { deepCopy } from '@/util/util'
import { mapGetters } from 'vuex'
const defaultForm = {
  progress: '',
  readable: false,
  roleCode: '',
  writeable: false,
  id: '',
  serviceType: '',
  title: '',
  description: ''
}
export default {
  props: {
    list: {
      type: Array,
      default: () => []
    },
    show: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      form: {},
      dialogVisible: this.show,
      title: '',
      roleList: [],
      rules: {
        progress: [{ required: true, message: '请选择进度', trigger: 'blur' }],
        roleCode: [{ required: true, message: '请选择角色', trigger: 'blur' }],
        title: [{ required: true, message: '请输入名称', trigger: 'blur' }],
        description: [{ required: true, message: '请输入描述', trigger: 'blur' }]
      },
      addClass: true
    }
  },
  methods: {
    change (val) {
      let c = parseInt(val)
      if (this.progress.indexOf(c) < 0) { // 不存在时
        this.addClass = false
        this.form.title = ''
        this.form.description = ''
      } else {
        this.addClass = true
        this.list.forEach(ele => {
          if (ele.progress === c) {
            this.form.title = ele.title
            this.form.description = ele.description
          }
        })
      }
    },
    handleClose () {
      this.$emit('update:show', false)
      this.$refs['serviceProgressForm'].resetFields()
      this.addClass = false
    },
    handleSubmit () {
      this.$refs['serviceProgressForm'].validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {})
            .then(() => {
              this.$emit('commitData', this.form)
            })
            .catch(() => {})
        }
      })
    },
    toUpdate (form) {
      this.title = '编辑'
      this.form = deepCopy(form || defaultForm)
    },
    toAdd () {
      this.title = '新增'
      this.form = deepCopy(defaultForm)
    }
  },
  computed: {
    ...mapGetters(['roleAll']),
    progress () {
      let result = []
      _.forEach(this.list, (item, index) => {
        result.push(item.progress)
      })
      result = _.uniq(result)
      return result
    }
  },
  watch: {
    show () {
      this.dialogVisible = this.show
    }
  }
}
</script>

<style>
.classBlock {
  display: block
}
.classNone {
  display: none
}
</style>
