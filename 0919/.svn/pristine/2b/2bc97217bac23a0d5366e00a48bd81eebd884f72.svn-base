/*
* @Author: chenjiandong
* @Date:   last_modified_time
* @Last Modified by:   chenjiandong
* @Last Modified time: last_modified_time
*/
import {axios, base} from './../raiis-axios'

let defaultType = 'flightnatures'
export const getFlightNaturesListPage = () => {
    let url = `${base}/flightnatures`
    return {
        name: 'getFlightNaturesListPage',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '查询航班属性信息',
        go: params => { return axios.get(url, { params: params }).then(res => res.data) }
    }
}

export const removeFlightNatures = () => {
    let url = `${base}/flightnatures`
    return {
        name: 'removeFlightNatures',
        type: defaultType,
        url: url,
        requetType: 'DELETE',
        remark: '删除航班属性信息',
        go: id => { return axios.delete(url + '/' + id).then(res => res.data) }
    }
}

export const editFlightNatures = () => {
    let url = `${base}/flightnatures`
    return {
        name: 'editFlightNatures',
        type: defaultType,
        url: url,
        requetType: 'PUT',
        remark: '更新航班属性信息',
        go: params => { return axios.put(url, params).then(res => res.data) }
    }
}

export const addFlightNatures = () => {
    let url = `${base}/flightnatures`
    return {
        name: 'addFlightNatures',
        type: defaultType,
        url: url,
        requetType: 'POST',
        remark: '新增航班属性信息',
        go: params => { return axios.post(url, params).then(res => res.data) }
    }
}
