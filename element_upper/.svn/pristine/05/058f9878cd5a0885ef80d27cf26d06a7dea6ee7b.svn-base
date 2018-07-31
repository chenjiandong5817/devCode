import {axios, base} from './../raiis-axios'

let defaultType = 'allocation boardingtime'

// 获取航班登机时间列表
export const getFlightBoardingTime = () => {
  let url = `${base}/allocation/boardingtime`
  return {
    name: 'getFlightBoardingTime',
    type: defaultType,
    url: url,
    requetType: 'GET',
    remark: '获取航班登机时间列表',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }
  }
}

// 添加航班登机时间
export const addBoardingTime = () => {
  let url = `${base}/allocation/boardingtime`
  return {
    name: 'addBoardingTime',
    type: defaultType,
    url: url,
    requetType: 'PUT',
    remark: '添加航班登机时间',
    go: params => { return axios.post(url, params).then(res => res.data) }
  }
}

// 保存航班登机时间
export const saveFlightBoardingTime = () => {
  let url = `${base}/allocation/boardingtime`
  return {
    name: 'saveFlightBoardingTime',
    type: defaultType,
    url: url,
    requetType: 'PUT',
    remark: '保存航班登机时间',
    go: params => { return axios.put(url, params).then(res => res.data) }
  }
}

export const removeBoardingTime = () => {
  let url = `${base}/allocation/boardingtime`
  return {
    name: 'removeBoardingTime',
    type: defaultType,
    url: url,
    requetType: 'DELETE',
    remark: '删除航班信息',
    go: params => {
      return axios.delete(url + '/' + params.flightId, params).then(res => res.data)
    }
  }
}
