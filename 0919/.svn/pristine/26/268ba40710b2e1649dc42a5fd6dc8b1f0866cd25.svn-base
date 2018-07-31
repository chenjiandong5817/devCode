<template>
  <li class="drag-tree-node">
    <template v-if="nodeData.placeholder">
      <div class="drag-tree-placeholder" :style="{height: `${placeholderHeight}px`}"></div>
    </template>
    <template v-else>
      <div class="drag-tree-handle" :class="{'drag-tree-handle--nodrag': !treeDragging}" @mousedown.self="dragStart">
        <i v-if="collapsable && hasChildren"  @click="toggle" :class="['clickable', {
          'el-icon-arrow-right': nodeData.collapsed,
          'el-icon-arrow-down': !nodeData.collapsed
        }]"></i>
        {{ nodeData[nameLabel] }}
        <div class="right-button">
          <el-button type="primary" size="mini" icon="plus" @click="insert" v-if="isAddable"></el-button>
          <el-button type="success" size="mini" icon="edit" @click="edit" v-if="isEditable"></el-button>
          <el-button type="danger" size="mini" icon="close" @click="remove" v-if="isRemovable"></el-button>
        </div>
      </div>
      <ol drag-tree-nodes class="drag-tree-nodes" :class="{hidden: nodeData.collapsed}">
        <drag-tree-node v-for="(node, index) in nodeData[childrenLabel]" :key="index" :nodeData="node" :parentNodeData="nodeData"></drag-tree-node>
      </ol>
    </template>
  </li>
