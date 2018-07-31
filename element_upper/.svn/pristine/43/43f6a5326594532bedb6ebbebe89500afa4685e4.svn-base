/*
* @Author: chenjiandong
* @Date:   last_modified_time
* @Last Modified by:   chenjiandong
* @Last Modified time: last_modified_time
*/
import {axios, base} from './../raiis-axios'

let defaultType = 'ckcounteropmodes'
export const getCkcounteropmodesListPage = () => {
    let url = `${base}/ckcounteropmodes`
    return {
        name: 'getCkcounteropmodesListPage',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '查询柜台分配模式信息',
        go: params => { return axios.get(url, { params: params }).then(res => res.data) }
    }
}

export const removeCkcounteropmodes = () => {
    let url = `${base}/ckcounteropmodes`
    return {
        name: 'removeCkcounteropmodes',
        type: defaultType,
        url: url,
        requetType: 'DELETE',
        remark: '删除柜台分配模式',
        go: id => { return axios.delete(url + '/' + id).then(res => res.data) }
    }
}

export const editCkcounteropmodes = () => {
    let url = `${base}/ckcounteropmodes`
    return {
        name: 'editCkcounteropmodes',
        type: defaultType,
        url: url,
        requetType: 'PUT',
        remark: '更新柜台分配模式',
        go: params => { return axios.put(url, params).then(res => res.data) }
    }
}

export const addCkcounteropmodes = () => {
    let url = `${base}/ckcounteropmodes`
    return {
        name: 'addCkcounteropmodes',
        type: defaultType,
        url: url,
        requetType: 'POST',
        remark: '新增柜台分配模式',
        go: params => { return axios.post(url, params).then(res => res.data) }
    }
}
