<template>
  <el-dialog :visible.sync="visible" title="新增控件" :append-to-body="true" :close-on-click-modal="false"
    @close="handleClose" width="40%" top="20vh" class="menu-control-dialog">
    <span class="err-title" v-if="disabled"><i class="fa fa-exclamation"/> &nbsp;未选择菜单无法继续操作...</span>
    <el-form :model.sync="menuControl" label-width="80px" :rules="rules" ref="menuControlForm" v-if="initComplete">
      <el-form-item label-width="0px">
       <el-row>
         <el-col :span="12">
          <el-form-item label="编码" prop="code">
            <el-input v-model="menuControl.code" placeholder="请输入编码" size="small"></el-input>
          </el-form-item>
         </el-col>
         <el-col :span="12">
           <el-form-item label="名称" prop="name">
            <el-input v-model="menuControl.name" placeholder="请输入名称" size="small"></el-input>
          </el-form-item>
         </el-col>
       </el-row>
      </el-form-item>
      <el-form-item label-width="0px">
       <el-row>
         <el-col :span="12">
           <el-form-item label="资源类型" prop="type">
            <el-select v-model="menuControl.type" clearable placeholder="请选择" size="small">
              <el-option
                v-for="item in resourceTypes"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
         </el-col>
         <el-col :span="12">
           <el-form-item label="请求方式" prop="method">
            <el-select v-model="menuControl.method" clearable placeholder="请选择" size="small">
              <el-option
                v-for="item in httpMethods"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
         </el-col>
       </el-row>
      </el-form-item>
      <el-form-item label="资源路径" prop="uri">
        <el-input v-model="menuControl.uri" placeholder="请输入资源路径" size="small"></el-input>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 4}" v-model="menuControl.remark" placeholder="请输入备注" size="small"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false" size="small">取 消</el-button>
      <el-button type="primary" @click="handleSubmit" :loading="loading" :disabled="disabled" size="small">确 定</el-button>
    </span>
  </el-dialog>
</template>
<script>
import {validResouceUri, validLegalName} from '@/util/rules'
export default {
  props: {
    callback: Function
  },
  data () {
    return {
      loading: false,
      visible: false,
      initComplete: false,
      disabled: false,
      menuControl: {
        type: 'RESOURCE' // 这个属性暂时没用
      },
      rules: {
        code: [
          { required: true, message: '请输入控件编码', trigger: 'blur' },
          { validator: validLegalName, trigger: 'blur' },
          { min: 4, max: 64, message: '长度在 4 到 64 个字符', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '请输入控件名称', trigger: 'blur' }
        ],
        type: [
          { required: true, message: '请选择控件类型', trigger: 'change' }
        ],
        method: [
          { required: true, message: '请选择请求方式', trigger: 'change' }
        ],
        uri: [
          { required: true, message: '请输入资源路径', trigger: 'blur' },
          { validator: validResouceUri, trigger: 'blur' }
        ]
      },
      resourceTypes: [],
      httpMethods: []
    }
  },
  computed: {
    menu () {
      return this.$parent.menu
    }
  },
  mounted () {
    this.getSelectorParams()
  },
  methods: {
    show () {
      this.visible = true
      this.initComplete = true
      if (!this.menu || !this.menu.id) {
        this.disabled = true
      }
    },
    handleSubmit () {
      this.$refs['menuControlForm'].validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.loading = true
            let addMenuControl = this.$auth('post_menu_control_add')
            addMenuControl(Object.assign({}, this.menuControl, {menuId: this.menu.id})).then((res) => {
              this.loading = false
              res.status && this.$ok(res.message)
              this.callback && this.callback()
              this.visible = false
            }).catch(() => {
              this.loading = false
              this.visible = false
            })
          })
        }
      })
    },
    handleClose () {
      this.$refs['menuControlForm'].resetFields()
    },
    getSelectorParams () {
      this.$store.dispatch('GetDic', ['RESOURCE_TYPE', 'HTTP_TYPE']).then(data => {
        this.resourceTypes = data['RESOURCE_TYPE']
        this.httpMethods = data['HTTP_TYPE']
      })
    }
  }
}
</script>
<style lang="scss">
.menu-control-dialog {
  .err-title {
    color: #ff0000;
  }
}
</style>
