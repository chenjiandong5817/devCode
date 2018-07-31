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
 * @Last Modified time: 2017-08-11 17:08:51
 * @Description: 机型信息
 */
import {axios, base} from './raiis-axios'

let defaultType = 'aircrafttypes'

export const getAircraftType = () => {
  let url = `${base}/aircrafttypes`
  return {
    name: 'getAircraftType',
    type: defaultType,
    url: url,
    requetType: 'GET',
    remark: '获取机型信息',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }
  }
}

export const addAircraftType = () => {
  let url = `${base}/aircrafttypes`
  return {
    name: 'addAircraftType',
    type: defaultType,
    url: url,
    requetType: 'POST',
    remark: '新增机型信息',
    go: params => { return axios.post(url, params).then(res => res.data) }
  }
}

export const editAircraftType = () => {
  let url = `${base}/aircrafttypes`
  return {
    name: 'editAircraftType',
    type: defaultType,
    url: url,
    requetType: 'PUT',
    remark: '修改机型信息',
    go: params => { return axios.put(url, params).then(res => res.data) }
  }
}

export const removeAircraftType = () => {
  let url = `${base}/aircrafttypes`
  return {
    name: 'removeAircraftType',
    type: defaultType,
    url: url,
    requetType: 'DELETE',
    remark: '删除机型信息',
    go: params => {
      return axios.delete(url + '/' + params.id, params).then(res => res.data)
    }
  }
}
