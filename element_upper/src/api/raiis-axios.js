/*
* @Author: chenjiandong
* @Date:   last_modified_time
* @Last Modified by:   chenjiandong
* @Last Modified time: last_modified_time
*/
/*
 * @Author: cdroid
 * @Date: 2017-05-25 10:49:43
 * @Last Modified by: cdroid
 * @Last Modified time: 2017-05-25 10:50:07
 * @Description:  axios 的拦截器等操作
 */
import a from 'axios'
import helper from './../common/js/axios-helper'

helper.requestInterceptor(a)
helper.responseInterceptor(a)

export const axios = a

export const base = '/raiis'

export const baselw = '/lw'
