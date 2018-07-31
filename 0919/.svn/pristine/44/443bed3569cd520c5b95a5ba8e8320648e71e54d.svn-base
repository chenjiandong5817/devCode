<template>
	<el-dialog :title="title" :visible.sync="visible" :size="size" :modal="modal">
	  <el-form :model="formData" :rules="addUpdateRules" label-width="10px" ref="VIPForm">
	    <el-form-item required>
	      <el-row :gutter="0">
	         <el-col :span="12">
	            <el-col :span="5">
	               <span>机场名称</span>
	            </el-col>
	            <el-col :span="19">
	               <el-form-item prop="airportCode">
		               <el-select v-model="formData.airportCode" filterable placeholder="请选择">
		                 <el-option-group
			              v-for="group in airportsTypesChoose"
			              :key="group.label"
			              :label="group.label">
			              <el-option
			                v-for="item in group.options"
			                :key="item.value"
			                :label="item.labelShow"
			                :value="item.value">
			                  <span style="float: left">{{ item.text }}</span>
			                  <span style="float: right">{{ item.code }}</span>
			              </el-option>
			            </el-option-group>
		              </el-select>
	              </el-form-item>
	            </el-col>
	         </el-col>
	         <el-col :span="12">
	            <el-col :span="5">
	               <span>VIP类型</span>
	            </el-col>
	            <el-col :span="19">
	               <el-form-item prop="vipType">
	                 <el-select v-model="formData.vipType" filterable placeholder="请选择">
	                 <el-option
	                    v-for="item in vipTypeChoose"
	                    :key="item.value"
	                    :label="item.text"
	                    :value="item.value">
	                  </el-option>
	                </el-select>
	                </el-form-item>
	            </el-col>
	         </el-col>
	      </el-row>
	    </el-form-item>
	    <el-form-item required>
	      <el-row :gutter="0">
	         <el-col :span="12">
	            <el-col :span="5">
	                <span>进出标志</span>
	            </el-col>
	            <el-col :span="19">
	                <el-form-item prop="direction">
		               <el-select v-model="formData.direction" filterable placeholder="请选择">
		                 <el-option
		                    v-for="item in directionChoose"
		                    :key="item.value"
		                    :label="item.text"
		                    :value="item.value">
		                 </el-option>
		              </el-select>
	              </el-form-item>
	            </el-col>
	         </el-col>
	         <el-col :span="12">
	            <el-col :span="5">
	                <span>航班任务</span>
	            </el-col>
	            <el-col :span="19">
	                <el-form-item prop="flightTask">
		               <el-select v-model="formData.flightTask" filterable placeholder="请选择">
		                 <el-option
		                    v-for="item in flightTaskChoose"
		                    :key="item.value"
		                    :label="item.text"
		                    :value="item.value">
		                 </el-option>
		              </el-select>
	              </el-form-item>
	            </el-col>
	         </el-col>
	      </el-row>
	    </el-form-item>
	    <el-form-item required>
	      <el-row :gutter="0">
	         <el-col :span="12">
               <el-col :span="5">
                  <span>执行日期</span>
               </el-col>
               <el-col :span="19">
               <el-form-item prop="flightDate">
                   <el-date-picker type="date" placeholder="请选择" v-model="formData.flightDate">
                   </el-date-picker>
                   </el-form-item>
               </el-col>
             </el-col>
	         <el-col :span="12">
	            <el-col :span="5">
	               <span>人数</span>
	            </el-col>
	            <el-col :span="19">
	               <el-form-item prop="numberOfPeople">
	                  <el-input type="number" v-model="formData.numberOfPeople" placeholder="请输入人数" min="0" max="999"></el-input>
	               </el-form-item>
	            </el-col>
	         </el-col>
	      </el-row>
	    </el-form-item>
	    <el-form-item required>
	      <el-row :gutter="0">
	         <el-col :span="12">
	            <el-col :span="5">
	               <span>承运人代码</span>
	            </el-col>
	            <el-col :span="19">
	               <el-form-item prop="carrier">
	                   <el-input type="text" v-model="formData.carrier" placeholder="请输入承运人" @change="getUpeerCarrier"></el-input>
	              </el-form-item>
	            </el-col>
	         </el-col>
	         <el-col :span="12">
	            <el-col :span="5">
	               <span>航班号</span>
	            </el-col>
	            <el-col :span="19">
	               <el-form-item prop="flight">
	                   <el-input type="text" v-model="formData.flight" placeholder="请输入航班号"></el-input>
	              </el-form-item>
	            </el-col>
	         </el-col>
	      </el-row>
	    </el-form-item>
	    <el-form-item required>
	      <el-row :gutter="0">
	         <el-col :span="12">
	            <el-col :span="5">
	               <span>VIP描述</span>
	            </el-col>
	            <el-col :span="19">
	               <el-form-item prop="vipDescription">
	                  <el-input type="textarea" v-model="formData.vipDescription" placeholder="请输入VIP描述"></el-input>
	               </el-form-item>
	            </el-col>
	         </el-col>
	         <el-col :span="12">
	            <el-col :span="5">
	               <span>备注</span>
	            </el-col>
	            <el-col :span="19">
	               <el-input type="textarea" v-model="formData.remark" placeholder="请输入备注"></el-input>
	            </el-col>
	         </el-col>
	      </el-row>
	    </el-form-item>
	    <el-form-item>
	      <el-row>
	        <el-col :span="24">
	         <div style="border-top: 1px solid #ccc"></div>
	        </el-col>
	      </el-row>
	    </el-form-item>
	    <el-form-item>
	       <el-row :gutter="5">
	          <el-col :span="3">
	             <span>VIP信息</span>
	          </el-col>
	          <el-col :span="4">
	             <span>姓名</span>
	          </el-col>
	          <el-col :span="4">
	             <span>职务</span>
	          </el-col>
	          <el-col :span="4">
	             <span>舱位</span>
	          </el-col>
	          <el-col :span="4">
	             <span>座位</span>
	          </el-col>
	       </el-row>
	    </el-form-item>
	    <el-form-item>
	       <el-row :gutter="5" v-for="item in formData.vipDetails" :key="item.no">
	          <el-col :span="3">
	             <div style="width: 600px;height: 10px"></div>
	          </el-col>
	          <el-col :span="4">
	            <el-input type="text" v-model="item.vipName" placeholder="姓名" @change="vipNameChange"></el-input>
	          </el-col>
	          <el-col :span="4">
	            <el-input type="text" v-model="item.title" placeholder="职务" @change="titleChange"></el-input>
	          </el-col>
	          <el-col :span="4">
	            <el-input type="text" v-model="item.cabin" placeholder="舱位" @change="cabinChange"></el-input>
	          </el-col>
	          <el-col :span="4">
	            <el-input type="text" v-model="item.seat" placeholder="座位" @change="seatChange"></el-input>
	          </el-col>
	          <el-col :span="1">
	             <div style="width: 100px;height: 50px;"></div>
	          </el-col>
	          <el-col :span="1">
	            <div style="width: 10px"></div>
	            <el-button size="small" @click="delVIPDetail(item)" v-show="item.delVisible"><span><li class="el-icon-minus"></li></span></el-button>
	          </el-col>
	          <el-col :span="1">
	            <el-button style="margin-left: 10px" size="small" @click="addVIPDetail(item)" v-show="item.addVisible"><span><li class="el-icon-plus"></li></span></el-button>
	          </el-col>
	       </el-row>
	    </el-form-item>
	  </el-form>
	  <div slot="footer" class="dialog-footer">
        <el-button @click.native="visible = false">取消</el-button>
        <el-button @click="resetForm('commonForm')">重置</el-button>
        <el-button type="primary" @click.native="handleSubmit" :loading="loading">提交</el-button>
    </div>
	</el-dialog>
