/*
 * @Author: cdroid
 * @Date: 2017-05-25 10:49:08
 * @Last Modified by: cdroid
 * @Last Modified time: 2017-05-25 10:49:33
 * @Description:  Login操作的API
 */
import {axios, base} from './raiis-axios'

let defaultType = 'login'
// Login
// export const requestLogin = params => { return axios.post(`${base}/user/login`, params).then(res => res.data) }
export const requestLogin = () => {
    let url = `${base}/user/login`
    return {
        name: 'requestLogin',
        type: defaultType,
        url: url,
        requetType: 'POST',
        remark: '登陆请求',
        go: params => { return axios.post(url, params).then(res => res.data) }
    }
}

// export const requestLogout = params => { return axios.post(`${base}/user/out`, params).then(res => res.data) }
export const requestLogout = () => {
    let url = `${base}/user/out`
    return {
        name: 'requestLogout',
        type: defaultType,
        url: url,
        requetType: 'POST',
        remark: '注销登陆',
        go: params => { return axios.post(url, params).then(res => res.data) }
    }
}
