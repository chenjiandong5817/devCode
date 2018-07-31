/*
 * @Author: llf
 * @Date: 2017-06-08 09:22:17
 * @Last Modified by: mikey.zhaopeng
 * @Last Modified time: 2017-08-11 17:36:00
 * @Description: 航班动态
 */
import {axios, base} from './raiis-axios'

let defaultType = 'dynamic flight'

export const getDynamicFlight = () => {
  let url = `${base}/flights`
  return {
    name: 'getDynamicFlight',
    type: defaultType,
    url: url,
    requetType: 'GET',
    remark: '获取航班列表',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }
  }
}

export const addDynamicFlight = () => {
  let url = `${base}/flights`
  return {
    name: 'addDynamicFlight',
    type: defaultType,
    url: url,
    requetType: 'POST',
    remark: '新增航班信息',
    go: params => { return axios.post(url, params).then(res => res.data) }
  }
}

export const editDynamicFlight = () => {
  let url = `${base}/flights`
  return {
    name: 'editDynamicFlight',
    type: defaultType,
    url: url,
    requetType: 'PUT',
    remark: '修改航班信息',
    go: params => { return axios.put(url, params).then(res => res.data) }
  }
}

export const removeDynamicFlight = () => {
  let url = `${base}/flights`
  return {
    name: 'removeDynamicFlight',
    type: defaultType,
    url: url,
    requetType: 'DELETE',
    remark: '删除航班信息',
    go: params => {
      return axios.delete(url + '/' + params.flightId, params).then(res => res.data)
    }
  }
}

export const getReplaceDataLs = (apiName) => {
  let url = `${base}/` + apiName
  return {
    name: 'getReplaceDataLs',
    type: defaultType,
    url: url,
    requetType: 'GET',
    remark: '获取替换文本',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }
  }
}

export const dateFmt = (fmt, date, row, isComp) => {
  if (date === undefined || date === null) {
    return ''
  }
  var s = date.replace(/ - /g, '')
  date = new Date(s)
  var o = {'M+': date.getMonth() + 1, 'd+': date.getDate(), 'h+': date.getHours(), 'm+': date.getMinutes(), 's+': date.getSeconds(), 'q+': Math.floor((date.getMonth() + 3) / 3), 'S+': date.getMilliseconds()}
  if (/(y+)/i.test(fmt)) {
    fmt = fmt.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length))
  }
  for (var k in o) {
    if (new RegExp('(' + k + ')').test(fmt)) {
      fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (('00' + o[k]).substr(('' + o[k]).length)))
    }
  }
  if (isComp) {
    var opDate = new Date(row.opDate.replace(/ - /g, ''))
    var diffDays = parseInt(Math.abs(opDate - date) / (1000 * 60 * 60 * 24))
    if (diffDays >= 1) {
      fmt = fmt + '+'
    } else if (diffDays < 0) {
      fmt = fmt + '-'
    }
  }
  return fmt
}

// 操作航班数据
export const opeateFlightData = (appendUrl, opeateCode, opeateName) => {
  let url = `${base}/` + appendUrl
  return {
    name: opeateCode,
    type: defaultType,
    url: url,
    requetType: 'PUT',
    remark: opeateName,
    go: params => { return axios.put(url, params).then(res => res.data) }
  }
}

// export const opeateFlightData = () => {
//   let url = `${base}/flights/simple`
//   return {
//     name: 'flightDep',
//     type: defaultType,
//     url: url,
//     requetType: 'PUT',
//     remark: '航班起飞',
//     go: params => { return axios.put(url, params).then(res => res.data) }
//   }
// }
