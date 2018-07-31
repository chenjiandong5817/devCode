/*
 * @Author: cdroid
 * @Date: 2017-05-25 10:56:38
 * @Last Modified by: cdroid
 * @Last Modified time: 2017-12-29 18:52:15
 * @Description:  vuex getter操作类
 */
// test
export const isLogin = (state) => {
  return (state.userStorage.user !== null && state.userStorage.token !== '')
}
export const getUserStorage = (state) => {
  return state.userStorage
}
