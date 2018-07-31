// mutations
const SWITCH_LOGINMASK = 'SWITCH_LOGINMASK'
const SET_CURRENTCITY = 'SET_CURRENTCITY'
const SET_GEOLOCATION = 'SET_GEOLOCATION'
const SET_SYSAREACONFIGDTO = 'SET_SYSAREACONFIGDTO'
const SET_OPENID = 'SET_OPENID'
const SET_USERMSG = 'SET_USERMSG'
const SET_ORDER = 'SET_ORDER'
const SWITCH_LOCATION = 'SWITCH_LOCATION'
const SELECT_CITY = 'SELECT_CITY'
const RESET_LOCATION = 'RESET_LOCATION'
const RESET_ALL_LOCATION = 'RESET_ALL_LOCATION'
const SWITCH_USERBAR = 'SWITCH_USERBAR'
const SET_AIRPORT = 'SET_AIRPORT'
const SET_CARTYPE = 'SET_CARTYPE'
const SET_PAYTYPE = 'SET_PAYTYPE'
const SET_PAYTYPELIST = 'SET_PAYTYPELIST'
const SET_PASSENGER = 'SET_PASSENGER'
const SET_COUPON = 'SET_COUPON'
const SET_COUPONLIST = 'SET_COUPONLIST'
const SET_TOPSERVICE = 'SET_TOPSERVICE'

const SET_STATIONDATA = 'SET_STATIONDATA'
const SET_CARMODELSDATA = 'SET_CARMODELSDATA'
const SET_DISANDDURA = 'SET_DISANDDURA'
const SET_ESTIMATEMSG = 'SET_ESTIMATEMSG'
const SET_ORIGINAREAUUID = 'SET_ORIGINAREAUUID'

export default{
  // 设置当前入口城市
  [SET_CURRENTCITY] (state, val) {
    state.currentCity = val
    state.location.selectCity = val.city
  },
  // 设置当前城市站点数据
  [SET_GEOLOCATION] (state, val) {
    state.Geolocation = val
  },
  // 设置当前城市站点数据
  [SET_SYSAREACONFIGDTO] (state, val) {
    state.sysAreaConfigDto = val
  },
  // 设置当前城市站点数据
  [SET_STATIONDATA] (state, val) {
    state.staticData.loca_data = val
  },
  // 设置车型列表数据
  [SET_CARMODELSDATA] (state, val) {
    state.carTypeList = val
    // 同时设置默认值
    state.carType = val[0]
  },
  // 设置路线信息
  [SET_DISANDDURA] (state, val) {
    state.disAndDuraResult = val
  },
  // 设置预估信息
  [SET_ESTIMATEMSG] (state, val) {
    state.estimateMsg = val
  },
  // openId
  [SET_OPENID] (state, val) {
    state.openId = val
  },
  // 用户信息
  [SET_USERMSG] (state, val) {
    state.userMsg = val
  },
  // 用户登录状态
  [SWITCH_LOGINMASK] (state) {
    state.loginMask = !state.loginMask
  },
  // 乘客信息
  [SET_PASSENGER] (state, val) {
    state.passenger = val
  },
  // 订单信息修改
  [SET_ORDER] (state, obj) {
    state.order[obj.name] = obj.val
  },
  // 用户侧边栏状态
  [SWITCH_USERBAR] (state) {
    state.showUserBar = !state.showUserBar
  },
  // location信息修改
  [SWITCH_LOCATION] (state, obj) {
    state.location[obj.name] = obj.val
    if (obj.name === 'locationFrom' || obj.name === 'locationTo') {
      state[obj.name] = obj.val
    }
  },
  // 城市选中状态
  [SELECT_CITY] (state, val) {
    state.location.selectCity = val.name
    state.location.cityFocus = false
  },
  // 清除当前location组件状态位
  [RESET_LOCATION] (state) {
    state.location.cityFocus = false
    state.location.addressFocus = false
    state.location.cityInputing = false
    state.location.addressInputing = false
    state.location.cityKeyWord = ''
    state.location.addressKeyWord = ''
  },
  // 重置location信息
  [RESET_ALL_LOCATION] (state) {
    state.locationFrom = ''
    state.locationTo = ''
    state.location.startOrEnd = true
    state.location.locationFrom = ''
    state.location.locationTo = ''
    state.location.cityFocus = false
    state.location.cityInputing = false
    state.location.cityKeyWord = ''
    state.location.addressFocus = false
    state.location.addressInputing = false
    state.location.addressKeyWord = ''
    state.location.tips = []
  },
  // 设置航班信息
  [SET_AIRPORT] (state, obj) {
    state.airport[obj.name] = obj.val
  },
  // 设置车型信息
  [SET_CARTYPE] (state, val) {
    state.carType = val
  },
  // 设置支付类型
  [SET_PAYTYPE] (state, val) {
    state.payType = val
  },
  // 设置支付列表
  [SET_PAYTYPELIST] (state, val) {
    state.payTypeList = val
    // state.payType = val[0]
  },
  // 设置优惠券
  [SET_COUPON] (state, obj) {
    state.coupon = obj
  },
  // 设置优惠券
  [SET_COUPONLIST] (state, obj) {
    state.couponList = obj
  },
  // 设置头等舱服务
  [SET_TOPSERVICE] (state, obj) {
    if (obj.name === 'checked') {
      state.topService = obj.val
    }
    if (obj.name === 'num') {
      state.VIPnum = obj.val
    }
  },
  // 设置起点区域id
  [SET_ORIGINAREAUUID] (state, val) {
    state.originAreaUuid = val
  }
}
