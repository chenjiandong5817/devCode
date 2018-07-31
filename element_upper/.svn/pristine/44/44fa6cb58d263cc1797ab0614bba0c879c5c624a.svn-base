/*
 * @Author: ylj
 * @Date: 2017-06-08 09:22:17
 * @Last Modified by: ylj
 * @Last Modified time: 2017-09-27 19:19:01
 * @Description: 航班机位资源分配
 */
import {axios, base} from './../raiis-axios'

let defaultType = 'stand allocation'

// 获取航班机位资源信息
export const getStandList = () => {
  let url = `${base}/allocation/stand`
  return {
    name: 'getStandList',
    type: defaultType,
    url: url,
    requetType: 'GET',
    remark: '获取航班机位资源信息',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }
  }
}

// 修改航班机位资源信息
export const editStandRes = () => {
  let url = `${base}/allocation/stand/list`
  return {
    name: 'editStandRes',
    type: defaultType,
    url: url,
    requetType: 'PUT',
    remark: '修改航班机位资源信息',
    go: params => { return axios.put(url, params).then(res => res.data) }
  }
}

// 删除航班机位资源信息
export const removeStandRes = () => {
  let url = `${base}/allocation/stand`
  return {
    name: 'removeStandRes',
    type: defaultType,
    url: url,
    requetType: 'DELETE',
    remark: '删除航班机位资源信息',
    go: params => {
      return axios.delete(url + '/' + params.id, params).then(res => res.data)
    }
  }
}

// 开启或关闭航班机位资源
export const openOrCloseStandRes = () => {
  let url = `${base}/allocation/stand`
  return {
    name: 'openOrCloseStandRes',
    type: defaultType,
    url: url,
    requetType: 'PUT',
    remark: '开启或关闭航班机位资源',
    go: params => { return axios.put(url, params).then(res => res.data) }
  }
}
