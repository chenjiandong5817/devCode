import axios from '@/router/axios'
import { setStore, getStore, removeStore } from '@/util/store'
import { MessageBox } from 'element-ui'
import { AUTH_CLIENT_ID, AUTH_CLIENT_SECRET, AUTH_SERVER, AUTH_PROXY } from '@/const/global'

const localCodeKey = 'authorizeCode'
const localTokenKey = 'token'
const localRefreshTokenKey = 'refreshToken'

function getWebsiteUrl () {
  const protocol = window.location.protocol
  const host = window.location.host
  return `${protocol}//${host}`
}

function buildParams (data) {
  let ret = ''
  for (const it in data) {
    ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
  }
  if (ret.length > 0) {
    ret = ret.substr(0, ret.length - 1)
  }
  return ret
}

class SsoLogin {
  /**
   * 构造函数
   * @param {*} clientId 客户端id
   * @param {*} clientSecret 客户端密钥
   * @param {*} ssoServer 授权服务器地址，不能用代理
   */
  constructor () {
    this.options = {
      authorizeMode: 'authorization_code',
      refreshMode: 'refresh_token',
      clientId: AUTH_CLIENT_ID,
      clientSecret: AUTH_CLIENT_SECRET,
      clientBasicAuth: 'Basic ' + btoa(`${AUTH_CLIENT_ID}:${AUTH_CLIENT_SECRET}`),
      ssoServer: AUTH_SERVER,
      loginProxy: AUTH_SERVER + '/oauth/authorize',
      logoutProxy: AUTH_SERVER + '/logout',
      tokenProxy: AUTH_PROXY + '/oauth/token'
    }
  }
  /**
   * 登录，跳转到sso
   */
  login () {
    let data = {
      response_type: 'code',
      redirect_uri: getWebsiteUrl(),
      client_id: this.options.clientId
    }
    let url = this.options.loginProxy
    let params = buildParams(data)
    window.location.href = url + '?' + params
  }
  /**
   * 退出登陆，注销token
   */
  logout () {
    return new Promise(resolve => {
      // 注销token
      this.revokeAccessToken().then(res => {
        this.clearAuthorization()
        // 跳转到服务器logout地址
        window.location.href = this.options.logoutProxy
        resolve(res)
      })
    })
  }
  /**
   * 获取access_token
   */
  requestAccessToken () {
    let localCode = this.getCode()
    if (!localCode) {
      throw new Error('登陆参数错误...')
    }
    let data = {
      redirect_uri: getWebsiteUrl(),
      grant_type: this.options.authorizeMode,
      code: localCode
    }
    this.useBasicAuth = true
    return axios.post(this.options.tokenProxy, data, {
      transformRequest: [function (data) {
        return buildParams(data)
      }],
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
        'Authorization': this.options.clientBasicAuth
      }
    }).then(res => {
      this.useBasicAuth = false
      this.saveAuthorization(res)
      return Promise.resolve(res)
    }).catch(e => {
      this.useBasicAuth = false
      return Promise.reject(e)
    })
  }
  /**
   * 刷新access_token
   */
  refreshAccessToken () {
    let refreshToken = getStore({ name: localRefreshTokenKey })
    if (!refreshToken) {
      throw new Error('未找到refresh_token...')
    }
    const data = {
      grant_type: this.options.refreshMode,
      refresh_token: refreshToken
    }
    this.useBasicAuth = true
    return axios.post(this.options.tokenProxy, {}, {
      params: data,
      headers: {
        'Authorization': this.options.clientBasicAuth
      }
    }).then(res => {
      this.useBasicAuth = false
      this.saveAuthorization(res)
      return Promise.resolve(true)
    }).catch(e => {
      this.useBasicAuth = false
      if (e && e.response) {
        let response = e.response
        let info = response.data
        if (response.status === 401 || info.status === 40101 || info.error === 'invalid_grant' || info.error === 'Bad Request') {
          MessageBox.confirm('登录信息已从其他站点更新，请刷新页面。', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.unauthorizedReload()
          })
        }
      }
      return Promise.reject(e)
    })
  }
  /**
   * 吊销token
   */
  revokeAccessToken () {
    this.useBasicAuth = true
    return axios.delete(this.options.tokenProxy, {
      params: {
        access_token: this.getToken()
      },
      headers: {
        'Authorization': this.options.clientBasicAuth
      }
    }).then(res => {
      this.useBasicAuth = false
      return Promise.resolve()
    }).catch(e => {
      this.useBasicAuth = false
      return Promise.reject(e)
    })
  }
  /**
   * 从缓存内取出token
   */
  getToken () {
    const token = getStore({ name: localTokenKey })
    return token || ''
  }
  /**
   * 从缓存内取出refresh_token
   */
  getRefreshToken () {
    const refreshToken = getStore({ name: localRefreshTokenKey })
    return refreshToken || ''
  }
  /**
   *  保存授权信息
   * @param {*} data 授权信息
   */
  saveAuthorization (data) {
    const token = 'Bearer ' + data.access_token
    const refreshToken = data.refresh_token
    setStore({ name: localTokenKey, content: token, type: 'session' })
    setStore({ name: localRefreshTokenKey, content: refreshToken, type: 'session' })
  }
  /**
   * 清空授权信息
   */
  clearAuthorization () {
    removeStore({ name: localTokenKey })
    removeStore({ name: localRefreshTokenKey })
  }
  /**
   * 保存授权码，存储起来用于校对
   * @param {*} code 授权码
   */
  saveCode (code) {
    setStore({ name: localCodeKey, content: code, type: 'session' })
  }
  /**
   * 获取缓存的授权码
   */
  getCode () {
    let code = getStore({ name: localCodeKey })
    return code || ''
  }
  /**
   * 重新加载页面，去掉code等参数
   */
  reloadWithoutCode () {
    let url = getWebsiteUrl()
    window.location.href = url
  }
  /**
   * 权限校验失败重新加载页面
   */
  unauthorizedReload () {
    this.clearAuthorization()
    this.reloadWithoutCode()
  }
}

export default SsoLogin
