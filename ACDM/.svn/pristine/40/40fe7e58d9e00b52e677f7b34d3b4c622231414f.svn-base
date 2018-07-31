<template>
  <el-row :gutter="10" class="div2" type="flex" align="middle">
    <el-col :span="13" style="height:100%">
      <div class="grid-content box" style="height:100%">
        <ve-histogram :data="chartData" :settings="chartSettings" :extend="chartExtend"  height="100%"  :legend-visible="false" :yAxis="yAxis"></ve-histogram>
      </div>
    </el-col>
    <el-col :span="11" style="height:100%">
      <div class="grid-content box" style="height:100%">
        <ve-pie :data="chartPieData" :settings="chartPieSettings"   height="100%"   :extend="pieExtend" legend-position="left"></ve-pie>
      </div>
    </el-col>
  </el-row>
</template>
<script>
import VePie from 'v-charts/lib/pie'
import VeHistogram from 'v-charts/lib/histogram'
import VeWaterFall from 'v-charts/lib/waterfall'
import echarts from 'echarts/lib/echarts'

export default {
  components: {
    VePie, VeWaterFall, echarts, VeHistogram
  },
  data () {
    return {
      chartData: {
        columns: ['时间段', '辅助', '架次', '占比', '其他'],
        rows: [
          { '时间段': '<5', '架次': 15, '辅助': 4 },
          { '时间段': '5-10', '架次': 12, '辅助': 6 },
          { '时间段': '10-20', '架次': 21, '辅助': 8 },
          { '时间段': '20-30', '架次': 23, '辅助': 10 },
          { '时间段': '30-60', '架次': 23, '辅助': 13 },
          { '时间段': '>90', '架次': 23, '辅助': 13 }
        ]
      },
      chartSettings: {
        metrics: ['辅助', '架次'],
        stack: {'单价': ['辅助', '架次']}
      },
      yAxis: {
        name: '架次',
        splitLine: {
          show: true,
          lineStyle: {
            color: '#292f36'
          }
        },
        axisLine: {
          lineStyle: {
            color: '#bec5d2'
          }
        },
        axisTick: {
          show: false
        }
      },
      chartExtend: {
        series (s) {
          let styleArr = [{
            normal: {
              barBorderColor: 'rgba(0,0,0,0)',
              color: 'rgba(0,0,0,0)'
            },
            emphasis: {
              barBorderColor: 'rgba(0,0,0,0)',
              color: 'rgba(0,0,0,0)'
            }
          },
          {
            normal: {
              color: function (params) {
                var colorList = ['#81d7fd', '#adbefd', '#e2b7ff', '#89dfda', '#ff90a8', '#ffca91']
                return colorList[params.dataIndex]
              }
            }
          }]
          s.forEach((element, index) => {
            element.itemStyle = styleArr[index]
          })
          return s
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: { // 坐标轴指示器，坐标轴触发有效
            type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
          },
          formatter: function (params) {
            var tar = params[1]
            return tar.name + '<br/>' + tar.seriesName + ' : ' + tar.value
          }
        },
        xAxis: {
          name: '时间段',
          nameLocation: 'end',
          data: ['<5', '5-10', '10-20', '20-30', '30-60', '>90'],
          axisLine: {
            lineStyle: {
              color: '#bec5d2'
            }
          }
        },
        grid: {
          top: '30%',
          bottom: '10%',
          right: '12%',
          backgroundColor: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
            // 0% 处的颜色
            offset: 0, color: '#fff' },
          {
            // 100% 处的颜色
            offset: 1, color: 'rgba(221,225,254,1)'
          }], false)
        },
        title: {
          show: true,
          text: '预计到达时间分布',
          textStyle: {
            fontSize: 14,
            color: '#bec5d2'
          }
        }
      },
      chartPieData: {
        columns: ['A', 'B'],
        rows: [
          { 'A': '登机前准备', 'B': 13 },
          { 'A': '登机结束', 'B': 23 },
          { 'A': '催促登机', 'B': 33 },
          { 'A': '允许登机', 'B': 23 }
        ]
      },
      chartPieSettings: {
        dimension: 'A',
        metrics: 'B',
        roseType: 'radius'
      },
      pieExtend: {
        color: ['#ff9493', '#96a2ff', '#3cb8ff', '#72abf7'],
        legend: {
          show: false,
          orient: 'vertical',
          left: 10,
          data: [{
            name: '登机前准备',
            // 强制设置图形为圆。
            icon: 'circle',
            textStyle: {
              color: '#ff9493'
            }
          },
          {
            name: '登机结束',
            icon: 'circle',
            textStyle: {
              color: '#96a2ff'
            }
          },
          {
            name: '催促登机',
            icon: 'circle',
            textStyle: {
              color: '#3cb8ff'
            }
          },
          {
            name: '允许登机',
            icon: 'circle',
            textStyle: {
              color: '#72abf7'
            }
          }]
        },
        series: {
          radius: '82%',
          center: ['50%', '50%'],
          label: {
            show: true,
            normal: {
              show: true,
              textStyle: {
                fontSize: '12'
              },
              formatter: function (params) {
                return params.name + ' : ' + params.value + '(' + params.percent + '%)'
              }
            }
          },
          itemStyle: {
            emphasis: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }
      }
    }
  }
}
</script>
