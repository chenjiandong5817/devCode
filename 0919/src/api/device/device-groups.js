/*
* @Author: cdroid
* @Date:   2018-01-04 11:27:43
 * @Last Modified by: chenyang
 * @Last Modified time: 2018-01-08 12:33:33
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
