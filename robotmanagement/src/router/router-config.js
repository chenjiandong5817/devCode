/*
* @Author: chenjiandong
* @Date:   last_modified_time
* @Last Modified by:   chenjiandong
* @Last Modified time: 2018-06-01 11:37:08
*/

import NotFound from '../views/404'
import Login from '../views/Login'
import Main from '../views/Main'
import chatLog from './../views/base/chatLog'
import knowLedge from './../views/base/knowLedge'
import robotInfo from './../views/base/robotInfo'
import knowledgeCategory from './../views/base/knowledgeCategory'
import simulateAskTest from './../views/base/simulateAskTest'
import chatLogAnalysis from './../views/base/chatLogAnalysis'
// 基础表
let Component = {
  home: chatLog,
  chatlog: chatLog,
  knowledge: knowLedge,
  robotinfo: robotInfo,
  knowledgecategory: knowledgeCategory,
  simulateasktest: simulateAskTest,
  chatloganalysis: chatLogAnalysis
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
      { id: 1001, path: '/home', name: '日志信息管理', components: Component }
    ]
  },
  {
    id: 1200,
    path: '/',
    component: Main,
    name: '',
    leaf: true,
    iconCls: 'el-icon-star-on',
    children: [
      { id: 1201, path: '/knowledge', name: '知识库信息管理', components: Component }
    ]
  },
  {
    id: 1300,
    path: '/',
    component: Main,
    name: '',
    leaf: true,
    iconCls: 'el-icon-view',
    children: [
      { id: 1301, path: '/robotinfo', name: '机器人信息管理', components: Component }
    ]
  },
  {
    id: 1400,
    path: '/',
    component: Main,
    name: '',
    leaf: true,
    iconCls: 'el-icon-menu',
    children: [
      { id: 1401, path: '/knowledgecategory', name: '知识库类别信息管理', components: Component }
    ]
  },
  {
    id: 1500,
    path: '/',
    component: Main,
    name: '',
    leaf: true,
    iconCls: 'el-icon-search',
    children: [
      { id: 1401, path: '/simulateasktest', name: '知识模拟问讯测试工具', components: Component }
    ]
  },
  {
    id: 1600,
    path: '/',
    component: Main,
    name: '',
    leaf: true,
    iconCls: 'el-icon-view',
    children: [
      { id: 1401, path: '/chatloganalysis', name: '日志信息统计', components: Component }
    ]
  },
  // {
  //   id: 1200,
  //   path: '/',
  //   component: Main,
  //   name: '机器人管理',
  //   iconCls: 'fa fa-list', // 图标样式class
  //   children: [
  //     { id: 1201, path: '/chatlog', name: '日志信息管理', components: Component },
  //     { id: 1202, path: '/knowledge', name: '知识库信息管理', components: Component },
  //     { id: 1203, path: '/robotinfo', name: '机器人信息管理', components: Component },
  //     { id: 1204, path: '/knowledgecategory', name: '知识库类别信息管理', components: Component }
  //   ]
  // },
  // {
  //   id: 1300,
  //   path: '/',
  //   component: Main,
  //   name: '系统管理',
  //   iconCls: 'fa fa-wrench',
  //   children: [
  //     // { id: 1301, path: '/user', name: '用户管理', components: Component }
  //     // { id: 1302, path: '/role', name: '角色管理', components: Component },
  //     // { id: 1303, path: '/permission', name: '权限管理', components: Component }
  //   ]
  // },
  {
    id: 9999,
    path: '/login',
    component: Login,
    name: 'login',
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
