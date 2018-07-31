<template>
  <div class="header">
    <div class="header-button is-left">
      <top-logo></top-logo>
    </div>
    <h1 class="header-title">
      <topMenu></topMenu>
    </h1>
    <div class="header-button is-right">
      <span class="header-item">
        <top-message></top-message>
      </span>
      <el-tooltip class="item" effect="dark" :content="isFullScren?'退出全屏':'全屏'" placement="bottom">
        <span class="header-item">
          <i :class="isFullScren ? 'fa fa-compress' : 'fa fa-expand'" style="cursor: pointer;" @click="handleScreen"></i>
        </span>
      </el-tooltip>
      <el-dropdown trigger="click" class="user-options" @visible-change="handleUserOptionsVisible">
        <span class="el-dropdown-link">
          {{userInfo.name || userInfo.username}}
          <i :class="['el-icon--right', 'el-icon-arrow-down', {'is-active' : userOptionsVisible} ]"></i>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item @click.native="toHome">首页</el-dropdown-item>
          <el-dropdown-item @click.native="toggleMessageReceivable">{{ `${isUnreceivable ? '开启' : '关闭'}消息` }}</el-dropdown-item>
          <el-dropdown-item @click.native="logout" divided>退出系统</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import { mapState, mapGetters } from 'vuex'
import { fullscreenToggel, listenfullscreen } from '@/util/util'
import topLogo from './top-logo'
import topMenu from './top-menu'
import topMessage from './top-message'
export default {
  components: { topMessage, topLogo, topMenu },
  name: 'top',
  data () {
    return {
      userOptionsVisible: false
    }
  },
  filters: {},
  created () {},
  mounted () {
    listenfullscreen(this.setScreen)
  },
  computed: {
    ...mapState({
      userInfo: state => state.user.userInfo
    }),
    ...mapGetters(['isFullScren', 'isUnreceivable'])
  },
  methods: {
    handleScreen () {
      fullscreenToggel()
    },
    setScreen () {
      this.$store.commit('SET_FULLSCREN')
    },
    handleUserOptionsVisible (val) {
      this.userOptionsVisible = val
    },
    toggleMessageReceivable () {
      this.$store.commit('SET_UNRECEIVABLE')
    },
    toHome () {
      this.$router.push({path: '/home'})
    },
    logout () {
      this.$confirm('即将退出系统, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$router.push({path: '/logout'})
      }).catch(() => {})
    }
  }
}
</script>

<style lang="scss" scoped>
</style>
