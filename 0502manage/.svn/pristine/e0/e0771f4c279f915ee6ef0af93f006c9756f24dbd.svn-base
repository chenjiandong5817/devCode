/*
* @Author: cdroid
* @Date:   2018-01-02 08:51:25
* @Last Modified by:   chenjiandong
* @Last Modified time: 2018-03-21 15:28:18
*/

// import loginInfo from '../../vuex/store'
// 这边引入缓存，用于格式化数据，原来是写死的
// import cache from './cache'
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
    }
}
