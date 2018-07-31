import { constantRouterMap, asyncRouterMap } from '@/router'
import { validatenull } from '@/util/validate'
import { deepCopy } from '@/util/util'

function hasPermission (route, routerDatas) {
  return routerDatas[route.name] !== undefined
}

function filterAsyncRouter (asyncRouterMap, menuDatas = {}) {
  const accessedRouters = asyncRouterMap.filter(route => {
    if (route.hidden) { // 异步添加自定义路由
      return true
    }
    if (hasPermission(route, menuDatas)) {
      let key = route.name
      route.name = menuDatas[key].label
      route.icon = menuDatas[key].icon
      if (route.children && route.children.length) {
        route.children = filterAsyncRouter(route.children, menuDatas[key].children)
      }
      return true
    }
    return false
  })
  return accessedRouters
}

function groupRouter (routers) {
  let menuDatas = {}
  routers.forEach(router => {
    if (router.children) {
      router.children = groupRouter(router.children)
    }
    menuDatas[router.code] = router
  })
  return menuDatas
}

const permission = {
  state: {
    routers: constantRouterMap,
    addRouters: undefined
  },
  actions: {
    GenerateRoutes ({commit, rootState}) {
      return new Promise(resolve => {
        let accessMenus = deepCopy(rootState.user.menus)
        if (validatenull(accessMenus)) {
          return resolve()
        }
        let menuDatas = groupRouter(accessMenus)
        accessMenus = filterAsyncRouter(asyncRouterMap, menuDatas)
        commit('SET_ROUTERS', accessMenus)
        resolve()
      })
    }
  },
  mutations: {
    SET_ROUTERS: (state, routers) => {
      state.addRouters = routers
      state.routers = constantRouterMap.concat(routers)
    }
  }
}

export default permission
