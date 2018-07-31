/*
* @Author: chenjiandong
* @Date:   last_modified_time
* @Last Modified by:   chenjiandong
* @Last Modified time: last_modified_time
*/
import {axios, base} from './../raiis-axios'

let defaultType = 'aircraftregistrations'

export const getAircraftregistrationsListPage = () => {
    let url = `${base}/aircraftregistrations`
    return {
        name: 'getAircraftregistrationsListPage',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '获取机号信息列表',
        go: params => { return axios.get(url, { params: params }).then(res => res.data) }
    }
}

export const removeAircraftregistrations = () => {
    let url = `${base}/aircraftregistrations`
    return {
        name: 'removeAircraftregistrations',
        type: defaultType,
        url: url,
        requetType: 'DELETE',
        remark: '删除机号信息',
        go: id => { return axios.delete(url + '/' + id).then(res => res.data) }
    }
}

export const editAircraftregistrations = () => {
    let url = `${base}/aircraftregistrations`
    return {
        name: 'editAircraftregistrations',
        type: defaultType,
        url: url,
        requetType: 'PUT',
        remark: '编辑机号信息',
        go: params => { return axios.put(url, params).then(res => res.data) }
    }
}

export const addAircraftregistrations = () => {
    let url = `${base}/aircraftregistrations`
    return {
        name: 'addAircraftregistrations',
        type: defaultType,
        url: url,
        requetType: 'POST',
        remark: '添加机号信息',
        go: params => { return axios.post(url, params).then(res => res.data) }
    }
}

