<template>
  <div class="stand-container">
    <svg class="svg01"></svg>
  </div>
</template>
<script>
import * as d3 from 'd3'
import icon from '@/assets/img/plane.svg'
export default {
  data () {
    return {
      svgWidth: 300,
      svgHeight: 300,
      flights: [
        { id: 1, xAxis: 30, yAxis: 40, rotate: 90 },
        { id: 2, xAxis: 30, yAxis: 80, rotate: 0 },
        { id: 3, xAxis: 70, yAxis: 40, rotate: -90 },
        { id: 4, xAxis: 70, yAxis: 80, rotate: 180 }
      ]
    }
  },
  mounted () {
    this.initSVG()
  },
  methods: {
    initSVG () {
      d3.select('.svg01')
        .attr('width', this.svgWidth)
        .attr('height', this.svgHeight)
        .selectAll('image')
        .data(this.flights)
        .enter()
        .append('image')
        .attr('href', icon)
        .attr('width', 32)
        .attr('height', 32)
        .attr('x', d => d.xAxis)
        .attr('y', d => d.yAxis)
        .attr('transform', d => `rotate(${d.rotate} ${d.xAxis + 16},${d.yAxis + 16})`)
        .attr('class', 'plane')
    }
  }
}
</script>
<style lang="scss">
.stand-container {
  // background-color: #000 !important;
  .svg01 {
    background: #ccc;
  }
  .plane {
    width: 32px;
    height: 32px;
    background-color: #ff0000;
    // background: url('./../../../../assets/img/plane.svg') no-repeat;
  }
}
</style>
