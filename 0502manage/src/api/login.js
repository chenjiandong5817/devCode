/*
* @Author: cdroid
* @Date:   2017-10-18 11:09:30
* @Last Modified by:   chenjiandong
* @Last Modified time: 2018-03-15 16:24:31
*/

/*
 * @Author: cdroid
 * @Date: 2017-05-25 10:49:08
 * @Last Modified by: cdroid
 * @Last Modified time: 2017-05-25 10:49:33
 * @Description:  Login操作的API
 */
import {axios, base, bus} from './raiis-axios'

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
// 元翔专车接口
// 元翔专车登录测试
export const busRequestLogin = () => {
    let url = `${bus}/user/tokens`
    return {
        name: 'busRequestLogin',
        type: defaultType,
        url: url,
        requetType: 'POST',
        remark: '登陆请求',
        go: params => { return axios.post(url, params).then(res => res.data) }
    }
}

// 验证码图片的获取
export const busValidateImage = () => {
    let url = `${bus}/user/authCode`
    return {
        name: 'busValidateImage',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '获取验证码图片',
        go: params => { return axios.get(url).then(res => res.data) }
    }
}

// 退出登录
export const busRequestLogout = () => {
    let url = `${bus}/user/tokens`
    return {
        name: 'busRequestLogout',
        type: defaultType,
        url: url,
        requetType: 'DELETE',
        remark: '注销登陆',
        go: id => { return axios.delete(url + '?id=' + id).then(res => res.data) }
    }
}
