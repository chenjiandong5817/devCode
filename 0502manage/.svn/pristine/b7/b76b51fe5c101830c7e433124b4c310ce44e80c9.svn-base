/*
* @Author: chenjiandong
* @Date:   last_modified_time
* @Last Modified by:   chenjiandong
* @Last Modified time: 2018-07-05 16:32:08
*/
// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import Vuex from 'vuex'
import store from './vuex/store'
import App from './App'
import router from './router'
import Element from 'element-ui'
import 'element-ui/lib/theme-default/index.css'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import 'font-awesome/css/font-awesome.min.css'
import Cache from './common/js/cache'
import './components/dragTree/dom-expand.js'
// import './common/js/jmessage-sdk-web.2.6.0.min.js'
import $ from 'jquery'
// import Raven from './common/js/raven'

Vue.use(Element)
Vue.use(Vuex)
Vue.prototype.$cache = Cache
// 引入极光推送
// var JIM = new window.JMessage()
// Vue.prototype.JIM = JIM
// 引入jquery
Vue.prototype.$ = $

NProgress.configure({ showSpinner: false })
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
