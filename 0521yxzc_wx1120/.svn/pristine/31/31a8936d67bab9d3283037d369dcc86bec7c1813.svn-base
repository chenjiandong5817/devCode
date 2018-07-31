import Vue from 'vue'
import Router from 'vue-router'
/* 主入口路由 */
import index from '@/view/index/app'
import view from '@/view/routerView'
import pickup from '@/view/index/pickup'
import dropoff from '@/view/index/dropoff'
/* 组件UI层路由 */
import flightPage from '@/components/flight/flightPage'
import suggest from '@/components/suggest/suggest'
import coupon from '@/components/common/coupon'
import payTypePick from '@/components/common/payTypePick'
import carTypePick from '@/components/common/carTypePick'
import estimateMsg from '@/components/common/estimateMsg'
import about from '@/view/about'
// import myWallet from '@/components/userSideBar/menuPages/myWallet'
// import invoice from '@/components/userSideBar/menuPages/invoice'
// import invoiceTrip from '@/components/userSideBar/menuPages/invoiceTrip'

Vue.use(Router)

export default new Router({
  routes: [
    // 默认首页
        // 默认接机(已在入口index执行进入pickup子路由)
    {
      path: '/',
      component: index
    },
        // 接机-子路由
    {
      path: '/pickup',
      component: view,
      children: [
        {
          path: '/',
          component: pickup
        },
        {
          path: 'suggest',
          component: suggest
        },
        {
          path: 'payTypePick',
          component: payTypePick
        },
        {
          path: 'carTypePick',
          component: carTypePick
        },
        {
          path: 'flightPage',
          component: flightPage
        },
        {
          path: 'coupon',
          component: coupon
        },
        {
          path: 'estimateMsg',
          component: estimateMsg
        }]
    },
        // 送机-子路由
    {
      path: '/dropoff',
      component: view,
      children: [
        {
          path: '/',
          component: dropoff
        },
        {
          path: 'suggest',
          component: suggest
        },
        {
          path: 'payTypePick',
          component: payTypePick
        },
        {
          path: 'carTypePick',
          component: carTypePick
        },
        {
          path: 'coupon',
          component: coupon
        },
        {
          path: 'estimateMsg',
          component: estimateMsg
        }]
    },
    // 发票
    {
      path: '/about',
      component: about
    }
    // 发票
//    {
//      path: '/invoice',
//      component: invoice
//    },
//    // 行程发票
//    {
//      path: '/invoiceTrip',
//      component: invoiceTrip
//    },
//    // 我的钱包
//    {
//      path: '/myWallet',
//      component: myWallet
//    }
  ]
})
