const getters = {
  // admin
  messageTypeAll: state => state.admin.messageTypeAll,
  roleAll: state => state.admin.roleAll,
  menuAll: state => state.admin.menuAll,
  roleLoading: state => state.admin.roleLoading,
  service: state => state.admin.service,
  // commom
  isLoading: state => state.common.isLoading,
  isCollapse: state => state.common.isCollapse,
  isUnreceivable: state => state.common.isUnreceivable,
  isFullScren: state => state.common.isFullScren,
  // user
  userInfo: state => state.user.userInfo,
  roles: state => state.user.roles,
  permission: state => state.user.permission,
  menus: state => state.user.menus,
  // tab
  tabList: state => state.tabs.tabList,
  tabCurrent: state => state.tabs.tabCurrent,
  tabHome: state => state.tabs.tabHome,
  tab: state => state.tabs.tab,
  // router
  addRouters: state => state.router.addRouters,
  routers: state => state.router.routers
}
export default getters
