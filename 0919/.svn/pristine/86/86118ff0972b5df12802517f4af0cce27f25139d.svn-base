/*
 * @Author: ylj
 * @Date: 2017-06-08 09:22:17
 * @Last Modified by: ylj
 * @Last Modified time: 2017-09-27 19:19:21
 * @Description: 航班登机口资源分配
 */
import {axios, base} from './../raiis-axios'

let defaultType = 'gate allocation'

// 获取航班登机口资源信息
export const getGateList = () => {
  let url = `${base}/allocation/gate`
  return {
    name: 'getGateList',
    type: defaultType,
    url: url,
    requetType: 'GET',
    remark: '获取航班登机口资源信息',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }
  }
}

// 修改航班登机口资源信息
export const editGateRes = () => {
  let url = `${base}/allocation/gate/list`
  return {
    name: 'editGateRes',
    type: defaultType,
    url: url,
    requetType: 'PUT',
    remark: '修改航班登机口资源信息',
    go: params => { return axios.put(url, params).then(res => res.data) }
  }
}

// 删除航班登机口资源信息
export const removeGateRes = () => {
  let url = `${base}/allocation/gate`
  return {
    name: 'removeGateRes',
    type: defaultType,
    url: url,
    requetType: 'DELETE',
    remark: '删除航班登机口资源信息',
    go: params => {
      return axios.delete(url + '/' + params.id, params).then(res => res.data)
    }
  }
}

// 开启或关闭航班登机口资源
export const openOrCloseGateRes = () => {
  let url = `${base}/allocation/gate`
  return {
    name: 'openOrCloseGateRes',
    type: defaultType,
    url: url,
    requetType: 'PUT',
    remark: '开启或关闭航班登机口资源',
    go: params => { return axios.put(url, params).then(res => res.data) }
  }
}
