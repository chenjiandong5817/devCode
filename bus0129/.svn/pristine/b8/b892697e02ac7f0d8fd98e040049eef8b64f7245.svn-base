/*
* @Author: chenjiandong
* @Date:   last_modified_time
* @Last Modified by:   chenjiandong
* @Last Modified time: last_modified_time
*/
import {axios, base} from './../raiis-axios'

let defaultType = 'version management'
// Airport
// export const getAirportListPage = params => { return axios.get(`${base}/aiisAirport/query`, { params: params }).then(res => res.data) }
export const getAirportList = () => {
    let url = `${base}/airports`
    return {
        name: 'getAirportList',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '获取通用机场列表',
        go: params => { return axios.get(url, { params: params }).then(res => res.data) }
    }
}
// 获取用户信息，用于转换名字来用
// export const getUserListPage = () => {
//   let url = `${base}/user`
//   return {
//     name: 'getUserListPage',
//     type: 'user',
//     url: url,
//     requetType: 'GET',
//     remark: '获取用户列表',
//     go: params => { return axios.get(url, { params: params }).then(res => res.data) }
//   }
// }
// 新增版本管理
export const addVersionInfoRecord = () => {
	let url = `${base}/appversions`
	return {
		name: 'addVersionInfoRecord',
		type: defaultType,
		url: url,
		requetType: 'POST',
		remark: '新增版本信息记录',
		go: params => { return axios.post(url, params.newValue).then(res => res.data) }
	}
}
// 删除版本信息记录
export const delVersionInfoRecord = () => {
	let url = `${base}/appversions`
	return {
		name: 'delVersionInfoRecord',
		type: defaultType,
		url: url,
		requetType: 'DELETE',
		remark: '删除版本信息记录',
        go: id => { return axios.delete(url + '/' + id).then(res => res.data) }
	}
}
// 修改版本信息记录
export const updateVersionInfoRecord = () => {
	let url = `${base}/appversions`
	return {
		name: 'updateVersionInfoRecord',
		type: defaultType,
		url: url,
		requetType: 'PUT',
		remark: '更新版本信息记录',
		go: params => { return axios.put(url, params.newValue).then(res => res.data) }
	}
}
// 获取版本信息列表
export const getVersionInfoList = () => {
	let url = `${base}/appversions`
	return {
		name: 'getVersionInfoList',
		type: defaultType,
		url: url,
		requetType: 'GET',
		remark: '获取版本信息的列表',
		go: params => { return axios.get(url, { params: params }).then(res => res.data) }
	}
}
// 上传文件
export const uploadFile = () => {
	let url = `${base}/appversions/uploadfile`
	return {
		name: 'uploadFile',
		type: defaultType,
		url: url,
		requetType: 'POST',
		remark: '上传文件',
		go: params => { return axios.post(url, params).then(res => res.data) }
	}
}
// 补全完整的路径
// export const getDelPath = () => {
// 	let url = `${baselw}/appversions`
// 	return {
// 		name: 'getDelPath',
// 		type: defaultType,
// 		url: url,
// 		requetType: 'GET',
// 		remark: '补全完整的路径',
//         go: id => { return axios.delete(url + '/' + id).then(res => res.data) }
// 	}
// }
// 获取下载记录表
export const getDownloadRecords = () => {
	let url = `${base}/downloadrecords`
	return {
		name: 'getDownloadRecords',
		type: defaultType,
		url: url,
		requetType: 'GET',
		remark: '获取下载记录表',
		go: params => { return axios.get(url).then(res => res.data) }
	}
}
// 多个文件同时上传
export function submitPostALLFile (data) {
	return axios({
		method: 'post',
		url: `${base}/appversions/uploadfile`,
		data: data
	})
}
