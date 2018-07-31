import api from './api'

export default {
  // 查询可开票额度
  set_Balance ({commit}, page) {
    let promise = new Promise((resolve, reject) => {
      api.get_balance().then((res) => {
        commit('SET_BALANCE', parseInt(res.data))
        if (res.data) {
          resolve(true)
        } else {
          resolve(false)
        }
      })
    })
    return promise
  },
  // 查询可开票额度
  set_Available ({commit}, page) {
    let promise = new Promise((resolve, reject) => {
      api.get_available(page).then((res) => {
        commit('SET_AVALIABLE', parseInt(res.data))
        if (res.data) {
          resolve(true)
        } else {
          resolve(false)
        }
      })
    })
    return promise
  },
  // 查询可开票行程列表
  set_MytripList ({commit}, page) {
    let promise = new Promise((resolve, reject) => {
      api.get_tripList(page).then((res) => {
        commit('SET_MYTRIPLIST', res.data)
        if (res.data.length) {
          resolve(true)
        } else {
          resolve(false)
        }
      })
    })
    return promise
  },
  // 查询开票历史记录
  set_invoiceHistory ({commit}, page) {
    let promise = new Promise((resolve, reject) => {
      api.get_invoiceHistory(page).then((res) => {
        commit('SET_INVOICEHISTORY', res.data || [])
        if (res.data) {
          resolve(true)
        } else {
          resolve(false)
        }
      })
    })
    return promise
  },
  // 优惠券列表存储
  set_MyCouponList ({commit}, page) {
    let promise = new Promise((resolve, reject) => {
      api.get_MyCouponList(page).then((res) => {
        let arr = []
        res.data.forEach(function (el, index) {
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
            'uuid': el.uuid
          }
          arr.push(item)
        })
        commit('SET_COUPONLIST', arr)
        if (res.data.length) {
          resolve(true)
        } else {
          resolve(false)
        }
      })
    })
    return promise
  },
  // 电子开票申请表单提交
  post_invoiceMsg ({commit}) {
    return api.post_invoice()
  },
  // 纸质发票开票申请表单提交
  post_paperinvoiceMsg ({commit}) {
    return api.post_paperinvoice()
  }
}
