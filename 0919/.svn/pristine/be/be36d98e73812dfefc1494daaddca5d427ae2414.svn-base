/*
* @Author: chenjiandong
* @Date:   last_modified_time
* @Last Modified by:   chenjiandong
* @Last Modified time: last_modified_time
*/
import {axios, base} from './../raiis-axios'

let defaultType = 'vips'

export const getVIPListPage = () => {
    let url = `${base}/vips`
    return {
        name: 'getVIPListPage',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '获取VIP信息列表',
        go: params => { return axios.get(url, { params: params }).then(res => res.data) }
    }
}

export const removeVIP = () => {
    let url = `${base}/vips`
    return {
        name: 'removeVIP',
        type: defaultType,
        url: url,
        requetType: 'DELETE',
        remark: '删除VIP信息',
        go: id => { return axios.delete(url + '/' + id).then(res => res.data) }
    }
}

export const editVIP = () => {
    let url = `${base}/vips`
    return {
        name: 'editVIP',
        type: defaultType,
        url: url,
        requetType: 'PUT',
        remark: '编辑VIP信息',
        go: params => { return axios.put(url, params).then(res => res.data) }
    }
}

export const addVIP = () => {
    let url = `${base}/vips`
    return {
        name: 'addVIP',
        type: defaultType,
        url: url,
        requetType: 'POST',
        remark: '添加VIP信息',
        go: params => { return axios.post(url, params).then(res => res.data) }
    }
}

