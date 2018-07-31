import Stomp from 'stompjs'
import { uuid } from './util'
import { validatenull } from './validate'
import { RMQ_SERVER, RMQ_VIRTUAL_HOST, RMQ_ACCOUNT, RMQ_PASSWORD } from '@/const/global'
class StompClient {
  constructor (rmqServer, rmqVirtualHost, rmqAccount, rmqPassword) {
    this.client = null
    this.options = {
      vhost: rmqVirtualHost,
      queue: 'temp_queue',
      incoming: 15000,
      outgoing: 15000,
      account: rmqAccount,
      pwd: rmqPassword,
      server: `ws://${rmqServer}/ws`
    }
    this.queue = this.options.queue
    this.userData = {}
    this.subscribe = (routingKeys, callback) => {
      if (routingKeys && routingKeys.length > 0) {
        Object.assign(this.userData, { routingKeys, callback })
        routingKeys.forEach(element => {
          let key = ''
          let fn = () => {}
          if (typeof element === 'object') {
            key = element.key
            fn = element.callback
          } else {
            key = element
            fn = callback
          }
          this.client.subscribe(`/topic/${key}`, fn, {'x-queue-name': this.queue})
          console.log('stomp 消息队列绑定成功!')
        })
      }
      return this
    }
    this.reSubscribe = () => {
      console.log('stomp 正在重新绑定消息队列...')
      let {routingKeys, callback} = this.userData
      this.subscribe(routingKeys, callback)
    }
    this.setQueueUUID = (options) => {
      this.queue = options.queue + '_' + uuid(16)
    }
    this.checkClient = (client) => {
      if (!client) {
        throw new Error('stomp 没有连接到服务器!')
      }
    }
  }
  send (routingKey, msg) {
    this.checkClient(this.client)
    this.client.send(`/topic/${routingKey}`, {}, msg)
    return this
  }
  bind (routingKeys, callback) {
    this.checkClient(this.client)
    Object.assign(this.userData, { routingKeys, callback })
    return this.subscribe(routingKeys, callback)
  }
  connect (options) {
    Object.assign(this.options, options)
    this.setQueueUUID(this.options)
    return new Promise((resolve, reject) => {
      this.client = Stomp.over(new WebSocket(this.options.server))
      // heartbeat
      this.client.heartbeat.incoming = this.options.incoming
      this.client.heartbeat.outgoing = this.options.outgoing
      // debug
      this.client.debug = e => {
        // console.log(e)
      }
      // connected
      let onConnect = () => {
        console.log('stomp 连接成功！')
        !validatenull(this.userData) && this.reSubscribe()
        resolve()
      }
      // error
      let onError = () => {
        // alert('与消息服务器断开连接，请刷新页面重试。\n\r如果还是无连接，请联系管理员。')
        console.error('stomp 断开连接，正在准备重新连接...')
        setTimeout(() => {
          console.log('stomp 正在重新连接...')
          this.connect(this.options)
        }, 5000)
      }
      // do connect
      this.client.connect(this.options.account, this.options.pwd, onConnect, onError, this.options.vhost)
    })
  }
}
export default new StompClient(RMQ_SERVER, RMQ_VIRTUAL_HOST, RMQ_ACCOUNT, RMQ_PASSWORD)
