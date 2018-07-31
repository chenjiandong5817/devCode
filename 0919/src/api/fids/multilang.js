/*
 * @Author: chenyang
 * @Date: 2017-08-22 10:47:42
 * @Last Modified by: chenyang
 * @Last Modified time: 2017-08-22 15:13:18
 */
import {axios, base} from './../raiis-axios'

let defaultType = 'multilang'

export const getMultiLang = () => {
  let url = `${base}/multiLang`
  return {
    name: 'getMultiLang',
    type: defaultType,
    url: url,
    requetType: 'GET',
    remark: '获取多语言列表',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }
  }
}

export const addMultiLang = () => {
  let url = `${base}/multiLang`
  return {
    name: 'addMultiLang',
    type: defaultType,
    url: url,
    requetType: 'POST',
    remark: '新增多语言信息',
    go: params => { return axios.post(url, params).then(res => res.data) }
  }
}

export const editMultiLang = () => {
  let url = `${base}/multiLang`
  return {
    name: 'editMultiLang',
    type: defaultType,
    url: url,
    requetType: 'PUT',
    remark: '修改多语言信息',
    go: params => { return axios.put(url, params).then(res => res.data) }
  }
}

export const removeMultiLang = () => {
  let url = `${base}/multiLang`
  return {
    name: 'removeMultiLang',
    type: defaultType,
    url: url,
    requetType: 'DELETE',
    remark: '删除多语言信息',
    go: params => { return axios.delete(url + '/' + params.id, params).then(res => res.data) }
  }
}
