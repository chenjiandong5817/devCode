/*
* @Author: chenjiandong
* @Date:   last_modified_time
* @Last Modified by:   chenjiandong
* @Last Modified time: last_modified_time
*/

import {axios, base} from './../raiis-axios'

let defaultType = 'baggagecarousels'
// Airport
// export const getAirportListPage = params => { return axios.get(`${base}/aiisAirport/query`, { params: params }).then(res => res.data) }
export const getBaggagecarouselsListPage = () => {
    let url = `${base}/baggagecarousels`
    return {
        name: 'getBaggagecarouselsListPage',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '获取行李提取装盘列表',
        go: params => { return axios.get(url, { params: params }).then(res => res.data) }
    }
}

// export const removeAirport = params => { return axios.post(`${base}/aiisAirport/delete`, params) }
export const removeBaggagecarousels = () => {
    let url = `${base}/baggagecarousels`
    return {
        name: 'removeBaggagecarousels',
        type: defaultType,
        url: url,
        requetType: 'DELETE',
        remark: '删除行李提取装盘',
        go: id => { return axios.delete(url + '/' + id).then(res => res.data) }
    }
}

// export const editAirport = params => { return axios.post(`${base}/aiisAirport/update`, params) }
export const editBaggagecarousels = () => {
    let url = `${base}/baggagecarousels`
    return {
        name: 'editBaggagecarousels',
        type: defaultType,
        url: url,
        requetType: 'PUT',
        remark: '编辑行李提取装盘',
        go: params => { return axios.put(url, params).then(res => res.data) }
    }
}

// export const addAirport = params => { return axios.post(`${base}/aiisAirport/add`, params) }
export const addBaggagecarousels = () => {
    let url = `${base}/baggagecarousels`
    return {
        name: 'addBaggagecarousels',
        type: defaultType,
        url: url,
        requetType: 'POST',
        remark: '添加行李提取装盘',
        go: params => { return axios.post(url, params).then(res => res.data) }
    }
}
