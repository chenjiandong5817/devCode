<template>
  <div class="food-service-container">
    <template v-if="readable">
      <div style="margin-bottom: 5px;">
        <el-tag>{{ serviceFlight }}</el-tag>
      </div>
      <div class="food-service-list">
        <template v-for="item in serviceData">
          <el-tag :class="['service-tag', {'is-active' : item.id === foodTarget.id}]"
            :key="item.id"
            @click.native="handleServerChange(item)"
            :type="judgeFoodTagType(item)"
          >
            {{ judgeFoodTagContent(item) }}
          </el-tag>
        </template>
      </div>
      <div style="display: flex;">
        <el-card style="margin-right: 15px; flex-grow: 1;">
          <el-form :model.sync="foodTarget" :rules="foodFirstRules" label-width="80px" label-position="left" ref="foodFirst">
            <!--  -->
            <h4 style="text-align: center">{{ currentProgress[0] ? currentProgress[0].title : '' }}</h4>
            <el-row :gutter="20">
              <el-col :span="6">
                <el-form-item label="配餐类型" prop="foodType" label-width="80px">
                  <el-select v-model="foodTarget.foodType" :disabled="!(writable && isCurrentProgressNode(0)) || foodTarget.canceled" size="small" placeholder="请选择配餐类型">
                    <el-option v-for="item in foodTypeOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <template v-if="isNormalServiceType">
              <div class="right-and-bottom10">
                <el-button type="primary" v-if="writable && isCurrentProgressNode(0)" :loading="loading"  @click="nextStep" size="small">发起配餐</el-button>
                <el-button type="primary" v-if="writableFirst && (!foodTarget.canceled) && addFlag(0)" :loading="cancelLoading"  @click="cancelStep" size="small" >取消配餐</el-button>
              </div>
              <div style="border-top: 1px solid black; margin-bottom: 15px"></div>
            </template>
          </el-form>
          <el-form :model.sync="foodTarget" :rules="foodTwoRules" label-width="80px" label-position="left" ref="foodTwo">
            <h4 style="text-align: center" v-if="isNormalServiceType">
              {{ currentProgress[1] ? currentProgress[1].title : '' }}
            </h4>

            <el-row :gutter="20">
              <el-col :span="6" v-if="isNormalServiceType">
                <el-form-item label-width="80px" label="配餐单位" prop="foodDepartment">
                  <el-select v-model="foodTarget.foodDepartment" :disabled="!(writable && isCurrentProgressNode(1) && foodTarget) || foodTarget.canceled" size="small" placeholder="请选择配餐单位">
                    <el-option v-for="item in departOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="6" v-else>
                <el-form-item label-width="80px" label="配餐单位" prop="foodDepartment">
                  <el-select v-model="foodTarget.foodDepartment" :disabled="!(writable && isCurrentProgressNode(0) && foodTarget) || foodTarget.canceled" size="small" placeholder="请选择配餐单位">
                    <el-option v-for="item in departOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="6" v-if="isNormalServiceType">
                <el-form-item label-width="80px" label="送达类型" prop="sendType">
                  <el-select v-model="foodTarget.sendType" :disabled="!(writable && isCurrentProgressNode(1)) || foodTarget.canceled" size="small" placeholder="请选择送达类型">
                    <el-option key="立即送达" label="立即送达" value="立即送达"></el-option>
                    <el-option key="预约送达" label="预约送达" value="预约送达"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="6" v-else>
                <el-form-item label-width="80px" label="送达类型" prop="sendType">
                  <el-select v-model="foodTarget.sendType" :disabled="!(writable && isCurrentProgressNode(0)) || foodTarget.canceled" size="small" placeholder="请选择送达类型">
                    <el-option key="立即送达" label="立即送达" value="立即送达"></el-option>
                    <el-option key="预约送达" label="预约送达" value="预约送达"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8" v-if="foodTarget.sendType !== '立即送达'">
                <el-form-item label="计划送达时间" prop="planArrive" label-width="120px">
                  <el-date-picker v-model="foodTarget.planArrive" :disabled="!(writable && isCurrentProgressNode(1)) || foodTarget.canceled" type="datetime" size="small"
                    format="yyyy-MM-dd HH:mm" placeholder="选择计划送达时间">
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <el-col :span="6" v-if="isNormalServiceType">
                <el-button type="primary" :disabled="!(writable && isCurrentProgressNode(1)) || foodTarget.canceled"  @click="visible = true" size="small">选择配餐小类</el-button>
              </el-col>
              <el-col :span="10" v-if="isNormalServiceType">
                <el-form-item label-width="80px" label="送餐地点" prop="sendAddr">
                  <el-input v-model="foodTarget.sendAddr" :disabled="!(writable && isCurrentProgressNode(1)) || foodTarget.canceled" placeholder="请输入送餐地点" size="small"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="10" v-else>
                <el-form-item label-width="80px" label="送餐地点" prop="sendAddr">
                  <el-input :value="getFoodDepartMentZh(foodTarget.sendAddr)" :disabled="!(writable && isCurrentProgressNode(0)) || foodTarget.canceled" placeholder="请输入送餐地点" size="small"></el-input>
                </el-form-item>
              </el-col>
            </el-row>

            <template v-if="isNormalServiceType">
              <el-row :gutter="20" class="title-setting">
                <el-col :span="4">
                  <span>配餐小类</span>
                </el-col>
                <el-col :span="4">
                  <span>数量</span>
                </el-col>
              </el-row>

              <el-row :gutter="20" v-for="(item, index) in foodTarget.inside" :key="index">
                <el-col :span="4">
                  <el-form-item prop="foodName" label-width="0px">
                    <el-input v-model="item.foodName" :disabled="!(writable && isCurrentProgressNode(1)) || foodTarget.canceled" placeholder="请输入餐食种类" readonly size="small"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="4">
                  <el-form-item prop="estCount" label-width="0px">
                    <el-input-number v-model="item.estCount" :disabled="!(writable && isCurrentProgressNode(1))  || foodTarget.canceled" :min="0" label="数量" size="small"></el-input-number>
                  </el-form-item>
                </el-col>
              </el-row>

              <el-row :gutter="20" class="title-setting">
                <el-col :span="4">
                  <span>合计数量</span>
                </el-col>
                <el-col :span="4">
                  <el-input v-model="totalCount" :disabled="true" size="small"></el-input>
                </el-col>
              </el-row>

              <div class="right-and-bottom10">
                <el-button type="primary" v-if="writable && isCurrentProgressNode(1)" :disabled="foodTarget.canceled" :loading="loading"  @click="nextStep" size="small">下一步</el-button>
              </div>
            </template>
            <div class="right-and-bottom10" v-else>
              <el-button type="primary" v-if="writable && isCurrentProgressNode(0)"  @click="nextStep" :loading="loading" size="small">发起追加配餐</el-button>
              <el-button type="primary" v-if="writableFirst && (!foodTarget.canceled) && addFlag(0)" :loading="cancelLoading"  @click="cancelStep" size="small" >取消追加配餐</el-button>
            </div>

            <div style="border-top: 1px solid black; margin-bottom: 15px"></div>
          </el-form>

          <el-form :model.sync="foodTarget" :rules="foodThreeRules" label-width="80px" label-position="left" ref="foodThree">
            <h4 style="text-align: center" v-if="isNormalServiceType">
              {{ currentProgress[2] ? currentProgress[2].title : '' }}
            </h4>
            <h4 style="text-align: center" v-else>
              {{ currentProgress[1] ? currentProgress[1].title : '' }}
            </h4>
            <el-row :gutter="20" v-if="isNormalServiceType">
              <el-col :span="8">
                <el-form-item label="预计送达时间" prop="estArrive" label-width="120px">
                  <el-date-picker v-model="foodTarget.estArrive" :disabled="!(writable && isCurrentProgressNode(2))  || foodTarget.canceled" type="datetime" size="small"
                    format="yyyy-MM-dd HH:mm" placeholder="选择预计送达时间">
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20" v-else>
              <el-col :span="8">
                <el-form-item label="预计送达时间" prop="estArrive" label-width="120px">
                  <el-date-picker v-model="foodTarget.estArrive" :disabled="!(writable && isCurrentProgressNode(1))  || foodTarget.canceled" type="datetime" size="small"
                    format="yyyy-MM-dd HH:mm" placeholder="选择预计送达时间">
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            <div class="right-and-bottom10" v-if="isNormalServiceType">
              <el-button type="primary" v-if="writable && isCurrentProgressNode(2)" :disabled="foodTarget.canceled"  @click="nextStep" :loading="loading" size="small">下一步</el-button>
            </div>
            <div class="right-and-bottom10" v-else>
              <el-button type="primary" v-if="writable && isCurrentProgressNode(1)" :disabled="foodTarget.canceled"  @click="nextStep" :loading="loading" size="small">下一步</el-button>
            </div>

            <div style="border-top: 1px solid black; margin-bottom: 15px"></div>
          </el-form>

          <el-form :model.sync="foodTarget" :rules="foodFourRules" label-width="80px" label-position="left" ref="foodFour">
            <h4 style="text-align: center" v-if="isNormalServiceType">
              {{ currentProgress[3] ? currentProgress[3].title : '' }}
            </h4>
            <h4 style="text-align: center" v-else>
              {{ currentProgress[2] ? currentProgress[2].title : '' }}
            </h4>
            <template v-if="isNormalServiceType">
              <el-row :gutter="20" class="title-setting">
                <el-col :span="4">
                  <span>配餐小类</span>
                </el-col>
                <el-col :span="4">
                  <span>实际数量</span>
                </el-col>
              </el-row>
              <el-row :gutter="20" v-for="item in foodTarget.inside" :key="item.id">
                <el-col :span="4">
                  <el-form-item prop="foodName" label-width="0px">
                    <el-input v-model="item.foodName" :disabled="!(writable && isCurrentProgressNode(3)) || foodTarget.canceled" placeholder="请输入餐食种类" readonly size="small"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="4">
                  <el-form-item prop="actCount" label-width="0px">
                    <el-input-number v-model="item.actCount" :disabled="!(writable && isCurrentProgressNode(3)) || foodTarget.canceled" :min="0" label="数量" size="small"></el-input-number>
                  </el-form-item>
                </el-col>
              </el-row>
            </template>
            <el-row :gutter="20" v-if="isNormalServiceType">
              <el-col :span="8">
                <el-form-item label="实际送达时间" prop="actArrive" label-width="120px">
                  <el-date-picker v-model="foodTarget.actArrive" :disabled="!(writable && isCurrentProgressNode(3)) || foodTarget.canceled" type="datetime" size="small"
                    format="yyyy-MM-dd HH:mm" placeholder="选择实际送达时间">
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20" v-else>
              <el-col :span="8">
                <el-form-item label="实际送达时间" prop="actArrive" label-width="120px">
                  <el-date-picker v-model="foodTarget.actArrive" :disabled="!(writable && isCurrentProgressNode(2)) || foodTarget.canceled" type="datetime" size="small"
                    format="yyyy-MM-dd HH:mm" placeholder="选择实际送达时间">
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>

          <div style="display: flex;justify-content: flex-end">
            <el-button type="primary" @click="nextStep" size="small" :loading="loading" v-if="(writable && isCurrentProgressNode(currentProgress.length - 1))" :disabled="foodTarget.canceled">结束流程</el-button>
          </div>
        </el-card>

        <div>
          <el-steps :active="foodTarget.progress" direction="vertical" finish-status="success" size="small">
            <el-step v-for="item in progressMap[foodTarget.serviceType]" :key="item.title" :title="item.title" :description="item.description"></el-step>
          </el-steps>
        </div>

      </div>
    </template>
    <template v-else>
      您没有权限查看服务流程页面。
    </template>
    <el-dialog title="配餐小类选择" :visible.sync="visible" width="30%">
      <el-tree
        :data="smallOptions"
        ref="tree"
        show-checkbox
        node-key="id"
        default-expand-all
        :props="{
          children: 'children',
          label: 'foodName'
        }">
      </el-tree>
      <span slot="footer" class="dialog-footer">
        <el-button @click="visible = false" size="small">取 消</el-button>
        <el-button type="primary" @click="handleSelectNode" :loading="loading" size="small">确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>
