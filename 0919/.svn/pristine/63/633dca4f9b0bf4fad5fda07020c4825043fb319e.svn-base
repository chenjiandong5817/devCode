/*
 * @Author: ylj
 * @Date: 2017-10-17 15:51:42
 * @Last Modified by: ylj
 * @Last Modified time: 2017-12-01 15:20:07
 */
import {axios, base} from './../raiis-axios'

let defaultType = 'plan flightschedule'

export const getPlanFlightls = () => {
  let url = `${base}/plan`
  return {
    name: 'getPlanFlightls',
    type: defaultType,
    url: url,
    requetType: 'GET',
    remark: '获取计划航班列表',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }
  }
}

export const addPlanFlight = () => {
  let url = `${base}/plan`
  return {
    name: 'addPlanFlight',
    type: defaultType,
    url: url,
    requetType: 'POST',
    remark: '新增计划航班信息',
    go: params => { return axios.post(url, params).then(res => res.data) }
  }
}

export const editPlanFlight = () => {
  let url = `${base}/plan`
  return {
    name: 'editPlanFlight',
    type: defaultType,
    url: url,
    requetType: 'PUT',
    remark: '修改计划航班信息',
    go: params => { return axios.put(url, params).then(res => res.data) }
  }
}

export const removePlanFlight = () => {
  let url = `${base}/plan`
  return {
    name: 'removePlanFlight',
    type: defaultType,
    url: url,
    requetType: 'DELETE',
    remark: '删除计划航班信息',
    go: params => {
      return axios.delete(url + '/' + params.flightId, params).then(res => res.data)
    }
  }
}

export const expPlanFlight = () => {
  let url = `${base}/plan/download`
  return {
    name: 'expPlanFlight',
    type: defaultType,
    url: url,
    requetType: 'GET',
    remark: '航班计划信息EXCEL导出',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }
  }
}

export const importPlanFlight = () => {
  let url = `${base}/plan/importExcel`
  return {
    name: 'importPlanFlight',
    type: defaultType,
    url: url,
    requetType: 'POST',
    remark: '航班次日计划信息EXCEL导入',
    go: params => { return axios.post(url, params).then(res => res.data) }
  }
}
