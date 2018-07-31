/*
 * @Author: ylj
 * @Date: 2017-06-08 09:22:17
 * @Last Modified by: ylj
 * @Last Modified time: 2017-10-25 09:44:07
 * @Description: 航班行李转盘资源分配
 */
import {axios, base} from './../raiis-axios'

let defaultType = 'carousel allocation'

// 获取航班行李转盘资源信息
export const getCarouselList = () => {
  let url = `${base}/allocation/carousel`
  return {
    name: 'getCarouselList',
    type: defaultType,
    url: url,
    requetType: 'GET',
    remark: '获取航班行李转盘资源信息',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }
  }
}

// 修改航班行李转盘资源信息
export const editCarouselRes = () => {
  let url = `${base}/allocation/carousel/list`
  return {
    name: 'editCarouselRes',
    type: defaultType,
    url: url,
    requetType: 'PUT',
    remark: '修改行李转盘资源信息',
    go: params => { return axios.put(url, params).then(res => res.data) }
  }
}

// 删除航班行李转盘资源信息
export const removeCarouselRes = () => {
  let url = `${base}/allocation/carousel`
  return {
    name: 'removeCarouselRes',
    type: defaultType,
    url: url,
    requetType: 'DELETE',
    remark: '删除行李转盘资源信息',
    go: params => {
      return axios.delete(url + '/' + params.id, params).then(res => res.data)
    }
  }
}

// 开启或关闭航班行李转盘资源
export const openOrCloseCarsRes = () => {
  let url = `${base}/allocation/carousel`
  return {
    name: 'openOrCloseCarsRes',
    type: defaultType,
    url: url,
    requetType: 'PUT',
    remark: '开启或关闭行李转盘资源',
    go: params => { return axios.put(url, params).then(res => res.data) }
  }
}
