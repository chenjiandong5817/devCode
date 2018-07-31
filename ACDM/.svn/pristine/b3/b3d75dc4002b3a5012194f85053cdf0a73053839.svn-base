<template>
  <!-- -->
  <div style="margin: 5px 15px;" v-if="isLoading">
    Loading...
  </div>
  <div v-else-if="external">
    <router-view></router-view>
  </div>
  <div class="pull-height animated main-container" v-else>
    <sidebar class="left" :class="{'sidebar-collapse': isCollapse}"></sidebar>
    <div class="right">
      <top></top>
      <div class="center" style="width: 100%">
        <!-- <router-view class="main"></router-view> -->
        <tabs></tabs>
      </div>
      <my-footer></my-footer>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import top from './top/'
import sidebar from './sidebar/'
import Tabs from './tabs/'
import MyFooter from './footer/'
export default {
  components: {
    top,
    sidebar,
    Tabs,
    MyFooter
  },
  name: 'index',
  data () {
    return {
    }
  },
  created () {
  },
  mounted () {
    // this.$store.commit('ADD_TAB', this.tab.value ? this.tab : this.tabHome)
    // this.$store.commit('ADD_TAB', {
    //   label: '主页2',
    //   name: 'home2',
    //   value: '/home2'
    // })
    // this.$router.push({ path: resolveUrlPath(this.tab.value || this.tabHome.value) })
    this.loadRequireData()
  },
  computed: {
    ...mapGetters(['tab', 'tabHome', 'tabList', 'isCollapse', 'isLoading']),
    external () {
      return this.$route.path.startsWith('/third-part')
    }
  },
  props: [],
  methods: {
    loadRequireData () {
      if (!this.isLoading) return
      this.$store.dispatch('ActionReady').then(() => {
        // 待执行函数
        let array = [
          new Promise(resolve => {
            this.$store.dispatch('GetRoleAll').then(() => {
              resolve()
            })
          }),
          new Promise(resolve => {
            this.$store.dispatch('GetMenuAll').then(() => {
              resolve()
            })
          })
        ]
        return new Promise(resolve => {
          Promise.all(array).then(results => {
            this.$store.dispatch('BindControlMessageType')
            resolve(results)
          })
        })
      }).then(() => {
        // 需要auth的请求需要等待RoleAll加载完毕，因此写到下一个then
        let array = [
          new Promise(resolve => {
            let getMessageTypes = this.$auth('get_message_type_list', false)
            if (getMessageTypes) {
              getMessageTypes().then((res) => {
                if (res.status === 1) {
                  this.$store.commit('SET_MESSAGR_TYPE_ALL', res.data)
                }
                resolve()
              })
            } else {
              resolve()
            }
          })
        ]
        return new Promise(resolve => {
          Promise.all(array).then(results => {
            resolve(results)
          })
        })
      }).then(() => {
        // 提示全局数据已加载结束
        this.$store.dispatch('LoadingComplete').then(() => {
          console.log('获取数据结束')
        })
      })
    }
  }
}
</script>

<style lang="scss" scoped>
@import "~@/styles/global";
.main-container {
  display: flex;
  .left {
    // transition: transform 3s cubic-bezier(0,0,1,1), width 3s cubic-bezier(.645,.045,.355,1);
    transition: width .3s cubic-bezier(.645,.045,.355,1);
    &:not(.el-menu--collapse) {
      width: 200px;
      // overflow-y: auto;
    }
    &.sidebar-collapse {
      // transform: translateX(calc(-100%));
      width: 0px;
    }
  }
  .right {
    height: calc(100%);
    padding: 0 0 20px 0;
    position: relative;
    min-height: 100%;
    background: #fff;
    overflow: hidden;
    flex: 1;
    .center {
      // padding-top: 90px;
      height: calc(100% - #{$main-header-height} - #{$main-footer-height});
      position: relative;
      box-sizing: border-box;
    }
  }
}
</style>
