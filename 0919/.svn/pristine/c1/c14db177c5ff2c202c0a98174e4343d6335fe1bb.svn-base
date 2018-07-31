/*
 * @Author: llf
 * @Date: 2017-06-08 09:22:17
 * @Last Modified by: mikey.zhaopeng
 * @Last Modified time: 2017-08-11 17:22:37
 * @Description: 进出方向
 */
import {axios, base} from './raiis-axios'

let defaultType = 'direction'

export const getDirection = () => {
  let url = `${base}/flightdirections`
  return {
    name: 'getDirection',
    type: defaultType,
    url: url,
    requetType: 'GET',
    remark: '获取进出方向列表',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }
  }
}

export const addDirection = () => {
  let url = `${base}/flightdirections`
  return {
    name: 'addDirection',
    type: defaultType,
    url: url,
    requetType: 'POST',
    remark: '新增进出方向',
    go: params => { return axios.post(url, params).then(res => res.data) }
  }
}

export const editDirection = () => {
  let url = `${base}/flightdirections`
  return {
    name: 'editDirection',
    type: defaultType,
    url: url,
    requetType: 'PUT',
    remark: '修改进出方向',
    go: params => { return axios.put(url, params).then(res => res.data) }
  }
}

export const removeDirection = () => {
  let url = `${base}/flightdirections`
  return {
    name: 'removeDirection',
    type: defaultType,
    url: url,
    requetType: 'DELETE',
    remark: '删除进出方向',
    go: params => {
      return axios.delete(url + '/' + params.id, params).then(res => res.data)
    }
  }
}
