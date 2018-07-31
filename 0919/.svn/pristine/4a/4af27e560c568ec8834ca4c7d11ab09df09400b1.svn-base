/*
 * @Author: ylj
 * @Date: 2018-02-24 16:27:06
 * @Last Modified by: ylj
 * @Last Modified time: 2018-03-05 14:28:32
 */

import {axios, base} from './../raiis-axios'

let defaultType = 'device repair record'

export const getDevRepairRecords = () => {
  let url = `${base}/deviceRepair`
  return {
    name: 'getDevRepairRecords',
    type: defaultType,
    url: url,
    requetType: 'GET',
    remark: '获取设备维修信息列表',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }
  }
}

export const addDeviceRepairRecord = () => {
  let url = `${base}/deviceRepair`
  return {
    name: 'addDeviceRepairRecord',
    type: defaultType,
    url: url,
    requetType: 'POST',
    remark: '新增设备维修记录',
    go: params => { return axios.post(url, params).then(res => res.data) }
  }
}

export const editDeviceRepairRecord = () => {
  let url = `${base}/deviceRepair`
  return {
    name: 'editDeviceRepairRecord',
    type: defaultType,
    url: url,
    requetType: 'PUT',
    remark: '修改设备维修记录',
    go: params => { return axios.put(url, params).then(res => res.data) }
  }
}

export const batchEditDeviceRepairRecords = () => {
  let url = `${base}/deviceRepair`
  return {
    name: 'batchEditDeviceRepairRecords',
    type: defaultType,
    url: url,
    requetType: 'PUT',
    remark: '批量登记设备维修记录',
    go: params => { return axios.put(url, params).then(res => res.data) }
  }
}

export const removeDeviceRepairRecord = () => {
  let url = `${base}/deviceRepair`
  return {
    name: 'removeDeviceRepairRecord',
    type: defaultType,
    url: url,
    requetType: 'DELETE',
    remark: '删除设备维修记录',
    go: params => {
      return axios.delete(url + '/' + params.id, params).then(res => res.data)
    }
  }
}
