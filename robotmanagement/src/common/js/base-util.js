/*
* @Author: cdroid
* @Date:   2018-01-02 08:51:25
* @Last Modified by:   chenjiandong
* @Last Modified time: 2018-03-12 16:30:41
*/

import store from '../../vuex/store'
import router from 'vue-router'
// 这边引入缓存，用于格式化数据，原来是写死的
// import cache from './cache'
export default {
    formatterIsNo: function (row, column, cellValue) {
      if (cellValue === 1) {
        return '启用'
      }
      if (cellValue === 0) {
        return '禁用'
      }
    },
    formatterDated: function (row, column, cellValue) {
        var dataFormated = null
        var moment = null
        moment = require('moment')
        if (cellValue === null) {
          dataFormated = '暂无'
        } else {
          dataFormated = moment(cellValue).format('YYYY-MM-DD HH:mm:ss')
        }
        return dataFormated
    },
    returnLogin: function () {
      console.log(1)
      store.dispatch('removeUserStorage')
      router.push('/login')
    }
}
