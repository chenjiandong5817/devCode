/*
 * @Author: cdroid
 * @Date: 2017-05-25 10:50:35
 * @Last Modified by: chenyang
 * @Last Modified time: 2017-10-09 17:09:12
 * @Description:  axios 封装实现类
 */
import util from './util'
import authorityHelper from './authority-helper'
import store from './../../vuex/store'
import NProgress from 'nprogress'

function setApiHeader (config) {
  if (store.getters.isLogin) {
    var storage = store.getters.getUserStorage
    var at = storage.token
    var name = storage.user.name || ''
    var uuid = util.uuid()
    var time = new Date().getTime()
    var appSecret = at + ',' + name + ',' + uuid + ',' + time
    var key = authorityHelper.sha1(appSecret)
    config.headers['Api-Version'] = 'v1.0'
    config.headers['Api-Loginname'] = name
    config.headers['Api-Nonce'] = uuid
    config.headers['Api-Time'] = time
    config.headers['Api-Key'] = key
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
      return Promise.reject(error)
    })
  }
}
