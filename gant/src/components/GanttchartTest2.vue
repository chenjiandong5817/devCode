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
    <div id="gantt-area" ref="svg-div"></div>
  </div>
</template>

<script>
import * as d3 from 'd3'

export default {
  data () {
    return {
      tickStep: 1,
      axisWidth: 800,
      axisHeight: 800,
      tickWidth: 10,
      sourceHeight: 30,
      xAxis: null,
      yAxis: null,
      taskRects: null,
      leftSpacing: 20,
      topSpacing: 50,
      leftX: 20,
      topY: 50,
      axisStartTime: '2018-03-29 08:00:00',
      axisEndTime: '2018-03-30 08:00:00',
      dateFormat: '%Y-%m-%d %H:%M:%S',
      tickFormat: '%H:%M',
      dateFormattType: 'minute',
      sources: ['01', '02', '03', '04', '05'],
      tasks: [
        { taskId: 't01', name: '01', startTime: '2018-03-29 08:30:00', endTime: '2018-03-29 08:45:00' },
        { taskId: 't02', name: '02', startTime: '2018-03-29 08:30:00', endTime: '2018-03-29 08:45:00' },
        { taskId: 't03', name: '03', startTime: '2018-03-29 08:30:00', endTime: '2018-03-29 08:45:00' },
        { taskId: 't04', name: '04', startTime: '2018-03-29 08:30:00', endTime: '2018-03-29 08:45:00' }
      ],
      x: null,
      y: null
    }
  },
  watch: {
    tickStep (val) {
      if (val < 1) {
        this.tickStep = 1
      }
      console.log(this.tickStep)
      this.initAxis()
      this.review()
    }
  },
  computed: {
    parseDate () {
      return d3.timeParse(this.dateFormat)
    },
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
    minDateTime () {
      let min = this.parseDate(this.axisStartTime)
      return min
    },
    maxDateTime () {
      let max = this.parseDate(this.axisEndTime)
      return max
    }
  },
  mounted () {
    this.initAxis()
    this.prDrawAxis()
    this.prTaskRects()
    this.review()
  },
  methods: {
    handleAddTask () {
    },

    getXAxisTickType (data, index) {
      if (index % 10 === 0) {
        return -20
      } else if (index % 5 === 0) {
        return -13
      }
      return -6
    },

    getXAxisText (data, index, always) {
      if (index % 10 === 0 || always) {
        return d3.timeFormat(this.tickFormat)(data)
      }
      return ''
    },

    rectTransform (data, index) {
      return `translate(${this.x(this.parseDate(data.startTime)) + this.leftSpacing}, ${this.y(data.name) + this.topSpacing + 3})`
    },

    onXDragStart (d) {
      this.xBeforeDrag = d3.event.x
      console.log('touch point:', d3.event.x)
    },

    onXDrag (d) {
      // d3.select('#xaxis')
      this.xTranslate = this.leftX - (this.xBeforeDrag - d3.event.x)
      console.log('xTranslate:', this.xTranslate)
      if (this.xTranslate > this.leftSpacing) {
        d3.select('#xaxis')
          .attr('transform', `translate(${this.leftSpacing}, ${this.topSpacing})`)
      } else {
        d3.select('#xaxis')
          .attr('transform', `translate(${this.xTranslate}, ${this.topSpacing})`)
      }
      console.log('drag x:', d3.event.x)
      d3.select('#taskrects')
        .attr('transform', `translate(${this.xTranslate}, 0)`)
    },

    onXDragEnd (d) {
      if (this.xTranslate > this.leftSpacing) {
        this.leftX = this.leftSpacing
      } else {
        this.leftX = this.xTranslate
      }
    },

    onYDrag (d) {

    },

    onYDragEnd (d) {

    },

    initAxis () {
      let unit = (this.maxDateTime - this.minDateTime) / (this.timeInterval.diff * this.tickStep)
      this.axisWidth = (unit > 1 ? unit : 1) * this.tickWidth
      console.log(this.axisWidth)
      this.x = d3.scaleTime().domain([this.minDateTime, this.maxDateTime]).range([0, this.axisWidth])
      this.xAxis = d3.axisTop(this.x)
        .ticks(this.timeInterval.time.every(this.tickStep))
        .tickFormat((d, i) => this.getXAxisText(d, i, false))
        .tickSize(20)
      this.y = d3.scaleBand().domain(this.sources).rangeRound([0, this.sourceHeight * this.sources.length])
      this.yAxis = d3.axisLeft(this.y).tickSize(6)
    },

    prDrawAxis () {
      let svg = d3.select('#gantt-area').append('svg')
        .attr('width', () => {
          return 800
        })
        .attr('height', 800)
      svg.append('g')
        .attr('id', 'xaxis')
        .attr('class', 'xAxis')
        .attr('transform', `translate(${this.leftSpacing}, ${this.topSpacing}) `)
        .transition()
      svg.append('g')
        .attr('id', 'yaxis')
        .attr('class', 'yAxis')
        .attr('transform', `translate(${this.leftSpacing}, ${this.topSpacing}) `)
        .transition()
    },

    drawAxis () {
      d3.select('#xaxis').call(this.xAxis)
        .selectAll('line')
        .attr('y2', this.getXAxisTickType)
      d3.select('#xaxis')
        .call(d3.drag()
          .on('start', this.onXDragStart)
          .on('drag', this.onXDrag)
          .on('end', this.onXDragEnd))
      d3.select('#yaxis').call(this.yAxis)
    },

    prTaskRects () {
      d3.select('svg')
        .append('g')
        .attr('class', 'tasks')
        .attr('id', 'taskrects')
    },

    drawTaskRects () {
      d3.select('#taskrects').selectAll('rect')
        .remove()
      d3.select('#taskrects')
        .selectAll('rect')
        .data(this.tasks)
        .enter()
        .append('rect')
        .attr('id', d => d.taskId)
        .attr('class', 'task')
        .transition()
        .attr('transform', this.rectTransform)
        .attr('width', (d) => {
          return (this.x(this.parseDate(d.endTime)) - this.x(this.parseDate(d.startTime)))
        })
        .attr('height', 28)
    },

    review () {
      this.drawAxis()
      this.drawTaskRects()
    }
  }
}
</script>

<style>
.svg-div {
  background-color: darkgray;
  width: 700px;
  height: 500px;
  margin-top: 50px;
  overflow: scroll;
}
</style>
