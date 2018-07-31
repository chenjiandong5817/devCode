import Contextmenu from '@/components/contextMenu/Contextmenu.vue'
import ContextmenuItem from '@/components/contextMenu/ContextmenuItem.vue'
import ContextmenuSubmenu from '@/components/contextMenu/ContextmenuSubmenu.vue'
import ContextmenuGroup from '@/components/contextMenu/ContextmenuGroup.vue'

export default {
  install (Vue) {
    let directive = (el, binding, vnode) => {
      const contextmenu = vnode.context.$refs[binding.arg]
      let array = [el]
      // 增加el-tabls 的特例情况
      if (el.className.includes('el-tabs')) {
        array = el.querySelectorAll('.el-tabs__nav')
      }
      if (Array.isArray(contextmenu.$refs.reference)) {
        // contextmenu.$refs.reference.push(el)
        array.forEach(item => {
          contextmenu.$refs.reference.push(item)
        })
      } else {
        contextmenu.$refs.reference = array
      }
      contextmenu.$contextmenuId = el.id || contextmenu._uid // eslint-disable-line no-underscore-dangle
    }

    Vue.directive('contextmenu', directive)
    Vue.component(Contextmenu.name, Contextmenu)
    Vue.component(ContextmenuItem.name, ContextmenuItem)
    Vue.component(ContextmenuSubmenu.name, ContextmenuSubmenu)
    Vue.component(ContextmenuGroup.name, ContextmenuGroup)
  }
}
