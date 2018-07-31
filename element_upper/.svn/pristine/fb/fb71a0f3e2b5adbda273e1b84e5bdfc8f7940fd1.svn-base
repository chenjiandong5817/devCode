<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-input v-model="filters.vipName" placeholder="VIP名字" ></el-input>
        </el-form-item>
        <el-form-item>
          <el-input v-model="filters.title" placeholder="职务" ></el-input>
        </el-form-item>
        <el-form-item>
          <el-input v-model="filters.flight" placeholder="航班号" ></el-input>
        </el-form-item>
        <el-form-item>
          <el-date-picker type="date" v-model="filters.flightDate" placeholder="日期" ></el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getVIPList">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleAdd">新增</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table v-bind:data="vips" highlight-current-row v-loading="listLoading" @selection-change="selsChange" :height="tableHeight" style="width: 100%;">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column type="index" width="60">
        <template scope="scope">
          {{pageSize * (pageNumber - 1) + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="carrier" label="承运人代码" min-width="140">
      </el-table-column>
      <el-table-column prop="flight" label="航班号" min-width="100" sortable>
      </el-table-column>
      <el-table-column prop="numberOfPeople" label="人数" min-width="80">
      </el-table-column>
      <el-table-column prop="vipType" label="VIP类型" min-width="100" :formatter="formatterVipType">
      </el-table-column>
      <el-table-column prop="direction" label="进出港" min-width="80" :formatter="formatterDirection">
      </el-table-column>
      <el-table-column prop="vipDescription" label="VIP描述" min-width="120">
      </el-table-column>
      <el-table-column prop="remark" label="备注" min-width="120" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="flightDate" label="日期" width="120" :formatter="dateFormatedTable">
      </el-table-column>
      <el-table-column label="VIP名字" min-width="100">
        <template scope="scope">
          <div class="content-rowspan" style="border-left: 1px solid #e8ecf2;">
            <div v-for="item1 in scope.row.vipDetails">{{ item1.vipName }}</div>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="职务" min-width="100">
        <template scope="scope">
          <div class="content-rowspan">
            <div v-for="item2 in scope.row.vipDetails">{{ item2.title }}</div>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="舱位" min-width="100">
        <template scope="scope">
          <div class="content-rowspan">
            <div v-for="item3 in scope.row.vipDetails">{{ item3.cabin }}</div>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="座位" min-width="100">
        <template scope="scope">
          <div class="content-rowspan">
            <div v-for="item4 in scope.row.vipDetails">{{ item4.seat }}</div>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="180" fixed="right" >
        <template scope="scope">
          <el-button size="small" @click="handleEdit(scope.$index, scope.row)"><span><i class="el-icon-edit"></i> 编辑 </span></el-button>
          <el-button size="small" @click="handleDel(scope.$index, scope.row)"><span><i class="el-icon-delete"></i> 删除 </span></el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--分页-->
    <pagination :to="getVIPList" ref="page"></pagination>

    <!--新增界面-->
    <common-add-or-update
        type="add"
        :to="API.addVIP().go"
        :callback="getVIPList"
        :labelWidth="100"
        :isDynamicAddViscible="isDynamicAddViscible"
        ref="addForm"></common-add-or-update>
    <!--编辑界面-->
    <common-add-or-update
        title="编辑"
        type="update"
        :to="API.editVIP().go"
        :callback="getVIPList"
        :labelWidth="100"
        :isDynamicAddViscible="isDynamicAddViscible"
        ref="editForm"></common-add-or-update>
    <!--删除窗口-->
    <common-delete
        :to="API.removeVIP().go"
        :callback="getVIPList"
        :labelWidth="100"
        ref="delConfirm"></common-delete>
  </section>
</template>

<script>
  import Util from '../../common/js/util'
  import baseUtil from '../../common/js/base-util'
  import chooseDialog from './../../components/CommChooseGrid'
  import commonAddOrUpdate from './VipForm/AddOrUpdate'
  import commonDelete from './../../components/CommDelete'
  import API from '../../api'
  import Pagination from '../../components/Pagination'

  export default {
    data () {
      return {
        filters: {
          vipName: '',
          title: '',
          flight: '',
          flightDate: new Date()
        },
        vips: [],
        tableHeight: 495,
        listLoading: false,
        airports: [],
        sels: [],
        // 登录用户订阅的机场
        adminAirportChoose: [],
        // 在新的VIP页面该显示哪些输入框
        isDynamicAddViscible: true,
        // VIP类型的下拉框数据
        VIPTypeChoose: [],
        // 进出标志
        flightDirectionChoose: [],
        // 这是一个在添加时需要传给addOrUpdate的值，之所以放在这里是因为在航班动态那边需要调用
        // 函数，并且用到这里面的值
        API: API,
        baseUtil: baseUtil
      }
    },
    computed: {
      pageNumber () {
        return this.$refs['page'].get('pageNumber')
      },
      pageSize () {
        return this.$refs['page'].get('pageSize')
      }
    },
    components: {
      chooseDialog: chooseDialog,
      commonAddOrUpdate: commonAddOrUpdate,
      commonDelete: commonDelete,
      pagination: Pagination
    },
    methods: {
      // 获取用户列表
      getVIPList () {
        this.filters.flightDate = this.dateFormatedPost(this.filters.flightDate)
        console.log('this.filters.flightDate', this.filters.flightDate)
        let para = Object.assign({}, this.filters, this.$refs['page'].queryParam())
        API.getVIPListPage().go(para).then((data) => {
          if (data.ok) {
            this.$refs['page'].set('total', data.attr.data.pager.recordCount)
            this.vips = data.attr.data.list
            this.filters.vipName = ''
            this.filters.title = ''
            this.filters.flight = ''
          } else {
            this.$notify(Util.notifyBody(false, data.msg))
          }
          this.listLoading = false
        })
      },
      selsChange: function (sels) {
        this.sels = sels
      },
      handleCommand: function (command, self) {
        var node = self.$vnode.data.attrs
        /* eslint-disable */
        eval('this.' + command).call(this, node.index, node.row)
        /* eslint-enable */
      },
      // 删除
      handleDel: function (index, row) {
        this.$refs['delConfirm'].del(row.id)
      },
      // 显示新增界面
      handleAdd: function () {
        // 要清空掉原来的数据，数据初始化
        let addFields = {
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
        this.$refs['addForm'].clearFormData()
        this.$refs['addForm'].show(addFields)
      },
      // 显示编辑界面
      handleEdit: function (index, row) {
        let editFields = Util.deepCopy(row)
        this.$refs['editForm'].show(editFields)
      },
      // 获取通用机场的列表
      getAirportList: function () {
          this.airports = Util.typedAirport(this.$cache.array.airports)
          // 筛选出登录用户的订阅机场
          this.adminAirportChoose = []
          var subscibeAirport = JSON.parse(sessionStorage.getItem('userStorage')).user.aiisAirports
          for (var i = 0; i < subscibeAirport.length; i++) {
            for (var j = 0; j < this.airports.length; j++) {
              if (subscibeAirport[i].airportCode === this.airports[j].value) {
                this.adminAirportChoose.push(this.airports[j])
              }
            }
          }
      },
      // 格式化机场的的代码
      formatterAirport: function (row, column, cellValue) {
        for (var i = 0; i < this.adminAirportChoose.length; i++) {
          if (this.adminAirportChoose[i].value === cellValue) {
            return this.adminAirportChoose[i].text1
          }
        }
      },
      // 获取VIP类型下拉框数据
      GetVIPTypeChoose: function () {
        this.VIPTypeChoose = []
        for (var i = 0; i < this.$cache.array.vipranks.length; i++) {
          var item = {}
          item['text'] = this.$cache.array.vipranks[i].description
          item['value'] = this.$cache.array.vipranks[i].rankCode
          this.VIPTypeChoose.push(item)
        }
      },
      formatterVipType: function (data) {
        var result = null
        for (var i = 0; i < this.VIPTypeChoose.length; i++) {
          if (this.VIPTypeChoose[i].value === data.vipType) {
            result = this.VIPTypeChoose[i].text
          }
        }
        return result
      },
      // 获取进出标志的下拉框数据
      getFlightDirectionChoose: function () {
        this.flightDirectionChoose = []
        for (var i = 0; i < this.$cache.array.flightdirections.length; i++) {
          var item = {}
          item['text'] = this.$cache.array.flightdirections[i].description
          item['value'] = this.$cache.array.flightdirections[i].directionCode
          this.flightDirectionChoose.push(item)
        }
      },
      formatterDirection: function (data) {
        var result = null
        for (var i = 0; i < this.flightDirectionChoose.length; i++) {
          if (this.flightDirectionChoose[i].value === data.direction) {
            result = this.flightDirectionChoose[i].text
          }
        }
        return result
      },
      // 转换时间格式
      dateFormatedPost: function (date) {
        // 当数据为undefined时，通过moment得来的数据是当前的数据
        var dateFormatedPost = null
        var moment = null
        moment = require('moment')
        dateFormatedPost = moment(date).format('YYYY-MM-DD')
        return dateFormatedPost
      },
      dateFormatedTable: function (row, column, cellValue) {
        var dateFormatedTable = null
        var moment = null
        moment = require('moment')
        dateFormatedTable = moment(cellValue).format('YYYY-MM-DD')
        return dateFormatedTable
      },
      // 这里多写一个方法，用于和航班动态页面进行沟通
      getDynamicFlightAddData: function (data) {
        // 这个变量只显示在航班动态中添加时需要显示的输入值
        this.isDynamicAddViscible = false
        console.log('data', data)
      }
    },
    mounted () {
      this.GetVIPTypeChoose()
      this.getFlightDirectionChoose()
	    this.getAirportList()
      this.getVIPList()
    }
  }

</script>

<style scoped>
  .content-rowspan {
    margin-left: -18px;
    margin-right: -18px;
    /*border-right: 1px solid #bfcbd9;*/
  }
  .content-rowspan div {
    padding-left: 13px;
    line-height: 40px;
    border-bottom: 1px solid #ECEDEE;
  }
  .content-rowspan div:last-child {
    border-bottom: 0;
    }
</style>
