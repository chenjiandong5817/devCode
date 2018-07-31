import Mock from 'mockjs'

function genPager (params) {
  let filter = {}
  let pager = {
    pageNumber: 0,
    pageSize: 20,
    recordCount: 0
  }
  for (let key in params) {
    if (Object.keys(pager).includes(key)) {
      pager[key] = Number(params[key])
    } else {
      filter[key] = params[key]
    }
  }
  return { pager, filter }
}

function response (data, pager, ok = true, msg = '成功') {
  return {
    ok,
    msg,
    attr: {
      data: {
        list: data,
        pager
      }
    }
  }
}

function checkData (record, filter) {
  for (let key in filter) {
    if (!filter[key]) {
      continue
    }
    if (record[key] !== filter[key]) {
      return false
    }
  }
  return true
}

export default {
  uuid () {
    return Mock.Random.guid().replace(/-/g, '')
  },
  splitParams (url) {
    let params = {}
    if (!url.includes('?')) {
      return params
    }
    let temps = url.substring(url.indexOf('?') + 1, url.length).split('&')
    temps.forEach(item => {
      params[[item.substring(0, item.indexOf('='))]] = item.substring(item.indexOf('=') + 1, item.length)
    })
    return params
  },
  generateData (count, method) {
    let re = []
    for (let i = 0; i < count; i++) {
      re.push(method(i))
    }
    return re
  },
  paging (data, pager) {
    if (pager.pageSize === 0) {
      return {list: data, pager}
    }
    if (pager.pageNumber < 1) {
      pager.pageNumber = 1
    }
    const start = ((pager.pageNumber > 0 ? pager.pageNumber : 1) - 1) * pager.pageSize
    const end = pager.pageSize > data.length ? data.length : pager.pageSize
    let list = data.slice(start, start + end)
    return {
      list, pager
    }
  },
  query (mockData, url) {
    let params = this.splitParams(url)
    params = genPager(params)
    let results = []
    mockData.forEach(item => {
      if (checkData(item, params.filter)) {
        results.push(item)
      }
    })
    params.pager.recordCount = results.length
    let {list, pager} = this.paging(results, params.pager)
    console.log(pager)
    return response(list, pager)
  }
}
