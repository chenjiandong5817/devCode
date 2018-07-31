/*
* @Author: chenjiandong
* @Date:   last_modified_time
* @Last Modified by:   chenjiandong
* @Last Modified time: last_modified_time
*/
import {axios, base} from './../raiis-axios'

let defaultType = 'gates'

export const getGateListPage = () => {
    let url = `${base}/gates`
    return {
        name: 'getGateListPage',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '获取登机口列表',
        go: params => { return axios.get(url, { params: params }).then(res => res.data) }
    }
}

export const removeGate = () => {
    let url = `${base}/gates`
    return {
        name: 'removeGate',
        type: defaultType,
        url: url,
        requetType: 'DELETE',
        remark: '删除登机口',
        go: id => { return axios.delete(url + '/' + id).then(res => res.data) }
    }
}

export const editGate = () => {
    let url = `${base}/gates`
    return {
        name: 'editGate',
        type: defaultType,
        url: url,
        requetType: 'PUT',
        remark: '编辑登机口',
        go: params => { return axios.put(url, params).then(res => res.data) }
    }
}

export const addGate = () => {
    let url = `${base}/gates`
    return {
        name: 'addGate',
        type: defaultType,
        url: url,
        requetType: 'POST',
        remark: '添加登机口',
        go: params => { return axios.post(url, params).then(res => res.data) }
    }
}

// 获取航站楼列表 先写在这边
export const getTerminalListPage = () => {
    let url = `${base}/terminals`
    return {
        name: 'getTerminalListPage',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '获取航站楼列表',
        go: params => { return axios.get(url, { params: params }).then(res => res.data) }
    }
}

// 候机区的请求地址
export const getWaitingHallListPage = () => {
    let url = `${base}/waitinghalls`
    return {
        name: 'getTerminalListPage',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '获取候机区列表',
        go: params => { return axios.get(url, { params: params }).then(res => res.data) }
    }
}
