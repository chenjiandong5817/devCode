/*
* @Author: chenjiandong
* @Date:   last_modified_time
* @Last Modified by:   chenjiandong
* @Last Modified time: last_modified_time
*/
import {axios} from './../../api/raiis-axios'
import Config from './../config/cacheConfig'
import Moment from 'moment'
import Store from './../../vuex/store'

const RAIIS_COMMON_MOMENT_FORMAT = 'YYYYMMDDHHmmss'
const RAIIS_COMMON_CACHE = 'RaiisCommonCacheStore'
const CACHE_FLUSH_TIME = Config.flushTime
let cacheTimer = null
let cacheLoading = false
let isUserOldCache = false
// 缓存键值对
let cacheTargets = Config.apis
let keys = () => {
  return Object.keys(cacheTargets)
}

class Cache {
  constructor (array) {
    this.array = array
  }
  generateSessionCache () {
    let oldCache = JSON.parse(sessionStorage.getItem(RAIIS_COMMON_CACHE))
    if (!sessionStorage.getItem(RAIIS_COMMON_CACHE) || Moment().diff(Moment(oldCache.time, RAIIS_COMMON_MOMENT_FORMAT)) >= CACHE_FLUSH_TIME) {
      isUserOldCache = false
      this.pull().then(() => {
        this.start()
      })
    } else {
      isUserOldCache = true
      console.log('--- use old cache：' + isUserOldCache + ' ---')
      this.array = oldCache.array
      this.start()
    }
  }
  // 全部缓存数据
  value () {
    return this.array
  }
  // 定时刷新
  start () {
    if (cacheTimer) {
      this.stop()
    }
    cacheTimer = setInterval(this.pull, CACHE_FLUSH_TIME)
  }
  // 删除定时刷新
  stop () {
    clearInterval(cacheTimer)
  }
  // 所有缓存的key
  keys
  // 拉取缓存
  pull (...keyArray) {
    console.log('--- start pull cache ---')
    cacheLoading = false
    let methods = []
    if (!keyArray || keyArray.length === 0) {
      keyArray = keys()
    }
    for (let key of keyArray) {
      methods.push(cacheTargets[key])
    }
    return new Promise((resolve, reject) => {
      axios.all(methods.map(method => method({pageSize: 0})))
        .then(axios.spread((...results) => {
          let allCache = {}
          results.forEach((item, index) => {
            allCache[keyArray[index]] = item.ok && item.attr.data ? item.attr.data.list : []
          })
          this.array = allCache
          sessionStorage.removeItem(RAIIS_COMMON_CACHE)
          sessionStorage.setItem(RAIIS_COMMON_CACHE, JSON.stringify({
            array: this.array,
            time: Moment().format(RAIIS_COMMON_MOMENT_FORMAT)
          }))
          // vuex 状态
          Store.commit('reCached')
          resolve()
          cacheLoading = true
          console.log('--- cache loading end: ' + cacheLoading + ' ---')
        })
      ).catch(err => {
        console.log(err)
        reject(err)
      })
    })
  }
  // 根据key匹配列表
  fetch (key) {
    return this.array ? this.array[key] : []
  }
  // 根据id匹配字段值
  findById (key, idVal, field) {
    return this.findByName(key, 'id', idVal, field)
  }
  // 根据name匹配字段值
  findByName (key, name, nameVal, field) {
    let fetchData = this.fetch(key)
    if (!fetchData) {
      return null
    }
    return (this.fetch(key).find(item => {
      return item[name] === nameVal
    }) || {})[field]
  }
  // 判断是否使用旧缓存信息
  isUserOldCache () {
    return isUserOldCache
  }
}
let cache = new Cache()
export default cache
