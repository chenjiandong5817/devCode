import {axios, base} from './../raiis-axios'

let defaultType = 'allocation checkincounter'

export const getFlightCkinTime = () => {
  let url = `${base}/allocation/checkincounter/checkintime`
  return {
    name: 'getFlightCkinTime',
    type: defaultType,
    url: url,
    requetType: 'GET',
    remark: '获取航班值机时间列表',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }
  }
}

export const saveFlightCkinTime = () => {
  let url = `${base}/allocation/checkincounter/checkintime`
  return {
    name: 'saveFlightCkinTime',
    type: defaultType,
    url: url,
    requetType: 'PUT',
    remark: '保存航班值机时间',
    go: params => { return axios.put(url, params).then(res => res.data) }
  }
}
