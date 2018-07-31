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
  flightdirections: API.getDirection().go,
  generalagents: API.getGeneralagent().go,
  terminals: API.getTerminal().go,
  flightnatures: API.getFlightnature().go,
  vipranks: API.getViprank().go,
  flightstatus: API.getFlightstatus().go,
  flighttasks: API.getFlighttask().go,
  irregularcodes: API.getIrregularcode().go,
  airlines: API.getAirlines().go,
  airports: API.getAirportListPage().go,
  aircraftTypes: API.getAircraftType().go,
  registrations: API.getRegistration().go,
  baggageCarousels: API.getBaggagecarouselsListPage().go,
  checkinCounters: API.getCheckincountersListPage().go,
  gate: API.getGateListPage().go,
  deviceInfo: API.getDeviceInfo().go,
  templates: API.getTemplate().go,
  aircraftcategorys: API.getAircraftcategory().go,
  stand: API.getStandListPage().go,
  waitinghalls: API.getWaitingHallListPage().go,
  ckcounteropmodes: API.getCkcounteropmodesListPage().go,
  ckcounterservicetypes: API.getCkcounterservicetypesListPage().go,
  enuminfo: API.getEnumInfoListPage().go,
  planSource: API.getSchedulesourcesListPage().go,
  // flights: API.getDynamicFlight().go,
  multilang: API.getMultiLang().go,
  seasonName: API.getPlanSeasonNameLs().go,
  counterGroups: API.checkingroups().go,
  resourcestatus: API.getResourcestatusListPage().go
}
export default {
  flushTime, apis
}
