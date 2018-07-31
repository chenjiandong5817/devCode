/*
 * @Author: ylj
 * @Date: 2017-09-23 11:09:03
 * @Last Modified by: ylj
 * @Last Modified time: 2017-11-07 17:30:47
 */
import {axios, base} from './../raiis-axios'

let defaultType = 'checkincounter allocation'

// 获取航班值机柜台资源信息
export const getCkCounterList = () => {
  let url = `${base}/allocation/checkincounter`
  return {
    name: 'getCheckCounterList',
    type: defaultType,
    url: url,
    requetType: 'GET',
    remark: '获取航班值机柜台资源信息',
    go: params => { return axios.get(url + '/' + params.flightId, { params: params }).then(res => res.data) }
  }
}

// 编辑航班值机柜台资源信息
export const editCkCounterRes = () => {
  let url = `${base}/allocation/checkincounter/list`
  return {
    name: 'editCkCounterRes',
    type: defaultType,
    url: url,
    requetType: 'PUT',
    remark: '编辑航班值机柜台资源信息',
    go: params => { return axios.put(url, params).then(res => res.data) }
  }
}

// 删除航班值机柜台资源信息
export const removeCkCounterRes = () => {
  let url = `${base}/allocation/checkincounter`
  return {
    name: 'removeCkCounterRes',
    type: defaultType,
    url: url,
    requetType: 'DELETE',
    remark: '删除航班值机柜台资源信息',
    go: params => { return axios.delete(url + '/' + params.id, params).then(res => res.data) }
  }
}

// 打开或关闭航班值机柜台资源信息
export const openOrCloseCkCounterRes = () => {
  let url = `${base}/allocation/checkincounter`
  return {
    name: '',
    type: defaultType,
    url: url,
    requetType: 'PUT',
    remark: '',
    go: params => { return axios.put(url, params).then(res => res.data) }
  }
}

// 值机柜台资源分配时间校验
export const checkincounterverify = () => {
  let url = `${base}/allocation/checkincounterverify`
  return {
    name: '',
    type: defaultType,
    url: url,
    requetType: 'PUT',
    remark: '',
    go: params => { return axios.put(url, params).then(res => res.data) }
  }
}
