import {axios, base} from './../raiis-axios'

let defaultType = 'aiis airport'
// Airport
// export const getAirportListPage = params => { return axios.get(`${base}/aiisAirport/query`, { params: params }).then(res => res.data) }
export const getAiisAirportListPage = () => {
    let url = `${base}/aiisairports`
    return {
        name: 'getAiisAirportListPage',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '获取运营机场列表',
        go: params => { return axios.get(url, { params: params }).then(res => res.data) }
    }
}

// export const removeAirport = params => { return axios.post(`${base}/aiisAirport/delete`, params) }
export const removeAiisAirport = () => {
    let url = `${base}/aiisairports`
    return {
        name: 'removeAiisAirport',
        type: defaultType,
        url: url,
        requetType: 'DELETE',
        remark: '删除运营机场',
        go: id => { return axios.delete(url + '/' + id).then(res => res.data) }
    }
}

// export const editAirport = params => { return axios.post(`${base}/aiisAirport/update`, params) }
export const editAiisAirport = () => {
    let url = `${base}/aiisairports`
    return {
        name: 'editAiisAirport',
        type: defaultType,
        url: url,
        requetType: 'PUT',
        remark: '编辑运营机场',
        go: params => { return axios.put(url, params).then(res => res.data) }
    }
}

// export const addAirport = params => { return axios.post(`${base}/aiisAirport/add`, params) }
export const addAiisAirport = () => {
    let url = `${base}/aiisairports`
    return {
        name: 'addAiisAirport',
        type: defaultType,
        url: url,
        requetType: 'POST',
        remark: '添加运营机场',
        go: params => { return axios.post(url, params).then(res => res.data) }
    }
}
