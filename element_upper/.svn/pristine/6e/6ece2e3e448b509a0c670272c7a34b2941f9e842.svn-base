/*
 * @Author: chenyang
 * @Date: 2017-07-30 09:47:00
 * @Last Modified by: chenyang
 * @Last Modified time: 2017-11-22 10:30:58
 * @Description: elementUI树形结构源码加以修改,目前供vncManager使用
 */
<template>
  <div class="el-tree" :class="{ 'el-tree--highlight-current': highlightCurrent }">
    <el-tree-node
      v-for="child in root.childNodes"
      :node="child"
      :props="props"
      :key="getNodeKey(child)"
      :render-content="renderContent"
      @node-expand="handleNodeExpand"
      @childtree="listenToChild">
    </el-tree-node>
    <div class="el-tree__empty-block" v-if="!root.childNodes || root.childNodes.length === 0">
      <span class="el-tree__empty-text">{{ emptyText }}</span>
    </div>
    <div ref="menuData" class="menu">
      <ul>
        <li v-for="item in items" @click="onItem(item)">
          <a>
            {{ item }}
          </a>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
  import TreeStore from './model/tree-store'
  import {t} from 'element-ui/src/locale'
  import emitter from 'element-ui/src/mixins/emitter'

  export default {
    name: 'ElTree',

    mixins: [emitter],

    components: {
      ElTreeNode: require('./tree-node.vue')
    },

    data () {
      return {
        store: null,
        root: null,
        currentNode: null,
        items: [    // 从配置文件读菜单列表
          '数据驱动的菜单',
          '菜单很菜很菜',
          '喵喵喵喵喵',
          '你好阿我是菜单',
          '蛋蛋蛋蛋蛋蛋'
        ]
      }
    },

    props: {
      callback: {
        type: Function,
        default: function () {}
      },
      menuClick: {
        type: Function,
        default: function () {}
      },
      data: {
        type: Array
      },
      emptyText: {
        type: String,
        default () {
          return t('el.tree.emptyText')
        }
      },
      nodeKey: String,
      checkStrictly: Boolean,
      defaultExpandAll: Boolean,
      expandOnClickNode: {
        type: Boolean,
        default: true
      },
      autoExpandParent: {
        type: Boolean,
        default: true
      },
      defaultCheckedKeys: Array,
      defaultExpandedKeys: Array,
      renderContent: Function,
      showCheckbox: {
        type: Boolean,
        default: false
      },
      props: {
        default () {
          return {
            children: 'children',
            label: 'label',
            icon: 'icon'
          }
        }
      },
      lazy: {
        type: Boolean,
        default: false
      },
      highlightCurrent: Boolean,
      currentNodeKey: [String, Number],
      load: Function,
      filterNodeMethod: Function,
      accordion: Boolean,
      indent: {
        type: Number,
        default: 16
      }
    },

    computed: {
      children: {
        set (value) {
          this.data = value
        },
        get () {
          return this.data
        }
      }
    },

    watch: {
      defaultCheckedKeys (newVal) {
        this.store.defaultCheckedKeys = newVal
        this.store.setDefaultCheckedKey(newVal)
      },
      defaultExpandedKeys (newVal) {
        this.store.defaultExpandedKeys = newVal
        this.store.setDefaultExpandedKeys(newVal)
      },
      currentNodeKey (newVal) {
        this.store.setCurrentNodeKey(newVal)
        this.store.currentNodeKey = newVal
      },
      data (newVal) {
        this.store.setData(newVal)
      }
    },

    methods: {
      listenToChild (somedata) {  // 点击的时候获取点击的哪个 可复用
        this.callback(somedata)  // 发送点击右键时是哪个节点
      },
      onItem (item) {   // 点击菜单时，获取菜单的选项
        this.menuClick(item)
      },
      filter (value) {
        if (!this.filterNodeMethod) throw new Error('[Tree] filterNodeMethod is required when filter')
        this.store.filter(value)
      },
      getNodeKey (node, index) {
        const nodeKey = this.nodeKey
        if (nodeKey && node) {
          return node.data[nodeKey]
        }
        return index
      },
      getCheckedNodes (leafOnly) {
        return this.store.getCheckedNodes(leafOnly)
      },
      getCheckedKeys (leafOnly) {
        return this.store.getCheckedKeys(leafOnly)
      },
      setCheckedNodes (nodes, leafOnly) {
        if (!this.nodeKey) throw new Error('[Tree] nodeKey is required in setCheckedNodes')
        this.store.setCheckedNodes(nodes, leafOnly)
      },
      setCheckedKeys (keys, leafOnly) {
        if (!this.nodeKey) throw new Error('[Tree] nodeKey is required in setCheckedNodes')
        this.store.setCheckedKeys(keys, leafOnly)
      },
      setChecked (data, checked, deep) {
        this.store.setChecked(data, checked, deep)
      },
      handleNodeExpand (nodeData, node, instance) {
        this.broadcast('ElTreeNode', 'tree-node-expand', node)
        this.$emit('node-expand', nodeData, node, instance)
      }
    },
    mounted () {
      document.addEventListener('click', () => {
        // console.log('click')
        if (!this.$refs.menuData) {
          return
        }
        this.$refs.menuData.style.display = 'none'
      })
    },
    created () {
      this.isTree = true

      this.store = new TreeStore({
        key: this.nodeKey,
        data: this.data,
        lazy: this.lazy,
        props: this.props,
        load: this.load,
        currentNodeKey: this.currentNodeKey,
        checkStrictly: this.checkStrictly,
        defaultCheckedKeys: this.defaultCheckedKeys,
        defaultExpandedKeys: this.defaultExpandedKeys,
        autoExpandParent: this.autoExpandParent,
        defaultExpandAll: this.defaultExpandAll,
        filterNodeMethod: this.filterNodeMethod
      })

      this.root = this.store.root
    }
  }
</script>
<style scoped>
  * {
    margin: 0;
    padding: 0
  }

  a {
    text-decoration: none
  }

  ul li {
    list-style: none
  }

  .menu {
    border: 1px solid black;
    border-radius: 5px;
    display: inline-block;
    position: fixed;
    top: 100px;
    left: 550px;
    overflow: hidden;
    padding-bottom: 10px;
    box-shadow: 0 0 10px 0;
    z-index: 999;
    display: none;
    background: white
  }

  .menu ul li {
    height: 30px;
    width: 200px
  }

  .menu ul li a {
    height: 30px;
    display: inline-block;
    width: 100%;
    text-align: left;
    line-height: 30px;
    padding: 5px 10px
  }

  .menu li a:hover {
    background-color: #EEF5E2
  }
</style>
