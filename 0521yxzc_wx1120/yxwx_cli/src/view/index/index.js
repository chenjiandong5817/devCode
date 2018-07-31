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
import AMap from 'vue-amap'
import App from './app.vue'
import router from '@/router/index.js'
import store from '@/store/index/index.js'

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

Vue.use(AMap)

AMap.initAMapApiLoader({
  // key: '5c91a4313d4ffeb0d844c226edc37cbe',
  key: '452056fb0b11a629c147b7c8c162f02b',
  plugin: ['AMap.Autocomplete', 'AMap.PlaceSearch', 'AMap.Geolocation']
})
Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  template: '<App/>',
  components: { App }
})
