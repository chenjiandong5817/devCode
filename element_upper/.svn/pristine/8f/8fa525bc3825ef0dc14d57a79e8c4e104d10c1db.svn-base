import {axios, base} from './../raiis-axios'

let defaultType = 'sourceData'

export const getSource = () => {
  let url = `${base}/source`
  return {
    name: 'getSource',
    type: defaultType,
    url: url,
    requetType: 'GET',
    remark: '获取数据源第一张表',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }
  }
}

export const getSourceData = () => {
  let url = `${base}/data`
  return {
    name: 'getSourceData',
    type: defaultType,
    url: url,
    requetType: 'GET',
    remark: '获取数据源第二张表信息',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }
  }
}

export const postSourceData = () => {
  let url = `${base}/data`
  return {
    name: 'postSourceData',
    type: defaultType,
    url: url,
    requetType: 'POST',
    remark: '新增数据源第二张表信息',
    go: params => { return axios.post(url, params).then(res => res.data) }
  }
}

export const editSourceData = () => {
  let url = `${base}/data`
  return {
    name: 'editSourceData',
    type: defaultType,
    url: url,
    requetType: 'PUT',
    remark: '修改数据源第二张表信息',
    go: params => { return axios.put(url, params).then(res => res.data) }
  }
}

export const removeSourceData = () => {
  let url = `${base}/data`
  return {
    name: 'removeSourceData',
    type: defaultType,
    url: url,
    requetType: 'DELETE',
    remark: '删除数据源第二张表信息',
    go: params => { return axios.delete(url + '/' + params.id, params).then(res => res.data) }
  }
}

export const getSql = () => {
  let url = `${base}/data/sql`
  return {
    name: 'getSql',
    type: defaultType,
    url: url,
    requetType: 'GET',
    remark: '获取where后面完整的sql',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }
  }
}
