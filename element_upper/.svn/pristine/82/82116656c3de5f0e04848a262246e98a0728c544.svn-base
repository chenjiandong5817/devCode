/*
* @Author: chenjiandong
* @Date:   last_modified_time
* @Last Modified by:   chenjiandong
* @Last Modified time: last_modified_time
*/
/*
 * @Author: llf
 * @Date: 2017-06-08 09:22:17
 * @Last Modified by: mikey.zhaopeng
 * @Last Modified time: 2017-08-11 17:04:56
 * @Description: 航空公司信息
 */
import {axios, base} from './raiis-axios'

let defaultType = 'airlines'

export const getAirlines = () => {
  let url = `${base}/airlines`
  return {
    name: 'getAirlines',
    type: defaultType,
    url: url,
    requetType: 'GET',
    remark: '获取航空公司信息',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }
  }
}

export const addAirlines = () => {
  let url = `${base}/airlines`
  return {
    name: 'addAirlines',
    type: defaultType,
    url: url,
    requetType: 'POST',
    remark: '新增航空公司信息',
    go: params => { return axios.post(url, params).then(res => res.data) }
  }
}

export const editAirlines = () => {
  let url = `${base}/airlines`
  return {
    name: 'editAirlines',
    type: defaultType,
    url: url,
    requetType: 'PUT',
    remark: '修改航空公司信息',
    go: params => { return axios.put(url, params).then(res => res.data) }
  }
}

export const removeAirlines = () => {
  let url = `${base}/airlines`
  return {
    name: 'removeAirlines',
    type: defaultType,
    url: url,
    requetType: 'DELETE',
    remark: '删除航空公司信息',
    go: params => {
      return axios.delete(url + '/' + params.id, params).then(res => res.data)
    }
  }
}
