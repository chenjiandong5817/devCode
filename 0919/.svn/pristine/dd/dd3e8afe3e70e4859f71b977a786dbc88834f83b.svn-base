/*
* @Author: chenjiandong
* @Date:   last_modified_time
* @Last Modified by:   chenjiandong
* @Last Modified time: last_modified_time
*/
/*
 * @Author: chenyang
 * @Date: 2017-06-26 11:02:00
 * @Last Modified by: chenyang
 * @Last Modified time: 2017-08-14 10:15:03
 * @Description:  aircraftcategory 的 API
 */
import {axios, base} from './../raiis-axios'

let defaultType = 'aircraftcategory'
export const getAircraftcategory = () => {
    let url = `${base}/aircraftcategorys`
    return {
        name: 'getAircraftcategory',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '查询机型座位等级分类信息',
        go: params => { return axios.get(url, { params: params }).then(res => res.data) }
    }
}

export const removeAircraftcategory = () => {
    let url = `${base}/aircraftcategorys`
    return {
        name: 'removeAircraftcategory',
        type: defaultType,
        url: url,
        requetType: 'DELETE',
        remark: '删除一条机型座位等级分类记录',
        go: params => { return axios.delete(url + '/' + params.id, params).then(res => res.data) }
    }
}

export const editAircraftcategory = () => {
    let url = `${base}/aircraftcategorys`
    return {
        name: 'editAircraftcategory',
        type: defaultType,
        url: url,
        requetType: 'PUT',
        remark: '更新机型座位等级分类信息',
        go: params => { return axios.put(url, params).then(res => res.data) }
    }
}

export const addAircraftcategory = () => {
    let url = `${base}/aircraftcategorys`
    return {
        name: 'addAircraftcategory',
        type: defaultType,
        url: url,
        requetType: 'POST',
        remark: '新增一条机型座位等级分类记录',
        go: params => { return axios.post(url, params).then(res => res.data) }
    }
}
