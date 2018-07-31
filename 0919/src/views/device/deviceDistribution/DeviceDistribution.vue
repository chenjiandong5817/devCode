<template>
  <el-row>
    <el-row style="margin-top:10px;margin-left:10px">
      <el-col :span="24">
        <div>
          <div style="display:inline-block;margin-right:30px">
            <city-name
              class="dropDown"
              ref="city"
              v-on:getAirportName = "getAirportCode">
            </city-name>
          </div>
          <div style="display:inline-block;">
            <label>移动设备:</label>
          </div>
          <div style="display:inline-block;vertical-align:middle;padding-right:8px" >
            <el-switch
              v-model="dragEnable" @change="changedSwitch">
            </el-switch>
          </div>
          <el-button type="primary" @click="handleAdd" v-bind:disabled="!dragEnable">新增</el-button>
          <el-button type="primary" @click="handleEdit" v-bind:disabled="!dragEnable">保存</el-button>
          <div style="display:inline-block;margin-left:30px">
            <div style="display:inline-block;vertical-align:middle">
              <el-input v-model="inputData" placeholder="请输入IP或设备名称"></el-input>
            </div>
          </div>
          <div style="display:inline-block;margin-left:30px;">
            <div style="display:inline-block;">
              <label>在线:</label>
            </div>
            <div style="display:inline-block;vertical-align:top" >
              <div class="circle"></div>
            </div>
          </div>
          <div style="display:inline-block;margin-left:8px">
            <div style="display:inline-block;">
              <label>离线:</label>
            </div>
            <div style="display:inline-block;vertical-align:top" >
              <div class="circle" style="background:#000"></div>
            </div>
          </div>
          <div style="display:inline-block;margin-left:8px">
            <div style="display:inline-block;">
              <label>网络异常:</label>
            </div>
            <div style="display:inline-block;vertical-align:top" >
              <div class="circle" style="background:#ff0"></div>
            </div>
          </div>
          <div style="display:inline-block;margin-left:8px">
            <div style="display:inline-block;">
              <label>通信异常:</label>
            </div>
            <div style="display:inline-block;vertical-align:top" >
              <div class="circle" style="background:#00f"></div>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>
    <el-row>
      <div class="h-line"></div>  
    </el-row>
    <el-row style="margin-top:20px">
      <el-col :span="24">
        <div id="svgBox" ref="svgDiv">
          <svg id='mapsvg'></svg>
        </div>
      </el-col>
    </el-row>
    <add-or-update
      :to="API.addDeviceInfo().go"
      :callback="getDeviceInfo"
      ref="deviceAddForm"></add-or-update>
  </el-row>
</template>

<script>
import Util from '../../../common/js/util'
import API from '../../../api'
import addOrUpdate from './../deviceManager/AddOrUpdate'
import CityName from '../../../components/CityName'
import LoginInfo from '../../../vuex/store'
import * as LCXSVGMap from './LCXSVGMap'
import * as d3 from 'd3'
import * as config from './deviceDistributionConfig'

