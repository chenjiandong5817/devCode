/*
* @Author: cdroid
* @Date:   2018-03-28 10:44:15
* @Last Modified by:   chenjiandong
* @Last Modified time: 2018-03-28 15:36:22
*/
import {axios} from './../raiis-axios'

let defaultType = 'robot'
export const getSimulateAskTest = () => {
    let url = `http://59.60.12.55:9090/robot/query/question`
    return {
        name: 'getSimulateAskTest',
        type: defaultType,
        url: url,
        requetType: 'GET',
        remark: '获取模拟问讯测试答案',
        go: params => { return axios.get(url + '/' + params).then(res => res.data) }
    }
}
