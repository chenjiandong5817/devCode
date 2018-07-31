/**
 * http配置
 */
import axios from 'axios'
// import { Toast, createAPI } from 'cube-ui'
/* ---上述解决方案仍存在axiosEnCode在IOS的兼容性问题，继续修复--- */
import QS from 'qs'
/* 注册新的Vue实例用于公用提示 */
import Vue from 'vue'
import { Toast } from 'cube-ui'
Vue.use(Toast)
const commom = new Vue()
let cancel
let promiseArr = {}
const CancelToken = axios.CancelToken
// 请求拦截器
axios.interceptors.request.use(function (config) {
    // 在发送请求之前做些什么
  if (promiseArr[config.url]) {
    promiseArr[config.url]('请求重复')
    promiseArr[config.url] = cancel
  } else {
    promiseArr[config.url] = cancel
  }
  return config
}, function (error) {
  let toast = commom.$createToast({
    txt: error.message,
    type: 'warn',
    mask: true
  })
  toast.show()
    // 对请求错误做些什么
  return Promise.reject(error)
})

// 响应拦截器即异常处理
axios.interceptors.response.use(function (response) {
    // 对响应数据做点什么
  return response.data
}, function (error) {
  let toast = commom.$createToast({
    txt: error.message,
    type: 'warn',
    mask: true
  })
  toast.show()
    // 对响应错误做点什么
  return Promise.reject(error)
})

// 设置默认请求头
axios.defaults.headers = {
  'X-Requested-With': 'XMLHttpRequest'
}
axios.defaults.timeout = 30000

export default {
  // get请求
  get (url, param) {
    return new Promise((resolve, reject) => {
      axios({
        headers: {
          'X-Requested-With': 'XMLHttpRequest',
          'Accept': 'application/json',
          'Content-Type': 'application/json; charset=UTF-8'
        },
        method: 'get',
        url,
        params: param,
        cancelToken: new CancelToken(c => {
          cancel = c
        })
      }).then(res => {
        if (!res.success) {
          let toast = commom.$createToast({
            txt: res.msg,
            type: 'warn',
            mask: true
          })
          toast.show()
        }
        resolve(res)
      }).catch(err => {
        reject(err)
      })
    })
  },
  // post请求
  post (url, param) {
    return new Promise((resolve, reject) => {
      axios({
        headers: {
          'X-Requested-With': 'XMLHttpRequest',
          'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
        },
        method: 'post',
        url,
        data: QS.stringify(param),
        cancelToken: new CancelToken(c => {
          cancel = c
        })
      }).then(res => {
        if (!res.success) {
          let toast = commom.$createToast({
            txt: res.msg,
            type: 'warn',
            mask: true
          })
          toast.show()
        }
        resolve(res)
      }).catch(err => {
        reject(err)
      })
    })
  }
}
