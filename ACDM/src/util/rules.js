import { validateURL } from './validate'
// 校验资源路径
export const validResouceUri = (rule, value, callback) => {
  // 匹配中文
  let pattern = /([\u4E00-\u9FA5]|[\uFE30-\uFFA0])+/g
  // 匹配合法字符
  let path = /^\/([\w{}][/]?)*$/
  if (pattern.test(value)) {
    callback(new Error('不能使用中文符号!'))
  } if (!validateURL(value) && !path.test(value)) {
    callback(new Error('非法路径!'))
  } if (/([{}])/.test(value) && !/\{\w+\}/.test(value)) {
    callback(new Error('占位符错误!'))
  } else {
    callback()
  }
}
// 校验数字输入框值是否为空
export const validNumberInput = (rule, value, callback) => {
  if (!value) {
    callback(new Error('请输入数量'))
  } else {
    callback()
  }
}
// 校验合法名称，字母数字和下划线
export const validLegalName = (rule, value, callback) => {
  let pattern = /^[a-zA-Z0-9_]+$/
  if (!pattern.test(value)) {
    callback(new Error('只能输入字母、数字和下划线'))
  } else {
    callback()
  }
}

// 校验中文
export const validChinese = (rule, value, callback) => {
  let pattern = /^[\u4E00-\u9FA5]|[\uFE30-\uFFA0]$/g
  if (!pattern.test(value)) {
    callback(new Error('请输入中文字符'))
  } else {
    callback()
  }
}

// 校验大写字母
export const validUpperCase = (rule, value, callback) => {
  let pattern = /^[A-Z]+$/
  if (!pattern.test(value)) {
    callback(new Error('请输入大写字母'))
  } else {
    callback()
  }
}

// 校验数字
export const validNumber = (rule, value, callback) => {
  let pattern = /^[0-9]+$/
  if (!pattern.test(value)) {
    callback(new Error('请输入数字'))
  } else {
    callback()
  }
}

// 手机号验证
export const validPhone = (rule, value, callback) => {
  let pattern = /^[1][3,4,5,7,8][0-9]{9}$/
  if (!pattern.test(value)) {
    callback(new Error('请输入正确手机号'))
  } else {
    callback()
  }
}
