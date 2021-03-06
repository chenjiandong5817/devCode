/*
* @Author: chenjiandong
* @Date:   last_modified_time
* @Last Modified by:   chenjiandong
* @Last Modified time: 2018-04-08 10:54:34
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

// export const uploadPicture = () => {
//     let url = `${bus}/ad/picture`
//     return {
//         name: 'uploadPicture',
//         type: defaultType,
//         url: url,
//         requetType: 'POST',
//         remark: '上传广告的图片',
//         go: params => { return axios.post(url, params).then(res => res.data) }
//     }
// }

export function uploadPicture (data) {
    return axios({
        method: 'post',
        url: `${bus}/ad/picture`,
        data: data
    })
}
