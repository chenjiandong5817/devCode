import { GET, POST, PUT } from '@/router/axios'
import { DIC } from '@/const/dic'

// 获取当前用户信息
export const getUserInfo = () => {
  return GET('/acdm/user/me')
}

// 获取系统角色
export const getRoleAll = () => {
  return GET('/acdm/role/all')
}

// 获取菜单
export const getMenu = () => {
  return GET('/acdm/menu/root')
}

// 获取字典
export const getDic = (type) => {
  return new Promise((resolve, reject) => {
    resolve({ data: DIC[type] })
  })
}

// 新增一条消息, 用于自动生成
export const addMessageInfo = (params) => {
  return POST('/acdm/messageInfo', params)
}

// 获取未读消息
export const getUnreadMsg = (params) => {
  return GET('/acdm/messageInfo/unread', params)
}

// 获取已读消息
export const getReadedMsg = (params) => {
  return GET('/acdm/messageLog/alreadyRead', params)
}

// 接收消息
export const receiveMsg = (params) => {
  return POST('/acdm/messageLog/receive', params)
}

// 确认消息
export const confirmMsg = (params) => {
  return PUT('/acdm/messageLog', params)
}
