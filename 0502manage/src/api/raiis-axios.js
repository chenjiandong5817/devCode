/*
* @Author: cdroid
* @Date:   2018-01-05 18:55:15
* @Last Modified by:   chenjiandong
* @Last Modified time: 2018-03-15 16:24:43
*/

import a from 'axios'
import helper from './../common/js/axios-helper'

helper.requestInterceptor(a)
helper.responseInterceptor(a)

export const axios = a

export const base = '/raiis'

export const baselw = '/lw'

export const bus = '/yxyt'
