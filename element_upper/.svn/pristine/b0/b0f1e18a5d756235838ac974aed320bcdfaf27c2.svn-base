/*
 * @Author: llf
 * @Date: 2017-06-08 09:22:17
 * @Last Modified by: mikey.zhaopeng
 * @Last Modified time: 2017-08-11 17:23:00
 * @Description: 总代
 */
import {axios, base} from './raiis-axios'

let defaultType = 'generalagent'

export const getGeneralagent = () => {
  let url = `${base}/generalagents`
  return {
    name: 'getGeneralagent',
    type: defaultType,
    url: url,
    requetType: 'GET',
    remark: '获取航站楼',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }
  }
}

export const addGeneralagent = () => {
  let url = `${base}/generalagents`
  return {
    name: 'addGeneralagent',
    type: defaultType,
    url: url,
    requetType: 'POST',
    remark: '新增航站楼',
    go: params => { return axios.post(url, params).then(res => res.data) }
  }
}

export const editGeneralagent = () => {
  let url = `${base}/generalagents`
  return {
    name: 'editGeneralagent',
    type: defaultType,
    url: url,
    requetType: 'PUT',
    remark: '修改航站楼',
    go: params => { return axios.put(url, params).then(res => res.data) }
  }
}

export const removeTerminal = () => {
  let url = `${base}/generalagents`
  return {
    name: 'removeGeneralagent',
    type: defaultType,
    url: url,
    requetType: 'DELETE',
    remark: '删除航站楼',
    go: params => {
      return axios.delete(url + '/' + params.id, params).then(res => res.data)
    }
  }
}
