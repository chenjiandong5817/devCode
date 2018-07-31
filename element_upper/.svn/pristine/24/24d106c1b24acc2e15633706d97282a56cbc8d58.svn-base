/*
 * @Author: chenyang
 * @Date: 2017-07-30 09:47:00
 * @Last Modified by: chenyang
 * @Last Modified time: 2017-09-18 08:39:39
 * @Description: elementUI树形结构源码加以修改,目前供vncManager使用
 */
<template>
  <div class="el-tree-node" @click.stop="handleClick" v-show="node.visible" :class="{
        'is-expanded': childNodeRendered && expanded,
        'is-current': tree.store.currentNode === node,
        'is-hidden': !node.visible
      }">
    <div class="el-tree-node__content" :style="{ 'padding-left': (node.level - 1) * tree.indent + 'px' }"  @contextmenu.prevent.stop="handleContextMenu">
      <span class="el-tree-node__expand-icon" @click.stop="handleExpandIconClick" :class="{ 'is-leaf': node.isLeaf, expanded: !node.isLeaf && expanded }">
        </span>
      <el-checkbox v-if="showCheckbox" v-model="node.checked" :indeterminate="node.indeterminate" @change="handleCheckChange" @click.native.stop="handleUserClick">
      </el-checkbox>
      <span v-if="node.loading" class="el-tree-node__loading-icon el-icon-loading">
        </span>
      <node-content :node="node"></node-content>
    </div>
    <el-collapse-transition>
      <div class="el-tree-node__children" v-show="expanded">
        <el-tree-node :render-content="renderContent" v-for="child in node.childNodes" :key="getNodeKey(child)" :node="child" @node-expand="handleChildNodeExpand">
        </el-tree-node>
      </div>
    </el-collapse-transition>
  </div>
</template>

<script type="text/jsx">
  import ElCollapseTransition from 'element-ui/src/transitions/collapse-transition'
  import ElCheckbox from 'element-ui/packages/checkbox'
  import emitter from 'element-ui/src/mixins/emitter'
  import menuList from '../../common/menu/menuList'

  export default {
    name: 'ElTreeNode',

    componentName: 'ElTreeNode',

    mixins: [emitter],

    props: {
      node: {
        default () {
          return {}
        }
      },
      props: {},
      renderContent: Function
    },

    components: {
      ElCollapseTransition,
      ElCheckbox,
      NodeContent: {
        props: {
          node: {
            required: true
          }
        },
        render(h) {
          const parent = this.$parent
          const node = this.node
          const data = node.data
          const store = node.store
          return (
            parent.renderContent ?
            parent.renderContent.call(parent._renderProxy, h, {
              _self: parent.tree.$vnode.context,
              node,
              data,
              store
            }) :
            h(
              'span', {
                class: 'el-tree-node__label'
              }, [
                this.node.label
              ]
            )
          )
        }
      }
    },

    data() {
      return {
        tree: null,
        expanded: false,
        childNodeRendered: false,
        showCheckbox: false,
        oldChecked: null,
        oldIndeterminate: null
      }
    },

    watch: {
      'node.indeterminate' (val) {
        this.handleSelectChange(this.node.checked, val)
      },

      'node.checked' (val) {
        this.handleSelectChange(val, this.node.indeterminate)
      },

      'node.expanded' (val) {
        this.expanded = val
        if (val) {
          this.childNodeRendered = true
        }
      }
    },

    methods: {
      handleContextMenu(e) {
        // console.log(this.node.label)
        if (this.node.level === 1) {
          this.$emit('childtree', this.node)
          this.$parent.items = []
          for (let i = 0; i < menuList.menuArray.length; i++) {
            this.$parent.items.push(menuList.menuArray[i])
          }
        } else if (this.node.level === 2) {
          this.$parent.$emit('childtree', this.node)
          this.$parent.$parent.items = []
          for (let i = 0; i < menuList.menuArray.length; i++) {
            this.$parent.$parent.items.push(menuList.menuArray[i])
          }
        } else if (this.node.level === 3) {
          this.$parent.$parent.$emit('childtree', this.node)
          this.$parent.$parent.$parent.items = []
          for (let i = 0; i < menuList.menuArray.length; i++) {
            this.$parent.$parent.$parent.items.push(menuList.menuArray[i])
          }
        }
        let cur = (vueElement) => {
          let menu = vueElement.$refs.menuData
          return menu || cur(vueElement.$parent)
        }
        let obj = cur(this.$parent)
        // this.contextMenu(menuId.$el)
        var mouseX = e.clientX
        var mouseY = e.clientY
        // console.log(obj)
        var wh = {}
        obj.style.visibility = 'hidden'
        wh.w = obj.scrollWidth
        wh.h = obj.scrollHeight
        obj.style.visibility = null
        var contentW = wh.w
        var contentH = wh.h
        var documentW = document.body.scrollWidth
        var documentH = document.body.scrollHeight
        obj.style.left = documentW - mouseX < contentW ? documentW - contentW + 'px' : mouseX + 'px'
        obj.style.top = documentH - mouseY < contentH ? mouseY - contentH + 'px' : mouseY + 'px'
        obj.style.display = 'inline-block'
      },
      getNodeKey(node, index) {
        const nodeKey = this.tree.nodeKey
        if (nodeKey && node) {
          return node.data[nodeKey]
        }
        return index
      },

      handleSelectChange(checked, indeterminate) {
        if (this.oldChecked !== checked && this.oldIndeterminate !== indeterminate) {
          this.tree.$emit('check-change', this.node.data, checked, indeterminate)
        }
        this.oldChecked = checked
        this.indeterminate = indeterminate
      },

      handleClick() {
        const store = this.tree.store
        store.setCurrentNode(this.node)
        this.tree.$emit('current-change', store.currentNode ? store.currentNode.data : null, store.currentNode)
        this.tree.currentNode = this
        if (this.tree.expandOnClickNode) {
          this.handleExpandIconClick()
        }
        this.tree.$emit('node-click', this.node.data, this.node, this)
      },

      handleExpandIconClick() {
        if (this.node.isLeaf) return
        if (this.expanded) {
          this.tree.$emit('node-collapse', this.node.data, this.node, this)
          this.node.collapse()
        } else {
          this.node.expand()
          this.$emit('node-expand', this.node.data, this.node, this)
        }
      },

      handleUserClick() {
        if (this.node.indeterminate) {
          this.node.setChecked(this.node.checked, !this.tree.checkStrictly)
        }
      },

      handleCheckChange(ev) {
        if (!this.node.indeterminate) {
          this.node.setChecked(ev.target.checked, !this.tree.checkStrictly)
        }
      },

      handleChildNodeExpand(nodeData, node, instance) {
        this.broadcast('ElTreeNode', 'tree-node-expand', node)
        this.tree.$emit('node-expand', nodeData, node, instance)
      }
    },

    created() {
      const parent = this.$parent

      if (parent.isTree) {
        this.tree = parent
      } else {
        this.tree = parent.tree
      }

      const tree = this.tree
      if (!tree) {
        console.warn('Can not find node\'s tree.')
      }

      const props = tree.props || {}
      const childrenKey = props['children'] || 'children'

      this.$watch(`node.data.${childrenKey}`, () => {
        this.node.updateChildren()
      })

      this.showCheckbox = tree.showCheckbox

      if (this.node.expanded) {
        this.expanded = true
        this.childNodeRendered = true
      }

      if (this.tree.accordion) {
        this.$on('tree-node-expand', node => {
          if (this.node !== node) {
            this.node.collapse()
          }
        })
      }
    }
  }
</script>
