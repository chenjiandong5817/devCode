/*
* @Author: chenjiandong
* @Date:   last_modified_time
* @Last Modified by:   chenjiandong
* @Last Modified time: last_modified_time
*/
import {axios, base} from './raiis-axios'

let defaultType = 'terminal'

export const getTerminal = () => {
  let url = `${base}/terminals`
  return {
    name: 'getTerminal',
    type: defaultType,
    url: url,
    requetType: 'GET',
    remark: '获取航站楼',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }
  }
}

export const addTerminal = () => {
  let url = `${base}/terminals`
  return {
    name: 'addTerminal',
    type: defaultType,
    url: url,
    requetType: 'POST',
    remark: '新增航站楼',
    go: params => { return axios.post(url, params).then(res => res.data) }
  }
}

export const editTerminal = () => {
  let url = `${base}/terminals`
  return {
    name: 'editTerminal',
    type: defaultType,
    url: url,
    requetType: 'PUT',
    remark: '修改航站楼',
    go: params => { return axios.put(url, params).then(res => res.data) }
  }
}

export const removeTerminal = () => {
  let url = `${base}/terminals`
  return {
    name: 'removeTerminal',
    type: defaultType,
    url: url,
    requetType: 'DELETE',
    remark: '删除航站楼',
    go: params => {
      return axios.delete(url + '/' + params.id, params).then(res => res.data)
    }
  }
}
