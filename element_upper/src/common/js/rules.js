/*
* @Author: chenjiandong
* @Date:   last_modified_time
* @Last Modified by:   chenjiandong
* @Last Modified time: last_modified_time
*/
/*
 * @Author: cdroid
 * @Date: 2017-05-25 10:50:55
 * @Last Modified by: ylj
 * @Last Modified time: 2017-11-30 16:07:04
 * @Description:  表单校验规则
 */
export default {
  // 校验电话号码
  telephone: function (rule, value, callback) {
    var ret = /^1((3|5|8){1}\d{1}|70)\d{8}$/
    if (value && !ret.test(value)) {
      callback(new Error('请输入正确的电话号码'))
    } else {
      callback()
    }
  },
  time: function (rule, value, callback) {
    var regTime = /^(?:(?:[0-2][0-3])|(?:[0-1][0-9]))[0-5][0-9]$/
    if (value && !regTime.test(value)) {
      callback(new Error('请输入正确的时间格式[0000-2359]'))
    } else {
      callback()
    }
  }
}
