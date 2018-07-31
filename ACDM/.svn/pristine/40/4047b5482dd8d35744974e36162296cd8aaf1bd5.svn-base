<template>
<section class="formClass">
  <el-dialog :title="title" :visible.sync="visible" :close-on-click-modal="false"
    @close="handleClose" width="50%" >
    <el-form :model.sync="target" label-width="110px" label-position="right" :rules="rules" ref="targetForm" v-if="initComplete">
      <div style="position: relative; margin-bottom: 15px">
        <el-button type="primary" size="small" @click="flightChoose">航班信息</el-button>
        <span style="margin-left: 10px">你已经选择：<el-tag v-for="tag in tags" :key="tag">{{tag}}</el-tag></span>
      </div>
      <el-row>
        <el-col :span="12">
          <el-form-item style="margin-right: 30px;" label-width="80px" label="服务时间" prop="planArrive">
          <el-date-picker
            v-model="target.planArrive"
            type="datetime"
            size="small"
            format="yyyy-MM-dd HH:mm"
            placeholder="选择服务时间">
          </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="计划送达时间" prop="provideDate">
            <el-date-picker v-model="target.provideDate" type="datetime" size="small"
              format="yyyy-MM-dd HH:mm" placeholder="选择计划送达时间">
            </el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row v-if="false">
        <el-col :span="8">
          <el-form-item style="margin-right: 30px;" label-width="80px" label="承运人" prop="carrier">
            <el-input v-model="target.carrier" placeholder="请输入承运人" size="small"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="航班号" prop="flight">
            <el-input v-model="target.flight" placeholder="请输入航班号" size="small"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="航班ID" prop="flightId">
            <el-input v-model="target.flightId" placeholder="请输入航班ID" size="small"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="12">
          <el-form-item style="margin-right: 30px;" label-width="80px" label="配餐单位" prop="foodDepartment">
          <el-input v-model="target.foodDepartment" placeholder="请输入配餐单位" size="small"></el-input>
        </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="配餐类型" prop="foodType">
          <el-select v-model="target.foodType" size="small" placeholder="请选择配餐类型">
            <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value"></el-option>
          </el-select>
        </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="12">
          <el-form-item style="margin-right: 30px;" label-width="80px" label="数量" prop="foodCount">
            <el-input-number v-model="target.foodCount" :min="0" label="数量" size="small"></el-input-number>
          </el-form-item>
        </el-col>
      </el-row>

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
import { deepCopy } from '@/util/util'
import moment from 'moment'
import flightChooseDialog from '@/page/flight/flightChooseDialog/index.vue'
const defaultTarget = {
  planArrive: '',
  flightId: null,
  carrier: '',
  flight: '',
  foodDepartment: '',
  foodType: '',
  foodCount: 0,
  provideDate: ''
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
      target: {},
      visible: false,
      title: '',
      rules: {
        planArrive: [{ required: true, message: '请选择服务时间', trigger: 'blur' }],
        flightId: [{ required: true, message: '请输入航班ID', trigger: 'blur' }, { pattern: /^\d+$/, message: '只能输入数字', trigger: 'blur' }],
        carrier: [{ required: true, message: '请输入承运人', trigger: 'blur' }],
        flight: [{ required: true, message: '请输入航班号', trigger: 'blur' }],
        foodDepartment: [{ required: true, message: '请输入配餐单位', trigger: 'blur' }],
        foodType: [{ required: true, message: '请选择配餐类型', trigger: 'change' }],
        provideDate: [{ required: true, message: '请选择计划送达时间', trigger: 'blur' }]
      },
      // 初始化标志，直接销毁form组件，避免resetFields的显示错误的数据
      initComplete: false,
      updateFlag: false,
      loading: false
    }
  },
  mounted () {
    this.$store.dispatch('GetDic', 'FOODTYPE').then(data => {
      this.options = data.FOODTYPE
    })
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
    show (target, fromFlightList = false) {
      this.tags = []
      if (fromFlightList === true) {
        this.title = '新增'
        this.target = Object.assign({}, defaultTarget, target)
        this.tags.push(`${this.target.carrier}${this.target.flight}`)
        this.updateFlag = false
        this.initComplete = true
      } else {
        this.init(target)
      }
      this.visible = true
    },
    init (target) {
      this.updateFlag = Boolean(target)
      if (target) {
        this.target = deepCopy(target)
        this.tags.push(`${this.target.carrier}${this.target.flight}`)
        this.target.planArrive = moment(this.target.planArrive).toDate()
        this.target.provideDate = moment(this.target.provideDate).toDate()
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
            let formCopy = deepCopy(this.target) // 在这里拷贝一次 避免修改了el-date-picker的变量 导致element报错
            formCopy.planArrive = moment(formCopy.planArrive).valueOf()
            formCopy.provideDate = moment(formCopy.provideDate).valueOf()
            // 加入消息参数
            this.$store.dispatch('PushMessageParams', {
              flightId: formCopy.flightId,
              carrier: formCopy.carrier,
              flight: formCopy.flight
            })
            // 以上 加入消息参数
            let update = this.$auth('put_providefood_update')
            let add = this.$auth('post_providefood_add')
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
