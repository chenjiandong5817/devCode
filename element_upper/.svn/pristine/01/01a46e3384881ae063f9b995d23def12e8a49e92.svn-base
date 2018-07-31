/*
* @Author: chenjiandong
* @Date:   last_modified_time
* @Last Modified by:   chenjiandong
* @Last Modified time: last_modified_time
*/
import {axios, base} from './../raiis-axios'

let defaultType = 'flighttasks'

export const getFlightTaskListPage = () => {
  let url = `${base}/flighttasks`
  return {
    name: 'getFlightTaskListPage',
    type: defaultType,
    url: url,
    requetType: 'GET',
    remark: '获取航班任务代码信息',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }
  }
}

export const addFlightTask = () => {
  let url = `${base}/flighttasks`
  return {
    name: 'addFlightTask',
    type: defaultType,
    url: url,
    requetType: 'POST',
    remark: '新增航班任务代码信息',
    go: params => { return axios.post(url, params).then(res => res.data) }
  }
}

export const editFlightTask = () => {
  let url = `${base}/flighttasks`
  return {
    name: 'editFlightTask',
    type: defaultType,
    url: url,
    requetType: 'PUT',
    remark: '修改航班任务代码信息',
    go: params => { return axios.put(url, params).then(res => res.data) }
  }
}

export const removeFlightTask = () => {
  let url = `${base}/flighttasks`
  return {
    name: 'removeFlightTask',
    type: defaultType,
    url: url,
    requetType: 'DELETE',
    remark: '删除航班任务代码信息',
    go: params => {
      return axios.delete(url + '/' + params.id, params).then(res => res.data)
    }
  }
}
