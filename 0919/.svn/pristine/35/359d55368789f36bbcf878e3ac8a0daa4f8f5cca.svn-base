/*
* @Author: chenjiandong
* @Date:   last_modified_time
* @Last Modified by:   chenjiandong
* @Last Modified time: last_modified_time
*/
import {axios, base} from './../raiis-axios'

let defaultType = 'generalagents'

export const getGeneralagentsListPage = () => {
    let url = `${base}/generalagents`
    return {
        name: 'getGeneralagentsListPage',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '获取代理人代码管理信息列表',
        go: params => { return axios.get(url, { params: params }).then(res => res.data) }
    }
}

export const removeGeneralagents = () => {
    let url = `${base}/generalagents`
    return {
        name: 'removeGeneralagents',
        type: defaultType,
        url: url,
        requetType: 'DELETE',
        remark: '删除代理人代码管理信息',
        go: id => { return axios.delete(url + '/' + id).then(res => res.data) }
    }
}

export const editGeneralagents = () => {
    let url = `${base}/generalagents`
    return {
        name: 'editGeneralagents',
        type: defaultType,
        url: url,
        requetType: 'PUT',
        remark: '编辑代理人代码管理信息',
        go: params => { return axios.put(url, params).then(res => res.data) }
    }
}

export const addGeneralagents = () => {
    let url = `${base}/generalagents`
    return {
        name: 'addGeneralagents',
        type: defaultType,
        url: url,
        requetType: 'POST',
        remark: '添加代理人代码管理信息',
        go: params => { return axios.post(url, params).then(res => res.data) }
    }
}

