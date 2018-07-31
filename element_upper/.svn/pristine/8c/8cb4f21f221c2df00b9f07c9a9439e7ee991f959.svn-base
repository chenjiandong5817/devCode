/*
 * @Author: chenyang
 * @Date: 2017-09-13 17:34:05
 * @Last Modified by: chenyang
 * @Last Modified time: 2017-09-14 10:38:59
 * 功能: 提供stomp(rabiitmq)相关的一些基础配置
 */

import Stomp from 'stompjs'
import Global from '../config/global'
class StompClient {
  constructor (client) {
    this.client = client
  }
  handleSend (routingKey, msg) {
    this.client.send(Global.rbUrl + routingKey, {}, msg)
  }
  connect () {
    this.client = Stomp.over(new WebSocket(Global.webUrl))
      // heartbeat
    this.client.heartbeat.incoming = Global.heartbeatIn
    this.client.heartbeat.outgoing = Global.heartbeatOut
      // debug
    this.client.debug = e => {
        console.log(e)
    }
    // connected
    let onConnect = () => {
      console.log('connected')
    }
    // error
    let onError = () => {
      console.log('error')
    }
    this.client.connect(Global.rbAccount, Global.rbPassword, onConnect, onError, Global.virtualHost)
  }
}

export default new StompClient(null)
