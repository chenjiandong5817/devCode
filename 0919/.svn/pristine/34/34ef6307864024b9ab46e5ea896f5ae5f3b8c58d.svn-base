/*
 * @Author: ylj
 * @Date: 2017-10-22 20:13:57
 * @Last Modified by: ylj
 * @Last Modified time: 2017-11-29 15:43:28
 */
import {axios, base} from './../raiis-axios'

let defaultType = 'mov flightload'

export const getFlightLoadls = () => {
  let url = `${base}/flightload `
  return {
    name: 'getFlightLoadls',
    type: defaultType,
    url: url,
    requetType: 'GET',
    remark: '获取航班客货行邮信息',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }
  }
}

export const getFloadlsByFlight = () => {
  let url = `${base}/flightload/byflight`
  return {
    name: 'getFloadlsByFlight',
    type: defaultType,
    url: url,
    requetType: 'GET',
    remark: '通过航班获取客货行邮信息',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }
  }
}

export const addFlightLoad = () => {
  let url = `${base}/flightload `
  return {
    name: 'addFlightLoad',
    type: defaultType,
    url: url,
    requetType: 'POST',
    remark: '新增航班客货行邮信息',
    go: params => { return axios.post(url, params).then(res => res.data) }
  }
}

export const editFlightLoad = () => {
  let url = `${base}/flightload `
  return {
    name: 'editFlightLoad',
    type: defaultType,
    url: url,
    requetType: 'PUT',
    remark: '修改航班客货行邮信息',
    go: params => { return axios.put(url, params).then(res => res.data) }
  }
}

export const removeFlightLoad = () => {
  let url = `${base}/flightload `
  return {
    name: 'removeFlightLoad',
    type: defaultType,
    url: url,
    requetType: 'DELETE',
    remark: '删除航班客货行邮信息',
    go: params => {
      return axios.delete(url + '/' + params.id, params).then(res => res.data)
    }
  }
}

export const expFlightLoad = () => {
  let url = `${base}/flightload/byflight/download`
  return {
    name: 'expFlightLoad',
    type: defaultType,
    url: url,
    requetType: 'GET',
    remark: '航班货邮信息EXCEL导出',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }
  }
}
