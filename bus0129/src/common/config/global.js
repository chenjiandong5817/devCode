/*
* @Author: cdroid
* @Date:   2018-01-29 14:26:22
* @Last Modified by:   chenjiandong
* @Last Modified time: 2018-04-03 14:47:30
*/
/**
 * 定义全局参数
 */

// sentry DSN
const SENTRY_DSN = 'http://056ddc0c61c64d0cab5fc31b878851fd@10.1.16.74:8080/2'

// stomp参数配置
const webUrl = 'ws://10.1.16.117:9090/ws'  // rabbitmq连接url
const rbUrl = '/exchange/fids.control/'   // exchange routing key等

// 模板编辑器预览服务器地址
export const templatePreviewUrl = 'http://localhost:8080/#/'
export default {
  SENTRY_DSN,
  webUrl,
  rbUrl,
  templatePreviewUrl
}
