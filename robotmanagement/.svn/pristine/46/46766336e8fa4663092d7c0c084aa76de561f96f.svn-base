/*
* @Author: cdroid
* @Date:   2018-03-05 09:59:21
* @Last Modified by:   chenjiandong
* @Last Modified time: 2018-03-06 15:20:53
*/
import {axios, robot} from './../raiis-axios'

let defaultType = 'robot'
export const getRobotInfoListPage = (pageNumber, pageSize) => {
    let url = `${robot}/getRobotInfo/pageNumber/${pageNumber}/pageSize/${pageSize}`
    return {
        name: 'getRobotInfoListPage',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '获取机器人信息列表',
        go: params => { return axios.get(url).then(res => res.data) }
    }
}

export const getRobotInfoCount = () => {
    let url = `${robot}/robotInfoCount`
    return {
        name: 'getRobotInfoCount',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '查询机器人信息列表条数',
        go: params => { return axios.get(url).then(res => res.data) }
    }
}

export const removeRobotInfo = () => {
    let url = `${robot}/deleteRobotInfo`
    return {
        name: 'removeRobotInfo',
        type: defaultType,
        url: url,
        requetType: 'DELETE',
        remark: '删除机器人信息',
        go: id => { return axios.delete(url + '/' + id).then(res => res.data) }
    }
}

export const editRobotInfo = (id) => {
    let url = `${robot}/updateRobotInfo/${id}`
    return {
        name: 'editRobotInfo',
        type: defaultType,
        url: url,
        requetType: 'PUT',
        remark: '更新机器人信息',
        go: params => { return axios.put(url, params).then(res => res.data) }
    }
}

export const addRobotInfo = () => {
    let url = `${robot}/addRobotInfo`
    return {
        name: 'addRobotInfo',
        type: defaultType,
        url: url,
        requetType: 'POST',
        remark: '新增机器人信息',
        go: params => { return axios.post(url, params).then(res => res.data) }
    }
}

export const searchRobotInfo = (isUse, category) => {
    let url = `${robot}/filterKnowledge/isUse=${isUse}/category=${category}`
    return {
        name: 'searchRobotInfo',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '查询机器人信息',
        go: params => { return axios.GET(url).then(res => res.data) }
    }
}
