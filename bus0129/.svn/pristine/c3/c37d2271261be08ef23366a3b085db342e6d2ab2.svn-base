import {axios, base} from './../raiis-axios'
let defaultType = 'server storage'

export const saveStorage = () => {
  let url = `${base}/storage`
  return {
    name: 'saveStorage',
    type: defaultType,
    url: url,
    requetType: 'POST',
    remark: '保存数据到服务端',
    go: params => { return axios.post(url, params).then(res => res.data) }
  }
}

export const getStorage = () => {
  let url = `${base}/storage`
  return {
    name: 'getStorage',
    type: defaultType,
    url: url,
    requetType: 'GET',
    remark: '获取服务端储存数据',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }
  }
}
