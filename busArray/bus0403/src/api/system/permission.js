/*
 * @Author: cdroid
 * @Date: 2017-06-29 10:39:55
 * @Last Modified by:   cdroid
 * @Last Modified time: 2017-06-29 10:39:55
 * @Description:
 */

import { axios, base } from './../raiis-axios'

// ************************************** Permission **************************************
export const getPermissionListPage = () => {
  let url = `${base}/permission`
  return {
    name: 'getPermissionListPage',
    type: 'permission',
    url: url,
    requetType: 'GET',
    remark: '获取权限列表',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }
  }
}

export const removePermission = () => {
  let url = `${base}/permission/`
  return {
    name: 'removePermission',
    type: 'permission',
    url: url,
    requetType: 'DELETE',
    remark: '添加权限',
    go: id => { return axios.delete(url + id).then(res => res.data) }
  }
}

export const editPermission = () => {
  let url = `${base}/permission`
  return {
    name: 'editPermission',
    type: 'permission',
    url: url,
    requetType: 'PUT',
    remark: '编辑权限',
    go: params => { return axios.put(url, params).then(res => res.data) }
  }
}

export const addPermission = () => {
  let url = `${base}/permission`
  return {
    name: 'addPermission',
    type: 'permission',
    url: url,
    requetType: 'POST',
    remark: '添加权限',
    go: params => { return axios.post(url, params).then(res => res.data) }
  }
}
