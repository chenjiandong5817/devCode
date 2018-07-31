/*
* @Author: chenjiandong
* @Date:   last_modified_time
* @Last Modified by:   chenjiandong
* @Last Modified time: last_modified_time
*/

import Home from '../views/fids/DynamicFlight'
import User from '../views/system/User'
import Role from '../views/system/Role'
import Permission from '../views/system/Permission'
import ApiGrid from '../views/system/ApiGrid'
import NotFound from '../views/404'
import Login from '../views/Login'
import Main from '../views/Main'
import AiisAirport from '../views/system/AiisAirport.vue'
import DeviceManage from '../views/device/DeviceManage'
// import DynamicFlight from '../views/fids/DynamicFlight'
import VncManage from '../views/device/VncManage'
// import CommTable from './../components/CommTable'
import VersionManagement from './../views/system/VersionManagement'
// 多语言管理
import MultiLanguage from '../views/device/MultiLanguage'
// 基础表
import Aircraftcategory from './../views/base/Aircraftcategory'
import BaseAirport from './../views/base/BaseAirport'
import Gates from './../views/resource/Gates'
import Stands from './../views/resource/Stands'
import Baggagecarousels from './../views/resource/Baggagecarousels'
import Checkincounters from './../views/resource/Checkincounters'
import Irregularcodes from './../views/base/Irregularcodes'
import FlightTask from './../views/base/FlightTask'
import ImageManage from '../views/device/ImageManage'
import Generalagents from './../views/base/Generalagents'
import Display from './../views/device/Display'
import Flightstatus from './../views/base/Flightstatus'
import Airlines from './../views/base/Airlines'
import Aircraftregistrations from './../views/base/Aircraftregistrations'
import Vipranks from './../views/base/Vipranks'
import Schedulesources from './../views/base/Schedulesources'
import ResourceStatus from './../views/base/ResourceStatus'
import ResourceNatures from './../views/base/ResourceNatures'
import FlightNatures from './../views/base/FlightNatures'
import FlightDirections from './../views/base/FlightDirections'
import CkcounterOpmodes from './../views/base/CkcounterOpmodes'
import CkcounterServiceTypes from './../views/base/CkcounterServiceTypes'
import AirportContacts from './../views/base/AirportContacts'
import WaitingHalls from './../views/resource/WaitingHalls'
import Terminals from './../views/resource/Terminals'
import Bridges from './../views/resource/Bridges'
import Aprons from './../views/resource/Aprons'
import EnumInfo from './../views/base/EnumInfo'
import PlanFlightSchedule from '../views/fids/PlanFlightSchedule'
import Vip from './../views/fids/Vip'
import PlanSeasonSchedule from '../views/fids/PlanSeasonSchedule'
import DestLang from './../views/base/DestLang'
import SeasonName from './../views/base/SeasonName'
import VisualTools from './../views/device/VisualTools'
import DataSource from './../views/device/DataSource'
import FlightLoad from './../views/fids/FlightLoad'
import MorFlightGenerateTime from './../views/fids/MorFlightGenerateTime'
let Component = {
  home: Home,
  user: User,
  role: Role,
  permission: Permission,
  aiisAirport: AiisAirport,
  apiGrid: ApiGrid,
  deviceManage: DeviceManage,
  // dynamicFlight: DynamicFlight,
  vncManage: VncManage,
  versionmanagement: VersionManagement,
  multiLanguage: MultiLanguage,
  aircraftcategory: Aircraftcategory,
  baseAirport: BaseAirport,
  gates: Gates,
  stands: Stands,
  baggagecarousels: Baggagecarousels,
  checkincounters: Checkincounters,
  flightTask: FlightTask,
  imageManage: ImageManage,
  irregularcodes: Irregularcodes,
  generalagents: Generalagents,
  display: Display,
  flightstatus: Flightstatus,
  airlines: Airlines,
  aircraftregistrations: Aircraftregistrations,
  vipranks: Vipranks,
  schedulesources: Schedulesources,
  resourceStatus: ResourceStatus,
  resourceNatures: ResourceNatures,
  flightNatures: FlightNatures,
  flightDirections: FlightDirections,
  ckcounterOpmodes: CkcounterOpmodes,
  ckcounterServiceTypes: CkcounterServiceTypes,
  airportContacts: AirportContacts,
  waitingHalls: WaitingHalls,
  terminals: Terminals,
  bridges: Bridges,
  enuminfo: EnumInfo,
  aprons: Aprons,
  planFlightSchedule: PlanFlightSchedule,
  vip: Vip,
  PlanSeasonSchedule: PlanSeasonSchedule,
  destLang: DestLang,
  seasonName: SeasonName,
  visualTools: VisualTools,
  dataSource: DataSource,
  flightLoad: FlightLoad,
  morFlightGenerateTime: MorFlightGenerateTime
}

