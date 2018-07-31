<template>
  <div class="drag-drop-menu">
    <div class="drag-drop-menu-select">
      <el-radio-group v-model="pageScale">
        <el-radio :label="1">正常</el-radio>
        <el-radio :label="2">大面积面板</el-radio>
      </el-radio-group>
      <ul>
        <template v-for="(item, index) in tags">
          <li v-if="!item.hidden" :key="item.name" class="drag-drop-select-item" draggable="true" @dragstart="onDrag($event, index)">{{ item.dt }}</li>
        </template>
      </ul>
    </div>
    <div class="drag-drop-menu-layout"
      id="Main_20171109"
      @drop="onDrop"
      @dragover="onDragOver"
      @dragenter="onDragEnter"
      @dragleave="onDragLeave"
      :style="{
        width: `${89 - (pageScale) * 11}%`
      }"
      ref="menuLayout">
      <layout-render :templateConf="template" ref="layoutRender"></layout-render>
    </div>
    <div class="drag-drop-menu-edit" :style="{
        width: `${11 * pageScale}%`
      }">
      <editor ref="templateEditor"></editor>
    </div>
  </div>
</template>
<script>
import LayoutRender from './LayoutRender'
import Templates from './templates'
import Editor from './TemplateEdit'
import Tools from './../../common/js/template-tools'
export default {
  data () {
    return {
      pageScale: 1,
      tags: [
        {name: 'Layout', dt: '最外层布局', hidden: true},
        {name: 'Row', dt: '行'},
        {name: 'Column', dt: '列'},
        {name: 'Grid', dt: '表格'}
        // ,{name: 'GridColumn', dt: '表格-单元格'}
      ],
      childColumn: ['rows', 'cols', 'children', 'columns'],
      typeSetting: {
        Main: ['Layout'],
        Layout: ['Row'],
        Row: ['Column'],
        Column: ['Row', 'Grid'],
        Grid: ['GridColumn'],
        GridColumn: ['Row', 'Column']
      },
      dropable: true,
      template: {},
      currentDrag: null
    }
  },
  components: {
    LayoutRender, Editor
  },
  methods: {
    toogleDropable (dropable) {
      this.dropable = dropable
    },
    editTemplate (partConf) {
      // Object.assign(this.operateTemplate(partConf.timestamp, this.template, 'query'), Tools.deepCopy(partConf))
      console.log('主页面修改模板')
      this.operateTemplate(partConf.timestamp, this.template, 'edit', Tools.deepCopy(partConf))
      console.log(JSON.stringify(this.template))
    },
    deleteTemplate (partConf) {
      this.operateTemplate(partConf.timestamp, this.template, 'delete')
      console.log(this.template)
    },
    onDrag (event, index) {
      // console.log(event)
      this.currentDrag = this.tags[index]
    },
    onDragOver (event) {
      event.preventDefault()
    },
    onDragEnter (event) {
      this.dropable = false
      // 判断是否可以放下
      let underElementType = event.target.id.split('_')[0]
      this.dropable = this.currentDrag && underElementType && (!this.typeSetting[underElementType] || this.typeSetting[underElementType].includes(this.currentDrag.name))
      console.log('dropable =', this.dropable)

      this.$emit('toogleDropable', this.dropable)
      this.dropable && (Tools.searchIdTarget(event.target).style.border = 'dashed 4px #ff9000')
    },
    onDragLeave (event) {
      Tools.searchIdTarget(event.target).style.border = null
    },
    onDrop (event) {
      event.preventDefault()
      event.stopPropagation()
      if (!this.dropable) { return }
      console.log('current insert element = ', event.target.id)
      let item = this.currentDrag || {}
      // let conf = Templates(item.name)
      // console.log('drop', conf)
      // this.operateTemplate(event.target.id, this.template, 'add', Tools.deepCopy(conf))
      this.addTemplateByName(item.name, event.target.id)
      // 默认加入Layout配置，这行代码无效
      // if (!this.template.timestamp) {
      //   this.template = conf
      // }
      this.onDragLeave(event)
      this.currentDrag = null
      console.log(this.template)
    },
    addTemplateByName (name, targetId) {
      let conf = Templates(name)
      this.operateTemplate(targetId, this.template, 'add', Tools.deepCopy(conf))
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
            console.log(baseConf, optConf)
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
          if (obj) break
        }
      }
      return obj
    },
    // 先删掉老数据的多余字段，在覆盖，否则会出现老数据残余字段的情况
    editConfEveryProp (oldConf, newConf = {}) {
      // 剔除oldConf的多余字段
      let newKeys = Object.keys(newConf)
      for (let key in oldConf) {
        if (!newKeys.includes(key)) {
          this.$delete(oldConf, key)
        }
      }
      for (let key of newKeys) {
        this.$set(oldConf, key, newConf[key])
      }
      // let oldKeys = Object.keys(oldConf)
      // for (let key in newConf) {
      //   if (!oldKeys.includes(key)) {
      //     this.$set(oldConf, key, newConf[key])
      //   }
      // }
      // Object.assign(oldConf, newConf)
    },
    onElementClick (timestamp) {
      // console.log(this.template)
      this.$refs.templateEditor.changeCurrentEdit(this.operateTemplate(timestamp, this.template, 'query'))
    }
  },
  mounted () {
    this.$refs.layoutRender.$on('onElementClick', this.onElementClick)
    this.$refs.templateEditor.$on('editTemplate', this.editTemplate)
    this.$refs.templateEditor.$on('deleteTemplate', this.deleteTemplate)
    this.$nextTick(() => {
      this.template = Templates('Layout')
    })
  }
}
</script>

<style>
.drag-drop-menu {
  clear:both;
  background: #fff;
  position: relative;
  overflow: auto;
  min-width: 1366px;
  height: 960px;
}
.drag-drop-menu-select {
  vertical-align: top;
  display: inline-block;
  background: #d9dadb;
  height: 99%;
  width: 10%;
}
.drag-drop-menu-layout {
  overflow: auto;
  display: inline-block;
  border: none;
  height: 99%;
  width: 78%;
}
.drag-drop-menu-edit {
  overflow: auto;
  background: #fff;
  display: inline-block;
  border: solid 1px;
  height: 99%;
  width: 11%;
}
.drag-drop-select-item {
  margin: 2px;
  padding: 0 3px;
  background: #000000;
  color: #fff;
  width: auto;
}
</style>
