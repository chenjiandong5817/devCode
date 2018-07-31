/*
 * @Author: ylj
 * @Date: 2018-02-26 09:13:57
 * @Last Modified by: ylj
 * @Last Modified time: 2018-02-28 21:08:08
 */

import Mock from 'mockjs'
import MockGen from './../../common/js/mock-test'

Mock.Random.extend({
  deviceIp: function (date) {
    let deviceIps = ['136.136.13.5', '136.136.17.180', '136.136.17.181', '136.136.13.75', '136.136.17.52', '136.136.17.51']
    return this.pick(deviceIps)
  },
  displaytype: function (date) {
    let displaytypes = ['002', '004', '005', '008', '010']
    return this.pick(displaytypes)
  },
  airportCode: function (date) {
    let airportCodes = ['LCX', 'XMN', 'FOC']
    return this.pick(airportCodes)
  },
  repairNo: function (date) {
    let repairNos = ['JW01', 'JW01', 'DZ01']
    return this.pick(repairNos)
  }
})

let devIrregularData = MockGen.generateData(105, () => {
  let deviceId = MockGen.uuid()
  return Mock.mock({
    id: MockGen.uuid(),
    deviceId: deviceId,
    deviceNo: Mock.Random.string(4),
    deviceIp: '@deviceIp',
    deviceIrrStatus: Mock.Random.natural(1, 3),
    displaytype: '@displaytype',
    airportCode: '@airportCode',
    terminal: 'T3',
    area: '04',
    reportUser: '张三',
    reportingTime: Mock.Random.datetime(),
    irrdescription: '设备异常测试数据'
  })
})

let devRepairRecordData = MockGen.generateData(105, () => {
  let devIrregularId = MockGen.uuid()
  let beginTime = Mock.Random.datetime()
  let date = new Date(beginTime)
  let endTime = new Date(date.setDate((date).getDate() + 1))
  return Mock.mock({
    id: MockGen.uuid(),
    deviceIrrId: devIrregularId,
    repairStatus: Mock.Random.natural(1, 3),
    repairNo: Mock.Random.string('number', 3),
    repairMatter: '设备维修记录测试数据',
    repairUnit: '电子工程',
    repairUser: '李四、张三',
    warrantyUnit: 'XXXX',
    warrantyMan: 'XXX',
    repairRecord: '维修记录',
    beginTime: beginTime,
    endTime: endTime,
    airportCode: 'XMN'
  })
})

Mock.mock(/^\/raiis\/deviceIrregulars/, 'get', options => {
  return MockGen.query(devIrregularData, options.url)
})

Mock.mock(/^\/raiis\/deviceRepairRecords/, 'get', options => {
  return MockGen.query(devRepairRecordData, options.url)
})
