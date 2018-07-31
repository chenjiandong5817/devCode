import Vue from 'vue'
import Router from 'vue-router'
// import Hello from '@/components/Hello'
import TemplateRender from '@/components/TemplateRender'
import MqClient from '@/components/bak/MqClient'
import ListClick from '@/components/bak/List-click'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/:deviceId/view',
      name: 'view',
      component: TemplateRender
    },
    {
      path: '/mq',
      name: 'MqClient',
      component: MqClient
    },
    {
      path: '/',
      name: 'list-click',
      component: ListClick
    }
  ]
})
