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