let routes = [
  {
    id: 1000,
    path: '/',
    component: Main,
    name: '',
    leaf: true,
    iconCls: 'fa fa-home fa-lg',
    children: [
      { id: 1001, path: '/home', name: '航班动态', components: Component }
    ]
  },
  {
    id: 1100,
    path: '/',
    component: Main,
    name: '基础管理',
    iconCls: 'fa fa-list', // 图标样式class
    children: [
        { id: 1101, path: '/baseAirport', name: '通航机场管理', components: Component, meta: { id: 'baseAirport' } },
        { id: 1102, path: '/aircraftcategory', name: '机型等级管理', components: Component, meta: { id: 'aircraftcategory' } },
        { id: 1103, path: '/irregularcodes', name: '异常代码管理', components: Component, meta: { id: 'irregularcodes' } },
        { id: 1104, path: '/generalagents', name: '代理人代码管理', components: Component, meta: { id: 'generalagents' } },
        { id: 1105, path: '/flightTask', name: '航班任务代码管理', components: Component, meta: { id: 'flightTask' } },
        { id: 1106, path: '/flightstatus', name: '航班状态代码管理', components: Component, meta: { id: 'flightstatus' } },
        { id: 1106, path: '/airlines', name: '航空公司管理', components: Component, meta: { id: 'airlines' } },
        { id: 1107, path: '/aircraftregistrations', name: '机号信息管理', components: Component, meta: { id: 'aircraftregistrations' } },
        { id: 1108, path: '/vipranks', name: 'VIP类型管理', components: Component, meta: { id: 'vipranks' } },
        { id: 1109, path: '/schedulesources', name: '计划类型管理', components: Component, meta: { id: 'schedulesources' } },
        { id: 1110, path: '/resourceStatus', name: '资源状态管理', components: Component, meta: { id: 'resourceStatus' } },
        { id: 1110, path: '/resourceNatures', name: '资源属性管理', components: Component, meta: { id: 'resourceNatures' } },
        { id: 1111, path: '/flightNatures', name: '航班属性管理', components: Component, meta: { id: 'flightNatures' } },
        { id: 1112, path: '/flightDirections', name: '方向代码管理', components: Component, meta: { id: 'flightDirections' } },
        { id: 1113, path: '/ckcounterOpmodes', name: '柜台分配模式管理', components: Component, meta: { id: 'ckcounterOpmodes' } },
        { id: 1114, path: '/ckcounterServiceTypes', name: '柜台服务类型管理', components: Component, meta: { id: 'ckcounterServiceTypes' } },
        { id: 1115, path: '/airportContacts', name: '机场联系人管理', components: Component, meta: { id: 'airportContacts' } },
        { id: 1116, path: '/enuminfo', name: '枚举信息管理', components: Component, meta: { id: 'enumInfo' } }
    ]
  },
  {
    id: 1200,
    path: '/',
    component: Main,
    name: '资源管理',
    iconCls: 'fa fa-list', // 图标样式class
    children: [
        { id: 1201, path: '/gates', name: '登机口', components: Component, meta: { id: 'gates' } },
        { id: 1202, path: '/stands', name: '机位', components: Component, meta: { id: 'stands' } },
        { id: 1203, path: '/baggagecarousels', name: '行李提取转盘', components: Component, meta: { id: 'baggagecarousels' } },
        { id: 1204, path: '/checkincounters', name: '值机柜台', components: Component, meta: { id: 'checkincounters' } },
        { id: 1205, path: '/waitingHalls', name: '候机厅', components: Component, meta: { id: 'waitingHalls' } },
        { id: 1206, path: '/terminals', name: '候机楼', components: Component, meta: { id: 'terminals' } },
        { id: 1207, path: '/bridges', name: '登机桥', components: Component, meta: { id: 'bridges' } },
        { id: 1208, path: '/aprons', name: '机坪区域', components: Component, meta: { id: 'aprons' } }
    ]
  },
  {
    id: 1300,
    path: '/',
    component: Main,
    name: '系统管理',
    iconCls: 'fa fa-wrench',
    children: [
      { id: 1301, path: '/user', name: '用户管理', components: Component },
      { id: 1302, path: '/role', name: '角色管理', components: Component },
      { id: 1303, path: '/permission', name: '权限管理', components: Component },
      { id: 1304, path: '/aiisAirport', name: '运营机场管理', components: Component },
      { id: 1305, path: '/apiGrid', name: '请求地址管理', components: Component },
      { id: 1306, path: '/versionmanagement', name: '版本管理', components: Component }
    ]
  },
  {
    id: 1400,
    path: '/',
    component: Main,
    name: '设备管理',
    iconCls: 'fa fa-desktop',
    children: [
      { id: 1401, path: '/deviceManage', name: '航显终端管理', components: Component },
      { id: 1402, path: '/vncManage', name: 'vnc设备管理', components: Component }
    ]
  },
  {
    id: 1500,
    path: '/',
    component: Main,
    name: '航班信息管理',
    iconCls: 'fa fa-plane',
    children: [
      { id: 1501, path: '/PlanSeasonSchedule', name: '航季计划管理', components: Component },
      { id: 1502, path: '/planFlightSchedule', name: '航班计划管理', components: Component },
      { id: 1503, path: '/home', name: '航班动态管理', components: Component },
      { id: 1504, path: '/vip', name: 'VIP管理', components: Component },
      { id: 1505, path: '/seasonName', name: '航季信息管理', components: Component, meta: { id: 'seasonName' } },
      { id: 1506, path: '/flightLoad', name: '航班客货行邮信息管理', components: Component, meta: { id: 'flightLoad' } },
      { id: 1507, path: '/morFlightGenerateTime', name: '航班计划生成时间范围管理', components: Component, meta: { id: 'morFlightGenerateTime' } }
    ]
  },
  {
    id: 1600,
    path: '/',
    component: Main,
    name: '航显管理',
    iconCls: 'fa fa-plane',
    children: [
      { id: 1601, path: '/multiLanguage', name: '多语言管理', components: Component },
      { id: 1602, path: '/imageManage', name: '航显图片管理', components: Component },
      { id: 1603, path: '/display', name: '播放列表管理', components: Component },
      { id: 1604, path: '/visualTools', name: '可视化编辑工具', components: Component },
      { id: 1605, path: '/dataSource', name: '数据源管理', components: Component },
      { id: 1605, path: '/destLang', name: '目的地语言管理', components: Component, meta: { id: 'destLang' } }
    ]
  },
  {
    id: 9999,
    path: '/login',
    component: Login,
    name: '',
    hidden: true
  },
  {
    id: 9998,
    path: '/404',
    component: NotFound,
    name: '',
    hidden: true
  },
  {
    id: 9997,
    path: '*',
    hidden: true,
    redirect: { path: '/404' }
  }
]

export default routes
