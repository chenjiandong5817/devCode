// import Tools from './../../../common/js/template-tools'
import Vue from 'vue'
import DATASOURCES from './../datasource'
import Tools from './../../../common/js/template-tools'
import { Message } from 'element-ui'

let alertTag = false

export const haveDataSource = (conf) => {
  let result = false
  for (let key in conf) {
    if (key === 'dataSource') {
      result = true
      break
    }
  }
  return result
}

export const addDataSource = (conf) => {
  Vue.set(conf, 'dataSource', '')
  Vue.set(conf, 'singleData', true)
  Vue.set(conf, 'mainPager', false)
}

export const deleteDataSource = (conf) => {
  Vue.delete(conf, 'dataSource')
  Vue.delete(conf, 'singleData')
  Vue.delete(conf, 'mainPager')
}

export const getDataSourceOptionById = (id) => {
  let dataSource = null
  for (let index in DATASOURCES) {
    if (DATASOURCES[index].id === id) {
      dataSource = Tools.deepCopy(DATASOURCES[index])
      break
    }
  }
  return dataSource
}

export const inputStr2Json = (target, key, value) => {
  try {
    // this.templateConf[target] = JSON.parse(value)
    Vue.set(target, key, JSON.parse(value))
    alertTag = false
    Message.closeAll()
  } catch (_) {
    console.log('非法的json数据')
    if (alertTag) return
    alertTag = true
    Message.warning({
      message: '非法的json数据',
      duration: 0,
      showClose: true,
      onClose: () => {
        alertTag = false
      }
    })
  }
}
