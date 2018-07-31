<template>
  <section>
    <div v-if="readable" class="vehicle-service-container">
      <div style="margin-bottom: 5px;">
        <el-tag>{{ serviceFlight }}</el-tag>
      </div>
      <div class="vehicle-service-tags">
        <template v-for="(item, index) in vehicleServer">
          <el-tag  :key="index" @click.native="vehicleServerChange(item)"
            :class="['service-tag', {'is-active': item.id === vehicleTarget.id}]"
            :type="item.progress === vehicleRoles.length ? 'success' : (item.iscancel ? 'danger' : '')">
              <span>{{item.destination}}</span>
              <span>{{moment(item.beginTime).format('HH:mm')}}</span>
              <span>
                {{item.progress === vehicleRoles.length ? '-已完成' : (item.iscancel ? '-已取消' : '')}}
              </span>
          </el-tag>
        </template>
      </div>
      <div style="display:flex">
        <el-card style="margin-right: 15px;flex-grow: 1">
        <el-form :rules="vehicleRules" :model.sync="vehicleTarget" label-width="120px" ref="vehicleTarget">
          <h4 style="text-align:center">{{vehicleRoles[0] ? vehicleRoles[0].title : ''}}</h4>
            <el-form-item label-width="0px">
            <el-row>
              <el-col :span="9">
                <el-form-item label="发起时间" prop="beginTime">
                  <el-date-picker :disabled="!(writable && isCurrentProgressNode(0))" v-model="vehicleTarget.beginTime" type="datetime" format="yyyy-MM-dd HH:mm" placeholder="请输入发起时间" size="small" clearable></el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="计划到位时间" prop="arriveTime">
                  <el-date-picker :disabled="!(writable && isCurrentProgressNode(0))" v-model="vehicleTarget.arriveTime" type="datetime" format="yyyy-MM-dd HH:mm" placeholder="请输入计划到位时间" size="small" clearable></el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="旅客数量" prop="passengers">
                  <el-input-number :disabled="!(writable && isCurrentProgressNode(0))" v-model="vehicleTarget.passengers" size="small" :min="0" clearable></el-input-number>
                </el-form-item>
              </el-col>
            </el-row>
            </el-form-item>
            <el-form-item label-width="0px">
              <el-row>
                <el-col :span="7">
                  <el-form-item label="起点-终点" prop="origin">
                    <el-input :disabled="!(writable && isCurrentProgressNode(0))" v-model="vehicleTarget.origin" placeholder="请输入起点" size="small" clearable></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="1">
                  <div style="transform:rotate(90deg);padding-top:15px;margin-left:17px"><i @click="changeValue" style="cursor:pointer;" class="el-icon-sort"></i></div>
                </el-col>
                <el-col :span="6" >
                  <el-form-item prop="destination">
                    <el-autocomplete :disabled="!(writable && isCurrentProgressNode(0))" class="inline-input" v-model="vehicleTarget.destination" :fetch-suggestions="querySearch" placeholder="请输入终点" size="small" clearable></el-autocomplete>
                  </el-form-item>
                </el-col>
              </el-row>
            </el-form-item>
            <div style="display: flex;justify-content: flex-end;margin-bottom: 10px;">
              <el-button type="primary" icon="el-icon-circle-close" v-if="isCancelable" size="small" @click="cancelService()">取消服务</el-button>
              <el-button type="primary" v-if="writable && isCurrentProgressNode(0)" :loading="vehicleLoading" @click="vehicleSubmit" size="small">下一步</el-button>
            </div>
          </el-form>
          <div style="border-top: 1px solid black;margin-bottom: 15px"></div>
            <h4 style="text-align:center">{{vehicleRoles[1] ? vehicleRoles[1].title : ''}}</h4>
            <el-form :inline="true">
              <el-form-item class="divided">
                <el-form-item>
                  <el-button v-if="writable && isCurrentProgressNode(1)" type="primary" @click="handleAdd" size="small">新增</el-button>
                </el-form-item>
                <el-form-item>
                  <el-button type="danger" v-if="writable && isCurrentProgressNode(1)" :disabled="toolDis" @click="handleRemove" size="small">删除</el-button>
                </el-form-item>
                <el-form-item>
                  <div class="carNumber">
                    <div v-for="(item, index) in car" :key="index">
                      <span :style="{backgroundColor:item.color}" class="circlemin"></span>
                      <span>{{item.type}}:{{item.num}}辆</span>
                    </div>
                  </div>
                </el-form-item>
              </el-form-item>
            </el-form>
            <div class="table-content">
              <el-table border highlight-current-row ref="table" @cell-mouse-enter="planHandleMouseEnter" @cell-mouse-leave="planHandleMouseOut"
              :data="vehicle" @row-click="handleSelect" style="width: 100%;" size="small">
                <el-table-column type="index" width="60">
                </el-table-column>
                <el-table-column prop="license" label="车牌号" sortable>
                </el-table-column>
                <el-table-column prop="type" label="车辆类型" sortable>
                </el-table-column>
                <el-table-column prop="phone" label="（司机）联系方式" >
                </el-table-column>

                <el-table-column prop="planArriveTime" label="预计到位时间" min-width="120">
                  <template slot-scope="scope">
                    <span v-if="!scope.row.editFlag">
                      <!-- {{realArriveTime}} -->
                      {{moment(scope.row.planArriveTime).format("YYYY-MM-DD HH:mm")}}
                    </span>
                    <span v-if="scope.row.editFlag" class="cell-edit-input">
                      <el-date-picker v-model="planArriveTime" type="datetime" format="yyyy-MM-dd HH:mm" size="small" clearable></el-date-picker>
                    </span>
                    <span v-if="!scope.row.editFlag && !(vehicleTarget.progress === 2)" style="margin-left:10px;" class="cell-icon"  @click="planHandleEdit(scope.row)">  <i class="el-icon-edit"></i> </span>
                    <span v-if="scope.row.editFlag && !(vehicleTarget.progress === 2)"  style="margin-left:10px;"  class="cell-icon"  @click.stop="planHandleSave(scope.row)">  <i class="el-icon-check"></i> </span>
                  </template>
                </el-table-column>
              </el-table>
            </div>
            <div style="display: flex;justify-content: flex-end;margin-bottom: 10px;">
              <el-button type="primary" v-if="writable && isCurrentProgressNode(1)" @click="setEstimate" size="small">设置预计到位时间</el-button>
              <el-button type="primary" v-if="writable && isCurrentProgressNode(1)" :loading="vehicleLoading" @click="kxSubmit" size="small">下一步</el-button>
            </div>

            <div style="border-top: 1px solid black; margin-bottom: 15px"></div>
            <h4 style="text-align:center">{{vehicleRoles[2] ? vehicleRoles[2].title : ''}}</h4>
            <div class="table-content">
              <el-table border highlight-current-row ref="table2" @cell-mouse-enter="handleMouseEnter" @cell-mouse-leave="handleMouseOut"
              :data="zjVehicle" style="width: 100%;" size="small">
                <el-table-column type="index" width="60">
                </el-table-column>
                <el-table-column prop="license" label="车牌号" sortable>
                </el-table-column>
                <el-table-column prop="type" label="车辆类型" sortable>
                </el-table-column>
                <el-table-column prop="phone" label="（司机）联系方式" >
                </el-table-column>
                <el-table-column prop="planArriveTime" label="预计到位时间" sortable>
                  <template slot-scope="scope">
                    {{moment(scope.row.planArriveTime).format("YYYY-MM-DD HH:mm")}}
                  </template>
                </el-table-column>
                <el-table-column prop="realArriveTime" label="实际到位时间" min-width="120">
                  <template slot-scope="scope">
                    <span v-if="!scope.row.zjEditFlag">
                      <!-- {{realArriveTime}} -->
                        {{scope.row.realArriveTime === null ? '' : moment(scope.row.realArriveTime).format("YYYY-MM-DD HH:mm")}}
                    </span>
                    <span v-if="scope.row.zjEditFlag" class="cell-edit-input">
                      <el-date-picker v-model="realArriveTime" type="datetime" format="yyyy-MM-dd HH:mm" size="small" clearable></el-date-picker>
                    </span>
                    <span v-if="!scope.row.zjEditFlag && !(vehicleTarget.progress === 3)" style="margin-left:10px;" class="cell-icon"  @click="handleEdit(scope.row)">  <i class="el-icon-edit"></i> </span>
                    <span v-if="scope.row.zjEditFlag && !(vehicleTarget.progress === 3)"  style="margin-left:10px;"  class="cell-icon"  @click.stop="handleSave(scope.row)">  <i class="el-icon-check"></i> </span>
                  </template>
                </el-table-column>

              </el-table>
            </div>
            <div style="display: flex;justify-content: flex-end;margin-bottom: 10px;">
              <el-button v-if="writable && isCurrentProgressNode(2)" type="primary" @click="setActual" size="small">设置实际到位时间</el-button>
              <el-button type="primary" v-if="writable && isCurrentProgressNode(2)" :loading="vehicleLoading" @click="zjVehicleSubmit" size="small">结束流程</el-button>
            </div>
        </el-card>
        <div>
          <el-steps :active="vehicleTarget.progress" finish-status="success" progress-status="progress" direction="vertical" size="small">
            <el-step v-for="(item, index) in vehicleRoles" :key="index" :title="item.title" :description="item.description"></el-step>
          </el-steps>
        </div>
      </div>
      <!-- 一键设置预计到位时间 -->
      <el-dialog title="设置预计到位时间" append-to-body :visible.sync="estimateVisible" :close-on-click-modal="false"
      @close="handleClose" width="30%" >
      <label>一键设置预计到位时间</label>
        <el-date-picker v-model="planArriveTime" style="width:200px" type="datetime" format="yyyy-MM-dd HH:mm" size="small" clearable>
        </el-date-picker>
        <span slot="footer" class="dialog-footer">
          <el-button @click="estimateVisible = false" size="small">取 消</el-button>
          <el-button type="primary" @click="estimateSubmit" size="small">确 定</el-button>
        </span>
      </el-dialog>
      <!-- 一键设置实际到位时间 -->
      <el-dialog title="设置实际到位时间" append-to-body :visible.sync="actualVisible" :close-on-click-modal="false"
      @close="handleClose" width="30%" >
      <label>一键设置实际到位时间</label>
        <el-date-picker v-model="realArriveTime" style="width:200px" type="datetime" format="yyyy-MM-dd HH:mm" size="small" clearable>
        </el-date-picker>
        <span slot="footer" class="dialog-footer">
          <el-button @click="actualVisible = false" size="small">取 消</el-button>
          <el-button type="primary" @click="actualSubmit" size="small">确 定</el-button>
        </span>
      </el-dialog>
      <vehicleDialog ref="vehicleDialog" @flash="getData"></vehicleDialog>
    </div>
    <div v-else>
      您没有权限访问此页面
    </div>
  </section>
