<template>
<section class="formClass">
  <el-dialog :title="title" :visible.sync="visible" :close-on-click-modal="false"
    @close="handleClose" width="30%">
    <el-form :model.sync="target" label-width="80px" label-position="left" :rules="rules" ref="targetForm" v-if="initComplete">
      <div style="position: relative; margin-bottom: 15px">
        <el-button type="primary" size="small" @click="flightChoose">航班信息</el-button>
        <span style="margin-left: 10px">你已经选择：<el-tag v-for="tag in tags" :key="tag">{{tag}}</el-tag></span>
      </div>

      <div v-if="false">
        <el-form-item label="航班ID" prop="flightId">
          <el-input v-model="target.flightId" placeholder="请输入航班ID" size="small"></el-input>
        </el-form-item>

        <el-form-item label="航班号" prop="carrier">
          <el-col :span="8">
            <el-form-item prop="carrier">
              <el-input v-model="target.carrier" placeholder="航空公司" size="small"></el-input>
            </el-form-item>
          </el-col>
          <el-col style="text-align: center" :span="2">-</el-col>
          <el-col :span="14">
            <el-form-item prop="flight">
              <el-input v-model="target.flight" placeholder="航班号" size="small"></el-input>
            </el-form-item>
          </el-col>
        </el-form-item>
      </div>

      <el-form-item label="消息类型" prop="typeCode">
        <el-cascader
          expand-trigger="hover"
          :options="options"
          :props="props"
          v-model="target.typeCode"
          size="small"
          placeholder="请选择消息类型"
          style="width: 100%">
        </el-cascader>
      </el-form-item>

      <el-form-item label="消息内容" prop="content">
        <el-input v-model="target.content" placeholder="请输入消息内容"
        type="textarea" autosize size="small"></el-input>
      </el-form-item>

      <el-form-item label="是否确认" prop="whetherConfirm">
        <el-radio-group v-model="target.whetherConfirm">
          <el-radio :label="1" border size="small">是</el-radio>
          <el-radio :label="0" border size="small">否</el-radio>
        </el-radio-group>
      </el-form-item>

    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false" size="small">取 消</el-button>
      <el-button type="primary" @click="handleSubmit" :loading="loading" size="small">确 定</el-button>
    </span>
  </el-dialog>

  <flight-choose-dialog :show.sync="showFlight" @dbClick="dbClick"></flight-choose-dialog>
</section>
</template>
<script>
import flightChooseDialog from '@/page/flight/flightChooseDialog/index.vue'
import { deepCopy } from '@/util/util'
import { validUpperCase } from '@/util/rules'
const defaultTarget = {
  flightId: '',
  carrier: '',
  flight: '',
  typeCode: [],
  content: '',
  whetherConfirm: 1
}
export default {
  props: {
    callback: Function
  },
  components: {
    flightChooseDialog
  },
  data () {
    return {
      tags: [],
      showFlight: false,
      options: [],
      props: {
        value: 'code',
        label: 'name',
        children: 'children'
      },
      target: {},
      visible: false,
      title: '',
      rules: {
        flightId: [{ required: true, message: '请输入航班ID', trigger: 'blur' }],
        carrier: [{ required: true, validator: validUpperCase, trigger: 'blur' }],
        flight: [{ required: true, message: '请输入航班号', trigger: 'blur' }],
        typeCode: [{ required: true, message: '请选择消息类型', trigger: 'change' }],
        content: [{ required: true, message: '请输入消息内容', trigger: 'blur' }]
      },
      // 初始化标志，直接销毁form组件，避免resetFields的显示错误的数据
      initComplete: false,
      updateFlag: false,
      loading: false
    }
  },
  methods: {
    dbClick (data) {
      this.target.flightId = data.flightId
      this.target.flight = data.flight
      this.target.carrier = data.carrier
      this.tags = []
      this.tags.push(`${this.target.carrier}${this.target.flight}`)
    },
    flightChoose () {
      this.tags = []
      this.showFlight = true
    },
    show (target, options) {
      this.tags = []
      this.options = options
      this.init(target)
      this.visible = true
    },
    init (target) {
      this.updateFlag = Boolean(target)
      if (target) {
        let temp = deepCopy(target)
        temp.typeCode = temp.typeCode.split('.')
        this.target = temp
        this.tags.push(`${this.target.carrier}${this.target.flight}`)
        this.title = '编辑'
      } else {
        this.target = deepCopy(defaultTarget)
        this.title = '新增'
      }
      this.initComplete = true
    },
    handleClose () {
      // this.$refs['userForm'].resetFields()
      this.initComplete = false
    },
    handleSubmit () {
      if (this.tags.length === 0) {
        this.$message({type: 'error', message: '请先选择航班'})
        return
      }
      this.$refs['targetForm'].validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.loading = true
            let formCopy = Object.assign({}, this.target)
            formCopy.typeCode = formCopy.typeCode.join('.')
            let update = this.$auth('put_messageInfo_update')
            let add = this.$auth('post_messageInfo_add')
            let doOperate = this.updateFlag ? update : add
            doOperate(formCopy).then((res) => {
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
