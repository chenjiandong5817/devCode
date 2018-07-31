// import { getToken, setToken, removeToken } from '@/util/auth'
import { getStore, setStore, removeStore } from '@/util/store'
import { getUserInfo, getMenu } from '@/api/admin'
import { validatenull } from '@/util/validate'
import { ip2String } from '@/util/yun'
import StompClient from '@/util/stomp-client'
import { replaceLocalUrlPrefx } from '@/util/util'

// 通过user的拥有的角色编码 和 菜单拥有的角色编码进行判断，过滤出user有权限访问的菜单
const validMenu = (roleCodes, menus = [], parentPath = '') => {
  let list = []
  menus.forEach(menu => {
    // 判断本来就没有子节点，或者是因为权限问题才没有子节点
    let childrenSize = 0
    if (menu.children) {
      childrenSize = menu.children.length
      menu = Object.assign({}, menu, { children: validMenu(roleCodes, menu.children, menu.href) })
    }
    let roles = (menu.meta ? menu.meta.roles : null) || []
    let itemRoleCodes = roles.map(role => {
      return role.code
    })
    let hasRole = itemRoleCodes.find(n => roleCodes.includes(n))
    if (hasRole || (childrenSize !== 0 && !validatenull(menu.children))) {
      menu.href = parentPath + (menu.href.startsWith('/') ? '' : '/') + menu.href
      list.push(menu)
    }
  })
  return list
}

const deepMenuAllSearchControlMsgType = (menuAll = [], controlAll = []) => {
  menuAll.forEach(menu => {
    if (menu.meta && menu.meta.controls) {
      let controls = menu.meta.controls
      controls.forEach(control => {
        let match = controlAll.find(ctrl => ctrl.id === control.id)
        if (match) {
          !match.meta && (match.meta = {})
          match.meta['messageTypes'] = control.meta['messageTypes']
        }
      })
    }
    if (menu.children && menu.children.length > 0) {
      deepMenuAllSearchControlMsgType(menu.children, controlAll)
    }
  })
}
const user = {
  state: {
    userInfo: getStore({ name: 'userInfo' }) || {},
    menus: getStore({ name: 'menus' }) || [],
    roles: getStore({ name: 'roles' }) || [],
    controls: [],
    // temp routing key
    stompClient: StompClient,
    routingKeys: [],
    messages: []
  },
  actions: {
    LoadAuthorizationInfo ({ commit, state, dispatch }) {
      return new Promise((resolve, reject) => {
        // 加载用户信息
        dispatch('GetUserInfo').then(() => {
          console.log('获取用户信息完成')
          resolve()
        })
      }).then(() => {
        // 加载菜单
        return new Promise((resolve, reject) => {
          dispatch('GetMenus').then(() => {
            console.log('获取菜单完成')
            resolve()
          })
        })
      })
    },
    // 系统准备工作
    ActionReady ({ commit, state, dispatch }) {
      return new Promise(resolve => {
        // 消息连接
        let ip = ip2String(state.userInfo.meta.ip)
        // 连接mq stomp
        state.stompClient.connect({queue: state.userInfo.username + '_' + ip}).then(() => {
          // console.log('=> rabbitmq 连接已启动')
        })
        resolve()
      })
    },
    // 获取用户信息
    GetUserInfo ({ commit, state, dispatch }) {
      return new Promise((resolve, reject) => {
        if (!validatenull(state.userInfo)) {
          resolve(state.userInfo)
        } else {
          getUserInfo().then((res) => {
            if (res.data.password) {
              delete res.data.password
            }
            console.log('userInfo', res.data)
            const data = res.data
            commit('SET_USERIFNO', data)
            resolve(data)
          })
        }
      }).then(userInfo => {
        return new Promise(resolve => {
          commit('SET_ROLES', userInfo.meta ? userInfo.meta.roles : [])
          resolve()
        })
      })
    },
    // 获取菜单
    GetMenus ({ commit, state }) {
      return new Promise(resolve => {
        if (!validatenull(state.menus)) {
          resolve(state.menus)
        } else {
          getMenu().then((res) => {
            const data = res.data
            commit('SET_MENUS', data)
            resolve(data)
          })
        }
      })
    },
    // 退出清空
    DestroyFrame ({ commit, state }) {
      return new Promise((resolve, reject) => {
        commit('CLEAR_USERINFO')
        commit('CLEAR_ROLES')
        commit('DEL_ALL_TAB')
        commit('CLEAR_MENUS')
        resolve()
      })
    },
    // 绑定菜单控件的消息
    BindControlMessageType ({ commit, rootState }, action = {}) {
      let menuAll = rootState.admin.menuAll
      commit('SET_CONTROL_MSGTYPE', {menuAll, menuControl: action})
    }
  },
  mutations: {
    SET_USERIFNO: (state, userInfo) => {
      state.userInfo = userInfo
      setStore({ name: 'userInfo', content: state.userInfo, type: 'session' })
    },
    CLEAR_USERINFO: (state) => {
      state.userInfo = {}
      removeStore({ name: 'userInfo' })
    },
    SET_ROLES: (state, roles = []) => {
      state.roles = roles
      setStore({ name: 'roles', content: state.roles, type: 'session' })
    },
    CLEAR_ROLES: (state) => {
      state.roles = []
      removeStore({ name: 'roles' })
    },
    SET_MENUS: (state, menus) => {
      state.menus = validMenu(state.roles, menus)
      setStore({ name: 'menus', content: state.menus, type: 'session' })
    },
    CLEAR_MENUS: (state) => {
      state.menus = []
      removeStore({ name: 'menus' })
    },
    SET_CONTROL: (state, roles = []) => {
      let controls = []
      roles.forEach(role => {
        if (state.roles.includes(role.code)) {
          let roleControls = role.meta ? role.meta.controls : []
          roleControls.forEach(control => {
            let exist = controls.find(item => control.code === item.code)
            if (!exist) {
              controls.push(control)
            }
          })
        }
      })
      replaceLocalUrlPrefx(controls)
      state.controls = controls
    },
    SET_CONTROL_MSGTYPE: (state, {menuAll, menuControl}) => {
      let controls = state.controls
      if (menuControl && menuControl.id) {
        // 单个设置
        let control = controls.find(item => item.id === menuControl.id)
        if (control) {
          !control.meta && (control.meta = {})
          control.meta['messageTypes'] = menuControl.meta['messageTypes']
        }
      } else {
        // 全部设置
        deepMenuAllSearchControlMsgType(menuAll, controls)
      }
    },
    SET_ROUTINTKEYS: (state, roles = []) => {
      let routingKeys = []
      roles.forEach(role => {
        if (state.roles.includes(role.code) && role.meta.messageTypes) {
          for (let messageType of role.meta.messageTypes) {
            if (!routingKeys.includes(messageType)) {
              routingKeys.push(messageType)
            }
          }
        }
      })
      console.log('routingKeys', routingKeys)
      state.routingKeys = routingKeys
    },
    SET_MESSAGES: (state, messages, pushFlag) => {
      if (pushFlag) {
        state.messages.push(...messages)
      } else {
        state.messages = messages
      }
    }
  }
}
export default user
