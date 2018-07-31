/*
* @Author: cdroid
* @Date:   2018-03-05 09:58:52
* @Last Modified by:   chenjiandong
* @Last Modified time: 2018-03-06 09:00:01
*/
import {axios, robot} from './../raiis-axios'

let defaultType = 'chatLog'
export const getChatLogListPage = (pageNumber, pageSize) => {
    let url = `${robot}/getChatlog/pageNumber/${pageNumber}/pageSize/${pageSize}`
    return {
        name: 'getChatLogListPage',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '查询日志分页信息列表',
        go: params => { return axios.get(url).then(res => res.data) }
    }
}

export const getChatLogCount = () => {
    let url = `${robot}/chatlogCount`
    return {
        name: 'getChatLogCount',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '查询日志分页信息的数量',
        go: params => { return axios.get(url).then(res => res.data) }
    }
}
