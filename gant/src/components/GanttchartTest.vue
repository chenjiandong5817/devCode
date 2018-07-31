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
      xAxis: null,
      yAxis: null,
      startTime: '2018-03-29 08:00:00',
      endTime: '2018-03-30 08:00:00',
      dateFormat: '%Y-%m-%d %H:%M:%S',
      tickFormat: '%H:%M',
      sources: [],
      tasks: [],
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
    }
  },
  computed: {
    parseDate () {
      return d3.timeParse(this.dateFormat)
    }
  },
  mounted () {
    d3.select('#gantt-area').append('svg')
      .attr('width', () => {
        return this.$refs['svg-div'].clientWidth
      })
      .attr('height', 800)
    this.initAxis()
  },
  methods: {
    initAxis () {
      this.x = d3.scaleTime().domain([this.parseDate(this.startTime), this.parseDate(this.endTime)]).range([0, 2500])
      this.xAxis = d3.axisBottom(this.x)
        .ticks(d3.timeMinute.every(15))
        .tickFormat((d, i) => this.getXAxisText(d, i, false))
        .tickSize(20)
      d3.select('svg')
        .append('g')
        .attr('class', 'xAxis')
        .attr('transform', 'translate(30,30)')
        .call(this.xAxis)
    },

    handleAddTask () {
    },

    getXAxisText (data, index, always) {
      return d3.timeFormat(this.tickFormat)(data)
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
