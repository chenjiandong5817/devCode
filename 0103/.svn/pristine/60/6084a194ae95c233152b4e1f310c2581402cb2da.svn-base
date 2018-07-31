import axios from 'axios'

const base = '/raiis'

// 获取模板
export const getTemplate = params => {
  return axios.get(`${base}/deviceConfig/template`, {params}).then(res => res.data)
}

// 获取数据
export const getSouceData = params => {
  return axios.get(`${base}/flight/datasource`, {params}).then(res => res.data)
}

// 获取设备信息
export const getDeviceInfo = id => {
  return axios.get(`${base}/devices`, {params: {id: id}}).then(res => res.data)
}

// 获取设备分组
export const getDeviceGroup = id => {
  return axios.get(`${base}/devicegroups/${id}`).then(res => res.data)
}
