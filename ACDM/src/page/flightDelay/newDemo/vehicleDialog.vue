<template>
  <el-dialog append-to-body title="新增车辆信息" :visible.sync="inVisible" :close-on-click-modal="false"
    @close="handleClose" width="50%" >
    <el-form :model.sync="target" label-width="120px" :rules="rules" ref="targetForm" v-if="initComplete">
      <el-form-item label-width="0px">
       <el-row>
         <el-col :span="12">
           <el-form-item label="车牌号码" prop="license">
            <el-input v-model="target.license" placeholder="请输入车牌号码" size="small" clearable></el-input>
          </el-form-item>
         </el-col>
         <el-col :span="12">
           <!-- <el-form-item label="车辆类型" prop="type">
             <el-autocomplete class="inline-input" v-model="target.type" :fetch-suggestions="querySearch" placeholder="请选择车辆类型" size="small" clearable></el-autocomplete>
          </el-form-item> -->
          <el-form-item label="车辆类型" prop="type">
            <el-select v-model="target.type" placeholder="请选择">
              <el-option
                v-for="item in carTypes"
                :key="item.value"
                :label="item.type"
                :value="item.type">
                <span style="float: left">{{ item.type }}</span>
                <span style="float: right; color: #8492a6; font-size: 13px">{{ item.num }}辆</span>
              </el-option>
            </el-select>
          </el-form-item>
         </el-col>
       </el-row>
      </el-form-item>
      <el-form-item label-width="0px">
        <el-row>
          <el-col :span="12">
           <el-form-item label="联系方式(司机)" prop="phone">
            <el-input v-model="target.phone" placeholder="请输入联系方式" size="small" clearable></el-input>
          </el-form-item>
         </el-col>
          <el-col :span="12">
            <el-form-item label="预计到位时间" prop="planArriveTime">
              <el-date-picker  v-model="target.planArriveTime" type="datetime" format="yyyy-MM-dd HH:mm" placeholder="请输入计划到位时间" size="small" clearable></el-date-picker>
            </el-form-item>
         </el-col>
       </el-row>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="inVisible = false" size="small">取 消</el-button>
      <el-button type="primary" @click="handleSubmit" :loading="loading" size="small">确 定</el-button>
    </span>
  </el-dialog>
</template>
<script>
/* import moment from 'moment' */
import { validPhone } from '@/util/rules'
export default {
  props: {
    /* callback: Function, */
    accommodationId: ''
  },
  data () {
    return {
      vehicleId: '',
      carTypes: [],
      title: '',
      target: {
        vehicleId: '',
        phone: '',
        license: '',
        planArriveTime: '',
        type: '',
        realArriveTime: ''
      },
      inVisible: false,
      rules: {
        phone: [ { required: true, message: '请输入司机联系方式', trigger: 'blur' }, { validator: validPhone, trigger: 'change' } ],
        license: [ { required: true, message: '请输入车牌号', trigger: 'blur' } ],
        planArriveTime: [ { required: true, message: '请输入预计到位时间', trigger: 'blur' } ],
        type: [ { required: true, message: '请选择车辆类型', trigger: 'change' } ]
      },
      // 初始化标志，直接销毁form组件，避免resetFields的显示错误的数据
      initComplete: false,
      updateFlag: false,
      loading: false
    }
  },
  mounted () {
    this.getCarType()
  },
  methods: {
    getCarType () {
      let getDataAjax = this.$auth('get_busTypeNum_list')
      getDataAjax().then(res => {
        this.carTypes = res.data
        this.carTypes.sort(function (a, b) {
          return -(a.num - b.num)
        })
      })
    },
    /* getCarType () {
      this.$store.dispatch('GetDic', 'CAR_TYPE').then(data => {
        this.carTypes = data['CAR_TYPE']
      })
    },
    querySearch (queryString, cb) {
      var carTypes = this.carTypes
      var results = queryString ? carTypes.filter(this.createFilter(queryString)) : carTypes
      cb(results)
    },
    createFilter (queryString) {
      return (carType) => {
        return (carType.value.toLowerCase().indexOf(queryString.toLowerCase()) !== -1)
      }
    }, */
    show (vehicleId) {
      this.target = {}
      this.target.vehicleId = vehicleId
      this.inVisible = true
      this.initComplete = true
    },
    handleClose () {
      this.initComplete = false
    },
    handleSubmit () {
      this.$refs['targetForm'].validate((valid) => {
        if (valid) {
          this.loading = true
          let postBus = this.$auth('post_delayBus_add')
          let putBusMsg = this.$auth('put_busTypeNum_update')
          /* this.target.planArriveTime = moment(this.target.planArriveTime).format() */
          let form = Object.assign({}, this.target)
          postBus(form).then((res) => {
            this.carTypes.forEach(item => {
              if (item.type === form.type) {
                item.num--
                putBusMsg(item).then(res => {
                  this.$emit('flash')
                  this.inVisible = false
                  this.loading = false
                })
              }
            })
          })
        }
      })
    }
  }
}
</script>
