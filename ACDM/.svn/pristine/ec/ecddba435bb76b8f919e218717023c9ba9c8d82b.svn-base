import { setStore, getStore, removeStore } from '@/util/store'

const tabObj = {
  label: '',
  name: '',
  value: '',
  query: '',
  closable: true
}
function setFistTab (list) {
  if (list.length === 1) {
    list[0].closable = false
  } else {
    list.some(a => {
      a.closable = !(a.name === 'home')
    })
  }
  return list
}

const navs = {
  state: {
    tabList: getStore({ name: 'tabList' }) || [],
    tab: getStore({ name: 'tab' }) || tabObj,
    tabHome: {
      label: '首页',
      name: 'home',
      value: '/home'
    },
    tabCurrent: getStore({name: 'currentTab'}) || [
      {
        label: '首页',
        name: 'home',
        value: '/home'
      }
    ]
  },
  actions: {

  },
  mutations: {
    ADD_TAB: (state, action) => {
      state.tab = action
      setStore({ name: 'tab', content: state.tab, type: 'session' })
      if (state.tabList.some(a => a.value === action.value)) return
      if (state.tabList.length > 6) {
        let index = state.tabList.findIndex(tab => tab.name !== 'home')
        if (index >= 0) {
          state.tabList.splice(index, 1)
        }
      }
      state.tabList.push({
        label: action.label,
        name: action.name,
        value: action.value,
        query: action.query
      })
      state.tabList = setFistTab(state.tabList)
      setStore({ name: 'tabList', content: state.tabList, type: 'session' })
    },
    SET_TAB_CURRENT: (state, tabCurrent) => {
      state.tabCurrent = tabCurrent
      setStore({ name: 'tabCurrent', content: state.tabCurrent })
    },
    SET_TAB: (state, name) => {
      state.tabList.forEach((ele, num) => {
        if (ele.name === name) {
          state.tab = state.tabList[num]
          setStore({ name: 'tab', content: state.tab })
        }
      })
    },
    DEL_ALL_TAB: (state, action) => {
      state.tab = tabObj
      state.tabList = []
      removeStore({ name: 'tab' })
      removeStore({ name: 'tabList' })
    },
    DEL_TAB_OTHER: (state, tab) => {
      if (!tab) {
        tab = state.tab
      }
      for (const [i, v] of state.tabList.entries()) {
        if (v.value === tab.value) {
          state.tabList = state.tabList.slice(i, i + 1)
          state.tab = state.tabList[0]
          if (state.tab.name !== 'home') {
            state.tabList.unshift(state.tabHome)
          }
          state.tabList[0].closable = false
          setStore({ name: 'tab', content: state.tab, type: 'session' })
          setStore({ name: 'tabList', content: state.tabList, type: 'session' })
          break
        }
      }
    },
    DEL_TAB: (state, action) => {
      for (const [i, a] of state.tabList.entries()) {
        if (a.value === action.value) {
          if (!a.closable) break
          state.tabList.splice(i, 1)
          state.tabList = setFistTab(state.tabList)
          state.tab = state.tabList[i > 0 ? (i - 1) : 0]
          setStore({ name: 'tab', content: state.tab, type: 'session' })
          setStore({ name: 'tabList', content: state.tabList, type: 'session' })
          break
        }
      }
    }
  }
}

export default navs
