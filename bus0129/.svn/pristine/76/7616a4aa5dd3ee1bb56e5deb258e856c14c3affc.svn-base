/*
 * @Author: cdroid
 * @Date: 2017-05-25 10:47:00
 * @Last Modified by: cdroid
 * @Last Modified time: 2017-06-27 17:03:06
 * @Description:  User/UserProfile/Role/Permission 的 API
 */
import { axios, base } from './raiis-axios'

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
    go: params => { return axios.post(url, params).then(res => res.data) }
  }
}

// ************************************** User **************************************
// export const getUserListPage = params => { return axios.get(`${base}/user/query`, { params: params }) }
export const getUserListPage = () => {
  let url = `${base}/user/query`
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
  let url = `${base}/user/delete`
  return {
    name: 'removeUser',
    type: 'user',
    url: url,
    requetType: 'POST',
    remark: '删除用户',
    go: params => { return axios.post(url, params).then(res => res.data) }
  }
}

// export const editUser = params => { return axios.get(`${base}/user/update`, { params: params }) }
export const editUser = () => {
  let url = `${base}/user/update`
  return {
    name: 'editUser',
    type: 'user',
    url: url,
    requetType: 'POST',
    remark: '更新用户',
    go: params => { return axios.post(url, params).then(res => res.data) }
  }
}

// export const addUser = params => { return axios.get(`${base}/user/add`, { params: params }) }
export const addUser = () => {
  let url = `${base}/user/add`
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
    requetType: 'POST',
    remark: '批量锁定用户',
    go: params => { return axios.post(url, params).then(res => res.data) }
  }
}

// export const batchUnlockedUser = params => { return axios.get(`${base}/user/batchunlocked`, { params: params }) }
export const batchUnlockedUser = () => {
  let url = `${base}/user/batchunlocked`
  return {
    name: 'batchUnlockedUser',
    type: 'user',
    url: url,
    requetType: 'POST',
    remark: '批量激活用户',
    go: params => { return axios.post(url, params).then(res => res.data) }
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
    requetType: 'POST',
    remark: '更新用户角色',
    go: params => { return axios.post(url, params).then(res => res.data) }
  }
}

// export const updateUserPer = params => { return axios.post(`${base}/admin/authority/user/update/permission`, params) }
export const updateUserPer = () => {
  let url = `${base}/user/update/permission`
  return {
    name: 'updateUserPer',
    type: 'user',
    url: url,
    requetType: 'POST',
    remark: '更新用户特许权限',
    go: params => { return axios.post(url, params).then(res => res.data) }
  }
}

// ************************************** UserProfile **************************************
export const currentUser = () => {
  let url = `${base}/user/profile/me`
  return {
    name: 'currentUser',
    type: 'user',
    url: url,
    requetType: 'POST',
    remark: '获取当前用户',
    go: params => { return axios.post(url, params).then(res => res.data) }
  }
}

export const getUserProfile = () => {
  let url = `${base}/user/profile/get`
  return {
    name: 'getUserProfile',
    type: 'user',
    url: url,
    requetType: 'POST',
    remark: '获取用户信息',
    go: params => { return axios.post(url, params).then(res => res.data) }
  }
}