</template>
<script>
import { validatenull } from '@/util/validate'
import vehicleDialog from './vehicleDialog'
import moment from 'moment'
import { mapGetters } from 'vuex'
export default {
  components: {
    vehicleDialog
  },
  data () {
    return {
      vehicleRules: {
        beginTime: [ { required: true, message: '请输入车辆发起时间', type: 'date', trigger: 'blur' } ],
        passengers: [ { required: true, message: '请输入旅客数量', trigger: 'blur' } ],
        origin: [ { required: true, message: '请输入起点', trigger: 'change' } ],
        destination: [ { required: true, message: '请输入终点', trigger: 'change' } ]
      },
      serviceType: 'VEHICLE_SERVICE',
      vehicleRoles: [],
      car: [],
      vehicleServer: [],
      vehicleLoading: false,
      vehicleId: '',
      estimateVisible: false,
      planArriveTime: '', // 一键设置预计到车时间的字段
      actualVisible: false,
      realArriveTime: '',
      selected: null,
      moment: moment,
      vehicle: [],
      zjVehicle: [],
      toolDis: true,
      vehicleTarget: {
        progress: -1,
        iscancel: false,
        serviceType: 'VEHICLE_SERVICE',
        flightId: '',
        flight: '',
        flightCompany: '',
        beginTime: '',
        arriveTime: '',
        passengers: '',
        origin: '',
        destination: '花园酒店'
      }
    }
  },
  computed: {
    ...mapGetters(['service', 'roles']),
    serviceFlight () {
      console.log('serviceFlight', this.vehicleTarget)
      return (this.vehicleTarget.flightCompany) + (this.vehicleTarget.flight)
    },
    currentProgress () {
      return this.vehicleRoles || []
    },
    // 当前流程节点
    currentProgressNode () {
      return this.vehicleRoles.find(item => item.progress === this.vehicleTarget.progress)
    },
    // 当前节点是否为控制室
    currentProgressRole () {
      if (this.roles.includes('controller')) {
        return true
      }
      return false
    },
    isCancelable () {
      if (this.vehicleTarget.id && this.vehicleTarget.progress >= 0 && (this.vehicleTarget.progress !== this.currentProgress.length) && !this.vehicleTarget.iscancel && this.currentProgressRole) {
        return true
      }
      return false
    },
    // 读权限
    readable () {
      if (this.roles.includes('admin')) {
        return true
      }
      for (let progress of this.vehicleRoles) {
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
      console.log(this.vehicleTarget)
      if (this.vehicleTarget.progress === this.currentProgress.length) { // 已完成
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
      return false
    }
  },
  watch: {
    'service': {
      immediate: true,
      deep: true, // 对象内部的属性监听，也叫深度监听
      handler (val) {
        console.log('车辆', val)
        this.vehicleTarget.flightId = val.flightId
        this.vehicleTarget.flightCompany = val.carrier
        this.vehicleTarget.flight = val.flight
        this.getServer()
        if (!validatenull(val.vehicle)) {
          this.initAccomVehicle(val)
        }
      }
    },
    'selected' (val) {
      val === undefined || val === null ? this.toolDis = true : this.toolDis = false
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
        flight: this.vehicleTarget.flight,
        carrier: this.vehicleTarget.flightCompany
      })
    },
    isCurrentProgressNode (progressIndex) {
      return this.currentProgressNode && (this.currentProgressNode.progress === progressIndex) && this.vehicleTarget.iscancel === false
    },
    cancelService () {
      let putVehicle = this.$auth('put_vehicleCancel_update')
      /* this.messagePost() */
      putVehicle({id: this.vehicleTarget.id}).then(res => {
        res.status && this.$ok(res.message)
        this.getServer()
      })
      this.messagePost()
    },
    // mount初始化功能
    initAccomVehicle (val) {
      if (val.vehicle.origin === undefined) {
        this.initVehicle(val)
        this.carNumberInit()
      }
      if (val.vehicle.origin !== undefined) {
        this.dbClick(val.vehicle)
        this.carNumberInit()
      }
    },
    // 同航班下不同服务获取
    getServer () {
      let getVehicleServer = this.$auth('get_delayVehicle_list')
      getVehicleServer({flight: this.vehicleTarget.flight, flightCompany: this.vehicleTarget.flightCompany, flightId: this.vehicleTarget.flightId}).then(res => {
        this.vehicleServer = res.data.content
      })
    },
    // 表单重置 移除验证效果
    resetForm () {
      this.$nextTick(function () {
        this.$refs['vehicleTarget'] && this.$refs['vehicleTarget'].resetFields()
      })
    },
    initVehicle (data) {
      this.resetForm()
      this.vehicleTarget.id = ''
      this.zjVehicle = []
      this.vehicle = []
      this.vehicleTarget.flightId = data.flightId
      this.vehicleTarget.flight = data.flight
      this.vehicleTarget.flightCompany = data.carrier
      this.vehicleTarget.origin = data.terminal + '航站楼'
      this.vehicleTarget.progress = 0
      this.vehicleTarget.beginTime = moment(new Date()).toDate()
      this.vehicleTarget.arriveTime = moment(new Date()).toDate()
      this.vehicleTarget.arriveTime.setMinutes(this.vehicleTarget.beginTime.getMinutes() + 15)
      this.judgeRole()
    },
    vehicleServerChange (val) {
      this.resetForm()
      this.zjVehicle = []
      this.vehicle = []
      let getVehicleServer = this.$auth('get_delayVehicleId_list')
      getVehicleServer({id: val.id}).then(res => {
        this.vehicleTarget = res.data
        this.vehicleId = res.data.id
        if (this.vehicleTarget.progress === 1) {
          this.vehicle = res.data.busEntities
        } else if (this.vehicleTarget.progress === 2 || this.vehicleTarget.progress === 3) {
          this.vehicle = res.data.busEntities
          this.zjVehicle = res.data.busEntities
          this.zjVehicle.forEach(item => {
            this.$set(item, 'zjEditFlag', false)
          })
        }
        this.judgeRole()
      })
    },
    // 进程角色及步骤判断
    judgeRole () {
      let ajax = this.$auth('get_progress_role_serviceType')
      ajax({serviceType: this.serviceType}).then(res => {
        this.vehicleRoles = res.data
        console.log(this.vehicleRoles)
      })
    },
    // 车辆地勤确定
    vehicleSubmit () {
      this.$refs['vehicleTarget'].validate((valid) => {
        if (valid) {
          this.vehicleTarget.progress++
          let postVehicle = this.$auth('post_delayVehicle_add')
          this.vehicleLoading = true
          this.messagePost()
          postVehicle(this.vehicleTarget).then(res => {
            this.vehicleId = res.data.id
            res.status && this.$ok(res.message)
            this.vehicleLoading = false
            this.vehicleTarget.progress = res.data.progress
            this.getServer()
          })
        }
      })
    },
    // 车辆快线确定
    kxSubmit () {
      let getBus = this.$auth('get_delayBus_list')
      let getVehicle = this.$auth('get_delayVehicleId_list')
      let putVehicle = this.$auth('put_delayVehicle_update')
      this.vehicleLoading = true
      getBus({vehicleId: this.vehicleId}).then(res => {
        res.status && this.$ok(res.message)
        getVehicle({id: this.vehicleId}).then(res => {
          let vehicle = res.data
          vehicle.progress++
          this.messagePost()
          putVehicle(vehicle).then(res => {
            this.vehicleTarget.progress = res.data.progress
            this.vehicleLoading = false
          })
        })
      })
    },
    // 车辆总确定
    zjVehicleSubmit () {
      let getVehicle = this.$auth('get_delayVehicleId_list')
      let putVehicle = this.$auth('put_delayVehicle_update')
      this.vehicleLoading = true
      getVehicle({id: this.vehicleId}).then(res => {
        let vehicle = res.data
        vehicle.progress++
        this.messagePost('no send vehicle')
        putVehicle(vehicle).then(res => {
          this.vehicleTarget.progress = res.data.progress
          this.vehicleLoading = true
          this.getServer()
        })
      })
    },
    handleClose () {
      this.planArriveTime = ''
      this.realArriveTime = ''
      this.estimateVisible = false
      this.actualVisible = false
    },
    setEstimate () {
      this.estimateVisible = true
    },
    estimateSubmit () {
      this.estimateVisible = false
      this.vehicle.forEach(item => {
        item.planArriveTime = this.planArriveTime
        let putBus = this.$auth('put_delayBus_update')
        putBus(item).then(res => {
          console.log(res)
        })
      })
    },
    setActual () {
      this.actualVisible = true
    },
    actualSubmit () {
      this.actualVisible = false
      this.zjVehicle.forEach(item => {
        item.realArriveTime = this.realArriveTime
        let putBus = this.$auth('put_delayBus_update')
        putBus(item).then(res => {
          console.log(res)
        })
      })
    },
    // 交换起终点的值
    changeValue () {
      let origin = this.vehicleTarget.origin
      let destination = this.vehicleTarget.destination
      this.vehicleTarget.origin = destination
      this.vehicleTarget.destination = origin
    },
    // 车辆实际到达时间输入
    handleMouseEnter (row, column, cell, event) {
      if (column.property === 'realArriveTime') {
        if (!(this.vehicleTarget.progress === 2)) {
          return false
        } else {
          cell.children[0].children[1].style.color = 'black'
        }
      }
    },
    handleMouseOut (row, column, cell, event) {
      if (column.property === 'realArriveTime') {
        if (!(this.vehicleTarget.progress === 2)) {
          return false
        } else {
          cell.children[0].children[1].style.color = '#ffffff'
        }
      }
    },
    handleEdit (row) {
      this.zjVehicle.forEach(item => {
        item.zjEditFlag = false
      })
      row.zjEditFlag = true
      this.realArriveTime = row.realArriveTime === null ? '' : row.realArriveTime
    },
    // 保存数据
    handleSave (row) {
      row.zjEditFlag = false
      row.realArriveTime = this.realArriveTime
      let putBus = this.$auth('put_delayBus_update')
      putBus(row).then(res => {
        console.log(res)
      })
    },
    // 车辆预计到达时间输入
    planHandleMouseEnter (row, column, cell, event) {
      if (column.property === 'planArriveTime') {
        if (!(this.vehicleTarget.progress === 1)) {
          return false
        } else {
          cell.children[0].children[1].style.color = 'black'
        }
      }
    },
    planHandleMouseOut (row, column, cell, event) {
      if (column.property === 'planArriveTime') {
        if (!(this.vehicleTarget.progress === 1)) {
          return false
        } else {
          cell.children[0].children[1].style.color = '#ffffff'
        }
      }
    },
    planHandleEdit (row) {
      this.vehicle.forEach(item => {
        item.editFlag = false
      })
      row.editFlag = true
      this.planArriveTime = row.planArriveTime
    },
    // 保存数据
    planHandleSave (row) {
      row.editFlag = false
      row.planArriveTime = this.planArriveTime
      let putBus = this.$auth('put_delayBus_update')
      putBus(row).then(res => {
        console.log(res)
      })
    },
    carNumberInit () {
      let carMsg = this.$auth('get_busTypeNum_list')
      carMsg().then(res => {
        this.car = res.data
        this.car.sort(function (a, b) {
          return -(a.num - b.num)
        })
        this.car.forEach(item => {
          switch (item.num > 100 ? 1 : ((item.num < 100 && item.num > 10) ? 2 : 3)) {
            case 1: this.$set(item, 'color', '#00FF00')
              break
            case 2: this.$set(item, 'color', '#FFFF00')
              break
            case 3: this.$set(item, 'color', '#FF0000')
              break
          }
        })
      })
    },
    getData () {
      this.selected = null
      let getBus = this.$auth('get_delayBus_list')
      this.carNumberInit()
      getBus({vehicleId: this.vehicleId}).then(res => {
        console.log(res.data.content)
        this.vehicle = res.data.content
        this.vehicle.forEach(item => {
          this.$set(item, 'editFlag', false)
        })
        res.status && this.$ok(res.message)
      })
    },
    // 表格选中一条记录的响应事件
    handleSelect (row) {
      this.selected = row
    },
    // 新增车辆按钮响应事件
    handleAdd () {
      this.$refs['vehicleDialog'].show(this.vehicleId)
    },
    handleRemove () {
      let deleteBus = this.$auth('delete_delayBus_remove')
      deleteBus({id: this.selected.id}).then(res => {
        this.getData()
      })
    },
    // 酒店下拉
    querySearch (queryString, cb) {
      let getDataAjax = this.$auth('get_hotel_list')
      getDataAjax({pageSize: 10000, pageNumber: 0}).then(res => {
        if (res.status && res.data) {
          let hotel = res.data.content
          hotel.forEach(item => {
            this.$set(item, 'value', item.name)
          })
          var results = queryString ? hotel.filter(this.createFilter(queryString)) : hotel
          cb(results)
        }
      })
      /* this.$store.dispatch('GetDic', 'HOTEL').then(data => {
        let hotel = data['HOTEL']
        var results = queryString ? hotel.filter(this.createFilter(queryString)) : hotel
        cb(results)
      }) */
    },
    createFilter (queryString) {
      return (carType) => {
        return (carType.value.toLowerCase().indexOf(queryString.toLowerCase()) !== -1)
      }
    },
    dbClick (data) {
      this.resetForm()
      this.zjVehicle = []
      this.vehicle = []
      let getVehicle = this.$auth('get_delayVehicleId_list')
      getVehicle({id: data.id}).then(res => {
        this.vehicleTarget = res.data
        this.vehicleId = res.data.id
        if (this.vehicleTarget.progress === 1) {
          this.vehicle = res.data.busEntities
        } else if (this.vehicleTarget.progress === 2 || this.vehicleTarget.progress === 3) {
          this.vehicle = res.data.busEntities
          this.zjVehicle = res.data.busEntities
          this.zjVehicle.forEach(item => {
            this.$set(item, 'zjEditFlag', false)
          })
        }
        this.judgeRole()
      })
    }
  }
}
</script>
<style lang="scss">
.vehicle-service-container {
  .vehicle-service-tags {
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
  .carNumber {
    display: flex;
    justify-content:space-around;
    margin-top: 0px
  }
  .circlemin{
    display: inline-block;
    width: 10px;
    height: 10px;
    border-radius: 5px;
  }
  .car-main-class {
    .el-step.is-vertical .el-step__main {
      width: 100px;
    }
  }
  .table-content {
    .cell-edit-input .el-input,
      .el-input__inner {
        width: 170px;
      }
      .cell-icon {
        cursor: pointer;
        color: #fff;
      }
  }
}
</style>
