<!-- /*
 * @Author: cdroid
 * @Date: 2017-05-25 10:42:17
 * @Last Modified by: mikey.zhaopeng
 * @Last Modified time: 2017-06-27 15:33:48
 * @Description: 通用机场列表界面
 */ -->
<template>
  <section>
    <!-- 订单概况 -->
    <el-row class="data-row-first" style="position: relative">
      <el-col :span="2">
        <div style="width: 5px;height: 5px;">
        </div>
      </el-col>
      <el-col :span="20" style="margin-top: 15px;" class="data-panel-first">
        <div>
          <el-row class="title-panel">
            <el-col :span="6">
              <div class="title">
                <p>
                  <span>订单概况</span>
                </p>
              </div>
            </el-col>
            <el-col :span="18">
                <div class="operation-type">
                  <el-radio-group v-model="status" size="medium" @change="getChangeData">
                    <el-radio-button label="总" ></el-radio-button>
                    <el-radio-button label="月" ></el-radio-button>
                    <el-radio-button label="周" ></el-radio-button>
                    <el-radio-button label="日" ></el-radio-button>
                  </el-radio-group>
                </div>
            </el-col>
          </el-row>
          <el-row >
            <el-col :span="2">
              <div style="width: 5px;height: 5px;"></div>
            </el-col>
            <el-col :span="4" v-for="item in orderOverview" :key="item.id">
              <el-row style="margin-top: 10px;">
                <el-col>
                  <div class="title title-height-first" style="display:table-cell;vertical-align:middle;">
                    <span>{{ item.title }}</span>
                  </div>
                </el-col>
              </el-row>
              <el-row>
                <el-col>
                  <div class="title result-height-first" style="display:table-cell;vertical-align:middle;">
                    <span>{{ item.result }}</span>
                  </div>
                </el-col>
              </el-row>
            </el-col>
          </el-row>
        </div>
      </el-col>
      <el-col :span="2">
        <div style="width: 5px;height: 5px;">
        </div>
      </el-col>
    </el-row>
    <!-- 未支付订单 -->
    <el-row class="data-row" style="position: relative">
      <el-col :span="2">
        <div style="width: 5px;height: 5px;">
        </div>
      </el-col>
      <el-col :span="20" style="margin-top: 15px;" class="data-panel">
        <div>
          <el-row class="title-panel">
            <el-col :span="6">
              <div class="title">
                <p>
                  <span>未支付订单</span>
                </p>
              </div>
            </el-col>
            <el-col :span="18">
            </el-col>
          </el-row>
          <el-row style="margin-top: 10px;">
            <el-col :span="24" class="table-border" style="padding-left: 10px;padding-right: 10px;">
              <el-table :data="unPaidDatas" highlight-current-row style="width: 100%" height="135">
                <el-table-column prop="orderType" label="订单类型" min-width="120">
                </el-table-column>
                <el-table-column prop="buyTime" label="购票时间" min-width="180" :formatter="baseUtil.formatterDated">
                </el-table-column>
                <el-table-column prop="sellstationId" label="售票点" min-width="120" :formatter="formatterSellStationId">
                </el-table-column>
                <el-table-column prop="machineid" label="终端编号" min-width="120">
                </el-table-column>
                <el-table-column prop="price" label="金额" min-width="80">
                </el-table-column>
                <el-table-column prop="busId" label="大巴车次" min-width="100">
                </el-table-column>
                <el-table-column label="操作" width="180" fixed="right" >
                  <template slot-scope="scope">
                    <el-button size="small" @click="close(scope.$index, scope.row)"><span><i class="el-icon-circle-close"></i> 关闭 </span></el-button>
                    <el-button size="small" @click="viewUnPay(scope.$index, scope.row)"><span><i class="el-icon-view"></i> 查看 </span></el-button>
                  </template>
                </el-table-column>
              </el-table>
            </el-col>
          </el-row>
        </div>
        <!-- 用于分页 -->
        <div>
          <el-row style="position: relative;top: 70px;">
             <pagination :to="getUnPaids" ref="pageUnpaid"></pagination>
          </el-row>
        </div>
      </el-col>
      <el-col :span="2">
        <div style="width: 5px;height: 5px;">
        </div>
      </el-col>
    </el-row>
    <!-- 订单流水 -->
    <el-row class="data-row" style="position: relative">
      <el-col :span="2">
        <div style="width: 5px;height: 5px;">
        </div>
      </el-col>
      <el-col :span="20" style="margin-top: 15px;" class="data-panel">
        <div>
          <el-row class="title-panel">
            <el-col :span="6">
              <div class="title">
                <p>
                  <span>订单流水</span>
                </p>
              </div>
            </el-col>
            <el-col :span="18">
            </el-col>
          </el-row>
          <el-row style="margin-top: 10px;">
            <el-col :span="24" class="table-border" style="padding-left: 10px;padding-right: 10px;">
              <el-table :data="orderWaterDatas" highlight-current-row style="width: 100%" height="135">
                <el-table-column prop="orderType" label="订单类型" min-width="120">
                </el-table-column>
                <el-table-column prop="buyTime" label="购票时间" min-width="180" :formatter="baseUtil.formatterDated">
                </el-table-column>
                <el-table-column prop="sellstationId" label="售票点" min-width="120" :formatter="formatterSellStationId">
                </el-table-column>
                <el-table-column prop="machineid" label="终端编号" min-width="120">
                </el-table-column>
                <el-table-column prop="price" label="金额" min-width="80">
                </el-table-column>
                <el-table-column prop="busId" label="大巴车次" min-width="100">
                </el-table-column>
                <el-table-column label="操作" width="100" fixed="right" >
                  <template slot-scope="scope">
                    <el-button size="small" @click="viewOrder(scope.$index, scope.row)"><span><i class="el-icon-view"></i> 查看 </span></el-button>
                  </template>
                </el-table-column>
              </el-table>
            </el-col>
          </el-row>
        </div>
        <!-- 用于分页 -->
        <div>
          <el-row style="position: relative;top: 70px;">
             <pagination :to="getOrderWaters" ref="pageOrderWater"></pagination>
          </el-row>
        </div>
      </el-col>
      <el-col :span="2">
        <div style="width: 5px;height: 5px;">
        </div>
      </el-col>
    </el-row>
    <!-- 查看订单详情页面 -->
    <el-dialog title="订单详情" :visible.sync="dialogTableVisible">
      <el-table :data="detailDatas">
        <el-table-column property="passengerName" label="乘客名" width="150"></el-table-column>
        <el-table-column property="passengerIDCard" label="证件号" min-width="250"></el-table-column>
        <el-table-column property="passengerPhone" label="乘客电话" width="150"></el-table-column>
        <el-table-column property="passengerCardType" label="证件类型" width="150"></el-table-column>
        <el-table-column property="ticketType" label="票类型" width="150" :formatter="getTicketTypeName"></el-table-column>
        <el-table-column property="ticketPrice" label="票价" width="150"></el-table-column>
        <el-table-column property="passengerSex" label="性别" width="150"></el-table-column>
        <el-table-column property="childrenCardId" label="儿童证件号" width="150"></el-table-column>
        <el-table-column property="childrenPassenger" label="儿童姓名" width="150"></el-table-column>
      </el-table>
    </el-dialog>
  </section>
