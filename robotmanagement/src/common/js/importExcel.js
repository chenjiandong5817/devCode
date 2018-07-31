/*
 * @Author: ylj
 * @Date: 2017-11-09 22:32:20
 * @Last Modified by: ylj
 * @Last Modified time: 2017-11-30 20:15:13
 */
import XLSX from 'xlsx'
// 是否将文件读取为二进制字符串
var rABS = true
export default {
  importFiles: function (obj, titleMap, titleNum) {
    // 导入
    var begin = new Date()
    var $impthis = this
    var result = { ok: true, msg: '', resultJson: '', time: '', data: '' }
    var file = null
    var reader = new FileReader()
    var resultJson = []
    if (obj === undefined) {
      return result
    }
    file = obj.raw
    return new Promise(function (resolve, reject) {
      if (rABS) {
        reader.readAsBinaryString(file)
      } else {
        reader.readAsArrayBuffer(file)
      }
      reader.onload = function (e) {
        try {
          var data = e.target.result
          var wb = null
          if (rABS) {
            wb = XLSX.read(data, { type: 'binary' }) // 以二进制流方式读取得到整份excel表格对象
          } else {
            var arr = $impthis.fixData(data)
            wb = XLSX.read(btoa(arr), { type: 'base64' })
          }
          // 遍历读取每个Sheet
          for (var sheet in wb.Sheets) {
            if (wb.Sheets.hasOwnProperty(sheet)) {
              resultJson = resultJson.concat(XLSX.utils.sheet_to_json(wb.Sheets[sheet]))
              // resultJson = resultJson.concat($impthis.parseExcel(wb.Sheets[sheet], resultJson.length))
            }
          }
          resultJson = $impthis.tranfKey(titleMap, titleNum, resultJson)
          var end = new Date()
          var time = end - begin
          result = { ok: true, msg: '成功解析文件！', resultJson: resultJson, time: '解析Excel文件耗时(ms)：' + time, data: data }
          resolve(result)
        } catch (e) {
          result = { ok: false, msg: '文件类型错误,仅支持上传XLSX/XLS文件！请重选！', resultJson: resultJson, time: '', data: '' }
          resolve(result)
        }
      }
    })
  },
  fixData: function (data) {
    let o = ''
    let l = 0
    let w = 10240
    for (; l < data.byteLength / w; ++l) {
      o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w, l * w + w)))
      o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w)))
      return o
    }
  },
  parseExcel: function (wbSheet, rowIndex) {
    var headers = {}
    var data = []
    var keys = Object.keys(wbSheet)
    var step = rowIndex - 2
    // 查找有效单元格并遍历，过滤以 ! 开头的 key
    keys.filter(k => k[0] !== '!')
      .forEach(k => {
        let col = k.substring(0, 1) // 获取列号，如A11 => A
        let row = parseInt(k.substring(1)) // 获取行号，如A11 => 11
        let value = wbSheet[k].v // 当前单元格值
        // 保存字段名
        if (row === 1) {
          headers[col] = value
          return
        }
        // 解析为JSON数据格式
        if (!data[row + step]) {
          data[row + step] = {}
        }
        data[row + step][headers[col]] = value
      })
    return data
  },
  tranfKey: function (titleMap, titleNum, resultJson) {
    let temp = JSON.stringify(resultJson)
    var index = 0
    for (let key in titleMap) {
      let t = temp
      let subStr = '"' + key + '"'
      let reg = new RegExp(subStr, 'g')
      let val = '"' + titleMap[key] + '"'
      index = index + 1
      if (index !== titleNum) {
        temp = t.replace(reg, val)
      } else {
        return JSON.parse(t.replace(reg, val))
      }
    }
  }
}
