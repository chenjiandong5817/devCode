/*
 * @Author: cdroid
 * @Date: 2017-09-14 09:21:10
 * @Last Modified by: cdroid
 * @Last Modified time: 2017-09-18 12:55:30
 * @Description:
 */

import {axios, base} from './../raiis-axios'
let defaultType = 'display manage'

// // mock data start
// import {axios} from './../raiis-axios'
// import Mock from 'mockjs'
// import MockTest from './../../common/js/mock-test'
// const base = '/mock'
// // 定义一份初始化的数据，用于增删查改
// Mock.Random.extend({
//   myIp: function (date) {
//     let ips = ['136.136.17.151', '136.136.17.97', '136.136.13.85']
//     return this.pick(ips)
//   }
// })
// let baseData = MockTest.generateData(105, () => {
//   let deviceId = MockTest.uuid()
//   return Mock.mock({
//     id: MockTest.uuid(),
//     deviceId: deviceId,
//     device: {
//       id: deviceId,
//       deviceIp: '@myIp'
//     },
//     temId: MockTest.uuid(),
//     timeSpan: '1',
//     begin: '0800',
//     end: '1700'
//   })
// })

// Mock.mock(/^\/mock\/deviceConfig/, 'get', options => {
//   // let params = MockTest.splitParams(options.url)
//   return MockTest.query(baseData, options.url)
// })

// // mock data end

export const getDeviceConfig = () => {
  let url = `${base}/deviceConfig`
  return {
    name: 'getDeviceConfig',
    type: defaultType,
    url: url,
    requetType: 'GET',
    remark: '获取设备绑定的模板',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }
  }
}

export const addDeviceConfig = () => {
  let url = `${base}/deviceConfig`
  return {
    name: 'addDeviceConfig',
    type: defaultType,
    url: url,
    requetType: 'POST',
    remark: '新增设备绑定的模板',
    go: params => { return axios.post(url, params).then(res => res.data) }
  }
}

export const editDeviceConfig = () => {
  let url = `${base}/deviceConfig`
  return {
    name: 'editDeviceConfig',
    type: defaultType,
    url: url,
    requetType: 'PUT',
    remark: '修改设备绑定的模板',
    go: params => { return axios.put(url, params).then(res => res.data) }
  }
}

export const removeDeviceConfig = () => {
  let url = `${base}/deviceConfig`
  return {
    name: 'removeDeviceConfig',
    type: defaultType,
    url: url,
    requetType: 'DELETE',
    remark: '删除设备绑定的模板',
    go: id => { return axios.delete(`${url}/${id}`).then(res => res.data) }
  }
}
