/*
* @Author: chenjiandong
* @Date:   last_modified_time
* @Last Modified by:   chenjiandong
* @Last Modified time: last_modified_time
*/
/*
 * @Author: chenyang
 * @Date: 2017-09-14 09:18:53
 * @Last Modified by: chenyang
 * @Last Modified time: 2017-09-14 10:51:21
 * 功能: 存放约定好的json格式,开关屏等操作的JSON
 */
function getJson (type, state, timestamp, data) {
  return {type, state, timestamp, data}
}
let curTime = Date.parse(new Date())
let clearCache = JSON.stringify(getJson('clearCache', '清理缓存', curTime, null))
let syncTime = JSON.stringify(getJson('syncTime', '同步时间', curTime, {currentTimestamp: curTime}))
let refresh = JSON.stringify(getJson('refresh', '刷新界面', curTime, null))   // 刷新WebView
let upgrade = JSON.stringify(getJson('upgrade', '应用升级', curTime, null))
let updatePlayList = JSON.stringify(getJson('updatePlayList', '更新播放列表', curTime, {playListUrl: 'http://xxxx.xxxx.xxxx'}))
let shutdown = JSON.stringify(getJson('shutdown', '关机', curTime, null))
let reboot = JSON.stringify(getJson('reboot', '重启', curTime, null))
let checkDevice = JSON.stringify(getJson('checkDevice', '检查设备状态', curTime, null))
let downloadMedia = JSON.stringify(getJson('downloadMedia', '下载多媒体信息', curTime, {mediaUrl: 'http://xxx.xxx.xxx'}))
let screenOn = JSON.stringify(getJson('screenOn', '亮屏', curTime, null))
let screenOff = JSON.stringify(getJson('screenOff', '灭屏', curTime, null))
let uploadErrorLogs = JSON.stringify(getJson('uploadErrorLogs', '上传错误日志', curTime, null))

export default {
  clearCache,
  syncTime,
  refresh,
  upgrade,
  updatePlayList,
  shutdown,
  reboot,
  checkDevice,
  downloadMedia,
  screenOn,
  screenOff,
  uploadErrorLogs
}
