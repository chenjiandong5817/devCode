/*
 * @Author: llf
 * @Date: 2017-06-14 15:33:17
 * @Last Modified by: mikey.zhaopeng
 * @Last Modified time: 2017-06-14 17:02:23
 * @Description: 数据字典
 */
import {axios, base} from './raiis-axios'

let defaultType = 'enuminfo'

export const getEnumInfo = () => {
  let url = `${base}/enuminfos`
  return {
    name: 'getEnumInfo',
    type: defaultType,
    url: url,
    requetType: 'GET',
    remark: '获取数据字典',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }
  }
}

export const addEnumInfo = () => {
  let url = `${base}/enuminfos`
  return {
    name: 'addEnumInfo',
    type: defaultType,
    url: url,
    requetType: 'POST',
    remark: '新增数据字典',
    go: params => { return axios.post(url, params).then(res => res.data) }
  }
}

export const editEnumInfo = () => {
  let url = `${base}/enuminfos`
  return {
    name: 'editEnumInfo',
    type: defaultType,
    url: url,
    requetType: 'PUT',
    remark: '修改设备信息',
    go: params => { return axios.put(url, params).then(res => res.data) }
  }
}

export const removeEnumInfo = () => {
  let url = `${base}/enuminfos`
  return {
    name: 'removeEnumInfo',
    type: defaultType,
    url: url,
    requetType: 'DELETE',
    remark: '删除设备信息',
    go: params => {
      return axios.delete(url + '/' + params.id).then(res => res.data)
    }
  }
}
