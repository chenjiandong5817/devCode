/*
* @Author: cdroid
* @Date:   2018-05-17 16:23:30
* @Last Modified by:   chenjiandong
* @Last Modified time: 2018-07-10 11:18:46
*/
import state from './state'
import axios from '../ajax'
import md5 from 'js-md5'
export default {
/* -------------工具类方法---------- */
  setLocalStorage (key, value) {
    if (window.localStorage) {
      var storage = window.localStorage
      if (typeof (value) !== 'string') { storage.setItem(key, JSON.stringify(value)) } else { storage.setItem(key, value) }
    } else {
      console.log('该应用不支持本地存储！')
    }
  },
  getLocalStorage (key, type) {
    if (window.localStorage) {
      var storage = window.localStorage
      var value
      if (type === 'object') { value = JSON.parse(storage.getItem(key)) } else { value = storage.getItem(key) }
      return value
    } else {
      console.log('该应用不支持本地存储！')
    }
  },
  delLocalStorage (key) {
    if (window.localStorage) {
      var storage = window.localStorage
      storage.removeItem(key)
    } else {
      console.log('该应用不支持本地存储！')
    }
  },
  // 对象数组集去重方法
  removeDup (array) {
    let arr = {}
    arr = array.reduce(function (item, next) {
      if (!arr[next.name]) {
        arr[next.name] = true
        item.push(next)
      }
      // arr[next.name] ? '' : arr[next.name] = true && item.push(next)
      return item
    }, [])
    return arr
  },
  distanceByLnglat (lng1, lat1, lng2, lat2) {
    var radLat1 = this.Rad(lat1)
    var radLat2 = this.Rad(lat2)
    var a = radLat1 - radLat2
    var b = this.Rad(lng1) - this.Rad(lng2)
    var s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)))
    s = s * 6378137.0// 取WGS84标准参考椭球中的地球长半径(单位:m)
    s = Math.round(s * 10000) / 10000
    return s
  },
  Rad (d) {
    d = parseFloat(d)
    return d * Math.PI / 180.0
  },
  Datefilter (d) {
    let time = new Date(d)
    return time.toLocaleDateString().replace(/\//g, '-') + ' ' + time.toTimeString().split(' ')[0]
  },
  changeUrl (subStstus, orderId) {
    var openId = this.getLocalStorage('openId')
    var timeZone = localStorage.getItem('TIME_ZONE')
    console.log('timeZone', timeZone)
    var h = ''
    if (subStstus === 100) {
      h = '/wechat/order/wreply/' + openId + '/' + orderId + '?orderUuid=' + orderId + '/' + timeZone
    } else if (subStstus === 200) { // 预约
      h = '/wechat/order/wmeeting1/' + openId + '/' + orderId + '/' + timeZone + '?orderUuid=' + orderId
    } else if (subStstus === 201) { // 即时
      h = '/wechat/order/wmeeting/' + openId + '/' + orderId + '/' + timeZone + '?orderUuid=' + orderId
    } else if (subStstus === 202) { // 待上车
      h = '/wechat/order/wtakecar/' + openId + '/' + orderId + '/' + timeZone + '?orderUuid=' + orderId
    } else if (subStstus === 300) { // 行程中
      h = '/wechat/order/onway/' + openId + '/' + orderId + '/' + timeZone + '?orderUuid=' + orderId
    } else if (subStstus === 301) { // 行程中
      h = '/wechat/order/onway/' + openId + '/' + orderId + '/' + timeZone + '?orderUuid=' + orderId
    } else if (subStstus === 400 || subStstus === 402 || subStstus === 401) { // 400待支付; 402部分支付
      h = '/wechat/order/wpay/' + openId + '/' + orderId + '/' + timeZone + '?orderUuid=' + orderId
    } else if (subStstus === 500) { // 待评价
      h = '/wechat/order/wrated/' + openId + '/' + orderId + '/' + timeZone + '?orderUuid=' + orderId
    } else { // 取消
        // 跳转到一个订单无人接收自动取消界面，目前貌似没有
      h = '/wechat/view/index'
    }
    if (window.location.href.indexOf(h) === -1) {
      window.location.href = h
    }
  },
/* -------------工具类方法---------- */
  // 发送短信
  sendMsgRequest (val) {
    let promise = new Promise((resolve, reject) => {
      let key = 'ARPORT912012KEY'
      let openId = this.getLocalStorage('openId')
      let sign = md5('openId=' + openId + '&key=' + key).toUpperCase()
      axios.post('/wechat/passenger/sendCode', {
        mobile: val,
        sign: sign,
        openId: openId
      }).then((data) => {
        resolve(data)
      }).catch((err) => {
        reject(err)
      })
    })
    return promise
  },
  // 登录请求
  loginRequest (obj) {
    let promise = new Promise((resolve, reject) => {
      axios.post('/wechat/passenger/login', {
        mobile: obj.phone,
        identifyCode: obj.validate,
        // nickName: 'nick',
        // sex: 1,
        // openId: state.openId
        nickName: this.getLocalStorage('nickName'),
        sex: this.getLocalStorage('wxUserSex'),
        openId: this.getLocalStorage('openId')
      }).then((data) => {
        resolve(data)
      }).catch((err) => {
        reject(err)
      })
    })
    return promise
  },
  /* -------------元翔定制部分---------- */
  opened_arport () {
    let promise = new Promise((resolve, reject) => {
      axios.get('/wechat/sysarea/opened.arport').then((data) => {
        resolve(data)
      }).catch((err) => {
        reject(err)
      })
    })
    return promise
  },
  // 重复部分
  get_openedCity () {
    let promise = new Promise((resolve, reject) => {
      axios.get('/wechat/sysarea/opened.arport').then((data) => {
        if (data.success) {
          resolve(data)
        }
      }).catch((err) => {
        reject(err)
      })
    })
    return promise
  },
  // 同样是获取areaUuid有什么不同1
  setArport_uuid (arport) {
    let promise = new Promise((resolve, reject) => {
      arport = arport || '厦门市'
      let arportlist = this.getLocalStorage('OPENED_ARPORT', 'object')
      let uuid = ''
      let sysAreaConfigDto = {}
      if (!arportlist) {
        setTimeout(this.setArport_uuid(arport), 60)
        return
      }
      for (var i = 0; i < arportlist.length; i++) {
        var ai = arportlist[i]
        // 对城市区域解析
        var ci = ai.cityNames.split(',')
        for (var j = 0; j < ci.length; j++) {
          var cij = ci[j]
          if (cij) {
            if (arport.indexOf(cij) > -1) {
              sysAreaConfigDto = ai.sysAreaConfigDto
              uuid = ai.uuid
              break
            }
          }
        }
      }
      // 为车型准备的OPENED_ARPORT_UUID福州CB33厦门F807。先修改这个值，再去调用get_CarModels接口
      this.setLocalStorage('OPENED_ARPORT_UUID', uuid)
      // this.setLocalStorage('sysAreaConfigDto', sysAreaConfigDto)
      resolve(sysAreaConfigDto)
    // return uuid
    })
    return promise
  },
  // 同样是获取areaUuid有什么不同2
  fliter_originAreaUuid (arport) {
    let promise = new Promise((resolve, reject) => {
      arport = arport || '厦门市'
      let arportlist = this.getLocalStorage('OPENED_ARPORT', 'object')
      let uuid = ''
      // let sysAreaConfigDto = {}
      if (!arportlist) {
        setTimeout(this.setArport_uuid(arport), 60)
        return
      }
      for (var i = 0; i < arportlist.length; i++) {
        var ai = arportlist[i]
        // 对城市区域解析
        var ci = ai.cityNames.split(',')
        for (var j = 0; j < ci.length; j++) {
          var cij = ci[j]
          if (cij) {
            if (arport.indexOf(cij) > -1) {
              // sysAreaConfigDto = ai.sysAreaConfigDto
              uuid = ai.uuid
              break
            }
          }
        }
      }
      // 为车型准备的OPENED_ARPORT_UUID福州CB33厦门F807
      this.setLocalStorage('OPENED_ARPORT_UUID', uuid)
      resolve(uuid)
    })
    return promise
  },
  // 获取当前城市站点信息
  get_station () {
    let promise = new Promise((resolve, reject) => {
      axios.post('/api/base/getLocations', {
        /* params: { */
        // areaUuid: state.currentCity.areaUuid
        areaUuid: this.getLocalStorage('OPENED_ARPORT_UUID')
        /* } */
      }).then((data) => {
        if (data.success) {
          resolve(data.data)
        }
      }).catch((err) => {
        reject(err)
      })
    })
    return promise
  },
  // 获取当前城市车型信息。通过传入areaUuid来获取这个城市匹配的车型。
  get_CarModels () {
    let promise = new Promise((resolve, reject) => {
      axios.get('/api/base/getCarModels', {
        areaUuid: this.getLocalStorage('OPENED_ARPORT_UUID')
      }).then((data) => {
        if (data.success) {
          // console.log(data)
          resolve(data.data)
        }
      }).catch((err) => {
        reject(err)
      })
    })
    return promise
  },
  post_validateOpenId () {
    let self = this
    let promise = new Promise((resolve, reject) => {
      axios.post('/wechat/passenger/login/validateOpenId', {
        openId: self.getLocalStorage('openId') ? self.getLocalStorage('openId') : state.openId
      }).then((data) => {
        resolve(data)
      }).catch((err) => {
        reject(err)
      })
    })
    return promise
  },
  // 检测是否存在状态订单
  post_jugStatus (uuid) {
    let self = this
    let promise = new Promise((resolve, reject) => {
      let data = {
        passengerUuid: uuid
      }
      axios.post('/wechat/order/jugStatus', data).then((result) => {
        if (result.success) {
          var order = result.data

          if (order.defaultOrders) { // 默认订单状态
            self.changeUrl(order.defaultOrders.subStatus, order.defaultOrders.uuid)
          } else
            if (order.ongoingOrders) { // 存在进行中的订单
              if (order.ongoingOrders.subStatus !== 200 && order.ongoingOrders.subStatus !== 201) {
                self.changeUrl(order.ongoingOrders.subStatus, order.ongoingOrders.uuid)
              }
                // 判断是否存在预支付及其状态
              switch (order.ongoingOrders.prepaidStatus) {
                case 1:
                case 2:
                case 4:
                  { // 预支付订单 待支付 支付中 支付异常
                    let openId = self.getLocalStorage('openId')
                    window.location.href = '/wechat/order/wprepayment/' + openId + '/' + order.ongoingOrders.uuid + '/' + localStorage.getItem('TIME_ZONE')
                  }
                  return
                case 0:
                case 3:
                  var now = Date.now()
                  // 无需预支付----预支付订单已支付
                  if ((order.ongoingOrders.deparTime >= now + 1800000)) {
                            // 当出发时间超过30分钟后，默认停留本页面
                    return
                  }
                  self.changeUrl(order.ongoingOrders.subStatus, order.ongoingOrders.uuid)
                  break
                default:
              }
            } else if (order.toPayOrders) { // 存在待支付订单
              self.changeUrl(order.toPayOrders.subStatus, order.toPayOrders.uuid)
            }
        }
      }).catch((err) => {
        reject(err)
      })
    })
    return promise
  },
  // 上传起终点
  post_disAndDura () {
    let promise = new Promise((resolve, reject) => {
      axios.post('/wechat/price/disAndDura', {
        'oriLng': state.locationFrom.location.lng,
        'oriLat': state.locationFrom.location.lat,
        'destLng': state.locationTo.location.lng,
        'destLat': state.locationTo.location.lat
      }).then((data) => {
        if (data.success) {
          resolve(data)
        }
      }).catch((err) => {
        reject(err)
      })
    })
    return promise
  },
  // 预估价请求
  post_estimated () {
    let promise = new Promise((resolve, reject) => {
      let data = {
        openId: state.openId,
        orderType: state.order.orderType, // 订单类型：接机？送机
        levelType: state.carType.value, // 车型
        planMileage: state.disAndDuraResult.distance / 1000, // 估计里程数
        planDuration: (state.disAndDuraResult.duration / 60) + 1, // 估计时长
        deparTimeStr: this.Datefilter(state.order.departTime), // 出发时间
        // deparTimeStr: '2018-01-13 11:35', // 出发时间
        userCouponUuid: state.payType.type === 2 ? state.coupon.id : '', // 优惠券
        areaUuid: state.originAreaUuid // 区域id
      }

      axios.post('/wechat/price/estimatedFare', data).then((data) => {
        resolve(data)
      }).catch((err) => {
        reject(err)
      })
    })
    return promise
  },
  // 预估金额优惠券检索请求
  post_findCoupon () {
    let promise = new Promise((resolve, reject) => {
      let data = {
        openId: state.openId,
        areaUuid: state.originAreaUuid,
        orderType: state.order.orderType
      }
      axios.get('/wechat/coupon/findEnableCouponForEstimate', data).then((data) => {
        if (data.success) {
          resolve(data)
        }
      }).catch((err) => {
        reject(err)
      })
    })
    return promise
  }
}
