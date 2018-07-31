/*
* @Author: chenjiandong
* @Date:   last_modified_time
* @Last Modified by:   chenjiandong
* @Last Modified time: last_modified_time
*/
import * as login from './login'
import * as user from './system/user'
import * as role from './system/role'
import * as permission from './system/permission'
import * as baseAiisAirport from './system/aiis-airport'
import * as baseAirport from './base/airport'
import * as registration from './registration'
import * as versionManagement from './system/version-management'
export default Object.assign({}, login, user, role, permission, baseAiisAirport, baseAirport, registration,
versionManagement)

