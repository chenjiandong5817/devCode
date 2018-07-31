/*
* @Author: chenjiandong
* @Date:   last_modified_time
* @Last Modified by:   chenjiandong
* @Last Modified time: 2018-03-26 15:25:57
*/

import { axios, bus } from './../raiis-axios'

let defaultType = 'sellStation'
// BUS
export const getSellStationListPage = () => {
    let url = `${bus}/station/sellstation`
    return {
        name: 'getSellStationListPage',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '获取所有售票站点信息列表',
        go: params => { return axios.get(url, { params: params }).then(res => res.data) }
    }
}

export const editSellStation = () => {
    let url = `${bus}/station/updateSellStation`
    return {
        name: 'editSellStation',
        type: defaultType,
        url: url,
        requetType: 'PUT',
        remark: '编辑售票站点管理信息',
        go: params => { return axios.put(url, params).then(res => res.data) }
    }
}

export const getArrivalStation = () => {
    let url = `${bus}/station/arrivalStation`
    return {
        name: 'getArrivalStation',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '获取售票站点的到达站点信息',
        go: params => { return axios.get(url + '?sellStationId=' + params).then(res => res.data) }
    }
}

export const addArrivalStation = () => {
    let url = `${bus}/station/arrivalStation`
    return {
        name: 'addArrivalStation',
        type: defaultType,
        url: url,
        requetType: 'POST',
        remark: '添加售票站点的可达站点的信息',
        go: params => { return axios.post(url, params).then(res => res.data) }
    }
}
