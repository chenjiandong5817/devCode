<template>
  <div class="menu-wrapper">
    <template  v-for="(item) in menu">
      <el-menu-item v-if="item.children && item.children.length===0 " :index="item.href" @click="open(item)" :key="item.label">
        <i :class="item.icon"></i>
        <span slot="title">{{item.label}}</span>
      </el-menu-item>
      <el-submenu v-else :index="item.label" :key="item.code">
        <template slot="title">
          <i :class="item.icon"></i>
          <span slot="title" :class="{display:!show}">{{item.label}}</span>
        </template>
        <template v-for="child in item.children">
          <!-- <el-menu-item :index="child.href" @click="open(child)" v-if="child.children && child.children.length===0" :key="child.label">
            <i :class="child.icon"></i>
            <span slot="title">{{child.label}}</span>
          </el-menu-item> -->
          <sidebar-item :menu="[child]" :show="show" :key="child.label"></sidebar-item>
        </template>
      </el-submenu>
    </template>
  </div>
</template>
<script>
export default {
  name: 'SidebarItem',
  data () {
    return {}
  },
  inject: ['open'],
  props: {
    menu: {
      type: Array
    },
    show: {
      type: Boolean
    }
  },
  created () {},
  mounted () {},
  methods: {
  }
}
</script>
<style lang="scss" scoped>
.display {
  display: none;
}
</style>
