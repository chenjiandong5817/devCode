/*
 * @Author: chenyang
 * @Date: 2017-09-27 16:05:15
 * @Last Modified by: chenyang
 * @Last Modified time: 2017-09-28 17:04:17
 */
import {axios, base} from './../raiis-axios'

let defaultType = 'deviceGroups'

export const getDeviceGroups = () => {
  let url = `${base}/devicegroups`
  return {
    name: 'getDeviceGroups',
    type: defaultType,
    url: url,
    requetType: 'GET',
    remark: '获取设备Group信息',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }
  }
}

export const addDeviceGroups = () => {
  let url = `${base}/devicegroups`
  return {
    name: 'addDeviceGroups',
    type: defaultType,
    url: url,
    requetType: 'POST',
    remark: '新增设备Group信息',
    go: params => { return axios.post(url, params).then(res => res.data) }
  }
}