export const updateUserProfile = () => {
  let url = `${base}/user/profile/update`
  return {
    name: 'updateUserProfile',
    type: 'user',
    url: url,
    requetType: 'POST',
    remark: '更新用户信息',
    go: params => { return axios.post(url, params).then(res => res.data) }
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

// ************************************** Role **************************************
// export const getRoleListPage = params => { return axios.get(`${base}/admin/authority/roles`, { params: params }) }
export const getRoleListPage = () => {
  let url = `${base}/role/roles`
  return {
    name: 'getRoleListPage',
    type: 'role',
    url: url,
    requetType: 'GET',
    remark: '获取角色列表',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }
  }
}

// export const removeRole = params => { return axios.post(`${base}/admin/authority/role/delete`, params) }
export const removeRole = () => {
  let url = `${base}/role/delete`
  return {
    name: 'removeRole',
    type: 'role',
    url: url,
    requetType: 'POST',
    remark: '删除角色',
    go: params => { return axios.post(url, params).then(res => res.data) }
  }
}

// export const editRole = params => { return axios.post(`${base}/admin/authority/role/update`, params) }
export const editRole = () => {
  let url = `${base}/role/update`
  return {
    name: 'editRole',
    type: 'role',
    url: url,
    requetType: 'POST',
    remark: '编辑角色',
    go: params => { return axios.post(url, params).then(res => res.data) }
  }
}

export const editRolePermission = () => {
  let url = `${base}/role/update/permission`
  return {
    name: 'editRolePermission',
    type: 'role',
    url: url,
    requetType: 'POST',
    remark: '编辑角色权限',
    go: params => { return axios.post(url, params).then(res => res.data) }
  }
}

// export const addRole = params => { return axios.post(`${base}/admin/authority/role/add`, params) }
export const addRole = () => {
  let url = `${base}/role/add`
  return {
    name: 'addRole',
    type: 'role',
    url: url,
    requetType: 'POST',
    remark: '添加角色',
    go: params => { return axios.post(url, params).then(res => res.data) }
  }
}

// export const fetchRolePerList = params => { return axios.get(`${base}/admin/authority/role/fetch`, { params: params }) }
export const fetchRolePerList = () => {
  let url = `${base}/role/fetch`
  return {
    name: 'fetchRolePerList',
    type: 'role',
    url: url,
    requetType: 'GET',
    remark: '获取角色权限列表',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }
  }
}

// ************************************** Permission **************************************
// export const getPermissionListPage = params => { return axios.get(`${base}/admin/authority/permissions`, { params: params }) }
export const getPermissionListPage = () => {
  let url = `${base}/admin/authority/permissions`
  return {
    name: 'getPermissionListPage',
    type: 'permission',
    url: url,
    requetType: 'GET',
    remark: '获取权限列表',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }
  }
}

// export const removePermission = params => { return axios.post(`${base}/admin/authority/permission/delete`, params) }
export const removePermission = () => {
  let url = `${base}/admin/authority/permission/delete`
  return {
    name: 'removePermission',
    type: 'permission',
    url: url,
    requetType: 'POST',
    remark: '添加权限',
    go: params => { return axios.post(url, params).then(res => res.data) }
  }
}

// export const editPermission = params => { return axios.post(`${base}/admin/authority/permission/update`, params) }
export const editPermission = () => {
  let url = `${base}/admin/authority/permission/update`
  return {
    name: 'editPermission',
    type: 'permission',
    url: url,
    requetType: 'POST',
    remark: '编辑权限',
    go: params => { return axios.post(url, params).then(res => res.data) }
  }
}

// export const addPermission = params => { return axios.post(`${base}/admin/authority/permission/add`, params) }
export const addPermission = () => {
  let url = `${base}/admin/authority/permission/add`
  return {
    name: 'addPermission',
    type: 'permission',
    url: url,
    requetType: 'POST',
    remark: '添加权限',
    go: params => { return axios.post(url, params).then(res => res.data) }
  }
}

// ************************************** ResponseCode **************************************
export const getResCodeList = () => {
  let url = `${base}/responseCode/list`
  return {
    name: 'getResCodeList',
    type: 'system',
    url: url,
    requetType: 'GET',
    remark: '获取状态编码列表',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }
  }
}

export const addResCode = () => {
  let url = `${base}/responseCode/add`
  return {
    name: 'addResCode',
    type: 'system',
    url: url,
    requetType: 'POST',
    remark: '添加状态编码',
    go: params => { return axios.post(url, params).then(res => res.data) }
  }
}

export const editResCode = () => {
  let url = `${base}/responseCode/update`
  return {
    name: 'editResCode',
    type: 'system',
    url: url,
    requetType: 'POST',
    remark: '添加状态编码',
    go: params => { return axios.post(url, params).then(res => res.data) }
  }
}

export const removeResCode = () => {
  let url = `${base}/responseCode/delete`
  return {
    name: 'removeResCode',
    type: 'system',
    url: url,
    requetType: 'POST',
    remark: '删除状态编码',
    go: params => { return axios.post(url, params).then(res => res.data) }
  }
}
