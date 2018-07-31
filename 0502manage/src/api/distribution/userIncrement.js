/*
* @Author: cdroid
* @Date:   2018-07-03 15:04:10
* @Last Modified by:   chenjiandong
* @Last Modified time: 2018-07-05 10:12:06
*/
import { axios, bus } from './../raiis-axios'

let defaultType = 'userIncrement'

export const getUserIncrement = () => {
    let url = `${bus}/bus`
    return {
        name: 'getUserIncrement',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '获取用户增量',
        go: params => { return axios.get(url + '?id=' + params).then(res => res.data) }
    }
}

export const removeUserIncrement = () => {
    let url = `${bus}/bus`
    return {
        name: 'removeUserIncrement',
        type: defaultType,
        url: url,
        requetType: 'DELETE',
        remark: '删除用户增量',
        go: id => { return axios.delete(url + '?id=' + id).then(res => res.data) }
    }
}
