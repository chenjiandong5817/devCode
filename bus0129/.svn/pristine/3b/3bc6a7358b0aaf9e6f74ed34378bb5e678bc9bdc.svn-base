import {axios, base} from './../raiis-axios'

let defaultType = 'checkincounters'

export const getCheckincountersListPage = () => {
    let url = `${base}/checkincounters`
    return {
        name: 'getCheckincountersListPage',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '获取值机柜台信息列表',
        go: params => { return axios.get(url, { params: params }).then(res => res.data) }
    }
}

export const removeCheckincounters = () => {
    let url = `${base}/checkincounters`
    return {
        name: 'removeCheckincounters',
        type: defaultType,
        url: url,
        requetType: 'DELETE',
        remark: '删除值机柜台信息',
        go: id => { return axios.delete(url + '/' + id).then(res => res.data) }
    }
}

export const editCheckincounters = () => {
    let url = `${base}/checkincounters`
    return {
        name: 'editCheckincounters',
        type: defaultType,
        url: url,
        requetType: 'PUT',
        remark: '编辑值机柜台信息',
        go: params => { return axios.put(url, params).then(res => res.data) }
    }
}

export const addCheckincounters = () => {
    let url = `${base}/checkincounters`
    return {
        name: 'addCheckincounters',
        type: defaultType,
        url: url,
        requetType: 'POST',
        remark: '添加值机柜台信息',
        go: params => { return axios.post(url, params).then(res => res.data) }
    }
}

export const getCkcounterservicetypesListPage = () => {
    let url = `${base}/ckcounterservicetypes`
    return {
        name: 'getCkcounterservicetypesListPage',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '获取值机柜台服务类型',
        go: params => { return axios.get(url, { params: params }).then(res => res.data) }
    }
}

export const getCkcounteropmodesListPage = () => {
    let url = `${base}/ckcounteropmodes`
    return {
        name: 'getCkcounteropmodesListPage',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '获取值机柜台分配模式',
        go: params => { return axios.get(url, { params: params }).then(res => res.data) }
    }
}

// 获取值机柜台值机岛编码
export const checkingroups = () => {
    let url = `${base}/checkincounters/checkingroups`
    return {
        name: 'checkingroups',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '获取值机柜台值机岛编码',
        go: params => { return axios.get(url, { params: params }).then(res => res.data) }
    }
}
