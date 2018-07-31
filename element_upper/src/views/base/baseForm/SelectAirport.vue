<!-- /*
 * @Author: cdroid
 * @Date: 2017-05-25 10:42:17
 * @Last Modified by: mikey.zhaopeng
 * @Last Modified time: 2017-06-27 15:33:48
 * @Description: 通用机场列表界面
 */ -->
<template>
    <el-select v-model="airportCode" filterable placeholder="请选择机场" @change="selectItem" style="width: 250px!important">
      <!-- 下拉框分组 -->
      <el-option-group
        v-for="group in airportsTypesChoose"
        :key="group.label"
        :label="group.label">
        <el-option
          v-for="item in group.options"
          :key="item.value"
          :label="item.labelShow"
          :value="item.value">
            <span style="float: left">{{ item.text }}</span>
            <span style="float: right">{{ item.code }}</span>
        </el-option>
      </el-option-group>
    </el-select>
</template>

<script>
  import Util from './../../../common/js/util'

  export default {
    data () {
      return {
        airportCode: JSON.parse(sessionStorage.getItem('userStorage')).user.aiisAirports[0].airportCode,
        adminAirportChoose: [],
        airportsTypesChoose: [],
        airports: []
      }
    },
    methods: {
      // 每次用户点击选择的时候
      selectItem: function () {
        this.$emit('airportCodeChange', this.airportCode)
      },
      // 获取通用机场的列表
      getAirportList: function () {
          // 筛选出登录用户的订阅机场
          var resultCache = [
            {
              label: '国际',
              labelkey: 'I',
              options: []
            },
            {
              label: '国内',
              labelkey: 'D',
              options: []
            }
          ]
          this.adminAirportChoose = []
          this.airports = Util.typedAirport(this.$cache.array.airports)
          var subscibeAirport = JSON.parse(localStorage.getItem('AdminSubscribeAirportJson'))
          for (var i = 0; i < subscibeAirport.length; i++) {
            for (var j = 0; j < this.airports.length; j++) {
              if (subscibeAirport[i].airportCode === this.airports[j].value) {
                this.adminAirportChoose.push(this.airports[j])
              }
            }
          }
          // 对下拉框的数据进行分组
          for (var a = 0; a < this.adminAirportChoose.length; a++) {
            if (this.adminAirportChoose[a].nature === 'I') {
              resultCache[0].options.push(this.adminAirportChoose[a])
            }
            if (this.adminAirportChoose[a].nature === 'D') {
              resultCache[1].options.push(this.adminAirportChoose[a])
            }
          }
          this.airportsTypesChoose = resultCache
          // 这边设置一个对象用于返回值
          var airportsData = { adminAirportChoose: this.adminAirportChoose, airportsTypesChoose: this.airportsTypesChoose }
          return airportsData
      }
    }
  }

</script>

<style scoped>

</style>
