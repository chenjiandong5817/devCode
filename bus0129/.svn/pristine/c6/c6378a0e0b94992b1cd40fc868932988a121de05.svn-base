<template>
  <div drag-tree class="drag-tree">
    <ol drag-tree-nodes class="drag-tree-nodes">
      <drag-tree-node v-for="node in root.children" :key="node.name" :nodeData="node" :parentNodeData="root" @setPlaceholderHeight="setPlaceholderHeight"></drag-tree-node>
    </ol>
  </div>
</template>
<script>
  import DragTreeNode from './DragTreeNode'
  // import DomHelper from './dom-helper'
  export default {
    name: 'DragTree',
    props: {
      list: Array,
      nameLabel: {
        type: String,
        default: 'name'
      },
      childrenLabel: {
        type: String,
        default: 'children'
      },
      addable: [Boolean, Function],
      removable: [Boolean, Function],
      editable: [Boolean, Function],
      moveSameLevel: Boolean,
      collapsable: {
        type: Boolean,
        default: true
      }
    },
    data () {
      return {
        root: null,
        store: {
          data: this.list,
          placeholderHeight: 0,
          isDragging: false
        }
      }
    },
    components: {
      DragTreeNode
    },
    computed: {
      pageTreeIsDragging () {
        return this.$store.state.dragTreeNodeIsDragging
      }
    },
    methods: {
      // 设置drag元素的高度
      setPlaceholderHeight (height) {
        this.$set(this.store, 'placeholderHeight', height)
      },
      // 设置drag元素的状态
      setDragStatus (status) {
        this.$set(this.store, 'isDragging', status)
      },
      // 增加节点事件
      insert (node, index) {
        this.list.splice(index, 0, node)
        // console.log('after insert ', this.list)
      }
    },
    created () {
      this.root = {
        [this.nameLabel]: '',
        [this.childrenLabel]: this.list
      }
    },
    mounted () {
      // this.storeRootData(this.list)
    }
  }
</script>
<style>
.drag-tree-nodes {
    position: relative;
    margin: 0;
    padding: 0;
    list-style: none;
}
.drag-tree-nodes.hidden {
    display: none;
    visibility: hidden;
}
.drag-tree-nodes .drag-tree-nodes {
    padding-left: 35px;
}
.drag-tree-placeholder div{
  user-select: none;
  display: block;
  margin-bottom: -1px;
  background-color : transparent;
}
</style>
