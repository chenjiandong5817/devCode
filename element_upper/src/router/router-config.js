/*
* @Author: chenjiandong
* @Date:   last_modified_time
* @Last Modified by:   chenjiandong
* @Last Modified time: last_modified_time
*/

import User from '../views/system/User'
import Role from '../views/system/Role'
import Permission from '../views/system/Permission'
import ApiGrid from '../views/system/ApiGrid'
import NotFound from '../views/404'
import Login from '../views/Login'
import Main from '../views/Main'
import AiisAirport from '../views/system/AiisAirport.vue'
// import CommTable from './../components/CommTable'
import VersionManagement from './../views/system/VersionManagement'
// 基础表
let Component = {
  user: User,
  role: Role,
  permission: Permission,
  aiisAirport: AiisAirport,
  apiGrid: ApiGrid,
  versionmanagement: VersionManagement
}

let routes = [
  {
    id: 1000,
    path: '/',
    component: Main,
    name: '',
    leaf: true,
    iconCls: 'fa fa-home fa-lg',
    children: []
  },
  {
    id: 1300,
    path: '/',
    component: Main,
    name: '系统管理',
    iconCls: 'fa fa-wrench',
    children: [
      { id: 1301, path: '/user', name: '用户管理', components: Component },
      { id: 1302, path: '/role', name: '角色管理', components: Component },
      { id: 1303, path: '/permission', name: '权限管理', components: Component },
      { id: 1304, path: '/aiisAirport', name: '运营机场管理', components: Component },
      { id: 1305, path: '/apiGrid', name: '请求地址管理', components: Component },
      { id: 1306, path: '/versionmanagement', name: '版本管理', components: Component }
    ]
  },
  {
    id: 9999,
    path: '/login',
    component: Login,
    name: '',
    hidden: true
  },
  {
    id: 9998,
    path: '/404',
    component: NotFound,
    name: '',
    hidden: true
  },
  {
    id: 9997,
    path: '*',
    hidden: true,
    redirect: { path: '/404' }
  }
]

export default routes
