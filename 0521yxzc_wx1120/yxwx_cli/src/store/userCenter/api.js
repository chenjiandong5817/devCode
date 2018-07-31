import state from './state'
import axios from '../ajax'
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
  // 根据uuid对象数组集去重方法
  removeDup (array, idname) {
    let arr = {}
    arr = array.reduce(function (item, next) {
      if (!arr[next.uuid]) {
        arr[next.uuid] = true
        item.push(next)
      }
      return item
    }, [])
    // 指定去重标志名
    if (idname) {
      arr = array.reduce(function (item, next) {
        if (!arr[next[idname]]) {
          arr[next[idname]] = true
          item.push(next)
        }
        return item
      }, [])
    }
    return arr
  },
  // 账户余额
  get_balance (page) {
    let promise = new Promise((resolve, reject) => {
      axios.post('/wechat/passenger/getBalance', {
        openId: this.getLocalStorage('openId')
      }).then((data) => {
        resolve(data)
      }).catch((err) => {
        reject(err)
      })
    })
    return promise
  },
  // 可开票额度
  get_available (page) {
    let promise = new Promise((resolve, reject) => {
      axios.get('/wechat/userInvoice/available', {
        pageNum: page,
        openId: this.getLocalStorage('openId')
      }).then((data) => {
        resolve(data)
      }).catch((err) => {
        reject(err)
      })
    })
    return promise
  },
  // 可开票行程
  get_tripList (page) {
    let promise = new Promise((resolve, reject) => {
      axios.get('/wechat/userInvoice/unBillingTrip', {
        pageNum: page,
        openId: this.getLocalStorage('openId')
      }).then((data) => {
        resolve(data)
      }).catch((err) => {
        reject(err)
      })
    })
    return promise
  },
  // 我的优惠券列表
  get_MyCouponList (page) {
    let promise = new Promise((resolve, reject) => {
      axios.get('/wechat/coupon/listMyCouponMap', {
        status: 1,
        pageNum: page,
        openId: this.getLocalStorage('openId')
        // openId: 'oukrA1dbbITe9sJSdTNW6JrARHL4'
      }).then((data) => {
        resolve(data)
      }).catch((err) => {
        reject(err)
      })
    })
    return promise
  },
  // 获取开票历史
  get_invoiceHistory (page) {
    let promise = new Promise((resolve, reject) => {
      axios.post('/wechat/userInvoice/invoice/history', {
      // axios.post('/wechat/userInvoice/electron/history', {
        pageNum: page,
        openId: this.getLocalStorage('openId')
      }).then((data) => {
        resolve(data)
      }).catch((err) => {
        reject(err)
      })
    })
    return promise
  },
  // 电子发票开票申请
  post_invoice () {
    let promise = new Promise((resolve, reject) => {
      axios.post('/wechat/userInvoice/electron/generate', {
        openId: this.getLocalStorage('openId'), // 登录令牌
        buyername: state.buyername, // 发票抬头
        taxnum: state.taxnum, // 税号
        ordertotal: state.countMoney, // 发票金额
        email: state.email, // 电子邮箱
        accountType: state.accountType, // 抬头类型
        account: state.account, // 购方银行账号
        address: state.address, // 购方地址
        telephone: state.telephone, // 购方电话
        message: state.message, // 备注
        orderUuids: state.PicktripList.toString(), // 行程列表
        ticketType: state.ticketType // 开票类型
      }).then((data) => {
        resolve(data)
      }).catch((err) => {
        reject(err)
      })
    })
    return promise
  },
  // 纸质发票开票申请
  post_paperinvoice () {
    let promise = new Promise((resolve, reject) => {
      axios.post('/wechat/userInvoice/paper/generate', {
        openId: this.getLocalStorage('openId'),
        header: state.buyername, // 抬头
        taxNumber: state.taxnum, // 税号
        money: state.countMoney, // 金额
        content: '客运服务费',
        headerType: state.accountType, // 抬头类型
        bankAccount: state.account, // 开户行及账户
        regAddress: state.address, // 注册地址
        regMobile: state.telephone, // 注册手机
        remark: state.message, // 备注
        orderUuids: state.PicktripList.toString(),
        type: state.ticketType, // 发票类型
// 纸质发票附件信息
        recipient: state.post_name, // 收件人
        mobile: state.post_mobile, // 收件人电话
        area: state.post_area, // 收件区域
        detailAddress: state.post_address, // 收件地址
        freightType: (state.countMoney >= 300) ? 2 : 1 // 运费支付方式
      }).then((data) => {
        resolve(data)
      }).catch((err) => {
        reject(err)
      })
    })
    return promise
  }
}
