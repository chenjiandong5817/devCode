/*
* @Author: cdroid
* @Date:   2018-03-05 09:59:21
* @Last Modified by:   chenjiandong
* @Last Modified time: 2018-03-08 10:11:35
*/
import {axios, robot} from './../raiis-axios'

let defaultType = 'robot'
export const getKnowledgeCategoryListPage = (pageNumber, pageSize) => {
    let url = `${robot}/getKnowledgeCategory/pageNumber/${pageNumber}/pageSize/${pageSize}`
    return {
        name: 'getKnowledgeCategoryListPage',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '查询知识库类别信息列表',
        go: params => { return axios.get(url).then(res => res.data) }
    }
}

export const getKnowledgeCategoryCount = () => {
    let url = `${robot}/knowledgeCategoryCount`
    return {
        name: 'getKnowledgeCategoryCount',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '查询知识库类别信息列表条数',
        go: params => { return axios.get(url).then(res => res.data) }
    }
}

export const deleteKnowledgeCategory = () => {
    let url = `${robot}/deleteKnowledgeCategory`
    return {
        name: 'deleteKnowledgeCategory',
        type: defaultType,
        url: url,
        requetType: 'DELETE',
        remark: '删除知识库类别信息',
        go: id => { return axios.delete(url + '/' + id).then(res => res.data) }
    }
}

export const updateKnowledgeCategory = (id) => {
    let url = `${robot}/updateKnowledgeCategory/${id}`
    return {
        name: 'updateKnowledgeCategory',
        type: defaultType,
        url: url,
        requetType: 'PUT',
        remark: '更新知识库类别信息',
        go: params => { return axios.put(url, params).then(res => res.data) }
    }
}

export const addKnowledgeCategory = () => {
    let url = `${robot}/addKnowledgeCategory`
    return {
        name: 'addKnowledgeCategory',
        type: defaultType,
        url: url,
        requetType: 'POST',
        remark: '新增知识库类别信息',
        go: params => { return axios.post(url, params).then(res => res.data) }
    }
}
