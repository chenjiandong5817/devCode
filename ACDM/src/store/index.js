import Vue from 'vue'
import Vuex from 'vuex'
import getters from '@/store/getters'
import user from '@/store/modules/user'
import common from '@/store/modules/common'
import admin from '@/store/modules/admin'
import tabs from '@/store/modules/tabs'
import router from '@/store/modules/router'

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    user,
    admin,
    common,
    tabs,
    router
  },
  getters
})
export default store
