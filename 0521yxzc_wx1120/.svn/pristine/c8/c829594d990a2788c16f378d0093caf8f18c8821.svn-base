import Vue from 'vue'
import Router from 'vue-router'
import index from '@/view/userCenter/app'
import view from '@/view/routerView'
import myWallet from '@/view/userCenter/myWallet'
import myCoupon from '@/view/userCenter/myCoupon'
import invoice from '@/view/userCenter/invoice'
import invoiceTrip from '@/view/userCenter/invoiceTrip'
import invoiceMoney from '@/view/userCenter/invoiceMoney'
import invoiceHistory from '@/view/userCenter/invoiceHistory'
import invoiceHistoryDesc from '@/view/userCenter/invoiceHistoryDesc'
import invoiceHistoryTrip from '@/view/userCenter/invoiceHistoryTrip'
import invoiceForm from '@/view/userCenter/invoiceForm'
import invoiceFormMore from '@/view/userCenter/invoiceFormMore'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      component: index,
      children: [{
        path: '/',
        component: myWallet
      }]
    },
    // 发票
    {
      path: '/invoice',
      component: view,
      children: [
        {
          path: '/',
          component: invoice
        },
        {
          path: 'invoiceTrip',
          component: invoiceTrip
        },
        {
          path: 'invoiceMoney',
          component: invoiceMoney
        },
        {
          path: 'invoiceHistory',
          component: invoiceHistory
        },
        {
          path: 'invoiceHistoryDesc/:uuid',
          // path: 'invoiceHistoryDesc',
          component: invoiceHistoryDesc
        },
        {
          path: 'invoiceHistoryTrip/:uuid',
          // path: 'invoiceHistoryTrip',
          component: invoiceHistoryTrip
        },
        {
          path: 'invoiceForm',
          component: view,
          children: [
            {
              path: '/',
              component: invoiceForm
            },
            {
              path: 'invoiceFormMore',
              component: invoiceFormMore
            }]
        }
      ]
    },
    // 行程发票
    // 我的钱包
    {
      path: '/myWallet',
      component: myWallet
    },
    // // 我的电子券
    {
      path: '/myCoupon',
      component: myCoupon
    }
  ]
})
