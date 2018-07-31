/*
* @Author: chenjiandong
* @Date:   last_modified_time
* @Last Modified by:   chenjiandong
* @Last Modified time: last_modified_time
*/
/*
 * @Author: cdroid
 * @Date: 2017-05-25 10:42:04
 * @Last Modified by: ylj
 * @Last Modified time: 2017-10-11 09:40:33
 * @Description: 各种工具方法
 */

import { Notification } from 'element-ui'

var SIGN_REGEXP = /([yMdhsm])(\1*)/g

var DEFAULT_PATTERN = 'yyyy-MM-dd'
function padding (s, len) {
  len = len - (s + '').length
  for (var i = 0; i < len; i++) { s = '0' + s }
  return s
}

export default {
  getQueryStringByName: function (name) {
    var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i')
    var r = window.location.search.substr(1).match(reg)
    var context = ''
    if (r != null) {
      context = r[2]
    }
    reg = null
    r = null
    return context === null || context === '' || context === 'undefined' ? '' : context
  },
  formatDate: {
    format: function (date, pattern) {
      pattern = pattern || DEFAULT_PATTERN
      return pattern.replace(SIGN_REGEXP, function ($0) {
        switch ($0.charAt(0)) {
          case 'y': return padding(date.getFullYear(), $0.length)
          case 'M': return padding(date.getMonth() + 1, $0.length)
          case 'd': return padding(date.getDate(), $0.length)
          case 'w': return date.getDay() + 1
          case 'h': return padding(date.getHours(), $0.length)
          case 'm': return padding(date.getMinutes(), $0.length)
          case 's': return padding(date.getSeconds(), $0.length)
        }
      })
    },
    parse: function (dateString, pattern) {
      var matchs1 = pattern.match(SIGN_REGEXP)
      var matchs2 = dateString.match(/(\d)+/g)
      if (matchs1.length === matchs2.length) {
        var _date = new Date(1970, 0, 1)
        for (var i = 0; i < matchs1.length; i++) {
          var _int = parseInt(matchs2[i])
          var sign = matchs1[i]
          switch (sign.charAt(0)) {
            case 'y': {
              _date.setFullYear(_int)
              break
            }
            case 'M': {
              _date.setMonth(_int - 1)
              break
            }
            case 'd': {
              _date.setDate(_int)
              break
            }
            case 'h': {
              _date.setHours(_int)
              break
            }
            case 'm': {
              _date.setMinutes(_int)
              break
            }
            case 's': {
              _date.setSeconds(_int)
              break
            }
          }
        }
        return _date
      }
      return null
    },
    flightDateFmt: function (fmt, date, isComp, cdate) {
      if (date === undefined || date === null) {
        return ''
      }
      date = new Date(date)
      fmt = this.format(new Date(date), fmt)
      if (isComp && cdate !== null && cdate !== undefined) {
        cdate = new Date(cdate.replace(/ - /g, ''))
        var diffDays = parseInt((date.getTime() - cdate.getTime()) / (1000 * 60 * 60 * 24))
        if (diffDays >= 1) {
          fmt = fmt + '+'
        } else if (diffDays < 0) {
          fmt = fmt + '-'
        }
      }
      return fmt === '0000' ? '' : fmt
    }
  },
  uuid: function (len, radix) {
    var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('')
    var uuid = []
    var i
    radix = radix || chars.length

    if (len) {
      // Compact form
      for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random() * radix]
    } else {
      // rfc4122, version 4 form
      var r

      // rfc4122 requires these characters
      uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-'
      uuid[14] = '4'

      // Fill in random data.  At i==19 set the high bits of clock sequence as
      // per rfc4122, sec. 4.1.5
      for (i = 0; i < 36; i++) {
        if (!uuid[i]) {
          r = 0 | Math.random() * 16
          uuid[i] = chars[(i === 19) ? (r & 0x3) | 0x8 : r]
        }
      }
    }

    return uuid.join('')
  },
  str2Bytes: function (str) {
    var ch
    var st
    var re = []
    for (var i = 0; i < str.length; i++) {
      ch = str.charCodeAt(i)  // get char
      st = []                 // set up "stack"
      do {
        st.push(ch & 0xFF)  // push byte to stack
        ch = ch >> 8          // shift value down by 1 byte
      }
      while (ch)
      // add stack contents to result
      // done because chars have "wrong" endianness
      re = re.concat(st.reverse())
    }
    // return an array of bytes
    return re
  },
  notifyBody: function (ok, msg) {
    var result = {
      title: '',
      message: msg || '',
      type: ''
    }
    if (ok) {
      result.title = '成功'
      result.type = 'success'
    } else {
      result.title = '失败'
      result.type = 'error'
    }
    return result
  },
  deepCopy: function (o) {
    if (o instanceof Function) {
      return o
    } else if (o instanceof Array) {
      var array = []
      for (var i = 0; i < o.length; ++i) {
        array[i] = this.deepCopy(o[i])
      }
      return array
    } else if (o instanceof Object) {
      var obj = {}
      for (var j in o) {
        obj[j] = this.deepCopy(o[j])
      }
      return obj
    } else {
      return o
    }
  },
  responseError: function (status) {
    let title = '错误'
    if (status === 403) {
      title = '获取权限失败，请重新登陆'
    } else if (status === 504) {
      title = '连接服务器失败'
    }
    Notification({
      title: title,
      message: status,
      type: 'error'
    })
  },
  typedAirport: function (data) {
    // console.log('data', data)
    var arrayAirportName = []
    var result = []
    for (var i = 0; i < data.length; i++) {
      if (data[i].airportcnname !== null) {
        arrayAirportName.push(data[i].airportcnname)
      }
    }
    arrayAirportName = arrayAirportName.sort(
    (param1, param2) => {
      // debugger
      return param1.localeCompare(param2)
    }
    )
    for (var j = 0; j < arrayAirportName.length; j++) {
      for (var k = 0; k < data.length; k++) {
        var object = {}
        if (arrayAirportName[j] === data[k].airportcnname) {
          object['text1'] = data[k].displaycnname
          object['text'] = data[k].displaycnname
          object['nature'] = data[k].airportnature
          object['code'] = data[k].iatacode + '/' + data[k].icaocode
          object['value'] = data[k].iatacode
          // 此处添加一个属性，用于在select框里的显示值
          object['labelShow'] = data[k].displaycnname + '(' + data[k].iatacode + ')'
          result.push(object)
        }
      }
    }
    return result
  }
}
