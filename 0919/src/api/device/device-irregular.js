/*
 * @Author: ylj
 * @Date: 2018-02-24 17:06:13
 * @Last Modified by: ylj
 * @Last Modified time: 2018-03-07 16:22:08
 */

import {axios, base} from './../raiis-axios'

let defaultType = 'device irregular'

export const getDeviceIrrInfos = () => {
  let url = `${base}/deviceIrregular`
  return {
    name: 'getDeviceIrrInfos',
    type: defaultType,
    url: url,
    requetType: 'GET',
    remark: '获取设备异常信息列表',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }
  }
}

export const addDeviceIrregularInfo = () => {
  let url = `${base}/deviceIrregular`
  return {
    name: 'addDeviceIrregularInfo',
    type: defaultType,
    url: url,
    requetType: 'POST',
    remark: '新增设备异常信息',
    go: params => { return axios.post(url, params).then(res => res.data) }
  }
}

export const editDeviceIrregularInfo = () => {
  let url = `${base}/deviceIrregular`
  return {
    name: 'editDeviceIrregularInfo',
    type: defaultType,
    url: url,
    requetType: 'PUT',
    remark: '修改设备异常信息',
    go: params => { return axios.put(url, params).then(res => res.data) }
  }
}

export const removeDeviceIrregularInfo = () => {
  let url = `${base}/deviceIrregular`
  return {
    name: 'removeDeviceIrregularInfo',
    type: defaultType,
    url: url,
    requetType: 'DELETE',
    remark: '删除设备异常信息',
    go: params => {
      return axios.delete(url + '/' + params.id, params).then(res => res.data)
    }
  }
}

// export const openSmartBI = () => {
//   let url = `http://10.1.17.3/smartbi/vision/openresource.jsp?resid=I8a819183015dbda3bda352e50161fea5346e3b25&user=zenglw&password=123456`
//   return {
//     name: 'openSmartBI',
//     type: defaultType,
//     url: url,
//     requetType: 'POST',
//     remark: '打开smartbi',
//     go: params => { return axios.post(url, params).then(res => res.data) }
//   }
// }
