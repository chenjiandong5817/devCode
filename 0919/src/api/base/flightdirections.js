/*
* @Author: chenjiandong
* @Date:   last_modified_time
* @Last Modified by:   chenjiandong
* @Last Modified time: last_modified_time
*/
import {axios, base} from './../raiis-axios'

let defaultType = 'flightdirections'
export const getFlightDirectionsListPage = () => {
    let url = `${base}/flightdirections`
    return {
        name: 'getFlightDirectionsListPage',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '查询方向代码信息',
        go: params => { return axios.get(url, { params: params }).then(res => res.data) }
    }
}

export const removeFlightDirections = () => {
    let url = `${base}/flightdirections`
    return {
        name: 'removeFlightDirections',
        type: defaultType,
        url: url,
        requetType: 'DELETE',
        remark: '删除方向代码信息',
        go: id => { return axios.delete(url + '/' + id).then(res => res.data) }
    }
}

export const editFlightDirections = () => {
    let url = `${base}/flightdirections`
    return {
        name: 'editFlightDirections',
        type: defaultType,
        url: url,
        requetType: 'PUT',
        remark: '更新方向代码信息',
        go: params => { return axios.put(url, params).then(res => res.data) }
    }
}

export const addFlightDirections = () => {
    let url = `${base}/flightdirections`
    return {
        name: 'addFlightDirections',
        type: defaultType,
        url: url,
        requetType: 'POST',
        remark: '新增方向代码信息',
        go: params => { return axios.post(url, params).then(res => res.data) }
    }
}
