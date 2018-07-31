/*
 * @Author: ylj
 * @Date: 2017-11-02 11:08:59
 * @Last Modified by: ylj
 * @Last Modified time: 2017-11-02 16:45:52
 */
import {axios, base} from './../raiis-axios'

let defaultType = 'plan seasonnames'

export const getPlanSeasonNameLs = () => {
    let url = `${base}/seasonnames`
    return {
        name: 'getPlanSeasonNameLs',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '获取航季基础信息',
        go: params => { return axios.get(url, { params: params }).then(res => res.data) }
    }
}

export const addPlanSeasonName = () => {
    let url = `${base}/seasonnames`
    return {
        name: 'addPlanSeasonName',
        type: defaultType,
        url: url,
        requetType: 'POST',
        remark: '添加航季基础信息',
        go: params => { return axios.post(url, params).then(res => res.data) }
    }
}

export const editPlanSeasonName = () => {
    let url = `${base}/seasonnames`
    return {
        name: 'editPlanSeasonName',
        type: defaultType,
        url: url,
        requetType: 'PUT',
        remark: '编辑航季基础信息',
        go: params => { return axios.put(url, params).then(res => res.data) }
    }
}

export const removePlanSeasonName = () => {
    let url = `${base}/seasonnames`
    return {
        name: 'removePlanSeasonName',
        type: defaultType,
        url: url,
        requetType: 'DELETE',
        remark: '删除航季基础信息',
        go: id => { return axios.delete(url + '/' + id).then(res => res.data) }
    }
}
