/*
 * @Author: chenyang
 * @Date: 2017-09-12 15:27:47
 * @Last Modified by: chenyang
 * @Last Modified time: 2017-11-16 09:07:47
 */

import {axios, base} from './../raiis-axios'

let defaultType = 'imageManage'
const displayAction = '/raiis/image'

export default {
  displayAction
}

export const getImage = () => {
  let url = `${base}/image`
  return {
    name: 'getImage',
    type: defaultType,
    url: url,
    requetType: 'GET',
    remark: '获取图片',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }
  }
}

export const postImage = () => {
  let url = `${base}/image`
  return {
    name: 'addImage',
    type: defaultType,
    url: url,
    requetType: 'POST',
    remark: '新增图片信息',
    go: params => { return axios.post(url, params).then(res => res.data) }
  }
}

export const editImage = () => {
  let url = `${base}/image`
  return {
    name: 'editImage',
    type: defaultType,
    url: url,
    requetType: 'PUT',
    remark: '修改图片信息',
    go: params => { return axios.put(url, params).then(res => res.data) }
  }
}

export const removeImage = () => {
  let url = `${base}/image`
  return {
    name: 'removeImage',
    type: defaultType,
    url: url,
    requetType: 'DELETE',
    remark: '删除多语言信息',
    go: params => { return axios.delete(url + '/' + params.id, params).then(res => res.data) }
  }
}

export const checkImageExist = () => {
  let url = `${base}/image/check`
  return {
    name: 'checkImage',
    type: defaultType,
    url: url,
    requetType: 'GET',
    remark: '查重',
    go: params => { return axios.get(url, { params: params }).then(res => res.data) }
  }
}

export function submitPutFile (data) {
  return axios({
    method: 'put',
    url: `${base}/image`,
    data: data
  })
}

export function submitPostFile (data) {
  return axios({
    method: 'post',
    url: `${base}/image`,
    data: data
  })
}
