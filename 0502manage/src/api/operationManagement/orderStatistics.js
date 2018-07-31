/*
* @Author: chenjiandong
* @Date:   last_modified_time
* @Last Modified by:   chenjiandong
* @Last Modified time: 2018-06-26 17:59:24
*/

import { axios, bus } from './../raiis-axios'

let defaultType = 'orderStatistics'
// BUS
export const getPayOrderListPage = () => {
    let url = `${bus}/order/payOrder`
    return {
        name: 'getPayOrderListPage',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '获取已支付订单管理信息列表',
        go: params => { return axios.get(url, { params: params }).then(res => res.data) }
    }
}

export const getUnPayOrderListPage = () => {
    let url = `${bus}/order/unpayOrder`
    return {
        name: 'getUnPayOrderListPage',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '获取未支付订单管理信息列表',
        go: params => { return axios.get(url, { params: params }).then(res => res.data) }
    }
}

export const getQueryOrder = () => {
    let url = `${bus}/order/queryorder`
    return {
        name: 'getQueryOrder',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '查询订单详情管理信息',
        go: params => { return axios.get(url + '?orderid=' + params).then(res => res.data) }
    }
}

export const cancelUnorder = () => {
    let url = `${bus}/order/unorder`
    return {
        name: 'cancelUnorder',
        type: defaultType,
        url: url,
        requetType: 'POST',
        remark: '取消未支付订单',
        go: params => { return axios.post(url, params).then(res => res.data) }
    }
}

export const orderExportData = () => {
    let url = `${bus}/order/orderExport`
    return {
        name: 'orderExportData',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '订单导出数据',
        go: params => { return axios.get(url + '?beginTime=' + params.beginTime + '&endTime=' + params.endTime + '&orderType=' + params.orderType + '&payStatus=' + params.payStatus).then(res => res.data) }
    }
}

export const searchOrderData = () => {
    let url = `${bus}/order/order`
    return {
        name: 'searchOrderData',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '查询订单详细信息',
        go: params => { return axios.get(url, { params: params }).then(res => res.data) }
    }
}
