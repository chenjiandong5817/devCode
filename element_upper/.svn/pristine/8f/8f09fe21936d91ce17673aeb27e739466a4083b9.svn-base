/*
* @Author: chenjiandong
* @Date:   last_modified_time
* @Last Modified by:   chenjiandong
* @Last Modified time: last_modified_time
*/
import {axios, base} from './../raiis-axios'

let defaultType = 'destLang'
export const getDestLangListPage = () => {
    let url = `${base}/destLang`
    return {
        name: 'getDestLangListPage',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '查询目的地语言信息列表',
        go: params => { return axios.get(url, { params: params }).then(res => res.data) }
    }
}

export const removeDestLang = () => {
    let url = `${base}/destLang`
    return {
        name: 'removeDestLang',
        type: defaultType,
        url: url,
        requetType: 'DELETE',
        remark: '删除目的地语言信息',
        go: id => { return axios.delete(url + '/' + id).then(res => res.data) }
    }
}

export const editDestLang = () => {
    let url = `${base}/destLang`
    return {
        name: 'editDestLang',
        type: defaultType,
        url: url,
        requetType: 'PUT',
        remark: '更新目的地语言信息',
        go: params => { return axios.put(url, params).then(res => res.data) }
    }
}

export const addDestLang = () => {
    let url = `${base}/destLang`
    return {
        name: 'addDestLang',
        type: defaultType,
        url: url,
        requetType: 'POST',
        remark: '新增目的地语言信息',
        go: params => { return axios.post(url, params).then(res => res.data) }
    }
}
