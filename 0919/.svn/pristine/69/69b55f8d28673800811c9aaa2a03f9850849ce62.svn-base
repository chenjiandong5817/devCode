/*
* @Author: chenjiandong
* @Date:   last_modified_time
* @Last Modified by:   chenjiandong
* @Last Modified time: last_modified_time
*/
import {axios, base} from './../raiis-axios'

let defaultType = 'enuminfos'
export const getEnumInfoListPage = () => {
    let url = `${base}/enuminfos`
    return {
        name: 'getEnumInfoListPage',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '查询枚举信息',
        go: params => { return axios.get(url, { params: params }).then(res => res.data) }
    }
}

export const removeEnumInfo = () => {
    let url = `${base}/enuminfos`
    return {
        name: 'removeEnumInfo',
        type: defaultType,
        url: url,
        requetType: 'DELETE',
        remark: '删除枚举信息',
        go: id => { return axios.delete(url + '/' + id).then(res => res.data) }
    }
}

export const editEnumInfo = () => {
    let url = `${base}/enuminfos`
    return {
        name: 'editEnumInfo',
        type: defaultType,
        url: url,
        requetType: 'PUT',
        remark: '更新枚举信息',
        go: params => { return axios.put(url, params).then(res => res.data) }
    }
}

export const addEnumInfo = () => {
    let url = `${base}/enuminfos`
    return {
        name: 'addEnumInfo',
        type: defaultType,
        url: url,
        requetType: 'POST',
        remark: '新增枚举信息',
        go: params => { return axios.post(url, params).then(res => res.data) }
    }
}
