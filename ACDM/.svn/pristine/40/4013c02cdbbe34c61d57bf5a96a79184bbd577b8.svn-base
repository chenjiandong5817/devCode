<template>
  <!-- <el-menu unique-opened :default-active="tab.value" class="el-menu-vertical-demo" :collapse="isCollapse">
    <sidebar-item :menu="menu" :show="!isCollapse"></sidebar-item>
  </el-menu> -->
  <div class="sidebar-container">
    <div class="sidebar-logo">
      <sidebar-logo></sidebar-logo>
    </div>
    <div class="sidebar-menu">
      <el-menu unique-opened :default-active="tab.value"  :collapse="false">
        <el-menu-item :index="menuHome.href" @click="open(menuHome)">
            <i :class="menuHome.icon"></i>
            <span slot="title">{{menuHome.label}}</span>
        </el-menu-item>
        <sidebar-item :menu="menus" :show="true"></sidebar-item>
      </el-menu>
    </div>
  </div>
</template>

<script>
import { resolveUrlPath, deepFindObject } from '@/util/util'
import { mapGetters } from 'vuex'
import SidebarItem from './sidebarItem'
import SidebarLogo from './sidebar-logo'
export default {
  data () {
    return {
    }
  },
  provide () {
    return {
      open: this.open
    }
  },
  created () {
    // this.$store.dispatch('GetMenu').then(data => {})
  },
  computed: {
    ...mapGetters(['menus', 'tab', 'isCollapse', 'tabHome']),
    menuHome () {
      return {
        label: this.tabHome.label,
        code: this.tabHome.name,
        href: this.tabHome.value,
        icon: 'fa fa-home fa-lg'
      }
    }
  },
  watch: {
    '$route.path' (val) {
      let menu = deepFindObject({
        objectArray: [this.menuHome].concat(this.menus),
        key: 'href',
        value: val,
        childKey: 'children'
      })
      if (menu) {
        this.open(menu)
      }
    }
  },
  mounted () {},
  methods: {
    open (item) {
      if (item.href.startsWith('/iframe/')) { // 去掉iframe 前缀
        item.href = item.href.substring(8)
      }
      let url = resolveUrlPath(item.href, item.code)
      // console.log(url)
      this.$router.push({ path: url, query: this.$route.query })
      this.$store.commit('ADD_TAB', {
        label: item.label,
        name: item.code,
        value: item.href
      })
    }
  },
  components: { SidebarItem, SidebarLogo }
}
</script>
<style lang="scss" scoped>

</style>
