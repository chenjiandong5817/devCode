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
