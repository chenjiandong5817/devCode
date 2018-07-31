<template>
  <div class="tabs-container">
    <v-contextmenu ref="contextmenu" @after="handleAfterShow">
      <v-contextmenu-item @click.native="refreshTab">重新加载</v-contextmenu-item>
      <v-contextmenu-item @click.native="closeOthersTabs">关闭其他</v-contextmenu-item>
      <v-contextmenu-item @click.native="closeAllTabs">关闭全部</v-contextmenu-item>
    </v-contextmenu>
    <el-tabs :value="(tab || {}).name" type="card" @tab-remove="removeTab"
      @tab-click="handleTabClick" ref="tabs" v-contextmenu:contextmenu>
      <el-tab-pane
        v-for="(item) in tabList"
        :label="item.label"
        :name="item.name"
        :key="item.name"
        :closable="item.closable"
      >
        <template>
          <my-iframe v-if="item.value.startsWith('http')" :src="item.value"></my-iframe>
          <transition name="tabs-slide-fade" mode="out-in" v-else>
            <keep-alive v-if="item.name !== selectTabName || !refreshFlag">
              <router-view class="router-view" v-if="item.value === $route.path"></router-view>
            </keep-alive>
          </transition>
          <!-- <router-view :name="item.name" class="router-view" v-else-if="(item.name !== selectTabName || !refreshFlag) && !isLoading"></router-view> -->
        </template>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
import { resolveUrlPath } from '@/util/util'
import { mapGetters } from 'vuex'
import MyIframe from '@/components/iframe'
export default {
  name: 'Tabs',
  components: {
    MyIframe
  },
  data () {
    return {
      refreshFlag: false,
      selectTabName: ''
    }
  },
  computed: {
    ...mapGetters(['tabHome', 'tabList', 'isCollapse', 'isLoading', 'tab', 'menus']),
    external () {
      return this.$route.path.startsWith('/third-part')
    }
  },
  created () {

  },
  mounted () {
    // console.log('router', this.$route)
    // console.log((this.tab || {}).name)
    // console.log(this.$refs.tabs.$children.filter(child => child.$options.name === 'TabNav'))
    this.autoOpenTab()
  },
  methods: {
    autoOpenTab () {
      if (!this.external) {
        this.$store.commit('ADD_TAB', this.tab.value ? this.tab : this.tabHome)
        this.$router.push({ path: resolveUrlPath(this.tab.value || this.tabHome.value) })
      }
    },
    deepSearchMenu (menus, key, value) {
      menus.find(item => {
        if (item[key] === value) {
          return true
        } else if (item.children) {
          return this.deepSearchMenu(item.children, key, value)
        } else {
          return false
        }
      })
    },
    getMenuByProperty (key, value) {
      return this.deepSearchMenu(this.menus, key, value)
    },
    getTabByProperty (key, value) {
      let tab = this.tabList.find(item => item[key] === value)
      return tab
    },
    handleTabClick ({name}) {
      if (!name) return
      let tab = this.getTabByProperty('name', name)
      this.$store.commit('ADD_TAB', {
        label: tab.label,
        name: tab.name,
        value: tab.value
      })
      this.$router.push({ path: resolveUrlPath(tab.value) })
    },
    removeTab (tabName) {
      let item = this.getTabByProperty('name', tabName)
      if (item) {
        this.$store.commit('DEL_TAB', item)
        this.handleTabClick(this.tab)
      }
    },
    refreshTab () {
      // console.log(this.selectTabName)
      this.refreshFlag = true
      this.$nextTick(() => {
        this.refreshFlag = false
      })
    },
    closeOthersTabs () {
      let tab = this.getTabByProperty('name', this.selectTabName)
      if (tab) {
        this.$store.commit('DEL_TAB_OTHER', tab)
        this.handleTabClick(this.tab)
      }
    },
    closeAllTabs () {
      this.$store.commit('DEL_ALL_TAB')
      this.$store.commit('ADD_TAB', this.tabHome)
      this.$router.push({ path: resolveUrlPath(this.tabHome.value) })
    },
    handleAfterShow (elm) {
      if (!elm || !elm.id) {
        return
      }
      let name = elm.id.replace(/(tab-)/, '')
      // this.handleTabClick({name})
      this.selectTabName = name
    }
  }
}
</script>
<style>
.tabs-slide-fade-enter-active, .tabs-slide-fade-leave-active {
  transition: all .35s ease;
}
/* .slide-fade-leave-active {
  transition: all .3s cubic-bezier(1.0, 0.5, 0.8, 1.0);
} */
.tabs-slide-fade-enter, .tabs-slide-fade-leave-to
/* .slide-fade-leave-active for below version 2.1.8 */ {
  transform: translate(1%);
  opacity: 0;
}
</style>
