<template>
  <el-row :gutter="10"  class="div3" type="flex" align="middle">
    <el-col :span="9" style="height:100%">
      <div class="grid-content box collapseDiv">
        <el-collapse v-model="activeName" accordion size="small" @change="handleChange" ref="collapse">
          <el-collapse-item title="航班执行情况" name="1" >
            <my-table
              :data="tableData"
              :col-configs="fSituation">
              <el-table-column slot="opt">
                <template slot-scope="{row, $index}">
                  <span :style="{backgroundColor:colorData[$index]}" class="circlemin"></span>
                  <span style="margin-left: 10px">{{ row.type }}</span>
                </template>
              </el-table-column>
            </my-table>
          </el-collapse-item>
          <el-collapse-item title="靠桥统计" name="2">
            <my-table
              :data="tableData"
              :col-configs="bridge">
              <el-table-column slot="opt">
                <template slot-scope="{row, $index}">
                  <span :style="{backgroundColor:colorData[$index]}" class="circlemin"></span>
                  <span style="margin-left: 10px">{{ row.type }}</span>
                </template>
              </el-table-column>
            </my-table>
          </el-collapse-item>
          <el-collapse-item title="航班延误情况" name="3">
            <my-table
              :data="tableData"
              :col-configs="delay">
              <el-table-column slot="opt">
                <template slot-scope="{row, $index}">
                  <span :style="{backgroundColor:colorData[$index]}" class="circlemin"></span>
                  <span style="margin-left: 10px">{{ row.type }}</span>
                </template>
              </el-table-column>
            </my-table>
          </el-collapse-item>
          <el-collapse-item title="航班放行正常率" name="4">
            <my-table
              :data="tableData"
              :col-configs="normalRate">
              <el-table-column slot="opt">
                <template slot-scope="{row, $index}">
                  <span :style="{backgroundColor:colorData[$index]}" class="circlemin"></span>
                  <span style="margin-left: 10px">{{ row.type }}</span>
                </template>
              </el-table-column>
            </my-table>
          </el-collapse-item>
          <el-collapse-item title="航班任务分布" name="5">
            <div class="taskDistribution">
              <div><span class="fontstyle"><span class="circlemiddle" style="backgroundColor:#0098f0"></span>正班</span><span>502</span></div>
              <div><span class="fontstyle"><span class="circlemiddle" style="backgroundColor:#0098f0"></span>备降</span><span>20</span></div>
              <div><span class="fontstyle"><span class="circlemiddle" style="backgroundColor:#0098f0"></span>本训</span><span>60</span></div>
            </div>
            <div class="taskDistribution">
              <div><span class="fontstyle"><span class="circlemiddle" style="backgroundColor:#ffc397"></span>正班</span><span>502</span></div>
              <div><span class="fontstyle"><span class="circlemiddle" style="backgroundColor:#ffc397"></span>备降</span><span>20</span></div>
              <div><span class="fontstyle"><span class="circlemiddle" style="backgroundColor:#ffc397"></span>本训</span><span>60</span></div>
            </div>
              <div class="taskDistribution">
              <div><span class="fontstyle"><span class="circlemiddle" style="backgroundColor:#ffc397"></span>正班</span><span>502</span></div>
              <div><span class="fontstyle"><span class="circlemiddle" style="backgroundColor:#ffc397"></span>备降</span><span>20</span></div>
              <div><span class="fontstyle"><span class="circlemiddle" style="backgroundColor:#ffc397"></span>本训</span><span>60</span></div>
            </div>
          </el-collapse-item>
        </el-collapse>
      </div>
    </el-col>
    <el-col :span="15" style="height:100%">
      <div class="grid-content box" style="height:100%">
        <ve-line :data="chartData" :settings="chartSettings" height="100%" :extend="extend" :yAxis="yAxis" ></ve-line>
      </div>
    </el-col>
  </el-row>
</template>
<script>
import VeLine from 'v-charts/lib/line'
import echarts from 'echarts/lib/echarts'
import MyTable from './myTable'

