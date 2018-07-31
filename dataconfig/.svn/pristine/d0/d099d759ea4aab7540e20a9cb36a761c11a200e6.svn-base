<template>
  <ol class="drag-tree-nodes" :class="{hidden: collapsed}">
    <drag-tree-node v-for="li in list" :key="li.name" :nodeData="li" @removeNode="removeNode"></drag-tree-node>
  </ol>
</template>
<script>
  export default {
    name: 'DragTreeNodes',
    props: {
      list: Array,
      collapsed: Boolean
    },
    methods: {
      removeNode (node) {
        let nodes = this.$children.filter(node => node.$options.name === 'DragTreeNode')
        let index = -1
        for (let i = 0; i < nodes.length; i++) {
          if (nodes[i] === node) {
            index = i
            break
          }
        }
        if (index > -1) {
          this.list.splice(index, 1)
        } else {
          this.$parent.$emit('removeNode', this)
        }
      }
    },
    beforeCreate () {
      // 解决互相递归调用组件时的template unregister错误
      this.$options.components.DragTreeNode = require('./DragTreeNode')
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
body li {
  list-style-type: none;
}
.drag-tree-nodes.hidden {
    display: none;
    visibility: hidden;
}
.drag-tree-nodes .drag-tree-nodes {
    padding-left: 35px;
}
</style>
