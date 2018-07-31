<!-- /*
 * @Author: cdroid
 * @Date: 2017-05-25 10:42:17
 * @Last Modified by: mikey.zhaopeng
 * @Last Modified time: 2017-06-27 15:33:48
 * @Description: 通用机场列表界面
 */ -->
<template>
  <section style="background-color: #f6f7f9;">
    <el-row>
      <el-col>
        <el-row style="margin-top: 30px;">
           <el-col :span="1">
             <div style="width: 5px;height: 5px;"></div>
           </el-col>
           <el-col :span="22" class="panel-show">
             <div id="realTimeData" class="real-time-data-home-tag realTimeDataHeight">
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
           <el-col :span="10" class="panel-show">
              <div id="operationIncome" class="operationIncomeHeight">
              </div>
           </el-col>
           <el-col :span="2">
             <div style="width: 5px;height: 5px;"></div>
           </el-col>
           <el-col :span="10" class="panel-show">
              <div id="operationSituation" class="operationSituationHeight">
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
  export default {
    data () {
      return {
      }
    },
    computed: {
    },
    components: {
    },
    methods: {
      initHighChart () {
        // 实时数据
        Highcharts.chart('realTimeData', {
          title: {
            text: '实时数据'
          },
          subtitle: {
            text: ''
          },
          xAxis: {
            categories: ['今日一体机订单', '今日便携式终端订单', '今日专车订单', '今日快线购票数量', '下单未支付数量']
          },
          yAxis: {
            title: {
               text: '数量'
            },
            plotLines: [{
               value: 0,
               width: 1,
               color: '#808080'
            }]
          },
          tooltip: {
            valueSuffix: ''
          },
          legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0
          },
          series: [ {
            name: 'realTimeData',
            data: [ 890, 80, 180, 280, 12 ]
          } ]
        })
        // 营运收入
        Highcharts.chart('operationIncome', {
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
            name: 'operationIncome',
            data: [890, 390, 500]
          } ]
        })
        // 一体机运行情况
        Highcharts.chart('operationSituation', {
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
              name: 'operationSituation',
              data: [
                 {
                    name: '正常运行台数',
                    y: 52,
                    sliced: true,
                    selected: true
                 },
                 ['暂停服务台数', 4]
              ]
           }]
        })
      }
    },
    mounted () {
      this.initHighChart()
    }
  }
</script>

<style scoped>
   .panel-show {
     /*height: 380px;*/
     background-color: #ffffff;
     /*border-width: 1px;*/
     /*border-style: solid;*/
     /*border-color: rgba(121, 121, 121, 1);*/
     /*border-radius: 10px;*/
   }
    .real-time-data-home-tag .highcharts-credits {
      display: none;
    }
    @media screen and (min-height: 0px) and (max-height: 767px) {
      .realTimeDataHeight {
        height: 244px!important
      }
      .operationIncomeHeight {
        height: 244px!important
      }
      .operationSituationHeight {
        height: 244px!important
      }
    }
    @media screen and (min-height: 768px) and (max-height: 1080px) {
      .realTimeDataHeight {
        height: 400px!important
      }
      .operationIncomeHeight {
        height: 400px!important
      }
      .operationSituationHeight {
        height: 400px!important
      }
    }
</style>
