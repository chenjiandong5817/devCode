/**
 * 定义全局参数
 */

// sentry DSN
const SENTRY_DSN = 'http://056ddc0c61c64d0cab5fc31b878851fd@10.1.16.74:8080/2'

// stomp参数配置
const webUrl = 'ws://10.1.16.117:9090/ws'  // rabbitmq连接url
const rbUrl = '/exchange/fids.control/'   // exchange routing key等
const rbAccount = 'raiis.chenyang'
const rbPassword = 'raiis.chenyang'
const virtualHost = 'raiis.test'
const heartbeatIn = 30000
const heartbeatOut = 30000
// 1 行李转盘 RES_BAGGAGECAROUSEL  2 值机柜台  RES_CHECKINCOUNTER  3登机口 RES_GATE
var linkTypeKeyValue = {'行李转盘': 1, '值机柜台': 2, '登机口': 3}
// 图片管理(图片类型，图片尺寸，文件类型)
var imageTypeText = ['航空公司LOGO(用于列表和值机柜台)', '静态图片', '目的地图片', '非值机业务柜台']
var imageType = ['flight_logo', 'static', 'destination', 'NonCheckin']
var imageSizeText = ['220x220', '700x400', '1024x768']
var imageSize = ['220x220', '700x400', '1024x768', '1000x1000', '2000x2000', '675x220']
var fileTypeText = ['.jpg', '.png', '.gif', '.bmp']
var fileType = ['.jpg', '.png', '.gif', '.bmp']
// 图片请求的Url前半部分
var urlPart = '/raiis/image/show?'
var imgName = 'imagename='
var imgType = 'imagetype='
var flType = 'filetype='
var imgSize = 'imagesize='
var queryPara = 'id='
export default {
  SENTRY_DSN,
  webUrl,
  rbUrl,
  rbAccount,
  rbPassword,
  virtualHost,
  heartbeatIn,
  heartbeatOut,
  linkTypeKeyValue,
  imageType,
  imageSize,
  fileType,
  urlPart,
  imgName,
  imgType,
  flType,
  imgSize,
  imageTypeText,
  imageSizeText,
  fileTypeText,
  queryPara
}