</template>
<script>
import API from './../../../api'
import Util from './../../../common/js/util'
export default {
	props: {
	    size: {
	      type: String,
	      default: 'small'
	    },
	    title: {
	      type: String,
	      default: '新增'
	    },
	    modal: {
	    	type: Boolean,
	    	default: true
	    },
	    labelWidth: {
	      type: Number,
	      default: 80
	    },
	    to: {
	      type: Function,
	      default: function () {}
	    },
	    callback: {
	      type: Function,
	      default: function () {}
	    }
  },
  data () {
  	return {
  		addUpdateRules: {
  			vipType: [
               { required: true, message: '请选择VIP类型' }
  			],
  			carrier: [
               { required: true, max: 2, min: 1, message: '请输入一位到二位字符串的承运人IATA代码' }
  			],
  			numberOfPeople: [
               { validator: null }
  			],
  			flight: [
               { required: true, max: 10, min: 1, message: '请输入一位到十位字符串的航班号' }
  			],
  			direction: [
               { required: true, message: '请选择进出标志' }
  			],
  			flightDate: [
               { required: true, message: '日期不能为空' }
  			]
  		},
  		formData: {
  			vipDescription: '',
  			numberOfPeople: 0,
  			vipType: '',
  			flightTask: '',
  			flightId: '',
  			carrier: '',
  			flight: '',
  			direction: '',
  			flightDate: '',
  			remark: '',
  			airportCode: '',
  			vipDetails: [
	           {
	           	no: 0,
	           	vipName: '',
	           	title: '',
	           	cabin: '',
	           	seat: '',
	           	addVisible: true,
	  		    delVisible: true
	           }
	  		]
  		},
  		loading: false,
  		visible: false,
  		vipTypeChoose: [],
  		adminAirportChoose: [],
  		// 用于分组的用户订阅的机场
        airportsTypesChoose: [],
  		carrierChoose: [],
  		flightChoose: [],
  		directionChoose: [],
  		flights: [],
  		flightTaskChoose: [],
  		formDataCache: null,
  		oldValue: null,
  		// 这个参数用于一进页面去统计vipdetails的条数
  		numberOfVipDetails: null,
  		numberOfPeopleValidate: null
  		// VIP信息
  	}
  },
  methods: {
  	// 父级点击添加的或者编辑的时候要调用的函数。分两种情况，一种是在航班动态的页面，另外一种是在新的VIP页面
  	show: function (data) {
  		this.visible = true
  		this.formDataCache = Util.deepCopy(data)
      	// 在这边对从父组件拿来的数据进行处理
      	for (var i = 0; i < data.vipDetails.length; i++) {
      		data.vipDetails[i]['no'] = i
      		data.vipDetails[i]['addVisible'] = true
      		data.vipDetails[i]['delVisible'] = true
      	}
      	// 一进页面就获取vipDetails的长度
      	this.numberOfVipDetails = data.vipDetails.length
      	this.formData = data
      	// 刚进入这个页面的时候，vipDetails的个数为一的时候，有两种情况,分别为空值和有值
      	if (this.formData.vipDetails.length === 1) {
      		if (this.formData.vipDetails[0].vipName.length === 0 && this.formData.vipDetails[0].title.length === 0 && this.formData.vipDetails[0].cabin.length === 0 && this.formData.vipDetails[0].seat.length === 0) {
      			this.formData.vipDetails[0].addVisible = false
      		    this.formData.vipDetails[0].delVisible = false
      		} else {
      			this.formData.vipDetails[0].addVisible = true
      		    this.formData.vipDetails[0].delVisible = false
      		}
      	}
      	// 有一个以上的值
      	if (this.formData.vipDetails.length > 1) {
      		var lengthDetails = this.formData.vipDetails.length
      		for (var j = 0; j < lengthDetails; j++) {
      			this.formData.vipDetails[j].addVisible = false
      			this.formData.vipDetails[j].delVisible = true
      		}
      		this.formData.vipDetails[lengthDetails - 1].addVisible = true
      	}
      	this.oldValue = Util.deepCopy(data)
      	this.addUpdateRules.numberOfPeople[0].validator = this.numberOfPeopleValidate
  	},
  	// 获取所有的航班动态的信息，用于搜索数据
    getDynamicFlight: function () {
  		let para = {
  			pageNumber: 1,
          	pageSize: 100000
  		}
  		API.getDynamicFlight().go(para).then((data) => {
          this.flights = data.attr.data.list
      })
  	},
  	// 获取机场下拉框的数据
  	getAirportList: function () {
  	   // 筛选出登录用户的订阅机场
          var resultCache = [
            {
              label: '国际',
              labelkey: 'I',
              options: []
            },
            {
              label: '国内',
              labelkey: 'D',
              options: []
            }
          ]
	   this.airports = Util.typedAirport(this.$cache.array.airports)
	   // 筛选出登录用户的订阅机场
	   this.adminAirportChoose = []
	   var subscibeAirport = JSON.parse(localStorage.getItem('AdminSubscribeAirportJson'))
	   for (var i = 0; i < subscibeAirport.length; i++) {
	     for (var j = 0; j < this.airports.length; j++) {
	       if (subscibeAirport[i].airportCode === this.airports[j].value) {
	         this.adminAirportChoose.push(this.airports[j])
	       }
	     }
	   }
	   // 对下拉框的数据进行分组
        for (var a = 0; a < this.adminAirportChoose.length; a++) {
          if (this.adminAirportChoose[a].nature === 'I') {
            resultCache[0].options.push(this.adminAirportChoose[a])
          }
          if (this.adminAirportChoose[a].nature === 'D') {
            resultCache[1].options.push(this.adminAirportChoose[a])
          }
        }
        this.airportsTypesChoose = resultCache
	 },
	 // 获取进出标志下拉跨框的数据
	 getDirectionChoose: function () {
	 	this.directionChoose = []
        for (var i = 0; i < this.$cache.array.flightdirections.length; i++) {
        	if (this.$cache.array.flightdirections[i].description !== '所有') {
        		var item = {}
		        item['text'] = this.$cache.array.flightdirections[i].description
		        item['value'] = this.$cache.array.flightdirections[i].directionCode
		        this.directionChoose.push(item)
        	}
        }
	 },
	 // 获取VIP类型下拉框数据
      GetVIPTypeChoose: function () {
        this.vipTypeChoose = []
        for (var i = 0; i < this.$cache.array.vipranks.length; i++) {
          var item = {}
          item['text'] = this.$cache.array.vipranks[i].description
          item['value'] = this.$cache.array.vipranks[i].rankCode
          this.vipTypeChoose.push(item)
        }
      },
      // 获取航班任务下拉框的数据
      getFlightTask: function () {
      	this.flightTaskChoose = []
      	for (var i = 0; i < this.$cache.array.flighttasks.length; i++) {
      		var item = {}
      		item['text'] = this.$cache.array.flighttasks[i].description
      		item['value'] = this.$cache.array.flighttasks[i].flightTaskCode
      		this.flightTaskChoose.push(item)
      	}
      },
      // 添加一条VIP信息
      addVIPDetail: function (itemData) {
      	// 点击添加按钮，添加一行空的VIP信息
      	// 在最后一个位置进行添加
  		this.formData.vipDetails.push({
           	no: 0,
           	vipName: '',
           	title: '',
           	cabin: '',
           	seat: '',
           	addVisible: true,
  		    delVisible: true
        })
        // 为每行VIP信息做一个标记,以便于删除的时候使用
      	for (var i = 0; i < this.formData.vipDetails.length; i++) {
      		this.formData.vipDetails[i].no = i
      		this.formData.vipDetails[i].addVisible = false
      		this.formData.vipDetails[i].delVisible = true
      	}
        // 更新numberOfVipDetails的值
        this.numberOfVipDetails = this.formData.vipDetails.length
      	// 也有可能在半中间对VIP信息进行添加
      },
      // 此处的四个函数用于判断右边的加号与减号是否该显示，他们的显示方式是已经填写完毕的只能显示减号，没有填写
      // 完毕的也只能填写减号，填写完成后，减号变成加号,当这四个值中的其中一个值发生变化都要去执行一次判断
      vipNameChange: function (value) {
      	this.nameTitleCabinSeatChange()
      },
      titleChange: function (value) {
      	this.nameTitleCabinSeatChange()
      },
      cabinChange: function (value) {
      	this.nameTitleCabinSeatChange()
      },
      seatChange: function (value) {
      	this.nameTitleCabinSeatChange()
      },
      // 删除一条VIP信息
      delVIPDetail: function (itemData) {
      	var result = []
      	this.formData.vipDetails.splice(itemData.no, 1)
      	result = this.formData.vipDetails
      	// 为标记进行重新赋值
      	for (var i = 0; i < result.length; i++) {
      		result[i].no = i
      		result[i].addVisible = false
      		result[i].delVisible = true
      	}
      	result[result.length - 1].addVisible = true
      	if (result.length === 1) {
      		result[result.length - 1].addVisible = true
      	    result[result.length - 1].delVisible = false
      	}
      	this.formData.vipDetails = result
      	// 记录此时vipdetails的长度
      	this.numberOfVipDetails = this.formData.vipDetails.length
      	// 增加判断
      	if (this.formData.vipDetails[this.numberOfVipDetails - 1].vipName.length === 0 || this.formData.vipDetails[this.numberOfVipDetails - 1].title.length === 0 || this.formData.vipDetails[this.numberOfVipDetails - 1].cabin.length === 0 || this.formData.vipDetails[this.numberOfVipDetails - 1].seat.length === 0) {
      		this.formData.vipDetails[this.numberOfVipDetails - 1].addVisible = false
      		this.formData.vipDetails[this.numberOfVipDetails - 1].delVisible = true
      		if (this.formData.vipDetails.length === 1) {
      			this.formData.vipDetails[0].delVisible = false
      		}
      	}
      },
      // 点击确认按钮
      handleSubmit: function () {
      	// 在用户进行提交的时候，对基础信息和VIP信息进行判断
      	// 定义一个值，这个值用于标记是否通过验证
      	var validTag = null
      	for (var i = 0; i < this.formData.vipDetails.length; i++) {
      		if (this.formData.vipDetails[i].vipName.length !== 0 && this.formData.vipDetails[i].title.length !== 0 && this.formData.vipDetails[i].cabin.length !== 0 && this.formData.vipDetails[i].seat.length !== 0) {
      			validTag = 'success'
      		} else {
      			validTag = 'error'
      			break
      		}
      	}
      	if (validTag === 'success') {
      		this.formData.flightDate = this.dateFormated(this.formData.flightDate)
	      	this.$refs['VIPForm'].validate((valid) => {
	        if (valid) {
	          this.$confirm('确认提交吗？', '提示', {}).then(() => {
	            this.loading = true
	            this.formData.numberOfPeople = parseInt(this.formData.numberOfPeople)
	            let para = { newValue: this.formData, oldValue: this.oldValue }
	            this.to(para).then((res) => {
	              this.loading = false
	              this.$notify(Util.notifyBody(res.ok, res.msg))
	              this.$refs['VIPForm'].resetFields()
	              this.visible = false
	              this.callback()
	            })
	          }).catch(() => {
	            this.$message({
	              type: 'info',
	              message: '已取消提交'
	            })
	          })
	        }
	      })
      	}
      	if (validTag === 'error') {
      		this.$notify(Util.notifyBody(false, 'vip详细信息不能有空值'))
      	}
      },
      // 转换时间格式
      dateFormated: function (date) {
        var dataFormated = null
        var moment = null
        moment = require('moment')
        dataFormated = moment(date).format('YYYY-MM-DD')
        return dataFormated
      },
      // 清空formData
      clearFormData: function () {
      	this.formData = {
  			vipDescription: '',
  			numberOfPeople: 0,
  			vipType: '',
  			flightTask: '',
  			flightId: '',
  			carrier: '',
  			flight: '',
  			direction: '',
  			flightDate: '',
  			remark: '',
  			airportCode: '',
  			vipDetails: [
	           {
	           	no: 0,
	           	vipName: '',
	           	title: '',
	           	cabin: '',
	           	seat: '',
	           	addVisible: true,
	  		    delVisible: true
	           }
	  		]
  		}
      },
      resetForm: function () {
      	var cache = Util.deepCopy(this.formDataCache)
      	this.formData = cache
      	// 增加判断
      	// 当vipdetails的长度为一的时候，有两种情况，一是全部为空值，二是全部都有值的时候
      	if (this.formData.vipDetails.length === 1) {
      		this.numberOfVipDetails = 1
      		this.formData.vipDetails[0]['no'] = 0
      		this.formData.vipDetails[0].addVisible = false
      		this.formData.vipDetails[0].delVisible = false
      		if (this.formData.vipDetails[0].vipName.length !== 0 && this.formData.vipDetails[0].title.length !== 0 && this.formData.vipDetails[0].cabin.length !== 0 && this.formData.vipDetails[0].seat.length !== 0) {
      			this.formData.vipDetails[0].addVisible = true
      		}
      	}
      	if (this.formData.vipDetails.length > 1) {
      		for (var i = 0; i < this.formData.vipDetails.length; i++) {
      			this.formData.vipDetails[i]['no'] = i
      			this.formData.vipDetails[i].addVisible = false
      			this.formData.vipDetails[i].delVisible = true
      		}
      		this.formData.vipDetails[this.formData.vipDetails.length - 1].addVisible = true
      		this.numberOfVipDetails = this.formData.vipDetails.length
      	}
      },
      // 转换大小写
      getUpeerCarrier: function (value) {
      	this.formData.carrier = value.toUpperCase()
      },
      // 这个函数用于当VIP名字，职务，舱位，座位发生改变时，需要执行的函数
      nameTitleCabinSeatChange: function () {
      	if (this.formData.vipDetails[this.numberOfVipDetails - 1].vipName.length !== 0 && this.formData.vipDetails[this.numberOfVipDetails - 1].title.length !== 0 && this.formData.vipDetails[this.numberOfVipDetails - 1].cabin.length !== 0 && this.formData.vipDetails[this.numberOfVipDetails - 1].seat.length !== 0) {
      		this.formData.vipDetails[this.numberOfVipDetails - 1].addVisible = true
      		this.formData.vipDetails[this.numberOfVipDetails - 1].delVisible = true
      		if (this.formData.vipDetails.length === 1) {
      			this.formData.vipDetails[0].delVisible = false
      		}
      	}
      	if (this.formData.vipDetails[this.numberOfVipDetails - 1].vipName.length === 0 || this.formData.vipDetails[this.numberOfVipDetails - 1].title.length === 0 || this.formData.vipDetails[this.numberOfVipDetails - 1].cabin.length === 0 || this.formData.vipDetails[this.numberOfVipDetails - 1].seat.length === 0) {
      		this.formData.vipDetails[this.numberOfVipDetails - 1].addVisible = false
      		this.formData.vipDetails[this.numberOfVipDetails - 1].delVisible = true
      		if (this.formData.vipDetails.length === 1) {
      			this.formData.vipDetails[0].delVisible = false
      		}
      	}
      },
      getNumberOfPeopleValidate: function () {
        this.numberOfPeopleValidate = (rule, value, callback) => {
          value = parseInt(value)
          console.log(value)
          if (value < 0) {
            return callback(new Error('值不能为负数'))
          }
          if (!Number.isInteger(value)) {
            callback(new Error('请输入数字值'))
          }
          if (value > 999) {
            callback(new Error('值只能在0~999之间'))
          }
          callback()
        }
      }
  },
  mounted () {
  	this.getDynamicFlight()
  	this.getAirportList()
  	this.getDirectionChoose()
  	this.GetVIPTypeChoose()
  	this.getFlightTask()
  	this.getNumberOfPeopleValidate()
  }
}
</script>
<style scoped>
	.el-date-editor--date {
		width: 85%!important
	}
	.el-select {
		width: 85%!important
	}
	.el-input {
		width: 85%!important
	}
    .el-textarea {
		width: 85%!important
	}
</style>
