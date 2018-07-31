/*
* @Author: chenjiandong
* @Date:   2017-09-07 15:48:37
* @Last Modified by:   chenjiandong
* @Last Modified time: last_modified_time
*/
import loginInfo from '../../vuex/store'
// 这边引入缓存，用于格式化数据，原来是写死的
import cache from './cache'
export default {
      // 对是否远机位进行格式化
    formatterIsNo: function (row, column, cellValue) {
      if (cellValue === '1') {
        return '是'
      }
      if (cellValue === '0') {
        return '否'
      }
    },
    // 对是否离港进行格式化
    formatterDirection: function (row, column, cellValue) {
      for (var i = 0; i < cache.array.flightdirections.length; i++) {
        if (cellValue === cache.array.flightdirections[i].directionCode) {
          return cache.array.flightdirections[i].description
        }
      }
    },
    // 对状态进行格式化
    formatterStatus: function (row, column, cellValue) {
      for (var i = 0; i < cache.array.resourcestatus.length; i++) {
        if (cellValue === cache.array.resourcestatus[i].statusCode) {
          return cache.array.resourcestatus[i].description
        }
      }
    },
    // 对地区进行格式化
    formatterNature: function (row, column, cellValue) {
      for (var i = 0; i < cache.array.flightnatures.length; i++) {
        if (cellValue === cache.array.flightnatures[i].flightNatureCode) {
          return cache.array.flightnatures[i].description
        }
      }
    },
    // 对是否离港进行格式化
    formatterCarouselType: function (row, column, cellValue) {
      if (cellValue === 'A') {
        return '进'
      }
      if (cellValue === 'D') {
        return '出'
      }
    },
    // 对是否禁用进行格式化
    formatterDisable: function (row, column, cellValue) {
      if (cellValue === 1) {
        return '禁用'
      }
      if (cellValue === 0) {
        return '启用'
      }
    },
    // 对是否禁用启用进行格式化返回的数据是字符串
    formatterDisableString: function (row, column, cellValue) {
      if (cellValue === '1') {
        return '禁用'
      }
      if (cellValue === '0') {
        return '启用'
      }
    },
    // 对是否虚拟航空公司进行格式化
    formatterIsvirtual: function (row, column, cellValue) {
      if (cellValue === '0') {
        return '非虚拟航空公司'
      }
      if (cellValue === '1') {
        return '虚拟航空公司'
      }
    },
    // 转换为大小写
    // toUpperCaseInput: function (data) {
    //   return data.toUpperCase()
    // }
    setSubAirportls () {
      // 设置绑定用户管辖运营机场
      let result = []
      let list = loginInfo.state.userStorage.user.aiisAirports
      for (var i = 0; i < list.length; i++) {
        let value = list[i].airportCode
        let text = list[i].airport !== undefined ? list[i].airport.cnabbr2w : ''
        text = text === undefined ? '' : text
        let obj = { value: value, text: text }
        result.push(obj)
      }
      return result
    },
    // 获取用户订阅机场信息
    getSubscribeAirports () {
      let result = []
      let data = loginInfo.state.userStorage.user.aiisAirports
      for (let i = 0; i < data.length; i++) {
        result.push(data[i].airportCode)
      }
      return result
    },
    // 获取区域属性的下拉框的数据
    getFlightNatureChoose: function () {
      var result = []
      for (var i = 0; i < cache.array.flightnatures.length; i++) {
        var item = {}
        item['text'] = cache.array.flightnatures[i].description
        item['value'] = cache.array.flightnatures[i].flightNatureCode
        result.push(item)
      }
      return result
    },
    // 获取状态下拉框属性的数据
    getResourceStatusChoose: function () {
      var result = []
      for (var i = 0; i < cache.array.resourcestatus.length; i++) {
        var item = {}
        item['text'] = cache.array.resourcestatus[i].description
        item['value'] = cache.array.resourcestatus[i].statusCode
        result.push(item)
      }
      return result
    },
    getFlightDirectionChoose: function () {
      var result = []
      for (var i = 0; i < cache.array.flightdirections.length; i++) {
        var item = {}
        item['text'] = cache.array.flightdirections[i].description
        item['value'] = cache.array.flightdirections[i].directionCode
        result.push(item)
      }
      return result
    }
}

