/**
 * http配置
 */
import axios from 'axios'
import Vue from 'vue'
import { fillStringPlaceholder } from '@/util/util'
import NProgress from 'nprogress'
import { Notification } from 'element-ui'
import { debounce } from 'throttle-debounce'

// url base
export const base = 'api'

// 跨域请求，允许保存cookie
axios.defaults.withCredentials = true

// 超时时间
axios.defaults.timeout = 45000

const startProgress = () => {
  NProgress.start()
}

const stopProgress = () => {
  NProgress && NProgress.done()
}

// 执行后如果401，判断是否有refresh_token，自动刷新token
const checkAfterRequest = (http, url, params, config) => {
  return new Promise((resolve, reject) => {
    http(url, params, config).then(data => {
      resolve(data)
    }).catch(error => {
      if (!error || !error.response) {
        return reject(error)
      }
      let response = error.response
      let info = response.data
      if ((response.status !== 401 && info.status !== 40101)) {
        return reject(error)
      }
      console.error('token过期，开始刷新token...')
      let ssoLogin = Vue.prototype.$sso
      if (!ssoLogin) {
        return reject(error)
      }
      ssoLogin.refreshAccessToken().then(() => {
        console.log('token 更新完毕，重新发送请求...')
        http(url, params, config).then(data => {
          return resolve(data)
        })
      }).catch(e => {
        return reject(e)
      })
    })
  })
}

// 其他类型错误提醒
const requestErrorMessage = debounce(300, true, ({title, msg, duration}) => {
  Notification({
    type: 'error',
    title: title || '错误',
    message: msg || '错误',
    duration: duration || 3000
  })
})
// HTTPrequest拦截
axios.interceptors.request.use(config => {
  startProgress()
  let ssoLogin = Vue.prototype.$sso
  let token = ssoLogin.getToken()
  if (token && !ssoLogin.useBasicAuth) {
    config.headers.Authorization = token// 让每个请求携带token--['X-Token']为自定义key 请根据实际情况自行修改
  }
  return config
}, error => {
  return Promise.reject(error)
})
// HTTPresponse拦截
axios.interceptors.response.use(res => {
  stopProgress()
  if (res.data && res.data.status === 0 && res.data.message) {
    requestErrorMessage({msg: res.data.message})
  }
  return res.data
}, error => {
  stopProgress()
  const response = error.response
  if (response === undefined) {
    requestErrorMessage({msg: '服务请求超时！', duration: 5 * 1000})
    return Promise.reject(error)
  }
  if (response.status === 400) {
    requestErrorMessage({msg: '用户凭证异常，请重新登录！', duration: 2 * 1000})
    return Promise.reject(error)
  }
  const info = response.data
  if (response.status === 401 || info.status === 40101) {
    // accessTokenTimeout()
    return Promise.reject(error)
  }
  if (response.status === 403) {
    requestErrorMessage({title: '禁止', msg: info.message, duration: 2 * 1000})
    return Promise.reject(error)
  }
  if (info.status === 30101) {
    requestErrorMessage({title: '失败', msg: info.message, duration: 2 * 1000})
    return Promise.reject(error)
  }
  if (response.status === 500 && info.message === 'GENERAL') {
    requestErrorMessage({msg: '正在重连网关，请稍后再试！', duration: 5 * 1000})
    return Promise.reject(error)
  }
  if (response.status === 504) {
    requestErrorMessage({msg: '后端服务异常，请联系管理员！', duration: 5 * 1000})
    return Promise.reject(error)
  }
  requestErrorMessage({msg: info.message, duration: 5 * 1000})
  return Promise.reject(error)
})

export default axios

/**
 * axios 请求调用底下封装的方法。
 * 在每次请求之后判断是否需要刷新token
 */
export const GET = (u, p) => {
  let get = (url, params) => {
    return axios.get(base + fillStringPlaceholder(url, params), {params})
  }
  return checkAfterRequest(get, u, p)
}
export const POST = (u, p, config) => {
  let post = (url, params, config) => {
    return axios.post(base + fillStringPlaceholder(url, params), params, config)
  }
  return checkAfterRequest(post, u, p, config)
}

export const PUT = (u, p, config) => {
  let put = (url, params, config) => {
    return axios.put(base + fillStringPlaceholder(url, params), params, config)
  }
  return checkAfterRequest(put, u, p, config)
}

export const DELETE = (u, p, config) => {
  let del = (url, params, config) => {
    return axios.delete(base + fillStringPlaceholder(url, params), config)
  }
  return checkAfterRequest(del, u, p, config)
}
