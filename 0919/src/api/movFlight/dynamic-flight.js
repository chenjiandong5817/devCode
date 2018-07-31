/*
 * @Author: llf
 * @Date: 2017-06-08 09:22:17
 * @Last Modified by: ylj
 * @Last Modified time: 2017-11-23 17:18:01
 * @Description: 航班动态
 */
import {axios, base} from './../raiis-axios'

let defaultType = 'dynamic flight'

export const getDynamicFlight = () => {
  let url = `${base}/flights`
  return {
    name: 'getDynamicFlight',
    type: defaultType,
    url: url,
    requetType: 'GET',
    remark: '获取航班动态列表',
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

export const getReplaceDataLs = () => {
  let url = `${base}/candidate/all`
  return {
    name: 'getReplaceDataLs',
    type: defaultType,
    url: url,
    requetType: 'GET',
    remark: '获取替换文本',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }
  }
}

// 操作航班数据
export const operateFlightData = (optCode, optName) => {
  let url = `${base}/flights/simple`
  return {
    name: optCode,
    type: defaultType,
    url: url,
    requetType: 'PUT',
    remark: optName,
    go: params => { return axios.put(url, params).then(res => res.data) }
  }
}

// 进港备降
export const operateFlightArrAlt = () => {
  let url = `${base}/flights/arrivealternate`
  return {
    name: 'arrivealternate',
    type: defaultType,
    url: url,
    requetType: 'PUT',
    remark: '进港备降',
    go: params => { return axios.put(url, params).then(res => res.data) }
  }
}

// 新增备降
export const operateFlightAddAlt = () => {
  let url = `${base}/flights/newalternate`
  return {
    name: 'newalternate',
    type: defaultType,
    url: url,
    requetType: 'PUT',
    remark: '新增备降',
    go: params => { return axios.put(url, params).then(res => res.data) }
  }
}

// 出港返航
export const operateFlightDepRtn = () => {
  let url = `${base}/flights/departreversal`
  return {
    name: 'departreversal',
    type: defaultType,
    url: url,
    requetType: 'PUT',
    remark: '出港返航',
    go: params => { return axios.put(url, params).then(res => res.data) }
  }
}

// 获取异常原因
export const getIrregularCode = () => {
  let url = `${base}/irregularcodes`
  return {
    name: 'irregularcodes',
    type: defaultType,
    url: url,
    requetType: 'GET',
    remark: '获取异常原因基础信息',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }
  }
}

export const searchReg = (partval) => {
  let url = `${base}/candidate/registration/` + partval
  return {
    name: 'searchReg',
    type: defaultType,
    url: url,
    requetType: 'GET',
    remark: '远程搜索机号信息',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }
  }
}

export const getFlightSchedulels = () => {
  let url = `${base}/flights/all`
  return {
    name: 'getFlightSchedulels',
    type: defaultType,
    url: url,
    requetType: 'GET',
    remark: '获取航班列表信息',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }
  }
}

export const flightScheduleDownload = () => {
  let url = `${base}/flights/download`
  return {
    name: 'flightScheduleDownload',
    type: defaultType,
    url: url,
    requetType: 'GET',
    remark: '航班动态信息导出',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }
  }
}
