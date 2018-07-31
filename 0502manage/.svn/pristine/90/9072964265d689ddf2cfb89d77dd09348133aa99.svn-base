<!-- /*
 * @Author: cdroid
 * @Date: 2017-05-25 10:42:17
 * @Last Modified by: mikey.zhaopeng
 * @Last Modified time: 2017-06-27 15:33:48
 * @Description: 通用机场列表界面
 */ -->
<!-- 代码模块化预备 -->
<template>
  <section style="background-color: #f6f7f9;">
    <el-row>
      <el-col>
        <el-row style ="margin-top: 0;">
          <el-col :span="1">
             <div style="width: 5px;height: 5px;"></div>
          </el-col>
          <el-col :span="17" class="home-select-city real-time-data-home-operate">
            <div style="display: inline-block;font-size: 15px;">
              所属区域
            </div>
            <div style="display: inline-block;margin-left: 10px;">
              <el-select v-model="selectedCity" clearable placeholder="请选择" @change="toSelectCity">
                <el-option
                  v-for="item in allCities"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </div>
            <div style="display: inline-block;margin-left: 10px;">
              <el-button type="primary" @click="refreshHandleData">刷新</el-button>
            </div>
          </el-col>
          <el-col :span="5" class="home-select-time real-time-data-home-operate">
            <div style="display: inline-block;" class="home_check">
              <el-checkbox v-model="isAuthRefresh" @change="authRefresh"></el-checkbox>
            </div>
            <div style="display: inline-block;margin-left: 10px;">
              自动刷新
            </div>
            <div style="display: inline-block;margin-left: 10px;">
              <el-select v-model="selectedTimeout" clearable placeholder="请选择" @change="toSelectTimeout">
                <el-option
                  v-for="item in allTimeOut"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </div>
          </el-col>
          <el-col :span="1">
             <div style="width: 5px;height: 5px;"></div>
          </el-col>
        </el-row>
        <el-row style="margin-top: 30px;">
           <el-col :span="1">
             <div style="width: 5px;height: 5px;"></div>
           </el-col>
           <el-col :span="22" class="panel-show">
             <div id="realTimeDataHome" class="real-time-data-home-tag realTimeDataHomeHeight">
             </div>
           </el-col>
           <el-col :span="1">
              <div style="width: 5px;height: 5px;"></div>
           </el-col>
        </el-row>
        <el-row style="margin-top: 20px;">
           <el-col :span="1">
             <div style="width: 5px;height: 5px;"></div>
           </el-col>
           <el-col :span="10" class="panel-show">
              <div id="operationIncomeHome" class="operationIncomeHomeHeight">
              </div>
           </el-col>
           <el-col :span="2">
             <div style="width: 5px;height: 5px;"></div>
           </el-col>
           <el-col :span="10" class="panel-show">
              <div id="operationSituationHome" class="operationSituationHomeHeight">
              </div>
           </el-col>
           <el-col :span="1">
             <div style="width: 5px;height: 5px;"></div>
           </el-col>
        </el-row>
      </el-col>
    </el-row>
  </section>
</template>

