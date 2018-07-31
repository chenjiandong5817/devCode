<template>
<el-dialog
  :title="title"
  :visible.sync="showAirportSubscribe"
  size="small">
  <el-form :inline="true" class="demo-form-inline">
    <el-form-item>
      <el-row>
        <el-col :span="6">
          <span style="margin-left: 10px">用户名</span>
        </el-col>
        <el-col :span="18">
          <el-input v-model="userName" :disabled="true"></el-input>
        </el-col>
      </el-row>
    </el-form-item>
    <el-form-item>
      <div style="width: 20px">
      </div>
    </el-form-item>
    <el-form-item>
      <el-row>
        <el-col :span="6">
          <span>备选机场</span>
        </el-col>
        <el-col :span="18">
          <el-select v-model="objectSuscribe.selectedAirport" filterable placeholder="请选择" @change="getSelectedAirport">
            <el-option
              v-for="item in defaultAirport"
              top="15%"
              :key="item.value"
              :label="item.text"
              :value="item.value">
            </el-option>
          </el-select>
        </el-col>
      </el-row>
    </el-form-item>
    <el-form-item>
      <el-row>
        <el-col :span="4">
        </el-col>
        <el-col :span="20">
        </el-col>
      </el-row>
    </el-form-item>
  </el-form>
  <template>
    <el-table
      style="width: 97%"
      :data="selectedAirportTableData"
      border
      height="201"
      tooltip-effect="dark">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column type="index" width="60">
      </el-table-column>
      <el-table-column
        prop="text"
        label="机场名称"
        min-width="120">
      </el-table-column>
      <el-table-column
        prop="value"
        label="机场代码"
        width="160">
      </el-table-column>
      <el-table-column label="操作" width="167">
        <template scope="scope">
          <el-button size="small" @click="delSubscribeTableData(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </template>
   <span slot="footer" class="dialog-footer">
    <el-button type="primary" @click="certainSelect">确 定</el-button>
  </span>
