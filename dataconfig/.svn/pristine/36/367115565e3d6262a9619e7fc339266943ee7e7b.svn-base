<template>
  <div :style="style" style="display: block;" v-show="show" class="right-menu" 
    @mousedown.stop
    @contextmenu.prevent
  >
    <!-- <a v-for="item in menus" :key="item.label">{{ item.label }}<span style="float: right;">{{ (item.children ? '>' : '') }}</span></a> -->
    <div>
      <div class="right-main-menu-title">{{currentDom && currentDom.id ? currentDom.id.split('_')[0] : ''}}</div>
      <ul class="right-main-menu">
        <li v-for="(item, index) in menus" :key="item.label" @mouseenter.stop="hanldeLiMouseEnter(index, $event)" @click.prevent.stop="menuEventAdaptor(item.on)">
          {{ item.label }} <span style="float: right">{{item.children ? '>' : ''}}</span>
          <div class="right-sub-menu" style="display: none;" v-if="item.children">
            <ul v-if="item.children">
              <li v-for="subItem in item.children" :key="subItem.label" @click.prevent.stop="menuEventAdaptor(subItem.on)">{{ subItem.label }}</li>
            </ul>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import Tools from './../../../common/js/template-tools'
import Template from './../../drag-drop/templates'
export default {
  name: 'context-menu',
  data () {
    return {
      triggerShowFn: () => {},
      triggerHideFn: () => {},
      x: null,
      y: null,
      style: {},
      binded: false,
      currentDom: null,
      menus: [
        {
          label: '插入',
          children: [
            {
              label: '行',
              on: {
                type: 'event-part-insert',
                args: ['insert', 'Row']
              }
            },
            {
              label: '登机口模板'
            }
          ]
        },
        {
          label: '插入模板',
          children: [
            {
              label: '航班展示模板',
              on: {
                type: 'event-template-insert',
                args: ['replace', 'Layout']
              }
            },
            {
              label: '登机口模板'
            }
          ]
        }
      ]
    }
  },
  props: {
    target: null,
    show: Boolean
  },
  mounted () {
    this.bindEvents()
  },
  watch: {
    show (show) {
      if (show) {
        this.bindHideEvents()
      } else {
        this.unbindHideEvents()
      }
    },
    target (target) {
      this.bindEvents()
    }
  },
  methods: {
    // event-template-* 参数为type操作类型 和 templateType模板类型
    // event-insert
    menuEventAdaptor (on) {
      console.log(on)
      if (!on || !on.type) return
      switch (on.type) {
        case 'event-template-insert':
          this.$emit('changeTemplate', on.args[0], Template(on.args[1]))
          break
        case 'event-part-insert':
          this.$emit('changeTemplate', on.args[0], Template(on.args[1]), this.currentDom.id)
          break
        default:
          console.log('did not adapt')
      }
      this.clickDocumentHandler()
    },
    // 二级菜单显示
    hanldeLiMouseEnter (index, event) {
      let target = event.target
      let ul = target.getElementsByTagName('div')[0]
      if (!ul) {
        this.hideAllSubMenu()
        return
      }
      if (ul.style.display === 'block') {
        return
      }
      this.hideAllSubMenu()
      ul.style.display = 'block'
      ul.style.marginTop = -31 + 'px'
    },
    // 隐藏所有的子菜单
    hideAllSubMenu () {
      let mainUl = this.$el.querySelector('.right-main-menu')
      let subUls = mainUl.querySelectorAll('div')
      for (let i = 0; i < subUls.length; i++) {
        if (subUls[i].style.display === 'none') continue
        subUls[i].style.display = 'none'
        break
      }
    },
    // 初始化事件
    bindEvents () {
      this.$nextTick(() => {
        if (!this.target || this.binded) return
        this.triggerShowFn = this.contextMenuHandler.bind(this)
        this.target.addEventListener('contextmenu', this.triggerShowFn)
        this.binded = true
      })
    },
    // 取消绑定事件
    unbindEvents () {
      if (!this.target) return
      this.target.removeEventListener('contextmenu', this.triggerShowFn)
    },
    // 绑定隐藏菜单事件
    bindHideEvents () {
      this.triggerHideFn = this.clickDocumentHandler.bind(this)
      document.addEventListener('mousedown', this.triggerHideFn)
      document.addEventListener('mousewheel', this.triggerHideFn)
    },
    // 取消绑定隐藏菜单事件
    unbindHideEvents () {
      document.removeEventListener('mousedown', this.triggerHideFn)
      document.removeEventListener('mousewheel', this.triggerHideFn)
    },
    // 鼠标按压事件处理器
    clickDocumentHandler (e) {
      if (e && this.$el.contains(e.target)) return
      this.$emit('update:show', false)
      this.hideAllSubMenu()
    },
    // 右键事件事件处理
    contextMenuHandler (e) {
      e.preventDefault()
      if (this.$el.contains(e.target)) return
      this.currentDom = Tools.searchIdTarget(e.target)
      this.x = e.clientX
      this.y = e.clientY
      this.layout()
      this.$emit('update:show', true, e)
    },
    // 布局
    layout () {
      this.style = {
        left: this.x + 'px',
        top: this.y + 'px'
      }
    }
  }
}
</script>
<style>
.right-menu, .right-sub-menu {
  position: fixed;
  background: #fff;
  z-index: 999;
  display: none;
  border: 1px solid #eee;
  box-shadow: 0 0.5em 1em 0 rgba(0,0,0,.1);
  border-radius: 1px;
}
.right-sub-menu {
  margin-left: 107px;
}
.right-menu ul, .right-sub-menu ul{  
   list-style: none;
   margin: 0;
   padding: 0;
}
.right-menu ul li, .right-sub-menu ul li, .right-main-menu-title{   
  width: 105px;
  height: 28px;
  line-height: 28px;
  text-align: center;
  display: block;
  color: #1a1a1a;
  text-decoration: none;
  padding: 2px;
  cursor: pointer;
  user-select: none;
} 
.right-main-menu-title {
  font-weight: bold;
}
.right-menu ul li:hover, .right-sub-menu ul li:hover  {
  /* background: #eee; */
  background: #42b983;
  color: #fff;
}
</style>
