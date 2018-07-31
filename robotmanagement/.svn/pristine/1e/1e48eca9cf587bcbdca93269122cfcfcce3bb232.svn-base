/*
* @Author: cdroid
* @Date:   2017-10-18 11:09:30
* @Last Modified by:   chenjiandong
* @Last Modified time: 2018-03-06 09:01:45
*/

/*
 * @Author: cdroid
 * @Date: 2017-05-25 10:49:08
 * @Last Modified by: cdroid
 * @Last Modified time: 2017-05-25 10:49:33
 * @Description:  Login操作的API
 */
import {axios, base, robot} from './raiis-axios'

let defaultType = 'login'
// Login

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

// 机器人登录
export const robotRequestLogin = () => {
    let url = `${robot}/loginPost`
    return {
        name: 'robotRequestLogin',
        type: defaultType,
        url: url,
        requetType: 'POST',
        remark: '登陆请求',
        go: params => { return axios.post(url, params).then(res => res.data) }
    }
}

// 机器人退出登录
export const robotRequestLogout = () => {
    let url = `${robot}/logout`
    return {
        name: 'robotRequestLogout',
        type: defaultType,
        url: url,
        requetType: 'POST',
        remark: '注销登陆',
        go: params => { return axios.get(url).then(res => res.data) }
    }
}
