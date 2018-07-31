/*
* @Author: chenjiandong
* @Date:   last_modified_time
* @Last Modified by:   chenjiandong
* @Last Modified time: last_modified_time
*/
import {axios, base} from './../raiis-axios'

let defaultType = 'terminals'

export const getTerminalsListPage = () => {
    let url = `${base}/terminals`
    return {
        name: 'getTerminalsListPage',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '获取候机楼信息列表',
        go: params => { return axios.get(url, { params: params }).then(res => res.data) }
    }
}

export const removeTerminals = () => {
    let url = `${base}/terminals`
    return {
        name: 'removeTerminals',
        type: defaultType,
        url: url,
        requetType: 'DELETE',
        remark: '删除候机楼信息',
        go: id => { return axios.delete(url + '/' + id).then(res => res.data) }
    }
}

export const editTerminals = () => {
    let url = `${base}/terminals`
    return {
        name: 'editTerminals',
        type: defaultType,
        url: url,
        requetType: 'PUT',
        remark: '编辑候机楼信息',
        go: params => { return axios.put(url, params).then(res => res.data) }
    }
}

export const addTerminals = () => {
    let url = `${base}/terminals`
    return {
        name: 'addTerminals',
        type: defaultType,
        url: url,
        requetType: 'POST',
        remark: '添加候机楼信息',
        go: params => { return axios.post(url, params).then(res => res.data) }
    }
}

