import Vue from 'vue'
import VueRouter from 'vue-router'
import Layout from '@/page/layout/'
import Iframe from '@/components/iframe/'
const _import = require('./_import_' + process.env.NODE_ENV)

Vue.use(VueRouter)

export const constantRouterMap = [
  { path: '/404', component: _import('layout/errorPage/404'), name: '404', hidden: true },
  { path: '/403', component: _import('layout/errorPage/403'), name: '403', hidden: true },
  { path: '/500', component: _import('layout/errorPage/500'), name: '500', hidden: true },
  { path: '/authredirect', component: _import('login/authredirect'), hidden: true },
  { path: '/login', component: _import('login/index'), hidden: true },
  { path: '/logout', component: _import('login/logout'), name: 'logout', hidden: true },
  {
    path: '',
    name: 'layout',
    component: Layout,
    leafOnly: true,
    // redirect: 'home',
    children: [
      { path: 'home', name: 'home', component: _import('layout/home/index') },
      { path: 'iframe', component: Iframe, name: 'iframe', hidden: true }
    ]
  }
]

export default new VueRouter({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})

export const asyncRouterMap = [
  { path: '*', redirect: '/404', hidden: true },
  {
    path: '/admin',
    name: 'admin',
    component: Layout,
    children: [
      {
        path: 'role',
        name: 'role',
        component: _import('admin/role/index')
      },
      {
        path: 'menu',
        name: 'menu',
        component: _import('admin/menu/index')
      },
      {
        path: 'user',
        name: 'user',
        component: _import('admin/user/index')
      },
      {
        path: 'test',
        name: 'test',
        component: _import('admin/test/index')
      },
      {
        path: 'fishbone',
        name: 'fishbone',
        component: _import('admin/test/test-fishbone')
      },
      {
        path: 'stand',
        name: 'stand',
        component: _import('admin/stand/index')
      }
    ]
  },
  {
    path: '/flight',
    name: 'flight',
    component: Layout,
    children: [
      {
        path: 'flightList',
        name: 'flightList',
        component: _import('flight/flightList/index')
      },
      {
        path: 'flightSagmentDep',
        name: 'flightSagmentDep',
        component: _import('flight/flightSagmentDep/index')
      }
    ]
  },
  {
    path: '/flight_delay',
    name: 'flight_delay',
    component: Layout,
    children: [
      {
        path: 'newDemo',
        name: 'newDemo',
        component: _import('flightDelay/newDemo/index')
      },
      {
        path: 'serviceProgressManage',
        name: 'serviceProgressManage',
        component: _import('flightDelay/serviceProgressManage/index')
      },
      {
        path: 'provideFood',
        name: 'provideFood',
        component: _import('flightDelay/provideFood/index')
      },
      {
        path: 'flightDelayLog',
        name: 'flightDelayLog',
        component: _import('flightDelay/flightDelayLog/index')
      },
      {
        path: 'foodTypeManage',
        name: 'foodTypeManage',
        component: _import('flightDelay/foodTypeManage/index')
      },
      {
        path: 'vehicleManage',
        name: 'vehicleManage',
        component: _import('flightDelay/vehicleManage/index')
      }
    ]
  },
  {
    path: '/message',
    name: 'message',
    component: Layout,
    children: [
      {
        path: 'messageInfo',
        name: 'messageInfo',
        component: _import('message/messageInfo/index')
      },
      {
        path: 'messageLog',
        name: 'messageLog',
        component: _import('message/messageLog/index')
      },
      {
        path: 'messageType',
        name: 'messageType',
        component: _import('message/messageType/index')
      }
    ]
  },
  {
    path: '/base',
    name: 'base',
    component: Layout,
    children: [
      {
        path: 'airlines',
        name: 'airlines',
        component: _import('base/airlines/index')
      },
      {
        path: 'aircraftregistrations',
        name: 'aircraftregistrations',
        component: _import('base/aircraftregistrations/index')
      },
      {
        path: 'ckcounteropmodes',
        name: 'ckcounteropmodes',
        component: _import('base/ckcounteropmodes/index')
      },
      {
        path: 'airport',
        name: 'baseAirport',
        component: _import('base/airport/index')
      },
      {
        path: 'irregularcodes',
        name: 'irregularcodes',
        component: _import('base/irregularcodes/index')
      },
      {
        path: 'resourcestatus',
        name: 'resourcestatus',
        component: _import('base/resourcestatus/index')
      },
      {
        path: 'vipranks',
        name: 'vipranks',
        component: _import('base/vipranks/index')
      },
      {
        path: 'flighttasks',
        name: 'flighttasks',
        component: _import('base/flighttasks/index')
      },
      {
        path: 'flightServiceCode',
        name: 'flightServiceCode',
        component: _import('base/flightServiceCode/index')
      },
      {
        path: 'country',
        name: 'country',
        component: _import('base/country/index')
      },
      {
        path: 'airlinesBranch',
        name: 'airlinesBranch',
        component: _import('base/airlinesBranch/index')
      },
      {
        path: 'flightEventCode',
        name: 'flightEventCode',
        component: _import('base/flightEventCode/index')
      },
      {
        path: 'aircraftType',
        name: 'aircraftType',
        component: _import('base/aircraftType/index')
      },
      {
        path: 'aircraftCategory',
        name: 'aircraftCategory',
        component: _import('base/aircraftCategory/index')
      },
      {
        path: 'airportContact',
        name: 'airportContact',
        component: _import('base/airportContact/index')
      },
      {
        path: 'flightDirection',
        name: 'flightDirection',
        component: _import('base/flightDirection/index')
      },
      {
        path: 'flightNature',
        name: 'flightNature',
        component: _import('base/flightNature/index')
      },
      {
        path: 'flightStatus',
        name: 'flightStatus',
        component: _import('base/flightStatus/index')
      },
      {
        path: 'generalAgent',
        name: 'generalAgent',
        component: _import('base/generalAgent/index')
      },
      {
        path: 'resourceNature',
        name: 'resourceNature',
        component: _import('base/resourceNature/index')
      },
      {
        path: 'scheduleSource',
        name: 'scheduleSource',
        component: _import('base/scheduleSource/index')
      },
      {
        path: 'checkCounterServiceType',
        name: 'checkCounterServiceType',
        component: _import('base/checkCounterServiceType/index')
      }
    ]
  },
  {
    path: '/service',
    name: 'service',
    component: Layout,
    children: [
      {
        path: 'gateway',
        name: 'gateway',
        component: _import('service/gateway/index')
      },
      {
        path: 'oauthClientDetails',
        name: 'oauthClientDetails',
        component: _import('service/oauth-client/index')
      },
      {
        path: 'application',
        name: 'application',
        component: _import('service/application/index')
      }
    ]
  }
]
