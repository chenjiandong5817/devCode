<template>
  <li class="drag-tree-node" @mousedown="dragStart">
    <div class="node-label">
      <i v-if="hasChildren"  @click="toggle" :class="['clickable', {
        'el-icon-arrow-right': collapsed,
        'el-icon-arrow-down': !collapsed
      }]"></i>
      {{ nodeData.name }}
      <el-button type="danger" size="mini" class="right-button" icon="close" @click="handleDelNode"></el-button>
      <el-button type="primary" size="mini" class="right-button" icon="plus" @click="handleAddNode"></el-button>
    </div>
    <drag-tree-nodes :list="nodeData.children" v-if="hasChildren" :collapsed="collapsed"></drag-tree-nodes>
  </li>
</template>
<script>
  import DragTreeNodes from './DragTreeNodes'
  export default {
    name: 'DragTreeNode',
    props: {
      nodeData: Object
    },
    data () {
      return {
        collapsed: false,
        isDragging: false,
        lastX: null,
        lastY: null
      }
    },
    computed: {
      hasChildren () {
        return this.nodeData.children && this.nodeData.children.length > 0
      }
    },
    components: {
      DragTreeNodes
    },
    methods: {
      // 切换节点展开收缩状态
      toggle () {
        this.collapsed = !this.collapsed
      },
      // 增加节点事件
      handleAddNode () {
        !this.nodeData.children && (this.nodeData.children = [])
        this.nodeData.children.push({
          name: 'new add',
          children: []
        })
      },
      // 删除节点操作，需要从父节点操作
      handleDelNode (evt) {
        this.$emit('removeNode', this)
      },
      insertAfter (node, target) {
        let parent = target.parentNode
        if (parent.lastChild === target) {
          parent.appendChild(node)
        } else {
          parent.insertBefore(node, target.nextSibling)
        }
      },
      dragStart (evt) {
        this.isDragging = true
        let el = this.$el // evt.target
        let cloneEl = el.cloneNode(true)
        let parentEl = el.parentNode
        // 创建隐藏节点
        let hiddenPlaceElm = document.createElement(el.tagName)
        hiddenPlaceElm.classList.add('drag-tree-node-hidden')
        // 创建替换节点
        let placeElm = document.createElement(el.tagName)
        placeElm.classList.add('drag-tree-placeholder')
        placeElm.style.height = `${el.offsetHeight}px`
        // 插入替换节点和隐藏节点
        this.insertAfter(placeElm, el)
        this.insertAfter(hiddenPlaceElm, el)
         // 创建父节点
        let dragElm = document.createElement(parentEl.tagName)
        dragElm.classList.add('drag-tree-node-drag', parentEl.className)
        // dragElm.style.height = `${el.offsetHeight}px`
        dragElm.style.width = `${el.offsetWidth}px`
        dragElm.style.zIndex = 9999
        // 克隆本节点，加入drag节点
        dragElm.appendChild(cloneEl)
        let targetRect = evt.target.getBoundingClientRect()
        dragElm.style.left = `${targetRect.left}px`
        dragElm.style.top = `${targetRect.top}px`
        // 加到body节点下
        document.querySelector('body').appendChild(dragElm)
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
.drag-tree-node .node-label {
  user-select: none;
  display: block;
  padding: 10px 15px;
  margin-bottom: -1px;
  background-color: #fff;
  border: 1px solid #ddd;
  cursor: move;
}
.drag-tree-node .node-label:hover {
  background: #727272;
  color: #fff;
}
.drag-tree-node .right-button {
  float: right;
  margin-right: 10px;
}
.drag-tree-node-drag {
  position: absolute;
  pointer-events: none;
  z-index: 999;
  opacity: .8;
}
.drag-tree-node-hidden {
  display: none;
}
</style>
