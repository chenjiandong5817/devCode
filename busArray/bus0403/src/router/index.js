/*
* @Author: chenjiandong
* @Date:   last_modified_time
* @Last Modified by:   chenjiandong
* @Last Modified time: last_modified_time
*/
/*
* @Author: chenjiandong
* @Date:   2017-08-14 08:41:59
* @Last Modified by:   chenjiandong
* @Last Modified time: 2017-09-14 09:49:30
*/
import Vue from 'vue'
import Router from 'vue-router'
import RouterConfig from './router-config'
// import authorityHelper from './../common/js/authority-helper'
import store from './../vuex/store'

Vue.use(Router)

var vueRouter = new Router({
  routes: RouterConfig
})
vueRouter.beforeEach((to, from, next) => {
  if (to.path === '/login') {
    store.dispatch('removeUserStorage')
  }
  // console.log('login state: ' + store.getters.isLogin)
  if (!store.getters.isLogin && to.path !== '/login') {
    next({ path: '/login' })
  } else if (to.path === '/') {
    next({ path: '/home' })
  } else {
    next()
  }
})
export default vueRouter