<script>
import Highcharts from 'highcharts'
import API from './../../api'
import Util from './../../common/js/util'
  export default {
    data () {
      return {
        realData: [ 890, 180, 280, 12 ],
        operationIncome: [ 890, 390, 500 ],
        operationSituation: [
          {
              name: '正常运行台数',
              y: 52,
              sliced: true,
              selected: true
          },
          ['暂停服务台数', 4] ],
        // 所有城市的选择
        allCities: [
          {
            label: '全部',
            value: ''
          },
          {
            label: '福州区域',
            value: '福州'
          },
          {
            label: '厦门区域',
            value: '厦门'
          }
        ],
        // 已经选中的城市,厦门为默认的城市
        selectedCity: '',
        // 是否自动刷新默认为false
        isAuthRefresh: false,
        // 可选的时间间隔
        allTimeOut: [
          {
            label: '请选择',
            value: null
          },
          {
            label: '30s',
            value: 30000
          },
          {
            label: '1min',
            value: 60000
          },
          {
            label: '10min',
            value: 600000
          },
          {
            label: '30min',
            value: 180000
          }
        ],
        // 已经选的时间间隔
        selectedTimeout: null,
        // 所有的票的数量
        allTicketsCount: null,
        // 重复刷新函数的变量
        functionParams: null
      }
    },
    computed: {
    },
    components: {
    },
    methods: {
      initHighChart () {
        // 实时数据
        Highcharts.chart('realTimeDataHome', {
          chart: {
            type: 'bar'
          },
          title: {
            text: '实时数据'
          },
          subtitle: {
            text: '数据来源：实际情况'
          },
          xAxis: {
            categories: ['今日一体机订单', '今日专车订单', '今日专线订单', '购票数量', '下单未支付数量'],
            title: {
               text: null
            }
          },
          yAxis: {
            min: 0,
            title: {
               text: '单位 (单)',
               align: 'high'
            },
            labels: {
               overflow: 'justify'
            }
          },
          tooltip: {
            valueSuffix: '单'
          },
          plotOptions: {
            bar: {
               dataLabels: {
                  enabled: true
               }
            }
          },
          legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'top',
            x: -40,
            y: 100,
            floating: true,
            borderWidth: 1,
            backgroundColor: ((Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'),
            shadow: true,
            enabled: false
          },
          credits: {
            enabled: false
          },
          series: [
            { name: '数量', data: this.realData }
          ]
        })
        // 营运收入
        Highcharts.chart('operationIncomeHome', {
          chart: {
            type: 'column'
          },
          title: {
            text: '营运收入'
          },
          subtitle: {
            text: ''
          },
          xAxis: {
            categories: ['今天营业总收入', '专车收入', '快线收入'],
            crosshair: true
          },
          yAxis: {
            min: 0,
            title: {
               text: '人民币 (rmb)'
            }
          },
          tooltip: {
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
               '<td style="padding:0"><b>{point.y:.1f} rmb</b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
          },
          plotOptions: {
            column: {
               pointPadding: 0.2,
               borderWidth: 0
            }
          },
          credits: {
            enabled: false
          },
          series: [ {
            name: '营运收入',
            data: this.operationIncome
          } ]
        })
        // 一体机运行情况
        Highcharts.chart('operationSituationHome', {
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false
            },
            title: {
                text: '一体机运行情况'
            },
            tooltip: {
                pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
            },
            credits: {
                enabled: false
            },
            plotOptions: {
              pie: {
                  allowPointSelect: true,
                  cursor: 'pointer',
                  dataLabels: {
                  enabled: true,
                  formatter: function () {
                    return '<span style="color: ' + this.point.color + '"> 值：' + this.y + '，占比' + this.percentage.toFixed(1) + '%</span>'
                  },
                  style: {
                    color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                  }
                }
              }
           },
            series: [{
              type: 'pie',
              name: '一体机运行情况',
              data: this.operationSituation
           }]
        })
      },
      // 一体机运行情况
      // 正常运行台数
      getRunDevices () {
        var area = this.selectedCity
        API.devicesRun().go(area).then((data) => {
          if (data.status === 1) {
            this.operationSituation[0].y = data.data
            this.getSuspendDevices()
          } else {
            this.$notify(Util.notifyBody(false, data.message))
          }
        })
      },
      // 暂停运行台数
      getSuspendDevices () {
        var area = this.selectedCity
        API.devicesSuspend().go(area).then((data) => {
          if (data.status === 1) {
            this.operationSituation[1][1] = data.data
            this.getTicketsCount()
          } else {
            this.$notify(Util.notifyBody(false, data.message))
          }
        })
      },
      // 获取总的订单数给购票数量
      getTicketsCount () {
        var params = {
          area: this.selectedCity,
          dataType: 0
        }
        API.queryAllOrder().go(params).then((data) => {
          if (data.status === 1) {
            this.allTicketsCount = data.data.totalCount
            this.getTodayOrder()
          } else {
            this.$notify(Util.notifyBody(false, data.message))
          }
        })
      },
      // 获得当日订单概况
      getTodayOrder () {
        var params = {
          area: this.selectedCity,
          dataType: 3
        }
        API.queryAllOrder().go(params).then((data) => {
          if (data.status === 1) {
            // 实时数据
            this.realData[0] = data.data.totalCount
            this.realData[1] = 0
            this.realData[2] = data.data.totalCount
            this.realData[3] = this.allTicketsCount
            this.realData[4] = data.data.totalCount - data.data.completeCount
            // 营运收入
            this.operationIncome[0] = data.data.totalMoney
            this.operationIncome[1] = 0
            this.operationIncome[2] = data.data.totalMoney
            this.initHighChart()
          } else {
            this.$notify(Util.notifyBody(false, data.message))
          }
        })
      },
      // 切换城市，默认为厦门
      toSelectCity () {
        this.getRunDevices()
      },
      // 手动刷新,它的区域值根据所属区域的下拉框而定
      refreshHandleData () {
        this.getRunDevices()
      },
      // 是否要自动刷新打钩的话是，不打钩的话不是
      authRefresh () {
        // 点击打钩的事件
        if (this.isAuthRefresh === false) {
          clearInterval(this.functionParams)
        }
        if (this.isAuthRefresh === true) {
          this.toSelectTimeout()
        }
      },
      // 选中时间间隔后的钩子函数
      toSelectTimeout () {
        clearInterval(this.functionParams)
        if (this.isAuthRefresh && this.selectedTimeout !== null) {
          this.functionParams = setInterval(() => {
            this.getRunDevices()
          }, this.selectedTimeout)
        }
      }
    },
    mounted () {
      this.getRunDevices()
    }
  }
