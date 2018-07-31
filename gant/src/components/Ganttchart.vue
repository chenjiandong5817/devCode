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
      svgHeight: 500,
      tickWidth: 10,
      sourceHeight: 30,
      xAxis: null,
      xAxisMarginLeft: 15,
      yAxis: null,
      yAxisMarginTop: 0,
      taskRects: null,
      leftSpacing: 20,
      topSpacing: 50,
      xTranslate: 15,
      yTranslate: 0,
      currentTime: 0,
      axisStartTime: '2018-04-10 08:00:00',
      axisEndTime: '2018-04-11 08:00:00',
      dateFormat: '%Y-%m-%d %H:%M:%S',
      tickFormat: '%H:%M',
      dateFormattType: 'minute',
      sources: ['01', '02', '03', '04', '05'],
      tasks: [
        { taskId: 't01', name: '01', startTime: '2018-04-10 08:30:00', endTime: '2018-04-10 08:45:00' },
        { taskId: 't02', name: '02', startTime: '2018-04-10 08:30:00', endTime: '2018-04-10 08:45:00' },
        { taskId: 't03', name: '03', startTime: '2018-04-10 08:30:00', endTime: '2018-04-10 08:45:00' },
        { taskId: 't04', name: '04', startTime: '2018-04-10 08:30:00', endTime: '2018-04-10 08:45:00' }
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
    },
    xTranslate (val) {
      d3.select('#xaxis')
        .attr('transform', `translate(${val}, ${this.topSpacing})`)
      d3.select('#taskrects')
        .attr('transform', `translate(${val - this.xAxisMarginLeft}, ${this.yTranslate - this.yAxisMarginTop})`)
      d3.select('#timeruler')
        .attr('transform', `translate(${val - this.xAxisMarginLeft}, 0)`)
    },
    yTranslate (val) {
      d3.select('#yaxis')
        .attr('transform', `translate(${this.leftSpacing}, ${val})`)
      d3.select('#taskrects')
        .attr('transform', `translate(${this.xTranslate - this.xAxisMarginLeft}, ${val - this.yAxisMarginTop})`)
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
    },
    maxDateTimeOfTasks () {
      let max = this.parseDate(d3.max(this.tasks, d => d.endTime))
      return this.timeInterval.time.offset(max, 1)
    },
    minDateTimeOfTasks () {
      let min = this.parseDate(d3.max(this.tasks, d => d.endTime))
      return this.timeInterval.time.offset(min, 1)
    },
    formatDate () {
      return d3.timeFormat(this.dateFormat)
    }
  },
  mounted () {
    // this.updateCurrentTimeInterval()
    this.initAxis()
    this.prDrawAxis()
    this.prTaskRects()
    this.prTimeRuler()
    this.review()
  },
  methods: {
    updateCurrentTimeInterval () {
      setInterval(() => {
        this.drawTimeRuler()
      }, 1000)
    },
    handleAddTask () {
      this.tasks.push({
        taskId: `t${new Date().getTime()}`,
        name: `0${Math.floor(Math.random() * 5 + 1)}`,
        startTime: this.formatDate(this.timeInterval.time.offset(this.maxDateTimeOfTasks, Math.ceil(1 * Math.random()))),
        endTime: this.formatDate(this.timeInterval.time.offset(this.maxDateTimeOfTasks, (Math.ceil(Math.random() * 3)) + 1))
      })
      console.log(this.tasks)
      this.review()
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
      return `translate(${this.x(this.parseDate(data.startTime))}, ${this.y(data.name) + 3})`
    },

    resetTranslate () {
      this.xTranslate = this.xAxisMarginLeft
      this.yTranslate = this.yAxisMarginTop
    },

    getTranslateValues (oldTranslate) {
      if (oldTranslate === null || oldTranslate === '') {
        return 'translate(0, 0)'
      }
      oldTranslate = oldTranslate.replace('translate(', '').replace(')', '').replace(' ', '')
      console.log('translate:', oldTranslate)
      let values = oldTranslate.split(',')
      return values
    },

    getNewTaskTranslate (oldTranslate, dx, dy) {
      let values = this.getTranslateValues(oldTranslate)
      let xvalue = parseInt(values[0]) + dx
      let yvalue = parseInt(values[1]) + dy
      let newTranslate = `translate(${xvalue}, ${yvalue})`
      return newTranslate
    },

    onXDragStart (d) {

    },

    onXDrag (d) {
      this.xTranslate = this.xTranslate + d3.event.dx
      if (this.xTranslate > this.xAxisMarginLeft) {
        this.xTranslate = this.xAxisMarginLeft
      } else if (this.axisWidth + this.xTranslate < this.$refs['svg-div'].clientWidth * 0.7) {
        this.xTranslate = this.xTranslate - d3.event.dx
      }
    },

    onXDragEnd (d) {

    },

    onYDragStart () {

    },

    onYDrag (d) {
      this.yTranslate = this.yTranslate + d3.event.dy
      if (this.yTranslate > this.yAxisMarginTop) {
        this.yTranslate = this.yAxisMarginTop
      } else if (this.axisHeight + this.yTranslate < this.svgHeight * 0.7) {
        this.yTranslate = this.yTranslate - d3.event.dy
      }
    },

    onYDragEnd (d) {

    },

    onTaskDrag (d) {
      console.log('onTaskDrag...')
      let beforeTranslate = d3.select(`#${d.taskId}`).attr('transform')
      d3.select(`#${d.taskId}`)
        .attr('transform', this.getNewTaskTranslate(beforeTranslate, d3.event.dx, 0))
    },

    onTaskDragEnd (d) {
      let beforeTranslate = d3.select(`#${d.taskId}`).attr('transform')
      let values = this.getTranslateValues(beforeTranslate)
      let dtime = values[0] / parseFloat(this.tickWidth) * this.tickStep * this.timeInterval.diff
      let startTime = this.formatDate(new Date(this.minDateTime).getTime() + dtime)
      let endTime = this.formatDate(new Date(this.minDateTime).getTime() + dtime + (this.x(this.parseDate(d.endTime)) - this.x(this.parseDate(d.startTime))) / parseFloat(this.tickWidth) * this.tickStep * this.timeInterval.diff)
      d.startTime = startTime
      d.endTime = endTime
      console.log('startTime:', startTime)
      console.log('endtime:', endTime)
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
      this.axisHeight = this.sourceHeight * this.sources.length
    },

    prDrawAxis () {
      let svg = d3.select('#gantt-area').append('svg')
        .attr('width', () => {
          return this.$refs['svg-div'].clientWidth
        })
        .attr('height', this.svgHeight)
      svg.append('svg')
        .attr('id', 'xaxis-svg')
        .attr('width', this.$refs['svg-div'].clientWidth)
        .attr('height', this.topSpacing + 1)
        .attr('x', this.leftSpacing - this.xAxisMarginLeft)
        .append('g')
        .attr('id', 'xaxis')
        .attr('class', 'xAxis')
        .attr('transform', `translate(${this.xAxisMarginLeft}, ${this.topSpacing}) `)
        .transition()

      svg.append('svg')
        .attr('id', 'yaxis-svg')
        .attr('height', this.axisHeight)
        .attr('width', 21)
        .attr('y', this.topSpacing - this.yAxisMarginTop)
        .append('g')
        .attr('id', 'yaxis')
        .attr('class', 'yAxis')
        .attr('transform', `translate(${this.leftSpacing}, ${this.yAxisMarginTop}) `)
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
      d3.select('#yaxis')
        .call(d3.drag()
          .on('start', this.onYDragStart)
          .on('drag', this.onYDrag)
          .on('end', this.onYDragEnd))
    },

    prTaskRects () {
      d3.select('svg')
        .append('svg')
        .attr('id', 'tasks-svg')
        .attr('width', this.axisWidth)
        .attr('height', this.axisHeight)
        .attr('x', this.leftSpacing + 1)
        .attr('y', this.topSpacing + 1)
        .append('g')
        .attr('class', 'tasks')
        .attr('id', 'taskrects')
    },

    drawTaskRects () {
      d3.select('#taskrects')
        .selectAll('rect')
        .data(this.tasks)
        .enter()
        .append('rect')
        .attr('id', d => d.taskId)
        .attr('class', 'task')
        // .on('mouseenter', function (d, i) {
        //   console.log('onMouseEnter..')
        //   d3.select(this).style('cursor', 'w-resize')
        // })
        // .on('mouseover', function (d, i) {
        //   console.log('onMouseover..')
        //   d3.select(this).style('cursor', 'move')
        // })
        .call(d3.drag()
          .on('drag', this.onTaskDrag)
          .on('end', this.onTaskDragEnd))
      d3.select('#taskrects')
        .selectAll('rect')
        .data(this.tasks)
        .transition()
        .attr('transform', this.rectTransform)
        .attr('width', (d) => {
          return (this.x(this.parseDate(d.endTime)) - this.x(this.parseDate(d.startTime)))
        })
        .attr('height', 28)
      d3.select('#taskrects')
        .selectAll('rect')
        .data(this.tasks)
        .exit()
        .remove()
    },

    prTimeRuler () {
      d3.select('#tasks-svg')
        .append('g')
        .attr('id', 'timeruler')
    },

    drawTimeRuler () {
      let currentTime = new Date().getTime()
      let formTime = this.formatDate(currentTime)
      console.log('currentTime:', currentTime)
      console.log('formTime:', formTime)
      let timeRuler = d3.path()
      timeRuler.moveTo(this.x(this.parseDate(formTime)), this.yAxisMarginTop)
      timeRuler.lineTo(this.x(this.parseDate(formTime)), this.yAxisMarginTop + this.axisHeight)
      timeRuler.toString()
      d3.select('#pathtimeruler')
        .remove()
      d3.select('#timeruler')
        .append('path')
        .attr('id', 'pathtimeruler')
        .attr('d', timeRuler)
        .style('fill', 'white')
        .style('stroke-width', 1)
        .style('stroke', '#ff0')
    },

    review () {
      this.drawAxis()
      this.resetTranslate()
      this.drawTaskRects()
      this.drawTimeRuler()
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
