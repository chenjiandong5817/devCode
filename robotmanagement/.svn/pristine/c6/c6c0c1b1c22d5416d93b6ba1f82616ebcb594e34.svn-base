/*
* @Author: cdroid
* @Date:   2018-03-05 09:59:21
* @Last Modified by:   chenjiandong
* @Last Modified time: 2018-03-28 15:32:41
*/
import {axios, robot} from './../raiis-axios'

let defaultType = 'robot'
export const getKnowledgeListPage = (pageNumber, pageSize) => {
    let url = `${robot}/getKnowledge/pageNumber/${pageNumber}/pageSize/${pageSize}`
    return {
        name: 'getKnowledgeListPage',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '查询知识库信息列表',
        go: params => { return axios.get(url).then(res => res.data) }
    }
}

export const getKnowledgeCount = () => {
    let url = `${robot}/knowledgeCount`
    return {
        name: 'getKnowledgeCount',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '查询知识库信息列表条数',
        go: params => { return axios.get(url).then(res => res.data) }
    }
}

export const removeKnowledge = () => {
    let url = `${robot}/destLang`
    return {
        name: 'removeKnowledge',
        type: defaultType,
        url: url,
        requetType: 'DELETE',
        remark: '删除知识库信息',
        go: id => { return axios.delete(url + '/' + id).then(res => res.data) }
    }
}

export const editKnowledge = (id) => {
    let url = `${robot}/updateKnowledge/${id}`
    return {
        name: 'editKnowledge',
        type: defaultType,
        url: url,
        requetType: 'PUT',
        remark: '更新知识库信息',
        go: params => { return axios.put(url, params).then(res => res.data) }
    }
}

export const addKnowledge = () => {
    let url = `${robot}/addKnowledge`
    return {
        name: 'addKnowledge',
        type: defaultType,
        url: url,
        requetType: 'POST',
        remark: '新增知识库信息',
        go: params => { return axios.post(url, params).then(res => res.data) }
    }
}

export const searchKnowledge = (isUse, category, knowledgeRobotId, pageNum, pageSize) => {
    let url = `${robot}/filterKnowledge/isUse=${isUse}/category=${category}/knowledgeRobotId=${knowledgeRobotId}/pageNum=${pageNum}/pageSize=${pageSize}`
    return {
        name: 'searchKnowledge',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '查询知识库信息',
        go: params => { return axios.get(url).then(res => res.data) }
    }
}

export const searchKnowledgeCount = (isUse, category, knowledgeRobotId) => {
    let url = `${robot}/filterKnowledgeCount/isUse=${isUse}/category=${category}/knowledgeRobotId=${knowledgeRobotId}`
    return {
        name: 'searchKnowledgeCount',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '查询知识库信息数量',
        go: params => { return axios.get(url).then(res => res.data) }
    }
}

export const fuzzySearchKnowledgeCount = (question) => {
    let url = `${robot}/fuzzySearchKnowledgeCount/question=${question}`
    return {
        name: 'fuzzySearchKnowledgeCount',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '模糊查询知识库信息数量',
        go: params => { return axios.get(url).then(res => res.data) }
    }
}

export const fuzzySearchKnowledge = (question, pageNumber, pageSize) => {
    let url = `${robot}/fuzzySearchKnowledge/question=${question}/pageNumber/${pageNumber}/pageSize/${pageSize}`
    return {
        name: 'fuzzySearchKnowledge',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '模糊查询知识库信息',
        go: params => { return axios.get(url).then(res => res.data) }
    }
}

export const fuzzySearchKnowledgeAnswerCount = (answer) => {
    let url = `${robot}/fuzzySearchKnowledgeAnswerCount/answer=${answer}`
    return {
        name: 'fuzzySearchKnowledgeAnswerCount',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '模糊查询知识库答案信息数量',
        go: params => { return axios.get(url).then(res => res.data) }
    }
}

export const fuzzySearchKnowledgeAnswer = (answer, pageNumber, pageSize) => {
    let url = `${robot}/fuzzySearchKnowledgeAnswer/answer=${answer}/pageNumber/${pageNumber}/pageSize/${pageSize}`
    return {
        name: 'fuzzySearchKnowledgeAnswer',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '模糊查询知识库答案信息',
        go: params => { return axios.get(url).then(res => res.data) }
    }
}
