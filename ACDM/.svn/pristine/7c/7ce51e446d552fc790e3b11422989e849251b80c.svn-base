import Mock from 'mockjs'
let userList = []
for (let i = 0; i < 2; i++) {
  userList.push(Mock.mock({
    id: '@increment',
    name: Mock.mock('@cname'),
    username: Mock.mock('@last'),
    grade: [0, 1],
    state: 0,
    date: new Date().getTime()
  }))
}
export const userTableData = {
  total: 11,
  pageSize: 10,
  tableData: userList
}

let roleList = []
for (let i = 0; i < 2; i++) {
  roleList.push(Mock.mock({
    id: '@increment',
    name: Mock.mock('@cname'),
    date: new Date().getTime(),
    check: [1, 3, 5]
  }))
}
export const roleTableData = {
  total: 11,
  pageSize: 10,
  tableData: roleList
}
