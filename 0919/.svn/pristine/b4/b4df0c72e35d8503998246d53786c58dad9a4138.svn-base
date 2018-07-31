/*
* @Author: chenjiandong
* @Date:   last_modified_time
* @Last Modified by:   chenjiandong
* @Last Modified time: last_modified_time
*/
import {axios, base} from './../raiis-axios'

let defaultType = 'irregularcodes'

export const getIrregularcodesListPage = () => {
    let url = `${base}/irregularcodes`
    return {
        name: 'getIrregularcodesListPage',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '获取异常代码信息列表',
        go: params => { return axios.get(url, { params: params }).then(res => res.data) }
    }
}

export const removeIrregularcodes = () => {
    let url = `${base}/irregularcodes`
    return {
        name: 'removeIrregularcodes',
        type: defaultType,
        url: url,
        requetType: 'DELETE',
        remark: '删除异常代码信息',
        go: id => { return axios.delete(url + '/' + id).then(res => res.data) }
    }
}

export const editIrregularcodes = () => {
    let url = `${base}/irregularcodes`
    return {
        name: 'editIrregularcodes',
        type: defaultType,
        url: url,
        requetType: 'PUT',
        remark: '编辑异常代码信息',
        go: params => { return axios.put(url, params).then(res => res.data) }
    }
}

export const addIrregularcodes = () => {
    let url = `${base}/irregularcodes`
    return {
        name: 'addIrregularcodes',
        type: defaultType,
        url: url,
        requetType: 'POST',
        remark: '添加异常代码信息',
        go: params => { return axios.post(url, params).then(res => res.data) }
    }
}

