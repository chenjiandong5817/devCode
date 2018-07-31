<template>
  <div class="tree-main">
    <drag-tree :list="list"></drag-tree>
  </div>
</template>
<script>
  import DragTree from './DragTree'
  const array = [
    'java', 'js', 'node', 'vue'
  ]
  export default {
    data () {
      return {
        list: array.map((name, index) => {
          return {name, children: name === 'js' ? [{name: 'angular', children: [{name: 'angular 1.0', children: []}]}] : []}
        })
      }
    },
    components: {
      DragTree
    }
  }
</script>
<style>
.tree-main {
  position: relative;
  padding: 20px 15px;
  border-top: solid 1px #d1d2d4;
  border-bottom: solid 1px #d1d2d4;
  max-height: 900px;
  overflow-y: auto;
  overflow-x: hidden;
  background: #ddd;
  /* border-radius: 4px; */
}
</style>