</template>

<script>
  import util from '../../common/js/util'
  import baseUtil from '../../common/js/base-util'
  import Pagination from '../../components/Pagination'
  import API from './../../api'
  export default {
    data () {
      return {
        status: null,
        // 显示在页面上的数据
        orderOverview: null,
        // 总的数据
        orderSurveyData: [
          {
            id: 0,
            title: '总订单数',
            totalOrders: null
          },
          {
            id: 1,
            title: '已完成订单',
            orderCompleted: null
          },
          {
            id: 2,
            title: '日均订单数',
            averageDailyOrders: null
          },
          {
            id: 3,
            title: '人均支付',
            perPayment: null
          }
        ],
        // 未支付订单表格的数据
        unPaidDatas: [],
        // 格式化售票站点
        sellStations: [],
        // 订单流水
        orderWaterDatas: [],
        // 是否显示详情页面的变量
        dialogTableVisible: false,
        // 详情页面的表格的变量
        detailDatas: null,
        API: API,
        baseUtil: baseUtil
      }
    },
    computed: {
      pageNumberUnPay () {
        return this.$refs['pageUnpaid'].get('pageNumber')
      },
      pageSizeUnPay () {
        return this.$refs['pageUnpaid'].get('pageSize')
      },
      pageNumberPaied () {
        return this.$refs['pageOrderWater'].get('pageNumber')
      },
      pageSizePaied () {
        return this.$refs['pageOrderWater'].get('pageSize')
      }
    },
    components: {
      pagination: Pagination
    },
    methods: {
      // 订单概况的数据
      getData () {
        // 强制让页面刷新后再进行赋值
        this.status = '月'
        this.getChangeData(this.status)
      },
      // 处理数据，为了在页面上进行显示
      resolveData (value) {
        var result = null
        result = util.deepCopy(value)
        for (var i = 0; i < result.length; i++) {
          var key = Object.keys(result[i])[2]
          result[i]['result'] = result[i][key]
        }
        return result
      },
      getChangeData (value) {
        this.$nextTick(() => {
          var dataType = null
          if (value === '总') {
            dataType = 0
          }
          if (value === '月') {
            dataType = 1
          }
          if (value === '周') {
            dataType = 2
          }
          if (value === '日') {
            dataType = 3
          }
          API.queryAllOrder().go(dataType).then((data) => {
            if (data.status === 1) {
              console.log('data.data', data.data)
              this.orderSurveyData[0].totalOrders = data.data.totalCount
              this.orderSurveyData[1].orderCompleted = data.data.completeCount
              this.orderSurveyData[2].averageDailyOrders = data.data.perdayCount
              this.orderSurveyData[3].perPayment = data.data.perOrderMoney
              console.log('this.orderSurveyData', this.orderSurveyData)
              this.orderOverview = this.resolveData(this.orderSurveyData)
              console.log('this.orderOverview', this.orderOverview)
            } else {
              this.$notify(util.notifyBody(false, data.message))
            }
            this.listLoading = false
          })
        })
      },
      // 未支付订单数据的获取
      getUnPaids () {
        let para = {
          pageNum: this.pageNumberUnPay,
          pageSize: this.pageSizeUnPay
        }
        this.listLoading = true
        API.getUnPayOrderListPage().go(para).then((data) => {
          if (data.status === 1) {
            this.$refs['pageUnpaid'].set('total', data.data.total)
            this.unPaidDatas = data.data.list
          } else {
            this.$notify(util.notifyBody(false, data.message))
          }
          this.listLoading = false
        })
      },
      // 订单流水的数据的获取
      getOrderWaters () {
        let para = {
          pageNum: this.pageNumberPaied,
          pageSize: this.pageSizePaied
        }
        this.listLoading = true
        API.getPayOrderListPage().go(para).then((data) => {
          if (data.status === 1) {
            this.$refs['pageOrderWater'].set('total', data.data.total)
            this.orderWaterDatas = data.data.list
          } else {
            this.$notify(util.notifyBody(false, data.message))
          }
          this.listLoading = false
        })
        this.$refs['pageOrderWater'].set('total', 16)
      },
      close (index, row) {
        var para = {
          orderId: row.orderId,
          pass: row.pass,
          sellStationId: row.sellstationId
        }
        API.cancelUnorder().go(para).then((data) => {
          if (data.status === 1) {
            this.$notify(util.notifyBody(true, data.message))
          } else {
            this.$notify(util.notifyBody(false, data.message))
          }
          this.listLoading = false
        })
      },
      viewUnPay (index, row) {
        this.detailDatas = null
        this.dialogTableVisible = true
        // 需要请求
        API.queryOrder().go(row.orderId).then((data) => {
          if (data.status === 1) {
            this.$nextTick(() => {
              this.detailDatas = data.data.seats
            })
          } else {
            this.$notify(util.notifyBody(false, data.message))
          }
          this.listLoading = false
        })
      },
      viewOrder (index, row) {
        this.detailDatas = null
        this.dialogTableVisible = true
        // 需要请求
        API.queryOrder().go(row.orderId).then((data) => {
          if (data.status === 1) {
            this.$nextTick(() => {
              this.detailDatas = data.data.seats
            })
          } else {
            this.$notify(util.notifyBody(false, data.message))
          }
          this.listLoading = false
        })
      },
      formatterSellStationId (row, column, cellValue) {
        var result = null
        for (var i = 0; i < this.sellStations.length; i++) {
          if (this.sellStations[i].value === cellValue) {
            result = this.sellStations[i].text
          }
        }
        return result
      },
      getSellStation () {
        let para = {
          sellStationName: '',
          pageNum: 1,
          pageSize: 10000
        }
        this.listLoading = true
        API.getSellStationListPage().go(para).then((data) => {
          if (data.status === 1) {
            for (var i = 0; i < data.data.list.length; i++) {
              var item = {}
              item['text'] = data.data.list[i].sellStationName
              item['value'] = data.data.list[i].sellStationId
              this.sellStations.push(item)
            }
          } else {
            this.$notify(util.notifyBody(false, data.msg))
          }
          this.listLoading = false
        })
      },
      getTicketTypeName (row, column, cellValue) {
        if (cellValue === '1') {
          return '全票'
        }
        if (cellValue === '2') {
          return '半票'
        }
        if (cellValue === '3') {
          return '携童票'
        } else {
          return null
        }
      }
    },
    mounted () {
      this.getData()
      this.getUnPaids()
      this.getOrderWaters()
      this.getSellStation()
    }
  }
