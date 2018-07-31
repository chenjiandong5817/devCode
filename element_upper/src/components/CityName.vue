/*
 * @Author: chenyang
 * @Date: 2017-11-09 15:18:52
 * @Last Modified by: chenyang
 * @Last Modified time: 2017-11-13 15:26:45
 * 机场下拉菜单模板
 */

<template>
  <el-select placeholder="选择机场" v-model="airportCode" @change="clickItem" :filterable="true">
    <el-option-group
      v-for="group in option"
      :key="group.label"
      :label="group.label">
      <el-option v-for="item in group.cities" :key="item.value" :label="item.label" :value="item.value">
        <span style="float: left">{{ item.chineseName }}</span>
        <span style="float: right">{{ item.code }}</span>
      </el-option>
    </el-option-group>
  </el-select>
</template>

<script>
export default {
  data () {
    return {
      airportList: [],
      obj: {value: '', label: '', code: '', chineseName: ''},
      airportCode: JSON.parse(sessionStorage.getItem('userStorage')).user.aiisAirports[0].airportCode,
      option: [{
        label: '',
        cities: [
          {
            value: '',
            label: '',
            code: '',
            chineseName: ''
          }
        ]
      }]
    }
  },
  watch: {
    '$store.state.cached' (val, oldVal) {
      this.setCitys(this.airportList)
    }
  },
  methods: {
    clickItem () {
      this.$emit('getAirportName', this.airportCode)
    },
    setValue (data) {
      this.airportCode = data
    },
    setCitys (airports) {  // 传递的是citys的列表['XMN','LCX']
      this.airportList = airports
      let area = this.$cache.fetch('flightnatures')
      let airportInfo = this.$cache.fetch('airports')
      this.option = []
      for (let z = 0; z < area.length; z++) {
        let airportTemp = []
        for (let j = 0; j < airports.length; j++) {
          for (let i = 0; i < airportInfo.length; i++) {
            if (airports[j] === airportInfo[i].iatacode && airportInfo[i].airportnature === area[z].flightNatureCode) {
              this.obj = {value: '', label: '', code: '', chineseName: ''}
              this.obj.value = airportInfo[i].iatacode
              this.obj.code = airportInfo[i].iatacode + '/' + airportInfo[i].icaocode
              if (airportInfo[i].cnabbr2w === '' || airportInfo[i].cnabbr2w === null) {
                this.obj.label = '暂无名字' + '(' + airportInfo[i].iatacode + ')'
                this.obj.chineseName = '暂无名字'
              } else {
                this.obj.label = airportInfo[i].cnabbr2w + '(' + airportInfo[i].iatacode + ')'
                this.obj.chineseName = airportInfo[i].cnabbr2w
              }
              airportTemp.push(this.obj)
              break
            }
          }
        }
        if (airportTemp.length !== 0) {
          let temp = {label: area[z].description, cities: airportTemp}
          this.option.push(temp)
        }
      }
    }
  }
}
</script>

<style>

</style>
