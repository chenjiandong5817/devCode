/**
 * 使用authKey获取于后台数据交互的api。参数可以不按照顺序填写
 * key: 权限键，String类型，在菜单权限控件（menuControl）的键值。
 * params: axios参数，Object类型，可以包含过滤条件和分页参数。
 * getDataFn(res): 回调方法，Function类型，在获取数据完成后，根据自定义的方法从载体内获取数据。
 *   - 参数
 *   - res: axios获取回来的原始值
 */
import Vue from 'vue'
class RemoteData {
  constructor () {
    for (let i = 0; i < arguments.length; i++) {
      let element = arguments[i]
      if (typeof element === 'string') {
        this[RemoteData.KEY] = element
      } else if (element instanceof Function) {
        this.getDataFn = element
      } else if (element) {
        this.params = element
      }
    }
  }
  findData () {
    if (!this[RemoteData.KEY]) {
      throw new Error(RemoteData.KEY + ' is required...')
    }
    let ajax = Vue.prototype.$auth(this[RemoteData.KEY])
    return ajax(this.params).then(res => {
      return Promise.resolve({[this[RemoteData.KEY]]: this.getDataFn ? this.getDataFn(res) : res})
    })
  }
  static instance (key, params, getDataFn) {
    return new RemoteData(key, params, getDataFn)
  }
}
RemoteData.KEY = 'authKey'
export default RemoteData