</script>

<style>
   .panel-show {
     /*height: 380px;*/
     background-color: #ffffff;
   }
   .real-time-data-home-tag .highcharts-credits {
  	 display: none;
   }
    @media screen and (min-height: 0px) and (max-height: 767px) {
      .realTimeDataHomeHeight {
        height: 224px!important;
      }
      .operationIncomeHomeHeight {
        height: 224px!important;
      }
      .operationSituationHomeHeight {
        height: 224px!important;
      }
      .home-select-city .el-select {
        width: 80px!important;
        margin-top: 15px!important;
      }
      .real-time-data-home-operate {
        height: 50px!important;
      }
      .home-select-time .el-select {
        width: 90px!important;
        margin-top: 15px!important;
      }
      .home_check {
        margin-left: 30px!important;
      }
    }
    @media screen and (min-height: 768px) and (max-height: 1080px) {
      .realTimeDataHomeHeight {
        height: 375px!important;
      }
      .operationIncomeHomeHeight {
        height: 375px!important;
      }
      .operationSituationHomeHeight {
        height: 375px!important;
      }
      .home-select-city .el-select {
        width: 110px!important;
        margin-top: 20px!important;
      }
      .real-time-data-home-operate {
        height: 50px!important;
      }
      .home-select-time .el-select {
        width: 110px!important;
        margin-top: 20px!important;
      }
      .home_check {
        margin-left: 100px!important;
      }
    }
    /*做1080分辨率以上的适配*/
    @media screen and (min-height: 1081px) {
      .realTimeDataHomeHeight {
        height: 375px!important;
      }
      .operationIncomeHomeHeight {
        height: 375px!important;
      }
      .operationSituationHomeHeight {
        height: 375px!important;
      }
      .home-select-city .el-select {
        width: 110px!important;
        margin-top: 20px!important;
      }
      .real-time-data-home-operate {
        height: 50px!important;
      }
      .home-select-time .el-select {
        width: 110px!important;
        margin-top: 20px!important;
      }
      .home_check {
        margin-left: 100px!important;
      }
    }
</style>
