/*
* @Author: chenjiandong
* @Date:   last_modified_time
* @Last Modified by:   chenjiandong
* @Last Modified time: 2018-03-22 14:59:48
*/

import { axios, bus } from './../raiis-axios'

let defaultType = 'remoteControl'
// BUS
export const getAllDevicesListPage = () => {
    let url = `${bus}/devices/all`
    return {
        name: 'getAllDevicesListPage',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '获取所有设备信息列表',
        go: params => { return axios.get(url, { params: params }).then(res => res.data) }
    }
}

export const editDevice = () => {
    let url = `${bus}/devices`
    return {
        name: 'editDevice',
        type: defaultType,
        url: url,
        requetType: 'PUT',
        remark: '编辑设备管理信息',
        go: params => { return axios.put(url, params).then(res => res.data) }
    }
}

export const addDevice = () => {
    let url = `${bus}/devices`
    return {
        name: 'addDevice',
        type: defaultType,
        url: url,
        requetType: 'POST',
        remark: '新增设备管理信息',
        go: params => { return axios.post(url, params).then(res => res.data) }
    }
}

export const maintainDevice = () => {
    let url = `${bus}/devices/maintain`
    return {
        name: 'maintainDevice',
        type: defaultType,
        url: url,
        requetType: 'POST',
        remark: '维修设备',
        go: params => { return axios.post(url, params).then(res => res.data) }
    }
}

export const startDevice = () => {
    let url = `${bus}/devices/start`
    return {
        name: 'startDevice',
        type: defaultType,
        url: url,
        requetType: 'POST',
        remark: '开启设备',
        go: params => { return axios.post(url, params).then(res => res.data) }
    }
}

export const stopDevice = () => {
    let url = `${bus}/devices/stop`
    return {
        name: 'stopDevice',
        type: defaultType,
        url: url,
        requetType: 'POST',
        remark: '停机设备',
        go: params => { return axios.post(url, params).then(res => res.data) }
    }
}

export const devicesRun = () => {
    let url = `${bus}/devices/run`
    return {
        name: 'devicesRun',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '设备正常运行台数',
        go: params => { return axios.get(url).then(res => res.data) }
    }
}

export const devicesSuspend = () => {
    let url = `${bus}/devices/suspend`
    return {
        name: 'devicesSuspend',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '暂停服务台数',
        go: params => { return axios.get(url).then(res => res.data) }
    }
}

export const queryOrder = () => {
    let url = `${bus}/order/queryorder`
    return {
        name: 'queryOrder',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '查询订单详情',
        go: params => { return axios.get(url + '?orderid=' + params).then(res => res.data) }
    }
}

export const queryAllOrder = () => {
    let url = `${bus}/order/allOrder`
    return {
        name: 'queryAllOrder',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '获得订单统计',
        go: params => { return axios.get(url + '?dateType=' + params).then(res => res.data) }
    }
}
