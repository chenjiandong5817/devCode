/*
* @Author: chenjiandong
* @Date:   last_modified_time
* @Last Modified by:   chenjiandong
* @Last Modified time: 2018-06-15 14:38:41
*/

import { axios, bus } from './../raiis-axios'

let defaultType = 'adEdit'
// BUS
export const getAdContent = () => {
    let url = `${bus}/ad`
    return {
        name: 'getAdContent',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '获取广告的内容',
        go: params => { return axios.get(url + '?id=' + params).then(res => res.data) }
    }
}

export const editAdContent = () => {
    let url = `${bus}/ad`
    return {
        name: 'editAdContent',
        type: defaultType,
        url: url,
        requetType: 'PUT',
        remark: '编辑广告的内容',
        go: params => { return axios.put(url, params).then(res => res.data) }
    }
}

export const getAdPicture = () => {
    let url = `${bus}/ad/picture`
    return {
        name: 'getAdPicture',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '获取广告的图片',
        go: params => { return axios.get(url + '?id=' + params).then(res => res.data) }
    }
}

export function uploadPicture (data) {
    return axios({
        method: 'post',
        url: `${bus}/ad/picture`,
        data: data
    })
}

// 新版广告位组别
export const getAdGroup = () => {
    let url = `${bus}/ads/adGroup`
    return {
        name: 'getAdGroup',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '获取所有广告组',
        go: params => { return axios.get(url + '?type=' + params).then(res => res.data) }
    }
}

export const removeAdGroup = () => {
    let url = `${bus}/ads/adGroup`
    return {
        name: 'removeAdGroup',
        type: defaultType,
        url: url,
        requetType: 'DELETE',
        remark: '删除广告组',
        go: id => { return axios.delete(url + '?id=' + id).then(res => res.data) }
    }
}

export const addAdGroup = () => {
    let url = `${bus}/ads/adGroup`
    return {
        name: 'addAdGroup',
        type: defaultType,
        url: url,
        requetType: 'POST',
        remark: '添加广告组',
        go: params => { return axios.post(url, params).then(res => res.data) }
    }
}

// 新版广告位图片
export const getNewAdPicture = () => {
    let url = `${bus}/ads/picture`
    return {
        name: 'getNewAdPicture',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '获取该组下的各个URL',
        go: params => { return axios.get(url + '?groupid=' + params).then(res => res.data) }
    }
}

export const getNewAdPictureImg = () => {
    let url = `${bus}/ads/picture/image`
    return {
        name: 'getNewAdPictureImg',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '获取该组下的所有图片',
        go: params => { return axios.get(url + '?url=' + params).then(res => res.data) }
    }
}

export const removeNewAdPicture = () => {
    let url = `${bus}/ads/picture`
    return {
        name: 'removeNewAdPicture',
        type: defaultType,
        url: url,
        requetType: 'DELETE',
        remark: '删除该组下的某个图片',
        go: id => { return axios.delete(url + '?id=' + id).then(res => res.data) }
    }
}

// export function uploadNewAdPicture (data) {
//     return axios({
//         method: 'post',
//         url: `${bus}/ads/picture`,
//         data: data
//     })
// }

export const uploadNewAdPicture = () => {
    let url = `${bus}/ads/picture`
    return {
        name: 'uploadNewAdPicture',
        type: defaultType,
        url: url,
        requetType: 'POST',
        remark: '上传图片',
        go: params => { return axios.post(url, params).then(res => res.data) }
    }
}

// 新版广告位设备

export const removeNewAdDevice = () => {
    let url = `${bus}/ads/device`
    return {
        name: 'removeNewAdDevice',
        type: defaultType,
        url: url,
        requetType: 'DELETE',
        remark: '删除广告设备',
        go: id => { return axios.delete(url + '?id=' + id).then(res => res.data) }
    }
}

export const getNewAdDevice = () => {
    let url = `${bus}/ads/device`
    return {
        name: 'getNewAdDevice',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '获取该组下的设备',
        go: params => { return axios.get(url + '?type=' + params.type + '&num=' + params.num).then(res => res.data) }
    }
}

export const addNewAdDevice = () => {
    let url = `${bus}/ads/device`
    return {
        name: 'addNewAdDevice',
        type: defaultType,
        url: url,
        requetType: 'POST',
        remark: '添加设备',
        go: params => { return axios.post(url, params).then(res => res.data) }
    }
}

export const getNewAdDeviceCount = () => {
    let url = `${bus}/ads/device/count`
    return {
        name: 'getNewAdDeviceCount',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '获取该组下的设备数量',
        go: params => { return axios.get(url + '?type=' + params.type + '&num=' + params.num).then(res => res.data) }
    }
}

export const getNewAdNoDevice = () => {
    let url = `${bus}/ads/device/notInGroup`
    return {
        name: 'getNewAdNoDevice',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '获取不是该组下的设备',
        go: params => { return axios.get(url + '?type=' + params.type + '&num=' + params.num).then(res => res.data) }
    }
}

// 广告标语
export const getAllAdContent = () => {
    let url = `${bus}/ads/adpay`
    return {
        name: 'getAllAdContent',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '获取所有广告的标语',
        go: groupid => { return axios.get(url + '?groupid=' + groupid).then(res => res.data) }
    }
}

export const removeAllAdContent = () => {
    let url = `${bus}/ads/adpay`
    return {
        name: 'removeAllAdContent',
        type: defaultType,
        url: url,
        requetType: 'DELETE',
        remark: '删除所有广告语',
        go: groupid => { return axios.delete(url + '?groupid=' + groupid).then(res => res.data) }
    }
}

export const addAllAdContent = () => {
    let url = `${bus}/ads/adpay`
    return {
        name: 'addAllAdContent',
        type: defaultType,
        url: url,
        requetType: 'POST',
        remark: '添加所有广告语',
        go: params => { return axios.post(url, params).then(res => res.data) }
    }
}

export const editAllAdContent = () => {
    let url = `${bus}/ads/adpay`
    return {
        name: 'editAllAdContent',
        type: defaultType,
        url: url,
        requetType: 'PUT',
        remark: '修改所有广告语',
        go: params => { return axios.put(url, params).then(res => res.data) }
    }
}
