/*
 * @Author: cdroid
 * @Date: 2017-05-25 10:42:52
 * @Last Modified by: chenyang
 * @Last Modified time: 2017-11-09 10:36:46
 * @Description: 系统主界面
 */

<template>
  <el-row class="container">
    <el-col :span="24" class="header">
      <el-col :span="1" class="logo" :class="['horizontal-collapse-transition', {'logo-collapse': collapsed}]">
        {{collapsed ? '' : sysName}}
        <div class="tools" @click.prevent="collapse" :class="{collapse: collapsed}">
          <i class="fa fa-align-justify"></i>
        </div>
      </el-col>
      <el-col :span="5" class="userinfo">
        <el-dropdown trigger="click">
          <span class="el-dropdown-link userinfo-inner">
            <img :src="this.sysUserAvatar" ></img> {{sysUserName}}</span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item>我的消息</el-dropdown-item>
            <el-dropdown-item @click.native="getUserProfile">个人中心</el-dropdown-item>
            <el-dropdown-item @click.native="clickFlushCacheBtn">刷新缓存</el-dropdown-item>
            <el-dropdown-item divided @click.native="logout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-col>
    </el-col>
    <el-col :span="24" class="main">
      <aside @wheel="menuScroll" >
        <el-menu class="left-menu-collapse"  :default-active="$route.path" @open="handleopen" @close="handleclose" @select="handleselect" theme="dark" unique-opened router :collapse="collapsed">
          <template :index="index+''" v-for="(item,index) in authorMenu" >
            <template v-if="!item.hidden">
              <el-submenu :index="index+''" v-if="!item.leaf" :key="item.id">
                <template slot="title">
                  <i :class="item.iconCls"></i><span slot="title">{{item.name}}</span>
                </template>
                <el-menu-item v-for="child in item.children" :index="child.path" :key="child.path">
                  <template v-if="!child.hidden">
                    <span slot="title">{{child.name}}</span>
                  </template>
                </el-menu-item>
              </el-submenu>
              <el-menu-item v-else-if="item.children.length>0" :index="item.children[0].path" :key="item.id">
                <i :class="item.iconCls"></i><span slot="title">{{item.children[0].name}}</span>
              </el-menu-item>
            </template>
          </template>
        </el-menu>
      </aside>

      <section class="content-container" style="width: 100%">
        <el-col :span="24" class="content-wrapper">
            <Tabs ref="Tabs"></Tabs>
          </el-col>
      </section>
    </el-col>
    <user-profile ref="userProfileDialog"></user-profile>
  </el-row>
</template>

<script>
import Tabs from '../components/Tabs.vue'
import API from '../api'
import UserProfile from './system/UserProfile'
import Util from './../common/js/util'

