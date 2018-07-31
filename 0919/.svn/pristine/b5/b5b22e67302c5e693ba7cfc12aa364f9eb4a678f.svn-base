/*
* @Author: chenjiandong
* @Date:   last_modified_time
* @Last Modified by:   chenjiandong
* @Last Modified time: last_modified_time
*/
import {axios, base} from './../raiis-axios'

let defaultType = 'vipranks'
export const getViprankListPage = () => {
    let url = `${base}/vipranks`
    return {
        name: 'getViprankListPage',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '查询VIP类型管理',
        go: params => { return axios.get(url, { params: params }).then(res => res.data) }
    }
}

export const removeViprank = () => {
    let url = `${base}/vipranks`
    return {
        name: 'removeViprank',
        type: defaultType,
        url: url,
        requetType: 'DELETE',
        remark: '删除VIP类型管理',
        go: id => { return axios.delete(url + '/' + id).then(res => res.data) }
    }
}

export const editViprank = () => {
    let url = `${base}/vipranks`
    return {
        name: 'editViprank',
        type: defaultType,
        url: url,
        requetType: 'PUT',
        remark: '更新VIP类型管理',
        go: params => { return axios.put(url, params).then(res => res.data) }
    }
}

export const addViprank = () => {
    let url = `${base}/vipranks`
    return {
        name: 'addViprank',
        type: defaultType,
        url: url,
        requetType: 'POST',
        remark: '新增VIP类型管理',
        go: params => { return axios.post(url, params).then(res => res.data) }
    }
}
