/*
 * @Author: ylj
 * @Date: 2017-11-30 11:33:41
 * @Last Modified by: ylj
 * @Last Modified time: 2017-11-30 11:38:28
 */
import {axios, base} from './../raiis-axios'

let defaultType = 'season daytime'

export const getMorFlightGenTimels = () => {
  let url = `${base}/season/daytime`
  return {
    name: 'getMorFlightGenTimels',
    type: defaultType,
    url: url,
    requetType: 'GET',
    remark: '获取次日航班生成的时间范围',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }
  }
}

export const addMorFlightGenTime = () => {
  let url = `${base}/season/daytime`
  return {
    name: 'addMorFlightGenTime',
    type: defaultType,
    url: url,
    requetType: 'POST',
    remark: '新增次日航班生成的时间范围',
    go: params => { return axios.post(url, params).then(res => res.data) }
  }
}

export const editMorFlightGenTime = () => {
  let url = `${base}/season/daytime`
  return {
    name: 'editMorFlightGenTime',
    type: defaultType,
    url: url,
    requetType: 'PUT',
    remark: '修改次日航班生成的时间范围',
    go: params => { return axios.put(url, params).then(res => res.data) }
  }
}

export const removeMorFlightGenTime = () => {
  let url = `${base}/season/daytime`
  return {
    name: 'removeMorFlightGenTime',
    type: defaultType,
    url: url,
    requetType: 'DELETE',
    remark: '删除次日航班生成的时间范围',
    go: params => {
      return axios.delete(url + '/' + params.id, params).then(res => res.data)
    }
  }
}

