/*
* @Author: chenjiandong
* @Date:   last_modified_time
* @Last Modified by:   chenjiandong
* @Last Modified time: 2018-04-03 14:31:11
*/

import User from '../views/system/User'
// import Role from '../views/system/Role'
// import Permission from '../views/system/Permission'
import NotFound from '../views/404'
import Login from '../views/Login'
import Main from '../views/Main'
import RealTimeStatistics from './../views/operationManagement/RealTimeStatistics'
import OrderStatistics from './../views/operationManagement/OrderStatistics'
import AdEdit from './../views/operationManagement/AdEdit'
import RemoteControl from './../views/operationManagement/RemoteControl'
import SiteConfiguration from './../views/operationManagement/SiteConfiguration'
// import RouteConfiguration from './../views/operationManagement/RouteConfiguration'
import DesClassify from './../views/operationManagement/DesClassify'
import SellStation from './../views/operationManagement/SellStation'
import home from './../views/operationManagement/Home'
// 基础表
let Component = {
  home: home,
  user: User,
  // role: Role,
  // permission: Permission,
  realTimeStatistics: RealTimeStatistics,
  orderStatistics: OrderStatistics,
  adEdit: AdEdit,
  remoteControl: RemoteControl,
  siteConfiguration: SiteConfiguration,
  // routeConfiguration: RouteConfiguration,
  desClassify: DesClassify,
  sellStation: SellStation
}

let routes = [
  {
    id: 1000,
    path: '/',
    component: Main,
    name: '',
    leaf: true,
    iconCls: 'fa fa-home fa-lg',
    children: [
      { id: 1001, path: '/home', name: '实时统计', components: Component }
    ]
  },
  {
    id: 1200,
    path: '/',
    component: Main,
    name: '运营管理',
    iconCls: 'fa fa-list', // 图标样式class
    children: [
      { id: 1201, path: '/realTimeStatistics', name: '实时统计', components: Component },
      { id: 1202, path: '/orderStatistics', name: '订单统计', components: Component },
      { id: 1203, path: '/adEdit', name: '广告填写', components: Component },
      { id: 1204, path: '/remoteControl', name: '远程控制', components: Component },
      { id: 1205, path: '/siteConfiguration', name: '站点配置', components: Component },
      { id: 1206, path: '/desClassify', name: '目的地分类', components: Component },
      // { id: 1207, path: '/routeConfiguration', name: '路线配置', components: Component },
      // { id: 1207, path: '/devices', name: '设备管理', components: Component },
      { id: 1207, path: '/sellStation', name: '售票站点配置', components: Component }
    ]
  },
  {
    id: 1300,
    path: '/',
    component: Main,
    name: '系统管理',
    iconCls: 'fa fa-wrench',
    children: [
      { id: 1301, path: '/user', name: '用户管理', components: Component }
      // { id: 1302, path: '/role', name: '角色管理', components: Component },
      // { id: 1303, path: '/permission', name: '权限管理', components: Component }
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
