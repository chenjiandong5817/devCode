/*
* @Author: chenjiandong
* @Date:   last_modified_time
* @Last Modified by:   chenjiandong
* @Last Modified time: last_modified_time
*/
import {axios, base} from './../raiis-axios'

let defaultType = 'resourcestatus'
export const getResourcestatusListPage = () => {
    let url = `${base}/resourcestatus`
    return {
        name: 'getResourcestatusListPage',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '查询资源状态',
        go: params => { return axios.get(url, { params: params }).then(res => res.data) }
    }
}

export const removeResourcestatus = () => {
    let url = `${base}/resourcestatus`
    return {
        name: 'removeResourcestatus',
        type: defaultType,
        url: url,
        requetType: 'DELETE',
        remark: '删除资源状态',
        go: id => { return axios.delete(url + '/' + id).then(res => res.data) }
    }
}

export const editResourcestatus = () => {
    let url = `${base}/resourcestatus`
    return {
        name: 'editResourcestatus',
        type: defaultType,
        url: url,
        requetType: 'PUT',
        remark: '更新资源状态',
        go: params => { return axios.put(url, params).then(res => res.data) }
    }
}

export const addResourcestatus = () => {
    let url = `${base}/resourcestatus`
    return {
        name: 'addResourcestatus',
        type: defaultType,
        url: url,
        requetType: 'POST',
        remark: '新增资源状态',
        go: params => { return axios.post(url, params).then(res => res.data) }
    }
}
