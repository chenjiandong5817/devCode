/*
* @Author: chenjiandong
* @Date:   last_modified_time
* @Last Modified by:   chenjiandong
* @Last Modified time: last_modified_time
*/
/*
 * @Author: cdroid
 * @Date: 2017-05-25 10:56:55
 * @Last Modified by: cdroid
 * @Last Modified time: 2017-09-18 15:02:00
 * @Description:  vuex store实例
 */
import Vue from 'vue'
import Vuex from 'vuex'
import * as actions from './actions'
import * as getters from './getters'

Vue.use(Vuex)

// 应用初始状态
const state = {
  userStorage: JSON.parse(sessionStorage.getItem('userStorage')) || { user: null, token: '' },
  cached: 0
}

// 定义所需的 mutations
const mutations = {
  saveUserStorage (state, storageData) {
    sessionStorage.setItem('userStorage', JSON.stringify(storageData))
    if (storageData && storageData.user && storageData.token) {
      state.userStorage = storageData
    }
  },
  removeUserStorage (stata) {
    sessionStorage.removeItem('userStorage')
    state.userStorage = {
      user: null,
      token: ''
    }
  },
  reCached (state, payload) {
    state.cached += 1
  }
}

// 创建 store 实例
export default new Vuex.Store({
  actions,
  getters,
  state,
  mutations
})
