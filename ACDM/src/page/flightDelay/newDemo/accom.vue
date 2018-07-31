<template>
  <section>
    <div v-if="readable" class="accom-service-container">
      <div style="margin-bottom: 5px;">
        <el-tag>{{ serviceFlight }}</el-tag>
      </div>
      <div class="accom-service-tags">
        <template v-for="(item, index) in accomServer">
          <el-tag  :key="index" @click.native="accomServerChange(item)"
            :class="['service-tag', {'is-active': item.id === accomTarget.id}]"
            :type="item.progress === hotelRoles.length ? 'success' : (item.iscancel ? 'danger' : '')">
              <span>{{ item.hotels[0].hotelName ? convertHotelName(item.hotels[0].hotelName) : '' }}</span>
              <span>{{ moment(item.beginTime).format('HH:mm') }}</span>
              <span>
                {{item.progress === hotelRoles.length ? '-已完成' : (item.iscancel ? '-已取消' : '')}}
              </span>
          </el-tag>
        </template>
      </div>
      <div style="display:flex">
        <el-card style="flex-grow: 1">
          <el-form :rules="accmRules" :model.sync="accomTarget" label-width="80px" ref="accomTarget">
          <h4 style="text-align:center">{{hotelRoles[0] ? hotelRoles[0].title : ''}}</h4>
            <el-form-item label-width="0px">
              <el-row type="flex" class="row-bg" justify="space-around">
                <el-col :span="6">
                  <el-form-item label="发起时间" prop="beginTime">
                    <el-date-picker :disabled="!(writable && isCurrentProgressNode(0))" v-model="accomTarget.beginTime" type="datetime" format="yyyy-MM-dd HH:mm" placeholder="请输入服务时间" size="small" clearable></el-date-picker>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label="高端旅客数量" label-width="110px" prop="vipPassengers">
                    <el-input-number :disabled="!(writable && isCurrentProgressNode(0))" v-model="accomTarget.vipPassengers" size="small" :min="0" clearable></el-input-number>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row type="flex" class="row-bg" justify="space-around">
                <el-col :span="6">
                  <el-form-item label="选择酒店" label-width="80px" prop="hotelName">
                    <el-select  filterable  :disabled="!(writable && isCurrentProgressNode(0))" v-model="accomTarget.hotelName"  placeholder="请选择酒店" size="small" clearable>
                      <el-option
                        v-for="item in hotelOptions"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                      </el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label="普通旅客数量" label-width="110px" prop="ordinaryPassengers">
                    <el-input-number :disabled="!(writable && isCurrentProgressNode(0))" v-model="accomTarget.ordinaryPassengers" size="small" :min="0" clearable></el-input-number>
                  </el-form-item>
                </el-col>
              </el-row>
            </el-form-item>
            <div style="display: flex;justify-content: flex-end;margin-bottom: 10px;">
              <el-button type="primary" icon="el-icon-circle-close" v-if="isCancelable" size="small" @click="cancelService()">取消服务</el-button>
              <el-button type="primary" v-if="writable && isCurrentProgressNode(0)" :loading="accmBtnLoading" @click="accmSubmit" size="small">下一步</el-button>
            </div>
          </el-form>
          <div style="border-top: 1px solid black; margin-bottom: 15px"></div>
          <el-form :rules="hotelRules" :model.sync="hotelTarget" label-width="120px" ref="hotelTarget">
            <h4 style="text-align:center">{{hotelRoles[1] ? hotelRoles[1].title : ''}}</h4>
            <el-form-item label-width="0px">
            <el-row type="flex" class="row-bg" justify="space-around">
              <el-col :span="6">
                <el-form-item label="高端旅客房间数" prop="vipRooms">
                  <el-input-number :disabled="!(writable && isCurrentProgressNode(1))" v-model="hotelTarget.vipRooms" size="small" :min="0" clearable></el-input-number>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="高端旅客数量" prop="vipPassengers">
                  <el-input-number :disabled="!(writable && isCurrentProgressNode(1))" v-model="hotelTarget.vipPassengers" size="small" :min="0" clearable></el-input-number>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row type="flex" class="row-bg" justify="space-around">
              <el-col :span="6">
                <el-form-item label="普通旅客房间数" prop="ordinaryRooms">
                  <el-input-number :disabled="!(writable && isCurrentProgressNode(1))" v-model="hotelTarget.ordinaryRooms" size="small" :min="0" clearable></el-input-number>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="普通旅客数量" prop="ordinaryPassengers">
                  <el-input-number :disabled="!(writable && isCurrentProgressNode(1))" v-model="hotelTarget.ordinaryPassengers" size="small" :min="0" clearable></el-input-number>
                </el-form-item>
              </el-col>
            </el-row>
            </el-form-item>
            <div style="display: flex;justify-content: flex-end;margin-bottom: 10px;">
              <el-button type="primary" v-if="writable && isCurrentProgressNode(1)" :loading="accmBtnLoading" @click="hotelSubmit" size="small">下一步</el-button>
            </div>
          </el-form>
          <div style="border-top: 1px solid black; margin-bottom: 15px"></div>
            <el-form :model.sync="zjTarget" label-width="120px" ref="zjTarget">
            <h4 style="text-align:center">{{hotelRoles[2] ? hotelRoles[2].title : ''}}</h4>
            <el-form-item label-width="0px">
              <el-row type="flex" class="row-bg" justify="space-around">
                <el-col :span="6">
                  <el-form-item label="高端旅客房间数" prop="vipRooms">
                    <el-input-number :disabled="!(writable && isCurrentProgressNode(2))" v-model="zjTarget.vipRooms" size="small" :min="0" clearable></el-input-number>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label="普通旅客房间数" prop="ordinaryRooms">
                    <el-input-number :disabled="!(writable && isCurrentProgressNode(2))" v-model="zjTarget.ordinaryRooms" size="small" :min="0" clearable></el-input-number>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row type="flex" class="row-bg" justify="space-around">
                <el-col :span="6">
                  <el-form-item label="高端旅客数量" prop="vipPassengers">
                    <el-input-number :disabled="!(writable && isCurrentProgressNode(2))" v-model="zjTarget.vipPassengers" size="small" :min="0" clearable></el-input-number>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label="普通旅客数量" prop="ordinaryPassengers">
                    <el-input-number :disabled="!(writable && isCurrentProgressNode(2))" v-model="zjTarget.ordinaryPassengers" size="small" :min="0" clearable></el-input-number>
                  </el-form-item>
                </el-col>
              </el-row>
            </el-form-item>
            <div style="display: flex;justify-content: flex-end;margin-bottom: 10px;">
              <el-button type="primary" v-if="writable && isCurrentProgressNode(2)" :loading="accmBtnLoading" @click="zjSubmit" size="small">结束流程</el-button>
            </div>
          </el-form>
        </el-card>
        <div>
          <el-steps :active="accomTarget.progress" finish-status="success" progress-status="progress" direction="vertical" size="small">
            <el-step v-for="(item, index) in hotelRoles" :key="index"  :title="item.title" :description="item.description"></el-step>
          </el-steps>
        </div>
      </div>
    </div>
    <div v-else>
      您没有权限访问此页面
    </div>
  </section>
