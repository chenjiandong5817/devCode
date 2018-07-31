/*
* @Author: chenjiandong
* @Date:   last_modified_time
* @Last Modified by:   chenjiandong
* @Last Modified time: last_modified_time
*/
import {axios, base} from './../raiis-axios'

let defaultType = 'resourcenatures'
export const getResourcenaturesListPage = () => {
    let url = `${base}/resourcenatures`
    return {
        name: 'getResourcenaturesListPage',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '查询资源属性信息',
        go: params => { return axios.get(url, { params: params }).then(res => res.data) }
    }
}

export const removeResourcenatures = () => {
    let url = `${base}/resourcenatures`
    return {
        name: 'removeResourcenatures',
        type: defaultType,
        url: url,
        requetType: 'DELETE',
        remark: '删除资源属性信息',
        go: id => { return axios.delete(url + '/' + id).then(res => res.data) }
    }
}

export const editResourcenatures = () => {
    let url = `${base}/resourcenatures`
    return {
        name: 'editResourcenatures',
        type: defaultType,
        url: url,
        requetType: 'PUT',
        remark: '更新资源属性信息',
        go: params => { return axios.put(url, params).then(res => res.data) }
    }
}

export const addResourcenatures = () => {
    let url = `${base}/resourcenatures`
    return {
        name: 'addResourcenatures',
        type: defaultType,
        url: url,
        requetType: 'POST',
        remark: '新增资源属性信息',
        go: params => { return axios.post(url, params).then(res => res.data) }
    }
}
