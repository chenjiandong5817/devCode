import Vue from 'vue'
import Router from 'vue-router'
// import Hello from '@/components/Hello'
import TemplateRender from '@/components/TemplateRender'
import MqClient from '@/components/bak/MqClient'
import ListClick from '@/components/bak/List-click'
import DragDropMenu from '@/components/drag-drop/Menu'
import Canvas from '@/components/editor/Canvas'
import DragDemo from '@/components/draggable/Demo'
import TreeEditor from '@/components/draggable/TreeEditor'
import DragTreeMain from '@/components/dragTree/Main'

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
      // 作废
      path: '/fids-maker',
      name: 'Menu',
      component: DragDropMenu
    },
    {
      path: '/',
      name: 'list-click',
      component: ListClick
    },
    {
      path: '/canvas',
      name: 'canvas',
      component: Canvas
    },
    {
      path: '/demo',
      name: 'demo',
      component: DragDemo
    },
    {
      path: '/editor',
      name: 'editor',
      component: TreeEditor
    },
    {
      path: '/tree',
      name: 'tree',
      component: DragTreeMain
    }
  ]
})
