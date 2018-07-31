/*
 * @Author: llf
 * @Date: 2017-06-08 09:22:17
 * @Last Modified by: mikey.zhaopeng
 * @Last Modified time: 2017-08-11 17:26:17
 * @Description: 机号信息
 */
import {axios, base} from './raiis-axios'

let defaultType = 'registration manage'

export const getRegistration = () => {
  let url = `${base}/aircraftregistrations`
  return {
    name: 'getRegistration',
    type: defaultType,
    url: url,
    requetType: 'GET',
    remark: '获取机号信息列表',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }
  }
}

export const addRegistration = () => {
  let url = `${base}/aircraftregistrations`
  return {
    name: 'addRegistration',
    type: defaultType,
    url: url,
    requetType: 'POST',
    remark: '新增机号信息',
    go: params => { return axios.post(url, params).then(res => res.data) }
  }
}

export const editRegistration = () => {
  let url = `${base}/aircraftregistrations`
  return {
    name: 'editRegistration',
    type: defaultType,
    url: url,
    requetType: 'PUT',
    remark: '修改机号信息',
    go: params => { return axios.put(url, params).then(res => res.data) }
  }
}

export const removeRegistration = () => {
  let url = `${base}/aircraftregistrations`
  return {
    name: 'removeRegistration',
    type: defaultType,
    url: url,
    requetType: 'DELETE',
    remark: '删除机号信息',
    go: params => {
      return axios.delete(url + '/' + params.id, params).then(res => res.data)
    }
  }
}
