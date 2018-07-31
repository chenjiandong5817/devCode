/*
* @Author: chenjiandong
* @Date:   last_modified_time
* @Last Modified by:   chenjiandong
* @Last Modified time: last_modified_time
*/
import {axios, base} from './../raiis-axios'

let defaultType = 'schedulesources'
export const getSchedulesourcesListPage = () => {
    let url = `${base}/schedulesources`
    return {
        name: 'getSchedulesourcesListPage',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '查询计划类型管理',
        go: params => { return axios.get(url, { params: params }).then(res => res.data) }
    }
}

export const removeSchedulesources = () => {
    let url = `${base}/schedulesources`
    return {
        name: 'removeSchedulesources',
        type: defaultType,
        url: url,
        requetType: 'DELETE',
        remark: '删除计划类型管理',
        go: id => { return axios.delete(url + '/' + id).then(res => res.data) }
    }
}

export const editSchedulesources = () => {
    let url = `${base}/schedulesources`
    return {
        name: 'editSchedulesources',
        type: defaultType,
        url: url,
        requetType: 'PUT',
        remark: '更新计划类型管理',
        go: params => { return axios.put(url, params).then(res => res.data) }
    }
}

export const addSchedulesources = () => {
    let url = `${base}/schedulesources`
    return {
        name: 'addSchedulesources',
        type: defaultType,
        url: url,
        requetType: 'POST',
        remark: '新增计划类型管理',
        go: params => { return axios.post(url, params).then(res => res.data) }
    }
}
