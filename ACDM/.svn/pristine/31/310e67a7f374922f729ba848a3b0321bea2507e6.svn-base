
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import '@/styles/theme.scss'
import 'font-awesome/css/font-awesome.min.css'
import './permission'
import AnimateCss from 'animate.css'
import ContextMenu from '@/directives/contentMenu'
import authResource from '@/directives/auth-resource'
import Moment from 'moment'
import SsoLogin from '@/util/sso-login'

Moment.locale('zh-cn')
// NProgress.configure({ showSpinner: false })

// 加入到vue变量
Vue.prototype.$progress = NProgress

// 权限控件
Vue.use(authResource)

Vue.use(AnimateCss)
Vue.use(ContextMenu)
Vue.use(ElementUI)
Vue.config.productionTip = false

// router.addRoutes(asyncRouterMap)

// 加入elementUI 全局提示
Vue.prototype.$ok = (message, title) => {
  ElementUI.Notification({
    type: 'success',
    title: title || '消息',
    message: message
  })
}
Vue.prototype.$fail = (message, title) => {
  ElementUI.Notification({
    type: 'error',
    title: title || '错误',
    message: message
  })
}

// 加入sso对象
Vue.prototype.$sso = new SsoLogin()

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  template: '<App/>',
  components: { App }
})