export default {
  components: {
    VeLine, MyTable
  },
  data () {
    return {
      height: '150',
      color: '#bec5d2',
      colorData: ['#0098f0', '#89dfda', '#96a2ff', '#ff9493', '#96ffa2'],
      chartData: {
        columns: ['日期', 'ACH', 'SCH', '占比', '其他'],
        rows: [
          { 'ACH': 1523, '日期': '1月1日', 'SCH': 1523, '占比': 0.12, '其他': 100 },
          { 'ACH': 1223, '日期': '1月2日', 'SCH': 1523, '占比': 0.345, '其他': 100 },
          { 'ACH': 2123, '日期': '1月3日', 'SCH': 1523, '占比': 0.7, '其他': 100 },
          { 'ACH': 4123, '日期': '1月4日', 'SCH': 1523, '占比': 0.31, '其他': 100 },
          { 'ACH': 3123, '日期': '1月5日', 'SCH': 1523, '占比': 0.12, '其他': 100 },
          { 'ACH': 7123, '日期': '1月6日', 'SCH': 1523, '占比': 0.65, '其他': 100 },
          { 'ACH': 1523, '日期': '1月1日', 'SCH': 1523, '占比': 0.12, '其他': 100 },
          { 'ACH': 1223, '日期': '1月2日', 'SCH': 1523, '占比': 0.345, '其他': 100 },
          { 'ACH': 2123, '日期': '1月3日', 'SCH': 1523, '占比': 0.7, '其他': 100 },
          { 'ACH': 4123, '日期': '1月4日', 'SCH': 1523, '占比': 0.31, '其他': 100 },
          { 'ACH': 3123, '日期': '1月5日', 'SCH': 1523, '占比': 0.12, '其他': 100 },
          { 'ACH': 7123, '日期': '1月6日', 'SCH': 1523, '占比': 0.65, '其他': 100 },
          { 'ACH': 1523, '日期': '1月1日', 'SCH': 1523, '占比': 0.12, '其他': 100 },
          { 'ACH': 1223, '日期': '1月2日', 'SCH': 1523, '占比': 0.345, '其他': 100 },
          { 'ACH': 2123, '日期': '1月3日', 'SCH': 1523, '占比': 0.7, '其他': 100 },
          { 'ACH': 4123, '日期': '1月4日', 'SCH': 1523, '占比': 0.31, '其他': 100 },
          { 'ACH': 3123, '日期': '1月5日', 'SCH': 1523, '占比': 0.12, '其他': 100 },
          { 'ACH': 7123, '日期': '1月6日', 'SCH': 1523, '占比': 0.65, '其他': 100 },
          { 'ACH': 1523, '日期': '1月1日', 'SCH': 1523, '占比': 0.12, '其他': 100 },
          { 'ACH': 1223, '日期': '1月2日', 'SCH': 1523, '占比': 0.345, '其他': 100 },
          { 'ACH': 2123, '日期': '1月3日', 'SCH': 1523, '占比': 0.7, '其他': 100 },
          { 'ACH': 4123, '日期': '1月4日', 'SCH': 1523, '占比': 0.31, '其他': 100 },
          { 'ACH': 3123, '日期': '1月5日', 'SCH': 1523, '占比': 0.12, '其他': 100 },
          { 'ACH': 7123, '日期': '1月6日', 'SCH': 1523, '占比': 0.65, '其他': 100 },
          { 'ACH': 1523, '日期': '1月1日', 'SCH': 1523, '占比': 0.12, '其他': 100 },
          { 'ACH': 1223, '日期': '1月2日', 'SCH': 1523, '占比': 0.345, '其他': 100 },
          { 'ACH': 2123, '日期': '1月3日', 'SCH': 1523, '占比': 0.7, '其他': 100 },
          { 'ACH': 4123, '日期': '1月4日', 'SCH': 1523, '占比': 0.31, '其他': 100 },
          { 'ACH': 3123, '日期': '1月5日', 'SCH': 1523, '占比': 0.12, '其他': 100 },
          { 'ACH': 7123, '日期': '1月6日', 'SCH': 1523, '占比': 0.65, '其他': 100 }
        ]
      },
      fSituation: [
        { slot: 'opt' },
        { label: '放行架次', prop: 'date', align: 'center', width: '70' },
        { label: '正常', prop: 'name', align: 'center', width: '70' },
        { label: '不正常', prop: 'address', align: 'center', width: '70' },
        { label: '正常率', prop: 'address', align: 'center', width: '70' }
      ],
      bridge: [
        { slot: 'opt' },
        { label: '厦航代理', prop: 'date', align: 'center', width: '70' },
        { label: '机场代理', prop: 'name', align: 'center', width: '70' },
        { label: '国际', prop: 'address', align: 'center', width: '70' },
        { label: '国内', prop: 'address', align: 'center', width: '70' },
        { label: '合计', prop: 'address', align: 'center', width: '70' }
      ],
      flight: [
        { slot: 'opt' },
        { label: '厦航代理', prop: 'date', align: 'center', width: '70' },
        { label: '正常', prop: 'name', align: 'center', width: '70' },
        { label: '不正常', prop: 'address', align: 'center', width: '70' },
        { label: '正常率', prop: 'address', align: 'center', width: '70' }
      ],
      delay: [
        { slot: 'opt' },
        { label: '已执行', prop: 'date', align: 'center', width: '70' },
        { label: '未执行', prop: 'name', align: 'center', width: '70' },
        { label: '合计', prop: 'address', align: 'center', width: '70' }
      ],
      normalRate: [
        { slot: 'opt' },
        { label: '放行架次', prop: 'date', align: 'center', width: '70' },
        { label: '正常', prop: 'name', align: 'center', width: '70' },
        { label: '不正常', prop: 'address', align: 'center', width: '70' },
        { label: '正常率', prop: 'address', align: 'center', width: '70' }
      ],
      chartSettings: {
        metrics: ['ACH', 'SCH'],
        dimension: ['日期']
      },
      extend: {
        grid: {
          bottom: 15,
          right: '10%'
        },
        series (s) {
          let colorList = ['rgba(163,182,255,1)', 'rgba(173,255,250)']
          s.forEach((element, index) => {
            element.areaStyle = {
              normal: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                  offset: 0,
                  color: colorList[index]
                }, {
                  offset: 1,
                  color: 'rgba(255,255,255,0)'
                }])
              }
            }
          })
          return s
        },
        title: {
          show: true,
          text: '高峰架次对比图',
          textStyle: {
            fontSize: 14,
            color: '#bec5d2'
          }
        },
        legend: {
          right: 20,
          data: [{
            name: 'ACH',
            // 强制设置图形为圆。
            icon: 'circle',
            textStyle: {
              color: '#2a87f8'
            }
          },
          {
            name: 'SCH',
            // 强制设置图形为圆。
            icon: 'circle',
            textStyle: {
              color: '#1efffa'
            }
          }]
        },
        xAxis: {
          name: '时间段',
          data: ['15-21', '15-23', '16-00', '16-03', '16-04', '16-05', '16-06', '16-07', '16-08', '16-09', '16-10', '16-11', '16-12', '16-13', '16-14', '16-15', '16-16', '16-17',
            '16-18', '16-19', '16-20', '16-21', '16-22', '16-23', '17-00', '17-01', '17-05', '17-07', '17-12'],
          nameLocation: 'end',
          axisLine: {
            lineStyle: {
              color: '#bec5d2'
            }
          }
        },
        color: ['#2a87f8', '#1efffa']
      },
      activeName: '1',
      activeHistory: '1',
      activeNameSub: '1',
      tableData: [{
        type: '计划航班执行',
        date: '23',
        name: '4',
        address: '1'
      }, {
        type: '已执行',
        date: '3',
        name: '4',
        address: '12'
      }, {
        type: '未执行',
        date: '33',
        name: '4',
        address: '67'
      }, {
        type: '延误',
        date: '23',
        name: '44',
        address: '5'
      },
      {
        type: '取消',
        date: '4',
        name: '33',
        address: '2'
      }],
      yAxis: {
        name: '架次',
        splitLine: {
          show: true,
          lineStyle: {
            color: '#292f36'
          }
        },
        axisTick: {
          show: false
        },
        axisLine: {
          lineStyle: {
            color: '#bec5d2'
          }
        }
      }
    }
  },
  methods: {
    handleChange (val) {
      // this.$set(this.$refs['collapse'], 'activeNames', ['1'])
      if (val) {
        this.activeHistory = val
      } else {
        setTimeout(() => {
          this.activeName = this.activeHistory
        }, 0)
      }
    }
  }
}
</script>
<style>
.collapseDiv{
    width: 100%;
}
.collapseDiv .el-collapse-item__header{
  font-weight: bold;
  font-size: 14px;
  height: 24px;
  line-height: 24px;
  padding-left: 15px;
  color: #51619c;
}
.collapseDiv .el-collapse-item__arrow {
    line-height: 24px;
}
.collapseDiv .el-collapse-item__content {
    padding-bottom: 0;
}
.collapseDiv{
    height: 100%;
    overflow-y: auto;
    overflow-x: hidden;
    width: 100%;
}
.circlemin{
    display: inline-block;
    width: 5px;
    height: 5px;
    border-radius: 5px;
}
.taskDistribution{
    display: flex;
    justify-content: space-around;
    line-height: 36px;
}
.circlemiddle {
    display: inline-block;
    width: 10px;
    height: 10px;
    border-radius: 10px;
    margin-right: 5px;
}
.taskDistribution>div{
  display: flex;
}
.taskDistribution>div>span:first-child{
  display: flex;
  align-items: center;
  margin-right: 20px;
}
.fontstyle {
  color: #bec5d2;
  font-weight: bold;
  font-size: 12px;
}
</style>
