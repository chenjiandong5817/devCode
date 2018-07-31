/*
* @Author: cdroid
* @Date:   2018-05-17 16:23:30
* @Last Modified by:   chenjiandong
* @Last Modified time: 2018-07-10 14:31:20
*/
import state from './state'
import axios from '../ajax'
import { cityList } from '../cityData' // 全国城市数据
import api from './api'

export default {
  // vuex数据初始化
  init_data ({ commit }) {
    // 缓存所有区域信息
    let areaUuid = api.getLocalStorage('areaUuid')
    // if (areaUuid === 'cb33bbcc0e8c400f992c4d1acff274cb') {
    //   let val = {
    //     city: '福州市',
    //     areaUuid: 'cb33bbcc0e8c400f992c4d1acff274cb'
    //   }
    //   commit('SET_CURRENTCITY', val)
    // } else {
    //   let val = {
    //     city: '厦门市',
    //     areaUuid: 'f807671564b0409aa647b7b80af555b6'
    //   }
    //   commit('SET_CURRENTCITY', val)
    // }
    if (areaUuid === 'a1d47255ea3549978ae02ee64b5527f2') {
      let val = {
        city: '三明市',
        areaUuid: 'a1d47255ea3549978ae02ee64b5527f2'
      }
      commit('SET_CURRENTCITY', val)
    }
    if (areaUuid === 'f807671564b0409aa647b7b80af555b6') {
      let val = {
        city: '厦门市',
        areaUuid: 'f807671564b0409aa647b7b80af555b6'
      }
      commit('SET_CURRENTCITY', val)
    }
    if (areaUuid === 'cb33bbcc0e8c400f992c4d1acff274cb') {
      let val = {
        city: '福州市',
        areaUuid: 'cb33bbcc0e8c400f992c4d1acff274cb'
      }
      commit('SET_CURRENTCITY', val)
    }
    if (areaUuid === '110d1a9682b947278b3117fa43a6450b') {
      let val = {
        city: '杭州市',
        areaUuid: '110d1a9682b947278b3117fa43a6450b'
      }
      commit('SET_CURRENTCITY', val)
    }
    // 缓存所有区域信息
    let openId = api.getLocalStorage('openId')
    if (openId) {
      commit('SET_OPENID', openId)
    }
    api.opened_arport().then((data) => {
      if (data.success) {
        api.setLocalStorage('OPENED_ARPORT', data.data)
        for (var c = 0; c < data.data.length; c++) {
          data.data[c].cityNames = data.data[c].cityNames.substring(0, 2) + '市'
          if (data.data[c].cityNames === '泉州市') {
            data.data[c].cityNames = '厦门市'
          }
        }
        var result = []
        for (var i = 0; i < data.data.length; i++) {
          for (var a = 0; a < cityList.length; a++) {
            for (var b = 0; b < cityList[a].items.length; b++) {
              if (data.data[i].cityNames === cityList[a].items[b].name) {
                result.push(cityList[a].items[b])
              }
            }
          }
        }
        commit('SET_OPENCITYDATA', result)
      }
      // 区域接口一
      api.setArport_uuid(state.currentCity.city).then((data) => {
        commit('SET_SYSAREACONFIGDTO', data)
      })
    }).then(() => {
    // 初始化站点
      api.get_station().then((data) => {
        let list = []
      // 重新构造站点信息列表组
        data.forEach(function (el, index) {
          list[index] = {
            'name': el.address,
            'address': el.address,
            'location': {
              'M': el.lng,
              'O': el.lat,
              'lat': el.lat,
              'lng': el.lng
            },
            'uuid': el.uuid,
            'city': el.city,
            'startingEnd': el.startingEnd,
            'status': el.status
          }
        })
        commit('SET_STATIONDATA', list)
      })
    }).then(() => {
    // 初始化车型，在一进入app的时候获取这个城市拥有的车型赋值再执行一遍这个方法
      api.get_CarModels().then((data) => {
        let list = []
      // 重新构造车型信息列表组
        data.forEach(function (el, index) {
          list[index] = {
            'name': el.modelName,
            'class': 'type' + el.modelNum,
            'value': el.modelNum,
            'modelName': el.modelName,
            'modelNum': el.modelNum,
            'picNum': el.picNum,
            'terminalPic': el.terminalPic,
            'status': el.status
          }
        })
        commit('SET_CARMODELSDATA', list)
      })
    })
    // 从系统获取时间
    var systemTime = new Date()
    // 将从系统获取的时间转换为字符串
    systemTime = systemTime.toString()
    // 获取所在时区的字符串
    var timaArea = systemTime.slice(systemTime.length - 12, systemTime.length - 11)
    // 将所在时区的字符串转换为数字
    timaArea = parseInt(timaArea)
    localStorage.setItem('TIME_ZONE', timaArea)
  },
  changeCarType ({ commit }) {
    api.get_CarModels().then((data) => {
      let list = []
    // 重新构造车型信息列表组
      data.forEach(function (el, index) {
        list[index] = {
          'name': el.modelName,
          'class': 'type' + el.modelNum,
          'value': el.modelNum,
          'modelName': el.modelName,
          'modelNum': el.modelNum,
          'picNum': el.picNum,
          'terminalPic': el.terminalPic,
          'status': el.status
        }
      })
      commit('SET_CARMODELSDATA', list)
    })
  },
  get_currentCity ({commit}, areaUuid) {
    let promise = new Promise((resolve, reject) => {
      let url = '/wechat/change/session/' + areaUuid
      axios.get(url, {}).then((res) => {
        if (res.success) {
          // window.location.href = '/wechat/view/index'
        }
        resolve(res)
      })
    })
    return promise
  },
  post_validateOpenId ({ commit }) {
    let promise = new Promise((resolve, reject) => {
      api.post_validateOpenId().then((data) => {
      // 是否有在后台注册账号
        if (data.success) {
          api.post_jugStatus(data.data.uuid)
          let payType = {
            name: '个人支付',
            type: 2,
            money: data.data.balance,
            status: 1
          }
          commit('SET_USERMSG', data.data)
          commit('SET_PAYTYPE', payType)
          commit('SWITCH_LOGINMASK', !data.success)
        } else {
          commit('SWITCH_LOGINMASK', !data.success)
        }
        resolve(data)
      })
    })
    return promise
  },
  post_disAndDura ({ commit }) {
    let promise = new Promise((resolve, reject) => {
      api.post_disAndDura().then((data) => {
      // 缓存路线信息至vuex
        commit('SET_DISANDDURA', data.data)
      // 缓存路线信息至本地
        api.setLocalStorage('routeInfo', data.data)
      }).then(() => {
        // 路线修改后再次请求预估价信息
        api.post_estimated().then((res) => {
          console.log(res.data)
          if (res.success) {
            // 企业余额足够支付预估价时
            if (res.data.entBalanceIsEnough && !state.hasSwitchPayType) {
              let payType = {
                name: '企业支付',
                type: 1,
                money: res.data.entAvailableBalance,
                status: res.data.isShowEnt
              }
              commit('SET_PAYTYPE', payType)
            }
        // 将预估信息改写进vuex
            commit('SET_ESTIMATEMSG', res.data)
            console.log(res.data)
            commit('SET_ORDER', {name: 'totalFee', val: res.data.totalFee})
            commit('SET_ORDER', {name: 'costItems', val: res.data.costItems})
        // 将余额信息改写进vuex
            let payTypeList = [{
              name: '个人支付',
              type: 2,
              money: res.data.balance,
              status: 1
            }, {
              name: '企业支付',
              type: 1,
              money: res.data.entAvailableBalance,
              status: res.data.isShowEnt
            }]
            commit('SET_PAYTYPELIST', payTypeList)
        // 缓存个人余额至本地
            api.setLocalStorage('personMoney', res.data.balance)
        // 缓存最优-优惠券
            if (res.data.userCouponUuid) {
              let bestCoupon = {
                id: res.data.userCouponUuid,
                title: res.data.couponName,
                des: res.data.couponName,
                date: 0,
                discount: 0,
                money: res.data.couponMoney,
                type: 0,
                value: res.data.couponMoney
              }
              commit('SET_COUPON', bestCoupon)
            }
          }
          resolve(res)
        })
      })
    })
    return promise
  },
  // 查询可用优惠券
  post_findCoupon ({ commit }) {
    api.post_findCoupon().then((data) => {
      let arr = []
      data.data.forEach(function (el, index) {
        let item = {
          'title': el.couponName,
          'discount': el.discount,
          'money': el.money,
          'highestMoney': el.highestMoney,
          'sysCouponUuid': el.sysCouponUuid,
          'type': el.type,
          'useCarType': el.useCarType,
          'des': el.useCondition,
          'date': el.useEndTime,
          'id': el.uuid
        }
        arr.push(item)
      })
      commit('SET_COUPONLIST', arr)
    })
  },
  // 用户侧边栏状态
  switch_UserBar ({ commit }) {
    commit('SWITCH_USERBAR')
  },
  // 改变location变量值公共方法 这个switch_location
  switch_location ({ commit }, obj) {
    if (obj.name === 'location') {
      obj.name = state.location.startOrEnd ? 'locationFrom' : 'locationTo'
      if (!(!obj.val) && !obj.val.city) {
        obj.val['city'] = state.location.selectCity
      }
      // 将起终点缓存至本地
      if (obj.name === 'locationFrom') {
        api.setArport_uuid(obj.val.city).then((data) => {
          commit('SET_SYSAREACONFIGDTO', data)
        })
        // 区域接口二
        api.fliter_originAreaUuid(obj.val.city).then((areaUuid) => {
          commit('SET_ORIGINAREAUUID', areaUuid)
        })
        api.setLocalStorage('START_LOCATION_INFO', obj.val)
      } else {
        api.setLocalStorage('END_LOCATION_INFO', obj.val)
      }
    }
    commit('SWITCH_LOCATION', obj)
  },
  // 修改SET_ORIGINAREAUUID的值的方法
  changeOriginAreaUuid ({ commit }, value) {
    commit('SET_ORIGINAREAUUID', value)
  },
  // 订单发生改变
  changeOrderType ({ commit }, value) {
    commit('CHANGE_ORDER', value)
  },
  // 开通城市数据发生变化
  changeOpenCityData ({ commit }, value) {
    commit('SET_OPENCITYDATA', value)
  },
  // 设置订单信息
  switch_order ({ commit }, val) {
    commit('SET_ORDER', val)
  },
  // 选择城市
  // // 最上面的城市一选择好就联动到上下车的城市
  selectCity ({ commit }, val) {
    commit('SELECT_CITY', val)
  },
  // 重置地址组件状态信息
  resetLocation ({ commit }) {
    commit('RESET_LOCATION')
  },
/* --------------高德定位-------start-------- */
  Geolocation ({ commit }) {
    let self = this
    let mapObj
    /* mapObj = new AMap.Map('.page', {
    }) */
    /* eslint-disable */
    mapObj = new AMap.Map('.page', {
      resizeEnable: true
    })
    mapObj.plugin('AMap.Geolocation', function () {
      let geolocation = new AMap.Geolocation({
        enableHighAccuracy: true, // 是否使用高精度定位，默认:true
        timeout: 10000,          // 超过10秒后停止定位，默认：无穷大
        maximumAge: 0,           // 定位结果缓存0毫秒，默认：0
        convert: true,           // 自动偏移坐标，偏移后的坐标为高德坐标，默认：true
        showButton: true,        // 显示定位按钮，默认：true
        buttonPosition: 'LB',    // 定位按钮停靠位置，默认：'LB'，左下角
        buttonOffset: new AMap.Pixel(10, 20), // 定位按钮与设置的停靠位置的偏移量，默认：Pixel(10, 20)
        showMarker: true,        // 定位成功后在定位到的位置显示点标记，默认：true
        showCircle: true,        // 定位成功后用圆圈表示定位精度范围，默认：true
        panToLocation: true,     // 定位成功后将定位到的位置作为地图中心点，默认：true
        zoomToAccuracy: true      // 定位成功后调整地图视野范围使定位位置及精度范围视野内可见，默认：false
      })
      mapObj.addControl(geolocation)
      geolocation.getCurrentPosition(function (status, res) {
        console.log(res)
        if (status === 'complete') {
          var Geolocation = {
            adcode: res.addressComponent.adcode,
            address: res.formattedAddress,
            city: res.addressComponent.city,
            // district: res.addressComponent.district,
            district: '',
            location: {
              M: res.position.M,
              O: res.position.O,
              lat: res.position.lat,
              lng: res.position.lng
            },
            name: res.addressComponent.township + res.addressComponent.street + res.addressComponent.streetNumber
          }
          commit('SWITCH_LOCATION', {name: 'selectCity', val: res.addressComponent.city})
          commit('SET_GEOLOCATION', Geolocation)
        } else if (status === 'error') {
          console.log(res)
        }
      })
      AMap.event.addListener(geolocation, 'complete', self.Geolocation_onComplete)// 返回定位信息
      AMap.event.addListener(geolocation, 'error', self.Geolocation_onError)      // 返回定位出错信息
    })
    /* eslint-enable */
  },
  Geolocation_onComplete (data) {
    console.log(data)
  },
  Geolocation_onError (err) {
    console.log(err)
  },
/* --------------高德定位-------end-------- */
  // 城市模糊检索本地数据
  CityListSearch ({ commit }, val) {
    let self = this
    let list = []
    // 将输入内容字母转成大写
    val = val.toUpperCase()
    state.cityList.forEach(function (element, index) {
      element.items.forEach(function (item, s) {
        if (item.tags.match(val)) {
          list.push(item)
        }
      })
    })
    list = Array.from(new Set(list))
    self.state.location.tips = list
  },
  // 详细地址输入提示
  autocomplete ({ commit }, value) {
    // 此处ESLint 会报错AMap is not defined 但是不影响
    let self = this
    /* eslint-disable */
    AMap.plugin('AMap.Autocomplete', function () {
      var AutoComplete = new AMap.Autocomplete({
        city: state.location.selectCity
      })
      value = state.location.selectCity + value // 将当前选择城市添加进搜索关键词中，可提升精确率
      AutoComplete.search(value, function (status, result) {
        self.state.location.tips = result.tips
      })
    })
    /* eslint-enable */
  },
  // 选择优惠券
  setcoupon ({commit}, value) {
    let id = value
    let coupon = state.coupon
      // 当不选择优惠券时
    if (id === '-1') {
      coupon = {
        id: '-1',
        value: 0,
        type: 0,
        title: '',
        des: '',
        date: 0
      }
    } else {
      state.couponList.forEach(function (item, index, arr) {
      // 匹配优惠券
        if (item.id === id) {
          coupon = item
        }
      })
    }
    commit('SET_COUPON', coupon)
  },
  // 发送短信
  sendMsgRequest ({commit}, value) {
    return api.sendMsgRequest(value)
  },
  // 登录请求
  loginRequest ({commit}, obj) {
    return api.loginRequest(obj)
  },
  // 订单请求
  orderRequest ({commit}) {
    // let self = this
    let promise = new Promise((resolve, reject) => {
      // 加入判断时间的0706
      var deparTimeChanged = state.order.departTime
      // 判断是哪个时区的，然后自动转换成北京时间
      // 从系统获取时间
      var systemTime = new Date()
      // 将从系统获取的时间转换为字符串
      systemTime = systemTime.toString()
      // 获取所在时区的字符串
      var timaArea = systemTime.slice(systemTime.length - 12, systemTime.length - 11)
      // 将所在时区的字符串转换为数字
      timaArea = parseInt(timaArea)
      localStorage.setItem('TIME_ZONE', timaArea)
      if (timaArea === 8) {
        deparTimeChanged = deparTimeChanged * 1
      }
      if (timaArea === 0) {
        deparTimeChanged = deparTimeChanged + 8 * 60 * 60 * 1000
      }
      // other
      let nowDate = Date.parse(new Date()) // 当前时间
      let departTime = state.order.departTime // 出发时间
      let iFneedPrePay = ((departTime >= (nowDate + state.sysAreaConfigDto.prepaidTime * 60000) || state.sysAreaConfigDto.prepaidTime === 0) && state.sysAreaConfigDto.prepaidStatus && state.payType.type === 2)

      let dataParam = {
        openId: api.getLocalStorage('openId') ? api.getLocalStorage('openId') : state.openId, // 微信openid

        prepayType: state.payType.type,  // 预支付方式（1企业支付，2个人支付）

        originAddress: state.location.locationFrom.name, // 起点的详细地址,
        originDetailAddress: (state.location.locationFrom.district || '') + state.location.locationFrom.address, // 起点的详细地址,
        originCity: state.location.locationFrom.city, // 起点城市,
        originLng: state.location.locationFrom.location.lng, // 起点精度,
        originLat: state.location.locationFrom.location.lat, // 起点纬度,

        destAddress: state.location.locationTo.name, // 终点的详细地址,
        destDetailAddress: (state.location.locationTo.district || '') + state.location.locationTo.address, // 终点的详细地址,
        destCity: state.location.locationTo.city, // 终点城市,
        destLng: state.location.locationTo.location.lng, // 终点精度,
        destLat: state.location.locationTo.location.lat, // 终点纬度,

        planTrip: state.disAndDuraResult.distance / 1000, // 预估里程(公里)  (单位是米/m)  取时间 routeInfo.time (单位是秒/s),
        planFare: state.order.totalFee, // 预估价格

        levelType: state.carType.value, // 车型级别

        deparTime: api.Datefilter(deparTimeChanged),  // 预约时间
        orderType: state.order.orderType, // 订单类型
        areaUuid: state.originAreaUuid,
        remark: '',
        prepaidFee: iFneedPrePay ? Math.floor(state.sysAreaConfigDto.prepaidPercent / 100 * (state.estimateMsg.totalFee - state.estimateMsg.couponMoney)) : 0, // 预支付金额，默认为0
        preChoiceUserCouponUuid: state.payType.type === 2 ? state.coupon.id : '',
        vipCount: (state.order.orderType === 3 && state.topService) ? state.VIPnum : '', // 不选择头等舱服务时,返回空
        actualPassengeMobile: state.passenger.phone ? state.passenger.phone : state.userMsg.mobile,
        actualPassengeName: '',
        payToObject: state.passenger.payOrNot ? 1 : '',
        flightNum: state.airport.num,
        flightTime: state.airport.time ? api.Datefilter(state.airport.time) : ''
      }
      let ifTooFar = api.distanceByLnglat(dataParam.originLng, dataParam.originLat, dataParam.destLng, dataParam.destLat) / 1000
      let couponMoney = (state.payType.type === 2) ? state.estimateMsg.couponMoney : 0
      let costMoney = +state.estimateMsg.totalFee - (+couponMoney)
      let accountMoney = +state.payType.money
      if (ifTooFar > 800) {
        resolve({
          noSubmit: true,
          msg: '仅提供800公里以内的服务'
        })
      } else if (ifTooFar < 2) {
        resolve({
          noSubmit: true,
          msg: '仅提供大于2公里的服务'
        })
      } else if ((state.payType.type === 1) && accountMoney < costMoney) {
        resolve({
          noSubmit: true,
          msg: '企业账户余额不足，请选择个人账户支付方式'
        })
      } else {
        axios.post('/wechat/order/advance', dataParam).then((res) => {
          if (res.success) {
        // 请求成功操作
            api.setLocalStorage('currentOrderId', res.data)
            window.location.href = '/wechat/order/wreply/' + state.openId + '/' + res.data + '/' + localStorage.getItem('TIME_ZONE')
          }
          resolve(res)
        }).catch((err) => {
        // 请求失败操作
          reject(err)
          console.log(err)
        })
      }
    })
    return promise
  }
}
