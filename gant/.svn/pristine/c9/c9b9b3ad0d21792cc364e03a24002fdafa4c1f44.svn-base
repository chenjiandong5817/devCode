/*
 * @Author: cdroid
 * @Date: 2018-01-17 14:16:05
 * @Last Modified by: cdroid
 * @Last Modified time: 2018-01-24 11:25:44
 */

<template>
  <div id="app">
    tickStep:
    <input type="radio" name="tickStep" :value="1" v-model="tickStep"/>1
    <input type="radio" name="tickStep" :value="5" v-model="tickStep"/>5
    <input type="radio" name="tickStep" :value="10" v-model="tickStep"/>10
    <input type="radio" name="tickStep" :value="15" v-model="tickStep"/>15
    <button @click="handleAddTask">Add Task</button>
    <br />
    <br />
    <div id="gantt-area"></div>
  </div>
</template>

<script>
import * as d3 from 'd3'
export default {
  name: 'app',
  data () {
    return {
      svgWidth: 800,
      svgHeight: 800,
      gates: ['01', '02', '03', '04', '05'],
      tasks: [
        {name: '01', startDate: '2017-01-01 08:01:00', endDate: '2017-01-01 08:03:00'},
        {name: '03', startDate: '2017-01-01 08:03:00', endDate: '2017-01-01 08:04:00'},
        {name: '01', startDate: '2017-01-01 08:05:00', endDate: '2017-01-01 08:06:00'}
      ],
      dateFormat: '%Y-%m-%d %H:%M:%S',
      tickFormat: '%H:%M',
      dateFormattType: 'minute',
      tickStep: 1,
      topSpacing: 35,
      leftSpacing: 50,
      x: null,
      y: null,
      xAxis: null,
      yAxis: null
    }
  },
  watch: {
    tickStep (val) {
      if (val < 1) {
        this.tickStep = 1
      }
      this.review()
      console.log(this.tickStep)
    }
  },
  computed: {
    timeInterval () {
      let timer = null
      switch (this.dateFormattType) {
        case 'day':
          timer = {
            time: d3.timeDay,
            diff: 24 * 60 * 60 * 1000
          }
          break
        case 'hour':
          timer = {
            time: d3.timeHour,
            diff: 60 * 60 * 1000
          }
          break
        case 'minute':
          timer = {
            time: d3.timeMinute,
            diff: 60 * 1000
          }
          break
        default:
          timer = {
            time: d3.timeDay,
            diff: 24 * 60 * 60 * 1000
          }
      }
      return timer
    },
    formatDate () {
      return d3.timeFormat(this.dateFormat)
    },
    parseDate () {
      return d3.timeParse(this.dateFormat)
    },
    minDateTime () {
      let min = this.parseDate(d3.min(this.tasks, d => d.startDate))
      return this.timeInterval.time.offset(min, -1)
    },
    maxDateTime () {
      let max = this.parseDate(d3.max(this.tasks, d => d.endDate))
      return this.timeInterval.time.offset(max, 1)
    }
  },
  mounted () {
    this.svgWidth = this.$el.offsetWidth
    this.scaleAxis()
  },
  methods: {
    handleAddTask () {
      this.tasks.push({
        name: `0${Math.floor(Math.random() * 5 + 1)}`,
        startDate: this.formatDate(this.timeInterval.time.offset(this.maxDateTime, Math.ceil(1 * Math.random()))),
        endDate: this.formatDate(this.timeInterval.time.offset(this.maxDateTime, (Math.ceil(Math.random() * 3)) + 1))
      })
      console.log(this.tasks)
      this.review()
    },
    buildRealWidth () {
      let unit = (this.maxDateTime - this.minDateTime) / (this.timeInterval.diff * this.tickStep)
      this.svgWidth = (unit > 1 ? unit : 1) * 10
      console.log(this.svgWidth)
    },
    rectTransform (data, index) {
      return `translate(${this.x(this.parseDate(data.startDate)) + this.leftSpacing}, ${this.y(data.name) + this.topSpacing + 3})`
    },
    initAxis () {
      this.buildRealWidth()
      this.x = d3.scaleTime().domain([this.minDateTime, this.maxDateTime]).range([0, this.svgWidth * 0.9])
      this.y = d3.scaleBand().domain(this.gates).rangeRound([0, 30 * this.gates.length])
      this.xAxis = d3.axisTop(this.x)
        .ticks(this.timeInterval.time.every(this.tickStep))
        .tickFormat((d, i) => this.getXAxisText(d, i, false))
        .tickSize(20)
      this.yAxis = d3.axisLeft(this.y).tickSize(0)
    },
    getXAxisText (data, index, always) {
      if (index % 10 === 0 || always) {
        return d3.timeFormat(this.tickFormat)(data)
      }
      return ''
    },
    getXAxisTick (data, index) {
      if (index % 10 === 0) {
        return -20
      } else if (index % 5 === 0) {
        return -13
      }
      return -6
    },
    scaleAxis () {
      this.initAxis()
      let svg = d3.select('#gantt-area')
        .append('svg')
        .attr('class', 'chart')
        .attr('width', this.svgWidth)
        .attr('height', this.svgHeight)
      // 坐标横轴
      svg.append('g')
        .attr('class', 'x axis')
        .attr('transform', `translate(${this.leftSpacing}, ${this.topSpacing})`)
        .transition()
        .call(this.xAxis)
        .selectAll('line')
        .attr('y2', this.getXAxisTick)
      // 坐标纵轴
      svg.append('g')
        .attr('class', 'y axis')
        .attr('transform', `translate(${this.leftSpacing}, ${this.topSpacing})`)
        .transition()
        .call(this.yAxis)
      // 块
      svg.selectAll('rect')
        .data(this.tasks)
        .enter()
        .append('rect')
        .attr('class', 'task')
        .transition()
        .attr('transform', this.rectTransform)
        .attr('width', (d) => {
          return (this.x(this.parseDate(d.endDate)) - this.x(this.parseDate(d.startDate)))
        })
        .attr('height', 28)
    },
    review (tasks) {
      this.initAxis()
      let svg = d3.select('.chart').attr('width', this.svgWidth)
      let rect = svg.selectAll('rect').data(this.tasks)
      rect.enter()
        .append('rect')
        .attr('class', 'task')
        .transition()
        .attr('transform', this.rectTransform)
        .attr('width', (d) => {
          return (this.x(this.parseDate(d.endDate)) - this.x(this.parseDate(d.startDate)))
        })
        .attr('height', 28)

      rect.transition()
        .attr('transform', this.rectTransform)
        .attr('width', (d) => {
          return (this.x(this.parseDate(d.endDate)) - this.x(this.parseDate(d.startDate)))
        })
        .attr('height', 28)
      rect.exit().remove()

      svg.select('.x')

        .call(this.xAxis)
        .selectAll('line')
        .attr('y2', this.getXAxisTick)
      svg.select('.y').transition().call(this.yAxis)
    }
  }
}
</script>
<style>
.task {
  fill: #ccc;
}
</style>
