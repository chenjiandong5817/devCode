/*
 * @Author: cdroid
 * @Date: 2017-05-25 10:50:12
 * @Last Modified by:   cdroid
 * @Last Modified time: 2017-05-25 10:50:12
 * @Description:  加密等操作
 */
import util from './util'
import Crypto from 'crypto'

export default {
  sha1: function (str) {
    var result = ''
    var sha1 = Crypto.createHash('sha1')
    var bytes = util.str2Bytes(str)
    sha1.update(bytes)
    result = sha1.digest('hex')
    return result
  }
}
