/*
* @Author: chenjiandong
* @Date:   last_modified_time
* @Last Modified by:   chenjiandong
* @Last Modified time: last_modified_time
*/
import {axios, base} from './../raiis-axios'

let defaultType = 'airlines'

export const getAirlinesListPage = () => {
    let url = `${base}/airlines`
    return {
        name: 'getFlightstatusListPage',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '获取航空公司信息列表',
        go: params => { return axios.get(url, { params: params }).then(res => res.data) }
    }
}

export const removeAirlines = () => {
    let url = `${base}/airlines`
    return {
        name: 'removeAirlines',
        type: defaultType,
        url: url,
        requetType: 'DELETE',
        remark: '删除航空公司信息',
        go: id => { return axios.delete(url + '/' + id).then(res => res.data) }
    }
}

export const editAirlines = () => {
    let url = `${base}/airlines`
    return {
        name: 'editAirlines',
        type: defaultType,
        url: url,
        requetType: 'PUT',
        remark: '编辑航空公司信息',
        go: params => { return axios.put(url, params).then(res => res.data) }
    }
}

export const addAirlines = () => {
    let url = `${base}/airlines`
    return {
        name: 'addAirlines',
        type: defaultType,
        url: url,
        requetType: 'POST',
        remark: '添加航空公司信息',
        go: params => { return axios.post(url, params).then(res => res.data) }
    }
}