export default {
  data () {
    return {
      filters: {
        deviceNo: '',
        deviceip: '',
        airportCode: JSON.parse(sessionStorage.getItem('userStorage')).user.aiisAirports[0].airportCode,
        displayType: ''
      },
      statusColor: {
        '0': 'green',
        '1': '#000',
        '2': '#ff0',
        '3': '#00f'
      },
      dragEnable: false,
      inputData: '',
      API: API,
      subscribeAirports: [],
      subscribeAirportsLables: [],
      allDevices: [],
      subDevices: [],
      svgDeviceDatas: [],
      meniu_items: ['删除', '编辑', '其他'],
      textSize: 10,
      temp: undefined,
      timeOutId: undefined,
      changedDeviceData: []
    }
  },
  components: {
    addOrUpdate: addOrUpdate,
    cityName: CityName
  },
  mounted () {
    this.drawMap()
    this.SubscribeAirports()
    this.setAirportInfo()
    this.getDeviceInfo()
    this.startGetDeviceInfoTask()
  },
  methods: {
    drawMap () {
      d3.select('#mapsvg')
        .on('contextmenu', function () {
          d3.event.preventDefault()
        })
        .on('click', function () {
          d3.select('#meniu').remove()
        })
      var svgBg = d3.select('#svgBg')
      svgBg.remove()
      if (this.filters.airportCode === 'XMN') {
        console.log('draw XMN map')
      } else if (this.filters.airportCode === 'LCX') {
        console.log('draw LCX map')
        var bgZoom = d3.zoom()
          .scaleExtent([0.25, 10])
          .on('zoom', () => {
            svgBg.attr('transform', d3.event.transform)
            this.transformStr = svgBg.attr('transform')
            console.log(this.transformStr)
          })
        svgBg = d3.select('#mapsvg')
          .attr('width', () => {
            console.log('width:', this.$refs['svgDiv'].clientWidth)
            return this.$refs['svgDiv'].clientWidth
          })
          .attr('height', config.SVG_HEIGHT)
          .call(bgZoom)
          .append('g')
          .attr('id', 'svgBg')

        svgBg.append('path')
          .attr('d', LCXSVGMap.terminalPath())
          .style('fill', 'white')
          .style('stroke-width', 2)
          .style('stroke', '#000')
      } else if (this.filters.airportCode === 'WUS') {
        console.log('draw XMN map')
      } else if (this.filters.airportCode === 'FOC') {
        console.log('draw XMN map')
      }
    },
    mountDevicesPoints () {
      console.log('mountDevicesPoints...')
      if (d3.select('#svgBg') === undefined) {
        return
      }
      d3.select('#circles').remove()
      let svgCircles = d3.select('#svgBg')
        .append('g')
        .attr('id', 'circles')
      this.svgDeviceDatas = []
      this.subDevices.forEach((item) => {
        this.svgDeviceDatas.push(Object.assign({}, item))
      })
      svgCircles.selectAll('circle').data(this.svgDeviceDatas)
        .enter()
        .append('circle')
        .attr('id', d => 'd' + d.id)
        .attr('cx', d => d.coordX)
        .attr('cy', d => d.coordY)
        .attr('r', config.CIRCLE_R)
        .on('mouseover', this.onMouseover)
        .on('mouseout', this.onMouseout)
        // .on('contextmenu', this.onRightClick)
        .style('fill', d => this.statusColor[d.deviceStatus])
      if (this.dragEnable) {
        svgCircles.selectAll('circle').call(d3.drag().on('drag', this.onDrag).on('end', this.onDragEnd))
      }
    },
    getAirportCode (airportData) {
      console.log(airportData)
      this.filters.airportCode = airportData
      this.drawMap()
      this.getDeviceInfo()
    },
    changedSwitch (value) {
      if (value === false) {
        if (this.changedDeviceData.length > 0) {
          this.$confirm('是否保存被修改的设备信息？', '提示', {
            confirmButtonText: '是',
            cancelButtonText: '否',
            type: 'warning'
          }).then(() => {
            // 保存修改
            this.handleEdit()
          }).catch(() => {
            this.changedDeviceData.splice(0, this.changedDeviceData.length)
            this.mountDevicesPoints()
            console.log('changedDeviceData length:', this.changedDeviceData.length)
            this.$message({
              type: 'info',
              message: '取消修改'
            })
          })
        }
      }
    },
    handleAdd () {
      this.$refs['deviceAddForm'].show()
    },
    handleEdit () {
      this.changedDeviceData.forEach((item) => {
        console.log('handleItem:', item)
        let i = 0
        for (; i < this.subDevices.length; i++) {
          console.log('in for')
          if (this.subDevices[i].id === item.id) {
            var mOldValue = this.subDevices[i]
            console.log('mOldValue:', mOldValue)
            console.log('newValue:', item)
            break
          }
        }
        let para = { newValue: item, oldValue: mOldValue }
        API.editDeviceInfo().go(para).then((date) => {
          if (date.ok) {
            console.log('修改成功')
            this.$message({
              type: 'success',
              message: '修改成功!'
            })
            this.subDevices[i] = item
            console.log('newSubDevices:', this.subDevices[i])
            this.changedDeviceData.splice(this.changedDeviceData.indexOf(item), 1)
          }
        })
      })
    },

    getSubDevices () {
      console.log('getSubDevices,airportCode:', this.filters.airportCode)
      let temps = []
      this.allDevices.forEach(d => {
        if (d.airportCode === this.filters.airportCode) {
          temps.push(d)
        }
      })
      this.subDevices = temps
      console.log('subDevices', this.subDevices)
    },
    getDeviceInfo () {
      console.log('getDeviceInfo:', this.filters.airportCode)
      API.getDeviceInfoAll().go().then((data) => {
        if (data.ok) {
          console.log(data)
          this.allDevices = data.attr.data.list
          this.getSubDevices()
        } else {
          this.$notify(Util.notifyBody(false, data.msg))
          clearInterval(this.getDeviceInfoTaskId)
        }
      })
    },
    startGetDeviceInfoTask () {
      this.getDeviceInfoTaskId = setInterval(() => {
        this.getDeviceInfo()
      }, 10000)
    },
    setAirportInfo () {
      this.$refs['city'].setCitys(this.subscribeAirports)
    },
    SubscribeAirports () {
      this.subscribeAirportsLables = []
      this.subscribeAirports = this.getSubscribeAirports()   // 用户订阅的机场列表
      for (let i = 0; i < this.subscribeAirports.length; i++) {
        let label = { text: this.subscribeAirports[i], value: this.subscribeAirports[i] }
        this.subscribeAirportsLables.push(label)
      }
      // this.filters.airportCode = this.subscribeAirports[0]
    },
    getSubscribeAirports () {
      let result = []
      let data = LoginInfo.state.userStorage.user.aiisAirports
      for (let i = 0; i < data.length; i++) {
        result.push(data[i].airportCode)
      }
      return result
    },
    onDrag (d) {
      console.log('onDrag', d)
      d3.select(`#d${d.id}`)
        .attr('cx', d.coordX = d3.event.x)
        .attr('cy', d.coordY = d3.event.y)
    },

    onDragEnd (d) {
      console.log('drag end x:', d3.event.x, ' y:', d3.event.y)
      this.changedDeviceData.push(d)
    },

    onMouseover (d) {
      if (this.dragEnable === true) {
        return
      }
      console.log('mouseover')
      console.log('mouseX:', d3.event.x, ' mouseY:', d3.event.y)
      if (this.temp !== undefined) {
        clearTimeout(this.timeOutId)
        this.temp.remove()
      }
      this.temp = d3.select('#circles')
        .append('g')
      let textWidth = d.deviceIp.length > d.deviceName.length ? d.deviceIp.length * config.MESSAGE_TEXT_FONT_SIZE * 0.5 : d.deviceName.length * config.MESSAGE_TEXT_FONT_SIZE * 0.5
      let text = this.temp.append('text')
        .attr('font-size', config.MESSAGE_TEXT_FONT_SIZE + 'px')
        .append('tspan')
        .attr('x', d.coordX - textWidth / 2)
        .attr('y', d.coordY + config.CIRCLE_R + config.MESSAGE_TEXT_FONT_SIZE)
        .text(d.deviceIp)
        .append('tspan')
        .attr('x', d.coordX - textWidth / 2)
        .attr('y', d.coordY + config.CIRCLE_R + config.MESSAGE_TEXT_FONT_SIZE + config.MESSAGE_TEXT_FONT_SIZE)
        .text(d.deviceName)
      let textBox = text.node().getBBox()
      this.temp.append('rect')
        .attr('x', textBox.x)
        .attr('y', textBox.y)
        .attr('width', textBox.width)
        .attr('height', textBox.height)
        .style('fill', 'blue')
        .style('fill-opacity', '.3')
        .style('stroke', '#666')
        .style('stroke-width', '1.5px')
    },
    onMouseout (d) {
      if (this.dragEnable === true) {
        return
      }
      this.timeOutId = setTimeout(() => {
        this.temp.remove()
      }, 5000)
    },
    onRightClick (d) {
      console.log('right click')
      d3.event.preventDefault()
      let meniu = d3.select('#meniu')
      meniu.remove()
      meniu = d3.select('#circles')
        .append('g')
        .attr('id', 'meniu')
      meniu.append('rect')
        .attr('x', d.coordX + config.CIRCLE_R + config.MENIU_MOVE_X)
        .attr('y', d.coordY - config.MENIU_TEXT_FONT_SIZE - config.MENIU_MARGIN_SIZE + config.MENIU_MOVE_Y)
        .attr('width', 2 * config.MENIU_TEXT_FONT_SIZE + 2 * config.MENIU_MARGIN_SIZE)
        .attr('height', this.meniu_items.length * config.MENIU_TEXT_FONT_SIZE + (this.meniu_items.length + 1) * config.MENIU_MARGIN_SIZE)
        .style('fill', config.MENIU_BACKGROUND_COLOR)
      let meniuItems = meniu.append('g')
        .attr('id', 'meniuItems')
      meniuItems.selectAll('text')
        .data(this.meniu_items)
        .enter()
        .append('text')
        .attr('x', d.coordX + config.CIRCLE_R + config.MENIU_MOVE_X + config.MENIU_MARGIN_SIZE)
        .attr('y', function (da, i) { return d.coordY + config.MENIU_MOVE_Y + i * (config.MENIU_TEXT_FONT_SIZE + config.MENIU_MARGIN_SIZE) })
        .attr('font-size', config.MENIU_TEXT_FONT_SIZE + 'px')
        .text(function (da, i) {
          console.log(da, ' ', i)
          return da
        })
      let textNodes = meniuItems.selectAll('text').nodes()
      meniuItems.selectAll('rect')
        .data(textNodes)
        .enter()
        .append('rect')
        .attr('x', function (td) {
          return td.getBBox().x
        })
        .attr('y', function (td) {
          return td.getBBox().y
        })
        .attr('width', function (td) {
          return td.getBBox().width
        })
        .attr('height', function (td) {
          return td.getBBox().height
        })
        .style('fill', config.MENIU_ITEM_BACKGROUND_COLOR)
        .style('fill-opacity', config.MENIU_ITEM_FILL_OPACITY)
        .on('mouseover', function (td, i) {
          console.log('onMouseover:', i)
          d3.select(this).style('fill', config.MENIU_ITEM_HINT_COCLOR)
        })
        .on('mouseout', function (td, i) {
          console.log('onMouseout:', i)
          d3.select(this).style('fill', config.MENIU_ITEM_BACKGROUND_COLOR)
        })
        .on('click', function (td, i) {
          console.log('click on ', this.meniu_items[i])
          d3.select('#meniu').remove()
          if (i === 0) {
            // 删除设备
            // show dialog...
            this.$confirm(`确定删除IP为:${d.deviceIp}设备？`, '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              let parse = { id: d.id }
              API.removeDeviceInfo().go(parse).then((data) => {
                console.log('delete device message:', data)
                if (data.ok) {
                  this.deleteElement('d' + d.id)
                  this.$message({
                    type: 'success',
                    message: '删除成功!'
                  })
                }
              })
            }).catch(() => {
              this.$message({
                type: 'info',
                message: '已取消删除'
              })
            })
          } else if (i === 1) {
            // 编辑设备信息
          } else {
            // 其他
          }
        }.bind(this))
    },
    deleteElement (id) {
      console.log('deleteElement id:', id)
      for (let i = 0; i < this.svgDeviceDatas.length; i++) {
        if (('d' + this.svgDeviceDatas[i].id) === id) {
          d3.select('#' + id)
            .remove()
          this.svgDeviceDatas.splice(i, 1)
          break
        }
      }
      for (let i = 0; i < this.subDevices.length; i++) {
        if (('d' + this.subDevices[i].id) === id) {
          this.subDevices.splice(i, 1)
          break
        }
      }
    }
  },
  watch: {
    dragEnable (value) {
      console.log(value)
      if (value) {
        d3.selectAll('circle')
          .on('mouseover', null)
          .on('mouseout', null)
          .on('contextmenu', this.onRightClick)
          .call(d3.drag().on('drag', this.onDrag).on('end', this.onDragEnd))
      } else {
        d3.selectAll('circle')
          .on('mouseover', this.onMouseover)
          .on('mouseout', this.onMouseout)
          .on('contextmenu', this.null)
          .on('.drag', null)
      }
    },
    filters (newFilters) {
      console.log(newFilters.airportCode)
    },
    subDevices (newSubDevices) {
      this.mountDevicesPoints()
    },
    inputData (value) {
      console.log('input value:', value)
      let serchedDevices = []
      this.subDevices.forEach((item) => {
        if (item.deviceIp.indexOf(value) !== -1) {
          console.log('serched IP:', item.deviceIp)
          serchedDevices.push(Object.assign({}, item))
        }
        if (item.deviceName.indexOf(value) !== -1) {
          console.log('serched IP:', item.deviceIp)
          serchedDevices.push(Object.assign({}, item))
        }
      })
      d3.selectAll('circle')
        .attr('r', config.CIRCLE_R)
      serchedDevices.forEach((item) => {
        d3.select(`#d${item.id}`)
          .attr('r', config.CIRCLE_R * 2)
      })
      if (value === '') {
        d3.selectAll('circle')
          .attr('r', config.CIRCLE_R)
      }
    }
  }
}
</script>

<style>
.circle {
  width: 20px;
  height: 20px;
  background: green;
  -moz-border-radius: 10px;
  -webkit-border-radius: 10px;
  border-radius: 10px;
}
.h-line {
  margin-top: 10px;
  padding: 0;
  width: auto;
  height: 1px;
  background-color: #8aabe5;
  overflow: hidden;
}
</style>
