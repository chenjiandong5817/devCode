// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
// By default we import all the components.
// Only reserve the components on demand and remove the rest.
// Style is always required.
import {
//  /* eslint-disable no-unused-vars */
 Style,
 Button,
 Checkbox,
 CheckboxGroup,
 Loading,
 Tip,
 Popup,
 Toast,
 Picker,
 CascadePicker,
 TimePicker,
 Dialog,
 ActionSheet,
 Scroll,
 Slide,
 IndexList
} from 'cube-ui'
import App from './app.vue'
import router from '@/router/userCenter.js'
import store from '@/store/userCenter/index.js'

Vue.use(Style)
Vue.use(Button)
Vue.use(Checkbox)
Vue.use(CheckboxGroup)
Vue.use(Loading)
Vue.use(Tip)
Vue.use(Popup)
Vue.use(Toast)
Vue.use(Picker)
Vue.use(CascadePicker)
Vue.use(TimePicker)
Vue.use(Dialog)
Vue.use(ActionSheet)
Vue.use(Scroll)
Vue.use(Slide)
Vue.use(IndexList)

Vue.config.productionTip = false
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  template: '<App/>',
  components: { App }
})
