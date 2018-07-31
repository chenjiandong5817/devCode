/*
* @Author: cdroid
* @Date:   2018-01-05 18:55:15
* @Last Modified by:   chenjiandong
* @Last Modified time: 2018-03-01 08:52:33
*/
/*
 * @Author: cdroid
 * @Date: 2018-01-04 10:11:07
 * @Last Modified by: chenjiandong
 * @Last Modified time: 2018-01-09 15:10:49
 */

import API from './../../api'
// 定时刷新时间
const flushTime = 1800000
// 需要缓存的key和数据请求地址
let apis = {
  flightdirections: API.getFlightDirectionsListPage().go,
  generalagents: API.getGeneralagentsListPage().go,
  terminals: API.getTerminal().go,
  flightnatures: API.getFlightnature().go,
  vipranks: API.getViprank().go,
  flightstatus: API.getFlightstatus().go,
  flighttasks: API.getFlighttask().go,
  irregularcodes: API.getIrregularcode().go,
  airlines: API.getAirlines().go,
  airports: API.getAirportListPage().go,
  registrations: API.getAircraftregistrationsListPage().go,
  baggageCarousels: API.getBaggagecarouselsListPage().go,
  checkinCounters: API.getCheckincountersListPage().go,
  gate: API.getGateListPage().go,
  deviceInfo: API.getDeviceInfo().go,
  templates: API.getTemplate().go,
  stand: API.getStandListPage().go,
  waitinghalls: API.getWaitingHallListPage().go,
  ckcounteropmodes: API.getCkcounteropmodesListPage().go,
  ckcounterservicetypes: API.getCkcounterservicetypesListPage().go,
  enuminfo: API.getEnumInfo().go,
  planSource: API.getSchedulesourcesListPage().go,
  multilang: API.getMultiLang().go,
  seasonName: API.getPlanSeasonNameLs().go,
  counterGroups: API.checkingroups().go,
  resourcestatus: API.getResourcestatusListPage().go,
  aircraftTypes: API.getAircraftType().go,
  aircraftcategory: API.getAircraftcategory().go,
  dataSources: API.getDataSourceNotSql().go,
  dataSourceFields: API.getDataSourceField().go,
  deviceConfig: API.getDeviceConfig().go
}
export default {
  flushTime, apis
}
