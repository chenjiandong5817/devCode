/*
 * @Author: cdroid
 * @Date: 2018-01-25 16:05:54
 * @Last Modified by: cdroid
 * @Last Modified time: 2018-01-25 16:16:35
 */
<template>
  <div class="chart">
    <div>
      <svg id="chart-svg"></svg>
    </div>
    <div>
      <button @click="dragable = !dragable">{{`${dragable ? '禁止' : '允许'}移动设备`}}</button>
      <ul>
        <li :style="{color: dragable ? 'green' : 'red'}">移动设备： {{dragable ? 'on' : 'off'}}</li>
        <li v-for="(item, index) in devices" :key="index"> {{ `x: ${item.x}, y: ${item.y}` }}</li>
      </ul>
    </div>
  </div>
</template>
<script>
import * as d3 from 'd3'
import pathData from '@/assets/data.json'
export default {
  data () {
    return {
      devices: [
        {x: 250, y: 90, state: 'normal'},
        {x: 130, y: 330, state: 'error'},
        {x: 300, y: 256, state: 'normal'}
      ],
      svgWidth: 500,
      svgHeight: 500,
      zoomParams: {
        minScale: 0.5,
        maxScale: 30
      },
      dragable: true,
      zoomRect: null,
      circleGroup: null,
      dragInfo: null
    }
  },
  watch: {
    dragable () {
      // this.initZoom()
    }
  },
  mounted () {
    this.initChart()
  },
  methods: {
    initZoom () {
      d3.select('#chart-svg')
        .append('rect')
        .attr('class', 'chart-rect-zoom')
        .attr('width', this.svgWidth)
        .attr('height', this.svgHeight)
        .style('fill', 'none')
        .style('pointer-events', 'all')
        .call(d3.zoom()
          .scaleExtent([this.zoomParams.minScale, this.zoomParams.maxScale])
          .on('zoom', function () {
            d3.select('.circle-group').attr('transform', d3.event.transform)
            d3.select('.area-path').attr('transform', d3.event.transform)
          })
        )
    },
    initArea () {
      let line = d3.line()
        .x(d => d[0])
        .y(d => d[1])
      d3.select('#chart-svg')
        .append('path')
        .attr('class', 'area-path')
        .datum(pathData)
        .attr('stroke', '#000')
        .attr('stroke-width', 1)
        .attr('fill', 'none')
        .attr('d', line)
    },
    initChart () {
      let vm = this
      // 过度动画
      let transition = d3.transition()
        .duration(750)
        .ease(d3.easeLinear)
      // 基础svg
      let svg = d3.select('#chart-svg')
        .attr('width', this.svgWidth)
        .attr('height', this.svgHeight)
      this.initArea()
      // 缩放层, 放在圆点下面, 防止圆点的drag事件被遮盖
      this.initZoom()
      // 圆点集合
      let circleGroup = svg.append('g').attr('class', 'circle-group')
      // 拖拽对象
      let drag = d3.drag().on('start', function (d) {
        vm.dragInfo = {
          enable: true,
          x: d.x,
          y: d.y
        }
      }).on('drag', function (d) {
        if (!vm.dragable) {
          return
        }
        // 判断点在范围内
        let x = d3.event.x
        let y = d3.event.y
        let flag = vm.rayCasting({x, y}, pathData)
        vm.dragInfo.enable = flag
        d3.select(this)
          .attr('cx', d.x = x)
          .attr('cy', d.y = y)
      }).on('end', function (d) {
        if (vm.dragInfo && !vm.dragInfo.enable) {
          d3.select(this)
            .transition(transition)
            .attr('cx', d.x = vm.dragInfo.x)
            .attr('cy', d.y = vm.dragInfo.y)
          d3.select('.area-path')
            .transition()
            .attr('stroke', '#f26060')
            .attr('stroke-width', 3)
            .transition()
            .attr('stroke', '#000')
            .attr('stroke-width', 1)
          vm.$nextTick(() => {
            vm.dragInfo = null
          })
        }
      })
      // 画圆点
      circleGroup.selectAll('circle')
        .data(this.devices)
        .enter()
        .append('circle')
        .attr('r', 10)
        .attr('cx', d => d.x)
        .attr('cy', d => d.y)
        .style('fill', d => d.state === 'normal' ? 'green' : 'red')
        // 加载拖拽对象
        .call(drag)
    },
    // 判断点在多边形内部，p为待匹配的点，poly为多边形顶点集合
    rayCasting (p, poly) {
      let px = p.x
      let py = p.y
      let flag = false
      for (let i = 0, l = poly.length, j = l - 1; i < l; j = i, i++) {
        // 获取坐标点, 根据poly的格式自己定义
        let sx = poly[i][0]
        let sy = poly[i][1]
        let tx = poly[j][0]
        let ty = poly[j][1]
        // 判断两点相等
        if (sx === tx && sy === ty) {
          continue
        }
        // 点与多边形顶点重合
        if ((sx === px && sy === py) || (tx === px && ty === py)) {
          return true
        }
        // 判断线段两端点是否在射线两侧
        if ((sy < py && ty >= py) || (sy >= py && ty < py)) {
          // 线段上与射线 Y 坐标相同的点的 X 坐标
          let x = sx + (py - sy) * (tx - sx) / (ty - sy)
          // 点在多边形的边上
          if (x === px) {
            return true
          }
          // 射线穿过多边形的边界
          if (x > px) {
            flag = !flag
          }
        }
      }
      // 射线穿过多边形边界的次数为奇数时点在多边形内
      return flag
    }
  }
}
</script>
<style scoped>
.chart > div {
  display: inline-block;
  vertical-align: top;
  overflow: scroll;
}
.chart > div:first-child {
  border: 1px solid #ccc;
}
</style>
