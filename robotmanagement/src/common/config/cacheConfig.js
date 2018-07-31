/*
* @Author: cdroid
* @Date:   2018-01-29 14:26:22
* @Last Modified by:   chenjiandong
* @Last Modified time: 2018-03-01 08:53:41
*/
/*
 * @Author: cdroid
 * @Date: 2018-01-04 10:11:07
 * @Last Modified by: chenjiandong
 * @Last Modified time: 2018-01-09 15:10:49
 */

// import API from './../../api'
// 定时刷新时间
const flushTime = 1800000
// 需要缓存的key和数据请求地址
let apis = {
  // dataSourceFields: API.queryUserMenuList().go
}
export default {
  flushTime, apis
}
