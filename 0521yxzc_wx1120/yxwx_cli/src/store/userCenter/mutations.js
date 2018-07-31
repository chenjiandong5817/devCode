import api from './api'
// mutations
const SET_BALANCE = 'SET_BALANCE'
const SET_AVALIABLE = 'SET_AVALIABLE'
const SET_MYTRIPLIST = 'SET_MYTRIPLIST'
const SET_PICKTRIPLIST = 'SET_PICKTRIPLIST'
const SET_COUNTMONEY = 'SET_COUNTMONEY'
const SET_COUPONLIST = 'SET_COUPONLIST'
const SET_ACCOUNTTYPE = 'SET_ACCOUNTTYPE'
const SET_BUYERNAME = 'SET_BUYERNAME'
const SET_ORDERTOTAL = 'SET_ORDERTOTAL'
const SET_EMAIL = 'SET_EMAIL'
const SET_ACCOUNT = 'SET_ACCOUNT'
const SET_ADDRESS = 'SET_ADDRESS'
const SET_TELEPHONE = 'SET_TELEPHONE'
const SET_MESSAGE = 'SET_MESSAGE'
const SET_ORDERUUIDS = 'SET_ORDERUUIDS'
const SET_TICKETTYPE = 'SET_TICKETTYPE'
const SET_TAXNUM = 'SET_TAXNUM'
const SET_INVOICEHISTORY = 'SET_INVOICEHISTORY'
const SET_INVOICETYPE = 'SET_INVOICETYPE'
const SET_POSTNAME = 'SET_POSTNAME'
const SET_POSTMOBILE = 'SET_POSTMOBILE'
const SET_POSTAREA = 'SET_POSTAREA'
const SET_POSTADDRESS = 'SET_POSTADDRESS'
const RESETALL = 'RESETALL'
export default{
  [SET_BALANCE] (state, val) {
    state.balance = val
  },
  [SET_AVALIABLE] (state, val) {
    state.available = val
  },
  [SET_MYTRIPLIST] (state, val) {
   // ES6合并数组
    state.MytripList = api.removeDup([...state.MytripList, ...val])
  },
  [SET_PICKTRIPLIST] (state, val) {
    state.PicktripList = val
  },
  [SET_COUNTMONEY] (state, val) {
    state.countMoney = val
  },
  [SET_COUPONLIST] (state, val) {
    // state.MycouponList = val
   // ES6合并数组
    state.MycouponList = api.removeDup([...state.MycouponList, ...val])
  },
  [SET_ACCOUNTTYPE] (state, val) {
    state.accountType = val
  },
  [SET_BUYERNAME] (state, val) {
    state.buyername = val
  },
  [SET_ORDERTOTAL] (state, val) {
    state.ordertotal = val
  },
  [SET_EMAIL] (state, val) {
    state.email = val
  },
  [SET_ACCOUNT] (state, val) {
    state.account = val
  },
  [SET_ADDRESS] (state, val) {
    state.address = val
  },
  [SET_TELEPHONE] (state, val) {
    state.telephone = val
  },
  [SET_MESSAGE] (state, val) {
    state.message = val
  },
  [SET_ORDERUUIDS] (state, val) {
    state.orderUuids = val
  },
  [SET_TICKETTYPE] (state, val) {
    state.ticketType = val
  },
  [SET_TAXNUM] (state, val) {
    state.taxnum = val
  },
  [SET_INVOICEHISTORY] (state, val) {
   // ES6合并数组
    state.invoiceHistory = api.removeDup([...state.invoiceHistory, ...val], 'invoiceUuid')
  },
  [SET_INVOICETYPE] (state, val) {
    state.invoiceType = val
  },
  [SET_POSTNAME] (state, val) {
    state.post_name = val
  },
  [SET_POSTMOBILE] (state, val) {
    state.post_mobile = val
  },
  [SET_POSTAREA] (state, val) {
    state.post_area = val
  },
  [SET_POSTADDRESS] (state, val) {
    state.post_address = val
  },
  // 重置信息
  [RESETALL] (state) {
    state.MytripList = []
    state.countMoney = 0
    state.PicktripList = []
    state.invoiceHistory = []
    state.invoiceType = 1
    state.buyername = ''
    state.taxnum = ''
    state.email = ''
    state.accountType = 1
    state.account = ''
    state.address = ''
    state.telephone = ''
    state.message = ''
    state.orderUuids = ''
    state.post_name = ''
    state.post_mobile = ''
    state.post_area = ''
    state.post_address = ''
  }
}