</template>
<script>
  import DomHelper from './dom-helper'
  export default {
    name: 'DragTreeNode',
    props: {
      nodeData: Object,
      parentNodeData: Object
    },
    // components: {
    //   DragTreeNodes
    // },
    data () {
      return {
        // 这个属性已经失去作用，真正控制搜索状态的是在nodeData.collapsed
        collapsed: false,
        isDragging: false,
        dragElm: null,
        // 移动坐标对象
        pos: null,
        firstMoving: true,
        dragInfo: null
      }
    },
    computed: {
      collapsable () {
        return this.rootVm.collapsable
      },
      moveSameLevel () {
        return this.rootVm.moveSameLevel
      },
      isAddable () {
        if (typeof this.rootVm.addable === 'function') {
          return this.rootVm.addable(this.nodeData)
        }
        return this.rootVm.addable
      },
      isEditable () {
        if (typeof this.rootVm.editable === 'function') {
          return this.rootVm.editable(this.nodeData)
        }
        return this.rootVm.editable
      },
      isRemovable () {
        if (typeof this.rootVm.removable === 'function') {
          return this.rootVm.removable(this.nodeData)
        }
        return this.rootVm.removable
      },
      hasChildren () {
        return this.nodeData[this.childrenLabel] && this.nodeData[this.childrenLabel].length > 0
      },
      isPlaceholder () {
        return this.nodeData.placeholder
      },
      rootVm () {
        return this.recursiveRoot(this)
      },
      treeDragging () {
        return this.rootVm.store.isDragging
      },
      rootTreeData () {
        return {
          [this.nameLabel]: '',
          root: true,
          [this.childrenLabel]: this.rootVm.store.data || []
        }
      },
      placeholderHeight () {
        return this.rootVm && this.rootVm.store.placeholderHeight > 0 ? this.rootVm.store.placeholderHeight : 15
      },
      nameLabel () {
        return this.rootVm.nameLabel
      },
      childrenLabel () {
        return this.rootVm.childrenLabel
      }
    },
    methods: {
      // 递归获取root节点
      recursiveRoot (vm) {
        if (!vm.$parent) {
          return null
        }
        if (vm.$parent.$options.name === 'DragTree') {
          return vm.$parent
        } else {
          return this.recursiveRoot(vm.$parent)
        }
      },
      // 切换节点展开收缩状态
      toggle () {
        this.collapsed = !this.collapsed
        this.$set(this.nodeData, 'collapsed', this.collapsed)
      },
      // store node height
      setPlaceHolderHeight (val) {
        this.rootVm.setPlaceholderHeight(val)
      },
      // 拖拽状态
      setDragTreeStatus (val) {
        this.isDragging = val
        this.rootVm.setDragStatus(val)
      },
      // get node's parent by store
      getNodeParent (node, rootNode, curArgs = {notRoot: false, match: null, isMatch: false}) {
        // debugger
        if (node.root) {
          return curArgs
        }
        let list = rootNode[this.childrenLabel]
        for (let i = 0; i < list.length; i++) {
          if (curArgs.isMatch) {
            break
          }
          let item = list[i]
          if (item === node) {
            curArgs.match = item
            break
          } else if (item[this.childrenLabel] && item[this.childrenLabel].length > 0) {
            let isRootBefore = !curArgs.notRoot
            if (isRootBefore) {
              curArgs.notRoot = true
            }
            let matchAfter = this.getNodeParent(node, item, curArgs)
            if (!curArgs.isMatch && matchAfter.match) {
              curArgs.match = item
              curArgs.isMatch = true
            }
            if (isRootBefore) {
              curArgs.notRoot = !isRootBefore
            }
          }
        }
        if (!curArgs.notRoot && node === curArgs.match) {
          curArgs.match = rootNode
          curArgs.isMatch = true
        }
        return curArgs
      },
      // 增加节点事件
      insert () {
        this.rootVm.$emit('insertNode', this.nodeData)
      },
      // 编辑节点事件
      edit () {
        this.rootVm.$emit('editNode', this.nodeData)
      },
      // 删除节点操作，需要从父节点操作
      remove () {
        let children = this.parentNodeData[this.childrenLabel]
        let index = -1
        for (let i = 0; i < children.length; i++) {
          if (this.nodeData === children[i]) {
            index = i
            break
          }
        }
        if (index > -1) {
          this.parentNodeData[this.childrenLabel].splice(index, 1)
        }
        // remove callback
        this.rootVm.$emit('removeNode', this.nodeData)
      },
      bindDragMoveEvent () {
        document.bind('mouseup', this.dragEnd)
        document.bind('mousemove', this.dragMove)
      },
      unbindDragMoveEvent () {
        document.unbind('mouseup', this.dragEnd)
        document.unbind('mousemove', this.dragMove)
      },
      dragStart (evt) {
        this.setDragTreeStatus(true)
        this.dragInfo = DomHelper.dragInfo(this.nodeData, this.parentNodeData)
        // console.log('dragInfo', this.dragInfo)
        let index = DomHelper.jsonIndex(this.nodeData, this.parentNodeData[this.childrenLabel])
        // 隐藏当前节点
        // this.nodeData.hidden = true
        // 加入替换节点 index + 1 => index, 1
        this.parentNodeData[this.childrenLabel].splice(index, 1, Object.assign({}, this.nodeData, {placeholder: true}))
        this.setPlaceHolderHeight(this.$el.offsetHeight)
        // 创建拖动节点
        let el = this.$el
        let cloneEl = el.cloneNode(true)
        let parentEl = el.parentNode
        this.dragElm = document.createElement(parentEl.tagName).addClass(parentEl.className).addClass('drag-tree-node-drag')
        this.dragElm.css('width', `${el.offsetWidth}px`)
        this.dragElm.css('z-index', 9999)
        this.pos = DomHelper.positionStarted(evt, el)
        this.dragElm.css({
          left: `${evt.pageX - this.pos.offsetX}px`,
          top: `${evt.pageY - this.pos.offsetY}px`
        })
        this.dragElm.append(cloneEl)
        document.querySelector('body').append(this.dragElm)
        // 绑定dragMove事件
        this.bindDragMoveEvent()
      },
      dragMove (evt) {
        if (!this.isDragging) return true
        // console.log('move')
        let leftElmPos = evt.pageX - this.pos.offsetX
        let topElmPos = evt.pageY - this.pos.offsetY
        this.dragElm.css({
          left: `${leftElmPos}px`,
          top: `${topElmPos}px`
        })
        // 判断移动方向
        DomHelper.positionMoved(evt, this.pos)
        if (this.pos.dirAx && !this.moveSameLevel) { // 水平移动
          if (this.pos.distX > 0) { // 向右移动
            let prev = this.dragInfo.prev()
            if (prev) {
              let children = prev[this.childrenLabel]
              if (children) {
                this.dragInfo.moveTo(prev, prev[this.childrenLabel], prev[this.childrenLabel].length)
              }
            }
          }
          if (this.pos.distX < 0) { // 向左移动
            let next = this.dragInfo.next()
            if (!next) {
              let target = this.dragInfo.parent
              if (target) {
                let parent = this.getNodeParent(target, this.rootTreeData).match
                if (parent) {
                  this.dragInfo.moveTo(parent, parent[this.childrenLabel], DomHelper.jsonIndex(target, parent[this.childrenLabel]) + 1)
                }
              }
            }
          }
        } else { // 垂直移动
          if (this.pos.distY > 0) {
            let next = this.dragInfo.next()
            if (next) {
              if (this.moveSameLevel) {
                let target = this.dragInfo.parent
                this.dragInfo.moveTo(target, target[this.childrenLabel], DomHelper.jsonIndex(next, target[this.childrenLabel]))
              } else if (next[this.childrenLabel]) {
                this.dragInfo.moveTo(next, next[this.childrenLabel], 0)
              }
            } else if (!this.moveSameLevel) { // 寻找父节点的同级节点
              let target = this.dragInfo.parent
              if (target) {
                let parent = this.getNodeParent(target, this.rootTreeData).match
                if (parent) {
                  this.dragInfo.moveTo(parent, parent[this.childrenLabel], DomHelper.jsonIndex(target, parent[this.childrenLabel]) + 1)
                }
              }
            }
          }
          if (this.pos.distY < 0) {
            let prev = this.dragInfo.prev()
            if (prev) {
              if (this.moveSameLevel) {
                let target = this.dragInfo.parent
                this.dragInfo.moveTo(target, target[this.childrenLabel], DomHelper.jsonIndex(prev, target[this.childrenLabel]))
              } else if (prev[this.childrenLabel]) {
                this.dragInfo.moveTo(prev, prev[this.childrenLabel], prev[this.childrenLabel].length)
              }
            } else if (!this.moveSameLevel) {
              let target = this.dragInfo.parent
              if (target) {
                let parent = this.getNodeParent(target, this.rootTreeData).match
                if (parent) {
                  this.dragInfo.moveTo(parent, parent[this.childrenLabel], DomHelper.jsonIndex(target, parent[this.childrenLabel]))
                }
              }
            }
          }
        }
      },
      dragEnd (evt) {
        this.unbindDragMoveEvent()
        this.setDragTreeStatus(false)

        if (this.dragElm) {
          this.dragInfo.apply()
          this.$nextTick(() => {
            this.dragElm.remove()
            this.dragElm = null

            this.dragInfo = null
          })
        }
      }
    }
  }
</script>
<style>
.drag-tree-node .clickable {
  cursor: pointer;
}
.drag-tree-node, .drag-tree-placeholder {
  position: relative;
  margin: 0;
  padding: 0;
  min-height: 20px;
  line-height: 20px;
}
.drag-tree-placeholder {
  background: #f0f9ff;
  border: dashed 2px #bed2db;
  box-sizing: border-box;
}
.drag-tree-node .drag-tree-handle {
  overflow: hidden;
  user-select: none;
  display: block;
  padding: 10px 15px;
  margin-bottom: -1px;
  background-color: #fff;
  border: 1px solid #ddd;
  cursor: move;
}
.drag-tree-node .drag-tree-handle--nodrag:hover {
  background: #727272;
  color: #fff;
}
.drag-tree-node .right-button {
  float: right;
}
.drag-tree-node .right-button button {
  margin-left: 3px;
}
.drag-tree-node-drag {
  position: absolute;
  pointer-events: none;
  z-index: 999;
  opacity: .8;
}
</style>
