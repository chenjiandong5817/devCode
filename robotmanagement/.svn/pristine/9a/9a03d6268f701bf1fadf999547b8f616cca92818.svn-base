/*
* @Author: cdroid
* @Date:   2018-01-29 17:15:36
* @Last Modified by:   chenjiandong
* @Last Modified time: 2018-03-08 17:33:10
*/
/*
 * @Author: cdroid
 * @Date: 2017-05-25 10:50:35
 * @Last Modified by: cdroid
 * @Last Modified time: 2018-01-04 11:30:46
 * @Description:  axios 封装实现类
 */
import util from './util'
import authorityHelper from './authority-helper'
import store from './../../vuex/store'
import NProgress from 'nprogress'
import Vue from 'vue'
import Router from 'vue-router'
import RouterConfig from './../../router/router-config'
// import ElementUI from 'element-ui'

// import vueRouter from './../../router/index'
// import store from './../../vuex/store'

function setApiHeader (config) {
  if (store.getters.isLogin) {
    var storage = store.getters.getUserStorage
    var at = storage.token
    var name = storage.user.name || ''
    var userId = storage.user.id
    var uuid = util.uuid()
    var time = new Date().getTime()
    var appSecret = at + ',' + name + ',' + uuid + ',' + time
    var cookie = 'sessionId=' + at
    var key = authorityHelper.sha1(appSecret)
    var authorization = userId + '_' + at
    config.headers['Api-Version'] = 'v1.0'
    config.headers['Api-Loginname'] = name
    config.headers['Api-Nonce'] = uuid
    config.headers['Api-Time'] = time
    config.headers['Api-Key'] = key
    config.headers['authorization'] = authorization
    config.headers['Set-Cookie'] = cookie
  }
  return config
}

export default {
  generatAuthHeader () {
    let target = {
      headers: {}
    }
    target = setApiHeader(target)
    return target.headers
  },
  requestInterceptor (axios) {
    axios.interceptors.request.use(config => {
      NProgress.start()
      return setApiHeader(config)
    }, error => {
      return Promise.reject(error)
    })
  },
  responseInterceptor (axios) {
    axios.interceptors.response.use(response => {
      if (NProgress) {
        NProgress.done()
      }
      return response
    }, error => {
      if (NProgress) {
        NProgress.done()
      }
      util.responseError(error.response.status)
      if (error.response.status === 403) {
        // TODO 跳转到登录界面
        // 在js页面进行路由跳转，引入的东西
        Vue.use(Router)
        var vueRouter = new Router({
          routes: RouterConfig
        })
        // ElementUI.Notification(util.notifyBody(false, '权限获取失败'))
        setTimeout(() => {
          vueRouter.push({ path: '/login' })
          vueRouter.go(0)
        }, 2500)
      }
      return Promise.reject(error)
    })
  }
}
