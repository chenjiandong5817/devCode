/*
* @Author: chenjiandong
* @Date:   last_modified_time
* @Last Modified by:   chenjiandong
* @Last Modified time: last_modified_time
*/

import {axios, base} from './../raiis-axios'

let defaultType = 'aprons'
// Airport
// export const getAirportListPage = params => { return axios.get(`${base}/aiisAirport/query`, { params: params }).then(res => res.data) }
export const getApronsListPage = () => {
    let url = `${base}/aprons`
    return {
        name: 'getApronsListPage',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '获取机坪区域管理信息列表',
        go: params => { return axios.get(url, { params: params }).then(res => res.data) }
    }
}

// export const removeAirport = params => { return axios.post(`${base}/aiisAirport/delete`, params) }
export const removeAprons = () => {
    let url = `${base}/aprons`
    return {
        name: 'removeAprons',
        type: defaultType,
        url: url,
        requetType: 'DELETE',
        remark: '删除机坪区域管理信息',
        go: id => { return axios.delete(url + '/' + id).then(res => res.data) }
    }
}

// export const editAirport = params => { return axios.post(`${base}/aiisAirport/update`, params) }
export const editAprons = () => {
    let url = `${base}/aprons`
    return {
        name: 'editAprons',
        type: defaultType,
        url: url,
        requetType: 'PUT',
        remark: '编辑机坪区域管理信息',
        go: params => { return axios.put(url, params).then(res => res.data) }
    }
}

// export const addAirport = params => { return axios.post(`${base}/aiisAirport/add`, params) }
export const addAprons = () => {
    let url = `${base}/aprons`
    return {
        name: 'addAprons',
        type: defaultType,
        url: url,
        requetType: 'POST',
        remark: '添加机坪区域管理信息',
        go: params => { return axios.post(url, params).then(res => res.data) }
    }
}
