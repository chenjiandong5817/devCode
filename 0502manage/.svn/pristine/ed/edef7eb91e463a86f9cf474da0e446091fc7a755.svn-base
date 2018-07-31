/*
* @Author: cdroid
* @Date:   2018-07-03 15:04:39
* @Last Modified by:   chenjiandong
* @Last Modified time: 2018-07-03 15:20:59
*/
import { axios, bus } from './../raiis-axios'

let defaultType = 'orderConversion'

export const getOrderConversion = () => {
    let url = `${bus}/bus`
    return {
        name: 'getOrderConversion',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '获取订单转换量',
        go: params => { return axios.get(url + '?id=' + params).then(res => res.data) }
    }
}
