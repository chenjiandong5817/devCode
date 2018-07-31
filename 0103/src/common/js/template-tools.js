import Moment from 'moment'
import Reflect from './reflect'
export default {
  // 遍历模板，搜索数据源id
  recurDataSource (conf) {
    let apis = []
    if (conf instanceof Array) {
      for (let i = 0; i < conf.length; i++) {
        let rs = this.recurDataSource(conf[i])
        if (rs && rs.length > 0) {
          apis = apis.concat(rs)
        }
      }
    } else if (conf instanceof Object) {
      for (var key in conf) {
        if (key === 'dataSource') {
          apis.push({key: conf[key], mainPager: conf['mainPager'], mainSource: conf['mainSource']})
        } else {
          let rs = this.recurDataSource(conf[key])
          if (rs && rs.length > 0) {
            apis = apis.concat(rs)
          }
        }
      }
    }
    return apis
  },
  // 根据类型，提取数据
  getMatchData (data, singleData, localData) {
    if (localData) {
      return data
    } else if (singleData) {
      if (data && data.list) {
        return data.list[0]
      } else if (data) {
        return data[0]
      } else {
        console.log('无列表数据')
        return {}
      }
    } else {
      return data ? data.list : []
    }
  },
  // 补全样式的后缀px， 目前只匹配height和width,margin,padding
  completeStyleSuffix (styleObj) {
    if (!(styleObj instanceof Object)) {
      return styleObj
    }
    const regex = /width|height|^padding|^margin|fontSize/gi
    for (let key in styleObj) {
      if (key.match(regex) && typeof styleObj[key] === 'number') {
        styleObj[key] = `${styleObj[key]}px`
      } else if (styleObj[key] instanceof Object) {
        styleObj[key] = this.completeStyleSuffix(styleObj[key])
      }
    }
    return styleObj
  },
  // 判断参数是否为对象，默认取key值，或者property值
  getObjectValue (data, propertyName) {
    if (data && typeof data === 'object') {
      return data[propertyName || 'key']
    } else {
      return data
    }
  },
  // 生成图片路径, 多字段拼接, 20171101修改成参数形式
  genImgPath (conf, data, airportCode) {
    if (!(conf.columnName && conf.imgType && conf.imgSize)) {
      return ''
    }
    // let path = `/${conf.imgType}/${conf.imgSize}/`
    let path = `?imagetype=${conf.imgType}&imagesize=${conf.imgSize}&airportcode=${airportCode}`
    if (conf.columnName.includes(',')) {
      let nameArray = []
      conf.columnName.split(',').forEach(name => {
        nameArray.push(this.getObjectValue(data[name], conf.property) || 'undefined')
      })
      path += `&imagename=${nameArray.join('_')}`
    } else {
      path += `&imagename=${this.getObjectValue(typeof data === 'object' ? data[conf.columnName] : data, conf.property)}`
    }
    return path
  },
  // multiSource
  handleMultiSource (conf, data) {
    let matchs = data[conf.columnName] ? [data[conf.columnName]] : []
    if (conf.multiSource) {
      conf.multiSource.forEach(source => {
        if (!source || !data[source.name]) { return }
        let sources = []
        if (source.handle && source.handle.split) {
          sources = data[source.name].split(source.handle.split)
        } else {
          sources.push(data[source.name])
        }
        sources.forEach(item => {
          /* eslint-disable no-eval */
          let regex = eval(source.handle && source.handle.regex ? source.handle.regex : /\S{0,}/)
          if (regex.test(item)) {
            let match = item.match(regex)
            if (match) {
              matchs.push(match[0])
            }
          }
        })
      })
    }
    return matchs
  },
  // 图片处理，图片来源多个字段, 用于航班列表 显示logo --- 即将作废
  handleMultiSourceText (conf, data, columnName) {
    let columnData = data[columnName]
    let matchs
    if (conf.multiSource) {
      if (conf.multiSource.type === 'Array') {
        matchs = [columnData]
      } else {
        matchs = columnData
      }
      conf.multiSource.sources.forEach(source => {
        if (!source || !data[source.name]) { return }
        let sources = []
        if (source.handle && source.handle.split) {
          sources = data[source.name].split(source.handle.split)
        } else {
          sources.push(data[source.name])
        }
        sources.forEach(item => {
          /* eslint-disable no-eval */
          let regex = eval(source.handle && source.handle.regex ? source.handle.regex : /\S{0,}/)
          if (regex.test(item)) {
            let match = item.match(regex)
            if (match) {
              if (conf.multiSource.type === 'Array') {
                matchs.push(match[0])
              } else {
                matchs += source.separator + match[0]
              }
            }
          }
        })
      })
    } else {
      return conf.defaultType === 'Array' ? [columnData] : columnData
    }
    return matchs
  },
  // 处理列表单元格显示的模式 -- 即将作废
  handleDisplayMode (conf, data, columnName) {
    if (!conf) { return }
    let displayMode
    if (conf.logo) { // 图片
      displayMode = 'logo'
    } else if (conf.marquee) { // 文本
      displayMode = 'marquee'
    }
    let re = this.handleMultiSourceText(conf[displayMode], data, columnName) || ''
    if (re && conf.date) {
      re = this.dateFormat(re, conf.date)
    }
    if (!re && conf.emptyReplace) {
      // 防止死循环
      let copyConf = Object.assign({}, conf)
      delete copyConf.emptyReplace
      for (let i = 0; i < conf.emptyReplace.columns.length; i++) {
        re = this.handleDisplayMode(copyConf, data, conf.emptyReplace.columns[i])
        if (re) {
          break
        }
      }
    }
    // 语言锁定和除外
    if (re && typeof re === 'object' && conf[displayMode].langLock) {
      re = re[conf[displayMode].langLock]
    }
    if (re && typeof re === 'object' && conf[displayMode].langExcept) {
      re = Object.assign({}, re)
      conf[displayMode].langExcept.forEach(lang => {
        delete re[lang]
      })
    }
    return re
  },
  dateFormat (text, params) {
    if (typeof text === 'string') {
      return Moment(text, params.formatFrom).format(params.formatTo)
    }
    return text
  },
  // 渲染文本
  renderText (conf, data) {
    let text = ''
    if (conf.value) {
      text = conf.value
    } else {
      text = data[conf.columnName]
    }
    // 特殊处理
    if (conf.special) {
      for (let i = 0; i < conf.special.length; i++) {
        text = Reflect[conf.special[i]](text)
      }
    }
    // 空值替换
    if (!text && conf.emptyReplace) {
      for (let i = 0; i < conf.emptyReplace.length; i++) {
        text = data[conf.emptyReplace[i]]
        if (text) {
          break
        }
      }
    }
    // 存在值替换
    if (text && conf.transExist) {
      text = conf.transExist
    }
    // object 类型限定使用某个属性的值，主要是多语言
    if (conf.property) {
      text = this.getObjectValue(text, conf.property)
    }
    // 时间类型格式化
    if (conf.date) {
      text = this.dateFormat(text, conf.date)
    }
    // 锁定显示语言
    if (text instanceof Object) {
      text = Object.assign({}, text)
      if (conf.langLock) { // 锁定的优先级更高
        text = text[conf.langLock]
      } else if (conf.langExcept) { // except的参数为 array
        conf.langExcept.forEach(lang => {
          delete text[lang]
        })
      }
    }
    return text
  },
  // 文本属性替换
  transTextProp (columnValue, propConf) {
    if (!propConf) {
      return
    }
    let property = propConf.property
    let matchType = propConf.matchType || ''
    let matchSet = propConf.matchSet || {}
    columnValue = this.getObjectValue(columnValue, property)
    if (!['begin', 'like', 'end', 'equals'].includes(matchType)) {
      matchType = 'begin'
    }
    let keys = Object.keys(matchSet)
    let matchKeys = keys.filter(key => {
      if (!columnValue) {
        return false
      } else if (matchType === 'begin') {
        return columnValue.startsWith(key)
      } else if (matchType === 'end') {
        return columnValue.endsWith(key)
      } else {
        return columnValue.includes(key)
      }
    })
    return matchKeys && matchKeys.length > 0 ? matchSet[matchKeys[0]] : ''
  },
  // 文本字符串替换
  replaceText (columnValue, replaceSet, data) {
    let text = columnValue
    replaceSet.forEach(item => {
      text = text.replace(`$\{${item.key}}`, (item.columnName ? data[item.columnName] : item.value) || '')
    })
    return text
  },
  // 添加合并文本
  addsMergeText (text, conf, data) {
    text = this.getObjectValue(text, null)
    let rs = []
    conf.forEach(item => {
      let array = item.matchArray || []
      if (array.length === 0 || this.indexOf(array, text) > -1) {
        rs.push(text && data[item.addsName] ? item.separator : '')
        rs.push(data[item.addsName] || '')
      }
    })
    return rs
  },
  // 给具有flopItemValue: true的配置设置flopItemName
  coverFlopValue (conf, name, value) {
    for (let key in conf) {
      if (key === 'columnName') {
        conf[name] = value
      } else if (conf[key] instanceof Array) {
        for (let i = 0; i < conf[key].length; i++) {
          this.coverFlopValue(conf[key][i], name, value)
        }
      } else if (conf[key] instanceof Object) {
        this.coverFlopValue(conf[key], name, value)
      }
    }
  },
  // 自定义数组indexof方法
  indexOf: function (arr, str) {
    // 如果可以的话，调用原生方法
    if (arr && arr.indexOf) {
      return arr.indexOf(str)
    }
    var len = arr.length
    for (var i = 0; i < len; i++) {
      // 定位该元素位置
      if (arr[i] === str) {
        return i
      }
    }
    // 数组中不存在该元素
    return -1
  },
  // 深拷贝
  deepCopy: function (o) {
    if (o instanceof Function) {
      return o
    } else if (o instanceof Array) {
      var array = []
      for (var i = 0; i < o.length; ++i) {
        array[i] = this.deepCopy(o[i])
      }
      return array
    } else if (o instanceof Object) {
      var obj = {}
      for (var j in o) {
        obj[j] = this.deepCopy(o[j])
      }
      return obj
    } else {
      return o
    }
  },
  // 判断对象未 null 或者 空属性
  emptyObject (o) {
    if (!o) {
      return true
    } else {
      return Object.keys(o).length === 0
    }
  },
  // 获取具有id的元素，否则向上冒泡, 参数为event.target
  searchIdTarget (target) {
    if (target.id) {
      return target
    } else {
      return this.searchIdTarget(target.parentElement)
    }
  }
}
