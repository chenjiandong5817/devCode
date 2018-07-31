/*
 * @Author: cdroid
 * @Date: 2017-06-29 10:40:09
 * @Last Modified by:   cdroid
 * @Last Modified time: 2017-06-29 10:40:09
 * @Description:
 */

import { axios, base } from './../raiis-axios'

// ************************************** User **************************************
// export const getUserListPage = params => { return axios.get(`${base}/user/query`, { params: params }) }
export const getUserListPage = () => {
  let url = `${base}/user`
  return {
    name: 'getUserListPage',
    type: 'user',
    url: url,
    requetType: 'GET',
    remark: '获取用户列表',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }
  }
}

// export const removeUser = params => { return axios.get(`${base}/user/delete`, { params: params }) }
export const removeUser = () => {
  let url = `${base}/user/`
  return {
    name: 'removeUser',
    type: 'user',
    url: url,
    requetType: 'DELETE',
    remark: '删除用户',
    go: id => { return axios.delete(url + id).then(res => res.data) }
  }
}

// export const editUser = params => { return axios.get(`${base}/user/update`, { params: params }) }
export const editUser = () => {
  let url = `${base}/user`
  return {
    name: 'editUser',
    type: 'user',
    url: url,
    requetType: 'PUT',
    remark: '更新用户',
    go: params => { return axios.put(url, params).then(res => res.data) }
  }
}

// export const addUser = params => { return axios.get(`${base}/user/add`, { params: params }) }
export const addUser = () => {
  let url = `${base}/user`
  return {
    name: 'addUser',
    type: 'user',
    url: url,
    requetType: 'POST',
    remark: '添加用户',
    go: params => { return axios.post(url, params).then(res => res.data) }
  }
}

// export const batchLockedUser = params => { return axios.get(`${base}/user/batchlocked`, { params: params }) }
export const batchLockedUser = () => {
  let url = `${base}/user/batchlocked`
  return {
    name: 'batchLockedUser',
    type: 'user',
    url: url,
    requetType: 'PUT',
    remark: '批量锁定用户',
    go: params => { return axios.put(url, params).then(res => res.data) }
  }
}

// export const batchUnlockedUser = params => { return axios.get(`${base}/user/batchunlocked`, { params: params }) }
export const batchUnlockedUser = () => {
  let url = `${base}/user/batchunlocked`
  return {
    name: 'batchUnlockedUser',
    type: 'user',
    url: url,
    requetType: 'PUT',
    remark: '批量激活用户',
    go: params => { return axios.put(url, params).then(res => res.data) }
  }
}

// export const fetchUserRoleList = params => { return axios.get(`${base}/admin/authority/user/fetch/role`, { params: params }) }
export const fetchUserRoleList = () => {
  let url = `${base}/user/fetch/role`
  return {
    name: 'fetchUserRoleList',
    type: 'user',
    url: url,
    requetType: 'GET',
    remark: '获取用户角色列表',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }
  }
}

// export const fetchUserPerList = params => { return axios.get(`${base}/admin/authority/user/fetch/permission`, { params: params }) }
export const fetchUserPerList = () => {
  let url = `${base}/user/fetch/permission`
  return {
    name: 'fetchUserPerList',
    type: 'user',
    url: url,
    requetType: 'GET',
    remark: '获取用户特许权限列表',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }
  }
}

// export const updateUserRole = params => { return axios.post(`${base}/admin/authority/user/update/role`, params) }
export const updateUserRole = () => {
  let url = `${base}/user/update/role`
  return {
    name: 'updateUserRole',
    type: 'user',
    url: url,
    requetType: 'PUT',
    remark: '更新用户角色',
    go: params => { return axios.put(url, params).then(res => res.data) }
  }
}

// export const updateUserPer = params => { return axios.post(`${base}/admin/authority/user/update/permission`, params) }
export const updateUserPer = () => {
  let url = `${base}/user/update/permission`
  return {
    name: 'updateUserPer',
    type: 'user',
    url: url,
    requetType: 'PUT',
    remark: '更新用户特许权限',
    go: params => { return axios.put(url, params).then(res => res.data) }
  }
}

// ************************************** UserProfile **************************************
export const currentUser = () => {
  let url = `${base}/user/profile/me`
  return {
    name: 'currentUser',
    type: 'user',
    url: url,
    requetType: 'GET',
    remark: '获取当前用户',
    go: params => { return axios.get(url, params).then(res => res.data) }
  }
}

export const getUserProfile = () => {
  let url = `${base}/user/profile`
  return {
    name: 'getUserProfile',
    type: 'user',
    url: url,
    requetType: 'GET',
    remark: '获取用户信息',
    go: params => { return axios.get(url, params).then(res => res.data) }
  }
}

export const updateUserProfile = () => {
  let url = `${base}/user/profile`
  return {
    name: 'updateUserProfile',
    type: 'user',
    url: url,
    requetType: 'PUT',
    remark: '更新用户信息',
    go: params => { return axios.put(url, params).then(res => res.data) }
  }
}

export const updateUserAvatar = () => {
  let url = `${base}/user/profile/avatar`
  return {
    name: 'updateUserAvatar',
    type: 'user',
    url: url,
    requetType: 'POST',
    remark: '更新用户头像',
    go: params => { return axios.post(url, params).then(res => res.data) }
  }
}

export const getUserAvatar = () => {
  let url = `${base}/user/profile/avatar`
  return {
    name: 'getUserAvatar',
    type: 'user',
    url: url,
    requetType: 'GET',
    remark: '获取用户头像',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }
  }
}

export const activeMail = () => {
  let url = `${base}/user/profile//active/mail`
  return {
    name: 'activeMail',
    type: 'user',
    url: url,
    requetType: 'POST',
    remark: '验证邮箱',
    go: params => { return axios.post(url, params).then(res => res.data) }
  }
}

export const getDefaultAirport = () => {
  let url = `${base}/user`
  return {
    name: 'getDefaultAirport',
    type: 'user',
    url: url,
    requetType: 'GET',
    remark: '获取默认机场',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }

  }
}

export const setUserAirportSubscribe = () => {
  let url = `${base}/userairports`
  return {
    name: 'setUserAirportSubscribe',
    type: 'userairports',
    url: url,
    requetType: 'POST',
    remark: '设置用户的机场订阅',
    go: params => { return axios.post(url, params).then(res => res.data) }
  }
}

export const getUserDefaultAirport = () => {
  let url = `${base}/userairports`
  return {
    name: 'getUserDefaultAirport',
    type: 'userairports',
    url: url,
    requetType: 'GET',
    remark: '获取用户的机场订阅',
    go: params => { return axios.get(url, params).then(res => res.data) }
  }
}
