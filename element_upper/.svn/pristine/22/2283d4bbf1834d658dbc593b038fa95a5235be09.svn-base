/*
* @Author: chenjiandong
* @Date:   last_modified_time
* @Last Modified by:   chenjiandong
* @Last Modified time: last_modified_time
*/
import {axios, base} from './../raiis-axios'

let defaultType = 'ckcounterservicetypes'
export const getCkcounterServiceTypesListPage = () => {
    let url = `${base}/ckcounterservicetypes`
    return {
        name: 'getCkcounterServiceTypesListPage',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '查询柜台服务类型信息',
        go: params => { return axios.get(url, { params: params }).then(res => res.data) }
    }
}

export const removeCkcounterServiceTypes = () => {
    let url = `${base}/ckcounterservicetypes`
    return {
        name: 'removeCkcounterServiceTypes',
        type: defaultType,
        url: url,
        requetType: 'DELETE',
        remark: '删除柜台服务类型',
        go: id => { return axios.delete(url + '/' + id).then(res => res.data) }
    }
}

export const editCkcounterServiceTypes = () => {
    let url = `${base}/ckcounterservicetypes`
    return {
        name: 'editCkcounterServiceTypes',
        type: defaultType,
        url: url,
        requetType: 'PUT',
        remark: '更新柜台服务类型',
        go: params => { return axios.put(url, params).then(res => res.data) }
    }
}

export const addCkcounterServiceTypes = () => {
    let url = `${base}/ckcounterservicetypes`
    return {
        name: 'addCkcounterServiceTypes',
        type: defaultType,
        url: url,
        requetType: 'POST',
        remark: '新增柜台服务类型',
        go: params => { return axios.post(url, params).then(res => res.data) }
    }
}
