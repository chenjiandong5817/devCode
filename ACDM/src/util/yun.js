import { Notification, Message } from 'element-ui'
import { getStore } from './store'
import { validatenull } from './validate'

/**
 * ip转字符串，如果complete为true，则补0
 * @param {*} complete
 */
export const ip2String = (ip, complete = true) => {
  if (!ip) {
    return ''
  }
  let completeZoro = (str) => {
    if (str.length < 3 && complete) {
      str = '0' + str
      return completeZoro(str)
    }
    return str
  }
  let array = ip.split('.')
  array = array.map(item => {
    return completeZoro(item)
  })
  return array.join('')
}

/**
 * 获取url参数
 */
export const getQueryString = (url, name) => {
  var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)')
  var r = url.substr(url.indexOf('?') + 1).match(reg)
  if (r != null) return unescape(r[2])
  return null
}
/**
 * 获取url参数，object类型
 */
export const getQueryObject = (url) => {
  var params = {}
  if (url.indexOf('?') !== -1) {
    let query = url.substr(url.indexOf('?'))
    let strs = query.split('&')
    for (var i = 0; i < strs.length; i++) {
      let strKeyValue = strs[i].split('=')
      query[strKeyValue[0]] = unescape(strKeyValue[1])
    }
  }
  return params
}

/**
 * object 转成string参数
 */
export const parseParam = (param) => {
  let paramStr = ''
  if (param instanceof Object) {
    for (let o in param) {
      if (!validatenull(param[o])) {
        paramStr = `${paramStr}${o}=${param[o]}&`
      }
    }
  }
  return paramStr.substr(0, paramStr.length - 1)
}

/**
 * 弹窗
 */
export const messageBox = function (conf) {
  if (!(conf instanceof Object)) {
    Message({
      title: '错误提示',
      message: conf,
      type: 'info'
    })
  } else if (conf.type === 1) {
    Notification({
      title: conf.title,
      message: conf.msg,
      type: conf.boxtype
    })
  } else {
    Message({
      title: conf.title,
      message: conf.msg,
      type: conf.boxtype
    })
  }
}
/**
 * 根据身份证计算年龄，性别
 */
export const IdCard = function (UUserCard, num) {
  if (UUserCard.length === 18) {
    if (num === 1) {
      // 获取出生日期
      let birth = ''
      birth = UUserCard.substring(6, 10) + '-' + UUserCard.substring(10, 12) + '-' + UUserCard.substring(12, 14)
      return birth
    }
    if (num === 2) {
      // 获取性别
      if (parseInt(UUserCard.substr(16, 1)) % 2 === 1) {
        // 男
        return '1'
      } else {
        // 女
        return '2'
      }
    }
    if (num === 3) {
      // 获取年龄
      var myDate = new Date()
      var month = myDate.getMonth() + 1
      var day = myDate.getDate()
      var age = myDate.getFullYear() - UUserCard.substring(6, 10) - 1
      if (UUserCard.substring(10, 12) < month || (UUserCard.substring(10, 12) === month && UUserCard.substring(12, 14) <= day)) {
        age++
      }
      return age
    }
  }
  return ''
}

/**
 * Object的属性致空，但是属性存在
 */
export const setObjectnull = function (obj) {
  for (var o in obj) {
    obj[o] = ''
  }
  return obj
}
/**
 * Object的属性为null的至为空字符串
 */
export const setObjectstr = function (obj) {
  for (var o in obj) {
    if (obj[o] === null || obj[o] === 'null') {
      obj[o] = ''
    }
  }
  return obj
}
/**
 * JSON格式化
 */
export const formatJson = function (json, options) {
  var reg = null
  let formatted = ''
  let pad = 0
  let PADDING = '    '
  options = options || {}
  options.newlineAfterColonIfBeforeBraceOrBracket = (options.newlineAfterColonIfBeforeBraceOrBracket === true)
  options.spaceAfterColon = !(options.spaceAfterColon === false)
  if (typeof json !== 'string') {
    json = JSON.stringify(json)
  } else {
    try {
      json = JSON.parse(json)
    } catch (e) {
      /* eslint-disable no-new */
      new Error('不是JSON对象')
    }
    json = JSON.stringify(json)
  }
  reg = /([{}])/g
  json = json.replace(reg, '\r\n$1\r\n')
  reg = /([[]])/g
  json = json.replace(reg, '\r\n$1\r\n')
  reg = /(,)/g
  json = json.replace(reg, '$1\r\n')
  reg = /(\r\n\r\n)/g
  json = json.replace(reg, '\r\n')
  reg = /\r\n,/g
  json = json.replace(reg, ',')
  if (!options.newlineAfterColonIfBeforeBraceOrBracket) {
    reg = /:\r\n\{/g
    json = json.replace(reg, ':{')
    reg = /:\r\n\[/g
    json = json.replace(reg, ':[')
  }
  if (options.spaceAfterColon) {
    reg = /:/g
    json = json.replace(reg, ':')
  }
  (json.split('\r\n')).forEach(function (node, index) {
    var i = 0
    let indent = 0
    let padding = ''
    if (node.match(/\{$/) || node.match(/\[$/)) {
      indent = 1
    } else if (node.match(/\}/) || node.match(/\]/)) {
      if (pad !== 0) {
        pad -= 1
      }
    } else {
      indent = 0
    }
    for (i = 0; i < pad; i++) {
      padding += PADDING
    }
    formatted += padding + node + '\r\n'
    pad += indent
  })
  return formatted
}
/**
 * 查找字符串是否存在
 */
export const findStrArray = (dic, value) => {
  if (!validatenull(dic)) {
    for (let i = 0; i < dic.length; i++) {
      if (dic[i] === value) {
        return i
      }
    }
  }
  return -1
}
/**
 * 查找对象数组是否存在
 */
export const findObjArray = (dic, obj, v1, v2) => {
  v1 = v1 || 'value'
  v2 = v2 || 'value'
  for (let i = 0; i < dic.length; i++) {
    let o = dic[i]
    if (o[v1] === obj[v2]) {
      return i
    }
  }
  return -1
}
/**
 * 根据字典的值取缓存里面的
 */
export const getDicvalue = (obj) => {
  // let flag = 0
  // if (obj === 'Area_CD1' || obj === 'INDUSTRY_CLASS') {
  //   flag = 1000
  // }
  if (localStorage.getItem(obj) === null) {
    /* eslint-disable no-eval */
    return eval(obj)
  } else {
    return JSON.parse(localStorage.getItem(obj))
  }
}
/**
 * 验证俩个对象的属性值是否相等
 */
export const validObj = (olds, news) => {
  var flag = true
  for (var obj in news) {
    if (news[obj] !== olds[obj]) {
      flag = false
      break
    }
  }
  return flag
}
/**
 * 数据转换器
 */
export const dataFormat = (data, type) => {
  data = getStore(data) || null
  if (validatenull(data)) return undefined
  if (type === 1) { // 转json对象
    return JSON.parse(data)
  } else if (type === 2) { // 转Boolean对象
    return eval(data)
  } else {
    return data
  }
}

// 删除数组制定元素
export const removeByValue = (arr, val) => {
  for (var i = 0; i < arr.length; i++) {
    if (arr[i] === val) {
      arr.splice(i, 1)
      return arr
    }
  }
}
