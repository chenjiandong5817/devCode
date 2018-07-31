
import { getRoleAll, getMenu } from '@/api/admin'
const admin = {
  state: {
    messageTypeAll: [],
    roleAll: [],
    roleLoading: false, // roleAll 重载会造成使用el-table的组件发生错乱，在组件上使用v-if强制渲染
    menuAll: [],
    service: {
      food: {},
      accom: {},
      vehicle: {},
      activeName: ''
    }
  },
  actions: {
    // 切换当前服务
    ChangeService ({ commit, state, dispatch }, data) {
      return new Promise(resolve => {
        commit('SET_SERVICE', data)
        resolve()
      })
    },
    // 角色获取状态
    ToggleRoleLoading ({ commit }, action) {
      commit('SET_ROLE_LOADING', action)
    },
    // 获取全部角色
    GetRoleAll ({ commit, state, dispatch }) {
      return new Promise((resolve, reject) => {
        dispatch('ToggleRoleLoading', true)
        getRoleAll().then(res => {
          let data = res.data
          commit('SET_ROLE_ALL', data)
          dispatch('ToggleRoleLoading', false)
          resolve(data)
        })
      }).then(data => {
        // 过滤用户有权限的控件
        commit('SET_CONTROL', data)
        // 设置角色订阅消息类型
        commit('SET_ROUTINTKEYS', data)
      })
    },
    // 获取全部菜单
    GetMenuAll ({ commit }) {
      return new Promise(resolve => {
        getMenu().then((res) => {
          const data = res.data
          commit('SET_MENU_ALL', data)
          resolve(data)
        })
      })
    }
  },
  mutations: {
    SET_SERVICE: (state, data) => {
      state.service = Object.assign({}, state.service, data)
    },
    SET_ROLE_ALL (state, roles) {
      state.roleAll = roles
    },
    SET_ROLE_LOADING (state, status) {
      state.roleLoading = status
    },
    SET_MENU_ALL: (state, menuAll) => {
      state.menuAll = menuAll
    },
    SET_MESSAGR_TYPE_ALL (state, types) {
      state.messageTypeAll = types
    }
  }
}
export default admin
