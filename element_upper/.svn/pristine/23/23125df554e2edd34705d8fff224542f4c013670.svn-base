/*
 * @Author: llf
 * @Date: 2017-06-08 09:22:17
 * @Last Modified by: chenyang
 * @Last Modified time: 2017-08-22 11:09:34
 * @Description: 设备管理
 */
import {axios, base} from './../raiis-axios'

let defaultType = 'device manage'

export const getDeviceInfoAll = () => {
  let url = `${base}/devices/all`
  return {
    name: 'addDeviceInfo',
    type: defaultType,
    url: url,
    requetType: 'GET',
    remark: '获取设备所有信息',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }
  }
}

export const getDeviceInfo = () => {
  let url = `${base}/devices`
  return {
    name: 'getDeviceInfo',
    type: defaultType,
    url: url,
    requetType: 'GET',
    remark: '获取设备信息列表',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }
  }
}

export const addDeviceInfo = () => {
  let url = `${base}/devices`
  return {
    name: 'addDeviceInfo',
    type: defaultType,
    url: url,
    requetType: 'POST',
    remark: '新增设备信息',
    go: params => { return axios.post(url, params).then(res => res.data) }
  }
}

export const editDeviceInfo = () => {
  let url = `${base}/devices`
  return {
    name: 'editDeviceInfo',
    type: defaultType,
    url: url,
    requetType: 'PUT',
    remark: '修改设备信息',
    go: params => { return axios.put(url, params).then(res => res.data) }
  }
}

export const removeDeviceInfo = () => {
  let url = `${base}/devices`
  return {
    name: 'removeDeviceInfo',
    type: defaultType,
    url: url,
    requetType: 'DELETE',
    remark: '删除设备信息',
    go: params => {
      return axios.delete(url + '/' + params.id, params).then(res => res.data)
    }
  }
}