export default {
  name: 'RaiisMain',
  componentName: 'RaiisMain',
  data () {
    return {
      sysName: 'VueRaiis',
      collapsed: false,
      sysUserName: '',
      sysUserAvatar: '',
      authorMenu: null,
      menuProp: {
        initX: 0,
        initY: 0,
        inited: false
      }
    }
  },
  methods: {
    handleopen () {
      // console.log('handleopen');
    },
    handleclose () {
      // console.log('handleclose');
    },
    handleselect: function (indexPath) {
      this.$router.push(indexPath)
      this.$refs.Tabs.$emit('addTab', indexPath)
    },
    // 退出登录
    logout: function () {
      var _this = this
      this.$confirm('确认退出吗?', '提示', {
        type: 'warning'
      }).then(() => {
        API.requestLogout().go({}).then((res) => {
          this.$store.dispatch('removeUserStorage')
          _this.$router.push('/login')
        }).catch(() => {
          this.$store.dispatch('removeUserStorage')
          _this.$router.push('/login')
        })
      }).catch(() => {
      })
    },
    // 折叠导航栏
    collapse: function () {
      this.$refs.Tabs.$emit('tabresize')
      this.collapsed = !this.collapsed
    },
    getUserProfile () {
      this.$refs['userProfileDialog'].show()
    },
    setAuthorMenu () {
      let m = function (data, authorIds) {
        let menu = []
        for (var i = 0; i < data.length; i++) {
          var item = data[i]
          if (item.children) {
            item.children = m(item.children, authorIds)
            if (item.children.length > 0) {
              menu.push(item)
            }
          } else if (item.hidden || authorIds.indexOf(item.id + '') > -1) {
            menu.push(item)
          }
        }
        return menu
      }
      // 特例，拷贝路由列表
      let copyRoute = function (routeArray) {
        var result = []
        for (var j = 0; j < routeArray.length; j++) {
          var item = {}
          item.id = routeArray[j].id
          item.name = routeArray[j].name
          item.path = routeArray[j].path
          if (routeArray[j].leaf) {
            item.leaf = routeArray[j].leaf
          }
          if (routeArray[j].iconCls) {
            item.iconCls = routeArray[j].iconCls
          }
          if (routeArray[j].children) {
            item.children = copyRoute(routeArray[j].children)
          }
          result.push(item)
        }
        return result
      }
      if (this.$store.getters.getUserStorage.user.name === 'admin') {
        this.authorMenu = this.$router.options.routes
      } else {
        API.queryUserMenuList().go().then(res => {
          if (!res.ok) {
            this.$notify(Util.notifyBody(res.ok, res.msg))
          } else {
            let authorIds = res.attr.list || []
            this.authorMenu = m(copyRoute(this.$router.options.routes), authorIds)
          }
        })
      }
    },
    showUser () {
      var storage = this.$store.getters.getUserStorage
      this.sysUserName = storage.user.profile.nickname || storage.user.name
    },
    menuScroll (event) {
      let menuParent = event.currentTarget
      let menu = menuParent.children[0]
      if (this.collapsed) {
        // TODO  判断滚动情况
      } else if (menu.clientHeight < menuParent.clientHeight) {
        return
      }
      let direction = event.deltaY > 0
      let oneLine = 30
      if (direction) {
        this.menuProp.initY -= oneLine
      } else {
        this.menuProp.initY += this.menuProp.initY + oneLine > 0 ? this.menuProp.initY : oneLine
      }
      menu.style.transform = `translateY(${this.menuProp.initY}px)`
    },
    // 刷新缓存，并且开始计时
    flushCache () {
      return this.$cache.pull().then(() => {
        this.$cache.start()
      })
    },
    clickFlushCacheBtn () {
      this.$notify({
          title: '刷新缓存',
          type: 'warning',
          message: '正在刷新缓存...'
        })
      this.flushCache().then(() => {
        this.$notify({
          title: '成功',
          type: 'success',
          message: '缓存刷新完毕'
        })
      })
    }
  },
  components: {
    Tabs: Tabs,
    UserProfile: UserProfile
  },
  created () {
    this.setAuthorMenu()
  },
  mounted () {
    // 更新用户事件
    this.$on('updateUser', () => {
      API.currentUser().go({}).then(user => {
        this.$store.dispatch('saveUserStorage', { user: user, token: this.$store.getters.getUserStorage.token })
        this.showUser()
      })
    })
    // 更新头像事件
    this.$on('updateAvatar', () => {
      this.sysUserAvatar = API.getUserAvatar().url + '?' + Date.parse(new Date())
    })
    this.$emit('updateAvatar')
    // 修改右上角显示用户
    this.showUser()
    // tabs页添加
    this.$refs.Tabs.$emit('addTab', this.$route.path)
    // 拉取缓存
    this.$cache.generateSessionCache()
  }
}

</script>

<style scoped lang="scss">
@import './../styles/global';

.container {
  position: absolute;
  top: 0px;
  bottom: 0px;
  width: 100%;
  .header {
    height: 60px;
    line-height: 60px;
    background: $color-primary;
    color: #c0ccda;
    .userinfo {
      text-align: right;
      padding-right: 35px;
      float: right;
      .userinfo-inner {
        cursor: pointer;
        color: #c0ccda;
        img {
          width: 40px;
          height: 40px;
          border-radius: 20px;
          margin: 10px 0px 10px 10px;
          float: right;
        }
      }
    }
    .logo {
      position: relative;
      display: flex;
      justify-content: space-between;
      align-items: center;
      width: 230px;
      height: 60px;
      padding: 0 15px;
      font-size: 22px;
      border-color: rgba(238, 241, 146, 0.3);
      // border-right-width: 1px;
      // border-right-style: solid;
      // transition:.3s width ease-in-out,.3s padding-left ease-in-out,.3s padding-right ease-in-out;
      .tools {
        display: inline-block;
        line-height: 60px;
        cursor: pointer;
        &.collapse {
          padding: 0;
        }
      }
    }
    .logo-collapse {
      width: 64px;
      padding: 0;
      justify-content: center;
    }
  }
  .main {
    display: flex;
    background: #324057;
    position: absolute;
    top: 60px;
    bottom: 0px; // height: 100%;
    overflow: hidden;
    aside {
      z-index: 3;
      .left-menu-collapse {
        // transition:.3s transform ease-in-out, .3s width ease-in-out;
        &:not(.el-menu--collapse) {
          width: 230px;
        }
      }
    }

    .content-container {
      background: #fff;
      flex: 1;
      min-height: 0;
      overflow-y: auto;
      overflow-x: hidden;
      padding: 0px 6px 6px 6px;
      .content-wrapper {
			  background-color: #fff;
				box-sizing: border-box;
			}
    }
  }
}
</style>
