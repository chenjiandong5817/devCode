function matchNumber(str) {
  if (!str) {
    return
  }
  return (str.match(/[0-9]*/g) || []).join()
}
function matchStr(str) {
  if (!str) {
    return
  }
  return (str.match(/[a-zA-Z]{1}/g) || []).join()
}
export default {
  induceCounter: function (val) {
    if (!val) {
      return
    }
    var result = []
    var tmpArray = val.split(',')
    if (tmpArray) {
      tmpArray.sort()
      var begin
      var end
      var isCon = false
      for (var i = 0; i <= tmpArray.length - 1; i++) {
        var current = matchStr(tmpArray[i]) + (Number(matchNumber(tmpArray[i])) + 1)
        var next = matchStr(tmpArray[i + 1]) + Number(matchNumber(tmpArray[i + 1]))
        if (current === next) {
          if (!isCon) {
            begin = tmpArray[i]
          }
          isCon = true
        } else {
          end = tmpArray[i]
          isCon = false
          if (begin && end) {
            result.push(begin + '-' + end)
          } else if (begin) {
            result.push(begin)
          } else {
            result.push(end)
          }
          begin = null
          end = null
        }
      }
    }
    return result.join(',')
  },
  color: function (args, val) {
    console.log(val)
    return null
  }
}
