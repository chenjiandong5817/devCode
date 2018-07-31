/*
 * @Author: cdroid
 * @Date: 2017-06-29 10:40:03
 * @Last Modified by:   cdroid
 * @Last Modified time: 2017-06-29 10:40:03
 * @Description:
 */

import { axios, base } from './../raiis-axios'

// ************************************** RoleMenu **************************************
export const queryUserMenuList = () => {
  let url = `${base}/roleMenu/queryMenus`
  return {
    name: 'queryUserMenuList',
    type: 'user',
    url: url,
    requetType: 'GET',
    remark: '查询当前用户的菜单列表',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }
  }
}

export const getRoleMenuList = () => {
  let url = `${base}/roleMenu/query`
  return {
    name: 'getRoleMenuList',
    type: 'role',
    url: url,
    requetType: 'GET',
    remark: '获取角色菜单列表',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }
  }
}

export const updateRoleMenu = () => {
  let url = `${base}/roleMenu/update`
  return {
    name: 'updateRoleMenu',
    type: 'role',
    url: url,
    requetType: 'POST',
    remark: '更新角色菜单',
    go: params => { return axios.put(url, params).then(res => res.data) }
  }
}
// ************************************** Role **************************************
export const getRoleListPage = () => {
  let url = `${base}/role`
  return {
    name: 'getRoleListPage',
    type: 'role',
    url: url,
    requetType: 'GET',
    remark: '获取角色列表',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }
  }
}

export const removeRole = () => {
  let url = `${base}/role/`
  return {
    name: 'removeRole',
    type: 'role',
    url: url,
    requetType: 'DELETE',
    remark: '删除角色',
    go: id => { return axios.delete(url + id).then(res => res.data) }
  }
}

export const editRole = () => {
  let url = `${base}/role`
  return {
    name: 'editRole',
    type: 'role',
    url: url,
    requetType: 'PUT',
    remark: '编辑角色',
    go: params => { return axios.put(url, params).then(res => res.data) }
  }
}

export const editRolePermission = () => {
  let url = `${base}/role/update/permission`
  return {
    name: 'editRolePermission',
    type: 'role',
    url: url,
    requetType: 'PUT',
    remark: '编辑角色权限',
    go: params => { return axios.put(url, params).then(res => res.data) }
  }
}

export const addRole = () => {
  let url = `${base}/role`
  return {
    name: 'addRole',
    type: 'role',
    url: url,
    requetType: 'POST',
    remark: '添加角色',
    go: params => { return axios.post(url, params).then(res => res.data) }
  }
}

export const fetchRolePerList = () => {
  let url = `${base}/role/fetch/permission`
  return {
    name: 'fetchRolePerList',
    type: 'role',
    url: url,
    requetType: 'GET',
    remark: '获取角色权限列表',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }
  }
}
