/*
* @Author: chenjiandong
* @Date:   last_modified_time
* @Last Modified by:   chenjiandong
* @Last Modified time: last_modified_time
*/

import {axios, base} from './../raiis-axios'

let defaultType = 'waitinghalls'
// Airport
// export const getAirportListPage = params => { return axios.get(`${base}/aiisAirport/query`, { params: params }).then(res => res.data) }
export const getWaitingHallsListPage = () => {
    let url = `${base}/waitinghalls`
    return {
        name: 'getWaitingHallsListPage',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '获取候机厅信息列表',
        go: params => { return axios.get(url, { params: params }).then(res => res.data) }
    }
}

// export const removeAirport = params => { return axios.post(`${base}/aiisAirport/delete`, params) }
export const removeWaitingHalls = () => {
    let url = `${base}/waitinghalls`
    return {
        name: 'removeWaitingHalls',
        type: defaultType,
        url: url,
        requetType: 'DELETE',
        remark: '删除候机厅信息',
        go: id => { return axios.delete(url + '/' + id).then(res => res.data) }
    }
}

// export const editAirport = params => { return axios.post(`${base}/aiisAirport/update`, params) }
export const editWaitingHalls = () => {
    let url = `${base}/waitinghalls`
    return {
        name: 'editWaitingHalls',
        type: defaultType,
        url: url,
        requetType: 'PUT',
        remark: '编辑候机厅信息',
        go: params => { return axios.put(url, params).then(res => res.data) }
    }
}

// export const addAirport = params => { return axios.post(`${base}/aiisAirport/add`, params) }
export const addWaitingHalls = () => {
    let url = `${base}/waitinghalls`
    return {
        name: 'addWaitingHalls',
        type: defaultType,
        url: url,
        requetType: 'POST',
        remark: '添加候机厅信息',
        go: params => { return axios.post(url, params).then(res => res.data) }
    }
}
