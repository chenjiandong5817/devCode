/**
 * http配置
 */
import axios from 'axios'
import QS from 'qs'
/* ---axios存在Content-Type无法设置的bug,最终采用以下方法解决--- */
/* function axiosEnCode (obj) {
  let param = new URLSearchParams()
  for (var key in obj) {
    param.append(key, obj[key])
  }
  return param
} */
/* ---上述解决方案仍存在axiosEnCode在IOS的兼容性问题，继续修复--- */
function QSstringify (obj) {
  return QS.stringify(obj)
}

// 超时时间
axios.defaults.timeout = 30000
// axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded'
// http请求拦截器
axios.interceptors.request.use(config => {
// 在发送请求之前做些什么
  // config.headers['Content-Type'] = 'application/x-www-form-urlencoded'
  // console.log(config)
  // config.data = axiosEnCode(config.data)
  config.data = QSstringify(config.data)
  return config
}, error => {
  return Promise.reject(error)
})
// http响应拦截器
axios.interceptors.response.use(data => {
// 对响应数据做些什么
// 因为axios请求后的回参会多加一层，此处将数据提升出来再返回
  return data.data
}, error => {
  return Promise.reject(error)
})

export default axios
