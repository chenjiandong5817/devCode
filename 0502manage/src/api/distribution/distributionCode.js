/*
* @Author: cdroid
* @Date:   2018-07-03 15:03:39
* @Last Modified by:   chenjiandong
* @Last Modified time: 2018-07-04 10:19:35
*/
import { axios, bus } from './../raiis-axios'

let defaultType = 'distributionCode'

export const getDistributionCode = () => {
    let url = `${bus}/bus`
    return {
        name: 'getDistributionCode',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '获取分销二维码',
        go: params => { return axios.get(url + '?id=' + params).then(res => res.data) }
    }
}

export const removeDistributionCode = () => {
    let url = `${bus}/bus`
    return {
        name: 'removeDistributionCode',
        type: defaultType,
        url: url,
        requetType: 'DELETE',
        remark: '删除分销二维码',
        go: id => { return axios.delete(url + '?id=' + id).then(res => res.data) }
    }
}

export const addDistributionCode = () => {
    let url = `${bus}/bus`
    return {
        name: 'addDistributionCode',
        type: defaultType,
        url: url,
        requetType: 'POST',
        remark: '新增分销二维码',
        go: params => { return axios.post(url, params).then(res => res.data) }
    }
}
