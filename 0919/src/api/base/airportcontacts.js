/*
* @Author: chenjiandong
* @Date:   last_modified_time
* @Last Modified by:   chenjiandong
* @Last Modified time: last_modified_time
*/
import {axios, base} from './../raiis-axios'

let defaultType = 'airportcontacts'
export const getAirportContactsListPage = () => {
    let url = `${base}/airportcontacts`
    return {
        name: 'getAirportContactsListPage',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '查询机场联系人',
        go: params => { return axios.get(url, { params: params }).then(res => res.data) }
    }
}

export const removeAirportContacts = () => {
    let url = `${base}/airportcontacts`
    return {
        name: 'removeAirportContacts',
        type: defaultType,
        url: url,
        requetType: 'DELETE',
        remark: '删除机场联系人',
        go: id => { return axios.delete(url + '/' + id).then(res => res.data) }
    }
}

export const editAirportContacts = () => {
    let url = `${base}/airportcontacts`
    return {
        name: 'editAirportContacts',
        type: defaultType,
        url: url,
        requetType: 'PUT',
        remark: '更新机场联系人',
        go: params => { return axios.put(url, params).then(res => res.data) }
    }
}

export const addAirportContacts = () => {
    let url = `${base}/airportcontacts`
    return {
        name: 'addAirportContacts',
        type: defaultType,
        url: url,
        requetType: 'POST',
        remark: '新增机场联系人',
        go: params => { return axios.post(url, params).then(res => res.data) }
    }
}
