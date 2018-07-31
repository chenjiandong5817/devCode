<template>
  <el-row class="tree-editor-container">
    <el-col :span="8" style="width: 220px;">
      <draggable element="div" class="list-group-container" v-model="selectItems" :options="dragOptions" :move="onMove"  @start="isDragging=true" @end="isDragging=false">
        <transition-group type="transition" class="list-group" :name="'flip-list'" tag="ul">
          <template v-for="element in selectItems">
            <li class="list-group-item"  :key="element.order" @mousemove="handleMouseMove"> 
              {{element.name}}
              <!-- <span class="badge">{{element.order}}</span> -->
            </li> 
          </template>
        </transition-group>
      </draggable>
    </el-col>
    <el-col :span="10" :offset="1">
      <draggable element="div" class="list-group-container" v-model="selectItemsCopy" :options="targetOptions" :move="onMove">
        <transition-group name="no" class="list-group" tag="ul">
          <template v-for="element in selectItemsCopy">
            <li class="list-group-item"  :key="element.order"> 
              {{element.name}}
              <draggable v-if="element.children" element="div" class="list-group-container" v-model="element.children" :options="targetOptions" :move="onMove"  @start="isDragging=true" @end="isDragging=false">
                <transition-group type="transition" class="list-group" :name="'flip-list'" tag="ul">
                  <template v-for="subElement in element.children">
                    <li class="list-group-item"  :key="subElement.order"> 
                      {{subElement.name}}
                      <!-- <span class="badge">{{element.order}}</span> -->
                    </li> 
                  </template>
                </transition-group>
              </draggable>
            </li> 
          </template>
        </transition-group>
      </draggable>
    </el-col>
  </el-row>
</template>
<script>
  import draggable from 'vuedraggable'
  const message = [
    'vue.draggable',
    'draggable',
    'component',
    'for',
    'vue.js 2.0',
    'based',
    'on',
    'Sortablejs'
  ]
  export default {
    data () {
      return {
        selectItems: message.map((name, index) => {
          return { name, order: index + 1, fixed: false, children: [{name: 'sub' + name, order: index + 1 + 10}] }
        }),
        selectItemsCopy: [],
        isDragging: false,
        editable: true
      }
    },
    components: {
      draggable
    },
    computed: {
      dragOptions () {
        return {
          animation: 0,
          group: {
            name: 'tree-editor',
            pull: 'clone',
            put: false,
            revertClone: false
          },
          disabled: !this.editable,
          ghostClass: 'ghost'
        }
      },
      targetOptions () {
        return {
          animation: 0,
          group: {
            name: 'tree-editor',
            pull: true,
            put: true
          },
          disabled: !this.editable,
          ghostClass: 'ghost',
          chosenClass: 'target-chosen'
        }
      }
    },
    methods: {
      onMove({ relatedContext, draggedContext }) {
        const relatedElement = relatedContext.element
        const draggedElement = draggedContext.element
        console.log('move', relatedElement, draggedElement)
        return relatedElement && draggedElement
        // 如果有fixed的话 return false 相等于不可拖拽
        // return (
        //   (!relatedElement || !relatedElement.fixed) && !draggedElement.fixed
        // )
      },
      handleMouseMove (evt) {
        evt.preventDefault()
        console.log(evt)
      }
    }
  }
</script>
<style scoped>
.tree-editor-container {
  position: relative;
  padding: 20px 15px;
}
.tree-editor-container .list-group-container {
  border-top: solid 1px #d1d2d4;
  border-bottom: solid 1px #d1d2d4;
  max-height: 900px;
  overflow-y: auto;
  overflow-x: hidden;
  background: #ddd;
  border-radius: 4px;
}
.tree-editor-container .list-group {
  min-height: 20px;
  padding-left: 0;
  margin-top: 0;
  margin-bottom: 0px;
}
.tree-editor-container .list-group-item {
  position: relative;
  display: block;
  padding: 10px 15px;
  margin-bottom: -1px;
  background-color: #fff;
  border: 1px solid #ddd;
  cursor: move;
}
.tree-editor-container .list-group-item:first-child {
  border-top-left-radius: 4px;
  border-top-right-radius: 4px;
}
.tree-editor-container .list-group-item:last-child {
  margin-bottom: 0;
  border-bottom-right-radius: 4px;
  border-bottom-left-radius: 4px;
}
.tree-editor-container .flip-list-move {
  transition: transform 0.5s;
}
.tree-editor-container .no-move {
  transition: transform 0s;
}
.tree-editor-container .ghost {
  opacity: 0.5;
  background: #c8ebfb;
}
.tree-editor-container .target-chosen {
  background: #fff;
}
</style>

