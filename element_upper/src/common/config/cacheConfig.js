/*
* @Author: chenjiandong
* @Date:   last_modified_time
* @Last Modified by:   chenjiandong
* @Last Modified time: last_modified_time
*/
import API from './../../api'
// 定时刷新时间
const flushTime = 1800000
// 需要缓存的key和数据请求地址
let apis = {
  airports: API.getAirportListPage().go,
  registrations: API.getRegistration().go
}
export default {
  flushTime, apis
}
