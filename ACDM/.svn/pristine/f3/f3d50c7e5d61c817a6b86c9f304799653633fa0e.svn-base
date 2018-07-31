import store from '@/store/'
import { GET, POST, PUT, DELETE } from '@/router/axios'
import { addMessageInfo } from '@/api/admin'
import { fillStringPlaceholder } from '@/util/util'
import { validatenull } from '@/util/validate'
import { Message } from 'element-ui'

const joinMessageTypeCode = (messageType, messageTypeAll) => {
  let code = ''
  for (let item of messageTypeAll) {
    if (item.children && item.children.length > 0) {
      let subStr = joinMessageTypeCode(messageType, item.children)
      if (subStr) {
        code = item.code + '.' + subStr
        break
      }
    } else if (item.id === messageType.id) {
      code = messageType.code
      break
    }
  }
  return code
}

export default {
  install (Vue) {
    let matchControl = (code) => {
      let controls = store.state.user.controls || []
      for (let control of controls) {
        if (control.code === code) {
          return control
        }
      }
      return null
    }

    let matchMethod = (control) => {
      let method = null
      switch (control.method) {
        case 'GET':
          method = GET
          break
        case 'POST':
          method = POST
          break
        case 'PUT':
          method = PUT
          break
        case 'DELETE':
          method = DELETE
          break
        default:
          method = null
      }
      return (params, config) => method(control.uri, params, config).then(res => {
        // 判断控件是否绑定了消息发送
        if (control.meta && control.meta.messageTypes && control.meta.messageTypes.length > 0) {
          let messageTypeAll = store.getters.messageTypeAll
          if (!messageTypeAll || messageTypeAll.length === 0) {
            console.error('本地MessageType缓存为空, 无法生成routingKey...')
            return Promise.resolve(res)
          }
          let obj = control.meta.messageTypes.map(item => {
            return {
              routingKey: joinMessageTypeCode(item.messageType, messageTypeAll),
              content: item.content,
              ack: item.ack
            }
          })
          return store.dispatch('ShiftMessageParams').then(data => {
            // 判断是否需要从返回数据中取值
            if (data.$dynamicParams && res.data && (typeof res.data === 'object')) {
              let dynamicData = res.data
              data.$dynamicParams.forEach(param => {
                data[param] = dynamicData[param]
              })
            }
            // 动态routingKey
            if (data.$dynamicRoutingKey && !validatenull(data.$dynamicRoutingKey)) {
              obj = obj.filter(item => !validatenull(data.$dynamicRoutingKey) && data.$dynamicRoutingKey.includes(item.routingKey))
            }
            return Promise.resolve(data)
          }).then(messageParams => {
            console.log('messageParams', messageParams)
            let ajaxs = obj.map(item => addMessageInfo({
              carrier: messageParams.carrier || '',
              flight: messageParams.flight || '',
              typeCode: item.routingKey,
              content: fillStringPlaceholder(item.content, messageParams),
              whetherConfirm: item.ack
            }))
            return new Promise(resolve => {
              Promise.all(ajaxs).then(() => {
                resolve(res)
              })
            })
          })
        } else {
          return Promise.resolve(res)
        }
      })
    }

    let auth = (el, value, vnode, isUpdate) => {
      let code = ''
      // 取消handler 加入具体事件名称，便于扩展
      // let handler = null
      let click = null
      let native = false
      if (typeof value === 'object') {
        code = value.code
        if (value.click) {
          click = value.click
        }
        if (value.native !== undefined) {
          native = Boolean(value.native)
        }
      } else {
        code = value
      }

      let control = getControl(code)

      if (!control && el.parentNode) {
        el.parentNode.removeChild(el)
      }
      // 控件权限都可以用来判断是否显示，button控件多了一个handler的功能
      // && control.type === 'BUTTON' // 暂时取消button 和 resource 类型的差别
      if (!el.getAttribute('pta') && control && click) {
        el.setAttribute('pta', 'click')
        let method = matchMethod(control)
        // 如果为字符串，即从context中找出名字为该字符串的方法
        if (typeof click === 'string') {
          let message = '找不到方法 [' + click + ']...'
          click = vnode.context[click]
          if (!click) {
            // throw message
            console.error(message)
            return
          }
        }
        // 绑定事件，尽量不要用原生js事件，不然可能会有不可预估的错误。。。
        if (!native && vnode.componentInstance) {
          // vnode.componentInstance.$off('click')
          vnode.componentInstance.$on('click', () => {
            console.log('auth btn click')
            click.apply(null, method ? [method] : [])
          })
        } else if (native) {
          el.addEventListener('click', () => {
            console.log('native - auth btn click')
            click.apply(null, method ? [method] : [])
          })
        }
      }
    }

    let getControl = (code, notify) => {
      if (!code) {
        return null
      }
      let control = matchControl(code)
      if (!control && notify) {
        let message = `[${code}]控件权限未找到...`
        Message({
          type: 'error',
          showClose: true,
          message,
          duration: 0
        })
        // throw message
        return null
      }
      return control
    }

    Vue.directive('auth', {
      // bind (el, binding, vnode) {
      //   auth(el, binding.value, vnode)
      // },
      inserted (el, binding, vnode) {
        auth(el, binding.value, vnode)
      },
      update (el, binding, vnode) {
        if (binding.value !== binding.oldValue || binding.value.code === binding.oldValue.code) {
          auth(el, binding.value, vnode, true)
        }
      }
    })
    Vue.prototype.$auth = (code, notify = true) => {
      let control = getControl(code, notify)
      if (!control) {
        return null
      }
      let method = matchMethod(control)
      return method
    }
  }
}
