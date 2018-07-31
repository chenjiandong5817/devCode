/*
* @Author: chenjiandong
* @Date:   last_modified_time
* @Last Modified by:   chenjiandong
* @Last Modified time: last_modified_time
*/

import {axios, base} from './raiis-axios'

let defaultType = 'base airport'
// Airport
// export const getAirportListPage = params => { return axios.get(`${base}/aiisAirport/query`, { params: params }).then(res => res.data) }
export const getAirportListPage = () => {
    let url = `${base}/airports`
    return {
        name: 'getAirportListPage',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '获取通用机场列表',
        go: params => { return axios.get(url, { params: params }).then(res => res.data) }
    }
}

// export const removeAirport = params => { return axios.post(`${base}/aiisAirport/delete`, params) }
export const removeAirport = () => {
    let url = `${base}/airports`
    return {
        name: 'removeAirport',
        type: defaultType,
        url: url,
        requetType: 'DELETE',
        remark: '删除通用机场',
        go: params => { return axios.delete(url, params).then(res => res.data) }
    }
}

// export const editAirport = params => { return axios.post(`${base}/aiisAirport/update`, params) }
export const editAirport = () => {
    let url = `${base}/airports`
    return {
        name: 'editAirport',
        type: defaultType,
        url: url,
        requetType: 'PUT',
        remark: '编辑通用机场',
        go: params => { return axios.put(url, params).then(res => res.data) }
    }
}

// export const addAirport = params => { return axios.post(`${base}/aiisAirport/add`, params) }
export const addAirport = () => {
    let url = `${base}/airports/add`
    return {
        name: 'addAirport',
        type: defaultType,
        url: url,
        requetType: 'POST',
        remark: '添加通用机场',
        go: params => { return axios.post(url, params).then(res => res.data) }
    }
}
