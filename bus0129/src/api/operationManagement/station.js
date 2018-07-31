/*
* @Author: chenjiandong
* @Date:   last_modified_time
* @Last Modified by:   chenjiandong
* @Last Modified time: 2018-03-13 17:02:08
*/

import { axios, bus } from './../raiis-axios'

let defaultType = 'station'
// BUS
export const getAllStationListPage = () => {
    let url = `${bus}/station/allstation`
    return {
        name: 'getAllStationListPage',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '获取所有站点信息列表',
        go: params => { return axios.get(url, { params: params }).then(res => res.data) }
    }
}

export const removeStation = () => {
    let url = `${bus}/stations`
    return {
        name: 'removeStation',
        type: defaultType,
        url: url,
        requetType: 'DELETE',
        remark: '删除站点管理信息',
        go: id => { return axios.delete(url + '/' + id).then(res => res.data) }
    }
}

export const editStation = () => {
    let url = `${bus}/station`
    return {
        name: 'editStation',
        type: defaultType,
        url: url,
        requetType: 'PUT',
        remark: '编辑站点管理信息',
        go: params => { return axios.put(url, params).then(res => res.data) }
    }
}

export const addStation = () => {
    let url = `${bus}/stations`
    return {
        name: 'addStation',
        type: defaultType,
        url: url,
        requetType: 'POST',
        remark: '添加站点管理信息',
        go: params => { return axios.post(url, params).then(res => res.data) }
    }
}

export const searchStation = () => {
    let url = `${bus}/station`
    return {
        name: 'searchStation',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '查询站点管理信息',
        go: params => { return axios.get(url + '?stationName=' + params).then(res => res.data) }
    }
}

export const getLatAndLon = () => {
    let url = `${bus}/latAndLon`
    return {
        name: 'getLatAndLon',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '查询经纬度信息信息',
        go: params => { return axios.get(url + '?addr=' + params).then(res => res.data) }
    }
}
