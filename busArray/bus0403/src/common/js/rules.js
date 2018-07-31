/*
* @Author: cdroid
* @Date:   2018-01-02 08:51:25
* @Last Modified by:   chenjiandong
* @Last Modified time: 2018-01-04 11:11:55
*/

/*
 * @Author: cdroid
 * @Date: 2017-05-25 10:50:55
 * @Last Modified by: ylj
 * @Last Modified time: 2017-12-29 16:36:54
 * @Description:  表单校验规则
 */
export default {
  // 校验电话号码
  telephone: function (rule, value, callback) {
    var ret = /^1((3|5|8){1}\d{1}|70)\d{8}$/
    if (value && !ret.test(value)) {
      callback(new Error('请输入正确的电话号码'))
    } else {
      callback()
    }
  },
  time: function (rule, value, callback) {
    var regTime = /^(?:(?:[0-2][0-3])|(?:[0-1][0-9]))[0-5][0-9]$/
    if (value && !regTime.test(value)) {
      callback(new Error('请输入正确的时间格式[0000-2359]'))
    } else {
      callback()
    }
  },
  numberUpperZero: function (rule, value, callback) {
    if (value < 0) {
      return 0
    } else {
      callback()
    }
  },
  checkNum: function (rule, value, callback) {
    if (value >= rule.minVal && value <= rule.maxVal) {
      callback()
    } else {
      callback(new Error('数值区间' + rule.minVal + '~' + rule.maxVal))
    }
  },
  arrayRequire: function (rule, value, callback) {
    if (value.length > 0) {
      callback()
    } else {
      callback(new Error(rule.message))
    }
  },
  validSegment: function (rule, value, callback) {
    // 校验航段配置
    if (rule.preDestination !== 0 && value !== rule.preDestination) {
      callback(new Error(rule.message))
    } else {
      callback()
    }
  },
  validSegTime: function (rule, value, callback) {
    // 校验航段时间配置
    let moment = require('moment')
    if (value === null || value === '' || rule.preTime === null || rule.preTime === '') {
      callback()
    }
    if (!(rule.timeType === 'depTime' && rule.preTime === 0)) {
      let time1 = typeof value === 'object' ? moment(value).format('YYYY-MM-DD HH:mm:ss') : value
      let time2 = typeof rule.preTime === 'object' ? moment(rule.preTime).format('YYYY-MM-DD HH:mm:ss') : rule.preTime
      if (time1 < time2) {
        callback(new Error(rule.message))
      } else {
        callback()
      }
    }
  },
  validSegTimeType: function (rule, value, callback) {
    // 校验时间格式 yyyy-mm-dd hh:mm:ss(yyyy/mm/dd hh:mm:ss)
    // let regexp = /^(\d{4})(-|\/)(\d{2})(-|\/)(\d{2}) (\d{2}):(\d{2}):(\d{2})$/
    let moment = require('moment')
    let regexp = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/
    if (value !== null && value !== '') {
      if (typeof value === 'string') {
        if (!regexp.test(value)) {
          callback(new Error(rule.message))
        } else {
          let date = new Date(value)
          if (moment(date).format('YYYY-MM-DD HH:mm:ss') !== value) {
            callback(new Error(rule.message))
          } else {
            callback()
          }
        }
      } else {
        return typeof value.getTime === 'function' && typeof value.getMonth === 'function' && typeof value.getYear === 'function'
      }
    }
  }
}
