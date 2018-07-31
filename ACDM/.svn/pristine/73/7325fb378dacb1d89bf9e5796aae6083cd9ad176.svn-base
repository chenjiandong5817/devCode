<template>
  <el-dialog :title="updateFlag ? '编辑' : '新增'" :visible.sync="visible" :close-on-click-modal="false"
    @close="handleClose" width="40%" >
    <el-form :model.sync="menu" label-width="55px" :rules="rules" ref="menuForm" v-if="initComplete">
      <el-form-item label-width="0px">
       <el-row>
         <el-col :span="12">
          <el-form-item label="父级" prop="parentId">
            <el-select v-model="menu.parentId" clearable placeholder="请选择" size="small">
              <el-option
                v-for="item in menuAll"
                :key="item.id"
                :label="item.label"
                :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
         </el-col>
         <el-col :span="12">
           <el-form-item label="序号" prop="orderNum">
            <el-input-number v-model="menu.orderNum" :min="1" :max="99" label="排列序号" size="small"></el-input-number>
          </el-form-item>
         </el-col>
       </el-row>
      </el-form-item>
      <el-form-item label-width="0px">
       <el-row>
         <el-col :span="12">
           <el-form-item label="标题" prop="label">
            <el-input v-model="menu.label" placeholder="请输入菜单标题" size="small"></el-input>
          </el-form-item>
         </el-col>
         <el-col :span="12">
           <el-form-item label="编码" prop="code">
            <el-input v-model="menu.code" placeholder="请输入菜单编码" size="small"></el-input>
          </el-form-item>
         </el-col>
       </el-row>
      </el-form-item>
      <el-form-item label-width="0px">
       <el-row>
         <el-col :span="12">
           <el-form-item label="路径" prop="href">
            <el-input v-model="menu.href" placeholder="请输入菜单路径" size="small"></el-input>
          </el-form-item>
         </el-col>
         <el-col :span="12">
           <el-form-item label="图标" prop="icon">
            <el-input v-model="menu.icon" placeholder="请输入菜单图标" size="small"></el-input>
          </el-form-item>
         </el-col>
       </el-row>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 4}" v-model="menu.remark" placeholder="请输入备注" size="small"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false" size="small">取 消</el-button>
      <el-button type="primary" @click="handleSubmit" :loading="loading" size="small">确 定</el-button>
    </span>
  </el-dialog>
</template>
<script>
import { mapGetters } from 'vuex'
import { deepCopy } from '@/util/util'
import { validResouceUri, validLegalName } from '@/util/rules'
const defaultMenu = {
  parentId: '',
  orderNum: 0,
  label: '',
  code: '',
  href: '/',
  icon: '',
  remark: ''
}
export default {
  props: {
    callback: Function
  },
  data () {
    return {
      menu: {},
      visible: false,
      rules: {
        label: [
          { required: true, message: '请输入菜单标题', trigger: 'blur' }
        ],
        code: [
          { required: true, message: '请输入菜单编码', trigger: 'blur' },
          { validator: validLegalName, trigger: 'blur' }
        ],
        href: [
          { required: true, message: '请输入菜单路径', trigger: 'blur' },
          { validator: validResouceUri, trigger: 'blur' }
        ]
      },
      initComplete: false,
      updateFlag: false,
      loading: false
    }
  },
  computed: {
    ...mapGetters(['menuAll'])
  },
  methods: {
    toUpdate (menu) {
      this.init(menu)
      this.visible = true
    },
    toAdd (parent) {
      this.init()
      if (parent && parent.id) {
        this.menu.parentId = parent.id
      }
      this.visible = true
    },
    init (menu) {
      this.updateFlag = Boolean(menu)
      this.menu = deepCopy(menu || defaultMenu)
      this.initComplete = true
    },
    handleClose () {
      // this.$refs['menuForm'].resetFields()
      setTimeout(() => {
        this.initComplete = false
      }, 355)
    },
    handleSubmit () {
      this.$refs['menuForm'].validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.loading = true
            let updateMenu = this.$auth('put_menu_update')
            let addMenu = this.$auth('post_menu_add')
            let doOperate = this.updateFlag ? updateMenu : addMenu
            doOperate(deepCopy(this.menu)).then((res) => {
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
