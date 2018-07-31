/*
 * @Author: ylj
 * @Date: 2017-10-22 20:13:57
 * @Last Modified by: ylj
 * @Last Modified time: 2017-11-30 10:50:26
 */
import {axios, base} from './../raiis-axios'

let defaultType = 'plan seasonschedule'

export const getSeasonSchedulels = () => {
  let url = `${base}/season`
  return {
    name: 'getSeasonSchedulels',
    type: defaultType,
    url: url,
    requetType: 'GET',
    remark: '获取航季计划信息',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }
  }
}

export const addSeasonSchedule = () => {
  let url = `${base}/season`
  return {
    name: 'addSeasonSchedule',
    type: defaultType,
    url: url,
    requetType: 'POST',
    remark: '新增航季计划信息',
    go: params => { return axios.post(url, params).then(res => res.data) }
  }
}

export const editSeasonSchedule = () => {
  let url = `${base}/season`
  return {
    name: 'editSeasonSchedule',
    type: defaultType,
    url: url,
    requetType: 'PUT',
    remark: '修改航季计划信息',
    go: params => { return axios.put(url, params).then(res => res.data) }
  }
}

export const removeSeasonSchedule = () => {
  let url = `${base}/season`
  return {
    name: 'removeSeasonSchedule',
    type: defaultType,
    url: url,
    requetType: 'DELETE',
    remark: '删除航季计划信息',
    go: params => {
      return axios.delete(url + '/' + params.flightId, params).then(res => res.data)
    }
  }
}

// 导入航季Excel信息
export const importSeaSchedule = () => {
  let url = `${base}/season/importExcel`
  return {
    name: 'importSeaSchedule',
    type: defaultType,
    url: url,
    requetType: 'POST',
    remark: '导入航季Excel信息',
    go: params => { return axios.post(url, params).then(res => res.data) }
  }
}

// 据航季生成次日航班计划
export const genMorrowFlight = () => {
  let url = `${base}/season/manualSchedule`
  return {
    name: 'genMorrowFlight',
    type: defaultType,
    url: url,
    requetType: 'PUT',
    remark: '据航季生成次日航班计划',
    go: params => { return axios.put(url, params).then(res => res.data) }
  }
}
