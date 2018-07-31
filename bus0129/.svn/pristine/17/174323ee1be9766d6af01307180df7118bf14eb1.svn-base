/*
* @Author: chenjiandong
* @Date:   last_modified_time
* @Last Modified by:   chenjiandong
* @Last Modified time: last_modified_time
*/

import {axios, base} from './../raiis-axios'

let defaultType = 'bridges'
// Airport
// export const getAirportListPage = params => { return axios.get(`${base}/aiisAirport/query`, { params: params }).then(res => res.data) }
export const getBridgesListPage = () => {
    let url = `${base}/bridges`
    return {
        name: 'getBridgesListPage',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '获取登机桥管理信息列表',
        go: params => { return axios.get(url, { params: params }).then(res => res.data) }
    }
}

// export const removeAirport = params => { return axios.post(`${base}/aiisAirport/delete`, params) }
export const removeBridges = () => {
    let url = `${base}/bridges`
    return {
        name: 'removeBridges',
        type: defaultType,
        url: url,
        requetType: 'DELETE',
        remark: '删除登机桥管理信息',
        go: id => { return axios.delete(url + '/' + id).then(res => res.data) }
    }
}

// export const editAirport = params => { return axios.post(`${base}/aiisAirport/update`, params) }
export const editBridges = () => {
    let url = `${base}/bridges`
    return {
        name: 'editBridges',
        type: defaultType,
        url: url,
        requetType: 'PUT',
        remark: '编辑登机桥管理信息',
        go: params => { return axios.put(url, params).then(res => res.data) }
    }
}

// export const addAirport = params => { return axios.post(`${base}/aiisAirport/add`, params) }
export const addBridges = () => {
    let url = `${base}/bridges`
    return {
        name: 'addBridges',
        type: defaultType,
        url: url,
        requetType: 'POST',
        remark: '添加登机桥管理信息',
        go: params => { return axios.post(url, params).then(res => res.data) }
    }
}