</script>

<style scoped>
  .bg-purple {
    background: #e4e4e4;
    margin-top: 10px;
  }
  .title-panel {
    margin-top: 0;
    background: #e4e4e4;
    border-radius: 10px 10px 0 0;
  }
  .operation-type {
    position: relative;
  }
  .operation-type .el-radio-group {
    position: absolute;
    top: 8px;
    right: 16px;
  }
  .table-border .el-table {
    border-right: 1px solid #dfe6ec;
  }
  .toolbar-bottom {
    border: 0;
    z-index: 90;
    background: #fff;
    padding: 10px;
    position: absolute;
    /*bottom: -70px;*/
    width: 100%!important;
  }
  .data-panel {
    height: 210px;
    border-width: 1px;
    border-style: solid;
    border-color: rgba(121, 121, 121, 1);
    border-radius: 10px;
  }
  @media screen and (min-height: 0px) and (max-height: 767px) {
      .title {
        font-family: 'PingFangSC-Regular', 'PingFang SC';
        font-weight: 400;
        font-style: normal;
        font-size: 15px;
        margin-left: 20px;
      }
      .data-row {
        height: 280px;
      }
      .data-row-first {
        height: 170px;
      }
      .data-panel-first {
        height: 140px;
        border-width: 1px;
        border-style: solid;
        border-color: rgba(121, 121, 121, 1);
        border-radius: 10px;
      }
      .title-height-first {
        height: 30px;
      }
      .result-height-first {
        height: 30px;
      }
  }
  @media screen and (min-height: 768px) and (max-height: 1080px) {
      .title {
        font-family: 'PingFangSC-Regular', 'PingFang SC';
        font-weight: 400;
        font-style: normal;
        font-size: 18px;
        margin-left: 20px;
      }
      .data-row {
        height: 290px;
      }
      .data-row-first {
        height: 290px;
      }
      .data-panel-first {
        height: 210px;
        border-width: 1px;
        border-style: solid;
        border-color: rgba(121, 121, 121, 1);
        border-radius: 10px;
      }
      .title-height-first {
        height: 50px;
      }
      .result-height-first {
        height: 50px;
      }
  }
</style>