</template>
<script>
import { validatenull } from '@/util/validate'
import moment from 'moment'
import { mapGetters } from 'vuex'
export default {
  data () {
    return {
      accmRules: {
        beginTime: [ { required: true, type: 'date', message: '请输入住宿发起时间', trigger: 'blur' } ],
        hotelName: [ { required: true, message: '请输入酒店', trigger: 'change' } ],
        vipPassengers: [ { required: true, message: '请输入高端旅客数量', trigger: 'blur' } ],
        ordinaryPassengers: [ { required: true, message: '请输入普通旅客数量', trigger: 'blur' } ]
      },
      hotelRules: {
        vipRooms: [ { required: true, message: '请输入高端旅客房间数', trigger: 'blur' } ],
        ordinaryRooms: [ { required: true, message: '请输入普通旅客房间数', trigger: 'blur' } ],
        vipPassengers: [ { required: true, message: '请输入高端旅客人数', trigger: 'blur' } ],
        ordinaryPassengers: [ { required: true, message: '请输入普通旅客人数', trigger: 'blur' } ]
      },
      serviceType: 'HOTEL_SERVICE',
      hotelRoles: [],
      accomServer: [],
      accmBtnLoading: false,
      accomId: '',
      moment: moment,
      accomTarget: {
        progress: -1,
        iscancel: false,
        serviceType: 'HOTEL_SERVICE',
        flightId: '',
        flight: '',
        flightCompany: '',
        beginTime: '',
        hotelName: '',
        vipPassengers: '',
        ordinaryPassengers: ''
      },
      hotelTarget: {
        accommodationId: '',
        vipRooms: '',
        ordinaryRooms: '',
        vipPassengers: '',
        ordinaryPassengers: ''
      },
      zjTarget: {
        accommodationId: '',
        vipRooms: '',
        ordinaryRooms: '',
        vipPassengers: '',
        ordinaryPassengers: ''
      }
    }
  },
  watch: {
    'service': {
      immediate: true,
      deep: true, // 对象内部的属性监听，也叫深度监听
      handler: function (val) {
        console.log('住宿', val)
        this.accomTarget.flightId = val.flightId
        this.accomTarget.flightCompany = val.carrier
        this.accomTarget.flight = val.flight
        this.getServer()
        if (!validatenull(val.accom)) {
          this.initAccomVehicle(val)
        }
      }
    }
  },
  computed: {
    ...mapGetters(['service', 'roles', 'messageTypeAll']),
    serviceFlight () {
      console.log(this.accomTarget.flight)
      return (this.accomTarget.flightCompany || '') + (this.accomTarget.flight || '')
    },
    // 酒店单位选择, 写死
    hotelOptions () {
      let accomObj = this.messageTypeAll.find(item => item.code === 'accom')
      if (accomObj && accomObj.children) {
        let hotelObj = accomObj.children.find(item => item.code === 'hotel')
        if (hotelObj && hotelObj.children) {
          return hotelObj.children.map(item => {
            return {
              label: item.name,
              value: [accomObj.code, hotelObj.code, item.code].join('.')
            }
          })
        }
      }
      return []
    },
    isCancelable () {
      if (this.accomTarget.id && this.accomTarget.progress >= 0 && (this.accomTarget.progress !== this.currentProgress.length) && !this.accomTarget.iscancel && this.currentProgressRole) {
        return true
      }
      return false
    },
    currentProgress () {
      return this.hotelRoles || []
    },
    // 当前流程节点
    currentProgressNode () {
      return this.hotelRoles.find(item => item.progress === this.accomTarget.progress)
    },
    // 当前节点是否为控制室
    currentProgressRole () {
      if (this.roles.includes('controller')) {
        return true
      }
      return false
    },
    // 读权限
    readable () {
      if (this.roles.includes('admin')) {
        return true
      }
      for (let progress of this.hotelRoles) {
        if (!validatenull(progress.roles)) { // 检测角色
          for (let role of progress.roles) {
            if (this.roles.includes(role.code) && role.readable) {
              return true
            }
          }
        }
      }
      return false
    },
    // 写权限
    writable () {
      if (this.accomTarget.progress === this.currentProgress.length) { // 已完成
        return false
      }
      let progress = this.currentProgressNode
      if (progress && !validatenull(progress.roles)) { // 检测角色
        for (let role of progress.roles) {
          if (this.roles.includes(role.code) && role.writable) {
            return true
          }
        }
      }
    }
  },
  created () {
    this.judgeRole()
  },
  methods: {
    messagePost (routingkey) {
      this.$store.dispatch('PushMessageParams', {
        // 从请求结果获取属性值
        $dynamicParams: ['id', 'serviceType'],
        // 动态routingkey, 覆盖已经配置好的routingkey, 如果动态routingKey不在本来的配置中，则无效
        $dynamicRoutingKey: routingkey ? (routingkey instanceof Array ? routingkey : [routingkey]) : null,
        flight: this.accomTarget.flight,
        carrier: this.accomTarget.flightCompany
      })
    },
    // 转换酒店名
    convertHotelName (value) {
      let item = this.hotelOptions.find(option => option.value === value)
      return item ? item.label : value
    },
    isCurrentProgressNode (progressIndex) {
      return this.currentProgressNode && (this.currentProgressNode.progress === progressIndex) && this.accomTarget.iscancel === false
    },
    cancelService () {
      let putAccm = this.$auth('put_accomCancel_update')
      this.messagePost(this.accomTarget.hotelName)
      putAccm({id: this.accomTarget.id}).then(res => {
        res.status && this.$ok(res.message)
        this.getServer()
      })
    },
    // mount初始化功能
    initAccomVehicle (val) {
      if (val.accom.vipPassengers === undefined) {
        this.initAccom(val)
      }
      // 更多进来的
      if (val.accom.vipPassengers !== undefined) {
        this.dbClick(val.accom)
      }
    },
    // 同航班下不同服务获取
    getServer () {
      let getAccomServer = this.$auth('get_delayAccommodation_list')
      getAccomServer({flight: this.accomTarget.flight, flightCompany: this.accomTarget.flightCompany, flightId: this.accomTarget.flightId}).then(res => {
        this.accomServer = res.data.content
      })
    },
    // 表单重置 移除验证效果
    resetForm () {
      this.$nextTick(function () {
        this.$refs['accomTarget'] && this.$refs['accomTarget'].resetFields()
        this.$refs['hotelTarget'] && this.$refs['hotelTarget'].resetFields()
        this.$refs['zjTarget'] && this.$refs['zjTarget'].resetFields()
      })
    },
    initAccom (data) {
      this.resetForm()
      this.accomTarget.id = ''
      this.accomTarget.flightId = data.flightId
      this.accomTarget.flight = data.flight
      this.accomTarget.flightCompany = data.carrier
      this.accomTarget.progress = 0
      this.accomTarget.beginTime = moment(new Date()).toDate()
      this.judgeRole()
    },
    // 更改服务
    accomServerChange (val) {
      this.resetForm()
      let getAccomServer = this.$auth('get_delayAccomId_list')
      getAccomServer({id: val.id}).then(res => {
        this.accomTarget = res.data
        this.hotelTarget = res.data.hotels[0]
        this.zjTarget = this.hotelTarget
        this.accomTarget.hotelName = res.data.hotels[0].hotelName
        if (res.data.progress === 1) {
          this.hotelTarget = res.data.hotels[0]
          this.hotelTarget.vipPassengers = res.data.vipPassengers
          this.hotelTarget.ordinaryPassengers = res.data.ordinaryPassengers
        } else if (res.data.progress === 2) {
          this.hotelTarget = res.data.hotels[0]
          this.zjTarget = this.hotelTarget
        }
        this.judgeRole()
      })
    },
    // 进程角色及步骤判断
    judgeRole () {
      let ajax = this.$auth('get_progress_role_serviceType')
      ajax({serviceType: this.serviceType}).then(res => {
        this.hotelRoles = res.data
      })
    },
    // 住宿地勤确定
    accmSubmit () {
      this.$refs['accomTarget'].validate((valid) => {
        if (valid) {
          this.accomTarget.time = moment(this.accomTarget.time).format()
          this.accomTarget.beginTime = moment(this.accomTarget.beginTime).format()
          this.accomTarget.progress++
          let hotels = []
          hotels.push({hotelName: this.accomTarget.hotelName})
          let postAccm = this.$auth('post_delayAccommodation_add')
          let form = Object.assign({}, this.accomTarget, {hotels})
          this.accmBtnLoading = true
          this.messagePost(this.accomTarget.hotelName)
          postAccm(form).then(res => {
            this.accomId = res.id
            this.accomTarget.progress = res.data.progress
            res.status && this.$ok(res.message)
            this.getServer()
            this.accmBtnLoading = false
          })
        }
      })
    },
    // 住宿酒店确定
    hotelSubmit () {
      this.$refs['hotelTarget'].validate((valid) => {
        if (valid) {
          let putAccm = this.$auth('put_delayAccommodation_update')
          let putHotel = this.$auth('put_delayHotel_update')
          this.accmBtnLoading = true
          putHotel(this.hotelTarget).then(res => {
            this.accomTarget.progress++
            let hotels = []
            hotels.push(this.hotelTarget)
            this.accomTarget.id = this.hotelTarget.accommodationId
            let form = Object.assign({}, this.accomTarget, {hotels})
            this.messagePost()
            putAccm(form).then(res => {
              this.accomTarget.progress = res.data.progress
              this.accmBtnLoading = false
            })
            res.status && this.$ok(res.message)
            /* this.zjTarget = this.hotelTarget */
          })
        }
      })
    },
    // 住宿总确定
    zjSubmit () {
      let putHotel = this.$auth('put_delayHotel_update')
      let putAccm = this.$auth('put_delayAccommodation_update')
      this.accmBtnLoading = true
      putHotel(this.zjTarget).then(res => {
        res.status && this.$ok(res.message)
        this.accomTarget.progress++
        let hotels = []
        hotels.push(this.zjTarget)
        this.accomTarget.id = this.zjTarget.accommodationId
        let form = Object.assign({}, this.accomTarget, {hotels})
        this.messagePost('no send accom')
        putAccm(form).then(res => {
          this.accomTarget.progress = res.data.progress
          this.accmBtnLoading = false
          this.getServer()
        })
      })
    },
    dbClick (data) {
      this.resetForm()
      this.accomId = data.id
      let getAccm = this.$auth('get_delayAccomId_list')
      getAccm({id: data.id}).then(res => {
        this.accomTarget = res.data
        this.accomTarget.hotelName = res.data.hotels[0].hotelName
        if (res.data.progress === 1) {
          this.hotelTarget = res.data.hotels[0]
          this.hotelTarget.vipPassengers = res.data.vipPassengers
          this.hotelTarget.ordinaryPassengers = res.data.ordinaryPassengers
        } else if (res.data.progress === 2) {
          this.hotelTarget = res.data.hotels[0]
          this.zjTarget = this.hotelTarget
        }
        this.judgeRole()
      })
    }
  }
}
</script>
<style lang="scss">
.accom-service-container {
  .accom-service-tags {
    .service-tag {
      margin-right: 10px;
      margin-bottom: 10px;
      cursor: pointer;
      &.is-active {
        position: relative;
        &::after {
          content: " ";
          position: absolute;
          bottom: -11px;
          left: calc(50% - 10px);
          border-bottom: solid 10px #ebeef5;
          border-left: solid 10px transparent;
          border-right: solid 10px transparent;
        }
      }
    }
  }
}
</style>
