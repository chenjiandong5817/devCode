<template>
  <div class="editor-canvas">
    <div v-if="Tools.emptyObject(templateConf)" style="text-align: center; vertical-align: middle;">请在此处右键操作</div>
    <layout-render :templateConf="templateConf" v-else></layout-render>
    <context-menu
      :target="contextMenuTarget" 
      :show="contextMenuVisible"
      @update:show="(show) => contextMenuVisible = show"
      ref="contextMenu">
    </context-menu>
  </div>
</template>
<script>
  import LayoutRender from './LayoutRender'
  import ContextMenu from './contextMenu/ContextMenu'
  import Tools from './../../common/js/template-tools'
  export default {
    data () {
      return {
        Tools,
        templateConf: {},
        contextMenuTarget: document.body,
        contextMenuVisible: false,
        childColumn: ['rows', 'cols', 'children', 'columns']
      }
    },
    components: {
      LayoutRender, ContextMenu
    },
    methods: {
      changeTempalte(type = 'insert', conf, partId) {
        switch (type) {
          case 'replace':
            this.templateConf = Tools.deepCopy(conf)
            break
          case 'insert':
            console.log(conf)
            this.operateTemplate(partId, this.templateConf, 'add', conf)
            break
          default:
            console.log('do nothing')
        }
      },
      // query不需要参数, add需要目标配置参数，edit需要新修改完配置参数，delete需要当前目标的父级配置参数
      operateTemplate (timestamp, baseConf, optType, optConf) {
        if (!['add', 'edit', 'delete', 'query'].includes(optType)) {
          optType = 'query'
        }
        let obj = (baseConf.timestamp === timestamp) ? baseConf : null
        let objChild = this.searchChildrenArray(baseConf) || []
        if (obj) { // 当前对象有值，进行子元素操作
          console.log('operate: ', optType)
          switch (optType) {
            case 'add':
              optConf && objChild.push(optConf)
              break
            case 'edit':
              // console.log(baseConf, optConf)
              // Object.assign(baseConf, Tools.deepCopy(optConf))
              this.editConfEveryProp(baseConf, optConf)
              break
            case 'delete':
              optConf && optConf.splice(this.searchChildrenIndex(optConf, obj), 1)
              break
            default:
              // console.log('query to do nothing')
          }
        } else { // 当前对象未匹配，继续寻找子节点
          for (let i = 0; i < objChild.length; i++) {
            obj = this.operateTemplate(timestamp, objChild[i], optType, optType === 'delete' ? objChild : optConf)
            if (obj) {
              break
            }
          }
        }
        return obj
      },
      // 先删掉老数据的多余字段，在覆盖，否则会出现老数据残余字段的情况
      editConfEveryProp (oldConf, newConf = {}) {
        let newKeys = Object.keys(newConf)
        for (var key in oldConf) {
          if (!newKeys.includes(key)) {
            this.$delete(oldConf, key)
          }
        }
        Object.assign(oldConf, newConf)
      },
      // 找到对象的子元素集合
      searchChildrenArray (obj) {
        let array = null
        for (var key in obj) {
          if (!this.childColumn.includes(key)) {
            continue
          }
          array = obj[key]
        }
        return array
      },
      // 找到对象的某个子元素下标
      searchChildrenIndex (array, obj) {
        let index = -1
        if (!array || !obj) { return index }
        for (let i = 0; i < array.length; i++) {
          if (array[i].timestamp === obj.timestamp) {
            index = i
            break
          }
        }
        return index
      }
    },
    mounted () {
      this.$refs.contextMenu.$on('changeTemplate', this.changeTempalte)
    }
  }
</script>
<style>
.editor-canvas {
  background-color: #9b9999;
  height: 100%;
  min-height: 100px;
  font-size: 14px;
}
</style>