<script>
import { ignoreNull } from '@/util/util'
import { validatenull } from '@/util/validate'
import moment from 'moment'
import { mapGetters } from 'vuex'

export default {
  components: {
  },
  data () {
    return {
      foodFirstRules: {
        foodType: [ { required: true, message: '请选择配餐类型', trigger: 'change' } ]
      },
      foodTwoRules: {
        planArrive: [ { required: true, type: 'date', message: '请选择预计送达时间', trigger: 'change' } ],
        foodDepartment: [ { required: true, message: '请选择配餐单位', trigger: 'change' } ],
        sendType: [ { required: true, message: '请选择送达类型', trigger: 'change' } ],
        sendAddr: [ { required: true, message: '请输入送餐地点', trigger: 'blur' } ]
      },
      foodThreeRules: {
        estArrive: [ { required: true, type: 'date', message: '请选择预计送达时间', trigger: 'change' } ]
      },
      foodFourRules: {
        actArrive: [ { required: true, type: 'date', message: '请选择实际送达时间', trigger: 'change' } ]
      },
      moment: moment,
      // 写死的服务类型， 0为正常配餐，1为追加配餐
      serviceTypeArray: ['MEALS_SERVICE', 'ADD_MEALS_SERVICE'],
      // 写死的消息类型，用户获取配餐单位，构造routingkey
      messageTypeArray: ['food.updateEstTime', 'food.updateActTime', 'food.faqipeichan'],
      messageTypePathArray: ['food', 'unit'],
      progressMap: {
        'MEALS_SERVICE': [],
        'ADD_MEALS_SERVICE': []
      },
      loading: false,
      cancelLoading: false,
      visible: false,
      serviceData: [], // 配餐数据下拉
      foodTypeOptions: [],
      smallOptions: [],
      // departOptions: [
      //   { label: '空厨', value: '空厨' },
      //   { label: '酒店', value: '酒店' },
      //   { label: '地勤', value: '地勤' }
      // ],
      foodTargetDefault: {
        progress: -1,
        serviceType: 'MEALS_SERVICE',
        flightId: null,
        carrier: '',
        flight: '',
        foodType: '',
        foodDepartment: '',
        sendAddr: '航班登机口',
        sendType: '立即送达',
        inside: [],
        planArrive: '',
        estArrive: '',
        actArrive: ''
      },
      foodTarget: {},
      totalCount: 0
    }
  },
  mounted () {
    // this.getProgressList('MEALS_SERVICE')
    // this.getProgressList('ADD_MEALS_SERVICE')
  },
  computed: {
    ...mapGetters(['service', 'roles', 'messageTypeAll']),
    // 配餐单位选择, 写死，从food.unit中获取选择列表
    departOptions () {
      let foodObj = this.messageTypeAll.find(item => item.code === this.messageTypePathArray[0])
      if (foodObj && foodObj.children) {
        let unitObj = foodObj.children.find(item => item.code === this.messageTypePathArray[1])
        if (unitObj && unitObj.children) {
          return unitObj.children.map(item => {
            return {
              label: item.name,
              value: [foodObj.code, unitObj.code, item.code].join('.')
            }
          })
        }
      }
      return []
    },
    // 航班号
    serviceFlight () {
      return (this.foodTarget.carrier || '') + (this.foodTarget.flight || '')
    },
    // 默认消息类型
    defaultServiceType () {
      return this.serviceTypeArray[0]
    },
    // 当前消息类型，为正常 或 追加
    currentServiceType () {
      if (this.foodTarget) {
        return this.foodTarget.serviceType
      }
      return this.defaultServiceType
    },
    // 当前流程，为正常 或 追加
    currentProgress () {
      return this.progressMap[this.currentServiceType] || []
    },
    // 当前流程节点
    currentProgressNode () {
      return this.currentProgress.find(item => item.progress === this.foodTarget.progress)
    },
    // 是否为正常服务类型
    isNormalServiceType () {
      return this.currentServiceType === this.defaultServiceType
    },
    // 是否不是正常服务类型
    isOtherServiceType () {
      return !this.isDefaultServiceType
    },
    // 读权限
    readable () {
      if (this.roles.includes('admin')) {
        return true
      }
      for (let progress of this.currentProgress) {
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
      if (this.foodTarget.progress === this.currentProgress.length) { // 已完成
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
    },
    writableFirst () {
      let progress = this.currentProgress[0]
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
      deep: true,
      handler: function (val) {
        console.log(val)
        this.foodTarget.flightId = val.flightId
        this.foodTarget.carrier = val.carrier
        this.foodTarget.flight = val.flight
        if (val.flightId && val.carrier && val.flight) {
          let params = {
            flightId: val.flightId,
            carrier: val.carrier,
            flight: val.flight
          }
          this.getServiceData(val.carrier, val.flight, val.flightId)
          if (!validatenull(val.food)) {
            this.foodTarget = Object.assign({}, this.foodTargetDefault, val.food, params)
            if (this.foodTarget.serviceType === this.serviceTypeArray[1] &&
              !this.foodTarget.foodDepartment.startsWith(this.messageTypePathArray.join('.'))) {
              this.foodTarget.foodDepartment = this.getFoodDepartMent(this.foodTarget.foodDepartment)
            }
            this.foodInit()
          } else {
            this.foodTarget.serviceType = this.serviceTypeArray[0]
          }
        }
      }
    },
    'foodTarget.inside': {
      deep: true,
      handler: function (arr) {
        this.totalCount = 0
        arr.forEach(item => {
          if (item.needCount === 1) {
            this.totalCount += item.estCount
          }
        })
      }
    },
    // 监听当前服务类型
    currentServiceType: {
      immediate: true,
      handler (val) {
        val && validatenull(this.progressMap[val]) && this.getProgressList(val)
      }
    }
  },
  methods: {
    getFoodDepartMentZh (val) {
      let name = ''
      if (!val) return
      let arr = val.split('.')
      if (arr.length > 0) {
        let Obj = this.messageTypeAll.find(item => item.code === arr[0])
        if (Obj && Obj.children) {
          let unitObj = Obj.children.find(item => item.code === arr[1])
          if (unitObj && unitObj.children) {
            unitObj.children.forEach(ele => {
              if (ele.code === arr[2]) {
                name = ele.name
              }
            })
          }
        }
      }
      return name
    },
    getFoodDepartMent (type) {
      if (type) {
        let val = type.split('.').pop()
        return [...this.messageTypePathArray, val].join('.')
      }
      return type
    },
    getProgressList (type) {
      let ajax = this.$auth('get_progress_role_serviceType')
      if (!ajax) {
        return false
      } else {
        ajax({serviceType: type}).then(res => {
          if (res.status && res.data) {
            this.progressMap[type] = res.data
          }
        })
      }
    },
    isCurrentProgressNode (progressIndex) {
      return this.currentProgressNode && (this.currentProgressNode.progress === progressIndex)
    },
    addFlag () {
      return this.foodTarget.progress > 0
    },
    judgeFoodTagType (service) {
      let string = ''
      if (service.progress === this.currentProgress.length) {
        string = 'success'
      } else if (service.canceled) {
        string = 'danger'
      }
      return string
    },
    judgeFoodTagContent (service) {
      let prefix = service.foodType + '-' + moment(service.createTime).format('HH:mm')
      let string = ''
      if (service.progress === this.currentProgress.length) {
        string = '-已完成'
      } else if (service.canceled) {
        string = '-已取消'
      }
      return prefix + string
    },
    getServiceData (carrier, flight, flightId) {
      // 获取当前航班的所有配餐服务
      let ajax = this.$auth('get_providefood_filterlist')
      if (flightId) {
        ajax({flightId: flightId, pageSize: 99, pageNumber: 0}).then(res => {
          if (res.status && res.data) {
            this.serviceData = res.data.content
          }
        })
      }
    },
    foodInit () {
      // 如果是新增配餐服务 将送餐地址初始化
      if (this.foodTarget.terminal) {
        this.foodTarget.sendAddr = `${this.foodTarget.terminal}登机口${this.foodTarget.gate}`
      }

      // 初始化进度对应的表单数据
      if (this.isNormalServiceType) {
        switch (this.foodTarget.progress) {
          case 0:
            // 获取配餐类型
            this.$auth('get_food_type_list')().then(res => {
              if (res.status && res.data) {
                this.foodTypeOptions = res.data.map(item => { return {label: item.foodName, value: item.foodName} })
              }
            })
            break
          case 1:
            // 根据大类 获取小类的树data
            this.$auth('get_food_type_filter')({name: this.foodTarget.foodType}).then(res => {
              if (res.status && res.data) {
                this.smallOptions = res.data.content[0].children
              }
            })
            this.getMealDetail()
            break
          case 2:
            this.foodTarget.estArrive = new Date(this.foodTarget.planArrive)
            this.getMealDetail()
            break
          case 3:
            this.foodTarget.actArrive = new Date(this.foodTarget.estArrive)
            this.getMealDetail()
            break
        }
      } else {
        switch (this.foodTarget.progress) {
          case 0:
            // 获取配餐类型
            this.$auth('get_food_type_list')().then(res => {
              if (res.status && res.data) {
                this.foodTypeOptions = res.data.map(item => { return {label: item.foodName, value: item.foodName} })
              }
            })
            break
          case 1:
            this.foodTarget.estArrive = new Date(this.foodTarget.planArrive)
            break
          case 2:
            this.foodTarget.actArrive = new Date(this.foodTarget.estArrive)
            break
        }
      }
    },
    getMealDetail (serviceId) {
      // 根据serviceID 获取小类详情
      this.$auth('put_provideMeal_getMeal')({serviceId: this.foodTarget.id}).then(res => {
        if (res.status && res.data.length > 0) {
          this.foodTarget.inside = res.data
          // 根据foodIds 获取foodName Map
          this.$auth('post_food_type_getFoodName')(this.foodTarget.inside.map(i => { return i.foodId })).then(res => {
            if (res.status) {
              this.foodTarget.inside.forEach(item => {
                item.foodName = res.data[0][item.foodId]
              })
            }
          })
        }
      })
    },
    handleServerChange (obj) {
      this.foodTarget = Object.assign({}, this.foodTargetDefault, obj)
      this.getServiceData(this.foodTarget.carrier, this.foodTarget.flight, this.foodTarget.flightId)
      this.foodInit()
    },
    handleSelectNode () {
      // 根据选择的叶子节点 插入对应小类详情
      this.loading = true
      let node = this.$refs.tree.getCheckedNodes(true) // true为只选择叶子节点
      this.foodTarget.inside = []
      node.forEach(item => {
        this.foodTarget.inside.push({
          serviceId: this.foodTarget.id,
          foodName: item.foodName,
          foodId: item.id,
          estCount: 0,
          actCount: 0,
          needCount: item.needCount
        })
      })
      this.loading = false
      this.visible = false
    },
    foodSubmit (ajaxStr, routingkey) {
      this.$store.dispatch('PushMessageParams', {
        // 从请求结果获取属性值
        $dynamicParams: ['id', 'serviceType'],
        // 动态routingkey, 覆盖已经配置好的routingkey, 如果动态routingKey不在本来的配置中，则无效
        $dynamicRoutingKey: routingkey ? (routingkey instanceof Array ? routingkey : [routingkey]) : null,
        flight: this.foodTarget.flight,
        carrier: this.foodTarget.carrier
      })
      this.foodTarget.progress = this.foodTarget.progress + 1
      this.$auth(ajaxStr)(ignoreNull(this.foodTarget)).then(res => {
        if (res.status) {
          this.foodTarget.id = res.data.id
          this.getServiceData(this.foodTarget.carrier, this.foodTarget.flight, this.foodTarget.flightId)
          this.loading = false
        }
      }).catch(res => {
        this.loading = false
      })
      // this.setAuth()
    },
    cancelStep () {
      var time = moment(new Date().getTime())
      if (time.diff(this.foodTarget.createTime, 'mintue') > 15) {
        this.$fail('已超时，无法取消。')
        return false
      }
      this.$confirm('确认取消配餐', '提示', {}).then(() => {
        let routingkey = this.foodTarget.foodDepartment
        this.$store.dispatch('PushMessageParams', {
          // 从请求结果获取属性值
          $dynamicParams: ['id', 'serviceType'],
          // 动态routingkey, 覆盖已经配置好的routingkey, 如果动态routingKey不在本来的配置中，则无效
          $dynamicRoutingKey: routingkey ? (routingkey instanceof Array ? routingkey : [routingkey]) : null,
          flight: this.foodTarget.flight,
          carrier: this.foodTarget.carrier
        })
        this.cancelLoading = true
        let ajax = this.$auth('put_provide_food_cancel')
        if (ajax) {
          ajax({id: this.foodTarget.id}).then(res => {
            if (res.status) {
              this.$ok(res.message)
              this.cancelLoading = false
              this.foodTarget.canceled = true
              this.getServiceData(this.foodTarget.carrier, this.foodTarget.flight, this.foodTarget.flightId)
            }
          })
        }
      })
    },
    nextStep () {
      this.loading = true
      if (this.isNormalServiceType) { // 正常配餐流程处理
        switch (this.foodTarget.progress) {
          case 0:
            this.$refs['foodFirst'].validate((valid) => {
              if (valid) {
                this.foodSubmit('post_providefood_add', this.messageTypeArray[2])
              } else {
                this.loading = false
                this.$fail('请输入必选项')
              }
            })
            break
          case 1:
            if (this.foodTarget.sendType === '立即送达') {
              if (this.totalCount <= 100) {
                this.foodTarget.planArrive = new Date(new Date().getTime() + 60 * 60 * 1000)
              } else {
                this.foodTarget.planArrive = new Date(new Date().getTime() + 60 * 90 * 1000)
              }
            }
            this.$refs['foodTwo'].validate((valid) => {
              if (valid) {
                if (this.foodTarget.inside.length === 0) {
                  this.$fail('请先选择小类')
                  this.loading = false
                } else {
                  this.foodTarget.inside.forEach(item => {
                    item.actCount = item.estCount
                  })
                  // 选中的配餐单位，即routingkey
                  this.foodSubmit('put_providefood_update', this.foodTarget.foodDepartment)

                  let meal = this.$auth('post_provideMeal_add')
                  meal(this.foodTarget.inside).then(res => {
                    if (!res.status) {
                      this.$fail(res.message)
                    }
                  })
                }
              } else {
                this.loading = false
                this.$fail('请输入必选项')
              }
            })
            break
          case 2:
            this.$refs['foodThree'].validate((valid) => {
              if (valid) {
                this.foodSubmit('put_providefood_update', this.messageTypeArray[1])
              } else {
                this.loading = false
                this.$fail('请输入必选项')
              }
            })
            break
          case 3:
            this.$refs['foodFour'].validate((valid) => {
              if (valid) {
                this.foodSubmit('put_providefood_update', ['don\'t send'])
              } else {
                this.loading = false
                this.$fail('请输入必选项')
              }
            })
            break
        }
      } else { // 追加配餐流程提交处理
        switch (this.foodTarget.progress) {
          case 0:
            if (this.foodTarget.sendType === '立即送达') {
              if (this.totalCount <= 100) {
                this.foodTarget.planArrive = new Date(new Date().getTime() + 60 * 60 * 1000)
              } else {
                this.foodTarget.planArrive = new Date(new Date().getTime() + 60 * 90 * 1000)
              }
            }
            this.$refs['foodFirst'].validate((valid) => {
              if (valid) {
                let ajax = this.$auth('post_provideFood_getServiceType')
                ajax(ignoreNull(this.foodTarget)).then(res => {
                  if (res.status) {
                    if (res.data === 1) {
                      this.$confirm('此航班已经存在这个配餐类型, 是否继续发起?', '提示', {
                        cancelButtonText: '取消',
                        confirmButtonText: '继续',
                        type: 'warning'
                      }).then(() => {
                        this.$refs['foodTwo'].validate((valid) => {
                          if (valid) {
                            this.foodSubmit('post_providefood_add', this.foodTarget.foodDepartment)
                          }
                        })
                      }).catch(() => {
                        this.foodTarget.foodType = ''
                        this.loading = false
                      })
                    } else {
                      this.$refs['foodTwo'].validate((valid) => {
                        if (valid) {
                          this.foodSubmit('post_providefood_add', this.foodTarget.foodDepartment)
                        }
                      })
                    }
                  }
                })
              } else {
                this.loading = false
                this.$fail('请输入必选项')
              }
            })
            break

          case 1:
            this.$refs['foodThree'].validate((valid) => {
              if (valid) {
                this.foodSubmit('put_providefood_update', this.messageTypeArray[1])
              } else {
                this.loading = false
                this.$fail('请输入必选项')
              }
            })
            break

          case 2:
            this.$refs['foodFour'].validate((valid) => {
              if (valid) {
                // 不需要发送消息，即打乱routingkey，乱输入一个用于过滤的值
                this.foodSubmit('put_providefood_update', ['don\'t send'])
                // this.foodTarget.progress++
                // let ajax = this.$auth('put_providefood_update')
                // ajax(ignoreNull(this.foodTarget)).then(res => {
                //   if (res.status) {
                //     this.$ok(res.message)
                //     this.loading = false
                //   }
                // })
              } else {
                this.loading = false
                this.$fail('请输入必选项')
              }
            })
            break
        }
      }
    }
  }
}
</script>
<style lang="scss">
.food-service-container {
  .food-service-list {
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
