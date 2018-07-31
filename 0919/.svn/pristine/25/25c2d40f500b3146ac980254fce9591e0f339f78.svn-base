import {axios, base} from './../raiis-axios'
let defaultType = 'tempate manage'

export const getTemplate = () => {
  let url = `${base}/config`
  return {
    name: 'getTemplate',
    type: defaultType,
    url: url,
    requetType: 'GET',
    remark: '获取模板列表',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }
  }
}

export const editTemplate = () => {
  let url = `${base}/config`
  return {
    name: 'editTemplate',
    type: defaultType,
    url: url,
    requetType: 'PUT',
    remark: '修改模板',
    go: params => { return axios.put(url, params).then(res => res.data) }
  }
}

export const addTemplate = () => {
  let url = `${base}/config`
  return {
    name: 'addTemplate',
    type: defaultType,
    url: url,
    requetType: 'POST',
    remark: '添加模板',
    go: params => { return axios.post(url, params).then(res => res.data) }
  }
}

export const removeTemplate = () => {
  let url = `${base}/config`
  return {
    name: 'removeTemplate',
    type: defaultType,
    url: url,
    requetType: 'DELETE',
    remark: '删除模板',
    go: id => { return axios.delete(url + '/' + id).then(res => res.data) }
  }
}
