<template>
  <section>
    <div class="stand-container">
      <svg class="svg01"></svg>
      <transition name="el-zoom-in-top">
        <div v-if="formShow">
          <el-form ref="form" :model="form" label-width="120px">
            <el-form-item label="横向偏移量">
              <el-input-number v-model="form.x"></el-input-number>
            </el-form-item>
            <el-form-item label="纵向偏移量">
              <el-input-number v-model="form.y"></el-input-number>
            </el-form-item>
            <el-form-item label="旋转度数">
              <el-input-number v-model="form.r"></el-input-number>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="submitForm()">确定修改</el-button>
              <el-button type="primary" @click="formShow = false">隐藏</el-button>
            </el-form-item>
          </el-form>
        </div>
      </transition>
    </div>
  </section>
</template>
<script>
import * as d3 from 'd3'
import icon from '@/assets/img/plane.svg'
export default {
  data () {
    return {
      formShow: false,
      data: {},
      that: null,
      form: {
        x: 0,
        y: 0,
        r: 0
      },
      svgWidth: 300,
      svgHeight: 300,
      flights: [
        { id: 1, xAxis: 30, yAxis: 40, x: 30, y: 40, r: 0 },
        { id: 2, xAxis: 30, yAxis: 80, x: 30, y: 80, r: 0 },
        { id: 3, xAxis: 70, yAxis: 40, x: 70, y: 40, r: 0 },
        { id: 4, xAxis: 70, yAxis: 80, x: 70, y: 80, r: 0 }
      ]
    }
  },
  mounted () {
    this.initSVG()
  },
  methods: {
    submitForm () {
      this.flights.forEach(item => {
        if (item.id === this.data.id) {
          item.x = this.form.x
          item.y = this.form.y
          item.r = this.form.r
          d3.select(this.that)
            .attr('transform', `rotate(${item.r} ${item.x + 16},${item.y + 16})`)
            /* .attr('translate', `(${item.x},${item.y})`) */
        }
      })
      this.$message({ message: '位置更改成功', type: 'success' })
    },
    clickImg (d, _that) {
      this.formShow = true
      this.data = d
      this.form.x = d.x
      this.form.y = d.y
      this.form.r = d.r
      this.that = _that
    },
    dbClickImg (d, _that) {
      this.flights.forEach(item => {
        if (item.id === d.id) {
          item.r += 30
          this.form.r = item.r
          d3.select(this.that)
            .attr('transform', `rotate(${item.r} ${item.x + 16},${item.y + 16})`)
          this.$message('已成功顺时针旋转30度')
        }
      })
    },
    dragmove (d, _that) {
      this.formShow = true
      this.data = d
      this.form.x = this.data.x
      this.form.y = this.data.y
      this.form.r = this.data.r
      /* d3.event.x = this.form.x
      d3.event.y = this.form.y */
      /* this.form.x = d.x
      this.form.y = d.y
      this.form.r = d.r */
      this.that = _that
      d3.select(this.that)
        .attr('x', this.form.x = d3.event.x)
        .attr('y', this.form.y = d3.event.y)
        .attr('transform', `rotate(${this.form.r} ${this.form.x + 16},${this.form.y + 16})`)
      /* d3.select('.svg01')
        .append('line')
        .attr('opacity', 0)
        .attr('x1', 0)
        .attr('y1', this.form.y)
        .attr('x2', this.form.x)
        .attr('y2', this.form.y)
        .attr('stroke', 'black')
        .attr('stroke-width', 2)
        .attr('stroke-dasharray', 5.5)
        .attr('opacity', 1) */
      this.flights.forEach(item => {
        if (item.id === this.data.id) {
          item.x = this.form.x
          item.y = this.form.y
          item.r = this.form.r
        }
      })
    },
    initSVG () {
      let vm = this
      // 定义拖拽函数
      var drag = d3.drag()
        .on('drag', function (d) { vm.dragmove(d, this) })
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
        .attr('x', d => d.x)
        .attr('y', d => d.y)
        .attr('y', d => d.y)
        .on('click', function (d) { vm.clickImg(d, this) })
        .on('dblclick', function (d) { vm.dbClickImg(d, this) })
        /* .on('click', function (d, i) {
          d3.select(this)
            .attr('transform', d => `rotate(${180} ${d.xAxis + 106},${d.yAxis + 16})`)
        }) */
        /* .attr('transform', d => `rotate(${d.rotate} ${d.xAxis + 16},${d.yAxis + 16})`) */
        .attr('class', 'plane')
        .call(drag)
    }
  }
}
</script>
<style lang="scss">
.stand-container {
  display: flex;
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
