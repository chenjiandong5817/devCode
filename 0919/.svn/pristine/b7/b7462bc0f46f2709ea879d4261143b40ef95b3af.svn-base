/*
* @Author: cdroid
* @Date:   2017-10-18 11:09:30
* @Last Modified by:   chenjiandong
* @Last Modified time: 2018-01-04 11:10:55
*/

import Vue from 'vue'
import Raven from 'raven-js'
import RavenVue from 'raven-js/plugins/vue'
import Global from './../config/global.js'

Vue.config.errorHandler = function handleErrorEvent (err) {
  throw err   
}
Raven
  .config(Global.SENTRY_DSN)
  .addPlugin(RavenVue, Vue)
  .install()

let send = (level, message) => {
  Raven.captureMessage(message, {
    level
  })
}

export default {
  info (message) {
    send('info', message)
  },
  warning (message) {
    send('warning', message)
  },
  error (message) {
    send('error', message)
  }
}
