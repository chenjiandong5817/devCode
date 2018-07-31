import Vue from 'vue'
import router from './router'
import store from './store'
import NProgress from 'nprogress'
import { validatenull } from '@/util/validate'
import { getQueryString } from '@/util/yun'

const whiteList = ['/login', '/authredirect', '/logout']// no redirect whitelist
router.beforeEach((to, from, next) => {
  NProgress.start()
  let ssoLogin = Vue.prototype.$sso
  if (ssoLogin.getToken()) { // determine if there has token
    /* has token */
    if (to.path === '/login') {
      next({ path: '/' })
      NProgress.done()
    } else {
      Promise.resolve().then(() => {
        return new Promise((resolve, reject) => {
          if (validatenull(store.getters.userInfo)) {
            store.dispatch('LoadAuthorizationInfo').then(() => {
              resolve()
            }).catch((e) => {
              next({ path: '/logout' })
              reject(e)
            })
          } else {
            resolve()
          }
        })
      }).then(() => {
        return new Promise(resolve => {
          if (store.getters.addRouters === undefined) {
            store.dispatch('GenerateRoutes').then(() => { // 生成可访问的路由表
              if (!validatenull(store.getters.addRouters)) {
                router.addRoutes(store.getters.addRouters) // 动态添加可访问路由表
                next({ ...to, replace: true }) // hack方法 确保addRoutes已完成
              } else {
                next()
              }
              resolve()
            })
          } else {
            next()
            resolve()
          }
        })
      })
    }
  } else {
    /* has no token */
    // 检测是否为sso服务端转发
    let code = getQueryString(window.location.search, 'code')
    code && ssoLogin.saveCode(code)
    if (code && to.path !== '/authredirect') {
      next({ path: '/authredirect' })
      NProgress.done()
    } else if (whiteList.indexOf(to.path) !== -1) { // 在免登录白名单，直接进入
      next()
    } else {
      next('/login') // 否则全部重定向到登录页
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  NProgress.done() // finish progress bar
})
