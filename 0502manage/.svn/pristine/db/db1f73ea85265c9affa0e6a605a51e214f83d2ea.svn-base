/*
* @Author: chenjiandong
* @Date:   last_modified_time
* @Last Modified by:   chenjiandong
* @Last Modified time: 2018-03-13 08:58:47
*/

import { axios, bus } from './../raiis-axios'

let defaultType = 'desClassify'
// BUS
export const getDesClassifyListPage = () => {
    let url = `${bus}/desclassify`
    return {
        name: 'getDesClassifyListPage',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '获取目的地分类管理信息列表',
        go: params => { return axios.get(url, { params: params }).then(res => res.data) }
    }
}

export const removeDesClassify = () => {
    let url = `${bus}/desclassify`
    return {
        name: 'removeDesClassify',
        type: defaultType,
        url: url,
        requetType: 'DELETE',
        remark: '删除目的地分类管理信息',
        go: id => { return axios.delete(url + '?id=' + id).then(res => res.data) }
    }
}

export const editDesClassify = () => {
    let url = `${bus}/desclassify`
    return {
        name: 'editDesClassify',
        type: defaultType,
        url: url,
        requetType: 'PUT',
        remark: '编辑目的地分类管理信息',
        go: params => { return axios.put(url, params).then(res => res.data) }
    }
}

export const addDesClassify = () => {
    let url = `${bus}/desclassify`
    return {
        name: 'addDesClassify',
        type: defaultType,
        url: url,
        requetType: 'POST',
        remark: '添加目的地分类管理信息',
        go: params => { return axios.post(url, params).then(res => res.data) }
    }
}
