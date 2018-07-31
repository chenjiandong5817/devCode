import { setStore, getStore } from '@/util/store'
import { getDic } from '@/api/admin'
const common = {
  state: {
    // 跟admin.js 里面的 xxxxLoading区分开来，这个isLoading 只用来判断框架需要参数是否加载完成
    isLoading: true,
    isCollapse: false,
    isFullScren: false,
    isUnreceivable: getStore({ name: 'isUnreceivable' }) || false,
    isLock: getStore({ name: 'isLock' }) || false,
    lockPasswd: getStore({ name: 'lockPasswd' }) || '',
    // 消息参数队列
    messageParamsQueue: []
  },
  actions: {
    // 全局数据加载完毕标志
    LoadingComplete ({ commit, state, dispatch }) {
      return new Promise((resolve, reject) => {
        commit('SET_LOADING', false)
        resolve()
      })
    },
    PushMessageParams ({ commit }, params) {
      commit('PUSH_MESSAGE_PARAMS_QUEUE', params)
    },
    ShiftMessageParams ({ commit, state }) {
      let obj = state.messageParamsQueue[0] || null
      commit('SHIFT_MESSAGE_PARAMS_QUEUE')
      return Promise.resolve(obj)
    },
    // 获取字典公用类
    GetDic ({ commit, state, dispatch }, dic) {
      return new Promise((resolve, reject) => {
        if (typeof dic === 'string') {
          dic = [dic]
        }
        if (dic instanceof Array) {
          Promise.all(dic.map(ele => getDic(ele))).then(data => {
            let result = {}
            dic.forEach((ele, index) => {
              result[ele] = data[index].data
            })
            resolve(result)
          })
        }
      })
    }
  },
  mutations: {
    SET_LOADING: (state, status) => {
      state.isLoading = status
    },
    SET_COLLAPSE: (state, action) => {
      state.isCollapse = !state.isCollapse
    },
    SET_FULLSCREN: (state, action) => {
      state.isFullScren = !state.isFullScren
    },
    SET_UNRECEIVABLE: (state, action) => {
      state.isUnreceivable = !state.isUnreceivable
      setStore({ name: 'isUnreceivable', content: state.isUnreceivable, type: 'session' })
    },
    PUSH_MESSAGE_PARAMS_QUEUE: (state, params) => {
      state.messageParamsQueue.push(params)
    },
    SHIFT_MESSAGE_PARAMS_QUEUE: (state, action) => {
      state.messageParamsQueue.shift()
    }
  }
}
export default common
