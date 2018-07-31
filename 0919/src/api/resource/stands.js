import {axios, base} from './../raiis-axios'

let defaultType = 'stands'

export const getStandListPage = () => {
    let url = `${base}/stands`
    return {
        name: 'getStandListPage',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '获取机位列表',
        go: params => { return axios.get(url, { params: params }).then(res => res.data) }
    }
}

export const removeStand = () => {
    let url = `${base}/stands`
    return {
        name: 'removeStand',
        type: defaultType,
        url: url,
        requetType: 'DELETE',
        remark: '删除机位',
        go: id => { return axios.delete(url + '/' + id).then(res => res.data) }
    }
}

export const editStand = () => {
    let url = `${base}/stands`
    return {
        name: 'editStand',
        type: defaultType,
        url: url,
        requetType: 'PUT',
        remark: '编辑机位',
        go: params => { return axios.put(url, params).then(res => res.data) }
    }
}

export const addStand = () => {
    let url = `${base}/stands`
    return {
        name: 'addStand',
        type: defaultType,
        url: url,
        requetType: 'POST',
        remark: '添加机位',
        go: params => { return axios.post(url, params).then(res => res.data) }
    }
}