</el-dialog>
</template>
<script>
	import util from './../../../common/js/util.js'
	// 引入LOGIN的API
	import { requestLogin } from './../../../api/login'
	export default {
		props: {
			title: {
				type: String,
				default: '机场订阅'
			},
			getAllBaseAirports: {
				type: Function,
				default: function () {}
			},
			getAllDefaultAirport: {
				type: Function,
				default: function () {}
			},
			getAllUsersSubscribes: {
				type: Function,
				default: function () {}
			},
			postUserSubscribe: {
				type: Function,
				default: function () {}
			},
			getLastedUserData: {
				type: Function,
				default: function () {}
			}
		},
		data () {
			return {
			// 是否显示dialog
			showAirportSubscribe: false,
		    // 机场订阅的默认的机场到时候注释掉
	        defaultAirport: [],
	        // 选到的人的ID
	        // selectedUserId: '',
	        // 选择的机场和对应的人的ID
	        objectSuscribe: {
	          selectedUserId: '',
	          selectedAirport: ''
	        },
	        // 所有的默认机场
	        allDefaultAirports: [],
	        // 所有的已经订阅机场的用户
	        // allSubscribedUsers: [],
	        // 点击机场订阅后，所赋值的用户的变量
	        userSubscribedAirport: [],
	        // 显示需要被操作的用户
	        userName: '',
	        // 选中的默认机场的下拉框的数据，用于展示在表格中
	        selectedAirportTableData: [],
	        // 所有的机场的信息
	        allBaseAirport: [],
	        // 调用登录接口时所使用的参数
	        ruleForm: {
	          account: 'admin',
	          checkPass: '123456'
	        }
			}
		},
		methods: {
		  // 显示机场订阅的界面
	      handleAirportSubscribe: function (index, row) {
	        this.objectSuscribe.selectedUserId = row.id
	        this.objectSuscribe.selectedAirport = ''
	        this.userSubscribedAirport = []
	        this.userName = row.name
	        this.userSubscribedAirport = row.aiisAirports
	        this.setTableTypeDatas(this.userSubscribedAirport)
	        this.setSelectsTypeDatas(this.allDefaultAirports)
	        this.showAirportSubscribe = true
	      },
	      // 确认所选的默认的机场
	      certainSelect: function () {
	        this.showAirportSubscribe = false
	        var result = []
	        for (var i = 0; i < this.selectedAirportTableData.length; i++) {
	          var Obj = {}
	          Obj['userId'] = this.objectSuscribe.selectedUserId
	          Obj['airportId'] = this.selectedAirportTableData[i].airportId
	          result.push(Obj)
	        }
	        var params = {}
	        params = {
	          userId: this.objectSuscribe.selectedUserId,
	          newValue: result
	        }
	        this.postUserSubscribe(params).then((res) => {
	          this.$notify(util.notifyBody(res.ok, res.msg))
	          this.getLastedUserData()
	          this.getAllDefaultAirports()
	          // 这边调用登录接口，刷新缓存。对用户的订阅机场缓存进行刷新？
              var loginParams = { username: this.ruleForm.account, password: this.ruleForm.checkPass }
              requestLogin().go(loginParams).then(data => {
                  localStorage.setItem('AdminId', data.attr.user.id)
                  var AdminSubscribeAirportJson = JSON.stringify(data.attr.user.aiisAirports)
                  localStorage.setItem('AdminSubscribeAirportJson', AdminSubscribeAirportJson)
              })
	        })
	      },
	      // 获取所有的默认机场
	      getAllDefaultAirports: function () {
	        let para = {
	          pageNumber: 1,
	          pageSize: 10000,
	          airportcode: ''
	        }
	        this.getAllDefaultAirport(para).then((data) => {
	          if (data.ok) {
	            this.allDefaultAirports = data.attr.data.list
	            // this.getUserDefaultSubscribeAirports()
	            this.getBaseAirports()
	          } else {
	            this.$notify(util.notifyBody(false, data.msg))
	          }
	        })
	      },
	      // 获取通用机场管理的全部机场，这个函数用于根据三字码获得机场的名称
	      getBaseAirports: function () {
	        let para = {
	          iatacode: '',
	          icaocode: '',
	          pageNumber: 1,
	          pageSize: 10000
	        }
	        this.getAllBaseAirports(para).then((data) => {
	          this.allBaseAirport = data.attr.data.list
	        })
	      },
	      // 对数据进行操作，只剩下除去这个用户外的默认机场，字段是机场ID，机场名称，三字码
	      setTableTypeDatas: function (data) {
	        var result = []
	        for (var i = 0; i < data.length; i++) {
	          var Obj = {}
	          Obj['airportId'] = data[i].id
	          Obj['text'] = data[i].airport.airportcnname
	          Obj['value'] = data[i].airportCode
	          result.push(Obj)
	        }
	        this.selectedAirportTableData = result
	      },
	      // 设置下下拉框的数据，要排除掉已经在table里的数据
	      setSelectsTypeDatas: function (data) {
	        var result = []
	        // 这个循环只是设置出有对应属性的下拉框数据defaultAirport
	        for (var i = 0; i < data.length; i++) {
	          var Obj = {}
	          Obj['airportId'] = data[i].id
	          Obj['text'] = data[i].airport
	          Obj['value'] = data[i].airportCode
	          result.push(Obj)
	        }
	        // 这个循环的作用是除去这个用户已经用的机场订阅，然后数据给defaultAirport
	        for (var j = 0; j < result.length; j++) {
	          for (var k = 0; k < this.selectedAirportTableData.length; k++) {
	            if (result[j].airportId === this.selectedAirportTableData[k].airportId) {
	              result[j]['del'] = 'null'
	              this.selectedAirportTableData[k]['del'] = 'null'
	            }
	          }
	        }
	        var resultSelected = []
	        for (var h = 0; h < result.length; h++) {
	          if (result[h].del !== 'null') {
	            resultSelected.push(result[h])
	          }
	        }
	        this.defaultAirport = resultSelected
	        this.defaultAirport = this.getTextName(this.defaultAirport)
	      },
	      // 获取选中的值并且组成一个数组
	      getSelectedAirport: function (value) {
	          var result = []
	          this.objectSuscribe.selectedAirport = ''
	          // 这个循环用于获取显示表格的数据以及将选中的数据从这个数组中除掉
	          for (var i = 0; i < this.defaultAirport.length; i++) {
	            if (this.defaultAirport[i].value === value) {
	              this.selectedAirportTableData.push(this.defaultAirport[i])
	            }
	            if (this.defaultAirport[i].value !== value) {
	              result.push(this.defaultAirport[i])
	            }
	          }
	          this.defaultAirport = result
	      },
	      // 删除机场订阅的表格的数据
	      delSubscribeTableData: function (index, row) {
	        var delResult = []
	        for (var i = 0; i < this.selectedAirportTableData.length; i++) {
	          if (row.value !== this.selectedAirportTableData[i].value) {
	            delResult.push(this.selectedAirportTableData[i])
	          }
	          if (row.value === this.selectedAirportTableData[i].value) {
	            this.defaultAirport.push(this.selectedAirportTableData[i])
	          }
	        }
	        this.selectedAirportTableData = delResult
	      },
	      // 通过这个函数使下拉框以及table有中文名称
	      getTextName: function (data) {
	        for (var i = 0; i < data.length; i++) {
	          for (var j = 0; j < this.allBaseAirport.length; j++) {
	            if (data[i].value === this.allBaseAirport[j].iatacode) {
	              data[i].text = this.allBaseAirport[j].airportcnname
	            }
	          }
	        }
	        return data
	      }
		},
		mounted () {
			this.getAllDefaultAirports()
		}
	}
</script>
<style>
	
</style>
