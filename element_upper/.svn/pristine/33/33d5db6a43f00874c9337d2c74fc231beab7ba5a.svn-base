/*
* @Author: chenjiandong
* @Date:   last_modified_time
* @Last Modified by:   chenjiandong
* @Last Modified time: last_modified_time
*/
import {axios, base} from './../raiis-axios'

let defaultType = 'flightstatus'

export const getFlightstatusListPage = () => {
    let url = `${base}/flightstatus`
    return {
        name: 'getFlightstatusListPage',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '获取航班进展状态编码记录列表',
        go: params => { return axios.get(url, { params: params }).then(res => res.data) }
    }
}

export const removeFlightstatuscodes = () => {
    let url = `${base}/flightstatus`
    return {
        name: 'removeFlightstatuscodes',
        type: defaultType,
        url: url,
        requetType: 'DELETE',
        remark: '删除航班进展状态编码记录',
        go: id => { return axios.delete(url + '/' + id).then(res => res.data) }
    }
}

export const editFlightstatus = () => {
    let url = `${base}/flightstatus`
    return {
        name: 'editFlightstatus',
        type: defaultType,
        url: url,
        requetType: 'PUT',
        remark: '编辑航班进展状态编码记录',
        go: params => { return axios.put(url, params).then(res => res.data) }
    }
}

export const addFlightstatus = () => {
    let url = `${base}/flightstatus`
    return {
        name: 'addFlightstatus',
        type: defaultType,
        url: url,
        requetType: 'POST',
        remark: '添加航班进展状态编码记录',
        go: params => { return axios.post(url, params).then(res => res.data) }
    }
}

